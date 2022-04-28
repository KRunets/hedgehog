package by.runets.hedgehog.event.impl;

import by.runets.hedgehog.domain.Notification;
import by.runets.hedgehog.event.EventConsumer;
import by.runets.hedgehog.dispatcher.NotificationDispatcher;
import by.runets.hedgehog.event.VerificationEvent;
import by.runets.hedgehog.event.dto.TemplateRequestDto;
import by.runets.hedgehog.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaEventConsumer implements EventConsumer {

    private static final Logger LOG = LogManager.getLogger(KafkaEventConsumer.class);

    private static final String CODE = "code";

    @Value("${template.service.url}")
    private String templateServiceUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationDispatcher notificationDispatcher;

    @Override
    @KafkaListener(topics = "VERIFICATION_TOPIC", containerFactory = "kafkaListenerContainerFactory")
    public void consumeEvent(VerificationEvent verificationEvent) {
        try {
            final ResponseEntity<String> templateResponseEntity = requestTemplate(verificationEvent);
            final Notification notification = buildTemplateRequestDto(verificationEvent, templateResponseEntity);
            notificationDispatcher.dispatchNotification(notification);
            notificationService.save(notification);
        } catch (JsonProcessingException e) {
            LOG.error("Json malformed. ", e);
        }
    }

    private ResponseEntity<String> requestTemplate(VerificationEvent verificationEvent) throws JsonProcessingException {
        final TemplateRequestDto templateRequestDto = buildTemplateRequestDto(verificationEvent);
        final String json = objectMapper.writeValueAsString(templateRequestDto);
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);
        return restTemplate.postForEntity(templateServiceUrl, request, String.class);
    }

    private Notification buildTemplateRequestDto(VerificationEvent verificationEvent, ResponseEntity<String> templateResponseEntity) {
        return new Notification.NotificationBuilder()
                .recipient(verificationEvent.getSubject().getIdentity())
                .channel(verificationEvent.getSubject().getType())
                .body(templateResponseEntity.getBody())
                .build();
    }

    private TemplateRequestDto buildTemplateRequestDto(VerificationEvent verificationEvent) {
        final Map<String, String> variables = new HashMap<>();
        variables.put(CODE, verificationEvent.getCode());
        return new TemplateRequestDto.TemplateRequestDtoBuilder()
                .slug(verificationEvent.getSubject().getType())
                .variables(variables)
                .build();
    }

    public void setNotificationDispatcher(NotificationDispatcher notificationDispatcher) {
        this.notificationDispatcher = notificationDispatcher;
    }
    public void setTemplateServiceUrl(String templateServiceUrl) {
        this.templateServiceUrl = templateServiceUrl;
    }
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}

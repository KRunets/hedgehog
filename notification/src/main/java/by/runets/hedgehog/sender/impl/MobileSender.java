package by.runets.hedgehog.sender.impl;

import by.runets.hedgehog.domain.Notification;
import by.runets.hedgehog.sender.Message;
import by.runets.hedgehog.sender.Sender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import static by.runets.hedgehog.utils.Constants.*;

@Service(MOBILE_SENDER)
public class MobileSender implements Sender {

    private static final Logger LOG = LogManager.getLogger(MobileSender.class);

    @Value(GOTIFY_SUBJECT_KEY)
    private String gotifyServiceSubject;
    @Value(GOTIFY_SERVICE_URL_KEY)
    private String gotifyServiceUrl;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void send(Notification notification) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            final Message message = buildMessage(notification);
            final String json = objectMapper.writeValueAsString(message);
            final HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);
            restTemplate.postForEntity(gotifyServiceUrl, request, String.class);
        } catch (JsonProcessingException e) {
            LOG.error("Json malformed.", e);
        }
    }

    private Message buildMessage(Notification notification) {
        final Message message = new Message.MessageBuilder()
                .message(notification.getBody())
                .title(gotifyServiceSubject)
                .build();
        return message;
    }

    public void setGotifyServiceSubject(String gotifyServiceSubject) {
        this.gotifyServiceSubject = gotifyServiceSubject;
    }
    public void setGotifyServiceUrl(String gotifyServiceUrl) {
        this.gotifyServiceUrl = gotifyServiceUrl;
    }
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}

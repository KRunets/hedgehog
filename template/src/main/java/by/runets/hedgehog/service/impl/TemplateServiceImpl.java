package by.runets.hedgehog.service.impl;

import by.runets.hedgehog.domain.Template;
import by.runets.hedgehog.exception.ResourceNotFoundException;
import by.runets.hedgehog.repository.TemplateRepository;
import by.runets.hedgehog.resource.dto.TemplateRequestDto;
import by.runets.hedgehog.service.TemplateService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TemplateServiceImpl implements TemplateService {

    private static final String CODE_KEY = "code";
    private static final String CODE_TEMPLATE = "{code}";

    @Autowired
    private TemplateRepository templateRepository;

    @Override
    @Transactional
    public String renderTemplateBySlug(TemplateRequestDto templateRequestDto) {

        final String slug = templateRequestDto.getSlug();
        final Template template = templateRepository.readTemplateBySlug(slug);

        if (template == null) {
            throw new ResourceNotFoundException("Template by slug = " + slug + " does not exists");
        }

        String body = template.getBody();
        if (!Strings.isNullOrEmpty(body)) {
            body = body.replace(CODE_TEMPLATE, templateRequestDto.getVariables().get(CODE_KEY));
        }

        return body;
    }

}

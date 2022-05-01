package by.runets.hedgehog.service;


import by.runets.hedgehog.resource.dto.TemplateRequestDto;

public interface TemplateService {
    String renderTemplateBySlug(TemplateRequestDto templateRequestDto);
}

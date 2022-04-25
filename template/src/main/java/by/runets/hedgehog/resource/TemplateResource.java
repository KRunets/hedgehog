package by.runets.hedgehog.resource;

import by.runets.hedgehog.resource.dto.TemplateRequestDto;
import by.runets.hedgehog.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TemplateResource {

    @Autowired
    private TemplateService templateService;

    @PostMapping("/templates/render")
    public ResponseEntity<String> renderTemplateByVerificationType(@Valid @RequestBody TemplateRequestDto templateRequestDto) {
        final String template = templateService.renderTemplateBySlug(templateRequestDto);
        return ResponseEntity.ok(template);
    }
}

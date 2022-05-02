package by.runets.hedgehog.service;

import by.runets.hedgehog.domain.Template;
import by.runets.hedgehog.repository.TemplateRepository;
import by.runets.hedgehog.resource.dto.TemplateRequestDto;
import by.runets.hedgehog.service.impl.TemplateServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
public class TemplateServiceTest {

    @TestConfiguration
    static class TemplateServiceImplTestContextConfiguration {
        @Bean
        public TemplateService templateService() {
            return new TemplateServiceImpl();
        }
    }

    @Autowired
    private TemplateService templateService;

    @MockBean
    private TemplateRepository templateRepository;

    @Before
    public void setUp() {
        final Template template = new Template.TemplateBuilder()
                .body("Test body")
                .slug("test_slug")
                .build();

        Mockito.when(templateRepository.readTemplateBySlug("test_slug"))
                .thenReturn(template);
    }

    @Test
    public void testRenderTemplateBySlug() {
        final Map<String, String> variables = new HashMap<>();
        variables.put("code", "1234");
        final TemplateRequestDto templateRequestDto = new TemplateRequestDto.TemplateRequestDtoBuilder()
                .slug("test_slug")
                .variables(variables)
                .build();

        final String template = templateService.renderTemplateBySlug(templateRequestDto);

        Assert.assertEquals(template, "Test body");
    }
}

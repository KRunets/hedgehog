package by.runets.hedgehog.resource;

import by.runets.hedgehog.exception.ResourceNotFoundException;
import by.runets.hedgehog.resource.dto.TemplateRequestDto;
import by.runets.hedgehog.service.TemplateService;
import by.runets.hedgehog.validator.GenericConstraintValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static by.runets.hedgehog.utils.Constants.MOBILE_VERIFICATION;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TemplateResource.class)
public class TemplateResourceTest {

    @TestConfiguration
    static class GenericConstraintValidatorTestContextConfiguration {
        @Bean
        public GenericConstraintValidator genericConstraintValidator() {
            return new GenericConstraintValidator();
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TemplateService templateService;

    @Test
    public void givenTemplate_whenGetTemplate_then_200() throws Exception {
        //given
        final Map<String, String> variables = new HashMap<>();
        variables.put("code", "1234");
        final TemplateRequestDto templateRequestDto = new TemplateRequestDto.TemplateRequestDtoBuilder().slug(MOBILE_VERIFICATION).variables(variables).build();

        //when
        given(templateService.renderTemplateBySlug(templateRequestDto)).willReturn("template");

        //then
        mvc.perform(post("/templates/render")
                        .content(asJsonString(templateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenTemplate_whenGetWithoutCode_then_422() throws Exception {
        //given
        final Map<String, String> variables = new HashMap<>();
        variables.put("code", "");
        final TemplateRequestDto templateRequestDto = new TemplateRequestDto.TemplateRequestDtoBuilder().slug(MOBILE_VERIFICATION).variables(variables).build();

        //when
        given(templateService.renderTemplateBySlug(templateRequestDto)).willReturn("template");

        //then
        mvc.perform(post("/templates/render")
                                .content(asJsonString(templateRequestDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @Test
    public void givenTemplate_whenGetWrongSlug_then_422() throws Exception {
        //given
        final Map<String, String> variables = new HashMap<>();
        variables.put("code", "1234");
        final TemplateRequestDto templateRequestDto = new TemplateRequestDto.TemplateRequestDtoBuilder().slug("wrong_slug").variables(variables).build();

        //when
        given(templateService.renderTemplateBySlug(templateRequestDto)).willReturn("template");

        //then
        mvc.perform(post("/templates/render")
                        .content(asJsonString(templateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @Test
    public void givenTemplate_whenGetWithMalformed_then_400() throws Exception {
        //given
        final Map<String, String> variables = new HashMap<>();
        variables.put("code", "1234");
        final TemplateRequestDto templateRequestDto = new TemplateRequestDto.TemplateRequestDtoBuilder().slug("wrong_slug").variables(variables).build();

        //when
        given(templateService.renderTemplateBySlug(templateRequestDto)).willReturn("template");
        final String json = asJsonString(templateRequestDto);
        final String malformedJson = json.replace(",", "qqqwwweee");
        //then
        mvc.perform(post("/templates/render")
                        .content(malformedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void givenTemplate_whenGetWithMalformed_then_404() throws Exception {
        //given
        final Map<String, String> variables = new HashMap<>();
        variables.put("code", "1234");

        final TemplateRequestDto templateRequestDto = new TemplateRequestDto.TemplateRequestDtoBuilder()
                .slug(MOBILE_VERIFICATION)
                .variables(variables)
                .build();
        final String json = asJsonString(templateRequestDto);
        given(templateService.renderTemplateBySlug(templateRequestDto)).willThrow(ResourceNotFoundException.class);
        //then
        mvc.perform(post("/templates/render")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

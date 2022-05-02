package by.runets.hedgehog.resource;

import by.runets.hedgehog.exception.NoPermissionException;
import by.runets.hedgehog.exception.ResourceNotFoundException;
import by.runets.hedgehog.resource.dto.VerificationCodeDto;
import by.runets.hedgehog.resource.dto.VerificationRequestDto;
import by.runets.hedgehog.service.VerificationService;
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

import java.util.UUID;

import static by.runets.hedgehog.utils.Constants.MOBILE_CONFIRMATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VerificationResource.class)
public class VerificationResourceTest {

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
    private VerificationService verificationService;

    @Test
    public void givenTemplate_whenCreateVerification_then_200() throws Exception {
        //given
        final VerificationRequestDto verificationRequestDto = new VerificationRequestDto.VerificationDtoBuilder()
                .identity("+375296946811")
                .type(MOBILE_CONFIRMATION)
                .build();

        //when
        given(verificationService.createVerification(any(), any())).willReturn(UUID.randomUUID());

        //then
        mvc.perform(post("/verifications/")
                        .content(asJsonString(verificationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenTemplate_whenCreateVerification_then_422() throws Exception {
        //given
        final VerificationRequestDto verificationRequestDto = new VerificationRequestDto.VerificationDtoBuilder()
                .identity("+375296946811")
                .type("wrong")
                .build();

        //when
        given(verificationService.createVerification(any(), any())).willReturn(UUID.randomUUID());

        //then
        mvc.perform(post("/verifications/")
                        .content(asJsonString(verificationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @Test
    public void givenTemplate_whenCreateVerification_then_400() throws Exception {
        //given
        final VerificationRequestDto verificationRequestDto = new VerificationRequestDto.VerificationDtoBuilder()
                .identity("+375296946811")
                .type("wrong")
                .build();

        //when
        given(verificationService.createVerification(any(), any())).willReturn(UUID.randomUUID());

        final String malformedJson = asJsonString(verificationRequestDto).replace(",", "$");

        //then
        mvc.perform(post("/verifications/")
                        .content(malformedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void givenTemplate_whenConfirmVerification_then_204() throws Exception {
        //given
        final VerificationCodeDto verificationCodeDto = new VerificationCodeDto();
        verificationCodeDto.setCode("12345678");
        //when
        given(verificationService.confirmVerification(any(), any(), any())).willReturn(true);

        //then
        mvc.perform(put("/verifications/" + UUID.randomUUID() + "/confirm")
                        .content(asJsonString(verificationCodeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void givenTemplate_whenConfirmVerification_then_406() throws Exception {
        //given
        final VerificationCodeDto verificationCodeDto = new VerificationCodeDto();
        verificationCodeDto.setCode("12345678");
        //when
        given(verificationService.confirmVerification(any(), any(), any())).willReturn(false);

        //then
        mvc.perform(put("/verifications/" + UUID.randomUUID() + "/confirm")
                        .content(asJsonString(verificationCodeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
    }

    @Test
    public void givenTemplate_whenCreateVerification_then_404() throws Exception {
        //given
        final VerificationRequestDto verificationRequestDto = new VerificationRequestDto.VerificationDtoBuilder()
                .identity("+375296946811")
                .type(MOBILE_CONFIRMATION)
                .build();

        //when
        given(verificationService.createVerification(any(), any())).willThrow(ResourceNotFoundException.class);

        //then
        mvc.perform(post("/verifications/")
                        .content(asJsonString(verificationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void givenTemplate_whenCreateVerification_then_401() throws Exception {
        //given
        final VerificationRequestDto verificationRequestDto = new VerificationRequestDto.VerificationDtoBuilder()
                .identity("+375296946811")
                .type(MOBILE_CONFIRMATION)
                .build();

        //when
        given(verificationService.createVerification(any(), any())).willThrow(NoPermissionException.class);

        //then
        mvc.perform(post("/verifications/")
                        .content(asJsonString(verificationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

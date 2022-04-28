package by.runets.hedgehog.resource.dto;

import by.runets.hedgehog.validator.annotation.ValidateConfirmationType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class VerificationRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String identity;
    @NotNull
    @ValidateConfirmationType
    private String type;

    public VerificationRequestDto() {
    }

    public VerificationRequestDto(VerificationDtoBuilder verificationDtoBuilder) {
        this.identity = verificationDtoBuilder.identity;
        this.type = verificationDtoBuilder.type;
    }

    public String getIdentity() {
        return identity;
    }
    public String getType() {
        return type;
    }

    public static class VerificationDtoBuilder {

        private String identity;
        private String type;

        public VerificationDtoBuilder subject(String identity) {
            this.identity = identity;
            return this;
        }
        public VerificationDtoBuilder confirmed(String type) {
            this.type = type;
            return this;
        }

        public VerificationRequestDto build() {
            return new VerificationRequestDto(this);
        }
    }
}

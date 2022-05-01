package by.runets.hedgehog.resource.dto;

import by.runets.hedgehog.validator.annotation.Code;

import java.io.Serializable;
import java.util.Objects;

public class VerificationCodeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Code
    private String code;

    public VerificationCodeDto() {
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationCodeDto that = (VerificationCodeDto) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}

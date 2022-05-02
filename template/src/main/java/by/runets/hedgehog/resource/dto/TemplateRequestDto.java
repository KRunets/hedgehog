package by.runets.hedgehog.resource.dto;

import by.runets.hedgehog.validator.annotation.NotEmptyCode;
import by.runets.hedgehog.validator.annotation.ValidateSlug;

import java.util.Map;
import java.util.Objects;

public class TemplateRequestDto {
    @ValidateSlug
    private String slug;
    @NotEmptyCode
    private Map<String, String> variables;

    public TemplateRequestDto() {
    }

    public TemplateRequestDto(TemplateRequestDtoBuilder templateRequestDtoBuilder) {
        this.slug = templateRequestDtoBuilder.slug;
        this.variables = templateRequestDtoBuilder.variables;
    }

    public String getSlug() {
        return slug;
    }
    public Map<String, String> getVariables() {
        return variables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateRequestDto that = (TemplateRequestDto) o;
        return Objects.equals(slug, that.slug) && Objects.equals(variables, that.variables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, variables);
    }

    public static class TemplateRequestDtoBuilder {

        private String slug;
        private Map<String, String> variables;

        public TemplateRequestDtoBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public TemplateRequestDtoBuilder variables(Map<String, String> variables) {
            this.variables = variables;
            return this;
        }

        public TemplateRequestDto build() {
            return new TemplateRequestDto(this);
        }
    }

}

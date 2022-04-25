package by.runets.hedgehog.resource.dto;

import by.runets.hedgehog.validator.annotation.NotEmptyCode;
import by.runets.hedgehog.validator.annotation.SlugAllowedValues;

import java.util.Map;
import java.util.Objects;

public class TemplateRequestDto {
    @SlugAllowedValues
    private String slug;
    @NotEmptyCode
    private Map<String, String> variables;

    public TemplateRequestDto(String slug, Map<String, String> variables) {
        this.slug = slug;
        this.variables = variables;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
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
}

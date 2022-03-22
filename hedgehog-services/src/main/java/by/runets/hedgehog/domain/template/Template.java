package by.runets.hedgehog.domain.template;

import by.runets.hedgehog.domain.verification.VerificationType;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public final class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final VerificationType type;
    private final String body;

    public Template(TemplateBuilder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.body = builder.body;
    }

    public UUID getId() {
        return id;
    }
    public VerificationType getType() {
        return type;
    }
    public String getBody() {
        return body;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(id, template.id) && type == template.type && Objects.equals(body, template.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, body);
    }

    public static class TemplateBuilder {

        private UUID id;
        private VerificationType type;
        private String body;

        public TemplateBuilder id(UUID id) {
            this.id = id;
            return this;
        }
        public TemplateBuilder type(VerificationType type) {
            this.type = type;
            return this;
        }
        public TemplateBuilder body(String body) {
            this.body = body;
            return this;
        }

        public Template build() {
            return new Template(this);
        }

    }


}

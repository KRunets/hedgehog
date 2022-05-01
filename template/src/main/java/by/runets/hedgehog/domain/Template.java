package by.runets.hedgehog.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String slug;
    private String body;

    public Template() {
    }

    public Template(TemplateBuilder builder) {
        this.id = UUID.randomUUID();
        this.slug = builder.slug;
        this.body = builder.body;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return slug;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(id, template.id) && slug == template.slug && Objects.equals(body, template.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slug, body);
    }

    public static class TemplateBuilder {

        private String slug;
        private String body;

        public TemplateBuilder slug(String slug) {
            this.slug = slug;
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

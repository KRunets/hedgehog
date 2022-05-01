package by.runets.hedgehog.domain;

import java.io.Serializable;
import java.util.Objects;

public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String identity;
    private String type;

    public Subject() {
    }

    public Subject(final SubjectBuilder subjectBuilder) {
        this.identity = subjectBuilder.identity;
        this.type = subjectBuilder.type;
    }

    public String getIdentity() {
        return identity;
    }
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(identity, subject.identity) && type == subject.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity, type);
    }

    public static class SubjectBuilder {

        private String identity;
        private String type;

        public SubjectBuilder identity(String identity) {
            this.identity = identity;
            return this;
        }
        public SubjectBuilder type(String type) {
            this.type = type;
            return this;
        }

        public Subject build() {
            return new Subject(this);
        }
    }

}

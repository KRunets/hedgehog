package by.runets.hedgehog.domain.verification;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public final class Verification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private final UUID id;
    private final Subject subject;
    private final boolean confirmed;
    private final String code;
    private final UserInfo userInfo;

    public Verification(VerificationBuilder verificationBuilder) {
        this.id = UUID.randomUUID();
        this.subject = verificationBuilder.subject;
        this.confirmed = verificationBuilder.confirmed;
        this.code = verificationBuilder.code;
        this.userInfo = verificationBuilder.userInfo;
    }

    public UUID getId() {
        return id;
    }
    public Subject getSubject() {
        return subject;
    }
    public boolean isConfirmed() {
        return confirmed;
    }
    public String getCode() {
        return code;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Verification that = (Verification) o;
        return confirmed == that.confirmed && Objects.equals(id, that.id) && Objects.equals(subject, that.subject) && Objects.equals(code, that.code) && Objects.equals(userInfo, that.userInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, confirmed, code, userInfo);
    }

    public static class VerificationBuilder {

        private Subject subject;
        private boolean confirmed;
        private String code;
        private UserInfo userInfo;

        public VerificationBuilder subject(Subject subject) {
            this.subject = subject;
            return this;
        }
        public VerificationBuilder confirmed(boolean confirmed) {
            this.confirmed = confirmed;
            return this;
        }
        public VerificationBuilder code(String code) {
            this.code = code;
            return this;
        }
        public VerificationBuilder userInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public Verification build() {
            return new Verification(this);
        }
    }

}

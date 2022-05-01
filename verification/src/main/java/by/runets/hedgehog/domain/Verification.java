package by.runets.hedgehog.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "verification")
public class Verification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    @Embedded
    private Subject subject;
    @Embedded
    private UserInfo userInfo;
    private String code;
    private boolean confirmed;
    private boolean expired;

    public Verification() {
    }

    public Verification(VerificationBuilder verificationBuilder) {
        this.id = UUID.randomUUID();
        this.subject = verificationBuilder.subject;
        this.confirmed = verificationBuilder.confirmed;
        this.code = verificationBuilder.code;
        this.userInfo = verificationBuilder.userInfo;
        this.expired = verificationBuilder.expired;
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
    public boolean isExpired() {
        return expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Verification that = (Verification) o;
        return confirmed == that.confirmed && expired == that.expired && Objects.equals(id, that.id) && Objects.equals(subject, that.subject) && Objects.equals(userInfo, that.userInfo) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, userInfo, code, confirmed, expired);
    }

    public static class VerificationBuilder {

        private Subject subject;
        private boolean confirmed;
        private boolean expired;
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
        public VerificationBuilder expired(boolean expired) {
            this.expired = expired;
            return this;
        }

        public Verification build() {
            return new Verification(this);
        }
    }

}

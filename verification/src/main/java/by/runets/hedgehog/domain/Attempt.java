package by.runets.hedgehog.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "attempt")
public class Attempt {

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    @Embedded
    private UserInfo userInfo;
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID verificationId;
    private Integer times;

    public Attempt() {
    }

    public Attempt(AttemptBuilder attemptBuilder) {
        this.id = UUID.randomUUID();
        this.userInfo = attemptBuilder.userInfo;
        this.verificationId = attemptBuilder.verificationId;
        this.times = attemptBuilder.times;
    }

    public UUID getId() {
        return id;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public UUID getVerificationId() {
        return verificationId;
    }
    public Integer getTimes() {
        return times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attempt attempt = (Attempt) o;
        return Objects.equals(id, attempt.id) && Objects.equals(userInfo, attempt.userInfo) && Objects.equals(verificationId, attempt.verificationId) && Objects.equals(times, attempt.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userInfo, verificationId, times);
    }

    public static class AttemptBuilder {

        private UserInfo userInfo;
        private UUID verificationId;
        private Integer times;

        public AttemptBuilder userInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }
        public AttemptBuilder verificationId(UUID verificationId) {
            this.verificationId = verificationId;
            return this;
        }
        public AttemptBuilder times(Integer times) {
            this.times = times;
            return this;
        }

        public Attempt build() {
            return new Attempt(this);
        }
    }
}

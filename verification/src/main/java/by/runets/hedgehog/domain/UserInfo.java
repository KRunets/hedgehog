package by.runets.hedgehog.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Embeddable
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userAgent;
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] ipAddress;

    public UserInfo() {
    }

    public UserInfo(UserInfoBuilder builder) {
        this.userAgent = builder.userAgent;
        this.ipAddress = builder.ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }
    public byte[] getIpAddress() {
        return ipAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(userAgent, userInfo.userAgent) && Arrays.equals(ipAddress, userInfo.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAgent, ipAddress);
    }

    public static class UserInfoBuilder {

        private String userAgent;
        private byte[] ipAddress;

        public UserInfoBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }
        public UserInfoBuilder ipAddress(byte[] ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(this);
        }
    }

}

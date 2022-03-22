package by.runets.hedgehog.domain.verification;

import java.io.Serializable;
import java.util.Objects;

public final class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String userAgent;
    private final String ipAddress;

    public UserInfo(UserInfoBuilder builder) {
        this.userAgent = builder.userAgent;
        this.ipAddress = builder.ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(userAgent, userInfo.userAgent) && Objects.equals(ipAddress, userInfo.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAgent, ipAddress);
    }

    public static class UserInfoBuilder {

        private String userAgent;
        private String ipAddress;

        public UserInfoBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }
        public UserInfoBuilder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(this);
        }
    }

}

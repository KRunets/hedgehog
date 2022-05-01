package by.runets.hedgehog.resource.dto;

import java.io.Serializable;

public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userAgent;
    private String clientIp;

    public UserInfoDto(UserInfoDtoBuilder userInfoDtoBuilder) {
        this.userAgent = userInfoDtoBuilder.userAgent;
        this.clientIp = userInfoDtoBuilder.clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }
    public String getClientIp() {
        return clientIp;
    }

    public static class UserInfoDtoBuilder {

        private String userAgent;
        private String clientIp;

        public UserInfoDtoBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }
        public UserInfoDtoBuilder clientIp(String clientIp) {
            this.clientIp = clientIp;
            return this;
        }

        public UserInfoDto build() {
            return new UserInfoDto(this);
        }
    }
}

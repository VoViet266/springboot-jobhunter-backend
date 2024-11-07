package vn.hoidanit.jobhunter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestLoginDTO {
    @JsonProperty("access_token")
    private String accessToken;
    private UserLogin user;
    private UserGetAccount userGetAccount;

    public RestLoginDTO(String accessToken, UserLogin user, UserGetAccount userGetAccount) {
        this.accessToken = accessToken;
        this.user = user;
        this.userGetAccount = userGetAccount;
    }

    public RestLoginDTO() {
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

    public static class UserLogin {
        private Long id;
        private String name;
        private String email;

        public UserLogin(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public UserLogin() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class UserGetAccount {
        private UserLogin user;

        public UserGetAccount(UserLogin user) {
            this.user = user;
        }

        public UserGetAccount() {
        }

        public UserLogin getUser() {
            return user;
        }

        public void setUser(UserLogin user) {
            this.user = user;
        }

    }

    public UserGetAccount getUserGetAccount() {
        return userGetAccount;
    }

    public void setUserGetAccount(UserGetAccount userGetAccount) {
        this.userGetAccount = userGetAccount;
    }
}

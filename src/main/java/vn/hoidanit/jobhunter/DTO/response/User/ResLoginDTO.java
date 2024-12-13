package vn.hoidanit.jobhunter.dto.response.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResLoginDTO {

    private UserLogin user;
    @JsonProperty("access_token")
    private String accessToken;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin {
        private Long id;
        private String name;
        private String email;
        private RoleUser role;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGetAccount {
        private UserLogin user;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleUser {
        private Long id;
        private String name;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInsideToken {
        private Long id;
        private String name;
        private String email;

    }
}

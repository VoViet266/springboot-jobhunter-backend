package vn.hoidanit.jobhunter.DTO.response;

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
    @JsonProperty("access_token")
    private String accessToken;
    private UserLogin user;
    private UserGetAccount userGetAccount;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin {
        private Long id;
        private String name;
        private String email;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGetAccount {
        private UserLogin user;
    }

}

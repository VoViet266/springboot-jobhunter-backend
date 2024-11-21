package vn.hoidanit.jobhunter.dto.response.User;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDTO {
    private Long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant createdAt;
    private Instant updatedAt;
    private CompanyUser company;
    private RoleUser role;

    
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompanyUser{
        private Long id;
        private String name;
    }
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleUser{
        private Long id;
        private String name;
    }

}

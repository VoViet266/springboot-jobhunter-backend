package vn.hoidanit.jobhunter.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private String address;
    private int age;
    private GenderEnum gender;
}
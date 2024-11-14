package vn.hoidanit.jobhunter.DTO.response.User;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReponseDTO {

    private String username;
    private String email;
    private String address;
    private int age;
    private GenderEnum gender;
    private Instant createdAt;
  
    
}

package vn.hoidanit.jobhunter.DTO.response;

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

    
    
    
}

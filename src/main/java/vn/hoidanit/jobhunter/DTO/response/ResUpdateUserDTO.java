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
public class ResUpdateUserDTO {

    private Long id;
    private String name;
    private String address;
    private int age;
    private GenderEnum gender;
    private Instant updateAt;
    private String updatedBy;

}

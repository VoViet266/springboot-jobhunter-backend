package vn.hoidanit.jobhunter.DTO.response.Resume;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.ResumeStateEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUpdateResumeDTO {
    private Long id;
    private String email;
    private String url;
    private ResumeStateEnum status;
    private Instant updateAt;
    private String updateBy;
}

package vn.hoidanit.jobhunter.DTO.response.Resume;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResCreateResumeDTO {
    private Long id;
    private Instant createdAt;
    private String createdBy;
}

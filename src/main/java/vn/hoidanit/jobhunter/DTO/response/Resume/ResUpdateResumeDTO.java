package vn.hoidanit.jobhunter.DTO.response.Resume;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUpdateResumeDTO {
    private Instant updatedAt;
    private String updatedBy;
}

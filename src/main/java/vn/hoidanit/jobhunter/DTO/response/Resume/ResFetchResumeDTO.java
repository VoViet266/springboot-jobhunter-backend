package vn.hoidanit.jobhunter.DTO.response.Resume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import vn.hoidanit.jobhunter.util.constant.ResumeStateEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResFetchResumeDTO {
    private Long id;
    private String email;
    private String url;
    private ResumeStateEnum status;
    private String createdBy;
    private String updateBy;
    private Instant createdAt;
    private Instant updateAt;
    private ResumeUser user;
    private ResumeJob job;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResumeUser {
        private Long id;
        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResumeJob {
        private Long id;
        private String name;
    }

}

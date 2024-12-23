package vn.hoidanit.jobhunter.dto.response.Jobs;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.constant.LevelEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResCreateJobDTO {
    private Long id;
    private String name;
    private String location;
    private Double salary;
    private int quantity;
    private LevelEnum lever;
    private Instant startDate;
    private Instant endDate;
    private boolean actived;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    
    private CompanyJob company;

    private List<String> skills;



    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompanyJob {
        private Long id;
        private String name;
    }

    @Setter
    @Getter
    public static class SkillJob {
        private Long id;
        private String name;
    }
}

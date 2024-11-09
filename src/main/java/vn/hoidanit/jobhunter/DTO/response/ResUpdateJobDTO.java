package vn.hoidanit.jobhunter.DTO.response;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.LevelEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResUpdateJobDTO {
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

    private List<String> skills;
}

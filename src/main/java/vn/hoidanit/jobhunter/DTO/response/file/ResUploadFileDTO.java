package vn.hoidanit.jobhunter.DTO.response.file;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUploadFileDTO {
    private String fileName;
    private Instant createdAt;
}

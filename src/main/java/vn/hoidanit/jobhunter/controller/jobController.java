package vn.hoidanit.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.dto.response.Jobs.ResCreateJobDTO;
import vn.hoidanit.jobhunter.dto.response.Jobs.ResUpdateJobDTO;
import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Job;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;
import vn.hoidanit.jobhunter.service.jobService;

@RestController
@RequestMapping("/api/v1")
public class jobController {

    private final jobService jobService;

    public jobController(jobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<ResultPaginationDTO> getJobs(
            @Filter Specification<Job> specification,
            Pageable pageable) {
        ResultPaginationDTO resultPaginationDTO = jobService.handleGetAllJob(specification, pageable);
        return ResponseEntity.ok(resultPaginationDTO);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJob(@PathVariable("id") Long id) throws IdInvalidException, Exception {
        Optional<Job> jobOptional = jobService.handleGetJobByID(id);
        if (!jobOptional.isPresent()) {
            throw new IdInvalidException("Job not found");
        }
        return ResponseEntity.ok(jobOptional.get());
    }

    @PostMapping("/jobs")
    public ResponseEntity<ResCreateJobDTO> createJoḅ̣̣(@RequestBody Job job)
            throws Exception {
        List<Job> jobList = jobService.findByName(job.getName());
        if (jobList.size() > 1) {
            throw new Exception("Job name already exists");
        }
        return ResponseEntity.ok(this.jobService.handleCreateJob(job));
    }

    @PutMapping("/jobs")
    public ResponseEntity<ResUpdateJobDTO> updateJob(@RequestBody Job job) throws IdInvalidException {
        Optional<Job> currentJob = jobService.handleGetJobByID(job.getId());
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }
        return ResponseEntity.ok(jobService.handleUpdateJob(job, currentJob.get()));

    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@Valid @PathVariable Long id)
            throws IdInvalidException, Exception {
        Optional<Job> jobOptional = jobService.handleGetJobByID(id);
        if (!jobOptional.isPresent()) {
            throw new IdInvalidException("Job not found");
        }
        jobService.handleDeleteJob(id);
        return ResponseEntity.noContent().build();
    }
}

package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.DTO.response.ResCreateJobDTO;
import vn.hoidanit.jobhunter.DTO.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.Job;
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
        Pageable pageable
    ) {
        try {
            ResultPaginationDTO resultPaginationDTO = jobService.handleGetAllJob(specification, pageable);
            return ResponseEntity.ok(resultPaginationDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJob(@PathVariable("id") Long id) {
        Optional<Job> jobOptional = jobService.handleGetJobByID(id);
        if(!jobOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        return ResponseEntity.ok(jobOptional.get());
    }

    @PostMapping("/jobs/create")
    public ResponseEntity<ResCreateJobDTO> createJoḅ̣̣(@RequestBody Job job) {
        
            // this.jobService.handleCreateJob(job);
            return ResponseEntity.ok(this.jobService.handleCreateJob(job));
     
    }

    @PutMapping("/jobs/update")
    public ResponseEntity<ResCreateJobDTO> updateJob(@RequestBody Job job) {
        Optional<Job> jobOptional = jobService.handleGetJobByID(job.getId());
        if(!jobOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        return ResponseEntity.ok(jobService.handleCreateJob(job));
      
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") Long id) {
        Optional<Job> jobOptional = jobService.handleGetJobByID(id);
        if(!jobOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        jobService.handleDeleteJob(id);
        return ResponseEntity.ok().build();
    }
}

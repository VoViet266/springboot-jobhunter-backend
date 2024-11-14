package vn.hoidanit.jobhunter.controller;

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
import vn.hoidanit.jobhunter.DTO.response.Resume.ResCreateResumeDTO;
import vn.hoidanit.jobhunter.DTO.response.Resume.ResFetchResumeDTO;
import vn.hoidanit.jobhunter.DTO.response.Resume.ResUpdateResumeDTO;
import vn.hoidanit.jobhunter.DTO.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.Resume;
import vn.hoidanit.jobhunter.service.resumeService;

@RestController
@RequestMapping("/api/v1")
public class resumeController {

    private final resumeService resumeService;

    public resumeController(resumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/resumes")
    public ResponseEntity<ResCreateResumeDTO> createResume(@Valid @RequestBody Resume resume) throws Exception {
        boolean checkIdExist = this.resumeService.checkResumeExistByUserAndJob(resume);
        if (checkIdExist) {
            throw new Exception("Resume với id = " + resume.getId() + " không tồn tại");
        }
        return ResponseEntity.ok(this.resumeService.createResume(resume));
    }

    @PutMapping("/resumes")
    public ResponseEntity<ResUpdateResumeDTO> updateResume(@RequestBody Resume resume) throws Exception {
        Optional<Resume> resumeOptional = this.resumeService.getResumeById(resume.getId());
        if (!resumeOptional.isPresent()) {
            throw new Exception("Resume với id = " + resume.getId() + " không tồn tại");
        }
        Resume reqResume = resumeOptional.get();
        reqResume.setStatus(resume.getStatus());


            // ResUpdateResumeDTO res = new ResUpdateResumeDTO(
            //     reqResume.getId(),
            //     reqResume.getEmail(),
            //     reqResume.getUrl(),
            //     reqResume.getStatus(),
            //     reqResume.getUpdateAt(),
            //     reqResume.getUpdateBy()

            // );
        return ResponseEntity.ok(this.resumeService.updateResume(reqResume));
    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable("id") Long id) throws Exception {
        Optional<Resume> resumeOptional = this.resumeService.getResumeById(id);
        if (!resumeOptional.isPresent()) {
            throw new Exception("Resume với id = " + id + " không tồn tại");
        }
        this.resumeService.deleteResume(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/resumes/{id}")
    public ResponseEntity<ResFetchResumeDTO> getResumeById(@Valid @PathVariable("id") Long id) throws Exception {
        Optional<Resume> resumeOptional = this.resumeService.getResumeById(id);
        if (!resumeOptional.isPresent()) {
            throw new Exception("Resume với id = " + id + " không tồn tại");    
        }
        ResFetchResumeDTO res = new ResFetchResumeDTO();
        res.setId(resumeOptional.get().getId());
        res.setEmail(resumeOptional.get().getEmail());
        res.setUrl(resumeOptional.get().getUrl());
        res.setStatus(resumeOptional.get().getStatus());
        res.setCreatedBy(resumeOptional.get().getCreatedBy());
        res.setUpdateBy(resumeOptional.get().getUpdatedBy());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/resumes")
    public ResponseEntity<ResultPaginationDTO > getAllResume (
        @Filter Specification<Resume> spec,
         Pageable pageable
    )  {
            ResultPaginationDTO resultPaginationDTO = resumeService.getAllResume(spec, pageable);
            return ResponseEntity.ok(resultPaginationDTO);
      
    }
}

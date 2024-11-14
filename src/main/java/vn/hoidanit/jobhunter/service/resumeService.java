package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.DTO.response.Resume.ResCreateResumeDTO;
import vn.hoidanit.jobhunter.DTO.response.Resume.ResFetchResumeDTO;
import vn.hoidanit.jobhunter.DTO.response.Resume.ResUpdateResumeDTO;
import vn.hoidanit.jobhunter.DTO.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.Resume;
import vn.hoidanit.jobhunter.repository.jobRepository;
import vn.hoidanit.jobhunter.repository.resumeRepository;
import vn.hoidanit.jobhunter.repository.userRepository;

@Service
public class resumeService {
    private final resumeRepository resumeRepository;
    private final userRepository userRepository;
    private final jobRepository jobRepository;

    public resumeService(resumeRepository resumeRepository, userRepository userRepository,
            jobRepository jobRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public ResCreateResumeDTO createResume(Resume resume) {
        resume = this.resumeRepository.save(resume);
        ResCreateResumeDTO res = new ResCreateResumeDTO(
                resume.getId(),
                resume.getCreatedAt(),
                resume.getCreatedBy());
        return res;
    }

    public ResUpdateResumeDTO updateResume(Resume resume) {
        Resume updatedResume = this.resumeRepository.save(resume);
        ResUpdateResumeDTO res = new ResUpdateResumeDTO(
                updatedResume.getUpdateAt(),
                updatedResume.getUpdateBy());
        return res;
    }

    public void deleteResume(Long id) {
        this.resumeRepository.deleteById(id);
    }

    public Optional<Resume> getResumeById(Long id) {
        return this.resumeRepository.findById(id);
    }

    // public boolean isNameExist(String name) {
    //     return this.resumeRepository.existsByName(name);
    // }

    public ResFetchResumeDTO getResume(Resume resume) {
        ResFetchResumeDTO res = new ResFetchResumeDTO();
        res.setId(resume.getId());
        res.setEmail(resume.getEmail());
        res.setUrl(resume.getUrl());
        res.setStatus(resume.getStatus());
        res.setCreatedBy(resume.getCreatedBy());
        res.setUpdateBy(resume.getUpdateBy());
        res.setCreatedAt(resume.getCreatedAt());
        res.setUpdateAt(resume.getUpdateAt());

        res.setUser(new ResFetchResumeDTO.ResumeUser(resume.getUser().getId(), resume.getUser().getUsername()));
        res.setJob(new ResFetchResumeDTO.ResumeJob(resume.getJob().getId(), resume.getJob().getName()));

        return res;
    }

    public boolean checkResumeExistByUserAndJob(Resume resume) {
        if (resume.getUser() == null || resume.getJob() == null) {
            return false;
        }
        boolean userExists = this.resumeRepository.existsById(resume.getUser().getId());
        boolean jobExists = this.resumeRepository.existsById(resume.getJob().getId());
        if (!userExists || !jobExists) {
            return false;
        }
        return true;
    }

    public ResultPaginationDTO getAllResume(
            Specification<Resume> spec,
            Pageable pageable) {

        Page<Resume> pageResumes = this.resumeRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageResumes.getNumber() + 1);
        meta.setPageSize(pageResumes.getSize());

        meta.setPages(pageResumes.getTotalPages());
        meta.setTotal(pageResumes.getTotalElements());
        resultPaginationDTO.setMeta(meta);

        List<ResFetchResumeDTO> listResume = pageResumes.getContent()
        .stream()
        .map(item -> this.getResume(item))
                .collect(Collectors.toList());
        resultPaginationDTO.setResult(listResume);

        return resultPaginationDTO;

    }

}

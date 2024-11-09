package vn.hoidanit.jobhunter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.DTO.response.ResCreateJobDTO;
import vn.hoidanit.jobhunter.DTO.response.ResUpdateJobDTO;
import vn.hoidanit.jobhunter.DTO.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.Job;
import vn.hoidanit.jobhunter.Entity.Skill;
import vn.hoidanit.jobhunter.Entity.Company;
import vn.hoidanit.jobhunter.repository.jobRepository;
import vn.hoidanit.jobhunter.repository.skillRepository;

@Service
public class jobService {

    private final jobRepository jobRepository;
    private final skillRepository skillRepository;
    private final companyService companyService;

    public jobService(jobRepository jobRepository, skillRepository skillRepository, companyService companyService) {
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
        this.companyService = companyService;
    }

    public Optional<Job> handleGetJobByID(Long id) {
        return this.jobRepository.findById(id);
    }

    public List<Job> findByIdIn() {
        return this.jobRepository.findAll();
    }

    public ResultPaginationDTO handleGetAllJob(Specification<Job> specification, Pageable pageable) {
        Page<Job> jobPage = this.jobRepository.findAll(specification, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(jobPage.getNumber() + 1);
        meta.setPageSize(jobPage.getSize());

        meta.setPages(jobPage.getTotalPages());
        meta.setTotal(jobPage.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(jobPage.getContent());

        return resultPaginationDTO;
    }

    public ResCreateJobDTO handleCreateJob(Job job) {
        Company company = this.companyService.findById(job.getCompany().getId()).get();
        if (company != null) {
            job.setCompany(company);
        }
        if (job.getSkills() != null) {
            List<Long> skills = job.getSkills()
                    .stream()
                    .map(action -> action.getId())
                    .collect(Collectors.toList());
            List<Skill> skillList = skillRepository.findByIdIn(skills);
            job.setSkills(skillList);
        }
        Job curentJob = jobRepository.save(job);

        ResCreateJobDTO resCreateJobDTO = new ResCreateJobDTO(
                curentJob.getId(),
                curentJob.getName(),
                curentJob.getLocation(),
                curentJob.getSalary(),
                curentJob.getQuantity(),
                curentJob.getLever(),
                curentJob.getStartDate(),
                curentJob.getEndDate(),
                curentJob.isActived(),
                curentJob.getCreatedAt(),
                curentJob.getUpdatedAt(),
                curentJob.getCreatedBy(),
                curentJob.getUpdatedBy(),
                curentJob.getCompany() != null
                        ? new ResCreateJobDTO.CompanyJob(curentJob.getCompany()
                                .getId(),
                                curentJob.getCompany()
                                        .getName())
                        : null,
                curentJob.getSkills() != null
                        ? curentJob.getSkills()
                                .stream()
                                .map(Skill::getName)
                                .collect(Collectors.toList())
                        : Collections.emptyList());

        return resCreateJobDTO;
    }

    public ResUpdateJobDTO handleUpdateJob(Job job) {
        Optional<Job> jobExist = this.jobRepository.findById(job.getId());
        if (jobExist.isPresent()) {
            if (job.getSkills() != null) {
                List<Long> skills = job.getSkills()
                        .stream()
                        .map(action -> action.getId())
                        .collect(Collectors.toList());
                List<Skill> skillList = skillRepository.findByIdIn(skills);
                job.setSkills(skillList);

            }

            Job curentJob = jobRepository.save(job);
            ResUpdateJobDTO resUpdateJobDTO = new ResUpdateJobDTO(
                    curentJob.getId(),
                    curentJob.getName(),
                    curentJob.getLocation(),
                    curentJob.getSalary(),
                    curentJob.getQuantity(),
                    curentJob.getLever(),
                    curentJob.getStartDate(),
                    curentJob.getEndDate(),
                    curentJob.isActived(),
                    curentJob.getCreatedAt(),
                    curentJob.getUpdatedAt(),
                    curentJob.getCreatedBy(),
                    curentJob.getUpdatedBy(),
                    curentJob.getCompany() != null
                            ? new ResUpdateJobDTO.CompanyJob(curentJob.getCompany()
                                    .getId(),
                                    curentJob.getCompany()
                                            .getName())
                            : null,
                    curentJob.getSkills() != null
                            ? curentJob.getSkills()
                                    .stream()
                                    .map(action -> action.getName())
                                    .collect(Collectors.toList())
                            : Collections.emptyList());
            return resUpdateJobDTO;
        }
        return null;
    }

    public void handleDeleteJob(Long id) {
        this.jobRepository.deleteById(id);
    }
}
package vn.hoidanit.jobhunter.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.dto.response.Jobs.ResCreateJobDTO;
import vn.hoidanit.jobhunter.dto.response.Jobs.ResUpdateJobDTO;
import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Company;
import vn.hoidanit.jobhunter.entity.Job;
import vn.hoidanit.jobhunter.entity.Skill;
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

    public List<Job> findByName(String name) {
        return this.jobRepository.findByName(name);
    }

    // Hàm xử lý lấy tất cả công việc
    // Hàm này sẽ trả về một đối tượng ResultPaginationDTO
    // ResultPaginationDTO chứa thông tin về trang hiện tại, số trang, tổng số phần
    // tử và dữ liệu
    // @Filter là một annotation của thư viện spring-filter giúp xử lý các tham số
    // filter truyền vào từ client
    // Pageable là một interface của Spring Data giúp xử lý phân trang
    // Pageable sẽ chứa thông tin về trang hiện tại, số phần tử trên mỗi trang, sắp
    // xếp dữ liệu
    // Pageable sẽ được truyền vào hàm findAll của jobRepository để lấy dữ liệu
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
        if (job.getCompany() != null) {
            Optional<Company> companyOptional = this.companyService.findById(job.getCompany().getId());
            if (companyOptional.isPresent()) {
                job.setCompany(companyOptional.get());
            }
        }

        Job curentJob = jobRepository.save(job);
        // Khơi tạo ResCreateJobDTO để trả về dữ liệu cho client từ dữ liệu đã lưu
        // Tuy chỉnh lại dữ liệu trả về cho client
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
                        ? new ResCreateJobDTO.CompanyJob(
                                curentJob.getCompany()
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

    public ResUpdateJobDTO handleUpdateJob(Job job, Job jobInDB) {
        Optional<Job> jobExist = this.jobRepository.findById(job.getId());
        if (jobExist.isPresent()) {
            if (job.getSkills() != null) {
                List<Long> skills = job.getSkills()
                        .stream()
                        .map(action -> action.getId())
                        .collect(Collectors.toList());
                List<Skill> skillList = skillRepository.findByIdIn(skills);
                jobInDB.setSkills(skillList);

            }
            if (job.getCompany() != null) {
                Optional<Company> companyOptional = this.companyService.findById(job.getCompany().getId());
                if (companyOptional.isPresent()) {
                    jobInDB.setCompany(companyOptional.get());
                }
            }

            jobInDB.setName(job.getName());
            jobInDB.setLocation(job.getLocation());
            jobInDB.setSalary(job.getSalary());
            jobInDB.setQuantity(job.getQuantity());
            jobInDB.setLever(job.getLever());
            jobInDB.setStartDate(job.getStartDate());
            jobInDB.setEndDate(job.getEndDate());
            jobInDB.setActived(job.isActived());

            Job curentJob = jobRepository.save(jobInDB);
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
        // remove job from resume
        Optional<Job> job = this.jobRepository.findById(id);
        if (job.isPresent()) {
            Job currentJob = job.get();
            currentJob.getResumes().forEach(resume -> resume.setJob(null));
            this.jobRepository.deleteById(id);
        }
    }
}
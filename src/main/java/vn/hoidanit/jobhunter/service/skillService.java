package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.DTO.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.Skill;
import vn.hoidanit.jobhunter.repository.skillRepository;

@Service
public class skillService {
    private final skillRepository skillRepository;

    public skillService(skillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill handleCreateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public Skill handleUpdateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public void handleDeleteSkill(Long id) {
        Optional<Skill> skill = this.skillRepository.findById(id);
        Skill currentSkill = skill.get();
        // remove skill from job
        currentSkill.getJobs().forEach(job -> job.getSkills().remove(currentSkill));
        this.skillRepository.deleteById(id);
    }

    public Skill handleGetSkillById(Long id) {
        return this.skillRepository.findById(id).orElse(null);
    }
  
    public boolean isNameExist (String name) {
        return this.skillRepository.existsByName(name);

    }

    public ResultPaginationDTO handleGetAllSkill(Specification<Skill> spec, Pageable pageable) {
        Page<Skill> pageSkills = this.skillRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageSkills.getNumber() + 1);
        meta.setPageSize(pageSkills.getSize());

        meta.setPages(pageSkills.getTotalPages());
        meta.setTotal(pageSkills.getTotalElements());
        resultPaginationDTO.setMeta(meta);

        resultPaginationDTO.setResult(pageSkills.getContent());

        return resultPaginationDTO;
    }
}

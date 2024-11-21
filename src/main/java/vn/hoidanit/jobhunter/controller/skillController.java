package vn.hoidanit.jobhunter.controller;

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

import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Skill;
import vn.hoidanit.jobhunter.service.skillService;

@RestController
@RequestMapping("/api/v1")
public class skillController {
    private final skillService skillService;

    public skillController(skillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/skills")
    public ResponseEntity<ResultPaginationDTO> getAllSkills(
            @Filter Specification<Skill> spec,
            Pageable pageable) throws Exception {
        try {
            ResultPaginationDTO resultPaginationDTO = skillService.handleGetAllSkill(spec, pageable);
            return ResponseEntity.ok(resultPaginationDTO);
        } catch (Exception e) {
            throw new Exception("Internal server error");
        }
    }

    @PostMapping("/skills/create")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) throws Exception {
        if (skill.getName() != null && this.skillService.isNameExist(skill.getName())) {
            throw new Exception("Skill name already exists!!");
            
        }
        return ResponseEntity.ok(this.skillService.handleCreateSkill(skill));

    }

    @PutMapping("/skills")
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill)
    throws Exception {
        Skill currentSkill = this.skillService.handleGetSkillById(skill.getId());
        if (currentSkill == null) {
           throw new Exception("Skill not found!!");
        }
        return ResponseEntity.ok(this.skillService.handleUpdateSkill(skill));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable("id") Long id)
    throws Exception {
        Long currentSkill = this.skillService.handleGetSkillById(id).getId();
        if (currentSkill != null) {
            this.skillService.handleDeleteSkill(currentSkill);
            return ResponseEntity.status(HttpStatus.OK).body("Skill deleted!!");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found!!");

    
    }
}

package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.turkraft.springfilter.boot.Filter;

import jakarta.websocket.server.PathParam;
import vn.hoidanit.jobhunter.DTO.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.Skill;
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
            Pageable pageable) {
        try {
            ResultPaginationDTO resultPaginationDTO = skillService.handleGetAllSkill(spec, pageable);
            return ResponseEntity.ok(resultPaginationDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @PostMapping("/skills/create")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        if (skill.getName() != null && this.skillService.isNameExist(skill.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Skill name already exists");
        }
        return ResponseEntity.ok(this.skillService.handleCreateSkill(skill));

    }

    @PutMapping("/skills")
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill) {
        Skill currentSkill = this.skillService.handleGetSkillById(skill.getId());
        if (currentSkill == null) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found");
        }
        return ResponseEntity.ok(this.skillService.handleUpdateSkill(skill));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<String> deleteSkill( @PathParam("id") @RequestBody Long id) {
        Skill currentSkill = this.skillService.handleGetSkillById(id);
        if (currentSkill == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found");
        }
        this.skillService.handleDeleteSkill(id);
        return ResponseEntity.status(HttpStatus.OK).body("Skill deleted successfully!!");
    }
}

package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turkraft.springfilter.boot.Filter;

import jakarta.websocket.server.PathParam;
import vn.hoidanit.jobhunter.DTO.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.Skill;
import vn.hoidanit.jobhunter.service.skillService;

@Controller
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
        
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleGetAllSkill(spec, pageable));
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        if (skill.getName() != null && this.skillService.isNameExist(skill.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(this.skillService.handleCreateSkill(skill));

    }

    @PutMapping("/skills")
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill) {
        Skill currentSkill = this.skillService.handleGetSkillById(skill.getId());
        if (currentSkill == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(this.skillService.handleUpdateSkill(skill));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Skill> deleteSkill( @PathParam("id") @RequestBody Long id) {
        Skill currentSkill = this.skillService.handleGetSkillById(id);
        if (currentSkill == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.skillService.handleDeleteSkill(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

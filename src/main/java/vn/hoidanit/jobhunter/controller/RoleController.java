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

import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Role;
import vn.hoidanit.jobhunter.service.roleService;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final roleService roleService;

    public RoleController(roleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<ResultPaginationDTO> getRoles(
            @Filter Specification<Role> specification,
            Pageable pageable) {
        return ResponseEntity.ok(this.roleService.getRoles(specification, pageable));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.roleService.findById(id).orElse(null));
    }
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role)
            throws Exception {
        if (this.roleService.existsByName(role.getName())) {
            throw new Exception("Role name already exists");
        }
        if (this.roleService.existsByName(role.getName())) {
            throw new Exception("Role name " + role.getName() + " already exists");
        }
        return ResponseEntity.ok(this.roleService.createRole(role));
    }

    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole( @RequestBody Role role) throws Exception {
        Role roleExist = this.roleService.findById(role.getId()).orElse(null);
        if (roleExist == null) {
            throw new Exception("Role with " + role.getId() + " not found");
        }
        // if(roleExist.getName().equals(role.getName())) {
        // throw new Exception("Role name " + role.getName() + " already exists");
        // }
        return ResponseEntity.ok(this.roleService.updateRole(role));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long id) throws Exception {
        Optional<Role> roleExist = this.roleService.findById(id);
        if (!roleExist.isPresent()) {
            throw new Exception("Role with " + id + " not found");
        }
        this.roleService.deleteRole(id);
        return ResponseEntity.ok("Delete role " + id + " success");
    }

}

package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        Pageable pageable
    ) {
        return ResponseEntity.ok(this.roleService.getRoles(specification, pageable));
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role)
    throws Exception {
   if (this.roleService.existsByName(role.getName())) {
            throw new  Exception("Role name already exists");
        }
        return ResponseEntity.ok(this.roleService.createRole(role));
    }

    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) throws Exception {
        if(this.roleService.findById(role.getId()).isEmpty()) {
            throw new Exception("Role with " + role.getId() +" not found");
        }
        if(this.roleService.existsByName(role.getName())) {
            throw new Exception("Role name " + role.getName() + " already exists");
        }
        return ResponseEntity.ok(this.roleService.updateRole(role));
    }
    
}

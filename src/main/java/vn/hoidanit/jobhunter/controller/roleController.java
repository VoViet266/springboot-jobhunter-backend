package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.jobhunter.Entity.Role;
import vn.hoidanit.jobhunter.service.roleService;


@RestController
@RequestMapping("/api/v1")
public class roleController {

    private final roleService roleService;

    public roleController(roleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role/create")
    public Role createRole(@RequestBody Role role) {
        {
            this.roleService.hadleCreateRole(role);
            return role; 
        }
    }

    // @GetMapping("/role/{id}")
    // public ResponseEntity<?> getRole(@PathVariable Long id) {
    //     Optional<Role> role = this.roleService.hadleGetRole(id);
    //     if (role.isPresent()) {
    //         return ResponseEntity.ok(role.get());
    //     }
    //     else {
            
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
    //     }
    // }


}

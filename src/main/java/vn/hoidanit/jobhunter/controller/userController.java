package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.dto.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.dto.resultPaginationDTO;
import vn.hoidanit.jobhunter.entity.User;
import vn.hoidanit.jobhunter.service.userService;

@RestController
@RequestMapping("/api/v1/user")
public class userController {
    private final userService userService;
    private final PasswordEncoder passwordEncoder;

    public userController(userService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/email/{email}")
    public User getEmail(@PathVariable("email") String email) {
        User user = this.userService.handleGetUserByUsername(email);
        return user;
    }
    // @PostMapping("/user/create")
    // public ResponseEntity<?> createUser(@RequestBody Map<String, Object> payload)
    // {
    // String username = (String) payload.get("username");
    // String email = (String) payload.get("email");
    // String password = (String) payload.get("password");
    // Long roleId = payload.containsKey("role_id") ? ((Number)
    // payload.get("role_id")).longValue() : null;
    // Optional<User> existingUserOpt =
    // this.userService.handleGetUserByEmail(email);

    // if (existingUserOpt.isPresent()) {
    // return
    // ResponseEntity.status(HttpStatus.CONFLICT).body(existingUserOpt.get());
    // }
    // User user = new User();
    // if (roleId != null) {
    // Role role = this.roleService.findRoleById(roleId).orElse(null);
    // user.setRole(role);
    // }
    // user.setEmail(email);
    // user.setUsername(username);
    // user.setPassword(this.passwordEncoder.encode(password));

    // this.userService.handleCreateUser(user); // Lưu user
    // return ResponseEntity.status(HttpStatus.CREATED).body(user);
    // }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") Long id) {
        try {
            User user = this.userService.handleGetUserByID(id).get();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getusers")
    public ResponseEntity<resultPaginationDTO> getUser(
        @Filter Specification<User> spec,
        Pageable pageable) {
            try {
                resultPaginationDTO resultPaginationDTO = this.userService.handleAllGetUser(spec, pageable);
                return ResponseEntity.ok(resultPaginationDTO);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id, User user) {
        try {
            this.userService.handleDeleteUser(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
            Optional<User> existingUserOpt = this.userService.handleGetUserByID(id);
            if (!existingUserOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
             User User = this.userService.handleUpdateUser(id, user);
            return ResponseEntity
            .ok()
            .body(this.userService.convertToResUpdateUserDTO(User));
     
    }

    // // Tìm tất cả các User có vai trò với id_role tương ứng
    // @GetMapping("/role/{roleName}")
    // public ResponseEntity<List<User>> getUsersByRoleId(@PathVariable("roleName")
    // Long roleName) {
    // List<User> usersWithRole = this.userService.GetUserByRole(roleName);
    // return ResponseEntity.ok().body(usersWithRole);
    // }
}

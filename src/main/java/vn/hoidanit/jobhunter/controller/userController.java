package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.DTO.response.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.DTO.response.ResUserDTO;
import vn.hoidanit.jobhunter.DTO.response.resultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.User;
import vn.hoidanit.jobhunter.service.userService;

@RestController
@RequestMapping("/api/v1")
public class userController {
    private final userService userService;

    public userController(userService userService) {
        this.userService = userService;

    }
    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getUserByID(@PathVariable("id") Long id) {
        try {
            User user = this.userService.handleGetUserByID(id).get();
            return ResponseEntity.ok().body(this.userService.convertToResUserDTO(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<resultPaginationDTO> getUser(
        @Filter Specification<User> spec,
        Pageable pageable) {
                resultPaginationDTO resultPaginationDTO = this.userService.handleAllGetUser(spec, pageable);
                return ResponseEntity.ok(resultPaginationDTO);
           
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id, User user) {
        try {
            this.userService.handleDeleteUser(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/users/update/{id}")
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
}

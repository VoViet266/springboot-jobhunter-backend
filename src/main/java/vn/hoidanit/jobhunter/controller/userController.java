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
import org.springframework.web.server.ResponseStatusException;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.DTO.response.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.DTO.response.ResUserDTO;
import vn.hoidanit.jobhunter.DTO.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.User;
import vn.hoidanit.jobhunter.service.userService;
import vn.hoidanit.jobhunter.service.error.IdInValidException;

@RestController
@RequestMapping("/api/v1")
public class userController {
    private final userService userService;

    public userController(userService userService) {
        this.userService = userService;

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getUserByID(@PathVariable("id") Long id)
    throws IdInValidException {
            Optional<User> user = this.userService.handleGetUserByID(id);
            if (user.isEmpty()) {
                throw new IdInValidException("Id not found");
            }
            return ResponseEntity.ok().body(this.userService.convertToResUserDTO(user.get()));
       
    }

    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> getUser(
            @Filter Specification<User> spec,
            Pageable pageable) 
            {
            ResultPaginationDTO resultPaginationDTO = this.userService.handleAllGetUser(spec, pageable);
            return ResponseEntity.ok(resultPaginationDTO);
        
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id, User user) {
        if (!this.userService.handleGetUserByID(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id user not found");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users/update")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User userRed) {
        Optional<User> userExist = this.userService.handleGetUserByID(userRed.getId());
        if (userExist.isPresent()) {
            return ResponseEntity.ok()
                    .body(this.userService.convertToResUpdateUserDTO(this.userService.handleUpdateUser(userRed)));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not existing");
        }
    }
}

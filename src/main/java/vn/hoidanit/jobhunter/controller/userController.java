package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.dto.response.User.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.dto.response.User.ResUserDTO;
import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.User;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;
import vn.hoidanit.jobhunter.service.userService;

@RestController
@RequestMapping("/api/v1")
public class userController {
    private final userService userService;

    public userController(userService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getUserByID(@PathVariable("id") Long id)
            throws IdInvalidException {

        Optional<User> user = this.userService.handleGetUserByID(id);
        if (!user.isPresent()) {
            throw new IdInvalidException("Id invalid");
        }
        return ResponseEntity.ok().body(this.userService.convertToResUserDTO(user.get()));

    }

    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> getUser(
            @Filter Specification<User> spec,
            Pageable pageable) {
        ResultPaginationDTO resultPaginationDTO = this.userService.handleAllGetUser(spec, pageable);
        return ResponseEntity.ok(resultPaginationDTO);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id, User user)
            throws Exception {
        Optional<User> userExist = this.userService.handleGetUserByID(id);
        if (!userExist.isPresent()) {
            throw new Exception("User not existing");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok().body("Delete success");
    }

    @PutMapping("/users/update")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User userRed)
            throws Exception {
        Optional<User> userExist = this.userService.handleGetUserByID(userRed.getId());
        if (userExist.isPresent()) {
            return ResponseEntity.ok()
                    .body(this.userService.convertToResUpdateUserDTO(this.userService.handleUpdateUser(userRed)));
        } else {
            throw new Exception("User not existing");
        }
    }
}

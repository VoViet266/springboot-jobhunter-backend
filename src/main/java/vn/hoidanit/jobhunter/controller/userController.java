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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.dto.response.User.ResCreateUserDTO;
import vn.hoidanit.jobhunter.dto.response.User.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.dto.response.User.ResUserDTO;
import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.User;
import vn.hoidanit.jobhunter.service.userService;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class userController {
    private final userService userService;
    private final PasswordEncoder passwordEncoder;

    public userController(userService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getUserByID(@PathVariable("id") Long id)
            throws IdInvalidException {

        Optional<User> user = this.userService.handleGetUserByID(id);
        if (!user.isPresent()) {
            throw new IdInvalidException("User with id: " + id + " not found");
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
     @PostMapping("/register")
    public ResponseEntity<ResCreateUserDTO> createUser(@RequestBody User user)
            throws IdInvalidException {

        // Kiểm tra xem email đã tồn tại
        User existingUserOpt = this.userService.handleGetUserByEmail(user.getEmail());
        if (existingUserOpt != null) {
            throw new IdInvalidException("Email: " + user.getEmail() +" already exists");
        }
        // Tạo mới user và lưu vào database
        User User = new User();
        User.setEmail(user.getEmail());
        User.setUsername(user.getUsername());
        User.setAddress(user.getAddress());
        User.setAge(user.getAge());
        User.setGender(user.getGender());
        User.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User.setCompany(user.getCompany());
        this.userService.handleCreateUser(User);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(User));

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id, User user)
            throws IdInvalidException {
        Optional<User> userExist = this.userService.handleGetUserByID(id);
        if (!userExist.isPresent()) {
            throw new IdInvalidException("User with id: " + id + " not found");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok().body("Delete success");
    }

    @PutMapping("/users")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User userRed) throws IdInvalidException {
        Optional<User> userExist = this.userService.handleGetUserByID(userRed.getId());
        // if (userRed.getEmail() != null) {
        //     throw new Exception("CAN NOT UPDATE EMAIL");
        // }
        if (userExist.isPresent()) {
            return ResponseEntity.ok()
                    .body(this.userService.convertToResUpdateUserDTO(this.userService.handleUpdateUser(userRed)));
        } else {
            throw new IdInvalidException("User with id: " + userRed.getId() + " not found");
        }
    }
}

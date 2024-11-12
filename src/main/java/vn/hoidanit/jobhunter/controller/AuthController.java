package vn.hoidanit.jobhunter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import vn.hoidanit.jobhunter.DTO.request.ReqLoginDTO;
import vn.hoidanit.jobhunter.DTO.response.ResCreateUserDTO;
import vn.hoidanit.jobhunter.DTO.response.ResLoginDTO;
import vn.hoidanit.jobhunter.Entity.User;
import vn.hoidanit.jobhunter.service.authService;
import vn.hoidanit.jobhunter.service.userService;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;
import vn.hoidanit.jobhunter.util.SecurityUtil;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final userService userService;
    private final authService authService;

    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            authService authService, userService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@RequestBody ReqLoginDTO loginDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            ResLoginDTO restLoginDTO = new ResLoginDTO();
            User currentUser = this.userService.handleGetUserByEmail(loginDto.getUsername());
            if (currentUser != null) {
                ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                        currentUser.getId(),
                        currentUser.getUsername(),
                        currentUser.getEmail());
                restLoginDTO.setUser(userLogin);
            }
            String accesss_Token = this.securityUtil.createAccessToken(authentication, restLoginDTO.getUser());
            restLoginDTO.setAccessToken(accesss_Token);
            String refresh_token = this.securityUtil.createRefeshToken(loginDto.getUsername(), restLoginDTO);
            // update refresh token
            this.userService.updateUserToken(refresh_token, loginDto.getUsername());
            ResponseCookie springCookie = ResponseCookie.from("refresh_token", refresh_token)
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(refreshTokenExpiration)
                    .path("/")
                    .build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, springCookie.toString()).body(restLoginDTO);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResCreateUserDTO> register(@RequestBody User user)
            throws IdInvalidException {

        // Kiểm tra xem email đã tồn tại
        User existingUserOpt = this.userService.handleGetUserByEmail(user.getEmail());
        if (existingUserOpt != null) {
            throw new IdInvalidException("Email already exists");
        }
        User User = new User();
        User.setEmail(user.getEmail());
        User.setUsername(user.getUsername());
        User.setAddress(user.getAddress());
        User.setAge(user.getAge());
        User.setGender(user.getGender());
        User.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User.setCompany(user.getCompany()); 
        this.authService.handleCreateUser(User);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(User));

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is empty");
        }
        this.userService.updateUserToken("", email);
        ResponseCookie springCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .path("/")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, springCookie.toString()).body(null);
    }

    @GetMapping("/account")
    public ResponseEntity<ResLoginDTO.UserGetAccount> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        User userCurent = this.userService.handleGetUserByEmail(email);
        ResLoginDTO.UserGetAccount userGetAccount = new ResLoginDTO.UserGetAccount();
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        if (userCurent != null) {
            userLogin.setId(userCurent.getId());
            userLogin.setEmail(userCurent.getEmail());
            userLogin.setName(userCurent.getUsername());
            userGetAccount.setUser(userLogin);
        }
        return ResponseEntity.ok(userGetAccount);
    }

}

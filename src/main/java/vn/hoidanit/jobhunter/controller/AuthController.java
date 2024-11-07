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

import vn.hoidanit.jobhunter.dto.RegisterReponseDTO;
import vn.hoidanit.jobhunter.dto.RegisterRequestDTO;
import vn.hoidanit.jobhunter.dto.RestLoginDTO;
import vn.hoidanit.jobhunter.dto.loginDTO;
import vn.hoidanit.jobhunter.entity.User;
import vn.hoidanit.jobhunter.service.authService;
import vn.hoidanit.jobhunter.service.userService;
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
    public ResponseEntity<RestLoginDTO> login(@RequestBody loginDTO loginDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            RestLoginDTO restLoginDTO = new RestLoginDTO();
            User currentUser = this.userService.handleGetUserByEmail(loginDto.getUsername());
            if (currentUser != null) {
                RestLoginDTO.UserLogin userLogin = new RestLoginDTO.UserLogin(
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
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterReponseDTO> register(@RequestBody RegisterRequestDTO registerDTO) {
   
            // Kiểm tra xem email đã tồn tại
            User existingUserOpt = this.userService.handleGetUserByEmail(registerDTO.getEmail());
            if (existingUserOpt != null) {
                User user = new User();
                user.setEmail(registerDTO.getEmail());
                user.setUsername(registerDTO.getUsername());
                user.setAddress(registerDTO.getAddress());
                user.setAge(registerDTO.getAge());
                user.setGender(registerDTO.getGender());
                user.setPassword(this.passwordEncoder.encode(registerDTO.getPassword())); // Mã hóa mật khẩu trước khi  Lưu user
                this.authService.handleCreateUser(user);
                // Tạo DTO phản hồi
                RegisterReponseDTO responseDTO = new RegisterReponseDTO(
                        user.getUsername(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getAge(),
                        user.getGender(),
                        user.getCreatedAt()
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
            }

            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RegisterReponseDTO());

    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        if(email.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
    public ResponseEntity<RestLoginDTO.UserGetAccount> getAccount(){
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        User userCurent = this.userService.handleGetUserByEmail(email);
        RestLoginDTO.UserGetAccount userGetAccount = new RestLoginDTO.UserGetAccount();
        RestLoginDTO.UserLogin userLogin = new RestLoginDTO.UserLogin();
        if(userCurent != null){
            userLogin.setId(userCurent.getId());
            userLogin.setEmail(userCurent.getEmail());
            userLogin.setName(userCurent.getUsername());
            userGetAccount.setUser(userLogin);
        }
        return ResponseEntity.ok(userGetAccount);
    }
    
    

}

package vn.hoidanit.jobhunter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.dto.request.ReqLoginDTO;
import vn.hoidanit.jobhunter.dto.response.User.ResCreateUserDTO;
import vn.hoidanit.jobhunter.dto.response.User.ResLoginDTO;
import vn.hoidanit.jobhunter.entity.User;
import vn.hoidanit.jobhunter.service.authService;
import vn.hoidanit.jobhunter.service.error.AuthenticationException;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;
import vn.hoidanit.jobhunter.service.userService;
import vn.hoidanit.jobhunter.util.SecurityUtil;

@RequestMapping("/api/v1/auth")
@RestController
public class authController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final userService userService;
    private final authService authService;

    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenExpiration;

    public authController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            authService authService, userService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@RequestBody ReqLoginDTO loginDto) throws AuthenticationException {
        // Xác thực từ username và password của người dùng và trả về đối tượng
        // Authentication object nếu xác thực thành công
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);

        ResLoginDTO restLoginDTO = new ResLoginDTO();
        User currentUser = this.userService.handleGetUserByEmail(loginDto.getUsername());
        if (currentUser != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                    currentUser.getId(),
                    currentUser.getUsername(),
                    currentUser.getEmail(),
                    new ResLoginDTO.RoleUser(currentUser.getRole().getId(), currentUser.getRole().getName()));

            restLoginDTO.setUser(userLogin);
        }
        // Tạo access token và refresh token
        String accesss_Token = this.securityUtil.createAccessToken(authentication.getName(), restLoginDTO);
        // Set access token vào đối tượng ResLoginDTO để trả về cho người dùng
        restLoginDTO.setAccessToken(accesss_Token);
        String refresh_token = this.securityUtil.createRefreshToken(loginDto.getUsername(), restLoginDTO);
        // Cập nhật refresh token vào database
        this.userService.updateUserToken(refresh_token, loginDto.getUsername());
        ResponseCookie springCookie = ResponseCookie.from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .maxAge(refreshTokenExpiration)
                .path("/")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, springCookie.toString()).body(restLoginDTO);

    }

    @PostMapping("/register")
    public ResponseEntity<ResCreateUserDTO> register(@RequestBody User user)
            throws IdInvalidException, AuthenticationException {

        // Kiểm tra xem email đã tồn tại
        User existingUserOpt = this.userService.handleGetUserByEmail(user.getEmail());
        if (existingUserOpt != null) {
            throw new AuthenticationException("Email: " + user.getEmail() + " already exists");
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
        this.authService.handleCreateUser(User);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(User));

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() throws AuthenticationException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        if (email.isEmpty()) {
            throw new AuthenticationException("User not login");
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
            userLogin.setRole(new ResLoginDTO.RoleUser(userCurent.getRole().getId(), userCurent.getRole().getName()));
            userGetAccount.setUser(userLogin);
        }
        return ResponseEntity.ok(userGetAccount);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResLoginDTO> refreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "none") String refreshToken)
            throws AuthenticationException {
        Jwt decodeToken = this.securityUtil.checkValidRefreshToken(refreshToken);
        String email = decodeToken.getClaim("email");
        // SecurityUtil.getCurrentUserLogin().get() : "";

        if (refreshToken.equals("none")) {
            throw new AuthenticationException("User not login");
        }
        ResLoginDTO restLoginDTO = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByEmail(email);

        if (currentUserDB != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                    currentUserDB.getId(),
                    currentUserDB.getUsername(),
                    currentUserDB.getEmail(),
                    new ResLoginDTO.RoleUser(currentUserDB.getRole().getId(), currentUserDB.getRole().getName()));
            restLoginDTO.setUser(userLogin);
        }
        // Tạo access token và refresh token
        String accesss_Token = this.securityUtil.createAccessToken(email, restLoginDTO);
        // Set access token vào đối tượng ResLoginDTO để trả về cho người dùng
        restLoginDTO.setAccessToken(accesss_Token);
        String new_refresh_token = this.securityUtil.createRefreshToken(email, restLoginDTO);
        // Cập nhật refresh token vào database
        this.userService.updateUserToken(new_refresh_token, email);

        // Tạo cookie
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", new_refresh_token)
                .httpOnly(true)
                .secure(true)
                .maxAge(refreshTokenExpiration)
                .path("/")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(restLoginDTO);
    }
}

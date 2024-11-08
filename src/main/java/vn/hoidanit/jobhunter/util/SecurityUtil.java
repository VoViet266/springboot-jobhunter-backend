package vn.hoidanit.jobhunter.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.DTO.ResLoginDTO;

// import vn.hoidanit.jobhunter.util.SecretKeyGenerator;

@Service
public class SecurityUtil {

    private final JwtEncoder jwtEncoder;

    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;
    // @Value("${hoidanit.jwt.base64-secret}")
    // private String jwtKey;

    @Value("${hoidanit.jwt.access-token-validity-in-seconds}")
    private Long accessTokenExpiration;
    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenExpiration;

    public String createAccessToken(Authentication authentication, ResLoginDTO.UserLogin restLoginDTO) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(validity)
                .claim("User", restLoginDTO)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(
                JwtEncoderParameters.from(jwsHeader, claims))
                .getTokenValue();
    }
    public String createRefeshToken(String email, ResLoginDTO restLoginDTO) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(now)
                .expiresAt(validity)
                .claim("User", restLoginDTO.getUser())
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(
                JwtEncoderParameters.from(jwsHeader, claims))
                .getTokenValue();
    }
    // get current user login
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(Authentication::getName);

    }
    // get current user login
    // public static Optional<String> getCurrentUserJWT() {
    //     SecurityContext securityContext = SecurityContextHolder.getContext();
    //     return Optional.ofNullable(securityContext.getAuthentication())
    //             .map(Authentication::getCredentials)
    //             .map(Object::toString);
    // }
}

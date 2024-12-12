package vn.hoidanit.jobhunter.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.util.Base64;

import vn.hoidanit.jobhunter.dto.response.User.ResLoginDTO;
import vn.hoidanit.jobhunter.entity.Role;

@Service
public class SecurityUtil {

    private final JwtEncoder jwtEncoder;

    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;
    @Value("${hoidanit.jwt.base64-secret}")
    private static String jwtKey;

    @Value("${hoidanit.jwt.access-token-validity-in-seconds}")
    private Long accessTokenExpiration;
    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenExpiration;

    public String createAccessToken(String email, ResLoginDTO dto) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

        ResLoginDTO.UserInsideToken userToken = new ResLoginDTO.UserInsideToken();
        userToken.setId(dto.getUser().getId());
        userToken.setName(dto.getUser().getName());
        userToken.setEmail(dto.getUser().getEmail());

        String roleName = "ROLE_" + dto.getUser().getRole().getName().toUpperCase();
        List<String> roles = Collections.singletonList(roleName);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(now)
                .expiresAt(validity)
                .claim("User", userToken)
                .claim("roles", roles)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters
                .from(jwsHeader, claims))
                .getTokenValue();
    }

    public String createRefreshToken(String email, ResLoginDTO dto) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);
        ResLoginDTO.UserInsideToken userToken = new ResLoginDTO.UserInsideToken();
        userToken.setId(dto.getUser().getId());
        userToken.setName(dto.getUser().getName());
        userToken.setEmail(dto.getUser().getEmail());

        String roleName = "ROLE_" + dto.getUser().getRole().getName();
        List<String> roles = Collections.singletonList(roleName);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(now)
                .expiresAt(validity)
                .claim("User", userToken)
                .claim("roles", roles)
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

    // get secret key from jwtKey
    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtil.JWT_ALGORITHM.getName());
    }

    // kiểm tra token có hợp lệ không
    public Jwt checkValidRefreshToken(String token) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withSecretKey(getSecretKey())
                .macAlgorithm(SecurityUtil.JWT_ALGORITHM)
                .build();
        try {
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            System.out.println(">>> JWT Error: " + e.getMessage());
            throw e;
        }
    }
}

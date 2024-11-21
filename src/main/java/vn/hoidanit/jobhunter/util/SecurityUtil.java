package vn.hoidanit.jobhunter.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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

import vn.hoidanit.jobhunter.dto.response.User.ResLoginDTO;

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

    // public String createAccessToken(Authentication authentication, ResLoginDTO.UserLogin restLoginDTO) {
    //     // ResLoginDTO.UserInsideToken userToken = new ResLoginDTO.UserInsideToken(
    //     //     restLoginDTO.getUser().getId(),
    //     //     restLoginDTO.getUser().getName(),
    //     //     restLoginDTO.getUser().getEmail());
    //     Instant now = Instant.now();
    //     Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);
           
    //     JwtClaimsSet claims = JwtClaimsSet.builder()
    //             .subject(authentication.getName())
    //             .issuedAt(Instant.now())
    //             .expiresAt(validity)
    //             .claim("User", restLoginDTO)
    //             // .claim("authorities", listAuthorities)  
    //             .build();
    //     JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
    //     return this.jwtEncoder.encode(
    //             JwtEncoderParameters.from(jwsHeader, claims))
    //             .getTokenValue();
    public String createAccessToken(String email, ResLoginDTO dto) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);
        List<String> listAuthorities =  new ArrayList<>();
        listAuthorities.add("ROLE_USER_CREATE");
        listAuthorities.add("ROLE_USER_UPDATE");
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(now)
                .expiresAt(validity)
                .claim("User", dto.getUser())
                .claim("authorities", listAuthorities)
                .build();
        
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(
                JwtEncoderParameters.from(jwsHeader, claims))
                .getTokenValue();
    }

    public String createRefreshToken(String email, ResLoginDTO restLoginDTO) {
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
//   private SecretKey getSecretKey() {
//         byte[] keyBytes = Base64.from(jwtKey).decode();
//         return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtil.JWT_ALGORITHM.getName());
//     }
        // check xem token co hop le khong va giai ma token de lay thong tin user
    //    public Claims checkValidRefreshToken(String token) {
    //     return Jwts.parserBuilder()
    //             .setSigningKey(jwtKey)
    //             .build()
    //             .parseClaimsJws(token)
    //             .getBody();
    //    }
}


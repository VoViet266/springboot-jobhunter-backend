package vn.hoidanit.jobhunter.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;

import vn.hoidanit.jobhunter.util.SecurityUtil;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Value("${hoidanit.jwt.base64-secret}")
    private String jwtKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                // Cau hinh cors voi cau hinh mac dinh
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/auth/refresh").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/auth/account").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/logout").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/hello").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/v1/jobs").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/jobs/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/jobs").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/jobs").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/jobs").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/companies/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/companies").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/companies").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/companies/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/skills").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/skills/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/skills").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/skills").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/skills/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/files/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/files").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/permissions/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/permissions").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/permissions").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/permissions/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/resumes/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/resumes").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/resumes").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/resumes").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/resumes/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/roles/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/roles").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/roles/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/roles").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/roles/**").permitAll()

                                
                                .requestMatchers(HttpMethod.GET, "/storage/**").permitAll()

                ///// can phai dang nhap
                ).oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .formLogin(f -> f.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    // Tao secret key de ma hoa token
    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtil.JWT_ALGORITHM.getName());
    }

    // JwtEncoder de ma hoa token
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    // JwtDecoder de giai ma token
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withSecretKey(getSecretKey())
                .macAlgorithm(SecurityUtil.JWT_ALGORITHM)
                .build();
        return token -> {
            try {
                return jwtDecoder.decode(token);
            } catch (JwtException e) {
                System.out.println(">>> JWT Error: " + e.getMessage());
                throw e;
            }
        };
    }

}

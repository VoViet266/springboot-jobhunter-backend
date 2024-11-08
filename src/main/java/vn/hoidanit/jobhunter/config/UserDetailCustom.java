package vn.hoidanit.jobhunter.config;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import vn.hoidanit.jobhunter.service.userService;

@Component("userDetailCustom")
public class UserDetailCustom implements UserDetailsService {

private final userService userService;

public UserDetailCustom(vn.hoidanit.jobhunter.service.userService userService) {
    this.userService = userService;
}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        vn.hoidanit.jobhunter.Entity.User user = this.userService.handleGetUserByEmail(username);

        return new User(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(
                "ROLE_USER" // Phân quyền cho người dùng
            ))
        );
    }
    
}

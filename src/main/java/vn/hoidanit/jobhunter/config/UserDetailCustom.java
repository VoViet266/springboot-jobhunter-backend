package vn.hoidanit.jobhunter.config;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    String ROLE_PREFIX = "ROLE_";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        vn.hoidanit.jobhunter.entity.User user = this.userService.handleGetUserByEmail(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

}

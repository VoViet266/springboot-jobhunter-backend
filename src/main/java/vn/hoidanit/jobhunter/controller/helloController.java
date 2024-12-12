package vn.hoidanit.jobhunter.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class helloController {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}

package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class helloController {

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }
}

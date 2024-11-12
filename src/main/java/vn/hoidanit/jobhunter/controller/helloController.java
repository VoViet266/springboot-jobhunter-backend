package vn.hoidanit.jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class helloController {

    @GetMapping("/")
    public ResponseEntity<String> hello()  {

        return ResponseEntity.ok().body("hello");
    }
}

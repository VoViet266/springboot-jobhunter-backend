package vn.hoidanit.jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.dto.UserDTO;
import vn.hoidanit.jobhunter.entity.Course;
import vn.hoidanit.jobhunter.entity.User;
import vn.hoidanit.jobhunter.service.courseService;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

    public final courseService courseService;

    public CourseController(courseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/course/create")
    public ResponseEntity<Course> createCourse(@RequestBody Course course, User user) {
        {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            this.courseService.handleCreateCourse(course);
            
       

            return ResponseEntity.ok(course); 
        }
    }



}

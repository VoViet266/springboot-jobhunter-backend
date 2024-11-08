package vn.hoidanit.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.Entity.Course;
import vn.hoidanit.jobhunter.repository.courseRepository;

@Service
public class courseService {
    private final courseRepository courseRepository;
    public courseService(courseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course handleCreateCourse(Course course){
       return  courseRepository.save(course);
    }
    

}

package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.Entity.Course;


@Repository
public interface courseRepository extends JpaRepository<Course, Long> {
    
}

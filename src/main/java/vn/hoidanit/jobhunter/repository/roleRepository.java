package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.entity.Role;

@Repository
public interface roleRepository extends JpaRepository<Role, Long> {
    
    
}


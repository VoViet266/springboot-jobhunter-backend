package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.hoidanit.jobhunter.entity.Role;

public interface roleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    
        boolean existsByName(String name);

}

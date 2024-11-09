package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.hoidanit.jobhunter.Entity.Skill;

public interface skillRepository extends JpaRepository<Skill, Long> , JpaSpecificationExecutor<Skill> {
    Boolean existsByName(String name);

}

package vn.hoidanit.jobhunter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.hoidanit.jobhunter.entity.Skill;

public interface skillRepository extends JpaRepository<Skill, Long> , JpaSpecificationExecutor<Skill> {
    Boolean existsByName(String name);
    List<Skill> findByIdIn(List<Long> ids);

}

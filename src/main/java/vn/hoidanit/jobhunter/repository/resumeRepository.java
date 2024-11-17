package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.hoidanit.jobhunter.entity.Resume;

public interface resumeRepository extends JpaRepository<Resume, Long> , JpaSpecificationExecutor<Resume> { 

}

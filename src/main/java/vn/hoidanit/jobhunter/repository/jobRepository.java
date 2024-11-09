package vn.hoidanit.jobhunter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.hoidanit.jobhunter.Entity.Job;
public interface jobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

}

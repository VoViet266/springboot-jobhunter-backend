package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.jobhunter.Entity.User;

public interface authRepository extends JpaRepository<User, Long>  {

}

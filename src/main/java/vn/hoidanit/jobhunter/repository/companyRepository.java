package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.hoidanit.jobhunter.entity.Company;

public interface companyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
 

}

package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.dto.resultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Company;
import vn.hoidanit.jobhunter.service.companyService;

@RestController
@RequestMapping("/api/v1/")
public class companyController {
    private final companyService companyService;

    public companyController(companyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") Long id) {
        try {
            Optional<Company> company = companyService.handleGetCompanyById(id);
            return ResponseEntity
                    .ok()
                    .body(company.get());
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .build();
        }
    }

    @GetMapping("/companies")
    public ResponseEntity<resultPaginationDTO> getCompany(
            @Filter Specification<Company> specification,
            Pageable pageable) {
        try {
            resultPaginationDTO resultPaginationDTO = companyService.handleGetAllCompany(
                    specification,
                    pageable);
            return ResponseEntity.ok(resultPaginationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .build();
        }

    }

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        try {
            Company newCompany = companyService.handleCreateCompany(company);
            return ResponseEntity.ok(newCompany);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Company> updateCompany(
        @PathVariable("id") 
        Long id, 
        @RequestBody Company company) {
        try {
            Optional<Company> exist = companyService.handleGetCompanyById(id);
            if (exist.isPresent()) {
                Company updatedCompany = companyService.handleUpdateCompany(company, id);
                return ResponseEntity.ok(updatedCompany);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Company> deleteCompany(@PathVariable("id") Long id) {
        try {
            this.companyService.handleDeleteCompany(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}

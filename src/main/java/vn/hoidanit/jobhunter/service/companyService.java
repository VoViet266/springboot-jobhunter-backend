package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.DTO.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.Company;
import vn.hoidanit.jobhunter.repository.companyRepository;

@Service
public class companyService {
    private final companyRepository companyRepository;

    public companyService(companyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return companyRepository.save(company); // Lưu người dùng vào cơ sở dữ liệu
    }

    public ResultPaginationDTO handleGetAllCompany(
        Specification<Company> specification, Pageable pageable) {
        Page<Company> pageCompany = companyRepository.findAll(specification, pageable);// sử dụng Pageable ở đây
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        // Tạo ra một đối tượng Meta để chứa thông tin phân trang
        // Pageable sẽ trả về một Page, chứa thông tin phân trang và content
        meta.setPage(pageCompany.getNumber() + 1);
        meta.setPageSize(pageCompany.getSize());

        meta.setPages(pageCompany.getTotalPages());
        meta.setTotal(pageCompany.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(pageCompany.getContent()); // Chỉ lấy content trong page
        return resultPaginationDTO;
    }
    public Optional<Company> handleGetCompanyById(Long id) {
        return companyRepository.findById(id); // Lấy thông tin công ty theo id
    }

    public Company handleUpdateCompany(Company company, Long id) {
        Optional<Company> Company = companyRepository.findById(id);
        if (Company.isPresent()) {
            Company existingCompany = Company.get();
            existingCompany.setName(company.getName());
            existingCompany.setAddress(company.getAddress());
            existingCompany.setLogo(company.getLogo());
            existingCompany.setDescription(company.getDescription());
            return companyRepository.save(existingCompany);
        } else {
            throw new RuntimeException("Company not found");
        }
    }

    public Optional<Company> findById(Long id) {
       return this.companyRepository.findById(id);
    }

    public void handleDeleteCompany(Long id) {
        companyRepository.deleteById(id); // Xóa người dùng
    }

}

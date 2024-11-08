package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.Entity.Company;
import vn.hoidanit.jobhunter.Entity.User;
import vn.hoidanit.jobhunter.repository.userRepository;

@Service
public class authService {
    private final companyService companyService;
    private final userRepository userRepository;

    public authService(userRepository userRepository, companyService companyService) {
        this.userRepository = userRepository;
        this.companyService = companyService;
    }

    public User handleCreateUser(User user) {
        if (user.getCompany() != null) {
            Optional<Company> companyOptional = this.companyService.findById(user.getCompany().getId());
            if (companyOptional.isPresent()) {
                user.setCompany(companyOptional.get());
            } else {
                user.setCompany(null);
            }

            // Lưu người dùng vào cơ sở dữ liệu
        }
        return this.userRepository.save(user);
    }

}

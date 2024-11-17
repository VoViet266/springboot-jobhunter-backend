package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.entity.Company;
import vn.hoidanit.jobhunter.entity.Role;
import vn.hoidanit.jobhunter.entity.User;
import vn.hoidanit.jobhunter.repository.userRepository;

@Service
public class authService {
    private final companyService companyService;
    private final userRepository userRepository;
    private final roleService roleService;

    public authService(userRepository userRepository, companyService companyService, roleService roleService) {
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.roleService = roleService;
    }

    public User handleCreateUser(User user) {
        if (user.getCompany() != null) {
            Optional<Company> companyOptional = this.companyService.findById(user.getCompany().getId());
            user.setCompany(companyOptional != null ? companyOptional.get() : null);
        }
        if (user.getRole() != null) {
            Optional<Role> roleOptional = this.roleService.findById(user.getRole().getId());
          user.setRole(roleOptional != null ? roleOptional.get() : null);
        }
        return this.userRepository.save(user);
    }

    

}

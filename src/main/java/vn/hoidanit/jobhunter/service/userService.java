
package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.dto.response.User.ResCreateUserDTO;
import vn.hoidanit.jobhunter.dto.response.User.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.dto.response.User.ResUserDTO;
import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Company;
import vn.hoidanit.jobhunter.entity.Role;
import vn.hoidanit.jobhunter.entity.User;
import vn.hoidanit.jobhunter.repository.userRepository;

@Service
public class userService {
    private final userRepository userRepository;
    private final companyService companyService;
    private final roleService roleService;

    public userService(userRepository userRepository, companyService companyService, roleService roleService) {
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.roleService = roleService;

    }

    public Optional<User> handleGetUserByID(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return Optional.ofNullable(user.orElse(null));
    }

    public ResultPaginationDTO handleAllGetUser(Specification<User> specification, Pageable pageable) {
        Page<User> pageUsers = userRepository.findAll(specification, pageable); // sử dụng Pageable ở đây
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageUsers.getNumber() + 1);
        meta.setPageSize(pageUsers.getSize());

        meta.setPages(pageUsers.getTotalPages());
        meta.setTotal(pageUsers.getTotalElements());
        resultPaginationDTO.setMeta(meta);

        List<ResUserDTO> listUser = pageUsers.stream()
                .map(action -> new ResUserDTO(
                        action.getId(),
                        action.getUsername(),
                        action.getEmail(),
                        action.getGender(),
                        action.getAddress(),
                        action.getAge(),
                        action.getCreatedAt(),
                        action.getUpdatedAt(),
                        action.getCompany() != null
                                ? new ResUserDTO.CompanyUser(
                                        action.getCompany().getId() != null ? action.getCompany().getId() : null,
                                        action.getCompany().getName() != null ? action.getCompany().getName() : null)
                                : null,
                        action.getRole() != null
                                ? new ResUserDTO.RoleUser(
                                        action.getRole().getId() != null ? action.getRole().getId() : null,
                                        action.getRole().getName() != null ? action.getRole().getName() : null)
                                : null))
                .collect(Collectors.toList());
        resultPaginationDTO.setResult(listUser);

        return resultPaginationDTO;
    }

    public void handleDeleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            this.userRepository.delete(user.get());
        }
    }

    public User handleGetUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
    public User getUserByRefeshTokenAndEmail(String email, String refreshToken) {
        return this.userRepository.findByRefreshTokenAndEmail(refreshToken, email);
    }

    public User handleUpdateUser(User updatedUser) {
        User currentUser = this.handleGetUserByID(updatedUser.getId()).get();

        if (currentUser != null) {
            currentUser.setUsername(updatedUser.getUsername());
            currentUser.setGender(updatedUser.getGender());
            currentUser.setAge(updatedUser.getAge());
            currentUser.setAddress(updatedUser.getAddress());

            if (updatedUser.getCompany() != null) {
                Optional<Company> companyOptional = this.companyService.findById(updatedUser.getCompany().getId());
                currentUser.setCompany(companyOptional.isPresent() ? companyOptional.get() : null);
            }
            if (updatedUser.getRole() != null) {
                Optional<Role> roleOptional = this.roleService.findById(updatedUser.getRole().getId());
                currentUser.setRole(roleOptional != null ? roleOptional.get() : null);
            }
            if(updatedUser.getResumes() != null) {
                currentUser.setResumes(updatedUser.getResumes());
            }
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO resCreateUserDTO = new ResCreateUserDTO();
        ResCreateUserDTO.CompanyUser companyUser = new ResCreateUserDTO.CompanyUser();

        resCreateUserDTO.setId(user.getId());
        resCreateUserDTO.setEmail(user.getEmail());
        resCreateUserDTO.setName(user.getUsername());
        resCreateUserDTO.setAge(user.getAge());
        resCreateUserDTO.setGender(user.getGender());
        resCreateUserDTO.setAddress(user.getAddress());

        if (user.getCompany() != null) {
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            resCreateUserDTO.setCompany(companyUser);
        }
        return resCreateUserDTO;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO resUpdateUserDTO = new ResUpdateUserDTO();
        ResUpdateUserDTO.CompanyUser companyUser = new ResUpdateUserDTO.CompanyUser();

        if (user.getCompany() != null) {
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            resUpdateUserDTO.setCompany(companyUser);
        }

        resUpdateUserDTO.setId(user.getId());
        resUpdateUserDTO.setName(user.getUsername());
        resUpdateUserDTO.setAddress(user.getAddress());
        resUpdateUserDTO.setAge(user.getAge());
        resUpdateUserDTO.setGender(user.getGender());
        resUpdateUserDTO.setUpdateAt(user.getUpdatedAt());
        resUpdateUserDTO.setUpdatedBy(user.getUpdatedBy());
        return resUpdateUserDTO;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO resUserDTO = new ResUserDTO();
        ResUserDTO.CompanyUser companyUser = new ResUserDTO.CompanyUser();
        ResUserDTO.RoleUser roleUser = new ResUserDTO.RoleUser();

        resUserDTO.setId(user.getId());
        resUserDTO.setEmail(user.getEmail());
        resUserDTO.setName(user.getUsername());
        resUserDTO.setAge(user.getAge());
        resUserDTO.setGender(user.getGender());
        resUserDTO.setAddress(user.getAddress());
        resUserDTO.setCreatedAt(user.getCreatedAt());
        resUserDTO.setUpdatedAt(user.getUpdatedAt());

        if (user.getCompany() != null) {
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            resUserDTO.setCompany(companyUser);
        }
        if (user.getRole() != null) {
            roleUser.setId(user.getRole().getId());
            roleUser.setName(user.getRole().getName());
            resUserDTO.setRole(roleUser);
        }
        return resUserDTO;
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

    public void updateUserToken(String token, String email) {
        User user = this.userRepository.findByEmail(email);
        if (user != null) {
            user.setRefreshToken(token);
            this.userRepository.save(user);
        }
    }
}

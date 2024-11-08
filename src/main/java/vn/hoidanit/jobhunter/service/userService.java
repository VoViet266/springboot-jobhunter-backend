
package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.DTO.response.ResCreateUserDTO;
import vn.hoidanit.jobhunter.DTO.response.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.DTO.response.ResUserDTO;
import vn.hoidanit.jobhunter.DTO.response.resultPaginationDTO;
import vn.hoidanit.jobhunter.Entity.User;
import vn.hoidanit.jobhunter.repository.userRepository;

@Service
public class userService {
    private final userRepository userRepository;

    public userService(userRepository userRepository) {
        this.userRepository = userRepository;

    }

    public Optional<User> handleGetUserByID(Long id) {
        return this.userRepository.findById(id);
    }

    public resultPaginationDTO handleAllGetUser(Specification<User> specification, Pageable pageable) {
        Page<User> pageUsers = userRepository.findAll(specification, pageable); // sử dụng Pageable ở đây
        resultPaginationDTO resultPaginationDTO = new resultPaginationDTO();
        resultPaginationDTO.Meta meta = new resultPaginationDTO.Meta();

        meta.setPage(pageUsers.getNumber() + 1);
        meta.setPageSize(pageUsers.getSize());

        meta.setPages(pageUsers.getTotalPages());
        meta.setTotal(pageUsers.getTotalElements());

        resultPaginationDTO.setMeta(meta);
       
        List<ResUserDTO> listUser = pageUsers.stream()
                .map(action -> new ResUserDTO(
                    action.getAddress(),
                    action.getAge(),
                    action.getCreatedAt(),
                    action.getEmail(),
                    action.getGender(),
                    action.getId(),
                    action.getUsername(),
                    action.getUpdatedAt()))
                .collect(Collectors.toList());
                resultPaginationDTO.setResult(listUser);

        return resultPaginationDTO;
    }

    public Boolean handleDeleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            this.userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    public User handleGetUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }


    public User handleUpdateUser(Long id, User updatedUser) {
        User user = this.userRepository.findById(id).get();
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            user.setGender(updatedUser.getGender());
            user.setAge(updatedUser.getAge());
            user.setAddress(updatedUser.getAddress());
            // Cập nhật các thuộc tính khác nếu cần
            return this.userRepository.save(user);
        }
        return user;

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

        if(user.getCompany() != null){
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            resCreateUserDTO.setCompany(companyUser);
        }
        return resCreateUserDTO;
    }
    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO resUpdateUserDTO = new ResUpdateUserDTO();
        ResUserDTO.CompanyUser companyUser = new ResUserDTO.CompanyUser();
        

        resUpdateUserDTO.setId(user.getId());
        resUpdateUserDTO.setName(user.getUsername());
        resUpdateUserDTO.setAddress(user.getAddress());
        resUpdateUserDTO.setAge(user.getAge());
        resUpdateUserDTO.setGender(user.getGender());
        resUpdateUserDTO.setUpdateAt(user.getUpdatedAt());
        resUpdateUserDTO.setUpdatedBy(user.getUpdatedBy());
        return resUpdateUserDTO;
    }
    public void updateUserToken(String token, String email){
        User user = this.userRepository.findByEmail(email);
       if(user != null){
            user.setRefreshToken(token);
            this.userRepository.save(user);
       }
    }

    public Optional<User> GetUserByRole(Long roleId) {
        return this.userRepository.findById(roleId);
    }
}

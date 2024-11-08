package vn.hoidanit.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.Entity.User;
import vn.hoidanit.jobhunter.repository.userRepository;


@Service
public class authService {
    private final userRepository userRepository;
    public authService(userRepository userRepository) {
        this.userRepository = userRepository;
    }
   
     public User handleCreateUser(User user ) {
        Boolean isEmailExist = this.userRepository.existsByEmail(user.getEmail());
        if (isEmailExist) {
            throw new RuntimeException("Email đã tồn tại");
        }
        return this.userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
    }

}

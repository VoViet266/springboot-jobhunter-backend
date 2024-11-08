package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.Entity.Role;
import vn.hoidanit.jobhunter.repository.roleRepository;
@Service
public class roleService {
    private final roleRepository roleRepository;

    public roleService(roleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public void hadleCreateRole(Role role){
        roleRepository.save(role);
    }
    public Optional<Role> findRoleById(Long id){
        return roleRepository.findById(id);
    }
}

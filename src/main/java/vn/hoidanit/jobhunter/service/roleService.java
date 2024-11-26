package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Permission;
import vn.hoidanit.jobhunter.entity.Role;
import vn.hoidanit.jobhunter.repository.permissionRepository;
import vn.hoidanit.jobhunter.repository.roleRepository;

@Service
public class roleService {

    private final roleRepository roleRepository;
    private final permissionRepository permissionRepository;

    public roleService(roleRepository roleRepository, permissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role createRole(Role role) {
        if (role.getPermissions() != null) {
            List<Long> reqPermissions = role.getPermissions()
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
            role.setPermissions(dbPermissions);
        }
        return this.roleRepository.save(role);
    }

    public Role updateRole(Role role) {
        Role dbRole = this.findById(role.getId()).get();  
        if (role.getPermissions() != null) {
            List<Long> reqPermissions = role.getPermissions()
                    .stream()
                    .map(Permission::getId)
                    .collect(Collectors.toList());
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
            role.setPermissions(dbPermissions);
        }
            dbRole.setName(role.getName());
            dbRole.setDescription(role.getDescription());
            dbRole.setActive(role.isActive());
            dbRole.setPermissions(role.getPermissions());

        return this.roleRepository.save(dbRole);
    }

    public Optional<Role> findById(Long id) {
        return this.roleRepository.findById(id);
    }

    public boolean existsByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public void deleteRole(Long id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginationDTO getRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> rolesPage = this.roleRepository.findAll(spec, pageable);
            ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(rolesPage.getNumber() + 1);
        meta.setPageSize(rolesPage.getSize());

        meta.setPages(rolesPage.getTotalPages());
        meta.setTotal(rolesPage.getTotalElements());

        resultPaginationDTO.setResult(rolesPage.getContent());
        resultPaginationDTO.setMeta(meta);
        return resultPaginationDTO;
    }

}

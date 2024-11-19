package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.DTO.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Permission;
import vn.hoidanit.jobhunter.repository.permissionRepository;

@Service
public class permissionService {

    private final permissionRepository permissionRepository;

    public permissionService(permissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public boolean isPermissionExist(Permission permission) {
        return this.permissionRepository.existsByModuleAndApiPathAndMethod(
                permission.getModule(),
                permission.getApiPath(),
                permission.getMethod());
    }

    public Permission getPermissionById(Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
      if(permission.isPresent()) {
          return permission.get();
      }
      return null;
    }

    public Permission updatePermission(Permission permission) {

        Permission permissionById = this.getPermissionById(permission.getId());
        if(permissionById != null) {
            permissionById.setName(permission.getName());
            permissionById.setMethod(permission.getMethod());
            permissionById.setApiPath(permission.getApiPath());
            permissionById.setModule(permission.getModule());
            permissionById = permissionRepository.save(permissionById);
            return permissionById;
        }
        return null;
    }


    public void deletePermission(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        Permission curremtPermission =  permissionOptional.get();

       curremtPermission.getRoles().forEach(role -> {
           role.getPermissions().remove(curremtPermission);
       });
        permissionRepository.deleteById(id);
    }

    public ResultPaginationDTO getAllPermission(Specification<Permission> spec, Pageable pageable) {

          Page<Permission> permissionPage = this.permissionRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(permissionPage.getNumber() + 1);
        meta.setPageSize(permissionPage.getSize());

        meta.setPages(permissionPage.getTotalPages());
        meta.setTotal(permissionPage.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(permissionPage.getContent());

        return resultPaginationDTO;
    }

}

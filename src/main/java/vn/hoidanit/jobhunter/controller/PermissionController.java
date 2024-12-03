package vn.hoidanit.jobhunter.controller;

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

import vn.hoidanit.jobhunter.dto.response.page.ResultPaginationDTO;
import vn.hoidanit.jobhunter.entity.Permission;
import vn.hoidanit.jobhunter.service.permissionService;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class permissionController {

    private final permissionService permissionService;

    public permissionController(permissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) throws Exception {

        boolean isPermissionExist = permissionService.isPermissionExist(permission);
        if (isPermissionExist) {
            throw new Exception("Permission is already exist");
        }
        return ResponseEntity.ok(permissionService.createPermission(permission));
    }

    @PutMapping("/permissions")
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission) throws IdInvalidException, Exception {
        boolean isPermissionExist = permissionService.isPermissionExist(permission);

        if (this.permissionService.getPermissionById(permission.getId()) == null) {
            throw new IdInvalidException("Permission" + permission.getId() + " is not exist");
        }
        if (isPermissionExist) {
            throw new Exception("Permission is already exist");
        }
        return ResponseEntity.ok(permissionService.updatePermission(permission));
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.permissionService.getPermissionById(id) == null) {
            throw new IdInvalidException("Permission" + id + " is not exist");
        }
        permissionService.deletePermission(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/permissions")
    public ResponseEntity<ResultPaginationDTO> getAllPermission(
            @Filter Specification<Permission> spec,
            Pageable pageable) {
        ResultPaginationDTO resultPaginationDTO = permissionService.getAllPermission(spec, pageable);
        return ResponseEntity.ok(resultPaginationDTO);
    }

}

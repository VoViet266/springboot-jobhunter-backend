package vn.hoidanit.jobhunter.entity;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.SecurityUtil;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Company name khong duoc de trong")
    private String name;
    private String address;
    private String logo;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @OneToMany(mappedBy = "company", fetch=FetchType.LAZY)
    @JsonIgnore
    List<User> users;

    @OneToMany(mappedBy = "company", fetch=FetchType.LAZY)
    @JsonIgnore
    List<Job> jobs;

      @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = SecurityUtil
        .getCurrentUserLogin()
        .isPresent() 
        ? SecurityUtil.getCurrentUserLogin().get() 
        : "system";

        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedBy = SecurityUtil
        .getCurrentUserLogin()
        .isPresent() 
        ? SecurityUtil.getCurrentUserLogin().get() 
        : "system";
    }
}

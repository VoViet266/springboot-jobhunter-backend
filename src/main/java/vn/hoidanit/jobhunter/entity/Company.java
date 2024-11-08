package vn.hoidanit.jobhunter.Entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String createBy;
    private String updateBy;
    @OneToMany(mappedBy = "company", fetch=FetchType.LAZY)
    private List<User> users;

    @PrePersist
    public void handleBeforeCreate() {
        this.createBy = "admin";
        this.createdAt = Instant.now();
        this.updateBy = "admin";
        this.updatedAt = Instant.now();

    }
    
}

package vn.hoidanit.jobhunter.DTO;

import java.time.Instant;

import vn.hoidanit.jobhunter.util.constant.GenderEnum;

public class ResUserDTO {
    private Long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant createdAt;
    private Instant updatedAt;

    public ResUserDTO(String address, int age, Instant createdAt, String email, GenderEnum gender, Long id, String name, Instant updatedAt) {
        this.address = address;
        this.age = age;
        this.createdAt = createdAt;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
    }

    public ResUserDTO() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public GenderEnum getGender() {
        return gender;
    }
    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    
    
}

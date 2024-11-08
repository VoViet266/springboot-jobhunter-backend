package vn.hoidanit.jobhunter.DTO;

import vn.hoidanit.jobhunter.util.constant.GenderEnum;
import java.time.Instant;

public class RegisterReponseDTO {

    private String username;
    private String email;
    private String address;
    private int age;
    private GenderEnum gender;
    private Instant createdAt;
  
    public RegisterReponseDTO(String username, String email, String address, int age, GenderEnum gender, Instant createdAt) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.createdAt = createdAt;
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
    public GenderEnum getGender() {
        return gender;
    }
    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }
    public RegisterReponseDTO() {
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
  
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

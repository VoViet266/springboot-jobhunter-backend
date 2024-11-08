package vn.hoidanit.jobhunter.DTO.request;

import vn.hoidanit.jobhunter.util.constant.GenderEnum;

public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private String address;
    private int age;
    private GenderEnum gender;


    // Constructor mặc định (bắt buộc khi sử dụng @RequestBody)
    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String address, int age, String email, GenderEnum gender, String password, String username) {
        this.address = address;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.username = username;
    }


    // Getters và Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    

}

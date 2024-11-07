package vn.hoidanit.jobhunter.dto;

public class RestLoginDTO {
    private String accessToken;
    private UserLogin user;
    public RestLoginDTO(String accessToken, UserLogin user) {
        this.accessToken = accessToken;
        this.user = user;
    }
   
    public RestLoginDTO() {
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public UserLogin getUser() {
        return user;
    }
    public void setUser(UserLogin user) {
        this.user = user;
    }
   public static class UserLogin{
    private Long id;
    private String name;
    private String email;

    public UserLogin(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public UserLogin() {
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
    
   }

}


package vn.hoidanit.jobhunter.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 

    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "image", nullable = true)
    private String image;
    @Column(name = "price", nullable = true)
    private String price;
    @Column(name = "discount", nullable = true)
    private String discount;
    @Column(name = "status", nullable = true)
    private String status;
    @ManyToOne
    @JoinColumn(name = "id_creator", referencedColumnName = "id", nullable = true)
    private User id_creator;
    @Column(name = "created_at")
    private String created_at;
    @Column(name = "updated_at")
    private String updated_at;
    
    public Course(Long id, String name, String description, String image, String price, String discount, String status,
            String created_at, String updated_at,User id_creator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id_creator = id_creator;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public User getId_creator() {
        return id_creator;
    }
    public void setId_creator(User id_creator) {
        this.id_creator = id_creator;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

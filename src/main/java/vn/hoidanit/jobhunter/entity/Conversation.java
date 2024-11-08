package vn.hoidanit.jobhunter.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "id_creator" , referencedColumnName = "id")
    private User id_Creator;
    public Conversation(Long id, String content, User id_Creator) {
        this.id = id;
        this.content = content;
        this.id_Creator = id_Creator;
    }
    public Conversation() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public User getId_Creator(){
        return id_Creator;
    }
    public void setId_Creator(User id_Creator){
        this.id_Creator = id_Creator;
    }

}

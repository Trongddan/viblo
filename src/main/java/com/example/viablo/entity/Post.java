package com.example.viablo.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity

@Table(name = "post")
public class Post  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String content;
    private int numOfLike;
    private int numOfCmt;
    private String date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_Id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinTable(name = "tag_post",joinColumns =@JoinColumn(name="post_id"),inverseJoinColumns = @JoinColumn(name = "tag_id"))

    private List<Tag> tag = new ArrayList<>();
    public void addNewTag(Tag tagg){
        this.tag.add(tagg);
    }

}

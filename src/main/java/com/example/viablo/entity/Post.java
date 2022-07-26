package com.example.viablo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String content;
    private int numOfLike;
    private int numOfCmt;
    private String date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tag_post",joinColumns =@JoinColumn(name="post_id"),inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tag;

}

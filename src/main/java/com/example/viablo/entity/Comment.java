package com.example.viablo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 1000)
    private String content;
    @ManyToOne()
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

}

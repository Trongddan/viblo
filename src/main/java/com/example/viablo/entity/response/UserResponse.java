package com.example.viablo.entity.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String email;
    private String username;
    private String token;
    private Collection roles;
    private String avatar;

    public UserResponse(int id,String username,String avatar,String email, String token, Collection roles) {
        this.id=id;
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.token = token;
        this.roles = roles;
    }
}

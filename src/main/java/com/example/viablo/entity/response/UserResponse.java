package com.example.viablo.entity.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@NoArgsConstructor
public class UserResponse {
    private String email;
    private String username;
    private String token;
    private Collection roles;
    private String avatar;

    public UserResponse(String username,String avatar,String email, String token, Collection roles) {
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.token = token;
        this.roles = roles;
    }
}

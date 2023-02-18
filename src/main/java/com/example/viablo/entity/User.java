package com.example.viablo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String name;
    @Column(nullable = false,length = 50,unique = true)
    private String username;
    @Column(nullable = false,length = 300)
    private String password;
    private String avatar;
    @Column(nullable = false,length = 100,unique = true)
    private String email;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    @JoinTable(
            name = "user_follow", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="follow_id")
    )
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User( String username, String avatar,String password, String email, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.avatar=avatar;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for(Role role : roles){
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public void addRole(Role role){
        roles.add(role);
    }
    public void addFollow(User user){
        this.users.add(user);
    }
}

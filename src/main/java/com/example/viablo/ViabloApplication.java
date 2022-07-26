package com.example.viablo;

import com.example.viablo.entity.Role;
import com.example.viablo.entity.User;
import com.example.viablo.respository.RoleRespository;
import com.example.viablo.respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ViabloApplication {
    @Autowired private UserRespository userRespository;
    @Autowired private RoleRespository roleRespository;
//    @PostConstruct
//    public  void createUser(){
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String password ="12345";
//        String email="dan301@gmail.com";
//        String endCoder = passwordEncoder.encode(password);
//        User user = new User("doandan133",endCoder,email);
//        userRespository.save(user);
//    }
//    @PostConstruct
//    public void createRole(){
//        Role admin = new Role("ROLE_ADMIN");
//        Role editor = new Role("ROLE_EDITOR");
//        Role customer = new Role("ROLE_CUSTOMER");
//        List<Role> list = new ArrayList<>();
//        list.add(admin);
//        list.add(editor);
//        list.add(customer);
//
//        roleRespository.saveAll(list);
//
//    }
    public static void main(String[] args) {
        SpringApplication.run(ViabloApplication.class, args);
    }

}

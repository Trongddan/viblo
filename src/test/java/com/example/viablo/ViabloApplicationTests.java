package com.example.viablo;

import com.example.viablo.entity.Role;
import com.example.viablo.entity.User;
import com.example.viablo.respository.RoleRespository;
import com.example.viablo.respository.UserRespository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ViabloApplicationTests {
    @Autowired
    private UserRespository userRepository;
    @Autowired private RoleRespository roleRespository;
    //
    @Test
    public void addRoleToUsery(){
        int userId =4;
        User user = userRepository.findById(userId).get();
        Role role = roleRespository.findById(2).get();
        user.getRoles().add(role);
        userRepository.save(user);
    }
    @Test
    void contextLoads() {
    }

}

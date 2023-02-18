package com.example.viablo.service;

import com.example.viablo.entity.User;
import com.example.viablo.respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired private UserRespository userRespository;
    public Optional<User> findUserByUsername(String Username){
        return userRespository.findUserByUsername(Username);
    }
    public Optional<User> findUserByEmail(String email){
        return userRespository.findUserByEmail(email);
    }
    public Optional<User> getUserById(int id){
        return userRespository.findById(id);
    }
    public void registerUser(User user){
        userRespository.save(user);
    }

}

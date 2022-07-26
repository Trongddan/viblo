package com.example.viablo.respository;

import com.example.viablo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User,Integer> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}

package com.example.viablo.respository;

import com.example.viablo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRespository extends JpaRepository<Role,Integer> {
    Role findRoleByName(String name);
}

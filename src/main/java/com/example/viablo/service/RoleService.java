package com.example.viablo.service;

import com.example.viablo.entity.Role;
import com.example.viablo.respository.RoleRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired private RoleRespository roleRespository;
    public Role findRoleByName(String name){
        return roleRespository.findRoleByName(name);
    }
}

package com.example.viablo.respository;

import com.example.viablo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    Optional<Tag> findTagByName(String name);
}

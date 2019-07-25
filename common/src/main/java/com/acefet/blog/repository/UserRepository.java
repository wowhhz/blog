package com.acefet.blog.repository;

import com.acefet.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    public User getByUsername(String username);

    void deleteUsersByIdIn(List<String> ids);
}

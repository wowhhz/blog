package com.acefet.blog.service;

import com.acefet.blog.entity.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    List<User> findAll();

    void save(User user);

    void delete(List<User> userList);
}

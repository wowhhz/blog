package com.acefet.blog.service.impl;

import com.acefet.blog.entity.User;
import com.acefet.blog.repository.UserRepository;
import com.acefet.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(List<User> userList) {
        userRepository.deleteInBatch(userList);
    }


}

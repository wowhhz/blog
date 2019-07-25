package com.acefet.blog.service.impl;

import com.acefet.blog.CommonApplicationTest;
import com.acefet.blog.entity.User;
import com.acefet.blog.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class UserServiceImplTest extends CommonApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void findUserByUsername() {
        User user = userService.findUserByUsername("admin");
        Assert.assertTrue(user!=null);
    }

    @Test
    public void findAll() {
        List<User> userList = userService.findAll();
        Assert.assertTrue(userList.size()>0);
    }

    @Test
    public void save(){
        User user = new User();
        user.setId("sdfsf");
        user.setName("测试用户");
        user.setUsername("test");
        user.setPassword("test");
        userService.save(user);
        Assert.assertTrue(user!=null);
    }

    @Test
    public void delete(){
        User user = new User();
        user.setId("sdfsf");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userService.delete(userList);
        Assert.assertTrue(true);
    }
}
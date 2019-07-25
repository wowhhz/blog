package com.acefet.blog.repository;

import com.acefet.blog.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getByUsername() throws Exception{
        User user = userRepository.getByUsername("admin");
        Assert.assertTrue(user!=null);
    }

    @Test
    public void findAll() throws Exception{
        List<User> userList = userRepository.findAll();
        Assert.assertTrue(userList.size()>0);
    }

    @Test
    public void save() throws Exception{
        User user = new User();
        user.setId("sdfsf");
        user.setName("测试用户");
        user.setUsername("test");
        user.setPassword("test");
        userRepository.save(user);
        Assert.assertTrue(user!=null);
    }

    @Test
    public void deleteByUsername() throws Exception{
        userRepository.deleteById("sdfsf");
        Assert.assertTrue(true);
    }


}
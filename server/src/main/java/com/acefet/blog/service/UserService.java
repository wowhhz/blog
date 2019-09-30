package com.acefet.blog.service;

import com.acefet.blog.entity.User;
import com.acefet.blog.form.UserForm;
import com.acefet.blog.vo.UserVO;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    User findUserByUsername(String username);

    UserVO findUserById(String id);

    List<User> findAll();

    void save(User user);

    void delete(List<User> userList);

//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    List<UserVO> query();

    UserVO create(UserForm userForm, Map<String,String> map, BindingResult bindingResult) throws IOException;

    UserVO update(UserForm userForm, Map<String,String> map, BindingResult bindingResult) throws IOException;

    UserVO save(UserForm userForm, Map<String,String> map, BindingResult bindingResult) throws IOException;

    void resetPwd(Map<String,Object> reqMap);

    void delete(String[] ids);

    UserVO login(HttpServletRequest request, HttpSession session);

    void logout(HttpSession session);
}

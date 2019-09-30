package com.acefet.blog.convert;

import com.acefet.blog.constant.enums.UserStatus;
import com.acefet.blog.entity.User;
import com.acefet.blog.form.UserForm;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.UserVO;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UserConvert {

    /**
     * 表单对象转换成实例对象
     * @param userForm
     * @return
     */
    public static User userForm2User(UserForm userForm){
        //Gson gson = new Gson();
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setId(Util.getUUID());
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        String md5Password = Util.getMD5Str(userForm.getPassword());
        user.setPassword(md5Password);
        user.setStatus(UserStatus.NORMAL.getStatusCode());
        return user;
    }

    /**
     * 实例对象转换成返回对象
     * @param user
     * @return
     */
    public static UserVO user2UserVO(User user){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setStatus(UserStatus.getUserStatus(user.getStatus()).getStatusCode());
        userVO.setCreateTime(sdf.format(user.getCreateTime()));
        return userVO;
    }

}


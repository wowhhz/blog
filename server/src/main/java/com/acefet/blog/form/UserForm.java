package com.acefet.blog.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserForm {

    private String id;
    /**名字*/
    @NotNull(message = "名字不能为空")
    @Length(min = 1,max = 32,message = "名字在32位以内")
    private String name;
    /**用户名*/
    @NotNull(message = "用户名不能为空")
    @Length(min = 3,max = 32,message = "用户名在3-32位以内")
    private String username;
    /**密码*/
    @NotNull(message = "密码不能为空")
    private String password;
    /**头像*/
    private MultipartFile icon;
    /**email*/
    @Length(max = 255,message = "email太长，存不下了")
    private String email;
    /**手机*/
    @Length(max = 11,message = "手机号太长，存不下了")
    private String phone;
}

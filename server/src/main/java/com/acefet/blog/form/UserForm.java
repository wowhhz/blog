package com.acefet.blog.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserForm {

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
    @Length(min = 5,max = 32,message = "密码必须在5位以上，32位以内")
    private String password;
    /**头像*/
    @Length(max = 255,message = "头像地址太长，存不下了")
    private String icon;
}

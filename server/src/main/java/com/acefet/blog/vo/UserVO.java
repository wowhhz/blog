package com.acefet.blog.vo;

import com.acefet.blog.constant.enums.UserStatus;
import lombok.Data;

@Data
public class UserVO {

    /**编号*/
    private String id;
    /**名字*/
    private String name;
    /**用户名*/
    private String username;
    /**账户状态*/
    private String status;
    /**头像*/
    private String icon;
    /**创建时间*/
    private String createTime;
    /**email*/
    private String email;
    /**手机号*/
    private String phone;
}

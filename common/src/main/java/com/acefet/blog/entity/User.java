package com.acefet.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class User {
    /**编号*/
    @Id
    private String id;
    /**名字*/
    private String name;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**账户状态*/
    private String status;
    /**头像*/
    private String icon;
    /**创建时间*/
    private Timestamp createTime;



}

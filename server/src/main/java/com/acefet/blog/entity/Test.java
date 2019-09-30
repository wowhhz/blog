package com.acefet.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Test {

    /**id**/
    @Id
    private String id;
    /**创建时间**/
    private Timestamp createTime;
    /**pid**/
    private String pid;
    /**名称**/
    private String name;
    /**密码**/
    private String password;
    /**Email**/
    private String email;
    /**日期**/
    private String dater;
    /**时间**/
    private String timer;
    /**下拉项**/
    private String selectr;
    /**单选项**/
    private String radio;
    /**多选项**/
    private String checkbox;
    /**大文本**/
    private String textarea;
    /**文件**/
    private String file;
    /**内容**/
    private String content;

}

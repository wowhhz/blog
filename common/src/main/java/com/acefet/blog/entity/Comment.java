package com.acefet.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class Comment {

    /**编号*/
    @Id
    private String id;
    /**文章ID*/
    private String articleId;
    /**用户名*/
    private String name;
    /**头像*/
    private String icon;
    /**留言时间*/
    private Timestamp releaseTime;
    /**点赞数*/
    private Integer likeNum;
    /**排序*/
    private Integer sort;
    /**留言*/
    private String content;

}

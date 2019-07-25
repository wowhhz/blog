package com.acefet.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class Article {
    /**编号*/
    @Id
    private String id;
    /**分类*/
    private String classTypeCode;
    /**排序*/
    private Integer sort;
    /**标题*/
    private String title;
    /**作者*/
    private String author;
    /**发布时间*/
    private Timestamp releaseTime;
    /**内容*/
    private String content;
    /**标签*/
    private String flags;
    /**阅读数*/
    private Integer readNum;
    /**点赞数*/
    private Integer likeNum;
    /**是否开启留言*/
    private String hasComment;



}

package com.acefet.blog.vo;

import com.acefet.blog.constant.enums.FieldSortType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ArticleVO {

    private String id;
    private String classTypeCode;
    private Integer sort;
    private String title;
    private String author;
    private String releaseTime;
    private String content;
    private String flags;
    private Integer readNum;
    private Integer likeNum;
    private String hasComment;
    @JsonProperty("classType")
    private ClassTypeVO classTypeVO;
}

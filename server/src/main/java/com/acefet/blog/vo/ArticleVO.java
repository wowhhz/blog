package com.acefet.blog.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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

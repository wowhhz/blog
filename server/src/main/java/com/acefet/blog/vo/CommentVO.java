package com.acefet.blog.vo;

import com.acefet.blog.constant.enums.FieldSortType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class CommentVO {

    private String id;
    private String articleId;
    private String name;
    private String icon;
    private String releaseTime;
    private Integer likeNum;
    private Integer sort;
    private String content;
}

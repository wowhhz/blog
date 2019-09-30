package com.acefet.blog.vo;

import lombok.Data;


@Data
public class CommentVO {

    private String id;
    private String articleId;
    private String name;
    private String icon;
    private String releaseTime;
    private Integer likeNum;
    private Integer unlikeNum;
    private Integer sort;
    private String content;
    private String email;
    /**网站**/
    private String site;
    /**是否审核**/
    private String hasCheck;
    /**预审**/
    private String preCheck;
}

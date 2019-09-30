package com.acefet.blog.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


@Data
public class CommentForm {

    private String id;
    /**文章ID*/
    @NotNull(message = "文章ID不能为空")
    private String articleId;
    /**用户名*/
    @Length(max = 32, message = "用户名最多32个字符")
    private String name;
    /**头像*/
    @Length(max = 255,message = "头像地址太长，存不下了")
    private String icon;
    /**留言时间*/
    private String releaseTime;
    /**留言*/
    @NotNull(message = "留言内容不能为空")
    @Length(min = 1,max = 4000, message = "留言最小1个字符，最多4000个字符")
    private String content;
    /**email*/
    @Length(max = 255,message = "email太长，存不下了")
    private String email;
    /**网站**/
    @Length(max = 255,message = "网址太长，存不下了")
    private String site;
    /**是否审核**/
    private String hasCheck;
    /**预审**/
    private String preCheck;
}

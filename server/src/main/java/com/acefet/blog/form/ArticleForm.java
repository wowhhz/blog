package com.acefet.blog.form;

import com.acefet.blog.constant.enums.FieldSortType;
import com.acefet.blog.vo.ClassTypeVO;
import com.acefet.blog.vo.CommentVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ArticleForm {

    private String id;
    private String classTypeCode;
    private Integer sort;
    @NotNull(message = "文章标题不能为空")
    @Length(min = 1,max = 255, message = "标题为1-255个字符之间")
    private String title;
    @Length(max = 255, message = "作者最多255个字符")
    private String author;
    private String releaseTime;
    @NotNull(message = "文章内容不能为空")
    @Length(min = 1, message = "文章必须有内容")
    private String content;
    @Length(max = 255, message = "标签最多255个字符")
    private String flags;
    private String hasComment;
}

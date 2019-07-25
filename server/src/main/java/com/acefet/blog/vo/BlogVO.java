package com.acefet.blog.vo;

import com.acefet.blog.entity.Article;
import com.acefet.blog.entity.ClassType;
import com.acefet.blog.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class BlogVO {

    private List<ClassType> classTypes;

    private List<Article> articles;

    private List<Comment> comments;

}

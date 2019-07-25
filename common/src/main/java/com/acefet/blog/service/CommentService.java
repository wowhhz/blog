package com.acefet.blog.service;

import com.acefet.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    Comment getById(String id);

    Page<Comment> findByArticleId(String articleId,Pageable pageable);

    Page<Comment> findAll(Pageable pageable);

    void save(Comment comment);

    void deleteById(String id);

    void deleteByArticleId(String articleId);

    void delete(List<Comment> commentList);
}

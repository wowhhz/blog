package com.acefet.blog.repository;

import com.acefet.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment,String>, JpaRepository<Comment,String> {
    public Page<Comment> findByArticleId(String articleId,Pageable pageable);

    public Page<Comment> findAll(Pageable pageable);

    public List<Comment> findByArticleId(String articleId);

    public void deleteCommentByArticleId(String articleId);
}

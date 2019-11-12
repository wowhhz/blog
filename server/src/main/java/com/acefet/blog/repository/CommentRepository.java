package com.acefet.blog.repository;

import com.acefet.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment,String>, JpaRepository<Comment,String>, JpaSpecificationExecutor<Comment> {
    Page<Comment> findByArticleId(String articleId,Pageable pageable);

    Page<Comment> findAll(Pageable pageable);

    List<Comment> findByArticleId(String articleId);

    void deleteCommentByArticleId(String articleId);

    List<Comment> findByIdIn(String[] ids);

    long countByHasCheck(String hasCheck);

    @Query(value = "select COALESCE(sum(like_num), 0) as likeNum from comment", nativeQuery = true)
    long totalLikeNum();

    @Query(value = "select COALESCE(sum(unlike_num),0) as unlikeNum from comment", nativeQuery = true)
    long totalUnlikeNum();


}

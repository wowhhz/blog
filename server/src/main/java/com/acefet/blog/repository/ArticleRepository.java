package com.acefet.blog.repository;

import com.acefet.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Map;

public interface ArticleRepository extends PagingAndSortingRepository<Article,String>, JpaRepository<Article,String>,JpaSpecificationExecutor<Article> {

    Article getArticleById(String id);

    Page<Article> findAll(Pageable pageable);

    Page<Article> findAllByClassTypeCode(String classTypeCode,Pageable pageable);

    Page<Article> findAllByClassTypeCodeAndTitleLike(String classTypeCode,String title,Pageable pageable);

    Page<Article> findAllByTitleLike(String searchValue, Pageable pageable);

    @Query(value = "select sum(read_num) from article", nativeQuery = true)
    long totalReadNum();

    @Query(value = "select sum(like_num) from article", nativeQuery = true)
    long totalLikeNum();

    @Query(value = "select * from article order by read_num desc limit 0,1", nativeQuery = true)
    Article getArticleForMaxReadNum();

    @Query(value = "select * from article order by like_num desc limit 0,1", nativeQuery = true)
    Article getArticleForMaxLikeNum();

    @Query(value = "select a.title,c.article_id,count(*) as num from comment c,article a where a.id=c.article_id group by c.article_id order by num desc limit 0,1 ", nativeQuery = true)
    Map<String,Object> getArticleForMaxCommentNum();

}

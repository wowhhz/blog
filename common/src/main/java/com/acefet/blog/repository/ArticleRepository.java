package com.acefet.blog.repository;

import com.acefet.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article,String>, JpaRepository<Article,String> {

    public Article getArticleById(String id);

    public Page<Article> findAll(Pageable pageable);

    public Page<Article> findAllByClassTypeCode(String classTypeCode,Pageable pageable);

}

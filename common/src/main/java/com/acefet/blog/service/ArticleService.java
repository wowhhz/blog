package com.acefet.blog.service;

import com.acefet.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ArticleService {

    Article getById(String id) throws UnsupportedEncodingException;

    Page<Article> findAll(Pageable pageable) throws UnsupportedEncodingException;

    Page<Article> findAllByClassTypeCode(String classTypeCode, Pageable pageable) throws UnsupportedEncodingException;

    void save(Article article);

    void delete(List<Article> articleList);
}

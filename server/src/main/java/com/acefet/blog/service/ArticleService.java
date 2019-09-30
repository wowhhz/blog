package com.acefet.blog.service;

import com.acefet.blog.entity.Article;
import com.acefet.blog.form.ArticleForm;
import com.acefet.blog.vo.ArticleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface ArticleService {

    Article getById(String id) throws UnsupportedEncodingException;

    Page<Article> findAll(Pageable pageable) throws UnsupportedEncodingException;

    Page<Article> findAllByClassTypeCode(String classTypeCode, Pageable pageable) throws UnsupportedEncodingException;

    void save(Article article);

    void delete(List<Article> articleList);

    Page<ArticleVO> query(Map map) throws UnsupportedEncodingException;

    Page<Article> query(String classTypeCode, String searchValue, String orderType, String orderName, String pageNum, String pageSize) throws UnsupportedEncodingException;

    ArticleVO show(String id) throws UnsupportedEncodingException;

    ArticleVO view(String id) throws UnsupportedEncodingException;

    ArticleVO like(String id,String isLike) throws UnsupportedEncodingException;

    ArticleVO create(ArticleForm articleForm,BindingResult bindingResult);

    ArticleVO update(ArticleForm articleForm,BindingResult bindingResult) throws UnsupportedEncodingException;

    ArticleVO save(ArticleForm articleForm, BindingResult bindingResult) throws IOException;

    void delete(String[] ids);

    Map<String,Object> getStatisticalData();
}

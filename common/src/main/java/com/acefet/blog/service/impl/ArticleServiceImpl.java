package com.acefet.blog.service.impl;

import com.acefet.blog.entity.Article;
import com.acefet.blog.repository.ArticleRepository;
import com.acefet.blog.service.ArticleService;
import com.acefet.blog.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article getById(String id) throws UnsupportedEncodingException {
        return transContent(articleRepository.getArticleById(id));
    }

    @Override
    public Page<Article> findAll(Pageable pageable) throws UnsupportedEncodingException {
        return transContent(articleRepository.findAll(pageable));

    }

    @Override
    public Page<Article> findAllByClassTypeCode(String classTypeCode, Pageable pageable) throws UnsupportedEncodingException {
        return transContent(articleRepository.findAllByClassTypeCode(classTypeCode, pageable));
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void delete(List<Article> articleList) {
        articleRepository.deleteInBatch(articleList);
    }

    /**
     * conversion content blob field
     * @param article
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Article transContent(Article article) throws UnsupportedEncodingException {
        if(null==article)return null;
        article.setContent(Util.getBlobField(article.getContent()));
        return article;
    }

    /**
     * conversion Page object content blob field
     * @param articlePage
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Page<Article> transContent(Page<Article> articlePage) throws UnsupportedEncodingException {
        List<Article> articleList = articlePage.getContent();
        for(Article article : articleList){
            transContent(article);
        }
        return articlePage;
    }
}

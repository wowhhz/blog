package com.acefet.blog.service.impl;

import com.acefet.blog.ServerApplicationTest;
import com.acefet.blog.entity.Article;
import com.acefet.blog.service.ArticleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Component
public class ArticleServiceImplTest extends ServerApplicationTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void getById() throws UnsupportedEncodingException {
        Article article = articleService.getById("1");
        assertTrue(article!=null);
    }

    @Test
    public void findAll() {
        Sort sort = new Sort(Sort.Direction.DESC,new String[]{"fieldSort","releaseTime"});
//        List<Article> articleList = (List<Article>) articleService.findAll();
//        Assert.assertTrue(articleList.size()>0);
    }

    @Test
    public void save() throws ParseException {
        Article article = new Article();
        article.setId("1");
        article.setAuthor("admin");
        article.setClassTypeCode("123");
        article.setContent("test");
        article.setSort(99);
        article.setHasComment("true");
        article.setLikeNum(0);
        article.setReadNum(1);
        article.setReleaseTime(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-02 02:01:01").getTime()));
        article.setTitle("文章测试");
        articleService.save(article);
        assertTrue(true);
    }

    @Test
    public void delete() {
        String[] ids = "1".split(",");
        List<Article> articleList = new ArrayList<>();
        for (String id: ids) {
            Article article = new Article();
            article.setId(id);
            articleList.add(article);
        }
        articleService.delete(articleList);
    }
}
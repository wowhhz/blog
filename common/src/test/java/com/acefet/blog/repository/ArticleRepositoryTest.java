package com.acefet.blog.repository;

import com.acefet.blog.entity.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void save() throws Exception{
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
        articleRepository.save(article);
        Assert.assertTrue(true);
    }

    @Test
    public void getArticleById() throws  Exception{
        Article article = articleRepository.getArticleById("Nm2OvFt7");
        article.setContent(new String(article.getContent().getBytes("ISO-8859-1"),"UTF-8"));
        Assert.assertTrue(article!=null);
    }

    @Test
    public void findAll(){
        Sort sort = new Sort(Sort.Direction.DESC,new String[]{"fieldSort","releaseTime"});
        List<Article> articleList = (List<Article>) articleRepository.findAll(sort);
        Assert.assertTrue(articleList.size()>0);


    }

    @Test
    public void deleteById(){
        articleRepository.deleteById("1");
        Assert.assertTrue(true);
    }

    //分页测试

}
package com.acefet.blog.service.impl;

import com.acefet.blog.CommonApplicationTest;
import com.acefet.blog.entity.Comment;
import com.acefet.blog.service.CommentService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CommentServiceImplTest extends CommonApplicationTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void save() throws Exception{
        Comment comment = new Comment();
        comment.setId("123");
        comment.setArticleId("1");
        comment.setContent("test");
        comment.setLikeNum(1);
        comment.setReleaseTime(new Timestamp(System.currentTimeMillis()));
        comment.setSort(1);
        comment.setName("测试用户");
        commentService.save(comment);

        comment = new Comment();
        comment.setId("234");
        comment.setArticleId("2");
        comment.setContent("test");
        comment.setLikeNum(1);
        comment.setReleaseTime(new Timestamp(System.currentTimeMillis()));
        comment.setSort(1);
        comment.setName("测试用户");
        commentService.save(comment);
        Assert.assertTrue(true);
    }

    @Test
    public void findByArticleId() throws  Exception{
//        List<Comment> commentList = commentService.findByArticleId("1");
//        Assert.assertTrue(commentList.size()>0);
    }
    @Test
    public void deleteById(){
        commentService.deleteById("123");
        Assert.assertTrue(true);
    }
    @Test
    public void deleteByArticleId(){
        commentService.deleteByArticleId("2");
        Assert.assertTrue(true);
    }

    @Test
    public void delete(){
        String[] ids = "123,234".split(",");
        List<Comment> list = new ArrayList<>();
        for (String id : ids){
            Comment comment = new Comment();
            comment.setId(id);
            list.add(comment);
        }
        commentService.delete(list);
    }

}
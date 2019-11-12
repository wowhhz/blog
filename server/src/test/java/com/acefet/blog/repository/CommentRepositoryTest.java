package com.acefet.blog.repository;

import com.acefet.blog.ServerApplication;
import com.acefet.blog.entity.Comment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ServerApplication.class)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

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
        commentRepository.save(comment);

        comment = new Comment();
        comment.setId("234");
        comment.setArticleId("2");
        comment.setContent("test");
        comment.setLikeNum(1);
        comment.setReleaseTime(new Timestamp(System.currentTimeMillis()));
        comment.setSort(1);
        comment.setName("测试用户");
        commentRepository.save(comment);
        Assert.assertTrue(true);
    }

    @Test
    public void findByArticleId() throws  Exception{
        List<Comment> commentList = commentRepository.findByArticleId("1");
        Assert.assertTrue(commentList.size()>0);
    }
    @Test
    public void deleteById(){
        commentRepository.deleteById("123");
        Assert.assertTrue(true);
    }
    @Test
    public void deleteByArticleId(){
        commentRepository.deleteCommentByArticleId("2");
        Assert.assertTrue(true);
    }

    @Test
    public void count(){
        long count = commentRepository.count();
        Assert.assertTrue(count>0);
    }

    @Test
    public void totalLikeNum(){
        long totalLikeNum = commentRepository.totalLikeNum();
        Assert.assertTrue(totalLikeNum>-1);
    }

    @Test
    public void totalUnlikeNum(){
        long totalUnlikeNum = commentRepository.totalUnlikeNum();
        Assert.assertTrue(totalUnlikeNum>-1);
    }

}
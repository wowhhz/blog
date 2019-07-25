package com.acefet.blog.service.impl;

import com.acefet.blog.entity.Comment;
import com.acefet.blog.repository.CommentRepository;
import com.acefet.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment getById(String id) {
        return commentRepository.getOne(id);
    }

    @Override
    public Page<Comment> findByArticleId(String articleId,Pageable pageable) {
        return commentRepository.findByArticleId(articleId,pageable);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteByArticleId(String articleId) {
        List<Comment> commentList = commentRepository.findByArticleId(articleId);
        commentRepository.deleteInBatch(commentList);
    }

    @Override
    public void delete(List<Comment> commentList) {
        commentRepository.deleteInBatch(commentList);
    }
}

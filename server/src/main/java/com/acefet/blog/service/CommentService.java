package com.acefet.blog.service;

import com.acefet.blog.entity.Comment;
import com.acefet.blog.form.CommentForm;
import com.acefet.blog.vo.CommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface CommentService {

    CommentVO getById(String id);

    Page<Comment> findByArticleId(String articleId,Pageable pageable);

    Page<Comment> findAll(Pageable pageable);

    void save(Comment comment);

    void deleteById(String id);

    void deleteByArticleId(String articleId);

    void delete(List<Comment> commentList);

    Page<CommentVO> query(Map map);

    CommentVO like(String id,String isLike);

    CommentVO create(CommentForm commentForm,BindingResult bindingResult);

    CommentVO update(CommentForm commentForm, BindingResult bindingResult);

    CommentVO save(CommentForm commentForm, BindingResult bindingResult);

    void del(String id);

    void delete(String[] ids);

    void mark(String[] ids);

    Long getNotCheckCount();

    Map<String,Object> getStatisticalData();
}

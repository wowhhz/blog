package com.acefet.blog.controller;

import com.acefet.blog.convert.CommentConvert;
import com.acefet.blog.entity.Comment;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.UserException;
import com.acefet.blog.form.CommentForm;
import com.acefet.blog.service.CommentService;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.CommentVO;
import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    final static int PAGE_SIZE = 10;
    final static int DEFAULT_PAGE = 0;

    @Autowired
    private CommentService commentService;

    /**
     * 根据文章ID过滤分页查找留言
     * @param articleId
     * @param pageNum
     * @return
     */
    @GetMapping({"/list/{articleId}/{pageSize}/{pageNum}",
            "/list/articleid={articleId}&pagesize={pageSize}&pagenum={pageNum}"})
    public ResultVO<Page> list(
            @PathVariable String articleId,
            @PathVariable String pageSize,
            @PathVariable String pageNum){
        int size = PAGE_SIZE, page = DEFAULT_PAGE;
        if(null==pageNum || "".equals(pageNum.trim()))pageNum="1";
        if(null!=pageSize && Util.isNumeric(pageSize))size=Integer.parseInt(pageSize);
        if(!Util.isNumeric(pageNum)){
            log.error("【查看留言】参数不正确，pageNum={},articleId={}",pageNum,articleId);
            throw new UserException(ResultEnum.PARAM_ERROR);
        }
        page = Integer.parseInt(pageNum)-1;
        Sort sort = Sort.by(Sort.Order.desc("releaseTime"));
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Comment> commentPage = commentService.findByArticleId(articleId,pageable);
        return ResultVOUtil.success(commentPage);
    }

    /**
     * 添加留言
     * @param commentForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<CommentVO> create(@Valid CommentForm commentForm,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【添加留言】参数不正确，CommentForm={}",commentForm);
            throw new UserException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //创建留言
        Comment comment = CommentConvert.commentForm2Comment(commentForm);
        commentService.save(comment);

        //返回留言
        CommentVO commentVO = CommentConvert.comment2CommentVO(comment);
        return ResultVOUtil.success(commentVO);
    };

    /**
     * 删除留言
     * @param reqMap
     * @return
     */
    @PostMapping("/delete")
    public ResultVO delete(@RequestParam Map<String,Object> reqMap){
        String idsStr = (String)reqMap.get("ids");
        if(idsStr==null || "".equals(idsStr)){
            log.error("【删除留言】参数不正确，ids={}",idsStr);
            throw new UserException(ResultEnum.PARAM_ERROR);
        }
        String[] ids = idsStr.split(",");
        List<Comment> commentList = new ArrayList<Comment>();
        for(String id : ids){
            Comment comment = new Comment();
            comment.setId(id);
            commentList.add(comment);
        }
        commentService.delete(commentList);
        return ResultVOUtil.success();
    };
}

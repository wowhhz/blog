package com.acefet.blog.controller;

import com.acefet.blog.entity.Comment;
import com.acefet.blog.form.CommentForm;
import com.acefet.blog.service.CommentService;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.vo.CommentVO;
import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

//    @Autowired
//    private CommentService commentService;
//
//    /**
//     * 根据文章ID过滤分页查找留言
//     * @param articleId
//     * @param searchValue
//     * @param orderType
//     * @param orderName
//     * @param hasCheck
//     * @param pageSize
//     * @param pageNum
//     * @return
//     */
//    @GetMapping("/list/articleid={articleId}&pagesize={pageSize}" +
//            "&pagenum={pageNum}&searchvalue={searchValue}" +
//            "&ordertype={orderType}&orderName={orderName}&hasCheck={hasCheck}")
//    public ResultVO<Page> query(
//            @PathVariable String articleId,
//            @PathVariable String searchValue,
//            @PathVariable String orderType,
//            @PathVariable String orderName,
//            @PathVariable String hasCheck,
//            @PathVariable String pageSize,
//            @PathVariable String pageNum){
//
//        Map map = new HashMap();
//        map.put("articleId",articleId);
//        map.put("searchValue",searchValue);
//        map.put("orderType",orderType);
//        map.put("orderName",orderName);
//        map.put("hasCheck",hasCheck);
//        map.put("pageSize",pageSize);
//        map.put("pageNum",pageNum);
//
//        Page<CommentVO> commentVOPage = commentService.query(map);
//        return ResultVOUtil.success(commentVOPage);
//    }
//
//    /**
//     * 对留言点赞
//     * @param id
//     * @param isLike
//     * @return
//     */
//    @GetMapping("/like/{id}/{isLike}")
//    public ResultVO<CommentVO> like(@PathVariable String id,
//                                     @PathVariable String isLike){
//        CommentVO commentVO = commentService.like(id,isLike);
//        return ResultVOUtil.success(commentVO);
//    }
//
//    /**
//     * 添加留言
//     * @param commentForm
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("/create")
//    public ResultVO<CommentVO> create(@Valid CommentForm commentForm,
//                           BindingResult bindingResult){
//        CommentVO commentVO = commentService.create(commentForm,bindingResult);
//        return ResultVOUtil.success(commentVO);
//    };
//
//    /**
//     * 删除留言
//     * @param id
//     * @return
//     */
//    @GetMapping({"/del/{id}","/del/id={id}"})
//    public ResultVO del(@PathVariable String id){
//        commentService.del(id);
//        return ResultVOUtil.success();
//    }
//
//    /**
//     * 删除留言
//     * @param ids
//     * @return
//     */
//    @PostMapping("/delete")
//    public ResultVO delete(@RequestParam String[] ids){
//        commentService.delete(ids);
//        return ResultVOUtil.success();
//    };
}

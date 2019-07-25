package com.acefet.blog.convert;

import com.acefet.blog.entity.Comment;
import com.acefet.blog.form.CommentForm;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.CommentVO;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentConvert {

    /**
     * 表单对象转换成实例对象
     * @param commentForm
     * @return
     */
    public static Comment commentForm2Comment(CommentForm commentForm){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentForm,comment);
        comment.setId(Util.getUUID());
        comment.setReleaseTime(new Timestamp(System.currentTimeMillis()));
        comment.setLikeNum(0);
        comment.setSort(1);
        return comment;
    }

    /**
     * 实例对象转换成返回对象
     * @param comment
     * @return
     */
    public static CommentVO comment2CommentVO(Comment comment){
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment,commentVO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        commentVO.setReleaseTime(sdf.format(comment.getReleaseTime()));
        return commentVO;
    }

//    /**
//     * (已废弃)实例对象返回成返回对象，返回子留言
//     * @param comment
//     * @param commentList
//     * @return
//     */
//    private static CommentVO comment2CommentVO(Comment comment, List<Comment> commentList){
//
//        CommentVO commentVO = new CommentVO();
//        BeanUtils.copyProperties(comment,commentVO);
//        String subId = comment.getCommentId();
//        if(subId==null || "".equals(subId)){
//            return commentVO;
//        }
//        List<CommentVO> volist = new ArrayList<CommentVO>();
//        for (int i = 0; i < commentList.size(); i++) {
//            Comment comment1 = commentList.get(i);
//            if (!comment1.getCommentId().equals(comment1.getId())
//                    && subId.equals(comment1.getId())){
//                volist.add(comment2CommentVO(comment1,commentList));
//            }
//        }
//        commentVO.setCommentVOList(volist);
//        return commentVO;
//
//    }
}

package com.acefet.blog.service.impl;

import com.acefet.blog.convert.CommentConvert;
import com.acefet.blog.entity.Comment;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.SystemException;
import com.acefet.blog.form.CommentForm;
import com.acefet.blog.repository.CommentRepository;
import com.acefet.blog.service.CommentService;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentVO getById(String id) {
        Comment comment = commentRepository.getOne(id);
        CommentVO commentVO = null;
        if(comment!=null){
            commentVO = CommentConvert.comment2CommentVO(comment);
        }
        return commentVO;
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

    /**
     * 分页查询
     * @param map
     * @return
     */
    @Override
    public Page<CommentVO> query(Map map) {
        String articleId = (String)map.get("articleId");
        String searchValue = (String)map.get("searchValue");
        String orderType = (String)map.get("orderType");
        String orderName = (String)map.get("orderName");
        String pageSize = (String)map.get("pageSize");
        String pageNum = (String)map.get("pageNum");
        String hasCheck = (String)map.get("hasCheck");

        int size = com.acefet.blog.util.Page.PAGESIZE_DEFAULT, page = 0;
        if(null==pageNum || "".equals(pageNum.trim()))pageNum="1";
        if(null!=pageSize && Util.isNumeric(pageSize))size=Integer.parseInt(pageSize);
        if(!StringUtils.isEmpty(articleId) && !Util.checkShortId(articleId)){
            log.error("【 getComments】articleId="+articleId, ResultEnum.PARAM_ERROR);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        if(!Util.isNumeric(pageNum)){
            log.error("【查看留言】参数不正确，pageNum={},articleId={}",pageNum,articleId);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        page = Integer.parseInt(pageNum)-1;
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        if(StringUtils.isEmpty(orderType) || StringUtils.isEmpty(orderName)){
            orderList.add(Sort.Order.desc("sort"));
            orderList.add(Sort.Order.desc("releaseTime"));
        }else{
            if("asc".equalsIgnoreCase(orderType)){
                orderList.add(Sort.Order.asc(orderName));
            }else{
                orderList.add(Sort.Order.desc(orderName));
            }
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderList));
        Page<Comment> commentPage = null;

        Specification<Comment> queryCondition = new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<>();
                if(!StringUtils.isEmpty(articleId)){
                    predicateList.add(criteriaBuilder.equal(root.get("articleId"), articleId));
                }

                if(!StringUtils.isEmpty(searchValue)){
                    predicateList.add(criteriaBuilder.like(root.get("content"), "%"+searchValue+"%"));
                }

                if(!StringUtils.isEmpty(hasCheck)){
                    predicateList.add(criteriaBuilder.equal(root.get("hasCheck"), hasCheck));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        commentPage = commentRepository.findAll(queryCondition,pageable);

        Page<CommentVO> commentVOPage = null;
        if(commentPage!=null){
            commentVOPage = commentPage.map(this::convertToCommentDto);
        }
        return commentVOPage;
    }

    private CommentVO convertToCommentDto(Comment comment){
        CommentVO commentVO = CommentConvert.comment2CommentVO(comment);
        return commentVO;
    }

    /**
     * 留言点赞同或不赞同
     * @param id
     * @param isLike
     * @return
     */
    @Override
    public CommentVO like(String id, String isLike) {
        if(null==isLike || "".equals(isLike) || null==id || "".equals(id)){
            log.error("【点赞留言】参数不正确，id={},isAgree={}",id,isLike);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        Comment comment = commentRepository.getOne(id);
        if(comment==null){
            log.error("【点赞留言】留言不存在，id={}",id);
            throw new SystemException(ResultEnum.ARTICLE_NOT_EXIST);
        }
        if("true".equals(isLike)){
            comment.setLikeNum(comment.getLikeNum()+1);
        }else{
            comment.setUnlikeNum(comment.getUnlikeNum()+1);
        }
        save(comment);
        CommentVO commentVO = CommentConvert.comment2CommentVO(comment);
        return commentVO;
    }

    /**
     * 创建留言
     * @param commentForm
     * @param bindingResult
     * @return
     */
    @Override
    public CommentVO create(CommentForm commentForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.error("【添加留言】参数不正确，CommentForm={}",commentForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //创建留言
        Comment comment = CommentConvert.commentForm2Comment(commentForm);
        commentRepository.save(comment);

        //返回留言
        CommentVO commentVO = CommentConvert.comment2CommentVO(comment);
        return commentVO;
    }

    /**
     * 更新留言
     * @param commentForm
     * @param bindingResult
     * @return
     */
    @Override
    public CommentVO update(CommentForm commentForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.error("【添加留言】参数不正确，CommentForm={}",commentForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        Comment comment = commentRepository.getOne(commentForm.getId());
        if(comment==null){
            log.error("【更新comment】数据不存在,id={}",commentForm.getId());
            throw new SystemException(ResultEnum.CUSTOM_NOT_EXIST);
        }

        //创建留言
        BeanUtils.copyProperties(commentForm,comment);
        commentRepository.save(comment);

        //返回留言
        CommentVO commentVO = CommentConvert.comment2CommentVO(comment);
        return commentVO;
    }

    @Override
    public CommentVO save(CommentForm commentForm, BindingResult bindingResult){
        CommentVO commentVO = null;
        if(StringUtils.isEmpty(commentForm.getId())){
            commentVO = create(commentForm,bindingResult);
        }else{
            commentVO = update(commentForm,bindingResult);
        }
        return commentVO;
    }

    /**
     * 删除留言
     * @param id
     */
    @Override
    public void del(String id) {
        if(id==null || "".equals(id)){
            log.error("【删除留言】参数不正确，id={}",id);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        deleteById(id);
    }

    /**
     * 删除留言
     * @param ids
     */
    @Override
    public void delete(String[] ids) {
        if(ids==null){
            log.error("【删除留言】参数不正确，ids={}",ids);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        List<Comment> commentList = new ArrayList<Comment>();
        for(String id : ids){
            Comment comment = new Comment();
            comment.setId(id);
            commentList.add(comment);
        }
        delete(commentList);
    }

    /**
     * 批量标记为已审
     * @param ids
     */
    @Override
    public void mark(String[] ids) {
        if(ids==null){
            log.error("【标记为已审】参数不正确，ids={}",ids);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        List<Comment> commentList = commentRepository.findByIdIn(ids);
        for(Comment comment : commentList){
            comment.setPreCheck("true");
            comment.setHasCheck("true");
            commentRepository.save(comment);
        }
    }

    @Override
    public Long getNotCheckCount(){
        return commentRepository.countByHasCheck("false");
    }

    @Override
    public Map<String,Object> getStatisticalData(){
        Map map = new HashMap();
        //总的评论数
        map.put("countComments",commentRepository.count());
        //总的点赞数
        map.put("totalLikeNumComments",commentRepository.totalLikeNum());
        //总的不支持赞数
        map.put("totalUnlikeNumComments",commentRepository.totalUnlikeNum());
        return map;
    }

}

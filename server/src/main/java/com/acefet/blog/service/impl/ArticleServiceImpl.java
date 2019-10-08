package com.acefet.blog.service.impl;

import com.acefet.blog.convert.ArticleConvert;
import com.acefet.blog.convert.ClassTypeConvert;
import com.acefet.blog.entity.Article;
import com.acefet.blog.entity.ClassType;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.SystemException;
import com.acefet.blog.form.ArticleForm;
import com.acefet.blog.form.TestForm;
import com.acefet.blog.repository.ArticleRepository;
import com.acefet.blog.service.ArticleService;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.service.CommentService;
import com.acefet.blog.util.StringUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.ArticleVO;
import com.acefet.blog.vo.ClassTypeVO;
import com.acefet.blog.vo.TestVO;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ClassTypeService classTypeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article getById(String id) throws UnsupportedEncodingException {
        return transContent(articleRepository.getArticleById(id));
    }

    @Override
    public Page<Article> findAll(Pageable pageable) throws UnsupportedEncodingException {
        return transContent(articleRepository.findAll(pageable));

    }

    @Override
    public Page<Article> findAllByClassTypeCode(String classTypeCode, Pageable pageable) throws UnsupportedEncodingException {
        return transContent(articleRepository.findAllByClassTypeCode(classTypeCode, pageable));
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void delete(List<Article> articleList) {
        articleRepository.deleteInBatch(articleList);
    }

    /**
     * conversion content blob field
     * @param article
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Article transContent(Article article) throws UnsupportedEncodingException {
        if(null==article)return null;
        article.setContent(Util.getBlobField(article.getContent()));
        return article;
    }

    /**
     * conversion Page object content blob field
     * @param articlePage
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Page<Article> transContent(Page<Article> articlePage) throws UnsupportedEncodingException {
        List<Article> articleList = articlePage.getContent();
        for(Article article : articleList){
            transContent(article);
        }
        return articlePage;
    }

    /**
     * 分页查询，返回Page<ArticleVO>对象
     * @param map
     * @return
     */
    @Override
    public Page<ArticleVO> query(Map map) throws UnsupportedEncodingException {
        String searchValue = (String)map.get("searchValue");
        String orderType = (String)map.get("orderType");
        String orderName = (String)map.get("orderName");
        String pageSize = (String)map.get("pageSize");
        String pageNum = (String)map.get("pageNum");
        String classTypeCode = (String)map.get("classTypeCode");
        String flag = (String)map.get("flag");
        String author = (String)map.get("author");

        int size = com.acefet.blog.util.Page.PAGESIZE_DEFAULT, page = 0;
        if(pageNum==null || "".equals(pageNum.trim()))pageNum="1";
        if(pageSize!=null && Util.isNumeric(pageSize))size = Integer.parseInt(pageSize);
        if(!Util.isNumeric(pageNum)){
            log.error("【查看文章】参数不正确，pageNum={}",pageNum);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        if(!StringUtils.isEmpty(searchValue) && searchValue.length()>50){
            log.error("【查看文章】"+ResultEnum.ARTICLE_LENGTH_ERROR.getMessage()+"，searchValue={}",searchValue);
            throw new SystemException(ResultEnum.ARTICLE_LENGTH_ERROR);
        }
        page = Integer.parseInt(pageNum)-1;

        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        if(StringUtils.isEmpty(orderType) || StringUtils.isEmpty(orderName)) {
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

        Specification<Article> queryCondition = new Specification<Article>(){

            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<>();
                if(!StringUtils.isEmpty(classTypeCode)){
                    predicateList.add(criteriaBuilder.equal(root.get("classTypeCode"),classTypeCode));
                }

                if(StringUtils.isEmpty(author) && StringUtils.isEmpty(flag) && !StringUtils.isEmpty(searchValue)){
                    String[] strs = searchValue.trim().split("\\s+");
                    List<String> qlist = new ArrayList<String>();
                    for (String str : strs){
                        qlist.add(str);
                        String _str = StringUtil.upperCase(str);
                        if(!_str.equals(str)){
                            qlist.add(_str);
                        }
                    }
                    List<Predicate> titlePredicates = new ArrayList<>();
                    List<Predicate> contentPredicates = new ArrayList<>();
                    List<Predicate> predicates = new ArrayList<>();
                    for(String str: qlist){
                        titlePredicates.add(criteriaBuilder.like(root.get("title"),"%"+str+"%"));
                        contentPredicates.add(criteriaBuilder.like(root.get("content"),"%"+str+"%"));
                    }
                    predicates.add(
                            criteriaBuilder.and(titlePredicates.toArray(new Predicate[titlePredicates.size()]))
                    );
                    predicates.add(
                            criteriaBuilder.and(contentPredicates.toArray(new Predicate[contentPredicates.size()]))
                    );
                    predicateList.add(
                            criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
                    );
                }

                if(!StringUtils.isEmpty(flag)){
                    predicateList.add(criteriaBuilder.like(root.get("flags"), "%"+searchValue+"%"));
                }

                if(!StringUtils.isEmpty(author)){
                    predicateList.add(criteriaBuilder.like(root.get("author"), "%"+searchValue+"%"));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
        Page<Article> articlePage = transContent(articleRepository.findAll(queryCondition,pageable));
        Page<ArticleVO> articleVOPage = null;
        if(articlePage!=null){
            articleVOPage = articlePage.map(this::convertToArticleDto);
        }
        return articleVOPage;
    }

    private ArticleVO convertToArticleDto(Article article) {
        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);
        return articleVO;
    }

    /**
     * 封装成分页对象，返回Page<Article>对象
     * @param classTypeCode
     * @param pageNum
     * @param pageSize
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public Page<Article> query(String classTypeCode,String searchValue,
                        String orderType,String orderName,
                        String pageNum,String pageSize) throws UnsupportedEncodingException {
        int size = com.acefet.blog.util.Page.PAGESIZE_DEFAULT, page = 0;
        if(pageNum==null || "".equals(pageNum.trim()))pageNum="1";
        if(pageSize!=null && Util.isNumeric(pageSize))size = Integer.parseInt(pageSize);
        if(!Util.isNumeric(pageNum)){
            log.error("【查看文章】参数不正确，pageNum={}",pageNum);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        page = Integer.parseInt(pageNum)-1;

        Page<Article> articlePage = null;
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        if(StringUtils.isEmpty(orderType) || StringUtils.isEmpty(orderName)) {
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
        if(StringUtils.isEmpty(classTypeCode)){
            if(StringUtils.isEmpty(searchValue)){
                articlePage = findAll(pageable);
            }else{
                articlePage = articleRepository.findAllByTitleLike(searchValue, pageable);
            }
        }else{
            if(StringUtils.isEmpty(searchValue)){
                articlePage = findAllByClassTypeCode(classTypeCode, pageable);
            }else{
                articlePage = articleRepository.findAllByClassTypeCodeAndTitleLike(classTypeCode,searchValue, pageable);
            }

        }
        return articlePage;
    }

    /**
     * 根据ID显示vo(blog调用)
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public ArticleVO show(String id) throws UnsupportedEncodingException {
        if(!Util.checkShortId(id)){
            log.error("【文章详情】参数不正确，id={}",id);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        Article article = getById(id);
        if(article==null){
            log.error("【文章详情】文章不存在，id={}",id);
            throw new SystemException(ResultEnum.ARTICLE_NOT_EXIST);
        }
        article.setReadNum(article.getReadNum()+1);
        save(article);

        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);

        List<ClassType> classTypeList = classTypeService.findAll();
        List<ClassTypeVO> classTypeVOList = new ArrayList<ClassTypeVO>();
        for (ClassType classType : classTypeList){
            ClassTypeVO classTypeVO = ClassTypeConvert.classType2classTypeVO(classType);
            classTypeVOList.add(classTypeVO);
            if(articleVO.getClassTypeCode().equals(classType.getCode())){
                articleVO.setClassTypeVO(classTypeVO);
                break;
            }
        }
        return articleVO;
    }

    /**
     * 根据id返回vo（管理员调用）
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public ArticleVO view(String id) throws UnsupportedEncodingException {
        if(!Util.checkShortId(id)){
            log.error("【文章详情】参数不正确，id={}",id);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        Article article = getById(id);
        if(article==null){
            log.error("【文章详情】文章不存在，id={}",id);
            throw new SystemException(ResultEnum.ARTICLE_NOT_EXIST);
        }
        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);
        return articleVO;
    }

    /**
     * 文章点赞
     * @param id
     * @param isLike
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public ArticleVO like(String id, String isLike) throws UnsupportedEncodingException {
        if(null==isLike || "".equals(isLike) || !Util.checkShortId(id)){
            log.error("【点赞文章】参数不正确，id={},isAgree={}",id,isLike);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        Article article = getById(id);
        if(article==null){
            log.error("【点赞文章】文章不存在，id={}",id);
            throw new SystemException(ResultEnum.ARTICLE_NOT_EXIST);
        }
        int likeNum = article.getLikeNum();
        if("true".equals(isLike)){
            likeNum +=1;
        }else{
            likeNum -=1;
        }
        if(likeNum<0)likeNum = 0;
        article.setLikeNum(likeNum);
        save(article);
        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);
        return articleVO;
    }

    /**
     * 创建文章
     * @param articleForm
     * @param bindingResult
     * @return
     */
    @Override
    public ArticleVO create(ArticleForm articleForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.error("【创建文章】参数不正确，ArticleForm={}",articleForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //创建文章实例
        Article article = ArticleConvert.articleFrom2Article(articleForm);
        save(article);

        //创建返回对象
        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);

        //获取分类返回对象
        ClassType classType = classTypeService.findByCode(article.getClassTypeCode());
        if(classType!=null){
            articleVO.setClassTypeVO(
                    ClassTypeConvert.classType2classTypeVO(classType));
        }
        return articleVO;
    }

    /**
     * 更新文章
     * @param articleForm
     * @param bindingResult
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public ArticleVO update(ArticleForm articleForm, BindingResult bindingResult) throws UnsupportedEncodingException {

        if(bindingResult.hasErrors()){
            log.error("【更新文章】参数不正确，ArticleForm={}",articleForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        Article article = getById(articleForm.getId());
        if(article==null){
            log.error("【更新文章】文章不存在，ArticleForm={}",articleForm);
            throw new SystemException(ResultEnum.ARTICLE_NOT_EXIST);
        }
        BeanUtils.copyProperties(articleForm,article);
        save(article);
        //获取分类返回对象
        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);

        ClassType classType = classTypeService.findByCode(article.getClassTypeCode());
        if(classType!=null){
            articleVO.setClassTypeVO(
                    ClassTypeConvert.classType2classTypeVO(classType));
        }
        return articleVO;
    }

    @Override
    public ArticleVO save(ArticleForm articleForm, BindingResult bindingResult) throws IOException {
        ArticleVO articleVO = null;
        if(StringUtils.isEmpty(articleForm.getId())){
            articleVO = create(articleForm,bindingResult);
        }else{
            articleVO = update(articleForm,bindingResult);
        }
        return articleVO;
    }

    /**
     * 删除文章
     * @param ids
     */
    @Override
    public void delete(String[] ids) {
        if(ids==null){
            log.error("【删除分类】参数不正确，ids={}",ids);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        List<Article> articleList = new ArrayList<>();
        for(String id : ids){
            Article article = new Article();
            article.setId(id);
            articleList.add(article);

            commentService.deleteByArticleId(article.getId());
        }
        delete(articleList);
    }

    /**
     * 获取统计数据
     * @return
     */
    @Override
    public Map<String,Object> getStatisticalData(){
        Map map = new HashMap();
        //总的文章数
        map.put("countArticle",articleRepository.count());
        //总的阅读数
        map.put("totalReadNumArticle",articleRepository.totalReadNum());
        //总的点赞数
        map.put("totalLikeNumArticle",articleRepository.totalLikeNum());
        //最多阅读数的文章
        map.put("maxReadNumArticle",articleRepository.getArticleForMaxReadNum());
        //最多点赞数的文章
        map.put("maxLikeNumArticle",articleRepository.getArticleForMaxLikeNum());
        //评论数最多的文章
        map.put("maxCommentNumArticle",articleRepository.getArticleForMaxCommentNum());
        return map;
    }


}

package com.acefet.blog.controller;

import com.acefet.blog.convert.ArticleConvert;
import com.acefet.blog.convert.ClassTypeConvert;
import com.acefet.blog.entity.Article;
import com.acefet.blog.entity.ClassType;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.UserException;
import com.acefet.blog.form.ArticleForm;
import com.acefet.blog.service.ArticleService;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.service.CommentService;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.ArticleVO;
import com.acefet.blog.vo.ClassTypeVO;
import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController {

    final static int PAGE_SIZE = 10;
    final static int DEFAULT_PAGE = 0;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ClassTypeService classTypeService;

    @Autowired
    private CommentService commentService;


    /**
     * 查询文章列表
     * @param classTypeCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping({"/list/{classTypeCode}/{pageSize}/{pageNum}",
            "/list/type={classTypeCode}&pagesize={pageSize}&pagenum={pageNum}"})
    public ResultVO<Page> list(@PathVariable String classTypeCode,
                     @PathVariable String pageNum,
                     @PathVariable String pageSize) throws UnsupportedEncodingException {
        int size = PAGE_SIZE, page = DEFAULT_PAGE;
        if(pageNum==null || "".equals(pageNum.trim()))pageNum="1";
        if(pageSize!=null && Util.isNumeric(pageSize))size = Integer.parseInt(pageSize);
        if(!Util.isNumeric(pageNum)){
            log.error("【查看文章】参数不正确，pageNum={}",pageNum);
            throw new UserException(ResultEnum.PARAM_ERROR);
        }
        page = Integer.parseInt(pageNum)-1;

        Page<Article> articlePage = null;
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(Sort.Order.desc("sort"));
        orderList.add(Sort.Order.desc("releaseTime"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderList));
        if(classTypeCode==null || "all".equals(classTypeCode)){
            articlePage = articleService.findAll(pageable);
        }else{
            articlePage = articleService.findAllByClassTypeCode(classTypeCode, pageable);
        }
        return ResultVOUtil.success(articlePage);
    }

    /**
     * 显示文章
     * @param id
     * @return
     */
    @GetMapping("/p/{id}")
    public ResultVO<ArticleVO> show(@PathVariable String id) throws UnsupportedEncodingException {

        ArticleVO articleVO = getArticleVO(id);

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
        return ResultVOUtil.success(articleVO);
    }

    /**
     * 显示文章
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public ResultVO<ArticleVO> view(@PathVariable String id) throws UnsupportedEncodingException {
        return ResultVOUtil.success(getArticleVO(id));
    }

    /**
     * 获取文章对象
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    public ArticleVO getArticleVO(String id) throws UnsupportedEncodingException {
        if(!Util.checkShortId(id)){
            log.error("【文章详情】参数不正确，id={}",id);
            throw new UserException(ResultEnum.PARAM_ERROR);
        }
        Article article = articleService.getById(id);
        if(article==null){
            log.error("【文章详情】文章不存在，id={}",id);
            throw new UserException(ResultEnum.ARTICLE_NOT_EXIST);
        }
        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);
        return articleVO;
    }

    /**
     * 创建文章
     * @param articleForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<ArticleVO> create(@Valid ArticleForm articleForm,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建文章】参数不正确，ArticleForm={}",articleForm);
            throw new UserException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //创建文章实例
        Article article = ArticleConvert.articleFrom2Article(articleForm);
        articleService.save(article);

        //创建返回对象
        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);

        //获取分类返回对象
        ClassType classType = classTypeService.findByCode(article.getClassTypeCode());
        if(classType!=null){
            articleVO.setClassTypeVO(
                    ClassTypeConvert.classType2classTypeVO(classType));
        }
        return ResultVOUtil.success(articleVO);
    }

    /**
     * 更新文章
     * @param articleForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    public ResultVO<ArticleVO> update(@Valid ArticleForm articleForm,
                                      BindingResult bindingResult) throws UnsupportedEncodingException {
        if(bindingResult.hasErrors()){
            log.error("【更新文章】参数不正确，ArticleForm={}",articleForm);
            throw new UserException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        Article article = articleService.getById(articleForm.getId());
        if(article==null){
            log.error("【更新文章】文章不存在，ArticleForm={}",articleForm);
            throw new UserException(ResultEnum.ARTICLE_NOT_EXIST);
        }
        BeanUtils.copyProperties(articleForm,article);
        articleService.save(article);
        //获取分类返回对象
        ArticleVO articleVO = ArticleConvert.article2ArticleVO(article);

        ClassType classType = classTypeService.findByCode(article.getClassTypeCode());
        if(classType!=null){
            articleVO.setClassTypeVO(
                    ClassTypeConvert.classType2classTypeVO(classType));
        }
        return ResultVOUtil.success(articleVO);
    }

    /**
     * 删除文章
     * @param reqMap
     * @return
     */
    @PostMapping("/delete")
    public ResultVO delete(@RequestParam Map<String,Object> reqMap){
        String idsStr = (String)reqMap.get("ids");
        if(idsStr==null || "".equals(idsStr)){
            log.error("【删除分类】参数不正确，ids={}",idsStr);
            throw new UserException(ResultEnum.PARAM_ERROR);
        }
        List<Article> articleList = new ArrayList<>();
        String[] ids = idsStr.split(",");
        for(String id : ids){
            Article article = new Article();
            article.setId(id);
            articleList.add(article);

            commentService.deleteByArticleId(article.getId());
        }
        articleService.delete(articleList);

        return ResultVOUtil.success();
    }
}

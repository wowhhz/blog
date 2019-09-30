package com.acefet.blog.controller;

import com.acefet.blog.entity.Article;
import com.acefet.blog.form.ArticleForm;
import com.acefet.blog.service.ArticleService;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.vo.ArticleVO;
import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController {

//    @Autowired
//    private ArticleService articleService;
//
//    /**
//     * 查询文章列表
//     * @param classTypeCode
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @GetMapping(
//            "/list/type={classTypeCode}&pagesize={pageSize}" +
//                    "&pagenum={pageNum}&ordertype={orderType}" +
//                    "&orderName={orderName}&searchValue={searchValue}")
//    public ResultVO<Page> query(@PathVariable String classTypeCode,
//                                @PathVariable String searchValue,
//                     @PathVariable String orderType,
//                     @PathVariable String orderName,
//                     @PathVariable String pageNum,
//                     @PathVariable String pageSize) throws UnsupportedEncodingException {
//        Page<Article> articlePage = articleService.query(
//                classTypeCode,searchValue,orderType,orderName,pageNum,pageSize);
//        return ResultVOUtil.success(articlePage);
//    }
//
//    /**
//     * 显示文章
//     * @param id
//     * @return
//     */
//    @GetMapping("/p/{id}")
//    public ResultVO<ArticleVO> show(@PathVariable String id) throws UnsupportedEncodingException {
//        ArticleVO articleVO = articleService.show(id);
//        return ResultVOUtil.success(articleVO);
//    }
//
//    /**
//     * 显示文章
//     * @param id
//     * @return
//     */
//    @GetMapping("/view/{id}")
//    public ResultVO<ArticleVO> view(@PathVariable String id) throws UnsupportedEncodingException {
//        ArticleVO articleVO = articleService.view(id);
//        return ResultVOUtil.success(articleVO);
//    }
//
//
//    /**
//     * 对文章点赞
//     * @param id
//     * @param isLike
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    @GetMapping("/like/{id}/{isLike}")
//    public ResultVO<ArticleVO> like(@PathVariable String id,
//                                     @PathVariable String isLike) throws UnsupportedEncodingException {
//        ArticleVO articleVO = articleService.like(id,isLike);
//        return ResultVOUtil.success(articleVO);
//    }
//
//    /**
//     * 创建文章
//     * @param articleForm
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("/create")
//    public ResultVO<ArticleVO> create(@Valid ArticleForm articleForm,
//                       BindingResult bindingResult){
//        ArticleVO articleVO = articleService.create(articleForm,bindingResult);
//        return ResultVOUtil.success(articleVO);
//    }
//
//    /**
//     * 更新文章
//     * @param articleForm
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("/update")
//    public ResultVO<ArticleVO> update(@Valid ArticleForm articleForm,
//                                      BindingResult bindingResult) throws UnsupportedEncodingException {
//        ArticleVO articleVO = articleService.update(articleForm,bindingResult);
//        return ResultVOUtil.success(articleVO);
//    }
//
//    /**
//     * 删除文章
//     * @param ids
//     * @return
//     */
//    @PostMapping("/delete")
//    public ResultVO delete(@RequestParam String[] ids){
//        articleService.delete(ids);
//        return ResultVOUtil.success();
//    }
}

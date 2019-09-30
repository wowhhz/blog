package com.acefet.blog.controller;

import com.acefet.blog.entity.Article;
import com.acefet.blog.form.ArticleForm;
import com.acefet.blog.service.ArticleService;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.datatables.DataTableQueryParameter;
import com.acefet.blog.util.datatables.DataTableReturnObject;
import com.acefet.blog.vo.ArticleVO;
import com.acefet.blog.vo.ClassTypeVO;
import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/article/")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ClassTypeService classTypeService;

    @GetMapping("list")
    public void list(HttpServletRequest request){
//        init param
        List hasCommentList = new ArrayList();
        String[] hasCommentStrs = "true 是|false 否".split("[|]");
        for(String str : hasCommentStrs){
            Map map = new HashMap();
            String[] s = str.split(" ");
            map.put("code",s[0]);
            map.put("name",s[1]);
            hasCommentList.add(map);
        }
        request.setAttribute("hasCommentList",hasCommentList);

        List classTypeCodeList = new ArrayList();
        List<ClassTypeVO> classTypeVOList = classTypeService.query();
        for (ClassTypeVO classTypeVO: classTypeVOList) {
            Map map = new HashMap();
            map.put("code",classTypeVO.getCode());
            map.put("name",classTypeVO.getName());
            classTypeCodeList.add(map);
        }
        request.setAttribute("classTypeCodeList",classTypeCodeList);
    }

    @PostMapping(value = "query", produces = "application/json")
    @ResponseBody
    public Object query(@RequestParam Map<String,String> mapParam) throws UnsupportedEncodingException {
        DataTableQueryParameter parameter = new DataTableQueryParameter(mapParam);
        String classType = mapParam.get("classType");

        String orderType = "desc",orderName = "";
        if(parameter.getOrders().size()>0){
            orderType = parameter.getOrders().get(0).getDir();
            orderName = parameter.getOrders().get(0).getOrderName();
        }
        Page<Article> articleVOPage =
        	articleService.query(classType,
        				"%"+parameter.getSearch().getValue()+"%",
                orderType,orderName,
                (parameter.getPage().getCurrentPage()+1)+"",
                parameter.getPage().getPageSize()+"");

        DataTableReturnObject returnObject = new DataTableReturnObject(articleVOPage,parameter);
        return returnObject.toMap();
    }

    @RequestMapping(value = "queryForSearch")
    @ResponseBody
    public Object queryForSearch(@RequestParam Map<String,String> mapParam) throws UnsupportedEncodingException {

        String orderType = "desc",orderName = "";

        String articleTitle = mapParam.get("term");

        Page<Article> articleVOPage =
                articleService.query("",
                        "%"+articleTitle+"%",
                        orderType, orderName,
                        "1",
                        "10");
        List<Article> articleList = articleVOPage.getContent();
        List<Map> list = new ArrayList();
        for (Article article:articleList) {
            Map map = new HashMap();
            map.put("label", article.getTitle());
            map.put("value", article.getId());
            list.add(map);
        }
        return list;
    }

    @GetMapping("edit")
    @ResponseBody
    public ResultVO edit(String id) throws UnsupportedEncodingException {
        ArticleVO articleVO = articleService.view(id);
        return ResultVOUtil.success(articleVO);
    }

    @GetMapping("view")
    @ResponseBody
    public ResultVO view(String id) throws UnsupportedEncodingException {
        return edit(id);
    }

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVO save(HttpServletRequest request,
                         @Valid ArticleForm articleForm,
                         BindingResult bindingResult) throws IOException {
        ArticleVO articleVO = articleService.save(articleForm,bindingResult);
        return ResultVOUtil.success(articleVO);
    }

    @PostMapping("del")
    @ResponseBody
    public ResultVO del(@RequestBody String[] ids){
        articleService.delete(ids);
        return ResultVOUtil.success();
    }


}

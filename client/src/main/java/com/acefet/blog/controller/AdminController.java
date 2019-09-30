package com.acefet.blog.controller;

import com.acefet.blog.constant.GlobalParameter;
import com.acefet.blog.entity.Article;
import com.acefet.blog.service.ArticleService;
import com.acefet.blog.service.CommentService;
import com.acefet.blog.service.OSInfoService;
import com.acefet.blog.util.HtmlUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.vo.CommentVO;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {


    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OSInfoService osInfoService;

    public String getAdminPath(String filename){
        return GlobalParameter.ADMIN_PATH_PRE+filename;
    }


    @GetMapping({"/",""})
    public String index(HttpServletRequest request,HttpSession session){
        UserVO userVO = (UserVO)session.getAttribute("user");
        request.setAttribute("user",userVO);
        return getAdminPathPre()+"index";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) throws UnsupportedEncodingException {
        Map map = new HashMap();
        map.putAll(articleService.getStatisticalData());
        map.putAll(commentService.getStatisticalData());
        request.setAttribute("data",map);

        //点赞数最多的前6篇文章
        Page<Article> articleVOPage =
                articleService.query("",
                        "",
                        "desc",
                        "likeNum",
                        "1",
                        "6");
        List<Article> articleList = articleVOPage.getContent();
        if(articleList==null)articleList = new ArrayList<>();
        request.setAttribute("topArticles",articleList);

        //点赞数最多的前6条评论
        Map queryMap = new HashMap();
        queryMap.put("orderType","desc");
        queryMap.put("orderName","likeNum");
        queryMap.put("pageSize","6");
        queryMap.put("pageNum","1");

        Page<CommentVO> commentVOPage = commentService.query(queryMap);
        List<CommentVO> commentVOList = commentVOPage.getContent();
        if(commentVOList==null)commentVOList = new ArrayList<>();
        for (CommentVO commentVO : commentVOList) {
            commentVO.setContent(HtmlUtil.getTextFromHtml(commentVO.getContent()));
        }
        request.setAttribute("topComments",commentVOList);


        return getAdminPathPre()+"/dashboard/dashboard";
    }

    @GetMapping("/getOSInfo")
    @ResponseBody
    public ResultVO getOSInfo(){
        return ResultVOUtil.success(osInfoService.getOSInfo());
    }




    public String getAdminPathPre(){
        return GlobalParameter.ADMIN_PATH_PRE;
    }

    public String getClassTypePathPre(){
        return GlobalParameter.CLASS_TYPE_PATH_PRE;
    }
}

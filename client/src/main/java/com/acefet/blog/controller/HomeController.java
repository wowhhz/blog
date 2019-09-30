package com.acefet.blog.controller;

import com.acefet.blog.constant.GlobalParameter;
import com.acefet.blog.entity.Article;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.form.CommentForm;
import com.acefet.blog.service.ArticleService;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.service.CommentService;
import com.acefet.blog.service.UserService;
import com.acefet.blog.util.HtmlUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ClassTypeService classTypeService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    private static String COOKIE_USER_NAME = "cookie_username";
    private static String COOKIE_USER_EMAIL = "cookie_email";
    private static String COOKIE_USER_AVATAR = "cookie_avatar";
    private static String COOKIE_USER_SITE = "cookie_site";

    private static Integer START_CONTENT_LENGTH = 200;
    private static Integer SEARCH_CONTENT_LENGTH = 80;

    public String getBlogPath(String filename){
        return GlobalParameter.BLOG_PATH_PRE+filename;
    }

    public Map getCookieUserInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Map cookieInfo = new HashMap();
        if(cookies==null)return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(COOKIE_USER_NAME)){
                cookieInfo.put(COOKIE_USER_NAME,cookie.getValue());
            }
            if(cookie.getName().equals(COOKIE_USER_EMAIL)){
                cookieInfo.put(COOKIE_USER_EMAIL,cookie.getValue());
            }
            if(cookie.getName().equals(COOKIE_USER_AVATAR)){
                cookieInfo.put(COOKIE_USER_AVATAR,cookie.getValue());
            }
            if(cookie.getName().equals(COOKIE_USER_SITE)){
                cookieInfo.put(COOKIE_USER_SITE,cookie.getValue());
            }
        }
        return cookieInfo;
    }

    public void getBasicInfo(HttpServletRequest request){
        List<ClassTypeVO> classTypeVOList = classTypeService.query();
        request.setAttribute("classTypeList",classTypeVOList);
        Map<String,ClassTypeVO> classTypeVOMap = new HashMap();
        for(ClassTypeVO classTypeVO : classTypeVOList){
            classTypeVOMap.put(classTypeVO.getCode(),classTypeVO);
        }
        request.setAttribute("classTypeMap",classTypeVOMap);

        Map userInfoMap = getCookieUserInfo(request);
        if(userInfoMap!=null){
            request.setAttribute("cookieUserInfo",userInfoMap);
        }

    }

    public static void writeCookie(HttpServletResponse response, String cookieName,String value){
        Cookie cookie = new Cookie(cookieName,value);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*365);
        response.addCookie(cookie);
    }


    /**
     * 截取文章前部分内容，如果能截至整句就截至整句
     * @param content
     * @return
     */
    public static String getStartContent(String content){
        if(StringUtils.isEmpty(content))return content;
        String _content = HtmlUtil.getTextFromHtml(content);
         if(_content.length()<=START_CONTENT_LENGTH)return _content;
        _content = _content.substring(0, START_CONTENT_LENGTH);
        Integer index  = _content.lastIndexOf("。");
        return (index>=(START_CONTENT_LENGTH/2)) ? _content.substring(0, index+1) : _content;
    }

    /**
     * 截取文章内的首张图片路径
     * @param content
     * @return
     */
    public static String getStartImage(String content){
        if(StringUtils.isEmpty(content))return null;
        Integer index = content.indexOf("<img");
        if(index==-1)return null;
        String src = content.substring(index);
        src = src.substring(src.indexOf("src=")+4);
        src = src.substring(0,src.indexOf(" "))
                .replace("'","").replace("\"","");
        return src;
    }

    /**
     * 截取文章内最先被引用的内容，优先引用标记q，其次是blockquote
     * @param content
     * @return
     */
    public static String getStartBlockquote(String content){
        if(StringUtils.isEmpty(content))return null;
        Integer index1 = content.indexOf("<q>");
        Integer index2 = content.indexOf("<blockquote>");
        if(index1==-1 && index2==-1)return null;
        Integer startIndexLen = START_CONTENT_LENGTH/2,endIndex = 0;
        String blockquote;
        if(index1>-1 && index1<startIndexLen){
            blockquote = content.substring(index1);
            endIndex = blockquote.indexOf("</q>");
            return blockquote.substring(0,endIndex>START_CONTENT_LENGTH ? START_CONTENT_LENGTH: endIndex);
        }
        if(index2>-1 && index2<startIndexLen){
            blockquote = content.substring(index2);
            endIndex = content.indexOf("</blockquote>");
            return blockquote.substring(0,endIndex>START_CONTENT_LENGTH ? START_CONTENT_LENGTH: endIndex);
        }
        return null;
    }

    /**
     * 返回搜索的内容部分
     * @param content
     * @param q
     * @return
     */
    public static String getSearchContent(String content,String q){
        if(StringUtils.isEmpty(content))return null;
        String _content = HtmlUtil.getTextFromHtml(content);
        String[] strs = q.split("\\s+");
        Integer index = 0,startIndex = -1,endIndex = -1;
        Boolean hasEndFlag = false;
        for (String str : strs){
            index = _content.indexOf(str);
            if(index==-1)continue;
            if(startIndex==-1){
                hasEndFlag = _content.length()<=index+SEARCH_CONTENT_LENGTH;
                _content = _content.substring(index<=SEARCH_CONTENT_LENGTH ? 0 : index-SEARCH_CONTENT_LENGTH,
                        hasEndFlag ? _content.length() : index+SEARCH_CONTENT_LENGTH);
                startIndex = _content.indexOf("。");
                if(startIndex==-1)startIndex = _content.indexOf("，");
                if(startIndex==-1)startIndex = _content.indexOf(".");
                if(startIndex==-1)startIndex = _content.indexOf(",");
                if(startIndex>-1)startIndex+=1;
                if(startIndex==-1)startIndex = index<=(SEARCH_CONTENT_LENGTH/2) ? 0 : index-(SEARCH_CONTENT_LENGTH/2);
                endIndex = _content.length()<=index+(SEARCH_CONTENT_LENGTH/2) ? _content.length() : index+(SEARCH_CONTENT_LENGTH/2);
                if(_content.length()>endIndex){
                    if(index<=startIndex){
                        _content = _content.substring(0,_content.length()>endIndex ? SEARCH_CONTENT_LENGTH : _content.length());
                    }else{
                        _content = _content.substring(index-startIndex>SEARCH_CONTENT_LENGTH/2 ? index-SEARCH_CONTENT_LENGTH/2 : startIndex,endIndex);
                    }
                    Integer _endIndex = _content.lastIndexOf("。");
                    if(_endIndex==-1)_endIndex = _content.lastIndexOf(".");
                    if(_content.indexOf(str)<_endIndex){
                        endIndex = _endIndex;
                        _content = _content.substring(0,endIndex+1);
                    }
                }else if(_content.indexOf(str)>startIndex){
                    _content = _content.substring(startIndex);
                }
            }
            _content = _content.replace(str,"<span class=\"text-red\">"+str+"</span>");
                if(!hasEndFlag) _content = _content+"...";
        }
        return _content;
    }

    /**
     * 获取点赞数最多的文章
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public List<ArticleVO> getTopArticles(HttpServletRequest request) throws UnsupportedEncodingException {
        //点赞数最多的前10篇文章
        Map map = new HashMap();
        map.put("orderType","desc");
        map.put("orderName","likeNum");
        map.put("pageSize",com.acefet.blog.util.Page.PAGESIZE_DEFAULT+"");
        map.put("pageNum","1");
        Page<ArticleVO> articleVOPage =
                articleService.query(map);
        List<ArticleVO> topArticleList = articleVOPage.getContent();
        if(topArticleList==null)topArticleList = new ArrayList<>();
        request.setAttribute("topArticles",topArticleList);
        return topArticleList;
    }

    @GetMapping({"/","/index"})
    public String home(HttpServletRequest request) throws UnsupportedEncodingException {
        getBasicInfo(request);
        getTopArticles(request);
        String pageNum = request.getParameter("pageNum");
        Map map = new HashMap();
        map.put("orderType","desc");
        map.put("orderName","releaseTime");
        map.put("pageSize","5");
        map.put("pageNum",pageNum);
        Page<ArticleVO> articleVOPage =articleService.query(map);
        //获取一些内容简要信息
        List<ArticleVO> articleVOList = articleVOPage.getContent();
        Map simpleRemarkMap = new HashMap();
        for(ArticleVO articleVO : articleVOList){
            String startContent = getStartContent(articleVO.getContent());
            //获取图片
            String startImageSrc = getStartImage(articleVO.getContent());
            //获取引言
            String startBlockquote = getStartBlockquote(articleVO.getContent());

            Map articleInfoMap = new HashMap();
            if(startContent!=null)articleInfoMap.put("startContent",startContent);
            if(startImageSrc!=null)articleInfoMap.put("startImageSrc",startImageSrc);
            if(startBlockquote!=null)articleInfoMap.put("startBlockquote",startBlockquote);
            simpleRemarkMap.put(articleVO.getId(),articleInfoMap);
        }
        request.setAttribute("simpleRemarkMap",simpleRemarkMap);
        Map classTypeMap = (Map)request.getAttribute("classTypeMap");
        request.setAttribute("articleList",articleVOPage);
        request.setAttribute("pageNum",pageNum);
        return getBlogPath("index");
    }

    @GetMapping("/list")
    public String list(HttpServletRequest request) throws UnsupportedEncodingException {
        getBasicInfo(request);
        getTopArticles(request);
        String classType = request.getParameter("classType");
        String flag = request.getParameter("flag");
        String pageNum = request.getParameter("pageNum");

        Map map = new HashMap();
        map.put("searchValue","");
        map.put("classTypeCode",classType);
        map.put("flag",flag);
        map.put("orderType","desc");
        map.put("orderName","releaseTime");
        map.put("pageSize",com.acefet.blog.util.Page.PAGESIZE_DEFAULT+"");
        map.put("pageNum",pageNum);
        Page<ArticleVO> articleVOPage =articleService.query(map);
        //获取一些内容简要信息
        List<ArticleVO> articleVOList = articleVOPage.getContent();
        Map simpleRemarkMap = new HashMap();
        for(ArticleVO articleVO : articleVOList){
            String startContent = getStartContent(articleVO.getContent());
            //获取图片
            String startImageSrc = getStartImage(articleVO.getContent());
            //获取引言
            String startBlockquote = getStartBlockquote(articleVO.getContent());

            Map articleInfoMap = new HashMap();
            if(startContent!=null)articleInfoMap.put("startContent",startContent);
            if(startImageSrc!=null)articleInfoMap.put("startImageSrc",startImageSrc);
            if(startBlockquote!=null)articleInfoMap.put("startBlockquote",startBlockquote);
            simpleRemarkMap.put(articleVO.getId(),articleInfoMap);
        }
        request.setAttribute("simpleRemarkMap",simpleRemarkMap);

        Map classTypeMap = (Map)request.getAttribute("classTypeMap");
        request.setAttribute("articleList",articleVOPage);
        request.setAttribute("classType",classTypeMap.get(classType));
        request.setAttribute("flag",flag);
        request.setAttribute("pageNum",pageNum);

        return getBlogPath("list");
    }

    @GetMapping("/search")
    public String search(HttpServletRequest request) throws UnsupportedEncodingException {
        getBasicInfo(request);
        String q = request.getParameter("q");
        String pageNum = request.getParameter("pageNum");
        String type = request.getParameter("type");
        if(!StringUtils.isEmpty(q)){
            //替换掉特殊字符
            q = q.replaceAll("[\\pP|~|$|^|<|>|\'|\"|\\||\\+|=]*", "");
            Map map = new HashMap();
            map.put("searchValue",q);
            map.put("orderType","desc");
            map.put("orderName","releaseTime");
            map.put("pageSize",com.acefet.blog.util.Page.PAGESIZE_DEFAULT+"");
            map.put("pageNum",pageNum);
            Page<ArticleVO> articleVOPage =articleService.query(map);
            request.setAttribute("articleList",articleVOPage);

            List<ArticleVO> articleVOList = articleVOPage.getContent();
            Map searchMap = new HashMap();
            for(ArticleVO articleVO : articleVOList){
                Map searchContentMap = new HashMap();
                searchContentMap.put("title",getSearchContent(articleVO.getTitle(),q));
                searchContentMap.put("content",getSearchContent(articleVO.getContent(),q));
                searchMap.put(articleVO.getId(),searchContentMap);
            }
            request.setAttribute("searchMap",searchMap);
        }
        request.setAttribute("q",q);
        request.setAttribute("type",type);
        request.setAttribute("pageNum",pageNum);
        return getBlogPath("search");
    }

    @GetMapping("/article/{id}")
    public String article(HttpServletRequest request, @PathVariable String id) throws UnsupportedEncodingException {
        getBasicInfo(request);
        getTopArticles(request);
        ArticleVO articleVO = articleService.show(id);
        request.setAttribute("article",articleVO);
        return getBlogPath("article");
    }

    @GetMapping("/comment/list")
    @ResponseBody
    public ResultVO getComments(String articleId,String pageNum){
        Map map = new HashMap();
        map.put("articleId",articleId);
        map.put("searchValue","");
        map.put("orderType","asc");
        map.put("orderName","releaseTime");
        map.put("hasCheck","true");
        map.put("pageSize",com.acefet.blog.util.Page.PAGESIZE_DEFAULT+"");
        map.put("pageNum",pageNum);

        Page<CommentVO> commentVOPage = commentService.query(map);

        return ResultVOUtil.success(commentVOPage);
    }

    @PostMapping(value = "/comment/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVO save(HttpServletRequest request,
                         @Valid CommentForm commentForm,
                         BindingResult bindingResult,
                         HttpServletResponse response) throws IOException {
        CommentVO commentVO = commentService.save(commentForm,bindingResult);

        writeCookie(response,COOKIE_USER_NAME, commentForm.getName());
        writeCookie(response,COOKIE_USER_EMAIL,commentForm.getEmail());
        writeCookie(response,COOKIE_USER_SITE,commentForm.getSite());
        writeCookie(response,COOKIE_USER_AVATAR,commentForm.getIcon());

        return ResultVOUtil.success(commentVO);
    }


    @GetMapping("/about")
    public String about(HttpServletRequest request) throws UnsupportedEncodingException {
        getBasicInfo(request);
        getTopArticles(request);
        return getBlogPath("about_me");
    }

    @GetMapping("/contact")
    public String contact(HttpServletRequest request) throws UnsupportedEncodingException {
        getBasicInfo(request);
        getTopArticles(request);
        return getBlogPath("contact_us");
    }


    public String getAdminPath(String filename){
        return GlobalParameter.ADMIN_PATH_PRE+filename;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        String url = request.getParameter("url");
        if(null==url)url="";
        request.setAttribute("url",url);
        return getAdminPath("login");
    }

    @RequestMapping("/checklogin")
    public String checkLogin(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
        UserVO userVO = null;
        String error = null;
        String username = (String)request.getParameter("username");
        String url = request.getParameter("url");
        if(url==null)url="";
        try{
            userVO = userService.login(request, session);
        }catch (RuntimeException e){
            error = e.getMessage();
        }
        session.setAttribute("user",userVO);
        if(error!=null || userVO==null){
            request.setAttribute("error",error);
            request.setAttribute("url",url);
            return login(request);
        }else if(!StringUtils.isEmpty(url)) {
            return "redirect:" + url;
        }else{
            return "redirect:"+getAdminPath("");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }



    /**
     * 点赞或反对
     * @param id
     * @param type
     * @param isLike
     * @return
     */
    @GetMapping("/like")
    @ResponseBody
    public ResultVO like(String type,
                         String id,
                         String isLike) throws UnsupportedEncodingException {
        if(StringUtils.isEmpty(type) || StringUtils.isEmpty(id) || StringUtils.isEmpty(isLike)){
            return ResultVOUtil.fail(ResultEnum.PARAM_ERROR);
        }
        Map map = new HashMap();
        map.put("type",type);
        map.put("id",id);
        map.put("isLike",isLike);
        if("comment".equals(type)){
            CommentVO commentVO = commentService.like(id,isLike);
            map.put("likeNum",commentVO.getLikeNum());
            map.put("unlikeNum",commentVO.getUnlikeNum());
        }
        if("article".equals(type)){
            ArticleVO articleVO = articleService.like(id,isLike);
            map.put("likeNum",articleVO.getLikeNum());
        }
        return ResultVOUtil.success(map);
    }

}

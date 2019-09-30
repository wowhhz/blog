package com.acefet.blog.controller;

import com.acefet.blog.form.CommentForm;
import com.acefet.blog.service.CommentService;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.datatables.DataTableQueryParameter;
import com.acefet.blog.util.datatables.DataTableReturnObject;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.CommentVO;
import com.acefet.blog.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/comment/")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("list")
    public void list(HttpServletRequest request){

        String hasCheck = request.getParameter("hasCheck");
        if(hasCheck==null)hasCheck = "";
//        init param
		String[] hasCheckStrs = "true 已审核|false 未审核".split("[|]");
		List hasCheckList = new ArrayList();
        for(String str : hasCheckStrs){
            Map map = new HashMap();
            String[] s = str.split(" ");
            map.put("code",s[0]);
            map.put("name",s[1]);
            hasCheckList.add(map);
        }
		request.setAttribute("hasCheckList",hasCheckList);
		String[] preCheckStrs = "true 已审核|false 未审核".split("[|]");
		List preCheckList = new ArrayList();
        for(String str : preCheckStrs){
            Map map = new HashMap();
            String[] s = str.split(" ");
            map.put("code",s[0]);
            map.put("name",s[1]);
            preCheckList.add(map);
        }
		request.setAttribute("preCheckList",preCheckList);

        request.setAttribute("hasCheck",hasCheck);

        UserVO userVO = (UserVO)request.getSession().getAttribute("user");
        request.setAttribute("user",userVO);
    }

    @PostMapping(value = "query", produces = "application/json")
    @ResponseBody
    public Object query(@RequestParam Map<String,String> mapParam) {
        DataTableQueryParameter parameter = new DataTableQueryParameter(mapParam);

        String orderType = "desc",orderName = "";
        if(parameter.getOrders().size()>0){
            orderType = parameter.getOrders().get(0).getDir();
            orderName = parameter.getOrders().get(0).getOrderName();
        }

        Map map = new HashMap();
        map.put("articleId",mapParam.get("articleId"));
        map.put("searchValue",mapParam.get("searchValue"));
        map.put("orderType",orderType);
        map.put("orderName",orderName);
        map.put("hasCheck",mapParam.get("hasCheck"));
        map.put("pageSize",parameter.getPage().getPageSize()+"");
        map.put("pageNum",(parameter.getPage().getCurrentPage()+1)+"");

        Page<CommentVO> commentVOPage = commentService.query(map);

        DataTableReturnObject returnObject = new DataTableReturnObject(commentVOPage,parameter);
        return returnObject.toMap();
    }

    @GetMapping("edit")
    @ResponseBody
    public ResultVO edit(String id) {
        CommentVO commentVO = commentService.getById(id);
        return ResultVOUtil.success(commentVO);
    }

    @GetMapping("view")
    @ResponseBody
    public ResultVO view(String id) {
        return edit(id);
    }

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVO save(HttpServletRequest request,
                         @Valid CommentForm commentForm,
                         BindingResult bindingResult) throws IOException {
        CommentVO commentVO = commentService.save(commentForm,bindingResult);
        return ResultVOUtil.success(commentVO);
    }

    @PostMapping("del")
    @ResponseBody
    public ResultVO del(@RequestBody String[] ids){
        commentService.delete(ids);
        return ResultVOUtil.success();
    }

    @PostMapping("mark")
    @ResponseBody
    public ResultVO mark(@RequestBody String[] ids){
        commentService.mark(ids);
        return ResultVOUtil.success();
    }

    @GetMapping("getNotCheckCount")
    @ResponseBody
    public ResultVO getNotCheckCount(){
        Long count = commentService.getNotCheckCount();
        return ResultVOUtil.success(count);
    }

}

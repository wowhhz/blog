package com.acefet.blog.controller;

import com.acefet.blog.constant.GlobalParameter;
import com.acefet.blog.constant.enums.UserStatus;
import com.acefet.blog.entity.User;
import com.acefet.blog.entity.UserFile;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.SystemException;
import com.acefet.blog.form.UserForm;
import com.acefet.blog.service.UserService;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.datatables.DataTableQueryParameter;
import com.acefet.blog.util.datatables.DataTableReturnObject;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/user/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public void list(HttpServletRequest request) throws IOException {
//        init param
        request.setAttribute("statusList",getPara());
        request.setAttribute("urlPath",FileUtil.getUrlFilePath());
    }

    @PostMapping(value = "query", produces = "application/json")
    @ResponseBody
    public Object query(@RequestParam Map<String,String> mapParam) {
        DataTableQueryParameter parameter = new DataTableQueryParameter(mapParam);
        List<UserVO> userVOList = userService.query();
        DataTableReturnObject returnObject = new DataTableReturnObject(userVOList,parameter);
        return returnObject.toMap();
    }

    @GetMapping("edit")
    @ResponseBody
    public ResultVO edit(String id) {
        UserVO userVO = userService.findUserById(id);
        return ResultVOUtil.success(userVO);
    }

    @GetMapping("view")
    @ResponseBody
    public ResultVO view(String id) {
        return edit(id);
    }

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVO save(HttpServletRequest request,
                         @Valid UserForm userForm,
                         BindingResult bindingResult) throws IOException {
        String uptype = (String)request.getParameter("uptype");

        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        if("profile".equalsIgnoreCase(uptype)){
            if(!user.getId().equals(userForm.getId())){
                throw new SystemException(ResultEnum.PARAM_ERROR);
            }
        }
        String filePath = FileUtil.getRealFilePath();
        String urlPath = FileUtil.getUrlFilePath();
        String subPath = FileUtil.getFileSubPath();

        Map<String,String> map = new HashMap();
        map.put("filePath",filePath);
        map.put("subPath",subPath);
        map.put("groupId", User.class.getName());
        map.put("actionId",user.getId());
        map.put("userId",user.getId());
        map.put("userName",user.getUsername());

        UserVO userVO = userService.save(userForm,map,bindingResult);

        if("profile".equalsIgnoreCase(uptype)){
            request.getSession().setAttribute("user",userVO);
            request.setAttribute("user",userVO);
        }
        return ResultVOUtil.success(userVO);

    }

    @PostMapping("del")
    @ResponseBody
    public ResultVO del(@RequestBody String[] ids){
        userService.delete(ids);
        return ResultVOUtil.success();
    }

    @GetMapping("profile")
    public void profile(HttpServletRequest request) throws IOException, SystemException {
        UserVO userVO = (UserVO)request.getSession().getAttribute("user");
        if(userVO==null){
            throw new SystemException(ResultEnum.USER_SESSION_ERROR);
        }
        request.setAttribute("user",userVO);
        request.setAttribute("statusList",getPara());
        request.setAttribute("urlPath",FileUtil.getUrlFilePath());
    }

    public List<Map> getPara(){
        List<Map> statusList = new ArrayList<Map>();
        UserStatus[] statuses = UserStatus.values();
        for(UserStatus userStatus : statuses){
            Map map = new HashMap();
            map.put("code",userStatus.getStatusCode());
            map.put("name",userStatus.getStatusName());
            statusList.add(map);
        }
        return statusList;
    }

    @GetMapping("repwd")
    public void repwd(HttpServletRequest request){
        UserVO userVO = (UserVO)request.getSession().getAttribute("user");
        if(userVO==null){
            throw new SystemException(ResultEnum.USER_SESSION_ERROR);
        }
        request.setAttribute("user",userVO);
    }

    @PostMapping("rePwdSave")
    @ResponseBody
    public ResultVO rePwdSave(HttpServletRequest request){
        String uptype = (String)request.getParameter("uptype");

        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        if(!id.equals(user.getId()) || !"repwd".equals(uptype)){
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        Map<String,Object> map = new HashMap();
        map.put("id",user.getId());
        map.put("username",user.getUsername());
        map.put("password",request.getParameter("password"));
        map.put("newpassword",request.getParameter("newpassword"));
        map.put("repassword",request.getParameter("repassword"));
        userService.resetPwd(map);
        return ResultVOUtil.success();
    }


}

package com.acefet.blog.controller;

import com.acefet.blog.entity.User;
import com.acefet.blog.form.UserForm;
import com.acefet.blog.service.UserService;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

//    @Autowired
//    private UserService userService;
//
//    /**
//     * 查询用户列表
//     * @return
//     */
//    @GetMapping("/list")
//    public ResultVO<List> query(){
//        List<UserVO> userVOList = userService.query();
//        return ResultVOUtil.success(userVOList);
//    }
//
//    /**
//     * 创建用户
//     * @param userForm
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("/create")
//    public ResultVO<UserVO> create(HttpServletRequest request,
//                                   @Valid UserForm userForm,
//                                   BindingResult bindingResult) throws IOException {
//        HttpSession session = request.getSession();
//        UserVO user = (UserVO) session.getAttribute("user");
//
//        String filePath = FileUtil.getRealFilePath();
//        String urlPath = FileUtil.getUrlFilePath();
//        String subPath = FileUtil.getFileSubPath();
//
//        Map<String,String> map = new HashMap();
//        map.put("filePath",filePath);
//        map.put("subPath",subPath);
//        map.put("groupId", User.class.getName());
//        map.put("actionId","userIcon");
//        map.put("userId",user.getId());
//        map.put("userName",user.getUsername());
//        UserVO userVO = userService.create(userForm,map,bindingResult);
//        return ResultVOUtil.success(userVO);
//    }
//
//    /**
//     * 更新用户
//     * @param userForm
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("/update")
//    public ResultVO<UserVO> update(HttpServletRequest request,
//                                   @Valid UserForm userForm,
//                                   BindingResult bindingResult) throws IOException {
//        HttpSession session = request.getSession();
//        UserVO user = (UserVO) session.getAttribute("user");
//
//        String filePath = FileUtil.getRealFilePath();
//        String urlPath = FileUtil.getUrlFilePath();
//        String subPath = FileUtil.getFileSubPath();
//
//        Map<String,String> map = new HashMap();
//        map.put("filePath",filePath);
//        map.put("subPath",subPath);
//        map.put("groupId", User.class.getName());
//        map.put("actionId","userIcon");
//        map.put("userId",user.getId());
//        map.put("userName",user.getUsername());
//        UserVO userVO = userService.update(userForm,map, bindingResult);
//        return ResultVOUtil.success(userVO);
//    }
//
//    /**
//     * 重置密码
//     * @param reqMap
//     * @return
//     */
//    @PostMapping("/resetpwd")
//    public ResultVO resetpwd(@RequestParam Map<String,Object> reqMap){
//        userService.resetPwd(reqMap);
//        return ResultVOUtil.success();
//
//    }
//
//    /**
//     * 删除用户
//     * @param ids
//     * @return
//     */
//    @PostMapping("/delete")
//    public ResultVO delete(@RequestParam String[] ids){
//        userService.delete(ids);
//        return ResultVOUtil.success();
//    }
}

package com.acefet.blog.controller;

import com.acefet.blog.constant.enums.UserStatus;
import com.acefet.blog.convert.UserConvert;
import com.acefet.blog.entity.User;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.UserException;
import com.acefet.blog.form.UserForm;
import com.acefet.blog.service.UserService;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List> list(){
        List<User> userList = userService.findAll();
        List<UserVO> userVOList = new ArrayList<UserVO>();
        for (User user : userList) {
            UserVO userVO = UserConvert.user2UserVO(user);
            userVOList.add(userVO);
        }
        return ResultVOUtil.success(userVOList);
    }

    /**
     * 创建用户
     * @param userForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<UserVO> create(@Valid UserForm userForm,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建用户】参数不正确，userForm={}",userForm);
            throw new UserException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User olduser = userService.findUserByUsername(userForm.getUsername());
        if(olduser!=null){
            log.error("【创建用户】用户已存在，userForm={}",userForm);
            throw new UserException(ResultEnum.USER_EXIST);
        }
        //创建user实例并赋值
        User user = UserConvert.userForm2User(userForm);
        userService.save(user);
        //转换到viewVO返回
        UserVO userVO = UserConvert.user2UserVO(user);
        return ResultVOUtil.success(userVO);
    }

    /**
     * 更新用户
     * @param userForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    public ResultVO<UserVO> update(@Valid UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【更新用户】参数不正确，userForm={}",userForm);
            throw new UserException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
            }
        User user = userService.findUserByUsername(userForm.getUsername());
        if(user==null && user.getId()==null){
            log.error("【更新用户】用户不存在，userForm={}",userForm);
            throw new UserException(ResultEnum.USER_NOT_EXIST);
        }
        String oldpwd = user.getPassword();
        BeanUtils.copyProperties(userForm, user);
        user.setPassword(oldpwd);
        userService.save(user);
        UserVO userVO = UserConvert.user2UserVO(user);
        return ResultVOUtil.success(userVO);
    }

    /**
     * 修改密码
     * @param reqMap
     * @return
     */
    @PostMapping("/resetpwd")
    public ResultVO resetpwd(@RequestParam Map<String,Object> reqMap){
        String username = (String)reqMap.get("username");
        String password = (String)reqMap.get("password");
        String newpassword = (String)reqMap.get("newpassword");
        String repassword = (String)reqMap.get("repassword");
        if(password==null || newpassword==null || repassword==null){
            log.error("【修改密码】参数不正确，userName={}",username);
            throw new UserException(ResultEnum.PARAM_ERROR);
        }
        if(!newpassword.equals(repassword)){
            log.error("【修改密码】两次密码不一致，userName={}",username);
            throw new UserException(ResultEnum.USER_TWOPWD_ERROR);
        }
        if(newpassword.length()<3 && newpassword.length()>32){
            log.error("【修改密码】密码长度超限(3-32位)，userName={}",username);
            throw new UserException(ResultEnum.USER_PWD_LENGTH_ERROR);
        }

        User user = userService.findUserByUsername(username);
        if(user==null || user.getId()==null){
            log.error("【修改密码】用户不存在，userName={}",username);
            throw new UserException(ResultEnum.USER_NOT_EXIST);
        }
        String md5oldPassword = Util.getMD5Str(password);
        if(!md5oldPassword.equals(user.getPassword())){
            log.error("【修改密码】原密码错误，userName={}",username);
            throw new UserException(ResultEnum.USER_PWD_ERROR);
        }

        String md5Password = Util.getMD5Str(newpassword);
        user.setPassword(md5Password);
        userService.save(user);
        return ResultVOUtil.success();

    }

    /**
     * 删除用户
     * @param reqMap
     * @return
     */
    @PostMapping("/delete")
    public ResultVO delete(@RequestParam Map<String,Object> reqMap){
        String idsStr = (String)reqMap.get("ids");
        if(idsStr==null || "".equals(idsStr)){
            log.error("【删除用户】参数不正确，ids={}",idsStr);
            throw new UserException(ResultEnum.PARAM_ERROR);
        }
        String[] ids = idsStr.split(",");
        List<User> userList = new ArrayList<User>();
        for(String id : ids){
            User user = new User();
            user.setId(id);
            userList.add(user);
        }
        userService.delete(userList);
        return ResultVOUtil.success();
    }
}

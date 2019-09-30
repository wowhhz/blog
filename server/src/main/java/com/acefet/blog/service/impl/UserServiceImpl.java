package com.acefet.blog.service.impl;

import com.acefet.blog.convert.UserConvert;
import com.acefet.blog.entity.User;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.SystemException;
import com.acefet.blog.form.UserForm;
import com.acefet.blog.repository.UserRepository;
import com.acefet.blog.service.UserFileService;
import com.acefet.blog.service.UserService;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.UserFileVO;
import com.acefet.blog.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFileService userFileService;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public UserVO findUserById(String id) {
        User user = userRepository.getOne(id);
        UserVO userVO = null;
        if(user!=null){
            userVO = UserConvert.user2UserVO(user);
        }
        return userVO;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(List<User> userList) {
        userRepository.deleteInBatch(userList);
    }


//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userRepository.getByUsername(s);
//        if(user==null){
//            throw new UsernameNotFoundException("用户名不存在");
//        }
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return new org.springframework.security.core.userdetails.User
//                (user.getUsername(), user.getPassword(), grantedAuthorities);
//    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<UserVO> query() {
        List<User> userList = findAll();
        List<UserVO> userVOList = new ArrayList<UserVO>();
        for (User user : userList) {
            UserVO userVO = UserConvert.user2UserVO(user);
            userVOList.add(userVO);
        }
        return userVOList;
    }

    /**
     * 创建用户
     * @param userForm
     * @param bindingResult
     * @return
     */
    @Override
    public UserVO create(UserForm userForm, Map<String,String> map, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.error("【创建用户】参数不正确，userForm={}",userForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User olduser = findUserByUsername(userForm.getUsername());
        if(olduser!=null){
            log.error("【创建用户】用户已存在，userForm={}",userForm);
            throw new SystemException(ResultEnum.USER_EXIST);
        }
        //创建user实例并赋值
        User user = UserConvert.userForm2User(userForm);
        UserFileVO userFileVO = userFileService.save(userForm.getIcon(),map);
        if(userFileVO==null) {
            user.setIcon("");
        }else{
            user.setIcon(userFileVO.getFilePath());
        }
        save(user);
        //转换到viewVO返回
        UserVO userVO = UserConvert.user2UserVO(user);
        return userVO;
    }

    /**
     * 更新用户
     * @param userForm
     * @param bindingResult
     * @return
     */
    @Override
    public UserVO update(UserForm userForm, Map<String,String> map, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.error("【更新用户】参数不正确，userForm={}",userForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User user = findUserByUsername(userForm.getUsername());
        if(user==null && user.getId()==null){
            log.error("【更新用户】用户不存在，userForm={}",userForm);
            throw new SystemException(ResultEnum.USER_NOT_EXIST);
        }
        String oldpwd = user.getPassword();
        String userIcon = user.getIcon();
        if(userIcon==null)userIcon="";
        BeanUtils.copyProperties(userForm, user);
        if(StringUtils.isEmpty(userForm.getPassword())){
            user.setPassword(oldpwd);
        }else{
            user.setPassword(Util.getMD5Str(userForm.getPassword()));
        }
        UserFileVO userFileVO = userFileService.save(userForm.getIcon(),map);
        if(userFileVO==null) {
            user.setIcon(userIcon);
        }else{
            user.setIcon(userFileVO.getFilePath());
        }
        save(user);
        UserVO userVO = UserConvert.user2UserVO(user);
        return userVO;
    }

    @Override
    public UserVO save(UserForm userForm, Map<String,String> map, BindingResult bindingResult) throws IOException {
        UserVO userVO = null;
        if(StringUtils.isEmpty(userForm.getId())){
            userVO = create(userForm,map,bindingResult);
        }else{
            userVO = update(userForm,map,bindingResult);
        }
        return userVO;
    }

    /**
     * 重置密码
     * @param reqMap
     */
    @Override
    public void resetPwd(Map<String, Object> reqMap) {
        String username = (String)reqMap.get("username");
        String password = (String)reqMap.get("password");
        String newpassword = (String)reqMap.get("newpassword");
        String repassword = (String)reqMap.get("repassword");
        if(password==null || newpassword==null || repassword==null){
            log.error("【修改密码】参数不正确，userName={}",username);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        if(!newpassword.equals(repassword)){
            log.error("【修改密码】两次密码不一致，userName={}",username);
            throw new SystemException(ResultEnum.USER_TWOPWD_ERROR);
        }
        if(newpassword.length()<3 && newpassword.length()>32){
            log.error("【修改密码】密码长度超限(3-32位)，userName={}",username);
            throw new SystemException(ResultEnum.USER_PWD_LENGTH_ERROR);
        }

        User user = findUserByUsername(username);
        if(user==null || user.getId()==null){
            log.error("【修改密码】用户不存在，userName={}",username);
            throw new SystemException(ResultEnum.USER_NOT_EXIST);
        }
        String md5oldPassword = Util.getMD5Str(password);
        if(!md5oldPassword.equals(user.getPassword())){
            log.error("【修改密码】原密码错误，userName={}",username);
            throw new SystemException(ResultEnum.USER_OLD_PWD_ERROR);
        }

        String md5Password = Util.getMD5Str(newpassword);
        user.setPassword(md5Password);
        save(user);
    }

    /**
     * 删除用户
     * @param ids
     */
    @Override
    public void delete(String[] ids) {
        if(ids==null){
            log.error("【删除用户】参数不正确，ids={}",ids);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        List<User> userList = new ArrayList<User>();
        for(String id : ids){
            User user = new User();
            user.setId(id);
            userList.add(user);
        }
        delete(userList);
    }

    @Override
    public UserVO login(HttpServletRequest request, HttpSession session) {
        String username = (String)request.getParameter("username");
        String password = (String)request.getParameter("password");

        if(username==null || password==null
                || "".equals(username.trim()) || "".equals(password.trim())){
            return null;
        }
        User user = this.findUserByUsername(username);
        if(user==null){
            log.error("【用户登录】用户不存在，username={}",username);
            throw new SystemException(ResultEnum.USER_NOT_EXIST);
        }
        if(!"enable".equalsIgnoreCase(user.getStatus())){
            log.error("【用户登录】用户已禁用，username={},status={}",username,user.getStatus());
            throw new SystemException(ResultEnum.USER_DISABLE);
        }
        String encryptPwd = Util.getMD5Str(password);

        if(!encryptPwd.equals(user.getPassword())){
            log.error("【用户登录】密码错误，username={}",username);
            throw new SystemException(ResultEnum.USER_PWD_ERROR);
        }

        session.setAttribute("username", user.getUsername());
        UserVO userVO = UserConvert.user2UserVO(user);
        return userVO;
    }

    @Override
    public void logout(HttpSession session) {
        String username = (String)session.getAttribute("username");
        if(username!=null){
            session.removeAttribute("username");
        }
    }


}

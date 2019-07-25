package com.acefet.blog.controller;

import com.acefet.blog.convert.ClassTypeConvert;
import com.acefet.blog.entity.ClassType;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.UserException;
import com.acefet.blog.form.ClassTypeForm;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.vo.ClassTypeVO;
import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/class")
@Slf4j
public class ClassTypeController {

    @Autowired
    private ClassTypeService classTypeService;

    /**
     * 查询分类
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List> list(){
        List<ClassType> classTypeList = classTypeService.findAll();
        List<ClassTypeVO> classTypeVOList = new ArrayList();
        for(ClassType classType : classTypeList){
            ClassTypeVO classTypeVO = ClassTypeConvert.classType2classTypeVO(classType);
            classTypeVOList.add(classTypeVO);
        }
        return ResultVOUtil.success(classTypeVOList);
    }

    /**
     * 创建分类
     * @param classTypeForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<ClassTypeVO> create(@Valid ClassTypeForm classTypeForm,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建分类】参数不正确，ClassTypeForm={}",classTypeForm);
            throw new UserException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        ClassType oldClassType = classTypeService.findByCode(classTypeForm.getCode());
        if(oldClassType!=null){
            log.error("【创建分类】类别已存在，ClassTypeForm={}",classTypeForm);
            throw new UserException(ResultEnum.CLASSTYPE_EXIST);
        }
        //创建分类对象
        ClassType classType = ClassTypeConvert.classTypeForm2classType(classTypeForm);
        classTypeService.save(classType);

        //创建返回对象
        ClassTypeVO classTypeVO = ClassTypeConvert.classType2classTypeVO(classType);
        return ResultVOUtil.success(classTypeVO);
    }

    /**
     * 更新分类
     * @param classTypeForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    public ResultVO<ClassTypeVO> update(@Valid ClassTypeForm classTypeForm,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【更新分类】参数不正确，ClassTypeForm={}",classTypeForm);
            throw new UserException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        ClassType classType = classTypeService.findByCode(classTypeForm.getCode());
        if(classType==null){
            log.error("【更新分类】类别不存在，ClassTypeForm={}",classTypeForm);
            throw new UserException(ResultEnum.CLASSTYPE_NOT_EXIST);
        }
        BeanUtils.copyProperties(classTypeForm, classType);
        classTypeService.save(classType);

        //创建返回对象
        ClassTypeVO classTypeVO = ClassTypeConvert.classType2classTypeVO(classType);
        return ResultVOUtil.success(classTypeVO);
    }

    /**
     * 删除分类
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
        List<ClassType> classTypeList = new ArrayList<>();
        String[] ids = idsStr.split(",");
        for(String id : ids){
            ClassType classType = new ClassType();
            classType.setId(id);
            classTypeList.add(classType);
        }
        classTypeService.delete(classTypeList);
        return ResultVOUtil.success();
    }
}

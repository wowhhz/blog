package com.acefet.blog.service.impl;

import com.acefet.blog.convert.ClassTypeConvert;
import com.acefet.blog.entity.ClassType;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.SystemException;
import com.acefet.blog.form.ClassTypeForm;
import com.acefet.blog.repository.ClassTypeRepository;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.vo.ClassTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClassTypeServiceImpl implements ClassTypeService {

    @Autowired
    private ClassTypeRepository classTypeRepository;

    @Override
    public ClassTypeVO getById(String id) {
        ClassType classType = classTypeRepository.getOne(id);
        if(classType==null){
            log.error("【查找classType】数据不存在，id={}",id);
            throw new SystemException(ResultEnum.CUSTOM_NOT_EXIST);
        }
        ClassTypeVO classTypeVO = ClassTypeConvert.classType2classTypeVO(classType);
        return classTypeVO;
    }

    @Override
    public ClassType findByCode(String code) {
        ClassType classType = classTypeRepository.getClassTypeByCode(code);
        return classType;
    }

    @Override
    public List<ClassType> findAll() {
        return classTypeRepository.findAllByOrderBySortDesc();
    }

    @Override
    public void save(ClassType classType) {
        classTypeRepository.save(classType);
    }

    @Override
    public void delete(List<ClassType> classTypeList) {
        classTypeRepository.deleteInBatch(classTypeList);
    }

    /**
     * 查询列表
     * @return
     */
    @Override
    public List<ClassTypeVO> query() {
        List<ClassType> classTypeList = findAll();
        List<ClassTypeVO> classTypeVOList = new ArrayList();
        for(ClassType classType : classTypeList){
            ClassTypeVO classTypeVO = ClassTypeConvert.classType2classTypeVO(classType);
            classTypeVOList.add(classTypeVO);
        }
        return classTypeVOList;
    }

    /**
     * 创建分类
     * @param classTypeForm
     * @param bindingResult
     * @return
     */
    @Override
    public ClassTypeVO create(ClassTypeForm classTypeForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.error("【创建分类】参数不正确，ClassTypeForm={}",classTypeForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        ClassType oldClassType = findByCode(classTypeForm.getCode());
        if(oldClassType!=null){
            log.error("【创建分类】类别已存在，ClassTypeForm={}",classTypeForm);
            throw new SystemException(ResultEnum.CLASSTYPE_EXIST);
        }
        //创建分类对象
        ClassType classType = ClassTypeConvert.classTypeForm2classType(classTypeForm);
        save(classType);

        //创建返回对象
        ClassTypeVO classTypeVO = ClassTypeConvert.classType2classTypeVO(classType);
        return classTypeVO;
    }

    /**
     * 更新分类
     * @param classTypeForm
     * @param bindingResult
     * @return
     */
    @Override
    public ClassTypeVO update(ClassTypeForm classTypeForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.error("【更新分类】参数不正确，ClassTypeForm={}",classTypeForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        ClassType classType = findByCode(classTypeForm.getCode());
        if(classType==null){
            log.error("【更新分类】类别不存在，ClassTypeForm={}",classTypeForm);
            throw new SystemException(ResultEnum.CLASSTYPE_NOT_EXIST);
        }
        BeanUtils.copyProperties(classTypeForm, classType);
        save(classType);

        //创建返回对象
        ClassTypeVO classTypeVO = ClassTypeConvert.classType2classTypeVO(classType);
        return classTypeVO;
    }

    @Override
    public ClassTypeVO save(ClassTypeForm classTypeForm, BindingResult bindingResult) {
        ClassTypeVO classTypeVO = null;
        if(StringUtils.isEmpty(classTypeForm.getId())){
            classTypeVO = create(classTypeForm,bindingResult);
        }else{
            classTypeVO = update(classTypeForm,bindingResult);
        }
        return classTypeVO;
    }

    /**
     * 删除分类
     * @param ids
     */
    @Override
    public void delete(String[] ids) {
        if(ids==null){
            log.error("【删除分类】参数不正确，ids={}",ids);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        List<ClassType> classTypeList = new ArrayList<>();
        for(String id : ids){
            ClassType classType = new ClassType();
            classType.setId(id);
            classTypeList.add(classType);
        }
        delete(classTypeList);
    }
}

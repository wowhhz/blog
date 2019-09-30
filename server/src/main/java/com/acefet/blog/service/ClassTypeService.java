package com.acefet.blog.service;

import com.acefet.blog.entity.ClassType;
import com.acefet.blog.form.ClassTypeForm;
import com.acefet.blog.vo.ClassTypeVO;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ClassTypeService {

    ClassTypeVO getById(String id);

    ClassType findByCode(String code);

    List<ClassType> findAll();

    void save(ClassType classType);

    void delete(List<ClassType> classTypeList);

    List<ClassTypeVO> query();

    ClassTypeVO create(ClassTypeForm classTypeForm,BindingResult bindingResult);

    ClassTypeVO update(ClassTypeForm classTypeForm,BindingResult bindingResult);

    ClassTypeVO save(ClassTypeForm classTypeForm, BindingResult bindingResult) throws IOException;

    void delete(String[] ids);

}

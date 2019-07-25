package com.acefet.blog.convert;

import com.acefet.blog.entity.ClassType;
import com.acefet.blog.form.ClassTypeForm;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.ClassTypeVO;
import org.springframework.beans.BeanUtils;

public class ClassTypeConvert {

    /**
     * 实例对象转换成返回对象
     * @param classType
     * @return
     */
    public static ClassTypeVO classType2classTypeVO(ClassType classType){
        ClassTypeVO classTypeVO = new ClassTypeVO();
        BeanUtils.copyProperties(classType, classTypeVO);
        return classTypeVO;
    }

    /**
     * 表单对象转换实例对象
     * @param classTypeForm
     * @return
     */
    public static ClassType classTypeForm2classType(ClassTypeForm classTypeForm){
        ClassType classType = new ClassType();
        BeanUtils.copyProperties(classTypeForm,classType);
        classType.setId(Util.getUUID());
        return classType;
    }
}

package com.acefet.blog.service.impl;

import com.acefet.blog.CommonApplicationTest;
import com.acefet.blog.entity.ClassType;
import com.acefet.blog.service.ClassTypeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class ClassTypeServiceImplTest extends CommonApplicationTest {

    @Autowired
    private ClassTypeService classTypeService;

    @Test
    public void save() {
        ClassType classType = new ClassType();
        classType.setId("123456");
        classType.setCode("test");
        classType.setName("分类测试");
        classType.setParentCode("");
        classType.setSort(1);
        classTypeService.save(classType);
        Assert.assertTrue(true);
    }

    @Test
    public void getById() {
        ClassType classType = classTypeService.getById("1");
        Assert.assertTrue(classType!=null);
    }

    @Test
    public void findAll() {
        List<ClassType> classTypeList = classTypeService.findAll();
        Assert.assertTrue(classTypeList.size()>0);
    }

    @Test
    public void findByCode(){
        ClassType classType = classTypeService.findByCode("test");
        Assert.assertTrue(classType!=null);
    }


    @Test
    public void delete() {
        List list = new ArrayList();
        ClassType classType = new ClassType();
        classType.setId("123456");
        list.add(classType);
        classTypeService.delete(list);
        Assert.assertTrue(true);
    }
}
package com.acefet.blog.service.impl;

import com.acefet.blog.ServerApplicationTest;
import com.acefet.blog.entity.ClassType;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.vo.ClassTypeVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class ClassTypeServiceImplTest extends ServerApplicationTest {

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
        assertTrue(true);
    }

    @Test
    public void getById() {
        ClassTypeVO classTypeVO = classTypeService.getById("1");
        assertTrue(classTypeVO!=null);
    }

    @Test
    public void findAll() {
        List<ClassType> classTypeList = classTypeService.findAll();
        assertTrue(classTypeList.size()>0);
    }

    @Test
    public void findByCode(){
        ClassType classType = classTypeService.findByCode("test");
        assertTrue(classType!=null);
    }


    @Test
    public void delete() {
        List list = new ArrayList();
        ClassType classType = new ClassType();
        classType.setId("123456");
        list.add(classType);
        classTypeService.delete(list);
        assertTrue(true);
    }
}
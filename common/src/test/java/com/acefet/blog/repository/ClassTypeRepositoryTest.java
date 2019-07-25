package com.acefet.blog.repository;

import com.acefet.blog.entity.ClassType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassTypeRepositoryTest {

    @Autowired
    private ClassTypeRepository classTypeRepository;

    @Test
    public void findAll(){
        Sort sort = new Sort(Sort.Direction.ASC,"sort");
        List<ClassType> classTypeList = classTypeRepository.findAll(sort);
        Assert.assertTrue(classTypeList.size()>0);
    }

    @Test
    public void save(){
        ClassType classType = new ClassType();
        classType.setId("123456");
        classType.setCode("test");
        classType.setName("分类测试");
        classType.setParentCode("");
        classType.setSort(1);
        classTypeRepository.save(classType);
        Assert.assertTrue(true);
    }

    @Test
    public void delete(){
        classTypeRepository.deleteById("123456");
        Assert.assertTrue(true);
    }
}
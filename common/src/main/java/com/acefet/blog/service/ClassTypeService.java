package com.acefet.blog.service;

import com.acefet.blog.entity.ClassType;

import java.util.List;

public interface ClassTypeService {

    ClassType getById(String id);

    ClassType findByCode(String code);

    List<ClassType> findAll();

    void save(ClassType classType);

    void delete(List<ClassType> classTypeList);
}

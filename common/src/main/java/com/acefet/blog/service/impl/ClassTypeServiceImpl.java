package com.acefet.blog.service.impl;

import com.acefet.blog.entity.ClassType;
import com.acefet.blog.repository.ClassTypeRepository;
import com.acefet.blog.service.ClassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassTypeServiceImpl implements ClassTypeService {

    @Autowired
    private ClassTypeRepository classTypeRepository;

    @Override
    public ClassType getById(String id) {
        return classTypeRepository.getOne(id);
    }

    @Override
    public ClassType findByCode(String code) {
        return classTypeRepository.getClassTypeByCode(code);
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
}

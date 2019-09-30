package com.acefet.blog.repository;

import com.acefet.blog.entity.ClassType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassTypeRepository extends JpaRepository<ClassType,String> {

    List<ClassType> findAllByOrderBySortDesc();

    ClassType getClassTypeByCode(String code);
}

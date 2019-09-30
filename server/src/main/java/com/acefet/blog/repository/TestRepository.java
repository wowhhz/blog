package com.acefet.blog.repository;

import com.acefet.blog.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TestRepository extends JpaRepository<Test,String>,PagingAndSortingRepository<Test,String> {

    Test getTestById(String id);

    Page<Test> findAll(Pageable pageable);

    Page<Test> findAllByName(String name,Pageable pageable);
}

package com.acefet.blog.repository;

import com.acefet.blog.entity.UserFile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserFileRepository extends JpaRepository<UserFile,String>,PagingAndSortingRepository<UserFile,String> {

    UserFile getFileById(String id);

    Page<UserFile> findAll(Pageable pageable);

    Page<UserFile> findAllByFileNameLikeOrFileDescriptionLike(String fileName, String fileDescription, Pageable pageable);

    <S extends UserFile> List<S> findAllByIdIn(String[] ids);
}

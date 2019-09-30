package com.acefet.blog.service;

import com.acefet.blog.form.UserFileForm;
import com.acefet.blog.vo.UserFileVO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UserFileService {

    UserFileVO view(String id);

    Page<UserFileVO> query(String searchValue, String orderType, String orderName,
                           String pageNum, String pageSize);

    UserFileVO save(UserFileForm userFileForm, BindingResult bindingResult) throws IOException;

    UserFileVO save(MultipartFile multipartFile, Map<String,String> map) throws IOException;

    void delete(String[] ids,String realPath);

}

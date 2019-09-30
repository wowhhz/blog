package com.acefet.blog.service;

import com.acefet.blog.form.TestForm;
import com.acefet.blog.vo.TestVO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface TestService {

    TestVO view(String id) throws UnsupportedEncodingException;

    Page<TestVO> query(String name,String orderType,String orderName,
                      String pageNum,String pageSize) throws UnsupportedEncodingException;

    TestVO save(TestForm testForm,BindingResult bindingResult) throws IOException;

    void delete(String[] ids);

}

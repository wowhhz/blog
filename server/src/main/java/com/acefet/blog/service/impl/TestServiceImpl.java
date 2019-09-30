package com.acefet.blog.service.impl;

import com.acefet.blog.convert.TestConvert;
import com.acefet.blog.entity.Test;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.SystemException;
import com.acefet.blog.form.TestForm;
import com.acefet.blog.repository.TestRepository;
import com.acefet.blog.service.TestService;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.TestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Override
    public TestVO view(String id) throws UnsupportedEncodingException {
        Test test = testRepository.getTestById(id);
        if(test==null){
            log.error("【查找test】数据不存在，id={}",id);
            throw new SystemException(ResultEnum.CUSTOM_NOT_EXIST);
        }
        TestVO testVO = TestConvert.test2TestVO(test,true);
        return testVO;
    }

    @Override
    public Page<TestVO> query(String searchValue,String orderType,String orderName,
                             String pageNum, String pageSize) throws UnsupportedEncodingException {
        int size = com.acefet.blog.util.Page.PAGESIZE_DEFAULT, page = 0;
        if(StringUtils.isEmpty(pageNum))pageNum = "1";
        if(pageSize!=null && Util.isNumeric(pageSize))size = Integer.parseInt(pageSize);
        if(!Util.isNumeric(pageNum)){
            log.error("【查找test】参数不正确，pageNum={}",pageNum);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        page = Integer.parseInt(pageNum)-1;
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        if(StringUtils.isEmpty(orderName)){
            orderList.add(Sort.Order.desc("createTime"));
        }else{
            if("asc".equalsIgnoreCase(orderType)){
                orderList.add(Sort.Order.asc(orderName));
            }else{
                orderList.add(Sort.Order.desc(orderName));
            }
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderList));
        Page<Test> testPage = null;
        if(StringUtils.isEmpty(searchValue)){
            testPage = testRepository.findAll(pageable);
        }else{
            testPage = testRepository.findAllByName(searchValue, pageable);
        }
        Page<TestVO> testVOPage = testPage.map(this::convertToTestDto);
        return testVOPage;
    }

    private TestVO convertToTestDto(Test test){
        TestVO testVO = null;
        try {
            testVO = TestConvert.test2TestVO(test,true);
        } catch (UnsupportedEncodingException e) {
            log.error("",e);
        }
        return testVO;
    }


    public TestVO create(TestForm testForm, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.error("【创建test】校验失败，testForm={}",testForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        String fileStr = FileUtil.uploadFiles(testForm.getFile());
        Test test = TestConvert.testForm2Test(testForm);
        if(!StringUtils.isEmpty(fileStr))test.setFile(fileStr);
        testRepository.save(test);

        TestVO testVO = TestConvert.test2TestVO(test,false);
        return testVO;
    }

    public TestVO update(TestForm testForm, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.error("【更新test】校验失败，testForm={}",testForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        Test test = testRepository.getTestById(testForm.getId());
        if(test==null){
            log.error("【更新test】数据不存在,id={}",testForm.getId());
            throw new SystemException(ResultEnum.CUSTOM_NOT_EXIST);
        }
        BeanUtils.copyProperties(testForm,test);
        String fileStr = FileUtil.uploadFiles(testForm.getFile());
        if(!StringUtils.isEmpty(fileStr))test.setFile(fileStr);
        testRepository.save(test);
        TestVO testVO = TestConvert.test2TestVO(test,false);
        return testVO;
    }

    @Override
    public TestVO save(TestForm testForm,BindingResult bindingResult) throws IOException {
        TestVO testVO = null;
        if(StringUtils.isEmpty(testForm.getId())){
            testVO = create(testForm,bindingResult);
        }else{
            testVO = update(testForm,bindingResult);
        }
        return testVO;
    }

    @Override
    public void delete(String[] ids) {
        if(ids==null){
            log.error("【删除test】参数不正确，ids={}",ids);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        List<Test> list = new ArrayList<Test>();
        for (String id : ids) {
            Test test = new Test();
            test.setId(id);
            list.add(test);
        }
        testRepository.deleteInBatch(list);
    }
}

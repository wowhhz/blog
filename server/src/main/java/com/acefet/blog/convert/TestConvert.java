package com.acefet.blog.convert;

import com.acefet.blog.entity.Test;
import com.acefet.blog.form.TestForm;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.TestVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TestConvert {

    public static Test testForm2Test(TestForm testForm){
        Test test = new Test();
        BeanUtils.copyProperties(testForm,test);
        test.setId(Util.getUUID());
        test.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return test;
    }

    public static TestVO test2TestVO(Test test,boolean hasContentTrans) throws UnsupportedEncodingException {
        TestVO testVO = new TestVO();
        BeanUtils.copyProperties(test,testVO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        testVO.setCreateTime(sdf.format(test.getCreateTime()));
        if(hasContentTrans)testVO.setContent(Util.getBlobField(test.getContent()));
        return testVO;
    }

    public static TestVO convertToTestDto(Test test){
        return null;
    }
}

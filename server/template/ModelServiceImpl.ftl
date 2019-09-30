<#assign hasBlobField="" />
<#assign id="id" />
<#assign tmp="" />
<#list MODEL_FIELDS as obj>
<#if (obj["saveType"]=="blob")>
<#assign hasBlobField="true" />
</#if>
<#if (obj["primaryKey"]=="true" && tmp=="")>
<#assign id='${obj["code"]}' />
<#assign tmp="tmp" />
</#if>
</#list>
package com.acefet.blog.service.impl;

import com.acefet.blog.convert.${MODEL_CODE?cap_first}Convert;
import com.acefet.blog.entity.${MODEL_CODE?cap_first};
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.SystemException;
import com.acefet.blog.form.${MODEL_CODE?cap_first}Form;
import com.acefet.blog.repository.${MODEL_CODE?cap_first}Repository;
import com.acefet.blog.service.${MODEL_CODE?cap_first}Service;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.${MODEL_CODE?cap_first}VO;
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
public class ${MODEL_CODE?cap_first}ServiceImpl implements ${MODEL_CODE?cap_first}Service {

    @Autowired
    private ${MODEL_CODE?cap_first}Repository ${MODEL_CODE}Repository;

    @Override
		public ${MODEL_CODE?cap_first}VO view(String ${id})<#if (hasBlobField=="true")> throws UnsupportedEncodingException</#if> {
        ${MODEL_CODE?cap_first} ${MODEL_CODE} = ${MODEL_CODE}Repository.get${MODEL_CODE?cap_first}By${id?cap_first}(${id});
        if(${MODEL_CODE}==null){
            log.error("【查找${MODEL_CODE}】数据不存在，${id}={}",${id});
            throw new SystemException(ResultEnum.CUSTOM_NOT_EXIST);
        }
        ${MODEL_CODE?cap_first}VO ${MODEL_CODE}VO = ${MODEL_CODE?cap_first}Convert.${MODEL_CODE}2${MODEL_CODE?cap_first}VO(${MODEL_CODE}<#if (hasBlobField=="true")>,true</#if>);
        return ${MODEL_CODE}VO;
    }

    @Override
    public Page<${MODEL_CODE?cap_first}VO> query(String searchValue,String orderType,String orderName,
                             String pageNum, String pageSize)<#if (hasBlobField=="true")> throws UnsupportedEncodingException</#if> {
        int size = com.acefet.blog.util.Page.PAGESIZE_DEFAULT, page = 0;
        if(StringUtils.isEmpty(pageNum))pageNum = "1";
        if(pageSize!=null && Util.isNumeric(pageSize))size = Integer.parseInt(pageSize);
        if(!Util.isNumeric(pageNum)){
            log.error("【查找${MODEL_CODE}】参数不正确，pageNum={}",pageNum);
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
        Page<${MODEL_CODE?cap_first}> ${MODEL_CODE}Page = null;
        
        ${MODEL_CODE}Page = ${MODEL_CODE}Repository.findAll(pageable);

        Page<${MODEL_CODE?cap_first}VO> ${MODEL_CODE}VOPage = null;
        if(${MODEL_CODE}Page!=null){
            ${MODEL_CODE}VOPage = ${MODEL_CODE}Page.map(this::convertTo${MODEL_CODE?cap_first}Dto);
        }    
        
        return ${MODEL_CODE}VOPage;
    }

    private ${MODEL_CODE?cap_first}VO convertTo${MODEL_CODE?cap_first}Dto(${MODEL_CODE?cap_first} ${MODEL_CODE}){
        ${MODEL_CODE?cap_first}VO ${MODEL_CODE}VO = null;
<#if (hasBlobField=="true")>
        try {
            ${MODEL_CODE}VO = ${MODEL_CODE?cap_first}Convert.${MODEL_CODE}2${MODEL_CODE?cap_first}VO(${MODEL_CODE}<#if (hasBlobField=="true")>,true</#if>);
        } catch (UnsupportedEncodingException e) {
            log.error("",e);
        }
<#else>
        ${MODEL_CODE}VO = ${MODEL_CODE?cap_first}Convert.${MODEL_CODE}2${MODEL_CODE?cap_first}VO(${MODEL_CODE}<#if (hasBlobField=="true")>,true</#if>);
</#if>
        
        return ${MODEL_CODE}VO;
    }

    public ${MODEL_CODE?cap_first}VO create(${MODEL_CODE?cap_first}Form ${MODEL_CODE}Form, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.error("【创建${MODEL_CODE}】校验失败，${MODEL_CODE}Form={}",${MODEL_CODE}Form);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        ${MODEL_CODE?cap_first} ${MODEL_CODE} = ${MODEL_CODE?cap_first}Convert.${MODEL_CODE}Form2${MODEL_CODE?cap_first}(${MODEL_CODE}Form);
<#list MODEL_FIELDS as obj>
<#if (obj["type"]=="file")>
        String ${obj["code"]}Str = FileUtil.uploadFiles(${MODEL_CODE}Form.get${obj["code"]?cap_first}());
        if(!StringUtils.isEmpty(${obj["code"]}Str))${MODEL_CODE}.set${obj["code"]?cap_first}(${obj["code"]}Str);
</#if>
</#list>
        ${MODEL_CODE}Repository.save(${MODEL_CODE});
        ${MODEL_CODE?cap_first}VO ${MODEL_CODE}VO = ${MODEL_CODE?cap_first}Convert.${MODEL_CODE}2${MODEL_CODE?cap_first}VO(${MODEL_CODE}<#if (hasBlobField=="true")>,false</#if>);
        return ${MODEL_CODE}VO;
    }

    public ${MODEL_CODE?cap_first}VO update(${MODEL_CODE?cap_first}Form ${MODEL_CODE}Form, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.error("【更新${MODEL_CODE}】校验失败，${MODEL_CODE}Form={}",${MODEL_CODE}Form);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        ${MODEL_CODE?cap_first} ${MODEL_CODE} = ${MODEL_CODE}Repository.get${MODEL_CODE?cap_first}By${id?cap_first}(${MODEL_CODE}Form.get${id?cap_first}());
        if(${MODEL_CODE}==null){
            log.error("【更新${MODEL_CODE}】数据不存在,${id}={}",${MODEL_CODE}Form.get${id?cap_first}());
            throw new SystemException(ResultEnum.CUSTOM_NOT_EXIST);
        }
        BeanUtils.copyProperties(${MODEL_CODE}Form,${MODEL_CODE});
<#list MODEL_FIELDS as obj>
<#if (obj["type"]=="file")>
        String ${obj["code"]}Str = FileUtil.uploadFiles(${MODEL_CODE}Form.get${obj["code"]?cap_first}());
        if(!StringUtils.isEmpty(${obj["code"]}Str))${MODEL_CODE}.set${obj["code"]?cap_first}(${obj["code"]}Str);
</#if>
</#list>
        ${MODEL_CODE}Repository.save(${MODEL_CODE});
        ${MODEL_CODE?cap_first}VO ${MODEL_CODE}VO = ${MODEL_CODE?cap_first}Convert.${MODEL_CODE}2${MODEL_CODE?cap_first}VO(${MODEL_CODE}<#if (hasBlobField=="true")>,false</#if>);
        return ${MODEL_CODE}VO;
    }

    @Override
    public ${MODEL_CODE?cap_first}VO save(${MODEL_CODE?cap_first}Form ${MODEL_CODE}Form,BindingResult bindingResult) throws IOException {
        ${MODEL_CODE?cap_first}VO ${MODEL_CODE}VO = null;
        if(StringUtils.isEmpty(${MODEL_CODE}Form.get${id?cap_first}())){
            ${MODEL_CODE}VO = create(${MODEL_CODE}Form,bindingResult);
        }else{
            ${MODEL_CODE}VO = update(${MODEL_CODE}Form,bindingResult);
        }
        return ${MODEL_CODE}VO;
    }

    @Override
    public void delete(String[] ${id}s) {
        if(${id}s==null){
            log.error("【删除${MODEL_CODE}】参数不正确，${id}s={}",${id}s);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        List<${MODEL_CODE?cap_first}> list = new ArrayList<${MODEL_CODE?cap_first}>();
        for (String ${id} : ${id}s) {
            ${MODEL_CODE?cap_first} ${MODEL_CODE} = new ${MODEL_CODE?cap_first}();
            ${MODEL_CODE}.set${id?cap_first}(${id});
            list.add(${MODEL_CODE});
        }
        ${MODEL_CODE}Repository.deleteInBatch(list);
    }
}

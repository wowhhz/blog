package com.acefet.blog.service;

import com.acefet.blog.form.${MODEL_CODE?cap_first}Form;
import com.acefet.blog.vo.${MODEL_CODE?cap_first}VO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface ${MODEL_CODE?cap_first}Service {

<#assign hasBlobField="" />
<#assign id="id" />
<#list MODEL_FIELDS as obj>
<#if (obj["saveType"]=="blob")>
<#assign hasBlobField="true" />
</#if>
<#if (obj["primaryKey"]=="true")>
<#assign id='${obj["code"]}' />
</#if>
</#list>
    ${MODEL_CODE?cap_first}VO view(String ${id})<#if (hasBlobField=="true")> throws UnsupportedEncodingException</#if>;

    Page<${MODEL_CODE?cap_first}VO> query(String searchValue,String orderType,String orderName,
                      String pageNum,String pageSize)<#if (hasBlobField=="true")> throws UnsupportedEncodingException</#if>;

    ${MODEL_CODE?cap_first}VO save(${MODEL_CODE?cap_first}Form ${MODEL_CODE}Form,BindingResult bindingResult) throws IOException;

    void delete(String[] ${id}s);

}

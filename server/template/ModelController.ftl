<#assign hasBlobField="" />
<#assign id="id" />
<#list MODEL_FIELDS as obj>
<#if (obj["saveType"]=="blob")>
<#assign hasBlobField="true" />
</#if>
<#if (obj["primaryKey"]=="true")>
<#assign id='${obj["code"]}' />
<#break>
</#if>
</#list>
package com.acefet.blog.controller;

import com.acefet.blog.form.${MODEL_CODE?cap_first}Form;
import com.acefet.blog.service.${MODEL_CODE?cap_first}Service;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.datatables.DataTableQueryParameter;
import com.acefet.blog.util.datatables.DataTableReturnObject;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.${MODEL_CODE?cap_first}VO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/${MODEL_CODE}/")
@Slf4j
public class ${MODEL_CODE?cap_first}Controller {

    @Autowired
    private ${MODEL_CODE?cap_first}Service ${MODEL_CODE}Service;

    @GetMapping("list")
    public void list(HttpServletRequest request){
//        init param
<#list MODEL_FIELDS as obj>				
<#if (obj["type"]=="select" || obj["type"]=="checkbox" || obj["type"]=="radio" || obj["type"]=="dropdown")>
		<#if (obj["parameterType"]=="select")>
		//List ${obj["selectConfig"]} = ParaGlobal.getParas(simpleCache, "${obj["selectConfig"]}");
		//systemCodeService.loadPara("paraConfig","${obj["selectConfig"]}");
		//request.setAttribute("${obj["selectConfig"]}",${obj["selectConfig"]});
		</#if>
		<#if (obj["parameterType"]=="list")>
		//String[] ${obj["code"]}Strs = "${obj["selectConfig"]?replace("\r","")?replace("\n","|")}".split("[|]");
		//List<SystemCode> ${obj["code"]}List = new ArrayList<SystemCode>();
		//for (int i = 0; i < ${obj["code"]}Strs.length; i++) {
		//	String[] strs = ${obj["code"]}Strs[i].split(" ");
		//	SystemCode systemCode = new SystemCode();
		//	systemCode.setCode(strs[0]);
		//	systemCode.setName(strs[1]);
		//	${obj["code"]}List.add(systemCode);
		//}
		//request.setAttribute("${obj["code"]}List",${obj["code"]}List);
		</#if>
		<#if (obj["parameterType"]=="sql")>
		//String ${obj["code"]}Sql = "${obj["selectConfig"]}";
		//List<Map> ${obj["code"]}RsList = databaseTemplateUtil.query(${obj["code"]}Sql);
		//List<SystemCode> btypeList = new ArrayList<SystemCode>();
		//for (int i = 0; i < ${obj["code"]}RsList.size(); i++) {
		//	Map<String,String> map = ${obj["code"]}RsList.get(i);
		//	String name = map.get("name");
		//	String code = map.get("code");
		//	
		//	SystemCode systemCode = new SystemCode();
		//	systemCode.setCode(code);
		//	systemCode.setName(name);
		//	${obj["code"]}List.add(systemCode);
		//}
		//request.setAttribute("${obj["code"]}List",${obj["code"]}List);
		</#if>
</#if>
</#list>
    }

    @PostMapping(value = "query", produces = "application/json")
    @ResponseBody
    public Object query(@RequestParam Map<String,String> mapParam)<#if (hasBlobField=="true")> throws UnsupportedEncodingException</#if> {
        DataTableQueryParameter parameter = new DataTableQueryParameter(mapParam);

        String orderType = "desc",orderName = "";
        if(parameter.getOrders().size()>0){
            orderType = parameter.getOrders().get(0).getDir();
            orderName = parameter.getOrders().get(0).getOrderName();
        }
        Page<${MODEL_CODE?cap_first}VO> ${MODEL_CODE}VOPage = 
        	${MODEL_CODE}Service.query(
        				parameter.getSearch().getValue(),
                orderType,orderName,
                (parameter.getPage().getCurrentPage()+1)+"",
                parameter.getPage().getPageSize()+"");

        DataTableReturnObject returnObject = new DataTableReturnObject(${MODEL_CODE}VOPage,parameter);
        return returnObject.toMap();
    }

    @GetMapping("edit")
    @ResponseBody
    public ResultVO edit(String ${id})<#if (hasBlobField=="true")> throws UnsupportedEncodingException</#if> {
        ${MODEL_CODE?cap_first}VO ${MODEL_CODE}VO = ${MODEL_CODE}Service.view(${id});
        return ResultVOUtil.success(${MODEL_CODE}VO);
    }

    @GetMapping("view")
    @ResponseBody
    public ResultVO view(String ${id})<#if (hasBlobField=="true")> throws UnsupportedEncodingException</#if> {
        return edit(id);
    }

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVO save(HttpServletRequest request,
                         @Valid ${MODEL_CODE?cap_first}Form ${MODEL_CODE}Form,
                         BindingResult bindingResult) throws IOException {
        ${MODEL_CODE?cap_first}VO ${MODEL_CODE}VO = ${MODEL_CODE}Service.save(${MODEL_CODE}Form,bindingResult);
        return ResultVOUtil.success(${MODEL_CODE}VO);
    }

    @PostMapping("del")
    @ResponseBody
    public ResultVO del(@RequestBody String[] ${id}s){
        ${MODEL_CODE}Service.delete(${id}s);
        return ResultVOUtil.success();
    }


}

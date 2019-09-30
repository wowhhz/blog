package com.acefet.blog.controller;

import com.acefet.blog.form.ClassTypeForm;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.datatables.DataTableQueryParameter;
import com.acefet.blog.util.datatables.DataTableReturnObject;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.ClassTypeVO;
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
@RequestMapping("admin/classType/")
@Slf4j
public class ClassTypeController {

    @Autowired
    private ClassTypeService classTypeService;

    @GetMapping("list")
    public void list(HttpServletRequest request){
//        init param
    }

    @PostMapping(value = "query", produces = "application/json")
    @ResponseBody
    public Object query(@RequestParam Map<String,String> mapParam) {
        DataTableQueryParameter parameter = new DataTableQueryParameter(mapParam);
        List<ClassTypeVO> classTypeVOList =  classTypeService.query();
        DataTableReturnObject returnObject = new DataTableReturnObject(classTypeVOList,parameter);
        return returnObject.toMap();
    }

    @GetMapping("edit")
    @ResponseBody
    public ResultVO edit(String id) {
        ClassTypeVO classTypeVO = classTypeService.getById(id);
        return ResultVOUtil.success(classTypeVO);
    }

    @GetMapping("view")
    @ResponseBody
    public ResultVO view(String id) {
        return edit(id);
    }

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVO save(HttpServletRequest request,
                         @Valid ClassTypeForm classTypeForm,
                         BindingResult bindingResult) throws IOException {
        ClassTypeVO classTypeVO = classTypeService.save(classTypeForm,bindingResult);
        return ResultVOUtil.success(classTypeVO);
    }

    @PostMapping("del")
    @ResponseBody
    public ResultVO del(@RequestBody String[] ids){
        classTypeService.delete(ids);
        return ResultVOUtil.success();
    }


}

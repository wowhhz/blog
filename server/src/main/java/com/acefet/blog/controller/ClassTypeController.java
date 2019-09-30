package com.acefet.blog.controller;

import com.acefet.blog.form.ClassTypeForm;
import com.acefet.blog.service.ClassTypeService;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.vo.ClassTypeVO;
import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/class")
@Slf4j
public class ClassTypeController {

//    @Autowired
//    private ClassTypeService classTypeService;
//
//    /**
//     * 查询分类
//     * @return
//     */
//    @GetMapping("/query")
//    public ResultVO<List> query(){
//        List<ClassTypeVO> classTypeVOList = classTypeService.query();
//        return ResultVOUtil.success(classTypeVOList);
//    }
//
//    /**
//     * 创建分类
//     * @param classTypeForm
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("/create")
//    public ResultVO<ClassTypeVO> create(@Valid ClassTypeForm classTypeForm,
//                       BindingResult bindingResult){
//        ClassTypeVO classTypeVO = classTypeService.create(classTypeForm,bindingResult);
//        return ResultVOUtil.success(classTypeVO);
//    }
//
//    /**
//     * 更新分类
//     * @param classTypeForm
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("/update")
//    public ResultVO<ClassTypeVO> update(@Valid ClassTypeForm classTypeForm,
//                           BindingResult bindingResult){
//        ClassTypeVO classTypeVO = classTypeService.update(classTypeForm,bindingResult);
//        return ResultVOUtil.success(classTypeVO);
//    }
//
//    /**
//     * 删除分类
//     * @param ids
//     * @return
//     */
//    @PostMapping("/del")
//    public ResultVO del(@RequestParam String[] ids){
//        classTypeService.delete(ids);
//        return ResultVOUtil.success();
//    }
}

package com.acefet.blog.controller;

import com.acefet.blog.form.TestForm;
import com.acefet.blog.service.TestService;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.datatables.DataTableQueryParameter;
import com.acefet.blog.util.datatables.DataTableReturnObject;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.TestVO;
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
@RequestMapping("admin/test/")
@Slf4j
public class TestController {


    @Autowired
    private TestService testService;

    @GetMapping("list")
    public void list(HttpServletRequest request){
//        init param
        String[] items = new String[]{"select","radio","checkbox"};
        for (String item: items) {
            List<Map> list = new ArrayList<Map>();
            for (int i = 1; i <= 3; i++) {
                Map map = new HashMap();
                map.put("name",item+" "+i);
                map.put("code",String.valueOf(i));
                list.add(map);
            }
            request.setAttribute(item+"Conf",list);
        }
    }

    @PostMapping(value = "query", produces = "application/json")
    @ResponseBody
    public Object query(@RequestParam Map<String,String> mapParam) throws UnsupportedEncodingException {
        DataTableQueryParameter parameter = new DataTableQueryParameter(mapParam);

        String orderType = "desc",orderName = "createTime";
        if(parameter.getOrders().size()>0){
            orderType = parameter.getOrders().get(0).getDir();
            orderName = parameter.getOrders().get(0).getOrderName();
        }
        Page<TestVO> testVOPage = testService.query(parameter.getSearch().getValue(),
                orderType,orderName,
                (parameter.getPage().getCurrentPage()+1)+"",
                parameter.getPage().getPageSize()+"");

        DataTableReturnObject returnObject = new DataTableReturnObject(testVOPage,parameter);
        return returnObject.toMap();
    }

    @GetMapping("edit")
    @ResponseBody
    public ResultVO edit(String id) throws UnsupportedEncodingException {
        TestVO testVO = testService.view(id);
        return ResultVOUtil.success(testVO);
    }

    @GetMapping("view")
    @ResponseBody
    public ResultVO view(String id) throws UnsupportedEncodingException {
        return edit(id);
    }

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVO save(HttpServletRequest request,
                         @Valid TestForm testForm,
                         BindingResult bindingResult) throws IOException {
        TestVO testVO = testService.save(testForm,bindingResult);
        return ResultVOUtil.success(testVO);
    }

    @PostMapping("del")
    @ResponseBody
    public ResultVO del(@RequestBody String[] ids){
        testService.delete(ids);
        return ResultVOUtil.success();
    }


}

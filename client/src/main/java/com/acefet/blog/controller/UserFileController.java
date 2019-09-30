package com.acefet.blog.controller;

import com.acefet.blog.entity.UserFile;
import com.acefet.blog.form.UserFileForm;
import com.acefet.blog.service.UserFileService;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.ResultVOUtil;
import com.acefet.blog.util.datatables.DataTableQueryParameter;
import com.acefet.blog.util.datatables.DataTableReturnObject;
import com.acefet.blog.vo.ResultVO;
import com.acefet.blog.vo.UserFileVO;
import com.acefet.blog.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/userFile/")
@Slf4j
public class UserFileController {

    @Autowired
    private UserFileService userFileService;

    @GetMapping("list")
    public void list(HttpServletRequest request) throws IOException {
//        init param
        request.setAttribute("urlPath",FileUtil.getUrlFilePath());
		request.setAttribute("statusList",getFileStatusList());
    }

    public List getFileStatusList(){
        String[] statusStrs = "normal 正常|disable 禁用|lose 文件缺失".split("[|]");
        List statusList = new ArrayList();
        for(String str : statusStrs){
            Map map = new HashMap();
            String[] s = str.split(" ");
            map.put("code",s[0]);
            map.put("name",s[1]);
            statusList.add(map);
        }
        return statusList;
    }

    @PostMapping(value = "query", produces = "application/json")
    @ResponseBody
    public Object query(@RequestParam Map<String,String> mapParam) {
        DataTableQueryParameter parameter = new DataTableQueryParameter(mapParam);

        String orderType = "desc",orderName = "";
        if(parameter.getOrders().size()>0){
            orderType = parameter.getOrders().get(0).getDir();
            orderName = parameter.getOrders().get(0).getOrderName();
        }
        Page<UserFileVO> fileVOPage = 
        	userFileService.query(
        				parameter.getSearch().getValue(),
                orderType,orderName,
                (parameter.getPage().getCurrentPage()+1)+"",
                parameter.getPage().getPageSize()+"");

        DataTableReturnObject returnObject = new DataTableReturnObject(fileVOPage,parameter);
        return returnObject.toMap();
    }

    @GetMapping("edit")
    @ResponseBody
    public ResultVO edit(String id) {
        UserFileVO fileVO = userFileService.view(id);
        return ResultVOUtil.success(fileVO);
    }

    @GetMapping("view")
    @ResponseBody
    public ResultVO view(String id) {
        return edit(id);
    }

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVO save(HttpServletRequest request,
                         @Valid UserFileForm fileForm,
                         BindingResult bindingResult) throws IOException {
        UserFileVO fileVO = userFileService.save(fileForm,bindingResult);
        return ResultVOUtil.success(fileVO);
    }

    @PostMapping("del")
    @ResponseBody
    public ResultVO del(@RequestBody String[] ids) throws IOException {
        userFileService.delete(ids,FileUtil.getRealFilePath());
        return ResultVOUtil.success();
    }

    @RequestMapping(value="/uploadFile")
    public void uploadFile(@RequestParam("upload") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws Exception{

        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        String filePath = FileUtil.getRealFilePath();
        String urlPath = FileUtil.getUrlFilePath();
        String subPath = FileUtil.getFileSubPath();

        Map<String,String> map = new HashMap();
        map.put("filePath",filePath);
        map.put("subPath",subPath);
        map.put("groupId",UserFile.class.getName());
        map.put("actionId","userFile");
        map.put("userId",user.getId());
        map.put("userName",user.getUsername());

        UserFileVO userFileVO = userFileService.save(multipartFile,map);
        if(userFileVO==null)return;

        String resourcePath = (urlPath+subPath+userFileVO.getFileName()).replace("\\", "/");

        String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
        if(!StringUtils.isEmpty(CKEditorFuncNum)){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out;
            String s = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+",'"+resourcePath+"');</script>";
            try{
                out = response.getWriter();
                out.print(s);
                out.flush();
            }catch(IOException e){
                log.error("upload userFile error", e);
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value="/browseFile")
    public ModelAndView browseFile(HttpServletRequest request, HttpServletResponse response) throws IOException{

        String urlPath = FileUtil.getUrlFilePath();

        ModelAndView mav = new ModelAndView();
        mav.addObject("statusList",getFileStatusList());

        response.setContentType("text/html;charset=UTF-8");
        String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
        if(CKEditorFuncNum==null) CKEditorFuncNum= "1";

        mav.addObject("CKEditorFuncNum", CKEditorFuncNum);
        mav.addObject("urlPath", urlPath);
        return mav;
    }

    @GetMapping("/download")
    public ResponseEntity downloadFile(String filePath) throws IOException {
        File file = new File(FileUtil.getRealFilePath()+filePath);
        InputStreamResource resource = null;

        try{
            resource = new InputStreamResource(new FileInputStream(file));
        }catch (FileNotFoundException e){
            log.error(e.getMessage(),e);
            throw new FileNotFoundException(file.getName()+"(文件没有找到)");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=%s", file.getName()));
        headers.add("Cache-Control",
                "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity entity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);

        return entity;
    }

}

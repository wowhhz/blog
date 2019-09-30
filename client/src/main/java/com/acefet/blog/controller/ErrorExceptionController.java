package com.acefet.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class ErrorExceptionController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private ErrorAttributes errorAttributes;
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * REST back error
     * @return
     */
//    @RequestMapping(value = {"/error"})
//    @ResponseBody
//    public String error(HttpServletRequest request) {
//
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
//        return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
//                        + "<div>Exception Message: <b>%s</b></div><body></html>",
//                statusCode, exception==null? "N/A": exception.getMessage());
//
//        return "404";
//    }

    /**
     * not found page error page
     * @return
     */
    @GetMapping(value = "/404")
    public String error_404() {
        return "error/404";
    }

    /**
     * Internal server error page
     * @param request
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/500")
    public String error_500(HttpServletRequest request, ModelMap modelMap) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        modelMap.put("code",statusCode);
        modelMap.put("msg",exception.getMessage());
        return "error/500";
    }


    @Autowired
    public ErrorExceptionController(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }

    /**
     * web页面处理
     */

    @RequestMapping(value = ERROR_PATH,produces = "text/html")
    public String errorPageHandler(HttpServletRequest request, HttpServletResponse response){
        int status = response.getStatus();
        switch (status){
            case 403:
                return "403";
            case 404:
                return "404";
            case 405:
                return "405";
            case 500:
                return "500";
        }
        return "index";
    }

//    /**
//     * 除web页面外的错误处理 ，如json/xml
//     */
//
//    @RequestMapping(value = ERROR_PATH)
//    @ResponseBody
//    public ApiResponse errorApiHandler(HttpServletRequest request){
//
//        WebRequest webRequest = new ServletWebRequest(request);
//
//        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(webRequest, false);
//
//        int status = getStatus(request);
//
//        return ApiResponse.ofMessage(status, String.valueOf(attr.getOrDefault("message", "error")));
//    }


    private int getStatus(HttpServletRequest request){
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(status!=null){
            return status;
        }
        return 500;
    }
}

package com.acefet.blog.advice;

import com.acefet.blog.constant.GlobalParameter;
import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    private String adminPathPre = GlobalParameter.ADMIN_PATH_PRE;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO defaultErrorHandler(HttpServletRequest request, Exception e){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode("500");
        resultVO.setMsg(e.getMessage());
        resultVO.setData("");

        return resultVO;
    }

}

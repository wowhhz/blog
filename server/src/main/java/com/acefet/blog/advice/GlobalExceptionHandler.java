package com.acefet.blog.advice;

import com.acefet.blog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO defaultErrorHandler(HttpServletRequest request, Exception e){
        log.error("", e);
        ResultVO resultVO = new ResultVO();
        if (e instanceof NoHandlerFoundException) {
            resultVO.setCode(404+"");
            resultVO.setMsg("页面没找到");
        } else {
            resultVO.setCode(500+"");
            resultVO.setMsg(e.getMessage());
        }
        resultVO.setData("");
        return resultVO;
    }

}

package com.acefet.blog.exception;

import com.acefet.blog.enums.ResultEnum;

public class SystemException extends RuntimeException {

    private String code;

    public SystemException(String code, String message){
        super(message);
        this.code = code;
    }

    public SystemException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}

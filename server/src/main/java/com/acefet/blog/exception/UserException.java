package com.acefet.blog.exception;

import com.acefet.blog.enums.ResultEnum;

public class UserException extends RuntimeException {

    private String code;

    public UserException(String code,String message){
        super(message);
        this.code = code;
    }

    public UserException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}

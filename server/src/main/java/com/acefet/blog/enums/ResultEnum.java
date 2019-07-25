package com.acefet.blog.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    PARAM_ERROR("b500","参数错误"),

    USER_EXIST("b501","用户已存在"),
    USER_PWD_ERROR("b502","密码错误"),
    USER_TWOPWD_ERROR("b503","两次密码不一致"),
    USER_NOT_EXIST("b504","用户不存在"),
    USER_PWD_LENGTH_ERROR("b505","密码长度不正确（3-32位之间）"),

    CLASSTYPE_EXIST("b601","分类已存在"),
    CLASSTYPE_NOT_EXIST("b602","分类不存在"),

    ARTICLE_EXIST("b701","文章已存在"),
    ARTICLE_NOT_EXIST("b702","文章不存在"),

    COMMENT_EXIST("b801","留言已存在"),
    COMMENT_NOT_EXIST("b802","留言不存在"),
        ;
    private String code;
    private String message;

    ResultEnum(String code,String message){
        this.code = code;
        this.message = message;
    }

}

package com.acefet.blog.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    PARAM_ERROR("b500","参数错误"),

    CUSTOM_NOT_EXIST("b0101", "数据不存在"),
    CUSTOM_EXIST("b0102","数据已存在"),
    CUSTOM_TRANS_ERROR("b0103","数据转换异常"),

    USER_EXIST("b501","用户已存在"),
    USER_PWD_ERROR("b502","密码错误"),
    USER_TWOPWD_ERROR("b503","两次密码不一致"),
    USER_NOT_EXIST("b504","用户不存在"),
    USER_PWD_LENGTH_ERROR("b505","密码长度不正确（3-32位之间）"),
    USER_DISABLE("b506","用户已禁用"),
    USER_SESSION_ERROR("b507","获取用户信息失效，需要重新登录"),
    USER_OLD_PWD_ERROR("b508","原密码错误"),

    CLASSTYPE_EXIST("b601","分类已存在"),
    CLASSTYPE_NOT_EXIST("b602","分类不存在"),

    ARTICLE_EXIST("b701","文章已存在"),
    ARTICLE_NOT_EXIST("b702","文章不存在"),
    ARTICLE_LENGTH_ERROR("b501","内容过长"),

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

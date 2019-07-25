package com.acefet.blog.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @param <T>
 */
@Data
public class ResultVO<T> {

    /**返回码*/
    private String code;
    /**返回信息*/
    private String msg;
    /**数据*/
    private T data;
}



package com.acefet.blog.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassTypeVO {

    private String id;
    private String name;
    private String code;
    private Integer sort;
    @JsonProperty("classTypes")
    private List<ClassTypeVO> subClassType;
}

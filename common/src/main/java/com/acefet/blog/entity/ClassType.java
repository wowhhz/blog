package com.acefet.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ClassType {
    /**编号*/
    @Id
    private String id;
    /**分类名称*/
    private String name;
    /**编码*/
    private String code;
    /**排序*/
    private Integer sort;
    /**父类*/
    private String parentCode;


}

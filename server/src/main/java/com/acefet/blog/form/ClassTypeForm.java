package com.acefet.blog.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ClassTypeForm {

    private String id;
    /**类别名称*/
    @NotNull(message = "类别名称不能为空")
    @Length(min =1,max = 32, message = "类别名称为1-32个字符之间")
    private String name;
    /**类别编码*/
    @NotNull(message = "类别编码不能为空")
    @Length(min = 1,max = 32, message = "类别编码为1-32个字符之间")
    private String code;
    private Integer sort;
    private String parentId;
}

package com.acefet.blog.vo;

import lombok.Data;

@Data
public class ${MODEL_CODE?cap_first}VO {

<#list MODEL_FIELDS as obj>
    /**${obj["name"]}**/
    private String ${obj["code"]};
</#list>
}

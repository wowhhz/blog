package com.acefet.blog.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ${MODEL_CODE?cap_first}Form {

<#list MODEL_FIELDS as obj>
    /**${obj["name"]}**/
<#if !obj["hasNull"]>
    @NotNull(message = "${obj["name"]}不能为空")
</#if>
<#if (obj["required"]=="true")>
  <#if (obj["fieldLength"]=="")>
    @Length(min = 1, message = "${obj["name"]}必须1位以上")
  <#else>
    @Length(min = 1, max = ${obj["fieldLength"]}, message = "${obj["name"]}在1-${obj["fieldLength"]}之间")
  </#if>
</#if>
<#if (obj["type"]=="email")>
		@Email(message = "必须提供正确的Email格式")
</#if>
<#if (obj["type"]=="file")>
		private List<MultipartFile> ${obj["code"]};
<#else>
		private String ${obj["code"]};
</#if>
</#list>
}

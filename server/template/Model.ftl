package com.acefet.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class ${MODEL_CODE?cap_first} {
<#list MODEL_FIELDS as obj>
	/**${obj["name"]}**/
<#if (obj["primaryKey"]=="true")>
	@Id
</#if>
<#if (obj["saveType"]=="timestamp")>
	private Timestamp ${obj["code"]};
<#elseif (obj["saveType"]=="numeric")>
	private int ${obj["code"]};
<#else>
	private String ${obj["code"]};
</#if>
</#list>
}

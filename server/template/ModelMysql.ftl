<#assign primaryKey="" />
create table t_${MODEL_CODE} (
<#list MODEL_FIELDS as obj>
<#if (obj["primaryKey"]=="true")>
<#if (primaryKey!="")>
<#assign primaryKey='${primaryKey},' />
</#if>
<#assign primaryKey='${primaryKey}`${obj["code"]}`' />
</#if>
	`${obj["code"]}` ${obj["saveType"]}<#if (obj["saveType"]=="varchar" || obj["saveType"]=="char")>(${obj["fieldLength"]})</#if><#if (obj["saveType"]!="blob")> collate utf8_bin</#if> comment '${obj["name"]}'<#if (primaryKey!="" || obj_index+1<MODEL_FIELDS?size)>,</#if>
</#list>
<#if (primaryKey!="")>	PRIMARY KEY (${primaryKey})</#if>
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
<#if (primaryKey!="")>ALTER TABLE t_${MODEL_CODE} ADD INDEX idx_${MODEL_CODE}(`id`,`createtime`); </#if>


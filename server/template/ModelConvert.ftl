<#assign hasBlobField="" />
<#assign hasTimestamp="" />
<#assign id="id" />
<#assign tmp="" />
<#list MODEL_FIELDS as obj>
<#if (obj["saveType"]=="blob")>
<#assign hasBlobField="true" />
<#elseif (obj["saveType"]=="timestamp")>
<#assign hasTimestamp="true" />
</#if>
<#if (obj["primaryKey"]=="true" && tmp=="")>
<#assign id='${obj["code"]}' />
<#assign tmp="tmp" />
</#if>
</#list>
package com.acefet.blog.convert;

import com.acefet.blog.entity.${MODEL_CODE?cap_first};
import com.acefet.blog.form.${MODEL_CODE?cap_first}Form;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.${MODEL_CODE?cap_first}VO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ${MODEL_CODE?cap_first}Convert {

    public static ${MODEL_CODE?cap_first} ${MODEL_CODE}Form2${MODEL_CODE?cap_first}(${MODEL_CODE?cap_first}Form ${MODEL_CODE}Form){
        ${MODEL_CODE?cap_first} ${MODEL_CODE} = new ${MODEL_CODE?cap_first}();
        BeanUtils.copyProperties(${MODEL_CODE}Form,${MODEL_CODE});
        ${MODEL_CODE}.set${id?cap_first}(Util.getUUID());
<#list MODEL_FIELDS as obj>
<#if (obj["saveType"]=="timestamp")>
        ${MODEL_CODE}.set${obj["code"]?cap_first}(new Timestamp(System.currentTimeMillis()));
</#if>
</#list>
        return ${MODEL_CODE};
    }

    public static ${MODEL_CODE?cap_first}VO ${MODEL_CODE}2${MODEL_CODE?cap_first}VO(${MODEL_CODE?cap_first} ${MODEL_CODE}<#if (hasBlobField=="true")>,boolean hasContentTrans</#if>)<#if (hasBlobField=="true")> throws UnsupportedEncodingException </#if>{
        ${MODEL_CODE?cap_first}VO ${MODEL_CODE}VO = new ${MODEL_CODE?cap_first}VO();
        BeanUtils.copyProperties(${MODEL_CODE},${MODEL_CODE}VO);        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
<#list MODEL_FIELDS as obj>
<#if (obj["saveType"]=="timestamp")>
        ${MODEL_CODE}VO.set${obj["code"]?cap_first}(sdf.format(${MODEL_CODE}.get${obj["code"]?cap_first}()));
<#elseif (obj["saveType"]=="blob")>
        if(hasContentTrans)${MODEL_CODE}VO.set${obj["code"]?cap_first}(Util.getBlobField(${MODEL_CODE}.get${obj["code"]?cap_first}()));
</#if>
</#list>
        return ${MODEL_CODE}VO;
    }   

}

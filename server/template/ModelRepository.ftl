package com.acefet.blog.repository;

import com.acefet.blog.entity.${MODEL_CODE?cap_first};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ${MODEL_CODE?cap_first}Repository extends JpaRepository<${MODEL_CODE?cap_first},String>,PagingAndSortingRepository<${MODEL_CODE?cap_first},String> {

<#assign id="id" />
<#list MODEL_FIELDS as obj>
<#if (obj["primaryKey"]=="true")>
<#assign id='${obj["code"]}' />
<#break>
</#if>
</#list>
    ${MODEL_CODE?cap_first} get${MODEL_CODE?cap_first}By${id?cap_first}(String ${id});

    Page<${MODEL_CODE?cap_first}> findAll(Pageable pageable);

}

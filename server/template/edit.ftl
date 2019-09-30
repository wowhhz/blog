<div id="${MODEL_CODE}-form-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="${MODEL_CODE}-form-modal" aria-hidden="true" data-backdrop="static" data-keybrard="true">
    <form id="${MODEL_CODE}-edit-form" action="" method="">
<#list MODEL_FIELDS as obj>
<#if (obj["type"]=="hidden")>
				<input type="hidden" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" value="" />
</#if>
</#list>
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">${MODEL_NAME}</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
<#list MODEL_FIELDS as obj>
  <#if (obj["type"]!="hidden")>   
    <#if (obj["type"]=="text")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" placeholder="请输入${obj["name"]}"<#if (obj["required"]=="true")> required</#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if> />
                        </div>
                    </div>
    <#elseif (obj["type"]=="password")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="password" class="form-control m-b-5" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" placeholder="请输入${obj["name"]}"<#if (obj["required"]=="true")> required</#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if> />
                        </div>
                    </div>
    <#elseif (obj["type"]=="email")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="email" class="form-control m-b-5" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" placeholder="请输入${obj["name"]}"<#if (obj["required"]=="true")> required</#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if> />
                        </div>
                    </div>
    <#elseif (obj["type"]=="date")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input class="form-control m-b-5 bootstrap-datepicker" type="text" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" placeholder="请输入${obj["name"]}" value=""<#if (obj["required"]=="true")> required</#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if> />
                        </div>
                    </div>
    <#elseif (obj["type"]=="time")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5 bootstrap-timepicker" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" placeholder="请输入${obj["name"]}" value=""<#if (obj["required"]=="true")> required</#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if> />
                        </div>
                    </div>
    <#elseif (obj["type"]=="select")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <select class="form-control m-b-5" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" placeholder="请选择${obj["name"]}"<#if (obj["required"]=="true")> required</#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if>>
    <#if (obj["parameterType"]=="select")>
                                ${r"<#list "}${obj["selectConfig"]}${r" as item>"}
    <#elseif (obj["parameterType"]=="list" || obj["parameterType"]=="sql")>
                                ${r"<#list "}${obj["code"]}List${r" as item>"}
    <#else>
                                ${r"<#list "}${obj["code"]}List${r" as item>"}
    </#if>
<#noparse>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
</#noparse>
                            </select>
                        </div>
                    </div>
    <#elseif (obj["type"]=="radio")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-9">
                            <div class="control-group">
    <#if (obj["parameterType"]=="select")>
                                ${r"<#list "}${obj["selectConfig"]}${r" as item>"}
    <#elseif (obj["parameterType"]=="list" || obj["parameterType"]=="sql")>
                                ${r"<#list "}${obj["code"]}List${r" as item>"}
    <#else>
                                ${r"<#list "}${obj["code"]}List${r" as item>"}
    </#if>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}<#noparse>${item.code}" value="${item.code}"</#noparse><#if (obj["required"]=="true")><#noparse><#if (item?index==0)> required</#if></#noparse></#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if> />
                                        <label class="form-check-label" for="${MODEL_CODE}-form-${obj["code"]}<#noparse>${item.code}</#noparse>"><#noparse>${item.name}</#noparse></label>
                                    </div>
<#noparse>
                                </#list>
</#noparse>
                            </div>
                        </div>
                    </div>
    <#elseif (obj["type"]=="checkbox")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-9">
                            <div class="control-group">
    <#if (obj["parameterType"]=="select")>
                                ${r"<#list "}${obj["selectConfig"]}${r" as item>"}
    <#elseif (obj["parameterType"]=="list" || obj["parameterType"]=="sql")>
                                ${r"<#list "}${obj["code"]}List${r" as item>"}
    <#else>
                                ${r"<#list "}${obj["code"]}List${r" as item>"}
    </#if>
                                    <div class="form-check">
                                        <input class="form-check-input m-b-5" type="checkbox" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}<#noparse>${item.code}" value="${item.code}"</#noparse><#if (obj["required"]=="true")><#noparse><#if (item?index==0)> required</#if></#noparse></#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if> />
                                        <label class="form-check-label" for="${MODEL_CODE}-form-${obj["code"]}<#noparse>${item.code}</#noparse>"><#noparse>${item.name}</#noparse></label>
                                    </div>
<#noparse>
                                </#list>
</#noparse>
                            </div>
                        </div>
                    </div>
    <#elseif (obj["type"]=="textarea")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-9">
                            <textarea rows="6" class="form-control m-b-5" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" placeholder="请输入${obj["name"]}"<#if (obj["required"]=="true")> required</#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if>></textarea>
                        </div>
                    </div>
    <#elseif (obj["type"]=="file")>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-9">
                            <input multiple="" class="form-control m-b-5" type="file" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}"<#if (obj["required"]=="true")> required</#if><#if (obj["enabled"]=="false")> readonly="readonly"</#if> />
                            <div id="${MODEL_CODE}-form-${obj["code"]}-fileList" class="well d-none">
                                <h4 class='green smaller lighter'>文件清单</h4>
                                <div id="${MODEL_CODE}-form-${obj["code"]}-fileList-div" class="list-group">
                                </div>
                            </div>
                        </div>
                    </div>
    <#elseif (obj["type"]=="editor" || obj["type"]=="ckeditor")>
                    <!-- bootstrap 模态对话框会导致ckeditor无法修改图片，tabindex=-1去除以后正常但模态对话框的ESC健无效-->
                    <!-- modal dialog attribute tabindex="-1" cause of ckeditor edit and ESC invalid-->
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">${obj["name"]}</label>
                        <div class="col-md-9">
                            <textarea class="ckeditor" name="${obj["code"]}" id="${MODEL_CODE}-form-${obj["code"]}" row="10"></textarea>
                        </div>
                    </div>
    </#if>
  </#if>
</#list>


                </div>

                <div class="modal-footer">
                    <button class="btn btn-white" data-dismiss="modal">
                        取消
                    </button>

                    <button class="btn btn-primary" id="${MODEL_CODE}-form-save-btn">
                        保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
<#assign hasEditorField="" />
<#assign hasDateField="" />
<#assign hasTimeField="" />
<#assign hasFileField="" />
<#assign id="id" />
<#assign tmp="" />
<#assign fileIds="" />
<#list MODEL_FIELDS as obj>
  <#if (obj["type"]=="ckeditor" || obj["type"]=="editor")>
    <#assign hasEditorField="true" />
  <#elseif (obj["type"]=="date")>
    <#assign hasDateField="true" />
  <#elseif (obj["type"]=="time")>
    <#assign hasTimeField="true" />
  <#elseif (obj["type"]=="file")>
    <#assign hasFileField="true" />
    <#if (fileIds!="")>
      <#assign fileIds='${fileIds},}' />
    </#if>
    <#assign fileIds='${fileIds}${obj["code"]}' />
  </#if>
  <#if (obj["primaryKey"]=="true" && tmp=="")>
    <#assign id='${obj["code"]}' />
    <#assign tmp="tmp" />
  </#if>
</#list>
<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#noparse>
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datatable_header.ftl" />
</#noparse>
<#if (hasDateField=="true" || hasTimeField=="true")>
<#noparse>
<#include "../public/include_datetimepicker_header.ftl" />
</#noparse>
</#if>
<!-- ================== END PAGE LEVEL STYLE ================== -->


<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">首页</a></li>
	<li class="breadcrumb-item"><a href="#${MODEL_CODE}/list">${MODEL_NAME}</a></li>
	<li class="breadcrumb-item active">${MODEL_NAME}</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">${MODEL_NAME} <!--<small>header small text goes here...</small>--></h1>
<!-- end page-header -->

<!-- begin panel -->
<div class="panel panel-inverse">
	<!-- begin panel-heading -->
	<div class="panel-heading">
<#noparse>	
		<#include "../public/include_panel_heading_btn.ftl" />
</#noparse>
		<h4 class="panel-title">数据列表</h4>
	</div>
	<!-- end panel-heading -->
	<!-- begin panel-body -->
	<div class="panel-body">
		<input type="hidden" id="${MODEL_CODE}TableAllSelectIds" name="${MODEL_CODE}TableAllSelectIds" value="" />
		<table id="${MODEL_CODE}_table" class="table table-striped table-bordered dataTable"><!-- table-hover-->
			<thead>
				<tr>
					<th class=" with-checkbox" style="width:17px" data-orderable="false">
						<input type="checkbox" class="" id="table_select_checkbox" />
					</th>
<#list MODEL_FIELDS as obj>
<#if (!obj["tableShow"]?exists || obj["tableShow"]!="false")> 
<#if (obj["type"]!="editor" && obj["type"]!="ckeditor")>
					<th class="text-nowrap">${obj["name"]}</th>
</#if>
</#if>
</#list>
				</tr>
			</thead>
			<tbody>
			</tbody>

		</table>

	</div>

<#noparse>
    <div id="modal_dialog_div">
        <#include "edit.ftl" />
        <#include "view.ftl" />
    </div>
</#noparse>
	<!-- end panel-body -->
</div>
<!-- end panel -->


<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<#noparse>
<#include "../public/include_datatable_footer.ftl" />
</#noparse>
<#if (hasDateField=="true" || hasTimeField=="true")>
<#noparse>
<#include "../public/include_datetimepicker_footer.ftl" />
</#noparse>
</#if>
<#if (hasEditorField=="true")>
<#noparse>
<script src="../assets/plugins/ckeditor/ckeditor.js"></script>
</#noparse>
</#if>

<script>
	App.setPageTitle('Blog Admin | ${MODEL_NAME}');
	App.restartGlobalFunction();
	
<#list MODEL_FIELDS as obj>
  <#if (obj["type"]=="select" || obj["type"]=="checkbox" || obj["type"]=="radio")>
    <#if (obj["parameterType"]=="select")>
	var ${obj["selectConfig"]} = new Array(
${r"<#list "}${obj["selectConfig"]}${r" as item>"}
<#noparse>
		{code:"${item.code}", name:"${item.name}"},		
</#list>
</#noparse>
	);
    </#if>
	  <#if (obj["parameterType"]=="list" || obj["parameterType"]=="sql")>
	var ${obj["code"]}List = new Array(
${r"<#list "}${obj["code"]}List${r" as item>"}
<#noparse>
		{code:"${item.code}", name:"${item.name}"},		
</#list>
</#noparse>
	);
	  </#if>
	</#if>
</#list>

	var table;
<#list MODEL_FIELDS as obj>
  <#if (obj["type"]=="ckeditor" || obj["type"]=="editor")>
	var ${obj["code"]}Editor;
  </#if>
</#list>
    $.getScript('../assets/plugins/gritter/js/jquery.gritter.min.js').done(function() {
		$.when(

            $.Deferred(function( deferred ){
                $(deferred.resolve);
            })
		).done(function() {

			initElement();

			function initElement(){
				table = initDataTables(getDataTablesPara());
<#list MODEL_FIELDS as obj>
  <#if (obj["type"]=="date")>
				initDatepicker("${MODEL_CODE}-form-${obj["code"]}");
  <#elseif (obj["type"]=="time")>
				initTimepicker("${MODEL_CODE}-form-${obj["code"]}");
  <#elseif (obj["type"]=="ckeditor" || obj["type"]=="editor")>
				${obj["code"]}Editor = initCkeditor("${MODEL_CODE}-form-${obj["code"]}");
  </#if>
</#list>
			}

			function getDataTablesPara(){
				var tableoptions = {
					tableId: "${MODEL_CODE}_table",
					ajax: {
						"url": "../admin/${MODEL_CODE}/query",
					},
					columns: [
						{
							data: null, render: createDataTableTdCheckbox,
						},
<#list MODEL_FIELDS as obj>	  
  <#if (!obj["tableShow"]?exists || obj["tableShow"]!="false")> 
    <#if (obj["type"]!="editor" && obj["type"]!="ckeditor")>
						{
							data: null, name:"${obj["code"]}",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.${id} + '\')">'
    	<#if (obj["type"]=="select" || obj["type"]=="checkbox" || obj["type"]=="radio")>
      	<#if (obj["parameterType"]=="select")>
											+getCurrentComboText(${obj["selectConfig"]},value,"<br />")+'</a>';
				<#elseif (obj["parameterType"]=="list" || obj["parameterType"]=="sql")>
											+getCurrentComboText(${obj["code"]}List,data.${obj["code"]},"<br />")+'</a>';
				<#else>
											+ data.${obj["code"]} + '</a>';
				</#if>
    	<#else>
    								+ data.${obj["code"]} + '</a>';
    	</#if>
							}
						},
		</#if>
  </#if>
</#list>
					],
					buttons: [
						{
							text: '增加',
							className: 'btn btn-default',
							action: ${MODEL_CODE}Add,
						},
						{
							text: '修改',
							className: 'btn btn-default',
							action: ${MODEL_CODE}Edit,

						},
						{
							text: '删除',
							className: 'btn btn-default',
							action: ${MODEL_CODE}Del,
						},
					],

				}
				return tableoptions;
			}


			function ${MODEL_CODE}Add() {

				clearForm("#${MODEL_CODE}-edit-form");
<#list MODEL_FIELDS as obj>
  <#if (obj["type"]=="ckeditor" || obj["type"]=="editor")>
				${obj["code"]}Editor.setData("");
  </#if>
</#list>
				$("#${MODEL_CODE}-form-modal").modal();

			}


			function ${MODEL_CODE}Edit() {
				clearForm("#${MODEL_CODE}-edit-form");
<#list MODEL_FIELDS as obj>
  <#if (obj["type"]=="ckeditor" || obj["type"]=="editor")>
				${obj["code"]}Editor.setData("");
  </#if>
</#list>
				var id = getCheckboxIdByTable("${MODEL_CODE}_table");
				if(id==null)return;
				setAjaxDataToForm({
					url:"${MODEL_CODE}/edit?${id}="+${id},
					formId: "${MODEL_CODE}-edit-form",
					modalId:"${MODEL_CODE}-form-modal",

				}
<#if (hasEditorField=="true")>
				, function(data){
	<#list MODEL_FIELDS as obj>
    <#if (obj["type"]=="ckeditor" || obj["type"]=="editor")>
					setEditorHtml(${obj["code"]}Editor,data.${obj["code"]});
    </#if>
  </#list>
				}
</#if>
				)
			}

			function ${MODEL_CODE}Del() {
				deleteByTableIds({
					tableId:"${MODEL_CODE}_table",
					table:table,
					url: "${MODEL_CODE}/del",
				});
			}

			$('table th input:checkbox').on('click', allChecked);

			$("#${MODEL_CODE}-form-save-btn").on("click", function () {
				var result = postData({
					url: "${MODEL_CODE}/save",
					table: table,
					btnId: "${MODEL_CODE}-form-save-btn",
					formId: "${MODEL_CODE}-edit-form",
					modalId: "${MODEL_CODE}-form-modal",
				}
<#if (hasEditorField=="true")>
				, function(formData){
	<#list MODEL_FIELDS as obj>
    <#if (obj["type"]=="ckeditor" || obj["type"]=="editor")>
      <#if (obj["required"]=="true")>
					if (${obj["code"]}Editor.getData().trim() == "") {
						webAlert("表单错误!", "请输入${obj["name"]}");
						return false;
					}
			</#if>
					formData.set("${obj["code"]}", ${obj["code"]}Editor.getData());
    </#if>
  </#list>
					return true;
				}
</#if>
				);
			});

			$("#${MODEL_CODE}-edit-form").validate(getValidateParameter());

			showView = function(id) {
				showData({
					url: "${MODEL_CODE}/view?${id}="+${id},
					inputIdPre: "${MODEL_CODE}-view-",
<#if (hasFileField=="true")>
					fileIds: "${fileIds}",
</#if>
					modalId: "${MODEL_CODE}-view-modal",
					trans:[
<#list MODEL_FIELDS as obj>
  <#if (obj["type"]=="select" || obj["type"]=="checkbox" || obj["type"]=="radio")>
  					{
  						name: "${obj["code"]}",
    <#if (obj["parameterType"]=="select")>
							items: ${obj["selectConfig"]},	
		<#elseif (obj["parameterType"]=="list" || obj["parameterType"]=="sql")>
							items: ${obj["code"]}List,
		<#else>
							items: ${obj["code"]}List,				
		</#if>
						},
  </#if>
</#list>
						]
				}
<#if (hasEditorField=="true")>
				,function(data){
  <#list MODEL_FIELDS as obj>
    <#if (obj["type"]=="ckeditor" || obj["type"]=="editor")>
					$("#${MODEL_CODE}-view-${obj["code"]}").html(data.${obj["code"]});
		</#if>
	</#list>
				}
</#if>
				)
			};
			
			
			

		
		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

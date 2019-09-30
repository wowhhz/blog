<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datatable_header.ftl" />
<!-- ================== END PAGE LEVEL STYLE ================== -->


<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">首页</a></li>
	<li class="breadcrumb-item"><a href="#userFile/list">文件</a></li>
	<li class="breadcrumb-item active">文件</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">文件 <!--<small>header small text goes here...</small>--></h1>
<!-- end page-header -->

<!-- begin panel -->
<div class="panel panel-inverse">
	<!-- begin panel-heading -->
	<div class="panel-heading">
		<#include "../public/include_panel_heading_btn.ftl" />
		<h4 class="panel-title">数据列表</h4>
	</div>
	<!-- end panel-heading -->
	<!-- begin panel-body -->
	<div class="panel-body">
		<input type="hidden" id="fileTableAllSelectIds" name="fileTableAllSelectIds" value="" />
		<table id="file_table" class="table table-striped table-bordered dataTable"><!-- table-hover-->
			<thead>
				<tr>
					<th class=" with-checkbox" style="width:17px" data-orderable="false">
						<input type="checkbox" class="" id="table_select_checkbox" />
					</th>
					<th class="text-nowrap">文件名</th>
					<th class="text-nowrap">文件描述</th>
					<th class="text-nowrap">文件类型</th>
					<th class="text-nowrap">文件大小</th>
					<th class="text-nowrap">文件状态</th>
					<th class="text-nowrap">上传时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>

		</table>

	</div>

    <div id="modal_dialog_div">
        <#include "edit.ftl" />
        <#include "view.ftl" />
    </div>
	<!-- end panel-body -->
</div>
<!-- end panel -->


<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<#include "../public/include_datatable_footer.ftl" />

<script>
	App.setPageTitle('Blog Admin | 文件');
	App.restartGlobalFunction();
	
	var statusList = new Array(
<#list statusList as item>
		{code:"${item.code}", name:"${item.name}"},		
</#list>
	);

	var table;
    $.getScript('../assets/plugins/gritter/js/jquery.gritter.min.js').done(function() {
		$.when(

            $.Deferred(function( deferred ){
                $(deferred.resolve);
            })
		).done(function() {

			initElement();

			function initElement(){
				table = initDataTables(getDataTablesPara());
			}

			function getDataTablesPara(){
				var tableoptions = {
					tableId: "file_table",
					ajax: {
						"url": "../admin/userFile/query",
					},
					columns: [
						{
							data: null, render: createDataTableTdCheckbox,
						},
						{
							data: null, name:"fileName",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.fileName + '</a>';
							}
						},
						{
							data: null, name:"fileDescription",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.fileDescription + '</a>';
							}
						},
						{
							data: null, name:"type",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.type + '</a>';
							}
						},
						{
							data: null, name:"fileSize",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ getFileSizeStr(data.fileSize) + '</a>';
							}
						},
						{
							data: null, name:"status",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
											+getCurrentComboText(statusList,data.status,"<br />")+'</a>';
							}
						},
						{
							data: null, name:"createTime",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.createTime + '</a>';
							}
						},
					],
					buttons: [
						{
							text: '增加',
							className: 'btn btn-default',
							action: fileAdd,
						},
						{
							text: '修改',
							className: 'btn btn-default',
							action: fileEdit,

						},
						{
							text: '删除',
							className: 'btn btn-default',
							action: fileDel,
						},
					],

				}
				return tableoptions;
			}


			function fileAdd() {

				clearForm("#userFile-edit-form");
				$("#userFile-form-modal").modal();

			}


			function fileEdit() {
				clearForm("#userFile-edit-form");
				var id = getCheckboxIdByTable("file_table");
				if(id==null)return;
				setAjaxDataToForm({
					url:"userFile/edit?id="+id,
					formId: "userFile-edit-form",
					modalId:"userFile-form-modal",

				}
				)
			}

			function fileDel() {
				deleteByTableIds({
					tableId:"file_table",
					table:table,
					url: "userFile/del",
				});
			}

			$('table th input:checkbox').on('click', allChecked);

			$("#userFile-form-save-btn").on("click", function () {
				var result = postData({
					url: "userFile/save",
					table: table,
					btnId: "userFile-form-save-btn",
					formId: "userFile-edit-form",
					modalId: "userFile-form-modal",
				}
				);
			});

			$("#userFile-edit-form").validate(getValidateParameter());

			showView = function(id) {
				showData({
					url: "userFile/view?id="+id,
					inputIdPre: "userFile-view-",
					modalId: "userFile-view-modal",
					trans:[
  					{
  						name: "status",
							items: statusList,
						},
						]
				},function(data){
					$("#userFile-view-fileSize").html(getFileSizeStr(data.fileSize));
					var types = "txt,ftl,png,jpg,gif,pdf,xml".split(",");
					var hasPreview = false;
					for(var i=0;i<types.length;i++){
						if(data.fileName.lastIndexOf(types[i])>1 || data.type.lastIndexOf(types[i])>1){
							hasPreview = true;
							break;
						}
					}
					if(hasPreview){
						$("#preview-btn").removeClass("disabled");
						$("#preview-btn").removeAttr("disabled");
					}else{
						$("#preview-btn").addClass("disabled");
						$("#preview-btn").attr("disabled","disabled");
					}

				}
				)
			};
			
			$("#preview-btn").on("click",function(){
				var win = window.open('${urlPath}'+$("#userFile-view-filePath").text(),'_blank');
				win.focus();
				return false;
			});

			$("#download-btn").on("click",function(){
				var win = window.open("userFile/download?filePath=/"+$("#userFile-view-filePath").text().replace("\\","/"),"_blank");
				win.focus();
				return false;
			})
			

		
		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

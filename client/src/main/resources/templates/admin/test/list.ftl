<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#include "../public/include_datatable_header.ftl" />
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datetimepicker_header.ftl" />
<!-- ================== END PAGE LEVEL STYLE ================== -->


<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">Home</a></li>
	<li class="breadcrumb-item"><a href="#test/list">test</a></li>
	<li class="breadcrumb-item active">test</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">test <!--<small>header small text goes here...</small>--></h1>
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
		<input type="hidden" id="testTableAllSelectIds" name="testTableAllSelectIds" value="" />
		<table id="test_table" class="table table-striped table-bordered dataTable"><!-- table-hover-->
			<thead>
				<tr>
					<th class=" with-checkbox" style="width:17px" data-orderable="false">
						<input type="checkbox" class="" id="table_select_checkbox" />
					</th>
					<th class="text-nowrap">名称</th>
					<th class="text-nowrap">下拉项</th>
					<th class="text-nowrap">单选</th>
					<th class="text-nowrap">多选</th>
					<th class="text-nowrap">大文本</th>
					<th class="text-nowrap">文件</th>
					<th class="text-nowrap">内容</th>
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
<#include "../public/include_datetimepicker_footer.ftl" />

<script src="../assets/plugins/ckeditor/ckeditor.js"></script>


<script>
	App.setPageTitle('Blog Admin | Managed Tables Test');
	App.restartGlobalFunction();

	var selectr = new Array(
	    <#list selectConf as item>
		{code:"${item.code}", name:"${item.name}"},
	    </#list>
	);

	var radio = new Array(
	<#list radioConf as item>
	{code:"${item.code}", name:"${item.name}"},
	</#list>
	);
	var checkbox = new Array(
	<#list checkboxConf as item>
	{code:"${item.code}", name:"${item.name}"},
	</#list>
	);

	var table;
	var contentEditor;
    $.getScript('../assets/plugins/gritter/js/jquery.gritter.min.js').done(function() {
		$.when(

            $.Deferred(function( deferred ){
                $(deferred.resolve);
            })
		).done(function() {

			initElement();

			function initElement(){
				table = initDataTables(getDataTablesPara());
				initDatepicker("test-form-dater");
				initTimepicker("test-form-timer");
				contentEditor = initCkeditor("test-form-content");
			}

			function getDataTablesPara(){
				var tableoptions = {
					tableId: "test_table",
					ajax: {
						"url": "../admin/test/query",
					},
					columns: [
						{
							data: null, render: createDataTableTdCheckbox,
						},
						{
							data: null, name:"name",render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">' + data.name + '</a>';
							}
						},
						{
							data: null,name:"selectr", render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">' + getCurrentComboText(selectr,data.selectr,"<br />")+ '</a>';
							}
						},
						{
							data: null,name:"radio", render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">' + getCurrentComboText(radio,data.radio,"<br />") + '</a>';
							}
						},
						{
							data: null,name:"checkbox", render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">' + getCurrentComboText(checkbox,data.checkbox,"<br />") + '</a>';
							}
						},
						{
							data: null,name:"textarea", render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">' + data.textarea + '</a>';
							}
						},
						{
							data: null,name:"userFile", render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">' + data.userFile + '</a>';
							}
						},
						{
							data: null,name:"content", render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">' + data.content + '</a>';
							}
						}
					],
					buttons: [
						{
							text: '增加',
							className: 'btn btn-default',
							action: testmodelAdd,
						},
						{
							text: '修改',
							className: 'btn btn-default',
							action: testmodelEdit,

						},
						{
							text: '删除',
							className: 'btn btn-default',
							action: testmodelDel,
						},
					],

				}
				return tableoptions;
			}


			function testmodelAdd() {

				clearForm("#test-edit-form");
				contentEditor.setData("");

				//setElementChecked("radio1","1");
				//setElementChecked("a7","1,2");

				$("#test-form-modal").modal();

			}


			function testmodelEdit() {
				clearForm("#test-edit-form");
				contentEditor.setData("");
				var id = getCheckboxIdByTable("test_table");
				if(id==null)return;
				setAjaxDataToForm({
					url:"test/edit?id="+id,
					formId: "test-edit-form",
					modalId: "test-form-modal",
				}, function(data){
					setEditorHtml(contentEditor,data.content);
				})
			}

			function testmodelDel() {
				deleteByTableIds({
					tableId:"test_table",
					table:table,
					url: "test/del",
				});

			}

			$('table th input:checkbox').on('click', allChecked);

			$("#test-form-save-btn").on("click", function () {
				var result = postData({
					url: "test/save",
					table: table,
					btnId: "test-form-save-btn",
					formId: "test-edit-form",
					modalId: "test-form-modal",
				},function(formData){
					if (contentEditor.getData().trim() == "") {
						webAlert("表单错误!", "请输入编辑内容");
						return false;
					}
					formData.set("content", contentEditor.getData());
					return true;
				});

			});

			$("#test-edit-form").validate(getValidateParameter());

			showView = function(id) {
				showData({
					url: "test/view?id="+id,
					inputIdPre: "test-view-",
					fileIds: "userFile",
					modalId: "test-view-modal",
					trans:[
						{
							name: "selectr",
							items: selectr,
						},
						{
							name: "radio",
							items: radio,
						},
						{
							name: "checkbox",
							items: checkbox,
						}]
				},function(data){
					$("#test-view-content").html(data.content);
				})
			};

		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

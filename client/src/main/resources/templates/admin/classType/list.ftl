<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datatable_header.ftl" />
<!-- ================== END PAGE LEVEL STYLE ================== -->


<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">首页</a></li>
	<li class="breadcrumb-item"><a href="#classType/list">分类</a></li>
	<li class="breadcrumb-item active">分类</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">分类 <!--<small>header small text goes here...</small>--></h1>
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
		<input type="hidden" id="classTypeTableAllSelectIds" name="classTypeTableAllSelectIds" value="" />
		<table id="classType_table" class="table table-striped table-bordered dataTable"><!-- table-hover-->
			<thead>
				<tr>
					<th class=" with-checkbox" style="width:17px" data-orderable="false">
						<input type="checkbox" class="" id="table_select_checkbox" />
					</th>
					<th class="text-nowrap">名称</th>
					<th class="text-nowrap">编码</th>
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
	App.setPageTitle('Blog Admin | 分类');
	App.restartGlobalFunction();
	

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
					paging: false,
					ordering: false,
					searching: false,
					info: false,
					tableId: "classType_table",
					ajax: {
						"url": "../admin/classType/query",
					},
					columns: [
						{
							data: null, render: createDataTableTdCheckbox,
						},
						{
							data: null, name:"name",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.name + '</a>';
							}
						},
						{
							data: null, name:"code",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.code + '</a>';
							}
						},
					],
					buttons: [
						{
							text: '增加',
							className: 'btn btn-default',
							action: classTypeAdd,
						},
						{
							text: '修改',
							className: 'btn btn-default',
							action: classTypeEdit,

						},
						{
							text: '删除',
							className: 'btn btn-default',
							action: classTypeDel,
						},
					],

				}
				return tableoptions;
			}


			function classTypeAdd() {

				clearForm("#classType-edit-form");
				$("#classType-form-modal").modal();

			}


			function classTypeEdit() {
				clearForm("#classType-edit-form");
				var id = getCheckboxIdByTable("classType_table");
				if(id==null)return;
				setAjaxDataToForm({
					url:"classType/edit?id="+id,
					formId: "classType-edit-form",
					modalId:"classType-form-modal",

				}
				)
			}

			function classTypeDel() {
				deleteByTableIds({
					tableId:"classType_table",
					table:table,
					url: "classType/del",
				});
			}

			$('table th input:checkbox').on('click', allChecked);

			$("#classType-form-save-btn").on("click", function () {
				var result = postData({
					url: "classType/save",
					table: table,
					btnId: "classType-form-save-btn",
					formId: "classType-edit-form",
					modalId: "classType-form-modal",
				}
				);
			});

			$("#classType-edit-form").validate(getValidateParameter());

			showView = function(id) {
				showData({
					url: "classType/view?id="+id,
					inputIdPre: "classType-view-",
					modalId: "classType-view-modal",
					trans:[
						]
				}
				)
			};
			
			
			

		
		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

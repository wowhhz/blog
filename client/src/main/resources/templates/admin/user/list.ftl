<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datatable_header.ftl" />
<!-- ================== END PAGE LEVEL STYLE ================== -->


<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">首页</a></li>
	<li class="breadcrumb-item"><a href="#user/list">用户</a></li>
	<li class="breadcrumb-item active">用户</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">用户 <!--<small>header small text goes here...</small>--></h1>
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
		<input type="hidden" id="userTableAllSelectIds" name="userTableAllSelectIds" value="" />
		<table id="user_table" class="table table-striped table-bordered dataTable"><!-- table-hover-->
			<thead>
				<tr>
					<th class=" with-checkbox" style="width:17px" data-orderable="false">
						<input type="checkbox" class="" id="table_select_checkbox" />
					</th>
					<th class="text-nowrap" style="width:40px;">头像</th>
					<th class="text-nowrap">名字</th>
					<th class="text-nowrap">用户名</th>
					<th class="text-nowrap">状态</th>
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
	App.setPageTitle('Blog Admin | 用户');
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
                    paging: false,
                    ordering: false,
                    searching: false,
                    info: false,
					tableId: "user_table",
					ajax: {
						"url": "../admin/user/query",
					},
					columns: [
						{
							data: null, render: createDataTableTdCheckbox,
						},
						{
							data: null, name:"icon",render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
										+ '<div class="iconImage"><img src="'+(data.icon=='' ? '../assets/img/user/user.png' : '${urlPath}'+ data.icon) + '"></img></div></a>';
							}
						},
						{
							data: null, name:"name",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.name + '</a>';
							}
						},
						{
							data: null, name:"username",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.username + '</a>';
							}
						},
						{
							data: null, name:"status",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
											+getCurrentComboText(statusList,data.status,"<br />")+'</a>';
							}
						},

					],
					buttons: [
						{
							text: '增加',
							className: 'btn btn-default',
							action: userAdd,
						},
						{
							text: '修改',
							className: 'btn btn-default',
							action: userEdit,

						},
						{
							text: '删除',
							className: 'btn btn-default',
							action: userDel,
						},
					],

				}
				return tableoptions;
			}


			function userAdd() {

				clearForm("#user-edit-form");
				$("#user-form-modal").modal();

			}


			function userEdit() {
				clearForm("#user-edit-form");
				var id = getCheckboxIdByTable("user_table");
				if(id==null)return;
				setAjaxDataToForm({
					url:"user/edit?id="+id,
					formId: "user-edit-form",
					modalId:"user-form-modal",
				},function(data){
					$("#user-form-icon-show").attr("src",'${urlPath}'+ data.icon);
				}
				)
			}

			function userDel() {
				deleteByTableIds({
					tableId:"user_table",
					table:table,
					url: "user/del",
				});
			}

			$('table th input:checkbox').on('click', allChecked);

			$("#user-form-save-btn").on("click", function () {
				var result = postData({
					url: "user/save",
					table: table,
					btnId: "user-form-save-btn",
					formId: "user-edit-form",
					modalId: "user-form-modal",
				}
				);
			});

			$("#user-edit-form").validate(getValidateParameter());

			showView = function(id) {
				showData({
					url: "user/view?id="+id,
					inputIdPre: "user-view-",
					modalId: "user-view-modal",
					trans:[
  					{
  						name: "status",
							items: statusList,
						},
						]
				},function(data){
					$("#user-view-icon").html('<img class="userImage" src="'+(data.icon=='' ? '../assets/img/user/user.png' : '${urlPath}'+ data.icon) + '"></img>');
				}
				)
			};
			
			
			

		
		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

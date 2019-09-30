<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datatable_header.ftl" />

<link href="../assets/plugins/jquery-tag-it/css/jquery.tagit.css" rel="stylesheet" />
<link href="../assets/plugins/ckeditor/plugins/codesnippet/lib/highlight/styles/solarized_light.css" rel="stylesheet" />
<!-- ================== END PAGE LEVEL STYLE ================== -->


<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">首页</a></li>
	<li class="breadcrumb-item"><a href="#article/list">文章</a></li>
	<li class="breadcrumb-item active">文章</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">文章 <!--<small>header small text goes here...</small>--></h1>
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
		<input type="hidden" id="articleTableAllSelectIds" name="articleTableAllSelectIds" value="" />

		<table id="article_table" class="table table-striped table-bordered dataTable"><!-- table-hover-->
			<thead>
				<tr>
					<th class=" with-checkbox" style="width:17px" data-orderable="false">
						<input type="checkbox" class="" id="table_select_checkbox" />
					</th>
					<th class="text-nowrap">分类</th>
					<th class="text-nowrap">标题</th>
					<th class="text-nowrap">标签</th>
					<th class="text-nowrap">阅读数</th>
					<th class="text-nowrap">点赞数</th>
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

<script src="../assets/plugins/jquery-tag-it/js/tag-it.min.js"></script>
<script src="../assets/plugins/ckeditor/ckeditor.js"></script>
<script src="../assets/plugins/ckeditor/plugins/codesnippet/lib/highlight/highlight.pack.js"></script>


<script>
	App.setPageTitle('Blog Admin | 文章');
	App.restartGlobalFunction();
	

	var classTypeCodeList = new Array(
<#list classTypeCodeList as item>
		{code:"${item.code}", name:"${item.name}"},
</#list>
	);

	var hasCommentList = new Array(
<#list hasCommentList as item>
		{code:"${item.code}", name:"${item.name}"},
</#list>
	);

	var table;
	var contentEditor;
	var tags = ["java","javaScript","dataBase","web","mySQL","life"];

    $.getScript('../assets/plugins/gritter/js/jquery.gritter.min.js').done(function() {
		$.when(

            $.Deferred(function( deferred ){
                $(deferred.resolve);
            })
		).done(function() {

			initElement();

			function initElement(){
				table = initDataTables(getDataTablesPara());
				contentEditor = initCkeditor("article-form-content");
                //hljs.initHighlightingOnLoad();
                $("#tag1").tagit({availableTags:tags});
            }

			function getTableFilterHtml(){
				var html = ' &nbsp;分类过滤: <select class="form-control" id="table_classType" onChange="javascript:table.ajax.reload();">'
						+'<option value="">全部</option>'
						<#list classTypeCodeList as item>
						+'<option value="${item.code}">${item.name}</option>'
						</#list>
						+'</select> ';
				return html;
			}

			function getDataTablesPara(){
				var tableoptions = {
					tableId: "article_table",
					dom: 'B<"toolbar pull-right">frtip',
					fnInitComplete: function(){
						$('div.toolbar').html(getTableFilterHtml());
					},
					ajax: {
						"url": "../admin/article/query",
						"data": function(d){
							var classType = $("#table_classType").val();
							if((classType+"")=="undefined")classType="";
							d.classType = classType;
						},
					},
					columns: [
						{
							data: null, render: createDataTableTdCheckbox,
						},
						{
							data: null, name:"classTypeCode",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
											+ data.classTypeCode + '</a>';
							}
						},
						{
							data: null, name:"title",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.title + '</a>';
							}
						},
						{
							data: null, name:"flags",render: function (data, type, row) {
							return setTagLabel(data.flags,"label-lime");
							}
						},
						{
							data: null, name:"readNum",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.readNum + '</a>';
							}
						},
						{
							data: null, name:"likeNum",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.likeNum + '</a>';
							}
						},
					],
					buttons: [
						{
							text: '增加',
							className: 'btn btn-default',
							action: articleAdd,
						},
						{
							text: '修改',
							className: 'btn btn-default',
							action: articleEdit,

						},
						{
							text: '删除',
							className: 'btn btn-default',
							action: articleDel,
						},
					],

				}
				return tableoptions;
			}

			function articleAdd() {

				clearForm("#article-edit-form");
				clearTagit("tag1");
				contentEditor.setData("");
                $(".cke_contents").css("height","500px");
                $("#article-form-modal").modal();

			}


			function articleEdit() {
				clearForm("#article-edit-form");
				clearTagit("tag1");
				contentEditor.setData("");
				var id = getCheckboxIdByTable("article_table");
				if(id==null)return;
				setAjaxDataToForm({
					url:"article/edit?id="+id,
					formId: "article-edit-form",
					modalId:"article-form-modal",

				}
				, function(data){
                    setTagit("tag1",$("#article-form-flags").val());
					setEditorHtml(contentEditor,data.content);
				}
				)
			}

			function articleDel() {
				deleteByTableIds({
					tableId:"article_table",
					table:table,
					url: "article/del",
				});
			}

			$('table th input:checkbox').on('click', allChecked);

			$("#article-form-save-btn").on("click", function () {
				var result = postData({
					url: "article/save",
					table: table,
					btnId: "article-form-save-btn",
					formId: "article-edit-form",
					modalId: "article-form-modal",
				}
				, function(formData){
					if (contentEditor.getData().trim() == "") {
						webAlert("表单错误!", "请输入内容");
						return false;
					}
                    $("#article-form-flags").val(getTagitValue("tag1"));
					formData.set("flags",$("#article-form-flags").val());
					formData.set("content", contentEditor.getData());
					return true;
				}
				);
			});

			$("#article-edit-form").validate(getValidateParameter());

			showView = function(id) {
				showData({
					url: "article/view?id="+id,
					inputIdPre: "article-view-",
					modalId: "article-view-modal",
					trans:[
  					{
  						name: "classTypeCode",
							items: classTypeCodeList,				
						},
  					{
  						name: "hasComment",
							items: hasCommentList,				
						},
						]
				}
				,function(data){
					$("#article-view-flags").html(setTagLabel(data.flags,"label-lime"));
					$("#article-view-content").html(data.content);
                    $('pre > code').each(function() {
                        hljs.highlightBlock(this);
                    });
				}
				);


			};







		
		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datatable_header.ftl" />

<link href="../assets/plugins/ckeditor/plugins/codesnippet/lib/highlight/styles/solarized_light.css" rel="stylesheet" />
<!-- ================== END PAGE LEVEL STYLE ================== -->

<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">首页</a></li>
	<li class="breadcrumb-item"><a href="#comment/list">评论</a></li>
	<li class="breadcrumb-item active">评论</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">评论 <!--<small>header small text goes here...</small>--></h1>
<!-- end page-header -->

<!-- begin panel -->
<div class="panel panel-inverse">
    <!-- begin panel-heading -->
    <div class="panel-heading">
        <#include "../public/include_panel_heading_btn.ftl" />
        <h4 class="panel-title">查询条件</h4>
    </div>
    <!-- end panel-heading -->
    <!-- begin panel-body -->
    <div class="panel-body">
        <form id="comment_search_form">
            <div class="row">
                <div class="form-group row m-b-15 width-250 m-3">
                    <label class="col-md-4 col-form-label">审核状态</label>
                    <div class="col-md-8">
                        <select class="form-control m-b-5" name="search_hasCheck" id="search_hasCheck">
                            <option value="">全部</option>
                            <#list hasCheckList as item>
                                <option value="${item.code}">${item.name}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row m-b-15 width-md m-3">
                    <label class="col-md-2 col-form-label">文章</label>
                    <div class="col-md-10">
                        <input type="hidden" name="search_articleId" id="search_articleId" value="" />
                        <input type="text" class="form-control m-b-5" name="search_articleTitle" id="search_articleTitle" value="" />
                        <small class="f-s-12 text-grey-darker">文章ID： <span id="search_view_articleId"></span></small>
                    </div>
                </div>
                <div class="form-group row m-b-15 width-sm m-3">
                    <label class="col-form-label col-md-3">评论</label>
                    <div class="col-md-9">
                        <input type="text" name="search_value" id="search_value" class="form-control m-b-5" />
                    </div>
                </div>
                <div class="row m-b-15 m-3">
                    <div class="col-md-12">
                        <button class="btn btn-primary" id="search-btn">查询</button>
                    </div>
                </div>

            </div>
        </form>
    </div>
    <!-- end panel-body -->
</div>
<!-- end panel -->
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
		<input type="hidden" id="commentTableAllSelectIds" name="commentTableAllSelectIds" value="" />
		<table id="comment_table" class="table table-striped table-bordered dataTable"><!-- table-hover-->
			<thead>
				<tr>
					<th class=" with-checkbox" style="width:17px" data-orderable="false">
						<input type="checkbox" class="" id="table_select_checkbox" />
					</th>
					<th class="text-nowrap">头像</th>
					<th class="text-nowrap">用户名</th>
					<th class="text-nowrap">评论时间</th>
					<th class="text-nowrap">评论</th>
					<th class="text-nowrap">是否审核</th>
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

<script src="../assets/plugins/ckeditor/ckeditor.js"></script>
<script src="../assets/plugins/ckeditor/plugins/codesnippet/lib/highlight/highlight.pack.js"></script>

<script>
	App.setPageTitle('Blog Admin | 评论');
	App.restartGlobalFunction();
	
	var hasCheckList = new Array(
<#list hasCheckList as item>
		{code:"${item.code}", name:"${item.name}"},		
</#list>
	);
	var preCheckList = new Array(
<#list preCheckList as item>
		{code:"${item.code}", name:"${item.name}"},		
</#list>
	);

	var table;
    var contentEditor;
    var hasCheck = "${hasCheck}";
	var defaultAvatar = '../assets/img/user/noavatar.png';
	var defaultIcon = '<i class="fa fa-user" />';
    $.getScript('../assets/plugins/gritter/js/jquery.gritter.min.js').done(function() {
		$.when(

            $.Deferred(function( deferred ){
                $(deferred.resolve);
            })
		).done(function() {

			initElement();

			function initElement(){
				$("#search_hasCheck").val(hasCheck);
				table = initDataTables(getDataTablesPara());
                contentEditor = initCkeditor("comment-form-content");
				initCommentIcons();
			}

			function getDataTablesPara(){
				var tableoptions = {
					tableId: "comment_table",
                    dom: 'Brtip',
					ajax: {
						"url": "../admin/comment/query",
                        "data": function(d){
						    //d.articleTitle = $("#search_articleTitle").val();
                            d.articleId = $("#search_articleId").val();
                            d.hasCheck = $("#search_hasCheck").val();
                            d.searchValue = $("#search_value").val();

                        }
					},
					columns: [
						{
							data: null, render: createDataTableTdCheckbox,
						},
						{
							data: null, name:"icon",render: function (data, type, row) {
								return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
										+ (data.icon!="" ? '<img src="'+data.icon+'" width="40">' : '') + '</a>';
							}
						},
						{
							data: null, name:"name",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.name + '</a>';
							}
						},
						{
							data: null, name:"releaseTime",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ data.releaseTime + '</a>';
							}
						},
						{
							data: null, name:"content",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
    								+ delHtmlTag(data.content) + '</a>';
							}
						},
						{
							data: null, name:"hasCheck",render: function (data, type, row) {
							return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
											+getCurrentComboText(hasCheckList,data.hasCheck,"<br />")+'</a>';
							}
						},
					],
					buttons: [
						{
							text: '增加',
							className: 'btn btn-default',
							action: commentAdd,
						},
						{
							text: '修改',
							className: 'btn btn-default',
							action: commentEdit,

						},
						{
							text: '删除',
							className: 'btn btn-default',
							action: commentDel,
						},
                        {
                            text: '回复',
                            className: 'btn btn-default',
                            action: commentReply,
                        },
                        {
                            text: '标记为已审',
                            className: 'btn btn-default',
                            action: commentMark,
                        }
					],

				}
				return tableoptions;
			}


			function commentAdd() {

				clearForm("#comment-edit-form");
                contentEditor.setData("");
				$(".cke_contents").css("height","200px");
                $("#comment-form-articleId").val($("#search_articleId").val());
                $("#comment-form-name").val("${user.name}");
                $("#comment-form-hasCheck").val("true");
				$("#comment-form-preCheck").val("true");
				$("#comment-form-modal").modal();
				$("#comment-form-avatar").attr("src",defaultAvatar);

			}


			function commentEdit() {
				clearForm("#comment-edit-form");
                contentEditor.setData("");
				var id = getCheckboxIdByTable("comment_table");
				if(id==null)return;
				setAjaxDataToForm({
					url:"comment/edit?id="+id,
					formId: "comment-edit-form",
					modalId:"comment-form-modal",

				}
				, function(data){
                    setEditorHtml(contentEditor,data.content);
					$("#comment-form-avatar").attr("src",data.icon=="" ? defaultAvatar : data.icon);
				}
				)
			}

			function commentDel() {
				deleteByTableIds({
					tableId:"comment_table",
					table:table,
					url: "comment/del",
				});
			}

			$('table th input:checkbox').on('click', allChecked);

			$("#comment-form-save-btn").on("click", function () {
				var result = postData({
					url: "comment/save",
					table: table,
					btnId: "comment-form-save-btn",
					formId: "comment-edit-form",
					modalId: "comment-form-modal",
				}
                , function(formData){
                    if (contentEditor.getData().trim() == "") {
                        webAlert("表单错误!", "请输入内容");
                        return false;
                    }
                    formData.set("content", contentEditor.getData());
                    return true;
				}
				);
			});

			$("#comment-edit-form").validate(getValidateParameter());

			showView = function(id) {
				showData({
					url: "comment/view?id="+id,
					inputIdPre: "comment-view-",
					modalId: "comment-view-modal",
					trans:[
  					{
  						name: "hasCheck",
							items: hasCheckList,
						},
  					{
  						name: "preCheck",
							items: preCheckList,
						},
						]
				}
                ,function(data){
					$("#comment-view-icon").html(data.icon=="" ? defaultIcon : '<img src="'+data.icon+'" height="70" alt="" />');
                    $("#comment-view-content").html(data.content);
					$('pre > code').each(function() {
						hljs.highlightBlock(this);
					});
				}
				)
			};


            $("#search_articleTitle").autocomplete({
                source: "article/queryForSearch",
                focus: function(event, ui) {
                    // prevent autocomplete from updating the textbox
                    event.preventDefault();
                    // manually update the textbox
                    $(this).val(ui.item.label);
                },
                select: function (event, ui) {
                    event.preventDefault();
                    $(this).val(ui.item.label); // display the selected text
                    $("#search_articleId").val(ui.item.value); // save selected id to hidden input
                    $("#search_view_articleId").html(ui.item.value);
                }
            }).blur(function(){
                if($(this).val()==""){
                    $("#search_articleId").val("");
                    $("#search_view_articleId").html("");
                }
            });

			$("#search-btn").on("click",function(){
			    if(table!=null)table.ajax.reload();
			    return false;

            });
			
			function commentReply(){
                clearForm("#comment-edit-form");
                contentEditor.setData("");
                var id = getCheckboxIdByTable("comment_table");
                if(id==null)return;
                showData({
                        url: "comment/view?id="+id,
                        modalId: "comment-form-modal",
                    }
                    ,function(data){
                        $("#comment-form-articleId").val(data.articleId);
                        contentEditor.setData('<blockquote><p><a href="#'+data.id+'">原评论</a> 由 '
                            +data.name+' 发表于 <span class="underline">'+data.releaseTime+'</span></p>'
                            +data.content+'</blockquote><p></p>');
                        $("#comment-form-name").val("${user.name}");
                        $("#comment-form-hasCheck").val("true");
						$("#comment-form-preCheck").val("true");
                    }
                )

                //$("#comment-form-modal").modal();
            };

			function commentMark(){
				markByTableIds({
                    tableId:"comment_table",
                    table:table,
                    url: "comment/mark",
                });
            }

			function markByTableIds(){
				var arg = Array.prototype.slice.call(arguments);
				var callback;
				var obj;
				var hasMarkIds = true;
				if(arg.length>0){
					for(var i=0;i<arg.length;i++){
						if(typeof arg[i]=="object"){
							obj = arg[i];
							if(obj.delIds!=null)hasMarkIds = obj.delIds;
						}else if(typeof arg[i]=="function"){
							callback = arg[i];
						}
					}
				}

				var checkeds = 0;
				var delids = "";
				var result = false;
				$("#"+obj.tableId).find("tr > td:first-child input:checkbox:checked")
						.each(function(index,item){
							checkeds++;
							if(delids!="")delids+=",";
							delids+=item.value;
						});
				if(checkeds==0){
					webAlert("","请先选择需要操作的数据");
					return false;
				}else{
					console.debug("标记的数据ids:"+delids);
					if(hasMarkIds){
						result = markAjaxByIds(obj.table,obj.url,delids.split(","));
					}
					if(callback!=null){
						result = callback(delids.split(","));
					}
					return result;
				}
			}

			function markAjaxByIds(table,url,data){
				$.ajax( {
					type: "POST",
					contentType: "application/json",
					url: url,
					dataType:"json",
					data: JSON.stringify(data),
					success: function(data) {
						if(data.code==getSuccessStatus()){
							console.debug("刷新表格操作");
							if(table!=null)table.draw();
						}else{
							webAlert("["+data.code+"] 标记数据失败",data.msg,"error");
						}

					},
					error: function(data){
						webAlert("",getErrorInfo(data,"标记数据失败"),"error");
						return false;
					}
				})
			}


			function initCommentIcons(){
				$("#comment_icon_list").append('<li>'
						+'<a class="comment-avatar" href="javascript:setAvatar(\''+defaultAvatar+'\')"><img src="'+defaultAvatar+'" /></a>'
						+'</li>');
				for(var i=1;i<=16;i++){
					$("#comment_icon_list").append('<li>'
							+'<a class="comment-avatar" href="javascript:setAvatar(\'../assets/img/user/user-'+i+'.png\')"><img src="../assets/img/user/user-'+i+'.png" /></a>'
							+'</li>');
				}

			}

			setAvatar = function(value){
				$("#comment-form-avatar").attr("src",value);
				if(value.indexOf("noavatar")>-1){
					$("#comment-form-icon").val("");
				}else{
					$("#comment-form-icon").val(value);
				}
			}

		
		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

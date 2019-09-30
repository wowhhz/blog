<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datatable_header.ftl" />
<!-- ================== END PAGE LEVEL STYLE ================== -->


<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">首页</a></li>
	<li class="breadcrumb-item"><a href="#user/profile">用户设置</a></li>
	<li class="breadcrumb-item active">用户设置</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">用户设置 <!--<small>header small text goes here...</small>--></h1>
<!-- end page-header -->

<!-- begin panel -->
<div class="panel panel-inverse">
	<!-- begin panel-heading -->
	<div class="panel-heading">
		<#include "../public/include_panel_heading_btn.ftl" />
		<h4 class="panel-title">用户设置</h4>
	</div>
	<!-- end panel-heading -->
	<!-- begin panel-body -->
	<div class="panel-body">
		<form id="user-edit-form" action="" method="">
			<input type="hidden" name="id" id="user-form-id" value="${user.id}" />
			<input type="hidden" name="createTime" id="user-form-createTime" value="${user.createTime}" />
			<input type="hidden" name="password" id="user-form-password" value="" />
			<input type="hidden" name="status" id="user-form-status" value="${user.status}" />
			<input type="hidden" name="uptype" id="user-form-uptype" value="profile" />
			<div class="row">
                <div class="col-sm">
                    <div class="form-group row m-b-15 width-md m-3">
                        <label class="col-form-label col-md-3">头像</label>
                        <div class="col-md-6 col-xs-12 col-sm-6 text-center">
                            <img class="userImage" id="user-form-icon-show" src="${urlPath}${user.icon}" />
                            <input type="file" class="form-control m-b-5" name="icon" id="user-form-icon" accept="image/*" onchange="readURL(this,'user-form-icon-show');" />
                        </div>
                    </div>
                </div>
                <div class="col-sm">
                    <div class="row">
                        <div class="form-group row m-b-15 width-md m-3">
                            <label class="col-form-label col-md-3">用户名</label>
                            <div class="col-md-6 col-xs-12 col-sm-6">
                                <input type="text" class="form-control m-b-5" name="username" id="user-form-username" placeholder="请输入用户名" value="${user.username}" required readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group row m-b-15 width-md m-3">
                            <label class="col-form-label col-md-3">名字</label>
                            <div class="col-md-6 col-xs-12 col-sm-6">
                                <input type="text" class="form-control m-b-5" name="name" id="user-form-name" placeholder="请输入名字" value="${user.name}" required />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group row m-b-15 width-md m-3">
                            <label class="col-form-label col-md-3">Email</label>
                            <div class="col-md-6 col-xs-12 col-sm-6">
                                <input type="email" class="form-control m-b-5" name="email" id="user-form-email" placeholder="请输入Email" value="${user.email}" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group row m-b-15 width-md m-3">
                            <label class="col-form-label col-md-3">联系电话</label>
                            <div class="col-md-6 col-xs-12 col-sm-6">
                                <input type="text" class="form-control m-b-5" name="phone" id="user-form-phone" placeholder="请输入联系电话" value="${user.phone}" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm">
                </div>

			</div>


			<div class="btn-block text-center">

				<button class="btn btn-primary" id="user-form-save-btn">
					保存
				</button>
			</div>
		</form>

	</div>

	<!-- end panel-body -->
</div>
<!-- end panel -->


<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<#include "../public/include_datatable_footer.ftl" />

<script>
	App.setPageTitle('Blog Admin | 用户设置');
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

			}

			$("#user-form-save-btn").on("click", function () {
				var result = postData({
					url: "user/save",
					btnId: "user-form-save-btn",
					formId: "user-edit-form",
					success: function(){
						webAlert("","用户信息更新成功","success");
						setTimeout(function(){
							location.reload();
						},3000);
					}
				});
				return false;
			});

			$("#user-edit-form").validate(getValidateParameter());


			
			
			

		
		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

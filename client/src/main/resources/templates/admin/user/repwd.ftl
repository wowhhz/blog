<!-- ================== BEGIN PAGE LEVEL STYLE ================== -->
<#include "../public/include_gritter_header.ftl" />
<#include "../public/include_datatable_header.ftl" />
<!-- ================== END PAGE LEVEL STYLE ================== -->


<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
	<li class="breadcrumb-item"><a href="">首页</a></li>
	<li class="breadcrumb-item"><a href="#user/repwd">密码重置</a></li>
	<li class="breadcrumb-item active">密码重置</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">密码重置 <!--<small>header small text goes here...</small>--></h1>
<!-- end page-header -->

<!-- begin panel -->
<div class="panel panel-inverse">
	<!-- begin panel-heading -->
	<div class="panel-heading">
		<#include "../public/include_panel_heading_btn.ftl" />
		<h4 class="panel-title">密码重置</h4>
	</div>
	<!-- end panel-heading -->
	<!-- begin panel-body -->
	<div class="panel-body">
		<form id="user-edit-form" action="" method="">
			<input type="hidden" name="id" id="user-form-id" value="${user.id}" />
			<input type="hidden" name="uptype" id="user-form-uptype" value="repwd" />
			<div class="row">
				<div class="col-sm">
				</div>
                <div class="col-sm">
                    <div class="row">
                        <div class="form-group row m-b-15 width-md m-3">
                            <label class="col-form-label col-md-3">用户名</label>
                            <div class="col-md-6 col-xs-12 col-sm-6">
                                <input type="text" class="form-control m-b-5" name="username" id="user-form-username" placeholder="请输入用户名" value="${user.username}" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group row m-b-15 width-md m-3">
                            <label class="col-form-label col-md-3">原密码</label>
                            <div class="col-md-6 col-xs-12 col-sm-6">
                                <input type="password" class="form-control m-b-5" name="password" id="user-form-password" placeholder="请输入原密码" value="" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group row m-b-15 width-md m-3">
                            <label class="col-form-label col-md-3">新密码</label>
                            <div class="col-md-6 col-xs-12 col-sm-6">
                                <input type="password" class="form-control m-b-5" name="newpassword" id="user-form-newpassword" placeholder="请输入新密码" value="" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group row m-b-15 width-md m-3">
                            <label class="col-form-label col-md-3">确认密码</label>
                            <div class="col-md-6 col-xs-12 col-sm-6">
                                <input type="password" class="form-control m-b-5" name="repassword" id="user-form-repassword" placeholder="请再次输入新密码" value="" />
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
	App.setPageTitle('Blog Admin | 密码重置');
	App.restartGlobalFunction();

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
					url: "user/rePwdSave",
					btnId: "user-form-save-btn",
					formId: "user-edit-form",
					success: function(){
						webAlert("","登录密码更新成功","success");
						$("#user-form-password").val("");
						$("#user-form-newpassword").val("");
						$("#user-form-repassword").val("");
					}
				});
				return false;
			});

			var validOptions = {
				rules: {
					password: {
						required: true,
						minlength: 6,
						maxlength: 32
					},
					newpassword: {
						required: true,
						minlength: 6,
						maxlength: 32
					},
					repassword: {
						equalTo: "#user-form-newpassword",
						required: true,
						minlength: 6,
						maxlength: 32
					}
				}
			}

			$("#user-edit-form").validate(getValidateParameter(validOptions));


			
			
			

		
		});

    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->

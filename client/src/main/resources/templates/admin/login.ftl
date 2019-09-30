<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>Blog Admin | Login Page</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
    <meta content="admin login" name="description" />
    <meta content="blog.acefet.com" name="author" />

    <!-- #include_header -->
    <#include "public/include_header.ftl" />

</head>
<body class="pace-top">
<!-- begin #page-loader -->
<div id="page-loader" class="fade show"><span class="spinner"></span></div>
<!-- end #page-loader -->

<!-- begin #page-container -->
<div id="page-container" class="fade">
    <!-- begin login -->
    <div class="login bg-black animated fadeInDown">
        <!-- begin brand -->
        <div class="login-header">
            <div class="brand">
                <span class="logo"></span> <b>Blog</b> Admin
                <small>仅限管理员登录</small>
            </div>
            <div class="icon">
                <i class="fa fa-lock"></i>
            </div>
        </div>
        <!-- end brand -->
        <!-- begin login-content -->
        <div class="login-content">
            <form action="/checklogin" method="POST" class="margin-bottom-0">
                <input type="hidden" id="url" name="url" value="${url}" />

                <#if error??>
                    <div class="alert alert-warning fade show">
                        <span class="close" data-dismiss="alert">×</span>
                        ${error}
                    </div>
                </#if>
                <div class="form-group m-b-20">
                    <input type="text" id="name" name="username" class="form-control form-control-lg inverse-mode" placeholder="Username" required />
                </div>
                <div class="form-group m-b-20">
                    <input type="password" id="password" name="password" class="form-control form-control-lg inverse-mode" placeholder="Password" required />
                </div>
                <div class="login-buttons">
                    <button type="submit" class="btn btn-success btn-block btn-lg">Sign in</button>
                </div>
            </form>
        </div>
        <!-- end login-content -->
    </div>
    <!-- end login -->

</div>
<!-- end page container -->
<!-- #footer -->
<#include "public/footer.ftl"/>

<script>
    $(document).ready(function() {
        App.init();
        $("#name").focus();
       var url = window.location.href;
       var repStr = "url=";
       var x = url.indexOf(repStr);
       if(x>0){
           $("#url").val(url.substring(x+repStr.length,url.length));
       }

    });
</script>
</body>
</html>

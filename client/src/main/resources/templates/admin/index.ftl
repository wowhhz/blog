<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>Blog Admin</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="blog admin" name="description" />
	<meta content="blog.acefet.com" name="author" />

	<!-- include_header -->
	<#include "public/include_header.ftl" />
</head>
<body>
<!-- begin #page-loader -->
<div id="page-loader" class="fade show"><span class="spinner"></span></div>
<!-- end #page-loader -->

<!-- begin #page-container -->
<div id="page-container" class="page-container fade page-sidebar-fixed page-header-fixed">
	<!-- begin #header -->
	<div id="header" class="header navbar-default">
		<!-- begin navbar-header -->
		<div class="navbar-header">
			<a href="index.html" class="navbar-brand"><span class="navbar-logo"></span> <b>Blog</b> Admin</a>
			<button type="button" class="navbar-toggle" data-click="sidebar-toggled">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>
		<!-- end navbar-header -->

		<!-- begin header-nav -->
		<ul class="navbar-nav navbar-right">
			<li>
				<form class="navbar-form">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="请输入查询关健字" />
						<button type="submit" class="btn btn-search" onclick="javascript:webAlert('','全站搜索功能不可用！','info');return false;"><i class="fa fa-search"></i></button>
					</div>
				</form>
			</li>
			<li class="dropdown">
				<a href="#" data-toggle="dropdown" class="dropdown-toggle f-s-14">
					<i class="fa fa-bell"></i>
					<span class="label fade" id="allMsgNum">0</span>
				</a>
				<ul class="dropdown-menu media-list dropdown-menu-right">
					<li class="dropdown-header">消息通知 (<span id="dropdownAllMsgNum">0</span>)</li>
					<li class="media" style="display:none;" id="commentsNotCheckMsg">
						<a href="#comment/list?hasCheck=false">
							<div class="media-left">
								<i class="fa fa-comments media-object bg-silver-darker" id="commentsMsgIcon"></i>
							</div>
							<div class="media-body">
								<h6 class="media-heading">有新评论，需要审核或回复 (<span id="allNotCheckCommentsNum">0</span>)</h6>
								<div class="text-muted f-s-11">点击查看</div>
								<div class="text-muted f-s-11">
									<span id="connectFailMsg" class="fade">
										<i class="fa fa-exclamation-circle text-danger"></i>
										<span class="text-danger" id="reConnectInfo">通讯失败</span>
									</span>
								</div>
							</div>
						</a>
					</li>
					<!--
					<li class="dropdown-footer text-center">
						<a href="javascript:;">View more</a>
					</li>
					-->
				</ul>
			</li>
			<li class="dropdown navbar-user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					<img src="<#if user.icon=="">../assets/img/user/user.png<#else>../assets/attachments/${user.icon}</#if>" alt="" />
					<span class="d-none d-md-inline">${user.name}</span> <b class="caret"></b>
				</a>
				<div class="dropdown-menu dropdown-menu-right">
					<a href="#user/profile" class="dropdown-item">用户设置</a>
                    <a href="#user/repwd" class="dropdown-item">密码重置</a>
					<div class="dropdown-divider"></div>
					<a href="javascript:location.href='../logout';" class="dropdown-item">登出系统</a>
				</div>
			</li>
		</ul>
		<!-- end header navigation right -->
	</div>
	<!-- end #header -->

	<!-- begin #sidebar -->
	<div id="sidebar" class="sidebar">
		<!-- begin sidebar scrollbar -->
		<div data-scrollbar="true" data-height="100%">
			<!-- begin sidebar user -->
			<ul class="nav">
				<li class="nav-profile">
					<a href="javascript:;" data-toggle="nav-profile">
						<div class="cover with-shadow"></div>
						<div class="image">
							<img src="<#if user.icon=="">../assets/img/user/user.png<#else>../assets/attachments/${user.icon}</#if>" alt="" />
						</div>
						<div class="info">
							<b class="caret pull-right"></b>
							${user.name}
							<small>欢迎！</small>
						</div>
					</a>
				</li>
				<li>
					<ul class="nav nav-profile">
						<li><a href="#user/profile"><i class="fa fa-cog"></i> 用户设置</a></li>
                        <li><a href="#user/repwd"><i class="fa fa-unlock"></i> 密码重置</a></li>
					</ul>
				</li>
			</ul>
			<!-- end sidebar user -->
			<!-- begin sidebar nav -->
			<ul class="nav">
				<li class="has-sub">
					<a href="#dashboard" data-toggle="ajax">
						<b class="dashboard"></b>
						<i class="fa fa-tachometer-alt"></i>
						<span>主面板</span>
					</a>
				</li>
				<!--
				<li class="has-sub">
                    <a href="#test/list" data-toggle="ajax">
                        <b class="article"></b>
                        <i class="fas fa-file-alt"></i>
                        <span>列表测试</span>
                    </a>
                </li>
				<li class="has-sub">
					<a href="#test/tree" data-toggle="ajax">
						<b class="article"></b>
						<i class="fas fa-file-alt"></i>
						<span>树测试</span>
					</a>
				</li>
				-->
				<li class="has-sub">
					<a href="#article/list" data-toggle="ajax">
						<b class="article"></b>
						<i class="fas fa-file-alt"></i>
						<span>文章</span>
					</a>
				</li>
				<li class="has-sub">
					<a href="#comment/list" data-toggle="ajax">
						<b class="comment"></b>
						<i class="fas fa-comments"></i>
						<span>评论</span>
					</a>
				</li>
                <li class="has-sub">
                    <a href="#userFile/list" data-toggle="ajax">
                        <b class="user"></b>
                        <i class="fas fa-file"></i>
                        <span>文件</span>
                    </a>
                </li>
				<li class="has-sub">
					<a href="#classType/list" data-toggle="ajax">
						<b class="classType"></b>
						<i class="fas fa-tags"></i>
						<span>分类</span>
					</a>
				</li>
				<li class="has-sub">
					<a href="#user/list" data-toggle="ajax">
						<b class="user"></b>
						<i class="fas fa-users"></i>
						<span>用户</span>
					</a>
				</li>



				<!-- begin sidebar minify button -->
				<li><a href="javascript:;" class="sidebar-minify-btn" data-click="sidebar-minify"><i class="fa fa-angle-double-left"></i></a></li>
				<!-- end sidebar minify button -->
			</ul>
			<!-- end sidebar nav -->
		</div>
		<!-- end sidebar scrollbar -->
	</div>
	<div class="sidebar-bg"></div>
	<!-- end #sidebar -->

	<!-- begin #content -->
	<div id="content" class="content"></div>
	<!-- end #content -->

	<!-- begin scroll to top btn -->
	<a href="javascript:;" class="btn btn-icon btn-circle btn-success btn-scroll-to-top fade" data-click="scroll-top"><i class="fa fa-angle-up"></i></a>
	<!-- end scroll to top btn -->
</div>
<!-- end page container -->

<!-- ================== BEGIN BASE JS ================== -->
<!-- footer -->
<#include "public/footer.ftl" />
<!-- ================== END BASE JS ================== -->

<script>
	$(document).ready(function() {
		App.init({
			ajaxMode: true,
			ajaxDefaultUrl: '#dashboard',
			ajaxType: 'GET',
			ajaxDataType: 'html'
		});

        var intervalId;
        var jobNum = 0,failNum = 0;
        var allMsgNum = 0;
        var getjob = function(){
			++jobNum;
			//console.debug("jobNum "+jobNum);
			//getCommentsNotCheckNum
			showData({
				url: "comment/getNotCheckCount",
				notAlert: true,
				success: function(data){
					//console.debug("success data:",data);
					allMsgNum=data;
					$("#dropdownAllMsgNum").text(allMsgNum);
					$("#allMsgNum").text(allMsgNum).removeClass("bg-silver-darker");
					$("#allNotCheckCommentsNum").text(data);
					$("#commentsMsgIcon").addClass("bg-gradient-lime");
					if(data=="" || data=="0"){
						$("#allMsgNum").addClass("fade");
						$("#commentsNotCheckMsg").attr("style","display:none;");
					}else{
						$("#allMsgNum").removeClass("fade");
						$("#commentsNotCheckMsg").removeAttr("style");
					}
					$("#connectFailMsg").addClass("fade");
					$("#reConnectInfo").text("");

				},
				error: function(data){
					++failNum;
					//console.debug();
					$("#commentsMsgIcon").removeClass("bg-gradient-lime");
					$("#connectFailMsg").removeClass("fade");
					$("#allMsgNum").addClass("bg-silver-darker");
					$("#reConnectInfo").text("通讯失败，尝试重连("+failNum+")");
					if(failNum==3){
						//console.log("stop job");
						$("#reConnectInfo").text("通讯失败，已断开连接");
						clearInterval(intervalId);
					}
				}
			});
		};


        initElement();

        function initElement(){
            //console.debug("first run");
            getjob();
        }

        intervalId = setInterval(getjob, 20000);


	});
</script>
</body>
</html>

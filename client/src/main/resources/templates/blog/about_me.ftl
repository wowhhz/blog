<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>关于网站 | About me</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="About me" name="description" />
	<meta content="blog.acefet.com" name="author" />

	<!-- #header css js -->
	<#include "include_header.ftl" >
</head>
<body>
	<!-- #header -->
	<#include "header.ftl">
	
	<!-- begin #page-title -->
	<div id="page-title" class="page-title has-bg">
		<div class="bg-cover" data-paroller="true" data-paroller-factor="0.5" data-paroller-factor-xs="0.2" style="background: url(../assets/img/cover/cover-3.jpg) center 0px / cover no-repeat"></div>
		<div class="container">
			<h1>关于网站</h1>
			<p>About me</p>
		</div>
	</div>
	<!-- end #page-title -->
	
	<!-- begin #content -->
	<div id="content" class="content">
		<!-- begin container -->
		<div class="container">
			<!-- begin row -->
			<div class="row row-space-30">
				<!-- begin col-9 -->
				<div class="col-md-9">
					<!-- begin section-container -->
					<div class="section-container">

						<h3 class="text-inverse about-me-title">关于网站</h3>
						<p class="about-me-desc">
							做这个网站的初衷是为了记录过去，感悟和思考，以备查阅，也分享给后来者。网站域名是随便取的，只是为了尽量简短，易记。
							网站用于记录互联网技术笔记，社交化生活思考，以及自己的生活感悟。
						</p>
						<!--
						<p>&nbsp;</p>
						<h3 class="text-inverse about-me-title">关于我</h3>
						<p class="about-me-desc">
							关于本人，从小向往多姿多彩的生活，一直从事互联网相关的工作，希望能通过自己的努力，借助互联网这个大平台，能给我们
							身处的环境带来多一些机会，让环境更包容，让生活更美好。
						</p>
						-->
					</div>
					<!-- end section-container -->
				</div>
				<!-- end col-9 -->

				<!-- #right navigation -->
				<#include "right_navigation.ftl" />

			</div>
			<!-- end row -->
		</div>
		<!-- end container -->
	</div>
	<!-- end #content -->

	<!-- #footer -->
	<#include "footer.ftl" >
	
	<script>
		$(document).ready(function() {
			App.init();
		});
	</script>
</body>
</html>
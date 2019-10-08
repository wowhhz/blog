<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>主页 | blog.acefet.com</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="blog index" name="description" />
	<meta content="blog.acefet.com" name="author" />

	<!-- #header css js -->
	<#include "include_header.ftl" >

</head>
<body>
	<!-- #header -->
	<#include "header.ftl">

	
	<!-- begin #page-title -->
	<div id="page-title" class="page-title has-bg">
		<div class="bg-cover" data-paroller="true" data-paroller-factor="0.5" data-paroller-factor-xs="0.2" style="background: url(../assets/img/cover/cover-4.jpg) center 0px / cover no-repeat"></div>
		<div class="container">
			<h1>生活感悟、技术记录</h1>
			<p>blog.acefet.com</p>
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
					<!-- begin post-list -->
					<ul class="post-list">
						<#list articleList.content as item>
						<li>
							<!-- begin post-left-info -->
							<#--<div class="post-left-info">
								<div class="post-date">
									<span class="day">21</span>
									<span class="month">OCT</span>
								</div>
								<div class="post-likes">
									<i class="fa fa-heart"></i>
									<span class="number">1,292</span>
								</div>
							</div>-->
							<!-- end post-left-info -->
							<!-- begin post-content -->
							<div class="post-content">
								<#if (simpleRemarkMap[item.id].startImageSrc??)>
								<!-- begin post-image -->
								<div class="post-image">
									<a href="../article/${item.id}">
										<div class="post-image-cover" style="background-image: url('${simpleRemarkMap[item.id].startImageSrc}');"></div>
									</a>
								</div>
								<!-- end post-image -->
								<#elseif (simpleRemarkMap[item.id].startBlockquote??)>
									<!-- begin blockquote -->
									<blockquote>
										"${simpleRemarkMap[item.id].startBlockquote}"
									</blockquote>
									<!-- end blockquote -->
								</#if>
								<!-- begin post-info -->
								<div class="post-info">
									<h4 class="post-title">
										<a href="../article/${item.id}">${item.title}</a>
									</h4>
									<div class="post-by">
										<#if item.author!=""> 作者：${item.author} <span class="divider">|</span></#if> ${item.releaseTime}
										<p>
											<i class="fa fa-coffee"></i> 阅读量：${item.readNum} &nbsp; <i class="fa fa-mouse-pointer"></i> 点赞数：${item.likeNum}
										</p>
									</div>
									<#if (simpleRemarkMap[item.id].startContent??)>
									<div class="post-desc">
										${simpleRemarkMap[item.id].startContent} [...]
									</div>
									</#if>
								</div>
								<!-- end post-info -->
								<!-- begin read-btn-container -->
								<div class="read-btn-container">
									<a href="../article/${item.id}" class="read-btn">详情 <i class="fa fa-angle-double-right"></i></a>
								</div>
								<!-- end read-btn-container -->
							</div>
							<!-- end post-content -->
						</li>
						</#list>
					</ul>
					<!-- end post-list -->
					<div class="section-container">
						<!-- begin pagination -->
						<div class="pagination-container text-center">
							<a href="../list" class="btn btn-primary btn-lg btn-block">查看更多文章</a>
						</div>
						<!-- end pagination -->
					</div>
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
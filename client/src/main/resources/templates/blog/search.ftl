<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title><#if (q??)>${q} - </#if>搜索 | blog.acefet.com</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="搜索,search" name="description" />
	<meta content="blog.acefet.com" name="author" />

	<!-- #header css js -->
	<#include "include_header.ftl" >

	<style>
		.search-result .search-tab {
			list-style-type: none;
			margin: 0;
			padding: 0 1.875rem;
			border-bottom: 1px solid #ccc;
			white-space: nowrap;
		}
		.search-result .search-input {
			position: relative;
			max-width: 100%;
			margin-bottom: .9375rem;
		}
		.form-control-lg {
			height: calc(2.875rem + 2px);
			padding: .5rem 1rem;
			font-size: 1.25rem;
			line-height: 1.5;
			border-radius: 9px;
		}
		.search-result .search-close {
			position: absolute;
			right: .9375rem;
			top: 50%;
			margin-top: -.875rem;
			width: 1.75rem;
			height: 1.75rem;
			text-align: center;
			line-height: 1.75rem;
			font-size: 1.25rem;
			color: #fff;
			background: #ccc;
			border-radius: 1.25rem;
		}
		.search-close:focus, .search-close:hover {
			text-decoration: none;
			background: #8A8A8F;
			color: #fff;
		}
		.profile-info-list>li.img-list a, .search-result .search-tab>li {
			display: inline-block;
		}

		.app-content {
			margin-left: 13.75rem;
			padding: 1.875rem;
			position: relative;
			height: 100%;
		}
		.page-header {
			color: #000;
			padding: 0;
			border: 0;
			margin: 0 0 .9375rem;
			font-size: 1.5rem;
			font-weight: 700;
		}
		.form-control-lg {
			height: calc(2.875rem + 2px);
			padding: .5rem 1rem;
			font-size: 1.25rem;
			line-height: 1.5;
			border-radius: 9px;
		}

		.form-control {
			display: block;
			width: 100%;
			height: calc(2.0625rem + 2px);
			padding: .375rem .75rem;
			font-size: .875rem;
			font-weight: 400;
			line-height: 1.5;
			color: #495057;
			background-color: #fff;
			background-clip: padding-box;
			border: 1px solid #ccc;
			border-radius: 6px;
			transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
		}

		
		.search-result .search-tab>li.active a {
			color: #000;
			border-bottom-color: #000;
		}
		.search-result-list {
			list-style-type: none;
			margin: -1px 0 0;
			padding: 0;
			color: #333;
		}

		.search-result .search-tab>li a {
			display: block;
			padding: .625rem;
			color: gray;
			text-decoration: none;
			border-bottom: 1px solid transparent;
			font-weight: 600;
			margin-bottom: -1px;
		}
		.search-result-list>li {
			padding: 1.25rem 0;
			margin: 0;
			border-top: 1px solid #C8C7CC;
			border-bottom: 1px solid #C8C7CC;
		}
		.search-result .search-result-list>li {
			padding: 1.25rem 0;
			display: -webkit-box;
			display: -ms-flexbox;
			display: flex;
		}
		.search-result .search-result-list>li .search-result-content h3 a {
			color: #000;
		}
		.btn-primary .badge, .list-group-item.active>.badge, .nav-pills .nav-link.active .badge, a {
			color: #007aff;
		}
		.search-tab>li.active a {
			color: #000;
		}

		.search-tab>li a:focus, .search-tab>li a:hover, .search-tab>li.active a {
			border-bottom: solid 2px #000;
		}
	</style>
</head>
<body>
	<!-- #header -->
	<#include "header.ftl">
	
	<!-- begin #page-title -->
	<div id="page-title" class="page-title has-bg">
		<div class="bg-cover" data-paroller="true" data-paroller-factor="0.5" data-paroller-factor-xs="0.2" style="background: url(../assets/img/cover/cover-5.jpg) center 0px / cover no-repeat"></div>
		<div class="container">
			<h1>站内搜索</h1>
			<p>blog.acefet.com</p>
		</div>
	</div>
	<!-- end #page-title -->
	
	<!-- begin #content -->
	<div id="content" class="content">
		<!-- begin container -->
		<div class="container">
			<!-- BEGIN page-header -->
			<h1 class="page-header">
				站内搜索
			</h1>
			<!-- END page-header -->

			<!-- BEGIN search-result -->
			<div class="search-result">
				<!-- BEGIN search-input -->
				<div class="search-input">
					<form action="search" method="GET" id="search_form" name="search_form">
						<a href="javascript:;" class="search-close" id="data-clear">&times;</a>
						<input type="text" class="form-control form-control-lg" name="q" value="<#if (q??)>${q}</#if>" placeholder="请输入搜索关键字..." />
					</form>
				</div>
				<!-- END search-input -->
				<!-- BEGIN search-result-remarker -->
				<#if (articleList??)>
				<div class="pull-right m-t-15">
					共 ${articleList.totalElements} 个结果
				</div>
				</#if>
				<!-- END search-result-remarker -->
				<!-- BEGIN search-tab -->
				<ul class="search-tab">
					<li class="active"><a href="#">文章</a></li>
				</ul>
				<!-- END search-tab -->
				<!-- BEGIN search-result -->
				<div class="search-result">
					<#if (articleList??)>
					<!-- BEGIN search-result-list -->
					<ul class="search-result-list">
						<#list articleList.content as item>
							<li>
								<div class="search-result-content">
									<div class="post-title"><a href="../article/${item.id}">${searchMap[item.id].title}</a></div>
									<div class="post-by">
										<#if item.author!=""> 作者：${item.author} <span class="divider">|</span></#if> ${item.releaseTime}
									</div>
									<p>
										${searchMap[item.id].content}
									</p>
									<a href="../article/${item.id}">http://blog.acefet.com/article/${item.id}</a>
								</div>
							</li>
						</#list>
					</ul>
					<!-- END search-result-list -->
					</#if>
					<!-- BEGIN pagination -->
					<div class="text-center m-t-20">
						<div class="pagination justify-content-center">
						</div>
						<#--<form action="" id="page-go" class="hide">
                            <input type="text" id="page-num">
                            <input type="submit" id="goto" value="Go">
                        </form>-->
						<!-- end pagination container-->
					</div>
					<!-- END pagination -->
				</div>
				<!-- END search-result -->
			</div>
			<!-- END search-result -->
		</div>
		<!-- end container -->


	</div>
	<!-- end #content -->

	<!-- #footer -->
	<#include "footer.ftl" >
    
	<!-- ================== BEGIN BASE JS ================== -->
	<script src="../assets/plugins/js-cookie/js.cookie.js"></script>
	<script src="../assets/plugins/masonry/masonry.min.js"></script>
	<script src="../assets/js/blog/simplePagination.js"></script>
	<!-- ================== END BASE JS ================== -->
	
	<script>
		$(document).ready(function() {
			App.init();

			var currentPage=<#if (articleList??)>${articleList.pageable.pageNumber}+1<#else>0</#if>,
					totalPages=<#if (articleList??)>${articleList.totalPages}<#else>0</#if>,
					totalElements=<#if (articleList??)>${articleList.totalElements}<#else>0</#if>,slp;
			function initPagination(){
				if(totalPages==0)return;
				if(slp==null){
					slp = new SimplePagination(totalPages);
					slp.init({
						container: '.pagination',
						maxShowBtnCount: 3,
						onPageChange: state => {
						if(currentPage!=state.pageNumber){
							location.href="search?q=<#if (q??)>${q}</#if><#if (flag??)>&flag=true</#if><#if (author??)>&author=true</#if>&pageNum="+state.pageNumber;
						}

						//console.log('pagination change:', state.pageNumber)
					}
				})
				}

				slp.gotoPage(currentPage);
				// document.getElementById('page-go').addEventListener('submit', e => {
				// 	e.preventDefault()
				// 	slp.gotoPage(currentPage)
				// })
			}

			$("#data-clear").on("click",function(){
                $("#search_form input").val("");
            })

			function init(){
				initPagination();
			}

			init();
		});
	</script>
</body>
</html>
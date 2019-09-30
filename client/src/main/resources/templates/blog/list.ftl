<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title><#if (classType??)>${classType.name}<#else>文章列表</#if> | blog.acefet.com</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="<#if (classType??)>${classType.name}<#else>文章列表</#if>,article list" name="description" />
	<meta content="blog.acefet.com" name="author" />

    <!-- #header css js -->
    <#include "include_header.ftl" >
</head>
<body>
    <!-- #header -->
    <#include "header.ftl">
	
	<!-- begin #page-title -->
	<div id="page-title" class="page-title has-bg">
		<div class="bg-cover" data-paroller="true" data-paroller-factor="0.5" data-paroller-factor-xs="0.2" style="background: url(../assets/img/cover/cover-9.jpg) center 0px / cover no-repeat"></div>
		<div class="container">
			<h1><#if (classType??)>${classType.name}<#else>文章列表</#if></h1>
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
					<div class="post-list post-grid post-grid-2">

                        <#list articleList.content as item>
                            <div class="post-li">
                                <!-- begin post-content -->
                                <div class="post-content">
                                    <#if (simpleRemarkMap[item.id].startImageSrc??)>
                                        <!-- begin post-image -->
                                        <div class="post-image">
                                            <a href="article/${item.id}">
                                                <div class="post-image-cover" style="background-image: url(${simpleRemarkMap[item.id].startImageSrc})"></div>
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
                                            <a href="article/${item.id}">${item.title}</a>
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
                                        <div class="read-btn-container">
                                            <a href="article/${item.id}" class="read-btn">详情 <i class="fa fa-angle-double-right"></i></a>
                                        </div>
                                    </div>
                                    <!-- end post-info -->
                                </div>
                                <!-- end post-content -->
                            </div>
                        </#list>

					</div>
					<!-- end post-list -->
					<div class="section-container">
                        <!-- begin pagination container-->
                        <div class="pagination-container">
                        </div>
                        <#--<form action="" id="page-go" class="hide">
                            <input type="text" id="page-num">
                            <input type="submit" id="goto" value="Go">
                        </form>-->
                        <!-- end pagination container-->
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
	

    
	<!-- ================== BEGIN BASE JS ================== -->
    <script src="../assets/plugins/js-cookie/js.cookie.js"></script>
    <script src="../assets/plugins/masonry/masonry.min.js"></script>
    <script src="../assets/js/blog/simplePagination.js"></script>
	<!-- ================== END BASE JS ================== -->
	
	<script>
		$(document).ready(function() {
			App.init();

            var currentPage=${articleList.pageable.pageNumber}+1,
                totalPages=${articleList.totalPages},
                totalElements=${articleList.totalElements},slp;
            function initPagination(){
                if(totalPages==0)return;
                if(slp==null){
                    slp = new SimplePagination(totalPages);
                    slp.init({
                        container: '.pagination-container',
                        maxShowBtnCount: 3,
                        onPageChange: state => {
                            if(currentPage!=state.pageNumber){
                                location.href="list?pageNum="+state.pageNumber+"<#if (classType??)>&classType=${classType.code}</#if>";
                            };

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

            function init(){
                initPagination();
            }

            init();



		});
	</script>
</body>
</html>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>${article.title}</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="${article.title},${article.flags}" name="description" />
	<meta content="${article.author}" name="author" />

	<!-- #header css js -->
	<#include "include_header.ftl" >
	<link href="../assets/plugins/ckeditor/plugins/codesnippet/lib/highlight/styles/solarized_light.css" rel="stylesheet" />
	<style>
		.comment-list > .dropdown-menu {
			margin-right:-1000px;
		}

		.comment-list > .dropdown-menu > li {
			display: inline-block;
		}
		#comment-edit-form .comment-avatar{
			margin:8px;
			padding:0;
			cursor:pointer;
		}
		.comment-avatar-list{
			padding:10px;
		}

	</style>
</head>
<body>
	<!-- #header -->
	<#include "header.ftl">
	
	<!-- begin #content -->
	<div id="content" class="content">
		<!-- begin container -->
		<div class="container">
			<!-- begin row -->
			<div class="row row-space-30">
				<!-- begin col-9 -->
				<div class="col-md-9">
					<!-- begin post-detail -->
					<div class="post-detail section-container">
						<ul class="breadcrumb">
							<li class="breadcrumb-item"><a href="../">主页</a></li>
							<li class="breadcrumb-item"><a href="../list">文章</a></li>
							<li class="breadcrumb-item active"></li>
						</ul>
						<h4 class="post-title">
							<a href="${article.id}">${article.title}</a>
						</h4>
						<div class="post-by">
							作者： <#if (article.author=="")>匿名<#else><a href="../search?author=true&q=${article.author}">${article.author}</a></#if> <span class="divider">|</span> ${article.releaseTime}
							<#if (article.flags!='')><span class="divider">|</span>
							<#list article.flags?split(",") as item>
								<#if (item?index>0)>,</#if> <a href="../search?flag=true&q=${item}">${item}</a>
							</#list>
							</#if>
                            <p>
                                <i class="fa fa-coffee"></i> 阅读量：${article.readNum} <span class="divider">|</span>
                                <i class="fa fa-mouse-pointer"></i> 点赞数：${article.likeNum} <span class="divider">|</span>
                                <a href="#comment"><i class="fa fa-comments"></i> 评论数 <span id="commentsCount">0</span></a>
                            </p>
						</div>
						<div class="article-content">
							<!--content begin-->
							${article.content}
							<!--content end-->
						</div>
					</div>
					<!-- end post-detail -->
					<div class="section-container text-center">
						<button type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off"
								onclick="javascript:addLike('${article.id}','true','article')">
							支持这篇文章，请点个赞 (<span id="article-${article.id}-likeNum">${article.likeNum}</span>)</button>
					</div>
					<!-- begin section-container -->
					<div class="section-container">
						<a name="comment"></a>
						<h4 class="section-title"><span>评论 (<span id="commentsNum">0</span>)</span></h4>
						<!-- begin comment-list -->
						<ul class="comment-list" id="comment-list"></ul>
						<ul class="comment-list hide">
							<li id="commentTemplate">
								<!-- begin comment-avatar -->
								<div class="comment-avatar">
									{icon}
								</div>
								<!-- end comment-avatar -->
								<!-- begin comment-container -->
								<div class="comment-container" name="{id}">
									<div class="comment-author">
										{name}
										<span class="comment-date">
										{email}
										发布于 <span class="underline">{releaseTime}</span>
										</span>
									</div>
									<div class="comment-content">
										{content}
									</div>
									<#if (article.hasComment=="true")>
									<div class="comment-btn pull-left">
										<a href="javascript:reply('{id}')"><i class="fa fa-reply"></i> 回复</a>
									</div>
									</#if>
									<div class="comment-rating">
										<!--赞同 或 反对:-->
										<button onClick="javascript:addLike('{id}','true','comment');" class="btn btn-link m-l-10 text-inverse"><i class="fa fa-thumbs-up text-success"></i> <span id="comment-{id}-likeNum">{likeNum}</span></button>
										<button onClick="javascript:addLike('{id}','false','comment');" class="btn btn-link m-l-10 text-inverse"><i class="fa fa-thumbs-down text-danger"></i> <span id="comment-{id}-unlikeNum">{unlikeNum}</span></button>
									</div>
								</div>
								<!-- end comment-container -->
							</li>
						</ul>
						<!-- end comment-list -->
                        <!-- begin pagination container-->
                        <div class="pagination-container">
                        </div>
                        <#--<form action="" id="page-go" class="hide">
                            <input type="text" id="page-num">
                            <input type="submit" id="goto" value="Go">
                        </form>-->
                        <!-- end pagination container-->
					</div>
					<!-- end section-container -->
					<#if (article.hasComment=="true")>
					<!-- begin section-container -->
					<div class="section-container">
						<h4 class="section-title m-b-20"><span>发表评论</span></h4>
						<div class="alert alert-warning f-s-12">
							提交的评论需要管理员审核后才能在页面显示。
						</div>
						<form class="form-horizontal" action="" method="POST" id="comment-edit-form">
							<input type="hidden" name="articleId" value="${article.id}" />
							<input type="hidden" name="hasCheck" value="false" />
							<input type="hidden" name="preCheck" value="true" />
							<input type="hidden" name="site" value="" />
							<input type="hidden" name="icon" id="comment-form-icon" value="" />
							<input type="hidden" name="id" value="" />
							<input type="hidden" name="releaseTime" value="" />
							<input type="hidden" name="sort" value="" />
							<input type="hidden" name="likeNum" value="0" />
							<input type="hidden" name="unlikeNum" value="0" />
							<div class="form-group row">
								<label class="col-form-label f-s-12 col-md-2 text-md-right">头像 </label>
								<div class="col-md-4 col-xs-12 col-sm-6">
									<div class="btn-group comment-list m-0">
										<div class="comment-avatar m-0" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											<img src="../assets/img/user/noavatar.png" id="comment-form-avatar">
										</div>
										<div class="dropdown-menu width-sm" style="margin:0;z-index:1021;">
											<ul class="list-inline comment-avatar-list" id="comment_icon_list">
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-form-label f-s-12 col-md-2 text-md-right">名称 <span class="text-danger">*</span></label>
								<div class="col-md-10">
									<input type="text" class="form-control" name="name" id="comment-form-name" required />
								</div>
							</div>
							<div class="form-group row">
								<label class="col-form-label f-s-12 col-md-2 text-md-right">Email </label>
								<div class="col-md-10">
									<input type="text" class="form-control" name="email" id="comment-form-email" />
								</div>
							</div>
							<div class="form-group row">
								<label class="col-form-label f-s-12 col-md-2 text-md-right">评论 <span class="text-danger">*</span></label>
								<div class="col-md-10">
									<textarea class="form-control" name="content" id="comment-form-content" rows="10" required></textarea>
								</div>
							</div>
							<!--
							<div class="form-group row">
								<div class="col-md-10 offset-md-2">
									<div class="custom-control custom-checkbox f-s-12">
										<input type="checkbox" class="custom-control-input" id="emailNotify" name="email_notify" />
										<label class="custom-control-label p-t-3" for="emailNotify">通过电子邮件通知我后续评论。</label>
									</div>
								</div>
							</div>
							-->
							<div class="form-group row">
								<div class="col-md-10 offset-md-2">
									<button type="button" class="btn btn-inverse btn-lg" id="post_comment_btn">提交评论</button>
								</div>
							</div>
						</form>
					</div>
					<!-- end section-container -->
					</#if>
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
	<script src="../assets/plugins/ckeditor/plugins/codesnippet/lib/highlight/highlight.pack.js"></script>
	<script src="../assets/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script src="../assets/plugins/jquery-validation/additional-methods.min.js"></script>
	<script src="../assets/plugins/jquery-validation/localization/messages_zh.js"></script>
	<script src="../assets/js/blog/simplePagination.js"></script>
	<script src="../assets/plugins/ckeditor/ckeditor.js"></script>
	<script>
		$(document).ready(function() {
			App.init();

			var data,editor,hasInitEditor = false,slp;
			var currentPage,totalPages,totalElements,comments;
			var commentTemplate = $("#commentTemplate").html(),
				defaultIcon = '<i class="fa fa-user" />',
				defaultAvatar = '../assets/img/user/noavatar.png',
				likeIds = [],likeFlag;

			function initEditor(){
				if(hasInitEditor)return;
				editor = CKEDITOR.replace("comment-form-content",{customConfig: 'config.js'});
                setTimeout(function(){
                    $(".cke_contents").css("height","150px");
                },750);
				hasInitEditor = true;
			}

			function initPagination(){
			    if(totalElements==0){
			        $(".pagination-container").addClass("hide");
			        return;
                }
			    if(slp==null){
                    slp = new SimplePagination(totalPages==0 ? 1 : totalPages);
                    slp.init({
                        container: '.pagination-container',
                        maxShowBtnCount: 3,
                        onPageChange: state => {
                        showComments(state.pageNumber);
                    console.log('pagination change:', state.pageNumber)
                    }
                    })
                }

                slp.gotoPage(currentPage==0 ? 1 : currentPage);
				// document.getElementById('page-go').addEventListener('submit', e => {
				// 	e.preventDefault()
				// 	slp.gotoPage(currentPage)
				// })
			}

			function initCommentInfo(){
                var userinfo =<#if (cookieUserInfo?? && cookieUserInfo.cookie_username??)>
                    {
                        name:"${cookieUserInfo.cookie_username}",
                        avatar:"${cookieUserInfo.cookie_avatar}",
                        email:"${cookieUserInfo.cookie_email}",
                        site:"${cookieUserInfo.cookie_site}"
                    };
                <#else>null;
                </#if>;
                //console.debug(userinfo);
                if(userinfo!=null){
                    $("#comment-form-avatar").attr("src",userinfo.avatar=="" ? defaultAvatar : userinfo.avatar);
                    $("#comment-form-icon").val(userinfo.avatar);
                    $("#comment-form-name").val(userinfo.name);
                    $("#comment-form-email").val(userinfo.email);
                    $("#comment-form-site").val(userinfo.site);
                }

			}

			$("#comment-form-content").on("focus",function(){
				initEditor();
			})

			reply = function(commentId){
				var html = "";
				for(var i=0;comments!=null && i<comments.length;i++){
					if(commentId==comments[i].id){
						html = '<blockquote><p><a href="#'+comments[i].id+'">原评论</a> 由 '
								+comments[i].name+' 发表于 <span class="underline">'+comments[i].releaseTime+'</span></p>'
								+comments[i].content+'</blockquote><p></p>';
						break;
					}
				}
				initEditor();
				setTimeout(function(){
					editor.setData("");
					editor.insertHtml(html);
				},1000);
			}

			addLike = function(id,isLike,type){
				//判断ID是否已点
				if(likeFlag == "true")return;
                var index = likeIds.indexOf(id);
				if(index>-1){
					if(type=="comment")return;
					if(type=="article"){
						isLike = "false";
					}
				}
                likeFlag = "true";
				if(type=="comment"){
					var e = $("#"+type+"-"+id+"-"+(isLike=="true" ? "likeNum" : "unlikeNum"));
					e.text(+e.text()+1);
                    likeIds.push(id);
				}
				if(type=="article"){
					var e = $("#"+type+"-"+id+"-likeNum");
					e.text(+e.text()+(isLike=="true" ? 1 : -1));
                    if(isLike=="true"){
                        likeIds.push(id);
                    }else{
                        likeIds.splice(index, 1);
                    }
				}

				//提交数据
				showData({
					url: "../like?type="+type+"&isLike="+isLike+"&id="+id,
					},
					function(data){
						$("#"+type+"-"+data.id+"-likeNum").text(data.likeNum);
						if(type=="comment"){
							$("#"+type+"-"+data.id+"-unlikeNum").text(data.unlikeNum);
						}
						// console.debug(likeIds,isLike,type);
                        likeFlag = "false";
					})
			}

			function showComments(pageNum) {
				showData({
							url: "../comment/list?articleId=${article.id}&pageNum="+pageNum,
						}
						,function(data){
							currentPage = pageNum;
							totalPages = data.totalPages;
							totalElements = data.totalElements;
							comments = data.content;

							$("#commentsCount").text(totalElements);
							$("#commentsNum").text(totalElements);
							$("#comment-list").html("");
							for(var i=0;comments!=null && i<comments.length;i++){
								var email = comments[i].email.replace(/\./g,"<!--#dcsde-->&#46;<!--#bde-->").replace("@","<!-- >@.<>adfefg -->@<!--bcef@esxf >@. -->");
								$("#comment-list").append("<li>"+commentTemplate.replace(/{id}/g,comments[i].id)
								.replace("{name}",comments[i].name)
								.replace("{icon}",comments[i].icon=="" ? defaultIcon : '<img src="'+comments[i].icon+'" alt="" />')
								.replace("{releaseTime}",comments[i].releaseTime)
								.replace("{email}", "") //comments[i].email=="" ? "" :'(<span class="underline">'+email+'</span>)'
								.replace("{content}",comments[i].content)
								.replace("{likeNum}",comments[i].likeNum)
								.replace("{unlikeNum}",comments[i].unlikeNum)
								+"</li>");
							}
							$('pre > code').each(function() {
								hljs.highlightBlock(this);
							});
                            initPagination();
						}
				)
			};

			$("#post_comment_btn").on("click",function(){
				postData({
					url: "../comment/save",
					btnId: "post_comment_btn",
					formId: "comment-edit-form",
					preHander: function(formData){
						if (editor.getData().trim() == "") {
							webAlert("表单错误!", "请输入评论内容");
							return false;
						}
						formData.set("content", editor.getData());
						return true;
					},
					success: function(jsondata){
						var data = jsondata.data;
						var email = data.email.replace(/\./g,"<!--#dcsde-->&#46;<!--#bde-->").replace("@","<!-- >@.<>adfefg -->@<!--bcef@esxf >@. -->");
						$("#comment-list").append("<li>"
                            +'<div class="alert alert-warning fade show">'
                            +'<span class="close" data-dismiss="alert">×</span>'
                            +'这是您刚提交的评论，已在预审中。当前仅限自己可见。'
                            +'</div>'
                            +commentTemplate.replace(/{id}/g,data.id)
										.replace("{name}",data.name)
										.replace("{icon}",data.icon=="" ? defaultIcon : '<img src="'+data.icon+'" alt="" />')
										.replace("{releaseTime}",data.releaseTime)
										.replace("{email}", "") //data.email=="" ? "" : '(<span class="underline">'+email+'</span>)'
										.replace("{content}",data.content)
										.replace("{likeNum}",data.likeNum)
										.replace("{unlikeNum}",data.unlikeNum)
								+"</li>");
						editor.setData("");
						webAlert("","评论提交成功，审核后将显示评论。","success");
					}
				});
			});

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

			$("#comment-edit-form").validate(getValidateParameter());

			function init(){
				hljs.initHighlightingOnLoad();
				showComments(1);
				initCommentIcons();
				initCommentInfo();
			}

			init();
		});
	</script>
</body>
</html>
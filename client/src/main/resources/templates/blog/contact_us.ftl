<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>联系我 | Contant Us</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="Contant us" name="description" />
	<meta content="blog.acefet.com" name="author" />

	<!-- #header css js -->
	<#include "include_header.ftl" >
</head>
<body>
<!-- #header -->
<#include "header.ftl">

<!-- begin #page-title -->
<div id="page-title" class="page-title has-bg">
	<div class="bg-cover" data-paroller="true" data-paroller-factor="0.5" data-paroller-factor-xs="0.2" style="background: url(../assets/img/cover/cover-8.jpg) center 0px / cover no-repeat"></div>
	<div class="container">
		<h1>联系我</h1>
		<p>Contant us</p>
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
					<#--<div id="map_container" style="height: 500px;"></div>-->
				</div>
				<!-- end section-container -->
				<!-- begin section-container -->
				<div class="section-container">
					<h4 class="section-title m-b-20"><span>CONTACT US</span></h4>
					<p class="m-b-30 f-s-13">
						如果你有好的建议或意见，欢迎给我发邮件！<img src="../assets/img/about/email.png" />
					</p>
					<!-- begin row -->
					<div class="row row-space-30 f-s-12 text-inverse">
						<!-- begin col-8 -->
						<!--
						<div class="col-md-8">
							<form class="form-horizontal">
								<div class="form-group row">
									<label class="col-form-label col-md-3 text-md-right">Name <span class="text-danger">*</span></label>
									<div class="col-md-9">
										<input type="text" class="form-control">
									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label col-md-3 text-md-right">Email <span class="text-danger">*</span></label>
									<div class="col-md-9">
										<input type="text" class="form-control">
									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label col-md-3 text-md-right">Message <span class="text-danger">*</span></label>
									<div class="col-md-9">
										<textarea class="form-control" rows="10"></textarea>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label col-md-3 text-md-right"></label>
									<div class="col-md-9 text-left">
										<button type="submit" class="btn btn-inverse btn-lg btn-block">Send Message</button>
									</div>
								</div>
							</form>
						</div>
						-->
						<!-- end col-8 -->
						<!-- begin col-4 -->
						<div class="col-md-4">

						</div>
						<!-- end col-4 -->
					</div>
					<!-- end row -->
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
<#--<script type="text/javascript"
		src="https://webapi.amap.com/maps?v=1.4.15&key=f48d75da5b612c43062725af4a98ead6"></script>
<script type="text/javascript">
	//初始化地图时，若center属性缺省，地图默认定位到用户所在城市的中心
	var map = new AMap.Map('map_container', {
		resizeEnable: true,
		center: [120.209947,30.245853]
	});-->
</script>
</body>
</html>
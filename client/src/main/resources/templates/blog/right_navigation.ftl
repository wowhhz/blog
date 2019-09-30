<!-- begin col-3 -->
<div class="col-md-3">
    <!-- begin section-container -->
    <div class="section-container">
        <form method="get" action="../search">
        <div class="input-group sidebar-search">
            <input type="text" class="form-control" name="q" placeholder="请输入搜索关键字..." />
            <span class="input-group-append">
							<button class="btn btn-inverse" type="submit"><i class="fa fa-search"></i></button>
							</span>
        </div>
        </form>
    </div>
    <!-- end section-container -->
    <!-- begin section-container -->
    <div class="section-container">
        <h4 class="section-title"><span>分类</span></h4>
        <ul class="sidebar-list">
            <li><a href="../list">所有文章</a></li>
            <#list classTypeList as item>
            <li><a href="../list?classType=${item.code}">${item.name}</a></li>
            </#list>
        </ul>
    </div>
    <!-- end section-container -->
    <!-- begin section-container -->
    <div class="section-container">
        <h4 class="section-title"><span>最受欢迎文章</span></h4>
        <ul class="sidebar-recent-post">
            <#list topArticles as item>
            <li>
                <div class="info">
                    <h4 class="title"><a href="../article/${item.id}">${item.title}</a></h4>
                    <div class="date">${item.releaseTime}</div>
                </div>
            </li>
            </#list>
        </ul>
    </div>
    <!-- end section-container -->
    <!-- begin section-container -->
    <div class="section-container">
        <h4 class="section-title"><span>关注我</span></h4>
        <ul class="sidebar-social-list">
            <li><a href="#"><i class="fab fa-github"></i></a></li>
            <li><a href="#"><i class="fab fa-weibo"></i></a></li>
            <li><a href="#"><i class="fab fa-zhihu"></i></a></li>
            <li><a href="#"><i class="fab fa-weixin"></i></a></li>
            <li><a href="#"><i class="fab fa-twitter"></i></a></li>
        </ul>
    </div>
    <!-- end section-container -->
</div>
<!-- end col-3 -->
<!-- begin #header -->
<div id="header" class="header navbar navbar-default navbar-fixed-top">
    <!-- begin container -->
    <div class="container">
        <!-- begin navbar-header -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#header-navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="../" class="navbar-brand">
                <span class="brand-logo"></span>
                <span class="brand-text">
                        blog.acefet.com
					</span>
            </a>
        </div>
        <!-- end navbar-header -->
        <!-- begin navbar-collapse -->
        <div class="collapse navbar-collapse" id="header-navbar">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="../">主页 </a>
                </li>
                <li class="dropdown">
                    <a href="#" data-toggle="dropdown">文章 <b class="caret"></b></a>
                    <div class="dropdown-menu">
                            <a class="dropdown-item" href="../list">所有文章</a>
                        <#list classTypeList as item>
                            <a class="dropdown-item" href="../list?classType=${item.code}">${item.name}</a>
                        </#list>
                    </div>
                </li>
                <li><a href="../about">关于网站</a></li>
                <li><a href="../contact">联系我</a></li>
            </ul>
        </div>
        <!-- end navbar-collapse -->
    </div>
    <!-- end container -->
</div>
<!-- end #header -->
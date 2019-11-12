<!-- ================== BEGIN PAGE LEVEL CSS STYLE ================== -->
<link href="../assets/plugins/jquery-jvectormap/jquery-jvectormap.css" rel="stylesheet" />
<link href="../assets/plugins/bootstrap-calendar/css/bootstrap_calendar.css" rel="stylesheet" />
<link href="../assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" />
<link href="../assets/plugins/nvd3/build/nv.d3.css" rel="stylesheet" />
<!-- ================== END PAGE LEVEL CSS STYLE ================== -->
<style>


    /*svg {
        background-color: transparent;
    }
    path.line {
        fill: none;
        stroke: #fff;
        stroke-width: 1px;
    }
    .axis {
        shape-rendering: crispEdges;
    }
    .axis text,
    .axis tick{
        fill:none;
        stroke:#d9e0e7;
        stroke-width: 0.5;
    }
    .x.axis .minor {
        stroke-opacity: .5;
    }
    .axis line,
    .axis path {
        font: 10px sans-serif;
        fill: none;
        stroke: #d9e0e7;
        stroke-width: 1;
    }
    .cpuline{
        fill:#4387DE;
        fill-opacity:0.6;
    }
    .cpulabel{
        fill:#4387DE;
        font: 12px sans-serif;
    }
    .memoryline{
        fill:#19B3B3;
        fill-opacity:0.6;
    }
    .memorylabel{
        fill:#19B3B3;
        font: 12px sans-serif;
    }

    path.line {
        fill: none;
        stroke: #666;
        stroke-width: 1.5px;
    }

    path.area {
        fill: #e7e7e7;
    }

    .axis {
        shape-rendering: crispEdges;
    }

    .x.axis line {
        stroke: #d9e0e7;
    }

    .x.axis .minor {
        stroke-opacity: .5;
    }*/

    .li-title{
        width:65%;
    }
    .list-content-group{
        height:256px;
    }

</style>
<!-- begin breadcrumb -->
<ol class="breadcrumb pull-right">
    <li class="breadcrumb-item"><a href="">主页</a></li>
    <li class="breadcrumb-item"><a href="#dashboard">主面板</a></li>
    <li class="breadcrumb-item active">主面板</li>
</ol>
<!-- end breadcrumb -->
<!-- begin page-header -->
<h1 class="page-header">主面板 <!--<small>header small text goes here...</small>--></h1>
<!-- end page-header -->
<!-- begin row -->
<div class="row">
    <!-- begin col-3 -->
    <div class="col-lg-3 col-md-6">
        <div class="widget widget-stats bg-gradient-teal">
            <div class="stats-icon stats-icon-lg"><i class="fa fa-podcast fa-fw"></i></div>
            <div class="stats-content">
                <div class="stats-title">总阅读量</div>
                <div class="stats-number">${data.totalReadNumArticle}</div>
                <!--
                <div class="stats-progress progress">
                    <div class="progress-bar" style="width: 100%;"></div>
                </div>
                <div class="stats-desc">Better than last week (100%)</div>
                -->
            </div>
        </div>
    </div>
    <!-- end col-3 -->
    <!-- begin col-3 -->
    <div class="col-lg-3 col-md-6">
        <div class="widget widget-stats bg-gradient-blue">
            <div class="stats-icon stats-icon-lg"><i class="fa fa-gem fa-fw"></i></div>
            <div class="stats-content">
                <div class="stats-title">总文章数</div>
                <div class="stats-number">${data.countArticle}</div>
                <!--
                <div class="stats-progress progress">
                    <div class="progress-bar" style="width: 100%;"></div>
                </div>
                <div class="stats-desc">Better than last week (100%)</div>
                -->
            </div>
        </div>
    </div>
    <!-- end col-3 -->
    <!-- begin col-3 -->
    <div class="col-lg-3 col-md-6">
        <div class="widget widget-stats bg-gradient-purple">
            <div class="stats-icon stats-icon-lg"><i class="fa fa-lightbulb fa-fw"></i></div>
            <div class="stats-content">
                <div class="stats-title">总评论数</div>
                <div class="stats-number">${data.countComments}</div>
                <!--
                <div class="stats-progress progress">
                    <div class="progress-bar" style="width: 76.3%;"></div>
                </div>
                <div class="stats-desc">Better than last week (76.3%)</div>
                -->
            </div>
        </div>
    </div>
    <!-- end col-3 -->
    <!-- begin col-3 -->
    <div class="col-lg-3 col-md-6">
        <div class="widget widget-stats bg-gradient-black">
            <div class="stats-icon stats-icon-lg"><i class="fa fa-star fa-fw"></i></div>
            <div class="stats-content">
                <div class="stats-title">文章总点赞数</div>
                <div class="stats-number">${data.totalLikeNumArticle}</div>
                <!--
                <div class="stats-progress progress">
                    <div class="progress-bar" style="width: 54.9%;"></div>
                </div>
                <div class="stats-desc">Better than last week (54.9%)</div>
                -->
            </div>
        </div>
    </div>
    <!-- end col-3 -->
</div>
<!-- end row -->

<!-- begin row -->
<div class="row">
    <!-- begin col-8 -->
    <div class="col-lg-8">
        <div class="widget-chart with-sidebar">
            <div class="widget-chart-content bg-black">
                <h4 class="chart-title">
                    服务器实时监控
                    <small>Server real-time monitoring</small>
                </h4>
                <input type="hidden" id="checkNumber" value="" />
                <div id="visitors-line-chart" class="widget-chart-full-width nvd3-inverse-mode" style="height: 260px;"></div>
            </div>
            <div class="widget-chart-sidebar bg-black-darker">
                <div class="chart-title"> <!--chart-number-->
                    磁盘使用量
                    <small>Disk usage</small>
                </div>
                <div id="visitors-donut-chart" class="nvd3-inverse-mode p-t-10" style="height: 180px"></div>
                <ul class="chart-legend f-s-11">
                    <li><i class="fa fa-circle fa-fw text-primary f-s-9 m-r-5 t-minus-1"></i> <span class="text-white" id="diskAvailableRate">100</span>% <span>磁盘可用</span></li>
                    <li><i class="fa fa-circle fa-fw text-success f-s-9 m-r-5 t-minus-1"></i> <span class="text-white" id="diskUseRate">0</span>% <span>磁盘已使用</span></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- end col-8 -->
    <!-- begin col-4 -->
    <div class="col-lg-4">
        <div class="panel panel-inverse" data-sortable-id="index-1">
            <div class="panel-heading">
                <h4 class="panel-title">
                    概要一览
                </h4>
            </div>
            <!--<div id="visitors-map" class="bg-black" style="height: 179px;"></div>-->
            <div class="list-group">
                <a href="javascript:;" title="<#if (data.maxReadNumArticle??)>${data.maxReadNumArticle.title}</#if>" data-toggle="tooltip" class="list-group-item list-group-item-inverse d-flex justify-content-between align-items-center text-ellipsis">
                    <span class="text-ellipsis li-title"><#if (data.maxReadNumArticle??)>${data.maxReadNumArticle.title}</#if></span>
                    <span class="badge f-w-500 bg-gradient-teal f-s-10">浏览最多的文章</span>
                </a>

                <a href="javascript:;" title="<#if (data.maxLikeNumArticle??)>${data.maxLikeNumArticle.title}</#if>" data-toggle="tooltip" class="list-group-item list-group-item-inverse d-flex justify-content-between align-items-center text-ellipsis">
                    <span class="text-ellipsis li-title"><#if (data.maxLikeNumArticle??)>${data.maxLikeNumArticle.title}</#if></span>
                    <span class="badge f-w-500 bg-gradient-teal f-s-10">最受欢迎的文章</span>
                </a>
                <a href="javascript:;" title="<#if (data.maxCommentNumArticle?size>0)>${data.maxCommentNumArticle.title}</#if>" data-toggle="tooltip" class="list-group-item list-group-item-inverse d-flex justify-content-between align-items-center text-ellipsis">
                    <span class="text-ellipsis li-title"><#if (data.maxCommentNumArticle?size>0)>${data.maxCommentNumArticle.title}</#if></span>
                    <span class="badge f-w-500 bg-gradient-teal f-s-10">评论最多的文章</span>
                </a>
                <a href="javascript:;" title="<#if (topComments?size>0)>${topComments[0].content}</#if>" data-toggle="tooltip" class="list-group-item list-group-item-inverse d-flex justify-content-between align-items-center text-ellipsis">
                    <span class="text-ellipsis li-title"><#if (topComments?size>0)>${topComments[0].content} <#else>&nbsp;</#if></span>
                    <span class="badge f-w-500 bg-gradient-teal f-s-10">最受欢迎的评论</span>
                </a>
                <a href="javascript:;" title="<#if (topComments?size>1)>${topComments[1].content}</#if>" data-toggle="tooltip" class="list-group-item list-group-item-inverse d-flex justify-content-between align-items-center text-ellipsis">
                    <span class="text-ellipsis li-title"><#if (topComments?size>1)>${topComments[1].content} <#else>&nbsp;</#if></span>
                    <span class="badge f-w-500 bg-gradient-teal f-s-10">第2支持的评论</span>
                </a>
                <a href="javascript:;" title="<#if (topComments?size>2)>${topComments[2].content}</#if>" data-toggle="tooltip" class="list-group-item list-group-item-inverse d-flex justify-content-between align-items-center text-ellipsis">
                    <span class="text-ellipsis li-title"><#if (topComments?size>2)>${topComments[2].content} <#else>&nbsp;</#if></span>
                    <span class="badge f-w-500 bg-gradient-blue f-s-10">第3支持的评论</span>
                </a>
                <a href="javascript:;" title="<#if (topComments?size>3)>${topComments[3].content}</#if>" data-toggle="tooltip" class="list-group-item list-group-item-inverse d-flex justify-content-between align-items-center text-ellipsis">
                    <span class="text-ellipsis li-title"><#if (topComments?size>3)>${topComments[3].content} <#else>&nbsp;</#if></span>
                    <span class="badge f-w-500 bg-gradient-grey f-s-10">第4支持的评论</span>
                </a>
            </div>
        </div>
    </div>
    <!-- end col-4 -->
</div>
<!-- end row -->

<!-- begin row -->
<div class="row">
    <!-- begin col-4 -->
    <div class="col-lg-4">
        <!-- begin panel -->
        <div class="panel panel-inverse" data-sortable-id="index-2">
            <div class="panel-heading">
                <h4 class="panel-title">最受欢迎的文章</h4>
            </div>
            <div class="list-group list-content-group">
                <#list topArticles as item>
                    <a href="javascript:;" title="${item.title}" data-toggle="tooltip" class="list-group-item d-flex justify-content-between align-items-center text-ellipsis">
                        <span class="text-ellipsis li-title">${item?index+1}、${item.title}</span>
                        <span class="badge f-w-500 bg-gradient-teal f-s-10">${item.classTypeCode}</span>
                    </a>
                </#list>
            </div>
            <div class="panel-footer text-center">
                <a href="#article/list" class="text-inverse">查看全部</a>
            </div>
        </div>
        <!-- end panel -->
    </div>
    <!-- end col-4 -->
    <!-- begin col-4 -->
    <div class="col-lg-4">
        <!-- begin panel -->
        <div class="panel panel-inverse" data-sortable-id="index-3">
            <div class="panel-heading">
                <h4 class="panel-title">最受欢迎的评论</h4>
            </div>
            <div class="list-group list-content-group">
                <#list topComments as item>
                    <a href="javascript:;" title="${item.content}" data-toggle="tooltip" class="list-group-item d-flex justify-content-between align-items-center text-ellipsis">
                        <span class="text-ellipsis li-title">${item?index+1}、${item.content}</span>
                        <span class="badge f-w-500 bg-gradient-teal f-s-10">${item.releaseTime}</span>
                    </a>
                </#list>
            </div>
            <div class="panel-footer text-center">
                <a href="#comment/list" class="text-inverse">查看全部</a>
            </div>
        </div>
        <!-- end panel -->
    </div>
    <!-- end col-4 -->
    <!-- begin col-4 -->
    <div class="col-lg-4">
        <!-- begin panel -->
        <div class="panel panel-inverse" data-sortable-id="index-4">
            <div class="panel-heading">
                <h4 class="panel-title">日历</h4>
            </div>
            <div class="list-group list-content-group">
                <div id="schedule-calendar" class="bootstrap-calendar"></div>
            </div>
            <div class="panel-footer text-center">
                <a href="javascript:;" class="text-inverse">今天的任务是否已完成？</a>
            </div>
        </div>
        <!-- end panel -->
    </div>
    <!-- end col-4 -->
</div>
<!-- end row -->

<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<script>
    App.setPageTitle('Blog Admin | 主面板');
    App.restartGlobalFunction();

    $.getScript('../assets/plugins/d3/d3.min.js').done(function() {
        $.when(
            $.getScript('../assets/plugins/nvd3/build/nv.d3.js'),
            $.getScript('../assets/plugins/jquery-jvectormap/jquery-jvectormap.min.js'),
            $.getScript('../assets/plugins/jquery-jvectormap/jquery-jvectormap-world-merc-en.js'),
            //$.getScript('../assets/plugins/jquery-jvectormap/jquery-jvectormap-cn-merc.js'),
            $.getScript('../assets/plugins/bootstrap-calendar/js/bootstrap_calendar.min.js'),
            $.getScript('../assets/plugins/gritter/js/jquery.gritter.min.js'),
            $.Deferred(function( deferred ){
                $(deferred.resolve);
            })
        ).done(function() {


            var timeoutId;
            var checkNum = $("#checkNumber");
            if(checkNum.val()==""){
                checkNum.val(Math.random());
            };
            var jobNum = 0,failNum = 0,checkNumber = checkNum.val();
            var getjob = function(){
                ++jobNum;
                //console.debug("jobNum "+jobNum);
                //getOSInfo
                showData({
                    url: "getOSInfo",
                    notAlert: true,
                    success: function(data){
                        //console.debug("success data:",data);
                        updateChart(data);


                    },
                    error: function(data){
                        ++failNum;
                        //console.debug("failNum",failNum);

                        if(failNum==3){
                            console.log("Server exception,stop job.");
                            clearTimeout(timeoutId);
                        }
                    }
                });
                //如果页面被重新加载，svg要素消失，就停止获取数据
                if($("#visitors-line-chart").html()==undefined || checkNumber!=$("#checkNumber").val()){
                    clearTimeout(timeoutId);
                    //console.debug("checkNumber",checkNumber,checkNum.val(),$("#checkNumber").val());
                    console.log("页面被重新加载了，原任务停止");
                }else{
                    if(failNum<3)setTimeout(getjob, 1000);
                }
            };


            initElement();

            function initElement(){
                //console.debug("first run");
                getjob();
            }



            var dateFormat = d3.time.format("%Y-%m-%d %H:%M:%S").parse;


            var getDate = function(date) {
                var currentDate = new Date(date);
                var dd = currentDate.getDate();
                var mm = currentDate.getMonth() + 1;
                var yyyy = currentDate.getFullYear();

                if (dd < 10) {
                    dd = '0' + dd;
                }
                if (mm < 10) {
                    mm = '0' + mm;
                }
                currentDate = yyyy+'-'+mm+'-'+dd;

                return currentDate;
            };


            var visitorAreaChartData = [{
                'key' : '内存使用量',
                'color' : COLOR_AQUA,
                'values' : []
            }, {
                'key' : 'CPU使用量',
                'color' : COLOR_BLUE,
                'values' : []
            }],
            donutChart, stackedAreaChart,visitorChart,diskChart,
            handleVisitorsAreaChart = function () {
                nv.addGraph(function() {
                    stackedAreaChart = nv.models.stackedAreaChart()
                        .useInteractiveGuideline(true)
                        .x(function(d) { return d[0] })
                        .y(function(d) { return d[1] })
                        // .pointSize(0.5)
                        .margin({'left':35,'right': 25,'top': 20,'bottom':20})
                        .controlLabels({stacked: 'Stacked'})
                        .showControls(false)
                        .duration(300);

                    stackedAreaChart.xAxis.tickFormat(function(d) {
                        return d3.time.format('%H:%M:%S')(new Date(d)) //"%Y-%m-%d
                    });
                    stackedAreaChart.yAxis.axisLabel('%').tickFormat(function (d) {
                        return parseFloat(d).toFixed(0) + "%";
                    });

                    visitorChart = d3.select('#visitors-line-chart')
                        .append('svg');

                    visitorChart.datum(visitorAreaChartData)
                        .transition().duration(1000)
                        .call(stackedAreaChart)
                        .each('start', function() {
                            setTimeout(function() {
                                d3.selectAll('#visitors-line-chart *').each(function() {
                                    if(this.__transition__)
                                        this.__transition__.duration = 1;
                                })
                            }, 0)
                        });

                    nv.utils.windowResize(stackedAreaChart.update);
                    return stackedAreaChart;
                });
            }


            var visitorDonutChartData = [
                { 'label': '磁盘可用', 'value' : 100, 'color': COLOR_BLUE },
                { 'label': '磁盘已使用', 'value' : 0, 'color': COLOR_GREEN }
            ];
            var handleVisitorsDonutChart = function() {
                var arcRadius = [
                    { inner: 0.65, outer: 0.93 },
                    { inner: 0.6, outer: 1 }
                ];

                nv.addGraph(function() {
                    donutChart = nv.models.pieChart()
                        .x(function(d) { return d.label })
                        .y(function(d) { return d.value })
                        .margin({'left': 10,'right':  10,'top': 10,'bottom': 10})
                        .showLegend(false)
                        .donut(true)
                        .growOnHover(false)
                        .arcsRadius(arcRadius)
                        .donutRatio(0.5);

                    donutChart.labelFormat(d3.format(',.0f'));

                    diskChart= d3.select('#visitors-donut-chart').append('svg');
                    diskChart.datum(visitorDonutChartData)
                        .transition().duration(3000)
                        .call(donutChart);

                    return donutChart;
                });

            };

            function updateChart(newdata) {
                //console.debug(visitorAreaChartData);
                visitorAreaChartData[0].values.push([dateFormat(newdata.dateTime), +newdata.memoryUseRate]);
                visitorAreaChartData[1].values.push([dateFormat(newdata.dateTime), +newdata.cpuUseRate]);

                // Update the SVG with the new data and call chart
                visitorChart.datum(visitorAreaChartData)
                    .transition().duration(1000)
                    .call(stackedAreaChart)
                    .each('start', function() {
                        setTimeout(function() {
                            // d3.selectAll('#visitors-line-chart *').each(function() {
                            //     if(this.__transition__)
                            //         this.__transition__.duration = 1;
                            // })
                        }, 0)
                    });

                var diskAvailableRate = 100 - +newdata.diskUseRate;
                var diskUseRate = +newdata.diskUseRate;
                visitorDonutChartData[0].value= diskAvailableRate;
                visitorDonutChartData[1].value= diskUseRate;
                setTimeout(function(){
                    diskChart.datum(visitorDonutChartData).transition().duration(3000).call(donutChart);
                    $("#diskAvailableRate").text(diskAvailableRate);
                    $("#diskUseRate").text(diskUseRate);
                },1200);

                nv.utils.windowResize(stackedAreaChart.update);
                if(visitorAreaChartData[0].values.length>30){
                    visitorAreaChartData[0].values.shift();
                    visitorAreaChartData[1].values.shift();
                }
            };

            var handleScheduleCalendar = function() {
                var monthNames = ["一月", "二月", "三月", "四月", "五月", "六月",  "七月", "八月", "九月", "十月", "十一月", "十二月"];
                var dayNames = ["日", "一", "二", "三", "四", "五", "六"];

                var now = new Date(),
                    date = now.getDate(),
                    month = now.getMonth() + 1,
                    year = now.getFullYear();

                var events = [[
                    date+ '/' + month + '/' + year,
                    '今天',
                    'javascript:;',
                    COLOR_GREEN
                ]];

                var calendarTarget = $('#schedule-calendar');
                $(calendarTarget).calendar({
                    months: monthNames,
                    days: dayNames,
                    events: events,
                    popover_options:{
                        placement: 'top',
                        html: true
                    }
                });
                $(calendarTarget).find('td.event').each(function() {
                    var backgroundColor = $(this).css('background-color');
                    $(this).removeAttr('style');
                    $(this).find('a').css('background-color', backgroundColor);
                });
                $(calendarTarget).find('.icon-arrow-left, .icon-arrow-right').parent().on('click', function() {
                    $(calendarTarget).find('td.event').each(function() {
                        var backgroundColor = $(this).css('background-color');
                        $(this).removeAttr('style');
                        $(this).find('a').css('background-color', backgroundColor);
                    });
                });
            };


            var handleDashboardGritterNotification = function() {
                setTimeout(function() {
                    $.gritter.add({
                        title: 'Welcome back, Admin!',
                        text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempus lacus ut lectus rutrum placerat.',
                        image: '../assets/img/user/user-12.jpg',
                        sticky: true,
                        time: '',
                        class_name: 'my-sticky-class'
                    });
                }, 1000);
            };



            handleVisitorsAreaChart();
            handleVisitorsDonutChart();
            handleScheduleCalendar();
            //handleDashboardGritterNotification();















































            // $.getScript('../assets/js/demo/dashboard-v2.min.js').done(function() {
            //     DashboardV2.init();
            // });



        //     var mapdata = [ // 使配置文件的地图信息显示中文名称
        //             { id: "CN-32", province: "江苏", data: "Jiangsu" },
        //             { id: "CN-52", province: "贵州", data: "Guizhou" },
        //             { id: "CN-53", province: "云南", data: "Yunnan" },
        //             { id: "CN-50", province: "重庆", data: "Chongqing" },
        //             { id: "CN-51", province: "四川", data: "Sichuan" },
        //             { id: "CN-31", province: "上海", data: "Shanghai" },
        //             { id: "CN-54", province: "西藏", data: "Xizang" },
        //             { id: "CN-33", province: "浙江", data: "Zhejiang" },
        //             { id: "CN-15", province: "内蒙古", data: "Inner Mongol" },
        //             { id: "CN-14", province: "山西", data: "Shanxi" },
        //             { id: "CN-", province: "福建", data: "Fujian" },
        //             { id: "CN-12", province: "天津", data: "Tianjin" },
        //             { id: "CN-13", province: "河北", data: "Hebei" },
        //             { id: "CN-11", province: "北京", data: "Beijing" },
        //             { id: "CN-34", province: "安徽", data: "Anhui" },
        //             { id: "CN-36", province: "江西", data: "Jiangxi" },
        //             { id: "CN-37", province: "山东", data: "Shandong" },
        //             { id: "CN-41", province: "河南", data: "Henan" },
        //             { id: "CN-43", province: "湖南", data: "Hunan" },
        //             { id: "CN-42", province: "湖北", data: "Hubei" },
        //             { id: "CN-45", province: "广西", data: "Guangxi" },
        //             { id: "CN-44", province: "广东", data: "Guangdong" },
        //             { id: "CN-46", province: "海南", data: "Hainan" },
        //             { id: "CN-65", province: "新疆", data: "Xinjiang" },
        //             { id: "CN-64", province: "宁夏", data: "Ningxia" },
        //             { id: "CN-63", province: "青海", data: "Qinghai" },
        //             { id: "CN-62", province: "甘肃", data: "Gansu" },
        //             { id: "CN-61", province: "山西", data: "Shaanxi" },
        //             { id: "CN-23", province: "黑龙江", data: "Heilongjiang" },
        //             { id: "CN-22", province: "吉林", data: "Jilin" },
        //             { id: "CN-21", province: "辽宁", data: "Liaoning" },
        //             { id: "CN-18", province: "台湾", data: "Taiwan" },
        //             { id: "CN-19", province: "钓鱼岛", data: "DiaoyuIslands" },
        //             { id: "MAC", province: "澳门", data: "Macao" },
        //             { id: "HKG", province: "香港", data: "Hongkong" }
        //         ],
        //         markers = [ // 给地图添加标记
        //             { latLng: [29.92, 95.75], name: '西藏 - 波密 2014' },
        //             ...
        // ],
        //     names = {};
        //     $.each(mapdata, function (index, item) {
        //         names[item.id] = item.province;
        //     });
        //     $('.map-container').vectorMap({
        //         map: 'cn_merc',
        //         backgroundColor: '#fff', // 地图背景色
        //         zoomAnimate: false,
        //         zoomOnScroll: false, // 是否可以使用鼠标滚轮缩放地图
        //         regionsSelectable: true, // 区域是否可以被选中
        //         regionsSelectableOne: false,
        //         regionStyle: { // 设置区域样式
        //             initial: { // 初始状态
        //                 fill: "#58D3F7",
        //                 "fill-opacity": 1,
        //                 stroke: 'none',
        //                 "stroke-width": 0,
        //                 "stroke-opacity": 1
        //             },
        //             hover: { // 当鼠标经过时的状态
        //                 fill: "#0080FF"
        //             },
        //             selected: { // 被选中的状态
        //                 fill: "#00BFFF"
        //             },
        //             selectedHover: { // 当被选中之后鼠标经过的状态
        //             }
        //         },
        //         markers: markers, // 初始化标记
        //         markerStyle: { // 设置地图标记的样式
        //             initial: {
        //                 fill: '#FFBF00'
        //             },
        //             hover: {
        //             },
        //             selected: {
        //                 fill: '#FA5858'
        //             },
        //             selectedHover: {
        //             }
        //         },
        //         labels: {
        //             regions: {
        //                 render: function(code) {
        //                     return names[code];
        //                 }
        //             }
        //         },
        //         // 区域标签展开时执行事件
        //         onRegionTipShow: function(event, label, code) {
        //             label.html(names[code]); // 返回标签内容
        //         }
        //     });




        });
    });
</script>
<!-- ================== END PAGE LEVEL JS ================== -->
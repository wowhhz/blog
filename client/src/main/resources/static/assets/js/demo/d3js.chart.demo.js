var timeoutId;
var checkNum = $("#checkNumber");
if(checkNum.val()==""){
    checkNum.val(Math.random());
};
var jobNum = 0,failNum = 0,checkNumber = checkNum.val();
var getjob = function(){
    ++jobNum;
    console.debug("jobNum "+jobNum);
    //getOSInfo
    showData({
        url: "getOSInfo",
        notAlert: true,
        success: function(data){
            console.debug("success data:",data);
            updateChart(data);


        },
        error: function(data){
            ++failNum;
            console.debug("failNum",failNum);

            if(failNum==3){
                console.log("Server exception,stop job.");
                clearTimeout(timeoutId);
            }
        }
    });
    //如果页面被重新加载，svg要素消失，就停止获取数据
    if($("#visitors-line-chart").html()==undefined || checkNumber!=$("#checkNumber").val()){
        clearTimeout(timeoutId);
        console.debug("checkNumber",checkNumber,checkNum.val(),$("#checkNumber").val());
        console.log("页面被重新加载了，原任务停止");
    }else{
        if(failNum<3)setTimeout(getjob, 1000);
    }
};


initElement();

function initElement(){
    console.debug("first run");
    getjob();
}

var data = [];
var timesRun = 0;
var dateFormat = d3.time.format("%Y-%m-%d %H:%M:%S").parse;

var color = d3.scale.category10();

var width = 520,
    height = 245,
    margin = { left: 50, top: 20, right: -28, bottom: 4 },
    g_width = width - margin.left - margin.right,
    g_height = height - margin.top - margin.bottom;


var xScale = d3.time.scale()
    .range([0, width - margin.left - margin.right]);

var yScale = d3.scale.linear()
    .range([height - margin.top - margin.bottom, 0]);

var xAxis = d3.svg.axis()
    .scale(xScale)
    .ticks(5)
    .tickFormat((d) => (d.getMinutes() + ":" + d.getSeconds()))

var yAxis = d3.svg.axis()
    .scale(yScale)
    .ticks(5)
    .tickFormat((d) => (d + "%"))
.orient("left")


var cpu_generator = d3.svg.area()
    .x(function (d, i) { return xScale(d.time); })
    .y0(g_height)
    .y1(function (d) { return yScale(d.cpuRate); })
    // .interpolate("monotone");
    .interpolate("linear");

var memory_generator = d3.svg.area()
    .x(function (d, i) { return xScale(d.time); })
    .y0(g_height)
    .y1(function (d) { return yScale(d.memoryRate); })
    // .interpolate("monotone");//cardinal
    .interpolate("linear");


var svg = d3.select("#visitors-line-chart")
    .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


function setChart(){
    data = [];
    // var timeoutId = 0;
    xScale.domain(d3.extent(data, function (d) { return d.time }))
    yScale.domain([0, 100]);

    var memoryPath = svg.append("path")
        .datum(data)
        .attr("class", "memoryline")
        .attr("d", memory_generator)

    var cpuPath = svg.append("path")
        .datum(data)
        .attr("class", "cpuline")
        .attr("d", cpu_generator)


    var label = svg.append("g");

    label.append("text")
        .attr("x", (d,i)=>(g_width-240))
.attr("y", (d,i)=>14)
.attr("class","cpulabel")
        .text("● CPU使用率")

    label.append("text")
        .attr("x", (d,i)=>(g_width-140))
.attr("y", (d,i)=>14)
.attr('class', 'memorylabel')
        .text("● 内存使用率")



    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + g_height + ")")
        .call(xAxis);

    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis)

}

var oldtime = "";
function updateChart(newdata) {
    console.log(newdata);
    ++timesRun;
    //var time = dateFormat("2015-10-12 12:10" + ":" + (39 + timesRun));
    data.push({
        cpuRate: +newdata.cpuUseRate,
        memoryRate: +newdata.memoryUseRate,
        diskRate: +newdata.diskUseRate,
        time: dateFormat(newdata.dateTime)
    });
    console.log(data);

    xScale.domain(d3.extent(data, function (d) { return d.time }))
    yScale.domain([0, 100]);

    var svg = d3.select("body").transition();

    // var cpuPath = svg.select(".cpuline")
    // .attr("d", cpu_generator(data))
    //.style('fill', "steelblue");

    var cpuPath = svg.select(".cpuline")
        .attr("transform", null)
        .attr("d", cpu_generator(data))
        .transition().duration(750)
        .attr("transform", "translate(" + xScale(data[0].time) + ",0)")
        //data[0].time.setSeconds(data[0].time.getSeconds()-1)
        .ease("linear");
    // .ease("circle")  //变换之后跳动 过度方式,”linear”,”circle”,”elastic”,”bounce”

    // cpuPath.exit().remove();

    var memoryPath = svg.select(".memoryline")
        .attr("transform", null)
        .attr("d", memory_generator(data))
    // .transition()
    // .duration(750)
    // .attr("transform", "translate(" + xScale(data[0].time) + ",0)")

    svg.select(".x.axis")//.transition()
        .duration(750)
        .call(xAxis);

    svg.select(".y.axis")//.transition()
        .duration(750)
        .call(yAxis)
    if(data.length>30)data.shift();

}

setChart();
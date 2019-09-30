function setDataTableReSet(oTable){
    if(oTable!=null)oTable.draw('full-hold');
}

function getTree(options){
    var _treeId = "_tree1";
    var _title = "请选择";
    var _btnAllClass = " hide";
    if(options.treeId!=null)_treeId = options.treeId;
    if(options.title!=null)_title = options.title;
    if(options.btnAll)_btnAllClass = options.btnAll ? "" : " hide";
    bootbox.dialog({
        title:_title,
        message:'<div id="'+_treeId+'" class="tree"></div>',
        buttons:{
            selected_all:{
                "label" : "全选",
                "className" : "btn-primary"+_btnAllClass,
                callback: function(e){
                    selectedTreeAll(_treeId);
                    return false;
                }
            },
            selected_allnone:{
                "label" : "反选",
                "className" : "btn-primary"+_btnAllClass,
                callback: function(e){
                    selectedTreeAllNone(_treeId);
                    return false;
                }
            },
            cancel:{
                "label" : "取消",
                "data-bb-handler":"cancel",
                "className" : "btn-default",
                callback: function(e){
                    //console.debug("取消选择");
                }
            },
            select_btn:{
                "label" : "确定",
                "data-bb-handler":"confirm",
                "className" : "btn-primary",
                callback: function(e){
                    //var returnvalue = $("#_tree_select_values").attr("value");
                    if(options.backMethod!=null && options.backMethod!=""){
                        setTimeout(options.backMethod+"('#"+_treeId+"')",10);
                    }

                }
            }
        }
    });
    loadTree(options);

}

function loadTree(options){

    var treeId = "_tree1";
    var url = "";
    var paramKey = "";
    var multiSelect = true;
    var selectable = true;
    var loadall = false;
    var itemSelect = true;
    var folderSelect = false;
    var selected_icon = "ace-icon fa fa-check";
    var unselected_icon = "ace-icon fa fa-times";
    var open_icon = "ace-icon tree-minus";
    var close_icon = "ace-icon tree-plus";
    var extFolder = -1;
    var selectedNode = [];

    if(options.treeId!=null)treeId = options.treeId;
    if(options.url!=null)url = options.url;
    if(options.paramKey!=null)paramKey = options.paramKey;
    if(options.multiSelect!=null)multiSelect = options.multiSelect;
    if(options.selectable!=null){
        selectable = options.selectable;
        if(options.selectable == false){
            selected_icon = "";
            unselected_icon = "";
        }
    }
    if(options.loadall)loadall = options.loadall;
    if(options.extFolder!=null)extFolder = options.extFolder;
    if(options.selectedNode!=null)selectedNode = options.selectedNode;
    if(options.itemSelect)itemSelect = options.itemSelect;
    if(options.folderSelect){
        folderSelect = options.folderSelect;
        open_icon = "ace-icon fa fa-folder-open";
        close_icon = "ace-icon fa fa-folder";
    }

    var dataSourceTree = {
        _data:options.data,
        _delay:options.delay,
        _url:options.url,
        _paramKey:options.paramKey,
        _loadall:options.loadall,
        _extFolder:options.extFolder,
        _selectedNode:options.selectedNode,
        _treeId:options.treeId
    };
    var tree_data = getTreeAjax(options,url,paramKey);
    for ( var key in tree_data) {
        selectedTreeNode1(tree_data[key],selectedNode);
    }

    var dataSource = function(options, callback) {
        var self = this;
        var $data = null;

        if(!("name" in options) && !("type" in options)){

            $data = tree_data;//the root tree
            callback({ data: $data });
            return;
        }
        else if("type" in options && options.type == "folder") {
            if("additionalParameters" in options && "children" in options.additionalParameters)
                $data = options.additionalParameters.children || {};
            else $data = {}//no data
        }

        if($data != null){//this setTimeout is only for mimicking some random delay
            //setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);
            //we have used static data here
            //but you can retrieve your data dynamically from a server using ajax call
            //checkout examples/treeview.html and examples/treeview.js for more info

            if(!dataSourceTree._loadall){
                $data = getTreeAjax(options,dataSourceTree._url,dataSourceTree._paramKey);
            }
            callback({data:$data});
        }
    };



    var treeDataSource = {
        data: tree_data,
        url:url,
        paramKey:paramKey,
        loadall:loadall,
        extFolder:extFolder,
        selectedNode:selectedNode,
        treeId:treeId,
        delay:400
    };

    $('#'+treeId).ace_tree({
        dataSource: dataSource ,
        multiSelect:multiSelect,
        loadingHTML:'<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',
        'open-icon' : open_icon,
        'close-icon' : close_icon,
        'selectable' : selectable,
        'selected-icon' : selected_icon,
        'unselected-icon' : unselected_icon,
        'itemSelect' : itemSelect,
        'folderSelect': folderSelect,
        'folder-open-icon' : 'ace-icon tree-plus',
        'folder-close-icon' : 'ace-icon tree-minus'
    });

    openTreeFolderLayer(treeId,loadall,extFolder);


    $('#'+treeId).on('loaded', function (evt, data) {
        //console.debug("loaded");
    });

    $('#'+treeId).on('opened', function (evt, data) {
        //console.debug("opened");
    });

    $('#'+treeId).on('closed', function (evt, data) {
        //console.debug("closed");
    });

    $('#'+treeId).on('selected', function (evt, data) {
        //console.debug("selected");
        var value = "";

        //$.each(data.info,function(i,item){
        //	if(i>0)value+=",";
        //	value+=item.value;
        //});
        //$("#_tree_select_values").attr("value",value);
    });



}

function getTreeAjax(options,url,paramKey){
    var tree_data = {};
    var querydata = {};
    if(paramKey!=null && options.value!=null){
        querydata = paramKey+"="+ options.value;
    }


    $.ajax( {
        type: "GET",
        url: url,
        dataType:"json",
        async:false,
        data:querydata,
        //data:JSON.stringify(jsonData),
        success: function(data) {
            tree_data = data;
        },
        error:function(response){
            console.log(response);
        }
    });
    return tree_data;
}

//load all tree data
function openTreeFolderLayer(treeId,loadall,extFolder){

    var maxOpenFolderLayer = 30;
    if(!loadall)return;
    var readnum = 0;
    var filterStr = "#"+treeId+" .tree-branch";
    while(readnum<maxOpenFolderLayer){
        if(readnum>0){
            filterStr+=" > .tree-branch-children > .tree-branch";
        }
        var treenodes = $(filterStr+" > .tree-branch-header");
        if(treenodes.length==0){
            break;
        }
        $.each(treenodes,function(i,treenode){
            if(readnum==0 && i==0){
                // rootnode not null
            }else{
                treenode.click();
                if(extFolder>-1 && extFolder<=readnum){
                    treenode.click();
                }
            }

        });
        readnum++;
    }
}

//load tree node selected
function selectedTreeNode1(data,selectedTreeNode){
    if(data==null)return;
    var hasSelected = false;
    for ( var i = 0; i < selectedTreeNode.length; i++) {
        var element = selectedTreeNode[i];
        if(element.value==null && data.value==element){
            hasSelected = true;
            break;
        }else if(element.value!=null && data.value==element.value){
            hasSelected = true;
            break;
        }
    }

    if(hasSelected){
        if(data['additionalParameters']==null){
            data['additionalParameters'] = {'item-selected':true};
        }else{
            //data['additionalParameters']['item-selected'] = true;
        }

    }
    if(data['additionalParameters']!=null){
        if(data['additionalParameters']['children']!=null){
            var subnodes = data['additionalParameters']['children'];
            $.each(subnodes,function(i,subnode){
                selectedTreeNode1(subnode,selectedTreeNode);
            });
        }
    }

};

function selectedTreeAll(treeId){
    var nodes = $("#"+treeId).find(".tree-item:not(.tree-selected)");
    $.each(nodes,function(i,node){
        node.click();
    });
}

function selectedTreeAllNone(treeId){
    var nodes = $("#"+treeId).find(".tree-selected");
    $.each(nodes,function(i,node){
        node.click();
    });
}

/**
 * load data table, set query data default parameter
 * @param options
 * @returns {jQuery}
 */
function roadJqueryDataTable(options){
    var tableId = "table1";
    var _options = {
        paging: true,
        ordering: true,
        serverSide: true,
        lengthChange: true,
        autoWidth: true,
        info: true,
        searching: true,
        //pagingType:"first_last_numbers","full","full_numbers","numbers","simple","simple_numbers"
        pagingType: "simple_numbers",
        //stateSave cause of order and columnDefs invalid
        //stateSave: true,
        select: false,
        language: {
            //old version
            "url": "../assets/js/jqueryDatabaseLanguage_zh_CN.txt"
            //new version not support
            //"url": "../assets/js/dataTableLanguage_zh_CN.txt"
        },
        processing: true,
        ajax: {
            url: "",
            type: "POST",
            async: false,
            dataType: 'json',
            //dataSrc: null
        },
        columnDefs: [],
        buttons: [],
        columns: options.columns,
        //responsive: true,
        order: [],
        //"aLengthMenu": [[25, 50, 75, -1], [25, 50, 75, "All"]],
        pageLength: 10,
        dom:"Bfrtip",
        responsive: true
    }
    for(var para in options){
        if(para=="ajax"){
            for(var ajaxpara in options.ajax){
                _options["ajax"][ajaxpara]=options["ajax"][ajaxpara];
            }
        }else{
            _options[para]=options[para];
        }
    }
    if(options.tableId!=null)tableId = options.tableId;

    var oTable = $('#'+tableId).DataTable( _options);

    return oTable;
}


function tooltip_placement(context, source) {
    var $source = $(source);
    var $parent = $source.closest('table')
    var off1 = $parent.offset();
    var w1 = $parent.width();

    var off2 = $source.offset();
    var w2 = $source.width();

    if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
    return 'left';
}

function retrieveData( sSource, aoData, fnCallback ) {
    $.ajax( {
        type: "POST",
        contentType: "application/json",
        url: sSource,
        dataType:"json",
        data:JSON.stringify(aoData),
        success: function(data) {
            fnCallback(data);
        }
    })

}


function getDataTable(options){
    $("#_modal_datatable_div").remove();
    $.ajax( {
        type: "GET",
        url: "../frame/datatable.do",
        success: function(data) {
            $("body").append('<div id="_modal_datatable_div"></div>');
            $("#_modal_datatable_div").html(data);
            if(options.title!=null){
                $("#_datatable-title").html(options.title);
            }if(options.selectable==false){
                $("#_datatable-btn-ok").addClass("hide");
            }
            $("#modal-datatable").modal();
            $("#_datatable-btn-ok").on("click",function(){
                $("#modal-datatable").modal("hide");
                //var returnvalue = $("#_tree_select_values").attr("value");
                setTimeout(options.backMethod+"('#'"+options.tableId+")",10);
            });
            roadJqueryDataTable(options);
        },
        error: function(data){
            bootbox.alert("通讯失败");
            return false;
        }
    })
    ;

}

/**
 * 使用官方包，不再使用此方法
 * @deprecated Since version 2.0. Will be deleted in version 3.0. Use bar instead.
 */
function formatDatepicker(){
    console.warn("Calling deprecated function!");
    $.datepicker.regional['zh-CN'] = {
        clearText: '清除',
        clearStatus: '清除已选日期',
        closeText: '关闭',
        closeStatus: '不改变当前选择',
        prevText: '<上月',
        prevStatus: '显示上月',
        prevBigText: '<<',
        prevBigStatus: '显示上一年',
        nextText: '下月>',
        nextStatus: '显示下月',
        nextBigText: '>>',
        nextBigStatus: '显示下一年',
        currentText: '今天',
        currentStatus: '显示本月',
        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
        monthStatus: '选择月份',
        yearStatus: '选择年份',
        weekHeader: '周',
        weekStatus: '年内周次',
        dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
        dayStatus: '设置 DD 为一周起始',
        dateStatus: '选择 m月 d日, DD',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        //numberOfMonths:1,//显示几个月
        //minDate:'2011-03-05',//最小日期
        //maxDate:'2011-03-20',//最大日期
        initStatus: '请选择日期',
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: '年',
        changeMonth: true,
        changeYear: true
    };
    //$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
    //$(".datepicker").datepicker({language:"zh-CN"});
}


function getCurrentComboText(objArray,code,splitText){
    var _text = "";
    if(objArray==null || objArray.lenth==0)return code;
    $.each(getCurrentComboObj(objArray,code),function(){
        if(_text.length>0)_text+=splitText;
        _text += this.text;
    })
    return _text;
}

function getCurrentComboObj(objArray,code){
    if(code==null)return "";
    return getComboObj(objArray,"code",code.split(","),"name");
}

function getComboObj(objArray,key,codes,name){
    var _result = new Array();
    $.each(codes,function(i,code){
        var hasSelected = false;
        $.each(objArray,function(i,item){
            if(item[key]==code){
                _result.push({
                    text:item[key]+"-"+item[name],
                    suffix:item[name],
                    prefix:key
                });
                hasSelected = true;
            };
        });
        if(!hasSelected){
            _result.push({
                text:code,
                suffix:"",
                prefix:code
            });
        }
    })
    return _result;
}

function addUrlPara(url,key,value){
    if(url.indexOf("?")==-1){
        url+="?";
    }else{
        url+="&";
    }
    url+=key+"="+value;
    return url;
}

function getTableAjax(options){
    var table_data = {};
    var querydata = {};
    var paramKey = options.paramKey;
    var url =options.url;
    if(paramKey!=null){
        url=addUrlPara(url,"paramKey",options.paramKey);
    }
    url=addUrlPara(url,"tableId",options.tableId);
    url=addUrlPara(url,"cols",options.cols);

    $.ajax( {
        type: "POST",
        url: url,
        async:false,
        dataType: 'html',
        contentType: "text/xml; charset=utf-8",
        data:querydata,
        //data:JSON.stringify(jsonData),
        success: function(data) {
            table_data = data;
        },
        error:function(response){
            console.debug(response);
            bootbox.alert(response.status+":加载页面失败");
        }
    });
    return table_data;
}


function tableSelectAll(){
    var that = this;
    $(this).closest('table').find('tr > td:first-child input:checkbox')
        .each(function(){
            this.checked = that.checked;
            //$(this).closest('tr').toggleClass('selected');
        });

}

/**
 * dataTables select all id checkbox
 */
function tableSelectAllById(tableid,checkid,selectType){
    var that = $("#"+checkid);
    var checkeds = $("#"+tableid).find('tr > td:first-child input:checkbox');
    if(selectType=="checked"){
        that.checked="checked";
        $("#"+checkid).attr("checked","checked");
        checkeds.each(function(){
            this.checked = "checked";
            //$("#"+tableid).closest('tr').toggleClass('selected');
        });
        return;
    }
    if(selectType=="notChecked"){
        that.checked="";
        $("#"+checkid).attr("checked",null);
        checkeds.each(function(){
            this.checked = "";
            //$("#"+tableid).closest('tr').toggleClass('selected');
        });
        return;
    }
    if($("#"+checkid).attr("checked")==null || selectType=="checked"){
        that.checked="checked";
        $("#"+checkid).attr("checked","checked");
    }else{
        that.checked="";
        $("#"+checkid).attr("checked",null);
    }

    checkeds.each(function(){
        this.checked = that.checked;
        //$("#"+tableid).closest('tr').toggleClass('selected');
    });

}

/**
 * check simple element has select
 * @param tableid
 * @param inputValue
 * @returns {string}
 */
function setInputChecked(tableid,inputValue){
    var idsName = tableid+"AllSelectIds";
    var ids = $("#"+idsName).attr("value").split(",");
    if(ids.length==0)return;
    for (var j = 0; j < ids.length; j++) {
        if(inputValue==ids[j]){
            //console.info(inputValue);
            return "checked=\"checked\"";
        }
    }
    return "";
}

/**
 * dataTables reset selected checkbox
 * @param tableid
 * @param idsStr
 */
function setSelectedTableIds(tableid,idsStr){
    var ids = idsStr.split(",");
    if(ids.length==0)return;
    var inputs = $("#"+tableid).find('tr > td:first-child input:checkbox');
    for (var i = 0; i < inputs.length; i++) {
        var hasChecked = false;
        for (var j = 0; j < ids.length; j++) {
            if(inputs[i].value==ids[j]){
                hasChecked = true;
                break;
            }

        }

        if(hasChecked){
            inputs[i].checked=true;
        }else{
            inputs[i].checked=false;
        }
    }
}

/**
 * dataTables reset get checkbox id value
 * @param tableid
 * @returns {string}
 */
function setSelectTableIds(tableid){
    var checkedIds = new Array();
    var notCheckedIds = new Array();
    var idsName = tableid+"AllSelectIds";
    var ids = $("#"+idsName).attr("value").split(",");
    $("#"+tableid).find('tr > td:first-child input:checkbox')
        .each(function(){
            if(this.value!=''){
                if(this.checked){
                    checkedIds.push(this.value);
                }else{
                    notCheckedIds.push(this.value);
                };
            }
        });
    ids = resetSelectIds(ids,checkedIds,notCheckedIds);
    var idsValue = "";
    for (var i = 0; i < ids.length; i++) {
        if(ids[i].length>0){
            if(i>0)idsValue+=",";
            idsValue+=ids[i];
        }

    }
    $("#"+idsName).attr("value",idsValue);
    return idsValue;
}

/**
 * get selected values id in data array
 * @param ids
 * @param checkedIds
 * @param notCheckedIds
 * @returns {any[]}
 */
function resetSelectIds(ids,checkedIds,notCheckedIds){
    for (var i = 0; i < checkedIds.length; i++) {
        var hasUnique = false;
        for (var j = 0; j < ids.length; j++) {
            if(checkedIds[i]==ids[j]){
                hasUnique = false;
                continue;
            }
        }
        if(!hasUnique){
            if(ids[0]==""){
                ids.splice(0,1,checkedIds[i]);
            }else{
                ids.push(checkedIds[i]);
            }

        }
    }
    for (var i = 0; i < notCheckedIds.length; i++) {
        for (var j = 0; j < ids.length; j++) {
            if(notCheckedIds[i]==ids[j]){
                ids.splice(j,1);
            }
        }
    }
    ids = uniqueIds(ids);
    return ids;
}

/**
 * Filter repetition values(过滤重复值)
 * @param ids
 * @returns {any[]}
 */
function uniqueIds(ids){
    var newids = new Array();
    for (var i = 0; i < ids.length; i++) {
        var hasUnique = false;
        for (var j = 0; j < newids.length; j++) {
            if(ids[i]==newids[j]){
                hasUnique = true;
                break;
            }
        }
        if(!hasUnique)newids.push(ids[i]);
    }
    return newids;
}

function bindDatePicker() {
    $("#datepicker").datepicker({
        showOtherMonths: true,
    });
}

function menuclick(url){
    var result = openPage(url);
    if(!result)return;
    $('#sidebar').removeClass('display');
    var menuli = $("#sidebar").find("ul > li");
    menuli.removeClass("open");
    $.each(menuli,function(i,n){
        var href = $(n).children("a").attr("href");
        if(href==url || href=="javascript:menuclick('"+url+"');"){
            $(n).addClass("active");
            if($(n).text().trim()!="主面板"){
                $(n).parents("li").addClass("open");
                //$(n).parents("li").addClass("active");
            }
        }else{
            $(n).removeClass("active");
        }
    });
}

function openPage(url){
    var result = true;
    initLoading();
    $.ajax( {
        type: "GET",
        async: false,
        url: url,
        //dataType:"json",
        success: function(data) {
            $("#main-content").html(data);

        },
        error: function(data){
            result = false;
            //console.dir(data);
            if(data.responseText.indexOf("javax.naming.AuthenticationException")>0){
                if(data.responseText.indexOf("9998")>0){
                    bootbox.alert({
                        title:'<i class="ace-icon fa fa-warning-sign orange"></i> 权限不足',
                        message:'9998:权限不足，无法继续！如权限不符请与管理员核实！',
                        buttons: {
                            ok: {
                                label: '关闭',
                                className: 'btn-info'
                            }
                        }
                    });
                    //$.gritter.add({
                    //	title: '权限不足',
                    //	text: '9998:权限不足，无法继续！如权限不符请与管理员核实！',
                    //	sticky: true,
                    //	time: '',
                    //	class_name: 'gritter-error gritter-center'
                    //});
                }else if(data.responseText.indexOf("9999")>0){
                    bootbox.alert({
                        title:'<i class="ace-icon fa fa-warning-sign orange"></i> 登录超时',
                        message:'9999:登录超时，请  重新登录 ！',
                        buttons: {
                            ok: {
                                label: '<i class="ace-icon fa fa-key"></i>重新登录',
                                className: 'btn-info'
                            }
                        },
                        callback:function(){
                            location.href="../login.do";
                        }

                    });
                    //$.gritter.add({
                    //	title: '登录超时',
                    //	text: '9999:登录超时，请 <button class="btn btn-sm" onClick=javascript:location.href="../login.do">重新登录</button>',
                    //	sticky: true,
                    //	time: '',
                    //	class_name: 'gritter-error gritter-center'
                    //});
                }
            }else{
                bootbox.alert(data.status+":载入页面失败");
            }
            closeLoading();
        }
    })

    closeLoading();
    return result;
}

function closeLoading(){
    $('#loading').hide();
}
function initLoading(){
    $('#loading').show();
}


/**
 * get jquery validate default parameter,default push rules
 * @param options
 * @returns {{
 * focusInvalid: boolean,
 * highlight: highlight,
 * unhighlight: unhighlight,
 * errorElement: string,
 * invalidHandler: invalidHandler,
 * errorClass: string,
 * submitHandler: submitHandler,
 * rules: {},
 * errorPlacement: errorPlacement
 * }}
 */
function getValidateParameter(){

    var arg = Array.prototype.slice.call(arguments);
    var options;
    if(arg.length>0 && (typeof arg[0])=="object"){
        options = arg[0];
    }

    var _options = {
        rules:{},
        errorElement: "span",
        errorClass: "help-block",
        focusInvalid: true,
        errorPlacement: function (error, element) {
            if (element.is(':checkbox') || element.is(':radio')) {
                var controls = element.closest('div[class*="col-"]');
                if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                else error.insertAfter(element.nextAll('label:eq(0)').eq(0));
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            //console.dir($(element).parents(".col-md-*"));
            $(element).parents('div[class*="col-"]').addClass("has-error").removeClass("has-success");

        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parents('div[class*="col-"]').addClass("has-success").removeClass("has-error");

        },

        submitHandler: function (form) {
            //form.submit();
        },
        invalidHandler: function (form) {
        }
    }

    for(var para in options){
        _options[para]=options[para];
    }

    return _options;
}

/**
 * swal调用封装
 * sweetAlert business api customize Package,
 * parameter reference sweetAlert 1.0 Official document:
 * additional parameter:
 * getArg: true, return properties object, else call swalAlert function.
 */
function webAlert(){

    var arg = Array.prototype.slice.call(arguments);

    var cancelButton = {
        text:"取消",
        value: null,
        visible:true,
        className:"btn btn-default",
        closeModal:!0
    };
    var confirmButton = {
        text:"确定",
        value:true,
        visible: true,
        className:"btn btn-lime",
        closeModal:!0
    };
    var dangerButton = {
        text:"确定",
        value:true,
        visible: true,
        className:"btn btn-danger",
        closeModal:!0
    };
    var content1 = {
        element: 'div',
        attributes: {
            className: "text-center",
            innerHTML: "",
        },
    };

    var swalArg = {};

    var titleWord = "提示";
    if(arg.length==1 && typeof arg[0]=="string"){
        swalArg.text =  arg[0];
        swalArg.buttons = {confirmButton:confirmButton};
    }else if(arg.length==2 && typeof arg[0]=="string"  && typeof arg[1]=="string"){

        swalArg.title = arg[0]=="" ? titleWord : arg[0];
        swalArg.text =  arg[1];
        swalArg.buttons = {confirmButton:confirmButton};
    }else if(arg.length==3 && typeof arg[0]=="string"  && typeof arg[1]=="string"  && typeof arg[2]=="string"){
        swalArg.content =  content1;
        swalArg.content.attributes.innerHTML = arg[1];
        swalArg.icon = arg[2];
        swalArg.buttons = {confirmButton:confirmButton};
    }else if(arg.length==1 && typeof arg[0]=="object"){
        if(arg[0].title!=null){
            if(arg[0].dangerMode==true){
                titleWord = "警告";
            }
            swalArg.title = arg[0].title=="" ? titleWord : arg[0].title;
        }
        if(arg[0].text!=null){
            if(arg[0].icon!=null){
                swalArg.content =  content1;
                swalArg.content.attributes.innerHTML = arg[0].text;
            }else{
                swalArg.text = arg[0].text;
            }
        }
        if(arg[0].icon!=null)swalArg.icon = arg[0].icon;
        if(arg[0].dangerMode!=null)swalArg.dangerMode = arg[0].dangerMode;
        if(arg[0].buttons==null){
            if(arg[0].dangerMode==true){
                swalArg.buttons = {
                    cancelButton: cancelButton,
                    dangerButton: dangerButton
                };
            }else{
                swalArg.buttons = {confirmButton:confirmButton};
            }
        }else{
            swalArg.buttons = arg[0].buttons;
            if(arg[0].buttons.cancelButton!=null){
                var cButton = arg[0].buttons.cancelButton;
                for(var i in cancelButton){
                    if(cButton[i]==null)swalArg.buttons.cancelButton[i] = cancelButton[i];
                }
            }
            if(arg[0].buttons.confirmButton!=null){
                var qButton = arg[0].buttons.confirmButton;
                for(var i in confirmButton){
                    if(qButton[i]==null)swalArg.buttons.confirmButton[i] = confirmButton[i];
                }
            }
            if(arg[0].buttons.dangerButton!=null){
                var dButton = arg[0].buttons.dangerButton;
                for(var i in dangerButton){
                    if(dButton[i]==null)swalArg.buttons.dangerButton[i] = dangerButton[i];
                }
            }
        }
    }
    // console.debug(swalArg);

    if(arg[0]!=null && arg[0].getArg==true)return swalArg;

    return swal(swalArg);
}

function initCkeditor(id){
    var instance = CKEDITOR.instances[id];
    if (instance) {
        instance.destroy(true);
    }
    instance = CKEDITOR.replace(id,{
        customConfig: 'ckeditor_config.js',
        extraPlugins: 'elementspath',
        height: 800
    });
    return instance;
}

function initDataTables(options){
    return roadJqueryDataTable(options);
}

/**
 * create datatables checkbox element
 * @param data
 * @param type
 * @param row
 * @returns {string}
 */
function createDataTableTdCheckbox(data, type, row){
    return "<input type='checkbox' name='ids' class='' id='id_"+data.id+"' value='" + data.id + "' />";
}

function initDatepicker(id){
    $("#"+id).datepicker({
        language: "zh-CN",
        format: "yyyy-mm-dd",
        autoclose: true,
        todayHighlight: true

    });
}

function initTimepicker(id){
    $("#"+id).timepicker({
        minuteStep: 1,
        showSeconds: true,
        showMeridian: false
    });
}

function clearForm(name){
    var inputfind = name+" input,"+name+" select,"+name+" textarea,"+name+" button";
    $(inputfind).each(function (i, item) {
            if (item.type == "radio" || item.type == "checkbox") {
                item.checked = false;
            }else if(item.type == "file"){
                item.value = "";
                $("#"+item.id+"-fileList-div").html("");
                $("#"+item.id+"-fileList").addClass("d-none");
            }else {//if(item.type=="button")
                if (item.id != null && item.id.indexOf("save-btn") != -1) {
                    $("#" + item.id).removeClass("disabled");
                    $("#"+item.id).removeAttr("disabled");
                } else {
                    item.value = "";
                }
            }

        }
    )
    reSetValidateElement();
    return inputfind;
}

function reSetValidateElement(){
    $(".form-group").removeClass('has-info').removeClass('has-error');
    $(".help-block").remove();
}

function setElementChecked(name,value){
    var inputs = $("input[name='"+name+"']");
    var values = value.split(",");
    $.each(inputs, function (i, item) {
        $.each(values, function (j, v) {
            if (item.value == v) item.checked = true;
        })
    })
}

function setFormData(name,data){
    var inputfind = name+" input,"+name+" select,"+name+" textarea";
    $(inputfind).each(function (i, item) {
        $.each(data, function (k, v) {
            if (item.name == k) {
                if (item.type == "radio" || item.type == "checkbox") {
                    var values = v.split(",");
                    $.each(values, function (j, s) {
                        if (item.value == s) item.checked = true;
                    })
                } else if (item.type == "file") {
                    setFileHtml(item,v);
                } else {
                    item.value = v;
                }
            }

        })

    })
}

function setFileHtml(item,v){
    $("#"+item.id+"-fileList-div").html("");
    $("#"+item.id+"-fileList").addClass("d-none");

    if (v != null && v.length > 0) {
        var fileListstr = "";
        $("#"+item.id+"-fileList").removeClass("d-none");
        $.each(v, function (j, value) {
            fileListstr += '<a class="list-group-item list-group-item-action" target="_bank" href="/admin/test/download?filePath=' + value.filePath + '">' + value.remark + '</a>';
        })
        $("#"+item.id+"-fileList-div").html(fileListstr);
    }
}


function setFileViewHtml(item,k,data){
    $("#"+item.id).html("");
    var fileListstr = "";
    var fileList = data;
    if(fileList!=null && fileList.length>0){
        fileListstr = "<div class='list-group'>";

        $.each(fileList,function(j,value){
            fileListstr += '<a class="list-group-item list-group-item-action" target="_bank" href="/admin/test/download?filePath='+value.filePath+'">'+value.remark+'</a>';
        })
        fileListstr += "</div>";
    }
    $("#"+item.id).html(fileListstr);
}

/**
 * set ckeditor html data
 * @param element
 * @param data
 */
function setEditorHtml(element,data){
    var sel = element.getSelection();
    var range = sel.getRanges()[0];

    // no range, means the editor is empty. Select the range.
    if (!range) {
        range = element.createRange();
        range.selectNodeContents(element.editable());
        sel.selectRanges([range]);
    }

    element.insertHtml(data);
}

function getCheckboxIdByTable(tableId){
    var items = $("#"+tableId).find("tr > td:first-child input:checkbox:checked");
    if (items.length == 0) {
        webAlert("","请先选择需要操作的数据");
        return;
    }
    return items[0].value;
}

/**
 * 设置ajax数据到表单元素
 * set ajax data to form elements
 * parameter:
 * tableId: table id,get id by table checkbox,and ajax success refresh dataTables data
 * url: post ajax url,
 * formId: form id,set form elements value,
 * modalId: if back success, show modal dialog,
 * notAlert: dont show prompt dialog,
 * second parameter is success callback function
 */
function setAjaxDataToForm(){
    var arg = Array.prototype.slice.call(arguments);
    var callback;
    var obj;
    if(arg.length>0){
        for(var i=0;i<arg.length;i++){
            if(typeof arg[i]=="object"){
                obj = arg[i];
            }else if(typeof arg[i]=="function"){
                callback = arg[i];
            }
        }
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: obj.url,
        dataType: "json",
        success: function (jsondata) {
            // console.debug(jsondata);
            if (jsondata.code == getSuccessStatus()) {
                console.debug("获取数据并赋值");
                var data = jsondata.data;
                if(obj.formId!=null)setFormData("#"+obj.formId,data);
                //回调函数
                if(callback!=null)callback(data);
                if(obj.modalId!=null){
                    $("#"+obj.modalId).modal();
                }
                return jsondata;
            } else {
                if(obj.notAlert!=true)webAlert("["+jsondata.code+"] 获取数据失败",jsondata.msg,"error");
                return false;
            }

        },
        error: function (data) {
            if(obj.notAlert!=true)webAlert("",getErrorInfo(data,"获取数据失败"),"error");
            return false;
        }
    })
}

/**
 * 删除表格数据
 * delete data by data table checkbox data value
 * parameter:
 * tableId: table id,get id by table checkbox,and ajax success refresh dataTables data
 * url: post ajax url,
 * notAlert: dont show prompt dialog,
 * second parameter is success callback function
 */
function deleteByTableIds(){
    var arg = Array.prototype.slice.call(arguments);
    var callback;
    var obj;
    var hasDelIds = true;
    if(arg.length>0){
        for(var i=0;i<arg.length;i++){
            if(typeof arg[i]=="object"){
                obj = arg[i];
                if(obj.delIds!=null)hasDelIds = obj.delIds;
            }else if(typeof arg[i]=="function"){
                callback = arg[i];
            }
        }
    }

    var checkeds = 0;
    var delids = "";
    var result = false;
    $("#"+obj.tableId).find("tr > td:first-child input:checkbox:checked")
        .each(function(index,item){
            checkeds++;
            if(delids!="")delids+=",";
            delids+=item.value;
        });
    if(checkeds==0){
        webAlert("","请先选择需要操作的数据");
        return false;
    }else{
        swal(webAlert({
                //icon: "warning",
                title: "",
                text: "删除后数据将不可恢复！确定要删除所选数据吗？",
                dangerMode: true,
                getArg: true,
            })
        ).then(function(btnVal){
            if(btnVal){
                setTimeout(function(){
                    console.debug("删除的数据ids:"+delids);
                    if(hasDelIds){
                        result = deleteAjaxByIds(obj,delids.split(","));
                    }
                    if(callback!=null){
                        result = callback(delids.split(","));
                    }
                    return result;
                },50);
            }

        });
        return result;
    }
}

function deleteAjaxByIds(obj,data){
    $.ajax( {
        type: "POST",
        contentType: "application/json",
        url: obj.url,
        dataType:"json",
        data: JSON.stringify(data),
        success: function(data) {
            if(data.code==getSuccessStatus()){
                if(obj.table!=null){
                    console.debug("刷新表格操作");
                    table.draw();
                }
            }else{
                if(obj.notAlert!=true)webAlert("["+data.code+"] 删除数据失败",data.msg,"error");
            }

        },
        error: function(data){
            if(obj.notAlert!=true)webAlert("",getErrorInfo(data,"删除数据失败"),"error");
            return false;
        }
    })
}

/**
 * select table all checkbox by first checkbox
 */
function allChecked() {
    var that = this;
    $(this).closest('table').find('tr > td:first-child input:checkbox')
        .each(function () {
            this.checked = that.checked;
        })

};

/**
 * ajax post提交json表单数据
 * ajax post json data,back data by call function parameter data,
 * parameter remark:
 * url: post data url,
 * validate: default false,set true call jquery validation valid form,
 * preHander: post pre hander function,
 * btnId: post button, post processing button set disabled,
 * modalId: if back success, close modal dialog,
 * notAlert: dont show prompt dialog,
 * success: success call function,
 * error: fail call function,
 * second parameter is success callback function
 */
function postData(){
    var arg = Array.prototype.slice.call(arguments);
    var callback;
    var obj;
    if(arg.length>0){
        for(var i=0;i<arg.length;i++){
            if(typeof arg[i]=="object"){
                obj = arg[i];
            }else if(typeof arg[i]=="function"){
                callback = arg[i];
            }
        }
    }

    var $validation = false;
    if (!$("#"+obj.formId).valid()) {
        console.debug("validate:false");
        return false;
    }
    var formData = new FormData($("#"+obj.formId)[0]);

    if(callback!=null){
        if(!callback(formData))return false;
    }
    if(obj.preHander!=null){
        if(!obj.preHander(formData))return false;
    }

    if(obj.btnId!=null){
        $("#"+obj.btnId).addClass("disabled");
        $("#"+obj.btnId).attr("disabled","disabled");
    }
    $.ajax({
        type: "POST",
        processData: false,
        contentType: false,
        url: obj.url,
        dataType: "json",
        data: formData,//JSON.stringify(
        success: function (data) {
            if(obj.btnId!=null){
                $("#"+obj.btnId).removeClass("disabled");
                $("#"+obj.btnId).removeAttr("disabled");
            }

            if (data.code != getSuccessStatus()) {
                if(obj.error!=null)obj.error(data);
                if(obj.notAlert!=true)webAlert("["+data.code+"] 处理出错！", data.msg);
                return false;
            }
            if(obj.table!=null){
                console.debug("刷新表格操作");
                obj.table.draw();
            }
            if(obj.modalId!=null)$("#"+obj.modalId).modal("hide");
            if(obj.success!=null)obj.success(data);
            return data;
        },
        error: function (data) {
            if(obj.btnId!=null){
                $("#"+obj.btnId).removeClass("disabled");
                $("#"+obj.btnId).removeAttr("disabled");
            }
            if(obj.error!=null)obj.error(data);
            if(obj.notAlert!=true)webAlert("",getErrorInfo(data,"提交数据失败"),"error");
            return false;
        }
    })
    return false;
}

/**
 * ajax 返回json数据并且设置value到表单元素
 * ajax get json data and set value to form elements,
 * back data status link getSuccessStatus() function,
 * parameter demo: showData({url:'url',[inputIdPre: 'form-*',...,success: function(data){}]},[function(data){}])
 * parameter remark:[] is optional,
 * url: ajax url,
 * inputIdPre: form element current id pre, e.g. view-form-*,
 * fileIds: file id, multiple separated by comma,
 * trans: input element by select/radio/checkbox, set value is 'value<br />value<br />value...',
 * modalId: if back success, close modal dialog,
 * notAlert: dont show prompt dialog,
 * success: success call function,
 * error: fail call function,
 * second parameter is success callback function
 */
function showData(){
    var arg = Array.prototype.slice.call(arguments);
    var callback;
    var obj;
    if(arg.length>0){
        for(var i=0;i<arg.length;i++){
            if(typeof arg[i]=="object"){
                obj = arg[i];
            }else if(typeof arg[i]=="function"){
                callback = arg[i];
            }
        }
    }

    var fileIds;
    var trans;
    if(obj.fileIds!=null)fileIds = obj.fileIds.split(",");
    if(obj.trans!=null)trans = obj.trans;
    $.ajax( {
        type: "GET",
        contentType: "application/json",
        url: obj.url,
        dataType:"json",
        success: function(jsondata) {
            //console.debug(jsondata);
            if(jsondata.code==getSuccessStatus()) {
                console.debug("获取数据并赋值");
                var data = jsondata.data;
                if(obj.inputIdPre!=null){
                    $("div[id^="+obj.inputIdPre+"]").each(function (i, item) {
                        $.each(data, function (k, v) {
                            if (item.id == obj.inputIdPre + k) {
                                var hasFileField = false;
                                var hasTransField = false;
                                var transType;
                                if(fileIds!=null){
                                    for (var j=0;j<fileIds.length;j++){
                                        if(fileIds[j]==k){
                                            hasFileField = true;
                                            break;
                                        }
                                    }
                                }
                                if(trans!=null){
                                    for (var j=0;j<trans.length;j++){
                                        if(trans[j].name==k){
                                            hasTransField = true;
                                            transType = trans[j].items;
                                            break;
                                        }
                                    }
                                }
                                if(hasFileField){
                                    console.warn(hasFileField,item,k,v);
                                    setFileViewHtml(item,k,v);
                                }else if(hasTransField){
                                    item.innerHTML = getCurrentComboText(transType,v,"<br />");
                                }else{
                                    item.innerText = v;
                                }
                            }
                        })

                    })
                }
                if(callback!=null)callback(data);
                if(obj.success!=null)obj.success(data);

                if(obj.modalId!=null)$("#"+obj.modalId).modal();
                return data;
            }else{
                if(obj.error!=null)obj.error(data);
                if(obj.notAlert!=true)webAlert("["+jsondata.code+"] 获取数据失败",jsondata.msg,"error");
            }

        },
        error: function(data){
            if(obj.error!=null)obj.error(data);
            if(obj.notAlert!=true)webAlert("",getErrorInfo(data,"获取数据失败"),"error");
            return false;
        }
    });
}

/**
 * defalut server back business status
 * @returns {string}
 */
function getSuccessStatus(){
    return "b200";
}

function getErrorInfo(data,info){
    var msg = "["+data.status+"] ";
    if(data.readyState=="4" && data.status=="200"){
        msg += "服务调整，"+info;
    }else if(data.status==404){
        msg += "页面没找到，"+info;
    }else if(data.status==0){
        msg += "无法连接至服务器，"+info;
    }
    return msg;
}

function clearTagit(id){
    $("#"+id).tagit("removeAll");
}

/**
 * set flags value to tagit plugins
 * @param id
 * @param value
 */
function setTagit(id,value){
    if(value==null)value="";
    var values = value.split(",");
    for(var i=0;i<values.length;i++){
        $("#"+id).tagit("createTag", values[i]);
    }
}

/**
 * tagit plugins get flags value
 * @param id
 * @returns {string|string}
 */
function getTagitValue(id){
    var data = $("#"+id).tagit("assignedTags");
    var value = "";
    for(var i=0;i<data.length;i++){
        value += (value!="" ? "," : "")+data[i];
    }
    return value;
}

/**
 * create flags, string separated by comma, to transform label flag
 * 生成标签，字符串按逗号分隔，每个分隔字符生成label标签
 * @param value
 * @param className
 * @returns {string|*}
 */
function setTagLabel(value,className){
    if(value==null || value.trim=="")return value;
    var values = value.split(",");
    var html = "";
    for(var i=0;i<values.length;i++){
        if(values[i]=="")continue;
        html+='<span class="label '+className+'">'+values[i]+'</span> ';
    }
    return html;
}

/**
 * delete all string html flag
 * @param str
 * @returns {*}
 */
function delHtmlTag(str){
    return str.replace(/<[^>]+>/g,"");
}

/**
 * format file size unit
 * @param filesize
 * @returns {string}
 */
function getFileSizeStr(filesize){
    if(filesize==null || filesize=="")return "";
    var filesizeStr = "";
    if(filesize/1024<1){
        filesizeStr = filesize+" byte";
    }else if(filesize/1024>=1 && filesize/1024/1024<1){
        filesizeStr += (Math.round(filesize/1024*100)/100)+" KB";
    }else if(filesize/1024/1024>=1 && filesize/1024/1024/1024<1){
        filesizeStr += (Math.round(filesize/1024/1024*100)/100)+" MB";
    }else if(filesize/1024/1024/1024>=1 && filesize/1024/1024/1024/1024<1){
        filesizeStr += (Math.round(filesize/1024/1024/1024*100)/100)+" GB";
    }else if(filesize/1024/1024/1024/1024>=1 && filesize/1024/1024/1024/1024/1024<1){
        filesizeStr += (Math.round(filesize/1024/1024/1024/1024*100)/100)+" TB";
    }
    return filesizeStr;
}

/**
 * file input get local image path，refresh to img src and show image
 * 从file input中读取本地图片路径，并更新到img src，实时展示图片或头像
 * @param input
 * @param imgId
 */
function readURL(input,imgId) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $("#"+imgId).attr('src', e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
    }
}
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8" />
    <title>文件浏览 - Web Project</title>

    <meta name="description" content="文件浏览" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link rel="icon" href="../favicon/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="../favicon/favicon.ico" type="image/x-icon" />

    <!-- basic styles -->
    <!-- ================== BEGIN BASE CSS STYLE ================== -->
    <link href="/assets/css/blog/googleapis.fonts.css" rel="stylesheet" />
    <link href="/assets/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" />
    <link href="/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/assets/plugins/font-awesome/css/all.min.css" rel="stylesheet" />
    <link href="/assets/plugins/animate/animate.min.css" rel="stylesheet" />
    <link href="/assets/css/default/style.min.css" rel="stylesheet" />
    <link href="/assets/css/default/style-responsive.min.css" rel="stylesheet" />
    <link href="/assets/css/default/theme/default.css" rel="stylesheet" id="theme" />
    <link href="/assets/css/default/theme/webproject.css" rel="stylesheet" />
    <!-- ================== END BASE CSS STYLE ================== -->

    <!-- ================== BEGIN BASE JS ================== -->
<#--    <script src="/assets/plugins/pace/pace.min.js"></script>-->
    <!-- ================== END BASE JS ================== -->

    <link href="/assets/plugins/DataTables/media/css/dataTables.bootstrap.min.css" rel="stylesheet"
          xmlns="http://java.sun.com/jsf/html"/>
    <link href="/assets/plugins/DataTables/extensions/Responsive/css/responsive.bootstrap.min.css" rel="stylesheet" />
    <link href="/assets/plugins/DataTables/extensions/Buttons/css/buttons.bootstrap.css" rel="stylesheet" />


</head>

<body>

<!-- begin #page-container -->
<div id="page-container" class="page-container">
    <div id="content" class="content m-2">
        <!-- begin panel -->
        <div class="panel panel-inverse">
            <!-- begin panel-heading -->
            <div class="panel-heading">
                <#include "../public/include_panel_heading_btn.ftl" />
                <h4 class="panel-title">文件浏览</h4>
            </div>
            <!-- end panel-heading -->
            <!-- begin panel-body -->
            <div class="panel-body">
                <input type="hidden" id="fileTableAllSelectIds" name="fileTableAllSelectIds" value="" />
                <table id="file_table" class="table table-striped table-bordered dataTable"><!-- table-hover-->
                    <thead>
                    <tr>
                        <th class="text-nowrap">文件名</th>
                        <th class="text-nowrap">文件描述</th>
                        <th class="text-nowrap">文件类型</th>
                        <th class="text-nowrap">文件大小</th>
                        <th class="text-nowrap">文件状态</th>
                        <th class="text-nowrap">上传时间</th>
                        <th class="text-nowrap" data-orderable="false">选择</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>

                </table>

            </div>

            <div id="modal_dialog_div">
                <#include "view.ftl" />
            </div>
            <!-- end panel-body -->
        </div>
        <!-- end panel -->
    </div>
    <!-- end #content -->

    <!-- begin scroll to top btn -->
    <a href="javascript:;" class="btn btn-icon btn-circle btn-success btn-scroll-to-top fade" data-click="scroll-top"><i class="fa fa-angle-up"></i></a>
    <!-- end scroll to top btn -->
</div>
<!-- end page container -->

<!-- footer -->
<!-- ================== BEGIN BASE JS ================== -->
<script src="/assets/plugins/jquery/jquery-3.3.1.min.js"></script>
<script src="/assets/plugins/jquery/jquery-migrate-1.1.0.min.js"></script>
<script src="/assets/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!--[if lt IE 9]>
<script src="/assets/crossbrowserjs/html5shiv.js"></script>
<script src="/assets/crossbrowserjs/respond.min.js"></script>
<script src="/assets/crossbrowserjs/excanvas.min.js"></script>
<![endif]-->
<script src="/assets/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/assets/plugins/js-cookie/js.cookie.js"></script>
<script src="/assets/plugins/bootstrap-sweetalert/sweetalert.min.js"></script>
<script src="/assets/plugins/gritter/js/jquery.gritter.min.js"></script>

<script src="/assets/plugins/jquery-validation/jquery.validate.min.js"></script>
<script src="/assets/plugins/jquery-validation/additional-methods.min.js"></script>
<script src="/assets/plugins/jquery-validation/localization/messages_zh.js"></script>
<script src="/assets/js/theme/default.min.js"></script>
<script src="/assets/js/apps.js"></script>
<script src="/assets/js/webproject.js"></script>
<!-- ================== END BASE JS ================== -->


<script src="/assets/plugins/DataTables/media/js/jquery.dataTables.min.js"></script>

<script src="/assets/plugins/DataTables/media/js/dataTables.bootstrap.min.js"></script>
<script src="/assets/plugins/DataTables/extensions/Responsive/js/dataTables.responsive.min.js"></script>
<script src="/assets/plugins/DataTables/extensions/Buttons/js/dataTables.buttons.min.js"></script>
<script src="/assets/plugins/DataTables/extensions/Buttons/js/buttons.bootstrap.min.js"></script>
<!--$.getScript('../assets/plugins/DataTables/extensions/Buttons/js/buttons.print.min.js'),-->
<script src="/assets/plugins/DataTables/extensions/Select/js/dataTables.select.min.js"></script>

<script>
    $(document).ready(function() {

        var statusList = new Array(
            <#list statusList as item>
            {code:"${item.code}", name:"${item.name}"},
            </#list>
        );

        initElement();

        function initElement(){
            table = initDataTables(getDataTablesPara());
        }

        function getDataTablesPara(){
            var url = "/admin/userFile/query";
            var tableoptions = {
                tableId: "file_table",
                ajax: {
                    "url": url,
                },
                columns: [
                    {
                        data: null, name:"fileName",render: function (data, type, row) {
                            return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
                                + data.fileName + '</a>';
                        }
                    },
                    {
                        data: null, name:"fileDescription",render: function (data, type, row) {
                            return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
                                + data.fileDescription + '</a>';
                        }
                    },
                    {
                        data: null, name:"type",render: function (data, type, row) {
                            return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
                                + data.type + '</a>';
                        }
                    },
                    {
                        data: null, name:"fileSize",render: function (data, type, row) {
                            return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
                                + getFileSizeStr(data.fileSize) + '</a>';
                        }
                    },
                    {
                        data: null, name:"status",render: function (data, type, row) {
                            return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
                                +getCurrentComboText(statusList,data.status,"<br />")+'</a>';
                        }
                    },
                    {
                        data: null, name:"createTime",render: function (data, type, row) {
                            return '<a href="javascript:void(0);" onClick="showView(\'' + data.id + '\')">'
                                + data.createTime + '</a>';
                        }
                    },
                    {
                        data: null, name:"createTime",render: function (data, type, row) {
                            return '<button class="btn btn-info btn-xs btn-info" ' +
                                'onClick="broswerSelect(\''+data.filePath.replace("\\","/")+'\')">选取</button>';
                        }
                    },
                ],
                language: {
                    //old version
                    "url": "/assets/js/jqueryDatabaseLanguage_zh_CN.txt"
                    //new version not support
                    //"url": "../assets/js/dataTableLanguage_zh_CN.txt"
                },


            }
            return tableoptions;
        };

        showView = function(id) {
            showData({
                    url: "/admin/userFile/view?id="+id,
                    inputIdPre: "userFile-view-",
                    modalId: "userFile-view-modal",
                    trans:[
                        {
                            name: "status",
                            items: statusList,
                        },
                    ]
                },function(data){
                    $("#userFile-view-fileSize").html(getFileSizeStr(data.fileSize));
                    var types = "txt,ftl,png,jpg,gif,pdf,xml".split(",");
                    var hasPreview = false;
                    for(var i=0;i<types.length;i++){
                        console.log(data.type.lastIndexOf(types[i]));
                        if(data.fileName.lastIndexOf(types[i])>1 || data.type.lastIndexOf(types[i])>1){
                            hasPreview = true;
                            break;
                        }
                    }
                    if(hasPreview){
                        $("#preview-btn").removeClass("disabled");
                        $("#preview-btn").removeAttr("disabled");
                    }else{
                        $("#preview-btn").addClass("disabled");
                        $("#preview-btn").attr("disabled","disabled");
                    }

                }
            )
        };

        broswerSelect = function(filepath){
            window.opener.CKEDITOR.tools.callFunction(${CKEditorFuncNum},"${urlPath}"+filepath);
            window.close();
        }


        $("#preview-btn").on("click",function(){
            var win = window.open('${urlPath}'+$("#userFile-view-filePath").text(),'_blank');
            win.focus();
            return false;
        });

        $("#download-btn").on("click",function(){
            var win = window.open("../userFile/download?filePath=/"+$("#userFile-view-filePath").text().replace("\\","/"),"_blank");
            win.focus();
            return false;
        })

    });
</script>
</body>
</html>



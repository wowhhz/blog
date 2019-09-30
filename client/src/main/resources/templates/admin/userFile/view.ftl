<div id="userFile-view-modal" class="modal fade" tabindex="-1">
    <form id="userFile-view-form" action="" method="get">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="blue bigger">文件</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="row">

                        <div class="col-xs-12 col-sm-12">

                            <table class="table table-bordered table-striped m-b-0"><!-- table-hover-->
                                <tbody>
                                <tr>
                                    <th class="width-100">id</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-id"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">文件名</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-fileName"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">文件描述</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-fileDescription"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">文件路径</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-filePath"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">文件类型</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-type"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">文件大小</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-fileSize"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">图像尺寸</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-imgSize"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">文件状态</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-status"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">文件标志</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-flag"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">所属组</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-groupId"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">所属编号</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-actionId"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">上传人编号</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-userId"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">上传人名称</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-userName"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">上传时间</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-createTime"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-100">备注</th>

                                    <td>
                                        <div class="overflow-auto" id="userFile-view-remark"></div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button class="btn btn-default" id="preview-btn">预览</button>
                    <button class="btn btn-default" id="download-btn">下载</button>
                    <button class="btn btn-lime" data-dismiss="modal">
                        关闭
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
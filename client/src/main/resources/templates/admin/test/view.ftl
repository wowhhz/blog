<div id="test-view-modal" class="modal fade" tabindex="-1">
    <form id="test-view-form" action="" method="get">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="blue bigger">详情</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="row">

                        <div class="col-xs-12 col-sm-12">
                            <input type="hidden" name="id" id="test-view-id" value="" />
                            <input type="hidden" name="createtime" id="test-view-createtime" value="" />

                            <table class="table table-bordered table-striped m-b-0"><!-- table-hover-->
                                <tbody>
                                <tr>
                                    <th class="width-80">名称</th>

                                    <td>
                                        <div id="test-view-name"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>密码</th>

                                    <td>
                                        <div id="test-view-password"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Email</th>

                                    <td>
                                        <div id="test-view-email"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>日期</th>

                                    <td>
                                        <div id="test-view-dater"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>时间</th>

                                    <td>
                                        <div id="test-view-timer"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>下拉项</th>

                                    <td>
                                        <div id="test-view-selectr"></div>
                                    </td>
                                </tr>

                                <tr>
                                    <th>单选项</th>

                                    <td>
                                        <div id="test-view-radio"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>多选项</th>

                                    <td>
                                        <div id="test-view-checkbox"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>大文本</th>

                                    <td>
                                        <div id="test-view-textarea"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>文件</th>

                                    <td>
                                        <div id="test-view-userFile"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>内容</th>

                                    <td>
                                        <div class="overflow-auto" id="test-view-content"></div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button class="btn btn-lime" data-dismiss="modal">
                        关闭
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
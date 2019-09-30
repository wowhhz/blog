<div id="userFile-form-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="userFile-form-modal" aria-hidden="true" data-backdrop="static" data-keybrard="true">
    <form id="userFile-edit-form" action="" method="">
				<input type="hidden" name="id" id="userFile-form-id" value="" />
				<input type="hidden" name="userId" id="userFile-form-userId" value="" />
				<input type="hidden" name="userName" id="userFile-form-userName" value="" />
				<input type="hidden" name="createTime" id="userFile-form-createTime" value="" />
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">文件</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">文件名</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="fileName" id="userFile-form-fileName" placeholder="请输入文件名" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">文件描述</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="fileDescription" id="userFile-form-fileDescription" placeholder="请输入文件描述" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">文件路径</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="filePath" id="userFile-form-filePath" placeholder="请输入文件路径" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">文件类型</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="type" id="userFile-form-type" placeholder="请输入文件类型" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">文件大小</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="fileSize" id="userFile-form-fileSize" placeholder="请输入文件大小" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">图像尺寸</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="imgSize" id="userFile-form-imgSize" placeholder="请输入图像尺寸" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">文件状态</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <select class="form-control m-b-5" name="status" id="userFile-form-status" placeholder="请选择文件状态">
                                <#list statusList as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">文件标志</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="flag" id="userFile-form-flag" placeholder="请输入文件标志" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">所属组</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="groupId" id="userFile-form-groupId" placeholder="请输入所属组" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">所属编号</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="actionId" id="userFile-form-actionId" placeholder="请输入所属编号" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">备注</label>
                        <div class="col-md-9">
                            <textarea rows="6" class="form-control m-b-5" name="remark" id="userFile-form-remark" placeholder="请输入备注"></textarea>
                        </div>
                    </div>


                </div>

                <div class="modal-footer">
                    <button class="btn btn-white" data-dismiss="modal">
                        取消
                    </button>

                    <button class="btn btn-primary" id="userFile-form-save-btn">
                        保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
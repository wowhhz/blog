<div id="classType-form-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="classType-form-modal" aria-hidden="true" data-backdrop="static" data-keybrard="true">
    <form id="classType-edit-form" action="" method="">
				<input type="hidden" name="id" id="classType-form-id" value="" />
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">分类</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">名称</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="name" id="classType-form-name" placeholder="请输入名称" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">编码</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="code" id="classType-form-code" placeholder="请输入编码" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">排序</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="soft" id="classType-form-soft" placeholder="请输入排序" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">父类编号</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="parentCode" id="classType-form-parentCode" placeholder="请输入父类编号" />
                        </div>
                    </div>


                </div>

                <div class="modal-footer">
                    <button class="btn btn-white" data-dismiss="modal">
                        取消
                    </button>

                    <button class="btn btn-primary" id="classType-form-save-btn">
                        保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
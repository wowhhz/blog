<div id="user-form-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="user-form-modal" aria-hidden="true" data-backdrop="static" data-keybrard="true">
    <form id="user-edit-form" action="" method="">
				<input type="hidden" name="id" id="user-form-id" value="" />
				<input type="hidden" name="createTime" id="user-form-createTime" value="" />
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">用户</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">头像</label>
                        <div class="col-md-6 col-xs-12 col-sm-6 text-center">
                            <img class="userImage" id="user-form-icon-show" src="" />
                            <input type="file" class="form-control m-b-5" name="icon" id="user-form-icon" accept="image/*" placeholder="请输入头像" onchange="readURL(this,'user-form-icon-show');" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">名字</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="name" id="user-form-name" placeholder="请输入名字" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">用户名</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="username" id="user-form-username" placeholder="请输入用户名" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">密码</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="password" class="form-control m-b-5" name="password" id="user-form-password" placeholder="请输入密码" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">状态</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <select class="form-control m-b-5" name="status" id="user-form-status" placeholder="请选择状态">
    														<#list statusList as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">Email</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="email" class="form-control m-b-5" name="email" id="user-form-email" placeholder="请输入Email" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">联系电话</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="phone" id="user-form-phone" placeholder="请输入联系电话" />
                        </div>
                    </div>


                </div>

                <div class="modal-footer">
                    <button class="btn btn-white" data-dismiss="modal">
                        取消
                    </button>

                    <button class="btn btn-primary" id="user-form-save-btn">
                        保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
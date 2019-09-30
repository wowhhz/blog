<div id="comment-form-modal" class="modal fade" role="dialog" aria-labelledby="comment-form-modal" aria-hidden="true" data-backdrop="static" data-keybrard="true">
    <form id="comment-edit-form" action="" method=""><!-- tabindex="-1"-->
				<input type="hidden" name="id" id="comment-form-id" value="" />
				<input type="hidden" name="articleId" id="comment-form-articleId" value="" />
				<input type="hidden" name="releaseTime" id="comment-form-releaseTime" value="" />
				<input type="hidden" name="likeNum" id="comment-form-likeNum" value="" />
				<input type="hidden" name="unlikeNum" id="comment-form-unlikeNum" value="" />
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">评论</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">用户名</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="name" id="comment-form-name" placeholder="请输入用户名" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">头像</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <input type="hidden" class="form-control m-b-5" name="icon" id="comment-form-icon" placeholder="请输入头像" />
                            <div class="btn-group comment-list m-0">
                                <div class="comment-avatar m-0" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <img src="../assets/img/user/noavatar.png" id="comment-form-avatar">
                                </div>
                                <div class="dropdown-menu width-sm" style="min-width:300px;margin:0;z-index:1021;">
                                    <ul class="list-inline comment-avatar-list" id="comment_icon_list">
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">评论</label>
                        <div class="col-md-10">
                            <textarea class="ckeditor" rows="6" name="content" id="comment-form-content" placeholder="请输入评论" required></textarea>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">Email</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="email" id="comment-form-email" placeholder="请输入Email" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">网站</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="site" id="comment-form-site" placeholder="请输入网站" />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">是否审核</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <select class="form-control m-b-5" name="hasCheck" id="comment-form-hasCheck" placeholder="请选择是否审核">
                                <#list hasCheckList as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">预审</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <select class="form-control m-b-5" name="preCheck" id="comment-form-preCheck" placeholder="请选择预审">
                                <#list preCheckList as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">排序</label>
                        <div class="col-md-2 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="sort" id="comment-form-sort" placeholder="请输入排序" />
                        </div>
                    </div>


                </div>

                <div class="modal-footer">
                    <button class="btn btn-white" data-dismiss="modal">
                        取消
                    </button>

                    <button class="btn btn-primary" id="comment-form-save-btn">
                        保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
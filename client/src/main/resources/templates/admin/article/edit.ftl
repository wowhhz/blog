<div id="article-form-modal" class="modal fade" role="dialog" aria-labelledby="article-form-modal" aria-hidden="true" data-backdrop="static" data-keybrard="true">
    <form id="article-edit-form" action="" method=""><!-- tabindex="-1"-->
				<input type="hidden" name="id" id="article-form-id" value="" />
				<input type="hidden" name="releaseTime" id="article-form-releaseTime" value="" />
				<input type="hidden" name="readNum" id="article-form-readNum" value="" />
				<input type="hidden" name="likeNum" id="article-form-likeNum" value="" />
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">文章</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">分类</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <select class="form-control m-b-5" name="classTypeCode" id="article-form-classTypeCode" placeholder="请选择分类" required>
    							<#list classTypeCodeList as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">标题</label>
                        <div class="col-md-10 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="title" id="article-form-title" placeholder="请输入标题" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">作者</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="author" id="article-form-author" placeholder="请输入作者" />
                        </div>
                    </div>
                    <!-- bootstrap 模态对话框会导致ckeditor无法修改图片，tabindex=-1去除以后正常但模态对话框的ESC健无效-->
                    <!-- modal dialog attribute tabindex="-1" cause of ckeditor edit and ESC invalid-->
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">内容</label>
                        <div class="col-md-10 article-content">
                            <textarea class="ckeditor" name="content" id="article-form-content" row="10"></textarea>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">标签</label>
                        <div class="col-md-10 col-xs-12 col-sm-6">
                            <input type="hidden" class="form-control m-b-5" name="flags" id="article-form-flags" placeholder="请输入标签" data-role="tagsinput" />
                            <ul class="primary" id="tag1"></ul>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">是否开启留言</label>
                        <div class="col-md-4 col-xs-12 col-sm-6">
                            <select class="form-control m-b-5" name="hasComment" id="article-form-hasComment" placeholder="请选择是否开启留言" required>
    							<#list hasCommentList as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-2">排序</label>
                        <div class="col-md-2 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="sort" id="article-form-sort" placeholder="请输入排序" />
                        </div>
                    </div>

                </div>

                <div class="modal-footer">
                    <button class="btn btn-white" data-dismiss="modal">
                        取消
                    </button>

                    <button class="btn btn-primary" id="article-form-save-btn">
                        保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
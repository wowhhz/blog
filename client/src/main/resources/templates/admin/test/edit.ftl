<div id="test-form-modal" class="modal fade"  role="dialog" aria-labelledby="test-form-modal" aria-hidden="true" data-backdrop="static" data-keybrard="true">
    <form id="test-edit-form" action="" method=""><!--  tabindex="-1"-->
        <input type="hidden" name="id" id="test-form-id" value="" />
        <input type="hidden" name="createtime" id="test-form-createtime" value="" />
        <input type="hidden" name="pid" id="test-form-pid" value="" />
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">test编辑</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">名称</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5" name="name" id="test-form-name" placeholder="请输入名称" required>

                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">密码</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="password" class="form-control m-b-5" name="password" id="test-form-password" placeholder="请输入密码" required>

                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">email</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="email" class="form-control m-b-5" name="email" id="test-form-email" placeholder="请输入email" required>

                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">日期</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input class="form-control m-b-5 bootstrap-datepicker" type="text" name="dater" id="test-form-dater" placeholder="请输入日期" value="" required />
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">时间</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <input type="text" class="form-control m-b-5 bootstrap-timepicker" name="timer" id="test-form-timer" placeholder="请输入时间" value="" required />

                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">下拉项</label>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <select class="form-control m-b-5" name="selectr" id="test-form-selectr" placeholder="请选择下拉项" required>
                                <#list selectConf as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                            </select>

                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">单选项</label>
                        <div class="col-md-9">
                            <div class="control-group">
                                <#list radioConf as item>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="radio" id="test-form-radio${item.code}" value="${item.code}"<#if item?index==0> required</#if> />
                                        <label class="form-check-label" for="test-form-radio${item.code}">${item.name}</label>
                                    </div>
                                </#list>
                            </div>

                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">多选项</label>
                        <div class="col-md-9">
                            <div class="control-group">
                                <#list checkboxConf as item>
                                    <div class="form-check">
                                        <input class="form-check-input m-b-5" type="checkbox" name="checkbox" id="test-form-checkbox${item.code}" value="${item.code}"<#if item?index==0> required</#if>>
                                        <label class="form-check-label" for="test-form-checkbox${item.code}">${item.name}</label>
                                    </div>
                                </#list>
                            </div>

                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">大文本</label>
                        <div class="col-md-9">
                            <textarea rows="6" class="form-control m-b-5" name="textarea" id="test-form-textarea" placeholder="请输入大文本" required></textarea>
                        </div>
                    </div>
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">文件</label>
                        <div class="col-md-9">
                            <input multiple="" class="form-control m-b-5" type="userFile" name="userFile" id="test-form-userFile" />
                            <div id="test-form-userFile-fileList" class="well d-none">
                                <h4 class='green smaller lighter'>文件清单</h4>
                                <div id="test-form-userFile-fileList-div" class="list-group">
                                </div>
                            </div>

                        </div>
                    </div>
                    <!-- bootstrap 模态对话框对引起cdeditor无法修改图片，tabindex=-1去除以后模态对话框的ESC健无效-->
                    <!-- modal dialog attribute tabindex="-1" cause of ckeditor edit and ESC invalid-->
                    <div class="form-group row m-b-15">
                        <label class="col-form-label col-md-3">编辑器</label>
                        <div class="col-md-9">
                            <textarea class="ckeditor" name="content" id="test-form-content" row="20"></textarea>
                        </div>
                    </div>



                </div>

                <div class="modal-footer">
                    <button class="btn btn-white" data-dismiss="modal">
                        取消
                    </button>

                    <button class="btn btn-primary" id="test-form-save-btn">
                        保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
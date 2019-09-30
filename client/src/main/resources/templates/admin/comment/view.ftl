<div id="comment-view-modal" class="modal fade" tabindex="-1">
    <form id="comment-view-form" action="" method="get">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="blue bigger">评论</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="row">

                        <div class="col-xs-12 col-sm-12">

                            <table class="table table-bordered table-striped m-b-0"><!-- table-hover-->
                                <tbody>
                                <tr>
                                    <th class="width-80">id</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-id"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">文章ID</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-articleId"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">用户名</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-name"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">头像</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-icon"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">评论时间</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-releaseTime"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">评论</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-content"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">赞同数</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-likeNum"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">不赞同数</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-unlikeNum"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">Email</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-email"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">网站</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-site"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">是否审核</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-hasCheck"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">预审</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-preCheck"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">排序</th>

                                    <td>
                                        <div class="overflow-auto" id="comment-view-sort"></div>
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
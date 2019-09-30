<div id="article-view-modal" class="modal fade" tabindex="-1">
    <form id="article-view-form" action="" method="get">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="blue bigger">文章</h4>
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
                                        <div class="overflow-auto" id="article-view-id"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">分类</th>

                                    <td>
                                        <div class="overflow-auto" id="article-view-classTypeCode"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">标题</th>

                                    <td>
                                        <div class="overflow-auto" id="article-view-title"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">作者</th>

                                    <td>
                                        <div class="overflow-auto" id="article-view-author"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">发布时间</th>

                                    <td>
                                        <div class="overflow-auto" id="article-view-releaseTime"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">内容</th>

                                    <td>
                                        <div class="overflow-auto article-content" id="article-view-content"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80 inverse">标签</th>

                                    <td>
                                        <div id="article-view-flags"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">阅读数</th>

                                    <td>
                                        <div class="overflow-auto" id="article-view-readNum"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">点赞数</th>

                                    <td>
                                        <div class="overflow-auto" id="article-view-likeNum"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">是否开启留言</th>

                                    <td>
                                        <div class="overflow-auto" id="article-view-hasComment"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="width-80">排序</th>

                                    <td>
                                        <div class="overflow-auto" id="article-view-sort"></div>
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
<div id="${MODEL_CODE}-view-modal" class="modal fade" tabindex="-1">
    <form id="${MODEL_CODE}-view-form" action="" method="get">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="blue bigger">${MODEL_NAME}</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <div class="modal-body overflow-visible">
                    <div class="row">

                        <div class="col-xs-12 col-sm-12">

                            <table class="table table-bordered table-striped m-b-0"><!-- table-hover-->
                                <tbody>
<#list MODEL_FIELDS as obj>
                                <tr>
                                    <th class="width-80">${obj["name"]}</th>

                                    <td>
                                        <div class="overflow-auto" id="${MODEL_CODE}-view-${obj["code"]}"></div>
                                    </td>
                                </tr>
</#list>
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
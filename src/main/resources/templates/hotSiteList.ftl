<form id="pagerForm" method="post" action="../api/findAllHotSite">
    <#--<input type="hidden" name="pageNum" value="1" />-->
    <#--<input type="hidden" name="numPerPage" value="${model.numPerPage}" />-->
    <#--<input type="hidden" name="orderField" value="${param.orderField}" />-->
    <#--<input type="hidden" name="orderDirection" value="${param.orderDirection}" />-->
</form>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="../api/toAddHotSitePage" target="dialog"><span>添加</span></a></li>
            <li><a class="delete" id="deleteHotSiteBtn"><span>删除</span></a></li>
            <#--<li><a class="edit" href="demo_page4.html?uid={sid_user}" target="navTab" warn="请选择一个用户"><span>修改</span></a></li>-->
        </ul>
    </div>

    <div id="w_list_print">
        <table class="list" width="98%" targetType="navTab"  layoutH="116">
            <thead>
            <tr>
                <th width="100">站点名称</th>
            </tr>
            </thead>
            <tbody>
            <#if siteList?exists && (siteList?size>0)>
                <#list siteList as site>
                    <tr onclick="chooseTr(this)" rel="${site.id}" >
                        <td>${site.siteName}</td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</div>
<script>
    document.getElementById("deleteHotSiteBtn").onclick = function () {

        alertMsg.confirm("确认删除？", {
            okCall: function() {
                let objId = document.getElementById("deleteHotSiteBtn").getAttribute("rel");
                $.post(
                    "../api/deleteHotSiteById",
                    {
                        id: objId
                    },
                    function (data) {
                        data = JSON.parse(data);
                        if (data.statusCode == 200) {
                            navTab.reload("../api/findAllHotSite", {}, "");
                        }
                    }
                )
            }
        });
    }
    
    function chooseTr(obj) {
        let objId = obj.getAttribute("rel");
        document.getElementById("deleteHotSiteBtn").setAttribute("rel", objId);
    }
</script>
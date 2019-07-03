<form id="pagerForm" method="post" action="../api/findUserListPage">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="numPerPage" value="${dwzPage.numPerPage}" />
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="../api/findUserListPage" method="post">
    </form>
</div>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="../api/toAddUser" target="dialog" width="800" height="500"><span>添加</span></a></li>
            <li><a class="delete" id="deleteUserBtn"><span>删除</span></a></li>
            <#--<li><a class="edit" href="../user/toUpdateUserPage?id={objId}" target="dialog" warn="请选择一个用户"  width="800" height="500"><span>修改</span></a></li>-->
        </ul>
    </div>

    <div id="w_list_print">
        <table class="list" width="98%" targetType="navTab"  layoutH="116">
            <thead>
            <tr>
                <th width="80">用户名</th>
            </tr>
            </thead>
            <tbody>
            <#if dwzPage.dataList?exists && (dwzPage.dataList?size>0)>
                <#list dwzPage.dataList as user>
                <tr onclick="chooseTr(this)" target="objId" rel="${user.id}" >
                    <td>${user.userName}</td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>

    <div class="panelBar">
        <div class="pages">
            <span>当前第${dwzPage.currentPage}页</span>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;共${dwzPage.totalPageNum}页</span>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;共${dwzPage.totalCount}条</span>
        </div>
        <div class="pagination" targetType="navTab" totalCount="${dwzPage.totalCount}" numPerPage="${dwzPage.numPerPage}" pageNumShown="0" currentPage="${dwzPage.currentPage}"></div>
    </div>
</div>
<script>
    document.getElementById("deleteUserBtn").onclick = function () {
        alertMsg.confirm("确认删除？", {
            okCall: function() {
                let objId = document.getElementById("deleteUserBtn").getAttribute("rel");
                $.post(
                        "../api/deleteUserById",
                        {
                            id: objId
                        },
                        function (data) {
                            data = JSON.parse(data);
                            if (data.statusCode == 200) {
                                navTab.reload("../api/findUserListPage", {}, "");
                            }
                        }
                )
            }
        });
    }

    function chooseTr(obj) {
        let objId = obj.getAttribute("rel");
        document.getElementById("deleteUserBtn").setAttribute("rel", objId);
    }
</script>
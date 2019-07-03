<form id="pagerForm"  method="post" action="busCardBindingLog/page?openid=${openid}">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="numPerPage" value="${dwzPage.numPerPage}" />
</form>
<div class="pageContent">
    <table class="table" width="100%" layoutH="330">
        <thead>
        <tr>
            <th width="30">序号</th>
            <th>卡号</th>
            <th>卡号区县</th>
            <th>操作时间</th>
            <th>操作项目（绑卡/解绑）</th>
            <th>操作人</th>
        </tr>
        </thead>
        <tbody>
        <#if dwzPage.dataList?exists && (dwzPage.dataList?size>0)>
            <#list dwzPage.dataList as logList>
            <tr>
                <td>${logList_index + 1}</td>
                <td>${logList.busCardNo}</td>
                <td>${logList.areaName}</td>
                <td>${logList.gmtCreate?datetime}</td>
                <#if logList.operationType == "1">
                    <td>绑定</td>
                <#else>
                    <td>解绑</td>
                </#if>
                <td>
                    <#if logList.createUserName??>
                        ${logList.createUserName}
                    </#if>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>

    <div class="panelBar">
        <div class="pages">
            <span>当前第${dwzPage.currentPage}页</span>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;共${dwzPage.totalPageNum}页</span>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;共${dwzPage.totalCount}条</span>
        </div>
        <div class="pagination" rel="buscardPage;busCardBindingLog/page?openid=${openid}"  targetType="jbsxBox" totalCount="${dwzPage.totalCount}" numPerPage="${dwzPage.numPerPage}" pageNumShown="0" currentPage="${dwzPage.currentPage}"></div>
    </div>
</div>
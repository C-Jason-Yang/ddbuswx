<form id="pagerForm"  method="post" action="redPacketDetail/pageByOpenid?openid=${openid}">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="numPerPage" value="${dwzPage.numPerPage}" />
</form>
<div class="pageContent">
    <table class="table" width="100%" layoutH="330">
        <thead>
        <tr>
            <th width="30">序号</th>
            <th>发放时间</th>
            <th>活动名称</th>
            <th>发放金额（元）</th>
            <th>卡号</th>
            <th>区县</th>
            <th>领取状态</th>
            <th>领取时间</th>
        </tr>
        </thead>
        <tbody>
        <#if dwzPage.dataList?exists && (dwzPage.dataList?size>0)>
            <#list dwzPage.dataList as redPacketReceiveLog>
            <tr>
                <td>${redPacketReceiveLog_index + 1}</td>
                <td>${redPacketReceiveLog.sendDate?datetime}</td>
                <td>${redPacketReceiveLog.activityName}</td>
                <td>${redPacketReceiveLog.amount?string('0.00')}</td>
                <td>${redPacketReceiveLog.busCardNo}</td>
                <td>${redPacketReceiveLog.areaName}</td>
                <#if redPacketReceiveLog.receive??>
                    <#if redPacketReceiveLog.receive == '1'>
                        <td style="color: green;">未领取</td>
                    </#if>
                    <#if redPacketReceiveLog.receive == '2'>
                        <td>已领取</td>
                    </#if>
                    <#if redPacketReceiveLog.receive == '3'>
                        <td style="color: #4c4c4c">退回</td>
                    </#if>
                    <#if redPacketReceiveLog.receive == '4'>
                        <td>发放失败</td>
                    </#if>
                <#else>
                    <td></td>
                </#if>
                <td>
                    <#if (redPacketReceiveLog.receiveDate)??>
                        ${(redPacketReceiveLog.receiveDate?string("yyyy-MM-dd HH:mm:ss"))!' '}
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
        <div class="pagination" rel="buscardPage;redPacketDetail/pageByOpenid?openid=${openid}"  targetType="jbsxBox" totalCount="${dwzPage.totalCount}" numPerPage="${dwzPage.numPerPage}" pageNumShown="0" currentPage="${dwzPage.currentPage}"></div>
    </div>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <span style="height: 29px;">
                活动名称：${redPacketActivity.activityName}&nbsp&nbsp&nbsp&nbsp
                所属区县：${redPacketActivity.areaName}&nbsp&nbsp&nbsp&nbsp
                计划发放数量：${redPacketActivity.redPacketNum}&nbsp&nbsp&nbsp&nbsp
                实际发放数量：${redPacketActivity.actualSendRedpacketNum}&nbsp&nbsp&nbsp&nbsp
                计划发放金额：${redPacketActivity.planSendAmount}&nbsp&nbsp&nbsp&nbsp
                实际发放金额：${redPacketActivity.actualSendAmount}&nbsp&nbsp&nbsp&nbsp
                <a style="display: none;" id="downloadExcelLink" href="${redPacketActivity.redPacketFilePath}"></a>
                <button type="button"onclick="document.getElementById('downloadExcelLink').click();" >下载红包明细文件</button>
            </span>
        </ul>
    </div>

    <div id="w_list_print">
        <table class="list" width="98%" targetType="navTab"  layoutH="90">
            <thead>
            <tr>
                <th>状态</th>
                <th>操作人</th>
                <th>操作时间</th>
            </tr>
            </thead>
            <tbody>
                <#if redPacketActivity.cancelDate??>
                <tr>
                    <td>已取消</td>
                    <td>${redPacketActivity.cancelUserName}</td>
                    <td>${redPacketActivity.cancelDate?datetime}</td>
                </tr>
                </#if>
                <#if redPacketActivity.sendDate??>
                <tr>
                    <td>已发放</td>
                    <td>${redPacketActivity.sendUserName}</td>
                    <td>${redPacketActivity.sendDate?datetime}</td>
                </tr>
                </#if>
                <#if redPacketActivity.confirmDate??>
                <tr>
                    <td>已确认</td>
                    <td>${redPacketActivity.confirmUserName}</td>
                    <td>${redPacketActivity.confirmDate?datetime}</td>
                </tr>
                </#if>
                <#if redPacketActivity.gmtCreate??>
                <tr>
                    <td>已创建</td>
                    <td>${redPacketActivity.createUserName}</td>
                    <td>${redPacketActivity.gmtCreate?datetime}</td>
                </tr>
                </#if>
            </tbody>
        </table>
    </div>
    <div class="formBar">
        <ul>
            <li>
                <div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
            </li>
        </ul>
    </div>
</div>
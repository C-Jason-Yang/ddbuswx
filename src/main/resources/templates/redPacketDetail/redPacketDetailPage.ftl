<form id="pagerForm" method="post" action="redPacketDetail/page">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="numPerPage" value="${dwzPage.numPerPage}" />
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="redPacketDetail/page" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tbody>
                <tr>
                    <td>
                        openID：
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.openid?exists && dwzPage.entity.openid != ''>
                            <input type="text" name="openid" value="${dwzPage.entity.openid}" id="openidInput"/>
                        <#else>
                            <input type="text" name="openid" value=""/>
                        </#if>
                    </td>
                    <td>
                        公交卡号：
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.busCardNo?exists && dwzPage.entity.busCardNo != ''>
                            <input type="text" name="busCardNo" value="${dwzPage.entity.busCardNo}" id="busCardNoInput"/>
                        <#else>
                            <input type="text" name="busCardNo" value=""/>
                        </#if>
                    </td>
                    <td>
                        手机号：
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.phone?exists && dwzPage.entity.phone != ''>
                            <input type="text" name="phone" value="${dwzPage.entity.phone}" id="phoneInput"/>
                        <#else>
                            <input type="text" name="phone" value=""/>
                        </#if>
                    </td>
                    <td class="dateRange">
                        发放时间：
                    <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.sendStartDateStr?exists && dwzPage.entity.sendStartDateStr != ''>
                        <input name="sendStartDateStr" class="date readonly textInput" readonly="readonly"
                               type="text" value="${dwzPage.entity.sendStartDateStr}" id="sendStartDateStrInput">
                    <#else>
                        <input name="sendStartDateStr" class="date readonly textInput" readonly="readonly"
                               type="text" value="">
                    </#if>

                        <span class="limit">-</span>

                    <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.sendEndDateStr?exists && dwzPage.entity.sendEndDateStr != ''>
                        <input name="sendEndDateStr" class="date readonly textInput" readonly="readonly"
                               type="text" value="${dwzPage.entity.sendEndDateStr}" id="sendEndDateStrInput">
                    <#else>
                        <input name="sendEndDateStr" class="date readonly textInput" readonly="readonly"
                               type="text" value="">
                    </#if>
                    </td>
                </tr>
                <tr>
                    <td>
                        活动名称：
                    <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.activityName?exists && dwzPage.entity.activityName != ''>
                        <input type="text" name="activityName" value="${dwzPage.entity.activityName}" id="activityNameInput"/>
                    <#else>
                        <input type="text" name="activityName" value=""/>
                    </#if>
                    </td>
                    <td>
                        交易订单号：
                    <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.mchBillno?exists && dwzPage.entity.mchBillno != ''>
                        <input type="text" name="mchBillno" value="${dwzPage.entity.mchBillno}" id="mchBillnoInput"/>
                    <#else>
                        <input type="text" name="mchBillno" value=""/>
                    </#if>
                    </td>
                    <td>
                        <label>区县：</label>
                        <select class="combox" name="areaCode" id="areaCodeSelect">
                            <option value="">全部</option>
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.areaCode?exists && dwzPage.entity.areaCode != ''>

                            <#if dwzPage.entity.areaCode == 'CN000001'>
                                <option value="CN000001" selected>颍上</option>
                            <#else>
                                <option value="CN000001">颍上</option>
                            </#if>

                            <#if dwzPage.entity.areaCode == 'CN000002'>
                                <option value="CN000002" selected>太和</option>
                            <#else>
                                <option value="CN000002">太和</option>
                            </#if>

                            <#if dwzPage.entity.areaCode == 'CN000003'>
                                <option value="CN000003" selected>利辛</option>
                            <#else>
                                <option value="CN000003">利辛</option>
                            </#if>

                            <#if dwzPage.entity.areaCode == 'CN000004'>
                                <option value="CN000004" selected>屯昌</option>
                            <#else>
                                <option value="CN000004">屯昌</option>
                            </#if>

                            <#if dwzPage.entity.areaCode == 'CN000005'>
                                <option value="CN000005" selected>辛集</option>
                            <#else>
                                <option value="CN000005">辛集</option>
                            </#if>

                            <#if dwzPage.entity.areaCode == 'CN000006'>
                                <option value="CN000006" selected>文昌</option>
                            <#else>
                                <option value="CN000006">文昌</option>
                            </#if>

                            <#if dwzPage.entity.areaCode == 'CN000007'>
                                <option value="CN000007" selected>徽州</option>
                            <#else>
                                <option value="CN000007">徽州</option>
                            </#if>

                        <#else>
                            <option value="CN000001">颍上</option>
                            <option value="CN000002">太和</option>
                            <option value="CN000003">利辛</option>
                            <option value="CN000004">屯昌</option>
                            <option value="CN000005">辛集</option>
                            <option value="CN000006">文昌</option>
                            <option value="CN000007">徽州</option>
                        </#if>
                        </select>
                    </td>
                    <td>
                        <label>状态：</label>
                        <select class="combox" name="receive" id="receiveSelect">
                            <option value="">全部</option>
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.receive?exists && dwzPage.entity.receive != ''>

                            <#if dwzPage.entity.receive == '1'>
                                <option value="1" selected>未领取</option>
                            <#else>
                                <option value="1">未领取</option>
                            </#if>

                            <#if dwzPage.entity.receive == '2'>
                                <option value="2" selected>已领取</option>
                            <#else>
                                <option value="2">已领取</option>
                            </#if>

                            <#if dwzPage.entity.receive == '3'>
                                <option value="3" selected>退回</option>
                            <#else>
                                <option value="3">退回</option>
                            </#if>

                            <#if dwzPage.entity.receive == '4'>
                                <option value="4" selected>发放失败</option>
                            <#else>
                                <option value="4">发放失败</option>
                            </#if>
                        <#else>
                            <option value="1">未领取</option>
                            <option value="2">已领取</option>
                            <option value="3">退回</option>
                            <option value="4">发放失败</option>
                        </#if>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
                    <li><div class="button"><div class="buttonContent"><button type="button" onclick="resetNavTabSearch(this.form)">重置</button></div></div></li>
                </ul>
            </div>
        </div>
    </form>
</div>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="icon" id="exportBtn"><span>导出Excel</span></a></li>
        <#--<li><a class="edit" id="updateByIdBtn"><span>修改</span></a></li>-->
        <#--<li><a class="edit" href="../api/toUpdateLostAndFoundPage?id={objId}" target="dialog" info="请选择一条信息"  width="800" height="500"><span>修改</span></a></li>-->
        </ul>
    </div>

    <div id="w_list_print">
        <table class="list" width="98%" targetType="navTab"  layoutH="150">
            <thead>
            <tr>
                <th>序号</th>
                <th width="200">活动名称</th>
                <th width="150">用户openID</th>
                <th width="150">手机号</th>
                <th width="100">公交卡号</th>
                <th width="80">所属区县</th>
                <th width="200">交易订单号</th>
                <th width="120">发放时间</th>
                <th width="80">红包金额（元）</th>
                <th width="80">领取状态</th>
                <th width="100">领取时间</th>
            </tr>
            </thead>
            <tbody>
            <#if dwzPage.dataList?exists && (dwzPage.dataList?size>0)>
                <#list dwzPage.dataList as redPacketDetail>
                <tr>
                    <td>${redPacketDetail_index + 1}</td>
                    <td>${redPacketDetail.activityName}</td>
                    <#if redPacketDetail.openid??>
                        <#if redPacketDetail.openid?length lt 10>
                            <td title="${redPacketDetail.openid}" >${redPacketDetail.openid}</td>
                        <#else>
                            <td title="${redPacketDetail.openid}">${redPacketDetail.openid[0..9]}...</td>
                        </#if>
                    <#else>
                        <td>
                        </td>
                    </#if>
                    <td>
                        <#if redPacketDetail.phone??>
                            ${redPacketDetail.phone}
                        </#if>
                    </td>
                    <td title="${redPacketDetail.busCardNo}">
                        <#if redPacketDetail.busCardNo?length lt 10>
                            ${redPacketDetail.busCardNo}
                        <#else>
                            ${redPacketDetail.busCardNo[0..9]}...
                        </#if>
                    </td>
                    <td>${redPacketDetail.areaName}</td>
                    <td>
                        <#if redPacketDetail.mchBillno??>
                            ${redPacketDetail.mchBillno}
                        </#if>
                    </td>
                    <td>${redPacketDetail.sendDate?datetime}</td>
                    <td>${redPacketDetail.amount?string('0.00')}</td>
                    <#if redPacketDetail.receive??>
                        <#if redPacketDetail.receive == '1'>
                            <td>未领取</td>
                        </#if>
                        <#if redPacketDetail.receive == '2'>
                            <td>已领取</td>
                        </#if>
                        <#if redPacketDetail.receive == '3'>
                            <td>退回</td>
                        </#if>
                        <#if redPacketDetail.receive == '4'>
                            <td>发放失败</td>
                        </#if>
                    <#else>
                        <td></td>
                    </#if>
                    <td>
                        <#if redPacketDetail.receiveDate??>
                            ${redPacketDetail.receiveDate?datetime}
                        </#if>
                    </td>
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
        <div class="pagination" targetType="navTab" totalCount="${dwzPage.totalCount?c}" numPerPage="${dwzPage.numPerPage}" pageNumShown="0" currentPage="${dwzPage.currentPage}"></div>
    </div>
</div>
<script>
    
    document.getElementById("exportBtn").onclick = function () {
        let param = {};
        let openidInput = document.getElementById("openidInput");
        if (openidInput != null) {
            param.openid = openidInput.value;
        }
        let busCardNoInput = document.getElementById("busCardNoInput");
        if (busCardNoInput != null) {
            param.busCardNo = busCardNoInput.value;
        }
        let phoneInput = document.getElementById("phoneInput");
        if (phoneInput != null) {
            param.phone = phoneInput.value;
        }
        let sendStartDateStrInput = document.getElementById("sendStartDateStrInput");
        if (sendStartDateStrInput != null) {
            param.sendStartDateStr = sendStartDateStrInput.value;
        }
        let sendEndDateStrInput = document.getElementById("sendEndDateStrInput");
        if (sendEndDateStrInput != null) {
            param.sendEndDateStr = sendEndDateStrInput.value;
        }
        let areaCodeSelect = document.getElementById("areaCodeSelect");
        if (areaCodeSelect != null) {
            param.areaCode = areaCodeSelect.value;
        }
        let receiveSelect = document.getElementById("receiveSelect");
        if (receiveSelect != null) {
            param.receive = receiveSelect.value;
        }
        let activityNameInput = document.getElementById("activityNameInput");
        if (activityNameInput != null) {
            param.activityName = activityNameInput.value;
        }
        let mchBillnoInput = document.getElementById("mchBillnoInput");
        if (mchBillnoInput != null) {
            param.mchBillno = mchBillnoInput.value;
        }
        let exportUrl = "redPacketDetail/export";
        let keysArr = Object.keys(param);
        let valuesArr = Object.values(param);
        for (let i = 0; i < keysArr.length; i++) {
            if (i == 0) {
                exportUrl = exportUrl + "?" + keysArr[i] + "=" + valuesArr[i];
            } else {
                exportUrl = exportUrl + "&" + keysArr[i] + "=" + valuesArr[i];
            }
        }
        console.error(exportUrl);
        document.location.href = exportUrl
    }
    
</script>
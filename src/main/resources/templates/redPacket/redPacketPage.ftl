<form id="pagerForm" method="post" action="redPacket/page">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="numPerPage" value="${dwzPage.numPerPage}" />
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="redPacket/page" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tbody>
                <tr>
                    <td>
                        活动名称：
                    <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.activityName?exists && dwzPage.entity.activityName != ''>
                        <input type="text" name="activityName" value="${dwzPage.entity.activityName}"/>
                    <#else>
                        <input type="text" name="activityName" value=""/>
                    </#if>
                    </td>
                    <td>
                        <label>区县：</label>
                        <select class="combox" name="areaCode">
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
                    <td class="dateRange">
                        创建时间:
                    <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.createStartDate?exists && dwzPage.entity.createStartDate != ''>
                        <input name="createStartDate" class="date readonly textInput" readonly="readonly"
                               type="text" value="${dwzPage.entity.createStartDate}">
                    <#else>
                        <input name="createStartDate" class="date readonly textInput" readonly="readonly"
                               type="text" value="">
                    </#if>

                        <span class="limit">-</span>

                    <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.createEndDate?exists && dwzPage.entity.createEndDate != ''>
                        <input name="createEndDate" class="date readonly textInput" readonly="readonly"
                               type="text" value="${dwzPage.entity.createEndDate}">
                    <#else>
                        <input name="createEndDate" class="date readonly textInput" readonly="readonly"
                               type="text" value="">
                    </#if>

                    </td>
                    <td>
                        <label>状态：</label>
                        <select class="combox" name="status">
                            <option value="">全部</option>
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.status?exists && dwzPage.entity.status != ''>

                            <#if dwzPage.entity.status == '1'>
                                <option value="1" selected>待确认</option>
                            <#else>
                                <option value="1">待确认</option>
                            </#if>
                            <#if dwzPage.entity.status == '2'>
                                <option value="2" selected>待发放</option>
                            <#else>
                                <option value="2">待发放</option>
                            </#if>
                            <#if dwzPage.entity.status == '3'>
                                <option value="3" selected>已发放</option>
                            <#else>
                                <option value="3">已发放</option>
                            </#if>
                            <#if dwzPage.entity.status == '4'>
                                <option value="4" selected>已取消</option>
                            <#else>
                                <option value="4">已取消</option>
                            </#if>
                        <#else>
                            <option value="1">待确认</option>
                            <option value="2">待发放</option>
                            <option value="3">已发放</option>
                            <option value="4">已取消</option>
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
            <li><a class="add" href="redPacket/toAddPage" target="dialog" width="800" height="500"><span>创建活动</span></a></li>
        </ul>
    </div>
    <div id="w_list_print">
        <table class="list" width="98%" targetType="navTab"  layoutH="130">
            <thead>
            <tr>
                <th>序号</th>
                <th>活动名称</th>
                <th>所属区县</th>
                <th>创建时间</th>
                <th>计划发放数量</th>
                <th>实际发放数量</th>
                <th>创建人</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#if dwzPage.dataList?exists && (dwzPage.dataList?size>0)>
                <#list dwzPage.dataList as redPacketList>
                <tr>
                    <td>${redPacketList_index + 1}</td>
                    <td>${redPacketList.activityName}</td>
                    <td>${redPacketList.areaName}</td>
                    <td>${redPacketList.gmtCreate?datetime}</td>
                    <td>${redPacketList.redPacketNum}</td>
                    <td>${redPacketList.actualSendRedpacketNum}</td>
                    <td>${redPacketList.createUserName}</td>
                    <td>
                        <#if redPacketList.status == '1'>待确认</#if>
                        <#if redPacketList.status == '2'>待发放</#if>
                        <#if redPacketList.status == '3'>已发放</#if>
                        <#if redPacketList.status == '4'>已取消</#if>
                    </td>
                    <td>
                        <#if redPacketList.status == '1'>
                            <a href="redPacket/getInfo?id=${redPacketList.id}" target="dialog" width="1000" height="300">详情</a>&nbsp&nbsp
                            <#if userName == "root" || userName == "superadmin">
                                <a class="confirmBtn" rel="${redPacketList.id}">确认</a>&nbsp&nbsp
                            </#if>
                            <a class="cancelBtn" rel="${redPacketList.id}">取消</a>
                        </#if>
                        <#if redPacketList.status == '2'>
                            <a href="redPacket/getInfo?id=${redPacketList.id}" target="dialog" width="1000" height="300">详情</a>&nbsp&nbsp
                            <#if userName == "root">
                                <a class="sendBtn" rel="${redPacketList.id}">发放</a>&nbsp&nbsp
                            </#if>
                            <a class="cancelBtn" rel="${redPacketList.id}">取消发放</a>
                        </#if>
                        <#if redPacketList.status == '3'>
                            <a href="redPacket/getInfo?id=${redPacketList.id}" target="dialog" width="1000" height="300">详情</a>
                        </#if>
                        <#if redPacketList.status == '4'>
                            <a href="redPacket/getInfo?id=${redPacketList.id}" target="dialog" width="1000" height="300">详情</a>
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
        <div class="pagination" targetType="navTab" totalCount="${dwzPage.totalCount}" numPerPage="${dwzPage.numPerPage}" pageNumShown="0" currentPage="${dwzPage.currentPage}"></div>
    </div>
</div>

<script>

    let updateStatus = "redPacket/updateStatus"
    let confirmBtnList = document.getElementsByClassName("confirmBtn");
    if (confirmBtnList != null) {
        for (let i = 0; i < confirmBtnList.length; i++) {
            confirmBtnList[i].onclick = function () {
                let id = this.rel;
                $.post(
                    updateStatus,
                    {
                        id: id,
                        status: "2"
                    },
                    function (data) {
                        data = JSON.parse(data);
                        if (data.statusCode == 200) {
                            navTab.reload("redPacket/page", {}, "");
                        }
                    }
                )
            }
        }
    }
    let sendUrl = "redPacket/sendRedPacket";
    let sendBtnList = document.getElementsByClassName("sendBtn");
    if (sendBtnList != null) {
        for (let i = 0; i < sendBtnList.length; i++) {
            sendBtnList[i].onclick = function () {
                let id = this.rel;
                alertMsg.confirm("确认发放红包活动？", {
                    okCall: function(){
                        $.post(
                            sendUrl,
                            {
                                id: id
                            },
                            function (data) {
                                data = JSON.parse(data);
                                if (data.statusCode == 200) {
                                    alertMsg.confirmV001("红包活动发放中", {
                                        okCall: function(){
                                            navTab.reload("redPacket/page", {}, "");
                                        }
                                    });
                                } else if (data.statusCode == 2000) {
                                    alertMsg.info("红包活动发放中");
                                }
                            }
                        )
                    }
                });
            }
        }
    }
    let cancelBtnList = document.getElementsByClassName("cancelBtn");
    if (cancelBtnList != null) {
        for (let i = 0; i < cancelBtnList.length; i++) {
            cancelBtnList[i].onclick = function () {
                let id = this.rel;
                alertMsg.confirm("取消红包活动，是否继续？", {
                    okCall: function() {
                        $.post(
                            updateStatus,
                            {
                                id: id,
                                status: "4"
                            },
                            function (data) {
                                data = JSON.parse(data);
                                if (data.statusCode == 200) {
                                    navTab.reload("redPacket/page", {}, "");
                                }
                            }
                        )
                    }
                });
            }
        }
    }
//    let infoBtnList = document.getElementsByClassName("infoBtn");
//    if (infoBtnList != null) {
//        for (let i = 0; i < infoBtnList.length; i++) {
//            infoBtnList[i].onclick = function () {
//                let id = this.rel;
//                $.post(
//                        updateStatus,
//                        {
//                            id: id,
//                            status: "4"
//                        },
//                        function (data) {
//                            data = JSON.parse(data);
//                            if (data.statusCode == 200) {
//                                navTab.reload("redPacket/page", {}, "");
//                            }
//                        }
//                )
//            }
//        }
//    }
</script>
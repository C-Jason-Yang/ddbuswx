<form id="pagerForm" method="post" action="../api/findInfoFeedBackList">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="numPerPage" value="${dwzPage.numPerPage}" />
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="demo_page1.html" method="post">
    <#--<div class="searchBar">-->
            <#--<ul class="searchContent">-->
                <#--<li>-->
                    <#--<label>我的客户：</label>-->
                    <#--<input type="text" name="keywords"/>-->
                <#--</li>-->
                <#--<li>-->
                    <#--<select class="combox" name="province">-->
                        <#--<option value="">所有省市</option>-->
                        <#--<option value="北京">北京</option>-->
                        <#--<option value="上海">上海</option>-->
                        <#--<option value="天津">天津</option>-->
                        <#--<option value="重庆">重庆</option>-->
                        <#--<option value="广东">广东</option>-->
                    <#--</select>-->
                <#--</li>-->
            <#--</ul>-->
            <#--<div class="subBar">-->
                <#--<ul>-->
                    <#--<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>-->
                    <#--<li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li>-->
                <#--</ul>-->
            <#--</div>-->
        <#--</div>-->
    </form>
</div>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="delete" id="batchDelteByIdsBtn"><span>删除</span></a></li>
            <li><a class="edit" id="updateByIdBtn"><span>查看</span></a></li>
            <#--<li><a class="edit" href="../api/findInfoFeedBackById?id={objId}" target="dialog" warn="请先选择一条信息" height="500" width="600"><span>查看</span></a></li>-->
        </ul>
    </div>

    <div id="w_list_print">
        <table class="list" width="98%" targetType="navTab"  layoutH="116">
            <thead>
            <tr>
                <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="200">提交时间</th>
                <th>提交内容</th>
                <th>联系方式</th>
            </tr>
            </thead>
            <tbody>
            <#if dwzPage.dataList?exists && (dwzPage.dataList?size>0)>
                <#list dwzPage.dataList as infoFeedBack>
                <tr target="objId" rel="${infoFeedBack.id}" >
                    <td><input name="ids" value="${infoFeedBack.id}" type="checkbox" class="id_check"></td>
                    <td>${infoFeedBack.submitTime}</td>
                    <td>${infoFeedBack.message}</td>
                    <td>${infoFeedBack.contact}</td>
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
//    document.getElementById("deleteInfoFeedBackBtn").onclick = function () {
//        let objId = document.getElementById("deleteInfoFeedBackBtn").getAttribute("rel");
//        if (objId == null || objId == "") {
//            alertMsg.info("请选择删除对象！");
//            return false;
//        }
//        alertMsg.confirm("确认删除？", {
//            okCall: function() {
//                $.post(
//                        "../api/deleteInfoFeedBackById",
//                        {
//                            id: objId
//                        },
//                        function (data) {
//                            data = JSON.parse(data);
//                            if (data.statusCode == 200) {
//                                navTab.reload("../api/findInfoFeedBackList", {}, "");
//                            }
//                        }
//                )
//            }
//        });
//    }
//    function chooseTr(obj) {
//        let objId = obj.getAttribute("rel");
//        document.getElementById("deleteInfoFeedBackBtn").setAttribute("rel", objId);
//    }
document.getElementById("batchDelteByIdsBtn").onclick = function () {
    let checked_dom_list = document.getElementsByClassName("id_check");
    let listId = [];
    for (let i = 0; i < checked_dom_list.length; i++) {
        if (checked_dom_list[i].checked) {
            listId[i] = checked_dom_list[i].value;
        }
    }
    if (listId != null && listId.length > 0) {
        listId = JSON.stringify(listId);
        alertMsg.confirm("确认删除？", {
        okCall: function() {
                $.post(
                        "../api/batchDeleteInfoFeedBackById",
                        {
                            listIdJson: listId
                        },
                        function (data) {
                            data = JSON.parse(data);
                            if (data.statusCode == 200) {
                                navTab.reload("../api/findInfoFeedBackList", {}, "");
                            }
                        }
                )
            }
        });
    } else {
        alertMsg.info("请选择删除对象！");
        return false;
    }
}


document.getElementById("updateByIdBtn").onclick = function () {
    let checked_dom_list = document.getElementsByClassName("id_check");
    let listId = [];
    let tempIdList = [];
    for (let i = 0; i < checked_dom_list.length; i++) {
        if (checked_dom_list[i].checked) {
            listId[i] = checked_dom_list[i].value;
        }
    }
    for (let i = 0; i < listId.length; i++) {
        if (listId[i] != null && listId[i] != "") {
            tempIdList[tempIdList.length] = listId[i]
        }
    }
    if (tempIdList != null && tempIdList.length == 0) {
        alertMsg.info("请选择查看对象！");
        return false;
    } else if (tempIdList != null && tempIdList.length == 1) {
        let id = tempIdList[0];
        $.pdialog.open("../api/findInfoFeedBackById?id=" + id, "1", "查看信息反馈", {width:600, height:500});
    } else {
        alertMsg.info("同时只能查看一条数据！");
        return false;
    }
}

</script>
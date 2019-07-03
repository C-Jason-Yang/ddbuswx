<form id="pagerForm" method="post" action="../ddbus/api/findLostAndFoundList">
<input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="numPerPage" value="${dwzPage.numPerPage}" />
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="../ddbus/api/findLostAndFoundList" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>标题：</label>
                    <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.title?exists && dwzPage.entity.title != ''>
                        <input type="text" name="title" value="${dwzPage.entity.title}"/>
                    <#else>
                        <input type="text" name="title" value=""/>
                    </#if>
                </li>
                <#--<li>-->
                    <#--<label>区域：</label>-->
                    <#--<select class="combox" name="areaMark">-->
                        <#--<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>-->
                        <#--<#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.areaMark?exists && dwzPage.entity.areaMark != ''>-->
                            <#--<#if dwzPage.entity.areaMark == 'TH'>-->
                                <#--<option value="TH" selected>太和</option>-->
                            <#--<#else>-->
                                <#--<option value="TH">太和</option>-->
                            <#--</#if>-->

                            <#--<#if dwzPage.entity.areaMark == 'YS'>-->
                                <#--<option value="YS" selected>颍上</option>-->
                            <#--<#else>-->
                                <#--<option value="YS">颍上</option>-->
                            <#--</#if>-->

                            <#--<#if dwzPage.entity.areaMark == 'LX'>-->
                                <#--<option value="LX" selected>利辛</option>-->
                            <#--<#else>-->
                                <#--<option value="LX">利辛</option>-->
                            <#--</#if>-->
                        <#--<#else>-->
                            <#--<option value="YS">颍上</option>-->
                            <#--<option value="TH">太和</option>-->
                            <#--<option value="LX">利辛</option>-->
                        <#--</#if>-->
                    <#--</select>-->
                <#--</li>-->
            </ul>
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
                </ul>
            </div>
        </div>
    </form>
</div>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="../ddbus/api/toAddLostAndFoundPage" target="dialog" width="800" height="500"><span>添加</span></a></li>
            <li><a title="确实要删除这些记录吗?" class="delete" id="batchDelteByIdsBtn"><span>删除</span></a></li>
            <li><a class="edit" id="updateByIdBtn"><span>修改</span></a></li>
            <#--<li><a class="edit" href="../api/toUpdateLostAndFoundPage?id={objId}" target="dialog" info="请选择一条信息"  width="800" height="500"><span>修改</span></a></li>-->
        </ul>
    </div>

    <div id="w_list_print">
        <table class="list" width="98%" targetType="navTab"  layoutH="116">
            <thead>
            <tr>
                <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="80">标题</th>
                <th>丢失时间</th>
                <th>发布时间</th>
                <th>下架时间</th>
            </tr>
            </thead>
            <tbody>
            <#if dwzPage.dataList?exists && (dwzPage.dataList?size>0)>
                <#list dwzPage.dataList as lostAndFound>
                <tr target="objId" rel="${lostAndFound.id}" >
                    <td><input name="ids" value="${lostAndFound.id}" type="checkbox" class="id_check"></td>
                    <td>${lostAndFound.title}</td>
                    <td>${lostAndFound.lostTime}</td>
                    <td>${lostAndFound.releaseTime}</td>
                    <td>${lostAndFound.downShelvesTime}</td>
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
//    document.getElementById("deleteLostAndFoundBtn").onclick = function () {
//        let objId = document.getElementById("deleteLostAndFoundBtn").getAttribute("rel");
//        if (objId == null || objId == "") {
//            alertMsg.info("请选择删除对象！");
//            return false;
//        }
//        alertMsg.confirm("确认删除？", {
//            okCall: function() {
//                $.post(
//                        "../api/deleteLostAndFoundById",
//                        {
//                            id: objId
//                        },
//                        function (data) {
//                            data = JSON.parse(data);
//                            if (data.statusCode == 200) {
//                                navTab.reload("../api/findLostAndFoundList", {}, "");
//                            }
//                        }
//                )
//            }
//        });
//    }
//    function chooseTr(obj) {
//        let objId = obj.getAttribute("rel");
//        document.getElementById("deleteLostAndFoundBtn").setAttribute("rel", objId);
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
                            "../ddbus/api/batchDeleteLostAndFoundById",
                            {
                                listIdJson: listId
                            },
                            function (data) {
                                data = JSON.parse(data);
                                if (data.statusCode == 200) {
                                    navTab.reload("../ddbus/api/findLostAndFoundList", {}, "");
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
        alertMsg.info("请选择修改对象！");
        return false;
    } else if (tempIdList != null && tempIdList.length == 1) {
        let id = tempIdList[0];
        $.pdialog.open("../ddbus/api/toUpdateLostAndFoundPage?id=" + id, "1", "修改失物招领", {width:800, height:500});
    } else {
        alertMsg.info("同时只能修改一条数据！");
        return false;
    }
}

</script>
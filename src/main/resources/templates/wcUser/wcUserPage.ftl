<form id="pagerForm" method="post" action="wcUser/page">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="numPerPage" value="${dwzPage.numPerPage}" />
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="wcUser/page" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tbody>
                <tr>
                    <td>
                        手机号：
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.phone?exists && dwzPage.entity.phone != ''>
                            <input type="text" name="phone" value="${dwzPage.entity.phone}"/>
                        <#else>
                            <input type="text" name="phone" value=""/>
                        </#if>
                    </td>
                    <td>
                        公交卡号：
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.busCard?exists && dwzPage.entity.busCard != ''>
                            <input type="text" name="busCard" value="${dwzPage.entity.busCard}"/>
                        <#else>
                            <input type="text" name="busCard" value=""/>
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
                        注册时间:
                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.registerStartDate?exists && dwzPage.entity.registerStartDate != ''>
                            <input name="registerStartDate" class="date readonly textInput" readonly="readonly"
                                   type="text" value="${dwzPage.entity.registerStartDate}">
                        <#else>
                            <input name="registerStartDate" class="date readonly textInput" readonly="readonly"
                                   type="text" value="">
                        </#if>

                        <span class="limit">-</span>

                        <#if dwzPage?exists && dwzPage.entity?exists && dwzPage.entity.registerEndDate?exists && dwzPage.entity.registerEndDate != ''>
                            <input name="registerEndDate" class="date readonly textInput" readonly="readonly"
                                   type="text" value="${dwzPage.entity.registerEndDate}">
                        <#else>
                            <input name="registerEndDate" class="date readonly textInput" readonly="readonly"
                                   type="text" value="">
                        </#if>

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
            <span>&nbsp;&nbsp;&nbsp;&nbsp;用户总数：${dwzPage.totalCount}</span>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;已绑定手机卡用户数：${dwzPage.entity.bindingPhoneUserNum}</span>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;已绑定公交卡用户数：${dwzPage.entity.bindingBusCardUserNum}</span>
            <#--<li><a class="add" href="../api/toAddLostAndFoundPage" target="dialog" width="800" height="500"><span>添加</span></a></li>-->
            <#--<li><a class="edit" id="updateByIdBtn"><span>修改</span></a></li>-->
        <#--<li><a class="edit" href="../api/toUpdateLostAndFoundPage?id={objId}" target="dialog" info="请选择一条信息"  width="800" height="500"><span>修改</span></a></li>-->
        </ul>
    </div>

    <div id="w_list_print">
        <table class="list" width="98%" targetType="navTab"  layoutH="116">
            <thead>
            <tr>
                <th>序号</th>
                <th>昵称</th>
                <th>手机号</th>
                <th>注册时间</th>
                <th>所属区县</th>
                <th>绑卡信息</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#if dwzPage.dataList?exists && (dwzPage.dataList?size>0)>
                <#list dwzPage.dataList as userList>
                    <tr>
                        <td>${userList_index + 1}</td>
                        <td>${userList.wcName}</td>
                        <#if userList.phone ?? && userList.phone != "">
                            <td>${userList.phone}</td>
                        <#else>
                            <td>未绑定</td>
                        </#if>
                        <td>${userList.gmtCreate?datetime}</td>

                        <#if userList.areaName ?? && userList.areaName != "">
                            <td>${userList.areaName}</td>
                        <#else>
                            <td>未绑定</td>
                        </#if>

                        <#if userList.busCard ?? && userList.busCard != "">
                            <td>${userList.busCard}</td>
                        <#else>
                            <td>未绑定</td>
                        </#if>

                        <td><a class="add" href="wcUser/getWcUserInfoById?id=${userList.id}" max="true" target="dialog" width="1000" height="700" title="用户信息">详情</a></td>
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
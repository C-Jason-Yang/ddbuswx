<div class="pageContent">
    <form id="updateLostAndFound" action="../ddbus/api/updateLostAndFoundById" class="pageForm" >
        <input type="hidden" name="id" value="${lostAndFound.id}">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>标题：</label>
                <input name="title" type="text" size="30" value="${lostAndFound.title}"  />
            </p>
            <p class="nowrap">
                <label>内容：</label>
                <textarea name="content" cols="50" rows="5">${lostAndFound.content}</textarea>
            </p>
            <#--<p>-->
                <#--<label>所属区域：</label>-->
                <#--<select class="combox" name="areaMark">-->
                    <#--<option value="" >请选择区域</option>-->
                    <#--<#if lostAndFound.areaMark=='YS'>-->
                        <#--<option value="YS" selected>颍上</option>-->
                    <#--<#else>-->
                        <#--<option value="YS">颍上</option>-->
                    <#--</#if>-->
                    <#--<#if lostAndFound.areaMark=='TH'>-->
                        <#--<option value="TH" selected>太和</option>-->
                    <#--<#else>-->
                        <#--<option value="TH">太和</option>-->
                    <#--</#if>-->
                    <#--<#if lostAndFound.areaMark=='LX'>-->
                        <#--<option value="LX">利辛</option>-->
                    <#--<#else>-->
                        <#--<option value="LX">利辛</option>-->
                    <#--</#if>-->
                <#--</select>-->
            <#--</p>-->
            <#--<p>-->
                <label>丢失时间：</label>
                <input type="text" name="lostTime" class="date" readonly="true" value="${lostAndFound.lostTime}"/>
            </p>
            <#--<p>-->
                <#--<label>发布日期：</label>-->
                <#--<input type="text" name="releaseTime" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" value="${lostAndFound.releaseTime}"/>-->
            <#--</p>-->
            <p>
                <label>下架日期：</label>
                <input type="text" name="downShelvesTime" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" value="${lostAndFound.downShelvesTime}"/>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="updateLostAndFoundBtn">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script>
    document.getElementById("updateLostAndFoundBtn").onclick = function () {
        let lostAndFound = $("#updateLostAndFound").serializeObject();
        if (lostAndFound.title == null || lostAndFound.title == '') {
            return false;
        }
        if (lostAndFound.content == null || lostAndFound.content == '') {
            return false;
        }
//        if (lostAndFound.areaMark == null || lostAndFound.areaMark == '') {
//            return false;
//        }
        if (lostAndFound.lostTime == null || lostAndFound.lostTime == '') {
            return false;
        }
//        if (lostAndFound.releaseTime == null || lostAndFound.releaseTime == '') {
//            return false;
//        }
        if (lostAndFound.downShelvesTime == null || lostAndFound.downShelvesTime == '') {
            return false;
        }
        $.post(
                "../ddbus/api/updateLostAndFoundById",
                lostAndFound,
                function (data) {
                    data = JSON.parse(data);
                    if (data.statusCode == 200) {
                        $.pdialog.closeCurrent();
                        navTab.reload("../ddbus/api/findLostAndFoundList", {}, "");
                    }
                }
        )
    }
</script>

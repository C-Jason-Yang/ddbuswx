<div class="pageContent">
    <form id="lostAndFoundForm" action="../ddbus/api/addLostAndFound" class="pageForm" >
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>标题：</label>
                <input name="title" id="titleInput" type="text" size="30"  />
            </p>
            <p class="nowrap">
                <label>内容：</label>
                <textarea name="content" cols="50" rows="5"></textarea>
            </p>
            <#--<p>-->
                <#--<label>所属区域：</label>-->
                <#--<select class="combox" name="areaMark">-->
                    <#--<option value="" selected >请选择区域</option>-->
                    <#--<option value="YS">颍上</option>-->
                    <#--<option value="TH">太和</option>-->
                    <#--<option value="LX">利辛</option>-->
                <#--</select>-->
            <#--</p>-->
            <p>
                <label>丢失时间：</label>
                <input type="text" name="lostTime" class="date" readonly="true"/>
            </p>
            <#--<p>-->
                <#--<label>发布日期：</label>-->
                <#--<input type="text" name="releaseTime" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>-->
            <#--</p>-->
            <p>
                <label>下架日期：</label>
                <input type="text" name="downShelvesTime" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addLostAndFoundBtn">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script>
    document.getElementById("addLostAndFoundBtn").onclick = function () {
        let lostAndFound = $("#lostAndFoundForm").serializeObject();
        if (lostAndFound.title == null || lostAndFound.title == '') {
            alertMsg.info("请添加标题");
            return false;
        }
        if (lostAndFound.content == null || lostAndFound.content == '') {
            alertMsg.info("请添加内容");
            return false;
        }
        if (lostAndFound.lostTime == null || lostAndFound.lostTime == '') {
            alertMsg.info("请添加丢失时间");
            return false;
        }
//        if (lostAndFound.releaseTime == null || lostAndFound.releaseTime == '') {
//            alertMsg.warn("请添加上架时间");
//            return false;
//        }
        if (lostAndFound.downShelvesTime == null || lostAndFound.downShelvesTime == '') {
            alertMsg.info("请添加下架时间");
            return false;
        }
        $.post(
            "../ddbus/api/addLostAndFound",
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

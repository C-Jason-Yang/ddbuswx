
<div class="pageContent">
    <form method="post" action="../api/addHotSite" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <#--<p>-->
                <#--<label>区域标识：</label>-->
                <#--<input name="areaMark" id="areaMarkInput" type="text" size="30"  />-->
            <#--</p>-->
            <p>
                <label>站点名称：</label>
                <input name="siteName" id="siteNameInput" type="text" size="30"  />
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addHotSiteBtn">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script>
    document.getElementById("addHotSiteBtn").onclick = function () {
//        let areaMark = document.getElementById("areaMarkInput").value;
        let siteName = document.getElementById("siteNameInput").value;
//        if (areaMark != null && areaMark != "" && siteName != null && siteName != "") {
        if (siteName != null && siteName != "") {
            $.post(
                    "../api/addHotSite",
                    {
//                        areaMark: areaMark,
                        siteName: siteName
                    },
                    function (data) {
                        data = JSON.parse(data);
                        if (data.statusCode == 200) {
                            $.pdialog.closeCurrent();
                            navTab.reload("../api/findAllHotSite", {}, "");
                        }
                    }
            )
        }
    }
</script>

<style>
    .userInfo li{
        font-size: 14px;
        line-height: 2;
        font-family: -webkit-body;
    }
</style>
<div class="pageContent">
        <div class="pageFormContent" layoutH="350">

            <div class="userInfo" style="padding-top: 20px; padding-left: 20px; display: inline-flex;">
                <img src="${wcUser.wcProfilePhoto}" style="margin-right: 10px;">
                <ul>
                    <li>用户昵称：${wcUser.wcName}</li>
                    <li>openID：${wcUser.wcOpenid}</li>
                    <li>注册时间：${wcUser.gmtCreate?datetime}</li>

                <#if wcUser.phone ?? && wcUser.phone != "">
                        <li>手机号：${wcUser.phone}</li>
                    <#else>
                        <li>手机号：未绑定</li>
                </#if>

                <#if wcUser.areaName ?? && wcUser.areaName != "">
                    <li id="busCardNoLi">${wcUser.areaName} &nbsp&nbsp&nbsp&nbsp NO：${wcUser.busCard} <button type="button" id="removeBusCardBindingBtn">解绑</button></li>
                    <input type="hidden" id="areaNameHidden" value="${wcUser.areaName}" />
                    <input type="hidden" id="busCardHidden" value="${wcUser.busCard}" />
                    <#else>
                        <li>公交卡号：未绑定</li>
                    </#if>
                    <input id="wcOpenidHidden" type="hidden" value="${wcUser.wcOpenid}" />
                </ul>
            </div>
        </div>
    <div>

        <div layoutH="320" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
            <ul class="tree treeFolder">
                <li><a href="redPacketDetail/pageByOpenid?openid=${wcUser.wcOpenid}"  target="ajax" rel="buscardPage">红包领取记录</a></li>
                <li><a id="busCardBindingLogLink" href="busCardBindingLog/page?openid=${wcUser.wcOpenid}" target="ajax" rel="buscardPage">绑卡记录</a></li>
            </ul>
        </div>
        <div id="buscardPage" class="unitBox" style="margin-left:246px;">
        </div>
    </div>
    <div class="formBar">
        <ul>
            <li>
                <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
            </li>
        </ul>
    </div>
</div>



<script>
    let btn = document.getElementById("removeBusCardBindingBtn");
    if (btn != null) {
        btn.onclick = function () {
            let areaName = document.getElementById("areaNameHidden").value;
            let busCardNo = document.getElementById("busCardHidden").value;
            alertMsg.confirm("您正在解除"+ areaName +"-"+ busCardNo +"公交卡绑定关系，是否继续？", {
                okCall: function() {
                    let wcOpenid = document.getElementById("wcOpenidHidden").value;
                    let postUrl = "wcUser/removeBusCardBinding?" + "wcOpenid=" + wcOpenid;
                    $.post(
                        postUrl,
                        function (data) {
                            data = JSON.parse(data);
                            if (data.statusCode == 200) {
                                document.getElementById("busCardNoLi").innerHTML = "<li>公交卡号：未绑定</li>";
                                navTab.reload("../ddbus/wcUser/page", {}, "");
                                let classValue = document.getElementById("busCardBindingLogLink").parentNode.getAttribute("class");
                                if (classValue == "selected") {
                                    var href= "busCardBindingLog/page?openid=" + wcOpenid;
                                    var $rel = $("#buscardPage");
                                    $rel.loadUrl(href, {}, function(){
                                        $rel.find("[layoutH]").layoutH();
                                    });
                                }
                            }
                        }
                    )
                }
            });
        }
    }
</script>

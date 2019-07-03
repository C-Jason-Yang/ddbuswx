<div class="pageContent">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>红包活动名称：</label>
                <input name="activityName" id="titleInput" type="text" size="20"  />
            </p>
            <p>
                <label>所属区县：</label>
                <select class="combox" name="areaCode" id="areaCodeSelect">
                    <option value="" selected >请选择区县</option>
                    <option value="CN000001">颍上</option>
                    <option value="CN000002">太和</option>
                    <option value="CN000003">利辛</option>
                    <option value="CN000004">屯昌</option>
                    <option value="CN000005">辛集</option>
                    <option value="CN000006">文昌</option>
                    <option value="CN000007">徽州</option>
                </select>
            </p>

            <p>
                <button id="uploadFileBtn" type="button">上传文件</button>
                <span id="uploadCallBakcTip" style="padding-top: 10px; display: block;">&nbsp&nbsp&nbsp&nbsp</span>
                <span style="padding-top: 10px; display: block;">单次人员上传数目不得超过1800个</span>
                <input type="hidden" name="tempRedPacketActivityId" id="redPacketActiviId">
                <input type="hidden" id="failNumHidden" />
            </p>
            <p>
                <button type="button" id="downloadxlsxBtn">下载模板</button>
                <a id="filePathLink" style="display: none;" ></a>
                <a id="downloadxlsxLink" style="display: none;" href="http://www.itimebus.com.cn/ddbusstatic/红包模板.xls"></a>
            </p>
            <form style="display: none;">
                <input id="redPacketFileInput"  type="file" style="display: none;" />
                <button type="reset" id="resetUploadFileBtn"></button>
            </form>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addRedPacketBtn">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
</div>
<script>

let file;
let data;
let isCanAdd = false;
let errorMsg;

document.getElementById("redPacketFileInput").addEventListener("change", function(obj) {
    file = obj.target.files[0];
    let fileName = file.name;
    let fileReader = new FileReader();
     fileReader.readAsDataURL(file);
    fileReader.onload = function(e){
        var fileString = e.target.result;
        let arr = fileString.split(";base64,");
        data = {
            "fileBase64Code": arr[1],
         	"fileName": fileName
        };
        let tempArr = fileName.split(".");
        let fileSuffix = tempArr[tempArr.length - 1];
        if (fileSuffix != "xls") {
            alertMsg.info("文件格式不正确");
            return false;
        }
        $.post("redPacketDetail/importData", data).success(function (result) {
            result = JSON.parse(result);
            if (result.statusCode == "200") {
                isCanAdd = true;
                let failNum = result.sheetLineNum - result.realLineNum;
                if (failNum != 0) {
                    isCanAdd = false;
                    errorMsg = "有信息上传失败，请修改后重新上传"
                }
                if (result.sheetLineNum > 1800) {
                    isCanAdd = false;
                    errorMsg = "上传红包信息不能大于1800条，请修改后重新上传"
                }
                if (result.sheetLineNum == 0) {
                    isCanAdd = false;
                    errorMsg = "上传红包信息不能为0条，请修改后重新上传"
                }
                document.getElementById("uploadCallBakcTip").innerHTML = "成功上传"+ data.fileName +"文件，本次成功上传" +
                        "<span style='color: red'>"+ result.realLineNum +"</span>条，" +
                        "失败<span style='color: red'>"+ failNum +"</span>条，红包总额"+ result.totalAmount +"元";
                document.getElementById("redPacketActiviId").value = result.redPacketActiviId;
                document.getElementById("failNumHidden").value = failNum;
                document.getElementById("uploadFileBtn").innerHTML = "重新上传";
                document.getElementById("filePathLink").setAttribute("href", result.filePath);
                document.getElementById("resetUploadFileBtn").click();
            }
        });
    }
});

document.getElementById("uploadFileBtn").onclick = function () {
    document.getElementById("redPacketFileInput").click();
}
document.getElementById("addRedPacketBtn").onclick = function () {
    if (typeof isCanAdd == "undefined") {
        alertMsg.info("请上传红包文件");
        return false;
    }
    let areaCode = document.getElementById("areaCodeSelect").value;
    let redPacketActiviName = document.getElementById("titleInput").value;
    let redPacketActiviId = document.getElementById("redPacketActiviId").value;
    let filePath = document.getElementById("filePathLink").getAttribute("href");
    let redPacket = {
        activityName: redPacketActiviName,
        areaCode: areaCode,
        tempRedPacketActivityId: redPacketActiviId,
        redPacketFilePath: filePath
    }
    if (redPacket.activityName == null || redPacket.activityName == '') {
        alertMsg.info("请添加活动名称");
        return false;
    } else {
        if (redPacket.activityName.length > 10) {
            alertMsg.info("活动名称不能超过十个字符");
            return false;
        }
    }

    if (redPacket.areaCode == null || redPacket.areaCode == '') {
        alertMsg.info("请选择活动地区");
        return false;
    }
    if (redPacket.tempRedPacketActivityId == null || redPacket.tempRedPacketActivityId == '') {
        alertMsg.info("请上传红包名单");
        return false;
    }
    let failNum = document.getElementById("failNumHidden").value;
    if (failNum != 0) {
        alertMsg.info("上传红包信息有错误不能生成红包活动!!!");
        return false;
    }
    if (!isCanAdd) {
        alertMsg.info(errorMsg);
        return false;
    }
    $.post(
            "redPacket/add",
            redPacket,
            function (data) {
                data = JSON.parse(data);
                if (data.statusCode == 200) {
                    $.pdialog.closeCurrent();
                    navTab.reload("redPacket/page", {}, "");
                }
            }
    )
}

document.getElementById("downloadxlsxBtn").onclick = function () {
    document.getElementById("downloadxlsxLink").click();
}
</script>

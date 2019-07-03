<div class="pageContent">
    <form id="userForm" action="../api/addLostAndFound" class="pageForm" >
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>用户名：</label>
                <input name="userName" type="text" size="30"  />
            </p>
            <p>
                <label>密码：</label>
                <input name="password" type="password" size="30"  />
            </p>
            <p>
                <label>所属区县：</label>
                <select class="combox" name="areaMark">
                    <option value="" selected >请选择区县</option>
                    <option value="YS">颍上</option>
                    <option value="TH">太和</option>
                    <option value="LX">利辛</option>
                </select>
            </p>

        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addUserBtn">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script>
    document.getElementById("addUserBtn").onclick = function () {
        let user = $("#userForm").serializeObject();
        if (user.userName == null || user.userName == '') {
            return false;
        }
        if (user.password == null || user.password == '') {
            return false;
        } else {
            user.password = md5(user.password);
        }
        if (user.areaMark == null || user.areaMark == '') {
            return false;
        }
        $.post(
            "../api/addUser",
            user,
            function (data) {
                data = JSON.parse(data);
                if (data.statusCode == 200) {
                    $.pdialog.closeCurrent();
                    navTab.reload("../api/findUserListPage", {}, "");
                }
            }
        )
    }
</script>

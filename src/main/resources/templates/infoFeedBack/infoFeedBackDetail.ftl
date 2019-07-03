<div class="pageContent">
    <form >
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>联系方式：</label>
                <input  type="text" size="30" value="${result.contact}" readonly />
            </p>
            <p class="nowrap">
                <label>内容：</label>
                <textarea cols="70" rows="20"  readonly > ${result.message}</textarea>
            </p>
            <p>
                <label>提交时间：</label>
                <input type="text" readonly value="${result.submitTime}"/>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>

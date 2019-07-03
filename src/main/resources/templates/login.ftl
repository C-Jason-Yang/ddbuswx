<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>大道公交微信公众号管理平台</title>
    <style>
        body{margin:0 auto; padding:0 auto; background-image:url(http://www.itimebus.com.cn/ddbusstatic/images/bg.png);}
        input{background:none; outline:none; border:0px; }
        *{font-family:"微软雅黑"; color:white;font-size:26px;}


        .box-bg{background-image:url(http://www.itimebus.com.cn/ddbusstatic/images/box.png); width:682px; height:430px;}
        .box{float:left; margin-top:20px; vertical-align:middle;}
        .logo{height:80px; vertical-align:middle;}
        .icon{float:left; margin-left:75px; margin-top:12px;}
        .title{float:left; margin-left:180px; margin-top:-65px; font-size:34px; color:#071954;}

        .fillout{background-image:url(http://www.itimebus.com.cn/ddbusstatic/images/fillout.png); width:703px; height:149px; margin-top:25px; margin-left:-10px;}
        .fillout input{width:300px; height:40px;}
        .ID{float:left; margin-left:151px; margin-top:32px;}
        .ID_box{margin-left:-130px;}
        .write_first{background-image:url(http://www.itimebus.com.cn/ddbusstatic/images/write.png); margin-top:28px;}
        .password{float:left; margin-left:151px; margin-top:12px;}
        .password_box{margin-left:-142px;}
        .write_second{background-image:url(http://www.itimebus.com.cn/ddbusstatic/images/write.png); margin-top:10px; margin-left:12px;}

        .login div{background-image:url(http://www.itimebus.com.cn/ddbusstatic/images/login.png); width:223px; height:40px; padding-bottom:16px; font-size:28px;}

        #logintips{display: block; font-size: 15px; color: red;}
        .copyright span{font-size:16px; color:#9dcfff; line-height:50px;}
    </style>
    <script src="http://www.itimebus.com.cn/ddbusstatic/js/jquery-2.1.4.min.js"></script>
    <script src="http://www.itimebus.com.cn/ddbusstatic/js/md5.min.js"></script>
</head>


<body>
<div id="middleDivId"   >
    <table border="0" cellpadding="0" cellspacing="0"  width="100%"  height="100%">
        <tr>
            <td valign="middle" align="center"	>
                <form  id="loginForm"  method="post" action="index" >
                    <div class="box-bg" >
                        <div class="box" >
                            <div class="logo">
                                <span class="icon" ><img src="http://www.itimebus.com.cn/ddbusstatic/images/icon.jpg" width="80" height="80"/></span>
                                <span class="title">大道掌上公交互联网应用平台</span>
                                <span style="position: relative; top: 95%; left: 15%;"></span>
                            </div>
                            <div class="fillout" >
                                <div >
                                    <span class="ID" > 账&nbsp;&nbsp;号 </span>
                                    <span class="ID_box"> <input class="write_first" type="text" id="userNameInput"  name="userName" /></span>
                                </div>
                                <div>
                                    <span class="password">密&nbsp;&nbsp;码</span>
                                    <span class="password_box"> <input class="write_second" type="password"   id="passwordInput"   name="password" /></span>
                                </div>
                            </div>

                            <div class="login" >
                                <span id="logintips">&nbsp;</span>
                                <div id="loginBtn">登&nbsp;录</div>
                            </div>

                            <div class="copyright" >

                            </div>

                        </div>

                    </div>
                </form>
            </td>
        </tr>
    </table>

</div>

<script>

    var clientHeight =  $(window).height() ;
    $("#middleDivId").css("height",clientHeight)

    //登录按钮点击事件
    document.getElementById("loginBtn").onclick = function () {
        let userNameValue = document.getElementById("userNameInput").value;
        let passwordValue = document.getElementById("passwordInput").value;
        if (userNameValue != null && userNameValue != "" && passwordValue != null && passwordValue != "") {
            document.getElementById("passwordInput").value = md5(passwordValue);
            document.getElementById("loginForm").submit();
        } else {
            alert("用户名或密码不能为空");
        }
    }

</script>
</body>
</html>
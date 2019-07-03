<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>大道公交微信公众号管理平台</title>

	<link href="../ddbus/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="../ddbus/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="../ddbus/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
	<link href="../ddbus/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
	<!--[if IE]>
	<link href="../ddbus/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
	<![endif]-->

	<!--[if lt IE 9]><script src="../ddbus/js/speedup.js" type="text/javascript"></script><script src="../ddbus/js/jquery-1.11.3.min.js" type="text/javascript"></script><![endif]-->
	<!--[if gte IE 9]><!--><script src="../ddbus/js/jquery-2.1.4.min.js" type="text/javascript"></script><!--<![endif]-->

	<script src="../ddbus/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="../ddbus/js/jquery.validate.js" type="text/javascript"></script>
	<script src="../ddbus/js/jquery.bgiframe.js" type="text/javascript"></script>
	<script src="../ddbus/xheditor/xheditor-1.2.2.min.js" type="text/javascript"></script>
	<script src="../ddbus/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
	<script src="../ddbus/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

	<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
	<script type="text/javascript" src="../ddbus/chart/raphael.js"></script>
	<script type="text/javascript" src="../ddbus/chart/g.raphael.js"></script>
	<script type="text/javascript" src="../ddbus/chart/g.bar.js"></script>
	<script type="text/javascript" src="../ddbus/chart/g.line.js"></script>
	<script type="text/javascript" src="../ddbus/chart/g.pie.js"></script>
	<script type="text/javascript" src="../ddbus/chart/g.dot.js"></script>

	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6PYkS1eDz5pMnyfO0jvBNE0F"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>

	<script src="../ddbus/js/dwz.core.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.util.date.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.validate.method.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.barDrag.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.drag.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.tree.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.accordion.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.ui.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.theme.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.switchEnv.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.alertMsg.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.contextmenu.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.navTab.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.tab.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.resize.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.dialog.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.dialogDrag.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.sortDrag.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.cssTable.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.stable.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.taskBar.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.ajax.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.pagination.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.database.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.datepicker.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.effects.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.panel.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.checkbox.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.history.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.combox.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.file.js" type="text/javascript"></script>
	<script src="../ddbus/js/dwz.print.js" type="text/javascript"></script>
    <script src="../ddbus/js/md5.min.js" type="text/javascript"></script>

	<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换时下面dwz.regional.zh.js还需要引入)
    <script src="bin/dwz.min.js" type="text/javascript"></script>
    -->
	<script src="../ddbus/js/dwz.regional.zh.js" type="text/javascript"></script>
	<script src="../ddbus/js/evcas.js" type="text/javascript"></script>

	<script type="text/javascript">
        $(function(){
            DWZ.init("../ddbus/dwz.frag.xml", {
                loginUrl:"../ddbus/login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.ftl",	// 跳到登录页面
                statusCode:{ok:200, error:300, timeout:301}, //【可选】
                pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
                keys: {statusCode:"statusCode", message:"message"}, //【可选】
                ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
                debug:false,	// 调试模式 【true|false】
                callback:function(){
                    initEnv();
                    $("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
                }
            });
        });
	</script>

</head>

<body>
<div id="layout">
	<div id="header">
		<div class="headerNav">
			<#--<a class="logo" href="http://j-ui.com">标志</a>-->
			<ul class="nav">
				<#--<li id="switchEnvBox"><a href="javascript:">（<span>北京</span>）切换城市</a>-->
					<#--<ul>-->
						<#--<li><a href="sidebar_1.html">北京</a></li>-->
						<#--<li><a href="sidebar_2.html">上海</a></li>-->
						<#--<li><a href="sidebar_2.html">南京</a></li>-->
						<#--<li><a href="sidebar_2.html">深圳</a></li>-->
						<#--<li><a href="sidebar_2.html">广州</a></li>-->
						<#--<li><a href="sidebar_2.html">天津</a></li>-->
						<#--<li><a href="sidebar_2.html">杭州</a></li>-->
					<#--</ul>-->
				<#--</li>-->
				<#--<li><a href="donation.html" target="dialog" height="400" title="捐赠 & DWZ学习视频">捐赠</a></li>-->
				<#--<li><a href="changepwd.html" target="dialog" width="600">设置</a></li>-->
				<#--<li><a href="http://www.cnblogs.com/dwzjs" target="_blank">博客</a></li>-->
				<li style="color: white;">${userName}</li>
				<li><a href="../ddbus/api/loginOut">退出</a></li>
			</ul>
			<ul class="themeList" id="themeList">
				<li theme="default"><div class="selected">蓝色</div></li>
				<li theme="green"><div>绿色</div></li>
				<!--<li theme="red"><div>红色</div></li>-->
				<li theme="purple"><div>紫色</div></li>
				<li theme="silver"><div>银色</div></li>
				<li theme="azure"><div>天蓝</div></li>
			</ul>
		</div>

		<!-- navMenu -->

	</div>

	<div id="leftside">
		<div id="sidebar_s">
			<div class="collapse">
				<div class="toggleCollapse"><div></div></div>
			</div>
		</div>
		<div id="sidebar">
			<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

			<div class="accordion" fillSpace="sidebar">
				<div class="accordionHeader">
					<h2><span>Folder</span>主菜单</h2>
				</div>
				<div class="accordionContent">
					<ul class="tree treeFolder">
						<li><a>公交数据管理</a>
							<ul>
								<#--<li><a href="../ddbus/api/findAllHotSite" target="navTab">热门站点数据管理</a></li>-->
                                <li><a href="../ddbus/api/findLostAndFoundList" target="navTab">失物招领管理</a></li>
                                <#--<li><a href="../ddbus/api/findInfoFeedBackList" target="navTab">信息反馈管理</a></li>-->
							</ul>
						</li>
						<li><a>用户管理</a>
							<ul>
								<#--<li><a href="../api/findUserListPage" target="navTab">系统用户管理</a></li>-->
								<li><a href="../ddbus/wcUser/page" target="navTab">乘客管理</a></li>
							</ul>
						</li>
                        <li><a>营销管理</a>
                            <ul>
                            <#--<li><a href="../api/findUserListPage" target="navTab">系统用户管理</a></li>-->
                                <li><a href="../ddbus/redPacket/page" target="navTab">红包活动</a></li>
                                <li><a href="../ddbus/redPacketDetail/page" target="navTab">红包记录</a></li>
                            </ul>
                        </li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div id="container">
		<div id="navTab" class="tabsPage">
			<div class="tabsPageHeader">
				<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
					<ul class="navTab-tab">
						<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
					</ul>
				</div>
				<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
				<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
				<div class="tabsMore">more</div>
			</div>
			<ul class="tabsMoreList">
				<li><a href="javascript:;">我的主页</a></li>
			</ul>
			<div class="navTab-panel tabsPageContent layoutBox">
				<div class="page unitBox">
					<div class="pageFormContent" layoutH="80" style="margin-right:230px">
					</div>
					<div style="width:230px;position: absolute;top:60px;right:0" layoutH="80">

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<#--<div id="footer">Copyright &copy; 2010 <a href="demo_page2.html" target="dialog">DWZ团队</a> 京ICP备15053290号-2</div>-->

</body>
</html>
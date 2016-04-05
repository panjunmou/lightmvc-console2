<%--
  Created by IntelliJ IDEA.
  User: PanJM
  Date: 2016/3/16
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>主页</title>
    <jsp:include page="inc.jsp"></jsp:include>
    <script type="text/javascript">
        var index_layout;
        var index_tabs;
        var index_tabsMenu;
        var layout_west_tree;

        var sessionInfo_userId = '${sessionInfo.id}';
        if (!sessionInfo_userId) {
            //如果没有登录,直接跳转到登录页面
            window.location.href = '${ctx}/console/index';
        }

        $(function () {
            index_layout = $('#index_layout').layout({
                fit: true
            });
            index_tabs = $('#index_tabs').tabs({
                fit: true,
                border: false,
                tools: [{
                    iconCls: 'icon-home',
                    handler: function () {
                        index_tabs.tabs('select', 0);
                    }
                }, {
                    iconCls: 'icon-refresh',
                    handler: function () {
                        var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                        index_tabs.tabs('getTab', index).panel('open').panel('refresh');
                    }
                }, {
                    iconCls: 'icon-del',
                    handler: function () {
                        var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                        var tab = index_tabs.tabs('getTab', index);
                        if (tab.panel('options').closable) {
                            index_tabs.tabs('close', index);
                        }
                    }
                }]
            });

            layout_west_tree = $('#layout_west_tree').tree({
                url: "${ctx}/sys/resource/treeList",
                idField:'treeId',
                textField:'name',
                lines: true,
                animate: true,
                onClick: function (node) {
                    if (node.attributes && node.attributes.url) {
                        var url = '${ctx}' + node.attributes.url + "?title=" + node.text;
                        addTab({
                            url: url,
                            title: node.text,
                            iconCls: node.iconCls
                        });
                    }
                },
                onLoadSuccess: function (node,data) {
                    //展开所有节点
//                    $("#layout_west_tree").tree('expandAll');
                    //展开指定节点
                    /*var node = $('#layout_west_tree').tree('find', 10001);
                    $("#layout_west_tree").tree('expand',node.target);*/
                }
            });
        });

        function addTab(params) {
            var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
            var t = $('#index_tabs');
            var opts = {
                title: params.title,
                closable: true,
                iconCls: params.iconCls,
                content: iframe,
                border: false,
                fit: true
            };
            if (t.tabs('exists', opts.title)) {
                t.tabs('select', opts.title);
            } else {
                t.tabs('add', opts);
            }
        }
    </script>
</head>
<body>
<%--<div id="loading"
     style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
    <img src="${ctx}/style/images/ajax-loader.gif"
         style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
</div>--%>
<div id="index_layout">
    <div data-options="region:'north',border:false" style=" overflow: hidden;">
        <div id="header">
			<span style="float: right; padding-right: 20px;">欢迎， <b>${sessionInfo.userName}</b>&nbsp;&nbsp;
                <a href="javascript:void(0)" onclick="editUserPwd()" style="color: #fff">修改密码</a>&nbsp;&nbsp;
                <a href="javascript:void(0)" onclick="logout()" style="color: #fff">安全退出</a>
        	&nbsp;&nbsp;&nbsp;&nbsp;
    		</span>
            <span class="header"></span>
        </div>
    </div>
    <div data-options="region:'west',split:true" title="菜单" style="width: 160px; overflow: hidden;overflow-y:auto;">
        <div class="well well-small" style="padding: 5px 5px 5px 5px;">
            <ul id="layout_west_tree"></ul>
        </div>
    </div>
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="index_tabs" style="overflow: hidden;">
            <div title="首页" data-options="border:false" style="overflow: hidden;">
                <div style="padding:10px 0 10px 10px">
                    <h2>系统介绍</h2>
                    <div class="light-info">
                        <div class="light-tip icon-tip"></div>
                        <div>XXX管理系统。</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div data-options="region:'south',border:false"
         style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #eee">
        版权所有@XXXXXXXXXX
    </div>
</div>
</body>
</html>

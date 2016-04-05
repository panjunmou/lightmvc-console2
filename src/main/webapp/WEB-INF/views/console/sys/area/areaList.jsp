<%--
  Created by IntelliJ IDEA.
  User: PanJM
  Date: 2016/4/5
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <title>地区管理</title>
    <script type="text/javascript">
        var treeGrid;
        var idField = "treeId";
        var treeField = "name";
        $(function () {
            /*加载树状列表*/
            // @TODO 可能的话加上checkbox
            treeGrid = $('#treeGrid').treegrid(
                    {
                        url: '<c:url value="${ctx}/sys/area/treeGrid"/>',
                        idField: idField,
                        treeField: treeField,
                        fit: true,
                        fitColumns: false,
                        border: false,
                        rownumbers: true,
                        frozenColumns: [[{
                            title: '编号',
                            field: 'id',
                            width: 40,
                            hidden: true
                        }]],
                        columns: [[
                            {
                                title: '名称',
                                field: 'name',
                                width: 150
                            },
                            {
                                field: 'seq',
                                title: '排序号',
                                width: 60,
                                align: 'center'
                            },
                            {
                                width: '80',
                                title: '创建人',
                                field: 'createUser',
                                align: "center"
                            },
                            {
                                width: '150',
                                title: '创建时间',
                                field: 'createDate',
                                align: "center",
                                sortable: true,
                                formatter: function (value, row, index) {
                                    var unixTimestamp = new Date(value);
                                    return unixTimestamp.toLocaleString();
                                }

                            },
                            {
                                width: '80',
                                title: '修改人',
                                field: 'updateUser',
                                align: "center"
                            },
                            {
                                width: '150',
                                title: '修改时间',
                                field: 'updateDate',
                                align: "center",
                                sortable: true,
                                formatter: function (value, row, index) {
                                    var unixTimestamp = new Date(value);
                                    return unixTimestamp.toLocaleString();
                                }

                            },
                            {
                                field: 'action',
                                title: '操作',
                                width: 100,
                                align: 'center',
                                formatter: function (value, row, index) {
                                    var str = '&nbsp;';
                                    if ($.canEdit) {
                                        str += $.formatString(
                                                '<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>',
                                                row.id);
                                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                    }
                                    if ($.canDelete) {

                                        str += $.formatString(
                                                '<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>',
                                                row.id);
                                    }
                                    return str;
                                }
                            }]],
                        onLoadSuccess: function (row, data) {
                            //展开所有节点
//                            $("#treeGrid").treegrid('expand',resourceId).treegrid("select",3);
//                            $("#treeGrid").treegrid('expandAll',1).treegrid("select",61);
//                            $("#treeGrid").treegrid('expandAll');

                            var lTree = $("#locationTree").val();
                            var split = lTree.split(",");
                            var num = split[0];
                            if (split.length > 1) {
                                lTree = lTree.substring(lTree.indexOf(",") + 1, lTree.length);
                                $("#locationTree").val(lTree);
                                $("#treeGrid").treegrid('expand', num);
                            } else if (split.length == 1) {
                                $("#treeGrid").treegrid("select", num);
                            }
                        }
                        , toolbar: '#toolbar'
                    });
        });

        /*新增*/
        function addFun() {
            parent.$.modalDialog({
                title: '新建资源',
                width: 500,
                height: 350,
                href: '<c:url value="${ctx}/sys/resource/addPage"/>',
                buttons: [{
                    text: '保存',
                    handler: function () {
                        parent.$.modalDialog.openner_treeGrid = treeGrid;
                        parent.$.modalDialog.locationTree = $("#locationTree");
                        var f = parent.$.modalDialog.handler.find('#myForm');
                        f.submit();
                    }
                }, {
                    text: '取消',
                    handler: function () {
                        parent.$.modalDialog.handler.dialog('close');
                    }
                }]
            });
        }

        /*修改*/
        function editFun(id) {
            parent.$.modalDialog({
                title: '修改资源',
                width: 500,
                height: 350,
                href: '<c:url value="${ctx}/sys/resource/editPage?id='+id+'"/>',
                buttons: [{
                    text: '保存',
                    handler: function () {
                        parent.$.modalDialog.openner_treeGrid = treeGrid;
                        parent.$.modalDialog.locationTree = $("#locationTree");
                        var f = parent.$.modalDialog.handler.find('#myForm');
                        f.submit();
                    }
                }, {
                    text: '取消',
                    handler: function () {
                        parent.$.modalDialog.handler.dialog('close');
                    }
                }]
            });
        }

        /*删除*/
        function deleteFun(id) {
            parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function (b) {
                if (b) {
                    progressLoad();
                    $.ajax({
                        type: "post",
                        url: "<c:url value="${ctx}/sys/resource/delete"/>",
                        dataType: "json",
                        data: {
                            "id": id
                        },
                        success: function (result) {
                            progressClose();
                            if (result.success) {
                                parent.$.messager.alert('提示', result.msg, 'info');
                                treeGrid.treegrid('reload');
                                parent.layout_west_tree.tree('reload');
                            }
                        },
                        error: function () {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>
<input type="hidden" id="locationTree" value=""/>
<div id="toolbar" style="display: none;">
    <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
       data-options="plain:true,iconCls:'icon-add'">新建
    </a>
</div>
<table id="treeGrid"></table>
</body>
</html>

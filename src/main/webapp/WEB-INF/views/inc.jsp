<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="edge"/>
<link rel="shortcut icon" href="${ctx}/style/images/favicon.png" />

<%--[jQuery]--%>
<script src="${ctx}/jslib/easyui1.4.5/jquery.min.js" type="text/javascript" charset="utf-8"></script>


<%-- [EasyUI] --%>
<link id="easyuiTheme" rel="stylesheet"
      href="${ctx}/jslib/easyui1.4.5/themes/<c:out value="${cookie.easyuiThemeName.value}" default="gray"/>/easyui.css"
      type="text/css">
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/jslib/easyui1.4.5/themes/icon.css" type="text/css">
<script type="text/javascript" src="${ctx}/jslib/easyui1.4.5/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/jslib/easyui1.4.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<!-- [扩展JS] -->
<script type="text/javascript" src="${ctx}/jslib/extentJs.js" charset="utf-8"></script>

<%-- [扩展lightmvc样式] --%>
<link rel="stylesheet" href="${ctx}/style/css/mhj.css" type="text/css">

<script type="text/javascript">

    <%--[重写indexOf方法,解决IE的兼容性]--%>
    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function (elt /*, from*/) {
            var len = this.length >>> 0;
            var from = Number(arguments[1]) || 0;
            from = (from < 0)
                    ? Math.ceil(from)
                    : Math.floor(from);
            if (from < 0)
                from += len;
            for (; from < len; from++) {
                if (from in this &&
                        this[from] === elt)
                    return from;
            }
            return -1;
        };
    }

    <%--[格式化时间]--%>
    Date.prototype.toLocaleString = function (fmt) {
        var format = "yyyy-MM-dd hh:mm:ss";
        if (fmt != undefined) {
            format = fmt;
        }
        var o = {
            "M+": this.getMonth() + 1, // month
            "d+": this.getDate(), // day
            "h+": this.getHours(), // hour
            "m+": this.getMinutes(), // minute
            "s+": this.getSeconds(), // second
            "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
            "S": this.getMilliseconds()
            // millisecond
        }
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
                        .substr(("" + o[k]).length));
        return format;
    };

</script>

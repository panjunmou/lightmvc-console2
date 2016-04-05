<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="inc.jsp"></jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎登录</title>
    <link href="${ctx}/style/css/base.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/style/css/login.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/style/css//bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">

        $(function () {

            if ($.cookie("rmbUser") == "true") {
                $("#rememberMe").prop("checked", true);
                $("#loginname").val($.cookie("loginname"));
                $("#password").val($.cookie("password"));
            }

            /*表单提交事件*/
            $('#loginform').form({
                url: '${ctx}/console/login',
                onSubmit: function () {
                    progressLoad("正在登录。。。");
                    var isValid = $(this).form('validate');
                    if (!isValid) {
                        progressClose();
                    }
                    return isValid;
                },
                success: function (data) {
                    var result = $.parseJSON(data);
                    progressClose();
                    if (result.success) {
                        window.location.href = "${ctx}/console/index";
                        save();
                    } else {
                        $.messager.show({
                            title: '提示',
                            msg: '<div class="light-info"><div class="light-tip icon-tip"></div><div>'
                            + result.msg + '</div></div>',
                            showType: 'show'
                        });
                    }
                }
            });

            /*回车登录*/
            $(document).keydown(function () {
                if (event.keyCode == 13) {
                    event.returnValue = false;
                    event.cancel = true;
                    $('#loginform').submit();
                }
            });
        });

        function submitForm() {
            $("#loginform").submit();
        }

        function save() {
            if ($("#rememberMe").prop("checked")) {
                var loginname = $("#loginname").val();
                var password = $("#password").val();
                $.cookie("rmbUser", "true", {
                    expires : 7
                }); // 存储一个带7天期限的cookie
                $.cookie("loginname", loginname, {
                    expires : 7
                });
                $.cookie("password", password, {
                    expires : 7
                });
            } else {
                $.cookie("rmbUser", "false", {
                    expire : -1
                });
                $.cookie("loginname", "", {
                    expires : -1
                });
                $.cookie("password", "", {
                    expires : -1
                });
            }
        };
    </script>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">系统登录</h3>
                </div>
                <div class="panel-body">
                    <form role="form" id="loginform">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" id="loginName" placeholder="用户名" name="loginName"
                                       type="text" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" id="passWord" placeholder="密码" name="passWord"
                                       type="password">
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="rememberMe" name="remember" type="checkbox" value="Remember">记住密码
                                </label>
                            </div>
                            <button class="btn btn-lg btn-success btn-block" type="button" onclick="submitForm()">登录
                            </button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
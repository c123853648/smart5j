
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<%@ include file="../common.jsp" %>
<head>

    <meta charset="UTF-8">
    <title>智慧5家商户后台</title>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>/css/login.css" />
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>/css/css.css" />
    <script type="text/javascript" src=""></script>
</head>
   <script src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript">
	function registers(){
		var username=$("#username").val();
		if(username==''||username==null){
			alert("用户名不能为空");
			return ;
		}
		var password=$("#password").val();
		if(password==''||password==null){
			alert("密码不能为空");
			return ;
		}
		$.ajax({
	            url: "${pageContext.request.contextPath }/seller/regisger",
	            datatype:"json",
	            data: {username:username, password:password},
	            type:'post',
	            success:function(msg){
	            	//alert(msg.reason);
	             	if(msg.resultcode==200){
	             		alert("注册成功,请去登录");
	             		//window.location.href="${pageContext.request.contextPath }/seller/index.jsp";
	             	}else{
	             		//alert(msg.reason);
	             	}
	             //	window.location.href="${pageContext.request.contextPath }/user/toIndex";
	            },
	            error: function(){
	            	
	            }
		});
	}
</script>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="147" background="<%=baseUrl%>/images/top02.gif">
            	<img src="<%=baseUrl%>/images/top03.gif" width="776" height="147" />
            </td>
    </tr>
</table>
<table id="main" width="562" border="0" align="center" cellpadding="0" cellspacing="0" class="right-table03">
    <tr>
        <td width="221">
            <table width="95%" border="0" cellpadding="0" cellspacing="0" class="login-text01">

                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="login-text01">
                            <tr>
                                <td align="center">
                                    <img src="<%=baseUrl%>/images/ico13.gif" width="107" height="97" />
                                </td>
                            </tr>
                            <tr>
                                <td height="40" align="center" style="color: red;font-weight: bold;"></td>
                            </tr>

                        </table>
                    </td>
                    <td>
                        <img src="images/line01.gif" width="5" height="292" />
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <form action="<%=baseUrl %>/user/register" method="post">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="31%" height="35" class="login-text02">用户名称：

                    </td>
                    <td width="69%">
                        <input name="username" id="username" type="text" size="30" placeholder="请输入用户名" />
                    </td>
                </tr>
                <tr>
                    <td height="35" class="login-text02">密　码：</td>
                    <td>
                        <input name="password" id="password" type="password" size="30" placeholder="请输入密码"/>
                    </td>
                </tr>
                <tr>
                    <td height="35">&nbsp;</td>
                    <td id ="loginOrregister">
                        <input name="register" id="register" type="button" onclick="registers();" class="right-button01" value="注册"/>
                    	<span font-size: 12px"><a href="<%=baseUrl%>shoplogin.jsp">返回登陆</a></span>
                    </td>
                </tr>
                 <tr >
                    <td height="35">&nbsp;</td>
                    <td style="text-align: right;font-size: 12px" id = "registerTr" class="loginOrRegister">
                        <span >${result}</span>
                    </td>
                </tr>
            </table>
        </form>
        </td>
    </tr>
</table>
</body>
</html>
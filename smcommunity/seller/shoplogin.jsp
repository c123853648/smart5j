
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<%@ include file="../common.jsp" %>
<%
   
%>
<head>

    <meta charset="UTF-8">
    <title>智慧5家商户后台</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/login.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/css.css" />
   <script src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript">
	function logins(){
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
	            url: "${pageContext.request.contextPath }/seller/login",
	            datatype:"json",
	            data: {username:username, password:password},
	            type:'post',
	            success:function(msg){
	            	alert(msg.reason);
	             	if(msg.resultcode==200){
	             		window.location.href="${pageContext.request.contextPath }/seller/index.jsp";
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
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="147" background="<%=basePath%>images/top02.gif" align="center">
            	<h1>智慧5家商户后台</h1>
        </td>
    </tr>
</table>
        <td>
            <form action="<%=basePath%>UserServlet?method=login" method="post">
            <table width="50%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td width="50%" height="35" class="login-text02" >用户名称：

                    </td>
                    <td width="50%">
                        <input name="username" id="username" type="text" size="30" placeholder="请输入用户名" />
                    </td>
                </tr>
                <tr>
                    <td height="35" class="login-text02">密　码：</td>
                    <td>
                        <input name="password" id="password"  type="password" size="30" placeholder="请输入密码"/>
                    </td>
                </tr>
                <tr>
                    <td height="35">&nbsp;</td>
                    <td id ="loginOrregister">
                        <input name="login" id="login" type="button" onclick="logins();" class="right-button01" value="登陆"/>
                        <input name="Submit232" type="reset" class="right-button02" value="重 置" />
                    </td>
                </tr>
                <tr>
                    <td height="35">&nbsp;</td>
                    <td style="text-align: right;font-size: 12px" id = "registerTr" class="loginOrRegister">
                        <span >没有账号? </span>
                        <a href="<%=basePath%>seller/register.jsp">注 册</a>
                    </td>
                </tr>
            </table>
        </form>
        </td>
    </tr>
</table>
</body>
</html>
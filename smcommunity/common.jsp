<%
String baseUrl= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+baseUrl+"/";

%>
<link rel="stylesheet" type="text/css" href="<%=baseUrl %>/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=baseUrl %>/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=baseUrl %>/easyui/themes/all.css"/>
<script type="text/javascript" src="<%=baseUrl %>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=baseUrl %>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=baseUrl %>/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=baseUrl %>/js/common.js"></script>
<script type="text/javascript" src="<%=baseUrl %>/js/json2.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


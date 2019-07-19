<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>


<html>
<head id="Head1">
    <%@ include file="../common.jsp" %>
<script type="text/javascript">
$(function () {
    $('#grid1').datagrid({
        title: '商品列表',
        nowrap: false,
        striped: true,
        fit: true,
        url: "<%=baseUrl%>/goods/search?goodstype=0",
        idField: 'id',
        pagination: true,
        rownumbers: true,
        pageSize: 10,
        pageNumber: 1,
        singleSelect: true,
        fitColumns: true,
        pageList: [5,10, 20, 50, 100, 200, 300, 500, 1000],
        sortName: 'id',
        sortOrder: 'desc',
        columns: [
            [
                //{field: 'ck', checkbox: true},
                {title: 'id', width: 100, field: 'id', sortable: true},
                {title: '商品名称', width: 100, field: 'goodsname', sortable: true},
                {title: '商品类别', width: 100, field: 'tname', sortable: true},
                {title: '商品描叙', width: 400, field: 'goodsdescribe',sortable: true},
                {title: '商品图片', width: 400, field: 'goodsimage', formatter:function(value,row,index){
                	return "<img style='width:60px;height:60px;' border='1' src='http://localhost:8080/"+row.goodsimage+"'/>";
                }}
            ]
        ], toolbar: [
            {
                text: '新增',
                id: "tooladd",
                disabled: false,
                iconCls: 'icon-add',
                handler: function () {
                    $("#action").val("add");
                    $("#managerDialog").dialog('open');
                    managForm.reset();
                }
            },
             '-',
            {
                text: '修改',
                id: 'tooledit',
                disabled: false,
                iconCls: 'icon-edit',
                handler: function () {
                    $("#action").val("edit");
                    var selected = $('#grid1').datagrid('getSelected');
                    if (selected) {
                        edit(selected);
                    } else {
                        $.messager.alert("提示", "请选择一条记录进行操作");
                    }
                }
            }, 
            '-',
            {
                text: '删除',
                id: 'tooldel',
                disabled: false,
                iconCls: 'icon-remove',
                handler: function () {
                    var rows = $('#grid1').datagrid('getSelections');
                    if (rows.length) {
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            ids += rows[i].id + ",";
                        }
                        ids = ids.substr(0, (ids.length - 1));
                        $.messager.confirm('提示', '确定要删除吗？', function (r) {
                            if (r) {
                                deleteItem(ids);
                            }
                        });
                    } else {
                        $.messager.alert("提示", "请选择一条记录进行操作");
                    }
                }
            }
        ]
    });

    document.onkeydown=function (e){
        e = e ? e : event;
        if(e.keyCode == 13){
            query();
        }
    }

});

function save() {
    $('#managForm').form('submit', {
        url: "<%=baseUrl%>/goods/addOrUpdate",
        onSubmit: function () {
            return inputCheck();
        },
        success: function (data) {
        	console.log(data)
            closeBackGround();
            $.messager.alert("提示", data, "info", function () {
                closeFlush();
            });
        }
    });
}

function edit(obj) {
	var id = obj.id;
	$("#id").val(id);
    $("#goodsname").val(obj.goodsname);
    $("#tid").val(obj.tid);
    $("#goodsdescribe").val(obj.goodsdescribe);
    $("#managerDialog").dialog('open');
}

function deleteItem(uuid) {
    //openBackGround();
    $.post("<%=baseUrl%>/goods/delete", {id: uuid}, function (data) {
        //closeBackGround();
       
    });
    closeFlush();
}

function cancel() {
    $.messager.confirm('提示', '是否要关闭？', function (r) {
        if (r) {
            closeFlush();
        }
    });
}

function query() {
    $('#grid1').datagrid('load', serializeObject($('#searchForm')));
}


function closeFlush() {
    managForm.reset();
    $("#managerDialog").dialog('close');
    $("#grid1").datagrid("reload");
}

function inputCheck() {
    if (!($("#managForm").form("validate"))) {
        return false;
    }
    openBackGround();
    return true;
}



function setNull(){
    searchForm.reset();
}



</script>
</head>
<body class="easyui-layout">
<div region="north" border="false" style="height:3px;overflow: hidden"></div>
<div region="west" border="false" style="width:3px;"></div>
<div region="east" border="false" style="width:3px;"></div>
<div region="south" border="false" style="height:3px;overflow: hidden"></div>
<div region="center" border="false">
    <div id="main" class="easyui-layout" fit="true" style="width:100%;height:100%;">
        <div region="north" id="" style="height:100%;" class="" title="查询条件">
            <form action="" id="searchForm" name="searchForm" method="post" align="center">
                <table cellpadding="5" cellspacing="0" class="tb_search" align="center">
                    <tr>
                        <td width="1%" >
                            <label for="goodsname">商品名称：</label>
                            <input type="text" id="key" name="key"  maxlength="32"/>
                        </td>
                        <td width="10%">
                            <a href="#" onclick="query();" id="querylink" class="easyui-linkbutton"
                               iconCls="icon-search">查询</a>
                            <a href="#" onclick="setNull();" class="easyui-linkbutton" iconCls="icon-redo">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div region="center" border="false" style="padding:3px 0px 0px 0px;overflow:hidden">

            <table id="grid1"></table>

        </div>
    </div>
</div>


<div id="managerDialog" class="easyui-dialog" title="商品管理" style="width:450px;height:350px;" toolbar="#dlg-toolbar"
     buttons="#dlg-buttons2" resizable="true" modal="true" closed='true'>
    <form id="managForm" name="managForm" method="post" enctype="multipart/form-data">
        <input type="hidden" id="id" name="id"/>
        <input type="hidden" id="goodstype" name="goodstype" value="0"/>
        <table cellpadding="1" cellspacing="1" class="tb_custom1">
            <tr>
                <th width="30%" align="right"><label>商品类别：</label></th>
                <td width="60%" colspan="1">
                   <input id="cc" name="tid">
                </td>
            </tr>
            <tr>
                <th width="30%" align="right"><label>商品名称：</label></th>
                <td width="60%" colspan="1">
                    <input id="goodsname" name="goodsname" class="easyui-validatebox"
                           style="width:300px;word-wrap: break-word;word-break:break-all;" type="text" required="true"
                           validType="length[0,100]"/>
                </td>
            </tr>
            <tr>
                <th width="30%" align="right"><label>商品简介：</label></th>
                <td width="60%" colspan="1">
                    <input id="goodsdescribe" name="goodsdescribe" class="easyui-validatebox"
                           style="width:300px;word-wrap: break-word;word-break:break-all;" type="text" required="true"
                           validType="length[0,100]"/>
                </td>
            </tr>
            <tr>
                <th width="30%" align="right"><label>商品图片：</label></th>
                <td width="60%" colspan="1">
                    <input type="file" name="file" style="width:200px">
                </td>
            </tr>

        </table>


    </form>
    <div id="dlg-buttons2">
        <a href="#" class="easyui-linkbutton" onclick="save();">保存</a>
        <a href="#" class="easyui-linkbutton" onclick="cancel();">取消</a>
    </div>
</div>



</body>
<script type="text/javascript">
$('#cc').combobox({
    url:'<%=baseUrl%>/type/findAll',
    valueField:'id',
    textField:'typename'
});
</script>
</html>
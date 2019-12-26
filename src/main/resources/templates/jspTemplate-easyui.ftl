<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	// 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/senhuo/）:    
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/WEB-INF/jsp/admin/common/link.jsp"%> 


    <script type="text/javascript">

    var dataGrid;
    $(function() {
    	
    	dataGrid = $('#dataGrid').datagrid({
    		url : '${entityPackage}/${lowerName}/list.html',
    		fit : true,
    		idField : 'id',
    		fitColumns : true,
    		border : false,
    		pagination : true,
    		pageSize : 30,
    		pageList : [ 10, 20, 30],
    		selectOnCheck : false,
			singleSelect : false,//false为多选状态
    		nowrap : true,
    		toolbar: '#toolbar',
    		columns : [ [ {
    			field : 'ed',
    			title:'<input type="checkbox"  id="ckAll" >',
				width : 2,
				align:'center',
				checkbox : false,
				formatter: function (value, row, rowIndex) {
                    return '<input type="checkbox" name="subCheck" value="' + row.id + '" >';
                }
    		}
    		#foreach($bean in $!{columnData})
    		,{
    			field : '${bean.columnName}',
    			title : '${bean.columnComment}',
    			width :20
    		}
    		#end
    		
    		] ]
    	});
    	$("#ckAll").click(function() {
			$("input[name='subCheck']").prop("checked", this.checked);
		});
    });
    
	function addFun(){
		$('#inputDialog').window('open');
    	$('#inputDialog').window('refresh', "${entityPackage}/${lowerName}/input.html");
	}
	function updateFun(){
		var rows = $("input:checkbox:checked");
    	if(rows.length == 0){
    		$.messager.alert('提示','请选择要修改的记录','info');
    	}else if(rows.length > 1){
    		$.messager.alert('提示','一次只能修改一条记录','info');
    	}else{
    		$('#inputDialog').window('open');
	    	$('#inputDialog').window('refresh', "${entityPackage}/${lowerName}/input.html?id="+rows[0].value);
    	}
	}
	
	function delFun(){
    	var rows = $("input:checkbox:checked");
    	if(rows.length==0){
    		$.messager.alert('提示','请选择要删除记录','info');
    	}else{
    		$.messager.confirm('确认', '确定删除吗？', function(r) {
    			if(r){
    				$.messager.progress({
    					title : '提示',
    					text : '数据处理中，请稍后....'
    				});
    				
    				var ids = [];
    				for(var i=0; i<rows.length; i++){
    					var temp = rows[i].value;
	 					if(temp != 'on'){
	 						ids.push(temp);
	 					}
    				}
    				var datas = 'ids='+ids;
    				$.post('${entityPackage}/${lowerName}/delete.html',datas, function(data) {
    					$.messager.progress('close');
    					if(data == 0 || data == '0'){
							$.messager.alert("操作提示 ", "删除成功", "info");
    						dataGrid.datagrid("reload");
    					}else{
    						$.messager.alert("操作提示 ", "删除失败", "error");
    					}
    				},"html");
    			}
    		});
    	}
    }
   
    </script>
    
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">	
		
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	
	<div id="toolbar" style="display: none;padding: 7px" >
		<a onclick="addFun();" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'">增加</a>
		<a onclick="updateFun();" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
		<a onclick="delFun();" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
	</div>
	
	<div id="inputDialog" class="easyui-window" title="增加/修改${codeName}信息"style="width: 600px; height: 550px;padding: 5px;"
		data-options="resizable:true,modal:true,shadow:true,collapsible:false,minimizable:false,maximizable:true,closed:true">
		<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center',border:false" style="padding:10px;">
            </div>
        </div>
	</div>
	
</body>
</html>
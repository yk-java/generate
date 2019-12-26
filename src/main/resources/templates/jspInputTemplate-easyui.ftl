<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">

function saveFun(){
	var isValid = $("#myform").form('validate');
	if (!isValid){
		$.messager.progress('close');
		return false;
	}
	$.messager.progress();
	
	var datas =$("#myform").serialize();
	
    $.post(
    	"${ctx}/${entityPackage}/${lowerName}/save.html", 
    	datas,
    	function (result) { 
    		$.messager.progress('close');
			var data = jQuery.parseJSON(result);
			if (data == "0") {
				$.messager.alert("操作提示 ", "保存成功", "info");
				$('#inputDialog').window('close');
				dataGrid.datagrid("reload");
			} else if (data == "-1") {
				$.messager.alert("操作提示 ", "该${codeName}已存在", "info");
			} else{
				$.messager.alert("操作提示 ", "保存失败！", "error");
			}
    	}, "text");
}

</script>
<div class="easyui-layout" data-options="fit:true">
	<form id="myform" method="post">
		<input type="hidden" id="id" name="id" value="${${lowerName}.id == null ? 0 : ${lowerName}.id }" />
		<div data-options="region:'center'" style="padding: 10px;">
			<dl class="form_dl clearfix">
				#foreach($bean in $!{columnData})
				#if($bean.columnName !='id')<dt>${bean.columnComment}：</dt>
				<dd>
					<input id="${bean.columnName}" name="${bean.columnName}" class="${bean.classType}" 
						value="${${lowerName}.${bean.columnName} }" style="width: 200px; height: 25px" 
						#if  ($bean.optionType =='required:true') data-options="${bean.optionType}"  #end />
					#if  ($bean.optionType =='required:true') <span style="font-size: 14px; color: red">*</span> #end </dd>
				#end
				#end
			</dl>
		</div>

		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px 0 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save'" onclick="saveFun()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'"
				onclick="$('#inputDialog').window('close');">取消</a>
		</div>
	</form>
</div>
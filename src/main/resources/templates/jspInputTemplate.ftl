<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${codeName}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${ctx}/css/public.css" media="all" />
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input type="hidden" id="id" name="id" value="0">

    #foreach($bean in $!{columnData})
    #if($bean.columnName !='id')
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">${bean.columnComment}</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input ${bean.columnName}" #if  ($bean.optionType =='required:true') lay-verify="required"  #end placeholder="请输入${bean.columnComment}">
        </div>
    </div>
    #end
    #end

    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-sm" lay-submit="" lay-filter="submitBtn">立即保存</button>
            <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript">
    var ctx = '${ctx}';
</script>
<script type="text/javascript" src="${ctx}/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/cdss/${entityPackage}/${lowerName}/${lowerName}-input.js"></script>
</body>
</html>
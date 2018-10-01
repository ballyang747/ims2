<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/commonality/commons.jsp" %>
<%@ taglib uri="ims/kingson" prefix="ks"  %>
<!DOCTYPE html >
<html>
<head>
<title>OA办公管理系统-查看流程定义</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/blockUI/jquery.blockUI.js"></script>
<script type="text/javascript" src="${ctx}/oajs/openpageCss.js"></script>
<!-- 引入分页样式 -->
<link rel="stylesheet"
	href="${ctx }/css/pager.css" />
</head>
<body style="width: 98%; height: 100%;" >
	<div class="panel panel-primary" style="padding-left: 10px;">
		<div class="panel-heading" style="background-color: #11a9e2;">
			<h3 class="panel-title">流程定义</h3>
		</div>
		<div class="panel-body" >
			<img alt="" src="${ctx }/workflow/process/definition/image.jspx?id=${def.id}"/>
		</div>
	</div>
</body>
</html>
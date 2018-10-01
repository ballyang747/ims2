<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/commonality/commons.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="ims/kingson" prefix="ks"  %>
<!DOCTYPE html >
<html>
<head>
<title>OA办公管理系统-我的待办</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/blockUI/jquery.blockUI.js"></script>
<script type="text/javascript" src="${ctx}/oajs/openpageCss.js"></script>
<!-- 引入分页样式 -->
<link rel="stylesheet" href="${ctx }/css/pager.css" />
<link rel="stylesheet" href="${ctx }/resources/workflow.css" />
</head>
<body style="width: 98%; height: 100%;" >
	<div class="panel panel-primary" style="padding-left: 10px;">
		<div class="panel-heading" style="background-color: #11a9e2;">
			<h3 class="panel-title">我的待办</h3>
		</div>
		<div class="panel-body data-list" >
			<table class="table table-bordered">
				<thead>
					<tr style="font-size: 12px;" align="center">
						<th style="text-align: center;">流程名称</th>
						<th style="text-align: center;">流程创建时间</th>
						<th style="text-align: center;">流程创始人</th>
						<th style="text-align: center;">任务名称</th>
						<th style="text-align: center;">任务创建时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="item">
						<%-- data-开头的都是HTML 5的特有属性，表示自定义的数据属性 --%>
						<tr style="cursor: pointer;" data-id="${item.task.id }">
							<td>${item.definition.name }</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.instance.startTime }"/></td>
							<td>${item.initiator.name }</td>
							<td>${item.task.name }</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.task.createTime }"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
        <!-- 分页标签区 -->
		<ks:pager pageIndex="${ pageModel.pageIndex}" 
			pageSize="${pageModel.pageSize}" 
			totalNum="${pageModel.recordCount}" 
			submitUrl="${ctx}/workflow/task.jspx?pageIndex={0}" pageStyle="badoo">
		</ks:pager>
	</div>
	<script type="text/javascript">
	$(".data-list tbody tr").click(function(){
		var id = $(this).attr("data-id");
		var url = "${ctx}/workflow/task/view.jspx?taskId=" + id;
		document.location.href = url;
	});
	
	</script>
</body>
</html>
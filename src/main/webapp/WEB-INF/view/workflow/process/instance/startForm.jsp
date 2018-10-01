<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/commonality/commons.jsp" %>
<%@ taglib uri="ims/kingson" prefix="ks"  %>
<!DOCTYPE html >
<html>
<head>
<title>OA办公管理系统-启动流程实例</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/blockUI/jquery.blockUI.js"></script>
<script type="text/javascript" src="${ctx}/oajs/openpageCss.js"></script>
<!-- 引入分页样式 -->
<link rel="stylesheet"
	href="${ctx }/css/pager.css" />

<!--  加入日期时间选择控件 -->
<link rel="stylesheet" href="${ctx }/resources/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />
<link rel="stylesheet" href="${ctx }/resources/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />

<script type="text/javascript" src="${ctx}/resources/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<link rel="stylesheet" href="${ctx }/resources/workflow.css" />
<script type="text/javascript" src="${ctx}/resources/workflow.js"></script>

<script type="text/javascript">
var contextPath = "${ctx}";
</script>

</head>
<body style="width: 98%; height: 100%;" >
	<%-- 启动流程实例，通过流程定义的ID来启动 --%>
	<form action="${ctx }/workflow/process/instance/start.jspx?processDefinitionId=${pf.definition.id }" 
		method="post"
		onsubmit="return checkFormOnSubmit();">
		<c:if test="${not empty pf.definition.description }">
		<div class="row">
			<div class="col-md-12 alert alert-info">
				${pf.definition.description }
			</div>
		</div>
		</c:if>
		<%-- 放表单内容 --%>
		<div class="row">
			<div class="col-md-12">
			<c:choose>
				<c:when test="${not empty pf.formContent }">
					${pf.formContent }
				</c:when>
				<c:when test="${not empty pf.formKey and empty pf.formContent }">
					<c:catch var="ex">
					<jsp:include page="/WEB-INF/view/process/${pf.definition.key }/form/${pf.formKey }"></jsp:include>
					</c:catch>
					<c:if test="${not empty ex }">
						<div class="alert alert-warning">
							没有表单内容
						</div>
					</c:if>
				</c:when>
			</c:choose>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-6">
				<fieldset>
					<legend>备注</legend>
					<textarea class="form-control" name="remark"></textarea>
				</fieldset>
			</div>
			<div class="col-md-6">
				<fieldset>
					<legend>操作</legend>
					<c:if test="${pf.definition.suspended }">
						<button type="button" disabled="disabled">启动流程实例</button>
					</c:if>
					<c:if test="${not pf.definition.suspended }">
						<button type="submit">启动流程实例</button>
					</c:if>
				</fieldset>
			</div>
		</div>
	</form>
</body>
</html>
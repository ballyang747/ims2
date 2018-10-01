<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/commonality/commons.jsp" %>
<%@ taglib uri="ims/kingson" prefix="ks"  %>
<!DOCTYPE html >
<html>
<head>
<title>OA办公管理系统-处理任务</title>
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
var businessData = ${json};
</script>

</head>
<body style="width: 98%; height: 100%;" >
	<%-- 启动流程实例，通过流程定义的ID来启动 --%>
	<form action="${ctx }/workflow/task.jspx?taskId=${item.task.id }" 
		method="post"
		onsubmit="return checkFormOnSubmit();">
		<%-- 保存业务数据的ID，用于后面修改数据 --%>
		<input type="hidden" name="id" value="${item.businessData.id }"/>
		<c:if test="${not empty item.task.description }">
		<div class="row">
			<div class="col-md-12 alert alert-info">
				${item.task.description }
			</div>
		</div>
		</c:if>
		<%-- 放表单内容 --%>
		<div class="row">
			<div class="col-md-12">
			<c:choose>
				<c:when test="${not empty item.formContent }">
					${item.formContent }
				</c:when>
				<c:when test="${not empty item.formKey and empty item.formContent }">
					<c:catch var="ex">
					<jsp:include page="/WEB-INF/view/process/${item.definition.key }/form/${item.formKey }"></jsp:include>
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
					<c:forEach items="${item.formData.formProperties }" var="p">
						<c:if test="${p.id eq 'approveAction' }">
							<legend>${p.name }</legend>
							
							<c:forEach items="${p.type.getInformation('values') }" var="kv">
								<label>
									${kv.key }
									<input type="radio" name="${p.id }" value="${kv.value }"/>
								</label>
							</c:forEach>
						</c:if>
					</c:forEach>
					
					<button type="submit">完成任务</button>
				</fieldset>
			</div>
		</div>
	</form>
</body>
</html>
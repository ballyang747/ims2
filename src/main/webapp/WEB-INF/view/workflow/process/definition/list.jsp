<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/commonality/commons.jsp" %>
<%@ taglib uri="ims/kingson" prefix="ks"  %>
<!DOCTYPE html >
<html>
<head>
<title>OA办公管理系统-流程定义管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/blockUI/jquery.blockUI.js"></script>
<script type="text/javascript" src="${ctx}/oajs/openpageCss.js"></script>
<!-- 引入分页样式 -->
<link rel="stylesheet"
	href="${ctx }/css/pager.css" />
<script type="text/javascript">
$(function(){
	$("#uploadProcessFile").click(function(){  
		
		 $('#uploadDialog').dialog({   
			title: '上传流程定义文件',   
			width: 600,   
			height: 150, 
			cls : "easyui-dialog",
		    closed: false,
		    modal: true
		}); 
	});
});
</script>
</head>
<body style="overflow: hidden; width: 98%; height: 100%;" >
   	<!-- 工具按钮区 -->
	<div>
		<button id="uploadProcessFile">上传</button>
	</div>
		<div class="panel panel-primary" style="padding-left: 10px;">
			<div class="panel-heading" style="background-color: #11a9e2;">
			<h3 class="panel-title">流程定义列表</h3>
		</div>
		<div class="panel-body" >
			
			<table class="table table-bordered">
				<thead>
					<tr style="font-size: 12px;" align="center">
						<th style="text-align: center;">分类</th>
						<th style="text-align: center;">流程名称</th>
						<th style="text-align: center;">流程KEY</th>
						<th style="text-align: center;">最新版本号</th>
						<th style="text-align: center;">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="def">
						<tr>
							<td>${def.category }</td>
							<td>${def.name }</td>
							<td>${def.key }</td>
							<td>${def.version }</td>
							<td>
								<c:if test="${def.suspended }">
									<a href="${ctx }/workflow/process/definition/active.jspx?id=${def.id}">激活</a>
								</c:if>
								<c:if test="${not def.suspended }">
									<a href="${ctx }/workflow/process/definition/suspend.jspx?id=${def.id}">禁用</a>
								</c:if>
								<a href="${ctx }/workflow/process/definition/view.jspx?id=${def.id}">查看</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
        <!-- 分页标签区 -->
		<ks:pager pageIndex="${ pageModel.pageIndex}" 
			pageSize="${pageModel.pageSize}" 
			totalNum="${pageModel.recordCount}" 
			submitUrl="${ctx}/workflow/process/definition.jspx?pageIndex={0}" pageStyle="badoo">
		</ks:pager>
	</div>
	<div id="uploadDialog" style="display: none;" >
		<form action="${ctx }/workflow/process/definition/upload.jspx"
			method="post"
			enctype="multipart/form-data">
			<p>
				上传的时候，只接收zip格式的压缩文件，流程定义设计好以后，压缩成zip格式的文件以后就能够使用了！
			</p>
			<%-- accept="application/zip"只要zip格式的压缩文件 --%>
			<input type="file" name="process" accept="application/zip"/>
			<button type="submit">上传</button>
		</form>
	</div>
</body>
</html>
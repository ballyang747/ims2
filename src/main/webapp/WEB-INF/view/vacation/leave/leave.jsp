<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
     <%@ taglib uri="ims/kingson" prefix="ks" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OA办公管理系统-假期管理</title>
<link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
	src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
<script type="text/javascript" src="${ctx}/oajs/openpageCss.js"></script>
<link rel="stylesheet"
	href="${ctx }/css/pager.css" />
	<script type="text/javascript">
	
	</script>
</head>
<body  style="overflow: hidden; width: 98%; height: 100%;">
<div>
	
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding: 5px;">
				<a  id="addLeave" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<a  id="deleteLeave" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
			</div>
			
			
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input  id="checkAll" type="checkbox" /></th>
							<th style="text-align: center;">假期名称</th>
							<th style="text-align: center;">创建者</th>
							<th style="text-align: center;">创建时间</th>
							<th style="text-align: center;">修改者</th>
							<th style="text-align: center;">修改时间</th>
							<th style="text-align: center;">备注</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					  
                  <c:forEach items="${leaveTypes}" var="leave" varStatus="stat">
	                    <tr id="dataTr_${stat.index}" align="center">
						<td><input type="checkbox" name="box" id="box_${stat.index}" value="${leave.code}"/></td>
						<td>${leave.name}</td>
						 <td>${leave.creater.name}</td> 
						<td><fmt:formatDate value="${leave.createDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
						<td>${leave.modifier.name}</td>
						<td><fmt:formatDate value="${leave.modifyDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>					
						<td>${leave.remark}</td>
						<td><span class="label label-info"><a href="#" onclick="updateLeave('${leave.code}')"  style="color: white;">修改</a></span>
						</td>
					   </tr>
                 </c:forEach>	
          
				</table>
			
			</div>
			
			
		</div>
	</div>
    <!-- div作为弹出窗口 -->
    <div id="divDialog" style="overflow: hidden;">
		<iframe id="iframe" scrolling="no" frameborder="0" width="100%" height="100%" ></iframe>
	</div>
</body>
</html>
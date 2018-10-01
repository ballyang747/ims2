<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OA办公管理系统-用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" href="${ctx }/resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
<!-- 导入bootStrap的库 -->
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">

<script type="text/javascript" src="${ctx}/oajs/openpageCss.js"></script>
<script type="text/javascript">
$(function() {
	 if("${message}"){
			$.messager.show({
				title:'提示信息',
				msg:"<font color='red'>${message}</font>",
				showType:'show'
			});
		}
	
	
	var boxes= $("input[id^='box_']");
	$.each(boxes,function(){
		if("${operas}".indexOf(this.value)!=-1){
			$(this).trigger("click").trigger("mouseover");
		}
	})
	
	
	$("#bindPopedom").click(function(){
		  var boxes  = $("input[id^='box_']").filter(":checked");
             var array = new Array();
               $.each(boxes,function(){
            	 array.push(this.value);
            	
         })	
         window.location = "${ctx}/identity/popedom/Bindopertion.jspx?code=${module.code}&id=${role.id}&name=${module.name}&codes="+array;
	})
	$("input[id^='box_']").click(function(){
		
			
			 var boxes  = $("input[id^='box_']:checked")
	         var array = new Array();
	           $.each(boxes,function(){
	        	 array.push(this.value);
	        	
	     })	
	     window.location = "${ctx}/identity/popedom/Bindopertion.jspx?code=${module.code}&id=${role.id}&name=${module.name}&codes="+array;
			
			
	
	})
	
})



</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<div>
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding: 5px;">
					<a  id="back" class="btn btn-primary"><span class="glyphicon glyphicon-hand-left"></span>&nbsp;返回</a>
				    <a  id="bindPopedom" class="btn btn-success"><span class="glyphicon glyphicon-copy"></span>&nbsp;绑定权限</a>
					<button type="button" disabled="disabled" class="btn btn-default">
  							<span  class="glyphicon glyphicon-user" aria-hidden="true"></span> <span style="font-style: italic;">当前角色: ${role.name }</span>
				    </button>
			</div>
			
			<div class="panel-heading" style="background-color: #11a9e2;">
				<h3 class="panel-title"><label style="color: white;font-size: 15px;"><span class="glyphicon glyphicon-yen"></span>&nbsp;${module.name.replaceAll('-','')}</label></h3>
			</div>
			
			<div class="panel-body" id="table" style="overflow:scroll;overflow-x:hidden;"  >
				<table class="table table-bordered" >
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input type="checkbox" id="checkAll"/></th>
							<th style="text-align: center;">编号</th>
							<th style="text-align: center;">名称</th>
<!-- 							<th>备注</th> -->
							<th style="text-align: center;">链接</th>
							<th style="text-align: center;">创建日期</th>
							<th style="text-align: center;">创建人</th>
<!-- 							<th>修改日期</th> -->
							<th style="text-align: center;">修改人</th>
						</tr>
					</thead>
					  <c:forEach items="${modules}" var="module" varStatus="stat">
				        <tr align="center" id="dataTr_${stat.index}">
							<td><input type="checkbox" name="box" id="box_${stat.index}" value="${module.code}"/></td>
							<td>${module.code}</td>
							<td>${module.name.replaceAll("-","")}</td>
<%-- 							<td>${module.remark}</td> --%>
							<td>${module.url}</td>
							<td><fmt:formatDate value="${module.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${module.creater.name }</td>
<%-- 							<td><fmt:formatDate value="${module.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
							<td>${module.modifier.name }</td>
						</tr>
				    </c:forEach>
				</table>
			</div>
		</div>
	</div>
		<div id="divDialog" style="display: none;" >
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
	
</body>
</html>
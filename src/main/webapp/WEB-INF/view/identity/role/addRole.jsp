<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>办公管理系统-添加用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta name="Keywords" content="keyword1,keyword2,keyword3" />
<meta name="Description" content="网页信息的描述" />
<meta name="Author" content="fkjava.org" />
<meta name="Copyright" content="All Rights Reserved." />
<link href="${ctx}/fkjava.ico" rel="shortcut icon" type="image/x-icon" />
<link rel="stylesheet"
	href="${ctx }/resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
<!-- 导入bootStrap的库 -->
<script type="text/javascript"
	src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
<script type="text/javascript">
	$(function(){
		
		$("#btn_submit").click(function(){
			var name = $("#name").val();
			var remark = $("#remark").val();
			var msg = "" ;
			if(!/^\S{1,30}$/.test($.trim(name))){
				msg = "请输入角色的名称";
			}else if(!/^\S{1,}$/.test($.trim(remark))){
				msg = "请输入角色备注";
			}
			
		
		})
	})
	
    
   function checkRolename() {
  				//json,charset=UTF-8
  	    	  var roleId=$("input[id^='name']").val();
  				//alert(userId)
  				if(roleId!==null && roleId !==""){
  					 $.ajax({
  			 			 type:"post",
  			 			 data:{"roleId":roleId},
  			 			 dataType:"text",
  			 			 url:"${ctx}/identity/role/checkRole.jspx",
  			 			 success:function(obj){				
  			 				if(obj=="success"){
  			 					
  			 					$.messager.show({
  			 						
  			 						title:'提示信息',
  			 						msg:"该角色可注册",
  			 						showType:"show"
  			 					});
  			 				
  			 				}
  			 				
  			 				if(obj=="error"){
  			 					$.messager.show({
  			 						
  			 						title:'提示信息',
  			 						msg:"该角色已经注册请换一个",
  			 						showType:"show"
  			 					});
  			 					
  			 				$("input[id^='name']").val("");
  			 				}
  			 			 },error:function(){
  			 				 $.messager.alert('提示信息',"网络异常",'error');
  			 			 }
  			 		 
  			 		 })	
  				}
	        }
	$(function(){
		
		$("#btn_submit").click(function(){
			var name=$("#name").val();
			var remack=$("#remack").val();
			var msg="";
			if(!/^\S{1,30}$/.test($.trim(name))){
				
				msg+="请输入角色名称";
			}else if(!/^\S{1,}$/.test($.trim(name))){
				msg+="请输入备注";
			}
			
			if(msg){
				
				$.messager.alert("提示信息",msg,"warning");
			}else{
				$("#addRoleForm").submit();
			}
			
		})
		
		if("${message}"){

			parent.$.messager.show({
				title:'提示信息',
				msg:"<font color='red'>${requestScope.message}</font>",
				showType:'show'
			});
			
			
		}
	})
	
</script>
</head>
<body style="background: #F5FAFA;">
	<center>
		<form id="addRoleForm" action="${ctx}/identity/role/addRole.jspx"
			method="post" style="padding: 10px;">
			<input type="hidden" value="${parentCode }" name="parentCode" />
			<table class="table-condensed" width="90%" height="100%">
				<tbody>
					<tr>
						<td align="center"><label>角色名称:<label></td>
						<td><input type="text" id="name" name="name"
							value="${role.name}" class="form-control" placeholder="请输入您的角色名称" onblur="checkRolename();"></td>
					</tr>
					<tr>
						<td align="center"><label>备注:<label></td>
						<td><textarea type="text" id="remark" name="remark"
							class="form-control" placeholder="请输入您的备注信息">${role.remark}</textarea></td>
					</tr>
			</table>
			<div align="center" style="margin-top: 20px;">
				<a id="btn_submit" class="btn btn-info"><span
					class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<button type="reset" class="btn btn-danger">
					<span class="glyphicon glyphicon-remove"></span>&nbsp;重置
				</button>
			</div>
		</form>

	</center>
</body>
</html>

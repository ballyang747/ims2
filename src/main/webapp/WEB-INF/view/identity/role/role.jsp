<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
     <%@ taglib uri="ims/kingson" prefix="ks"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-角色管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
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
	
<!-- 引入分页样式 -->
<link rel="stylesheet"
	href="${ctx }/css/pager.css" />
	<script type="text/javascript">
	 $(function(){
		
		 $("#addRole").click(function(){  
		    	
	    	  $('#divDialog').dialog({   
	    		    title: '添加用户',   
	    		    width: 600,   
	    		    height: 450, 
	    		    cls : "easyui-dialog",
	    		    closed: false,   
	    		    cache: false,   
	    		    maximizable : true, // 最大化
					minimizable : true, // 最小化
					collapsible : true, // 可伸缩
	    		    modal: true ,
	    		    onClose:function(){
	    		    	window.location = "${ctx}/identity/role/selectRole.jspx?pageIndex=${pageModel.pageIndex}";
	      		    }
	    		});  
	    	  $("#iframe").prop("src","${ctx}/identity/role/showAddRole.jspx");
	      })
		 	 
	       $("#deleteRole").click(function(){

				    //获取选中的checkbox
				   //var boxes  = $("input[type='checkbox'][name='box']:checked");
				   var boxes  = $("input[type='checkbox'][name='box']").filter(":checked");
				  
	               if(boxes.length == 0){
	            	   $.messager.alert('提示信息',"请选择需要删除的角色信息！",'warning');
	               }else{
	            	   
	            	   //提示用户是否确认删除该记录            	   
	            	  $.messager.confirm('提示信息', "是否确认删除？", function(r){
	      				if (r){
	      				//定义数组用于存储需要删除的用户的id
	                 	   var array = new Array();
	                 	   $.each(boxes,function(){
	                 		   //将用户的账号存放在数组中
	                 		   array.push(this.value);
                                   alert(this.value)
	                 		   window.location = "${ctx}/identity/role/deleteRole.jspx?roleIds="+array.join(",")+"&pageIndex=${pageModel.pageIndex}";
	                 	   })

	      				}
	      			 });
	            	 
	               }			  
			  })
	      
	     	 })
	 	   function updateRole(roleId){
			 $('#divDialog').dialog({   
	    		    title: '修改用户',   
	    		    width: 600,   
	    		    height: 450, 
	    		    cls : "easyui-dialog",
	    		    closed: false,   
	    		    cache: false,   
	    		    maximizable : true, // 最大化
					minimizable : true, // 最小化
					collapsible : true, // 可伸缩
	    		    modal: true ,
	    		    onClose:function(){
	    		    	window.location = "${ctx}/identity/role/selectRole.jspx?pageIndex=${pageModel.pageIndex}";
	      		    }
	    		});  
	    	  $("#iframe").prop("src","${ctx}/identity/role/showupdateRole.jspx?roleId="+roleId);
	      
		 }

	</script>
</head>
<body style="overflow: hidden;width: 100%;height: 100%;padding: 5px;">
	<div>
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding-top: 4px;padding-bottom: 4px;">
				<a  id="addRole" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<a  id="deleteRole" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
			</div>
			
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
					    <tr>
						<th style="text-align: center;"><input type="checkbox" id="checkAll"/></th>
						<th style="text-align: center;">名称</th>
						<th style="text-align: center;">备注</th>
						<th style="text-align: center;">操作</th>
						<th style="text-align: center;">创建日期</th>
						<th style="text-align: center;">创建人</th>
						<th style="text-align: center;">修改日期</th>
						<th style="text-align: center;">修改人</th>
						<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					<c:forEach items="${roles}" var="role" varStatus="stat">
				         <tr align="center" id="dataTr_${stat.index}">
							<td><input type="checkbox" name="box" id="box_${stat.index}" value="${role.id}"/></td>
							<td>${role.name}</td>
							<td>${role.remark}</td>
							<td><span class="label label-success"><a href="${ctx}/identity/role/selectRoleUser.jspx?id=${role.id}" style="color: white;">绑定用户</a></span>&nbsp;
								<span class="label label-info"><a href="${ctx}/identity/popedom/mgrPopedom.jspx?id=${role.id}" style="color: white;">绑定操作</a></span></td>
							<td><fmt:formatDate value="${role.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${role.creater.name}</td>
							<td><fmt:formatDate value="${role.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${role.modifier.name}</td>
							<td>   <span class="label label-info"><a href="javascript:updateRole('${role.id}')">修改</a></span></td>
						</tr>
		   			 </c:forEach>
				</table>
				<!-- 分页标签区 -->
				
			</div>
			<ks:pager pageIndex="${ pageModel.pageIndex}" pageSize="${pageModel.pageSize}" totalNum="${pageModel.recordCount}" submitUrl="${ctx}/identity/role/selectRole.jspx?pageIndex={0}" pageStyle="badoo"></ks:pager>
			<!-- ${ctx}/identity/role/selectRole.jspx?pageIndex={0} -->
		</div>
	</div>
    <!-- div作为弹出窗口 -->
  
		<div id="divDialog" style="display: none;" >
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
     <%@ taglib uri="ims/kingson" prefix="ks"  %>
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
<link rel="stylesheet"
	href="${ctx }/css/pager.css" />

<script type="text/javascript">
  $(function(){
	  
	  if("${message}"){
			$.messager.show({
				title:'提示信息',
				msg:"<font color='red'>${message}</font>",
				showType:'show'
			});
			
			
			//将dTree刷新
			parent.referchDtree();
	  }
	  
	  $("#addModule").click(function(){
		  $("#divDialog").dialog({
				title : "添加模块", // 标题
				cls : "easyui-dialog", // class
				width : 580, // 宽度
				height : 355, // 高度
				maximizable : true, // 最大化
				minimizable : false, // 最小化
				collapsible : true, // 可伸缩
				modal : true, // 模态窗口
				onClose : function(){ // 关闭窗口
					//重新查询角色列表信息
					window.location = "${ctx}/identity/module/getModulesByParent.jspx?pageIndex=${pageModel.pageIndex}&pCode=${pCode}";
				}
			});
			
			$("#iframe").prop("src","${ctx}/identity/module/showAddModule.jspx?pCode=${pCode}");
	  })
	 
	            
          $("#deleteModule").click(function(){
        	  
        	  var boxes=$("input[type='checkbox'][name='box']").filter(":checked");
        	  if(boxes.length==0){
        		  $.messager.alert('提示信息',"请选择需要删除的用户信息！",'warning');
        	  }else{
        		  
        		 $.messager.confirm('提示信息','是否确认删除',function(r){
        			 if(r){
           			  var array = new Array();
           			  $.each(boxes,function(){
           				  
           				  array.push(this.value);
           				  window.location="${ctx}/identity/module/deleteModule.jspx?codes="+array.join(",")+"&pageIndex=${pageModel.pageIndex}&pCode=${pCode}";
        			 
        		 })
        		 
        		  
        	  }
        		 
          })
        	     }			  
		  })
		  
	  
		//"src","${ctx}/identity/module/showAddModule.jspx?pCode=${pCode}
			  //${ctx}/identity/module/getModulesByParent.jspx?pageIndex=${pageModel.pageIndex}&pCode=${pCode}
	 
	  
  })
  
  function updateModule(code){
	  $("#divDialog").dialog({
			title : "修改模块", // 标题
			cls : "easyui-dialog", // class
			width : 580, // 宽度
			height : 355, // 高度
			maximizable : true, // 最大化
			minimizable : false, // 最小化
			collapsible : true, // 可伸缩
			modal : true, // 模态窗口
			onClose : function(){ // 关闭窗口
				//重新查询角色列表信息
				window.location = "${ctx}/identity/module/getModulesByParent.jspx?pageIndex=${pageModel.pageIndex}&pCode=${pCode}";
			}
		});
		
		$("#iframe").prop("src","${ctx}/identity/module/showUpdateModuleByCode.jspx?code="+code);   
		  
	  
	  
  }
</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<div>
	
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding: 5px;">
				<a  id="addModule" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<a  id="deleteModule" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
			</div>
			
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input type="checkbox" id="checkAll"/></th>
							<th style="text-align: center;">编号</th>
							<th style="text-align: center;">名称</th>
<!-- 							<th>备注</th> -->
							<th style="text-align: center;">链接</th>
							<th style="text-align: center;">操作</th>
<!-- 							<th style="text-align: center;">创建日期</th> -->
<!-- 							<th style="text-align: center;">创建人</th> -->
<!-- 							<th>修改日期</th> -->
							<th style="text-align: center;">修改人</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					  <c:forEach items="${modules}" var="module" varStatus="stat">
				        <tr align="center" id="dataTr_${stat.index}">
							<td><input type="checkbox" name="box" id="box_${stat.index}" value="${module.code}"/></td>
							<td>${module.code}</td>
							<td>${module.name.replaceAll("-","")}</td>
<%-- 							<td>${module.remark}</td> --%>
							<td>${module.url}</td>
							<td><span class="label label-success"><a href="${ctx}/identity/module/getModulesByParent.jspx?pCode=${module.code}" style="color: white;" id="name">查看下级</a></span></td>
<%-- 							<td><fmt:formatDate value="${module.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
<%-- 							<td>${module.creater.name }</td> --%>
<%-- 							<td><fmt:formatDate value="${module.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
							<td>${module.modifier.name }</td>
							<td><span class="label label-info"><a href="javascript:updateModule('${module.code}');" style="color: white;">修改</a></span></td>
						</tr>
				    </c:forEach>
				</table>
					<!-- 分页标签区 -->
				
			</div>
			<ks:pager pageIndex="${ pageModel.pageIndex}" pageSize="${pageModel.pageSize}" totalNum="${pageModel.recordCount}" submitUrl="${ctx}/identity/module/getModulesByParent.jspx?pageIndex={0}&pCode=${pCode}" pageStyle="badoo"></ks:pager>
		</div>
	</div>
		<div id="divDialog" style="display: none;" >
			 <!-- 放置一个添加用户的界面  -->
			  <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
	
</body>
</html>
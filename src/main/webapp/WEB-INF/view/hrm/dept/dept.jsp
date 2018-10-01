<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

$(function(){
	
	// 如果有提示就弹出来 
	if("${message}"){
		$.messager.show({
			title:'操作提示',
			msg:"<font color='red'>${message}</font>",
			timeout:2000,
			showType:'show'
		});
	}
	
	
	
	/** 添加模块绑定点击事件  */
	$("#addDept").click(function(){
	
		$("#divDialog").dialog({
			title : "添加部门", // 标题
			cls : "easyui-dialog", // class
			width : 480, // 宽度
			height : 310, // 高度
			maximizable : true, // 最大化
			minimizable : false, // 最小化
			collapsible : true, // 可伸缩
			modal : true, // 模态窗口
			onClose : function(){ // 关闭窗口
				window.location = "${ctx}/hrm/dept/dept.jspx?pageIndex=${pageModel.pageIndex}";
			}
		});
		/** div弹出以后，立即加载界面，并且显示界面  */
	     $("#iframe").attr("src", "${ctx}/hrm/dept/showAddDept.jspx").show();
	})
	
	  $("#deleteDept").click(function(){

			    //获取选中的checkbox
			   //var boxes  = $("input[type='checkbox'][name='box']:checked");
			   var boxes  = $("input[type='checkbox'][name='box']").filter(":checked");
			  
               if(boxes.length == 0){
            	   $.messager.alert('提示信息',"请选择需要删除的用户信息！",'warning');
               }else{
            	   
            	   //提示用户是否确认删除该记录            	   
            	  $.messager.confirm('提示信息', "是否确认删除？", function(r){
      				if (r){
      				//定义数组用于存储需要删除的用户的id
                 	   var array = new Array();
                 	   $.each(boxes,function(){
                 		   //将用户的账号存放在数组中
                 		   array.push(this.value);

                 		   window.location =  "${ctx}/hrm/dept/deleteDept.jspx?pageIndex=${pageModel.pageIndex}&ids="+array;
                 	   })

      				}
      			 });
            	 
               }			  
		  })
	//window.location = "${ctx}/hrm/dept/deleteDept.jspx?pageIndex=${pageModel.pageIndex}&ids="+deptIds.get();
		
});

    //修改部门信息
    function updateDept(id){

    	/** 获取当前选中的数据行数 */
			$("#divDialog").dialog({
				title : "修改部门", // 标题
				cls : "easyui-dialog", // class
				width : 480, // 宽度
				height : 310, // 高度
				maximizable : true, // 最大化
				minimizable : false, // 最小化
				collapsible : true, // 可伸缩
				modal : true, // 模态窗口
				onClose : function(){ // 关闭窗口
					window.location = "${ctx}/hrm/dept/dept.jspx?pageIndex=${pageModel.pageIndex}";
				}
			});
			/** div弹出以后，立即加载界面，并且显示界面  */
		     $("#iframe").attr("src", "${ctx}/hrm/dept/showUpdateDept.jspx?id="+id);
		
    }
</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<div>
	
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding: 5px;">
				<a  id="addDept" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<a  id="deleteDept" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
			</div>
			
			
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input  id="checkAll" type="checkbox" /></th>
							<th style="text-align: center;">部门名称</th>
							<th style="text-align: center;">创建者</th>
							<th style="text-align: center;">创建时间</th>
							<th style="text-align: center;">修改者</th>
							<th style="text-align: center;">修改时间</th>
							<th style="text-align: center;">备注</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					  
                  <c:forEach items="${depts}" var="dept" varStatus="stat">
	                    <tr id="dataTr_${stat.index}" align="center">
						<td><input type="checkbox" name="box" id="box_${stat.index}" value="${dept.id}"/></td>
						<td>${dept.name}</td>
						<td>${dept.creater.name}</td>
						<td><fmt:formatDate value="${dept.createDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
						<td>${dept.modifier.name}</td>
						<td><fmt:formatDate value="${dept.modifyDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>					
						<td>${dept.remark}</td>
						<td><span class="label label-info"><a href="#" onclick="updateDept('${dept.id}')"  style="color: white;">修改</a></span>
						</td>
					   </tr>
                 </c:forEach>	
          
				</table>
			
			</div>
			<!-- 分页标签 -->
			<ks:pager pageIndex="${ pageModel.pageIndex}" pageSize="${pageModel.pageSize}" totalNum="${pageModel.recordCount}" submitUrl="${ctx}/hrm/dept/dept.jspx?pageIndex={0}&name=${dept.name }" pageStyle="badoo"></ks:pager>
		</div>
	</div>
    <!-- div作为弹出窗口 -->
    <div id="divDialog" style="overflow: hidden;">
		<iframe id="iframe" scrolling="no" frameborder="0" width="100%" height="100%" ></iframe>
	</div>
	
</body>
</html>
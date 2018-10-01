<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
     <%@ taglib uri="ims/kingson" prefix="ks" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OA办公管理系统-日记类型列表</title>
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
		}
	
	/** 添加模块绑定点击事件  */
	$("#addNoticeType").click(function(){
	
		$("#divDialog").dialog({
			title : "添加日记", // 标题
			cls : "easyui-dialog", // class
			width : 600, // 宽度
			height : 310, // 高度
			maximizable : true, // 最大化
			minimizable : false, // 最小化
			collapsible : true, // 可伸缩
			modal : true, // 模态窗口
			onClose : function(){ // 关闭窗口
				window.location = "${ctx}/log/logMenager/selecWorkLog.jspx?pageIndex=${pageModel.pageIndex}";
			}
		}); 
		
		/** div弹出以后，立即加载界面，并且显示界面  */
 	     $("#iframe").attr("src", "${ctx}/log/logMenager/showAddLogType.jspx").show();
 

	   })
	   //删除日记
	   $("#deleteDiary").click(function(){
     	  
     	  var boxes=$("input[type='checkbox'][name='box']").filter(":checked");
     	  if(boxes.length==0){
     		  $.messager.alert('提示信息',"请选择需要删除的用户信息！",'warning');
     	  }else{
     		  
     		 $.messager.confirm('提示信息','是否确认删除',function(r){
     			 if(r){
        			  var array = new Array();
        			  $.each(boxes,function(){
        				  
        				  array.push(this.value);
        				  window.location="${ctx}/log/logMenager/deleteDiary.jspx?pageIndex=${pageModel.pageIndex}&ids="+array;
        			 
     			 
     		 })
     		 
     		  
     	  }
     		 
       })
           }			  
		  })
		  
	
   })
   //修改日记
   function updateNoticeType(id){
	
	$("#divDialog").dialog({
		title : "修改日记", // 标题
		cls : "easyui-dialog", // class
		width : 600, // 宽度
		height : 310, // 高度
		maximizable : true, // 最大化
		minimizable : false, // 最小化
		collapsible : true, // 可伸缩
		modal : true, // 模态窗口
		onClose : function(){ // 关闭窗口
			window.location = "${ctx}/log/logMenager/selecWorkLog.jspx?pageIndex=${pageModel.pageIndex}";
		}
	}); 
	
	/** div弹出以后，立即加载界面，并且显示界面  */
	     $("#iframe").attr("src", "${ctx}/log/logMenager/updateDiary.jspx?id="+id).show();


	
    }    
    function preDiary(id){
    	$("#divDialog").dialog({
    		title : "预览日记", // 标题
    		cls : "easyui-dialog", // class
    		width : 900, // 宽度
    		height : 450, // 高度
    		maximizable : true, // 最大化
    		minimizable : false, // 最小化
    		collapsible : true, // 可伸缩
    		modal : true, // 模态窗口
    		onClose : function(){ // 关闭窗口
    			window.location = "${ctx}/log/logMenager/selecWorkLog.jspx?pageIndex=${pageModel.pageIndex}";
    		}
    	}); 
    	
    	/** div弹出以后，立即加载界面，并且显示界面  */
    	     $("#iframe").attr("src", "${ctx}/log/logMenager/preDiary.jspx?id="+id).show();


    	
    }
</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<div>
	
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<!-- <div style="padding: 5px;">
				<a  id="addEmp" class="btn btn-info">添加</a>
				<a  id="deleteEmp" class="btn btn-danger">删除</a>
				<a  id="updateEmp" class="btn btn-danger">修改</a>
			</div> -->
				<!-- 工具按钮区 -->
		<form class="form-horizontal"
			action="${ctx}/log/logMenager/selecWorkLog.jspx" method="post"
			style="margin-top: 5px;">
			<div class="form-group">
				<div class="col-sm-2">
					<input name="title" type="text" value="${diary.title}" class="form-control" placeholder="日记主题">
				</div>

				<div class="col-sm-3">
					<button type="submit" id="selectDiary" class="btn btn-info">查询</button>
					<a id="addNoticeType" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
					 <a id="deleteDiary"
						class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
				</div>
			</div>

		</form>
		
		
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input  id="checkAll" type="checkbox" /></th>
							<th style="text-align: center;">日记主题</th>
							<th style="text-align: center;">日记内容</th>
							<th style="text-align: center;">创建者</th>
							<th style="text-align: center;">创建时间</th>
							<th style="text-align: center;">修改者</th>
							<th style="text-align: center;">修改时间</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					  
                  <c:forEach items="${diarys}" var="diarys" varStatus="stat">
                    <tr id="dataTr_${stat.index}" align="center">
					<td><input type="checkbox" name="box" id="box_${stat.index}" value="${diarys.id}"/></td>
					<td>${diarys.title}</td>
					<td>${diarys.content}</td>
					<td>${diarys.creater.name}</td>
					<td><fmt:formatDate value="${diarys.createDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
					<td>${diarys.modifier.name}</td>
					<td><fmt:formatDate value="${diarys.modifyDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
                    <td>
                       	<span class="label label-info"><a href="#" onclick="updateNoticeType('${diarys.id}')"  style="color: white;">修改</a></span>
				  <span id="preDiary_${stat.index}" class="label label-success"><a href="javascript:preDiary('${diarys.id}')"  style="color: white;">预览</a></span>
				   </tr>
          
                 </c:forEach>	
          
				</table>
					<!-- 分页标签区 -->
				
			</div>
			<ks:pager pageIndex="${ pageModel.pageIndex}" pageSize="${pageModel.pageSize}" totalNum="${pageModel.recordCount}" submitUrl="${ctx}/log/logMenager/selecWorkLog.jspx?pageIndex={0}" pageStyle="badoo"></ks:pager>
		</div>
	</div>
    <!-- div作为弹出窗口 -->
    <div id="divDialog" style="overflow: hidden;">
		<iframe id="iframe" scrolling="no" frameborder="0" width="100%" height="100%"></iframe>
	</div>
	
</body>
</html>
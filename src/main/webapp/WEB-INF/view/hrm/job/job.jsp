<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript">

$(function(){
	
	// 如果有提示就弹出来 
	if("${tip}"){
		$.messager.show({
			title:'操作提示',
			msg:"<font color='red'>${tip}</font>",
			timeout:2000,
			showType:'show'
		});
	}
	
	
	/** 获取所有的数据选项 */
	var dataBoxs = $("input[name='box'][id^='box_']");
	/** 给全选按钮绑定点击事件 */
	$("#checkAll").click(function(){
		dataBoxs.attr("checked",this.checked);
		$("tr[id^='dataTr_']").trigger(this.checked?"mouseover":"mouseout");
	})
	
	/** 如果数据按钮都选中全选也选中 ， 反之   */
	dataBoxs.click(function(event){
		// 阻止事件的传播，只点击点选就结束
		event.stopPropagation();
		/** 获取当前选中的数据行数 */
		var checkedBoxs = dataBoxs.filter(":checked");
		/** 判断选中的数据行是否等于总的数据行，如果是全选选中，如果不是全选去选中*/
		$("#checkAll").attr("checked",checkedBoxs.length == dataBoxs.length);
	})
	
	/** 给数据行绑定点击事件：通过点击行来控制数据的选中与不选中 */
	$("tr[id^='dataTr_']").click(function(event){
		
		// 获取点击当前行的数据选项id
		var boxId = this.id.replace("dataTr_","box_");
		/** 触发对应选项的点击*/
		$("#"+boxId).trigger("click");
	}).hover(function(){
		$(this).css({backgroundColor:"#cccccc",cursor : "pointer"});
	},function(){
		var boxId = this.id.replace("dataTr_","box_");
		if(!$("#"+boxId).attr("checked")){
			$(this).css("backgroundColor","#ffffff");
		}
	});
	
	/** 添加模块绑定点击事件  */
	$("#addJob").click(function(){
	
		$("#divDialog").dialog({
			title : "添加职位", // 标题
			cls : "easyui-dialog", // class
			width : 480, // 宽度
			height : 310, // 高度
			maximizable : true, // 最大化
			minimizable : false, // 最小化
			collapsible : true, // 可伸缩
			modal : true, // 模态窗口
			onClose : function(){ // 关闭窗口
				window.location = "${ctx}/hrm/job/selectJob.jspx?pageIndex=${pageModel.pageIndex}";
			}
		});
		/** div弹出以后，立即加载界面，并且显示界面  */
	     $("#iframe").attr("src", "${ctx}/hrm/job/showAddJob.jspx").show();
	})
	
	/** 给删除绑定点击事件 */
	$("#deleteJob").on("click",function(){
		/** 获取当前选中的数据行数 */
		var checkedBoxs = dataBoxs.filter(":checked");
		if(checkedBoxs.length > 0){
			/** 提示确认删除吗?*/
			$.messager.confirm('职位管理', '确认删除职位吗?', function(r){
				if (r){
				    /** 获取选中的数据行模块id */
					var idMaps = checkedBoxs.map(function(){
						return this.value;
					});
				    
				    /** 获取需要删除的模块的id */
				  //  alert(idMaps.get()); // admin,limin
				    window.location = "${ctx}/hrm/job/deleteJob.jspx?pageIndex=${pageModel.pageIndex}&codes="+idMaps.get();
				}
			});		
		}else{
			 $.messager.alert('错误提示',"请选择需要删除的职位!",'error');
		}
	})
	
});

   
      //修改职位信息
      function  updateJob(code){
    	  
    	  $("#divDialog").dialog({
				title : "修改职位", // 标题
				cls : "easyui-dialog", // class
				width : 480, // 宽度
				height : 310, // 高度
				maximizable : true, // 最大化
				minimizable : false, // 最小化
				collapsible : true, // 可伸缩
				modal : true, // 模态窗口
				onClose : function(){ // 关闭窗口
					window.location = "${ctx}/hrm/job/selectJob.jspx?pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}";
				}
			});
			/** div弹出以后，立即加载界面，并且显示界面  */
		     $("#iframe").attr("src", "${ctx}/hrm/job/showUpdateJob.jspx?code="+code).show();
      }

</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<div>
	
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding: 5px;">
				<a  id="addJob" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<a  id="deleteJob" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
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
					  
                  <c:forEach items="${requestScope.jobs}" var="job" varStatus="stat">
                    <tr id="dataTr_${stat.index}" align="center">
					<td><input type="checkbox" name="box" id="box_${stat.index}" value="${job.code}"/></td>
					<td>${job.name}</td>
					<td>${job.creater.name}</td>
					<td><fmt:formatDate value="${job.createDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
					<td>${job.modifier.name}</td>
					<td><fmt:formatDate value="${job.modifyDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>				
					<td>${job.remark}</td>
					<td>
					   <span class="label label-info"><a href="#" onclick="updateJob('${job.code}')" style="color: white;">修改</a></span>
					   </td>
				   </tr>
          
                 </c:forEach>	
          
				</table>
					<!-- 分页标签区 -->
				<fkjava:pager pageIndex="${pageModel.pageIndex}" 
				  pageSize="${pageModel.pageSize}" 
				  recordCount="${pageModel.recordCount}" 
				  submitUrl="${ctx}/hrm/job/selectJob.jspx?pageIndex={0}"/>
			</div>
			
		</div>
	</div>
    <!-- div作为弹出窗口 -->
    <div id="divDialog" style="overflow: hidden;">
		<iframe id="iframe" scrolling="no" frameborder="0" width="100%" height="100%" style="display:none;"></iframe>
	</div>
	
</body>
</html>
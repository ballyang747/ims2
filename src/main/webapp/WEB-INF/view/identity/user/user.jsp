<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
	<%@ include file="/WEB-INF/commonality/commons.jsp" %>
	<%@ taglib uri="ims/kingson" prefix="ks"  %>
<!DOCTYPE html >
<html>
<head>
<title>OA办公管理系统-用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />

<script type="text/javascript" src="${ctx}/resources/blockUI/jquery.blockUI.js"></script>
<script type="text/javascript" src="${ctx}/oajs/openpageCss.js"></script>
<!-- 引入分页样式 -->
<link rel="stylesheet"
	href="${ctx }/css/pager.css" />
<script type="text/javascript">
  /** 文档加载完成*/
  	 
    	
  
     $(function(){
    	 if("${message}"){
  			$.messager.show({
  				title:'提示信息',
  				msg:"<font color='red'>${message}</font>",
  				showType:'show'
  			});
  		}
    
	    	//$(document).ajaxStart($.blockUI({ css: { backgroundColor: '#11a9e2', color: '#fff' } , //message: '<h6>正在加载..</h6>'})).ajaxStop($.unblockUI);

			 /** 激活用户操作*/
    	  $("input[id^='checkUser_']").switchbutton({
              onChange: function(checked){
            	    var status = checked?"1":"0";
            	   
            		window.location = "${ctx}/identity/user/activeUser.jspx?userId="+this.value+"&status="+status
						+"&pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
              }
          });
			 
			 
            $.ajax({
            	type:"post",
            	dataType:"json",
            	url:"${ctx}/identity/user/ajaxLoadDeptAndJob.jspx",
            	success:function(obj){
            		    var depts= obj.depts;
            		    $.each(depts,function(){
            		    	$("<option>").val(this.id).text(this.name).appendTo("#deptSelect");
            		    }) 
            		    
            		    var jobs = obj.jobs;
            		     $.each(jobs,function(){
            		    	$("<option>").val(this.code).text(this.name).appendTo("#jobSelect");
            		    })
            		
            	},
            	error:function(){
            		
            		alert("网络异常");
            	}
            	
            	
            	
            })
            
            
                            
          $("#deleteUser").click(function(){
        	  
        	  var boxes=$("input[type='checkbox'][name='box']").filter(":checked");
        	  if(boxes.length==0){
        		  $.messager.alert('提示信息',"请选择需要删除的用户信息！",'warning');
        	  }else{
        		  
        		 $.messager.confirm('提示信息','是否确认删除',function(r){
        			 if(r){
           			  var array = new Array();
           			  $.each(boxes,function(){
           				  
           				  array.push(this.value);
           				  window.location="${ctx}/identity/user/deleteUser.jspx?userIds="+array.join(",")+"&pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
           			 
        			 
        		 })
        		 
        		  
        	  }
        		 
          })
              }			  
		  })
		  
          
      $("#addUser").click(function(){  
    	
    	  $('#divDialog').dialog({   
    		    title: '添加用户',   
    		    width: 600,   
    		    height: 450, 
    		    cls : "easyui-dialog",
    		    closed: false,   
    		    cache: true,   
    		    maximizable : true, // 最大化
				minimizable : true, // 最小化
				collapsible : true, // 可伸缩
    		    modal: true ,
    		    onClose:function(){
    		    	window.location = "${ctx}/identity/user/selectUser.jspx?pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
      		    }
    		});  
    	  $("#iframe").prop("src","${ctx}/identity/user/showAduUser.jspx");
      })
      
         /*  $("#updateuserId").click(function(userId){  
        	  
    	  $('#up').dialog({   
    		    title: '添加用户',   
    		    width: 600,   
    		    height: 450, 
    		    cls : "easyui-dialog",
    		    closed: false,   
    		    cache: true,   
    		    maximizable : true, // 最大化
				minimizable : true, // 最小化
				collapsible : true, // 可伸缩
    		    modal: true ,
    		    onClose:function(){
    		    	
      		    }
    		});  
    	  
    	  
    	  $("#update").prop("src","${ctx}/identity/user/updateUser.jspx?userId=userId");
      })    */
      
      	
      
      })
      
      function updateUser(userId){
    	 $('#up').dialog({   
 		    title: '修改用户',   
 		    width: 600,   
 		    height: 450, 
 		    cls : "easyui-dialog",
 		    closed: false,   
 		    cache: true,   
 		    maximizable : true, // 最大化
				minimizable : true, // 最小化
				collapsible : true, // 可伸缩
 		    modal: true ,
 		    //修改后执行
 		    onClose:function(){
 		    	
 		    	window.location = "${ctx}/identity/user/selectUser.jspx?pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
 		    	
   		    }
 		});  
 	  
 	  
 	  $("#update").prop("src","${ctx}/identity/user/updateUser.jspx?userId="+userId);	
      }
     function preUser(userId){
    	 $('#po').dialog({   
 		    title: '预览用户',   
 		    width: 1000,   
 		    height: 400, 
 		    cls : "easyui-dialog",
 		    closed: false,   
 		    cache: true,   
 		    maximizable : true, // 最大化
				minimizable : false, // 最小化
				collapsible : true, // 可伸缩
 		    modal: true ,
 		    //修改后执行
 		    onClose:function(){
 		    	
 		    	window.location = "${ctx}/identity/user/selectUser.jspx?pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
 		    	
   		    }
 		});  
 	  
 	  
 	  $("#lookuser").prop("src","${ctx}/identity/user/lookUser.jspx?userId="+userId);	
      }
  
</script>
</head>
<body style="overflow: hidden; width: 98%; height: 100%;" >
   	<!-- 工具按钮区 -->
	<form class="form-horizontal" 
			action="${ctx }/identity/user/selectUser.jspx" method="post" style="padding-left: 5px;" >
			<table class="table-condensed">
					<tbody>
					<tr>
					   <td>
						<input name="name" type="text" class="form-control"
							placeholder="姓名" value="${user.name}" >
						</td>
						<td>	
						<input type="text" name="phone" class="form-control"
							placeholder="手机号码" value="${user.phone}" >
						</td>
<!-- 						<td>	 -->
<!-- 						   <input type="text" class="form-control" placeholder="状态"> -->
<!-- 						</td> -->
						<td>	
						<select class="btn btn-default"
							placeholder="部门" id="deptSelect" name="dept.id">
							<option value="0">==请选择部门==</option>
						</select>
						</td>
						<td>	
						<select class="btn btn-default"
							placeholder="职位" id="jobSelect" name="job.code">
							<option value="0">==请选择职位==</option>
						</select>
						</td>
						<td>	
					<ks:pagerOperas value="user:selectUser">	<button type="submit" id="selectUser" class="btn btn-info"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></ks:pagerOperas>
						<ks:pagerOperas value="user:addUser"><a id="addUser" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a></ks:pagerOperas>
					 <ks:pagerOperas value="user:deleteUser">   <a  id="deleteUser" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a></ks:pagerOperas>
					 </td>
					</tr>
					</tbody>
				</table>
		</form>
 		<div class="panel panel-primary" style="padding-left: 10px;">
 			<div class="panel-heading" style="background-color: #11a9e2;">
				<h3 class="panel-title">用户信息列表</h3>
			</div>
			<div class="panel-body" >
				
				<table class="table table-bordered">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input id="checkAll"
								type="checkbox" /></th>
							<th style="text-align: center;">账户</th>
							<th style="text-align: center;">姓名</th>
							<th style="text-align: center;">性别</th>
							<th style="text-align: center;">部门</th>
							<th style="text-align: center;">职位</th>
							<th style="text-align: center;">手机号码</th>
							<th style="text-align: center;">邮箱</th>
							<th style="text-align: center;">激活状态</th>
							<th style="text-align: center;">审核人</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
							
						<c:forEach items="${users}" var="user" varStatus="stat">
						
						<tr id="dataTr_${stat.index}" align="center">
							<td><input type="checkbox" name="box" id="box_${stat.index}"
								value="${user.userId}" /></td>
							<td>${user.userId}</td>
							<td>${user.name}</td>
							<td>${user.sex == 1 ? '男' : '女' }</td>
							<td>${ user.dept.name}</td>
							<td>${ user.job.name}</td>
							<td>${user.phone}</td>
							<td>${user.email}</td>
							<td>
							 <ks:pagerOperas value="user:checkUser">
								<c:if test="${user.status == 1 }">
									<input id="checkUser_${user.userId }" value="${user.userId }" name="status" class="easyui-switchbutton" data-options="onText:'激活',offText:'冻结'" checked>
								</c:if>
								<c:if test="${user.status != 1 }">
									<input id="checkUser_${user.userId }" value="${user.userId }" name="status" class="easyui-switchbutton" data-options="onText:'激活',offText:'冻结'" >
								</c:if>
								</ks:pagerOperas>
						    </td>
								<td>${user.checker.name}</td>
							<td>
							<ks:pagerOperas value="user:updateUser">   <span id="updateUser_${stat.index}"  class="label label-info"><a id="updateuserId" href="javascript:updateUser('${user.userId}')" name="${user.userId}" uestyle="color: white;">修改</a></span></ks:pagerOperas>
							   <span id="preUser_${stat.index}" class="label label-success"><a href="javascript:preUser('${user.userId}')"  style="color: white;">预览</a></span>
							
							</td>
							
						</tr>
						
						
						</c:forEach>
						
						
						
						
				</table>
				
			</div>
                 <!-- 分页标签区 -->
		<ks:pager pageIndex="${ pageModel.pageIndex}" pageSize="${pageModel.pageSize}" totalNum="${pageModel.recordCount}" submitUrl="${ctx}/identity/user/selectUser.jspx?pageIndex={0}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}" pageStyle="badoo"></ks:pager>
		</div>
		
		<div id="divDialog" style="display: none;" >
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
	    <div id="up" style="display: none;" >
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="update" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
		<div id="po" style="display: none;" >
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="lookuser" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
</body>
</html>
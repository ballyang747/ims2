<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-查看员工</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
		<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="Description" content="网页信息的描述" />
		<meta name="Author" content="fkjava.org" />
		<meta name="Copyright" content="All Rights Reserved." />
   <link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
	src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">

 <!-- 导入dwr相关js文件 -->
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/interface/fileUpload.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/util.js'></script>

 <!-- 引入My97 时间控件-->
    <script type='text/javascript' src='${ctx}/resources/My97DatePicker/WdatePicker.js'></script>


		<script type="text/javascript">
			$(function(){
				
				if("${sex==1}"){
					$("#inlineCheckbox1").attr("checked",true);
				}else{
					$("#inlineCheckbox2").attr("checked",true);
				}
				
				var date = "<fmt:formatDate value='${employee.birthday}' pattern='yyyy/MM/dd'/>";
                $("#birthday").val(date);
				
				/** 加载部门与职位 */
				/** 异步加载部门信息与职位信息  */
				  $.ajax({
				  url : "${ctx}/identity/dept/loadDeptsAndJobsAjax.jspx",
				  dataType : "json",
				  type :"post",
				  async : true,
				  success : function(data){
					 
					  // {dept[{},{},{}] , jobs :[{},{},{}]}
					  var depts = data.depts;
					  var jobs = data.jobs;
					 
					  $.each(depts , function(){
						  $("<option/>").val(this.code).html(this.name).attr("selected",this.code=="${user_session.dept.id}").appendTo("#deptSelect");
					  })
					  
					   $.each(jobs , function(){
						 $("<option/>").val(this.code).html(this.name).attr("selected",this.code=="${user_session.job.code}").appendTo("#jobSelect");
					   })
					  
				  },
				  error : function(){
					  
				  }
			  });
			});
			
			//头像上传  
			function changeFn(obj){
				
				fileUpload.picUpload(obj,function(path){
		         $("#logoImage").attr("src","${ctx}/"+path);
		         
		         //将头像路径存放在表单中
		         //$("#picture").attr("value",path);
		         $("#picture").val(path);
					  
				})	
			}
		</script>
	</head>
<body>
		
	 	<div class="container">
			<form id="updateEmpForm" style="padding: 30px;" class="form-horizontal" action="${ctx}/hrm/employee/updateEmp.jspx" method="post">
			<input type="hidden" value="${employee.id}" name="id"/>
			<input type="hidden" name="picture" value="${employee.picture}" id="picture"/>
			
			<div class="form-group col-xs-6">
		        <div>
		            <img src="${ctx}${employee.picture}" width="139" height="147" id="logoImage"/>		     
		              <div style="width:70px;height:17px;background-image:url('${ctx}/images/bt_file.jpg');overflow:hidden;">
		                 <input type='file' id="image" onchange="changeFn(this);"  style="filter:alpha(opacity=0);opacity:0.0;width:70px;height:17px;"/>
		              </div>
		        </div>
		    </div>
			<div class="form-group col-xs-6">
		        <div class="col-sm-10">
		            <input type="text" class="form-control" name="name" required  value="${employee.name}" id="inputName" placeholder="请输入员工姓名">
		        </div>
		    </div>
		    
		    <div class="form-group col-xs-6">
		        <div class="col-sm-10">
		            <input type="text" class="form-control" name="cardId" required  value="${employee.cardId}" id="inputCardId" placeholder="请输入身份证号">		            
		        </div>
		    </div>
		    
		    
			<div class="form-group col-xs-6">
		        <div class="col-sm-10">
		             <label class="checkbox-inline">
					    <input type="radio" name="sex"  id="inlineCheckbox1" value="1">男
					  </label>
					  <label class="checkbox-inline">
					    <input type="radio" name="sex" id="inlineCheckbox2" value="2">女
					 </label>
		        </div>
		    </div>
		    
		    <div class="form-group col-xs-6">
		        <div class="col-sm-10">
		            <input type="text" class="form-control" name="tel" required value="${employee.tel}" id="inputTel" placeholder="请输入电话信息">
		        </div>
		    </div>
			<div class="form-group col-xs-6">
		        <div class="col-sm-10">
		            <input type="email" class="form-control" name="email" required value="${employee.email}" id="inputEmail" placeholder="请输入您的邮箱地址">
		        </div>
		    </div>
		    
		    <div class="form-group col-xs-6">
		        <div class="col-sm-10">
		        <input type="text" class="form-control" name="birthday" required  placeholder="请输入您的出生日期" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy/MM/dd'});" 
					name="employee.birthday" id="birthday">
		        
		        </div>
		    </div>
			<div class="form-group col-xs-6">
		        <div class="col-sm-12">
		            <select name="job.code" class="btn btn-default"
						 id="jobSelect">
							
						</select>
		        </div>
		    </div>
		    
		    <div class="form-group col-xs-6">
		        <div class="col-sm-12">
		            <select name="dept.id" class="btn btn-default" id="deptSelect"></select>
		        </div>
		    </div>
			


            <div class="form-group col-xs-12 text-center">
		        <div class="col-sm-12">
			          <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		        </div>
		    </div>
		
			</form>
		</div>
</body>
</html>	
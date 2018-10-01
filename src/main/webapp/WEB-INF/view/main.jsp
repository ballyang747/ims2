<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
    
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
     <c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
  
   
    
    
<!DOCTYPE html> 
<head> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
    <title>kingson软件--智能办公</title> 
 
 <link href="${ctx}/css/base.css" rel="stylesheet">
<link href="${ctx}/css/platform.css" rel="stylesheet">
  
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
<script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
 <!-- <script type="text/javascript" src="js/menu.js"></script> -->

<script type="text/javascript" src="${ctx}/oajs/main.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tt').tabs({
		    	  tabHeight: 40,
			      onSelect:function(title,index){
			        var currentTab = $('#tt').tabs("getSelected");
			        if(currentTab.find("iframe") && currentTab.find("iframe").size()){
			            currentTab.find("iframe").attr("src",currentTab.find("iframe").attr("src"));
			        }
			      }
		 });
	})

     // 写一个方法往easyUI中添加面板 
	function addPanel(id,url,name){
    	name = name.replace(/-/g,"");
    	// 判断之前是否已经存在该面板存在就不创建新的面板 
    	var exist = $('#tt').tabs('exists',name);
    	if(exist){
    		// 已经存在就将该面板选中  exist
    		$('#tt').tabs('select',name);
    		var currentTab =  $('#tt').tabs('getTab',name);
    		// 刷新一下界面 
    		if(currentTab.find("iframe") && currentTab.find("iframe").size()){
	            currentTab.find("iframe").attr("src",currentTab.find("iframe").attr("src"));
	        }
    	}else{
    		$('#tt').tabs('add',{
    			id:id,
    			title: name,
    			content: '<div style="width:100%;height:100%;"><iframe class="page-iframe" src="${ctx}'+url+'" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe></div>',
    			closable: true
    		});
    	}
	}
	   
	    $(window).resize(function(){
	          $('.tabs-panels').height($("#pf-page").height()-46);
	          $('.panel-body').height($("#pf-page").height()-76)
	    }).resize();

	    var page = 0,
	        pages = ($('.pf-nav').height() / 70) - 1;

	    if(pages === 0){
	      $('.pf-nav-prev,.pf-nav-next').hide();
	    }
	    $(document).on('click', '.pf-nav-prev,.pf-nav-next', function(){
			    	
	      if($(this).hasClass('disabled')) return;
	      if($(this).hasClass('pf-nav-next')){
	        page++;
	        $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
	        if(page == pages){
	          $(this).addClass('disabled');
	          $('.pf-nav-prev').removeClass('disabled');
	        }else{
	          $('.pf-nav-prev').removeClass('disabled');
	        }
	      }else{
	        page--;
	        $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
	        if(page == 0){
	          $(this).addClass('disabled');
	          $('.pf-nav-next').removeClass('disabled');
	        }else{
	          $('.pf-nav-next').removeClass('disabled');
	        }
	        
	      }
	    })
	    //用户退出
	    function exit() {
	    	window.location="${ctx}/identity/user/logout.jspx";
			
		}
	  
	      function username(username){
	    	  //top.location.reload();
	    	  $("#username").text();
	      }
	    </script>

</head> 
<body>
    <div class="container">
        <div id="pf-hd">
           <div class="pf-logo"">
                <img  src="${ctx}/images/main_logo1.png" alt="logo">
           </div>
            
            <div class="pf-nav-wrap">
              <div class="pf-nav-ww">
                <ul class="pf-nav">
                 
	                  <li class="pf-nav-item home" data-menu="sys-manage">
	                      <a href="javascript:;">
	                          <span class="iconfont">&#xe63f;</span>
	                          <span class="pf-nav-title">系统管理</span>
	                      </a>
	                  </li>
                </ul>
              </div>

             
              <a href="javascript:;" class="pf-nav-prev disabled iconfont">&#xe606;</a>
              <a href="javascript:;" class="pf-nav-next iconfont">&#xe607;</a>
            </div>
            


            <div class="pf-user">
                <div class="pf-user-photo">
                    <img style="width: 40px;height: 40px;" src="${ctx}/images/timg.jpg" alt="">
                </div>
                <h4 class="pf-user-name ellipsis"><span id="username">${session_user.name}</h4></span>
                <i class="iconfont xiala">&#xe607;</i>

                <div class="pf-user-panel">
                    <ul class="pf-user-opt">
                        <li>
                            <a href="javascript:;">
                                <i id="usermessage" class="iconfont">&#xe60d;</i>
                                <span class="pf-opt-name">用户信息</span>
                            </a>
                        </li>
                        <li  id="exit">
                            <a href="javascript:exit();">
                                <i class="iconfont">&#xe60e;</i>
                                <span class="pf-opt-name">退出</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

        </div>

        <div id="pf-bd">
            <div id="pf-sider">
                <h2 class="pf-model-name">
                    <span class="iconfont">&#xe64a;</span>
                    <span class="pf-name">信息系统</span>
                    <span class="toggle-icon"></span>
                </h2>
				<!-- 展示系统左侧权限树  -->
                <ul class="sider-nav" id="sider-nav">
                  	  
                  	  	    
                  	  	   <c:forEach items="${mapMenu }" var="map">
                  	  	    	<li>
		                  	  	    <a href="javascript:;" >
			                            <span class="iconfont sider-nav-icon">&#xe611;</span>
			                            <span class="sider-nav-title">${map.key.name}</span>
			                            <i class="iconfont">&#xe642;</i>
			                        </a>
			                        <ul class="sider-nav-s">
			                            <c:forEach items="${map.value}" var="module">
			                           <li><a href="javascript:addPanel('${module.code}' ,'${module.url}','${module.name}');">${module.name}</a></li>
			                            </c:forEach>
			                  <!-- 
			                  <li> <a href="javascript:addPanel('001' ,'/identity/user/selectUser.jspx','用户管理');">用户管理</a><li>
			                  
			                  <li><a href="javascript:addPanel('002' ,'/identity/role/selectRole.jspx','角色管理');">角色管理</a><li>
			                       		<li><a href="javascript:addPanel('003' ,'/identity/module/mgrModule.jspx','菜单管理');">菜单管理</a><li> -->
			                        </ul>
                  	  	        </li>
                  	  	  
	                      </c:forEach>
                        
                 </ul> 
            </div>

           <!-- 面板 -->
            <div id="pf-page">
                <div class="easyui-tabs" id="tt" style="width:100%;height:100%;">
                  <div title="当前用户" id="user" style="padding:10px 5px 5px 10px;">
                    	<iframe class="page-iframe" src="${ctx}/home.jspx" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
                  </div>
                </div>
            </div>
        </div>

        <div id="pf-ft">
            <div class="system-name">
              <i class="iconfont">&#xe6fe;</i>
              <span>智能办公平台&nbsp;v1.0</span>
            </div>
            <div class="copyright-name">
              <span>Copyright&nbsp;©&nbsp;2015&nbsp;-&nbsp;2016&nbsp;kingson软件-分享知识，传递希望&nbsp;版权所有</span>
              <i class="iconfont" >&#xe6ff;</i>
            </div>
        </div>
    </div>
</body> 
</html>
    
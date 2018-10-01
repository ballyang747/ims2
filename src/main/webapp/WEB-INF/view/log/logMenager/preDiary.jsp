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
<script type="text/javascript" src="${ctx}/resources/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<link rel="stylesheet"
	href="${ctx }/css/pager.css" />
<script type="text/javascript">
tinyMCE.init({
	// General options
	//mode : "textareas",//匹配模式     页面中所有的textarea都加上富文本样式
	mode : "exact",//精确匹配
    elements : "content",//指定textarea的id
	theme : "advanced",//主题样式  
	language : "zh-cn",//语言
	plugins : "autolink,lists,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,wordcount,advlist,autosave",

	// Theme options
	theme_advanced_buttons1 : " save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
	theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
	theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
	theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak,restoredraft",
	
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_statusbar_location : "bottom",
	theme_advanced_resizing : true //是否可以拖动
});


</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	
			<table class="table-condensed" style="width: 80%;height: 80%;padding: 13px;">
				<tbody>
				
				    <tr>
				    <td><label>日记主题:</label></td>
				   <td><span class="label label-info">${diary.title}</span></td>
					
				    </tr>
					<tr>
					   
					   <td><label>创建人:</label></td>
						<td><span class="label label-info">${diary.creater.name}</span></td>
					
					
						<td><label>创建日期:</label></td>
						<td><span class="label label-info">${diary.createDate}</span></td>
					</tr>
					<tr>
						<td><label>修改人:</label></td>
						<td><span class="label label-info">${diary.modifier.name}</span></td>
						<td><label>修改日期:</label></td>
						<td><span class="label label-info">${diary.modifyDate}</span></td>
					</tr>
				<tr>
				
				</tbody>
				
			</table> 
			<tbody>
				<label>日记内容:</label>
				 <textarea id="content" name="content" class="form-control" rows="3" >${diary.content}</textarea>
				
				</tbody>
		</body>
</html>

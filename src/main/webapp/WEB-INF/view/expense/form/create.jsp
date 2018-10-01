<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pull-right">
	<a href="#" class="btn btn-default" onclick="addRow()">
		<i class=" icon icon-color  icon-plus"></i>添加
	</a>
</div>
<table class="table table-bordered table-striped table-condensed data-list">
	  <thead>
		  <tr>
			  <th style="width: 20%">费用类型</th>
			  <th style="width: 20%">报销金额</th>
			  <th style="width: 20%">发生时间</th>
			  <th>备注</th>
			  <th style="width: 10%">操作</th>                                          
		  </tr>
	  </thead>   
	<tbody id="expenseRows">
		<tr>
			<td>
				<select name="details[0].type.id" class="form-control" id="type" required="required">
					<option value="">-- 请选择费用类型 --</option>
				</select>
			</td>
			<td class="center">
				<input name="details[0].amount" type="number" class="form-control" required="required" min="0"/>
			</td>
			<td class="center">
				<input name="details[0].occurrenceDate" type="date" class="form-control" required="required"/>
			</td>
			<td class="center">
				<!-- 业务数据里面不能再使用remark这个名字，因为通用页面使用了 -->
				<!-- 所以这里改为 expenseRemark -->
				<textarea rows="1" cols="3" name="details[0].expenseRemark" class="form-control" required="required"></textarea>
			</td>
			<td class="center">
				<a href="#" class="btn btn-danger" onclick="deleteRow($(this).parent().parent())">
					<i class=" icon-remove icon-white"></i>删除
				</a>
			</td>         
		</tr>
	</tbody>
 </table>
<script type="text/javascript">

$(setDatePicker);

// 异步加载报销类型
$.ajax({
	url: contextPath + "/expense/type/available",
	success: function(data){
		$(data).each(function(){
			var option = new Option(this.name, this.id);
			$("#type").append(option);
		});
	},
	error: function(){
		alert("无法获得报销类型，请查看服务器日志！");
	}
});

function addRow()
{
	//复制第一行
	var firstRow = $("#expenseRows tr:first").clone();
	//清空复制的行里面的数据
	$("select", firstRow).val("");
	$("[name$='amount']", firstRow).val("");
	$("[name$='occurrenceDate']", firstRow).val("");
	$("[name$='expenseRemark']", firstRow).val("");
	//追加新的行到tbody里面
	firstRow.appendTo("#expenseRows");
	

	setDatePicker();
}

function deleteRow(tr){
	//判断是否为第一行，如果是则不能删除！
	var rows = $("#expenseRows tr").length;
	
	//必须行数大于1才能删除
	if( rows > 1 ){
		tr.remove();
	}
}

function checkForm(){
	return setInputName();
}
</script>
function checkFormOnSubmit(){
	try{
		//每个业务表单，都可以有一个checkForm函数，用于在提交表单之前对表单进行一些额外的处理。
		//以及校验表单是否正确等操作。
		return checkForm();
	}catch(ex){
		return true;
	}
}
//为所有的Time结束的字段增加日期时间选择，包括可以选择时分秒的
var setDateTimePicker = function(){
	var inputs = $("input[name$=Time]").each(function(){
		$(this).datetimepicker({
			language: "zh-CN",
			autoclose: 1,
			format: "yyyy-mm-dd hh:ii"
		});
	});
};

//为所有Date结尾的字段，增加日期选择，只能选择年月日
var setDatePicker = function(){
	var inputs = $("input[name$=Date]").each(function(){
		$(this).datepicker({
			autoclose: 1,
			language: "zh-CN",
			format: "yyyy-mm-dd"
		});
	});
};

/**
设置输入框的name属性的值
*/
function setInputName(){
	//默认table里面所有行里面，都带了一个0，要在提交之前，把0替换成 0～行数减一的数字
	var rows = $(".data-list tbody tr");
	//console.debug(rows);
	//return false;
	try{
		for( var index = 0; index < rows.length; index++){
			//找到一行里面，所有输入name属性带中括号的控件的name属性的值
			var tr = rows[index];
			//alert($(tr).html());
			
			var inputs = $("input", tr);
			var textareas = $("textarea", tr);
			var selects = $("select", tr);
			inputs.push(textareas);
			inputs.push(selects);
			//alert(inputs.length);
			for(var i = 0; i < inputs.length; i++)
			{
				var input = inputs[i];
				//alert($(this).attr("name"));
				var name = $(input).attr("name");
				if(/\[\d+\]/.test(name)){
					//把name属性的值里面的中括号里面的数字替换掉
					name = name.replace(/\[\d+\]/, "[" + index + "]");
					//alert(name);
					$(input, tr).attr("name", name);
				}
			}
		}
		return true;
	}catch(e){
		alert(e);
	}
	
	return false;
}
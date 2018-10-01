/**
 * 
 */
 
 $(function(){
  
            $("#checkAll").click(function(){
            	
            	var boxes=$("input[id^='box_']");
            	var flag = this.checked;
            	boxes.prop("checked",flag);
            	$("tr[id]").trigger(flag?"mouseover":"mouseout");
            })
            
            $("tr[id^='dataTr_']").hover(function(){
            	
            	$(this).css("backgroundColor","yellow").css("cursor","pointer");
            },function(){
            	var trId= this.id;
            	var boxId=trId.replace("dataTr","box");
            	var flag = $("#"+boxId).prop("checked");
            	if(!flag){
            		
            		$(this).css("backgroundColor","");
            	}
            }).click(function(){
            	
            	
            	var trId = this.id;
            	var boxId = trId.replace("dataTr","box");
            $("#"+boxId).prop("checked",!$("#"+boxId).prop("checked"))
            
            var checkedBoxes  =$("input[id^='box']").filter(":checked");
            var boxes = $("input[id^='box_']");
            $("#checkAll").prop("checked",checkedBoxes.length ==boxes.length);
           })
           
           $("input[id^='box_']").click(function (event){
        	   
        	   var checkedBoxes =$("#input[id^='box_']").filter(":checked");
        	   var boxes = $("input[id^='box_']");
        	   
        	   $("#checkAll").prop("checked",checkedBoxes.length == boxes.length)
            event.stopPropagation();        	   
           })
           
 
 
 })
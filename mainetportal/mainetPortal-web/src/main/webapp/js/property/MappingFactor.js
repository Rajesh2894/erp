$(function(){
	
	jQuery('.hasDecimal').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	
	
	
	$("#customFields1").on('click','.addCF2',function(){
		
		var errorList = [];
	
		//alert("hello6");
		//$(".testone").multiselect('refresh');
		
		//$('.testone').multiselect({includeSelectAllOption: true,});
		//$(".testone").multiselect('deselectAll', false);
		
		
		
			if (errorList.length == 0) {
			
				$(".datepicker2").datepicker("destroy");
				var content = $(this).closest('tr').clone();
				$(this).closest("tr").after(content);
				var clickedIndex = $(this).parent().parent().index() - 1;	
				content.find("input:text").val('');
				//content.find('input[type=checkbox][value=N]').prop('checked', false);
				content.find("select").val('0');
			
				$('.error-div').hide();
				datePickerLogic();
				reOrderTableSequence('.factorTableClass');
				return false;
			} else {
			displayErrorsOnPage(errorList);
		}
		
	}); 
	
	$("#customFields1").on('click','.remCF2',function(){
		
		if($("#customFields1 tr").length != 2) {
			$(this).parent().parent().remove();					
			reOrderTableSequence('.factorTableClass');
		} else {
			var errorList = [];
			errorList.push("First row cannot be remove.");
			displayErrorsOnPage(errorList);
			
		}
		 
	});


});


function reOrderTableSequence(className) {
	
	$(className).each(function(i) {
		// re-ordering id 	
		
		$(".datepicker2").datepicker("destroy");
		$(this).find("select:eq(0)").attr("id", "factorType_"+i);
		//$(this).find("select:eq(0)").attr("onchange","getFactorValue("+i+")");
		$(this).find("select:eq(1)").attr("id", "factorValue_"+i);
		$(this).find("select:eq(2)").attr("id", "assfActive_"+i);
		$(this).find("input:text:eq(0)").attr("id", "fromDate_"+i);
		$(this).find("input:text:eq(1)").attr("id", "toDate_"+i);
		$(this).find("input:text:eq(2)").attr("id", "srId_"+i);
		
		
		
		$("#srNoId_"+i).text(i+1);
		//"+i+"
		// re-ordering path binding
		$(this).find("select:eq(0)").attr("name","tableList["+i+"].assfFactor");
		$(this).find("select:eq(1)").attr("name","tableList["+i+"].assfFactorValue");
		$(this).find("select:eq(2)").attr("name","tableList["+i+"].assfActive");
		$(this).find("input:text:eq(0)").attr("name","tableList["+i+"].assFromDate");
		$(this).find("input:text:eq(1)").attr("name","tableList["+i+"].assToDate");

		
		datePickerLogic();
				
	});
	
}



function getFactorValue(count){
	
	 var factorType=$("#factorType_"+count+" option:selected").attr("code");
	
		$('#factorValue_'+count).find('option:gt(0)').remove();
			var postdata = 'factorType=' + factorType;
			
			var json = __doAjaxRequest('SelfAssessmentForm.html?getMappingFactor', 'POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#factorValue_'+count).append(optionsAsString );
}

function datePickerLogic(){
	
	 $(".datepicker2").datepicker({
	        dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true
		});
		return true;
}

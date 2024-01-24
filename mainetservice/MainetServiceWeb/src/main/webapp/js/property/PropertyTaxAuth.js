function populateSrNoDropDown(srNoArr) {
	$('.srNoCss').empty();
	$.each(srNoArr, function(val, text) {
        $('.srNoCss').append( $('<option></option>').val(text).html(text) )
      //   $('.srNoCss').append( $('<option></option>').html(text))
        }); 
	
	/*$('.testone').multiselect({
		enableFiltering: true,
		includeSelectAllOption: true,
		selectAllJustVisible: false,
    });
	$(".testone").multiselect('rebuild');*/
	
	//$(".testone").multiselect('rebuild');
	
	/*$(".testone").multiselect({
		enableFiltering: true,	
        includeSelectAllOption: true
    });*/

}


 var q=1;
$(function(){
	
	var srNoArr = [];
	
	var factorList = $('#factorSrnoListId').val();
	//q = factorList;
	var numbers = [];
	
	var list = factorList.replace(/[\])}[{(]/g, '');
	numbers = list.split(',');
	$.each(numbers, function(item, index) {
	//	alert("item >>"+item+" :: numbers >>"+numbers[item]);
	    srNoArr.push(numbers[item]);
	    q = numbers[item];
	});
	
//	srNoArr.push(numbers);
	/*alert("factorList >> "+factorList);
	$.each(q, function(val, text) {
		alert("val >> "+val+" text >> "+text);
		 $('.srNoCss').append( $('<option></option>').val(val).html(text));
	});*/
	
	populateSrNoDropDown(srNoArr);
	
	jQuery('.hasDecimal').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
		
	$("#customFields").on('click','.addCF',function(){
		var errorList = [];
		q++;
		srNoArr.push(q);
		populateSrNoDropDown(srNoArr);		
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
				
				reOrderTableIdSequence('.tableRowClass');
				return false;
			} else {
			displayErrorsOnPage(errorList);
		}
			
			
	}); 
	
	$("#customFields").on('click','.remCF',function(){
		
		alert("id >> ");
		if(q>1){
			q--;
			srNoArr.pop(q);
			populateSrNoDropDown(srNoArr);
		}
		
		
		if($("#customFields tr").length != 2) {
		//	$(this).parent().parent().remove();	
		
			reOrderTableIdSequence('.tableRowClass');
		} else {
			var errorList = [];
			errorList.push("First row cannot be remove.");
			displayErrorsOnPage(errorList);
			
		}
		 
	});

});


/*function addRow(rowId){
	alert("rowId from add >> "+rowId);
	
	var errorList = [];
	q++; 
	srNoArr.push(q);
	populateSrNoDropDown(srNoArr);		
		if (errorList.length == 0) {
			$(".datepicker2").datepicker("destroy");
			var content = $(this).closest('tr').clone();
			$(this).closest("tr").after(content);
			var clickedIndex = $(this).parent().parent().index() - 1;	
			content.find("input:text").val('');
			content.find("select").val('0');
			$('.error-div').hide();
			datePickerLogic();
			reOrderTableIdSequence('.tableRowClass');
			return false;
		} else {
		displayErrorsOnPage(errorList);
	}
}

function removeRow(rowId){
	alert("rowId from remove >> "+rowId);
		
	if(q>1){
		q--;
		// srNoArr.pop(q);
		
		srNoArr.pop(rowId);
		
		populateSrNoDropDown(srNoArr);
	}
	
	
	if($("#customFields tr").length != 2) {
		$(this).parent().parent().remove();	
	
		reOrderTableIdSequence('.tableRowClass');
	} else {
		var errorList = [];
		errorList.push("First row cannot be remove.");
		displayErrorsOnPage(errorList);
		
	}
	
}*/

function reOrderTableIdSequence(className) {
	
	$(className).each(function(i) {
		// re-ordering id 	
		$(this).find("input:hidden:eq(0)").attr("id", "unitSrNo_"+i);
		$(".datepicker2").datepicker("destroy");
//		$(this).find("select:eq(0)").attr("id", "proAssdUnitTypeId_"+i);
		$(this).find("select:eq(0)").attr("id", "proAssdFloorNo_"+i);
		$(this).find("input:text:eq(0)").attr("id", "proAssdBuildupArea_"+i);
		$(this).find("select:eq(1)").attr("id", "proAssdUsagetype_"+i);
		$(this).find("select:eq(2)").attr("id", "proAssdConstruType_"+i);
		$(this).find("input:text:eq(1)").attr("id", "yeardate_"+i);
		$(this).find("select:eq(3)").attr("id", "proAssdOccupancyType_"+i);
//		$(this).find("select:eq(4)").attr("id", "proAssdRoadfactor_"+i);
		$(this).find("input:text:eq(2)").attr("id", "proAssdAnnualRent_"+i);
		$(this).find("input:hidden:eq(1)").attr("id", "proAssdAlv_"+i);
		$(this).find("input:hidden:eq(2)").attr("id", "proAssdRv_"+i);
		$(this).find("input:hidden:eq(3)").attr("id", "proAssdStdRate_"+i);
				
	//	$("#srNoId_"+i).text(i+1);
		$("#unitSrNo_"+i).text(i+1);
		$("#unitSrNo_"+i).val(i+1);
	//	alert("i value >> "+i);
		
		// re-ordering path binding
		$(this).find("input:hidden:eq(0)").attr("name","entity.provisionalAssesmentDetails["+i+"].unitSrNo");
//		$(this).find("select:eq(0)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdUnitTypeId");
		$(this).find("select:eq(0)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdFloorNo");
		$(this).find("input:text:eq(0)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdBuildupArea");
		$(this).find("select:eq(1)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdUsagetype1");
		$(this).find("select:eq(2)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdConstruType");
		$(this).find("input:text:eq(1)").attr("name","entity.provisionalAssesmentDetails["+i+"].createdDate");
		$(this).find("select:eq(3)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdOccupancyType");
//		$(this).find("select:eq(4)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdRoadfactor");
		$(this).find("input:text:eq(2)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdAnnualRent");
		$(this).find("input:hidden:eq(1)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdAlv");
		$(this).find("input:hidden:eq(2)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdRv");
		$(this).find("input:hidden:eq(3)").attr("name","entity.provisionalAssesmentDetails["+i+"].proAssdStdRate");
			
		datePickerLogic();
	});
	
}

function datePickerLogic(){
	
	 $(".datepicker2").datepicker({
	        dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true
		});
		return true;
}

function redirectToDepartmentWiseBillPayment(){
	debugger;
	 var divName = '.content-page';
	    var ajaxResponse = doAjaxLoading(
		    'CommonManualReceiptEntry.html?redirectToDepartmentWiseBillPayment', {}, 'html',
		    divName);
	    $(divName).removeClass('ajaxloader');
	    $(divName).html(ajaxResponse);
	    prepareTags();
}


function redirectToDepartmentWiseBillPayment1(){
	var depShortCode = $("#departmentId").val();
	if(depShortCode == 'WT'){
		value = "WaterManualReceiptEntry.html";
	}else if(depShortCode == 'AS'){
		value = "ManualReceiptEntry.html";
	}

$("#postMethodForm").prop('action', '');
$("#postMethodForm").prop('action', value);
$("#postMethodForm").submit();
}



$(document).ready(function() {
	
	$('.chosen-select-no-results').chosen();
	
$('.alphaNumeric').keyup(function() {
		
		var regx = /^[A-Za-z0-9.\s]*$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
});

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CBBOFiledStaffDetailForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];


	var cbboExpertName = $("#cbboExpertName").val();
	var sdb3 = $("#sdb3").val();
	
	var divName = '.content-page';
	if ((cbboExpertName == "" || cbboExpertName == undefined) && (sdb3 == "" ||  sdb3 == undefined || sdb3 == 0)) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {

				"name" :cbboExpertName,
				"block" :sdb3
				
		};
		var ajaxResponse = doAjaxLoading('CBBOFiledStaffDetailForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}



function ResetForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CBBOFiledStaffDetailForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function modifyCase(fsdId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"fsdId" : fsdId,
		"mode" : mode
		
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveCBBOFiledStaffDetailForm(obj) {

	var errorList = [];

	errorList = validateCBBOFiledStaffDetailForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "CBBO Filed Staf fDetail Request Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateCBBOFiledStaffDetailForm(errorList){



	var cbboExpertName = $("#cbboExpertName").val();
	var sbd3 = $("#sdb3").val();
	var emailId = $("#emailId").val();
	var contactNo = $("#contactNo").val();
	

	if (cbboExpertName == undefined || cbboExpertName == "" ) {
		errorList.push(getLocalMessage("sfac.cbbo.field.staff.validation.cbboExpertName"));
	}

	if (sbd3 == undefined || sbd3 == "" || sbd3 =="0") {
		errorList.push(getLocalMessage("sfac.cbbo.field.staff.validation.sbd3"));
	}

	if (emailId == undefined || emailId == "" ) {
		errorList.push(getLocalMessage("sfac.cbbo.field.staff.validation.emailId"));
	}
	if (contactNo == undefined || contactNo == "" ) {
		errorList.push(getLocalMessage("sfac.cbbo.field.staff.validation.contactNo"));
	}

	return errorList;

}

function getSD(ele){

	var requestData = {
			"block" : $("#sdb3").val()
		};
			var URL = 'CBBOFiledStaffDetailForm.html?getSD';
			var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
			var prePopulate = JSON.parse(returnData);
			
			
				$('#sdb2').val(prePopulate.sdb2);
				$('#sdb1').val(prePopulate.sdb1);
					
			
			$('#sdb1').trigger("chosen:updated");
			$('#sdb2').trigger("chosen:updated");

}

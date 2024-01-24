$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0

	});

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
	var ajaxResponse = doAjaxLoading('ABSEntryForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];


	var fpoId = $("#fpoId").val();

	var fy = $("#status").val();
	
	var divName = '.content-page';
	if ((fpoId == "0" || fpoId == "" || fpoId == undefined)  ) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
				"fpoId" : fpoId,

				"status":fy
		};

		var ajaxResponse = doAjaxLoading('ABSEntryForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}



function ResetForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('ABSEntryForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function modifyCase(cnId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"cnId" : cnId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}






function saveABSForm(obj) {

	var errorList = [];

	errorList = validateABSForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "Audit Balance Sheet Entry Request Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateABSForm(errorList){


	var crpDateFrom = $("#crpDateFrom").val();
	var crpDateTo = $("#crpDateTo").val();
	var prpDateFrom = $("#prpDateFrom").val();
	var prpDateTo = $("#prpDateTo").val();
	
	if (crpDateFrom == undefined || crpDateFrom == "") {
		errorList.push(getLocalMessage("sfac.abs.entry.validation.crpDateFrom") );
	}
	if (crpDateTo == undefined || crpDateTo == "") {
		errorList.push(getLocalMessage("sfac.abs.entry.validation.crpDateTo"));
	}
	if (prpDateFrom == undefined || prpDateFrom == "") {
		errorList.push(getLocalMessage("sfac.abs.entry.validation.prpDateFrom") );
	}
	
	if (prpDateTo == undefined || prpDateTo == "") {
		errorList.push(getLocalMessage("sfac.abs.entry.validation.prpDateTo") );
	}
	

	return errorList;

}

function saveABSEntryApprovalData(obj){
	return saveOrUpdateForm(obj, "Audit Balance Sheet Entry Saved Successfully!", 'AdminHome.html', 'saveform');
}


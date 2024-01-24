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
	var ajaxResponse = doAjaxLoading('MilestoneCompletionForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];


	var iaId = $("#iaId").val();
	var cbboId = $("#cbboId").val();
	var fy = $("#status").val();
	var divName = '.content-page';
	if ((iaId == "0" || iaId == "" || iaId == undefined)  ) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {

				"iaId" :iaId,
				"cbboId" :cbboId,
				"status":fy
		};
		var ajaxResponse = doAjaxLoading('MilestoneCompletionForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function saveMilestoneCompletionForm(obj) {

	var errorList = [];

	errorList = validateMilestoneForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "Milestone Completion Request Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateMilestoneForm(errorList){
	
	var actualCompletionDate = $("#actualCompletionDate").val();
	var invoiceAmount = $("#invoiceAmount").val();
	var allocationBudget = $("#allocationBudget").val();
	var msId = $("#msId").val();
	
	if (msId == undefined || msId == "" || msId == "0") {
		errorList.push(getLocalMessage("sfac.milestone.validation.msId"));
	}
	
	if (actualCompletionDate == undefined || actualCompletionDate == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.actualCompletionDate"));
	}

	if (invoiceAmount == undefined || invoiceAmount == "" ) {
		errorList.push(getLocalMessage("sfac.milestone.validation.invoiceAmount"));
	}
	
	if(invoiceAmount > allocationBudget){
		errorList.push(getLocalMessage("sfac.milestone.validation.notGreaterThanALLBud"));
	}
	
	return errorList;
}

function ResetForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MilestoneCompletionForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function modifyCase(mscId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"mscId" : mscId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getMilestoneDetails(obj){
	var divName = '.content-page';
	var msId = $('#msId').val();
	
	var requestData = {

		
			"msId":msId
	};
	var ajaxResponse = doAjaxLoading('MilestoneCompletionForm.html?getMilestoneDetails', requestData, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}


function saveMilestoneCompletionApprovalData(obj){
	return saveOrUpdateForm(obj, "Milestone Completion Request Saved Successfully!", 'AdminHome.html', 'saveform');
}
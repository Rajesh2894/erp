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
	var ajaxResponse = doAjaxLoading('DPREntryForm.html?formForCreate',
			{}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function searchForm(obj) {
	var errorList = [];


	var iaId = $("#iaId").val();
	var fpoId = $("#fpoId").val();
	
	var divName = '.content-page';
	if ((iaId == "0" || iaId == "" || iaId == undefined) && (fpoId == "0" || fpoId == "" || fpoId == undefined) ) {
		errorList.push(getLocalMessage("sfac.searchCriteria"));
	}
	if (errorList.length == 0) {
		var requestData = {
				"fpoId" :fpoId,
				"iaId" :iaId,
				
		};
		var ajaxResponse = doAjaxLoading('DPREntryForm.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}

function saveDPREntryForm(obj) {

	var errorList = [];

	errorList = validateDPRForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "DPR Entry Request Saved Successfully!", 'AdminHome.html', 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateDPRForm(errorList){
	
	var iaId = $("#iaId").val();
	var fpoId = $("#fpoId").val();
	var dateOfSubmission = $("#dateOfSubmission").val();
	
	
	if (fpoId == undefined || fpoId == "" || fpoId == "0") {
		errorList.push(getLocalMessage("sfac.dpr.validation.fpoId"));
	}
	if (iaId == undefined || iaId == "" || iaId == "0") {
		errorList.push(getLocalMessage("sfac.dpr.validation.iaId"));
	}
	
	if (dateOfSubmission == undefined || dateOfSubmission == "" ) {
		errorList.push(getLocalMessage("sfac.dpr.validation.dateOfSubmission"));
	}
	
	errorList = validateDocDetailsTable(errorList);

	
	
	return errorList;
}

function ResetForm() {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('DPREntryForm.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function modifyCase(dprId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"dprId" : dprId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}



function savDPREntryApprovalData(obj){
	var errorList = [];
	errorList = validateApprvoal(errorList);
	if (errorList.length == 0) {
	return saveOrUpdateForm(obj, "DPR Entry Request Saved Successfully!", 'AdminHome.html', 'saveform');
	}
	else {
		displayErrorsOnPage(errorList);
	}
}

function validateApprvoal(errorList){
	
	var rowCount = $('#dprSecDetailsTable tr').length;	
		$("#dprSecDetailsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var dprScore = $("#dprScore" + i).val();
			
				var constant = 1;
       }
        else{
        	var dprScore = $("#dprScore" + i).val();
        	var constant = i+1;
     }
		
				
				if (dprScore == undefined || dprScore == "" || dprScore =="0") {
					errorList.push(getLocalMessage("sfac.dpr.validation.dprScore") + " " + (i + 1));
				}
				
				
		});
	
	return errorList;

}


function calculateTotal(ids) {
	var newDemand = $("#dprScore"+ids).val();
	var newDemandTotal = $("#overallScore").val();
	if (newDemand !=undefined && newDemand != ""){
		if(newDemandTotal ==undefined || newDemandTotal == ""){
			newDemandTotal = 0;
		}
		$("#overallScore").val(parseFloat(newDemandTotal) + parseFloat(newDemand));
	}
}


function addDPRSecButton(element) {
	var errorList = [];
	errorList = validateDocDetailsTable(errorList);
	if (errorList.length == 0) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var row = $("#dprSecDetailsTable tbody .appendableDPRSecDetails").length;
	
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('DPREntryForm.html?fileCountUpload',
				'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		
	
		$('.content-page').html(response);
	prepareTags();
	}else {
		displayErrorsOnPage(errorList);
	}

}

function validateDocDetailsTable(errorList){
	
	var rowCount = $('#dprSecDetailsTable tr').length;	
	
		$("#dprSecDetailsTable tbody tr").each(function(i) {
			if(rowCount <= 3){
				
				var dprSection = $("#dprSection" + i).val();
				var constant = 1;
       }
        else{
        	var dprSection = $("#dprSection" + i).val();
        	var constant = i+1;
     }
				
				if (dprSection == undefined || dprSection == "" || dprSection =="0") {
					errorList.push(getLocalMessage("sfac.dpr.validation.dprSection") + " " + (i + 1));
				}
				
		});
	
	return errorList;
}

function deleteDPRSecDetails(obj, id) {

	requestData = {
		"id" : id
	};

	var row = $("#dprSecDetailsTable tbody .appendableDPRSecDetails").length;
	if (row != 1) {
		var response = __doAjaxRequest('DPREntryForm.html?doEntryDeletion',
				'POST', requestData, false, 'html');
		$(".content-page").html(response);
		prepareTags();
	}
}
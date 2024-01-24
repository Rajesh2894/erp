$(document).ready(function() {

	

	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2020:2200",
		maxDate: 0
	});
	
	
	
	$('.chosen-select-no-results').chosen();
});


function getDistrictList() {
	
	var requestData = {
			"state" : $("#sdb1").val()
	};
	var URL = 'StateInformationMaster.html?getDistrictList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#sdb2').html('');
	$('#sdb2').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#sdb2').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#sdb2').trigger("chosen:updated");
}



function saveEquityGrantForm(obj){

	var errorList = [];

	errorList = validateCGFForm(errorList);

	if (errorList.length == 0) {

		return saveOrUpdateForm(obj, "FPO Credit Guarantee Request Saved Successfully!", 'CreditGrantEntry.html', 'saveCreditGuaranteeForm');
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateCGFForm(errorList){

	return errorList;
}


function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CreditGrantEntry.html?formForCreate',
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
		var ajaxResponse = doAjaxLoading('CreditGrantEntry.html?searchForm', requestData, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	} else {
		displayErrorsOnPage(errorList);
	}
}


function modifyCase(egId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
			"mode" : mode,
			"egId" : egId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getCheckList(obj) 
{

	var errorList = [];
	errorList = validateCGFData(errorList);
	
	if (errorList.length == 0)
	{
		
		return saveOrUpdateForm(obj,
				" ", "CreditGrantEntry.html",
				'getCheckList');
		
	}
	else 
	{
		displayErrorsOnPage(errorList);
	}
}

function validateCGFData(errorList){
	
	/*var businessofFPC = $("#businessofFPC").val();
	var noOfSMLShareholderMemb = $("#noOfSMLShareholderMemb").val();
	var amountofEquityGrant = $("#amountofEquityGrant").val();
	var maxIndShareholdMem = $("#maxIndShareholdMem").val();
	
	
	var noOfDirectors = $("#noOfDirectors" ).val();
	var womenDirectors = $("#womenDirectors").val();
	var dateBoardMeetingsLastYear = $("#dateBoardMeetingsLastYear").val();
	var modeOfBoardFormation = $("#modeOfBoardFormation").val();
	var bankId = $("#bankId").val();
	
	
	
	if (businessofFPC == undefined || businessofFPC == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.businessofFPC") );
	}
	if (noOfSMLShareholderMemb == undefined || noOfSMLShareholderMemb == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.noOfSMLShareholderMemb"));
	}
	if (amountofEquityGrant == undefined || amountofEquityGrant == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.amountofEquityGrant") );
	}
	
	if (maxIndShareholdMem == undefined || maxIndShareholdMem == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.maxIndShareholdMem") );
	}
	if (modeOfBoardFormation == undefined || modeOfBoardFormation == "" || modeOfBoardFormation == "0") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.modeOfBoardFormation") );
	}
	if (noOfDirectors == undefined || noOfDirectors == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.noOfDirectors"));
	}
	
	if (womenDirectors == undefined || womenDirectors == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.womenDirectors") );
	}
	if (dateBoardMeetingsLastYear == undefined || dateBoardMeetingsLastYear == "") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.dateBoardMeetingsLastYear") );
	}
	
	if (bankId == undefined || bankId == "" || bankId == "0") {
		errorList.push(getLocalMessage("sfac.fpo.egr.validation.bankId") );
	}*/


return errorList;
}


function saveCreditGuaranteeApprovalData(obj){
	return saveOrUpdateForm(obj, "Credit Guarantee Request Saved Successfully!", 'AdminHome.html', 'saveform');
}





function ResetForm() {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CreditGrantEntry.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}



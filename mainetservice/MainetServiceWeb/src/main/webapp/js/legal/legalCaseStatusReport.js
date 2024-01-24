/**
 * 
 */
$(document).ready(function() {

/*	$("#suitNo").val();
	$("#cseType").val();
	$("#cseStatus").val();*/

	$('#csefrmDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	$("#csefrmDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#csetoDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#csetoDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#resetNewCaseForm").on("click", function() {
		window.location.reload("#newCaseTrackerReport")
	});
	
	// JS to display "ALL" as the first option in the select drop-down
	$('select').on('mouseenter focus', function(){
		var codeAll = $(this).children('option[code="ALL"]');
		codeAll.insertAfter($(this).find('option[value="0"]'));
	});
});

function saveForm(obj) {

	var errorList = [];
	var caseType = $("#cseType").val();
	var caseStatus = $("#cseStatus").val();
	var csefrmDate = $("#csefrmDate").val();
	var csetoDate = $("#csetoDate").val();

	if (caseStatus == 0 || caseStatus == null) {
		errorList.push(getLocalMessage("legal.case.validation.status"));
	}
	if (caseType == 0 || caseType == null) {
		errorList.push(getLocalMessage("legal.case.validation.type"));
	}

	if (csefrmDate == "" || csefrmDate == 0 || csefrmDate == null) {
		errorList.push(getLocalMessage("legal.case.active.from.date"));
	}

	if (csetoDate == "" || csetoDate == 0 || csetoDate == null) {
		errorList.push(getLocalMessage("legal.case.active.to.date"));
	}

	if ((compareDate(csefrmDate)) > compareDate(csetoDate)) {
		errorList
				.push(getLocalMessage("legal.case.validation.active.from.to.date"));
	}

	if (caseStatus == -1) {
		caseStatus = 0;
	}
	if (caseType == -1) {
		caseType = 0;
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = '&caseType=' + caseType + '&caseStatus=' + caseStatus
				+ '&csefrmDate=' + csefrmDate + '&csetoDate=' + csetoDate;

		var URL = 'LegalCaseReport.html?GetCaseStatusReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
		/*
		 * if (returnData == "f") {
		 * errorList.push(getLocalMessage("legal.validation.financialYrDate"));
		 * displayErrorsOnPage(errorList); } else { window.open(returnData,
		 * '_blank'); }
		 */
	}
}
/****************** Cases Dairy Report *********************/
function saveListCases(obj) {

	var errorList = [];
	var CasedSuitNo = $("#suitNo").val();

	if (CasedSuitNo == 0 || CasedSuitNo == "") {
		errorList.push(getLocalMessage("legal.validation.detail.case.Suit.No"));
	}

	if (CasedSuitNo == -1) {
		CasedSuitNo = "X";
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = {
			"CasedSuitNo" : CasedSuitNo

		};

		var URL = 'LegalCaseReport.html?GetListOfCases';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
		/*if (returnData == "f") {
			errorList.push(getLocalMessage("legal.validation.financialYrDate"));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}*/
	}

}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function resetFormDetails() {
	$('#suitNo').val('0').trigger('chosen:updated');
}

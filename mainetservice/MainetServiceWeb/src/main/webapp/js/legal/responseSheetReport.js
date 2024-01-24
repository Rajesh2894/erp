/**
 * 
 */
$(document).ready(function() {
	$("#cseDeptId").val();
	$("#AdvName").val();
	$("#cseCategory1").val();
	$("#cseCategory2").val();
	$("#suitNo").val();
	$("#cseStatus").val();
	$("#crtId").val();
	$("#crtType").val();
	$("#state").val();
	$("#district").val();

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

	$("#resetForm").on("click", function() {
		window.location.reload("#ResponseReport")
	});

	$("#resetCaseForm").on("click", function() {
		window.location.reload("#newCaseTrackerReport")
	});
	
	// JS to display "ALL" as the first option in the select drop-down
	$('select').on('mouseenter focus', function(){
		var codeAll = $(this).children('option[code="ALL"]');
		codeAll.insertAfter($(this).find('option[value="0"]'));
	});
});

/* Response sheet Report */
function saveResponseForm(obj) {

	var errorList = [];
	var csedepartment = $("#cseDeptId").val();
	/* var courtType = $('#crtType').val(); */
	var caseAdvName = $("#AdvName").val();
	var cseSuitNo = $("#suitNo").val();
	var caseCategory = $("#cseCategory1").val();
	var caseSubCategory = $("#cseCategory2").val();
	var csefrmDate = $("#csefrmDate").val();
	var csetoDate = $("#csetoDate").val();

	if (csedepartment == 0 || csedepartment == null
			|| csedepartment == undefined) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.case.department"));
	}

	/*
	 * if (courtType == 0) { errorList
	 * .push(getLocalMessage("legal.casePendency.validation.court.type")); }
	 */
	
	if (caseAdvName == 0 || caseAdvName == null || caseAdvName == undefined) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.advocate.name"));
	}

	if (caseCategory == 0) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.case.category"));
	}
	if (caseSubCategory == 0) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.case.sub.category"));
	}
	if (csefrmDate == 0 || csefrmDate == undefined || csefrmDate == null
			|| csefrmDate == "") {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.active.from.date"));
	}

	if (csetoDate == 0 || csetoDate == undefined || csetoDate == null
			|| csetoDate == "") {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.active.to.date"));
	}

	if (cseSuitNo == 0) {
		errorList.push(getLocalMessage("legal.detail.case.Case.Suit.No"));
	}

	if ((compareDate(csefrmDate)) > compareDate(csetoDate)) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.active.from.to.date"));
	}
	/* Setting value as Zero if user select All in form */
	/*
	 * if (courtType == -1) { courtType = 0; }
	 */
	if (caseCategory == -1) {

		caseCategory = 0;
	}
	if (caseSubCategory == -1) {
		caseSubCategory = 0;
	}
	if (csedepartment == -1) {
		csedepartment = 0;
	}
	if (caseAdvName == -1) {
		caseAdvName = 0;
	}

	if (cseSuitNo == -1) {
		cseSuitNo = 'X';
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = {
			"csedepartment" : csedepartment,
			/* "courtType" : courtType, */
			"caseAdvName" : caseAdvName,
			"caseCategory" : caseCategory,
			"caseSubCategory" : caseSubCategory,
			"csefrmDate" : csefrmDate,
			"csetoDate" : csetoDate,
			"cseSuitNo" : cseSuitNo

		}

		var URL = 'ResponseSheetReport.html?GetResponseReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

/* CASE TRACKER REPORT */
function saveCaseTrackerForm(obj) {
	
	var errorList = [];
	var caseSuitNo = $("#suitNo").val();
	var casefrmDate = $("#csefrmDate").val();
	var casetoDate = $("#csetoDate").val();
	var casStatus = $("#cseStatus").val();
	

	if (casefrmDate == 0 || casefrmDate == undefined || casefrmDate == null
			|| casefrmDate == "") {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.active.from.date"));
	}

	if (casetoDate == 0 || casetoDate == undefined || casetoDate == null
			|| casetoDate == "") {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.active.to.date"));
	}

	if (caseSuitNo == 0) {
		errorList.push(getLocalMessage("legal.detail.case.Case.Suit.No"));
	}

	if (casStatus == 0 || casStatus == null) {
		errorList.push(getLocalMessage("legal.case.validation.status"));
	}
	if ((compareDate(casefrmDate)) > compareDate(casetoDate)) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.active.from.to.date"));
	}

	if (caseSuitNo == -1) {
		caseSuitNo = 'X';
	}

	if (casStatus == -1) {
		casStatus = 0
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData1 = {

			"casefrmDate" : casefrmDate,
			"casetoDate" : casetoDate,
			"caseSuitNo" : caseSuitNo,
			"casStatus" : casStatus

		}

		var URL1 = 'ResponseSheetReport.html?getCaseReport';
		var returnData1 = __doAjaxRequest(URL1, 'POST', requestData1, false);
		window.open(returnData1, '_blank');
		/*
		 * if (returnData1 == "f") {
		 * errorList.push(getLocalMessage("legal.validation.financialYrDate"));
		 * displayErrorsOnPage(errorList); } else { window.open(returnData1,
		 * '_blank'); }
		 */

	}
}

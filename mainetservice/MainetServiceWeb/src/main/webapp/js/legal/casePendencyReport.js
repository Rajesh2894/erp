/**
 * 
 */
$(document).ready(function() {
	$("#cseDeptId").val(-1);
	$("#crtType").val(-1);
	$("#AdvName").val(-1);
	$("#cseStatus").val(-1);
	$("#cseCategory1").val(-1);
	$("#cseCategory2").val(-1);
	$("#cseType").val(-1);

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
		window.location.reload("#CasePendencyFormReport")
	});
	
	// JS to display "ALL" as the first option in the select drop-down
	$('select').on('mouseenter focus', function(){
		var codeAll = $(this).children('option[code="ALL"]');
		codeAll.insertAfter($(this).find('option[value="0"]'));
	});
});

function resetForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#CasePendencyFormReport").validate().resetForm();
}
function saveForm(obj) {
	

	var errorList = [];
	var csedepartment = $("#cseDeptId").val();
	var courtType = $('#crtType').val();
	var caseAdvName = $("#AdvName").val();
	var caseStatus = $("#cseStatus").val();
	var caseCategory = $("#cseCategory1").val();
	var caseSubCategory = $("#cseCategory2").val();
	var csefrmDate = $("#csefrmDate").val();
	var csetoDate = $("#csetoDate").val();
	var caseType = $("#cseType").val();
	

	
	if (csedepartment == 0 || csedepartment == null
			|| csedepartment == undefined) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.case.department"));
	}

	if (courtType == 0) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.court.type"));
	}
	if (caseAdvName == 0 || caseAdvName == null || caseAdvName == undefined) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.advocate.name"));
	}

	if (caseStatus == 0) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.case.status"));
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

	if (caseType == 0) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.case.type"));
	}

	/*if (compareDate(csefrmDate) > compareDate(csetoDate)) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.active.from.to.date"));
	}
	*/
	/* Setting value as Zero if user select All in form */
	if (courtType == -1) {
		courtType = 0;
	}
	if (caseStatus == -1) {
		caseStatus = 0;
	}
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

	if (caseType == -1) {
		caseType = 0;
	}
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = '&csedepartment=' + csedepartment + '&courtType='
				+ courtType + '&caseAdvName=' + caseAdvName + '&caseStatus='
				+ caseStatus + '&caseCategory=' + caseCategory
				+ '&caseSubCategory=' + caseSubCategory + '&csefrmDate='
				+ csefrmDate + '&csetoDate=' + csetoDate + '&caseType='
				+ caseType;

		var URL = 'CasePendencyReport.html?GetCaseReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}
function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

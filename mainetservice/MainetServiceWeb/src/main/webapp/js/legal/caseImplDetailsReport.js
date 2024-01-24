
$(document).ready(function() {

	$("#suitNo").val(-1);

	

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
		window.location.reload("#caseImplDetailsReport")
	});

});


function resetForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#caseImplDetailsReport").validate().resetForm();
}
function saveForm(obj) {

	var errorList = [];
	var CasedSuitNo = $("#suitNo").val();
	var csefrmDate = $("#csefrmDate").val();
	var csetoDate = $("#csetoDate").val();
	
	
	if (CasedSuitNo == 0 || CasedSuitNo == null || CasedSuitNo == "") {
		errorList.push(getLocalMessage("legal.validation.detail.case.Suit.No"));
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


	if ((compareDate(csefrmDate)) > compareDate(csetoDate)) {
		errorList
				.push(getLocalMessage("legal.casePendency.validation.active.from.to.date"));
	}
	
	
	if (CasedSuitNo == -1) {
		CasedSuitNo = "X";
	}


	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData =  '&CasedSuitNo=' + CasedSuitNo + '&csefrmDate='
				+ csefrmDate + '&csetoDate=' + csetoDate ;

		var URL = 'CaseImplementationDetailsReport.html?GetCaseReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}
function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}


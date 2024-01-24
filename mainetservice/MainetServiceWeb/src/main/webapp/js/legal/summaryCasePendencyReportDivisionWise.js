/**
 * 
 */

$(document).ready(function() {

	$("#cseDivWise").val(-1);
	$("#crtId").val();
	

	$("#resetReportForm").on("click", function() {
		window.location.reload("#advListGenderWise")
	});
	
	// JS to display "ALL" as the first option in the select drop-down
	$('select').on('mouseenter focus', function(){
		var codeAll = $(this).children('option[code="ALL"]');
		codeAll.insertAfter($(this).find('option[value="0"]'));
	});
});

function saveForm(obj) {

	var errorList = [];
	var cseDivisionType = $("#cseDivWise").val();
	var cseCourtId = $("#crtId").val();

	if (cseDivisionType == 0 || cseDivisionType == ""
			|| cseDivisionType == undefined) {
		errorList.push(getLocalMessage("legal.validation.summary.Division"));
	}
	if (cseCourtId == 0 || cseCourtId == "" || cseCourtId == undefined) {
		errorList.push(getLocalMessage("legal.validation.summary.Court"));
	}

	if (cseDivisionType == -1) {
		cseDivisionType = 0;
	}
	/*
	 * if(errorList.length > 0) { $("#errorDiv").show();
	 * displayErrorsOnPage(errorList); } else { $("#errorDiv").hide();
	 * window.open("SummaryCasePendencyReport.html?GetSummaryCaseReport&cseDivisionType="+cseDivisionType +
	 * "&cseCourtId="+cseCourtId , "_blank"); }
	 */

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = '&cseDivisionType=' + cseDivisionType
				+ '&cseCourtId=' + cseCourtId;
		var URL = 'SummaryCasePendencyReport.html?GetSummaryCaseReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}
}

/* gender wise advocate list report */
function saveGenderAdvForm(obj) {
	
	var errorList = [];

	var advGen = $("#gender").val();
	var courtIde = $("#crtId").val();

	if (advGen == 0 || advGen == undefined || advGen == "") {
		errorList.push(getLocalMessage("legal.report.gender.validation"));
	}
	if (courtIde == 0 || courtIde == undefined || courtIde == "") {
		errorList.push(getLocalMessage("legal.validation.summary.Court"));
	}

	if (advGen == -1) {
		advGen = 0;
	}
	if (courtIde == -1) {
		courtIde = 0;
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData1 = {
			"advGen" : advGen,
			"courtIde" : courtIde

		};

		var URL = 'SummaryCasePendencyReport.html?getGenderAdvList';
		var returnData1 = __doAjaxRequest(URL, 'POST', requestData1, false);
		window.open(returnData1, '_blank');

	}

}

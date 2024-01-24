/**
 * 
 */
$(document).ready(function() {

/*	$("#suitNo").val();
	$("#cseDivWise").val();
	$("#AdvName").val();
	$("#crtType").val();*/
	
	// JS to display "ALL" as the first option in the select drop-down
	$('select').on('mouseenter focus', function(){
		var codeAll = $(this).children('option[code="ALL"]');
		codeAll.insertAfter($(this).find('option[value="0"]'));
	});
});
/*function resetForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#DetailCaseFormReport").validate().resetForm();
}*/
function saveForm(obj) {
	
	
	var errorList = [];
	var CasedSuitNo = $("#suitNo").val();
	var cseDivision = $("#cseDivWise").val();
	var cseAdvocateId = $("#AdvName").val();
	var cseCourtId = $("#crtType").val();
	

	if (CasedSuitNo == 0 || CasedSuitNo == null || CasedSuitNo == "") {
		errorList.push(getLocalMessage("legal.validation.detail.case.Suit.No"));
	}
	if (cseDivision == 0 || cseDivision == null || cseDivision == "") {
		errorList
				.push(getLocalMessage("legal.validation.detail.case.Division"));
	}
	if (cseAdvocateId == 0 || cseAdvocateId == null || cseAdvocateId == "") {
		errorList
				.push(getLocalMessage("legal.validation.detail.case.Advocate"));
	}
	if (cseCourtId == 0 || cseCourtId == null || cseCourtId == "") {
		errorList.push(getLocalMessage("legal.validation.detail.case.Court"));
	}
	if (CasedSuitNo == -1) {
		CasedSuitNo = 0;
	}

	if (cseDivision == -1) {
		cseDivision = 0;
	}
	if (cseAdvocateId == -1) {
		cseAdvocateId = 0;
	}
	if (cseCourtId == -1) {
		cseCourtId = 0;
	}
	/*
	 * if(errorList.length>0){ $("#errorDiv").show();
	 * displayErrorsOnPage(errorList); }
	 * 
	 * else { $("#errorDiv").hide();
	 * window.open("DetailCaseReport.html?GetDetailReport&CseSuitNo="+CaseSuitNo +
	 * "&cseDivision="+Division +"&cseAdvocateId="+ AdvocateId
	 * +"&cseCourtId="+CourtId, "_blank"); }
	 */


	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = '&CasedSuitNo=' + CasedSuitNo + '&cseDivision='
				+ cseDivision + '&cseAdvocateId=' + cseAdvocateId
				+ '&cseCourtId=' + cseCourtId;
		var URL = 'DetailCaseReport.html?GetCaseReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}

}

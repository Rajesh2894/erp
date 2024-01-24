$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
});

function openaddSurveyReport(id, formUrl, actionParam) {
	
	var divName = '.content-page';
	var data = {
		"locId" : id
	}
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function searchSurvey(formUrl, actionParam) {
	
	var errorList = [];
	var code = $('#sType option:selected').attr('code');
	if (code == "GVP" || code == "BS" || code == "ODS" || code == "BWG"
			|| code == "RWA" || code == "PG" || code == "PT") {
		var typ = $('#sType').val();
		var data = {
			"id" : $('#sType').val()
		}
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data,
				'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		$('#sType').val(typ);
		prepareTags();
	}else{
		errorList.push(getLocalMessage("swm.survey.entry.form.validation"));
		displayErrorsOnPage(errorList);
	}
}

function Proceed(element) {
	var errorList = [];
	var errorList = validateForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		return saveOrUpdateForm(element, getLocalMessage('swm.success.add'),
				'SurveyReportMaster.html', 'saveform');
	}

}

function validateForm(errorList) {
	var locName = $("#locName").val();
	var locAddress = $("#locAddress").val();
	var codWard1 = $("#codWard1").val();
	var codWard2 = $("#codWard2").val();
	var codWard3 = $("#codWard3").val();
	var codWard4 = $("#codWard4").val();
	var codWard5 = $("#codWard5").val();
	var sName = $("#sName").val();
	var sMobNo = $("#sMobNo").val();
	var pName = $("#pName").val();
	var cName = $("#cName").val();
	var remark = $("#remark").val();
	var suDate = $("#suDate").val();

	if (locName == "" || locName == null)
		errorList.push(getLocalMessage("swm.survey.validation.locationName"));
	if (locAddress == "" || locAddress == null)
		errorList
				.push(getLocalMessage("swm.survey.validation.locationAddress"));
	if (codWard1 == "0" || codWard1 == null && (typeof codWard1 !== "undefined")) {
		errorList.push(getLocalMessage("swm.survey.validation.zone"));
	}
	
	if (codWard2 == "0" || codWard2 == null && (typeof codWard2 !== "undefined")) {
		errorList.push(getLocalMessage("swm.survey.validation.ward"));
	}
	
	if (codWard3 == "0" || codWard3 == null && (typeof codWard3 !== "undefined")) {
		errorList.push(getLocalMessage("swm.survey.validation.block"));
	}
	if (codWard4 == "0" || codWard4 == null && (typeof codWard4 !== "undefined")) {
		errorList.push(getLocalMessage("swm.survey.validation.route"));
	}
	
	if (sName == "" || sName == null)
		errorList.push(getLocalMessage("swm.survey.validation.supervisorName"));
	if (sMobNo == "" || sMobNo == null)
		errorList
				.push(getLocalMessage("swm.survey.validation.supervisorMobNo"));
	if (suDate == "" || suDate == null)
		errorList.push(getLocalMessage("swm.survey.validation.date"));
	return errorList;

}

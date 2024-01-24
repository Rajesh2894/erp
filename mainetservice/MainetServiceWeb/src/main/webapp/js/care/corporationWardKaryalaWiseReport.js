$(document).ready(function() {

	$("#corporationType").hide();
	var radios = $('input:radio[Value]');

	/*
	 * if (radios.is(':checked') === false) {
	 * radios.filter('[value]').prop('checked', false); }
	 */
	// getReportType();
});

function getReportType() {
	
	var radiovalue = $('input[type=radio]:checked').val();

	if (radiovalue == 'S') {
		$('#corporationType').show();

	} else {
		$('#corporationType').show();

	}
}

function saveCorporationForm(obj) {

	var errorList = [];

	var corporationName = $('input[type=radio]:checked').val();
	var corporationServiceUlb = $(
			'input[name="careReportRequest.reports"]:checked',
			'#careCorporationReports').val();

	if (corporationName == "" || corporationName == undefined
			|| corporationName == null) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}

	else {
		if (corporationServiceUlb == "" || corporationServiceUlb == undefined
				|| corporationServiceUlb == null) {
			errorList
					.push(getLocalMessage("property.validation.collection.report.type"));
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = {
			"corporationName" : corporationName,
			"corporationServiceUlb" : corporationServiceUlb
		};

		var URL = 'CareReportBirtForm.html?getCorporationReports';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

$("#resetform").on("click", function() {
	window.location.reload("#careCorporationReports")
});

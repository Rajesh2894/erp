/** specific to skdcl * */
$(document).ready(function() {
	$('#mnassward1').val(-1);
	$('#mnassward2').val(-1);
	$('#mnassward3').val(-1);
	$('#mnassward4').val(-1);
	$('#mnassward5').val(-1);
	$('#proertyNo').val();
	$('#finalcialYear').val();

	var radios = $('input:radio[Value]');
	
	$("#resetForm").on("click", function() {
		window.location.reload("OutstandingForm")
	});

});

function getReportType() {
	var radiovalue = $('input[type=radio]:checked').val();
	if (radiovalue == 'S') {
		$('#detailType').hide();
		$('#proertyNo').hide();
	} else {
		$('#detailType').show();
		$('#proertyNo').show();
	}
}

function saveReportForm(obj) {

	var errorList = [];
	var wardZone1 = $('#mnassward1').val();
	var wardZone2 = $("#mnassward2").val();
	var wardZone3 = $("#mnassward3").val();
	var wardZone4 = $("#mnassward4").val();
	var wardZone5 = $("#mnassward5").val();
	var proertyNo = $("#proertyNo").val();
	var finyear = $('#finalcialYear').val();
	var ReportType = $('input[type=radio]:checked').val();

	if (ReportType == 'D') {
		if (wardZone1 == 0 || wardZone1 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone1"));
		}
		if (wardZone2 == 0 || wardZone2 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone2"));
		}
		if (wardZone3 == 0 || wardZone3 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone3"));
		}
		if (wardZone4 == 0 || wardZone4 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone4"));
		}
		if (wardZone5 == 0 || wardZone5 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone5"));
		}

		if (finyear == 0 || finyear == "" || finyear == undefined) {
			errorList.push(getLocalMessage("property.valid.finyear"));
		}

	} else if (ReportType == 'S') {

		if (wardZone1 == 0 || wardZone1 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone1"));
		}
		if (wardZone2 == 0 || wardZone2 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone2"));
		}
		if (wardZone3 == 0 || wardZone3 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone3"));
		}
		if (wardZone4 == 0 || wardZone4 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone4"));
		}
		if (wardZone5 == 0 || wardZone5 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone5"));
		}
		if (finyear == 0 || finyear == "" || finyear == undefined) {
			errorList.push(getLocalMessage("property.valid.finyear"));
		}

	}

	if (ReportType == "" || ReportType == undefined) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}

	if (proertyNo == "") {
		proertyNo = 'X';
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1, wardZ2, wardZ3, wardZ4, wardZ5;

		if ($("#mnassward1").val() == undefined) {
			wardZ1 = 0;
		} else {
			wardZ1 = $("#mnassward1").val();
		}
		if ($("#mnassward2").val() == undefined) {
			wardZ2 = 0;
		} else {
			wardZ2 = $("#mnassward2").val();
		}
		if ($("#mnassward3").val() == undefined) {
			wardZ3 = 0;
		} else {
			wardZ3 = $("#mnassward3").val();
		}
		if ($("#mnassward4").val() == undefined) {
			wardZ4 = 0;
		} else {
			wardZ4 = $("#mnassward4").val();
		}
		if ($("#mnassward5").val() == undefined) {
			wardZ5 = 0;
		} else {
			wardZ5 = $("#mnassward5").val();
		}

		if (wardZ1 == -1) {
			wardZ1 = 0;
		}
		if (wardZ2 == -1) {
			wardZ2 = 0;
		}
		if (wardZ3 == -1) {
			wardZ3 = 0;
		}
		if (wardZ4 == -1) {
			wardZ4 = 0;
		}
		if (wardZ5 == -1) {
			wardZ5 = 0;
		}
		var requestData = {
			"wardZone1" : wardZ1,
			"wardZone2" : wardZ2,
			"wardZone3" : wardZ3,
			"wardZone4" : wardZ4,
			"wardZone5" : wardZ5,
			"proertyNo" : proertyNo,
			"finyear" : finyear,
			"ReportType" : ReportType
		};

		var URL = 'DetailOutstandingRegister.html?getOutstandingReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);

		window.open(returnData, '_blank');

	}
}

/* for xlsx */

function saveXLSXReport(obj) {

	var errorList = [];
	var wardZone1 = $('#mnassward1').val();
	var wardZone2 = $("#mnassward2").val();
	var wardZone3 = $("#mnassward3").val();
	var wardZone4 = $("#mnassward4").val();
	var wardZone5 = $("#mnassward5").val();
	var proertyNo = $("#proertyNo").val();
	var finyear = $('#finalcialYear').val();
	var ReportType = $('input[type=radio]:checked').val();

	if (ReportType == 'D') {
		if (wardZone1 == 0 || wardZone1 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone1"));
		}
		if (wardZone2 == 0 || wardZone2 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone2"));
		}
		if (wardZone3 == 0 || wardZone3 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone3"));
		}
		if (wardZone4 == 0 || wardZone4 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone4"));
		}
		if (wardZone5 == 0 || wardZone5 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone5"));
		}
		if (finyear == 0 || finyear == "" || finyear == undefined) {
			errorList.push(getLocalMessage("property.valid.finyear"));
		}

	} else if (ReportType == 'S') {

		if (wardZone1 == 0 || wardZone1 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone1"));
		}
		if (wardZone2 == 0 || wardZone2 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone2"));
		}
		if (wardZone3 == 0 || wardZone3 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone3"));
		}
		if (wardZone4 == 0 || wardZone4 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone4"));
		}
		if (wardZone5 == 0 || wardZone5 == "") {
			errorList.push(getLocalMessage("property.validation.wardZone5"));
		}
		if (finyear == 0 || finyear == "" || finyear == undefined) {
			errorList.push(getLocalMessage("property.valid.finyear"));
		}

	}

	if (ReportType == "" || ReportType == undefined) {
		errorList
				.push(getLocalMessage("property.validation.collection.report.type"));
	}

	if (proertyNo == "") {
		proertyNo = 'X';
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1, wardZ2, wardZ3, wardZ4, wardZ5;

		if ($("#mnassward1").val() == undefined) {
			wardZ1 = 0;
		} else {
			wardZ1 = $("#mnassward1").val();
		}
		if ($("#mnassward2").val() == undefined) {
			wardZ2 = 0;
		} else {
			wardZ2 = $("#mnassward2").val();
		}
		if ($("#mnassward3").val() == undefined) {
			wardZ3 = 0;
		} else {
			wardZ3 = $("#mnassward3").val();
		}
		if ($("#mnassward4").val() == undefined) {
			wardZ4 = 0;
		} else {
			wardZ4 = $("#mnassward4").val();
		}
		if ($("#mnassward5").val() == undefined) {
			wardZ5 = 0;
		} else {
			wardZ5 = $("#mnassward5").val();
		}

		if (wardZ1 == -1) {
			wardZ1 = 0;
		}
		if (wardZ2 == -1) {
			wardZ2 = 0;
		}
		if (wardZ3 == -1) {
			wardZ3 = 0;
		}
		if (wardZ4 == -1) {
			wardZ4 = 0;
		}
		if (wardZ5 == -1) {
			wardZ5 = 0;
		}
		var requestData = {
			"wardZone1" : wardZ1,
			"wardZone2" : wardZ2,
			"wardZone3" : wardZ3,
			"wardZone4" : wardZ4,
			"wardZone5" : wardZ5,
			"proertyNo" : proertyNo,
			"finyear" : finyear,
			"ReportType" : ReportType
		};

		var URL = 'DetailOutstandingRegister.html?getXLSXReportOutstanding';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}

}

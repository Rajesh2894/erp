$(document).ready(function() {

	$('#codDwzid1').val();
	$('#codDwzid2').val();
	$('#codDwzid3').val();
	$('#codDwzid4').val();
	$('#codDwzid5').val();
	$('#trmGroup1').val();
	$('#trmGroup2').val();
	$('#trmGroup3').val();
	$('#trmGroup4').val();
	$('#trmGroup5').val();
	$('#csCcnsize').val();
	$('#csMeteredccn').val();
	$("#csCcn").val();
	$('input[type=radio]:checked').val();

	$("#resetOutform").on("click", function() {
		window.location.reload("#OutsadignReportForm")
	});

	getReportType();
});

function getReportType() {
	var radiovalue = $('input[type=radio]:checked').val();
	if (radiovalue == 'C') {
		$('#form1').show();
		$('#form2').hide();
		$('#form3').hide();
		$('#form4').hide();
		$('#Detailtype').show();
	} else if (radiovalue == 'D') {
		$('#Detailtype').hide();
		$('#form1').show();
		$('#form2').show();
		$('#form3').show();
		$('#form4').show();
	} else if (radiovalue == 'S') {
		$('#Detailtype').hide();
		$('#form1').show();
		$('#form2').show();
		$('#form3').show();
		$('#form4').show();

	} else {
		$('#form1').show();
		$('#form2').show();
		$('#form3').show();
		$('#form4').show();
	}
}

function saveOutstandForm(obj) {
	
	var errorList = [];
	var wardZone1 = $('#codDwzid1').val();
	var wardZone2 = $("#codDwzid2").val();
	var wardZone3 = $("#codDwzid3").val();
	var wardZone4 = $("#codDwzid4").val();
	var wardZone5 = $("#codDwzid5").val();
	var trmgrp = $("#trmGroup1").val();
	var trmgrp2 = $("#trmGroup2").val();
	var trmgrp3 = $("#trmGroup3").val();
	var trmgrp4 = $("#trmGroup4").val();
	var trmgrp5 = $("#trmGroup5").val();
	var CcnSize = $("#csCcnsize").val();
	var csMeteredccn = $("#csMeteredccn").val();
	var csCcn = $("#csCcn").val();
	var ReportType = $('input[type=radio]:checked').val();

	if (ReportType == 'D' || ReportType == 'S') {

		if (wardZone1 == 0 || wardZone1 == "") {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward1"));
		}

		if (wardZone2 == 0 || wardZone2 == "") {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward2"));
		}

		if (wardZone3 == 0 || wardZone3 == "") {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward3"));
		}

		if (wardZone4 == 0 || wardZone4 == "") {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward4"));
		}

		if (wardZone5 == 0 || wardZone5 == "") {
			errorList
					.push(getLocalMessage("water.meterReadingReport.validation.list.Zone/Ward5"));
		}

		if (trmgrp == 0 || trmgrp == "") {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category"));
		}

		if (trmgrp2 == 0 || trmgrp2 == "") {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category2"));
		}
		if (trmgrp3 == 0 || trmgrp3 == "") {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category3"));
		}
		if (trmgrp4 == 0 || trmgrp4 == "") {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category4"));
		}
		if (trmgrp5 == 0 || trmgrp5 == "") {
			errorList
					.push(getLocalMessage("water.validation.list.tarrif.category5"));
		}

		if (CcnSize == 0) {
			errorList
					.push(getLocalMessage('water.report.validation.connectionsize'));
		}
		if (csMeteredccn == 0) {
			errorList
					.push(getLocalMessage('water.report.validation.meter/non-metered'));
		}

	}
	if (ReportType == 'C') {
		if (csCcn == "") {
			errorList
					.push(getLocalMessage('water.meterCutOff.validationMSG.connectionNo'));
		}
	}

	if (CcnSize == -1) {
		CcnSize = 0;
	}
	if (csMeteredccn == -1) {
		csMeteredccn = 0;
	}

	if (csCcn == "") {
		csCcn = 'X';
	}

	if (ReportType == "" || ReportType == undefined) {
		errorList.push(getLocalMessage('water.report.validation.reporttype'));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1, wardZ2, wardZ3, wardZ4, wardZ5;
		var tarCategory1, tarCategory2, tarCategory3, tarCategory4, tarCategory5;

		if ($("#codDwzid1").val() == undefined) {
			wardZ1 = 0;
		} else {
			wardZ1 = $("#codDwzid1").val();
		}

		if ($('#codDwzid2').val() == undefined) {
			wardZ2 = 0;
		} else {
			wardZ2 = $("#codDwzid2").val();
		}

		if ($('#codDwzid3').val() == undefined) {
			wardZ3 = 0;
		} else {
			wardZ3 = $("#codDwzid3").val();
		}

		if ($('#codDwzid4').val() == undefined) {
			wardZ4 = 0;
		} else {
			wardZ4 = $("#codDwzid4").val();
		}

		if ($('#codDwzid5').val() == undefined) {
			wardZ5 = 0;
		} else {
			wardZ5 = $("#codDwzid5").val();
		}
		if ($("#trmGroup1").val() == undefined) {
			tarCategory1 = 0;
		} else {
			tarCategory1 = $("#trmGroup1").val();
		}
		if ($("#trmGroup2").val() == undefined) {
			tarCategory2 = 0;
		} else {
			tarCategory2 = $("#trmGroup2").val();
		}
		if ($("#trmGroup3").val() == undefined) {
			tarCategory3 = 0;
		} else {
			tarCategory3 = $("#trmGroup3").val();
		}
		if ($("#trmGroup4").val() == undefined) {
			tarCategory4 = 0;
		} else {
			tarCategory4 = $("#trmGroup4").val();
		}
		if ($("#trmGroup5").val() == undefined) {
			tarCategory5 = 0;
		} else {
			tarCategory5 = $("#trmGroup5").val();
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
		if (tarCategory1 == -1) {
			tarCategory1 = 0;
		}
		if (tarCategory2 == -1) {
			tarCategory2 = 0;
		}
		if (tarCategory3 == -1) {
			tarCategory3 = 0;
		}
		if (tarCategory4 == -1) {
			tarCategory4 = 0;
		}
		if (tarCategory5 == -1) {
			tarCategory5 = 0;
		}
		var requestData = "&csCcn=" + csCcn + "&ReportType=" + ReportType
				+ "&wardZone1=" + wardZ1 + "&wardZone2=" + wardZ2
				+ "&wardZone3=" + wardZ3 + "&wardZone4=" + wardZ4
				+ "&wardZone5=" + wardZ5 + "&trmgrp=" + tarCategory1
				+ "&trmgrp2=" + tarCategory2 + "&trmgrp3=" + tarCategory3
				+ "&trmgrp4=" + tarCategory4 + "&trmgrp5=" + tarCategory5
				+ "&CcnSize=" + CcnSize + "&csMeteredccn=" + csMeteredccn;

		var URL = 'waterOutstandingRegister.html?getOutstandingWaterReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

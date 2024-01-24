$(document).ready(function() {

	$('#hoardingZone1').val(-1);
	$('#hoardingZone2').val(-1);
	$('#hoardingZone3').val(-1);
	$('#hoardingZone4').val(-1);
	$('#hoardingZone5').val(-1);
	// $('#finalcialYear').val()
	$('#tillDueDate').val();

	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});

	$("#tillDueDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
});

function viewDNRform(obj) {

	var errorList = [];
	var hoardingZone1 = $("#hoardingZone1").val();
	var hoardingZone2 = $("#hoardingZone2").val();
	var hoardingZone3 = $("#hoardingZone3").val();
	var hoardingZone4 = $("#hoardingZone4").val();
	var hoardingZone5 = $("#hoardingZone5").val();
	// var finalcialYear = $("#finalcialYear").val();
	var dueDate = $('#tillDueDate').val();

	if (hoardingZone1 == "" || hoardingZone1 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone1"));

	}
	if (hoardingZone2 == "" || hoardingZone2 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone2"));

	}

	if (hoardingZone3 == "" || hoardingZone3 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone3"));

	}
	if (hoardingZone4 == "" || hoardingZone4 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone4"));

	}
	if (hoardingZone5 == "" || hoardingZone5 == 0) {
		errorList.push(getLocalMessage("adh.report.validn.hoardingZone5"));

	}

	/*
	 * if (finalcialYear == "") {
	 * errorList.push(getLocalMessage("adh.validate.financial.year")); }
	 */

	if (dueDate == "") {
		errorList.push(getLocalMessage("adh.validate.till.due.date"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var hoardingWz1;
		var hoardingWz2;
		var hoardingWz3;
		var hoardingWz4;
		var hoardingWz5;

		if ($("#hoardingZone1").val() == undefined) {
			hoardingWz1 = 0;
		} else {
			hoardingWz1 = $("#hoardingZone1").val();
		}
		if ($("#hoardingZone2").val() == undefined) {
			hoardingWz2 = 0;
		} else {
			hoardingWz2 = $("#hoardingZone2").val();
		}
		if ($("#hoardingZone3").val() == undefined) {
			hoardingWz3 = 0;
		} else {
			hoardingWz3 = $("#hoardingZone3").val();
		}
		if ($("#hoardingZone4").val() == undefined) {
			hoardingWz4 = 0;
		} else {
			hoardingWz4 = $("#hoardingZone4").val();
		}
		if ($("#hoardingZone5").val() == undefined) {
			hoardingWz5 = 0;
		} else {
			hoardingWz5 = $("#hoardingZone5").val();
		}

		if (hoardingWz1 == -1) {
			hoardingWz1 = 0;
		}
		if (hoardingWz2 == -1) {
			hoardingWz2 = 0;
		}
		if (hoardingWz3 == -1) {
			hoardingWz3 = 0;
		}
		if (hoardingWz4 == -1) {
			hoardingWz4 = 0;
		}
		if (hoardingWz5 == -1) {
			hoardingWz5 = 0;
		}
		var requestData = {

			"hoardingZone1" : hoardingWz1,
			"hoardingZone2" : hoardingWz2,
			"hoardingZone3" : hoardingWz3,
			"hoardingZone4" : hoardingWz4,
			"hoardingZone5" : hoardingWz5,
			"dueDate" : dueDate
		/* "finalcialYear" : finalcialYear*/

		};

		var URL = 'DemandNoticeRegister.html?GetDemandNotice';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}

}

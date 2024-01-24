$(document).ready(function() {

	$("#agencyStatus").val(-1);
	$("#licenseType").val(-1);
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
	});
	$("#fromdate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#todate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
});

function viewAdvReport(obj) {

	var errorList = [];
	var fromDate = $("#fromdate").val();
	var toDate = $("#todate").val();
	var licstatus = $("#agencyStatus").val();
	var lictype = $(licenseType).val();

	var fromDt = moment(fromDate, "DD.MM.YYYY HH.mm").toDate();
	var toDt = moment(toDate, "DD.MM.YYYY HH.mm").toDate();
	if (fromDate == "") {
		errorList.push(getLocalMessage("adh.validate.from.date"));
	}
	if (toDate == "") {
		errorList.push(getLocalMessage("adh.validate.to.date"));
	}
	if (licstatus == "" || licstatus == 0) {
		errorList.push(getLocalMessage("hoarding.master.validate.status"));
	}

	if (lictype == "" || lictype == 0) {
		errorList.push(getLocalMessage("adh.validate.licanse.type"));
	}

	if (fromDate == null) {
		errorList = validatedate(errorList, 'fromdate');
	}
	if (toDate != null) {
		errorList = validatedate(errorList, 'todate');
	}
	if ((fromDt.getTime()) > (toDt.getTime())) {
		errorList.push(getLocalMessage("adh.compare.from.to.date"));
	}
	if (licstatus == -1) {
		licstatus = 'X';
	}

	if (lictype == -1) {
		lictype = 0;
	}

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = {
			"fromDate" : fromDate,
			"toDate" : toDate,
			"licstatus" : licstatus,
			"lictype" : lictype
		};

		var URL = 'AdvertisementsRegister.html?GetAdvReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		if (returnData == "f") {
			errorList.push(getLocalMessage("adh.validation.financialYrDate"));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}

	}
}

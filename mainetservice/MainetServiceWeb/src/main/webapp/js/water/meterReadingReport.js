/**
 * 
 */
$(document).ready(function() {

	$('#csFromdt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#csFromdt").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#csTodt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#csTodt").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
});
function resetForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#MeterReadingForm").validate().resetForm();
}
function saveForm(obj) {

	var errorList = [];

	var ConnNoFrom = $("#csCcnFrom").val();
	var ConnNoTo = $("#csCcnTo").val();

	if (ConnNoFrom != "" || ConnNoFrom != 0 || ConnNoFrom != null) {
		if (ConnNoTo == 0) {
			errorList
					.push(getLocalMessage("water.defaulter.validation.ConnectionTo"));
		}
	}

	if (ConnNoTo != "" || ConnNoTo != 0 || ConnNoTo != null) {
		if (ConnNoFrom == 0) {
			errorList
					.push(getLocalMessage("water.defaulter.validation.ConnectionFrom"));
		}
	}

	if ((parseFloat(ConnNoTo) < parseFloat(ConnNoFrom))
			&& (ConnNoFrom != "" || ConnNoFrom != 0)
			&& (ConnNoTo != "" || ConnNoTo != 0)) {
		errorList
				.push(getLocalMessage("water.defaulter.validation.ConnectionFromTo"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = '&ConnNoFrom=' + ConnNoFrom + '&ConnNoTo=' + ConnNoTo;

		var URL = 'meterReading.html?GetMeterReadingReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}
}
function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);

}

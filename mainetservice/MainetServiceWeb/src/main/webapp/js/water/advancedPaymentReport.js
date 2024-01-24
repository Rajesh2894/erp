/**
 * 
 */
$(document).ready(function() {
	$('#csCcnFrom').val();
	$('#csCcnTo').val();

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
	
	$("#resetForm").on("click", function() {
		window.location.reload("#AdvancedForm")
	})
});
function resetForm() {

	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#AdvancedForm").validate().resetForm();
}
function saveForm(obj) {

	var errorList = [];
	var csFromdt = $("#csFromdt").val();
	var csTodt = $("#csTodt").val();
	var ConnNoFrom = $("#csCcnFrom").val();
	var ConnNoTo = $("#csCcnTo").val();

	if (csFromdt == "") {
		errorList.push(getLocalMessage("water.validation.list.from.date"));
	}
	if (csTodt == "") {
		errorList.push(getLocalMessage("water.validation.list.to.date"));
	}

	if ((compareDate(csFromdt)) > compareDate(csTodt)) {
		errorList.push(getLocalMessage("water.validation.list.from.to.date"));
	}

	if (ConnNoFrom != "" || ConnNoFrom != 0) {
		if (ConnNoTo == 0) {
			errorList
					.push(getLocalMessage("water.defaulter.validation.ConnectionTo"));
		}
	}

	if (ConnNoTo != "" || ConnNoTo != 0) {
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

	if (ConnNoFrom == "" || ConnNoFrom == null) {
		ConnNoFrom = 0;
	}
	if (ConnNoTo == "" || ConnNoTo == null) {
		ConnNoTo = 0;
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = '&csFromdt=' + csFromdt + '&csTodt=' + csTodt
				+ '&ConnNoFrom=' + ConnNoFrom + '&ConnNoTo=' + ConnNoTo;

		var URL = 'advancePaymentReport.html?GetAdvancedReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		if (returnData == "f") {
			errorList.push(getLocalMessage('water.report.validation.FYCheck'));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}
	}
}
function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);

}

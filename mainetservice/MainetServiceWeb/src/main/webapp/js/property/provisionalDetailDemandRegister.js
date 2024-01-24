/**
 * 
 */
$(document).ready(function() {
	$("#save").hide();

	$("#demandGenerate").on("click", function() {
		var $this = $(this);
		$this.button('loading');
		setTimeout(function() {
			generateBillAndSave(this);
			$this.button('reset');
		}, 80);
	})
	$('#mnassward1').val(-1);
	$('#mnassward2').val(-1);
	$('#mnassward3').val(-1);
	$('#mnassward4').val(-1);
	$('#mnassward5').val(-1);
	/*
	 * $('#mnFromdt').datepicker({ dateFormat : 'dd/mm/yy', changeMonth : true,
	 * changeYear : true });
	 */
	/* var minFinDate =$("#minFinDate").val(); */
	var maxFinDate = $("#maxFinDate").val();

	$('#mnFromdt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : 0,
		maxDate : 0,
	});

	/*
	 * $('#mnTodt').datepicker({ dateFormat : 'dd/mm/yy', changeMonth : true,
	 * changeYear : true, minDate : minFinDate, maxDate : maxFinDate, });
	 */

	$("#mnFromdt").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	/*
	 * $('#mnTodt').datepicker({ dateFormat : 'dd/mm/yy', changeMonth : true,
	 * changeYear : true });
	 * 
	 * $("#mnTodt").keyup(function(e) { if (e.keyCode != 8) { if
	 * ($(this).val().length == 2) { $(this).val($(this).val() + "/"); } else if
	 * ($(this).val().length == 5) { $(this).val($(this).val() + "/"); } } });
	 */
});

function resetForm() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#DemandFormReport").validate().resetForm();
}
function resetFormDetails() {
	$("#demandGenerate").show();
	$("#save").hide();
	$('input[type=text]').val('');
	$("#DemandFormReport").validate().resetForm();
}



function saveForm(obj) {

	var errorList = [];
	var wardZone1 = $('#mnassward1').val();
	var wardZone2 = $("#mnassward2").val();
	var wardZone3 = $("#mnassward3").val();
	var wardZone4 = $("#mnassward4").val();
	var wardZone5 = $("#mnassward5").val();
	var mnFromdt = $("#mnFromdt").val();
	/* var mnTodt = $("#mnTodt").val(); */

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
	if (mnFromdt =="" ) {
		errorList.push(getLocalMessage("property.provisional.Demand.asOnDate"));
	}
	/*
	 * if (mnTodt == "") { errorList
	 * .push(getLocalMessage("property.provisional.validation.outstanding.to.date")); }
	 * 
	 * if ((compareDate(mnFromdt)) > (compareDate(mnTodt))) { errorList
	 * .push(getLocalMessage("property.provisional.validation.outstanding.from.to.date")); }
	 */

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var wardZ1;
		var wardZ2;
		var wardZ3;
		var wardZ4;
		var wardZ5;

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
			"mnFromdt" : mnFromdt,
		/*
		 * "mnTodt" : $("#mnTodt").val(), "mnFromdt" : $("#mnFromdt").val(),
		 */
		}
		var URL = 'ProvisionalDemand.html?GetProvision';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');

	}
}

function compareDate(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function generateBillAndSave(element) {

	var errorList = [];
	errorList = validateFormData(errorList);
	if (errorList.length == 0) {
		var theForm = '#DemandFormReport';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'ProvisionalDemand.html?generateBillAndSave';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		$("#save").show();
		$("#demandGenerate").hide();
	} else {
		displayErrorsOnPage(errorList);
	}
}
function validateFormData(errorList) {

	var wardZone1 = $('#mnassward1').val();
	var wardZone2 = $("#mnassward2").val();
	var wardZone3 = $("#mnassward3").val();
	var wardZone4 = $("#mnassward4").val();
	var wardZone5 = $("#mnassward5").val();
	var mnFromdt = $("#mnFromdt").val();

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
	if (mnFromdt == "" || mnFromdt == null) {
		errorList
				.push(getLocalMessage("property.provisional.validation.asOnDate"));
	}
	return errorList;
}

function saveFormDetails(obj) {

	var requestData = {};
	var URL = 'ProvisionalDemand.html?viewDemandRegisterReport';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
	window.open(returnData, '_blank');
	$("#save").hide()
}
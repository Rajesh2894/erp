/**
 * 
 */
$(document).ready(function() {
	
	$("#mnassward1").val(-1);
	$("#mnassward2").val(-1);

	$('#mnFromdt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#mnFromdt").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#mnTodt').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#mnTodt").keyup(function(e) {
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
	$("#OutstandingFormReport").validate().resetForm();
}

function saveForm(obj) {

	var errorList = [];
	var zoneLevel = $("#mnassward1").val();
	var wardLevel = $("#mnassward2").val();
	var mnFromdt = $("#mnFromdt").val();
	var mnTodt = $("#mnTodt").val();

	if (zoneLevel == 0) {
		errorList.push(getLocalMessage("property.provisional.validation.outstanding.zone"));
	}
	if (wardLevel == 0) {
		errorList.push(getLocalMessage("property.provisional.validation.outstanding.ward"));
	}
	if (mnFromdt == 0) {
		errorList.push(getLocalMessage("property.provisional.validation.outstanding.from.date"));
	}
	if (mnTodt == 0) {
		errorList.push(getLocalMessage("property.provisional.validation.outstanding.to.date"));
	}
	if ((compareDate(mnFromdt)) > (compareDate(mnTodt))) {
		errorList.push(getLocalMessage("property.validation.outstanding.from.to.date"));
	}
	
	if(zoneLevel==-1){
		zoneLevel=0;
	}
	if(wardLevel==-1){
		wardLevel=0;
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();
		var requestData = "&zoneLevel=" + zoneLevel + "&wardLevel=" + wardLevel
				+ '&mnFromdt=' + mnFromdt + '&mnTodt=' + mnTodt;
		var URL = 'ProvisionalDetailOutstanding.html?GetProvisionalOutstanding';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		window.open(returnData, '_blank');
	}
}
function compareDate(date) {
	
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}
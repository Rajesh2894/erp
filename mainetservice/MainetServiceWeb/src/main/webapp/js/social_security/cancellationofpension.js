var url = "CancellationofPension.html";
$(document).ready(function() {
	$("#cancelDateId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		//yearRange : "-100:-0"
	});

	$("#cancelDateId").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#resetform").on("click", function() {
		window.location.reload("#cancelPensionId")
	});
});

function cancelPensionValidation(errorList) {
	var benefnumNname = $("#benefnumNname").val();
	var pensionCancelReason = $("#pensionCancelReason").val();
	var cancelDateId = $("#cancelDateId").val();

	if (benefnumNname == "0" || benefnumNname == undefined
			|| benefnumNname == '') {
		errorList.push(getLocalMessage('social.sec.dropdown.benefnoNname.req'));
	}

	if (pensionCancelReason == "0" || pensionCancelReason == undefined
			|| pensionCancelReason == '') {
		errorList.push(getLocalMessage('social.sec.cancelreason.req'));
	}

	if (cancelDateId == "0" || cancelDateId == undefined || cancelDateId == '') {
		errorList.push(getLocalMessage('social.sec.canceldate.req'));
	}

	return errorList;
}
// function to save pension cancellation form
function saveCancellationForm(element) {
	var errorList = [];
	errorList = cancelPensionValidation(errorList);
	if (errorList.length == 0) {

		return saveOrUpdateForm(element, "Form Saved Successfully",
				'CancellationofPension.html', 'saveCancelPension');

		$(divName).html(response);
	} else {
		displayErrorsOnPage(errorList);
	}
}

// function to get data on selecting dropdown of beneficiary name and applicant name
$(document).on('change', '#benefnumNname', function() {
	var errorList = [];
	var theForm = '#cancelPensionId';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'CancellationofPension.html?getPensionDetails';
	var response = __doAjaxRequest(URL, 'POST', requestData, false);
	$(formDivName).html(response);
});

// function for reset form
function resetRenewalForm() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#cancelPensionId").validate().resetForm();
}

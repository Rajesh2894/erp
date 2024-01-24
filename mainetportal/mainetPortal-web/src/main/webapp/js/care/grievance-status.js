$(document).ready(function() {

	$("#searchString").on("keypress", function(e) {
		// preventing enter key event to to default action (Submit form to
		// action URL)
		if (e.which === 13) {
			e.preventDefault();
			submitPreLoginForm();
		}
	});

	$("#tokenNumber").on("keypress", function(e) {
		// preventing enter key event to to default action (Submit form to
		// action URL)
		if (e.which === 13) {
			e.preventDefault();
			submitAfterLoginForm();
		}
	});

	$('#Token').hide();
	$('#Mobile').hide();
	$('#buttonAction').hide();

	$('#resetCustom').click(function(event) {
		$('#token-no').val('');
		$('#status').html('');
		$('#errorDivId').hide();
	});

});

function getAckStatus(tokenNo) {

	var url = "grievance.html?displayStatus";
	var requestData = 'searchString=' + tokenNo;
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	if (response == '') {
		errorList.push(getLocalMessage('care.token.invalid'));
	} else {
		$('#status').html(response);
		getAllFunctions();
	}
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('.error-div').html(errMsg);
	$(".error-div").show();

	return false;
}

function submitPreLoginForm(event) {

	var errorList = [];
	$('#status').html('');
	$('#errorDivId').hide();

	var searchString = $('#searchString').val();
	if (searchString == null || searchString == "") {
		errorList.push(getLocalMessage('care.token.empty'));
	}

	if (errorList.length == 0) {
		var url = "grievance.html?displayComplaintStatus";
		var requestData = {};
		requestData = 'searchString=' + searchString;
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
		if (response == '') {
			errorList.push(getLocalMessage('care.token.invalid'));
		} else {
			$('#status').html(response);
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}

}

function submitAfterLoginForm(event) {

	var errorList = [];
	$('#status').html('');
	$('#errorDivId').hide();
	var tokenNo = $('#tokenNumber').val();

	if (tokenNo == null || tokenNo == '') {
		errorList.push(getLocalMessage('care.token.empty'));
	}

	if (errorList.length == 0) {
		var url = "grievance.html?displayStatus";
		var requestData = 'searchString=' + $('#tokenNumber').val();
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
		if (response == '') {
			errorList.push(getLocalMessage('care.token.invalid'));
		} else {
			$('#status').html(response);
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}

}

function displayComplaintStatus(event) {

	var errorList = [];
	$('#status').html('');
	$('#errorDivId').hide();

	var searchString = $('#searchString').val().trim();
	if (searchString == null || searchString == "") {
		errorList.push(getLocalMessage('care.token.empty'));
	}

	if (errorList.length == 0) {
		var url = "grievance.html?displayComplaintStatus";
		var requestData = {};
		requestData = 'searchString=' + searchString;
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
		if (response == '') {
			errorList.push(getLocalMessage('care.token.invalid'));
		} else {
			$('#status').html(response);
		}
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}

}

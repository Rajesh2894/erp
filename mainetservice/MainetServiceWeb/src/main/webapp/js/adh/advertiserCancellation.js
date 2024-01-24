$(document).ready(function() {

    $('#cancellationDate').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	minDate : '0',
	maxDate : '0',
    });
    $('#AdvertiserCancellation').validate({
	onkeyup : function(element) {
	    this.element(element);
	    console.log('onkeyup fired');
	},
	onfocusout : function(element) {
	    this.element(element);
	    console.log('onfocusout fired');
	}
    });

});

$("#cancellationDate").keyup(function(e) {
    if (e.keyCode != 8) {
	if ($(this).val().length == 2) {
	    $(this).val($(this).val() + "/");
	} else if ($(this).val().length == 5) {
	    $(this).val($(this).val() + "/");
	}
    }
});

function searchAdvertiserNameByLicNo() {
    var errorList = [];
    var requestData = {
	"agencyLicNo" : $("#agencyLicNo").val()
    };
    $('#agencyName').val('');

    var ajaxResponse = doAjaxLoading(
	    'AdvertiserCancellation.html?searchAdvertiserNameByLicNo',
	    requestData, 'json');

    if (ajaxResponse == 'N') {
	errorList
		.push(getLocalMessage('adh.license.no.cancel'));
	displayErrorsOnPage(errorList);
    } else {
	$("#errorDiv").hide();
	$('#agencyName').val(ajaxResponse);
    }

}

function saveAdvertiser(element) {
    var errorList = [];
    var agencyLicNo = $("#agencyLicNo").val();
    errorList = validateForm(errorList);
    if (errorList.length > 0) {
	displayErrorsOnPage(errorList);
    }

    else {
	$.fancybox.close();
	var divName = '.content-page';

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var object = __doAjaxRequest(
		"AdvertiserCancellation.html?saveAdvertiserCancellation",
		'POST', requestData, false, 'json');

	if (object.error != null && object.error != 0) {
	    $.each(object.error, function(key, value) {
		$.each(value, function(key, value) {
		    if (value != null && value != '') {
			errorList.push(value);
		    }
		});
	    });
	    displayErrorsOnPage(errorList);
	} else {
		/*Defect #125451*/
		var errorMsg = getLocalMessage('adh.advertiser.cancel.licenseNo');
	    showBoxForApproval(errorMsg + agencyLicNo + " " + "successfully");
	}

    }
}

function validateForm(errorList) {
    var agencyLicNo = $("#agencyLicNo").val();
    var agencyName = $("#agencyName").val();
    var cancellationReason = $("#cancellationReason").val();
    var cancellationDate = $("#cancellationDate").val();

    if (agencyLicNo == "" || agencyLicNo == undefined || agencyLicNo == null
	    || agencyLicNo == '0') {
	errorList
		.push(getLocalMessage('advertiser.cancellation.validate.licNo'));
    }
    if (agencyName == "" || agencyName == undefined || agencyName == null) {
	errorList
		.push(getLocalMessage('advertiser.cancellation.validate.agency.name'));
    }
    if (cancellationDate == "" || cancellationDate == undefined
	    || cancellationDate == null) {
	errorList
		.push(getLocalMessage('advertiser.cancellation.validate.cancellation.date'));
    }
    if (cancellationReason == "" || cancellationReason == undefined
	    || cancellationReason == null) {
	errorList
		.push(getLocalMessage('advertiser.cancellation.validate.cancellation.reason'));
    }

    return errorList;

}

function showBoxForApproval(succesMessage) {

    var childDivName = '.msg-dialog-box';
    var message = '';
    var Proceed = getLocalMessage("works.management.proceed");
    var no = 'No';
    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
	    + '</p>';

    message += '<div class=\'text-center padding-bottom-10\'>'
	    + '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
	    + Proceed + '\'  id=\'Proceed\' ' + ' onclick="closeApproval();"/>'
	    + '</div>';
    $(childDivName).addClass('ok-msg').removeClass('warn-msg');
    $(childDivName).html(message);
    $(childDivName).show();
    $('#Proceed').focus();
    showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}
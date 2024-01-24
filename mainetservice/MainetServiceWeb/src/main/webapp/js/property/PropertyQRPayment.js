$(document).ready(function() {
	$('.lessthancurrdate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});

	prepareDateTag();
});

function prepareDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}

function SearchButton(obj) {
	window.location.href = getLocalMessage("paytm.dqr.homescreen");
	var errorList = [];
	errorList = ValidateDetails(errorList);
	if (errorList.length == 0) {
		var theForm = '#PropertyQRPaymentSearch';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var ajaxResponse = __doAjaxRequest(
				'PropertyQRPayment.html?getBillPaymentDetail', 'POST',
				requestData, false, 'html');
		$("#dataDiv").html(ajaxResponse);
		return false;

	} else {
		showErrorOnPage(errorList);
	}
}

function ValidateDetails(errorList) {

	var propNo = $("#assNo").val();
	var oldPropNo = $("#assOldpropno").val();
	var flatNo = $("#flatNo").val();
	if (propNo == "" && oldPropNo == "" && flatNo == "") {
		errorList.push(getLocalMessage("property.propAndPtinNo"));
	} else if (propNo != "" && oldPropNo != "" && flatNo != "") {
		errorList.push(getLocalMessage("property.propAndPtinNoEither"));
	}
	return errorList;

}
function showErrorOnPage(errorList) {
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
	return false;
}

function showQRCode(element, flag) {
	debugger;

	var errorList = [];
	var partialAmt = $("#payAmount").val();
	var emailId = $("#emailId").val();
	var mobileNo = $("#mobileNo").val();
	if (partialAmt == '' || partialAmt == undefined || (partialAmt <= 0)) {
		errorList.push(getLocalMessage("property.billPayment.amount"));
	}
	if (emailId != "") {
		var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
		var valid = emailRegex.test(emailId);
		if (!valid) {
			errorList.push(getLocalMessage('update.invalid.email'));
		}
	}
	if (mobileNo != '' && mobileNo.length < 10) {
		errorList.push(getLocalMessage('update.invalidNumber'));
	}
	if (errorList.length > 0) {
		showErrorOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		var orderId = "";
		var requestData = {
			"amount" : partialAmt
		};
		var ajaxResponse = __doAjaxRequest('PropertyQRPayment.html?showQRCode',
				'POST', requestData, false, 'html');
		if (ajaxResponse != null) {
			var ajaxResponseObj = JSON.parse(ajaxResponse);
			orderId = ajaxResponseObj.orderId;
			var redirectURL = ajaxResponseObj.redirectURL;
			window.location.href = redirectURL;
		}
		showloader(true);
		setTimeout(function () {
            showConfirmBoxSave(orderId);
            showloader(false);
        }, 10000);

	}
}

function showConfirmBoxSave(orderId) {
	debugger;
	var saveorAproveMsg = "Please Sacn the QR Code and make payment";
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("account.btn.save.yes");

	message += '<h4 class=\"text-center text-blue-2\">' + saveorAproveMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>  '
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 text-center \'    '
			+ ' onclick="redirectToSummary(\'' + orderId + '\')"/>'
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function redirectToSummary(orderId) {
	debugger;
	var data = {"orderId":orderId};
	var URL = 'PropertyQRPayment.html?backToMainPage';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
	$.fancybox.close();
}
function setAmountoPay() {

	var totalpaidamount = $("#payAmount").val();

	if (totalpaidamount != "0.00" || totalpaidamount != undefined) {
		$("#amountToPay").val(totalpaidamount);
	}

}

function backToMain() {
	var data = {};
	var URL = 'PropertyQRPayment.html?backToMainPage';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);
}

$(document).ready(function() {
	$(function() {

		$("#id_customerTable").dataTable({
			"oLanguage" : {
				"sSearch" : ""
			},
			"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true,
			"ordering" : false,
			"order" : [ [ 1, "desc" ] ]
		});

	});
});

function openaddcustomerFrom(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function Proceed(element) {
	var errorList = [];
	errorList = ValidatecustomerMasterForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		return saveOrUpdateForm(element,
				getLocalMessage('master.form.submit.success'),
				'CustomerMaster.html', 'saveform');
	}

}

   
function validateMobile(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}

function modifyCustomer(custId, formUrl, actionParam) {
	
	var divName = '.content-page';
	var requestData = {
		"id" : custId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};
function searchCustomer(formUrl, actionParam) {
	
	var custName = $('#custName').val();
	var custAddress = $('#custAddress').val();

	var data = {
		"custName" : custName,
		"custAddress" : custAddress
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#custAddress').val(custAddress);
	$('#custName').val(custName);
	prepareTags();
}

function fnValidatePAN(element) {
	
	$('.error-div').hide();
	var errorList = [];

	Obj = $('#custPANNo').val();

	var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
	var code = /([C,P,H,F,A,T,B,L,J,G])/;
	var code_chk = Obj.substring(3, 4);
	if (Obj.search(panPat) == -1) {
		errorList.push('Invaild PAN Number');
		$('#custPANNo').val("");
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
};

// /////////////////////////////////////////////////////////////////////////////////////////////
// # FILE UPLOAD

var UPLOADURL = "CustomerMaster.html"

function downloadTamplate() {
	
	window.location.href = UPLOADURL + "?ExcelTemplateData";
}

function uploadExcelFile() {
	
	var errorLis = [];

	var fileName = $("#uploadFileName").val().replace(/C:\\fakepath\\/i, '');
	if (fileName == null || fileName == "") {
		errorLis.push(getLocalMessage("excel.upload.vldn.error"));
		showErr(errorLis);
		return false;
	}
	$("#filePath").val(fileName);
	var requestData = $.param($('#wmsMaterialMaster').serializeArray())
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CustomerMaster.html?' + "loadExcelData",
			{}, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	if ($("#validationerror_errorslist").size() == 0) {
		showSaveResultBox1("CustomerMaster.html");
	}
	
	prepareTags();

}
function showSaveResultBox1(redirectUrl) {
	var messageText = getLocalMessage("swm.population.excel.upload.success.message");
	var message = '';
	var cls = getLocalMessage('eip.page.process');
	message += '<p class="text-blue-2 text-center padding-15">' + messageText
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-success\'    '
			+ ' onclick="closebox(\'' + errMsgDiv + '\',\'' + redirectUrl
			+ '\')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}


function redirectToHomePage() {
	// $.fancybox.close();
	window.location.href = 'CustomerMaster.html';
}


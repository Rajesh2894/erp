function uploadExcelFile() {
	
	var errorList = [];
	if ($("#idfId").val() == 0 || $("#idfId").val() == null) {
		errorList.push(getLocalMessage("swm.report.type.validation"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var errorLis = [];
		var fileName = $("#uploadedFile").val().replace(/C:\\fakepath\\/i, '');
		if (fileName == null || fileName == "") {
			errorLis.push(getLocalMessage("excel.upload.vldn.error"));
			showErr(errorLis);
			return false;
		}
		$("#filePath").val(fileName);
		var requestData = $.param($('#logBookReportMasterForm')
				.serializeArray())
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('fileUpload.html?' + "save",
				requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		if ($("#validationerror_errorslist").size() == 0) {
			showSaveResultBox1("fileUpload.html");
		}
		
		prepareTags();
	}
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

$(document).ready(function() {
	var table = $('.LogId').DataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"bPaginate" : true,
		"bFilter" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});

});

function searchExcelFile(formName, actionParam) {
	var errorList = [];
	if ($("#idfId").val() == 0 || $("#idfId").val() == null) {
		errorList.push(getLocalMessage("swm.report.type.validation"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		
		if ($("#idfId").val() != 0) {
			var id = $("#idfId").val();
			var theForm = '#' + formName;
			var url = formName;
			url += '?' + actionParam;
			var requestData = {};
			requestData["logbookId"] = $("#idfId").val();
			var divName = '.content-page';
			var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
					'html');
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			$("#idfId").val(id);
		}
	}
}

function deleteFile(excelId) {
	$.fancybox.close();
	var divName = '.content-page';
	var requestData = {
		"logbookId" : excelId
	};
	var ajaxResponse = doAjaxLoading('fileUpload.html' + '?' + 'deleteDoc',
			requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	showSaveResultBox2('fileUpload.html');
}

function showConfirmBoxForDelete(excelId) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage("swm.cnfrmdelete") + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="deleteFile(' + excelId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function showSaveResultBox2(redirectUrl) {
	var messageText = getLocalMessage("swm.file.delete.success");
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
function resetFileupload() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'fileUpload.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

function exportExcelData() {
	
	var logBookCode = $('#idfId option:selected').attr('code');
	window.location.href = 'LogBookReportUploadByExcel.html?exportLogBookExcel&logBookCode='
			+ logBookCode;

}

function exportAllExcelData() {
	
	var logBookCode = $('#idfId option:selected').attr('code');
	window.location.href = 'LogBookReportUploadByExcel.html?exportExcelData&logBookCode='
			+ logBookCode;
}

function savePopulationMasterForm(element) {
	var status = saveOrUpdateForm(element,
			getLocalMessage('population.master.sucesss'),
			'PopulationMaster.html', 'saveform');
}

function uploadExcelFile() {
	
	var errorLis = [];
	var fileName = $("#uploadedFile").val().replace(/C:\\fakepath\\/i, '');
	if (fileName == null || fileName == "") {
		errorLis.push(getLocalMessage("excel.upload.vldn.error"));
		showErr(errorLis);
		return false;
	}
	$("#filePath").val(fileName);
	var requestData = $.param($('#logBookReportForm').serializeArray())
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('LogBookReportUploadByExcel.html?'
			+ "save", requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	if ($("#validationerror_errorslist").size() == 0) {
		showSaveResultBox1("LogBookReportUploadByExcel.html");
	}
	
	prepareTags();
}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}
function showImportFile(formName, actionParam) {
	
	var logBookCode = $('#idfId').val();
	if (logBookCode != " " && logBookCode != '0') {
		$('.logBook').show();
	}
/*	if (logBookCode != 0) {
		var theForm = '#' + formName;
		var url = formName;
		url += '?' + actionParam;

		var requestData = {};
		requestData["scheme"] = $("#idfId").val();

		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');

		$(".document").html(ajaxResponse);
		$(".document").show();
	} else {
		$(".document").html('');
		$(".document").hide();
	}*/
}
function backLogBookForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'LogBookReportUploadByExcel.html');
	$("#postMethodForm").submit();
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

function getExcelToDownload(formName, actionParam) {
	if ($("#idfId").val() != 0) {
		var theForm = '#' + formName;
		var url = $(theForm).attr('action');
		url += '?' + actionParam;

		var requestData = {};
		requestData["scheme"] = $("#idfId").val();

		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');

		$(".document").html(ajaxResponse);
		$(".document").show();
	} else {
		$(".document").html('');
		$(".document").hide();
	}
}
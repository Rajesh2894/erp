function uploadExcelFile() {
	
	var errorLis = [];
	var fileName = $("#uploadedFile").val().replace(/C:\\fakepath\\/i, '');
	if (fileName == null || fileName == "") {
		errorLis.push(getLocalMessage("excel.upload.vldn.error"));
		showErr(errorLis);
		return false;
	}
	$("#filePath").val(fileName);
	var requestData = $.param($('#logBookFileDownload').serializeArray())
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('fileUpload.html?' + "save", requestData,'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	if ($("#validationerror_errorslist").size() == 0) {
		showSaveResultBox("filedownload.html");
	}
	
	prepareTags();
}
function showSaveResultBox(redirectUrl) {
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


$(document).ready(function(){
	var table = $('.LogId').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true,
	    "ordering":  false,
	    "order": [[ 1, "desc" ]]
	    });
	
});	

function searchExceluploadedFile(formName, actionParam) {
	
	if ($("#idfId").val() != 0) {
		var theForm = '#' + formName;
		var url = formName;
		url += '?' + actionParam;
		var requestData = {};
		requestData["logbookId"] = $("#idfId").val();
		var divName = '.content-page';
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	} else {
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	}
}
function resetFileDownload() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'filedownload.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}





var UPLOADURL = "AccountVoucherEntry.html";

function downloadTamplate() {
	
	window.location.href = UPLOADURL+"?ExcelTemplateData";
}

function uploadExcelFile(obj) {
	
	var errorList = [];
	var fileName = $("#uploadFileName").val().replace(/C:\\fakepath\\/i, '');
	if (fileName == null || fileName == "") {
		errorList.push(getLocalMessage("excel.upload.vldn.error"));
	}
	if (errorList.length == 0) {
		$("#filePath").val(fileName);
		var url = UPLOADURL+"?loadExcelData";
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var ajaxResponse = __doAjaxRequestForSave(url, 'post', requestData,
				false, '', obj);
		if(ajaxResponse != false) {
			$('.content').html(ajaxResponse);
			var val=$(ajaxResponse).find('#keyTest').val();
			//alert("message",message);
			if(val != null && val!=undefined && val !=''){
				displayMessageOnSubmitExcel(val);
			}else{
				var divName = '.content';
				$(divName).html(ajaxResponse);
			}
		}else{
			var divName = '.content';
			$(divName).html(ajaxResponse);
			//$("#transactionDateId").val(transactionDateId);
		}
	} else {
		displayErrorsOnPage(errorList);
	}

}

function displayMessageOnSubmitExcel(successMsg) {
	
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h5 class=\'text-center text-blue-2 padding-5\'>' + successMsg
			+ '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls
			+ '\'  id=\'btnNo\' onclick="redirectToVoucherExcelHomePage()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToVoucherExcelHomePage() {
	// $.fancybox.close();
	
	window.location.href = "AccountVoucherEntry.html";
}

function showPopUpMsg(childDialog) {
	
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic', // 'elastic', 'fade' or 'none'
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}








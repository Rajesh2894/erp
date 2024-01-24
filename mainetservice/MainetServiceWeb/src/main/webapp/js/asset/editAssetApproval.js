function saveAssetInfoApprovalData(approvalData){
	var errorList = [];
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	
	if(decision == undefined || decision == '')
		errorList.push(getLocalMessage('asset.info.approval'));
	else if(comments == undefined || comments =='')
		errorList.push(getLocalMessage('asset.info.comment'));

		
		if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData,
				getLocalMessage('work.estimate.approval.creation.success'),
				'AdminHome.html', 'saveDecision');
	}

}

function openAST(value) {
	
	var response = __doAjaxRequest('AssetRegistration.html?' + value + '',
			'POST', {}, false, 'html');
	$('#editAstApprovalDiv').html(response);
	//$('.pagediv').html(response);
}

function backToSearch() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AssetSearch.html');
	$("#postMethodForm").submit();
}

function backToHomePage() {
	
	var response = __doAjaxRequest('AssetRegistration.html?showEditAssetPage',
			'POST', {}, false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
}

function editModeProcess(response) {
	var tempDiv = $('<div id="tempDiv">' + response + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');
	
	if (!errorsPresent || errorsPresent == undefined
			|| errorsPresent.length == 0) {
		if ($.isPlainObject(response)) {
			
			var message = response.command.message;
			displayMessageOnSubmit(message);
		}
	} else {
		$('#editAstApprovalDiv').html(response);
	}
}

function displayMessageOnSubmit(message) {
	
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';

	var d = '<h5 class=\'text-center text-blue-2 padding-5\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
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
function proceed() {
	backToHomePage();
	$.fancybox.close();
}

$(document).ready(function() { $(function(){
		/*var assetGroup = $("#assetGroup option:selected").attr("code");
		var acquisitionMethod=$("#acquisitionMethod option:selected").attr("code");
		if(assetGroup!="L")
			{
			$("#accountCode option[value='showAstLinePage']").remove();
			}
		if(acquisitionMethod!="LE")
		{
			$("#accountCode option[value='showAstLeasePage']").remove();
		}*/
//	editModeProcess();
	
	var urlToCall	=	document.getElementById("urlParam").value;
	
	
	openAST(urlToCall);
	
});	
});	
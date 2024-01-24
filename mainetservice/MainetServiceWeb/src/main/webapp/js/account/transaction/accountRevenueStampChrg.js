function saveRevenueStamp(element){
	
	
	var errorsList = [];
	errorsList = validateRavanueSaveForm(errorsList);
	
	if (errorsList.length == 0) {
	var url = "AccountRevenueStampCharges.html?create";
	var requestData = $('#revenueStampChargeDTOId').serialize();
	var status = __doAjaxRequestForSave(url, 'post', requestData, false,
			'', element);
	if (status != false) {
		if(status == 'Y') {
			showConfirmBox();
		}else {
			$(".widget-content").html(status);
			$(".widget-content").show();
		}
	}
	}
	else {
		displayErrorsPage(errorsList);
	}
	

}

function validateRavanueSaveForm(errorsList){
	
	var fromDate = 	$("#fromDate").val();
	var toDate = $("#toDate").val();
	var fromAmount =	$("#fromAmount").val();
	var toAmount = $("#toAmount").val();
	var	stampCharge = 	$("#stampCharge").val();
	
	
	
	if(fromDate == "")
	{
	errorsList.push(getLocalMessage('account.enter.fromDate'));
	}
	if(toDate=="")
		{
		errorsList.push(getLocalMessage('account.enter.toDate'));
		}
	if(fromAmount == "")
		{
		errorsList.push(getLocalMessage('account.enter.fromAmt'));
		}
	if(toAmount == "")
	{
	errorsList.push(getLocalMessage('account.enter.toAmt'));
	}

	if(stampCharge == "")
	{
	errorsList.push(getLocalMessage('account.enter.stamp.charge.amt'));
	}
	
	
	
	
	
		
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var eDate = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
		  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
		  if (eDate > sDate) {
			  errorsList.push("To Date should not be less than From Date"); 
		 }
	  
	
	return errorsList;
	
}

function displayErrorsPage(errorsList) {

	if (errorsList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorsList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorsList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}

}

function displayMessageOnSubmit(successMsg){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function redirectToDishonorHomePage () {
	window.location.href='AccountRevenueStampCharges.html';
}

function showPopUpMsg(childDialog){
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        closeBtn : false ,
        helpers: {
			overlay : {
				closeClick : false
			}
		},
		 keys : {
			    close  : null
			  }
    });
	return false;
}

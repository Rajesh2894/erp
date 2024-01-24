/**
 * 
 */
var fileArray=[];
//this will work on the delete button and it will delete particular row which one select for delete 
$(document).ready(function(){
	$("#attachDoc").on("click", '.delButton', function(e) {
		
		var countRows = -1;
		$('.appendableClass').each(function(i) {
			if ( $(this).closest('tr').is(':visible') ) {
					countRows = countRows + 1;			
			}
		});
		row = countRows;
		if (row != 0) {
			$(this).parent().parent().remove();
			
			row--;
		}
		e.preventDefault();
	});	
	//in case of edit this function will delete file which is uploaded
	 $("#deleteDoc").on("click", '#deleteFile', function(e) {
			var errorList = [];
							
			if (errorList.length > 0) {
			  $("#errorDiv").show();
			  showErr(errorList);
			 return false;
			}else{
		           $(this).parent().parent().remove();
					var fileId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
					if(fileId != ''){
						fileArray.push(fileId);
					}
				    $('#deleteByAtdId').val(fileArray);
				}
			});
});
//this function will count how many documents are uploaded 
function fileCountUpload(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('AssetRegistration.html?fileCountUpload','POST',requestData,false,'html');
	$("#uploadTagDiv").html(response);
	prepareTags();

}

// this is for saving the documents 
function saveAssetDocumentDetails(enclosuresData){
		var formName = findClosestElementId(enclosuresData, 'form');
		var theForm = '#' + formName;
		//var requestData = __serializeForm(theForm);
		var requestData = __serializeForm('#assetDocumentId');
		$(enclosuresData).attr('disabled',true);
		var loading	 ='<i class=\"fa fa-circle-o-notch fa-spin fa-2x"></i>';
		showModalBoxWithoutClose(loading);
		var delayInMilliseconds = 40000; //1 second

		setTimeout(function() {
		  //your code to be executed after 1 second
		}, delayInMilliseconds);
		var ajaxResponse = __doAjaxRequest('AssetRegistration.html?saveAstDocPage', 'POST', requestData, false,'',enclosuresData);
		
		var tempDiv = $('<div id="tempDiv">' + ajaxResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		
		$.fancybox.close();
		if(errorsPresent.length > 0) {
			var divError = $(tempDiv).find('#validationerrordiv');
			$(divError).addClass('show');
			$('#astDoc').html(ajaxResponse);
			prepareDateTag();
		}else {
			if ($.isPlainObject(ajaxResponse))
			{
				var message = ajaxResponse.command.message;
				showMessageOnSubmit(ajaxResponse,message,'AssetRegistration.html');
			}
		}
	}
//final summit confirm box	
function showMessageOnSubmit(successMsg,message,redirectURL) {
	var	errMsgDiv		=	'.msg-dialog-box';
	var cls = getLocalMessage('asset.proceed');
	
	var d	='<h5 class=\'text-center text-blue-2 padding-5\'>'+message+'</h5>';
	d	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', 
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
function proceed() {
	var approvalProcessFlag = $("#approvalProcessFlag").val();
	
		if(approvalProcessFlag == 'SEND'){
			window.location.href = "AdminHome.html";
		} else{
			window.location.href = ""+$("#moduleDeptUrl").val()+"";
		}
}

function saveAssetDocDetails(element) {
	var modeType = 	$("#modeType").val();
	if(modeType=='E')
		{
		var requestData = __serializeForm('#assetDocumentId');
		$(element).attr('disabled',true);
		var loading	 ='<i class=\"fa fa-circle-o-notch fa-spin fa-2x"></i>';
		showModalBoxWithoutClose(loading);
		var delayInMilliseconds = 40000; //1 second

		setTimeout(function() {
		  //your code to be executed after 1 second
		}, delayInMilliseconds);
		var ajaxResponse = __doAjaxRequest('AssetRegistration.html?saveAstDocPage', 'POST', requestData, false,'', element);
		var tempDiv = $('<div id="tempDiv">' + ajaxResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		
		$.fancybox.close();
		if(errorsPresent.length > 0) {
			var divError = $(tempDiv).find('#validationerrordiv');
			$(divError).addClass('show');
			//D#87950
			$('#astDocId').html(ajaxResponse);
			$(element).attr('disabled',false);
			prepareDateTag();
		}else {
			if ($.isPlainObject(ajaxResponse))
			{
				var message = ajaxResponse.command.message;
				showMessageOnSubmit(ajaxResponse,message,'AssetRegistration.html');
			}
		}
	}
	else
		{
		saveAssetDocumentDetails(element);
		prepareDateTag();
		}
	
}















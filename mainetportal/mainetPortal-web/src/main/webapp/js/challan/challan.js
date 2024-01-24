//on search

$(document).ready(function(){
	var errorList=[];
	var flag=$('#isChallanReceived').val();
	var challanNo=$('#challanNo').val();
	if ($.trim(flag) == 'Y') {
		errorList.push($('#challan_noId').val()+' '+challanNo+' '+$('#already_verifiedId').val());
		displayErrorsOnPage(errorList);
	} else if ($.trim(flag) == 'N') {
		errorList.push($('#invalidChallanId').val());
		displayErrorsOnPage(errorList);
	}
});


$(function() {
	$("#challanSearch").click(function(){
		
		var errorList=[];
		var challanNo=$('#challanNo').val();
		var URL=$('#challanForm').attr('action');
//		var URL=$('.form').attr('action', $(this).attr('formaction'));
//		var payMode=$('input[name=payMode]:checked', '#workflowId').val();
		if (challanNo=='' || challanNo=='0' || challanNo == undefined) {
			errorList.push($('#challan_no_emptyId').val());
		}
		
		if (errorList.length==0) {
			var requestData = __serializeForm('form');
			var result=__doAjaxRequest(URL,'POST',requestData,false,'html');
			if (result == 'Y') {
				
			}else{
				$('#formDivId').html(result);
			}
			
		} else {
			displayErrorsOnPage (errorList);
		}
		
		
	});
	
});



$(function() {
	$("#challanSave").click(function(){
		
		var URL=$('#challanUpdate').attr('action');
			var requestData = __serializeForm('form');
			var result=__doAjaxRequest(URL,'POST',requestData,false,'json');
			if (result == 'Y') {
				promptSuccessMessage($('#challan_success_msgId').val());
			}else{
				promptSuccessMessage($('#challan_fail_msgId').val());
			}
		
	});
	
});


/**
 * used to render validation errors
 * @param errorList
 * @returns {Boolean}
 */
function displayErrorsOnPage(errorList) {
	var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeOutErrBox()" width="25"/></div>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('.error-div').html(errMsg);
	$(".error-div").show();
	
	return false;
}

$(function() {
	$("#backIddd").click(function(){
		console.log('clicked');
		window.location.href='ChallanVerification.html';
	});
	
});

$(function(){
	$('#resetId').click(function(){
		$('.error-div').hide();
		$('#challanNo').val('');
	});
});

function closeOutErrBox(){
	$('.error-div').hide();
}


function promptSuccessMessage(warnMsg) {
	
	
	var formAction ='ChallanVerification.html';
	var message='';
	var okmsg = $("#okId").val();
	message	+='<div class="sucess ok-msg"><h3>'+warnMsg+'</h3></div>';
	message	+='<div class="btn_fld padding_10">'	+				
			'<input type=\'button\' class=\'css_btn\'  value="'+okmsg+'" onclick="closeChallanBox()"/>'+
			'</div>';
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();

	showModalBoxWithoutClose(errMsgDiv);
	
}

function closeChallanBox() {
	
	window.location.href='ChallanVerification.html';
	
}


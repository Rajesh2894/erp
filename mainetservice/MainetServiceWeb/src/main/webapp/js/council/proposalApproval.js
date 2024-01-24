$(document).ready(function() {
});
function showConfirmBoxForApproval(approvalData) {
	
	element = approvalData;
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		saveProposalApprovalData();
}

function saveProposalApprovalData() {
	var errorList = [];	
		$.fancybox.close();
		var divName = '.content-page';
		 var formName = findClosestElementId(element, 'form');
		 var theForm = '#' + formName; 
		 var requestData = __serializeForm(theForm);
		 var object = __doAjaxRequest("CouncilProposalApproval.html?saveProposalApprovalDetails", 'POST',requestData, false,'json');		
		
		 if(object.error != null && object.error != 0  ){	
			 $.each(object.error, function(key, value){
				    $.each(value, function(key, value){
				    	if(value != null && value != ''){				    		
				    		errorList.push(value);
				    	}				        
				    });
				});
			 displayErrorsOnPage(errorList);
		 }else{
			if(object.wfStatus =='REJECTED'){
				 showBoxForApproval(getLocalMessage('council.proposal.rejection'));
			 }else if(object.wfStatus =='SEND_BACK'){
				 showBoxForApproval(getLocalMessage('council.proposal.sendBack'));
			 }else{
				 showBoxForApproval(getLocalMessage('council.proposal.creation'));
			 }
		 }
}

function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  getLocalMessage("council.proceed");
	var no = 'No';	
	message += '<p class="text-blue-2 text-center padding-15">'
		    + succesMessage +'</p>';
	
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			+ ' onclick="closeApproval();"/>' + '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval(){
	window.location.href='AdminHome.html';
	$.fancybox.close();
}

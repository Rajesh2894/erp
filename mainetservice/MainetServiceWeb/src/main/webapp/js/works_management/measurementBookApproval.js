function saveEstimateApprovalData() {
	
	var errorList = [];		 
  
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		$.fancybox.close();
		var divName = '.content-page';
		
		 var formName = findClosestElementId(element, 'form');
		 var theForm = '#' + formName; 
		 var requestData = __serializeForm(theForm);
		 var object = __doAjaxRequest("MeasurementBookApproval.html?saveSanctionDetails", 'POST',requestData, false,'json');		
		
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
			 if(object.sanctionNumber != null){
				  showMessageForApproval(object.sanctionNumber,object.workId ,object.projId);
			 }else if(object.workStatus =='REJECTED'){
				 showBoxForApproval(getLocalMessage('work.measure.approval.Rejected.success'));
			 }else if(object.workStatus =='FORWARD_TO_EMPLOYEE'){
				 showBoxForApproval(getLocalMessage('work.measure.approval.forwarded.success'));
			 }else if(object.workStatus =='SEND_BACK'){
				 showBoxForApproval(getLocalMessage('work.measure.approval.send.back.success'));
			 }
			 else{
				 showBoxForApproval(getLocalMessage('work.measure.approval.creation.success'));
			 }
		 }		 
	}
}
	
	
function showConfirmBoxForApproval(approvalData) {
	
	element = approvalData;
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var yes = 'Yes';
	var no = 'No';
	if ($("input:checkBox[name='finalApproval']").is(":checked")) {
		message += '<p class="text-blue-2 text-center padding-15">'
				+ 'You Checked For Final Approval. Are You Sure Want To Proceed ?'
				+ '</p>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+yes+'\'  id=\'btnNo\' '
				+ ' onclick="saveEstimateApprovalData();"/>' + '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	} else {
		saveEstimateApprovalData();
	}
}

function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  getLocalMessage("works.management.proceed");
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

function showMessageForApproval(sanctionNumber,workId ,projId){
	
	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage("works.management.proceed");
	var no = 'No';
	
	message += '<p class="text-blue-2 text-center padding-15">'
		    +  getLocalMessage('work.estimate.approval.creation.success') +" "+ getLocalMessage("work.estimate.approval.sanction.no") + "" + sanctionNumber 
		       '</p>';
	
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			+ ' onclick="viewReport(\'' +sanctionNumber +'\',\'' +workId +'\',\'' +projId+ '\');"/>' + '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
	return false;
}

function closeApproval(){
	window.location.href='AdminHome.html';
	$.fancybox.close();
}
	
function getEditWork(workMbId,mode) {	
	var divName = '.content-page';
	var url = "MeasurementBookApproval.html?editWorkEstimate";
	var actionParam = $("form").serialize() + '&workMbId=' + workMbId + '&mode='
			+ mode;
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getSorByValue();
}	
	
function getSorByValue() {
	var actionParam = {
		'actionParam' : $("#sorList").val(),
		'sorId' : $("#sorId").val()
	}
	var url = "MeasurementBookApproval.html?selectAllSorData";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$("#estimationTagDiv").html(ajaxResponse);
	prepareTags();
}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

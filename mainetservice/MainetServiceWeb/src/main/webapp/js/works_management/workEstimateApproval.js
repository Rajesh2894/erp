var element = "";
$(document).ready(function() {

			if ($("input:radio[name='workflowActionDto.decision']").is(
					":checked")
					&& $("#decision").val() == "APPROVED") {
				
				$('#finalApproval1').removeAttr('disabled');

			} else if(document.getElementById("finalApproval1")!=null) {
			
				$('#finalApproval1').attr('disabled', 'disabled');
				
				document.getElementById("finalApproval1").checked = false;
			}

			if($("#taskName").val() == 'Initiator'){
				$("input[type=radio][value='REJECTED']").attr("disabled",true);	
				$("input[type=radio][value='FORWARD_TO_EMPLOYEE']").attr("disabled",true);
				$("input[type=radio][value='SEND_BACK']").attr("disabled",true);
				$('#finalApproval1').attr('disabled', 'disabled');
			}
		});

function viewWorkEstimateAbstractReport(projId, workId, reportType,workType,deptId) {
	
	var divName = '.content-page';
	$("#errorDiv").hide();
	var requestData = $("form").serialize() + '&projId=' + projId + '&workId='
			+ workId + '&reportType=' + reportType+'&workType=' + workType+'&deptId=' + deptId;
	var ajaxResponse = doAjaxLoading(
			'WorkEstimateApproval.html?getWorkAbstractSheet', requestData,
			'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function getEditWorkEstimate(workId, mode) {	
	var divName = '.content-page';
	var url = "WorkEstimateApproval.html?editWorkEstimate";
	var actionParam = $("form").serialize() + '&workId=' + workId + '&mode='
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
	var url = "WorkEstimate.html?selectAllSorData";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$("#estimationTagDiv").html(ajaxResponse);
	prepareTags();
}

function saveEstimateApprovalData() {
	
	var errorList = [];		 
     errorList = validateTermsAndConditionForApproval(errorList);	
    //D99427
    var yearId=$("#faYearId0").val();
 	var sacHeadId=$("#sacHeadId0").val();
 	var yerFinCode=$("#yerFinCode0").val();
 	var yeDocRefNo=$("#yeDocRefNo0").val();
 	var yeBugAmount=$("#yeBugAmount0").val();
 	
 	if(yearId == "" || sacHeadId == "" || yeDocRefNo == "" || yeBugAmount == "" || yerFinCode == ""){
 		errorList.push("Please Enter Budget Details");
 	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		$.fancybox.close();
		var divName = '.content-page';
		
		 var formName = findClosestElementId(element, 'form');
		 var theForm = '#' + formName; 
		 var requestData = __serializeForm(theForm);
		 var object = __doAjaxRequest("WorkEstimateApproval.html?saveSanctionDetails", 'POST',requestData, false,'json');		
		
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
				 showBoxForApproval(getLocalMessage('work.estimate.approval.Rejected.success'));
			 }else if(object.workStatus =='FORWARD_TO_EMPLOYEE'){
				 showBoxForApproval(getLocalMessage('work.estimate.approval.forwarded.success'));
			 }else if(object.workStatus =='SEND_BACK'){
				 showBoxForApproval(getLocalMessage('work.estimate.approval.send.back.success'));
			 }
			 else{
				 showBoxForApproval(getLocalMessage('work.estimate.approval.creation.success'));
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

function viewReport(workSancNo,workId,projId,workflowId){
	
	$.fancybox.close();
	var divName = '.content-page';
	$("#errorDiv").hide();
	var requestData = $("form").serialize() + '&workSancNo=' + workSancNo + '&workId='
			+ workId + '&projId=' + projId ;

	var ajaxResponse = doAjaxLoading(
			'WorkEstimateApproval.html?getTermsAndCondition', requestData,
			'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);	
	prepareTags();	
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

function closeApproval(){
	window.location.href='AdminHome.html';
	$.fancybox.close();
}

$("form input:radio").change(function() {
	
	if ($(this).val() == "APPROVED") {
		if($("#taskName").val() != 'Initiator'){
			$('#finalApproval1').removeAttr('disabled');
		}	else {
			$('#finalApproval1').attr('disabled', 'disabled');
		}	
	} else {		
		$('#finalApproval1').attr('disabled', 'disabled');
		document.getElementById("finalApproval1").checked = false;
	}
});

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function viewExpenditure(i) {

    var errorList = [];
	var yearId = $('#faYearId' + i).val();
	var sacHeadId = $('#sacHeadId' + i).val();
	var proposalAmt = $('#yeBugAmount' + i).val();
	var fieldId = $("#wardZoneId0").val();
	var dpDeptId = $("#depId").val();


    if (yearId == '') {
	errorList.push(getLocalMessage("council.proposal.validation.yearId"));
	displayErrorsOnPage(errorList);
	return false;
    }
    if (sacHeadId == '') {
	errorList
		.push(getLocalMessage("council.proposal.validation.sacHeadId"));
	displayErrorsOnPage(errorList);
	return false;
    }
    if (proposalAmt == '') {
	errorList.push(getLocalMessage('council.proposal.validation.amount'));
	displayErrorsOnPage(errorList);
	return false;
    }
    var requestData = {
	'yearId' : yearId,
	'sacHeadId' : sacHeadId,
	"proposalAmt" : proposalAmt,
	"fieldId" : fieldId,
	'dpDeptId' : dpDeptId
    };

    var ajaxResponse = __doAjaxRequest(
	    "WorkEstimateApproval.html?getBudgetHeadDetails", 'POST',
	    requestData, false, 'json');

    if (ajaxResponse.authorizationStatus == 'Y') {
	var message = '';
	var sMsg = '';
	message += '<h4 class="text-center">Budget Details</h4>';
	message += '<div class="margin-right-10 margin-left-10">';
	message += '<table class=\"table table-bordered"\>' + '<tr>'
		+ '<th>Budget' + '</th>' + '<td class="text-right"> '
		+ parseFloat(ajaxResponse.invoiceAmount).toFixed(2) + '</td> '
		+ '</tr>';
	message += '<tr>' + ' <th>Previous Expenditure' + '</th>'
		+ '<td class="text-right">'
		+ parseFloat(ajaxResponse.sanctionedAmount).toFixed(2)
		+ '</td>' + '</tr>';
	message += '<tr>' + ' <th>Current Expenditure' + '</th>'
		+ '<td class="text-right">'
		+ parseFloat(ajaxResponse.billAmount).toFixed(2) + '</td>'
		+ '</tr>';
	message += '<tr>' + ' <th>Balance' + '</th>'
		+ '<td class="text-right">'
		+ parseFloat(ajaxResponse.netPayables).toFixed(2) + '</td>'
		+ '</tr></table>';
	message += '</div>';
	if (ajaxResponse.disallowedRemark == 'Y') {
	    sMsg = 'account.budget.check.msg';
	    message += '<h4 class=\"text-center red padding-12\">' + sMsg
		    + '</h4>';
	}
    } else {
	errorList
		.push(getLocalMessage("proposal.no.budget.availabe.against.selected.account"));
	displayErrorsOnPage(errorList);
	return false;
    }
    $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
    $(errMsgDiv).html(message);
    $(errMsgDiv).show();
    $('#btnNo').focus();
    showModalBox(errMsgDiv);
    return false;
}
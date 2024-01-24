
/**
 * @author vishwajeet.kumar
 * @returns
 */

var fileArray = [];

var element="";
$(document).ready(function() {
	$('.fancybox').fancybox();

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});
	
	
	if($("#mbTaskName").val() != 'Initiator'){
		$("input[type=radio]").attr("disabled",true);
    }
		
	if($("#mbTaskName").val() == 'Initiator'){
		$("input[type=radio][value='REJECTED']").attr("disabled",true);	
		$("input[type=radio][value='FORWARD_TO_EMPLOYEE']").attr("disabled",true);
		$("input[type=radio][value='SEND_BACK']").attr("disabled",true);
		$("#inspRequired").attr("disabled",true);
	}
	
    var grandTotal = 0;
	$('#calculationMb tr').each(function(i) {
		
		var totalActualAmount = $("#actualAmount" + i).text();
		if (totalActualAmount != 0) {
			grandTotal += isNaN(Number(totalActualAmount)) ? 0 : Number(totalActualAmount);
		}
	});
	$("#totalWorkMb").text(grandTotal.toFixed(2));
	
	getInspRequired();
	
	var memoType = $("#memoType").val();
	if(memoType == 'A'){		
		$("input[type=radio][value='APPROVED']").attr("disabled",true);
		$("input[type=radio][value='REJECTED']").attr("disabled",false);	
		$("input[type=radio][value='FORWARD_TO_EMPLOYEE']").attr("disabled",false);
		$("input[type=radio][value='SEND_BACK']").attr("disabled",false);
	}else if(memoType =='I'){
		$("input[type=radio][value='APPROVED']").attr("disabled",false);
		$("input[type=radio][value='FORWARD_TO_EMPLOYEE']").attr("disabled",true);
		$("input[type=radio][value='REJECTED']").attr("disabled",false);	
		$("input[type=radio][value='SEND_BACK']").attr("disabled",true);
	}
	
});

function viewMbAbstractSheet(workMbId) {
	
	var divName = '.content-page';
	var requestData = '&workMbId=' + workMbId;

	var ajaxResponse = doAjaxLoading(
			'WorkMBApproval.html?getWorkMBAbstractSheet', requestData, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
}

function getEditMB(workMbId, mode) {
	
	var divName = '.content-page';
	var url = "WorkMBApproval.html?editMb";
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
	var url = "WorkEstimate.html?selectAllSorData";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$("#estimationTagDiv").html(ajaxResponse);
	prepareTags();
}
function saveWorkMbApproval(saveApprovalForm) {
	
	var  element = saveApprovalForm;
	var errorList = [];		
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(element,
				getLocalMessage('work.mb.approval.creation.success'),
				'WorkMBApproval.html?paymentOrder', 'saveform');
	}
}

function getMemoType(){
	
	var memoType = $("#memoType").find("option:selected").attr('value');
	if(memoType == ''){
		$("input[type=radio]").attr("disabled",true);
		return false;
	}
	if(memoType == 'A'){		
		$("input[type=radio][value='APPROVED']").attr("disabled",true);
		$("input[type=radio][value='REJECTED']").attr("disabled",false);	
		$("input[type=radio][value='FORWARD_TO_EMPLOYEE']").attr("disabled",false);
		$("input[type=radio][value='SEND_BACK']").attr("disabled",false);
	}else{
		$("input[type=radio][value='APPROVED']").attr("disabled",false);
		$("input[type=radio][value='FORWARD_TO_EMPLOYEE']").attr("disabled",true);
		$("input[type=radio][value='REJECTED']").attr("disabled",false);	
		$("input[type=radio][value='SEND_BACK']").attr("disabled",true);
	}
}

function getInspRequired(){
	
	if($("#flag").val()== "N"){
		$("input[type=radio][value='APPROVED']").attr("disabled",false);
		$("input[type=radio][value='FORWARD_TO_EMPLOYEE']").attr("disabled",true);
		$("input[type=radio][value='REJECTED']").attr("disabled",false);	
		$("input[type=radio][value='SEND_BACK']").attr("disabled",true);
		return false;
	}
	var inspectionReq = $("#inspRequired").find("option:selected").attr('value');
	
	if(inspectionReq == 'Y'){
		$("#vigilenceReferenceNo").prop('disabled', false);
		$("#memoDate").prop('disabled', false);
		$("#inspectionDate").prop('disabled', false);
		$("#memoType").prop('disabled', false);
		$("#memoTypeValue").attr("disabled",true);
		$("#memoTypeValue").val($("#memoType").val());
	}else if(inspectionReq == 'N'){
		
		$("#memoDate").val("");
		$("#inspectionDate").val("");
		$("#inspectionDate").val(null);
		$('#memoType').find("option:selected").attr("selected", false); 
		$('#memoType  option[value=""]').attr("selected", true);
		$("#inspectionDate").attr('disabled', true);
		$("#memoType").attr('disabled', true);
		$("#memoTypeValue").attr("disabled",false);
		$("#memoTypeValue").val("");
		$("input[type=radio][value='REJECTED']").attr("disabled",false);	
		$("input[type=radio][value='APPROVED']").attr("disabled",false);
	}
	else {
		$("#vigilenceReferenceNo").prop('disabled', true);
		$("#memoDate").prop('disabled', true);
		$("#inspectionDate").prop('disabled', true);
		$("#memoType").prop('disabled', true);
	}
}

function backToApprvForm(){
	
	var divName = '.content-page';
	var url = "WorkMBApproval.html?showApprovalCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function printdiv(printpage) {
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}
function printPaymentOrder() {
	
	var divName = formDivName;
	
	var ajaxResponse = __doAjaxRequest('WorkMBApproval.html?paymentOrder',
			'post', {}, false, 'html');
	var divContents = ajaxResponse;
	
	$(divName).html(divContents);
	prepareTags();
}

function viewRADetailsForApproval(raId, mode) {
	var divName = '.content-page';
	var url = "raBillGeneration.html?editRa";
	var actionParam = {
		'raId' : raId,
		'mode' : mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveInspectionDetails(inspectionData){
	debugger;
	var errorList = [];
	var inspRequired = $("#inspRequired").val();
	var memoType = $("#memoType").val();	
	var memoDesc = $("#memoDescription").val();
	 if($("#mbTaskName").val() != 'Initiator'){
	    if(inspRequired == ''){
		  errorList.push(getLocalMessage("work.mb.approval.select.inspection.required"));
	  }
	 }
	if(inspRequired == 'Y'){
		if(memoType == ''){
			errorList.push(getLocalMessage("work.mb.approval.select.memo.type"));
		}
	}
	if(memoDesc == ''){
		errorList.push(getLocalMessage("work.vigilance.please.enter.comments"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		var requestData=$("#WorkMBApproval :input[value!='on']").serialize();
		
		var response = doAjaxLoading('WorkMBApproval.html?saveInspectionData',
				requestData, 'json');
	    if(response!=0){
	    	var childDivName = '.msg-dialog-box';
	    	var message = '';
	    	var Proceed =  getLocalMessage("works.management.proceed");
	    	var no = getLocalMessage("work.vigilance.inspection.required.no");
	    	message += '<p class="text-blue-2 text-center padding-15">'
	    		    + response.succesMessage +'</p>';
	    	
	    	message += '<div class=\'text-center padding-bottom-10\'>'
	    			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
	    			+ ' onclick="ProcedUserApproval();"/>' + '</div>';
	    	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	    	$(childDivName).html(message);
	    	$(childDivName).show();
	    	$('#Proceed').focus();
	    	showModalBoxWithoutClose(childDivName);
	    }
	}
}
function ProcedUserApproval(){
	
	$.fancybox.close();
	openUserActionForm();
}

function callServiceSS(appNo,serviceId,taskId,workflowId,taskName){
	
	var divName = '.content-page';
	var data = {}; 
	data.appNo = appNo;
	data.taskId = serviceId;
	data.actualTaskId = taskId;
	data.workflowId=workflowId;
	data.taskName=taskName;
	var url = "WorkMBApproval.html?showDetails";
	var response =__doAjaxRequest(url, 'post', data, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();
}

function openUserActionForm(){
	
	var errorList = []
	var inspectionReq = $("#inspRequired").find("option:selected").attr('value');
	if($("#mbTaskName").val() != 'Initiator'){
	    if(inspectionReq == ''){
		  errorList.push(getLocalMessage("work.mb.approval.select.inspection.required"));
		  displayErrorsOnPage(errorList);
		return false;
	  }
	 }
	var divName = '.content-page';
	var actionParam = {
			'actionParam' : 'actionParam'
		}
	var url = "WorkMBApproval.html?showUserAction";
	var ajaxResponse = __doAjaxRequest(url, 'POST',actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function fileCountInspectionUpload(dataElement){
	
	var formName = findClosestElementId(dataElement, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('WorkMBApproval.html?fileCountInspectionUpload',
			'POST', requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();
}

$("#attachDoc").on("click", '.delButton', function(e) {
	
	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	var row = countRows;
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
	}
	e.preventDefault();
});


$("#deleteDocument").on("click",'#deleteFile',function(e) {
	
	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	} else {
		$(this).parent().parent().remove();
		var fileId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (fileId != '') {
			fileArray.push(fileId);
		}
		$('#removeFileByIds').val(fileArray);
	}
});


function otherTask() {
	return false;
}

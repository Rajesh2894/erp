var fileArray=[];
$(document).ready(function() {

	$('#frwdToDepFormGrp').hide();
	prepareDateTag();
	
	var deptCode = $("#deptCode").val();
	var subUnitClosed = $("#subUnitClosed").val();
	
	if(deptCode == "AD" && subUnitClosed == ''){
		$('.subUnithide').hide();
	}else{
		$('.subUnithide').show();
	} 
});


function loadForwardData(obj){
	
	
	//var auditDeptId=$('#audDepId').val();
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var url	=	$(theForm).attr('action')+'?' + 'getAuditWorkflowAction';
	if($('input[name="workflowActionDto.decision"]:checked').val()=="FORWARD_TO_EMPLOYEE")
	{
		
		$('#frwdToDepFormGrp').show();
	}
	else{
		$('#serverEvent').val('');
		$('#empId').val('');
		$('#eventEmp').val('');
		$("#forward").hide();
		$("#sendBack").hide();
		$('#frwdToDepFormGrp').hide();
		}
	
}


function fetchEmpFromDepartment(obj)
{
	//alert("Here");
	
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName; 
	var url	=	$(theForm).attr('action')+'?' + 'getAuditWorkflowAction';
	var deptId = $('#forwardToDept').val();
	var data = {
			"decision" : "FORWARD_TO_EMPLOYEE",
			"serEventId":null,
			"deptId":deptId
	};	
	var returnData = __doAjaxRequest(url, 'POST', data, false);
	$('#empId').html('');
	
	if ($('#empId option[value="0"]').length === 0) {
	    var newOption = '<option value="0" selected="selected">Select</option>';
	    $('#empId').prepend(newOption);
	}

	$.each( returnData, function( key, value ) {
		$('#empId').append('<option value="' + key + '">' + value  + '</option>').trigger('chosen:updated');
	});

}


function showConfirmBoxForApproval(approvalData) {
	
	element = approvalData;
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		saveAuditParaApprovalData();
}


function saveAuditParaApprovalData() {
	var isEditable = $("#isEditable").val();
	
	var errorList = [];	
	if (isEditable == "true"){
		var entryDate = $('#auditEntryDate').val();
		var departmentName = $('#auditDeptId').val();
		var auditType = $('#auditType').val();
		var severity = $('#auditSeverity').val();
		var subject = $('#auditParaSub').val();
		var workName = $('#auditWorkName').val();
		var contractorName = $('#auditContractorName').val();
		var description = $('#auditAppendix').val();
		var recAmt = $('#recAmt').val();
		var auditParaYear = $('#auditParaYear').val();
		var auditParaTOYear = $('#auditParaTOYear').val();
		var zoneWard = $('#auditWard1').val();
		var categoryId = $('#categoryId').val();
		var subUnit = $('#subUnit').val();
		var auditParaStatus = $('#auditParaStatus').val();
		var type=($('option:selected', $("#categoryId")).attr('code'));
		
		
		if (entryDate == '') {
			errorList
					.push(getLocalMessage('audit.mgmt.validation.entryDate'));
		}
		if (departmentName == '' || departmentName == 0) {
			errorList
					.push(getLocalMessage('audit.mgmt.validation.departmentName'));
		}
		if (auditParaYear == '' || auditParaYear == 0) {
			errorList
					.push(getLocalMessage('audit.validation.from.date'));
		}
		if (auditParaTOYear == '' || auditParaTOYear == 0) {
			errorList
					.push(getLocalMessage('audit.validation.to.date'));
		}
		if (zoneWard == '' || zoneWard == 0) {
			errorList
					.push(getLocalMessage('audit.validation.zone'));
		}
		if (auditType == '' || auditType == 0) {
			errorList
					.push(getLocalMessage('audit.mgmt.validation.auditType'));
		}
		if (severity == '' || severity == 0) {
			errorList
					.push(getLocalMessage('audit.mgmt.validation.severity'));
		}
		if(recAmt =="") { 
			if(type == 'FIN' || type == 'FL' || type == 'RECA' || type == 'OUTA'){
				errorList
					.push(getLocalMessage('audit.validation.recovery.amount'));
			}
		}
		if (subject == '') {
			errorList
					.push(getLocalMessage('audit.mgmt.validation.subject'));
		}
		if (categoryId == '' || categoryId == 0) {
			errorList
					.push(getLocalMessage('audit.mgmt.validation.categoryId'));
		}
		if (subUnit == '' || subUnit == null || subUnit == undefined) {
			errorList
					.push(getLocalMessage('audit.validation.sub.units'));
		}	
		if (description == '' || description == undefined) {
			errorList
					.push(getLocalMessage('audit.mgmt.validation.description'));
		}
		
	}else{
		
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;	
		var minDates=$('#auditEntryDate').val();
		var max=new Date();
		var auditDate=$('#auditDate').val();
		var newAuditDate=$('#newAuditDate').val();
		var subUnit = $('#subUnit').val();
		var subUnitClosed = $('#subUnitClosed').val();
		var subUnitVal = parseInt(subUnit);
		var subUnitClosedVal = parseInt(subUnitClosed);
		var min = new Date(minDates.replace(pattern, '$3-$2-$1'));
		var current = new Date(auditDate.replace(pattern, '$3-$2-$1'));
		var newAuditDates = new Date(newAuditDate.replace(pattern, '$3-$2-$1'));
		var decision=$('input[name="workflowActionDto.decision"]:checked').val();
		var fowardEmployee = $('#empId').val();
		var fowardDept = $('#forwardToDept').val()
		var keyTest = $("#keyTest").val();
		var subUnitCompDone = $("#subUnitCompDone").val();
		var subUnitCompPending = $("#subUnitCompPending").val();
		var remark = $("#comments").val();
		var deptCode = $("#deptCode").val();
		
		if(auditDate==null || auditDate==""){
			errorList.push(getLocalMessage('audit.selectAuditDate'));
		}
		if(newAuditDate!=null && newAuditDate!=""){
			if(current<newAuditDates){
				errorList.push(getLocalMessage('audit.minDateLatest'));
			}
		}else{
			if(current<min){
				errorList.push(getLocalMessage('audit.minDate'));
			}		
		}
		if(decision =="FORWARD_TO_EMPLOYEE"){
			if(fowardDept == 0 || fowardEmployee == []){
				errorList.push(getLocalMessage('audit.foward.employee.validation'));
			}
		}
		if(current>max){
			errorList.push(getLocalMessage('audit.maxDate'));
		}
		if(keyTest == "Y" && deptCode != "AD"){
			

			var forwardToDept = $("#forwardToDept option:selected").text();

			var departmentName = forwardToDept.match(/\((.*?)\)/);

			if (departmentName && departmentName.length > 1) {
				departmentName = departmentName[1]; // Extract the captured
			} else {
				departmentName = ''; // If not found, set to an empty string
			}
			
			if (departmentName === "Auditor Dept") {
				if(subUnitClosed == null || subUnitClosed == ""){
					errorList.push(getLocalMessage('audit.validation.sub.units.closed'));
				}
				if(subUnitCompDone == null || subUnitCompDone == ""){
					errorList.push(getLocalMessage('audit.validation.sub.units.complaince.pending'));
				}
				if(subUnitCompPending == null || subUnitCompPending == ""){
					errorList.push(getLocalMessage('audit.validation.sub.units.complaince.done'));
				}
			}
			if(subUnitClosed != null && (subUnitClosedVal > subUnitVal)){
				errorList.push(getLocalMessage('audit.max.sub'));
			}
			
		}
		if(decision =="APPROVED" && keyTest == "Y"){
			if (subUnitClosedVal != subUnitVal){
				errorList.push(getLocalMessage('audit.validation.suv.units.approval'));
			}
		}
		if(remark==null || remark==""){
			errorList.push(getLocalMessage('audit.approval.validation.remark'));
		}
	}
	
	if(errorList.length > 0){
		displayErrorsOnPage(errorList);
	}
	
	
	else{
		$.fancybox.close();
		var removeFileById=$('#removeFileById').val();	
		var divName = '.content-page';
		 var formName = findClosestElementId(element, 'form');
		 var theForm = '#' + formName; 
		 var requestData = __serializeForm(theForm);
		 var decision = $('input[name="workflowActionDto.decision"]:checked').val();
		 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveAuditParaApproval', 'POST',requestData, false,'json');		
		 
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
			 if(decision == "REJECTED"){
				 showBoxForApproval(getLocalMessage('audit.para.rejection'));
			 }else if(decision == "SEND_BACK"){
				 showBoxForApproval(getLocalMessage('audit.para.sendBack'));
			 }else if(decision =="FORWARD_TO_EMPLOYEE"){
				 showBoxForApproval(getLocalMessage('audit.para.forward'));
			 }else{
				 showBoxForApproval(getLocalMessage('audit.para.creation'));
			 }
		 }
		}
}

function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  getLocalMessage("audit.proceed");
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

$("#attachDocuments").on("click",'#deleteFile',function(e) {

	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	} else {

		$(this).parent().parent().remove();
		var fileId = $(this).parent().parent().find(
				'input[type=hidden]:first').attr('value');
		if (fileId != '') {
			fileArray.push(fileId);
		}
		$('#removeFileById').val(fileArray);
	}
});

$(document).on('change', '.paraCategory', function() {
	var type=($('option:selected', $("#categoryId")).attr('code'));

				if(type == undefined || type == ""){
					$('.amountHide').hide();
				} else if(type == 'FIN' || type == 'FL' || type == 'RECA' || type == 'OUTA'){
					$('.amountHide').show();
				}else{
					$('.amountHide').hide();
				}
	});

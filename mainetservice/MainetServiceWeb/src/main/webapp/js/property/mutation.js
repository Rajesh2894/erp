$(document).ready(function(){
	//datepicker function for date of effect
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0",
	});
	yearLength();
	
/*var locId=$("#locId").val();
	if(locId!=null && locId!='' && locId!=undefined && locId>0){
		var result=	getOperationalWardZone($("#orgId").val(),$("#deptId").val(),$("#locId").val());
		$("#wardZone").html(result);
	}*/
	
});

function yearLength(){	
	var dateFields = $('.dateClass');
    dateFields.each(function () {
    	
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}

function getOwnerTypeInfo() {
var ownerType = $("#ownerInfo" + " option:selected").attr("code");
if(ownerType!=undefined){
var data = {"ownershipType" : ownerType};
var URL = 'MutationForm.html?getOwnershipTypeDiv';
var returnData = __doAjaxRequest(URL, 'POST', data, false);
$("#ownerDetails").html(returnData);
$("#ownerDetails").show();
//checkListCharge
$("#detailDiv").hide();
$("#saveMut").hide();
$("#checkListCharge").show();
}
else{
	$("#ownerDetails").html("");
}
}

function fetchNewCheckList(){
	$("#detailDiv").hide();
	$("#saveMut").hide();
	$("#checkListCharge").show();
}


function saveMutationWithoutEdit(element){
	var appNo= $("#submit").val();
	var popupMsg = "This application has been rejected";
	if($("input:radio[name='workflowActionDto.decision']").filter(":checked").val() == 'REJECTED')
	{
//		return saveOrUpdateForm(element, popupMsg,'AdminHome.html', 'saveMutationWithoutEdit');
		return saveOrUpdateForm(element, popupMsg,'CommonRejectionLetter.html?printCommonRejectionLetter&appNo='+appNo, 'saveMutationWithoutEdit');
	}
	else if($("input:radio[name='workflowActionDto.decision']").filter(":checked").val() == 'FORWARD_TO_EMPLOYEE' || 
			$("input:radio[name='workflowActionDto.decision']").filter(":checked").val() == 'SEND_BACK'){
		return saveOrUpdateForm(element,"Bill Payment done successfully!", 'AdminHome.html', 'saveMutationWithoutEdit');
	}
	var isLastAuth= $("#isLastAuth").val();
	var isBeforeLastAuth= $("#isBeforeLastAuth").val();
	var bypassPublicNotice= $("#bypassPublicNotice").val();
	if(isLastAuth=="false" && isBeforeLastAuth=="false"){
		 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'AdminHome.html', 'saveMutationWithoutEdit');
		}else if(isLastAuth=="true"){
			 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'MutationAuthorization.html?printMutationCertificate', 'saveMutationWithoutEdit');
		}else if(isBeforeLastAuth=="true" && !bypassPublicNotice=="Y" || bypassPublicNotice==""){
			 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'MutationAuthorization.html?printPublicNoticeReport', 'saveMutationWithoutEdit');
		}else if(isBeforeLastAuth=="true" && bypassPublicNotice=="Y"){
			 return saveOrUpdateForm(element,"Bill Payment done successfully!", 'AdminHome.html', 'saveMutationWithoutEdit');
		}
}

function saveMutationWithEdit(element){
	var theForm	=	'#MutationForm';
	var requestData = {};
	var returnData;
	var isBeforeLastAuth= $("#isBeforeLastAuth").val();
	var bypassPublicNotice= $("#bypassPublicNotice").val();
	requestData = __serializeForm(theForm);
	var URL = 'MutationAuthorization.html?saveMutationWithEdit';
	if(isBeforeLastAuth=="true" && bypassPublicNotice=="Y"){
		returnData =doFormActionForSaveProperty(element,"Saved Successfully",URL, true, 'AdminHome.html');
	}else{
	 returnData =doFormActionForSaveProperty(element,"Saved Successfully",URL, true, 'MutationAuthorization.html?printPublicNoticeReport');
	}
}

function editMutation(element){
	var theForm	=	'#MutationForm';
	var data = {};
	data = __serializeForm(theForm);
	var URL = 'MutationAuthorization.html?editMutation';
	returnData = __doAjaxRequest(URL, 'POST', data, false);
	if(returnData){
	$(formDivName).html(returnData);
//	 var ownerType = $("#ownershipNewId").val();
	var ownerType = $("#ownerInfo" + " option:selected").attr("code");
		var data1 = {"ownershipType" : ownerType};
		var URL1 = 'MutationForm.html?getOwnershipTypeDiv';
		var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
	
		$("#ownerDetails").html(returnData1);
		$("#ownerDetails").show();
	}
}



function confirmToProceed(element){ 
	
	var errorList = [];	
//	errorList = ValidateTick(errorList);	
	if (errorList.length == 0) {
	}
	else {
		showErrorOnPage(errorList);
	}
}

function savePropertyFrom(element){
	var kdmcMutFlag = $("#kdmcMutFlag").val();
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
			var returnData = saveOrUpdateForm(element,"Bill Payment done successfully!", 'MutationForm.html?redirectToPay', 'saveform');
			if(returnData==false){
				setOwner(returnData);
				}
			}
			else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N'|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')
				{
				var returnData = saveOrUpdateForm(element,"Bill Payment done successfully!", 'MutationForm.html?PrintReport', 'saveform');
				if(returnData==false){
					setOwner(returnData);
					}
				}
			else if (kdmcMutFlag == 'Y'){
			var returnData = saveOrUpdateForm(element,"Bill Payment done successfully!", 'MutationForm.html?PrintReport', 'saveform');
			if(returnData==false){
				setOwner(returnData);
				}
			}
			else{
				var returnData = saveOrUpdateForm(element,"Bill Payment done successfully!", 'MutationForm.html', 'saveform');
				if(returnData==false){
					setOwner(returnData);
				}
			}

	}


function getMutationDetail(){
	var errorList = [];
	$("#saveMut").hide();
	var propNo= $("#assNo").val();
	var oldPropNo= $("#assOldpropno").val();
	if((propNo == "" || propNo == null || propNo == undefined) && (oldPropNo == "" || oldPropNo == null || oldPropNo == undefined)){
		errorList.push(getLocalMessage("mutation.input.validation"));
		return showErrorOnPage(errorList);
	}
	var data = {"propNo" : propNo,
			  "oldPropNo": oldPropNo
			};
	var URL = 'MutationForm.html?getMutationDetail';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(formDivName).html(returnData);	/*$("#dataDiv").show();*/
	
}

function getCheckListAndCharges(element){
	var theForm	=	'#MutationForm';
	var requestData = {};
	requestData = __serializeForm(theForm);

	var URL = 'MutationForm.html?getCheckListAndCharges';
	var returnData =__doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
	if(returnData){
	 $(formDivName).html(returnData);
	 var ownerType = $("#ownershipNewId").val();
		var data1 = {"ownershipType" : ownerType};
		var URL1 = 'MutationForm.html?getOwnershipTypeDiv';
		var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);		
		$("#ownerDetails").html(returnData1);
		$("#ownerDetails").find("select").prop("disabled","disabled");
		$("#ownerDetails").find("input").prop("disabled","disabled");
		$("#ownerDetails").show();
		$("#checkListCharge").hide();
		
	}
	
}

function setOwner(returnData){
		 var ownerType = $("#ownershipNewId").val();
			var data1 = {"ownershipType" : ownerType};
			var URL1 = 'MutationForm.html?getOwnershipTypeDiv';
			var returnData1 = __doAjaxRequest(URL1, 'POST', data1, false);
			
			$("#ownerDetails").html(returnData1);
			$("#ownerDetails").show();
		
	
}

function showErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}

function doFormActionForSaveProperty(obj,successMessage, URL, sendFormData, successUrl)
{
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	
	             var requestData = {};
	
				if (sendFormData) {
					
					requestData = __serializeForm(theForm);
				}
			
				var url	=URL;
				
				
				var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
				if ($.isPlainObject(returnData))
				{
					var message = returnData.command.message;
					
					var hasError = returnData.command.hasValidationError;
					
					if (!message) {
						message = successMessage;
					}
					
					if(message && !hasError)
						{
						   	if(returnData.command.hiddenOtherVal == 'SERVERERROR')
						   		
						   		showSaveResultBox(returnData, message, 'AdminHome.html');
						   	
						   	else
						   		
						   		showSaveResultBox(returnData, message, successUrl);
						}
					else if(hasError)
					{
						$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
					}
					else
						return returnData;
					
				}
				else if (typeof(returnData) === "string")
				{
					$(formDivName).html(returnData);	
					prepareTags();
				}
				else 
				{
					alert("Invalid datatype received : " + returnData);
				}
				
				return false;
				
	
}

//fetch API details on selection of button 
function getLandApiDetails(obj){
	var landTypePrefix=$(".landValue").val();
	var data = {};
	var URL = 'MutationForm.html?getLandTypeApiDetails';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);		
	$("#showAuthApiDetails").html(returnData);
	$("#showAuthApiDetails").show();		
}


function generateLoiCharges(element) {
    var divName = '.content-page';
    var URL = 'MutationAuthorization.html?generateLoiCharges';
    var formName = findClosestElementId(element, 'form');
    var theForm = '#' + formName;
    var requestData = {};
    requestData = __serializeForm(theForm);
    var returnData = __doAjaxRequest(URL, 'Post', requestData, false, 'html');

    $(divName).removeClass('ajaxloader');
    $(divName).html(returnData);
    prepareTags();
   
}

function saveMutationAfterApproval(element) {
	var errorList = [];
	var decision = $("input[name='workflowActionDto.decision']:checked").val();
	var remark = $("#comments").val();
	if (decision == '' || decision == undefined) {
		errorList.push(getLocalMessage("property.select.atleast.oneDecision"));
	}
	if (remark == "") {
		errorList.push(getLocalMessage("property.enter.remark"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		return saveOrUpdateForm(element, "", 'AdminHome.html',
				'saveAfterApproval');
	}
}

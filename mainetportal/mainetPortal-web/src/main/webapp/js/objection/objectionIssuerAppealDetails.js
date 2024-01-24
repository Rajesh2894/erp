$(document).ready(function() {
	
	var isValidationError = $("#isValidationError").val();

	$('#rtiObjIssuerDetailsForm').validate({

		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	
	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true
	});

	$('body').on('focus', ".hasMobileNo", function() {
		$('.hasMobileNo').keyup(function() {
			this.value = this.value.replace(/[^1-9][0-9]{9}/g, '');
			$(this).attr('maxlength', '10');
		});
	});

	$('body').on('focus', ".hasPincode", function() {
		$('.hasPincode').keyup(function() {
			this.value = this.value.replace(/[^1-9][0-9]{5}/g, '');
			$(this).attr('maxlength', '6');
		});
	});

	$("#serviceId").change(function(e)
			{
				
				if($('#serviceId option:selected').attr('code') == "RFA")
				{
					
					$("#proceedbutton").show();
					$("#save").hide();
				}
				});
	
	
});

function resetRtiObjDetail() {
	$('input[type=text]').val('');
	$(".alert-danger").hide();
	$("#rtiObjIssuerDetailsForm").validate().resetForm();
}


function saveObjectionDetailForm(element) {	
	
	var errorList = [];
	errorList = validateObjDetailForm(errorList);
	/*if (errorList.length == 0) {
	var result= saveOrUpdateForm(element, "Objection Against Application is submitted successfully!",
				'ObjectionDetails.html', 'saveObjectionDetails');
	if(result==false){
		getObjectionServiceByDept();
	}
	} */
	if (errorList.length == 0) {
		if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N' || $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')
	  	{
		   return saveOrUpdateForm(element,"Objection Against Application is submitted successfully!", 'ObjectionDetails.html?PrintReport', 'saveObjectionDetails');
	    }
		 else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
		return saveOrUpdateForm(element,"Your application Data  saved successfully!!",'ObjectionDetails.html?redirectToPay', 'saveObjectionDetails');
	}
		else{
	var result= saveOrUpdateForm(element, "Objection Against Application is submitted successfully!",
				'CitizenHome.html', 'saveObjectionDetails');
	if(result==false){
		getObjectionServiceByDept();
	}
	}
	}else {
		displayErrorsOnPage(errorList);
	}
}

function validateObjDetailForm(errorList) {
	
	var fName = $("#fName").val();
	var lName = $("#lName").val();
	var gender = $("#gender").val();
	var mobileNo = $("#mobileNo").val();
	var adharno = $("#uid").val();
	var address=$("#address").val();
	var objectionDeptId=$("#objectionDeptId").val();
	var objType = $("#serviceId").val();
	var locationId=$("#locId").val();
	var appealobjdetails = $("#ObjDetails").val();
	var objRefNo = $("#objectionReferenceNumber").val();
	var objectionOn = $("#objectionOn").val();
	

	if (fName == "" || fName == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantFirstName"));
	}
	if (lName == "" || lName == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantLastName"));
	}
	if (gender == "0" || gender == undefined) {
		errorList.push(getLocalMessage("rti.validation.Gender"));
	}
	if (mobileNo == "" || mobileNo == undefined) {
		errorList.push(getLocalMessage("rti.validation.ApplicantMobileNo"));
	}
	if (adharno == "" || adharno == undefined) {
		errorList.push(getLocalMessage("rti.validation.AadharNo"));
	}
	if (address == "" || address == undefined) {
		errorList.push(getLocalMessage("Please Enter Address"));
	}
	if (objectionDeptId == "0" || objectionDeptId == undefined || objectionDeptId=="") {
		errorList.push(getLocalMessage("Department must not be empty"));
	}
	if (objType == "0" || objType == undefined || objType=="") {
		errorList.push(getLocalMessage("Service name must not be empty"));
	}
	if (objectionOn == "0" || objectionOn == undefined || objectionOn=="") {
		errorList.push(getLocalMessage("Please select Objection On"));
	}
	if (objRefNo == "" || objRefNo == undefined) {
		errorList.push(getLocalMessage("Property No/RTI No/Trade License No must not be empty"));
	}
	if (appealobjdetails == "" || appealobjdetails == undefined) {
		errorList.push(getLocalMessage("Objection Details must not be empty"));
	}
	if (locationId == "0" || locationId == undefined || locationId=="") {
		errorList.push(getLocalMessage("Please select Location"));
	}	
	return errorList;
}
//used to get Objection service by Department
function getObjectionServiceByDept(){
	var errorList = [];
	var requestData = {"objectionDeptId":$('#objectionDeptId option:selected').attr('value')}
	var URL = 'ObjectionDetails.html?getObjectionServiceByDepartment';
	var returnData=  __doAjaxRequest(URL,'POST',requestData, false,'html');
	$('#serviceId').html('');
	$('#serviceId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	
	if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
	{
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
		//	$('#serviceId').append($("<option></option>").attr("value", value.lookUpId).text((value.descLangFirst)));   
			$('#serviceId').append($("<option></option>").attr("value",value.lookUpId).attr("code",value.lookUpCode).text(value.descLangFirst));
		});
		
	}else{
		
		errorList
		.push(getLocalMessage('Please Configure Service For Department'));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	
	getLocationByDeptName();
}

//use to get Department List
function getDeptName()
{
	var requestData = {"objectionDeptId":$('#objectionDeptId option:selected').attr('value')}
	var URL = 'ObjectionDetails.html?getDeptName';
	var returnData=  __doAjaxRequest(URL,'POST',requestData, false,'html');
	if(returnData != null)
	{
		$("#objectionReferenceNumber").html(returnData);
	}
}

//used to get Location by Department Name
function getLocationByDeptName()
{
	var errorList = [];
	var requestData = {"objectionDeptId":$('#objectionDeptId option:selected').attr('value')}
	var URL = 'ObjectionDetails.html?getLocationByDepartment';
	var returnData=  __doAjaxRequest(URL,'POST',requestData, false,'html');
	$('#locId').html('');
	$('#locId').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
		
	if(returnData != null && returnData != undefined && returnData != "" && returnData != "[]")
	{
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#locId').append(
					$("<option></option>").attr("value", value.lookUpId).text(
							(value.descLangFirst)));
		});
	}else{
		
		errorList
		.push(getLocalMessage('Please Configure Location For Department'));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
}

function loadPreDataOnValidation(){
	getObjectionServiceByDept();
	getDeptName();
}

function displayErrorsOnPage(errorList){
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

function getCharges(element){
	
	var errorList = [];
	errorList = validateObjDetailForm(errorList);
	
	if (errorList.length == 0) {
	var serviceCode =$('#serviceId option:selected').attr('value');
	var theForm	=	'#ObjIssuerDetailsForm';
	var data = {};
	data = __serializeForm(theForm);
	var URL = 'ObjectionDetails.html?saveObjectionOrGetCharges';
	returnData = __doAjaxRequest(URL, 'POST', data, false);
	if(returnData){
	$(formDivName).html(returnData);

	}
	}
	else {
		displayErrorsOnPage(errorList);
	}
}
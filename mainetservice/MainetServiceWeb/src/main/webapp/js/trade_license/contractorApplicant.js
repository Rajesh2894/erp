function openVendorMaster(element){
	
	var errorList = [];

	errorList = ValidateContractorApplicant(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);	
	} else{
	
		 /*var requestUrl = "ContractorApplicantDetail.html?form";        
		 var requestData = {     

		 	};       
		var ajaxResponse = doAjaxLoading(requestUrl, requestData, 'html', ''); */
		autoUpdateValues();           
		$("#vendorMasterDiv").show();
		
		$("#divSubmit").hide();
		
		$("#submitDiv").hide();
		
		$("#saveSubmit").show();
		
		
	}
	
}

function resetForm(){
	
	let actionParam = 'addCouncilMOM';
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('ContractorApplicantDetail.html', {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
}

function ValidateContractorApplicant(errorList){
	
	var title = $("#titleId").val();
	var fname = $('#fName').val();
	var lname = $('#lName').val();
	var gender = $('#gender').val();
	var buildingname = $('#bldgName').val();
	var wardNo = $('#wardNo').val();
	var city = $('#cityName').val();
	var mobileNo= $('#mobileNo').val();
	
	if(title=="0"||title==null)
		errorList.push(getLocalMessage("rnl.validation.title"));
	if(fname==""||fname==null)
		errorList.push(getLocalMessage("tradelicense.validation.fname"));
	if(lname==""||lname==null)
		errorList.push(getLocalMessage("tradelicense.validation.lname"));
	if(gender=="0"||gender==null)
		errorList.push(getLocalMessage("tradelicense.validation.gender"));
	if(buildingname==""||buildingname==null)
		errorList.push(getLocalMessage("tradelicense.validation.buildingname"));
	if(wardNo=="0"||wardNo==null)
		errorList.push(getLocalMessage("tradelicense.validation.wardNo"));
	if(city==""||city==null)
		errorList.push(getLocalMessage("tradelicense.validation.city"));
	if(mobileNo==""||mobileNo==null)
		errorList.push(getLocalMessage("tradelicense.validation.ownerMobileNo")); 
	else{
		if (!validateMobile(mobileNo)) 
			errorList.push(getLocalMessage("tradelicense.validation.validMobileNo"));
		
	}
	/*else if(!validatePhone(mobileNo) || mobileNo == undefined)){
		errorList.push(getLocalMessage("rnl.validation.mobileNo"));
	}*/
	
	
	return errorList;
}
function validatePhone(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}

function showPopUp(returnData, successMessage, redirectUrl) {

	var messageText = successMessage;
	
	var message='';
	var cls = getLocalMessage('eip.page.process');
	
	 message	+='<p class="text-blue-2 text-center padding-15">'+ messageText+'</p>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-success\'    '+ 
	' onclick="closebox1()"/>'+	
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message); 
	$(errMsgDiv).show();
	$('#btnNo').focus();
    showModalBoxWithoutClose(errMsgDiv);
	return false;
}
function closebox1(){
	
	window.location.href = 'AdminHome.html';
	$.fancybox.close();
	
}

function disposeModalBox(){	
	$('#'+modalDivName).hide();
	return false;
}

function saveContractorApplicant(element){
	debugger;
	$('.error-div').hide();
	var errorList = [];

	// var sliMode = $('#sliMode').val();
	// $('#sliPrefixMode').val(sliMode);

	var panNo = $('#vendor_vmpannumber').val();
	var mobileNo = $('#vendor_mobileNo').val();
	var uidNo = $('#vendor_vmuidno').val();
	var vat = $("#vendor_tinnumber").val();
	var gst = $('#gstNumber').val();
	
	var vendorType = $("#cpdVendortype").val();
	
	var vendorSubType = $("#cpdVendorSubType").val();
	
	var vendorName = $("#vendor_vmvendorname").val();
	
	var vendorAddress = $("#vendor_vvmvendoradd").val();
	
	var vendorClass = $("#vendor_venClassName").val();
	
	if(vendorType == "" || vendorType == undefined || vendorType == null)
		errorList.push(getLocalMessage("tradeLicense.validation.vendorType"));
	
	if(vendorClass == "" || vendorClass == undefined || vendorClass == null)
		errorList.push(getLocalMessage("tradeLicense.validation.vendorClass"));
	
	if(vendorSubType == "" || vendorSubType == undefined || vendorSubType == null)
		errorList.push(getLocalMessage("tradeLicense.validation.vendorSubType"));
	
	if(vendorName == "" || vendorName == undefined || vendorName == null)
		errorList.push(getLocalMessage("tradeLicense.validation.vendorName"));
	
	if(mobileNo==""||mobileNo==null)
		errorList.push(getLocalMessage("tradelicense.validation.ownerMobileNo")); 
	else{
		if (!validateMobile(mobileNo)) 
			errorList.push(getLocalMessage("tradelicense.validation.validMobileNo"));
	}
	if(vendorAddress == "" || vendorAddress == undefined || vendorAddress == null)
		errorList.push(getLocalMessage("tradelicense.validation.address"));
		
		
	if (gst != 0 || gst != '') {
		/*var regExGst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([a-zA-Z]){1}([0-9a-zA-Z]){1}?$/;*/
		var regExGst = /^([0-9]){2}([A-Z]){5}([0-9]){4}([A-Z]){1}([1-9A-Z]){1}Z([0-9A-Z]){1}?$/;
		if (!regExGst.test(gst) && gst != '') {
			errorList.push(getLocalMessage("tbOrganisation.error.gstNo"));
		}
	}
	
	var bankAccNo=$("#vendor_bankaccountnumber").val();
	
	if(bankAccNo!="" && bankAccNo!=undefined && bankAccNo.length <8 || bankAccNo.length>16 ){
		errorList.push("Please Enter Account Number Minimum 8 or Maximum 16 Digit");
	}
	if (mobileNo.length == 10) {
		var mobileRegex = /^[789]\d{9}$/;
		if (!mobileRegex.test(mobileNo)) {
			errorList.push(getLocalMessage('account.valid.mobileNo'));
		}
	}
	
	/*if(panNo =="" && uidNo=="" && gst==""){
		errorList.push(getLocalMessage("tbOrganisation.error.atLeastOne"));
	}*/

	 /*var chk = $('#vendor_rtgsvendorflag').is(':checked');
	 if(chk){
		 $("#rtgsvendorflag").val('Y');
	 }else{
		 $("#rtgsvendorflag").val('N');
	 }*/
		 
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);	
	} else {
		
		if (errorList.length <= 0) {
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var formAction = $("#formAction").val();
			$(theForm).attr('action', formAction);
			var requestData = __serializeForm(theForm);
			var succsessMsg= getLocalMessage('trade.successMsg');
			if($('#form_AddEditMode').val()=="update"){
				succsessMsg= getLocalMessage('account.edit.succ');
			}else{
				succsessMsg= getLocalMessage('trade.successMsg');
			}
			
			var appNo = __doAjaxRequest(
					'ContractorApplicantDetail.html?saveForm','post', requestData, false,'json');
			if(!isNaN(appNo)){
				succsessMsg = succsessMsg + " " + appNo;
				showPopUp(appNo, succsessMsg, 'AdminHome.html');
			}
			else{
				displayErrorsOnPage(errorList);	
			}
			
		} else {
			displayErrorsOnPage(errorList);	
		}
	}
}

function autoUpdateValues(){
	if ($(vendor_vmvendorname).val().length == 0) {
		var vendorName = document.getElementById("fName").value + " " +document.getElementById("lName").value;
		document.getElementById("vendor_vmvendorname").value = vendorName;
	}
	
	if ($(vendor_mobileNo).val().length == 0) {
		var vendorMobileNumber = document.getElementById("mobileNo").value;
		document.getElementById("vendor_mobileNo").value = vendorMobileNumber;
	}
	
	if ($(vendoremailId).val().length == 0) {
		var vendorEmail = document.getElementById("email").value;
		document.getElementById("vendoremailId").value = vendorEmail;
	}
	
}

function showVendorError(errorList) {
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#vendorErrorDiv").html(errMsg);
	$("#vendorErrorDiv").show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
	return false;
}

function fnValidatePAN(Obj) {

	$('.error-div').hide();
	var errorList = [];
	Obj = $('#panNo').val();
	if (Obj != "") {
		ObjVal = Obj;
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
		var code = /([C,P,H,F,A,T,B,L,J,G])/;
		var code_chk = ObjVal.substring(3, 4);
		if (ObjVal.search(panPat) == -1) {
			errorList.push('Invaild PAN Number');
			
		} else if (code.test(code_chk) == false) {
			errorList.push('Invaild PAN Number');
			
		}
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	return errorList;
}

	if (errorList.length > 0) {
		showVendorError(errorList);
	}
};

function validateMobile(mobile) {
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(mobile);
}

function saveContractorApplicantApprovalData(element) {

	var errorList = [];
	if ($("#remarkApproval").val() == "") {
		errorList.push("Please enter the remark")
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest($(theForm).attr('action')+ '?saveContractorApplicantApprovalData', 'POST', requestData,false, 'json');
		if (object.error != null && object.error != 0) {
			$.each(object.error, function(key, value) {
				$.each(value, function(key, value) {
					if (value != null && value != '') {
						errorList.push(value);
					}
				});
			});
			displayErrorsOnPage(errorList);
		} else {
			
			if (object.wfStatus == 'REJECTED') {
				showBoxForApproval(getLocalMessage('DeploymentOfStaffDTO.Validation.rejectedStatus'));
			} else if (object.wfStatus == 'SEND_BACK') {
				showBoxForApproval(getLocalMessage('DeploymentOfStaffDTO.Validation.sendBack'));
			} else if (object.wfStatus == 'FORWARD_TO') {
				showBoxForApproval(getLocalMessage('DeploymentOfStaffDTO.Validation.forward'));
			} else {
				showBoxForApproval(getLocalMessage('DeploymentOfStaffDTO.Validation.acceptStatus'));
			}
		}
	}
}

function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  'Proceed';
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

function closeApproval() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}

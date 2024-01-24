var removeDevLicenseArray=[];

$(document).ready(function() {
	$("#showCompanyApiHeading").hide();
	var directorInfoFlag = $("input[name='developerRegistrationDTO.directorInfoFlag']:checked").val();
	if (directorInfoFlag === 'Y') {
		$('#directorsInfo').show();
		$('#directorsInfoSum').show();
	} else {
		$('#directorsInfo').hide();
		$('#directorsInfoSum').hide();
	}
	
	$(".directorInfoSelect").click(function() {
		var directorInfoFlag = $("input[name='developerRegistrationDTO.directorInfoFlag']:checked").val();
		if (directorInfoFlag === 'Y') {
			$('#directorsInfo').show();
		} else {
			$('#directorsInfo').hide();
		}
	});

	var licenseInfoFlag = $("input[name='developerRegistrationDTO.licenseInfoFlag']:checked").val();
	if (licenseInfoFlag === 'Y') {
		$('#licenseInfo').show();
		$('#licenseInfoSum').show();
	} else {
		$('#licenseInfo').hide();
		$('#licenseInfoSum').hide();
	}
	
	$(".licenseInfoDiv").click(function() {
		var licenseInfoFlag = $("input[name='developerRegistrationDTO.licenseInfoFlag']:checked").val();
		if (licenseInfoFlag === 'Y') {
			$('#licenseInfo').show();
		} else {
			$('#licenseInfo').hide();
		}
	});
	
	$(".licenseDate").datepicker(
	{
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-1d',
		yearRange : "-100:-0"
		});
	
	$("#LLPDiv").hide();
	$("#nameDiv").hide();
	$("#genderDiv").hide();
	$("#dobDiv").hide();
	$("#panNo").hide();

	$("#LLPDivSum").hide();
	$("#nameDivSum").hide();
	$("#genderDivSum").hide();
	$("#dobDivSum").hide();
	$("#panNoSum").hide();
	
	var devTypeCode=$('#devType option:selected').attr('code');
	$("#devType").change(function() {
		var devTypeCode=$('#devType option:selected').attr('code');
		if(devTypeCode=="COM" || devTypeCode=="PAF"){
			$("#CINDiv").show();
			$("#companyDiv").show();
			$("#dateOfIncorporation").show();
			$("#commonDetails").show();
			$("#stakeholderDiv").show();
			$("#LLPDiv").hide();
			$("#nameDiv").hide();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").hide();
			$("#firmNameDiv").hide();
			if($("#companyDetailsAPIFlagId").val()=='Y'){
				$("#showCompanyApiHeading").show();
			}
		}else if(devTypeCode=="IND" || devTypeCode=="HUF"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").hide();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").hide();
			$("#nameDiv").show();
			$("#genderDiv").show();
			$("#dobDiv").show();
			$("#panNo").show();
			$("#panIndVerifyBtn").show()
			$("#firmNameDiv").hide();
			$("#showCompanyApiHeading").hide();

		}
		else if(devTypeCode=="PRF"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").hide();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").hide();
			$("#nameDiv").show();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").show();
			$("#panIndVerifyBtn").hide()
			$("#firmNameDiv").hide();
			$("#showCompanyApiHeading").hide();
		}
		else if(devTypeCode=="LLP"){
			$("#CINDiv").hide();
			$("#companyDiv").hide();
			$("#dateOfIncorporation").show();
			$("#commonDetails").show();
			$("#stakeholderDiv").hide();
			$("#LLPDiv").show();
			$("#nameDiv").hide();
			$("#genderDiv").hide();
			$("#dobDiv").hide();
			$("#panNo").hide();
			$("#firmNameDiv").show();
			$("#showCompanyApiHeading").hide();
		}
	});
	
	var devTypeCode=$('#devType option:selected').attr('code');
	if(devTypeCode=="COM" || devTypeCode=="PAF"){
		$("#CINDiv").show();
		$("#companyDiv").show();
		$("#dateOfIncorporation").show();
		$("#commonDetails").show();
		$("#stakeholderDiv").show();
		$("#LLPDiv").hide();
		$("#nameDiv").hide();
		$("#genderDiv").hide();
		$("#dobDiv").hide();
		$("#panNo").hide();
		
		$("#CINDivSum").show();
		$("#companyDivSum").show();
		$("#dateOfIncorporationSum").show();
		$("#commonDetailsSum").show();
		$("#stakeholderDivSum").show();
		$("#LLPDivSum").hide();
		$("#nameDiv").hide();
		$("#genderDiv").hide();
		$("#dobDiv").hide();
		$("#panNoSum").hide();
		$("#firmNameDiv").hide();
		$("#firmNameDivSum").hide();
		if($("#companyDetailsAPIFlagId").val()=='Y'){
			$("#showCompanyApiHeading").show();
		}
	}else if(devTypeCode=="IND" || devTypeCode=="HUF"){
		$("#CINDiv").hide();
		$("#companyDiv").hide();
		$("#dateOfIncorporation").hide();
		$("#commonDetails").show();
		$("#stakeholderDiv").hide();
		$("#LLPDiv").hide();
		$("#nameDiv").show();
		$("#genderDiv").show();
		$("#dobDiv").show();
		$("#panNo").show();
		$("#panIndVerifyBtn").show()
		
		$("#CINDivSum").hide();
		$("#companyDivSum").hide();
		$("#dateOfIncorporationSum").hide();
		$("#commonDetailsSum").show();
		$("#stakeholderDivSum").hide();
		$("#LLPDivSum").hide();
		$("#nameDivSum").show();
		$("#genderDivSum").show();
		$("#dobDivSum").show();
		$("#panNoSum").show();
		$("#firmNameDiv").hide();
		$("#firmNameDivSum").hide();
		$("#showCompanyApiHeading").hide();
	}
	else if(devTypeCode=="PRF"){
		$("#CINDiv").hide();
		$("#companyDiv").hide();
		$("#dateOfIncorporation").hide();
		$("#commonDetails").show();
		$("#stakeholderDiv").hide();
		$("#LLPDiv").hide();
		$("#nameDiv").show();
		$("#genderDiv").hide();
		$("#dobDiv").hide();
		$("#panNo").show();
		$("#panIndVerifyBtn").hide()
		
		$("#CINDivSum").hide();
		$("#companyDivSum").hide();
		$("#dateOfIncorporationSum").hide();
		$("#commonDetailsSum").show();
		$("#stakeholderDivSum").hide();
		$("#LLPDivSum").hide();
		$("#nameDivSum").show();
		$("#genderDivSum").hide();
		$("#dobDivSum").hide();
		$("#panNoSum").show();
		$("#firmNameDiv").hide();
		$("#firmNameDivSum").hide();
		$("#showCompanyApiHeading").hide();
	}
	else if(devTypeCode=="LLP"){
		$("#CINDiv").hide();
		$("#companyDiv").hide();
		$("#dateOfIncorporation").show();
		$("#commonDetails").show();
		$("#stakeholderDiv").hide();
		$("#LLPDiv").show();
		$("#nameDiv").hide();
		$("#genderDiv").hide();
		$("#dobDiv").hide();
		$("#panNo").hide();
		
		$("#CINDivSum").hide();
		$("#companyDivSum").hide();
		$("#dateOfIncorporationSum").show();
		$("#commonDetailsSum").show();
		$("#stakeholderDivSum").hide();
		$("#LLPDivSum").show();
		$("#nameDivSum").hide();
		$("#genderDivSum").hide();
		$("#dobDivSum").hide();
		$("#panNoSum").hide();
		$("#firmNameDiv").show();
		$("#firmNameDivSum").show();
		$("#showCompanyApiHeading").hide();
	}
	
	$(".authDOB").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-1d',
		yearRange : "-100:-0"
	});
	
	$('#projectsQue').hide();
	var licenseHDRUFlag = $(
			"input[name='developerRegistrationDTO.licenseHDRUFlag']:checked")
			.val();
	if (licenseHDRUFlag === 'Y') {
		$('#licenseHDRU').show();
		$('#projectsQue').hide();
		$('#licenseHDRUSum').show();
		$('#projectsQueSum').hide();
	} else if (licenseHDRUFlag === 'N') {
		$('#licenseHDRU').hide();
		$('#projectsQue').show();
		$('#licenseHDRUSum').hide();
		$('#projectsQueSum').show();
		var projectQueDiv = $("input[name='developerRegistrationDTO.projectsFlag']:checked").val();
		if (projectQueDiv === 'Y') {
			$('#licenseProject').show();
			$('#licenseProjectSum').show();
		} else {
			$('#licenseProject').hide();
			$('#licenseProjectSum').hide();
		}
	} else {
		$('#licenseHDRU').hide();
		$('#projectsQue').hide();
		$('#licenseHDRUSum').hide();
		$('#projectsQueSum').hide();
	}
	
	$(".licenseHDRUDiv").click(function() {
		var radioValueSel = $("input[name='developerRegistrationDTO.licenseHDRUFlag']:checked").val();
		if (radioValueSel === 'Y') {
			$('#licenseHDRU').show();
			$('#projectsQue').hide();
			$('#licenseProject').hide();
		} else if(radioValueSel === 'N') {
			$('#licenseHDRU').hide();
			$('#projectsQue').show();
			var projectQueDiv = $("input[name='developerRegistrationDTO.projectsFlag']:checked").val();
			if (projectQueDiv === 'Y') {
				$('#licenseProject').show();
				$('#licenseProjectSum').show();
			} else {
				$('#licenseProject').hide();
				$('#licenseProjectSum').hide();
			}
		}else{
			$('#licenseHDRU').hide();
			$('#projectsQue').hide();
		}
	});

	var radioValue = $("input[name='developerRegistrationDTO.projectsFlag']:checked").val();
	if (radioValue === 'Y') {
		$('#licenseProject').show();
		$('#licenseProjectSum').show();
	} else {
		$('#licenseProject').hide();
		$('#licenseProjectSum').hide();
	}
	
	$(".licenseProjectDiv").click(function() {
		var radioValueSel = $("input[name='developerRegistrationDTO.projectsFlag']:checked").val();
		if (radioValueSel === 'Y') {
			$('#licenseProject').show();
			$('#licenseHDRU').hide();
		} else {
			$('#licenseProject').hide();
		}
	});
	
	$('#authorizedUserTable tbody tr')
	.each(function(i) {
				var panVerifiedFlagVar = $.trim($("#panVerifiedFlagId" + i).val());
				if(panVerifiedFlagVar =="Y"){
					$.trim($("#addAuthUserId" + i).removeClass("disabled"));
				}else{
					$.trim($("#addAuthUserId" + i).addClass("disabled"));
				}
			});
	

	$(".dateOfGrantLicenseHDRU").datepicker(
		{
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-1d',
			yearRange : "-100:-0",
			onClose : function(selectedDate) {
				$(".licenseValidity")
						.datepicker("option",
								"minDate",
								selectedDate);
			}
		});

	$('.hasAlphaNumericClass').keyup(function () { 
	    this.value = this.value.replace(/[^a-z A-Z 0-9]/g,'');
	   
	});
	
	$(".success-div").hide();
	$('#devLicenseTable tbody tr').each(
			function(i) {
				$(".dateOfGrantLicenseHDRU"+i).datepicker(
						{
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							maxDate : '-1d',
							yearRange : "-100:-0",
							onClose : function(selectedDate) {
								$(".licenseValidity"+i)
										.datepicker("option",
												"minDate",
												selectedDate);
							}
						});

				$(".licenseValidity"+i).datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true
				});
			});
	
	var maxBirthDate = new Date();
	maxBirthDate.setFullYear( maxBirthDate.getFullYear() - 18,11,31);
	$(".DOB").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-1d',
		maxDate: maxBirthDate,
		yearRange: '1950:'+maxBirthDate.getFullYear()	
		});
	
	$('#authorizedUserTable tbody tr').each(function(i) {
		var panDetailsFlagIdVar = $.trim($("#panDetailsFlagId" + i).val());
		if(panDetailsFlagIdVar=='Y'){
			$.trim($("#devloperAuthId" + i).addClass("disabled"));
			$.trim($("#authUserNameId" + i).addClass("disabled"));
			$.trim($("#authMobileNoId" + i).addClass("disabled"));
			$.trim($("#authEmailId" + i).addClass("disabled"));
			$.trim($("#authGenderId" + i).addClass("disabled"));
			$.trim($("#authDOBId" + i).addClass("disabled"));
			$.trim($("#authPanNumberId" + i).addClass("disabled"));			
		}
	});
});

function applicantInfo(){

	var directorInfoFlag = $("input[name='developerRegistrationDTO.directorInfoFlag']:checked").val();
	if (directorInfoFlag === 'Y') {
		$('#directorsInfo').show();
	} else {
		$('#directorsInfo').hide();
	}
	
	var licenseInfoFlag = $("input[name='developerRegistrationDTO.licenseInfoFlag']:checked").val();
	if (licenseInfoFlag === 'Y') {
		$('#licenseInfo').show();
	} else {
		$('#licenseInfo').hide();
	}
	
	$("#individualDiv").hide();
	$("#LLPDiv").hide();
	var devTypeCode=$('#devType option:selected').attr('code');
}

function addDevRow(element, id){
	$('#errorDivId').hide();
	var errorList = [];
	
	errorList = validateDevDetails(errorList);
	errorList = errorList.concat(validateStkPercent(id));
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var row = $("#stakeholderTable tbody .developerAppendable").length;

	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('DeveloperRegistrationForm.html?addStakeholderRow',
			'POST', requestData, false, 'html');	
	$(formDivName).html(response);	
	prepareTags();
}


function validateDevDetails() {

	var errorList = [];
	$('#stakeholderTable tbody tr')
			.each(
					function(i) {
						var devloperName = $.trim($("#devloperNameId" + i)
								.val());
						var devloperDesignation = $.trim($("#devloperDesignationId" + i).val());
						var devloperPercentage = $.trim($("#devloperPercentageId" + i).val());
						if (devloperName == null || devloperName == "") {
							errorList
									.push(getLocalMessage("enter.devloper.name"));
						}
						if (devloperDesignation == null || devloperDesignation == "") {
							errorList
									.push(getLocalMessage("enter.devloper.designation"));
						}
						if (devloperPercentage == null || devloperPercentage == "") {
							errorList
									.push(getLocalMessage("enter.devloper.percentage"));
						}
					});

	return errorList;
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$("#errorDivId").show();
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	return false;
}



function deleteDevRow(obj, id) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var row = $("#stakeholderTable tbody .developerAppendable").length;
		$("#stkRowId").val(id);
		var requestData = __serializeForm(theForm);
		if (row != 1) {
			var response = __doAjaxRequest('DeveloperRegistrationForm.html?deleteStakeholderRow',
					'POST', requestData, false, 'html');
			$(formDivName).html(response);
			prepareTags();
			applicantInfo();
		}

}


function addDirectorRow(element){
	$('#errorDivId').hide();
	var errorList = [];
	
	errorList = validateDirectorDetails(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var row = $("#directorsInfoTable tbody .directorAppendable").length;

	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('DeveloperRegistrationForm.html?addDirectorRow',
			'POST', requestData, false, 'html');	
	$(formDivName).html(response);	
	prepareTags();
}


function validateDirectorDetails() {
	var errorList = [];
	$('#directorsInfoTable tbody tr')
			.each(
					function(i) {
						var dinNumber = $.trim($("#dinNumberId" + i)
								.val());
						var directorName = $.trim($("#directorNameId" + i).val());
						var contactNo = $.trim($("#directorContactNumberId" + i).val());
						if (dinNumber == null || dinNumber == "") {
							errorList
									.push(getLocalMessage("enter.din.number"));
						}
						if(contactNo!=null && contactNo!=""){
							if(contactNo.length<=9){
								errorList.push(getLocalMessage("enter.valid.contact.digit"));
							}else if (!validateContact(contactNo)) {
								errorList.push(getLocalMessage("enter.valid.contact"));
							}
						}						
						if (directorName == null || directorName == "") {
							errorList
									.push(getLocalMessage("enter.director.name"));
						}
					});

	return errorList;
}

function deleteDirectorRow(obj, id) {
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var row = $("#directorsInfoTable tbody .directorAppendable").length;
		$("#directorRowId").val(id);
		var requestData = __serializeForm(theForm);
		if (row != 1) {
			var response = __doAjaxRequest('DeveloperRegistrationForm.html?deleteDirectorRow',
					'POST', requestData, false, 'html');
			$(formDivName).html(response);
			prepareTags();
			applicantInfo
		}
}


function saveApplicantForm(element){	
	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		var divName = '#applicantInfo';
		var targetDivName = '#authorizedUser';
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		
		applicantResponse = __doAjaxRequest(
				'DeveloperRegistrationForm.html?saveDevloperInfo', 'POST', requestData,
				false, '', 'html');
		
		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + applicantResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {			
			$(targetDivName).html(applicantResponse);
			let parentTab =  '#devParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#authorizedUser-tab').removeClass('disabled');
				$('.nav li#authorizedUser-tab').removeClass('link-disabled');
				$('.nav li#developerCapacity-tab').addClass('disabled');
				$('.nav li#summarys-tab').addClass('disabled');
			}
			$(''+parentTab+' a[href="#authorizedUser"]').tab('show');
			prepareDateTag();
		}	
	}

}	

function addAuthUserRow(element){
	$('#errorDivId').hide();
	var errorList = [];
	errorList = validateAuthUserDetails(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var row = $("#authorizedUserTable tbody .AuthUserAppendable").length;

	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('DeveloperRegistrationForm.html?addAuthUserRow',
			'POST', requestData, false, 'html');	
	$("#authorizedUser").html(response);	
	prepareTags();
}


function validateAuthUserDetails(errorList) {
	var mobileNoArray = [];
	var emailArray = [];
	var panNoArray = [];
	$('#authorizedUserTable tbody tr').each(function(i) {
		var authUserName = $.trim($("#authUserNameId" + i).val());
		var authMobileNo = $.trim($("#authMobileNoId" + i).val());
		var authEmail = $.trim($("#authEmailId" + i).val());
		var authGender = $.trim($("#authGenderId" + i).val());
		var authDOB = $.trim($("#authDOBId" + i).val());
		var authPanNumber = $.trim($("#authPanNumberId" + i).val());

		if (authUserName == null || authUserName == "")
			errorList.push(getLocalMessage("enter.auth.user.name")+ " for Sr. No. " + (i + 1));
		if (authMobileNo == null || authMobileNo == "")
			errorList.push(getLocalMessage("enter.auth.mobile.no")+ " for Sr. No. " + (i + 1));
		else if (authMobileNo != null && authMobileNo != "")
			errorList = errorList.concat(validateMobNo(authMobileNo));
		if (authEmail == null || authEmail == "")
			errorList.push(getLocalMessage("enter.auth.email")+ " for Sr. No. " + (i + 1));
		else if (!validateEmailId(authEmail))
			errorList.push(getLocalMessage("professional.val.invalid.email.id")+ " for Sr. No. " + (i + 1));
		if (authGender == null || authGender == "")
			errorList.push(getLocalMessage("select.auth.gender") + " for Sr. No. " + (i + 1));
		if (authDOB == null || authDOB == "")
			errorList.push(getLocalMessage("select.auth.DOB") + " for Sr. No. " + (i + 1));
		if (authPanNumber == null || authPanNumber == "")
			errorList.push(getLocalMessage("select.auth.pan.number") + " for Sr. No. " + (i + 1));

		if (mobileNoArray.includes(authMobileNo))
			errorList.push(getLocalMessage("Duplicate Mobile No. Found for") + " " + authMobileNo + " At Sr. No. " + (i + 1));
		mobileNoArray.push(authMobileNo);
		if (emailArray.includes(authEmail))
			errorList.push(getLocalMessage("Duplicate Email Found for") + " " + authEmail + " At Sr. No. " + (i + 1));
		emailArray.push(authEmail);
		if (panNoArray.includes(authPanNumber))
			errorList.push(getLocalMessage("Duplicate Pan Number Found for") + " " + authPanNumber + " At Sr. No. " + (i + 1));
		panNoArray.push(authPanNumber);

	});
	return errorList;
}


function deleteAuthUserRow(obj, id) {	
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var row = $("#authorizedUserTable tbody .AuthUserAppendable").length;
	$("#authUserId").val(id);
	var requestData = __serializeForm(theForm);
	if (row != 1) {
		var response = __doAjaxRequest('DeveloperRegistrationForm.html?deleteAuthUserRow',
				'POST', requestData, false, 'html');
		$("#authorizedUser").html(response);
		prepareTags();
	}
}

function showTab(tabId) {
	$('.error-div').hide();
	$('#devParentTab a[href="' + tabId + '"]').tab('show');
}

function saveAuthUserForm(element){	
	$('.error-div').hide();
	$("#validationerror_errorslist").html('');
	$("#validationerror_errorslist").hide();
	var errorList = [];
	errorList = validateAuthUserDetails(errorList);
	$('#authorizedUserTable tbody tr').each(function(i) {
		var panVerifiedFlagVal = $("#panVerifiedFlagId" +i).val();
		if (panVerifiedFlagVal == "N") {
			errorList.push(getLocalMessage("Please verify PAN details")+" for Sr. No. " + (i + 1));
		}
	});	
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		var divName = '#authorizedUser';
		var targetDivName = '#developerCapacity';
		var requestData = $('form').serialize();
		var ajaxResponse = __doAjaxRequest('DeveloperRegistrationForm.html?saveAuthorizedUserInfo', 'POST', requestData, false, 'html');
		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<form id="authorizedUserForm">' + ajaxResponse + '</form>');
		var isMobileValidation = tempDiv.find('#isMobileValidation');
		if ("Y" == isMobileValidation.val()) {	
			$(divName).html(ajaxResponse);
		} else {
			$(targetDivName).html(ajaxResponse);
			let parentTab = '#devParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#developerCapacity-tab').removeClass('disabled');
				$('.nav li#developerCapacity-tab').removeClass('link-disabled');
				$('.nav li#summarys-tab').addClass('disabled');
			}
			$(''+parentTab+' a[href="#developerCapacity"]').tab('show');
		}

		/*applicantResponse = __doAjaxRequest(
				'DeveloperRegistrationForm.html?saveAuthorizedUserInfo', 'POST', requestData,
				false, '', 'html');
		
		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + applicantResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {			
			$(targetDivName).html(applicantResponse);
			let parentTab =  '#devParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#developerCapacity-tab').removeClass('disabled');
				$('.nav li#developerCapacity-tab').removeClass('link-disabled');
				$('.nav li#summarys-tab').addClass('disabled');
			}
			$(''+parentTab+' a[href="#developerCapacity"]').tab('show');
			prepareDateTag();
		}	
		else{
			$(targetDivName).html(applicantResponse);
			let parentTab =  '#devParentTab';
			var disabledTab = $(parentTab).find('.disabled');
			console.log("removing diabled" + disabledTab);
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				$('.nav li#developerCapacity-tab').removeClass('disabled');
				$('.nav li#developerCapacity-tab').removeClass('link-disabled');
				$('.nav li#summarys-tab').addClass('disabled');
			}
			$(''+parentTab+' a[href="#developerCapacity"]').tab('show');
		}*/
	}

}	

function addDevLicenseRow(){
	$('#errorDivId').hide();
	var errorList = [];
	
	errorList = validateDevLicenseDetails(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}
	var content = $('#devLicenseTable tr').last().clone();
	$('#devLicenseTable tr').last().after(content);

	content.find("input:text").val('');
	reOrderDevLicenseTable("devLicenseTable");
}


function validateDevLicenseDetails(errorList) {

	var qualificationList = [];
	$('#devLicenseTable tbody tr').each(
					function(i) {
						var licenseNo = $.trim($("#licenseNoId" + i)
								.val());
						var DateOfGrantLicense = $.trim($("#dateOfGrantLicenseId" + i).val());
						var purposeOfColony = $.trim($("#purposeOfColonyId" + i).val());
						var DateOfValidityLicense = $.trim($("#dateOfValidityLicenseId" + i).val());
						
						if (licenseNo == null || licenseNo == "") {
							errorList
									.push(getLocalMessage("enter.license.no"));
						}					
						if (DateOfGrantLicense == null || DateOfGrantLicense == "") {
							errorList
									.push(getLocalMessage("enter.date.of.grant.license"));
						}
						if (purposeOfColony == null || purposeOfColony == "") {
							errorList
									.push(getLocalMessage("select.purpose.of.colony"));
						}
						if (DateOfValidityLicense == null || DateOfValidityLicense == "") {
							errorList
									.push(getLocalMessage("selecta.dte.of.validity.license"));
						}
				
					});

	return errorList;
}

function reOrderDevLicenseTable(tableName){
	var tableId="#"+tableName;
	var rowName = $('#devLicenseTable tr');
	var rowCount = rowName.length;
	var reTable = $(tableId+'tr');
	for (var i=0; i<=rowCount; i++){
		if((rowCount-2) == i){			
			rowName.eq(i+1).find("input:text:eq(2)").removeClass('dateOfGrantLicenseHDRU'+(i-1)).addClass('dateOfGrantLicenseHDRU'+i);
			rowName.eq(i+1).find("input:text:eq(3)").removeClass('licenseValidity'+(i-1)).addClass('licenseValidity'+i);
			$('.dateOfGrantLicenseHDRU'+i).removeClass("hasDatepicker");
			$('.licenseValidity'+i).removeClass("hasDatepicker");
		}
		
		rowName.eq(i+1).find('input[id^=devLicenseId]').val(i+1);
		rowName.eq(i+1).find('input[id^=devLicenseId]').attr('id','devLicenseId'+i);

        rowName.eq(i+1).find('input[id^=licenseNoId]').attr('name','developerRegistrationDTO.devLicenseHDRUDTOList['+i+'].licenseNo');
        rowName.eq(i+1).find('input[id^=licenseNoId]').attr('id','licenseNoId'+i);

		rowName.eq(i+1).find('input[id^=dateOfGrantLicenseId]').attr('name','developerRegistrationDTO.devLicenseHDRUDTOList['+i+'].dateOfGrantLicense');
        rowName.eq(i+1).find('input[id^=dateOfGrantLicenseId]').attr('id','dateOfGrantLicenseId'+i);
         
		rowName.eq(i+1).find('select[id^=purposeOfColonyId]').attr('name','developerRegistrationDTO.devLicenseHDRUDTOList['+i+'].purposeOfColony');
        rowName.eq(i+1).find('select[id^=purposeOfColonyId]').attr('id','purposeOfColonyId'+i);

		rowName.eq(i+1).find('input[id^=dateOfValidityLicenseId]').attr('name','developerRegistrationDTO.devLicenseHDRUDTOList['+i+'].dateOfValidityLicense');
        rowName.eq(i+1).find('input[id^=dateOfValidityLicenseId]').attr('id','dateOfValidityLicenseId'+i);
		

		if ((rowCount - 2) == i) {
			$('#devLicenseTable tbody tr').each(
					function(i) {
						$(".dateOfGrantLicenseHDRU" + i).datepicker(
								{
									dateFormat : 'dd/mm/yy',
									changeMonth : true,
									changeYear : true,
									maxDate : '-1d',
									yearRange : "-100:-0",
									onClose : function(selectedDate) {
										$(".licenseValidity" + i).datepicker(
												"option", "minDate",
												selectedDate);
									}
								});

						$(".licenseValidity" + i).datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true
						});
					});
		}
  }
}


function deleteDevLicenseRow(obj) {
	var errorList = [];
	var cropDetailsRowLength = $('#devLicenseTable tr').length;
	if ($("#devLicenseTable tr").length != 2) {
		$(obj).parent().parent().remove();
		var stakeholderId = $(obj).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (stakeholderId != '') {
			removeDevLicenseArray.push(stakeholderId);
		}
		$('#removeStorageInfoIds').val(removeDevLicenseArray);
		reOrderDevLicenseTable();
		cropDetailsRowLength--;
	} else {
		errorList.push("You cannot delete first row");
		displayErrorsOnPage(errorList);
	}
}

function saveDevCapacityForm(element){	
	var errorList = [];
	errorList = validateDevCapacityForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		var divName = '#developerCapacity';
		var targetDivName = '#summary';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		
		var ajaxResponse = __doAjaxRequest(
				'DeveloperRegistrationForm.html?saveDevCapacityForm', 'POST', requestData,
				false, 'html');
		
		var tempDiv = $('<div id="tempDiv">' + ajaxResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {	
			$(targetDivName).html(ajaxResponse);
			let parentTab =  '#devParentTab';	
			$('.nav li#summarys-tab').removeClass('disabled');
			$('.nav li#summarys-tab').removeClass('link-disabled');
			$(''+parentTab+' a[href="#summary"]').tab('show');
			prepareDateTag();
			}	
		else{
			$(divName).html(ajaxResponse);
			prepareDateTag();
			return false;
		}
	}

}

function validateApplicantInfo(errorList){
	var cinNo=$("#cinNoId").val();
	var companyName = $("#companyName").val();
	var dateOfIncorporation = $("#dateOfIncorporationId").val();
	var registeredAddress = $("#registeredAddress").val();
	var Email = $("#emailId").val();
	var mobNo = $("#mobNoId").val();
	var gstNo = $("#gstNoId").val();
	var name=$("#nameId").val();
	var gender=$("#genderId").val();
	var dob=$("#dobId").val();
	var panNo=$("#panNoId").val();
	var LLpNo = $("#LLpNo").val();
	var devLicenseDate=$("#devLicenseDate").val();
	var indPanVerifiedFlagVal=$("#indPanVerifiedFlagId").val();
	var firmName = $("#firmNameId").val();
	var devTypeCode=$('#devType option:selected').attr('code');
	
	if(devTypeCode=="" || devTypeCode==null){
		errorList.push(getLocalMessage("select.developer"));
	}
	if(devTypeCode=="COM" || devTypeCode=="PAF"){
		if (cinNo == null || cinNo == "") {
			errorList.push(getLocalMessage("enter.cin.no"));
		}	
	}
	if(devTypeCode=="COM" || devTypeCode=="PAF" || devTypeCode=="LLP"){
		if (companyName == null || companyName == "") {
			errorList.push(getLocalMessage("enter.company.name"));
		}
		
		if (dateOfIncorporation == null || dateOfIncorporation == "") {
			errorList.push(getLocalMessage("enter.date.of.incorporation"));
		}
		
		if (registeredAddress == null || registeredAddress == "") {
			errorList.push(getLocalMessage("enter.registered.address"));
		}
		
		if (Email == null || Email == "") {
			errorList.push(getLocalMessage("enter.email"));
		}else if (!validateEmailId(Email)) {
			errorList.push(getLocalMessage("professional.val.invalid.email.id"));
		}
		
		if (mobNo == null || mobNo == "") {
			errorList.push(getLocalMessage("enter.mobile.no"));
		}else if(mobNo != null && mobNo != "") {
			errorList = errorList.concat(validateMobNo(mobNo));
		}		
		
		if (gstNo == null || gstNo == "") {
			errorList.push(getLocalMessage("enter.GST.no"));
		}
	}
		
	if(devTypeCode=="IND" || devTypeCode=="HUF"){
		if (name == null || name == "") {
			errorList.push(getLocalMessage("enter.name"));
		}
		
		if (gender == null || gender == "") {
			errorList.push(getLocalMessage("enter.gender"));
		}
		
		if (dob == null || dob == "") {
			errorList.push(getLocalMessage("enter.dob"));
		}
		
		if (registeredAddress == null || registeredAddress == "") {
			errorList.push(getLocalMessage("enter.registered.address"));
		}
		
		if (Email == null || Email == "") {
			errorList.push(getLocalMessage("enter.email"));
		}else if (!validateEmailId(Email)) {
			errorList.push(getLocalMessage("professional.val.invalid.email.id"));
		}
		
		if (mobNo == null || mobNo == "") {
			errorList.push(getLocalMessage("enter.mobile.no"));
		}
		else if(mobNo != null && mobNo != "") {
			errorList = errorList.concat(validateMobNo(mobNo));
		}
		
		if (gstNo == null || gstNo == "") {
			errorList.push(getLocalMessage("enter.GST.no"));
		}
		if (panNo == null || panNo == "") {
			errorList.push(getLocalMessage("enter.PAN.No"));
		}
		if (indPanVerifiedFlagVal == "N" || indPanVerifiedFlagVal=="" || indPanVerifiedFlagVal== null) {
			errorList.push(getLocalMessage("please verify PAN details"));
		}
	}
	
	if(devTypeCode=="PRF"){
		if (name == null || name == "") {
			errorList.push(getLocalMessage("enter.name"));
		}		
		if (registeredAddress == null || registeredAddress == "") {
			errorList.push(getLocalMessage("enter.registered.address"));
		}
		if (Email == null || Email == "") {
			errorList.push(getLocalMessage("enter.email"));
		}else if (!validateEmailId(Email)) {
			errorList.push(getLocalMessage("professional.val.invalid.email.id"));
		}
		if (mobNo == null || mobNo == "") {
			errorList.push(getLocalMessage("enter.mobile.no"));
		}
		else if(mobNo != null && mobNo != "") {
			errorList = errorList.concat(validateMobNo(mobNo));
		}		
		if (gstNo == null || gstNo == "") {
			errorList.push(getLocalMessage("enter.GST.no"));
		}
		if (panNo == null || panNo == "") {
			errorList.push(getLocalMessage("enter.PAN.No"));
		}
	}
	
	if(devTypeCode=="LLP"){

		if (LLpNo == null || LLpNo == "") {
			errorList.push(getLocalMessage("enter.LLP.no"));
		}
		if (firmName == null || firmName == "") {
			errorList.push(getLocalMessage("enter.firm.name"));
		}
	}
				
	if(devTypeCode=="COM" || devTypeCode=="PAF"){
		errorList = errorList.concat(validateDevDetails());
		errorList = errorList.concat(validateStkPercentOnSubmit());
	}
	
	if(devTypeCode=="COM" || devTypeCode=="PAF"){
		var directorInfoFlag = $("input[name='developerRegistrationDTO.directorInfoFlag']:checked").val();
		if(directorInfoFlag ==null || directorInfoFlag==""){
			errorList.push(getLocalMessage("select.Yes.No"));
		}
		
		var licenseInfoFlag = $("input[name='developerRegistrationDTO.licenseInfoFlag']:checked").val();
		if(licenseInfoFlag ==null || licenseInfoFlag==""){
			errorList.push(getLocalMessage("select.Yes.No"));
		}
		
	}
		
	if (directorInfoFlag === 'Y') {
		errorList = errorList.concat(validateDirectorDetails());
	}	
		
	
	if (licenseInfoFlag === 'Y') {
		var licenseNo = $("#licenseNo").val();
		var DevName = $("#DevName").val();
		var dateOfGrantLicense = $("#dateOfGrantLicense").val();
		
		if (licenseNo == null || licenseNo == "") {
			errorList.push(getLocalMessage("enter.license.No"));
		}
		
		if (DevName == null || DevName == "") {
			errorList.push(getLocalMessage("enter.developer.name"));
		}
		
		if (devLicenseDate == null || devLicenseDate == "") {
			errorList.push(getLocalMessage("enter.dateOfGrantLicense.date"));
		}
	
	}
	return errorList;
}

function validateDevCapacityForm(errorList){
	var licenseHDRUFlag = $("input[name='developerRegistrationDTO.licenseHDRUFlag']:checked").val();
	if(licenseHDRUFlag ==null || licenseHDRUFlag==""){
		errorList.push(getLocalMessage("select.Yes.No"));
	}
	if (licenseHDRUFlag === 'Y') {
		errorList = validateDevLicenseDetails(errorList);
	} else if(licenseHDRUFlag === 'N'){
		var projectsFlag = $("input[name='developerRegistrationDTO.projectsFlag']:checked").val();
		if(projectsFlag ==null || projectsFlag==""){
			errorList.push(getLocalMessage("select.Yes.No"));
		}
		
		if (projectsFlag === 'Y') {
			var projectName = $("#projectName").val();
			var AuthorityName = $("#AuthorityName").val();
			var devStatus = $("#devStatus").val();
			var areaOfProject = $("#areaOfProject").val();
			var location = $("#locationId").val();
			if (projectName == null || projectName == "") {
				errorList.push(getLocalMessage("enter.project.name"));
			}
			
			if (AuthorityName == null || AuthorityName == "") {
				errorList.push(getLocalMessage("enter.authority.name"));
			}
			
			if (devStatus == null || devStatus == "") {
				errorList.push(getLocalMessage("enter.status.of.development"));
			}
			if (areaOfProject == null || areaOfProject == "") {
				errorList.push(getLocalMessage("enter.area.Of.Project"));
			}
			if (location == null || location == "") {
				errorList.push(getLocalMessage("enter.location"));
			}
		}
	}
	return errorList;
}


function saveDevloperRegForm(element) {
	
	if ($('#acceptTerms').is(':checked')) {
		var requestData = $('form').serialize();
		var returnData = __doAjaxRequest("DeveloperRegistrationForm.html" + "?saveform", 'POST', requestData, false, 'json');
		if ($.isPlainObject(returnData)) {
			var message = returnData.command.message;
			var hasError = returnData.command.hasValidationError;
			if (!message) {
				message = successMessage;
			}
			if (message && !hasError) {
				if (returnData.command.hiddenOtherVal == 'SERVERERROR')
					showSaveResultBox(returnData, message, 'AdminHome.html');
				else
					showSaveResultBox(returnData, message, "AdminHome.html");
			} else if (hasError) {
				$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');
			}
			showloader(false);
			return false;
		}
	}else {
		showErrormsgboxTitle(getLocalMessage("Please accept terms and conditions"));
	}
		
	
}

function verifyPan(id){
	var errorList = [];
	errorList = validateAuthUserDetails(errorList);	
	//errorList = errorList.concat(validateMobileNo(id));
	//errorList = errorList.concat(validateEmail(id));
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}else{
		$(".error-div").html('');
	}
	var fullName; 
	var panno ;
	var mobile ;
	var email ;
	var gender;
	var dob;
	
	fullName = $.trim($("#authUserNameId" + id).val());
	panno = $.trim($("#authPanNumberId" + id).val());
	mobile = $.trim($("#authMobileNoId" + id).val());
	email = $.trim($("#authEmailId" + id).val());
	gender = $.trim($("#authGenderId" + id).val());
	dob = $.trim($("#authDOBId" + id).val());
	
	var requestData={
			"fullName" : fullName,
			"panno" : panno,
			"dob" : dob,
			"gender" : gender,
			"mobile" : mobile,
			"email" : email
	};
	var errorList = [];
	var divName = '#authorizedUser';
	var panResponse = __doAjaxRequest(
			'DeveloperRegistrationForm.html?verifyPanDetails', 'POST', requestData,
			false, 'json');
	if(panResponse.result=="success"){				
		$("#panVerifiedFlagId"+id).val('Y');
		$.trim($("#addAuthUserId" + id).removeClass("disabled"));
		$("#panVerifySuccessMsgId").val('Y');					
		$(".success-div").show();
		
	}else{
		$("#panVerifiedFlagId"+id).val('N')
		$.trim($("#addAuthUserId" + id).addClass("disabled"));
		errorList.push(getLocalMessage(panResponse.result)+ " for Sr. No. " + (id + 1));
		displayErrorsOnPage(errorList);	
		$(".success-div").hide();
		prepareDateTag();
		return false;
	}			

}

function fetchCinDetails(element){
	var errorList = [];
	var cinNo = $("#cinNoId").val();
	if (cinNo == null || cinNo == "") {
		errorList.push(getLocalMessage("enter.cin.no"));
	}	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	}else{
		$(".error-div").html('');	
		var divName = '#applicantInfo';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var cinResponse = __doAjaxRequest(
				'DeveloperRegistrationForm.html?fetchCinData', 'POST', requestData,
				false, '', 'html');
		var tempDiv = $('<div id="tempDiv">' + cinResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {			
			$(divName).html(cinResponse);
			prepareDateTag();
			var companyDetailsAPIFlag =$("#companyDetailsAPIFlagId").val();
			if(companyDetailsAPIFlag=="Y"){
				$("#companyName").prop("readonly", true);
				$("#dateOfIncorporationId").prop("readonly", true);
				$("#registeredAddress").prop("readonly", true);
				$("#emailId").prop("readonly", true);
			}else{
				$("#companyName").prop("readonly", false);
				$("#dateOfIncorporationId").prop("readonly", false);
				$("#registeredAddress").prop("readonly", false);
				$("#emailId").prop("readonly", false);
			}
		}else{
			$(divName).html(cinResponse);
			prepareDateTag();
			return false;
		}
	}
}

function validateMobileNo(currentRow) {
	var errorList = [];
	var curVal = $("#authMobileNoId" + currentRow).val();
	$('#authorizedUserTable tbody tr').each(function(i) {
		if (currentRow != i && (curVal == $("#authMobileNoId" + i).val())) {
			$("#authMobileNoId" + currentRow).val();
			errorList.push(getLocalMessage('Mobile no. already exist'));
			return false;
		}
	});
	return errorList;
}

function validateEmail(currentRow) {
	var errorList = [];
	var curVal = $("#authEmailId" + currentRow).val();
	$('#authorizedUserTable tbody tr').each(function(i) {
		if (currentRow != i && (curVal == $("#authEmailId" + i).val())) {
			$("#authMobileNoId" + currentRow).val();
			errorList.push(getLocalMessage('Email already exist'));
			return false;
		}
	});
	return errorList;
}

function validateEmailId(email) {
	var regexPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return regexPattern.test(email);
}

function validateContact(mobile) {
	var regexPattern = /^[789]\d{9}$/;
	return regexPattern.test(mobile);
}
function validateStkPercent(currentRow) {
	var errorList = [];
	var curVal = parseInt($("#devloperPercentageId" + currentRow).val());
	var sum = 0;
	$('#stakeholderTable tbody tr').each(function(i) {
		if (currentRow != i){
			var otherVal = parseInt($("#devloperPercentageId" + i).val());
			sum+= parseInt(otherVal);
			if(parseInt(sum+curVal)>100){
				errorList.push(getLocalMessage('percentage should not be more than 100'));
				return false;
		} 
		}
	});
	return errorList;
}

function validateStkPercentOnSubmit() {
	var errorList = [];
	var sum = 0;
	$('#stakeholderTable tbody tr').each(function(i) {
		var perVal = parseInt($("#devloperPercentageId" + i).val());
		sum+= parseInt(perVal);
		if(parseInt(sum)>100){
			errorList.push(getLocalMessage('percentage should not be more than 100'));
			return false;
		} 		
	});
	return errorList;
}
function verifyPanNumber(){
	var errorList = [];
	var fullName = $("#nameId").val();
	var panno =  $("#panNoId").val();
	var mobile =  $("#mobNoId").val();
	var email =  $("#emailId").val();
	var gender =  $("#genderId").val();
	var dob =  $("#dobId").val();
	
	if (fullName == null || fullName == "") {
		errorList.push(getLocalMessage("enter.name"));
	}
	if (panno == null || panno == "") {
		errorList.push(getLocalMessage("enter.PAN.No"));
	}
	if (dob == null || dob == "") {
		errorList.push(getLocalMessage("enter.dob"));
	}
	if (gender == null || gender == "") {
		errorList.push(getLocalMessage("enter.gender"));
	}
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		$(".error-div").html('');

		var requestData = {
			"fullName" : fullName,
			"panno" : panno,
			"dob" : dob,
			"gender" : gender,
			"mobile" : mobile,
			"email" : email
		};
		var errorList = [];
		var divName = '#applicantInfo';
		var panNoResponse = __doAjaxRequest(
				'DeveloperRegistrationForm.html?verifyPanDetails', 'POST',
				requestData, false, 'json');
		if (panNoResponse.result == "success") {
			$("#indPanVerifiedFlagId").val('Y');
			$(".IND-success-div").show();

		} else {
			errorList.push(getLocalMessage(panNoResponse.result));
			displayErrorsOnPage(errorList);
			$("#indPanVerifiedFlagId").val('N');
			$(".IND-success-div").hide();
			prepareDateTag();
			return false;
		}	
	}
}
function verifyIndPanDetails(){
	$("#indPanVerifiedFlagId").val('N');
}
function checkVerifyPanDetails(id){
	$("#panVerifiedFlagId"+id).val('N');
}
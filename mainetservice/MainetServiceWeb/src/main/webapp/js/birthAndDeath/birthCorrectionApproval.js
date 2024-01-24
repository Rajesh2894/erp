$(document).ready(function() {
	prepareDateTag();
	$("#frmBirthCorrectionForm").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	var corrCategory = $.trim($("#corrCategory").val());
	var corrCategoryArr = [];
	if(corrCategory!=null && corrCategory!=""){
	corrCategoryArr = corrCategory.split(",");
	$.each(corrCategoryArr, function(index) {
		$("."+corrCategoryArr[index]).addClass('highlight-field');
	});
	}
});

function searchBirthData() {
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var url = "BirthCorrectionForm.html?searchBirthDetail";
		var requestData = "brCertNo=" + $('#brCertNo').val()
				+ "&applicationId=" + $('#applicationId').val() + "&year="
				+ $("#year").val() + "&brRegNo=" + $("#brRegNo").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,'json');
		if (returnData != 'Internal Server Error.') {
			$('#brChildName').val(returnData.brChildName);
			$('#brChildNameMar').val(returnData.brChildNameMar);
			$('#pdFathername').val(returnData.parentDetailDTO.pdFathername);
			$('#pdFathernameMar').val(returnData.parentDetailDTO.pdFathernameMar);
			$('#pdMothername').val(returnData.parentDetailDTO.pdMothername);
			$('#pdMothernameMar').val(returnData.parentDetailDTO.pdMothernameMar);
			$('#brBirthPlace').val(returnData.brBirthPlace);
			$('#brBirthPlaceMar').val(returnData.brBirthPlaceMar);
			$('#brBirthAddr').val(returnData.brBirthAddr);
			$('#brBirthAddrMar').val(returnData.brBirthAddrMar);
			$('#cpdRefTypeId').val(returnData.cpdRefTypeId);
			
			$('#brDob').val(getDateFormat(returnData.brDob));
			$('#brSex').val(returnData.brSex);
			$('#BrBirthWt').val(returnData.BrBirthWt);
			
			$('#brBirthPlaceType').val(returnData.brBirthPlaceType);
			$('#hospitalList').val(returnData.hiId);
			$('#brInformantName').val(returnData.brInformantName);
			$('#brInformantNameMar').val(returnData.brInformantNameMar);
			$('#brInformantAddr').val(returnData.brInformantAddr);
			$('#brInformantAddrMar').val(returnData.brInformantAddrMar);
			$('#cpdAttntypeId').val(returnData.cpdAttntypeId);
			$('#cpdDelMethId').val(returnData.cpdDelMethId);
			$('#brPregDuratn').val(returnData.brPregDuratn);
			$('#brBirthMark').val(returnData.brBirthMark);
			$('#cpdFEducnId').val(returnData.parentDetailDTO.cpdFEducnId);
			$('#cpdFOccuId').val(returnData.parentDetailDTO.cpdFOccuId);
			$('#cpdMEducnId').val(returnData.parentDetailDTO.cpdMEducnId);
			$('#cpdMOccuId').val(returnData.parentDetailDTO.cpdMOccuId);
			$('#pdAgeAtMarry').val(returnData.parentDetailDTO.pdAgeAtMarry);
			$('#pdAgeAtBirth').val(returnData.parentDetailDTO.pdAgeAtBirth);
			$('#pdLiveChildn').val(returnData.parentDetailDTO.pdLiveChildn);
			//$('#motheraddress').val(returnData.parentDetailDTO.motheraddress);
			$('#motheraddress').val(returnData.parentDetailDTO.pdMothername);
			//$('#motheraddress').val(returnData.parentDetailDTO.pdMothernameMar);
			$('#pdParaddress').val(returnData.parentDetailDTO.pdParaddress);
			$('#pdParaddressMar').val(returnData.parentDetailDTO.pdParaddressMar);
			$('#cpdId1').val(returnData.parentDetailDTO.cpdId1);
			$('#cpdId2').val(returnData.parentDetailDTO.cpdId2);
			$('#cpdId3').val(returnData.parentDetailDTO.cpdId3);
			$('#cpdId4').val(returnData.parentDetailDTO.cpdId4);
			$('#cpdReligionId').val(returnData.parentDetailDTO.cpdReligionId);
			$('#pdRegUnitId').val(returnData.parentDetailDTO.pdRegUnitId);
			
			$('#brId').val(returnData.brId);
			
		} else {
			errorsList.push("No record Found for Select criteria")
			$('#frmIssuCertificateForm').trigger("reset");
			displayErrorsOnPage(errorsList);
		}
	}
}

function validateBirthSearchForm(errorsList) {
	var certNo = $('#brCertNo').val();
	var regisApplicationNo = $('#applicationId').val();
	var regDate = $("#year").val();
	var regisNo = $("#brRegNo").val();
	// validate the year
	validatedates(errorsList);
	if (certNo == "" && regisApplicationNo == "" && regDate == "" && regisNo == "") {
		errorsList.push(getLocalMessage('Please enter at least one criteria for search'));
	} else if (certNo != "" || regisApplicationNo != "") {
	// go for Search
	} else if (regDate != "" && regisNo != "") {
	// go for Search
	} else {
		errorsList.push(getLocalMessage('BirthDeath.NoRecord.Error'));
	}
	return errorsList;
}

function validatedates(errorList) {
	$('.error-div').hide();
	var name = "Issuance certificate";
	var trTenderDate;
	if (($("#year").val()) != undefined) {
		trTenderDate = $("#year").val();
	}
	if (trTenderDate != null && trTenderDate != "") {
		var yy = parseInt(trTenderDate);
		if (yy < 1940 || yy > (new Date()).getFullYear()) {
			errorList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
		} else if (yy < 1940) {
			errorList.push(getLocalMessage("BirthDeath.invalidYear.Error"));
		}
	}
	return errorList;
}

function validateBirthCorrSearchForm(errorsList) {
	var certNo = $('#brCertNo').val();
	var regDate = $("#brRegDate").val();
	var regNo = $("#brRegNo").val();
	var apmApplicationId = $("#apmApplicationId").val();

	if (certNo == "" && regDate == "" && regNo == "" && apmApplicationId == "") {
		errorsList.push(getLocalMessage('Please enter at least one search criteria'));
	}
	return errorsList;
}

function saveBirthCorrData(element) {
	if ($("#frmBirthCorrectionForm").valid() == true) {
		return saveOrUpdateForm(element, "", 'BirthCorrectionForm.html',
				'saveform');
	} else {
	}
}

function resetBirthCorrData() {
	window.open('BirthCorrectionForm.html', '_self');
}

$(document).ready(function() {
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maate : '-0d',
		changeYear : true,
	});
});

function getChecklistAndCharges(element) {
	var errorList = [];
	var flag = false;
	if ($("#frmBirthCorrectionForm").valid() == true) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'BirthCorrectionForm.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$(divName).show();
			$('#chekListChargeId').show();
			$('#proceedId').hide();
			$('#brDob').prop("disabled", true);
			}
		}
}



function saveBirthRegCorrApprovalData(element) {
	var errorList = [];	
	//$.fancybox.close();
	if($("#birthRegremark").val()==""){
		errorList.push("Please enter the remark")
		 displayErrorsOnPage(errorList);
	}
	else{
	 var divName = '.pagediv';
	 var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveBirthRegCorrApproval', 'POST',requestData, false,'json');		
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
		if(object.BirthWfStatus =='REJECTED'){
			 showBoxForApproval(getLocalMessage("BirthRegistrationDTO.submit.reject"));
		 }else if(object.BirthWfStatus =='SEND_BACK'){
			 showBoxForApproval(getLocalMessage('bnd.para.sendBack'));
		 }else if(object.BirthWfStatus =='FORWARD_TO'){
			 showBoxForApproval(getLocalMessage('bnd.para.sendBack'));
		 }else{
			 showBoxForApproval(getLocalMessage("bnd.approval.msg1") + object.certificateNo + " " + getLocalMessage("bnd.approval.msg2"));
		 }
	 } 
   }
}


function showBoxForApproval(succesMessage){
	
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  getLocalMessage('TbDeathregDTO.form.proceed');
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

function displayCorrCharge(element) {
	var errorList = [];	
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	if(decision == undefined || decision == '')
		errorList.push(getLocalMessage('asset.info.approval'));
	else if(comments == undefined || comments =='')
		errorList.push(getLocalMessage('asset.info.comment'));
		
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else if(decision =='SEND_BACK'){
		saveBirthRegCorrApprLOI(element);
	}else{
	var divName = '.content-page';
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName; 
	var requestData = __serializeForm(theForm);
	var ajaxResponse = doAjaxLoading('BirthRegistrationCorrectionApproval.html?displaybirthCorrCharge', requestData, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
	}
}

function saveBirthRegCorrApprLOI(element) {

	var errorList = [];	
	var birthRegremark = $('#birthRegremark').val();
	var authRemark = $('#authRemark').val();
	if(authRemark == undefined){
	if((birthRegremark == "" || birthRegremark == null || birthRegremark == undefined) && (authRemark == "" || authRemark == null || authRemark == undefined)) {
		errorList.push(getLocalMessage('TbDeathregDTO.label.rema'))
	}
	}else{
		if(authRemark == "" || authRemark == null){
			errorList.push(getLocalMessage('bnd.final.remark.validin'))
		}
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}else{
	 var divName = '.pagediv';
	 var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveBirthRegCorrApprLOI', 'POST',requestData, false,'json');		
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
		if(object.BirthWfStatus =='REJECTED'){
			 showBoxForApproval(getLocalMessage("BirthRegistrationDTO.submit.reject"));
		 }else if(object.BirthWfStatus =='SEND_BACK'){
			 showBoxForApproval(getLocalMessage('bnd.para.sendBack'));
		 }else if(object.BirthWfStatus =='FORWARD_TO'){
			 showBoxForApproval(getLocalMessage('bnd.para.sendBack'));
		 }else{
			 showBoxForApproval(getLocalMessage("BirthRegistrationDTO.submit.approve"));
		 }
	 } 
   }
}

	function ApplyChargeDisable(){
		
		//var radiovalue = $('input[type=radio]:checked').val();
		var radiovalue = $("input:radio[name='tbBirthregcorrDTO.birthRegstatus']").filter(":checked").val();
		if(radiovalue=='REJECTED'){
			$('#applCharge').hide();
			$('#saveDeath').show();
		}else{
			$('#applCharge').show();
			$('#saveDeath').hide();
		}
}

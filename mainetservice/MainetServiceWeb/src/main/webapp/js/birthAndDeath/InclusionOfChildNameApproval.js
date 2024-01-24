
$(document).ready(function() {
	
	prepareDateTag();
	$("#InclusionOfChildName").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maate : '-0d',
			changeYear : true,
		});
	});

	$(document).ready(function() {
	$("#brRegDate").keyup(function(e){
		
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });	
	});


function searchBirthData() {
	var errorsList = [];
	// validate the form
	errorsList = validateBirthSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var url = "InclusionOfChildName.html?searchBirthDetail";
		var requestData = "brCertNo=" + $('#brCertNo').val()
				+ "&applicationId=" + $('#applicationId').val() + "&year="
				+ $("#year").val() + "&brRegNo=" + $("#brRegNo").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		if (returnData != 'Internal Server Error.') {
			$('#brChildName').val(returnData.brChildName);
			$('#brChildNameMar').val(returnData.brChildNameMar);
			$('#brSex').val(returnData.brSex);
			$('#brDob').val(getDateFormat(returnData.brDob));
			$('#pdFathername').val(returnData.parentDetailDTO.pdFathername);
			$('#pdFathernameMar').val(
					returnData.parentDetailDTO.pdFathernameMar);
			$('#pdMothername').val(returnData.parentDetailDTO.pdMothername);
			$('#pdMothernameMar').val(
					returnData.parentDetailDTO.pdMothernameMar);
			$('#brBirthPlace').val(returnData.brBirthPlace);
			$('#brBirthPlaceMar').val(returnData.brBirthPlaceMar);
			$('#brBirthAddr').val(returnData.brBirthAddr);
			$('#brBirthAddrMar').val(returnData.brBirthAddrMar);
			$('#brCertNo').val(returnData.brCertNo);

		} else {
			errorsList.push("No record Found for Select criteria")
			$('#frmIssuCertificateForm').trigger("reset");
			displayErrorsOnPage(errorsList);
		}
	}
}

function searchDeathData() {
	
	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var url = "InclusionOfChildName.html?searchDeathDetail";
		var requestData = "drCertNo=" + $('#drCertNo').val() + "&drRegno="
				+ $('#drRegno').val() + "&year=" + $("#year").val()
				+ "&applicationId=" + $("#applicationNo").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'html');
		if (returnData != null) {
			$(".content-page").html(returnData);
		} else {
			errorsList.push("No record Found for Select criteria")
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
		errorsList.push(getLocalMessage('Please enter year and registration No.'));
	}
	return errorsList;
}

function validateDeathSearchForm(errorsList) {
	
	var certNo = $('#drCertNo').val();
	var regisApplicationNo = $('#applicationNo').val();
	var regDate = $("#year").val();
	var regisNo = $("#drRegno").val();
	// validate the year
	validatedates(errorsList);
	if (certNo == "" && regisApplicationNo == "" && regDate == "" && regisNo == "") {
		errorsList.push(getLocalMessage('Please enter at least one criteria for search'));
	} else if (certNo != "" || regisApplicationNo != "") {
		// go for Search
	} else if (regDate != "" && regisNo != "") {
		// go for Search
	} else {
		errorsList.push(getLocalMessage('Please enter year and registration No.'));
	}
	return errorsList;
}

function saveInclusionOfChildName(element) {
	
	if ($("#frmInclusionOfChildName").valid() == true) {
		return saveOrUpdateForm(element, "", 'InclusionOfChildName.html', 'saveform');
	} else {
	}
}

function OffLinePayment() {
	
	$('#PaymnetMode').show();
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


function getChecklistAndCharges(element) {
	var errorList = [];
	var flag = false;
	if ($("#frmInclusionOfChildName").valid() == true) {
		
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'InclusionOfChildName.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		
		if (returnData) {
			var divName = '.pageDivContent';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$(divName).show();
			$('#chekListChargeId').show();
			$('#proceedId').hide();
			$('#brDob').prop("disabled", true);
			}
		}
}


function getAmountOnNoOfCopes(){
 	var errorsList= [];
 	var form_url = $("#frmInclusionOfChildName").attr("action");
  	var url=form_url+'?getBNDChargeForInclusion';
 	var isscopy=0;
 	var isscopy=$("#brCertNo").val();
 	if(isscopy==''){
 		isscopy=0;
 	}
 	if($('#noOfCopies').val()!='' && $('#noOfCopies').val()!=undefined){	
	var requestData = "noOfCopies=" + $('#noOfCopies').val()+ "&issuedCopy=" +isscopy;
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	 $("#amount").val(returnData);
 	}
 	else{
 		//errorsList.push("Please enter the no of copies !");
 		//displayErrorsOnPage(errorsList);
 	}
}




function saveInclusionOfChildNameApprovalData(element) {
	
	var errorList = [];	
	//$.fancybox.close();
	if($("#birthRegremark").val()==""){
		errorList.push("Please enter the remark")
		 displayErrorsOnPage(errorList);
	}
	else{
	 var divName = '.pageDivContent';
	 var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveInclusionOfChildApproval', 'POST',requestData, false,'json');		
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








$(document).ready(function() {
	prepareDateTag();
	var corrCategory = $.trim($("#corrCategory").val());
	var corrCategoryArr = [];
	if(corrCategory!=null && corrCategory!=""){
	corrCategoryArr = corrCategory.split(",");
	$.each(corrCategoryArr, function(index) {
		$("."+corrCategoryArr[index]).addClass('highlight-field');
	});
	}
});
function SearchDeathCorrectionData() {
	var errorsList = [];
	errorsList = validateDeathSearchForm(errorsList);
	if (errorsList.length > 0) {
		displayErrorsOnPage(errorsList);
	} else {
		var table = $('#deathCorrDataTable').DataTable();
		var url = "DeathRegistrationCorrection.html?searchDeathCorrection";
		var requestData = "drCertNo=" + $('#drCertNo').val() + "&drRegno="
				+ $('#drRegno').val() + "&year=" + $("#year").val()
				+ "&applnId=" + $("#applnId").val()
				+ "&drDod=" + $("#drDod").val()+"&drDeceasedname=" + $("#drDeceasedname").val();
		var returnData = __doAjaxRequest(url, 'post', requestData, false,
				'json');
		
		
		if (returnData == 'Internal Server Error.') {
			errorsList.push(getLocalMessage("BirthDeath.NoRecord.Error"));
			displayErrorsOnPage(errorsList);
		}
		
		var result = [];
		$
				.each(
						returnData,
						function(index) {
							var obj = returnData[index];
							let drId = obj.drId;
							let drDod = obj.drDod;
							let drDeceasedname=obj.drDeceasedname;
							let applnId = obj.applnId;
							let drSex = obj.drSex;
							let cpdRegUnit = obj.cpdDesc;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">'
													+ getDateFormat(drDod) + '</div>',
											'<div class="text-center">'
													+ applnId + '</div>',
											'<div class="text-center">'
													+ drDeceasedname + '</div>',
											'<div class="text-center">'
													+ drSex + '</div>',
											'<div class="text-center">'
													+ cpdRegUnit + '</div>',
											'<div class="text-center">'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyDeath(\''
													+ drId
													+ '\',\'DeathRegistrationCorrection.html\',\'editBND\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);
						});
		table.rows.add(result);
		table.draw();
	} 
}

function validateDeathSearchForm(errorsList) {
	var drCertNo = $('#drCertNo').val();
	var applnId = $('#applnId').val();
	var year = $("#year").val();
	var drRegno = $("#drRegno").val();
	var drDod= $("#drDod").val();
	var drDeceasedname=$("#drDeceasedname").val();
	if (drCertNo == "" && applnId == "" && year == ""
			&& drRegno == "" && drDod == "" && drDeceasedname == "") {
		errorsList
				.push(getLocalMessage('Please enter at least one criteria for search'));
	} else if (drCertNo != "" || applnId != "" || drDod != "" || drDeceasedname != "") {
		// go for Search
	} else if (year != "" && drRegno != "") {
		// go for Search
	} 
	else {
		errorsList
				.push(getLocalMessage('Please enter year and registration No.'));
	}
	return errorsList;
}


function modifyDeath(drId, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
	"mode" : mode,
	"id" : drId
	 };
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
						'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$('#cpdDeathplaceType').prop("disabled", true);
	$('#hospitalList').prop("disabled", true);
	$('#drDod').prop("disabled", true);
	$('#cemeteryList').prop("disabled", true);
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
	$('#cemeteryList').prop("disabled", true);
	
}


function getChecklistAndCharges(element) {
	var errorList = [];
	var drDod = $("#drDod").val();
	var mcVerifnDate = $("#mcVerifnDate").val();
	var currDate= new Date();
	
		if(drDod == ""){
			errorList.push(getLocalMessage("deceased Date Should not be blank"));
		}
		
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;						
		var drDod = new Date(drDod.replace(pattern, '$3-$2-$1'));
		var mcVerifnDate = new Date(mcVerifnDate.replace(pattern, '$3-$2-$1'));
	
		if (drDod > currDate) {
			errorList.push(getLocalMessage("deceased Date cannot be greater than Current Date"));
		}

		if (drDod > mcVerifnDate) {
			errorList.push(getLocalMessage("Post-Mortem cannot be less than Deceassed Date"));
		}
		if (errorList.length > 0) {
			checkDate(errorList);
		}
		
		else{
		var flag = false;
		if ($("#frmDeathRegCorrForm").valid() == true) {
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'DeathRegistrationCorrection.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');

		if (returnData) {
			var divName = '.pagediv';
			$(divName).removeClass('ajaxloader');
			$(divName).html(returnData);
			$(divName).show();
			$('#chekListChargeId').show();
			$('#proceedId').hide();
			$('#drDod').prop("disabled", true);
			$('#within1').prop("disabled", true)
			$('#within2').prop("disabled", true)
			$('#ceName').prop("disabled", true);
			$('#ceNameMar').prop("disabled", true);
			$('#ceAddr').prop("disabled", true);
			$('#ceAddrMar').prop("disabled", true);
			$('#cemeteryList').prop("disabled", true);
		}

   	}
 }
}
function disFields(element) {
	$('#ceName').prop("disabled", true);
	$('#ceNameMar').prop("disabled", true);
	$('#ceAddr').prop("disabled", true);
	$('#ceAddrMar').prop("disabled", true);
	$('#cemeteryList').prop("disabled", false);
}

function enaFields(element) {
	$('#ceName').prop("disabled", false);
	$('#ceNameMar').prop("disabled", false);
	$('#ceAddr').prop("disabled", false);
	$('#ceAddrMar').prop("disabled", false);
	$('#cemeteryList').prop("disabled", true);
}

function resetDeathCorrData() {
	window.open('DeathRegistrationCorrection.html', '_self');
}

function saveDeathCorrData(element) {
	if ($("#frmDeathRegCorrForm").valid() == true) {
		$('#drDod').prop("disabled", false);
		return saveOrUpdateForm(element, "Application Number generation done successfully", 'DeathRegistrationCorrection.html',
				'saveform');
	} else {
	}
}

function selecthosp(element)
{
    var code=$(element).find(':selected').attr('code')
	if(code=="I")
	{
	$('#hospitalList').prop("disabled", false);
	}else{
		$('#hospitalList').prop("disabled",true);
	}
}

function saveDeathRegApprovalData(obj){ 
	  var remark=$("#deathRegremark").val();
	  var approveOrReject=$("#deathRegstatus").val();
	  var errorList = [];
	  if(remark=="" || remark==undefined ){
		 errorList.push("Please enter the Remark");
		}
	  if(approveOrReject=="" || approveOrReject==undefined){
		 errorList.push("Please select Radio Button");
		}
	  if(errorList.length>0){
		 displayErrorsOnPage(errorList);
		}
	  else{
			//Save code
	  } 
}





function getAmountOnNoOfCopes(){
 	var errorsList= [];
 	var form_url = $("#frmDeathRegCorrForm").attr("action");
  	var url=form_url+'?getBNDCharge';
 	var isscopy=0;
 	var isscopy=$("#brCertNo").val();
 	if(isscopy=='' || isscopy==undefined ){
 		isscopy=0;
 	}
 	if($('#noOfCopies').val()!='' && $('#noOfCopies').val()!=undefined){	
	var requestData = "noOfCopies=" + $('#noOfCopies').val()+ "&issuedCopy=" +isscopy;
	var returnData = __doAjaxRequest(url, 'post', requestData, false,
			'json');  
	 $("#amount").val(returnData);
 	}
 	else if($('#numberOfCopies').val()!='' && $('#numberOfCopies').val()!=undefined){
 		var requestData = "noOfCopies=" + $('#numberOfCopies').val()+ "&issuedCopy=" +isscopy;
 		var returnData = __doAjaxRequest(url, 'post', requestData, false,
 				'json');  
 		 $("#amount").val(returnData);
 	}
 	else{
 		//errorsList.push("Please enter the no of copies !");
 		//displayErrorsOnPage(errorsList);
 	}
}


function saveDeathRegCorrApprovalData(element) {
	var errorList = [];	
	//$.fancybox.close();
	if($("#corrAuthRemark").val()==""){
		errorList.push("Please enter the remark")
		 displayErrorsOnPage(errorList);
	}
	else{
	 var divName = '.pagediv';
	 var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveDeathRegCorrApproval', 'POST',requestData, false,'json');		
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
		if(object.DeathWfStatus =='REJECTED'){
			showBoxForApproval(getLocalMessage("TbDeathregDTO.submit.reject"));
		 }else if(object.DeathWfStatus =='SEND_BACK'){
			 showBoxForApproval(getLocalMessage('bnd.para.sendBack'));
		 }else if(object.DeathWfStatus =='FORWARD_TO'){
			 showBoxForApproval(getLocalMessage('bnd.para.sendBack'));
		 } 
		 else{
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
		saveDeathRegCorrApprLOI(element);
	}else{
	var divName = '.content-page';
	var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	var ajaxResponse = doAjaxLoading('DeathRegistrationCorrectionApproval.html?displaydeathCorrCharge',requestData, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	}
}


function ApplyChargeDisable() {
	
	// var radiovalue = $('input[type=radio]:checked').val();
	var radiovalue = $("input:radio[name='tbDeathregDTO.deathRegstatus']")
			.filter(":checked").val();
	if (radiovalue == 'REJECTED') {
		$('#applCharge').hide();
		$('#saveDeath').show();
	} else {
		$('#applCharge').show();
		$('#saveDeath').hide();
	}
}


function saveDeathRegCorrApprLOI(element) {
	

	var errorList = [];	
	var deathRegremark = $('#corrAuthRemark').val();
	var authRemark = $('#deathRegremark').val();
	if(authRemark == undefined){
		if((deathRegremark == "" || deathRegremark == null || deathRegremark == undefined) && (authRemark == "" || authRemark == null || authRemark == undefined)) {
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
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveDeathRegCorrApprLOI', 'POST',requestData, false,'json');		
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
		if(object.DeathWfStatus =='REJECTED'){
			showBoxForApproval(getLocalMessage("TbDeathregDTO.submit.reject"));
		 }else if(object.DeathWfStatus =='SEND_BACK'){
			 showBoxForApproval(getLocalMessage('bnd.para.sendBack'));
		 }else if(object.DeathWfStatus =='FORWARD_TO'){
			 showBoxForApproval(getLocalMessage('bnd.para.sendBack'));
		 } 
		 else{
			 showBoxForApproval(getLocalMessage("BirthRegistrationDTO.submit.approve"));
		 }
	 } 
   }
}
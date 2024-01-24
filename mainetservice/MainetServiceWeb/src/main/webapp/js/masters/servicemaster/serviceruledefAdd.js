/**
 * JS file added by Harsha 
 */

$(document).ready(function(){

	$("input").on("keypress", function(e) {
	    if (e.which === 32 && !this.value.length)
	        e.preventDefault();
	});
	
	if($("#modeId").val() == 'update'){
		$("#smShortdesc").prop('readonly',true);
		$("#deptIdHidden").val($("#deptId").val());
		$("#deptId").prop('disabled',true);
		var smChecked = $('input[name=smFeesSchedule]:checked', '#serviceMasterForm').val();
		if(smChecked == 0){
			document.getElementById('iffree').style.display = 'none';
		}

	}else{
		document.getElementById('iffree').style.display = 'none';
	}
});


function toggleChargeFlag(obj) {
	if(obj.value == 0)	{
	 
	        document.getElementById('iffree').style.display = 'none';
		    $("#smAppliChargeFlag").prop('checked',false);
		    $("#smScrutinyChargeFlag").prop('checked',false);
	}else if (obj.value == 1){
			document.getElementById('iffree').style.display = 'block'; 
			$("#smAppliChargeFlag").prop('checked',false);
			$("#smScrutinyChargeFlag").prop('checked',false);
	  
	}
	
	}

function toggleDays(obj){
	$("#errorDivScrutiny").hide();
	var selVal = $("#smChklstVerify").find('option:selected').attr('code');	
	$("#smChklstVerifyCode").val(selVal);
}

function setPrintResponseCode(obj) {
	var code = $(obj).find('option:selected').attr('code');	
	$("#smPrintResponsCode").val(code);
}

function checkShortCode(){
	var shortCode= $('#smShortdesc').val();
	var orgId=$('#orgId').val();
	var requestData = "shortcode="+shortCode+"&orgid="+orgId;
	var url = "ServiceMaster.html?checkShortCode";
	
	var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'','');
	if(returnData == 1){
		var errorList = [];
		errorList.push(getLocalMessage("service.error.shortDescExist"));
		showError(errorList);
	}else{
		$("#errorDivScrutiny").hide();
	}
}


function submitForm(obj) {
	var errorList = [];
	var smServiceDuration = $("#smServiceDuration").val();
	var smDurationUnit = $("#smDurationUnit").val();
	
	if($("#deptId").val() == 0 || $("#deptId").val() == ''){
		errorList.push(getLocalMessage("service.error.deptId"));
	}
	if($("#serviceNameEng").val() == 0 || $("#serviceNameEng").val() == ''){
		errorList.push(getLocalMessage("service.error.serviceNameEng"));
	}
	if($("#serviceNameReg").val() == 0 || $("#serviceNameReg").val() == ''){
		errorList.push(getLocalMessage("service.error.serviceNameReg"));
	}
	if($("#smShortdesc").val() == 0 || $("#smShortdesc").val() == ''){
		errorList.push(getLocalMessage("service.error.smShortdesc"));
	}
	if($("#smServActive").val() == 0 || $("#smServActive").val() == ''){
		errorList.push(getLocalMessage("service.error.smServActive"));
	}
	if($("#smChklstVerify").val() == 0 || $("#smChklstVerify").val() == ''){
		errorList.push(getLocalMessage("service.error.smChklstVerify"));
	}
	
	if($('.radio-check:checked').length == 0){
		errorList.push(getLocalMessage("service.error.feeSchedule"));
	}
	
	if($("#ChargeableService").is(':checked')){
		if( $("#smAppliChargeFlag").prop('checked') == false && $("#smScrutinyChargeFlag").prop('checked') == false){
			errorList.push(getLocalMessage("service.error.chargeable"));
		}
	}
	if($("#smPrintRespons").val() == 0 || $("#smPrintRespons").val() == ''){
		errorList.push(getLocalMessage("service.error.smPrintRespons"));
	}
	
	if($("#smProcessId").val() == 0 || $("#smProcessId").val() == ''){
		errorList.push(getLocalMessage("service.error.smBpmProcess"));
	}
	
	
	 if(smServiceDuration != "" && smServiceDuration != undefined || (smDurationUnit !="" && smDurationUnit !='0'  && smDurationUnit != undefined )){
		 if(smServiceDuration == "" || smServiceDuration == undefined ){
			 errorList.push(getLocalMessage('workflow.form.validation.enter.sla'));
		 }
		 if(smDurationUnit =="" || smDurationUnit =='0'  || smDurationUnit == undefined ){
			 errorList.push(getLocalMessage('workflow.form.validation.select.unit'));
		 }
		
	 }	 
	
	
	if(errorList.length > 0){
		showError(errorList);
		return false;
	}
	
	var	formName =	findClosestElementId(obj, 'form');
	var theForm	=	'#'+formName;
	var requestData = {};
	var requestData = __serializeForm(theForm);
	var url = "";
	
	if($("#modeId").val() == "create"){
		url="ServiceMaster.html?create"
	}else{
	url = "ServiceMaster.html?update"
	}
	var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'','');
	if($.isPlainObject(returnData)) {
		showConfirmBox();
	} else {
		$(".content").html(returnData);
	    $(".content").show();
	    if(document.getElementById('ChargeableService').checked) {
	    	 $("#iffree").show();
	    	}
		
	}
	return false;
}

function showConfirmBox(){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Proceed';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Form Submitted Successfully</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="proceed()"/>'+
	'</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	
	return false;
}

function proceed() {
	window.location.href='ServiceMaster.html';
}

function closeOutErrBox() {
	$("#errorDivScrutiny").hide();
}

function showError(errorList){
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDivScrutiny").html(errMsg);
	$("#errorDivScrutiny").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
}

/*function checkForTransaction(){
	var count = $("#transactionCount").val();
	var activeness = $('#activeness').val();
	if(count > 0 && $('#smServActive').val() != activeness){
		$(errMsgDiv).html("<h4 class=\"text-center text-blue-2 padding-10\">Transaction Exists,<br/>cannot change the status</h4>");
		$(errMsgDiv).show();
		showMsgModalBoxSrvc(errMsgDiv);
		$('#smServActive').val(activeness).attr('selected','selected');
	}
	
}*/

function showMsgModalBoxSrvc(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

function resetForm(){
	$('#deptId').val('');
	$('#serviceNameEng').val('');
	$('#serviceNameReg').val('');
	$('#smShortdesc').val('');
	$('#smServActive').val('');
	$('#smChklstVerify').val('');
	$('#challanvalidty').val('');
	$('#apprv').val('');
	$('#rejctn').val('');
	$('#smPrintRespons').val('');
	$('#remark').val('');
	$('#smProcessId').val('');
    $("#serviceMasterForm radio").removeAttr('selected');
    $("#serviceMasterForm checkbox").removeAttr('checked');
    $(".chosen-select-no-results").trigger("chosen:updated");
    $("#errorDivScrutiny").hide();
    $("#iffree").hide();
   
    
}


/*Defect #34893*/

function getchilddept() {	
	
	var requestData = {
		"deptId" : $("#deptId").val()
			};
	$('#cdmChildDeptId').html('');
	$('#cdmChildDeptId').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading('ServiceMaster.html?getchilddept',
			requestData, 'html');
	
	
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		
		$('#cdmChildDeptId').append(
				$("<option></option>").attr("value", value.dpDeptid).text(
						(value.dpDeptdesc)));
	});
	$('#cdmChildDeptId').trigger("chosen:updated");
}








/**
 * @author: Harsha Ramachandran
 */


/*$(document).ready(function(){
	var mode = $('#modeId').val();
	if(mode == 'update'){
		$('#tbOrganisation_esdtDate').prop('readonly',true);
	}
});*/
//var availableMobNoTags = [];
$(document).ready(function(){
	$(".chosen-select-no-results").trigger("change");
	
	$(".chosen-select-no-results").chosen();
	$('.chosen-select-no-results').next().addClass('mandColorClass');
	
	$("input").on("keypress", function(e) { 
	    if ((e.which === 32 && !this.value.length )|| e.which == 13 || e.which == 34 || e.which == 39)
	        e.preventDefault();
	});
	
/*	var mobileNoList= $("#mobileNoList").val().split(',');
	$.each(mobileNoList, function(index, value) {
		availableMobNoTags.push(value);
	});
	
	$(function() {
	$("#empMobNo").autocomplete({
		highlightClass : "bold-text",
		source : availableMobNoTags
	});
});*/
	
	$("input").on("keypress", function(e) {
	    if (e.which === 32 && !this.value.length)
	        e.preventDefault();
	});
	
	$('#file_list_0').hide();
	$('#uploadPreview').hide();
	otherTask();
	
});
//datepicker2 for Establishment Date
$('.datepicker1').datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
	    changeYear: true,
		yearRange: "-450:+450",
		minDate : new Date('1600/01/01'),
		maxDate : new Date()
	});

//datepicker2 for Transaction Date
$('.datepicker2').datepicker({
	dateFormat: 'dd/mm/yy',
	changeMonth: true,
    changeYear: true,
	yearRange: "-450:+450",
	minDate : new Date('1600/01/01'),
	maxDate : '+1Y'
});

jQuery('.hasNumber').keyup(function () {
    this.value = this.value.replace(/[^0-9]/g,'');
  
});
		
jQuery('.hasCharacter').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z]/g,'');
   
});

function validateLatitude()
{ 	$('.error-div').hide();
var errorList = [];
 var latVal = /^-?([0-8]?[0-9]|90)\.[0-9]{1,6}$/;
 var enteredLatitude = $("#tbOrganisation_orgLatitude").val();
   if(!latVal.test(enteredLatitude)){
	   errorList.push("Invalid Latitude,Please enter valid Latitude");
	  $("#tbOrganisation_orgLatitude").val('');
   }
   if(errorList.length > 0){
		showOrgError(errorList);
		return false;
	}
   return true;
}


function validateLongitude()
{  	$('.error-div').hide();
var errorList = [];
 var lngVal = /^-?((1?[0-7]?|[0-9]?)[0-9]|180)\.[0-9]{1,6}$/;
 var enterLangitude = $("#tbOrganisation_orgLongitude").val();
   if(!lngVal.test(enterLangitude)){
	   errorList.push("Invalid Longitude,Please enter valid Longitude");
	   $("#tbOrganisation_orgLongitude").val('');
   }
   if(errorList.length > 0){
		showOrgError(errorList);
		return false;
	}
   return true;
}

function saveData(obj){

	var gstNo = $('#tbOrganisation_gstNumber').val();
    var envFlag = $("#envFlag").val();
	
	if($("#tbOrganisation_orgStatus").is(':checked')){
		$("#tbOrganisation_orgStatus").val('A');
	}else{
		$("#tbOrganisation_orgStatus").val('I');
	}

	var errorList = [];
	if($("#tbOrganisation_ulbOrgid").val() == 0 || $("#tbOrganisation_ulbOrgid").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.orgid"));
	}
	if($("#tbOrganisation_orgShortNm").val() == 0 || $("#tbOrganisation_orgShortNm").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.orgShortNm"));
	}else{
		
		if($("#modeId").val() == "create"){
			var error = checkDuplicateShortCode();
			if(error != null && error != ""){
				errorList.push(error);
			}
			errorList = validateDefaultUserDetails(errorList);
			
		}	
	}
	 if($("#tbOrganisation_oNlsOrgname").val() == 0 || $("#tbOrganisation_oNlsOrgname").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.oNlsOrgname"));
	}
	if($("#tbOrganisation_oNlsOrgnameMar").val() == 0 || $("#tbOrganisation_oNlsOrgnameMar").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.oNlsOrgnameMar"));
	}
	
	if($('#tbOrganisation_esdtDate').val() == null || $('#tbOrganisation_esdtDate').val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.estdDate"));
	}
	
	if($('#tbOrganisation_trnscDate').val() == null || $('#tbOrganisation_trnscDate').val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.trnsStartDate"));
	}
	
	if($('#isDefault').val() == null || $('#isDefault').val() == ''){
		if($('#tbOrganisation_esdtDate').val() != null && $('#tbOrganisation_esdtDate').val() != '' && $('#tbOrganisation_trnscDate').val() != null && $('#tbOrganisation_trnscDate').val() != ''){
			var validDateUrl = "Organisation.html?validateDate";
			var reqData = {
			    	"estdDate" : $('#tbOrganisation_esdtDate').val(),
			    	"trnsDate" : $('#tbOrganisation_trnscDate').val()
		    	};
			var resp =__doAjaxRequest(validDateUrl,'post',reqData, false,'','');
			if(!$.isEmptyObject(resp)){
				for(var i=0;i<resp.length;i++){
					errorList.push(resp[i]);
				}
				
			}			
		}
	}
		
	if($("#orgCpdId").val() == 0 || $("#orgCpdId").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.orgCpdId"));
	}
	if($("#orgCpdIdDiv").val() == 0 || $("#orgCpdIdDiv").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.orgCpdIdDiv"));
	}
	if($("#orgCpdIdOst").val() == 0 || $("#orgCpdIdOst").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.orgCpdIdOst"));
	}
	if($("#orgCpdIdDis").val() == 0 || $("#orgCpdIdDis").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.orgCpdIdDis"));
	} 
	if($("#orgCpdIdState").val() == 0 || $("#orgCpdIdState").val() == ''){
		errorList.push(getLocalMessage("tbOrganisation.error.orgCpdIdState"));
	}
	
	if (envFlag == 'Y'){
		if($("#sdbId1").val() == 0 || $("#sdbId1").val() == ''){
			errorList.push(getLocalMessage("tbOrganisation.error.orgCpdIdState"));
		}
		if($("#sdbId2").val() == 0 || $("#sdbId2").val() == ''){
			errorList.push(getLocalMessage("tbOrganisation.error.orgCpdIdDis"));
		} 
		if($("#sdbId3").val() == 0 || $("#sdbId3").val() == ''){
			errorList.push(getLocalMessage("tbOrganisation.error.orgblock"));
		}
	}
	if(gstNo != 0 || gstNo != ''){
		var regExGst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([Z]){1}([0-9|a-zA-Z]){1}?$/;
		if(!regExGst.test(gstNo) && gstNo !=''){
			errorList.push(getLocalMessage("tbOrganisation.error.gstNo"));
		}
	}

	
	if(errorList.length > 0){
		showOrgError(errorList);
		return false;
	}
	
	var	formName =	findClosestElementId(obj,'form');
	var theForm	=	'#'+formName;
	var orgData = {};
	var orgId = 0;
	if($("#tbOrganisation_orgid").val() != undefined && $("#tbOrganisation_orgid").val() != null){
		orgId = $("#tbOrganisation_orgid").val();
	}
	
	orgData = __serializeForm(theForm);
	var url	=	"";
	var testIfExist = "Organisation.html?checkForExist";
	var requestData = {"mode" : $("#modeId").val(),
    		"orgId" : orgId,
	    	"ulbOrgId" : $("#tbOrganisation_ulbOrgid").val(),
	    	"orgName" : $("#tbOrganisation_oNlsOrgname").val(),
	    	"orgNameMar" : $("#tbOrganisation_oNlsOrgnameMar").val()
    	};
	var response =__doAjaxRequestForSave(testIfExist,'post',requestData, false,'','');
	
	if(response.length > 0){
		var errorList = [];
		for(var i in response){
			errorList.push(response[i]);
		}
		showOrgError(errorList);
		return false;
	}else{
			var redirectUrl='Organisation.html';
			
			if($("#modeId").val() == "create"){
				url="Organisation.html?create"
			}else{
				url = "Organisation.html?update"
			}
			
			$.ajax({
				url : url,
				data : orgData,
				 beforeSend: function() { 
					
				      $("#save").html('<option>Please wait...</option>');
				      $("#save").prop('disabled', true);
				      $("#reset").prop('disabled', true);
				      $("#back").prop('disabled', true);
				      
				    },
				success : function(response) {
				  if(response=="success"){
					     $("#save").html('save');
					      $("#save").prop('disabled', false);
					      $("#reset").prop('disabled', false);
					      $("#back").prop('disabled', false);
					var	errMsgDiv		=	'.msg-dialog-box';
					var message='';
					var cls = 'OK';
					var save="Record Saved Successfully";
					
					message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+save+'</h4>';
					 message	+='<div class=\'text-center padding-bottom-10\'>'+	
					'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
					' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+
					'</div>';
					
					$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
					$(errMsgDiv).html(message);
					$(errMsgDiv).show();
					$('#btnNo').focus();
					showModalBoxWithoutClose(errMsgDiv);
				  }else if(response=="failure"){
					  var errorList = [];
						errorList.push(getLocalMessage("admin.login.internal.server.error"));
						showOrgError(errorList);
				  }					 
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showOrgError(errorList);
				}
			});
		}
}

	function closeOutErrBox(){
		$('.error-div').hide();
	}

	function showOrgError(errorList){
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorDivCustBankMas").html(errMsg);
		$("#errorDivCustBankMas").show();
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}
	
	function showMsg(){
		var errorList = [];
		errorList.push(getLocalMessage("tbOrganisation.error.status"));
		showOrgError(errorList);
	}

	function resetOrg(){
		$('.error-div').hide();
		$("#userName").val("").attr('readonly', false);  
		$("#pass").val("").attr('readonly', false);
		$("#confirmpass").val("").attr('readonly', false);
		$("#empemail").val("").attr('readonly', false);
		//resetImage();
		setTimeout(function(){ $("select").trigger("chosen:updated"); }, 50);
		
	}
	
	function checkDuplicateShortCode(){
		$('.error-div').hide();
		var returnData = null;
		var orgShortCode = $('#tbOrganisation_orgShortNm').val();
		if($.trim(orgShortCode) != null && $.trim(orgShortCode) != ''){
			var url = "Organisation.html?checkDuplicateShortCode";
			var requestData ={
					"orgShortCode":orgShortCode
			}
			var returnData=__doAjaxRequest(url, 'post',requestData, '','','');
		}
		return returnData;
	}
	
	function otherTask() {
		var URL = 'Organisation.html?getUploadedImage';
		var data = {};
		
		var returnData = __doAjaxRequest(URL, 'post', data, false, 'json');
		$('#uploadPreview ul').empty();
		$('#file_list_0').hide();
		
		if (returnData != '' && returnData != null && returnData != 'null') {
			$('#uploadPreview ul')
					.append(
							'<li id="0_file_0_t"><img src="'+returnData+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="showConfirmBox();" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
			$('#uploadPreview').show();
		}
		else{
			var modeVal=$('#modeId').val();
			var imagePath=$('#hiddenLogoPath').val();
			if(modeVal=='update' && imagePath != "" && imagePath != null){
				$('#uploadPreview ul')
				.append(
						'<li id="0_file_0_t"><img src="'+imagePath+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="showConfirmBox();" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
				$('#uploadPreview').show();
			}
		}
	}
	
	function doFileDeletephoto() {
		
		var url = '';
		var formName = 'orgMaster';// findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		url = 'Organisation.html?doFileDeletion';
		$('#uploadPreview').hide();
		
		$('#hiddenDeleteFlag').val('Y');
		if (!(($.browser.msie) && ($.browser.version == 9.0))) {
			data1 = 'browserType=Other';
		} else {
			data1 = 'browserType=IE';

		}
		var t_id = '0_file_0';//$(obj).attr('id');
		var data = 'fileId=' + t_id + '&' + data1;

		var jsonResponse = __doAjaxRequest(url, 'post', data, false, 'json');
		$('#' + t_id + '_t').remove();

		if (!(($.browser.msie) && ($.browser.version == 9.0))) {

			$('.fileUploadClass').val(null);
		} else {

			var jsonMessage = jsonResponse.message;

			var msg = "";
			var firstLoop = jsonMessage.split('?');
			$
					.each(
							firstLoop,
							function(index, value) {
								if (index == 0) {
									msg = "<ul><li><b>" + value + "</b></li>";
								}

								if (index != 0) {
									var secondLoop = value.split('*');
									$
											.each(
													secondLoop,
													function(index, value) {
														if (index == 0)
															msg += "<li>"
																	+ value;
														if (index != 0)
															msg += "<img src='css/images/close.png' alt='Remove' width='17' title='Remove' id='"
																	+ value
																	+ "' onclick='doFileDeletephoto()' ></li>";
													});
								}

							});

			window.location.reload(false);
		}
		$.fancybox.close();
	}
	
	function showConfirmBox() {		
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = 'Yes';

		message += '<p class="text-center text-blue-2 padding-10">Are you sure want to delete?</p>';
		message += '<p style=\'text-align:center;margin: 5px;\'>'
				+ '<br/><input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="doFileDeletephoto()"/>' + '</p>';
	
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function resetImage(){
		
		$('#isReset').val('N');
		var URL = 'Organisation.html?getRealImagePath';
		var data = {
				'orgId':$('#tbOrganisation_orgid').val()
			}
		
		var returnData = __doAjaxRequest(URL, 'post', data, false, 'json');
		$('#uploadPreview ul').empty();
		$('#file_list_0').hide();
		if (returnData != '' && returnData != null && returnData != 'null') {
			$('#hiddenLogoPath').val(returnData);
			$('#isReset').val('Y');
			$('#uploadPreview ul')
					.append(
							'<li id="0_file_0_t"><img src="'+returnData+'" width="150" height="145" class="img-thumbnail"><a  href="#" onclick="doFileDeletephoto(this);" id="0_file_0" title="Delete"><i class="fa fa-trash fa-lg text-danger btn-lg"></i></a></li>');
			$('#uploadPreview').show();
		}
	}
	

	function getUserData(){
		var empMobNo = $.trim($('#empMobNo').val());
		$("#userName").val("").attr('readonly', false);  
		$("#pass").val("").attr('readonly', false);
		$("#confirmpass").val("").attr('readonly', false);
		$("#empemail").val("").attr('readonly', false);
		$("#defaultRegisteredUserPass").val('');
		$("#alreadyRegisteredUser").val('');
		var URL = 'Organisation.html?getUserData';
		var data = {'empMobNo':empMobNo}
		var returnData = __doAjaxRequest(URL, 'post', data, false, 'json');
		if (returnData != '' && returnData != null && returnData != 'null') {
			$.each(returnData, function(index){
				var obj= returnData[index];
				$("#userName").val(obj.emploginname).attr('readonly', true);  
				$("#pass").val(obj.emppassword).attr('readonly', true);
				$("#confirmpass").val("XXXXXXXX").attr('readonly', true);
				$("#empemail").val(obj.empemail).attr('readonly', true);
				
				$("#defaultRegisteredUserPass").val("XXXXXXXX");
				$("#alreadyRegisteredUser").val("Y");
				
			});
		}
	}
	
	
	function validateDefaultUserDetails(errorList){
		var empMobNo = $.trim($('#empMobNo').val());
		var userName = $.trim($("#userName").val());  
		var pass = $.trim($("#pass").val());
		var confirmpass = $.trim($("#confirmpass").val());
		var empemail = 	$.trim($("#empemail").val());
		var alredyRegistered= $("#alreadyRegisteredUser").val();
		
		if(empMobNo=='' || empMobNo==null){
			errorList.push(getLocalMessage("emp.error.mobileno"));
		} else {
			var phoneno = /^[1-9]{1}[0-9]{9}$/;
			if(!phoneno.test(empMobNo)) {
				errorList.push(getLocalMessage("emp.error.notValid.mobile"));
			}
		}
		if(userName=='' || userName==null){
			errorList.push(getLocalMessage("emp.error.loginname"));
		}
	
		if(alredyRegistered != 'Y'){
			if(pass=='' || pass==null) {
				errorList.push(getLocalMessage("emp.error.newpassword"));
			} 
			if( confirmpass=='' || confirmpass==null){
				errorList.push(getLocalMessage("emp.error.confirmnewpass"));
			}
			
			if(pass!='' && confirmpass!=''){
				var newPass = $.trim($("#pass").val());
				var confirmPass =  $.trim($("#confirmpass").val());
				if(newPass == confirmPass) {
					if (newPass.length > 7) {
						if (newPass.length < 16) {
							var passwordValidationRE = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
							var check = newPass.match(passwordValidationRE);
							if (check != null && check != 'null' && check != '') {
							} else {
								errorList.push(getLocalMessage("citizen.login.passMustContain.1st.error"));
								errorList.push(getLocalMessage("citizen.login.passMustContain.2nd.error"));
								errorList.push(getLocalMessage("citizen.login.passMustContain.3rd.error"));
								errorList.push(getLocalMessage("citizen.login.passMustContain.4th.error"));
							}
						} else {
							errorList.push(getLocalMessage("citizen.login.passMustContain.error"));
						}

					} else {
						errorList.push(getLocalMessage("citizen.login.passMustContain.8char.error"));
					}
				}else{
					errorList.push(getLocalMessage("emp.error.notEqual.password"));
				}
			}
			
			if(empemail != '') {
				var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
				if(!emailPattern.test(empemail)) {
					errorList.push(getLocalMessage("emp.error.notValid.email"));
				}
			}
			
		}
		
		return errorList;
		
	}
	

	
function getBlockList() {
    var requestData = {
		"sdbId2" : $("#sdbId2").val()
	};
	var URL = 'Organisation.html?getBlockList';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
	$('#sdbId3').html('');
	$('#sdbId3').append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown')));
	var prePopulate = JSON.parse(returnData);
	$.each(prePopulate, function(index, value) {
		$('#sdbId3').append(
				$("<option></option>").attr("value", value.lookUpId).text(
						(value.lookUpDesc)));
	});
	$('#sdbId3').trigger("chosen:updated");
}

function getDistrictList() {
	var requestData = {
		"sdbId1" : $("#sdbId1").val()
	};
		var URL = 'Organisation.html?getDistrictList';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'html');
		$('#sdbId2').html('');
		$('#sdbId2').append(
				$("<option></option>").attr("value", "").text(getLocalMessage('selectdropdown')));
		var prePopulate = JSON.parse(returnData);
		$.each(prePopulate, function(index, value) {
			$('#sdbId2').append(
					$("<option></option>").attr("value", value.lookUpId).text(
							(value.lookUpDesc)));
		});
		$('#sdbId2').trigger("chosen:updated");
}
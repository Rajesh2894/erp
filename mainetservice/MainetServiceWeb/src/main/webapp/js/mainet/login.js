/***

Author : 

***/



/**
 * declare global Login related function  
 */



$.getScript("js/mainet/alertify.min.js")
  .done(function( script, textStatus ) {
    console.log( textStatus );
  })
  .fail(function( jqxhr, settings, exception ) {
    $( "div.log" ).text( "Triggered ajaxError handler." );
});

$.getScript("js/mainet/jquery.browser.js")
.done(function( script, textStatus ) {
  console.log( textStatus );
})
.fail(function( jqxhr, settings, exception ) {
  $( "div.log" ).text( "Triggered ajaxError handler." );
});


var agencyLoginURL = 'AgencyLogin.html';
var citizenLoginURL = 'CitizenLogin.html';
var adminLoginURL='AdminLogin.html';
var error500 = 'Internal Server Error !!!';
var citizenRegURL = 'CitizenRegistration.html';
var adminRegURL = 'AdminRegistration.html';
var agencyRegURL = 'AgencyRegistration.html';
var citizenForgotPassword = 'CitizenForgotPassword.html';
var agencyForgotPassword = 'AgencyForgotPassword.html';
var agencyResetPassword = 'AgencyResetPassword.html';
var citizenResetPassword = 'CitizenResetPassword.html';
var adminResetPassword = 'AdminResetPassword.html';




/*  Login related function  start*/


function getCitizenLoginForm(val) {
	openPopuplogin(citizenLoginURL,val);
}


function getAgencyLoginForm() {
	
	openPopup(agencyLoginURL);
}

function getAdminLoginForm() {
	
	openPopup(adminLoginURL);
}





function doCitizenLogin(obj) {
	var errorList = [];
	$(obj).prop('disabled',true);
	if(!(($.browser.msie) && ($.browser.version==9.0))){
		console.log(" Processing");
	  }
/*	alertify.set({ delay: 3000 });
	alertify.log(getLocalMessage("process.completed.alert"));*/
	errorList = validateCitizenLoginFrom(errorList);
	
	if (errorList.length == 0) {
		var form = document.getElementById('citizenLoginForm');
	    var formData = $(form).serialize();
	    var pass = __doAjaxRequest('CitizenLogin.html?passEncrypt', 'POST', formData, false);
	    
	    $('#citizenEmployee\\.emppassword').val(pass);
	    doLogin('citizenLoginForm','AdminOTPVerification.html',citizenLoginURL,errorList,'citizen.login.incorrectIdAndPass.error',obj);
	}else{
		doRefreshLoginCaptcha();
		showError(errorList);
		$(obj).prop('disabled',false);
	}
	
}


function doAgencyLogin(obj) {
	var errorList = [];
	
	$(obj).prop('disabled',true);
	if(!(($.browser.msie) && ($.browser.version==9.0))){
		console.log(" Processing");
	  }
	
	/*alertify.set({ delay: 3000 });
	alertify.log(getLocalMessage("process.completed.alert"));*/
	errorList=validateAgencyLoginFrom(errorList);
	if (errorList.length == 0) {
		var form = document.getElementById('agencyLoginForm');
	    var formData = $(form).serialize();
	    var pass = __doAjaxRequest('AgencyLogin.html?passEncrypt', 'POST', formData, false);
	    $('#emppassword').val(pass);
	   doLogin('agencyLoginForm','AgencyOTPVerification.html',agencyLoginURL,errorList,'agency.login.incorrectIdAndPass.error',obj);
	}else{
			showError(errorList);
			$(obj).prop('disabled',false);
		}
	
}

/*function showloader(showloader){
	if(showloader){$(".sloading").show();$("#sdiv").show();}else{
	$(".sloading").hide();$("#sdiv").hide();}
}*/

function doAdminLogin(obj) {
	var errorList = [];
	$(obj).prop('disabled',true);
	if(!(($.browser.msie) && ($.browser.version==9.0))){
		
	  }
	//alertify.set({ delay: 3000 });
	//alertify.log(getLocalMessage("process.completed.alert"));
	
	errorList =  validateAdminLoginFrom(errorList);
	errorList = checkPasswordMaxLength(errorList,'adminEmployee\\.emppassword');
	if (errorList.length == 0){showloader(true);}
	setTimeout(function(){serviceLogin(obj,errorList)},2);
}

function serviceLogin(obj,errorList){
if (errorList.length == 0) {		
	var form = document.getElementById('adminLoginForm');
    var formData = $(form).serialize();
    /* var pass = __doAjaxRequest('AdminLogin.html?passEncrypt', 'POST', formData, false);*/
    var str = $('#adminEmployee\\.emppassword').val();
    var pass = window.btoa(str);
    $('#adminEmployee\\.emppassword').val(pass);

    doLogin('adminLoginForm','AdminOTPVerification.html',adminLoginURL,errorList,'admin.login.incorrectIdAndPass.error',obj);
	}else{
	showError(errorList);
	$(obj).prop('disabled',false);
	showloader(false);
	}
}
function getLocation(){
	 $("#selectedLocation").text(''); 
	 var langId=$("#languageId").val();
	 var empName=$("#emploginname").val();
	
	if($('#selectedDistrict').val() > 0){
		 var requestData = {
				 orgId : $("#selectedDistrict").val(),
				 employeeName : $("#emploginname").val(),
		 };
		 var url	=	"AdminLogin.html?locationsList";
		 var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,''); 
		 if(returnData != ""){
			 $("#selectedLocation")
			 .append($("<option></option>")
					 .attr("value",-1)
					 .text(getLocalMessage('eip.admin.login.select.location')));
			 $.each(returnData, function( index, value ) {
				 if(langId == 1){
					 $("#selectedLocation")
					 .append($("<option></option>")
							 .attr("value",value[0])
							 .text(value[2]));
				 }else{
					 $("#selectedLocation")
					 .append($("<option></option>")
							 .attr("value",value[0])
							 .text(value[3]));
				 }
				 if(value[0] == value[1]){
					  $('#selectedLocation').val(value[0]);
				 }
				 
			 });
		 }else{
			 $('#selectedLocation').val(-1);
		 }
		 $(".chosen-select-no-results").trigger("chosen:updated");
	}
}


function getLocationByUser(){
	 var langId=$("#languageId").val();
	$(".error-div").hide();
	$('#selectedLocation').val();
	var empName = $("#emploginname").val();
	var orgId = $("#selectedDistrict").val();
	var orgNme= $("#selectedDistrict option:selected").text();
	if(orgId > 0 && empName != null && empName != ""){
				 var requestData = {
				 orgId : orgId,
				 employeeName: empName
		 };
		 var empLocationUrl	=	"AdminLogin.html?getEmployeeLocation";
		  var returnData=__doAjaxRequestForSave(empLocationUrl, 'post',requestData, false,'');
		  var errorList = [];
		  if(returnData != ""){
			  if (langId == 1) {
				$("#selectedLocation").append(
						$("<option></option>").attr("value", returnData.locId)
								.text(returnData.locNameEng));
				$('#selectedLocation').val(returnData.locId);

			} else {
				$("#selectedLocation").append(
						$("<option></option>").attr("value", returnData.locId)
								.text(returnData.locNameReg));
				$('#selectedLocation').val(returnData.locId);
			}
			 
		 }else if(returnData == null || returnData == ""){ 
			 $('#selectedLocation').val(-1);
			 errorList.push(getLocalMessage("eip.landingpage.employee.msg")+orgNme);
			 showError(errorList);
			 $(".error-div").show();
		 }
		  $(".chosen-select-no-results").trigger("chosen:updated");
	}
}




/**
 * Method doLogin(formId,verificationReq,url,errorList,displayMsg) is used to submit login form.
 */

function doLogin(formId,verificationReq,url,errorList,displayMsg,obj){
	    var form = document.getElementById(formId);
	    var formData = $(form).serialize();
        var result = __doAjaxRequest(url, 'POST', formData, false);
        showloader(false);
		if (result != null && result != '') {
			
			if(result == 'orgNotSelect'){
				$(obj).prop('disabled',false);
				clearPassword(formId);
				errorList.push(getLocalMessage("eip.landingpage.urbanlocal.msg"));
				//doRefreshLoginCaptcha();
				showError(errorList);
			}else if (result == 'captchaNotMatched') {
				$(obj).prop('disabled',false);
				clearPassword(formId);
				errorList.push(getLocalMessage("citizen.login.reg.captha.valid.error"));
				//doRefreshLoginCaptcha();
				showError(errorList);
			}else if(result == 'authenticationFailed'){
				$(obj).prop('disabled',false);
				clearPassword(formId);
				 errorList.push(getLocalMessage("citizen.login.incorrectIdAndPassAndAgency.error"));
				 //doRefreshLoginCaptcha();
				 showError(errorList);
			}else if(result == 'accountLock'){
				 $(obj).prop('disabled',false);
				 clearPassword(formId);
				 errorList.push(getLocalMessage("admin.login.account.lock"));
				 //doRefreshLoginCaptcha();
				 showError(errorList);
				
			}else if(result === 'passwordExpired'){
				 $(obj).prop('disabled',false);
				 clearPassword(formId);
				 errorList.push(getLocalMessage("password.expired"));
				 //doRefreshLoginCaptcha();
				 openPopup(adminResetPassword);
				 showError(errorList);
				
			}// #130199
			/*else if(result === 'AlreadyLoggedIn'){				
				 $(obj).prop('disabled',false);
				 clearPassword(formId);
				 errorList.push(getLocalMessage("citizen.already.logged"));
				 //doRefreshLoginCaptcha();
				 showError(errorList);
			}*/
			else if(result === 'FirstLogin'){
				 getAdminResetPassStep0();
			}else if(result === 'longPassDenailOfService'){
				
				 $(obj).prop('disabled',false);
				 clearPassword(formId);
				 errorList.push(getLocalMessage("admin.login.passMustContain.error"));
				// doRefreshLoginCaptcha();
				 showError(errorList);
			}
			else{
				
				if(result==adminOTPVerificationURL){
					
						getAdminOTPVerificationForm(result);
					
				}else{
					formRedirect(result,false);
				}
				
			}
			
		} else {
			 $(obj).prop('disabled',false);
			 clearPassword(formId);
			 errorList.push(getLocalMessage("citizen.login.incorrectIdAndPass.error"));
			 //doRefreshLoginCaptcha();			 
			 showError(errorList);
		}
		
	
	
}


function clearPassword(formId){
	if(formId == 'citizenLoginForm'){
		$('#citizenEmployee\\.emppassword').val('');
		$('#captchaSessionLoginValue').val('');
	}else if(formId == 'adminLoginForm'){
		$('#adminEmployee\\.emppassword').val('');
	}else{
		$('#emppassword').val('');
	}
	
}



/*  Login related function end */



/*  Registration related function start */

function getCitizenRegistrationForm() {

	
	openPopup(citizenRegURL);
}

function getAdminRegistrationForm() {

	
	openPopup(adminRegURL);
}


function getAgencyRegistrationForm() {

	
	openPopup(agencyRegURL);
}




/*  Registration related function end */


/*  RecoverOTP related function start */

function getCitizenForgotPassStep1(otpType) {

	
	openPopupForOTP_MobileNo(citizenForgotPassword,otpType);
}

function getAgencyForgotPassStep1(otpType) {

	
	openPopupForAgencyLognOTPMobileNo(agencyForgotPassword,otpType);
}


function getAdminForgotPassStep1() {

	
	
	openPopup('AdminForgotPassword.html');
	
}


/*  RecoverOTP related function end */



/* Reset pass related function start */

function getCitizenResetPassStepI() {

	
	openPopup(citizenResetPassword);
}


function getAgencyResetPassStepI() {

	
	openPopup(agencyResetPassword);
}


function getAdminResetPassStep0() {
	
	var actionParam = {
			'emploginname' : $('#emploginname').val()
	}; 

	var url = "AdminUpdatePersonalDtls.html?showDetails";

	var response = __doAjaxRequest(url, 'GET',actionParam, false, 'html');
	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);
	prepareTags();
	
}

function getAdminResetPassStepI() {

	openPopup(adminResetPassword);
	
}



/* Reset pass related function end */


/* Globally access function start*/


/**
 * Method openPopup(url) is used to get Login form,Registration form
 */

function openPopupForOTP_MobileNo(url,otpType){
	
	var type='';
	var val=getLocalMessage("eip.commons.submitBT");
	var response = __doAjaxRequest(url, 'get', {}, false, 'html');
    var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	
	if(otpType=='Register'){
	     type= 'Register';
	}else{
	     type='Nonregister'	 ;
	     $(childPoppup).find('.login-heading').html(getLocalMessage("eip.mobile.change"));
	}
	$(childPoppup).find('#checkOTPType').prepend('<input type=\'button\' value=\''+val+'\' onclick="getCitizenForgotPassStep2(\''+type +'\')"   />');
	$(childPoppup).find('#mobileTextForKeyPress').append('<input type=\'hidden\' value=\''+type+'\' id=\'checkOTPTypeValue\' name=\'checkOTPTypeValue\' />');
	$(childPoppup).show();
    showModalBox(childPoppup);
    					
   
}

function openPopupForAgencyLognOTPMobileNo(url,otpType){
	
	var type='';
	var val=getLocalMessage("eip.commons.submitBT");
	var response = __doAjaxRequest(url, 'get', {}, false, 'html');
    var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	
	if(otpType=='Register'){
	     type= 'Register';
	}else{
	     type='Nonregister'	 ;
	     $(childPoppup).find('.login-heading').html(getLocalMessage("eip.mobile.change"));
	}
	$(childPoppup).find('#checkOTPType').prepend('<input type=\'button\' value=\''+val+'\' onclick="getAgencyForgotPassStep2(\''+type +'\')"   />');
	$(childPoppup).find('#mobileTextForKeyPress').append('<input type=\'hidden\' value=\''+type+'\' id=\'checkOTPTypeValue\' name=\'checkOTPTypeValue\' />');
	$(childPoppup).show();
    showModalBox(childPoppup);
    					
   
}

function openPopuplogin(url,val){
	
	var data = {
					"quickflag" : val
			   };
	var response = __doAjaxRequest(url, 'get', data, false, 'html');
	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);
	
}
function openPopup(url){
	var response = __doAjaxRequest(url, 'get', {}, false, 'html');
	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);
	
}

function openPopupWithResponse(response){
	
	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);
	
}

function showError(errorList) {
	
	
	var errMsg = '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	
    $('.error-div').html(errMsg);
    if(errMsg){
    	$('.error-div').show();
    }
}


function __doAjaxRequestForSave(url, reqType, data, async, dataType,obj) {
	 
     //var result = '';
	 //var token='';
	showloader(true);
	 var inputType=$(obj).attr('type');
	 if(typeof inputType != 'undefined'){
		if(inputType == 'submit' || inputType == 'button'){
			 $('.error-div').remove();
			 $(obj).prop('disabled',true);
			 if(!(($.browser.msie) && ($.browser.version==9.0))){
				console.log("Processing");
			 }
			 
			 /*alertify.log(getLocalMessage("process.completed.alert"));*/
		 }
    }
	  
	$.ajax({
		url : url,
		data : data,
		type : reqType,
		async : async,
		dataType : dataType,
		success : function(response) {
			result = response;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	showloader(false);
	return result;
}

$(document).ready(function() {
	$('#emploginname').focus();
	if( $("#selectedDistrict option:selected").val()!=undefined && $("#selectedDistrict option:selected").val() >'0')
	{
		var langId=$("#languageId").val();
	 var requestData = {
			 orgId : $("#selectedDistrict option:selected").val() };
			
	 var url	=	"AdminLogin.html?locationList";
	 var returnData=__doAjaxRequestForSave(url, 'post',requestData, false,''); 
	  
	 if(returnData != ""){
		 $.each(returnData, function( index, value ) {
			     if(langId == 1)
			    	 {
			    	 $("#selectedLocation")
					 .append($("<option></option>")
							 .attr("value",value.locId)
							 .text(value.locNameEng));
			    	 }
			     else
			    	 {
			    	 $("#selectedLocation")
					 .append($("<option></option>")
							 .attr("value",value.locId)
							 .text(value.locNameReg));
			    	 }
		
		 });
	 }
	 $("#selectedLocation").trigger("chosen:updated");
	}
});	

	function formRedirect(result,showloader)
	{
	  if(showloader){ajaxloader();}
      /*var obj ='.error-dialog';
      var loading	 ='<img src=\'css/images/loader.gif\' title=\'Loading...\' alt=\'Loading...\' />';
	  $(obj).addClass('ajaxloader').addClass('padding-10').addClass('text-center').fadeIn('slow');
	  $(obj).html(loading);
	  showModalBoxWithoutClose(obj);*/
	
	   var form = document.createElement("form");
       form.setAttribute("method", "get");
       form.setAttribute("action", result);
       form.setAttribute("target", "_self");

       document.body.appendChild(form);
       form.submit();
}
   function ajaxloader(){
	  var obj ='.error-dialog';
      var loading	 ='<img src=\'css/images/loader.gif\' title=\'Loading...\' alt=\'Loading...\' />';
	  $(obj).addClass('ajaxloader').addClass('padding-10').addClass('text-center').fadeIn('slow');
	  $(obj).html(loading);
	  showModalBoxWithoutClose(obj);
   } 
function __doAjaxRequest(url, reqType, data, async, dataType, skip) {
	showloader(true);
	// condition add by vardan sir as csrf token is to be set for POST request and not for GET.
	if(reqType && reqType.toLowerCase() === "post") {
		if(skip=="0"){
			var token = "";
			var header ="";
		}else{
			 var token = $("meta[name='_csrf']").attr("content");
			 var header=$("meta[name='_csrf_header']").attr("content");
			 }
		 //alert(token);
		if(header=="" && token==""){
			
		}else{
			 $(document).ajaxSend(function(e, xhr, options) {
				 xhr.setRequestHeader(header, token);
			    });
		}
	}
	 $.ajax({
		url : url,
		data : data,
		type : reqType,
		async : async,
		dataType : dataType,
		headers   : {header: token},
		success : function(response) {
			result = response;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});
	 showloader(false);
	return result;
}

function __doAjaxRequestWithCallback(url, reqType, data, async, dataType,
		successCallback, errorCallback) {
	showloader(true);
	// condition add by vardan sir as csrf token is to be set for POST request
	// and not for GET.
	if (reqType && reqType.toLowerCase() === "post") {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	}
	$
			.ajax({
				url : url,
				data : data,
				type : reqType,
				async : async,
				dataType : dataType,
				headers : {
					header : token
				},
				success : function(response) {
					if (successCallback != null || successCallback != undefined) {
						successCallback(response);
					} else {
						result = response;
					}

				},
				error : function(response) {
					if (errorCallback != null || errorCallback != undefined) {
						errorCallback(response);
					} else {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}

				}

			});
	showloader(false);
	return result;
}

$.preloadImages = function() {
	  for (var i = 0; i < arguments.length; i++) {
	    $("<img />").attr("src", arguments[i]);
	  }
	};
	$.preloadImages("css/images/loader.gif");
/* Globally access function start*/
	
	function doRefreshLoginCaptcha(){
		 var idxRAND=Math.floor(Math.random()*90000) + 10000;
		 $("#cimg").attr("src",'AdminResetPassword.html?captcha&id='+idxRAND);
	}
	
	function checkPasswordMaxLength(errorList,id){
		debugger
	 var passVal = $('#'+id).val();
	if(passVal  !=undefined && passVal != '' && passVal.length > 16 ){
		errorList.push(getLocalMessage("admin.login.passMustContain.error"));
	}
	return errorList;
    }
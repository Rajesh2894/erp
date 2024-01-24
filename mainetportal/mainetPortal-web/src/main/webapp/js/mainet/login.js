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
var feedbackURL = 'CitizenFeedBack.html';
	
var div = '.widget login margin-bottom-0';

/*  Login related function  start*/


function getCitizenLoginForm(val) {	
	openPopuplogin(citizenLoginURL,val);
	if($("#top-header").hasClass('hide')){
		$("#top-header").removeClass('hide');
	}
	if($(".header-inner").hasClass('hide')){
		$(".header-inner").removeClass('hide');
	}
}

function getCitizenLoginFormredirectLogin(val,redirectLoginURL) {
	openPopupredirectLogin(citizenLoginURL,val,redirectLoginURL);
}

function msgBoxLogin()
{
	var fst = getLocalMessage("citizen.user.not.registered");
	var snd = getLocalMessage("citizen.user.not.registered.msg")
	var messageText = fst+'<br/>'+snd;
	var message='';
	message	+=	'<p style="text-align: center; padding: 0px 10px;">'+messageText+'</p>';
	message	+=	'<p style=\'text-align:center;margin: 5px;\'>';					
	message	+=	'<input type=\'button\' value=\'Yes\'';
	message	+=	' onclick="getCitizenLoginFormValidationPopUp(1)" class="btn btn-success" style="margin-right: 10px;"/>';
	message	+=	'<input type=\'button\' value=\'No\'';
	message	+=	' onclick="getCitizenLoginFormValidationPopUp(2)" class="btn btn-danger"/>';
	message	+=	'</p>';
	$(errMsgDiv).html(message);	
	$(errMsgDiv).show();
	showModalBoxWithoutClose(errMsgDiv);
}

function getCitizenLoginFormValidationPopUp(flag) {
	$('.fancybox-overlay').remove();
	$('.fancybox-wrap').remove();
	if(flag == 1){
		getCitizenRegistrationForm();
	}else{
		getCitizenLoginForm('N');
	}
	
}


function getAgencyLoginForm() {
	
	openPopup(agencyLoginURL);
}

function getAdminLoginForm() {
	
	openPopup(adminLoginURL);
	if($("#top-header").hasClass('hide')){
		$("#top-header").removeClass('hide');
	}
	if($(".header-inner").hasClass('hide')){
		$(".header-inner").removeClass('hide');
	}
	
}

function getfeedbackForm()
{
	openPopup(feedbackURL);
}


function doCitizenLogin(obj) {
	var errorList = [];
	$(obj).prop('disabled',true);
	if(!(($.browser.msie) && ($.browser.version==9.0))){
		console.log(" Processing");
	  }
	/*alertify.set({ delay: 3000 });*/
	/*alertify.log(getLocalMessage("process.completed.alert"));*/
	errorList = validateCitizenLoginFrom(errorList);
	errorList = checkPasswordMaxLength(errorList,'citizenEmployee\\.emppassword');
	if (errorList.length == 0) {
		var form = document.getElementById('citizenLoginForm');
	    var formData = $(form).serialize();
	    /*var pass = __doAjaxRequest('CitizenLogin.html?passEncrypt', 'POST', formData, false);*/
	    var str = $('#citizenEmployee\\.emppassword').val();
	    var pass = window.btoa(str);
	    $('#citizenEmployee\\.emppassword').val(pass);
	    doLogin('citizenLoginForm','CitizenOTPVerification.html',citizenLoginURL+"?login",errorList,'citizen.login.incorrectIdAndPass.error',obj);
	}else{
		doRefreshLoginCaptcha();
		showError(errorList);
		$(obj).prop('disabled',false);
		$('#captchaSessionLoginValue').val("");
	}
	
}


function doAgencyLogin(obj) {
	var errorList = [];
	
	$(obj).prop('disabled',true);
	if(!(($.browser.msie) && ($.browser.version==9.0))){
		console.log(" Processing");
	  }
	

	errorList=validateAgencyLoginFrom(errorList);
	if (errorList.length == 0) {
		var form = document.getElementById('agencyLoginForm');
	    var formData = $(form).serialize();
	    /*var pass = __doAjaxRequest('AgencyLogin.html?passEncrypt', 'POST', formData, false);*/
	    var str = $('#emppassword').val();
	    var pass = window.btoa(str);
	    $('#emppassword').val(pass);
	   doLogin('agencyLoginForm','AgencyOTPVerification.html',agencyLoginURL+"?login",errorList,'agency.login.incorrectIdAndPass.error',obj);
	}else{
			doRefreshLoginCaptcha();
			showError(errorList);
			$(obj).prop('disabled',false);
			$('#captchaSessionLoginValue').val("");
		}
	
}


function doAdminLogin(obj) {
	var errorList = [];
	$(obj).prop('disabled',true);
	if(!(($.browser.msie) && ($.browser.version==9.0))){
		console.log(" Processing");
	  }
	
	errorList =  validateAdminLoginFrom(errorList);
	errorList = checkPasswordMaxLength(errorList,'adminEmployee\\.emppassword');
	if (errorList.length == 0) {
		var form = document.getElementById('adminLoginForm');
	    var formData = $(form).serialize();
	   /* var pass = __doAjaxRequest('AdminLogin.html?passEncrypt', 'POST', formData, false);*/
	    var str = $('#adminEmployee\\.emppassword').val();
	    var pass = window.btoa(str);
	    $('#adminEmployee\\.emppassword').val(pass);
	    doLogin('adminLoginForm','CitizenOTPVerification.html',adminLoginURL+"?login",errorList,'admin.login.incorrectIdAndPass.error',obj);
	}else{
		doRefreshLoginCaptcha();
		showError(errorList);
		$(obj).prop('disabled',false);
		$('#captchaSessionLoginValue').val("");
	}
	
}



/**
 * Method doLogin(formId,verificationReq,url,errorList,displayMsg) is used to submit login form.
 */

function doLogin(formId,verificationReq,url,errorList,displayMsg,obj){
	    
	
	    var form = document.getElementById(formId);
	    var formData = $(form).serialize();
        var result = __doAjaxRequest(url, 'POST', formData, false);

		/*if (result != null && result != '') {
			//window.open(result, '_self');
			if(result==verificationReq){
				getCitizenOTPVerificationForm(result);
			}else if(result=='otpNotVerified'){
				var response = __doAjaxRequest(adminResetPassword, 'get', {}, false, 'html');

				show_Poppup(response);
				
			}else{
				formRedirect(result);
			}
			
		} else {
			
				errorList.push(getLocalMessage(displayMsg));
				showError(errorList);
		}*/
		
		
		if (result != null && result != '') {
			//window.open(result, '_self');
			
			if (result == 'Captcha Not Matched') {
				$(obj).prop('disabled',false);
				clearPassword(formId);
				errorList.push(getLocalMessage("citizen.login.reg.captha.valid.error"));
				doRefreshLoginCaptcha();
				showError(errorList);
				
			} 
			
			else if(result == 'authenticationFailed'){
				$(obj).prop('disabled',false);
				clearPassword(formId);
				 errorList.push(getLocalMessage("citizen.login.incorrectIdAndPassAndAgency.error"));
				 doRefreshLoginCaptcha();
				 showError(errorList);
		}else if(result === 'accountLock'){
			 $(obj).prop('disabled',false);
			 clearPassword(formId);
			 errorList.push(getLocalMessage("citizen.login.account.lock"));
			 doRefreshLoginCaptcha();
			 showError(errorList);
			
		}else if(result === 'Password Wrong'){
			 $(obj).prop('disabled',false);
			 clearPassword(formId);
			 errorList.push(getLocalMessage("citizen.login.account.wrong.password"));
			 doRefreshLoginCaptcha();
			 showError(errorList);
			
		}else if(result === 'User Not Exist'){
			 $(obj).prop('disabled',false);
			 clearPassword(formId);
			 //errorList.push("User Not Registered");
			 msgBoxLogin();
			 doRefreshLoginCaptcha();
			 showError(errorList);
			
		}
		else if(result === 'passwordExpired'){
			 $(obj).prop('disabled',false);
			 clearPassword(formId);
			 errorList.push(getLocalMessage("password.expired"));
			 doRefreshLoginCaptcha();
			 showError(errorList);
			
		} //#130199
		/*else if(result === 'AlreadyLoggedIn'){				
			$(obj).prop('disabled',false);
			 clearPassword(formId);
			 errorList.push(getLocalMessage("citizen.already.logged"));
			 doRefreshLoginCaptcha();
			 showError(errorList);
		}*/
		else if(result === 'FirstLogin'){
				 getAdminResetPassStepI();
		}else if(result === 'longPassDenailOfService'){
			 $(obj).prop('disabled',false);
			 clearPassword(formId);
			 errorList.push(getLocalMessage("admin.login.passMustContain.error"));
			 doRefreshLoginCaptcha();
			 showError(errorList);
		}else{
				
				if(result==verificationReq){
					if(result == 'AgencyOTPVerification.html'){
						getAgencyOTPVerificationForm(result);
					}else{
						getCitizenOTPVerificationForm(result);
					}
				}else{
					
					if(result =='CitizenHome.html')
					{	
					formRedirect(result);
					}
					else if(result.indexOf("redirect") != -1){
					var	start   = result.lastIndexOf(":"); 
					var end     = result.length;
					var redirectURL=result.substring(start+1, end) ;
					formRedirect(redirectURL);
					}
					else
					{	
					url = result ;
					var result2 = __doAjaxRequest(url, 'GET', formData, false);
				    $('.content-page').html(result2);
						//formRedirect(result);
					}
				}
				
			}
			
		} else {
			 $(obj).prop('disabled',false);
			 clearPassword(formId);
			 errorList.push(getLocalMessage("citizen.login.incorrectIdAndPass.error"));
			 doRefreshLoginCaptcha();
			 showError(errorList);
			
			
		}
		
	
	
}


function clearPassword(formId){
	if(formId == 'citizenLoginForm'){
		$('#citizenEmployee\\.emppassword').val('');
		$('#captchaSessionLoginValue').val('');
	}else if(formId == 'adminLoginForm'){
		$('#adminEmployee\\.emppassword').val('');
		$('#captchaSessionLoginValue').val('');
	}else{
		$('#emppassword').val('');
	}
	
}



/*  Login related function end */

function dofeedback(obj) {
	var errorList = [];
	         errorList=validateAgencyLoginFrom1(errorList);
			if (errorList.length == 0) {
		         var form = document.getElementById('frmCitizenFeedBack');
		         var formData = $(form).serialize();
		        
		         var response = __doAjaxRequest('CitizenFeedBack.html?sendfeedback', 'POST', formData, false);
	        	

		         if ($.isPlainObject(response)){
		        	 
		        	 $('.message').hide();
		        	 	errorList.push(response.command.message);
		        	 	 doRefreshLoginCaptcha();
		        	 	$('#captchaSessionLoginValue').val("");
						showError(errorList);
						$(obj).prop('disabled',false);
		        	 
		         }else{
		        	 errorList = [];
			         errorList.push(getLocalMessage("Feedback.Save"));
			         doRefreshLoginCaptcha();
			         showError1(errorList);
		         }
		         
			}
			else
			{
				$('.message').hide();
				showError(errorList);
				$(obj).prop('disabled',false);
			}	
}

function subscribeNews(obj) {
	var errorList = [];
	errorList = validateEmail(errorList);
	if (errorList.length == 0) {
		var form = document.getElementById('frmNewsLetterSubscription');
		var formData = $(form).serialize();
		var response = __doAjaxRequest('NewsLetterSubscription.html?subscribe',
				'POST', formData, false);
		if (typeof(response) === "string"){
			errorList.push(getLocalMessage("portal.newssub.save"));
			showError1(errorList);
		}else{
			errorList.push(response.command.message);
			showError(errorList);
		}
		
		
	} else {
		$('.message').hide();
		showError(errorList);
		$(obj).prop('disabled', false);
	}
}

function validateAgencyLoginFrom1(errorList) {

	if (isEmpty('fdUserName')) {
		errorList.push(getLocalMessage("Feedback.clientUserName"));
	}
	if (isEmpty('mobileNo')) {
		errorList.push(getLocalMessage("Feedback.clientmobileNo"));
	}
	else
		{
		var mobileNo=document.getElementById('mobileNo').value;
		if(mobileNo.length!=""){
			var count=0;
			
			for(var i=0; i<mobileNo.length; i++){
				
				var val=mobileNo.charAt(i);
				if(val==0){
					count++;
				}
				
			}
			
			if(count==10){
				errorList.push(getLocalMessage("citizen.login.valid.mob.error"));
			}else if(mobileNo.length<=9){
				errorList.push(getLocalMessage("citizen.login.valid.10digit.mb.error"));
			}
		}
		}
	if (isEmpty('emailId')) {
		errorList.push(getLocalMessage("Feedback.clientemailId"));
	}
	else{
		var empEmail=document.getElementById('emailId').value;
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		
		if (reg.test(empEmail) == false) 
	    {
	        errorList.push(getLocalMessage("citizen.valid.email.error"));
	       
	    }
	}
	if (isEmpty('feedback.feedBackDetails')) {
		errorList.push(getLocalMessage("Feedback.clientfeedBackDetails"));
	}
	
	if($('#captchaSessionLoginValue').val() == '' && getCookie("accessibility")!='Y' ){
		errorList.push(getLocalMessage("citizen.login.reg.captha.error"));
	}
	return errorList;
}
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
//	var val=getLocalMessage("eip.commons.submitBT");
	var response = __doAjaxRequest(url, 'get', {}, false, 'html');
    var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	//$(childPoppup).html(response);
	
	if(otpType=='Register'){
	     type= 'Register';
	}else{
	     type='Nonregister'	 ;
	     $(childPoppup).find('.login-heading').html(getLocalMessage("eip.mobile.change"));
	}
	/*$(childPoppup).find('#checkOTPType').prepend(' <div class="col-lg-6 col-md-6 col-xs-6" > <input type=\'button\' value=\''+Submit+'\'  class=\'btn btn-primary btn-block\' onclick="getCitizenForgotPassStep2(\''+type +'\')" /> </div>');
	$(childPoppup).find('#mobileTextForKeyPress').append('<input type=\'hidden\' value=\''+type+'\' id=\'checkOTPTypeValue\' name=\'checkOTPTypeValue\' />');*/
	/*$(childPoppup).show();
    showModalBox(childPoppup);
  
    					*/
	  $('.content-page').html(response);
   
}

function openPopupForAgencyLognOTPMobileNo(url,otpType){
	
	var type='';
	var val=getLocalMessage("eip.commons.submitBT");
	var response = __doAjaxRequest(url, 'get', {}, false, 'html');
    var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	/*$(childPoppup).html(response);*/
	
	if(otpType=='Register'){
	     type= 'Register';
	}else{
	     type='Nonregister'	 ;
	     $(childPoppup).find('.login-heading').html(getLocalMessage("eip.mobile.change"));
	}
	$(childPoppup).find('#checkOTPType').prepend('<input type=\'button\' value=\''+val+'\' onclick="getAgencyForgotPassStep2(\''+type +'\')"   />');
	$(childPoppup).find('#mobileTextForKeyPress').append('<input type=\'hidden\' value=\''+type+'\' id=\'checkOTPTypeValue\' name=\'checkOTPTypeValue\' />');
	/*$(childPoppup).show();
    showModalBox(childPoppup);*/
	 $('.content-page').html(response);
    					
   
}

function openPopuplogin(url,val){
	
	var data = {
					"quickflag" : val
			   };
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	var childPoppup = '.popup-dialog';
	//$(childPoppup).addClass('login-dialog');
	$('.content-page').html(response);
	if(getCookie("accessibility")=='Y')
	{
	$("#captchaL").hide()
	}
	/*$(childPoppup).show();
    showModalBox(childPoppup);*/
	
}


function openPopupredirectLogin(url,val,redirecturl){
	
	var data = {
					"quickflag1" : val,"redirecturl1":redirecturl
			   };
	var response = __doAjaxRequest(url, 'get', data, false, 'html');
	var childPoppup = '.popup-dialog';
	//$(childPoppup).addClass('login-dialog');
	$('.content-page').html(response);
	/*$(childPoppup).show();
    showModalBox(childPoppup);*/
	
}
function openPopup(url){
	
	var response = __doAjaxRequest(url, 'post', {}, false, 'html');
	var childPoppup = '.popup-dialog';
	/*$(childPoppup).addClass('login-dialog');*/
	$('.content-page').html(response);
	/*$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);*/
	
}

function openPopupWithResponse(response){
	
	
	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(response);
	$(childPoppup).show();
    showModalBox(childPoppup);
	
}


function showError(errorList) {
	
	
	var errMsg ='<button type="button" class="close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>'

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('.error-div').html(errMsg);
    $('.error-div').show();
}

function closeErrBox()
{
	 $('.error-div').hide();
}

	function formRedirect(result)
	{
      var obj ='.error-dialog';
      var loadingMsg = getLocalMessage('admin.login.loadingWait.msg');
      var loading	 ='<h3 class="text-center padding-bottom-10 text-black">' + loadingMsg + '</h3>' + '<img src=\'css/images/loader.gif\' title=\'Loading...\' alt=\'Loading...\' />';
	  $(obj).addClass('ajaxloader').addClass('login-loader').fadeIn('slow');
	  $(obj).html(loading);
	  showLoadingModalBoxWithoutClose(obj);
	
	   var form = document.createElement("form");
       form.setAttribute("method", "post");
       form.setAttribute("action", result);
       form.setAttribute("target", "_self");
       
       var csrf_token = $('meta[name=_csrf]').attr('content');
	   	var	my_tb_csrf=document.createElement('INPUT');
	   	my_tb_csrf.type='HIDDEN';
	   	my_tb_csrf.name= '_csrf';
	   	my_tb_csrf.value = csrf_token;
	   	form.appendChild(my_tb_csrf); 

       document.body.appendChild(form);
       form.submit();

}

	

$.preloadImages = function() {
	  for (var i = 0; i < arguments.length; i++) {
	    $("<img />").attr("src", arguments[i]);
	  }
	};
	$.preloadImages("css/images/loader.gif");
	
function getLogin(serviceURL){
		var data = "serviceURL=" + serviceURL;
		__doAjaxRequest("CitizenOnlineServices.html?storeServiceURL", 'post', data, false,'json');
		
		getCitizenLoginForm('N');
	}

function getRTILogin(serviceURL){
	var data = "serviceURL=" + serviceURL;
	__doAjaxRequest("CitizenHome.html?storeServiceURL", 'post', data, false,'json');
	
	getCitizenLoginForm('N');
}


function validateEmail(errorList) {
	if (isEmpty('emailId')) {
		errorList.push(getLocalMessage("Feedback.clientemailId"));
	} else {
		var empEmail = document.getElementById('emailId').value;
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

		if (reg.test(empEmail) == false) {
			errorList.push(getLocalMessage("citizen.valid.email.error"));

		}
	}
	return errorList;
}

function checkPasswordMaxLength(errorList,id){
	
	 var passVal = $('#'+id).val();
	if(passVal  !=undefined && passVal != '' && passVal.length > 16 ){
		errorList.push(getLocalMessage("admin.login.passMustContain.error"));
	}
	return errorList;
}

/* Globally access function start */
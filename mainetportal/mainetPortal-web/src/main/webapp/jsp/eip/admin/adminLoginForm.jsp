<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 

<script  src="js/eip/admin/adminLoginForm.js"></script>
<script  src="js/eip/admin/adminRegistrationForm.js"></script>
<script  src="js/eip/citizen/adminResetPasswordProcess.js"></script>
<script >
$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
function dispose() {
	$('.dialog').html('');
	$('.dialog').hide();
	disposeModalBox();
}


$(document).ready(function() 
{
	 setTimeout( function() { $( '#emploginname' ).focus() }, 500 );
	 $(document).keyup(function(e) 
	 {
	    	
     });
});

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});

</script>
<script>
(function($){
  $(document).ready(function(){		
	if(getCookie("accessibility")=='Y')
		{
		$("#captchaL").hide()
		}
	
	var passVal = $('.password-validation');
	passVal.password();
	passVal.parent('.input-group').css({'width':'100%'});
	$('.password-validation').next('.input-group-append').find('.btn').css({'border-radius':'0','z-index':'4','position':'absolute','top':'1px','right':'1px'});
  });
})(jQuery);

$(function() {
	$("#adminLoginForm").validate();
});

function resetAdminLoginFrm(){
	$('.error-div').hide();
	doRefreshLoginCaptcha();
}
</script>

<div class="row padding-40" id="CitizenService">	
<div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
	<div class="widget-header">
		<h2><spring:message code="eip.admin.login.FormHeader.IPRD"/></h2>
	</div>
	<div class="widget-content padding">
	<div id="basic-form">
	<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>

	<form:form id="adminLoginForm" name="adminLoginForm" method="POST" action="AdminLogin.html" autocomplete="off">
	<spring:message code='eip.captcha.placeholder' var="captchaP" />
	<spring:message code='eip.email.mobile.placeholer' var="mobileP" />
	<spring:message code="eip.password.placeholer" var="passP" />
	<input type="hidden" id ="uniqueKeyId" value ="${userSession.getCurrent().getUniqueKeyId()}">
	<div class="form-group">
<%-- <label for="emploginname"><i class="fa fa-user"></i> <spring:message code="eip.admin.login.loginName"/></label>
 --%>		
 	<c:set var="AdminEmailFieldLevelMsg" value="${command.getAppSession().getMessage('eip.admin.login.email.msg') }"></c:set>		
	<input aria-label="Login id" type="text" id="emploginname" class="form-control mandClassColor" onkeypress="return tryLogin(event)" name="adminEmployee.emploginname" 
	onkeypress="return tryLogin(event)" maxlength="50" autocomplete="off"   readonly   onfocus="this.removeAttribute('readonly');"  placeholder="${mobileP}" data-rule-required="true" data-msg-required="${AdminEmailFieldLevelMsg}"/>
		 </div>
	
			<div class="form-group">
				<%-- <label for="adminEmployee.emppassword">	<i class="fa fa-lock"></i>
				<spring:message code="eip.admin.login.password"/></label> --%>
				<c:set var="AdminPassFieldLevelMsg" value="${command.getAppSession().getMessage('eip.admin.login.pass.msg') }"></c:set>
				<input aria-label="password" type="password" id="adminEmployee.emppassword" class="form-control mandClassColor margin-bottom-0 password-validation" name="adminEmployee.emppassword" onkeypress="return tryLogin(event)" 
				maxlength="15" autocomplete="off"   readonly  onfocus="this.removeAttribute('readonly');" placeholder='${passP}' data-rule-required="true" data-msg-required="${AdminPassFieldLevelMsg}"/>
		 </div>
	
	<div class="form-group" id="captchaL">
					<div class=" margin-top-10" id="captchaLDiv">
						<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
						<img id="cimg" src="AdminRegistration.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
						<a href="#" onclick="doRefreshLoginCaptcha()" class="margin-left-20" tabindex="-1"><i
							class="fa fa-refresh fa-lg"><span class="hide">Refresh</span></i></a>
					</div>
					<div class="">
					<label for="captchaSessionLoginValue" class="hide">captchaP</label>
						<c:set var="AdminCaptchaFieldLevelMsg" value="${command.getAppSession().getMessage('eip.admin.login.captcha.msg') }"></c:set>
						<form:input path="captchaSessionLoginValue" maxlength="4"
							cssClass="form-control margin-top-20 hasNumber" placeholder='${captchaP}'
							onkeypress="return tryLogin(event)" autocomplete="off" data-rule-required="true" data-msg-required="${AdminCaptchaFieldLevelMsg}"/>
					</div>
				</div>
				<div class="clear"></div>
					
	
	<div class="row margin-top-10">
		<div class="col-lg-4 col-lg-offset-2 col-md-4 col-md-offset-2 col-sm-4 col-sm-offset-2 col-xs-6">
			<input type="button" class="btn btn-success btn-block" onclick="doAdminLogin(this);" value="<spring:message code="eip.commons.submitBT"/>" />
		</div>
		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6">	    
		<input type="reset" class="btn btn-warning btn-block" value="<spring:message code="eip.commons.resetBT"/>" onclick="resetAdminLoginFrm()"> 		
		</div> 
	</div>
	
	
	<div class="text-center margin-top-10">
			<%-- <a href="javascript:void(0);" onclick="getAdminForgotPassStep1()"  class="text-large text-success"><spring:message code="eip.admin.login.forgotPassword"/></a> | --%>
			<a href="javascript:void(0);" onclick="getAdminResetPassStepI();" class="text-large text-danger"><spring:message code="eip.admin.login.ResetPassword" /></a>
	</div>
</form:form>

</div>
</div>
</div>
</div>
</div>
</div>
<hr/>
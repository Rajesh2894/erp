<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%> 

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>
<script src="js/eip/admin/adminRegistrationForm.js"></script>
<script>
$(function(e)
{
	$("#oneTimePassword").focus();
});
</script>



<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>

<div class="row padding-40" id="CitizenService">
      <div class="col-md-4 col-md-offset-4">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
	<div class="widget-header">
	<h2 class="login-heading"><spring:message code="eip.citizen.otp.FormHeader" text="OTP Verification"/></h2>	
	</div>
<div class="widget-content padding padding-bottom-5">
	
	<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>

	<form:form id="verifyOTPForm" name="verifyOTPForm" method="POST" action="AdminForgotPassword.html" class="form-horizontal" autocomplete="on">
	   
		<div class="form-group">
			<c:if test="${command.mobileNumber ne ''}">	   
			   <label class="col-sm-4 control-label" for="mobileNumber"><spring:message code="eip.citizen.otp.mobileNo" text="Mobile No."/></label>
			   <div class="col-sm-8"><span class="form-control">${command.mobileNumber}</span><form:hidden path="mobileNumber" autocomplete="off"/></div>	
			</c:if>
		</div>
		<div class="form-group">
			<label class="col-sm-4 control-label" for="oneTimePassword"><spring:message text="Please enter OTP : " code="eip.citizen.otp.otpNo"/></label>
			<div class="col-sm-8"><form:password id="oneTimePassword" path="oneTimePassword" onkeypress="return admintryStep2(event)" cssClass="form-control" maxlength="10" autocomplete="off"/></div>	
		</div>	
		
		<div class="row">			
		  <div class="col-sm-6">		
			<input type="button" class="btn btn-success btn-block" onclick="doAdminOTPVerification();" value="<spring:message code="eip.commons.submitBT"/>" />
		  </div>
		   <div class="col-sm-6">	
			<input type="reset" class="btn btn-warning btn-block" onclick="{$('.error-div').html('');}"  value="<spring:message code="eip.commons.resetBT"/>"> 	
		   </div>
		</div>
			
		<div class="text-center">
			<a class="btn btn-link" href="javascript:void(0);" onclick="adminresendOneTimePassword()"><spring:message code="eip.citizen.otp.resendOTP" text="Resend OTP"/></a>
		</div>
		
</form:form>
</div>
</div>
</div>
</div>
</div>


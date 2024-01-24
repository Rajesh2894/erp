<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<script>
	$(function(e) {
		$("#oneTimePassword").focus();
		
	});
	
	$('.form-control').on( "copy cut paste drop", function() {
	     return false;
	 });	 
		
</script>



<div class="widget login">
	<div class="widget-header">
		<h2><strong><spring:message code="FormHeaderEmail" text="OTP Verification" /></strong></h2>
	</div>



	<div class="widget-content padding">
	
		<div class="error-div alert alert-danger" id="rest_error"></div>
		<form:form id="verifyOTPForm" name="verifyOTPForm" method="POST" action="AdminResetPassword.html" autocomplete="on">
<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<c:if test="${command.mobileNumber ne ''}">
				<div class="form-group"><label for="mobileNumber"><spring:message code="eip.citizen.otp.mobileNoEmail" text="Mobile No. or Email ID" />:</label>
				<form:input path="" value="${command.mobileNumber}" class="form-control" disabled="true" autocomplete="off"/><form:hidden path="mobileNumber" />
				</div>
			</c:if>

			<div class="form-group">
				<label for="oneTimePassword"><spring:message text="Please enter OTP :" code="eip.citizen.otp.otpNo" />:</label>
				<form:password path="oneTimePassword" id="oneTimePassword"  onkeypress="return admintryStepII(event)" cssClass="form-control mandClassColor" maxlength="10" autocomplete="new-password"/>
			</div>


			<div class="form-group">
				<a href="javascript:void(0);" onclick="resetPasswordResendOTP()"><spring:message code="eip.citizen.otp.resendOTP" text="Resend OTP" /></a>
			</div>

			<div class="row">
				<div class="col-sm-6">
					<input type="button" onclick="adminvalidateFormStepII();" class="btn btn-success btn-block" value="<spring:message text="Submit" code="eip.commons.submitBT"/>" />
				</div>
				<div class="col-sm-6">
					<input type="reset" class="btn btn-danger btn-block" onclick="{$('.error-div').html('');}"
						value="<spring:message code="eip.commons.resetBT"/>">
				</div>
			</div>
		</form:form>
	</div>
</div>
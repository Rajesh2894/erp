<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script>
	$('.form-control').bind("cut copy paste",function(e) {
	    e.preventDefault();
	});
	
	$(function() {
		$("#verifyOTPForm").validate();
	});
	function restOtpValidation(){
		$('.error-div').hide();
		$('#validationerrordiv').hide(); 
	}
</script>
<div class="row padding-40" id="CitizenService">
	<div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
		<div class="login-panel">
			<div class="widget margin-bottom-0">

				<div class="widget-header">
					<h2>
						<Strong><spring:message code="eip.citizen.otp.FormHeader"
								text="OTP Verification" /></Strong>
					</h2>
				</div>

				<div class="widget-content padding">

					<form:form id="verifyOTPForm" name="verifyOTPForm" method="POST"
						action="CitizenResetPassword.html" class="form-horizontal" autocomplete="off">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="error-div alert alert-danger alert-dismissable"
						role="alert" style="display: none"></div>
						<c:if test="${command.mobileNumber ne ''}">
							<div class="form-group">
								<label class="col-xs-4 control-label" for="mobileNumber"><spring:message
										code="eip.citizen.otp.mobileNo" text="Mobile No." /></label>
								
								
								<c:if test="${userSession.employee.emploginname eq 'NOUSER' }">
									<div class="col-xs-8">
										<span class="form-control">${command.mobileNumber}</span>
										<form:hidden path="mobileNumber" />
									</div>
								</c:if>
								<c:if
									test="${userSession.employee.emploginname ne 'NOUSER' and  userSession.employee.loggedIn eq 'Y' }">
									<div class="col-xs-8">
										<span class="form-control">${command.userSession.employee.empmobno}</span>
										<form:hidden path="mobileNumber"
											value="${command.userSession.employee.empmobno}" />
									</div>

								</c:if>
							</div>
						</c:if>
						<div class="form-group">
							<label class="col-xs-4 control-label" for="oneTimePassword"><spring:message
									text="Please enter OTP" code="eip.citizen.otp" /></label>
							<div class="col-xs-8">
							<c:set var="otpNo" value="${command.getAppSession().getMessage('eip.citizen.otp.otpNo') }" />
								<form:password id="oneTimePassword" path="oneTimePassword"
									onkeypress="return tryStepII(event)" maxlength="10"
									cssClass="form-control" data-rule-required="true" data-msg-required="${otpNo}"/>
							</div>
						</div>

						<div class="text-center">
						<div class="col-lg-4 col-md-4 col-xs-4">
							<input type="button" class="btn btn-success"
								onclick="validateFormStepII();"
								value="<spring:message text="Submit" code="eip.commons.submitBT"/>" />
						</div>
						<div class="col-lg-4 col-md-4 col-xs-4">
							<input type="reset" class="btn btn-warning"
								onclick="restOtpValidation()"
								value="<spring:message  code="eip.commons.resetBT"/>">
						</div>
						<div class="col-lg-4 col-md-4 col-xs-4">
							<input type="button" class="btn btn-danger btn-block" value="<spring:message code="bckBtn" text="Back" />" 
							onclick="getCitizenResetPassStepI();">
						</div>
						</div>

						<div class="text-center">
							<a class="btn btn-link" href="javascript:void(0);"
								onclick="resendOTPResetPassword()"><spring:message
									code="eip.citizen.otp.resendOTP" text="Resend OTP" /></a>
						</div>

					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>

<hr/>
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

$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
	$(document).ready(function() {
		$("#oneTimePassword").focus();
	});
</script>




<div class="row padding-40">
	<div class="col-md-4 col-md-offset-4">
		<div class="login-panel">
			<div class="widget margin-bottom-0">

				
				<div class="widget-header">
					<h2>
						<spring:message code="eip.citizen.otp.FormHeader"
							text="OTP Verification" />
					</h2>
				</div>
				<div class="widget-content padding">
					<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>

					<form:form id="verifyOTPForm" cssClass="form-horizontal"
						name="verifyOTPForm" method="POST"
						action="AgencyResetPassword.html" autocomplete="off">
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
										<form:hidden path="mobileNumber" />
									</div>
									<form:hidden path="mobileNumber"
										value="${command.userSession.employee.empmobno}" />
								</c:if>
							</div>
						</c:if>
						<div class="form-group">
							<label class="col-xs-4 control-label" for="oneTimePassword"><spring:message
									text="Please enter OTP" code="eip.citizen.otp.otpNo" /></label>
							<div class="col-xs-8">
								<form:input path="oneTimePassword" id="oneTimePassword"
									onkeypress="return tryStepII(event)" maxlength="10"
									cssClass="form-control" />
							</div>
						</div>



						<div class="row">
							<div class="col-lg-6 col-md-6 col-xs-6">
								<input type="button" class="btn btn-success btn-block"
									onclick="getAgencyResetPassStepIII();"
									value="<spring:message text="Submit" code="eip.commons.submitBT"/>" />
							</div>
							<div class="col-lg-6 col-md-6 col-xs-6">
								<input type="reset" class="btn btn-warning btn-block"
									onclick="{$('.error-div').html('');}"
									value="<spring:message  code="eip.commons.resetBT"/>">
							</div>
						</div>
						<div class="text-center">
							<a class="btn btn-link" href="javascript:void(0);"
								onclick="resendAgencyOTPResetPassword()" tabindex="2"><spring:message
									code="eip.citizen.otp.resendOTP" text="Resend OTP" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
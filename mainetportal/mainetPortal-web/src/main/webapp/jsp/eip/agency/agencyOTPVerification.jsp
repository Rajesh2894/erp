<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script
	src="js/eip/agency/agencyForgotPasswordProcess.js"></script>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<script>
	
	$('.form-control').bind("cut copy paste", function(e) {
		e.preventDefault();
	});
	$(function(e) {
		$("#otpPassword").focus();
	});
</script>

<div class="row padding-40">
	<div class="col-md-4 col-md-offset-4">
		<div class="login-panel">
			<div class="widget margin-bottom-0">

				<div class="widget-header">
					<h2>
						<strong><spring:message code=""
								text="Agency Reset Password" /></strong>
					</h2>
				</div>

				<div class="error-div alert alert-danger alert-dismissable"
					role="alert" style="display: none">
					<strong> </strong>
				</div>
				<div class="widget-content padding">
					<div id="basic-form">
						<form:form id="agencyOTPVerificationForm"
							name="agencyOTPVerificationForm" method="POST"
							action="AgencyOTPVerification.html" class="form-horizontal"
							role="form" autocomplete="off">
							<div class="form-group">
								<label for="mobileNumber" class="col-sm-4 control-label">
									<spring:message code="eip.citizen.otp.mobileNo" />
								</label>
								<div class="col-sm-8">
									<c:choose>
										<c:when test="${command.mobileNumber ne ''}">
											<span class="form-control">${command.mobileNumber}</span>
											<form:hidden path="mobileNumber" />
										</c:when>
										<c:otherwise>
											<form:input path="mobileNumber" maxlength="10" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="form-group">
								<label for="otpPassword" class="col-sm-4 control-label">
									<spring:message code="eip.citizen.otp.otpNo" />
								</label>
								<div class="col-sm-8">
									<form:password id="otpPassword" path="otpPassword"
										cssClass="form-control mandClassColor" maxlength="10" />
								</div>
							</div>
							<div class="text-center">
							<a href="javascript:void(0);" onclick="resendOTP()"><spring:message
									code="eip.citizen.otp.resendOTP" /></a>
							</div>

					<div class="text-center padding-bottom-30">
						<div class="col-sm-6">
							<input type="button" class="btn btn-primary btn-block"
								onclick="doAgencyOTPVerification();"
								value="<spring:message code="eip.commons.submitBT"/>" />
						</div>
						<div class="col-sm-6">
							<input type="reset" class="btn btn-danger btn-block"
								value="<spring:message code="eip.commons.reset"/>"
								onclick="{$('.error-div').html('');}">
						</div>
					</div>

					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
</div>


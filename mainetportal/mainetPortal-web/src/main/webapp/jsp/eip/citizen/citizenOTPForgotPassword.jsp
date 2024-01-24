<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script>
	//cut copy paste Disable
	$('.form-control').bind("cut copy paste",function(e) {
	    e.preventDefault();
	});
</script>


<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>
<div class="row padding-40" id="CitizenService">
	<div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
		<div class="login-panel">
			<div class="widget margin-bottom-0">

				<div class="widget-header">
					<h2>
						<strong><spring:message code="eip.citizen.otp.FormHeader"
								text="OTP Verification" /></strong>
					</h2>
				</div>
				<div class="widget-content padding">
				<div class="error-div alert alert-danger alert-dismissable"
					role="alert" style="display: none"></div>
<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<input type="hidden" id="hiddenerrid"
					value="<spring:message code="eip.citizen.forgotPassword.invalidOTP"/>">
				
					<form:form id="verifyOTPForm" name="verifyOTPForm" method="POST"
						action="CitizenForgotPassword.html" class="form-horizontal" autocomplete="on">
						<c:if test="${command.mobileNumber ne ''}">
							<div class="form-group">
									<%-- <label class="col-xs-4 control-label" for="mobileNumber"><spring:message
											code="eip.citizen.otp.mobileNo" text="Mobile No." /></label> --%>
								<div class="col-xs-12"><span class="form-control">${command.mobileNumber}</span><form:hidden
										path="mobileNumber" />
								</div>
							</div>
						</c:if>
						<div class="form-group">
								<%-- <label class="col-xs-4 control-label" for="oneTimePassword"><spring:message
										text="Please enter OTP" code="eip.citizen.otp.otpNo" /></label> --%>
							<div class="col-xs-12">
								<form:password path="oneTimePassword" aria-label="Enter OTP Password"
									cssClass="form-control mandClassColor" id="oneTimePassword"
									onkeypress="return tryStep2(event)" maxlength="10" autocomplete="off"/>
								<form:hidden path="mobileNumberType" id="mobileNumberType" />
							</div>
						</div>

						<div class="row">
							<div class="col-lg-4 col-lg-offset-2">
								<input type="button" id="otpvalid" value="<spring:message code="eip.commons.submitBT"/>"
									class="btn btn-success btn-block"
									onclick="validateCitizenFormStep2(this,'${command.mobileNumberType}')">
							</div>
							<div class="col-lg-4">
								<input type="reset" onclick="{$('.error-div').hide();}"
									class="btn btn-warning btn-block"
									value="<spring:message code="eip.commons.resetBT"/>">
							</div>

						</div>
						<div class="text-center">
							<p><a class="btn btn-link text-success" href="javascript:void(0);" onclick="resendCitizenOneTimePassword('${command.mobileNumberType}')"><spring:message text="Please enter OTP" code="eip.citizen.otp.resendOTP"/></a></p>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<hr/>
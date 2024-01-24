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
	$(function(e) {
		$("#oneTimePassword").focus();
	});
	function restOtpValidation(){
		$('.error-div').hide();
		$('#validationerrordiv').hide(); 
	}
</script>
<div class="row padding-40" id="CitizenService">
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
				
					
			<form:form id="verifyOTPForm" name="verifyOTPForm" method="POST" action="AdminResetPassword.html" cssClass="form-horizontal" autocomplete="on">
<jsp:include page="/jsp/tiles/validationerror.jsp" />
<div class="error-div alert alert-danger alert-dismissable"
						role="alert" style="display: none"></div>
			<c:if test="${command.mobileNumber ne ''}">
			<div class="form-group">
              <label class="col-sm-4 control-label" for="mobileNumber"><spring:message code="eip.citizen.otp.mobileNo" text="Mobile No." /></label>
              <div class="col-sm-8"><span class="form-control">${command.mobileNumber}</span><form:hidden path="mobileNumber" /></div>             
            </div>
            </c:if>
            <div class="form-group">
              <label class="col-sm-4 control-label" for="oneTimePassword"><spring:message text="Please enter OTP" code="eip.citizen.otp.otpNo" /></label>
              <div class="col-sm-8"><form:password path="oneTimePassword" id="oneTimePassword" onkeypress="return admintryStepII(event)" cssClass="form-control" maxlength="10" autocomplete="off"/></div>             
            </div>
						
			<div class="row">
				<div class="col-lg-4 col-md-4 col-xs-4">
					<input type="button" class="btn btn-success btn-block" onclick="adminvalidateFormStepII();" value="<spring:message text="Submit" code="eip.commons.submitBT"/>" />
				</div>
				<div class="col-lg-4 col-md-4 col-xs-4">
					<input type="reset" class="btn btn-warning btn-block" onclick="restOtpValidation()" value="<spring:message code="eip.commons.resetBT"/>">
				</div>
				<div class="col-lg-4 col-md-4 col-xs-4">
			 	<input type="button" class="btn btn-danger btn-block" value="<spring:message code="bckBtn" text="Back" />" onclick="getAdminResetPassStepI();"> 
			    </div>
			</div>
			
			<div class="text-center">
				<a href="javascript:void(0);" onclick="resetPasswordResendOTP()"><spring:message code="eip.citizen.otp.resendOTP" text="Resend OTP" /></a>
			</div>
						
			</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
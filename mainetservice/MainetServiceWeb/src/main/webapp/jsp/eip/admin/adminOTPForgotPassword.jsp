<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>

<script>
$(function(e)
{
	$("#oneTimePassword").focus();
});
$( '.form-control' ).on( "copy cut paste drop", function() {
    return false;
});
</script>

<div class="widget login">
	<div class="widget-header"><h2><strong><spring:message code="eip.citizen.otp.FormHeader" text="OTP Verification"/></strong>	</h2></div>
<div class="widget-content padding">




<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>
 	
	<div class="error-div alert alert-danger"></div> 

	<form:form id="verifyOTPForm" name="verifyOTPForm" method="POST" action="AdminForgotPassword.html" autocomplete="on">
	   
		<c:if test="${command.mobileNumber ne ''}">	   
		<div class="form-group">
		<label for="mobileNumber"><spring:message code="eip.citizen.otp.mobileNo" text="Mobile No."/> :</label>
		<form:input path="" class="form-control" value="${command.mobileNumber}" disabled="true" autocomplete="off"/><form:hidden path="mobileNumber"/>
		</div>	
		</c:if>
		
					<div class="form-group">
					<label for="oneTimePassword"><spring:message text="Please enter OTP : " code="eip.citizen.otp.otpNo"/> :</label>
					<form:password id="oneTimePassword" path="oneTimePassword" onkeypress="return admintryStep2(event)" cssClass="form-control mandClassColor" maxlength="10" autocomplete="off"/>
					</div>	
	
		<div class="form-group">
				<a href="javascript:void(0);" onclick="adminresendOneTimePassword()"><spring:message code="eip.citizen.otp.resendOTP" text="Resend OTP"/></a>
		</div>			
		
			<div class="row padding_top_5">
			<div class="col-sm-6"><input type="button" class="btn btn-success btn-block" onclick="getAdminForgotPassStep3();" value="<spring:message code="eip.commons.submitBT"/>" /></div>
			<div class="col-sm-6"><input type="reset" class="btn btn-danger btn-block" onclick="{$('.error-div').html('');}"  value="<spring:message code="eip.commons.resetBT"/>"></div> 	
			</div>
</form:form>
</div>
</div>
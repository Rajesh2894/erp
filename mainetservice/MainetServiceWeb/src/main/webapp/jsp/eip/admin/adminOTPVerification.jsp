<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%> 
<script type="text/javascript" src="js/eip/admin/adminRegistrationForm.js"></script>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>
<script>
$(function(e)
{
	$("#otpPassword").focus();
});
$( '.form-control' ).on( "copy cut paste drop", function() {
    return false;
});
</script>



<div class="widget login">
	<div class="widget-header"><h2><strong><spring:message code="eip.citizen.otp.FormHeader"/></strong>	</h2></div>
<div class="widget-content padding">

 
<div class="popup-form-div">
	
	<div class="error-div"></div>

	<form:form id="adminOTPVerificationForm" name="adminOTPVerificationForm" method="POST" action="AdminOTPVerification.html" autocomplete="on" >
	   
	   <div class="form-group">
			<label for="mobileNumber"><spring:message code="eip.citizen.otp.mobileNo"/> :</label>
			<c:choose>
			<c:when test="${command.mobileNumber ne ''}"><form:input path="" value="${command.mobileNumber}" disabled="true" class="form-control" />
			<form:hidden path="mobileNumber"/>
			</c:when>
			<c:otherwise>
			<form:input path="mobileNumber" class="form-control" maxlength="10" autocomplete="off"/>
			</c:otherwise>
			</c:choose>
		</div>
		<div class="form-group">
		<label for="otpPassword"><spring:message code="eip.citizen.otp.otpNo"/> :</label>
		<form:password id="otpPassword" path="otpPassword" cssClass="mandClassColor form-control" maxlength="10" autocomplete="off"/>
		</div>
							
		
		<div class="form-group"><a href="javascript:void(0);"  onclick="resendAdminOTP()"><spring:message code="eip.citizen.otp.resendOTP"/></a></div>			
		
		<div class="row">
		<div class="col-sm-6">
		<input type="button" class="btn btn-success btn-block"  onclick="doAdminOTPVerification();" value="<spring:message code="eip.commons.submitBT"/>" />
		</div>
		<div class="col-sm-6">
		<input type="reset"  class="btn btn-danger btn-block" value="<spring:message code="eip.commons.reset"/>" onclick="{$('.error-div').html('');}"> 
		</div>
		</div>
</form:form>
</div>
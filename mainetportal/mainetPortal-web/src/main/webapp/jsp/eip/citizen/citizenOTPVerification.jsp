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
<script>
$(document).ready(function() {
	$('#resettButon').on('click',function() {
						$('#citizenOTPVerificationForm').find('input:text').val('');
						$('.error-div').hide();
	})
});
$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
$(function(e)
{
	$("#otpPassword").focus();
});
</script>
<div class="row padding-40" id="CitizenService">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
  <div class="widget-header">
    <h2><spring:message code="eip.citizen.otp.FormHeader"/></h2>
  </div>
  <div class="widget-content padding">
  <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"><strong> </strong></div>
    <div id="basic-form">
      <form:form id="citizenOTPVerificationForm" name="citizenOTPVerificationForm" method="POST" action="CitizenOTPVerification.html" class="form" role="form" autocomplete="off">
      <spring:message code="eip.citizen.otp.otpNo" var="otp"/>
        <div class="form-group">
        
					<%-- <label for="mobileNumber">
						<spring:message code="eip.citizen.otp.mobileNo"/> :
					</label> --%>
						<c:choose>
							<c:when test="${command.mobileNumber ne ''}">
							<span class="form-control">	${command.mobileNumber} </span>
								<form:hidden path="mobileNumber"/>
							</c:when>
							<c:otherwise>
								<form:input path="mobileNumber" maxlength="10" />
							</c:otherwise>
						</c:choose>
				</div>
				<div class="form-group">	
<%-- 				<label for="otpPassword"><spring:message code="eip.citizen.otp.otpNo"/> :</label>
 --%>					<form:password id="otpPassword"  aria-label="Enter OTP Password" path="otpPassword" cssClass="form-control" maxlength="10" placeholder="${otp}"/>
				</div>	
				
		<div class="row">
		  <div class="col-xs-4 col-xs-offset-2"><input type="button"  class="btn btn-success btn-block" onclick="doCitizenOTPVerification();" value="<spring:message code="eip.commons.submitBT"/>" /></div>
		  <div class="col-xs-4"><input type="button"  class="btn btn-warning btn-block" id="resettButon"  value="<spring:message  code="eip.commons.reset"/>" > </div>
        </div> 
        
        <div class="text-center margin-top-10">
			<p class="text-success">	<a href="javascript:void(0);"  onclick="resendOTP()" class="text-success"><spring:message code="eip.citizen.otp.resendOTP"/></a></p>
		</div>
        </form:form>
    </div>
  </div>
</div>
</div>
</div>
</div>
<hr/>
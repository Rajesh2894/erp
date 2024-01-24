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
$(function(e)
{
	$("#newPassword").focus();
});
</script>
<div class="row padding-40">
      <div class="col-md-4 col-md-offset-4">
        <div class="login-panel">
          <div class="widget margin-bottom-0">

<div class="widget">

  <div class="widget-header"><h2 class="login-heading"><spring:message code="eip.citizen.set.FormHeader"/></h2>
  </div>
<div class="widget-content padding">

	<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>

	<form:form id="setForgotPasswordForm" name="setForgotPasswordForm" method="POST" action="AgencySetForgotPasswordForm.html" autocomplete="off">
		  
		   	<p class="float nopadding_right">
					<label for="newPassword">
						<spring:message text="Enter Password" code="eip.citizen.set.newPassword"/>
					</label>
				</p>
		<p class="float">
						<form:password path="newPassword" id="newPassword" cssClass="form-control" onkeypress="return tryStep3(event)" maxlength="15" tabindex="1"/>
					</p>		
				
			<p class="float">
					<label for="reEnteredPassword">
						<spring:message text="Re-Enter Password" code="eip.citizen.set.reEnteredPassword"/>
					</label>
					</p>
					
				<p class="float">
						<input type="password" class="form-control" onkeypress="return tryStep3(event)" name="reEnteredPassword" id="reEnteredPassword"  maxlength="15" tabindex="2"/>
						<form:hidden path="mobileNumber"/>
					</p>
			<div class="clear"></div>
			<div class="row padding-top-10">
			<div  class="col-lg-6">
				<input type="button"  class="btn btn-success form-control" onclick="getAgencyForgotPassStep4();"	value="<spring:message text="Submit" code="eip.commons.submitBT"/>" />
			</div>
			<div  class="col-lg-6">
				<input type="reset"   class="btn btn-warning  form-control"  onclick="{$('.error-div').html('');}" value="<spring:message text="Submit" code="eip.commons.resetBT"/>" /> 
			
			</div>
			</div>
	</form:form>
</div>
</div>
</div>
</div></div>
</div>

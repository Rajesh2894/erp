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
$( document ).ready(function()
{
	$("#newPassword").focus();
});
</script>


<div class="row padding-40">
      <div class="col-md-4 col-md-offset-4">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
   <div class="widget-header">
	<h2><spring:message code="eip.citizen.set.FormHeader"/></h2>	
   </div>


<div class="widget-content padding">
	<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>

	<form:form id="resetPasswordForm" name="resetPasswordForm" method="POST" action="AgencyResetPassword.html" autocomplete="off">
	    <div class="form-group">
					<label for="newPassword">
						<spring:message text="Enter New Password"  code="eip.commons.enter.newPassword" />
					</label>
		
						<form:password path="newPassword" id="newPassword" onkeypress="return tryStepIII(event)" maxlength="15" cssClass="form-control"  tabindex="1"/>
		</div>			
			<div class="clear"></div>
		    <div class="form-group">
					<label for="reEnteredPassword">
						<spring:message text="Re-Enter Password" code="eip.commons.re-enter.newPassword"/>
					</label>
					
						<input type="password" onkeypress="return tryStepIII(event)" name="reEnteredPassword" id="reEnteredPassword" class="form-control" maxlength="15"  tabindex="2"/>
						<form:hidden path="mobileNumber"/>
					
					
				</div>
				<div class="clear"></div>
			 <div class="row">
			 <div class="col-lg-6">
				<input type="button" class="btn btn-success btn-block" onclick="getAgencyResetPassStepIV();"	value="<spring:message text="Submit" code="eip.commons.submitBT"/>" tabindex="3"/>
			 </div>
			 <div class="col-lg-6">	
			 	<input type="reset"  class="btn btn-warning btn-block"  onclick="{$('.error-div').html('');}"  value="<spring:message text="Submit" code="eip.commons.resetBT"/>"  tabindex="4" > 	
			 </div>
			</div>
	</form:form>
	</div>
</div>
</div>
</div>
</div>
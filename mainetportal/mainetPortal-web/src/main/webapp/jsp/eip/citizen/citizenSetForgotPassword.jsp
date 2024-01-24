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

<div class="row padding-40" id="CitizenSerivce">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">

<div class="widget margin-bottom-0">

  <div class="widget-header"><h2><spring:message code="eip.citizen.set.FormHeader"/></h2></div>
	
 <div class="widget-content padding">
 
 <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
 <spring:message  code="eip.citizen.set.newPassword" var="newPass"/>
 <spring:message  code="eip.citizen.set.reEnteredPassword" var="newPass1"/>
	<form:form id="setForgotPasswordForm" name="setForgotPasswordForm" method="POST" action="CitizenSetForgotPasswordForm.html" autocomplete="off">
		   		<div class="form-group">
					<%-- <label for="newPassword">
						<spring:message text="Enter Password" code="eip.citizen.set.newPassword"/>
					</label> --%>
						<form:password path="newPassword" aria-label="Enter Password" id="newPassword" cssClass="form-control mandClassColor" onkeypress="return tryStep3(event)" maxlength="15" Placeholder="${newPass}"/> 
				</div>
			
			
				<div class="form-group">
					<%-- <label for="reEnteredPassword">
						<spring:message text="Re-Enter Password" code="eip.citizen.set.reEnteredPassword"/>
					</label> --%>
						<input type="password" aria-label="Re-enter Password"class="form-control mandClassColor" onkeypress="return tryStep3(event)" name="reEnteredPassword" id="reEnteredPassword"  maxlength="15" Placeholder="${newPass1}"/> 
						<form:hidden path="mobileNumber"/>
				</div>
			
			<div class="row clear padding_top_10">
			
				<div class="col-lg-4 col-lg-offset-2" ><input type="button" class="btn btn-success form-control" onclick="getCitizenForgotPassStep4(this);"	value="<spring:message text="Submit" code="eip.commons.submitBT"/>"  tabindex="3"/></div>
				
				 <div class="col-lg-4" ><input type="reset" class="btn btn-warning  form-control" onclick="{$('.error-div').hide();}" value="<spring:message text="Reset" code="eip.commons.resetBT"/>" tabindex="4"></div>
				
			</div>
	</form:form>
</div>
</div>
</div>
</div>
</div><hr/>
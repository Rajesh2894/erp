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

$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
$(function(e)
{
	$("#newPassword").focus();
});
</script>


<div class="row padding-40" id="CitizenService">
      <div class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">
        <div class="login-panel">

<div class="widget margin-bottom-0">

  <div class="widget-header"><h2><spring:message code="eip.citizen.set.FormHeader"/></h2></div>
	
 <div class="widget-content padding">
 
 <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>


<form:form id="setForgotPasswordForm" name="setForgotPasswordForm" method="POST" action="AdminSetForgotPasswordForm.html" class="form-horizontal" autocomplete="off">

	<div class="form-group">
	  <label class="col-sm-12" for="newPassword"><spring:message text="Enter Password" code="eip.admin.set.newPassword"/></label>
	  	<div class="col-sm-12">
	  	<form:password id="newPassword" path="newPassword" onkeypress="return admintryStep3(event)" maxlength="15" cssClass="form-control mandClassColor"/>    
	</div></div>
	
	<div class="form-group">
	  <label class="col-sm-12" for="reEnteredPassword"><spring:message text="Re-Enter Password"  code="eip.admin.set.reEnteredPassword"/></label>
	  <div class="col-sm-12">	<input type="password" onkeypress="return admintryStep3(event)" name="reEnteredPassword" id="reEnteredPassword" class="form-control mandClassColor" maxlength="15" /></div>
	  	<form:hidden path="mobileNumber"/>	    
	</div>
	
	<div class="row clear padding_top_10">
			
				<div class="col-lg-6" ><input type="button" class="btn btn-success form-control" onclick="getAdminForgotPassStep4();"	value="<spring:message text="Submit" code="eip.commons.submitBT"/>"  tabindex="3"/></div>
				
				 <div class="col-lg-6" ><input type="reset" class="btn btn-warning  form-control" onclick="{$('.error-div').html('');}" value="<spring:message text="Reset" code="eip.commons.resetBT"/>" tabindex="4"></div>
				
			</div>
</form:form>
</div>
</div>
</div>
</div>
</div>
<hr/>
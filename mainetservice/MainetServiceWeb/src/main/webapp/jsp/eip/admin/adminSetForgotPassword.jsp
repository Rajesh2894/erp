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
$(function(e)
{
	$("#newPassword").focus();
});
$( '.form-control' ).on( "copy cut paste drop", function() {
    return false;
});
</script>



<%-- <h1 class="login-heading"><spring:message code="eip.admin.set.FormHeader" text="Set Password"/></h1>
<div class="popup-form-div">

	<div class="error-div"></div> --%>
<div class="widget login">
<div class="widget-header"><h2><strong><spring:message code="eip.admin.set.FormHeader" text="Set Password" /></strong></h2></div>
	<div class="widget-content padding">
<div class="error-div alert alert-danger"></div>

<form:form id="setForgotPasswordForm" name="setForgotPasswordForm" method="POST" action="AdminSetForgotPasswordForm.html" autocomplete="on">

<div class="form-group"><label for="newPassword"><spring:message text="Enter Password" code="eip.admin.set.newPassword"/>:</label>		
<form:password id="newPassword" path="newPassword" onkeypress="return admintryStep3(event)" maxlength="15" cssClass="form-control mandClassColor" autocomplete="off"/></div>

<div class="form-group"><label for="reEnteredPassword"><spring:message text="Re-Enter Password"  code="eip.admin.set.reEnteredPassword"/>:</label>
<input type="password" onkeypress="return admintryStep3(event)" name="reEnteredPassword" id="reEnteredPassword" class="form-control mandClassColor" maxlength="15" autocomplete="off"/>
</div>
<form:hidden path="mobileNumber"/>
<div class="row">
<div class="col-sm-6">
<input type="button" onclick="getAdminForgotPassStep4();" class="btn btn-success btn-block" value="<spring:message text="Submit" code="eip.commons.submitBT"/>"  />
</div>
<div class="col-sm-6">
<input type="reset" class="btn btn-danger btn-block" onclick="{$('.error-div').html('');}" > 
</div>
</div>
</form:form>
</div>
</div>
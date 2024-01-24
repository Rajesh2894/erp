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
	$("#enterPassword").focus();
});
</script>

<div class="widget login">
<div class="widget-header"><h2><strong><spring:message code="eip.citizen.set.FormHeader"/></strong></h2></div>
	<div class="widget-content padding">
	<div class="error-div alert alert-danger"></div>
	<form:form id="adminSetPasswordForm" name="adminSetPasswordForm" method="POST" action="AdminSetPassword.html" autocomplete="on">
	<div class="form-group"><label for="newPassword"><spring:message code="eip.citizen.set.newPassword"/>:</label>
	<form:password path="newPassword" id="enterPassword" cssClass="mandClassColor form-control" maxlength="15" autocomplete="off"/></div>
	<div class="form-group"><label for="reEnteredPassword"><spring:message code="eip.citizen.set.reEnteredPassword"/>:</label>
	<input type="password" class="mandClassColor form-control" name="reEnteredPassword" id="reEnteredPassword"  maxlength="15" autocomplete="off"/></div>
 	<div class="row">
	<div class="col-sm-6">
	<input type="button"  onclick="doAdminSetPassword();" class="btn btn-success btn-block"	value="<spring:message code="eip.commons.submitBT"/>" />
	</div>
	<div class="col-sm-6">
	<input type="reset" class="btn btn-danger btn-block"  value="<spring:message code="eip.commons.reset"/>" onclick="{$('.error-div').html('');}"> 	
	</div>
	</div>
	</form:form>
</div>
</div>
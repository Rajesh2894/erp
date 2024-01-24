<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%> 
<script src="js/eip/admin/adminRegistrationForm.js"></script>
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
	$("#enterPassword").focus();
});
</script>

<h1 class="login-heading"><spring:message code="eip.citizen.set.FormHeader"/></h1>
<div class="popup-form-div">
	<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>

	<form:form id="adminSetPasswordForm" name="adminSetPasswordForm" method="POST" action="AdminSetPassword.html" class="form-horizontal" autocomplete="on">
	<div class="form-group">
        <label class="col-sm-4 control-label" for="newPassword"><spring:message code="eip.citizen.set.newPassword"/></label>
        <div class="col-sm-8">
        	<form:password path="newPassword" id="enterPassword" cssClass="form-control" maxlength="15" autocomplete="off"/>               
        </div>   
      </div>
      
      <div class="form-group">
        <label class="col-sm-4 control-label" for="reEnteredPassword"><spring:message code="eip.citizen.set.reEnteredPassword"/></label>
        <div class="col-sm-8">
        	 <input type="password" class="form-control" name="reEnteredPassword" id="reEnteredPassword"  maxlength="15" autocomplete="off" />              
        </div>   
      </div>
        
		
	<div class="text-center">
	<input type="button" class="btn btn-success"  onclick="doAdminSetPassword();" value="<spring:message code="eip.commons.submitBT"/>" />
	<input type="reset" class="btn btn-warning" value="<spring:message code="eip.commons.reset"/>" onclick="{$('.error-div').html('');}"> 	
	</div>
	</form:form>
</div>
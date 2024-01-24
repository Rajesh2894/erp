<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%> 
<script type="text/javascript" src="js/eip/admin/adminUpdatePersonalDtlsProcess.js"></script>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>
<script>
$( '.form-control' ).on( "copy cut paste drop", function() {
    return false;
});
</script>



<div class="widget login">
	<div class="widget-header"><h2><strong><spring:message code="eip.citizen.upd.FormHeader"/></strong>	</h2></div>
<div class="widget-content padding">

 
<div class="popup-form-div">
	
	<div class="error-div"></div>

	<form:form id="adminUpdatePersonalDtlsForm" name="adminUpdatePersonalDtlsForm" method="POST" action="AdminUpdatePersonalDtls.html" autocomplete="on" >
	   
	   <div class="form-group">
			<label for="mobileNumber" class="control-label required-control"><spring:message code="eip.citizen.otp.mobileNo"/> :</label>
			<form:input path="mobileNumber" class="form-control" maxlength="10" autocomplete="off"/>
		</div>
		<div class="form-group">
		<label for="empFName"  class="control-label required-control"><spring:message code="eip.citizen.upd.empFName"/> :</label>
		<form:input id="empFName" path="empFName" cssClass="form-control hasSpecialChara required-control" autocomplete="off"/>
		</div>
		<div class="form-group">
		<label for="empMName"><spring:message code="eip.citizen.upd.empMName"/> :</label>
		<form:input id="empMName" path="empMName" cssClass="form-control hasSpecialChara" autocomplete="off"/>
		</div>					
		<div class="form-group">
		<label for="empLName" class="control-label required-control"><spring:message code="eip.citizen.upd.empLName"/> :</label>
		<form:input id="empLName" path="empLName" cssClass="form-control hasSpecialChara" autocomplete="off"/>
		</div>
		
		<div class="row">
		<div class="col-sm-4">
		<input type="button" class="btn btn-success btn-block"  onclick="validateAndupdatePersonalDtls();" value="<spring:message code="eip.commons.submitBT"/>" />
		</div>
		<div class="col-sm-4">
		<input type="reset"  class="btn btn-danger btn-block" value="<spring:message code="eip.commons.reset"/>" onclick="{$('.error-div').html('');}"> 
		</div>
		<div class="col-sm-4">
		<input type="reset"  class="btn btn-success btn-block" value="<spring:message code="eip.commons.skip"/>" onclick="skipToResetPassword();"> 
		</div>
		</div>
</form:form>
</div>
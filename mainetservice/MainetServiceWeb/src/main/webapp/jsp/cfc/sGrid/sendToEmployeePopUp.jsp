<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% response.setContentType("text/html; charset=utf-8");%>

<%@ taglib	prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib	prefix="form"		uri="http://www.springframework.org/tags/form"%>
<%@ taglib	prefix="spring"		uri="http://www.springframework.org/tags"%>
<%@ taglib	prefix="apptags" 	tagdir="/WEB-INF/tags"%>


<script type="text/javascript">
		
$(document).ready(function() 
		{
			$('.error-div').hide();
		});
$("#sendtoreset").click(function(e)
		{
			$('.error-div').hide();
		});
	
</script>

<div class="popup-dialog">
<h1 class="login-heading"><spring:message code="cfc.selEmpl" text="Select Employee"/></h1>
	<div class="mand-label"><spring:message code="MandatoryMsg" text="MandatoryMsg"/></div>

	<form:form 	method="post" class="form" action="ScrutinyGridForm.html" name="frmSendToEmployee" id="frmSendToEmployee" required="true">
<%-- 	 <jsp:include page="/jsp/tiles/validationerror.jsp"/> 
 --%>	
 
<div class="error-div"></div>
	
	<form:hidden path="entity.applicationId" value="${command.sGridDTO.applicationId}"/>
	<form:hidden path="entity.saApplicationId" value="${command.sGridDTO.saApplicationId}"/>

	<div class="form-elements margin_top_10 clear" >
		<label for=""><spring:message code="cfc.empName" text="Employee Name"/> :</label>
		
		<form:select path="entity.id" cssClass="mandClassColor">
        <form:option value="0"><spring:message code="cfc.selEmpl" text="Select Employee"/></form:option>
		<c:forEach items="${command.employees}" var="emp">
        <form:option value="${emp.empId}">${emp.fullName}</form:option>
		</c:forEach>
		</form:select><span class="mand">*</span></div>
		
		<div class="btn_fld clear padding_top_10">
			<input type="button" class="css_btn" onclick="processForSendTo(this,'processForSendTo');" name="Close" value="<spring:message code='tp.save' />" />
			<input type="reset" value="<spring:message code='tp.reset' />" class="css_btn" id="sendtoreset"/>
		</div>
	</form:form>
</div>

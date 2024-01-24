<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% response.setContentType("text/html; charset=utf-8");%>
<%@ taglib	prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib	prefix="form"		uri="http://www.springframework.org/tags/form"%>
<%@ taglib	prefix="spring"		uri="http://www.springframework.org/tags"%>
<%@ taglib	prefix="apptags" 	tagdir="/WEB-INF/tags"%>

<div class="popup-dialog">
	<h1 class="login-heading"><spring:message code="" text="Send To Printer Grid"/></h1>	
	<form:form 	method="post" cssClass="form" action="ScrutinyGridForm.html" name="frmSendToPrinter" id="frmSendToPrinter" required="true">
		<div class="sucess"><p>${command.printerMsg}</p></div>
		<div class="buttons btn-fld padding_top_10">
		<input type="button" class="css_btn"  onclick="return processForPrinter(this,'processForPrinter');" name="Send" value="Send" />
		</div>
	</form:form>
</div>
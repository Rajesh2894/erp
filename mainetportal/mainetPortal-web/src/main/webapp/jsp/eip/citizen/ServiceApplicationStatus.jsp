<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
	
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<script>
	function dispose() {

		$('.dialog').html('');
		$('.dialog').hide();
		disposeModalBox();
	}

</script>

<c:if test="${empty command.details.pamApplicationId}">
	<div class="alert alert-danger margin-top-5"><p><spring:message code="rti.serAppMsg"></spring:message></p></div>
</c:if>

<c:if test="${not empty command.details.pamApplicationId}">
<div class="table-responsive margin-top-5">
<table class="table table-bordered table-condensed table-striped">
	<tr>
		<th><spring:message code="rti.apmApplicationId"/></th>
		<td>${command.details.pamApplicationId}</td>
	</tr>
		<c:set var="date" value="${command.details.pamApplicationDate}" />
	<tr>
		<th><spring:message code="rti.date" /></th>
		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${date}"/></td>
	</tr>
	
	
	 <tr>
		<th><spring:message code="rti.apmServiceName"/></th>
		<td>${command.dto.serviceName}</td>
	</tr> 
	
  <tr>
		<th><spring:message code="rti.apmApplicationName"/></th>
		<td>${command.dto.applicantName}</td>
	</tr>  
	
	<tr>
		<th><spring:message code="rti.apmStatus"/></th>
		<td>${command.details.pamApplicationStatus}</td>
		
	</tr>
	 <c:if test="${empty command.details.pamApplicationId}">
	<tr>
		<th colspan="2">
			<spring:message code="rti.serAppMsg"></spring:message>	
		</th>
	</tr>
	</c:if> 
</table>
</div>
</c:if>
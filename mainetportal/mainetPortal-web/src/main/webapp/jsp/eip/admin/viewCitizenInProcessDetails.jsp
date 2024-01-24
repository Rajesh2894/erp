<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
	
<script src="js/rti/applicationForm.js"></script>
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

<c:if test="${empty command.details.apmId}">
	<div><span><spring:message code="rti.serAppMsg"></spring:message></span></div>
</c:if>

<c:if test="${not empty command.details.apmId}">
<div class="about">
  <h1><spring:message code="rti.Serviceapplication"></spring:message></h1>
  </div>
<table class="gridtable">
	<tr>
		<td width="40%"><spring:message code="rti.apmApplicationId"/></td>
		<td>${command.details.apmId}</td>
	</tr>
		<c:set var="date" value="${command.details.apmDate}" />
	<tr>
		<td><spring:message code="rti.date" /></td>
		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${date}"/></td>
	</tr>
	<c:set var="durationdate" value="${command.details.apmServiceDuration}"></c:set>
	<%
	 Date durDate= new Date();
	 Calendar cal = Calendar.getInstance();
	 cal.setTime (durDate);
	 cal.add (Calendar.DATE,new Integer(pageContext.getAttribute("durationdate").toString()));
	 durDate = cal.getTime ();
	 pageContext.setAttribute("serdate",durDate);
	%>
	<tr>
	    <c:set var="currentDate" value="${serdate}"></c:set>	
		<td><spring:message code="rti.dueDate"/></td>
		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${currentDate}"/></td>
	</tr>
	
	<tr>
		<td><spring:message code="rti.apmServiceName"/></td>
		<td>${command.details.apmServiceName}</td>
	</tr>
	
	<tr>
		<td><spring:message code="rti.apmApplicationName"/></td>
		<td>${command.details.apmName}</td>
	</tr>
	
	<tr>
		<td><spring:message code="rti.apmStatus"/></td>
		<td>${command.details.apmStsFlag}</td>
		
	</tr>
	<c:if test="${empty command.details.apmId}">
	<tr>
		<td colspan="2">
			<spring:message code="rti.serAppMsg"></spring:message>	
		</td>
	</tr>
	</c:if>
</table>
</c:if>
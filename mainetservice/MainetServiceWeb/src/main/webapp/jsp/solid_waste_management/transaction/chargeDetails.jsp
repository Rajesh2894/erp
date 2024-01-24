<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="widget login">
  
  <div class="widget-content">
    <table class="table table-bordered table-condensed">
  <tr>
    <th><spring:message code="label.name.charge.details"/></th>
    <th><spring:message code="label.name.amount"/></th>
  </tr>
  <c:forEach items="${command.chargesInfo}" var="charge" varStatus="lk">
  	 <tr>
  	 	<c:choose>
  	 		<c:when test="${userSession.languageId eq 1}">
  	 			 <td>${charge.chargeDescEng}</td>
  	 		</c:when>
  	 		<c:otherwise>
  	 			 <td>${charge.chargeDescReg}</td>
  	 		</c:otherwise>
  	 	</c:choose>
  	 	<c:if test="${not empty command.collectorReqDTO.collectorDTO.complainNo}">
  	 	 <td class="text-center">${charge.chargeAmount * 1.2}</td>
  	 	</c:if>
  	 	<c:if test="${empty command.collectorReqDTO.collectorDTO.complainNo}">
   		 <td class="text-center">${charge.chargeAmount}</td>
   		 </c:if>
  	</tr>
  </c:forEach>
  <tr>
    <th><p class="text-right"><spring:message code="swm.trip.sheet.report.no.of.trip"/></p></th>
    <th>${command.collectorReqDTO.collectorDTO.noTrip}</th>
  </tr>
  <tr>
    <th><p class="text-right"><spring:message code="label.name.total"/></p></th>
    <th>${command.offlineDTO.amountToShow}</th>
  </tr>
</table>
  </div>
</div>
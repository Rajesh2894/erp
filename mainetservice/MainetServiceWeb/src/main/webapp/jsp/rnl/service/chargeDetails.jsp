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
  	 	<fmt:formatNumber type="number" value="${charge.chargeAmount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="chargeAmount"/>
   		 <td>${chargeAmount}</td>
  	</tr>
  </c:forEach>
  <tr>
    <th><p class="text-right"><spring:message code="label.name.total"/></p></th>
    <fmt:formatNumber type="number" value="${command.offlineDTO.amountToShow}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amountToShow"/>
    <th>${amountToShow}</th>
  </tr>
</table>
  </div>
</div>
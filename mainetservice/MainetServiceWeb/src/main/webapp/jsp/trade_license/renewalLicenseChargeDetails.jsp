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

  
    <c:forEach var="charge" items="${command.tradeMasterDetailDTO.chargesInfo}"> 
     <tr>
      <td>${charge.key}  </td>
      <td>${charge.value}</td>
     </tr>
    </c:forEach>
  	<%-- 	<tr>
  		<td>Application Charge${command.tradeMasterDetailDTO.applicationCharge}</td>
  	 	<td class="text-right">${command.tradeMasterDetailDTO.totalApplicationFee}</td>
  		</tr> --%>
  
  <tr>
    <th><p class="text-right"><spring:message code="label.name.total"/></p></th>
    <th class="text-right">${command.offlineDTO.amountToShow}</th>
  </tr>
</table>
  </div>
</div>
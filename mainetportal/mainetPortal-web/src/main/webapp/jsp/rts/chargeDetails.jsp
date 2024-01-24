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
    <table class="table table-bordered margin-top-0">
  <tr>
    <th><spring:message code="label.name.charge.details"/></th>
    <th><spring:message code="label.name.amount"/></th>
  </tr>
  <tr>
  <td><spring:message code="rts.applicationCharge"/></td>
  <td class="text-right">${command.offlineDTO.amountToShow}</td>
  </tr>
  <tr>
    <td class="text-right"><spring:message code="label.name.total"/></td>
    <td class="text-right">${command.offlineDTO.amountToShow}</td>
  </tr>
</table>
  </div>
</div>
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/RefuelingAdvice.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<h2>
	<spring:message code="fuelling.details" text="Fueling Details" />
</h2>
<div class="table-responsive clear">
	<table summary="Dumping Ground Data" id="vehicleFuellingReconcilation"
		class="table table-bordered table-striped sm">
		<thead>
			<tr>
				<th><spring:message code="vehicle.maintenance.master.id"
						text="Sr. No" /></th>
				<th><spring:message code="route.master.vehicle.type"
						text="Vehicle Type" /></th>
				<th><spring:message code="vehicle.master.vehicle.no"
						text="Vehicle Reg. No." /></th>

				<th><spring:message code="vehicle.fuelling.adviceno"
						text="Advice No." /></th>

				<th><spring:message code="vehicle.fuelling.adviceDate"
						text="Advice Date" /></th>

				<th><spring:message code="vehicle.fuelling.cost"
						text="Total Cost" /></th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th align="right"><span style="font-weight: bold"><spring:message
							code="swm.total" text="Total" /></span></th>
				<th align="center">
					<div align="center" class="input-group">
						<form:input path="command.vehicleFuellingDTO.vefRmamount"
							class="form-control mandColorClass text-right" id="id_total"
							readOnly="true" />
					</div>
				</th>
			</tr>
		</tfoot>
		<tbody>
			<c:forEach items="${vehicleFuelReconcilationList}" var="data"
				varStatus="status">
				<tr class="appendableClass">
					<td align="center">${status.count}</td>
					<td>${data.veVetype}</td>
					<td>${data.veNo}</td>
					<td>${data.adviceNumber}</td>
					<td>${data.adviceDate}</td>
					<td>${data.vefRmamount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
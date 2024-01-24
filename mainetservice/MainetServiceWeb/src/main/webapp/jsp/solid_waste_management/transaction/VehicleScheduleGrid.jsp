<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet"
	type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/VehicleScheduling.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/fullcalendar.min.js"></script>
<!-- End JSP Necessary Tags -->
<!-- Start Main Page Heading -->
<div class="widget">
	<div class="widget-header">
		<h2>
			<strong><spring:message code="swm.vehiclescheduling"
					text="Vehicle Scheduling" /></strong>
		</h2>
		<apptags:helpDoc url="VehicleScheduling.html" />
	</div>
	<div class="widget-content padding">
		<form:form action="VehicleScheduling.html"
			class="form-horizontal form" name="command" id="id_VehicleScheduling">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<div class="table-responsive">
				<div class="table-responsive margin-top-10">
					<table class="table table-striped table-condensed table-bordered"
						id="id_vehicleScheduling">
						<thead>
							<tr>
								<th><spring:message code="refueling.pump.master.sr.no" text="Sr. No." /></th>
								<th><spring:message code="swm.vehicletype"
										text="Vehicle Type" /><span class="mand">*</span></th>
								<th><spring:message code="vehicle.master.vehicle.no" text="Vehicle No." /></th>
								<th><spring:message code="swm.scheduleFrom"
										text="Schedule From" /><span class="mand">*</span></th>
								<th><spring:message code="swm.scheduleTo"
										text="Schedule To" /><span class="mand">*</span></th>

								<th><spring:message code="swm.reccurance" text="Reccurance" /><span
									class="mand">*</span></th>
								<th><spring:message code="swm.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.vehicleScheduleDtos}"
								var="vehiclescheduling" varStatus="loop">
								<tr>
									<td align="center">${loop.count}</td>
									<td align="center">${vehiclescheduling.veDesc}</td>
									<td align="center">${vehiclescheduling.veRegnNo}</td>
									<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
											value="${vehiclescheduling.vesFromdt}" /></td>
									<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
											value="${vehiclescheduling.vesTodt}" /></td>
									<c:choose>
										<c:when test="${vehiclescheduling.vesReocc eq 'D'}">
											<td align="center"><spring:message
													code="swm.sanitary.report.daily" /></td>
										</c:when>
										<c:when test="${vehiclescheduling.vesReocc eq 'W'}">
											<td align="center"><spring:message
													code="swm.sanitary.report.Weekly" /></td>
										</c:when>
										<c:otherwise>
											<td align="center"><spring:message
													code="swm.sanitary.report.Monthly" /></td>
										</c:otherwise>
									</c:choose>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View"
											onclick="modifyVehicleScheduling(${vehiclescheduling.vesId},'CollectionScheduling.html','ViewVehicleScheduling','V')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit"
											onclick="modifyVehicleScheduling(${vehiclescheduling.vesId},'CollectionScheduling.html','EditVehicleScheduling','E')">
											<i class="fa fa-pencil"></i>
										</button>
										<button type="button" class="btn btn-danger btn-sm"
											title="Delete"
											onclick="deletevehicleScheduling(${vehiclescheduling.vesId},'CollectionScheduling.html','DeleteVehicleScheduling')">
											<i class="fa fa-trash"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</form:form>
		<!-- End Form -->
		<div class="text-center padding-top-10">
			<apptags:backButton url="CollectionScheduling.html"></apptags:backButton>			
		</div>
	</div>
</div>
<!-- End Widget Content here -->



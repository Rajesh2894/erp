<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script type="text/javascript" src="js/vehicle_management/VehicleMaintenance.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,
		});
	});
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.maintenance.heading"
					text="Vehicle Maintenance"></spring:message>
			</h2>
			<apptags:helpDoc url="vehicleMaintenanceMgmt.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="vehicleMaintenanceMgmt.html" name="VehicleMaintenance"
				id="VehicleMaintenanceList" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-sm-2" for="Maintenance Type"><spring:message
							code="vehicle.maintenance.type" text="Maintenance Type" /></label>
					<c:set var="baseLookupCode" value="MNT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMaintenanceDTO.vemMetype" cssClass="form-control"
						hasId="true" selectOptionLabelCode="selectdropdown" />

					<div class="form-group">
						<label class="control-label col-sm-2" for="VehicleType"><spring:message
								code="vehicle.maintenance.master.type" /></label>
						<c:set var="baseLookupCode" value="VCH" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="vehicleMaintenanceDTO.veVetype" cssClass="form-control"
							hasId="true" selectOptionLabelCode="selectdropdown" />
					</div>
				</div>
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="vehicle.maintenance.fromDate"
						datePath="vehicleMaintenanceDTO.fromDate" cssClass="custDate">
					</apptags:date>
					<apptags:date fieldclass="datepicker"
						labelCode="vehicle.maintenance.toDate"
						datePath="vehicleMaintenanceDTO.toDate" cssClass="custDate">
					</apptags:date>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2"
						onclick="searchVehicleMaintenance('vehicleMaintenanceMgmt.html', 'search');"
						title="<spring:message code="solid.waste.search" text="Search" />">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search" />
					</button>
					<button type="Reset" class="btn btn-warning" onclick=" resetVehicleMaintenance();"
						title="<spring:message code="solid.waste.reset" text="Reset"></spring:message>">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addVehicleMaintenance('vehicleMaintenanceMgmt.html','AddVehicleMaintenance');"
						title="<spring:message code="fueling.pump.master.add" text="Add" />">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="fueling.pump.master.add" text="Add" />
					</button>
				</div>
				<div class="table-responsive clear">
					<table summary="Vehicle Maintenance Data"
						class="table table-bordered table-striped vm">
						<thead>
							<tr>
								<th><spring:message code="vehicle.maintenance.type"
										text="Maintenance Type" /></th>
								<th><spring:message code="vehicle.maintenance.date"
										text="Date of Repair/Maintenance" /></th>
								<th><spring:message code="vehicle.master.vehicle.type"
										text="Vehicle Type" /></th>
								<th><spring:message code="vehicle.maintenance.regno"
										text="Vehicle Reg. No." /></th>
								<th><spring:message
										code="vehicle.maintenance.total.cost.incurred"
										text="Total Cost Incurred" /></th>
								<th width="100"><spring:message
										code="vehicle.maintenance.master.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.vehicleMaintenanceList}" var="data"
								varStatus="index">
								<fmt:formatDate value="${data.vemDate}" var="vemDateString"
									pattern="dd/MM/yyyy" />
								<tr>
									<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.vemMetype)"
											var="lookup" />${lookup.lookUpDesc }</td>
									<td>${vemDateString}</td>
									<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.veVetype)"
											var="lookup" />${lookup.lookUpDesc }</td>
									<td>${data.veNo}</td>
									<td class="text-right">${data.vemCostincurred}</td>
									<td style="width: 15%" align="center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getVehicleMaintenanceView('vehicleMaintenanceMgmt.html','viewVehicleMaintenance',${data.vemId})"
											title="<spring:message code="vehicle.view" text="View"/>">
											<strong class="fa fa-eye"></strong> <span class="hide">
												<spring:message code="solid.waste.view" text="View"></spring:message>
											</span>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getVehicleMaintenance('vehicleMaintenanceMgmt.html','editVehicleMaintenance',${data.vemId})"
											title="<spring:message code="vehicle.edit" text="Edit"/>">
											<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
													code="solid.waste.edit" text="Edit"></spring:message></span>
										</button>
										<button
											onclick="printdiv('vehicleMaintenanceMgmt.html', 'formForPrint', ${data.vemId});"
											class="btn btn-darkblue-3" type="button" title="<spring:message code="vehicle.fuel.print" text="Print"/>">
											<strong class="fa fa-print"></strong><span class="hide"><spring:message
													code="vehicle.fuel.print" text="Print"></spring:message></span>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>

		</div>
	</div>
</div>

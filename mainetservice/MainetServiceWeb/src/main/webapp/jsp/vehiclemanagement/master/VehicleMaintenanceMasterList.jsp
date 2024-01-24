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
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/vehicle_management/VehicleMaintenanceMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.maintenance.master.heading"
					text="Vehicle Maintenance Master" />
			</h2>
			<apptags:helpDoc url="vehicleMaintenanceMasController.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="vehicleMaintenanceMasController.html"
				name="VehicleMaintenanceMaster" id="VehicleMaintenanceMasterList"
				class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-sm-2" for="VehicleType"><spring:message
							code="vehicle.maintenance.master.type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMaintenanceMasterDTO.veVetype"
						cssClass="form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" onclick="searchVehicleMaintenanceMaster();"
						title="<spring:message code="solid.waste.search" text="Search" />">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning" onclick="resetVehicleMaintenanceMaster();"
						title="<spring:message code="solid.waste.reset" text="Reset"></spring:message>">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addVehicleMaintenance('vehicleMaintenanceMasController.html','AddVehicleMaintenanceMaster');"
						title="<spring:message code="fueling.pump.master.add" text="Add" />">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="fueling.pump.master.add" text="Add" />
					</button>
				</div>
				<div class="table-responsive clear">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped vm">
						<thead>
							<tr>
								<th><spring:message code="vehicle.maintenance.master.type" /></th>
								<th><spring:message
										code="vehicle.maintenance.master.maintenanceAfter" /></th>
								<th><spring:message
										code="vehicle.maintenance.master.estimatedDowntime" /></th>
								<th width="100"><spring:message
										code="disposal.site.master.status" text="Status" /></th>
								<th width="100"><spring:message
										code="vehicle.maintenance.master.action" text="Action" /></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.vehicleMaintenanceList}" var="data"
								varStatus="index">
								<tr>
									<td><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.veVetype)"
											var="lookup" />${lookup.lookUpDesc}</td>
									<td>${data.veMainday}&nbsp;<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.veMainUnit)"
											var="lookup" />${lookup.lookUpDesc}</td>
									<td>${data.veDowntime}&nbsp;<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.veDowntimeUnit)"
											var="lookup" />${lookup.lookUpDesc}</td>
									<td class="text-center"><c:choose>
											<c:when test="${data.veMeActive eq 'Y'}">
												<a href="#" class="fa fa-check-circle fa-2x green "
													title="Active"></a>
											</c:when>
											<c:otherwise>
												<a href="#" class="fa fa-times-circle fa-2x red "
													title="InActive"></a>
											</c:otherwise>
										</c:choose></td>
									<td style="width: 15%" align="center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getVehicleMaintenancemasterData('vehicleMaintenanceMasController.html','viewVehicleMaintenanceMaster',${data.veMeId})"
											title="<spring:message code="vehicle.view" text="View"/>">
											<strong class="fa fa-eye"></strong><span class="hide"><spring:message
													code="solid.waste.view" text="View"></spring:message></span>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getVehicleMaintenancemasterData('vehicleMaintenanceMasController.html','editVehicleMaintenanceMaster',${data.veMeId})"
											title="<spring:message code="vehicle.edit" text="Edit"/>">
											<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
													code="solid.waste.edit" text="Edit"></spring:message></span>
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
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
<script type="text/javascript"
	src="js/solid_waste_management/VehicleFuelling.js"></script>
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
				<spring:message code="vehicle.fuelling.heading"
					text="Vehicle Fueling" />
			</h2>
			<apptags:helpDoc url="VehicleFueling.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="VehicleFueling.html" name="VehicleFueling"
				id="VehicleFuelingList" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-sm-2 " for="Census"><spring:message
							code="vehicle.maintenance.master.type" text="Census(year)" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleFuellingDTO.veVetype" cssClass="form-control"
						hasId="true" selectOptionLabelCode="selectdropdown" />
					<label class="control-label col-sm-2 "> <spring:message
							code="vehicle.fuelling.pump.name" /></label>
					<div class="col-sm-4">
						<form:select cssClass=" form-control"
							path="vehicleFuellingDTO.puId" id="puId">
							<form:option value=""><spring:message code="solid.waste.select" text="Select" /></form:option>
							<c:forEach items="${pumps}" var="pump">
								<form:option value="${pump.puId}">${pump.puPumpname}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<apptags:date fieldclass="fromDateClass"
						labelCode="swm.fromDate"
						datePath="vehicleFuellingDTO.fromDate" cssClass="custDate" >
					</apptags:date>
					<apptags:date fieldclass="toDateClass"
						labelCode="vehicle.maintenance.toDate"
						datePath="vehicleFuellingDTO.toDate" cssClass="custDate" >
					</apptags:date>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2"
						onclick="searchVehicleFueling('VehicleFueling.html', 'searchVehicleFueling');">
						<i class="fa fa-search"></i> <spring:message code="solid.waste.search" text="Search" />
					</button>
					<button type="Reset" class="btn btn-warning"
						onclick="resetVehicleFuelling();">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addVehicleFueling('VehicleFueling.html','AddVehicleFueling');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="solid.waste.add" text="Add" />
					</button>
				</div>
				<div class="table-responsive clear">
					<table summary="Dumping Ground Data"
						class="table table-bordered table-striped sm">
						<thead>
							<tr>
								<th><spring:message code="vehicle.maintenance.master.type"
										text="Vehicle Type" /></th>
								<th><spring:message code="vehicle.fuelling.fuelingDate"
										text="Fueling Date" /></th>
								<th><spring:message code="vehicle.maintenance.regno"
										text="Vehicle Reg. No." /></th>
								<th><spring:message code="vehicle.fuelling.vehicleReading"
										text="Vehicle Reading" /></th>
								<th><spring:message code="vehicle.fuelling.pump.name"
										text="Name of Pump" /></th>
								<th><spring:message code="vehicle.fuelling.totalCost"
										text="Total Cost" /></th>
								<th width="150"><spring:message code="solid.waste.action"
										text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.vehicleFuellingList}" var="data"
								varStatus="index">
								<tr>
									<td align="center"><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.veVetype)"
											var="lookup" />${lookup.lookUpDesc}</td>
									<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
											value="${data.vefDate}" /></td>
									<td align="center">${data.veNo}</td>
									<td align="center">${data.vefReading}</td>
									<td align="center">${data.puPumpname}</td>
									<td align="right">${data.vefRmamount}</td>
									<td align="center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getVehicleFuellingDataView('VehicleFueling.html','viewVehicleFueling',${data.vefId})"
											title="View">
											<strong class="fa fa-eye"></strong> <span class="hide">
												<spring:message code="solid.waste.view" text="View"></spring:message>
											</span>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getVehicleFuellingData('VehicleFueling.html','editVehicleFuelling',${data.vefId})"
											title="Edit">
											<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
													code="solid.waste.edit" text="Edit"></spring:message></span>
										</button>
										<button
											onclick="printdiv('VehicleFueling.html', 'formForPrint', ${data.vefId});"
											class="btn btn-darkblue-3" type="button">
											<i class="fa fa-print"></i> 
										</button>
									</td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>



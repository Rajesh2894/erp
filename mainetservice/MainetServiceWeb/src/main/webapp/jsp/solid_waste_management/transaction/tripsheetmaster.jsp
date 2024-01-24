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
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/tripSheet.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.tripsheetsum"
						text="Trip Sheet Summary" /></strong>
			</h2>
			<apptags:helpDoc url="TripSheetMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="TripSheetMaster.html" commandName="command"
				class="form-horizontal form" name="tripsheet" id="id_tripsheet">
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				<div class="form-group">
					<label class="col-sm-2 control-label " for="vehicleType"><spring:message
							code="swm.vehicleType" /></label>
					<apptags:lookupField items="${command.getLevelData('VCH')}"
						path="tripSheetDto.veType" cssClass="form-control chosen-select-no-results"
						changeHandler="showVehicleRegNo()"
						selectOptionLabelCode="selectdropdown" hasId="true" isMandatory="" />
					<label class="col-sm-2 control-label " for="vehicleno"><spring:message
							code="swm.vehicleno" /></label>
					<div class="col-sm-4">
						<form:select path="tripSheetDto.no" class="form-control chosen-select-no-results"
							label="Select" id="veId">
							<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
							<c:forEach items="${command.vehicleMasterList}" var="lookup">
								<form:option value="${lookup.veId}" code="${lookup.veId}">${lookup.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<apptags:input labelCode="swm.fromDate"
						cssClass="fromDateClass fromDate" path="tripSheetDto.fromDate" isReadonly="false"
						isMandatory="" placeholder="dd/mm/yyyy" maxlegnth="10" />
					<apptags:input labelCode="swm.toDate" cssClass="toDateClass toDate"  isReadonly="false"
						path="tripSheetDto.toDate" isMandatory="" placeholder="dd/mm/yyyy" maxlegnth="10" />
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchTripSheet('TripSheetMaster.html','SearchTripSheetMaster')">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search" />
					</button>
					<%-- Defect #155498 --%>
					<button type="button" class="btn btn-warning" onclick="javascript:openRelatedForm('TripSheetMaster.html');" title="Reset">
						<spring:message code="rstBtn" text="Reset" />
					</button>
					<button type="submit" class="button-input btn btn-success"
						onclick="openaddtripsheet('TripSheetMaster.html','AddTripSheetMaster')"
						name="button-Add" style="" id="button-submit">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="solid.waste.add" text="Add" />
					</button>
				</div>
				<!-- End button -->
				
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_tripSheetTable">
							<thead>
								<tr>
									<th><spring:message code="swm.tripNo" text="Trip No." /></th>
									<th><spring:message code="swm.vehicleType"
											text="Vehicle Type" /></th>
									<th><spring:message code="swm.vehicleno"
											text="Vehicle No." /></th>
									<th><spring:message code="swm.tripdate" text="Trip Date" /></th>
									<th><spring:message code="swm.totalgarbage"
											text="Total Garbage" /></th>
									<th><spring:message code="swm.dsplsite"
											text="Disposal Site" /></th>
									<th><spring:message code="swm.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.tripSheetDtos}" var="trip"
									varStatus="loop">
									<tr>
										<td align="center">${trip.tripId}</td>
										<td align="center">${trip.veType}</td>
										<td align="center">${trip.veNo}</td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
												value="${trip.tripDate}" /></td>
										<td align="right">${trip.tripTotalgarbage} &nbsp;<spring:message
												code="swm.kilo" text=" Kgs" /></td>
										<td align="center">${trip.deName}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"
												onclick="modifyTrip(${trip.tripId},'TripSheetMaster.html','ViewTripSheetMaster','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit"
												onclick="modifyTrip(${trip.tripId},'TripSheetMaster.html','EditTripSheetMaster','E')">
												<i class="fa fa-pencil"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>






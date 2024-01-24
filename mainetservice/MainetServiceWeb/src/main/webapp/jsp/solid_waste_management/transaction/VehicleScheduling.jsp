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
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.collection.scheduling"
						text="Collection Scheduling" /></strong>
			</h2>
			<apptags:helpDoc url="VehicleScheduling.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="CollectionScheduling.html"
				class="form-horizontal form" name="command"
				id="id_VehicleScheduling">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="gender"><spring:message
							code="swm.vehicleType" /></label>
					<apptags:lookupField items="${command.getLevelData('VCH')}"
						path="vehicleScheduleDto.veVetype" cssClass="form-control"
						selectOptionLabelCode="selectdropdown" hasId="true"
						changeHandler="showVehicleRegNo()" isMandatory="true" />
					<label class="col-sm-2 control-label " for="gender"><spring:message
							code="swm.vehicleRegNo" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleScheduleDto.veId" id="veid"
							class="form-control mandColorClass " label="Select">
							<form:option value="0">
								<spring:message code="solid.waste.select" text="select" /></form:option>
							<c:forEach items="${command.vehicleMasterList}" var="lookup">
								<form:option value="${lookup.veId}" code="${lookup.veId}">${lookup.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2"
						onclick="searchVehicleScheduling('CollectionScheduling.html', 'SearchVehicleScheduling');">
						<i class="fa fa-search"></i>
						<spring:message code="swm.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetVehicleScheduling();">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<button type="submit" class="button-input btn btn-success"
						onclick="openAddVehicleScheduling('CollectionScheduling.html','AddVehicleScheduling');"
						name="button-Add" style="" id="button-submit">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="bt.add" text="Add" />
					</button>

				</div>
				<div id="calendar"></div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->




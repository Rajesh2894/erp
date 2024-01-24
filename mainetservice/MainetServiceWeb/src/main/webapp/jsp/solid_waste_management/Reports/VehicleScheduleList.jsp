<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/report/vehicleScheduleReport.js"></script>

<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="swm.collection.schedule.report" text="Collection Schedule Report" />
			</h2>
			<apptags:helpDoc url="VehicleScheduleReport.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="get"
				class="form-horizontal ng-pristine ng-valid">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div id="errorId"></div>
				</div>
				<div class="panel panel-default">
					<div id="a0" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label for="select-1478760963433" class="col-sm-2 control-label"><spring:message
										code="swm.sanitary.report.vehicle.type" text="Vehicle Type" /><span
									class="required-control"></span> </label>
								<c:set var="baseLookupCode" value="VCH" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="vehicleMasterDTO.veVetype"
									cssClass="form-control  chosen-select-no-results"
									isMandatory="true" selectOptionLabelCode="selectdropdown"
									hasId="true" showAll="true" changeHandler="showVehicleRegNo()" />

								<label for="select-1478760966687" class="col-sm-2 control-label"><spring:message
										code="swm.sanitary.report.vehicle.regno"
										text="Vehicle Registration No. " /><span
									class="required-control"></span> </label>
								<div class="col-sm-4">
									<form:select
										class=" mandColorClass form-control chosen-select-no-results"
										path="vehicleMasterDTO.veNo" id="vemId">
										<form:option value="" selected="true">
											<spring:message code="solid.waste.select" text="Select" />
										</form:option>
										<form:option value="0">
											<spring:message code="solid.waste.All" text="All" />
										</form:option>
										<c:forEach items="${command.vehicleMasterList}" var="vehicle">
											<form:option value="${vehicle.veId}">${vehicle.veNo}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<apptags:date fieldclass="datepicker"
									labelCode="vehicle.fuelling.fromDate"
									datePath="vehicleMasterDTO.veRentFromdate" isMandatory="true"
									cssClass="custDate mandColorClass">
								</apptags:date>
								<apptags:date fieldclass="datepicker" labelCode="vehicle.fuelling.toDate"
									datePath="vehicleMasterDTO.veRentTodate" isMandatory="true"
									cssClass="custDate mandColorClass">
								</apptags:date>
							</div>

						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit" title='<spring:message code="solid.waste.print" text="Print" />'
						onClick="vehicleSchedulePrint('VehicleScheduleReport.html','report')"
						data-original-title="View">
						<spring:message code="solid.waste.print" text="Print"></spring:message>
					</button>
					<button type="Reset" class="btn btn-warning" title='<spring:message code="solid.waste.reset" text="Reset" />' onclick="resetTrip()">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
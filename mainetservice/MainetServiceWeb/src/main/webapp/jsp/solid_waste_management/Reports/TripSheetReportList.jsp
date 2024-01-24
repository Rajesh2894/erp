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
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>

<script type="text/javascript"
	src="js/solid_waste_management/report/TripSheetReport.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="swm.trip.sheet.report.head" text="Trip sheet"/></h2>
			<apptags:helpDoc url="TripSheetReport.html"></apptags:helpDoc>
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
					<span id="errorId"></span>
				</div>
				<div class="panel panel-default">
					<div id="a0" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group">
								<label for="date-1493383113506" class="col-sm-2 control-label "><spring:message code="swm.trip.sheet.report.type" text="Report Type"/><span class="required-control"></span>
								</label>
								<div class="col-sm-4">
									<label for="radio-group-1492067297931-0" class="radio-inline ">
										<input type="radio" name="reporttype" class="radio-group"
										id="radio1" value="Summary" checked><spring:message code="swm.trip.sheet.report.summary" text="Summary"/>
									</label> <label for="radio-group-1492067297931-1" class="radio-inline">
										<input type="radio" name="reporttype" class="radio-group"
										id="radio2" value="Detail"> <spring:message code="swm.trip.sheet.report.detail" text="Detail"/>
									</label>
								</div>
							</div>
							<div class="form-group">
								<label for="select-1478760963433" class="col-sm-2 control-label"><spring:message code="swm.trip.sheet.report.vehicle.type" text="Vehicle Type"/><span class="required-control"></span>
								</label>
								<c:set var="baseLookupCode" value="VCH" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="vehicleMasterDTO.veVetype"
									cssClass="form-control mandColorClass chosen-select-no-results"
									isMandatory="true" selectOptionLabelCode="selectdropdown"
									hasId="true" showAll="true" changeHandler="showVehicleRegNo()" />

								<label for="select-1478760966687" class="col-sm-2 control-label"><spring:message code="swm.trip.sheet.report.vehicleno" text="Vehicle No"/><span class="required-control"></span>
								</label>
								<div class="col-sm-4">
									<form:select
										class="form-control mandColorClass chosen-select-no-results"
										path="vehicleMasterDTO.veNo" id="vemId">
										<form:option value="" selected="true"><spring:message code="solid.waste.select" text="Select"/></form:option>
										<form:option value="0"><spring:message code="solid.waste.All" text="All"/></form:option>
										<c:forEach items="${command.vehicleMasterList}" var="vehicle">
											<form:option value="${vehicle.veId}">${vehicle.veNo}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label for="select-1478760966687" class="col-sm-2 control-label"><spring:message code="swm.trip.sheet.report.vendorName" text="Vendor Name"/>
								</label>
								<div class="col-sm-4">
									<form:select
										class="form-control mandColorClass chosen-select-no-results"
										path="vehicleMasterDTO.veNo" id="vendorName">
										<form:option value="" selected="true"><spring:message code="solid.waste.select" text="Select"/></form:option>
										<form:option value="0"><spring:message code="solid.waste.All" text="All"/></form:option>
										<c:forEach items="${command.vendorNameList}" var="vendorName">
											<form:option value="${vendorName}">${vendorName}</form:option>
										</c:forEach>
									</form:select>
								</div>

								<label for="select-1478760966687" class="col-sm-2 control-label"><spring:message code="swm.trip.sheet.report.ContractNo" text="Contract No"/>
								</label>
								<div class="col-sm-4">
									<form:select
										class="form-control mandColorClass chosen-select-no-results"
										path="vehicleMasterDTO.veNo" id="contractNo">
										<form:option value="" selected="true"><spring:message code="solid.waste.select" text="Select"/></form:option>
										<form:option value="0"><spring:message code="solid.waste.All" text="All"/></form:option>
										<c:forEach items="${command.contractNoList}" var="contractNo">
											<form:option value="${contractNo}">${contractNo}</form:option>
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
								<apptags:date fieldclass="datepicker" labelCode="To Date"
									datePath="vehicleMasterDTO.veRentTodate" isMandatory="true"
									cssClass="custDate mandColorClass">
								</apptags:date>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onClick="tripsheetReportPrint('TripSheetReport.html','report')"
						data-original-title="View">
						<spring:message code="solid.waste.print" text="Print"></spring:message>
					</button>
					<button type="Reset" class="btn btn-warning" onclick="resetTrip()">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
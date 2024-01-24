<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/solid_waste_management/VehicleMaintenanceMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.maintenance.master.heading"
					text="Vehicle Maintenance Master" />
			</h2>
		
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with " /><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="VehicleMaintenanceMaster.html"
				name="VehicleMaintenanceMaster" id="VehicleMaintenanceMasterForm"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="vehicle.maintenance.master.type" text="Census(year)" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMaintenanceMasterDTO.veVetype"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="true" />
					<label for="maintenanceAfter"
						class="col-sm-2 control-label required-control"><spring:message
							code="vehicle.maintenance.master.maintenanceAfter"
							text="Maintenance After" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type='text'
								path="vehicleMaintenanceMasterDTO.veMainday"
								class=' mandColorClass form-control hasNumber' placeholder=""
								disabled="true" />
							<div class='input-group-field'>
								<form:select path="vehicleMaintenanceMasterDTO.veMainUnit"
									cssClass="form-control required-control mandColorClass"
									id="veMainUnit" disabled="true">
									<form:option value="">
										<spring:message code="solid.waste.search" text="Select" />
									</form:option>
									<c:forEach items="${command.getLevelData('UOM')}" var="lookup">
										<c:if
											test="${lookup.otherField eq 'DURATION' or (lookup.otherField eq 'LENGTH' and lookup.descLangFirst eq 'Kilometer')}">
											<form:option value="${lookup.lookUpId}"
												code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="estimatedDowntime"
						class="col-sm-2 control-label required-control"><spring:message
							code="vehicle.maintenance.master.estimatedDowntime"
							text="Estimated Downtime" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type='text'
								path="vehicleMaintenanceMasterDTO.veDowntime"
								class=' mandColorClass form-control mandColorClass hasNumber'
								placeholder="" disabled="true" />
							<div class='input-group-field'>
								<form:select path="vehicleMaintenanceMasterDTO.veDowntimeUnit"
									cssClass="form-control required-control mandColorClass"
									id="veDowntimeUnit" disabled="true">
									<form:option value="">
										<spring:message code="solid.waste.search" text="Select" />
									</form:option>
									<c:forEach items="${command.getLevelData('UOM')}" var="lookup">
										<c:if
											test="${lookup.otherField eq 'DURATION' or (lookup.otherField eq 'LENGTH' and lookup.descLangFirst eq 'Kilometer')}">
											<form:option value="${lookup.lookUpId}"
												code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
					<label class="col-sm-2 control-label" for=""><spring:message
							code="employee.verification.status" text="Status" /> </label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								id="status1" path="vehicleMaintenanceMasterDTO.veMeActive"
								value="Y" disabled="true" checked="checked" /> <spring:message
								code="swm.Active" text="Active" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="status2" path="vehicleMaintenanceMasterDTO.veMeActive"
								value="N" disabled="true" /> <spring:message
								code="swm.Inactive" text="Inactive" />
						</label>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backVehicleMaintenanceMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

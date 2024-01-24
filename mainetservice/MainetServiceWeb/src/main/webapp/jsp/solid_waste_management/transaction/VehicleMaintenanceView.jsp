<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/VehicleMaintenance.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.maintenance.heading"
					text="Vehicle Maintenance"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;"></div>
			<form:form action="VehicleMaintenance.html" name="VehicleMaintenance"
				id="VehicleMaintenance" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="vehicle.maintenance.type" text="Maintenance Type" /></label>
					<c:set var="baseLookupCode" value="MNT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMaintenanceDTO.vemMetype" cssClass=" form-control "
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="true" />

					<apptags:date fieldclass="datepicker"
						labelCode="vehicle.maintenance.date"
						datePath="vehicleMaintenanceDTO.vemDate" isMandatory="true"
						cssClass="custDate mandColorClass" readonly="true">
					</apptags:date>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="vehicle.maintenance.details" text="Vehicle Details" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="Census"><spring:message
											code="vehicle.maintenance.master.type" text="Census(year)" /></label>
									<c:set var="baseLookupCode" value="VCH" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="vehicleMaintenanceDTO.veVetype"
										cssClass="mandColorClass form-control" hasId="true"
										selectOptionLabelCode="selectdropdown" disabled="true" />
									<label class="control-label col-sm-2 required-control">
										<spring:message code="vehicle.maintenance.regno" />
									</label>
									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="vehicleMaintenanceDTO.veId" id="vemId" disabled="true">
											<form:option value="">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<c:forEach items="${vehicles}" var="vehicle">
												<form:option value="${vehicle.veId}">${vehicle.veNo}</form:option>
											</c:forEach>
										</form:select>
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
												path="vehicleMaintenanceDTO.vemDowntime"
												class='mandColorClass form-control hasNumber'
												disabled="true" placeholder="" id="vemDowntime" />
											<div class='input-group-field'>
												<form:select path="vehicleMaintenanceDTO.vemDowntimeunit"
													cssClass="form-control required-control mandColorClass"
													id="vemDowntimeunit" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" text="Select" />
													</form:option>
													<c:forEach items="${command.getLevelData('UOM')}"
														var="lookup">
														<c:if test="${lookup.otherField eq 'DURATION'}">
															<form:option value="${lookup.lookUpId}"
																code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
														</c:if>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
									<apptags:input labelCode="vehicle.maintenance.repair.reading"
										path="vehicleMaintenanceDTO.vemReading" isDisabled="true"
										cssClass="hasNumber mandColorClass"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:input
										labelCode="vehicle.maintenance.total.cost.incurred"
										path="vehicleMaintenanceDTO.vemCostincurred"
										isMandatory="true" cssClass="hasNumber" isDisabled="true"></apptags:input>
									<apptags:input labelCode="vehicle.maintenance.receiptno"
										isDisabled="true" path="vehicleMaintenanceDTO.vemReceiptno"
										cssClass="hasNumber mandColorClass"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="vehicle.maintenance.receiptDate"
										datePath="vehicleMaintenanceDTO.vemReceiptdate"
										cssClass="custDate" isMandatory="true" readonly="true">
									</apptags:date>
									<apptags:input labelCode="vehicle.maintenance.expendiureHead"
										isReadonly="true" path="vehicleMaintenanceDTO.vemExpHead"
										cssClass="hasNumber mandColorClass" isDisabled="true"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:textArea labelCode="vehicle.maintenance.remark"
										path="vehicleMaintenanceDTO.remark" isDisabled="true"></apptags:textArea>
								</div>
								<c:if test="${! command.attachDocsList.isEmpty()}">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="deleteDoc">
										<tr>
											<th width="8%"><spring:message
													code="population.master.srno" text="Sr. No." /></th>
											<th><spring:message code="scheme.view.document" text="" /></th>

										</tr>
										<c:set var="e" value="0" scope="page" />
										<c:forEach items="${command.attachDocsList}" var="lookUp">
											<tr>
												<td>${e+1}</td>
												<td><apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}" actionUrl="VehicleMaintenance.html?Download" /></td>
											</tr>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:forEach>
									</table>
								</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backVehicleMaintenanceForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>
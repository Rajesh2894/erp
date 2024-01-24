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
	src="js/vehicle_management/VehicleMaintenance.js"></script>
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
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;"></div>
			<form:form action="vehicleMaintenanceMgmt.html" name="VehicleMaintenance"
				id="VehicleMaintenance" class="form-horizontal">
				<form:hidden path="" id="mode" value="E" />
				<form:hidden path="vehicleMaintenanceDTO.removeFileById" id="removeFileById" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="vehicle.maintenance.type" text="Maintenance Type" /></label>
					<c:set var="baseLookupCode" value="MNT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMaintenanceDTO.vemMetype"
						cssClass=" form-control mandColorClass " isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown" />

					<apptags:date fieldclass="datepicker"
						labelCode="vehicle.maintenance.date"
						datePath="vehicleMaintenanceDTO.vemDate" isMandatory="true"
						cssClass="custDate mandColorClass">
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
										cssClass="mandColorClass form-control" isMandatory="true"
										hasId="true" selectOptionLabelCode="selectdropdown"
										changeHandler="showVehicleRegNo()" />
									<label class="control-label col-sm-2 required-control">
										<spring:message code="vehicle.maintenance.regno" />
									</label>
									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="vehicleMaintenanceDTO.veId" id="vemId">
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
												path="vehicleMaintenanceDTO.vemDowntime" maxlength="12"
												class='mandColorClass form-control hasNumber' placeholder=""
												id="vemDowntime" />
											<div class='input-group-field'>
												<form:select path="vehicleMaintenanceDTO.vemDowntimeunit"
													cssClass="form-control required-control mandColorClass"
													id="vemDowntimeunit">
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
									<label for="text-1" class="control-label col-sm-2 required-control"><spring:message
											code="vehicle.maintenance.repair.reading" text="Vehicle Reading During Repair/ Maintenance" /></label>
									<div class="col-sm-4">
										<form:input cssClass="form-control text-right"
											path="vehicleMaintenanceDTO.vemReading"				
											onkeypress="return hasAmount(event, this, 12, 1)"
											id="vemReading" />
									</div>
									<%-- <apptags:input labelCode="vehicle.maintenance.repair.reading"
										path="vehicleMaintenanceDTO.vemReading" maxlegnth="12"
										cssClass="hasNumber mandColorClass" isMandatory="true"></apptags:input> --%>
								</div>
								<div class="form-group">
									<apptags:input
										labelCode="vehicle.maintenance.total.cost.incurred"
										path="vehicleMaintenanceDTO.vemCostincurred" maxlegnth="12"
										isMandatory="true" cssClass="hasDecimal text-right"></apptags:input>
									<apptags:input labelCode="vehicle.maintenance.receiptno"
										path="vehicleMaintenanceDTO.vemReceiptno" maxlegnth="12"
										cssClass="hasNumber mandColorClass" isMandatory="true"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="vehicle.maintenance.receiptDate"
										datePath="vehicleMaintenanceDTO.vemReceiptdate"
										cssClass="custDate" isMandatory="true">
									</apptags:date>
									<%-- <apptags:input labelCode="vehicle.maintenance.expendiureHead"
										path="vehicleMaintenanceDTO.vemExpHead"
										cssClass="hasNumber mandColorClass" isDisabled="true"></apptags:input> --%>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="swm.fileupload" /></label>
									<div class="col-sm-4">
										<small class="text-blue-2"> <spring:message code="vehicle.file.upload.tooltip"
												text="(Upload File upto 5MB and only pdf,doc,xls,xlsx)" />
										</small>
										<apptags:formField fieldType="7" labelCode="" hasId="true"
											fieldPath="" isMandatory="false" showFileNameHTMLId="true"
											fileSize="BND_COMMOM_MAX_SIZE"
											maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
											currentCount="0" />
									</div>
									<apptags:textArea labelCode="vehicle.maintenance.remark" maxlegnth="95"
										path="vehicleMaintenanceDTO.vemReason"></apptags:textArea>
								</div>
								<c:if test="${! command.attachDocsList.isEmpty()}">
							    <div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="deleteDoc">
										<tr>
											<th width="8%"><spring:message code="population.master.srno" text="Sr. No." /></th>
											<th><spring:message code="scheme.view.document" text="" /></th>
											<th><spring:message code="" text="Action" /></th>
										</tr>
										<c:set var="e" value="0" scope="page" />
										<c:forEach items="${command.attachDocsList}" var="lookUp">
											<tr>
												<td>${e+1}</td>
												<td><apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="vehicleMaintenanceMgmt.html?Download" /></td>
												<td class="text-center"><a href='#' id="deleteFile"
														onclick="return false;" class="btn btn-danger btn-sm"><i
														class="fa fa-trash"></i></a> <form:hidden path=""
														value="${lookUp.attId}" /></td>		
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
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveVehicleMaintenance(this);">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
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
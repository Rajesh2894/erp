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
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
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
						path="vehicleMaintenanceDTO.vemMetype"
						cssClass=" form-control mandColorClass " isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown" />

					<apptags:date fieldclass="datepicker" readonly="true"
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
												path="vehicleMaintenanceDTO.vemDowntime"
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
									<apptags:input labelCode="vehicle.maintenance.repair.reading"
										path="vehicleMaintenanceDTO.vemReading"
										cssClass="hasNumber mandColorClass" isMandatory="true"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input
										labelCode="vehicle.maintenance.total.cost.incurred"
										path="vehicleMaintenanceDTO.vemCostincurred"
										isMandatory="true" cssClass="hasDecimal text-right"></apptags:input>
									<apptags:input labelCode="vehicle.maintenance.receiptno"
										path="vehicleMaintenanceDTO.vemReceiptno"
										cssClass="hasNumber mandColorClass" isMandatory="true"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker" readonly="true"
										labelCode="vehicle.maintenance.receiptDate"
										datePath="vehicleMaintenanceDTO.vemReceiptdate"
										cssClass="custDate" isMandatory="true">
									</apptags:date>
									<apptags:input labelCode="vehicle.maintenance.expendiureHead"
										path="vehicleMaintenanceDTO.expenditureHead"
										cssClass="hasNumber mandColorClass" isDisabled="true"></apptags:input>
									<form:hidden path="vehicleMaintenanceDTO.expenditureId"
										id="expenditureId" />
								</div>
								<div class="form-group">
									<label for="vendorName"
										class="col-sm-2 control-label required-control"><spring:message
											code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></label>
									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="vehicleMaintenanceDTO.vendorId" id="vendorId">
											<form:option value="">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<c:forEach items="${command.vendors}" var="vendor">
												<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<apptags:textArea labelCode="vehicle.maintenance.remark"
										path="vehicleMaintenanceDTO.remark"></apptags:textArea>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="swm.fileupload" /></label>
									<div class="col-sm-4">
										<small class="text-blue-2"> <spring:message code="fuelling.advice.file.upload"
												text="(Upload File upto 5MB and only pdf,doc,xls,xlsx)" />
										</small>
										<apptags:formField fieldType="7" labelCode="" hasId="true"
											fieldPath="" isMandatory="false" showFileNameHTMLId="true"
											fileSize="BND_COMMOM_MAX_SIZE"
											maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
											currentCount="0" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="fuelling.advice.total.deduction.amount" text="Total Deduction Amount" /></label>
									<div class="col-sm-4">
										<form:input path="vehicleMaintenanceDTO.dedAmt"
											readonly="true"
											class="form-control mandColorClass text-right" id="dedAmt"></form:input>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-target="#a1" data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#a1"><spring:message
									code="fuelling.advice.tax.deduction.details" text="Tax Deduction Details" /></a>
						</h4>
					</div>
					<c:set var="d" value="0" scope="page" />
					<table class="table table-bordered table-striped"
						id="mbTaxDetailsTab">
						<thead>
							<tr>
								<th width="5%"><spring:message
										code="vehicle.maintenance.master.id" text="Sr. No." /><span
									class="mand">*</span></th>
								<th width="25%"><spring:message
										code="vehicle.maintenance.master.tax.category"
										text="Tax Category" /><span class="mand">*</span></th>

								<th width="8%"><spring:message
										code="vehicle.maintenance.master.addition.deduction"
										text="Addition/Deduction" /><span class="mand">*</span></th>

								<th width="5%"><spring:message
										code="vehicle.maintenance.master.amount"
										text="Amount/Percentage" /><span class="mand">*</span></th>

								<th width="10%"><spring:message
										code="vehicle.maintenance.master.value" text="Value" /><span
									class="mand">*</span></th>
							</tr>
						</thead>
						<tr class="appendableClass">
							<td align="center"><form:input path=""
									cssClass="form-control" id="sequence${d}" value="${d+1}"
									readonly="true" /></td>
							<td align="center"><form:select
									path="vehicleMaintenanceDTO.dedAcHeadId"
									cssClass="form-control  mandColorClass">
									<form:option value="">
										<spring:message code="solid.waste.select" text="Select" />
									</form:option>
									<c:forEach items="${command.vehicleMaintenanceList}"
										var="secheadId">
										<form:option value="${secheadId.dedAcHeadId}">${secheadId.dedAcHead}</form:option>
									</c:forEach>
								</form:select></td>
							<td><form:select path=""
									class="form-control chosen-select-no-results" id="TaxType"
									disabled="false">
									<form:option value="">
										<spring:message code="solid.waste.select" text="Select" />
									</form:option>
									<form:option value="A">
										<spring:message code="swm.vehicle.maint.addition" text="Addition" />
									</form:option>
									<form:option value="D">
										<spring:message code="swm.vehicle.maint.deduction" text="deduction" />
									</form:option>
								</form:select></td>
							<td><form:select path=""
									cssClass="form-control mandColorClass chosen-select-no-results"
									id="payTypeId" data-rule-required="true">
									<form:option value="">
										<spring:message code='master.selectDropDwn' />
									</form:option>
									<c:forEach items="${command.valueTypeList}" var="payType">
										<form:option value="${payType.lookUpId }"
											code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
									</c:forEach>
								</form:select></td>
							<td><form:input path="" class="form-control text-right"
									disabled="false" id="taxValue"
									onchange="getAmountFormatInDynamic()" /></td>
						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</table>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveVehicleMaintenance(this);">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning" onclick="resetForm();">
						<spring:message code="solid.waste.reset" text="Reset" />
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
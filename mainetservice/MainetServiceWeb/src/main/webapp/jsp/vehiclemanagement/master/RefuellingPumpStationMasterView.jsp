<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
				<spring:message code="refueling.pump.master.heading"
					text="Refueling And Pump Master" />
			</h2>
			<apptags:helpDoc url="RefuellingPumpStationMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="fuelPumpMas.html"
				name="RefuellingPumpStationMaster" id="RefuellingPumpMasterForm"
				class="form-horizontal">
				
				<div class="form-group">
					<label class="control-label col-sm-2 " for="Census"> <spring:message
							code="refueling.pump.master.type" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<div class='input-group-field'>
								<form:select id="puPutype" path="pumpMasterDTO.puPutype"
									class=" form-control " disabled="true">
									<form:option value="">
										<spring:message code="solid.waste.select" text="Select" />
									</form:option>
									<c:forEach items="${command.getLevelData('PMP')}" var="lookup">
										<form:option value="${lookup.lookUpId}"
											code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
					<label for="Pump Name" class="col-sm-2 control-label "><spring:message
							code="refueling.pump.master.name" text="Pump Name" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type='text' path="pumpMasterDTO.puPumpname"
								class=' form-control ' disabled="true" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="vendorName" class="col-sm-2 control-label "><spring:message
							code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:select class=" form-control chosen-select-no-results"
								path="pumpMasterDTO.vendorId" id="vendorId" disabled="true">
								<c:forEach items="${vendors}" var="vendor">
									<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<label for="Address" class="col-sm-2 control-label "> <spring:message
							code="disposal.site.master.adress" text="Address" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type='text' path="pumpMasterDTO.puAddress"
								class=' form-control' disabled="true" />

						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="population.master.status" text="Status" /> </label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								id="status1" path="pumpMasterDTO.puActive" value="Y"
								checked="checked" disabled="true" /> <spring:message
								code="swm.Active" text="Active" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="status2" path="pumpMasterDTO.puActive" value="N"
								disabled="true" /> <spring:message code="swm.Inactive"
								text="Inactive" />
						</label>
					</div>
				</div>
				<div class="table-responsive">
					<table id="refuellingPumpMaster" summary="Dumping Ground Data"
						class="table table-bordered table-striped">
						<c:set var="d" value="0" scope="page"></c:set>
						<thead>
							<tr>
								<th width="100"><spring:message
										code="vehicle.maintenance.master.id" /></th>
								<th><spring:message
										code="refueling.pump.master.sold.item.types" /></th>
								<th><spring:message code="refueling.pump.master.unit" /></th>
								<th width="100"><spring:message
										code="refueling.pump.master.isApplicable" /></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach var="refuellingPumpMasterList"
								items="${command.pumpMasterDTO.tbSwPumpFuldets}"
								varStatus="status">
								<tr class="appendableClass">
									<td>${d+1}</td>
									<td><form:select
											path="pumpMasterDTO.tbSwPumpFuldets[${d}].puFuid"
											class=" form-control " id="puFuid${d}" disabled="true">
											<form:option value="">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData('PTF')}"
												var="lookup">
												<form:option value="${lookup.lookUpId}"
													code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:select
											path="pumpMasterDTO.tbSwPumpFuldets[${d}].puFuunit"
											class="form-control "
											cssClass="form-control required-control" id="puFuunit${d}"
											disabled="true">
											<form:option value="">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData('UOM')}"
												var="lookup">
												<c:if
													test="${lookup.otherField eq 'CAPACITY' or (lookup.otherField eq 'WEIGHT' and lookup.descLangFirst eq 'Kilogram')}">
													<form:option value="${lookup.lookUpId}"
														code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
												</c:if>
											</c:forEach>
										</form:select></td>
									<td class="text-center"><form:checkbox
											path="pumpMasterDTO.tbSwPumpFuldets[${d}].puActive"
											id="puApplicable${d}" value="Y" disabled="true" /></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="text-center padding-top-10">

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backPumpMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

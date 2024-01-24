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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/vehicle_management/PumpMaster.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="fueling.pump.master.form"
					text="Fuel Pump Master" />
			</h2>
			<apptags:helpDoc url="RefuellingPumpStationMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="fuelPumpMas.html"
				name="RefuellingPumpStationMaster"
				id="RefuellingPumpStationMasterForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				
				<form:input type="hidden" path="mode" id="mode"/>
				
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="PumpType"> <spring:message
							code="refueling.pump.master.type" text="Pump Type" />
					</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<div class='input-group-field'>
								<form:select id="puPutype" path="pumpMasterDTO.puPutype"
									cssClass=" mandColorClass form-control" label="Select">
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
					<label class="control-label col-sm-2 required-control"
						for="PumpName"> <spring:message
							code="refueling.pump.master.name" text="Pump Name" />
					</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input id="puPumpname" type='text' maxlength="50"
								path="pumpMasterDTO.puPumpname"
								cssClass='mandColorClass form-control' />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="Address"> <spring:message
							code="disposal.site.master.adress" />
					</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:textarea cssClass="mandColorClass form-control"
								maxlength="250" id="puAddress" path="pumpMasterDTO.puAddress" />
						</div>
					</div>
					<label for="vendorName"
						class="col-sm-2 control-label required-control"><spring:message
							code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:select
								class="mandColorClass form-control chosen-select-no-results"
								path="pumpMasterDTO.vendorId" id="vendorId">
								<form:option value="">
									<spring:message code="solid.waste.select" text="Select" />
								</form:option>
								<c:forEach items="${vendors}" var="vendor">
									<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>
				<div class="table-responsive">
					<table id="refuellingPumpMaster" summary="Dumping Ground Data"
						class="table table-bordered table-striped dpsM">
						<c:set var="d" value="0" scope="page"></c:set>
						<thead>
							<tr>
								<th width="10%"><spring:message
										code="vehicle.maintenance.master.id" /></th>
								<th><spring:message
										code="refueling.pump.master.sold.item.types" /></th>
								<th><spring:message code="refueling.pump.master.unit" /></th>
								<th width="10%"><spring:message
										code="refueling.pump.master.isApplicable" /></th>
								<th width="10%"><spring:message
										code="vehicle.master.vehicle.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<tr class="appendableClass">
								<td align="center"><form:input path=""
										cssClass="form-control mandColorClass " id="sequence${d}"
										value="${d+1}" disabled="true" /></td>
								<td><form:select
										path="pumpMasterDTO.tbSwPumpFuldets[${d}].puFuid"
										cssClass="mandColorClass form-control required-control"
										id="puFuid${d}" onchange="targetInfo()">
										<form:option value="">
											<spring:message code="solid.waste.select" text="Select" />
										</form:option>
										<c:forEach items="${command.getLevelData('PTF')}" var="lookup">
											<form:option value="${lookup.lookUpId}"
												code="${lookup.lookUpCode}">${lookup.descLangFirst}
												</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:select
										path="pumpMasterDTO.tbSwPumpFuldets[${d}].puFuunit"
										class="form-control " cssClass="form-control required-control"
										id="puFuunit${d}">
										<form:option value="">
											<spring:message code="solid.waste.select" text="Select" />
										</form:option>
										<c:forEach items="${command.getLevelData('UOM')}" var="lookup">
											<c:if
												test="${lookup.otherField eq 'CAPACITY' or (lookup.otherField eq 'WEIGHT' and lookup.descLangFirst eq 'Kilogram')}">
												<form:option value="${lookup.lookUpId}"
													code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
											</c:if>
										</c:forEach>
									</form:select></td>  
								<td class="text-center"><form:checkbox
										path="pumpMasterDTO.tbSwPumpFuldets[${d}].puActive"
										id="puApplicable${d}" value="" onclick="clickCheckBox();" /></td>
								<td><a href="#" data-toggle="tooltip"
									data-placement="top" class="btn btn-blue-2  btn-sm"
									data-original-title="<spring:message code="fueling.pump.master.add" text="Add" />" 
									onclick="addEntryData('${d}');"><strong class="fa fa-plus"></strong><span class="hide"></span></a>
									<a href="#" data-toggle="tooltip" data-placement="top"
									class="btn btn-danger btn-sm" data-original-title="<spring:message
												code="pump.master.delete" text="Delete" />"
									onclick="deleteEntry($(this),'removedIds');"> <strong
										class="fa fa-trash"></strong> <span class="hide"><spring:message
												code="solid.waste.delete" text="Delete" /></span>
								</a></td>
							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</tbody>
					</table>
				</div>
<form:input type="hidden" path="" id="status"/> 		
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="savePumpmasterData(this);">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
					<%-- <button type="Reset" class="btn btn-warning"
						onclick="resetScheme();">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button> --%>

					<button type="button" id="reset"
						onclick="openForm('fuelPumpMas.html','AddRefuellingPumpMaster')"
						class="btn btn-warning">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
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
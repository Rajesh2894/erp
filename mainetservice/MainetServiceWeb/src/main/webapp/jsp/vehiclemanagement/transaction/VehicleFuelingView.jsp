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
<script type="text/javascript"
	src="js/vehicle_management/VehicleFuelling.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.fuelling.heading"
					text="Vehicle Fueling" />
			</h2>
			<apptags:helpDoc url="vehicleFuel.html" />
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			
			
			<form:form action="vehicleFuel.html" name="VehicleFuelling"
				id="VehicleFuelingForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="fuelling.details" text="Fueling Details" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="vehicle.fuelling.date"
										datePath="vehicleFuellingDTO.vefDate" isMandatory="true"
										cssClass="custDate  vefDate" isDisabled="true">
									</apptags:date>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control " for="Census"><spring:message
											code="vehicle.maintenance.master.type" text="Census(year)" /></label>
									<c:set var="baseLookupCode" value="VCH" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="vehicleFuellingDTO.veVetype" cssClass="form-control" 
										hasId="true" selectOptionLabelCode="selectdropdown" isMandatory="true"
										changeHandler="showVehicleRegNo()" disabled="true" />
									<label class="control-label col-sm-2 required-control"> <spring:message
											code="vehicle.maintenance.regno" /></label>
									<div class="col-sm-4">
										<form:select cssClass=" chosen-select-no-results"
											path="vehicleFuellingDTO.veId" id="vemId" disabled="true">
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
									<apptags:input labelCode="vehicle.fuelling.vehicleReadingInKm"
										path="vehicleFuellingDTO.vefReading" isMandatory="true" cssClass="hasNumber"
										isDisabled="true"></apptags:input>
									<apptags:input labelCode="vehicle.fuelling.driverName"
										path="vehicleFuellingDTO.driverName" cssClass="hasNameClass"
										isDisabled="true"></apptags:input>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="vehicle.fuel.details" text="Fuel Details" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"> <spring:message
											code="vehicle.fuelling.pump.name" /></label>
									<div class="col-sm-4">
										<form:select cssClass="form-control chosen-select-no-results"
											path="vehicleFuellingDTO.puId" id="puId"
											data-rule-required="true" onchange="targetInfo()"
											disabled="true">
											<form:option value="">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<c:forEach items="${pumps}" var="pump">
												<form:option value="${pump.puId}">${pump.puPumpname}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:input labelCode="vehicle.fuelling.adviceno"
										path="vehicleFuellingDTO.vefDmno"
										cssClass="hasNumber mandColorClass form-control"
										isMandatory="true" isDisabled="true"></apptags:input>
									<apptags:date fieldclass="datepicker"
										labelCode="vehicle.fuelling.adviceDate"
										datePath="vehicleFuellingDTO.vefDmdate" cssClass="custDate" isMandatory="true"
										isDisabled="true">
								</apptags:date>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<c:if test="${! command.attachDocsList.isEmpty()}">
					<div class="table-responsive">
						<table class="table table-bordered table-striped" id="deleteDoc">
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
											filePath="${lookUp.attPath}"
											actionUrl="vehicleFuel.html?Download" /></td>
								</tr>
								<c:set var="e" value="${e + 1}" scope="page" />
							</c:forEach>
						</table>
					</div>
				</c:if>
				
				<div id="pumpFuelDets">
					<div class="table-responsive">
						<table id="vehicleFuelling" summary="Vehicle Fuelling Data"
							class="table table-bordered table-striped">
							<c:set var="d" value="0" scope="page"></c:set>
							<thead>
								<tr>
									<th width="100"><spring:message
											code="vehicle.maintenance.master.id" /></th>
									<th><spring:message code="PetrolRequisitionDTO.fuelType" /></th>
									<th><spring:message code="vehicle.fuelling.unit" /></th>
									<th width="10%"><spring:message
											code="vehicle.fuelling.quantity" /></th>
									<th width="10%"><spring:message
											code="vehicle.fuelling.cost" /></th>
									<th width="10%"><spring:message
											code="vehicle.fuelling.totalCost" /></th>

								</tr>
							</thead>
							<tfoot>
								<tr>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th align="right"><span style="font-weight: bold"><spring:message
												code="swm.total" text="Total" /></span></th>
									<th align="center"><div align="center" class="input-group">
											<form:input path="vehicleFuellingDTO.vefRmamount"
												class="form-control text-right" id="id_total"
												readOnly="true" />
										</div></th>

								</tr>
							</tfoot>
							<tbody>
								<c:forEach items="${command.vehicleFuellingDTO.tbSwVehiclefuelDets}" var="item"
									varStatus="status">
									<c:if test="${command.vehicleFuellingDTO.tbSwVehiclefuelDets[status.index].pumpFuelName ne null}">
									<tr class="appendableClass">
									
										<td align="center">${status.count}</td>
										<td align="center">
											<div class="input-group">
												<form:input
													path="vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].pumpFuelName"
													cssClass="form-control text-left"
													id="pumpFuelName${status.index}" disabled="true" />

											</div>
										</td>
										<td align="center">
											<div class="input-group">
												<form:input
													path="vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].fuelUnit"
													cssClass="form-control text-left"
													id="fuelUnit${status.index}" disabled="true" />

											</div>
										</td>
										<%-- 	<td>${item.puFuName}</td>
										<form:hidden
												path="vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].pfuId"
												value="${item.pfuId }" readonly="true"
												id="pfuId${status.index}" /> --%>
										<%-- 		<td>${item.puFuUnitName }</td>
										<form:hidden
												path="vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].vefdUnit"
												value="${item.puFuunit}" readonly="true"
												id="vefdUnit${status.index}" /> --%>
										<td align="center">
											<div class="input-group">
												<form:input
													path="vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].vefdQuantity"
													cssClass="form-control hasDecimal text-right"
													id="vefdQuantity${status.index}" disabled="true" />

											</div>
										</td>
										<td align="center">
											<div class="input-group">
												<form:input
													path="vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].vefdCost"
													cssClass="form-control hasDecimal text-right"
													readonly="true" onchange="sum()"
													id="vefdCost${status.index}" disabled="true" />
											</div>
										</td>
										<td align="right"><form:input
												path="vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].multi"
												cssClass="form-control text-right" id="multi${status.index}"
												readonly="true" />
									</tr>
									</c:if>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
				
				<div id="pumpFuelDets"></div>
				<div class="text-center padding-top-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						title='<spring:message code="solid.waste.back" text="Back" />'
						onclick="backVehicleFuellingForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
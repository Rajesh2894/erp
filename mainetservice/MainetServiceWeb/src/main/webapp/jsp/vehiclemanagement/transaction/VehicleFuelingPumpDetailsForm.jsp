<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/vehicle_management/VehicleFuelling.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<div class="table-responsive">
	<table id="vehicleFuelling" summary="Vehicle Fuelling Data"
		class="table table-bordered table-striped">
		<c:set var="d" value="0" scope="page"></c:set>
		<thead>
			<tr>
				<th width="100"><spring:message
						code="vehicle.maintenance.master.id" /></th>
				<th><spring:message code="vehicle.fuelType" /></th>
				<th><spring:message code="vehicle.fuelling.unit" /></th>
				<th width="10%"><spring:message
						code="vehicle.fuelling.quantity" /></th>
				<th width="10%"><spring:message code="vehicle.fuelling.cost" /></th>
				<th width="10%"><spring:message
						code="vehicle.fuelling.totalCost" /></th>
				<!-- <th width="50"></th> -->
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
						<form:input path="command.vehicleFuellingDTO.vefRmamount"
							class="form-control mandColorClass text-right" id="id_total"
							readOnly="true" />
					</div></th>
				<!-- <th></th> -->
			</tr>
		</tfoot>
		<tbody>
			<c:forEach items="${pumpFuelDetails}" var="item" varStatus="status">
			<input type="hidden" name = ""  id ="puFuName${status.index}" value ="${item.puFuName}"/>
			<input type="hidden" name = ""  id ="puActive${status.index}" value ="${item.puActive}"/>
				<tr class="appendableClass">

					<td align="center"><form:input
							path="command.vehicleFuellingDTO.seqNo"
							cssClass="form-control mandColorClass " id="sequence${d}"
							value="${status.count}" disabled="true" /></td>

					<%-- <td align="center">${status.count}</td> --%>
					<td>${item.puFuName}</td>
					<form:hidden
						path="command.vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].pfuId"
						value="${item.pfuId}" readonly="true" id="pfuId${status.index}" />
					
					<td>${item.puFuUnitName}</td>
					<form:hidden
						path="command.vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].vefdUnit"
						value="${item.puFuunit}" readonly="true"
						id="vefdUnit${status.index}" />
					<td align="center">
						<div class="input-group">
							<form:input
								path="command.vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].vefdQuantity"
								onkeypress="return hasAmount(event, this, 4, 2)"
								cssClass="form-control mandColorClass hasDecimal text-right" onblur="sum()"
								id="vefdQuantity${status.index}" />

						</div>
					</td>
					<td align="center">
						<div class="input-group">
							<form:input
								path="command.vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].vefdCost"
								onkeypress="return hasAmount(event, this, 4, 2)"
								cssClass="form-control mandColorClass hasDecimal text-right"
								onchange="sum()" id="vefdCost${status.index}" />

						</div>
					</td>
					<td align="right"><form:input path="command.vehicleFuellingDTO.tbSwVehiclefuelDets[${status.index}].multi" cssClass="form-control text-right" id="multi${status.index}" readonly="true" />
					</td>
					<%-- <td><a class="btn btn-danger btn-sm" data-original-title="Delete"
						onclick="deleteEntry($(this),'removedIds');"> <strong
							class="fa fa-trash"></strong> <span class="hide"><spring:message
									code="solid.waste.delete" text="Delete" /></span>
					</a></td> --%>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>



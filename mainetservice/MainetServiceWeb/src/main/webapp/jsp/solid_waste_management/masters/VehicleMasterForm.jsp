<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/solid_waste_management/VehicleMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}

.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.master.heading" text="Vehicle Master" />
			</h2>
			<apptags:helpDoc url="VehicleMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand" text="Field with " /><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="VehicleMaster.html" name="VehicleMaster" id="VehicleMasterId" cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="vehicleMasterDTO.veVetype"
						cssClass="form-control required-control chosen-select-no-results" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
					<label class="control-label col-sm-2 required-control" for="VehicleRegNo"><spring:message
							code="vehicle.master.vehicle.no" text="Vehicle No." /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" path="vehicleMasterDTO.veNo" class="form-control" id="VehicleRegNo" maxlength=""
							readonly="true"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 " for="EngineNo"><spring:message
							code="vehicle.master.vehicle.engine.no" text="Engine No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veEngSrno" type="text" class="form-control " id="EngineNo"></form:input>
					</div>
					<label class="control-label col-sm-2 required-control" for="MakeModel"><spring:message code="vehicle.master.vehicle.reg.no"
							text="Vehicle Reg .No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veRegNo" type="text" class="form-control " id="veReg"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="VehicleStandardWeight"><spring:message
							code="vehicle.master.vehicle.standard.weight" text="Vehicle Standard Weight" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path="vehicleMasterDTO.veStdWeight" class=' mandColorClass form-control hasDecimal text-right'
								id="stardWgt" placeholder="" />
							<span class="input-group-addon"><spring:message code="swm.kgs" text="Kilograms" /></span>

						</div>
					</div>
					<label class="control-label col-sm-2" for="MakeModel"><spring:message
							code="vehicle.master.vehicle.model" text="Make / Model / Manufacturer" /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veModel" type="text" class="form-control" id="MakeModel"></form:input>
					</div>
				</div>

				<div class=" form-group">
					<label for="text-1515133683412" class="col-sm-2 required-control control-label "><spring:message
							code="vehicle.tracking" text="Vehicle Tracking" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.veGpsDeviceid" class="form-control mandColorClass" label="Select"
							id="VehicleGPSID">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="VGPS001">
								<spring:message code="vehicle.tracking.gps" text="Gps Based" />
							</form:option>
							<form:option value="VAPS002">
								<spring:message code="vehicle.tracking.app" text="App Based" />
							</form:option>
							<form:option value="VMAS003">
								<spring:message code="vehicle.tracking.manual" text="Mannual" />
							</form:option>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message code="vehicle.master.vehicle.department.own.vehicle"
							text="Department owned vehicle" /><span class="mand">*</span> </label>
					<div class="col-sm-4">

						<label class="radio-inline" for="DepartmentownedYes"> <form:radiobutton name="Department"
								path="vehicleMasterDTO.veFlag" checked="checked" value="Y" id="DepartmentownedYes"></form:radiobutton> <spring:message
								code="solid.waste.Yes" text="Yes" />
						</label> <label class="radio-inline" for="DepartmentownedNo"> <form:radiobutton name="Department"
								path="vehicleMasterDTO.veFlag" value="N" id="DepartmentownedNo"></form:radiobutton> <spring:message
								code="solid.waste.No" text="No" />
						</label>
					</div>
				</div>
				<div class="yes hidebox">
					<div class="form-group">
						<apptags:date fieldclass="datepicker" labelCode="vehicle.master.vehicle.purchase.date"
							datePath="vehicleMasterDTO.vePurDate" isMandatory="false" cssClass="custDate mandColorClass">
						</apptags:date>

						<label class="control-label col-sm-2" for="PurchasePrice"><spring:message
								code="vehicle.master.vehicle.purchase.price" text="Purchase Price" /></label>

						<div class="col-sm-4">
							<div class="input-group">
								<form:input type="text" path="vehicleMasterDTO.vePurPrice" class="form-control hasDecimal text-right"
									id="PurchasePrice"></form:input>
								<label class="input-group-addon"><i class="fa fa-inr"></i><span class="hide"><spring:message
											code="vehicle.master.vehicle.rupees" text="Rupees" /></span></label>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2" for="SourceofPurchase"><spring:message
								code="vehicle.master.vehicle.source.purchase" text="Source of Purchase" /></label>
						<div class="col-sm-4">
							<form:select path="vehicleMasterDTO.vePurSource" class="form-control required-control" name="vmVendorname"
								id="vePurSource" onchange="" disabled="false">
								<form:option value="">
									<spring:message code="solid.waste.select" text="Select" />
								</form:option>
								<c:forEach items="${command.vendorList}" varStatus="status" var="vendor">
									<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<label class="control-label col-sm-2" for="AssetCode"><spring:message
								code="vehicle.master.vehicle.asset.code" text="Asset Code" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input type="text" path="vehicleMasterDTO.assetNo" class="form-control " id="AssetCode"></form:input>
								<label class="input-group-addon"><i class="fa fa-globe"></i><span class="hide"><spring:message
											code="vehicle.master.vehicle.asset.code" text="AssetCode" /></span></label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2 " for="MakeModel"><spring:message
								code="vehicle.master.vehicle.chasis.no" text="Chasis No." /></label>
						<div class="col-sm-4">
							<form:input name="" path="vehicleMasterDTO.veChasisSrno" type="text" class="form-control " id="chasisno"></form:input>
						</div>
					</div>
				</div>


				<div class="no hidebox" style="display: none">
					<div class="form-group">
						<label class="control-label col-sm-2 required-control" for="VendorName"><spring:message
								code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></label>
						<div class="col-sm-4">
							<form:select path="vehicleMasterDTO.vmVendorid" class="form-control required-control " name="vmVendorname"
								onchange="getContractNo()" id="vmVendorname">
								<form:option value="">
									<spring:message code="solid.waste.select" text="Select" />
								</form:option>
								<c:forEach items="${command.vendorList}" varStatus="status" var="vendor">
									<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<label class="control-label col-sm-2 required-control" for="ContractNo"><spring:message
								code="vehicle.master.vehicle.contract.no" text="Contract No." /></label>
						<div class="col-sm-4">
							<form:select path="vehicleMasterDTO.contId" class="form-control  " name="vmContractno" id="vmContractno"
								onchange="getContrctDate()">
								<option value=""><spring:message
										code="solid.waste.select" text="Select" /></option>
								<c:forEach items="${command.contractlist}" varStatus="status" var="contract">
									<form:option value="${contract.vendorName}" fDate="${contract.fromDate}" tDate="${contract.toDate}">${contract.contractNo}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<apptags:date fieldclass="date" labelCode="contract.mapping.contract.from.date"
							datePath="vehicleMasterDTO.veRentFromdate" readonly="true" cssClass="custDate mandColorClass">
						</apptags:date>
						<apptags:date fieldclass="date" labelCode="contract.mapping.contract.to.date"
							datePath="vehicleMasterDTO.veRentTodate" readonly="true" cssClass="custDate mandColorClass">
						</apptags:date>
					</div>

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2" for="Remarks"><spring:message code="vehicle.master.vehicle.remarks"
							text="Remarks" /></label>
					<div class="col-sm-4">
						<form:textarea path="vehicleMasterDTO.veRemarks" class="form-control" id="Remarks"></form:textarea>
					</div>
				</div>


				<h4>
					<spring:message code="swm.vehicle.capacity.head" text="Capacity Of Vehicle" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="empDetails">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="WasteDetailTable" class="table table-striped table-bordered appendableClass wasteDetails">
								<thead>
									<tr>
										<th width="100"><spring:message code="vehicle.maintenance.master.id" /></th>
										<th><spring:message code="swm.wastetype" text="Waste Type" /><i class="text-red-1">*</i></th>
										<th><spring:message code="swm.capacity.kg" text="Capacity In Kg" /><i class="text-red-1">*</i></th>
										<th scope="col" width="8%"><spring:message code="solid.waste.action" text="Action" /></th>		
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="1" class="text-center" rowspan="1"></th>
										<th align="left"><spring:message code="" text="Total Capacity" /></th>
										<th><form:input path="" cssClass="form-control mandColorClass  text-right" onchange=""
												id="id_grand_Total" disabled="" readonly="true" /></th>
										<th width="10%"></th>
									</tr>
								</tfoot>
								<tbody>
									<tr class="firstWasteRow">
										<td align="center" width="10%"><form:input path="" cssClass="form-control mandColorClass "
												id="sequence${d}" value="${d+1}" disabled="true" /></td>
										<td><c:set var="baseLookupCode" value="WTY" /> <form:select
												path="vehicleMasterDTO.tbSwVehicleMasterdets[${d}].wasteType" cssClass="form-control mandColorClass"
												id="wasteType${d}" onchange="" disabled="" data-rule-required="true">
												<form:option value="0">
													<spring:message code="solid.waste.select" text="select" />
												</form:option>
												<c:forEach items="${command.getSortedLevelData(baseLookupCode,1)}" var="lookUp">
													<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:input type="text" path="vehicleMasterDTO.tbSwVehicleMasterdets[${d}].veCapacity"
												class="form-control hasDecimal text-right" id="wasteCId${d}" onchange="sum1()"></form:input></td>
										<td class="text-center">
										<a href="javascript:void(0);" data-toggle="tooltip" title="Add"
											data-placement="top" onclick="addEntryData('WasteDetailTable');" class=" btn btn-success btn-sm"><i
												class="fa fa-plus-circle"></i></a>
										<a href="#" data-toggle="tooltip" data-placement="top" class="btn btn-danger btn-sm"
											onclick="deleteEntry($(this),'removedIds');"> <strong class="fa fa-trash"></strong> <span class="hide"><spring:message
														code="solid.waste.delete" text="Delete" /></span>
										</a></td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit" name="button-submit"
						onclick="saveVehicleMasterForm(this);">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning" onclick="resetForm();">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<button type="button" class="button-input btn btn-danger" name="button-Cancel" value="Cancel" style=""
						onclick="backVehicleMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>

</div>
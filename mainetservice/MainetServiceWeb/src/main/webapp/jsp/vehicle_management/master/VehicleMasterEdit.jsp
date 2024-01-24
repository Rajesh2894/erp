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
	src="js/vehicle_management/VehicleMaster.js"></script>
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
				<span><spring:message code="solid.waste.mand"
						text="Field with " /><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="VehMaster.html" name="VehicleMaster"
				id="VehicleMasterId" cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<%-- 	<div class="form-group">
					<label class="control-label col-sm-2" for="VehicleID"><spring:message
							code="vehicle.master.vehicleid" text="Vehicle ID" /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veId" type="text"
							class="form-control" id="VehicleID"></form:input>
					</div> 
					<label class="control-label col-sm-2" for="VehicleGPSID"><spring:message
							code="vehicle.master.vehicle.gps.id.track"
							text="Vehicle GPS Tracking Device ID" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" path=""
								class="form-control "  readonly="true" id="VehicleGPS"></form:input>
							<label class="input-group-addon"><i class="fa fa-globe"></i></label>
						</div>
					</div>
				</div> --%>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="VehicleRegNo"><spring:message
							code="vehicle.master.vehicle.no" text="Vehicle No." /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" path="vehicleMasterDTO.veNo"
							class="form-control" id="VehicleRegNo" readonly="true"></form:input>
					</div>

					<label class="control-label col-sm-2 required-control"
						for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMasterDTO.veVetype"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 " for="EngineNo"><spring:message
							code="vehicle.master.vehicle.engine.no" text="Engine No." /></label>

					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veEngSrno" type="text"
							class="form-control" id="EngineNo"></form:input>
					</div>
					<label class="control-label col-sm-2 " for="MakeModel"><spring:message
							code="" text="Vehicke Reg.No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veRegNo" type="text"
							class="form-control " id="veReg"></form:input>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="VehicleStandardWeight"><spring:message
							code="vehicle.master.vehicle.standard.weight"
							text="Vehicle Standard Weight" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path="vehicleMasterDTO.veStdWeight"
								class=' mandColorClass form-control hasDecimal text-right'
								id="stardWgt" placeholder="" />
							<span class="input-group-addon"><spring:message
									code="swm.kgs" text="Kilograms" /></span>

						</div>
					</div>
					<label class="control-label col-sm-2 required-control"
						for="MakeModel"><spring:message
							code="vehicle.master.vehicle.model"
							text="Make / Model / Manufacturer" /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veModel" type="text"
							class="form-control" id="MakeModel"></form:input>
					</div>
				</div>
				<div class=" form-group">
					<label for="text-1515133683412"
						class="col-sm-2 required-control control-label "><spring:message
							code="vehicle.tracking" text="Vehicle Tracking" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.veGpsDeviceid"
							class="form-control mandColorClass" label="Select"
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
					<label class="col-sm-2 control-label"><spring:message
							code="vehicle.master.vehicle.department.own.vehicle"
							text="Department owned vehicle" /><span class="mand">*</span> </label>
					<div class="col-sm-4">
						<label class="radio-inline" for="DepartmentownedYes"> <form:radiobutton
								name="Department" path="vehicleMasterDTO.veFlag" value="Y"
								id="DepartmentownedYes" disabled="true"></form:radiobutton> <spring:message
								code="solid.waste.Yes" text="Yes" />
						</label> <label class="radio-inline" for="DepartmentownedNo"> <form:radiobutton
								name="Department" path="vehicleMasterDTO.veFlag" value="N"
								id="DepartmentownedNo" disabled="true"></form:radiobutton> <spring:message
								code="solid.waste.No" text="No" />
						</label>
					</div>
				</div>
				<c:if test="${command.vehicleMasterDTO.veFlag eq 'Y'}">
					<div class="yes hidebox">
						<div class="form-group">
							<apptags:date fieldclass="datepicker" isDisabled="true"
								labelCode="vehicle.master.vehicle.purchase.date"
								datePath="vehicleMasterDTO.vePurDate" isMandatory="true"
								cssClass="custDate mandColorClass">
							</apptags:date>
							<label class="control-label col-sm-2" for="PurchasePrice"><spring:message
									code="vehicle.master.vehicle.purchase.price"
									text="Purchase Price" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input type="text" path="vehicleMasterDTO.vePurPrice"
										class="form-control hasDecimal text-right" id="PurchasePrice"></form:input>
									<label class="input-group-addon"><i class="fa fa-inr"></i><span
										class="hide"><spring:message
												code="vehicle.master.vehicle.rupees" text="Rupees" /></span></label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="SourceofPurchase"><spring:message
									code="vehicle.master.vehicle.source.purchase"
									text="Source of Purchase" /></label>
							<div class="col-sm-4">
								<form:select path="vehicleMasterDTO.vePurSource"
									class="form-control required-control" name="vmVendorname"
									id="vePurSource" onchange="" disabled="false">
									<form:option value="">
										<spring:message code="solid.waste.select" text="Select" />
									</form:option>
									<c:forEach items="${command.vendorList}" varStatus="status"
										var="vendor">
										<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
									</c:forEach>
								</form:select>
							</div>
							<label class="control-label col-sm-2"
								for="AssetCode"><spring:message
									code="vehicle.master.vehicle.asset.code" text="Asset Code" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input type="text" path="vehicleMasterDTO.assetNo"
										class="form-control" id="AssetCode"></form:input>
									<label class="input-group-addon"><i class="fa fa-globe"></i><span
										class="hide"><spring:message
												code="vehicle.master.vehicle.asset.code" text="AssetCode" /></span></label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="Remarks"><spring:message
									code="vehicle.master.vehicle.remarks" text="Remarks" /></label>
							<div class="col-sm-4">
								<form:textarea name="" path="vehicleMasterDTO.veRemarks" cols=""
									rows="" class="form-control" id="Remarks"></form:textarea>
							</div>
							<label class="control-label col-sm-2 " for="MakeModel"><spring:message
									code="vehicle.master.vehicle.chasis.no" text="Chasis No." /></label>
							<div class="col-sm-4">
								<form:input name="" path="vehicleMasterDTO.veChasisSrno"
									type="text" class="form-control " id="chasisno"></form:input>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${command.vehicleMasterDTO.veFlag eq 'N'}">
					<div class="no hidebox">
						<div class="form-group">
							<label class="required-control control-label col-sm-2"
								for="VendorName"><spring:message
									code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></label>
							<div class="col-sm-4">
								<form:select path="vehicleMasterDTO.vmVendorid"
									class="form-control required-control" name="vmVendorname"
									id="vmVendorname" onchange="getContractNo()" disabled="true">
									<c:forEach items="${command.vendorList}" varStatus="status"
										var="vendor">
										<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
									</c:forEach>
								</form:select>
							</div>
							<label class="control-label col-sm-2 required-control"
								for="ContractNo"><spring:message
									code="vehicle.master.vehicle.contract.no" text="Contract No." /></label>
							<div class="col-sm-4">
								<form:select path="vehicleMasterDTO.contId"
									class="form-control required-control " name="vmContractno"
									id="vmContractno" onchange="getContrctDate()" disabled="true">
									<option value=""><spring:message
											code="solid.waste.select" text="Select" /></option>
									<c:forEach items="${command.contractlist}" varStatus="status"
										var="contract">
										<form:option value="${contract.contId}"
											fDate="${contract.fromDate}" tDate="${contract.toDate}">${contract.contractNo}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<apptags:date fieldclass="date"
								labelCode="contract.mapping.contract.from.date" readonly="true"
								datePath="vehicleMasterDTO.veRentFromdate" isMandatory="true"
								cssClass="custDate mandColorClass">
							</apptags:date>
							<apptags:date fieldclass="date"
								labelCode="contract.mapping.contract.to.date" readonly="true"
								datePath="vehicleMasterDTO.veRentTodate" isMandatory="true"
								cssClass="custDate mandColorClass">
							</apptags:date>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2 " for="MakeModel"><spring:message
									code="vehicle.master.vehicle.chasis.no" text="Chasis No." /></label>
							<div class="col-sm-4">
								<form:input name="" path="vehicleMasterDTO.veChasisSrno"
									type="text" class="form-control " id="chasisno"></form:input>
							</div>
						</div>
					</div>
				</c:if>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="" text="Status" /> </label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								id="status1" path="vehicleMasterDTO.veActive" value="Y"
								checked="checked" /> <spring:message code="swm.Active"
								text="Active" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="status2" path="vehicleMasterDTO.veActive" value="N" /> <spring:message
								code="swm.Inactive" text="Inactive" />
						</label>
					</div>
				</div>
 --%>
				<h4>
					<spring:message code="" text="Deployment Details" />
				</h4>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="department"><spring:message code="" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.deptId"
							cssClass="form-control" id="department" data-rule-required="true"
							onchange="getEmployeeByDept(this);">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"
						for="employee">Driver</label>
					<div class="col-sm-4">
						<form:select id="driverName" path="vehicleMasterDTO.driverName"
							cssClass="form-control" data-rule-required="true">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${employees}" var="employee">
								<form:option value="${employee.empId}">${employee.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">


					<label class="col-sm-2 control-label"
						for="location"><spring:message code="" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.locId" cssClass="form-control"
							id="location" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${locations}" var="loc">
								<form:option value="${loc.locId}">${loc.locNameEng}-${loc.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"
						for="employee">Assigned To</label>
					<div class="col-sm-4">
						<form:select id="assignedTo" path="vehicleMasterDTO.assignedTo"
							cssClass="form-control" data-rule-required="true">

							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${employees}" var="empl">
								<form:option value="${empl.empId}">${empl.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="Remarks"><spring:message
							code="" text="Purpose" /></label>
					<div class="col-sm-4">
						<form:textarea path="vehicleMasterDTO.purpose"  maxLength="250"
						 	class="form-control" id="purpose"></form:textarea>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						name="button-submit" onclick="saveVehicleMasterForm(this);">
						<spring:message code="vehicle.submit" text="Submit" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backVehicleMasterForm();" id="button-Cancel">
						<spring:message code="vehicle.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>

</div>
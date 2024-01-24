<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/vehicle_management/VehicleMaster.js"></script>
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

.form-group label[for="DepartmentownedYes"], .form-group label[for="DepartmentownedN"]
	{
	margin: 6px 0 0;
}

#DepartmentownedYes, #DepartmentownedN {
	margin-top: 2px;
}

.form-group .control-label {
	text-transform: capitalize;
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
			<form:form action="veMasterCon.html" name="VehicleMaster"
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
				<h4>
					<spring:message code="vehicle.master.details"
						text="Vehicle Details" />
				</h4>

				<div class="form-group">
					<label class="control-label col-sm-2 " for="VehicleRegNo"><spring:message
							code="vehicle.master.vehicle.no" text="Vehicle No." /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" path="vehicleMasterDTO.veNo"
							class="form-control" id="VehicleRegNo" disabled="true"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMasterDTO.veVetype"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true"
						disabled="true" />

					<label class="control-label col-sm-2 required-control"
						for="EngineNo"><spring:message
							code="vehicle.master.vehicle.engine.no" text="Engine No." /></label>

					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veEngSrno" type="text"
							class="form-control" id="EngineNo" disabled="true"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="MakeModel"><spring:message
							code="vehicle.master.vehicle.chasis.no" text="Chasis No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veChasisSrno"
							maxlength="50" type="text" class="form-control " id="chasisno"
							disabled="true"></form:input>
					</div>
					<label class="control-label col-sm-2" for="MakeModel"><spring:message
							code="vehicle.master.vehicle.reg.no" text="Vehicle Reg.No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veNo" type="text"
							class="form-control" id="veReg" disabled="true"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="VehicleStandardWeight"><spring:message
							code="vehicle.master.vehicle.standard.weight"
							text="Vehicle Standard Weight" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path="vehicleMasterDTO.veStdWeight"
								class=' mandColorClass form-control hasNumber text-right'
								id="stardWgt" placeholder="" disabled="true" />

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
							class="form-control" id="MakeModel" disabled="true"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label for="vehicle.master.vehicle.capacity"
						class="col-sm-2 control-label "><spring:message
							code="vehicle.master.vehicle.capacity" text="Vehicle Capacity" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type='text' path="vehicleMasterDTO.veCapacity"
								class='mandColorClass form-control hasNumber' placeholder=""
								id="veCapacity" disabled="true" />
							<div class='input-group-field'>
								<form:select path="vehicleMasterDTO.veCaunit"
									cssClass="form-control required-control mandColorClass"
									disabled="true" id="veCaunit">
									<form:option value="">
										<spring:message code="solid.waste.search" text="Select" />
									</form:option>
									<c:forEach items="${command.getLevelData('UOW')}" var="lookup">
										<form:option value="${lookup.lookUpId}"
											code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
					<label for="text-1515133683412"
						class="col-sm-2 required-control control-label "><spring:message
							code="vehicle.tracking" text="Vehicle Tracking" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.veGpsDeviceid"
							class="form-control mandColorClass" label="Select"
							disabled="true" id="deAreaUnit">
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
				</div>

				<div class=" form-group">
					<label class="control-label col-sm-2" for="FuelCapacity"><spring:message
							code="vehicle.master.vehicle.fuel.capacity" text="Fuel Capacity" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type="text" path="vehicleMasterDTO.veFuelCapacity"
								cssClass="form-control hasDecimal text-right"
								id="veFuelCapacity"
								onkeypress="return hasAmount(event, this, 12, 2)"
								disabled="true"
								onchange="getAmountFormatInDynamic((this),'veFuelCapacity')" />
							<span class="input-group-addon"><spring:message
									code="vehicle.fuelCapacity" text="Litre" /></span>
						</div>
					</div>
					<label class="control-label col-sm-2 required-control"
						for="FuelType"><spring:message code="vehicle.fuelType"
							text="Fuel Type" /></label>
					<c:set var="fuelTypePrefix" value="VFT" />
					<apptags:lookupField
						items="${command.getLevelData(fuelTypePrefix)}"
						path="vehicleMasterDTO.fuelType" disabled="true"
						cssClass="form-control required-control chosen-select-no-results"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				</div>

				<div class=" form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="vehicle.master.vehicle.depart.owned.vehicle"
							text="Department Owned Vehicle" /><span class="mand">*</span> </label>
					<div class="col-sm-4">
						<label class="radio-inline" for="DepartmentownedYes"> <form:radiobutton
								name="Department" path="vehicleMasterDTO.veFlag" value="Y"
								id="DepartmentownedYes" disabled="true"></form:radiobutton> <spring:message
								code="solid.waste.Yes" text="Yes" />
						</label> <label class="radio-inline" for="DepartmentownedN"> <form:radiobutton
								name="Department" path="vehicleMasterDTO.veFlag" value="N"
								id="DepartmentownedN" disabled="true"></form:radiobutton> <spring:message
								code="solid.waste.No" text="No" />
						</label>
					</div>
					<c:if test="${command.vehicleMasterDTO.veFlag eq 'Y'}">
						<div class="yes hidebox">
							<apptags:date fieldclass="datepicker" readonly="true"
								isDisabled="true"
								labelCode="vehicle.master.vehicle.purchase.date"
								datePath="vehicleMasterDTO.vePurDate"
								cssClass="custDate mandColorClass">
							</apptags:date>
						</div>
					</c:if>
				</div>

				<c:if test="${command.vehicleMasterDTO.veFlag eq 'Y'}">
					<div class="yes hidebox">
						<div class="form-group">
							<label class="control-label col-sm-2" for="PurchasePrice"><spring:message
									code="vehicle.master.vehicle.purchase.price"
									text="Purchase Price" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input type="text" path="vehicleMasterDTO.vePurPrice"
										class="form-control text-right" id="PurchasePrice"
										disabled="true"></form:input>
									<label class="input-group-addon"><i class="fa fa-inr"></i><span
										class="hide"><spring:message
												code="vehicle.master.vehicle.rupees" text="Rupees" /></span></label>
								</div>
							</div>
							<label class="control-label col-sm-2" for="SourceofPurchase"><spring:message
									code="vehicle.master.vehicle.source.purchase"
									text="Source of Purchase" /></label>
							<div class="col-sm-4">
								<form:select path="vehicleMasterDTO.vePurSource"
									class="form-control required-control" name="vmVendorname"
									id="vePurSource" onchange="" disabled="true">
									<form:option value="">
										<spring:message code="solid.waste.select" text="Select" />
									</form:option>
									<c:forEach items="${command.vendorList}" varStatus="status"
										var="vendor">
										<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
				</c:if>

				<div class="form-group">
					<c:if test="${command.vehicleMasterDTO.veFlag eq 'Y'}">
						<div class="yes hidebox">
							<label class="control-label col-sm-2" for="AssetCode"><spring:message
									code="vehicle.master.vehicle.asset.code" text="Asset Code" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input type="text" path="vehicleMasterDTO.assetNo"
										class="form-control" id="AssetCode" disabled="true"></form:input>
									<label class="input-group-addon"><i class="fa fa-globe"></i><span
										class="hide"><spring:message
												code="vehicle.master.vehicle.asset.code" text="AssetCode" /></span></label>
								</div>
							</div>
						</div>
						<label class="control-label col-sm-2" for="Remarks"><spring:message
								code="vehicle.master.vehicle.remarks" text="Remarks" /></label>
						<div class="col-sm-4">
							<form:textarea name="" path="vehicleMasterDTO.veRemarks" cols=""
								rows="" class="form-control" id="Remarks" disabled="true"></form:textarea>
						</div>
					</c:if>
				</div>

				<c:if test="${command.vehicleMasterDTO.veFlag eq 'N'}">
					<div class="no hidebox">
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"
								for="VendorName"><spring:message
									code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></label>
							<div class="col-sm-4">
								<form:select path="vehicleMasterDTO.vmVendorid"
									class="form-control required-control " name="vmVendorname"
									onchange="getContract()" disabled="true" id="vmVendorname">
									<form:option value="">
										<spring:message code="solid.waste.select" text="Select" />
									</form:option>
									<c:forEach items="${command.vendorList}" varStatus="status"
										var="vendor">
										<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
									</c:forEach>
								</form:select>
							</div>
							<apptags:input labelCode="vehicle.rent"
								path="vehicleMasterDTO.veRentamt" isMandatory="true"
								maxlegnth="15" cssClass="hasDecimal" isDisabled="true" />
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"> <spring:message
									code="vehicle.from.date" text="From Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="vehicleMasterDTO.veRentFromdate"
										id="veRentFromdate"
										class="form-control mandColorClass datepicker dateValidation"
										value="" disabled="true" maxLength="10" />
									<label class="input-group-addon" for="fromDate"><i
										class="fa fa-calendar"></i><span class="hide"> <spring:message
												code="" text="icon" /></span><input type="hidden" id=fromDate></label>
								</div>
							</div>
							<label class="col-sm-2 control-label required-control"> <spring:message
									code="vehicle.to.date" text="To Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="vehicleMasterDTO.veRentTodate"
										id="veRentTodate"
										class="form-control mandColorClass datepicker dateValidation "
										value="" disabled="true" maxLength="10" />
									<label class="input-group-addon" for="toDate"><i
										class="fa fa-calendar"></i><span class="hide"> <spring:message
												code="" text="icon" /></span><input type="hidden" id=toDate></label>
								</div>
							</div>
						</div>
					</div>
				</c:if>

				<c:if test="${command.vehicleMasterDTO.veFlag eq 'N'}">
					<div class="form-group">
						<label class="control-label col-sm-2" for="Remarks"><spring:message
								code="vehicle.master.vehicle.remarks" text="Remarks" /></label>
						<div class="col-sm-4">
							<form:textarea name="" path="vehicleMasterDTO.veRemarks" cols=""
								rows="" class="form-control" id="Remarks" disabled="true"></form:textarea>
						</div>
					</div>
				</c:if>
				<h4>
					<spring:message code="vehicle.deployment.details"
						text="Deployment Details" />
				</h4>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="department"><spring:message code="vehicle.deptId"
							text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.deptId"
							cssClass="form-control" id="department" data-rule-required="true"
							disabled="true" onchange="getEmployeeByDept(this);">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<c:if test="${userSession.languageId eq 1}">
									<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<form:option value="${dept.dpDeptid}">${dept.dpNameMar}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="employee"><spring:message
							code="vehicle.driver" text="Driver" /></label>
					<div class="col-sm-4">
						<form:select id="driverName" path="vehicleMasterDTO.driverName"
							cssClass="form-control" disabled="true" data-rule-required="true">
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
					<label class="col-sm-2 control-label" for="location"><spring:message
							code="vehicle.locId" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.locId" cssClass="form-control"
							disabled="true" id="location" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${locations}" var="loc">
								<c:if test="${userSession.languageId eq 1}">
									<form:option value="${loc.locId}">${loc.locNameEng} - ${loc.locArea}</form:option>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<form:option value="${loc.locId}">${loc.locNameReg} - ${loc.locAreaReg}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 " for="Remarks"><spring:message
							code="vehicle.assignedTo" text="Assigned To" /></label>
					<div class="col-sm-4">
						<form:textarea path="vehicleMasterDTO.purpose" disabled="true"
							class="form-control mandColorClass" id="purpose"></form:textarea>
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
											actionUrl="insuranceClaim.html?Download" /></td>
								</tr>
								<c:set var="e" value="${e + 1}" scope="page" />
							</c:forEach>
						</table>
					</div>
				</c:if>

				<div class="text-center padding-top-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backVehicleMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
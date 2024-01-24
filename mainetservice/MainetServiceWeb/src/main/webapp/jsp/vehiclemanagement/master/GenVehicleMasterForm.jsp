<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
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

.form-group .control-label {
	text-transform: capitalize;
}

.form-group .input-group-addon {
	text-align: left;
	border: 1px solid #ccc;
}

.form-group .mandColorClass, .form-group .has-error, .form-group label[for="PurchasePrice"]+div>.input-group
	{
	margin: 0 !important;
}

.form-group label[for="DepartmentownedYes"], .form-group label[for="DepartmentownedNO"]
	{
	margin: 6px 0 0;
}

#DepartmentownedYes, #DepartmentownedNO {
	margin-top: 2px;
}

.vehicle-capacity+div>.input-group {
	display: block;
}

.vehicle-capacity+div>.input-group input.input-group-addon {
	background-color: transparent;
	width: calc(100% - 25%);
}

.vehicle-capacity+div>.input-group select {
	width: calc(100% - 75%);
}

select#veCaunit+.chosen-container.chosen-container-single {
	width: 25% !important;
	min-width: auto;
}

select#veCaunit+.chosen-container.chosen-container-single a.chosen-single
	{
	padding-bottom: 4px;
	height: 30px;
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
			<apptags:helpDoc url="VehMaster.html" />
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
					<label class="control-label col-sm-2 required-control"
						for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMasterDTO.veVetype"
						cssClass="form-control required-control chosen-select-no-results"
						isMandatory="true" selectOptionLabelCode="selectdropdown"
						hasId="true" />

					<label class="control-label col-sm-2 required-control"
						for="EngineNo"><spring:message
							code="vehicle.master.vehicle.engine.no" text="Engine No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veEngSrno" type="text"
							maxlength="50" class="form-control " id="EngineNo"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="MakeModel"><spring:message
							code="vehicle.master.vehicle.chasis.no" text="Chasis No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veChasisSrno"
							maxlength="50" type="text" class="form-control " id="chasisno"></form:input>
					</div>

					<label class="control-label col-sm-2" for="MakeModel"><spring:message
							code="vehicle.master.vehicle.reg.no" text="Vehicle Reg .No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veNo" type="text"
							maxlength="50" class="form-control " id="veReg"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for=""><spring:message
							code="vehicle.master.vehicle.standard.weight"
							text="Vehicle Standard Weight" /> </label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input name="" path="vehicleMasterDTO.veStdWeight"
								type="text" maxlength="15"
								class="form-control hasDecimal text-right" id="veStdWeight"></form:input>
							<label class="input-group-addon"> <spring:message
									code="swm.kgs" text="Kilograms" />
							</label>
						</div>
					</div>
					
					<label class="control-label col-sm-2 required-control"
						for="MakeModel"><spring:message
							code="vehicle.master.vehicle.model"
							text="Make / Model / Manufacturer" /></label>
					<div class="col-sm-4">
						<form:input name="" path="vehicleMasterDTO.veModel" type="text"
							maxlength="50" class="form-control" id="MakeModel"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 vehicle-capacity" for="">
						<spring:message code="vehicle.master.vehicle.capacity"
							text="Vehicle Capacity" />
					</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input name="" path="vehicleMasterDTO.veCapacity"
								type="text" maxlength="15"
								class="form-control hasDecimal text-right input-group-addon"
								id="veCapacity"
								onkeypress="return hasAmount(event, this, 10, 2)"
								onchange="getAmountFormatInDynamic((this),'veCapacity')"></form:input>
							<form:select path="vehicleMasterDTO.veCaunit"
								cssClass="form-control required-control mandColorClass chosen-select-no-results"
								id="veCaunit">
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

					<label for="text-1515133683412"
						class="col-sm-2 required-control control-label "><spring:message
							code="vehicle.tracking" text="Vehicle Tracking" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.veGpsDeviceid"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select" id="VehicleGPSID">
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
						path="vehicleMasterDTO.fuelType"
						cssClass="form-control required-control chosen-select-no-results"
						selectOptionLabelCode="selectdropdown" hasId="true" />
				</div>

				<div class=" form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="vehicle.master.vehicle.depart.owned.vehicle"
							text="Department Owned Vehicle" /><span class="mand">*</span> </label>
					<div class="col-sm-4">
						<label class="radio-inline" for="DepartmentownedYes"> <form:radiobutton
								name="Department" path="vehicleMasterDTO.veFlag"
								checked="checked" value="Y" id="DepartmentownedYes"></form:radiobutton>
							<spring:message code="solid.waste.Yes" text="Yes" />

						</label> <label class="radio-inline" for="DepartmentownedNO"> <form:radiobutton
								name="Department" path="vehicleMasterDTO.veFlag" value="N"
								id="DepartmentownedNO"></form:radiobutton> <spring:message
								code="solid.waste.No" text="No" />
						</label>
					</div>
				</div>

				<div class="yes hidebox">
					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="vehicle.master.vehicle.purchase.date" text="Purchase Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="vehicleMasterDTO.vePurDate" id="vePurDate"
									class="form-control mandColorClass datepicker dateValidation"
									value="" readonly="false" maxLength="10" />
								<label class="input-group-addon" for="fromDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden" id=vePurDate></label>
							</div>
						</div>
						
						<label class="control-label col-sm-2" for="PurchasePrice"><spring:message
								code="vehicle.master.vehicle.purchase.price"
								text="Purchase Price" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input type="text" path="vehicleMasterDTO.vePurPrice"
									cssClass="form-control text-right" id="PurchasePrice"
									onkeypress="return hasAmount(event, this, 12, 2)"
									onchange="getAmountFormatInDynamic((this),'PurchasePrice')" />
								<label class="input-group-addon"><i class="fa fa-inr"></i><span
									class="hide"><spring:message
											code="vehicle.master.vehicle.rupees" text="Rupees" /></span></label>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="yes hidebox">
						<label class="control-label col-sm-2" for="SourceofPurchase"><spring:message
								code="vehicle.master.vehicle.source.purchase"
								text="Source of Purchase" /></label>
						<div class="col-sm-4">
							<form:select path="vehicleMasterDTO.vePurSource"
								class="form-control required-control chosen-select-no-results"
								name="vmVendorname" id="vePurSource" onchange=""
								disabled="false">
								<form:option value="">
									<spring:message code="solid.waste.select" text="Select" />
								</form:option>
								<c:forEach items="${command.vendorList}" varStatus="status"
									var="vendor">
									<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<label class="control-label col-sm-2 " for="AssetCode"><spring:message
								code="vehicle.master.vehicle.asset.code" text="Asset Code" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input type="text" path="vehicleMasterDTO.assetNo"
									maxlength="50" class="form-control " id="AssetCode"></form:input>
								<label class="input-group-addon"><i class="fa fa-globe"></i><span
									class="hide"><spring:message
											code="vehicle.master.vehicle.asset.code" text="AssetCode" /></span></label>
							</div>
						</div>
					</div>
				</div>

				<div class="no hidebox" style="display: none">
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="VendorName"><spring:message
								code="vehicle.master.vehicle.vendor.name" text="Vendor Name" /></label>
						<div class="col-sm-4">
							<form:select path="vehicleMasterDTO.vmVendorid"
								class="form-control required-control chosen-select-no-results"
								name="vmVendorname" onchange="getContract()" id="vmVendorname">
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
							maxlegnth="12" cssClass="hasDecimal" />
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="vehicle.from.date" text="From Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="vehicleMasterDTO.veRentFromdate"
									id="veRentFromdate"
									class="form-control mandColorClass datepicker dateValidation"
									value="" readonly="false" maxLength="10" />
								<label class="input-group-addon" for="fromDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden" id=veRentFromdate></label>
							</div>
						</div>
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="vehicle.to.date" text="To Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="vehicleMasterDTO.veRentTodate"
									id="veRentTodate"
									class="form-control mandColorClass datepicker dateValidation "
									value="" readonly="false" maxLength="10" />
								<label class="input-group-addon" for="toDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden" id=veRentTodate></label>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="Remarks"><spring:message
							code="vehicle.master.vehicle.remarks" text="Remarks" /></label>
					<div class="col-sm-4">
						<form:textarea path="vehicleMasterDTO.veRemarks" maxLength="250"
							class="form-control" id="Remarks"></form:textarea>
					</div>
				</div>

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
							cssClass="form-control chosen-select-no-results" id="department"
							data-rule-required="true" isMandatory="true"
							onchange="getEmployeeByDept(this);">
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
							cssClass="form-control chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${employees}" var="employee">
								<option value="${employee.empId}">${employee.fullName}</option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="location"><spring:message
							code="vehicle.locId" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="vehicleMasterDTO.locId"
							cssClass="form-control chosen-select-no-results"
							isMandatory="false" id="location" data-rule-required="true">
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

					<label class="control-label col-sm-2" for="Remarks"><spring:message
							code="vehicle.assignedTo" text="Assign to" /></label>
					<div class="col-sm-4">
						<form:textarea path="vehicleMasterDTO.purpose" maxLength="250"
							class="form-control mandColorClass" id="purpose"></form:textarea>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="swm.fileupload" /></label>
					<div class="col-sm-4">
						<small class="text-blue-2"> <spring:message
								code="vehicle.file.upload.tooltip"
								text="(Upload File upto 5MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp extension(s) file(s) are allowed.)" />
						</small>
						<apptags:formField fieldType="7" labelCode="" hasId="true"
							fieldPath="" isMandatory="false" showFileNameHTMLId="true"
							fileSize="BND_COMMOM_MAX_SIZE"
							maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION" currentCount="0" />
					</div>
				</div>

				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						name="button-submit" onclick="saveVehicleMasterForm(this);">
						<spring:message code="vehicle.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning" onclick="resetForm();">
						<spring:message code="solid.waste.reset" text="Reset" />
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

<script type="text/javascript">
	document.getElementById("DepartmentownedN").disabled = true;
</script>

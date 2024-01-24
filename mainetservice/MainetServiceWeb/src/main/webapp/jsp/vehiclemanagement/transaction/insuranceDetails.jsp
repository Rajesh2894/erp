<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- End JSP Necessary Tags -->
<!-- <link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" /> -->
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<!-- <script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script type="text/javascript"
	src="js/vehicle_management/insuranceDetails.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<link
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	rel="stylesheet" type="text/css">


<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="insurance.detail" text="Insurance Details" /></strong>
				<apptags:helpDoc url="insuranceDetails.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="insuranceDetails.html" name="frmVehicleLogBook"
				id="frmVehicleLogBook" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>


				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="department"><spring:message
							code="insurance.detail.department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceDetailsDto.department"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' }"
							cssClass="form-control chosen-select-no-results" id="department" data-rule-required="true"
							isMandatory="true" onchange="getVehicleTypeByDept()">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>


					<label class="control-label col-sm-2 required-control"
						for="VehicleType"><spring:message
							code="insurance.detail.vehicletype" text="Vehicle Type" /></label>
					<apptags:lookupField items="${command.getLevelData('VCH')}"
						hasId="true" path="insuranceDetailsDto.vehicleType"
						cssClass="form-control chosen-select-no-results" selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
						isMandatory="true" changeHandler="showVehicleRegNo(this,'A')" />

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="vehicle"><spring:message
							code="insurance.detail.vehicleno" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceDetailsDto.veId" id="veid"
							class="form-control mandColorClass chosen-select-no-results" label="Select"
							onchange=""
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}">
							<form:option value="0">
								<spring:message code="insurance.detail.select" text="select" />
							</form:option>
							<c:forEach items="${command.vehicleMasterList}" var="lookup">
								<form:option value="${lookup.veId}" code="${lookup.veId}">${lookup.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label required-control col-sm-2" for="SourceofPurchase"><spring:message
							code="insurance.detail.insuredBy" text="Insured By" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceDetailsDto.insuredBy" isMandatory="true"
							class="form-control mandColorClass required-control chosen-select-no-results" name="vmVendorname"
							id="vePurSource" disabled="${command.saveMode eq 'V'}">
							<form:option value="">
								<spring:message code="insurance.detail.select" text="Select" />
							</form:option>
							<c:forEach items="${command.vendorList}" varStatus="status"
								var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">

					<apptags:date labelCode="insurance.detail.issueDate" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}" fieldclass="datepicker"
						datePath="insuranceDetailsDto.issueDate" readonly=""></apptags:date>

					<apptags:date labelCode="insurance.detail.endDate" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}" fieldclass="datepicker"
						datePath="insuranceDetailsDto.endDate" readonly=""></apptags:date>
				</div>


		<%-- 		<div class="form-group">

					<label for="vehicleMeterRead"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Insured Amount" /></label>
					<div class="col-sm-4">
						<form:input type='text' path="insuranceDetailsDto.insuredAmount"
							maxlength="8" id="insuredAmount" placeholder="0.0"
							disabled="${command.saveMode eq 'V'}"
							class="mandColorClass form-control hasNumber" />
					</div>

					<label for="fuelQuantity"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Insured Fees" /></label>
					<div class="col-sm-4">
						<form:input type='text' path="insuranceDetailsDto.insuredFees"
							maxlength="6" id="insuredFees" placeholder="0.0"
							disabled="${command.saveMode eq 'V'}"
							class="mandColorClass form-control hasNumber" />
					</div>
				</div> --%>
				
					<div class="form-group">
					<apptags:input labelCode="insurance.detail.premium.amount"
						path="insuranceDetailsDto.insuredAmount" isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
						maxlegnth="12"
						cssClass="hasDecimal" />

					<apptags:input labelCode="insurance.detail.PolicyNumber" isMandatory="true"
						path="insuranceDetailsDto.policyNo" isDisabled="${command.saveMode eq 'V'}" maxlegnth="20"
						cssClass="hasCharacterWithNumbersSlash" />
				</div>

				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save"/>"
							title='<spring:message code="bt.save"/>'
							onclick="confirmToProceed(this)" class="btn btn-success"
							id="Submit">
						<c:if test="${command.saveMode ne 'E'}">
							<button type="Reset" class="btn btn-warning"
								title='<spring:message code="lgl.reset" text="Reset" />'
								onclick="openForm('insuranceDetails.html','AddInsuranceDetailsForm')">
								<spring:message code="lgl.reset" text="Reset"></spring:message>
							</button>
						</c:if>
					</c:if>
					<input type="button"
						title='<spring:message code="vehicle.back" text="Back"/>'
						onclick="window.location.href='insuranceDetails.html'"
						class="btn btn-danger  hidden-print" value="<spring:message code="vehicle.back" text="Back"/>">
				</div>

		</form:form>
		</div>
		</div>
		</div>	


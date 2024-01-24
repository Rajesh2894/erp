<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/vehicle_management/insuranceClaim.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	rel="stylesheet" type="text/css">


<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="insurance.claim"
						text="Insurance Details" /></strong>
				<apptags:helpDoc url="insuranceClaim.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="insuranceClaim.html" name="frmVehicleLogBook"
				id="frmVehicleLogBook" method="POST" commandName="command"
				class="form-horizontal form">
				<form:hidden path="insuranceClaimDto.removeFileById" id="removeFileById" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>


				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="department"><spring:message
							code="insurance.detail.department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceClaimDto.department"
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
						hasId="true" path="insuranceClaimDto.vehicleType"
						disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' }"
						cssClass="form-control chosen-select-no-results" selectOptionLabelCode="selectdropdown"
						isMandatory="true" changeHandler="showVehicleRegNo(this,'E')" />

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="vehicle"><spring:message
							code="vehicle.sanitary.vehicleNo" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceClaimDto.veId" id="veid"
							class="form-control mandColorClass chosen-select-no-results" label="Select"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' }"
							onchange="searchInsuranceDetails(this)">
							<form:option value="0">
								<spring:message code="insurance.detail.select" text="select" />
							</form:option>
							<c:forEach items="${command.vehicleMasterList}" var="lookup">
								<form:option value="${lookup.veId}" code="${lookup.veId}">${lookup.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label required-control col-sm-2"
						for="SourceofPurchase"><spring:message
							code="insurance.detail.insuredBy" text="Insured By" /></label>
					<div class="col-sm-4">
						<form:select path="insuranceClaimDto.insuredBy" isMandatory="true"
							class="form-control mandColorClass required-control"
							name="vmVendorname" id="vePurSource" disabled="true">
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

					<apptags:date labelCode="insurance.detail.issueDate"
						isMandatory="true" fieldclass="datepicker" isDisabled="true"
						datePath="insuranceClaimDto.issueDate" readonly=""></apptags:date>

					<apptags:date labelCode="insurance.detail.endDate"
						isMandatory="true" fieldclass="datepicker" isDisabled="true"
						datePath="insuranceClaimDto.endDate" readonly=""></apptags:date>
				</div>


				<div class="form-group">
					<apptags:input labelCode="insurance.detail.amount"
						path="insuranceClaimDto.insuredAmount" isMandatory="true"
						maxlegnth="12" isDisabled="true" cssClass="hasDecimal" />

					<apptags:input labelCode="insurance.detail.PolicyNumber" isMandatory="false"
						path="insuranceClaimDto.policyNo" isDisabled="true" maxlegnth="20"
						cssClass="hasCharacterWithNumbersSlash" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="insurance.detail.claimamount"
						isDisabled="${command.saveMode eq 'V'}"
						path="insuranceClaimDto.claimAmount" isMandatory="true"
						maxlegnth="5" cssClass="hasDecimal" />

					<apptags:input labelCode="insurance.detail.claimapproveamount"
						isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
						path="insuranceClaimDto.claimApprovedAmount" maxlegnth="5"
						cssClass="hasDecimal" />
				</div>
				<c:if test="${command.saveMode ne 'V'}">

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
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
								currentCount="0" />
						</div>
					</div>
				</c:if>

				<c:if test="${command.saveMode eq 'V' || command.saveMode eq 'E'}">
					<c:if test="${! command.attachDocsList.isEmpty()}">
						<div class="table-responsive">
							<table class="table table-bordered table-striped" id="deleteDoc">
								<tr>
									<th width="8%"><spring:message
											code="population.master.srno" text="Sr. No." /></th>
									<th><spring:message code="scheme.view.document" text="" /></th>
									<th><spring:message code="" text="Action" /></th>
								</tr>
								<c:set var="e" value="0" scope="page" />
								<c:forEach items="${command.attachDocsList}" var="lookUp">
									<tr>
										<td>${e+1}</td>
										<td><apptags:filedownload filename="${lookUp.attFname}"
												filePath="${lookUp.attPath}"
												actionUrl="insuranceClaim.html?Download" /></td>
										<td class="text-center"><a href='#' id="deleteFile"
											onclick="return false;" class="btn btn-danger btn-sm"><i
												class="fa fa-trash"></i></a> <form:hidden path=""
												value="${lookUp.attId}" /></td>
									</tr>
									<c:set var="e" value="${e + 1}" scope="page" />
								</c:forEach>
							</table>
						</div>
					</c:if>
				</c:if>

				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save"/>"
							onclick="confirmToProceed(this)" class="btn btn-success"
							id="Submit">
						<c:if test="${command.saveMode ne 'E'}">
							<button type="Reset" class="btn btn-warning"
								onclick="openForm('insuranceClaim.html','AddInsuranceDetailsForm')">
								<spring:message code="lgl.reset" text="Reset"></spring:message>
							</button>
						</c:if>
					</c:if>
					<input type="button"
						onclick="window.location.href='insuranceClaim.html'"
						class="btn btn-danger  hidden-print"
						value="<spring:message code="vehicle.back" text="Back"/>">
				</div>

			</form:form>
		</div>
	</div>
</div>


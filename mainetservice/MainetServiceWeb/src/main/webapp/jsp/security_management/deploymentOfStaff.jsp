<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/security_management/deploymentOfStaff.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script>

$(document).ready(function() {
	$('#contStaffSchFrom').removeClass('hasDatepicker');
	$('#contStaffSchFrom').addClass('contStaffSchFrom');
	$('#contStaffSchTo').removeClass('hasDatepicker');
	$('#contStaffSchTo').addClass('contStaffSchTo');
});

$('.contStaffSchFrom').datepicker({
	dateFormat: 'dd/mm/yy',	
	changeMonth: true,
	changeYear: true,
	minDate:$('#contStaffSchFrom').val(),
	maxDate:$('#contStaffSchTo').val(),
	
});
$('.contStaffSchTo').datepicker({
	dateFormat: 'dd/mm/yy',	
	changeMonth: true,
	changeYear: true,
	maxDate:$('#contStaffSchTo').val(),
	minDate:$('#contStaffSchFrom').val(),
	
});

</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="DeploymentOfStaffDTO.form.name" text="Deployment Of Staff" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">
		<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message code="leadlift.master.ismand" />
				</span>
			</div>
			<!-- End mand-label -->
			<form:form action="DeploymentOfStaff.html" method="POST"
				commandName="command" class="form-horizontal"
				name="frmDeploymentStaffFormAdd" id="frmDeploymentStaffFormAdd">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="">
						<spring:message code="DeploymentOfStaffDTO.empTypeId"
							text="Employee Type" />
					</label>
					<c:set var="baseLookupCode" value="EMT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="deploymentOfStaffDTO.empTypeId" changeHandler="resetData()"
						disabled="${command.saveMode eq 'V'}"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="ContractualStaffMasterDTO.vendorId" text="Agency Name" />
					</label>
					<div class="col-sm-4">
						<form:select id="vmVendorid" path="deploymentOfStaffDTO.vendorId"
							cssClass="form-control chosen-select-no-results vmVendorid "
							data-rule-required="true" disabled="${command.saveMode eq 'V'}"
							onchange="getVendorData()">
							<form:option value="0">
								<spring:message code="Select" text="Select " />
							</form:option>
							<c:forEach items="${command.vendorList}" var="Vendor">
								<form:option value="${Vendor.vmVendorid}"
									code="${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}">${Vendor.vmVendorcode}-${Vendor.vmVendorname}
													</form:option>
							</c:forEach>
						</form:select>
					</div>
					<c:choose>
						<c:when test="${command.saveMode ne 'V'}">
							<label class="control-label col-sm-2 required-control"
								for="contStaffIdNo"> <spring:message code="DeploymentOfStaffDTO.contStaffName" text="Name" /></label>
							<div class="col-sm-4">
								<form:select id="contStaffIdNo"
									path="deploymentOfStaffDTO.contStaffIdNo"
									cssClass="form-control chosen-select-no-results "
									onchange="getStaffData(this)" data-rule-required="true"
									disabled="${command.saveMode eq 'V'}">
									<form:option value="0">
										<spring:message code="Select" text="Select" />
									</form:option>
									<c:forEach items="${command.employeeList}" var="empNameList">
										<form:option value="${empNameList.contStaffIdNo}">${empNameList.contStaffName}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<label class="control-label col-sm-2 required-control"
								for="contStaffIdNo"> <spring:message code="" text="Name" /></label>
							<div class="col-sm-4">
								<form:select id="contStaffIdNo"
									path="deploymentOfStaffDTO.contStaffIdNo"
									cssClass="form-control chosen-select-no-results "
									onchange="getStaffData(this)" data-rule-required="true"
									disabled="${command.saveMode eq 'V'}">
									<form:option value="0">
										<spring:message code="Select" text="Select" />
									</form:option>
									<c:forEach items="${command.empNameList}" var="empNameList">
										<form:option value="${empNameList.contStaffIdNo}">${empNameList.contStaffName}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="location"> <spring:message code="LocationDetailsOfStaffDTO.locId"
							text="From Location" /></label>
					<div class="col-sm-4">
						<form:select id="location" path="deploymentOfStaffDTO.fromLocId"
							value="${command.deploymentOfStaffDTO.locId}"
							cssClass="form-control chosen-select-no-results " disabled="true"
							data-rule-required="true">

							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.location}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 required-control"
						for="location"> <spring:message code="DeploymentOfStaffDTO.to.location" text="To Location" /></label>
					<div class="col-sm-4">
						<form:select id="locId" path="deploymentOfStaffDTO.locId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true" disabled="${command.saveMode eq 'V'}">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.location}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="">
						<spring:message code="DeploymentOfStaffDTO.from.shift" text="From Shift" />
					</label>
					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="deploymentOfStaffDTO.fromCpdShiftId" disabled="true"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" isMandatory="true" />

					<%-- <label class="control-label col-sm-2 required-control" for="">
						<spring:message code="DeploymentOfStaffDTO.to.shift" text="To Shift" />
					</label>
					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="deploymentOfStaffDTO.cpdShiftId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" isMandatory="true"
						disabled="${command.saveMode eq 'V'}" /> --%>
						
							
					<label class="control-label col-sm-2 required-control " for=""> <spring:message
							code="DeploymentOfStaffDTO.to.shift" text="To Shift" /></label>
					<div class="col-sm-4">
						<form:select id="cpdShiftId" path="deploymentOfStaffDTO.cpdShiftId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.lookup}" var="lookup">
								<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						datePath="deploymentOfStaffDTO.contStaffSchFrom"
						labelCode="DeploymentOfStaffDTO.contStaffSchFrom"
						isDisabled="${command.saveMode eq 'V' || command.saveMode eq 'A'}" cssClass="mandColorClass"
						isMandatory="true"></apptags:date>

					<apptags:date fieldclass="datepicker"
						datePath="deploymentOfStaffDTO.contStaffSchTo"
						labelCode="DeploymentOfStaffDTO.contStaffSchTo"
						isDisabled="${command.saveMode eq 'V' || command.saveMode eq 'A'}" cssClass="mandColorClass"
						isMandatory="true"></apptags:date>
				</div>
							
				<div class="form-group">
					<apptags:input labelCode="DeploymentOfStaffDTO.contStaffMob"
						cssClass="form-control hasMobileNo " maxlegnth="10"
						dataRuleMinlength="10" path="deploymentOfStaffDTO.contStaffMob"
						isReadonly="${command.saveMode eq 'V'}" />

					<label class="control-label col-sm-2 "> <spring:message
							code="DeploymentOfStaffDTO.remarks" text="Remarks"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:textarea id="remarks" path="deploymentOfStaffDTO.remarks"
							class="form-control mandColorClass" maxLength="50"
							disabled="${command.saveMode eq 'V'}" />
					</div>
				</div>

				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-success" title="Save"
							onclick="saveData(this)">
							<i class="fa fa-floppy-o" aria-hidden="true"></i>
							<spring:message code="DeploymentOfStaffDTO.form.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'E' }">
						<button type="button" class="btn btn-success" title="Save"
							onclick="saveData(this)">
							<i class="fa fa-floppy-o" aria-hidden="true"></i>
							<spring:message code="DeploymentOfStaffDTO.form.save" text="Save" />
						</button>
					</c:if>

					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" id="reset"
							onclick="addDeploymentStaff('DeploymentOfStaff.html','ADD','A');"
							class="btn btn-warning" title="Reset">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="DeploymentOfStaffDTO.form.reset"
								text="Reset" />
						</button>
					</c:if>

					<button type="button" id="back" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='DeploymentOfStaff.html'"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="DeploymentOfStaffDTO.form.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>
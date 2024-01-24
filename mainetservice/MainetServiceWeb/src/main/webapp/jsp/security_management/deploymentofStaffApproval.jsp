<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/security_management/deploymentOfStaff.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Deployment Of Staff" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">
			<form:form action="DeploymentOfStaffApproval.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmDeploymentStaffFormAddApproval"
				id="frmDeploymentStaffFormAddApproval">

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
						path="deploymentOfStaffDTO.empTypeId"
						disabled="${command.saveMode eq 'V'}"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />

					<label class="control-label col-sm-2 required-control"
						for="contStaffIdNo"> <spring:message code="" text="Name" /></label>
					<div class="col-sm-4">
						<form:select id="contStaffIdNo"
							path="deploymentOfStaffDTO.contStaffIdNo"
							cssClass="form-control chosen-select-no-results "
							onchange="getStaffData(this)" data-rule-required="true"
							disabled="${command.saveMode eq 'V'}">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${empNameList}" var="empNameList">
								<form:option value="${empNameList.contStaffIdNo}">${empNameList.contStaffName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="location"> <spring:message code=""
							text="From Location" /></label>
					<div class="col-sm-4">
						<form:select id="location" path="deploymentOfStaffDTO.fromLocId"
							value="${command.deploymentOfStaffDTO.locId}"
							cssClass="form-control chosen-select-no-results "
							disabled="${command.saveMode eq 'V'}" data-rule-required="true">

							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${location}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 required-control"
						for="location"> <spring:message code="" text="To Location" /></label>
					<div class="col-sm-4">
						<form:select id="locId" path="deploymentOfStaffDTO.locId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true" disabled="${command.saveMode eq 'V'}">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${location}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="">
						<spring:message code="" text="From Shift" />
					</label>
					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="deploymentOfStaffDTO.fromCpdShiftId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" isMandatory="true"
						disabled="${command.saveMode eq 'V'}" />

					<label class="control-label col-sm-2 required-control" for="">
						<spring:message code="" text="To Shift" />
					</label>

					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="deploymentOfStaffDTO.cpdShiftId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" isMandatory="true"
						disabled="${command.saveMode eq 'V'}" />
				</div>

				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						datePath="deploymentOfStaffDTO.contStaffSchFrom"
						labelCode="DeploymentOfStaffDTO.contStaffSchFrom"
						isDisabled="${command.saveMode eq 'V'}" cssClass="mandColorClass"
						isMandatory="true"></apptags:date>

					<apptags:date fieldclass="datepicker"
						datePath="deploymentOfStaffDTO.contStaffSchTo"
						labelCode="DeploymentOfStaffDTO.contStaffSchTo"
						isDisabled="${command.saveMode eq 'V'}" cssClass="mandColorClass"
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
				<c:if test="${command.approvalView ne 'A'}">
				<div class="widget-header">
					<h2>
						<strong><spring:message code="" text="User Action" /></strong>
					</h2>
				</div>
				<br>

				<div class="form-group">
					<apptags:radio radioLabel="Approve,Reject"
						radioValue="APPROVED,REJECTED" isMandatory="true"
						labelCode="DeploymentOfStaffDTO.statusApproval"
						path="deploymentOfStaffDTO.statusApproval"
						defaultCheckedValue="APPROVED">
					</apptags:radio>
					<apptags:textArea labelCode="DeploymentOfStaffDTO.remarkApproval"
						isMandatory="true" path="deploymentOfStaffDTO.remarkApproval"
						cssClass="hasNumClass form-control" maxlegnth="100" />
				</div>
				<div class="text-center">
					<button type="button" value="<spring:message code="bt.save"/>"
						class="btn btn-green-3" title='<spring:message code="bt.save"/>'
						onclick="saveDeploymentStaffReqApprovalData(this)">
						<spring:message code="bt.save"/>
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
				</c:if>
				
				<c:if test="${command.approvalView eq 'A'}">
					<div class="text-center">
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</c:if>

			</form:form>
		</div>
	</div>
</div>
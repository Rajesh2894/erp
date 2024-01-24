<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript"
	src="js/security_management/contractualEmpData.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- new_contractual_staff_master_form.jsp -->
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="ContractualStaffMaster.form.name"
						text="Contractual Staff Master" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">
			<form:form action="ContractualStaffMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmContractualStaffMasterForm"
				id="frmContractualStaffMasterForm">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="ContractualStaffMasterDTO.empType" text="Employee Type" /></label>
					<div class="radio col-sm-4 margin-top-5">
						<label> <form:radiobutton path="dto.empType" id="contract"
								value="C" onclick="getContractEmpList()" /> <spring:message
								code="ContractualStaffMasterDTO.form.contract" /></label> <label><form:radiobutton
								path="dto.empType" value="R" id="permanent" checked="checked"
								onclick="getEmployeeList()" /> <spring:message
								code="ContractualStaffMasterDTO.form.permanent" /></label>
					</div>

					<form:hidden path="" value="" id="hideEmpId"/>	
					<label class="col-sm-2 control-label required-control"
						for="employee"><spring:message code="EmployeeSchedulingDTO.contStaffName"
							text="Employee Name" /></label>
					<div class="col-sm-4">
						<form:select id="empId" path="dto.empId" cssClass="form-control"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${empList}" var="employee">
								<form:option value="${employee.empId}"
									label="${employee.empname}"></form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">
					<apptags:input labelCode="ContractualStaffMasterDTO.contStaffName"
						isMandatory="true" path="dto.contStaffName"
						cssClass="hasNameClass" maxlegnth="50"
						isReadonly="${command.saveMode eq 'V'}" />

					<label class="control-label col-sm-2 required-control" for="Census">
						<spring:message code="ContractualStaffMasterDTO.sex" text="Gender" />
					</label>
					<c:set var="baseLookupCode" value="GEN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}" path="dto.sex"
						cssClass="form-control" hasId="true"
						selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V'}" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="ContractualStaffMasterDTO.contStaffMob"
						cssClass="form-control hasMobileNo " maxlegnth="10"
						dataRuleMinlength="10" path="dto.contStaffMob"
						isReadonly="${command.saveMode eq 'V'}" />
					<apptags:textArea
						labelCode="ContractualStaffMasterDTO.contStaffAddress"
						path="dto.contStaffAddress" maxlegnth="250"
						cssClass="alphaNumeric" isReadonly="${command.saveMode eq 'V'}" />
				</div>
				
				<div class="form-group">				
					<fmt:formatDate pattern="dd/MM/yyyy" value="${dto.dob}"
						var="dateFormat" />
					<label class="col-sm-2 control-label "><spring:message
							code="ContractualStaffMasterDTO.dob" text="Date of Birth" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dto.dob" id="dob"
								disabled="${command.saveMode eq 'V'}"
								class="form-control mandColorClass datepicker dateValidation"
								maxLength="10" />
							<label class="input-group-addon" for="outDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=outDate></label>
						</div>
					</div>
					
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="ContractualStaffMasterDTO.dsgId" text="Designation" /></label>
					<div class="col-sm-4">
						<form:select id="designation" path="dto.dsgId"
							cssClass="form-control chosen-select-no-results"
							data-rule-required="true" disabled="${command.saveMode eq 'V'}">

							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${designation}" var="desi">
								<form:option value="${desi.dsgid}">${desi.dsgname}
							</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <!-- required-control -->
						<spring:message code="ContractualStaffMasterDTO.vendorId"
							text="Agency Name" />
					</label>
					<div class="col-sm-4">

						<form:select id="vmVendorid" path="dto.vendorId"
							cssClass="form-control vmVendorid " data-rule-required="true">
							<form:option value="">
								<spring:message code="select" text="Select " />
							</form:option>
							<c:forEach items="${VendorList}" var="Vendor">
								<form:option value="${Vendor.vmVendorid}"
									code="${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}">${Vendor.vmVendorcode}-${Vendor.vmVendorname}
													</form:option>
							</c:forEach>
						</form:select>
					</div>

					<c:choose>
						<c:when test="${command.saveMode eq 'A'}">
							<apptags:input
								labelCode="ContractualStaffMasterDTO.contStaffIdNo"
								path="dto.contStaffIdNo" cssClass="alphaNumeric" maxlegnth="8"
								isMandatory="true" />
						</c:when>
						<c:otherwise>
							<apptags:input
								labelCode="ContractualStaffMasterDTO.contStaffIdNo"
								path="dto.contStaffIdNo" cssClass="alphaNumeric" maxlegnth="8"
								isMandatory="true" isReadonly="true" />
						</c:otherwise>
					</c:choose>

				</div>

				<div class="form-group">

					<apptags:date fieldclass="datepicker"
						datePath="dto.contStaffAppointDate"
						labelCode="ContractualStaffMasterDTO.contStaffAppointDate"
						cssClass="mandColorClass" isMandatory="true"></apptags:date>

					<label class="control-label col-sm-2 required-control"
						for="location"> <!-- required-control --> <spring:message
							code="ContractualStaffMasterDTO.locId" text="Current Location" /></label>
					<div class="col-sm-4">
						<form:select id="location" path="dto.locId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true" disabled="${command.saveMode eq 'V'}">
							<!--  data-rule-required="true" -->
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
						<spring:message code="ContractualStaffMasterDTO.cpdShiftId"
							text="Current Shift" />
					</label>

					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="dto.cpdShiftId" cssClass="mandColorClass form-control"
						hasId="true" selectOptionLabelCode="selectdropdown"
						isMandatory="true" />

					<label class="control-label col-sm-2 required-control" for="">
						<spring:message code="ContractualStaffMasterDTO.dayPrefixId"
							text="Weekly Off" />
					</label>

					<c:set var="baseLookupCode" value="DAY" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="dto.dayPrefixId" cssClass="mandColorClass form-control"
						hasId="true" selectOptionLabelCode="selectdropdown"
						isMandatory="true" />
				</div>

				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						datePath="dto.contStaffSchFrom"
						labelCode="ContractualStaffMasterDTO.contStaffSchFrom"
						cssClass="mandColorClass" isMandatory="true"></apptags:date>

					<apptags:date fieldclass="datepicker" datePath="dto.contStaffSchTo"
						labelCode="ContractualStaffMasterDTO.contStaffSchTo"
						cssClass="mandColorClass" isMandatory="true"></apptags:date>

				</div>

				<div class="text-center clear padding-10">
					<input type="button"
						value="<spring:message code="ContractualStaffMasterDTO.save"/>"
						onclick="confirmToProceed(this,'A')" class="btn btn-success"
						id="Save">

					<button type="Reset" class="btn btn-warning"
						onclick="addContractualStaffMaster('ContractualStaffMaster.html','ADD','A');">
						<spring:message code="ContractualStaffMasterDTO.form.reset"
							text="Reset"></spring:message>
					</button>

					<apptags:backButton url="ContractualStaffMaster.html"></apptags:backButton>

				</div>


			</form:form>
		</div>

	</div>
</div>





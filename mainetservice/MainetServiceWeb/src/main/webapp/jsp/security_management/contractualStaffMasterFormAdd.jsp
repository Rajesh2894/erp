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
	src="js/security_management/contractualStaffMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- new_contractual_staff_master_form.jsp -->
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="ContractualStaffMaster.form.name"
						text="Staff Master" /></strong>
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
			<form:form action="ContractualStaffMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmContractualStaffMasterForm"
				id="frmContractualStaffMasterForm">
				<form:hidden path="dto.contStsffId" id="contStsffId"/>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				
				<c:if test="${command.saveMode eq 'A'}">
				<div class="form-group">
					<%-- <apptags:radio radioLabel="ContractualStaffMasterDTO.form.contract,ContractualStaffMasterDTO.form.permanent"
										radioValue="C,R" labelCode="Employee Type" 
										path="dto.empType" defaultCheckedValue="C"></apptags:radio> --%>
					<label class="col-sm-2 control-label "><spring:message
							code="ContractualStaffMasterDTO.empType" text="Employee Type" /></label>
					<div class="radio col-sm-4 margin-top-5">				
						<label> <form:radiobutton path="dto.empType" id="contractual" onclick="getContractEmpList()" 
								value="C" checked="checked" /> <spring:message code="ContractualStaffMasterDTO.form.contract" /></label>
						<label><form:radiobutton path="dto.empType" value="R" id="permanent"
								onclick="getEmployeeList()" /> <spring:message code="ContractualStaffMasterDTO.form.permanent" /></label> 
					</div>	
														
					<form:hidden path="" value="" id="hideEmpId"/>	
					
												
					<label class="col-sm-2 control-label required-control"
						for="employee" id="employeeNames"><spring:message
							code="EmployeeSchedulingDTO.contStaffName" text="Employee Name" /></label>
					<div class="col-sm-4">
						<form:select id="empId" path="dto.empId"			
							cssClass="form-control chosen-select-no-results" data-rule-required="true" >
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${empList}" var="employee">
								<form:option value="${employee.empId}" label="${employee.empname}"></form:option>
							</c:forEach>
						</form:select>
					</div>						
				</div>
				</c:if>

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
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${dto.dob}" var="dateFormat" />
					<label class="col-sm-2 control-label"><spring:message
							code="ContractualStaffMasterDTO.dob" text="Date of Birth" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dto.dob"  id="dob"
								disabled="${command.saveMode eq 'V'}"
								isMandatory="true"
								cssClass="form-control" 
								fieldclass="datepicker" placeholder="dd/mm/yyyy"
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
							cssClass="form-control vmVendorid chosen-select-no-results" data-rule-required="true"
							disabled="${command.saveMode eq 'V'}">
							<form:option value="">
								<spring:message code="Select" text="Select " />
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
								path="dto.contStaffIdNo" cssClass="alphaNumeric" isMandatory="true" maxlegnth="8"/>
						</c:when>
						<c:otherwise>
							<apptags:input
								labelCode="ContractualStaffMasterDTO.contStaffIdNo"
								path="dto.contStaffIdNo" cssClass="alphaNumeric" isMandatory="true" maxlegnth="8" isReadonly="true"  />
						</c:otherwise>
					</c:choose>

				</div>

				<div class="form-group">

					<apptags:date fieldclass="datepicker"
						datePath="dto.contStaffAppointDate"
						labelCode="ContractualStaffMasterDTO.contStaffAppointDate"
						cssClass="mandColorClass" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}"></apptags:date>

					<label class="control-label col-sm-2"
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
					<label class="control-label col-sm-2" for="">
						<spring:message code="ContractualStaffMasterDTO.cpdShiftId"
							text="Current Shift" />
					</label>

					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="dto.cpdShiftId" cssClass="mandColorClass form-control"
						hasId="true" selectOptionLabelCode="selectdropdown"
						isMandatory="true" disabled="${command.saveMode eq 'V'}" />

					<label class="control-label col-sm-2 " for="">
						<spring:message code="ContractualStaffMasterDTO.dayPrefixId"
							text="Weekly Off" />
					</label>

					<c:set var="baseLookupCode" value="DAY" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="dto.dayPrefixId" cssClass="mandColorClass form-control"
						hasId="true" selectOptionLabelCode="selectdropdown"
						isMandatory="true" disabled="${command.saveMode eq 'V'}" />
				</div>

				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						datePath="dto.contStaffSchFrom"
						labelCode="ContractualStaffMasterDTO.contStaffSchFrom"
						cssClass="mandColorClass" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}"></apptags:date>

					<apptags:date fieldclass="datepicker" datePath="dto.contStaffSchTo"
						labelCode="ContractualStaffMasterDTO.contStaffSchTo"
						cssClass="mandColorClass" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}"></apptags:date>



				</div>

				<!-- RM-38980 -->
				<c:if test="${command.saveMode ne 'A'}">
				<div class="form-group">
					<apptags:radio
						radioLabel="ContractualStaffMasterDTO.active,ContractualStaffMasterDTO.inactive"
						radioValue="A,I" labelCode="ContractualStaffMasterDTO.status"
						isMandatory="true" path="dto.status" defaultCheckedValue="A"
						disabled="${command.saveMode eq 'V' }" />
				</div>
				</c:if>

				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'A'}">

						<input type="button"
							value="<spring:message code="ContractualStaffMasterDTO.save"/>"
							title='<spring:message code="ContractualStaffMasterDTO.save"/>'
							onclick="confirmToProceed(this,'A')" class="btn btn-success"
							id="Save">
					</c:if>
					<c:if test="${command.saveMode eq 'E' }">

						<input type="button"
							value="<spring:message code="ContractualStaffMasterDTO.save"/>"
							title='<spring:message code="ContractualStaffMasterDTO.save"/>'
							onclick="confirmToProceed(this,'E')" class="btn btn-success"
							id="Save">
					</c:if>

					<c:if test="${command.saveMode eq 'A'}">
						<button type="Reset" class="btn btn-warning"
							title='<spring:message code="ContractualStaffMasterDTO.form.reset" text="Reset"/>'
							onclick="addContractualStaffMaster('ContractualStaffMaster.html','ADD','A');">
							<spring:message code="ContractualStaffMasterDTO.form.reset"
								text="Reset"></spring:message>
						</button>
					</c:if>

					<apptags:backButton url="ContractualStaffMaster.html"></apptags:backButton>

				</div>


			</form:form>
		</div>

	</div>
</div>

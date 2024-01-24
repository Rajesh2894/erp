<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript"
	src="js/security_management/employeeScheduling.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="EmployeeSchedulingMaster.form.name" text="Employee Scheduling" /></strong>
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
			<form:form action="employeeCalendar.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmEmployeeSchedulingFormSearch"
				id="frmEmployeeSchedulingFormSearchId">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 " for="">
						<spring:message code="EmployeeSchedulingDTO.empTypeId"
							text="Employee Type" />
					</label>
					<c:set var="baseLookupCode" value="EMT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="employeeSchedulingDTO.empTypeId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />

					<label class="col-sm-2 control-label "> 
						<spring:message code="EmployeeSchedulingDTO.vendorId"
							text="Agency Name" />
					</label>
					<div class="col-sm-4">

						<form:select id="vmVendorid" path="employeeSchedulingDTO.vendorId"
							cssClass="form-control vmVendorid ">
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
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 "
						for="location"> <spring:message
							code="EmployeeSchedulingDTO.locId" text="Current Location" /></label>
					<div class="col-sm-4">
						<form:select id="location" path="employeeSchedulingDTO.locId"
							cssClass="form-control chosen-select-no-results ">

							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${location}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 " for="">
						<spring:message code="EmployeeSchedulingDTO.cpdShiftId"
							text="Current Shift" />
					</label>

					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="employeeSchedulingDTO.cpdShiftId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />
				</div>

				<div class="form-group">
					<apptags:date fieldclass="fromDateClass"
						datePath="employeeSchedulingDTO.contStaffSchFrom"
						labelCode="EmployeeSchedulingDTO.emplSchFrom"
						readonly = "true"
						cssClass="mandColorClass" >
					</apptags:date>

					<apptags:date fieldclass="toDateClass"
						datePath="employeeSchedulingDTO.contStaffSchTo"
						labelCode="EmployeeSchedulingDTO.emplSchTo"
						readonly ="true"
						cssClass="mandColorClass" >
					</apptags:date>
				</div>

				<div class="form-group">
					<apptags:input labelCode="EmployeeSchedulingDTO.contStaffName"
						path="employeeSchedulingDTO.contStaffName"
						cssClass="hasNameClass" maxlegnth="50" />

					<%-- <apptags:input labelCode="EmployeeSchedulingDTO.emplIdNo"
						path="employeeSchedulingDTO.contStaffIdNo" cssClass="alphaNumeric"
						maxlegnth="8"  /> --%>
				</div>

				<div class="text-center clear padding-10">

					<button type="button" id="search" class="btn btn-blue-2"
						onclick="searchEmployeeScheduling()" title='<spring:message code="ContractualStaffMasterDTO.form.search" text="Search" />'>
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="ContractualStaffMasterDTO.form.search" text="Search" />
					</button>


					<button type="button" id="reset"
						onclick="window.location.href='employeeCalendar.html'"
						class="btn btn-warning" title='<spring:message code="EmployeeSchedulingDTO.reset" text="Reset" />'>
						<spring:message code="EmployeeSchedulingDTO.reset" text="Reset" />
					</button>

					<button type="button" id="back" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" title='<spring:message code="ShiftMasterDTO.form.back" text="Back" />'
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<spring:message code="ShiftMasterDTO.form.back" text="Back" />
					</button>

					<button type="button" id="add" class="btn btn-success add"
						onclick="addEmployeeScheduling('employeeCalendar.html','ADD','A');"
						title='<spring:message code="ShiftMasterDTO.form.add" text="Add" />'>
						<spring:message code="ShiftMasterDTO.form.add" text="Add" />
					</button>

				</div>
				<div id="calendar"></div>

			</form:form>
		</div>
	</div>
</div>
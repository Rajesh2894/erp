<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/security_management/locationDetailOfStaff.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="LocationDetailsOfStaffDTO.location.details"
						text="Location Detail Of Staff" /></strong>
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
			<form:form action="SecurityReport.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmLocationDetailStaff" id="frmLocationDetailStaff">

				<div class="form-group">
					<apptags:input labelCode="LocationDetailsOfStaffDTO.contStaffName"
						path="locationDetailsOfStaffDTO.contStaffName"
						cssClass="hasNameClass" maxlegnth="20" />


					<label class="control-label col-sm-2 " for=""> <spring:message
							code="LocationDetailsOfStaffDTO.empTypeId" text="Employee Type" />
					</label>
					<c:set var="baseLookupCode" value="EMT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="locationDetailsOfStaffDTO.empTypeId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />
				</div>
				<div class="form-group">

					<label class="control-label col-sm-2 " for="location"> <spring:message
							code="LocationDetailsOfStaffDTO.locId" text="Current Location" /></label>
					<div class="col-sm-4">
						<form:select id="location" path="locationDetailsOfStaffDTO.locId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${location}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label "> <spring:message
							code="LocationDetailsOfStaffDTO.vendorId" text=" VendorList" />
					</label>
					<div class="col-sm-4">
						<form:select id="vmVendorid"
							path="locationDetailsOfStaffDTO.vendorId"
							cssClass="form-control vmVendorid">
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
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 " for=""> <spring:message
							code="LocationDetailsOfStaffDTO.cpdShiftId" text="Current Shift" />
					</label>

					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="locationDetailsOfStaffDTO.cpdShiftId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />
				</div>


				<div class="text-center margin-top-10">
					<input type="button" onClick="searchForm(this);"
						value="<spring:message code="LocationDetailsOfStaffDTO.view"/>"
						class="btn btn-success" id="Search">

					<button type="Reset" class="btn btn-warning" onclick="resetForm1()">
						<spring:message code="" text="Reset" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>

			</form:form>

		</div>
	</div>
</div>
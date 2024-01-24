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
<link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet"
	type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/SanitaryStaffScheduling.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/fullcalendar.min.js"></script>
<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Employee Scheduling" /></strong>
			</h2>
			<apptags:helpDoc url="SanitaryStaffScheduling.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="SanitaryStaffScheduling.html"
				class="form-horizontal form" name="" id="">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="gender"><spring:message
							code="swm.sanitary.employeeName" /></label>
					<div class="col-sm-4">
						<form:select path="employeeScheduleDto.empid"
							cssClass="form-control required-control chosen-select-no-results"
							label="Select" id="empid${d}">
							<form:option value="">
								<spring:message code="solid.waste.select" />
							</form:option>
							<c:forEach items="${command.employeeBeanList}" var="emp">
								<form:option value="${emp.empId}">${emp.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2"
						onclick="searchSanitaryStaffScheduling('SanitaryStaffScheduling.html', 'searchSanitaryStaffScheduling');">
						<i class="fa fa-search"></i>
						<spring:message code="swm.sanitary.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetSanitaryStaffScheduling();">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<button type="submit" class="btn btn-success add"
						onclick="openAddSanitaryStaffScheduling('SanitaryStaffScheduling.html','AddSanitaryStaffScheduling');"
						name="button-Add" id="button-submit">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="swm.sanitary.add" text="Add" />
					</button>
				</div>
				<!-- End button -->
				<div id="calendar"></div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->





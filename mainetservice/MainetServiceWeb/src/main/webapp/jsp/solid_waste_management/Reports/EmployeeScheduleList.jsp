<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/report/EmployeeScheduleReport.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="swm.sanitary.report.heading"
					text="Employee Schedule Report" />
			</h2>
			<apptags:helpDoc url="EmployeeScheduleReport.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="get" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div id="a0" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="select-1479372680758"
										class="col-sm-2 control-label required-control"><spring:message
											code="swm.sanitary.scheduleType" text="Schedule Type" /></label>
									<div class="col-sm-4">
										<form:select path="employeeScheduleDTO.empName"
											cssClass="form-control  chosen-select-no-results mandColorClass"
											name="select-1479372680758" id="emstype" aria-required="true">
											<form:option value="" selected="true">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<form:option value="D">
												<spring:message code="swm.sanitary.report.dis" text="MRF Center Wise" />
											</form:option>										
											<form:option value="V">
												<spring:message code="swm.sanitary.report.vehicle"
													text="Vehicle Wise" />
											</form:option>
											<form:option value="A">
												<spring:message code="swm.sanitary.report.area"
													text="Area Wise" />
											</form:option>
										</form:select>
									</div>
									<label for="select-1479366113414"
										class="col-sm-2 control-label required-control"><spring:message
											code="swm.sanitary.report.empname" text="Employee Name" /></label>
									<div class="col-sm-4">
										<form:select path="employeeScheduleDTO.empid"
											cssClass="form-control  chosen-select-no-results mandColorClass"
											label="Select" id="empId">
											<form:option value="">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<form:option value="0">
												<spring:message code="solid.waste.All" text="All" />
											</form:option>
											<c:forEach items="${command.employeeBeanList}" var="emp">
												<form:option value="${emp.empId}">${emp.fullName}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="vehicle.fuelling.fromDate"
										datePath="employeeScheduleDTO.fromDate" isMandatory="true"
										cssClass="custDate mandColorClass">
									</apptags:date>
									<apptags:date fieldclass="datepicker" labelCode="swm.toDate"
										datePath="employeeScheduleDTO.toDate" isMandatory="true"
										cssClass="custDate mandColorClass">
									</apptags:date>
								</div>
								<div class="text-center padding-top-10">
									<button type="button" class="btn btn-success btn-submit"
										onClick="employeeSchedulePrint('EmployeeScheduleReport.html','report')"
										data-original-title="Print">
										<spring:message code="solid.waste.print" text="Print"></spring:message>
									</button>
									<button type="Reset" class="btn btn-warning"
										onclick="resetTrip()">
										<spring:message code="solid.waste.reset" text="Reset" />
									</button>
									<apptags:backButton url="AdminHome.html"></apptags:backButton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
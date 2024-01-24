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
<div class="widget">
	<div class="widget-header">
		<h2>
			<strong><spring:message code="swm.sanitarystaffscheduling"
					text="Sanitary Staff Scheduling" /></strong>
		</h2>
		<apptags:helpDoc url="SanitaryStaffScheduling.html" />
	</div>

	<div class="widget-content padding">
		<form:form action="SanitaryStaffScheduling.html"
			class="form-horizontal form" name="" id="">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;">
				<i class="fa fa-plus-circle"></i>
			</div>

			<!--table responsive start-->
			<div class="table-responsive">
				<div class="table-responsive margin-top-10">
					<table class="table table-striped table-condensed table-bordered"
						id="id_sanitarystaffscheduling">
						<thead>
							<tr>
								<th><spring:message code="swm.target.wise.collection.sr.no" text="Sr. No." /></th>
								<th><spring:message code="swm.sanitary.scheduleType"
										text="Schedule Type" /><span class="mand">*</span></th>
								<th><spring:message code="swm.sanitary.employeeName"
										text="Employee Name" /><span class="mand">*</span></th>
								<th><spring:message code="swm.sanitary.scheduleFrom"
										text="Schedule From" /><span class="mand">*</span></th>
								<th><spring:message code="swm.sanitary.scheduleTo"
										text="Schedule To" /><span class="mand">*</span></th>
								<th><spring:message code="swm.sanitary.reccurance"
										text="Reccurance" /><span class="mand">*</span></th>
								<th><spring:message code="swm.action"
										text="Action" /></th>

							</tr>
						<tbody>
							<c:forEach items="${command.employeeScheduleList}"
								var="sanitaryscheduling" varStatus="loop">
								<tr>
									<td align="center">${loop.count}</td>
									<c:choose>
										<c:when test="${sanitaryscheduling.emsType eq 'D'}">
											<td align="center">MRF Center Wise</td>
										</c:when>
										<c:when test="${sanitaryscheduling.emsType eq 'T'}">
											<td align="center">Task Wise</td>
										</c:when>
										<c:when test="${sanitaryscheduling.emsType eq 'V'}">
											<td align="center">Vehicle Wise</td>
										</c:when>
										<c:otherwise>
											<td align="center">Area Wise</td>
										</c:otherwise>
									</c:choose>
									<td align="center">${command.employeeScheduleDto.empName}</td>
									<td align="center">${sanitaryscheduling.estfromdate}</td>
									<td align="center">${sanitaryscheduling.estTodate}</td>
									<c:choose>
										<c:when test="${sanitaryscheduling.emsReocc eq 'D'}">
											<td align="center">Daily</td>
										</c:when>
										<c:when test="${sanitaryscheduling.emsReocc eq 'W'}">
											<td align="center">Weekly</td>
										</c:when>
										<c:otherwise>
											<td align="center">Monthly</td>
										</c:otherwise>
									</c:choose>

									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View"
											onclick="modifySanitaryScheduling(${sanitaryscheduling.emsId},'SanitaryStaffScheduling.html','viewSanitaryStaffScheduling')">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit"
											onclick="modifySanitaryScheduling(${sanitaryscheduling.emsId},'SanitaryStaffScheduling.html','editSanitaryStaffScheduling')">
											<i class="fa fa-pencil"></i>
										</button>
										<button type="button" class="btn btn-danger btn-sm"
											title="Delete"
											onclick="deleteSanitaryScheduling(${sanitaryscheduling.emsId},'SanitaryStaffScheduling.html','deleteSanitaryStaffScheduling')">
											<i class="fa fa-trash"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<!-- table responsive end -->

		</form:form>

		<div class="text-center padding-top-10">
			<apptags:backButton url="SanitaryStaffScheduling.html"></apptags:backButton>
		</div>
		<!-- End Form -->
	</div>
</div>
<!-- End Widget Content here -->






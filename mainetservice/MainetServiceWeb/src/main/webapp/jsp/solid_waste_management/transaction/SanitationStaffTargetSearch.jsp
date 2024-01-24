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
<script type="text/javascript"
	src="js/solid_waste_management/SanitationStaffTarget.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.sanitationstaff"
						text="Sanitation Staff Target Summary" /></strong>
			</h2>
			<apptags:helpDoc url="VehicleTarget.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="VehicleTarget.html" commandName="command"
				class="form-horizontal form" name="SanitationStaffTarget"
				id="id_sanitation">

				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->

				<div class="form-group">
					<apptags:input labelCode="swm.fromDate" isReadonly="true"
						cssClass="fromDateClass fromDate"
						path="sanitationStaffTargetDto.sanTgfromdt" isMandatory="" />
					<apptags:input labelCode="swm.toDate" cssClass="toDateClass toDate" isReadonly="true"
						path="sanitationStaffTargetDto.sanTgtodt" isMandatory="" />
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchtarget('VehicleTarget.html','SearchSanitationStaffTarget')">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search" />
					</button>
					<button type="submit" class="button-input btn btn-success"
						onclick="openaddtarget('VehicleTarget.html', 'AddSanitationStaffTarget') "
						name="button-Add" style="" id="button-submit">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="swm.emptarget" text="add" />
					</button>
				</div>
				<!-- End button -->
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_sanitationStaffTable">
							<thead>
								<tr>
									<th><spring:message code="population.master.srno"
											text="SR. No." /></th>
									<th><spring:message code="swm.vehiclenumber"
											text="Vehicle Number" /></th>
									<th><spring:message code="swm.targetfrmDt"
											text="Target From Date" /></th>
									<th><spring:message code="swm.targettoDt"
											text="Target To Date" /></th>
									<th><spring:message code="swm.grbgVol"
											text="Garbage Volume" /></th>

									<th><spring:message code="swm.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.sanitationStaffTargetDets}"
									var="target" varStatus="loop">
									<tr>
										<td align="center">${target.sanId}</td>
										<td align="center"></td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
												value="${target.sanTgfromdt}" /></td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
												value="${target.sanTgtodt}" /></td>
										<td align="center">${target.total }<spring:message
												code="swm.kilo" text=" Kgs" /></td>
										<td class="text-center">
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit"
												onclick="modifySanitationStaffTarget(${target.sanId},'VehicleTarget.html','EditSanitationStaffTarget','E')">
												<i class="fa fa-pencil"></i>
											</button>
											<button type="button" class="btn btn-blue-3 btn-sm"
												title="Print"
												onclick="modifySanitationStaffTarget(${target.sanId},'VehicleTarget.html','PrintSanitationStaffTarget','P')">
												<i class="fa fa-building-o"></i>
											</button>

										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>




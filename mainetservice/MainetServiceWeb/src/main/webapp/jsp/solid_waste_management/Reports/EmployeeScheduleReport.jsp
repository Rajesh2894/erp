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
<script type="text/javascript">
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<spring:message code="swm.sanitary.report.heading"
					text="Employee Schedule Report" />
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="col-xs-2">
							<h1>
								<img width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-8 col-xs-8  text-center">
							<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
								<c:if test="${userSession.languageId eq 1}">
									 ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
         							 ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if>
								<spring:message code="swm.sanitary.report.heading"
									text="Employee Schedule Report"></spring:message>

							</h2>
						</div>
						<div class="col-xs-2">
							<p>
								<spring:message code="solid.waste.date" text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="solid.waste.time" text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						<div class="clearfix padding-10"></div>
						<div class="clearfix padding-10"></div>

						<div class="form-group">
							<label for="select-1479372680758" class="col-xs-2 control-label"><spring:message
									code="swm.fromDate" text="From Date" /></label>
							<div class="col-xs-4">
								<p>${command.employeeDTO.fromDate}</p>
							</div>
							<label for="select-1479372680758" class="col-xs-2 control-label"><spring:message
									code="swm.toDate" text="To Date" /></label>
							<div class="col-xs-4">
								<p>${command.employeeDTO.toDate}</p>
							</div>
						</div>
						<div class="padding-5 clear">&nbsp;</div>
						<div id="export-excel">
							<p>
								<c:if test="${command.employeeDTO.empName ne null}">
									<spring:message code="swm.empname" text="Employee Name" />: <b>${command.employeeDTO.empName}</b>
								</c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Schedule Type :
								<c:choose>
									<c:when test="${command.employeeDTO.emsType eq 'D'}">
										<b><spring:message code="swm.sanitary.report.dis"
												text="MRF Center Wise" /></b>
									</c:when>
									<c:when test="${command.employeeDTO.emsType eq 'T'}">
										<b><spring:message code="swm.sanitary.report.task"
												text="Task Wise" /></b>
									</c:when>
									<c:when test="${command.employeeDTO.emsType eq 'V'}">
										<b><spring:message code="swm.sanitary.report.vehicle"
												text="Vehicle Wise" /></b>
									</c:when>
									<c:when test="${command.employeeDTO.emsType eq 'A'}">
										<b><spring:message code="swm.sanitary.report.area"
												text="Area Wise" /></b>
									</c:when>
								</c:choose>
							</p>
							<table class="table table-bordered table-condensed">
								<thead>
									<tr>
										<th class="text-center"><spring:message code=""
												text="Vehicle No." /></th>
										<th class="text-center"><spring:message
												code="swm.sanitary.report.empname" text="Employee Name" /></th>
										<th class="text-center"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /></th>
										<th class="text-center"><spring:message
												code="swm.sanitary.scheduleType" text="Schedule Type" /></th>
										<th class="text-center"><spring:message
												code="swm.sanitary.reccurance" text="Recurrence" /></th>
										<th class="text-center"><spring:message code=""
												text="Waste Genrated Type" /></th>
										<th class="text-center"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /></th>
										<c:if test="${command.employeeDTO.emsType ne 'V'}">
											<th class="text-center"><spring:message
													code="swm.sanitary.toTime" text="To Time" /></th>
										</c:if>
										<c:if test="${command.employeeDTO.emsType eq 'V'}">
											<th class="text-center"><spring:message code=""
													text="Shift Type" /></th>
										</c:if>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="8" class="ts-pager form-horizontal">
											<div class="btn-group">
												<button type="button" class="btn first">
													<i class="fa fa-step-backward" aria-hidden="true"></i>
												</button>
												<button type="button" class="btn prev">
													<i class="fa fa-arrow-left" aria-hidden="true"></i>
												</button>
											</div> <span class="pagedisplay"></span> <!-- this can be any element, including an input -->
											<div class="btn-group">
												<button type="button" class="btn next">
													<i class="fa fa-arrow-right" aria-hidden="true"></i>
												</button>
												<button type="button" class="btn last">
													<i class="fa fa-step-forward" aria-hidden="true"></i>
												</button>
											</div> <select class="pagesize input-mini form-control"
											title="Select page size">
												<option selected="selected" value="10" class="form-control">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="all"><spring:message
														code="swm.report.all.records" text="All Records" />
												</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach
										items="${command.employeeScheduleDTO.employeeScheduleList}"
										var="data" varStatus="index">
										<tr>
											<td>${data.vehicleNo}</td>
											<td>${data.empName}</td>
											<td class="text-center">${data.empScheduledate}</td>
											<c:choose>
												<c:when test="${data.emsType eq 'D'}">
													<td><spring:message code="swm.sanitary.report.dis"
															text="MRF Center Wise" /></td>
												</c:when>
												<c:when test="${data.emsType eq 'T'}">
													<td><spring:message code="swm.sanitary.report.task"
															text="Task Wise" /></td>
												</c:when>
												<c:when test="${data.emsType eq 'V'}">
													<td><spring:message code="swm.sanitary.report.vehicle"
															text="Vehicle Wise" /></td>
												</c:when>
												<c:when test="${data.emsType eq 'A'}">
													<td><spring:message code="swm.sanitary.report.area"
															text="Area Wise" /></td>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${data.emsReocc eq 'D'}">
													<td><spring:message code="swm.sanitary.report.daily"
															text="Daily" /></td>
												</c:when>
												<c:when test="${data.emsReocc eq 'W'}">
													<td><spring:message code="swm.sanitary.report.Weekly"
															text="Weekly" /></td>
												</c:when>
												<c:when test="${data.emsReocc eq 'M'}">
													<td><spring:message code="swm.sanitary.report.Monthly"
															text="Monthly" /></td>
												</c:when>
											</c:choose>
											<td class="text-center"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(data.wasteType,'')" />
											</td>
											<td class="text-center">${data.fromDate}</td>
											<c:choose>
												<c:when test="${data.emsType ne 'V'}">
													<td class="text-center">${data.toDate}</td>
												</c:when>
												<c:when test="${data.emsType eq 'V'}">
													<td class="text-center"><spring:eval
															expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(data.shiftId,'')" /></td>
												</c:when>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('${cheque.dishonor.register}');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i>
							<spring:message code="solid.waste.print" text="Print" />
						</button>
						<button type="button" class="btn btn-danger" onclick="back();">
							<spring:message code="solid.waste.back" text="Cancel" />
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
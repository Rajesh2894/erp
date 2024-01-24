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
	src="js/solid_waste_management/allLogBookReport/SweepingReport.js"></script>
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
				<spring:message code="" text="Sweeping Survey Report" />
			</div>
			<div class="widget-content padding">
				<form:form action="" method="get" class="form-horizontal">
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
								<spring:message code="" text="Sweeping Survey Report" />
								<br>
								<spring:message code=""
									text="Commercial / Redsidential Cleaning  Report of Month" />
								:${command.vehicleScheduleDTO.monthName}<br>
								<spring:message code="" text="Beat Name" />
								:${command.vehicleScheduleDTO.beatName}<br>

							</h2>
						</div>
						<div class="clearfix padding-10"></div>
						<div class="padding-5 clear">&nbsp;</div>
						<div id="export-excel">
							<table class="table table-bordered table-condensed">
								<thead>
									<tr>
										<th class="text-center" width="50%" colspan="9">${command.vehicleScheduleDTO.colleType}</th>

									</tr>
									<tr>
										<th colspan="3">Present 1Shift</th>
										<th colspan="2">Present 2Shift</th>
									</tr>
									<tr>
										<th>Date</th>
										<th>Cleaning Person Name</th>
										<th>Time</th>
										<th>Cleaning Person Name</th>
										<th>Time</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="9" class="ts-pager form-horizontal">
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
									<c:if test="${command.vehicleScheduleDTO.collectionCode eq 'SS'}">
										<c:forEach
											items="${command.vehicleScheduleDTO.vehicleScheduleList}"
											var="data" varStatus="index">
											<tr>
												<td>${data.vehicleScheduledate}</td>
												<td>${data.empName}</td>
												<td>${data.vehStartTym}</td>
												<td>${data.sweepingemp}</td>
												<td>${data.sweepingintime}</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${command.vehicleScheduleDTO.collectionCode eq 'DC'}">
										<c:forEach
											items="${command.vehicleScheduleDTO.vehicleScheduleList}"
											var="data" varStatus="index">
											<tr>
												<td>${data.vehicleScheduledate}</td>
												<td>${data.drainingempName}</td>
												<td>${data.dariningvehStartTym}</td>
												<td>${data.drainingemp}</td>
												<td>${data.drainingintime}</td>
											</tr>
										</c:forEach>
									</c:if>
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
							<spring:message code="solid.waste.cancel" text="Cancel" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
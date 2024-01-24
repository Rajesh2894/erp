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
	src="js/solid_waste_management/report/expenditureIncurredReport.js"></script>
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
				<h2>Vehicle Maintenance Expenditure</h2>
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
								</c:if>Expenditure
								Incurred on Transportation
							</h2>
						</div>
						<div class="col-xs-2">
							<p>
								Date:
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br> Time:
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						<div class="clearfix padding-10"></div>
						<div class="clearfix padding-10"></div>

						<div class="form-group">
							<label for="select-1479372680758" class="col-xs-2 control-label">From
								Date</label>
							<div class="col-xs-4">
								<p class="padding-5">${command.vehicleMasterDTO.fromDate}</p>
							</div>
							<label for="select-1479372680758" class="col-xs-2 control-label">To
								Date</label>
							<div class="col-xs-4">
								<p class="padding-5">${command.vehicleMasterDTO.toDate}</p>
							</div>
						</div>
						<div class="padding-5 clear">&nbsp;</div>
						<div class="overflow-visible">
							<div id="export-excel">
							<p>
								Expense Type : <b>${command.vehicleMasterDTO.expenseType}</b>
							</p>
								<table class="table table-bordered table-condensed">
									<thead>
										<tr>
											<th>Maintenance Date</th>
											<th>Vehicle Type</th>
											<th>Vehicle No.</th>
											<c:if test="${command.isPSCLEnv ne 'Y'}">
												<th>Vehicle Down Time Actual</th>
											</c:if>
											<th>Vehicle Reading During Maintenance</th>
											<th>Total Cost Incurred</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th colspan="6" class="ts-pager form-horizontal">
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
													<option value="all">All Records</option>
											</select> <select class="pagenum input-mini form-control"
												title="Select page number"></select>
											</th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach
											items="${command.vehicleMaintenanceDTO.vehicleMaintenanceList}"
											var="data" varStatus="index">
											<tr>
												<td class="text-center">${data.mantenanceDate}</td>
												<td>${data.veName}</td>
												<td class="text-center">${data.veNo}</td>
												<c:if test="${command.isPSCLEnv ne 'Y'}">
													<td class="text-right">${data.vemDowntime}&nbsp;<spring:eval
														expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getCPDDescription(data.vemDowntimeunit,'V')" /></td>
												</c:if>
												<td class="text-right">${data.vemReading}</td>
												<td class="text-right">${data.vemCostincurred}</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<c:choose>
												<c:when test="${command.isPSCLEnv eq 'Y'}">
													<th class="text-right" colspan="4">Total</th>
												</c:when>
												<c:otherwise>
													<th class="text-right" colspan="5">Total</th>
												</c:otherwise>
											</c:choose>
											<th class="text-right">${command.vehicleMaintenanceDTO.totalCost}</th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('${cheque.dishonor.register}');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i> Print
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
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
<script type="text/javascript" src="js/rnl/report/report.js"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script>
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
	<!-- Start Content here -->
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">
					<spring:message code="rnl.outstanding.report" text="Outstanding Report" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<style>
							@media print {
								@page {
									margin: 15px;
									size: landscape;
								}
							}
						</style>
						<div class="form-group">
						<!-- D#76903 -->
						<div class="col-sm-2 col-xs-3">
								<%-- <p>
									<spring:message code="rnl.outstanding.register" text="Report Till Date"/>: ${command.reportDTO.asOnDate}
								</p> --%>
							</div>
							<div
								class="col-sm-6 col-sm-offset-1 col-xs-6 col-xs-offset-1 text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if></h3>
								<h3
									class="text-extra-large margin-bottom-0 margin-top-0 excel-title">
									<spring:message code="rnl.outstanding.register"
										text="OUTSTANDING REGISTER" />
								</h3>
								<%-- <p>
									<strong>From Date:</strong> ${command.reportDTO.fromDateString} <strong>To
										Date:</strong> ${command.reportDTO.toDateString}
								</p> --%>
							</div>
							<div class="col-sm-2 col-xs-3 col-sm-offset-1">
								<p>
									<spring:message code='estate.date' text="Date" />:
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									</br><spring:message code='estate.time' text="Time"/>:
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
								</p>
								
								
							</div>
						</div>

						<input type="hidden" value="${validationError}" id="errorId">
						<div class="margin-top-10">
							<div id="export-excel">
								<table class="table table-bordered table-condensed">
									<thead>
										<tr>
											<th><spring:message code="receipt.register.srno"
													text="Sr. No." /></th>
											<th><spring:message
													code="rnl.outstanding.report.lessee" text="Name Of Lessee" /></th>
											<th><spring:message code="rnl.outstanding.report.propertyName"
													text="Property Name" /></th>
											<th><spring:message code="rnl.outstanding.report.contractNo"
													text="Contract Number" /></th>
											<th><spring:message code="rnl.outstanding.report.outAmt"
													text="Outstanding Amount" /></th>
										</tr>
									</thead>
									<tfoot class="tfoot">
										<tr>
											<th colspan="5" class="ts-pager form-horizontal">
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
										<c:forEach items="${command.summaryReportDetails}"
											var="summary" varStatus="status">
											<tr>
												<td align="center">${status.count}</td>
												<td align="left">${summary.nameOfLessee}</td>
												<td align="left">${summary.propertyName}</td>
												<td align="center">${summary.contractNo}</td>
												<td align="right">${summary.outstandingAmt}</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<th colspan="4"><spring:message
													code="rnl.outstanding.report.totalOutAmt" text="Total Outstanding Amount" /></th>
											<th class="text-right">${command.reportDTO.cashAmountTotalIndianCurrency}</th>											
										</tr>
									</tfoot>

								</table>
							</div>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('receipt');"
							class="btn btn-primary hidden-print" title="Print">
							<i class="fa fa-print"></i>
							<spring:message code="account.budgetestimationpreparation.print"
								text="Print" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='EstateOutstandingReport.html'"
							title="Back">
							<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
							<spring:message code="account.bankmaster.back" text="Back" />
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
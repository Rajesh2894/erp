<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
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
			size: 12
		});
	});
</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message code="account.Statement.on.Status.of.Cheques.Received" text="Statement on Status of Cheques Received"></spring:message></h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
				      <h2 class="excel-title" style="display:none"><spring:message code="account.Statement.on.Status.of.Cheques.Received" text="Statement on Status of Cheques Received"></spring:message></h2>
					<div
						class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-2  text-center">
						<h3 class="text-large margin-bottom-0 margin-top-0 text-bold"><c:if test="${userSession.languageId eq 1}">
							${userSession.getCurrent().organisation.ONlsOrgname}
							</c:if>
							
							<c:if test="${userSession.languageId ne 1}">
							${userSession.getCurrent().organisation.ONlsOrgnameMar}
							</c:if>
							<br> <spring:message code="account.Statement.on.Status.of.Cheques.Received" text="Statement on Status of Cheques Received"></spring:message>
						</h3>
						<p>
							<strong><spring:message code="accounts.financial.audit.trail.fromdate"
								text="From Date:" /></strong>
							${reportData.fromDate}
							<strong><spring:message code="accounts.financial.audit.trail.todate"
								text="To Date:" /></strong>
							${reportData.toDate}
						</p>
					</div>
					<div class="col-sm-2">
						<p>
						<spring:message code="accounts.date" text="	Date:"></spring:message> <fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							<br>
							<spring:message code="acounts.time" text="Time:"></spring:message> <fmt:formatDate value="${date}" pattern="hh:mm a" />
						</p>
					</div>

					<div class="clearfix padding-10"></div>
					<!-- <div class="overflow-visible margin-top-10"> -->
					<div class="margin-top-10">
						<div class="table-responsive" id="export-excel">
							<table class="table table-bordered  table-fixed" id="importexcel">
								<div id="tlExcel" class="excel-title" style="display:none"><spring:message code="account.Statement.on.Status.of.Cheques.Received" text="Statement on Status of Cheques Received"></spring:message></div>
								<thead>
									<tr>
										<th width="8%"> <spring:message code="cheque.dd.receipt.number" text="Receipt No."></spring:message></th>
										<th> <spring:message  code="receipt.register.receiptdate" text="Receipt Date"></spring:message></th>
										<th> <spring:message code="accounts.receipt.received_from" text="Received From"></spring:message></th>
										<th><spring:message code="accounts.receipt.cheque.number" text="Cheque No."></spring:message></th>
										<th width="12%"><spring:message code="account.Cheque.Amount(Rs.)" text="Cheque Amount(Rs.)"></spring:message></th>
										<th><spring:message code="accounts.receipt.cheque.date" text="Cheque Date"></spring:message></th>
										<th><spring:message code="account.bankmaster.bankname" text="Bank Name"></spring:message></th>
										<th><spring:message code="account.bankmaster.status" text="Status"></spring:message></th>
									</tr>
								</thead>
								<tfoot class="tfoot">
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
												<option selected="selected" value="12" class="form-control">12</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="all"><spring:message code="account.all.records" text="All Records"></spring:message></option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach items="${reportData.listOfBudgetEstimation}"
										var="chequeReceived">
										<tr>
											<td>${chequeReceived.receiptNo}</td>
											<td>${chequeReceived.receiptDate}</td>
											<td>${chequeReceived.receivedFrom}</td>
											<td>${chequeReceived.chequeNo}</td>
											<td style="text-align: right">${chequeReceived.rdAmount}</td>
											<td>${chequeReceived.chequeIssueDate}</td>
											<td>${chequeReceived.bankname}</td>
											<td>${chequeReceived.remarks}</td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>		
					</div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${cheque.dishonor.register}');"
						class="btn btn-primary hidden-print" type="button" title="Print">
						<i class="fa fa-print"></i> <spring:message code="account.budgetestimationpreparation.print" text="Print"></spring:message>
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountDailyReports.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
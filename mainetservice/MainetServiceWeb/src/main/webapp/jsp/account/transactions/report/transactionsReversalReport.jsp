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
	$(document).ready(function() {
		$('.fancybox').fancybox();
	});
</script>


<ol class="breadcrumb">
	<li><a href="#"><i class="fa fa-home"></i></a></li>
	<li>Transactions Reversal Report</li>
</ol>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message code="transactions.Reversal.Report" text="Transactions Reversal Report" /></h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
				      <h2 class="excel-title" style="display:none" id="tlExcel">Transactions Reversal Report</h2>
					<div
						class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
						<p class="text-large margin-bottom-0 margin-top-0 text-bold">
							<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
							</c:if>
							<br><spring:message code="transactions.Reversal.Report" text="Transactions Reversal Report" /> :${reportData.accountHead}
						</p>
						<p><spring:message code="account.fromDate" text="From date" />:${reportData.fromDate} &nbsp; 
							<spring:message code="account.todate" text="To date" />:${reportData.toDate}</p>
					</div>
					<div class="col-sm-2 col-xs-3">
						<p>
							<spring:message code="account.date" text="date" />:
							<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							<br><spring:message code="account.time" text="Time" />:
							<fmt:formatDate value="${date}" pattern="hh:mm a" />
						</p>
					</div>
					<div class="padding-5 clear">&nbsp;</div>
						<div id="export-excel">
							<table class="table table-bordered table-condensed" id="importexcel">
								<thead>
									<tr>
										<th>Transaction No</th>
										<th>Transaction Date</th>
										<th>Amount</th>
										<th>Narration</th>
										<th>Receiver/ Payer Name</th>
										<th>Reversed By</th>
										<th>Reversed Date</th>
										<th>Reversal Reason</th>
										<th>Authorized By</th>
									</tr>
								</thead>

								<tfoot class="tfoot">
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
													<i class="fa fa-arrow-left" aria-hidden="true"></i>
												</button>
												<button type="button" class="btn last">
													<i class="fa fa-step-forward" aria-hidden="true"></i>
												</button>
											</div> <select class="pagesize input-mini form-control"
											title="Select page size">
												<option selected="selected" value="12" class="form-control">12</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="all">All Records</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach items="${reportData.transactionReversal}"
										var="transactionrevesal">
										<tr>
											<td>${transactionrevesal.transactionNo}</td>
											<td>${transactionrevesal.transactionDate}</td>
											<td style="text-align: right">${transactionrevesal.amount}</td>
											<td>${transactionrevesal.narration}</td>
											<td>${transactionrevesal.receivePayname}</td>
											<td>${transactionrevesal.reversedBy}</td>
											<td>${transactionrevesal.reversedDate}</td>
											<td>${transactionrevesal.reversalReason}</td>
											<td>${transactionrevesal.authorizedBy}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${cheque.dishonor.register}');"
						class="btn btn-primary hidden-print" type="button" title="Print">
						<i class="fa fa-print"></i> <spring:message code="account.budgetestimationpreparation.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountOtherReports.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

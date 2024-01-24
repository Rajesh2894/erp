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
<jsp:useBean id="date" class="java.util.Date"  scope="request"/>
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
	$(function() {

		$(".table").tablesorter({

			cssInfoBlock : "avoid-sort",

		});

	});
</script>


<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message
                            code="bank.Account.Wise.Balance.Statement" text="Bank Account Wise Balance Statement" /></h2>
		</div>
		<div class="widget-content padding">
			<div id="receipt">
			<form action="" method="get" class="form-horizontal">
				          <h2 class="excel-title" style="display:none"><spring:message
                            code="bank.Account.Wise.Balance.Statement" text="Bank Account Wise Balance Statement" /></h2>
					<div class="form-group">
						<div
							class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1 text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
								<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if>
								<br><spring:message
                            code="bank.Account.Wise.Balance.Statement" text="Bank Account Wise Balance Statement" />
							</h3>
							<p>
								<strong><spring:message
                            code="from.date.label" text="From Date" />:</strong>${reportData.fromDate} &nbsp; <strong><spring:message
                            code="day.book.report.todate" text="To Date" />:</strong>${reportData.toDate}
							</p>
							<p>
								<strong><spring:message
										code="cheque.payment.register.fieldId" text="Field Name:" /></strong>
								<c:if test="${not empty fieldName}">
									
										${fieldName}
									
								</c:if>

								<c:if test="${ empty fieldName}">All
								</c:if>
							</p>
						</div>
						<div class="col-sm-2 col-xs-3">
							<p>
								<spring:message
                            code="account.date" text="Date" />:
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br><spring:message
                            code="account.time" text="Time" />:
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
					</div>
					<div class="margin-top-10">
						<div id="export-excel">
							<table class="table table-bordered table-condensed" id="importexcel">
								<h2 class="excel-title" id="tlExcel" style="display:none"><spring:message
                            code="bank.Account.Wise.Balance.Statement" text="Bank Account Wise Balance Statement" /></h2>
								<thead>
									<tr>
										<th width="20%"> <spring:message text="Bank Name/Br. Name" code="account.Bank.Name/Br.Name" ></spring:message></th>
										<th width="10%"><spring:message code="bank.master.acc.type" text=" Bank A/C Type"></spring:message> </th>
										<th width="30%"> <spring:message code="account.Bank.a/c.Name/No" text="Bank a/c Name / No."></spring:message></th>
										<th width="10%"> <spring:message code="account.Opening.Balace.Rs" text="Opening Balance Rs."></spring:message></th>
										<th width="10%"> <spring:message code="account.Receipts.Amount.Rs" text="Receipts Amount Rs."></spring:message></th>
										<th width="10%"><spring:message code="account.Payments.Amount.Rs" text="Payments Amount Rs."></spring:message></th>
										<th width="10%"><spring:message code="account.Closing.Balance.Rs" text="Closing Balance Rs."></spring:message></th>
									</tr>
								</thead>
								<tfoot class="tfoot">

									<tr>
										<th colspan="7" class="ts-pager form-horizontal">
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
									<c:forEach items="${reportData.bankAccountSummary}"
										var="bankAcntSummary">
										<tr>
											<td>${bankAcntSummary.bankname}</td>
											 <td>${bankAcntSummary.accountType}</td>
											<td>${bankAcntSummary.baBankAcName}-${bankAcntSummary.bankAcNo}</td>
											<td style="text-align: right">${bankAcntSummary.openingBalancers}</td>
											<td style="text-align: right">${bankAcntSummary.receiptAmt}</td>
											<td style="text-align: right">${bankAcntSummary.paymentAmnt}</td>
											<td style="text-align: right">${bankAcntSummary.closingAmt}</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot class="avoid-sort">
									<tr>
										<th colspan="3" class="text-right">Total</th>
										<th style="text-align: right">${reportData.totalOpeningBalancers}</th>
										<th style="text-align: right">${reportData.totalReceiptAmt}</th>
										<th style="text-align: right">${reportData.totalPaymentAmt}</th>
										<th style="text-align: right">${reportData.totalClosingAmt}</th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
			</form>
			</div>
			<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${cheque.dishonor.register}');"
						class="btn btn-primary hidden-print" type="button" title="Print">
						<i class="fa fa-print"></i> <spring:message code="account.budgetestimationpreparation.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountDailyReports.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
			</div>
		</div>

	</div>
</div>





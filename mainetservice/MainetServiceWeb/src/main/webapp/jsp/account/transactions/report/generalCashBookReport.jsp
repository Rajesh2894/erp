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
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />

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
	$(function() {

		$(".table").tablesorter({

			cssInfoBlock : "avoid-sort",

		});

	});
</script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">
					<spring:message code="general.cash.book" text="General Cash Book" />
				</h2>
			</div>
			<div class="widget-content padding">
			<div id="receipt">
				<form action="" method="get" class="form-horizontal">
						<h2 class="excel-title" style="display:none">
							<spring:message code="general.cash.book" text="General Cash Book" />
						</h2>
						<div class="form-group">
							<div
								class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if></h3>
								<strong><spring:message code="general.cash.book"
										text="General Cash Book" /></strong> <br> <strong>
									<spring:message
                            code="from.date.label" text="From Date" />:</strong> ${reportData.fromDate} <strong><spring:message
                            code="day.book.report.todate" text="To Date" />:</strong>
								${reportData.toDate}
								<c:if test="${not empty fieldName}">
									<p>
										<strong><spring:message
												code="cheque.payment.register.fieldId" text="Field Name:" /></strong>
										${fieldName}
									</p>
								</c:if>
							</div>
							<div class="col-sm-2 col-xs-3">
								<p>
									<spring:message
                            code="account.date" text="Date" />:
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									<br><spring:message
                            code="account.time" text="Time" />:
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</div>
						</div>
						<input type="hidden" value="${validationError}" id="errorId">
						<div class="margin-top-10">
							<div id="export-excel">
								<table class="table table-bordered table-condensed" id="importexcel">
									<div class="excel-title" id="tlExcel" style="display:none;"> <spring:message code="general.cash.book" text="General Cash Book" /></div>
									<thead>
										<tr>
											<th colspan="2" style="text-align: center;width: 10%"><spring:message
													code="general.cash.opening.balance"
													text="Opening balance as on" /></th>
											<th colspan="7" align="right" class="text-right">${reportData.openingBalance}
												${reportData.drCrType}</th>
										</tr>
										<tr>
											<th style="text-align: center;width: 10%"><spring:message
													code="account.voucher.date" text="Voucher Date" /></th>
											<th><spring:message code="account.voucher.number"
													text="Voucher No." /></th>

											<th><spring:message code="account.deposit.accountcode"
													text="Account Head" /></th>
											<th><spring:message code="general.cash.payee"
													text="Payer/Payee" /></th>
											<th><spring:message code="general.cash.particulars"
													text="Particulars" /></th>
											<th><spring:message code="general.cash.amount.debit"
													text="Amount Debit (Rs.)" /></th>
											<th><spring:message code="general.cash.amount.credit"
													text="Amount Credit (Rs.)" /></th>
											<th><spring:message code="general.cash.closing.bal"
													text="Closing Balance (Rs.)" /></th>
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
													<option value="all">All Records</option>
											</select> <select class="pagenum input-mini form-control"
												title="Select page number"></select>
											</th>
										</tr>
									</tfoot>



									<tbody>
										<c:forEach items="${reportList}" var="generalCachBook">
											<tr>
												<td>${generalCachBook.voucherDate}</td>
												<td>${generalCachBook.voucherNo}</td>

												<td>${generalCachBook.accountHead}</td>
												<td>${generalCachBook.payerPayee}</td>
												<td>${generalCachBook.particular}</td>
												<td align="right">${generalCachBook.drAmount}</td>
												<td align="right">${generalCachBook.crAmount}</td>
												<td align="right">${generalCachBook.closingBalance}</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<th colspan="2" style="text-align: left"><spring:message
													code="general.cash.closing.balance.as"
													text="Closing balance as on" /></th>
											<td></td>
											<td></td>
											<td></td>
											<th class="text-right">${reportData.totalDrAmount}</th>
											<th class="text-right">${reportData.totalCrAmount}</th>

											<th class="text-right">${reportData.closingBalanceAsOn}</th>
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
							<i class="fa fa-print"></i>  <spring:message code="account.budget.code.print" text="Print" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='AccountDailyReports.html'"
							title="Back">
							<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
							<spring:message code="account.bankmaster.back" text="Back" />
						</button>
				</div>
			</div>
		</div>
	</div>
</div>
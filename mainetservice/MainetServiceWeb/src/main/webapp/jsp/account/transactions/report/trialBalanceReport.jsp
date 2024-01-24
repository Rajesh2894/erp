<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">
					<spring:message code="balance.sheet.trial.balancess"
						text="Trial Balance" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<!-- <div class="form-group"> -->
						<div
							class="col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-1 text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
								<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if>
							</h3>
							<strong class="excel-title"><spring:message
									code="balance.sheet.trial.balancess" text="Trial Balance" /></strong>
							<p>
								<strong> <spring:message
                            code="from.date.label" text="From Date" />:</strong> ${dto.fromDate} <strong>
									 <spring:message
                            code="account.todate" text="To Date" />:</strong> ${dto.toDate}
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
						<div class="clear"></div>
						<input type="hidden" value="${validationError}" id="errorId">
						<div class="margin-top-10">
							<div id="export-excel">
								<table class="table table-bordered table-condensed" id="importexcel">
									<div class="excel-title" id="tlExcel" style="display: none">
										<spring:message code="balance.sheet.trial.balancess"
											text="Trial Balance" />
									</div>
									<thead>
										<tr class="head">
											<th rowspan="2"><spring:message
													code="receipt.payment.particular.acchead"
													text="Particulars/Account Head" /></th>
											<th colspan="2"><spring:message code="bank.master.opBal"
													text="Opening Balance" /></th>
											<th colspan="2"><spring:message
													code="receipt.payment.transaction" text="Transactions" /></th>
											<th colspan="2"><spring:message
													code="bank.master.closeBal" text="Closing Balance" /></th>
										</tr>
										<tr>
											<th><spring:message code="receipt.payment.debit"
													text="Debit (Rs.)" /></th>
											<th><spring:message code="receipt.payment.credit"
													text="Credit (Rs.)" /></th>
											<th><spring:message code="receipt.payment.debit"
													text="Debit (Rs.)" /></th>
											<th><spring:message code="receipt.payment.credit"
													text="Credit (Rs.)" /></th>
											<th><spring:message code="receipt.payment.debit"
													text="Debit (Rs.)" /></th>
											<th><spring:message code="receipt.payment.credit"
													text="Credit (Rs.)" /></th>
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
													<option value="all">All Records</option>
											</select> <select class="pagenum input-mini form-control"
												title="Select page number"></select>
											</th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach items="${dto.listOfSum}" var="trailBalance">


											<tr>

												<td>${trailBalance.accountCode}</td>
												<%-- <td>${trailBalance.particular}</td> --%>
												<td align="right">${trailBalance.indCurrOpeningDrAmount}</td>
												<td align="right">${trailBalance.indCurrOpeningCrAmount}</td>
												<td align="right">${trailBalance.indCurrTransactionDrAmount}</td>
												<td align="right">${trailBalance.indCurrTransactionCrAmount}</td>
												<td align="right">${trailBalance.indCurrClosingDrAmount}</td>
												<td align="right">${trailBalance.indCurrClosingCrAmount}</td>

											</tr>

										</c:forEach>
									</tbody>
									<tfoot class="avoid-sort">
										<tr>
											<th><b><spring:message code="account.voucher.total"
														text="Total" /></b></th>
											<%-- <td>${trailBalance.particular}</td> --%>
											<th style="text-align: right;">${dto.indCurrSumOpeningDR}</th>
											<th style="text-align: right;">${dto.indCurrSumOpeningCR}</th>
											<th style="text-align: right;">${dto.indCurrSumTransactionDR}</th>
											<th style="text-align: right;">${dto.indCurrSumTransactionCR}</th>
											<th style="text-align: right;">${dto.indCurrSumClosingDR}</th>
											<th style="text-align: right;">${dto.indCurrSumClosingCR}</th>
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
							onclick="window.location.href='AccountFinancialReport.html'"
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



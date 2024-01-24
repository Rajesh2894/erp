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
	$(function() {

		$(".table").tablesorter({

			cssInfoBlock : "avoid-sort",

		});

	});
	//Print Div
</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message
										code="account.deposit.registerofdeposit"
										text="Register of deposits" /></h2>
		</div>
		<div class="widget-content padding">
		<div id="receipt">
			<form action="" method="get" class="form-horizontal">
					<div class="form-group">
						<div class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1 text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if>  </h3>
							 <strong class="excel-title"><spring:message
										code="account.deposit.registerofdeposit"
										text="Register of deposits" /></strong>
							<p>
								<strong><spring:message
                            code="from.date.label" text="From Date" />:</strong> ${reportData.fromDate} <strong><spring:message
                            code="day.book.report.todate" text="To Date" />:</strong>${reportData.toDate}
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
							<table class="table table-bordered table-condensed clear" id="importexcel">
							<div class="excel-title" id="tlExcel" style="display: none"> <spring:message code="account.deposit.registerofdeposit" text="Register of Deposits"></spring:message></div>
								<thead>
									<tr>

										<th style="text-align: center;width:8%"><spring:message code="account.deposit.depNo"
												text="Deposit No." /></th>
										<th width="8%"><spring:message
												code="account.deposit.name" text="Name of the Depositor." /></th>
										<th width="20%"><spring:message
												code="account.deposit.purpose" text="Purpose of deposit" /></th>
										<th width="10%"><spring:message
												code="account.deposit.deposittype" text="Type of Deposit" /></th>
										<th width="10%"><spring:message
												code="account.deposit.amountofdeposit"
												text=" Deposit Amount (Rs.)" /></th>
										<th width="12%"><spring:message
												code="account.deposit.receiptvoucher"
												text="Receipt Voucher No.& Date" /></th>
										<th width="10%"><spring:message
												code="account.deposit.amountrecovereds"
												text="Recovered Amount (Rs.)" /></th>
										<th width="13%"><spring:message
												code="account.deposit.voucher.adjustment"
												text="Adjustment/ Forfeiture Amount (Rs.)" /></th>
										<th width="12%"><spring:message
												code="account.deposit.amountofdeposits"
												text="Refund Amount (Rs.)" /></th>
										<th width="13%"><spring:message
												code="account.deposit.balance" text="Balance (Rs.)" /></th>
									</tr>
								</thead>
								<tfoot class="tfoot">
									<tr>
										<th colspan="10" class="ts-pager form-horizontal">
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
												<option value="all"> <spring:message code="account.all.records" text="All Records"></spring:message></option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<c:forEach items="${reportData.listOfDeposit}"
									var="depositRegister">
									<tr>

										<td align="right">${depositRegister.depositNumber}</td>
										<td>${depositRegister.payerPayee}</td>
										<td>${depositRegister.accountCode}<br>${depositRegister.depositNarration}</td>
										<td>${depositRegister.typeOfDeposit}</td>
										<td align="right">${depositRegister.depositAmountIndianCurrency}</td>
										<td align="center">${depositRegister.depositRecieptNumber}<br>${depositRegister.depositDate}</td>
										<td align="right">${depositRegister.recoveredDepositAmountIndianCurrency}</td>
										<td align="right">${depositRegister.adjustmentForfeitureAmount}<br>${depositRegister.adjustmentForfeitureNo}</td>
										<td align="right">${depositRegister.voucherAmountIndianCurrency}<br>${depositRegister.voucherNo}</td>
										<td align="right">${depositRegister.depositBalanceIndianCurrency}</td>
									</tr>
								</c:forEach>
								</tbody>
								<tfoot class="avoid-sort">
									<tr>
										<th colspan="4" class="text-right"><spring:message code="account.total" text="Total"></spring:message></th>
										<th class="text-right">${reportData.totalDepositIndianCurrency}</th>
										<th></th>
										<th class="text-right">${reportData.totalRecoveredDepositAmountIndianCurrency}</th>
										<th class="text-right">${reportData.totalAdjustmentForfeitureAmount}</th>
										<th class="text-right">${reportData.voucherAmountIndianCurrency}</th>
										<th class="text-right">${reportData.totalBalanceIndianCurrency}</th>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
			</form>
			</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${account.deposit.formnumber}');"
						class="btn btn-primary hidden-print" type="button"  title="Print">
						<i class="fa fa-print"></i> <spring:message code="account.budget.code.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountOtherReports.html'"  title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
		</div>
	</div>
</div>
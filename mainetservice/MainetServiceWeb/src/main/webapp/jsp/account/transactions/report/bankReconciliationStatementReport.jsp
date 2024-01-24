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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message
										code="bank.reconciliation.statement"
										text="Bank Reconciliation Statement" /></h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<!-- <div class="form-group"> -->
						<div class="col-xs-8 col-sm-6 col-sm-offset-3 col-xs-offset-1  text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">
							<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
							</c:if>
						</h3>
						<strong class="excel-title"><spring:message
										code="bank.reconciliation.statement"
										text="Bank Reconciliation Statement" /> </strong>

						</div>
						<div class="col-sm-3 col-xs-3">
							<p>
								<spring:message
                            code="account.date" text="Date" />:
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br><spring:message
                            code="account.time" text="Time" />:
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
						</div>
					<!-- </div> -->
					<div class="clear"></div>
					<div class="row margin-top-10">
						<div class="col-sm-9 col-xs-9">
							<p>
								<strong> <spring:message
										code="bank.reconciliation.bankname" text=" Name of the Bank" />
								</strong> ${reportData.accountCode}
							</p>
						</div>

						<div class="col-sm-3 col-xs-3">
							<p>
								<strong><spring:message code="account.deposit.date"
										text="As on Date" /></strong> ${reportData.transactionDate}
							</p>
						</div>
					</div>
					<div class="margin-top-10">
						<div id="export-excel">
							<table class="table table-bordered table-condensed" id="importexcel">
							<div class="excel-title" id="tlExcel" style="display: none" >Bank Reconciliation Statement</div>
								<thead>
									<tr>
										<th width="40px;"><spring:message
												code="general.cash.particulars" text="Particulars" /></th>
										<th width="10px;"><spring:message
												code="account.deposit.rs" text="Amount (Rs.)" /></th>
										<th width="10px;"><spring:message
												code="account.deposit.rs" text="Amount (Rs.)" /></th>
									</tr>
								</thead>
								<tfoot class="tfoot">
									<tr>
										<th colspan="15" class="ts-pager form-horizontal">
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
									<tr>
										<td><spring:message
												code="bank.reconciliation.bankBalance"
												text="Bank Balance as per Cash Book" /></td>
										<td></td>
										<td align="right">${reportData.openingBalanceIndianCurrency}</td>
									</tr>

									<tr>
										<td><spring:message code="bank.reconciliation.addA"
												text="Add: (A) Cheques issued but not presented into bank " /></td>
										<td align="right">${reportData.chequeAmountIndianCurrency}</td>
										<td></td>
									</tr>


									<tr>
										<td><spring:message code="bank.reconciliation.addB"
												text="Add: (B) Cheques drawn but not actually issued to parties "></spring:message></td>
										<td></td>
										<td></td>
									</tr>

									<tr>
										<td><spring:message code="bank.reconciliation.addC"
												text="Add: (C ) Cheque issued and payment stopped by CB" /></td>
										<td align="right">${reportData.balanceAmountIndianCurrency}</td>
										<td></td>
									</tr>

									<tr>
										<td><spring:message code="bank.reconciliation.addD"
												text="Add: (D) Credits of investment proceeds in Bank (e.g. Fixed" /></td>
										<td></td>
										<td></td>
									</tr>



									<tr>
										<td><spring:message code="bank.reconciliation.addE"
												text="Add: (E) Amount (Cash or Cheque) deposited by the" /></td>
										<td></td>
										<td></td>
									</tr>


									<tr>
										<td><spring:message code="bank.reconciliation.addF"
												text="Add: (F) Credit given by Bank either for interest or for any other" /></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>

								<tfoot class="avoid-sort">
									<tr>
										<td><strong><spring:message
													code="bank.reconciliation.subtotal" text="Sub-total" /></strong></td>
										<td></td>
										<td align="right"><strong>${reportData.chequeAmountIndianCurrency}</strong></td>
									</tr>
								</tfoot>

								<tbody>
									<tr>
										<td><spring:message code="bank.reconciliation.addH"
												text="Less: (H) Cheques Deposited but not cleared" /></td>
										<td align="right">${reportData.chequeDepositIndianCurrency}</td>
										<td></td>
									</tr>

									<tr>
										<td><spring:message code="bank.reconciliation.addI"
												text="Less: (I) Payments directly made by the bank but not" /></td>
										<td></td>
										<td></td>
									</tr>


									<tr>
										<td><spring:message code="bank.reconciliation.addJ"
												text="Less: (J) Cheques deposited but dishonoured" /></td>
										<td align="right">${reportData.chequeDishonouredIndianCurrency}</td>
										<td></td>
									</tr>

									<tr>
										<td><spring:message code="bank.reconciliation.addK"
												text="Less: (K) Service Charges / Bank Charges or any other" /></td>
										<td></td>
										<td></td>
									</tr>
									
								</tbody>
								<tfoot class="avoid-sort">
								<tr>
									<td><strong><spring:message
												code="bank.reconciliation.subtotal" text="Sub Total" /></strong></td>
									<td style="text-align: right;"></td>
									<td style="text-align: right;"><strong>${reportData.totalDepositAndDishonouredIndianCurrency}</strong></td>
								</tr>

								<tr>
									<th><spring:message
											code="bank.reconciliation.bankstatement"
											text="Bank Balance as per Pass Book/Bank Statement" /></th>
									<th style="text-align: right;"></th>
									<th style="text-align: right;">${reportData.subTractionTatalIndianCurrency}</th>
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
						onclick="window.location.href='AccountFinancialReport.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>

			</form>
		</div>
	</div>
</div>

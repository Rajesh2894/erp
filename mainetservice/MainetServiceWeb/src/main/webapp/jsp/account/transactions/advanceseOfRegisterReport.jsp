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
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
	
</script>
<style>
	
#receipt .table tr th {
	padding:4px 10px 4px 20px !important;
}
</style>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="accounts.registerof.advances.header"
						text="Register of Advances" />
				</h2>
			</div>
			<div class="widget-content padding">
				<div id="receipt">
				<form action="" method="get" class="form-horizontal">
					
						<h2 style="display:none" class="excel-title">
							<spring:message code="accounts.registerof.advances.header" text="Register of Advances" />
						</h2>
						<div class="form-group padding-bottom-10">
							<div class="col-xs-10 text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if> </h3>
								<strong> <spring:message
										code="accounts.registerof.advances.heading"
										text="Register of Advances As On Date" />:
									${command.advanceEntryDTO.advanceDate}
								</strong>
							</div>
							<div class="col-sm-2">
								<p>
									<strong><spring:message code="accounts.date" text="Date" /></strong> :
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								</p>
								<p>
									<strong><spring:message
											code="accounts.financial.audit.trail.time" text="Time" /></strong> :
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
								</p>
							</div>
						</div>
							<div id="export-excel">
								<table class="table table-bordered table-condensed" id="importexcel">
									<div id="tlExcel" style="display: none">
										<spring:message code="accounts.registerof.advances.header"
											text="Register of Advances" />
									</div>
									<thead>
										<tr>
										<th data-sorter="false"><spring:message
													code="accounts.registerof.advances.sr.no" text="Sr.No." /></th>
											<th><spring:message
													code="accounts.registerof.advances.sdv.no" text="Advance No" /></th>
											<th><spring:message
													code="accounts.registerof.advances.date" text="Date" /></th>
											<th><spring:message
													code="accounts.registerof.advances.nameof.person"
													text="Name of the Person to whom the advance is paid" /></th>
											<th><spring:message
													code="accounts.registerof.advances.particulars"
													text="Particulars of the Advance" /></th>
											<th><spring:message
													code="accounts.registerof.advances.bank.payment"
													text="Bank Payment Voucher No. & Date" /></th>
											<th><spring:message
													code="accounts.registerof.advances.payment.order"
													text="Payment Order No. & Date" /></th>
											<th><spring:message
													code="accounts.registerof.advances.amount"
													text="Amount(Rs.)" /></th>
											<th  width="200"><spring:message
													code="accounts.registerof.advances.date.ofpayment"
													text="Repayment No. and Date" /></th>
											<th  width="200"><spring:message
													code="accounts.registerof.advances.voucher.number"
													text="Adjustment No. and Date" /></th>
											<th><spring:message
													code="accounts.registerof.advances.balanace.remaining"
													text="Balance Remaining unadjusted at the end of year" /></th>
											<th><spring:message
													code="accounts.registerof.advances.remarks" text="Remarks" /></th>
										</tr>
									</thead>
									<tfoot class="tfoot">
										<tr>
											<th colspan="11" class="ts-pager form-horizontal">
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
										<c:forEach
											items="${command.advanceEntryDTO.advanceLedgerList}"
											var="advanceLedgerList" varStatus="count">
											<tr>
											    <td align="center">${count.index+1}</td>
												<td align="center">${advanceLedgerList.prAdvEntryNo}</td>
												<td>${advanceLedgerList.advanceDate}</td>
												<td>${advanceLedgerList.vendorName}</td>
												<td align="right">${advanceLedgerList.payAdvParticulars}</td>
												<td align="right">${advanceLedgerList.paymentNumber}<br>${advanceLedgerList.advPaymentDate}</td>
												<td align="right">${advanceLedgerList.payAdvSettlementNumber}<br>${advanceLedgerList.advPaymentOrderDate}</td>
												<td align="right">${advanceLedgerList.advanceAmount}</td>
												<td align="right">
												<c:forEach items="${advanceLedgerList.receiptList}" var="receipt"
														varStatus="loop">
														${receipt.rmRcptno} - ${receipt.transactionDate} - ${receipt.rmAmount}<br>
														</c:forEach>
														</td>
														<td align="right">
												<c:forEach items="${advanceLedgerList.receiptList}" var="nd"
														varStatus="loop">
														<br>
													</c:forEach>
													<c:forEach items="${advanceLedgerList.billList}" var="bill"
														varStatus="loop">
														${bill.billNo} - ${bill.billDate} - ${bill.invoiceValue}<br>
													</c:forEach>
												</td>
												<td align="right">
												<c:forEach items="${advanceLedgerList.receiptList}" var="nd"
														varStatus="loop">
														${nd.balanceAmount}<br>
														</c:forEach>
														<c:forEach items="${advanceLedgerList.billList}" var="bill"
														varStatus="loop">
														${bill.balanceAmount}<br>
													</c:forEach>
														</td>
													<td></td>	
												</tr>
										</c:forEach>
									</tbody>
									<tfoot class="avoid-sort">
										<tr>
											<th><b><spring:message code="account.voucher.total"
														text="Total" /></b></th>
											<%-- <td>${trailBalance.particular}</td> --%>
											<th style="text-align: right;"></th>
											<th style="text-align: right;"></th>
											<th style="text-align: right;"></th>
											<th style="text-align: right;"></th>
											<th style="text-align: right;"></th>
											<th style="text-align: right;"></th>
											<th style="text-align: right;">${command.advanceEntryDTO.sumAdvAmt}</th>
											<th style="text-align: right;"></th>
											<th style="text-align: right;"></th>
											<th style="text-align: right;">${command.advanceEntryDTO.sumblncAmt}</th>
											<th style="text-align: right;"></th>
										</tr>
									</tfoot>

								</table>
							</div>
				</form>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${cheque.dishonor.register}');"
						class="btn btn-primary hidden-print" type="button">
						<i class="fa fa-print"></i>  <spring:message code="account.budget.code.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="backAbstractForm();">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
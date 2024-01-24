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
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="receipt.register.payment"
					text="Payment Register" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div id="receipt">
			   <h2 class="excel-title" style="display:none">
				   <spring:message code="receipt.register.payment"
					text="Payment Register" />
			   </h2>
					<div
						class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">
							<p>
							<strong><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if> <c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if> </strong><br>
							<spring:message code="receipt.register.payment"
									text="Payment Register" />
								<br>
								<strong><spring:message code="cheque.payment.register.fromdate"
									text="From Date:" /></strong>
								${reportData.fromDate}
								<strong><spring:message code="cheque.payment.register.todate"
									text="To Date:" /></strong>
								${reportData.toDate}
							</p>
						</h3>
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
				<div class="clear"></div>
				<div class="margin-top-10">
					<div class="margin-top-10 col-sm-8 col-xs-8 text-left">
						<p>
							<strong><spring:message
									code="accounts.stop.payment.bank.account" text="Bank Account :" /></strong>
									<c:if test="${not empty bankAccountHead }">: ${bankAccountHead}</c:if>
									<c:if test="${ empty  bankAccountHead }">: <spring:message code="account.common.all" text="ALL"></spring:message></c:if>
						</p>
					</div>

					<div class="margin-top-10 col-sm-8 col-xs-8 text-left">
						<p>
							<strong><spring:message code="acc.master.fieldCode"
									text="Field Code :" /></strong> 
									<c:if test="${not empty  fieldCode }">: ${fieldCode}</c:if>
									<c:if test="${ empty  fieldCode }">: <spring:message code="account.common.all" text="ALL"></spring:message></c:if>
						</p>
					</div><br>
                       
					<div id="export-excel">
						<table class="table table-bordered table-condensed" id="importexcel">
							<div class="excel-title" id="tlExcel" style="display: none">
								<spring:message code="cheque.payment.register"
									text="CHEQUE REGISTER" />
							</div>
							<thead>
								<tr>
									<th data-sorter="false"><spring:message code="cheque.payment.srno"
											text="Sr. No." /></th>
									<th><spring:message
											code="cheque.payment.bankpayment.voucherno.date"
											text="Bank Payment Voucher No. Date" /></th>
									<th><spring:message code="cheque.payment.orderno.date"
											text="Payment Order No. & Date" /></th>
									<th width="10%"><spring:message
											code="cheque.payment.register.namepayee.naturepayment"
											text="Name of the  Payee & Nature of the Payment" /></th>
									<th><spring:message
											code="cheque.payment.register.cheque.draft.date"
											text="Cheque/Draft No" /></th>
									<th><spring:message code="cheque.payment.register.payment"
											text="Payment Amount(Rs.)" /></th>
									<th width="10%"><spring:message
											code="cheque.payment.register.enterby" text="Entered By" /></th>
									<th><spring:message
											code="cheque.payment.register.dateofissue.cheque.draft"
											text="Date of Issue of Cheque/Draft" /></th>
									<th><spring:message
											code="cheque.payment.register.signature"
											text="Signature of the Receipent of Cheque/Draft" /></th>
									<th><spring:message
											code="cheque.payment.register.dateof.clearence"
											text="Date of Clearence" /></th>
									<th><spring:message code="cheque.payment.register.remarks"
											text="Remarks" /></th>
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
								<c:forEach items="${reportData.listofchequepayment}"
									var="chequepayment">
									<tr class="text-center">
										<td>${chequepayment.index}</td>
										<td>${chequepayment.paymentNo}<br>${chequepayment.paymentDates}</td>
										<td>${chequepayment.billNo}<br>${chequepayment.billEntryDate}</td>
										<td>${chequepayment.vendorName}&
											${chequepayment.particular}</td>
										<td>${chequepayment.chequeNo}<br>${chequepayment.instrumentDate}</td>
										<td style="text-align: right">${chequepayment.paymentAmnt}</td>
										<td>${chequepayment.approvedBy}</td>
										<td>${chequepayment.issuanceDate}</td>
										<td></td>
										<td>${chequepayment.chequeIssueDate}</td>
										<td></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
							<tr>
								<th colspan="5" style="text-align: right"><strong><spring:message
											code="cheque.payment.register.total" text="TOTAL" /></strong></th>
								<td style="text-align: right"><strong>${reportData.totalPaymentAmnt}</strong></td>
								<th colspan="6">&nbsp;</th>
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
					onclick="window.location.href='AccountExpensesReports.html'" title=Back>
					<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
					<spring:message code="account.bankmaster.back" text="Back" />
				</button>
			</div>

		</div>
	</div>
</div>
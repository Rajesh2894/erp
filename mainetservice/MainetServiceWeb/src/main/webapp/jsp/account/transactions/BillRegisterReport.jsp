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
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
<!-- <script
	src="assets/libs/excel-export/excel-export.js"></script>	 -->
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
@media print {
	.table-responsive {
		overflow: visible;
		padding: 0;
	}
	.table-responsive>.table>thead>tr>th, .table-responsive>.table>tbody>tr>th,
		.table-responsive>.table>tfoot>tr>th, .table-responsive>.table>thead>tr>td,
		.table-responsive>.table>tbody>tr>td, .table-responsive>.table>tfoot>tr>td
		{
		white-space: normal;
	}
}
	
#receipt .table tr th {
	padding:4px 10px 4px 20px !important;
}
.billRegisterDiv .padding-left20{
	padding-left: 13rem !important;
}
.billRegisterDiv .padding-left10{
	padding-left: 4rem !important;
}
</style>
<!-- Start Content here -->
<div class="widget">
	<div class="widget-header">
		<h2 class="excel-title"><spring:message code="accounts.register.of.Bills" text="Register of Bills" /></h2>
	</div>

	<div class="widget-content padding">
		<div id="receipt">
			<div class="form-group margin-bottom-0 billRegisterDiv">
				<div class="col-xs-10 text-center padding-left20">
					<h3 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if> </h3>
					<div>
					 <p><spring:message code="register.of.Bills.for.Payment.Period" text="Register of Bills for Payment for the Period" /></p>
					 <spring:message code="from.date.label" text="From Date" />:<strong> ${billReportdata.fromDate}</strong>&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="to.date.label" text="To Date" /> : <strong>${billReportdata.toDate}</strong>
					</div>
				</div>
				<div class="col-sm-2 padding-left10">
					<p>
						<strong><spring:message code="account.date" text="Date" /></strong> :
						<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
					</p>
					<p>
						<strong><spring:message
								code="accounts.financial.audit.trail.time" text="Time" /></strong> :
						<fmt:formatDate value="${date}" pattern="hh:mm a" />
						 <br><br>
						<spring:message code="accounts.register.from.gen" text="Form GEN-13 " />
					</p>
				</div>
			</div>
			<div class="clearfix padding-10"></div>
			<div class="table-responsive">
				<div id="export-excel">
					<table class="table table-bordered table-condensed" id="importexcel">
						<div id="tlExcel" style="display:none;">Register of Bills</div>
						<thead>
							<tr>
								<th width="54" data-sorter="false"><spring:message code="account.tenderentrydetails.srno" text="Sr.No." /></th>
								<th width="90"><spring:message code="accounts.bill.no" text="Bill No. & " /><br> <spring:message code="bill.date" text="Bill Date " />
								</th>
								<th width="150"><spring:message code="account.deposit.nameofParty" text="Name of Party" /></th>
								<th width="65"><spring:message code="accounts.registerof.amount.bill" text="Amount of Bill (Rs.)" />  </th> 
								<th width="150" style="align:left"><spring:message code="accounts.registerof.particulars" text="Particulars" /> </th>
								<th style="width: 60"><spring:message code="accounts.register.amount.sanctioned" text="Amount Sanctioned (Rs.) &" /> <br> <spring:message code="accounts.register.date.sanction" text="Date of Sanction" /> </th>
								<th><spring:message code="accounts.registerof.amount.disallowed" text="Amount Disallowed (Rs.)" /></th>
								<th><spring:message code="accounts.registerof.amount.deducted" text="Amount Deducted (Rs.)" /></th>
								<th width="57"><spring:message code="account.voucher.number" text="Voucher No." /></th>
								<th><spring:message code="accounts.registerof.payment.amount" text="Payment Amount (Rs.)" /> </th>
								<th><spring:message code="accounts.registerof.balance.amount" text="Balance Amount (Rs.)" /></th>
								<th><spring:message code="accounts.register.authorized" text="Initials of Authorized Officer &" /> <br><spring:message code="accounts.register.delay.payment" text="Reason for delay in payment & " /> <br><spring:message code="accounts.registerof.advances.remarks" text="Remarks" /></th>
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
							<c:forEach items="${billReportdata.listOfBillRegister}"
								var="billRegister">
								<tr class="text-center">
									<td>${billRegister.count}</td>
									<td>${billRegister.billNo}<br>${billRegister.billEntryDate}</td>
									<td>${billRegister.vendorName}</td>
									<td class="text-right">${billRegister.invoiceValueStr}</td>
									<td class="text-left">${billRegister.narration}</td>
									<td class="text-right">${billRegister.billtoatalAmt}<br>${billRegister.checkerDate}
									</td>
									<td class="text-right">${billRegister.totalDisallowedAmountStr}</td>
									<td class="text-right">${billRegister.dedcutionAmountStr}</td>
									<td>${billRegister.invoiceNumber}</td>
									<td class="text-right">${billRegister.payee}</td>
									<td class="text-right">${billRegister.totalBillAmountStr}</td>
									<td class="text-left">${billRegister.empName}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot class="avoid-sort">
						<tr>
							<th colspan="3" class="text-right"><spring:message code="accounts.deduction.register.tds.total" text="Total" /></th>
							<th class="text-right"><Strong>${billReportdata.billtoatalAmt}</Strong></th>
							<th>&nbsp;</th>
							<th class="text-right"><Strong>${billReportdata.totalSactAmt}</Strong></th>
							<th class="text-right"><Strong>${billReportdata.totalDedcutionsAmountStr}</Strong></th>
							<th class="text-right"><Strong>${billReportdata.sumDeductionAmt}</Strong></th>
							<th>&nbsp;</th>
							<th class="text-right"><Strong>${billReportdata.totalPayments}</Strong></th>
							<th class="text-right">${billReportdata.totalBalance}</th>
							<th>&nbsp;</th>
						</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<style>
				@media print {
					@page {
						size: landscape;
						margin: 5px;
					}
				}
			</style>
		</div>
		<div class="text-center hidden-print padding-10">
			<button onclick="PrintDiv('${accounts.receipt.challan}');"
				class="btn btn-primary hidden-print" title="Print">
				<i class="fa fa-print"></i>
				<spring:message code="account.budgetestimationpreparation.print"
					text="Print" />
			</button>
			<button type="button" class="btn btn-danger"
				onclick="window.location.href='BillRegister.html'" title="Back">
				<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
				<spring:message code="account.bankmaster.back" text="Back" />
			</button>
		</div>

	</div>
</div>


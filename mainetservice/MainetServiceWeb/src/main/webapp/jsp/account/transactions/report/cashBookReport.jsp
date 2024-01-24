<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script
	src="assets/libs/excel-export/excel-export.js"></script>
<script src="js/account/cashBookReport.js"></script>
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
	function printdiv(printpage) {
		debugger;
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>
<style>
	
#receipt .table tr th {
	padding:4px 15px !important;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message code="cash.Book.Report" text="Cash Book Report" /></h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="text-center col-sm-offset-2 col-sm-8 margin-bottom-10">
						<h3 class="margin-top-0 margin-bottom-0">
							<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
							</c:if>
						</h3>
						<div class="text-bold"><spring:message code="cash.Book.Report" text="Cash Book Report" /></div>
						<div>
							<b><spring:message code="account.fromDate" text="From Date" />:</b>  <span class="text-bold">${command.accountFinancialReportDTO.fromDate}</span>
							 <b><spring:message code="account.todate" text="TO Date" />: </b> <span class="text-bold">${command.accountFinancialReportDTO.toDate}</span>
						</div>
					</div>
					<div class="col-sm-2 margin-bottom-10">
						<p>
							<spring:message code="swm.day.wise.month.report.date" text="Date" />
							<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							<br>
							<spring:message code="swm.day.wise.month.report.time" text="Time" />
							<fmt:formatDate value="${date}" pattern="hh:mm a" />
						</p>
						<span class="text-bold">(Form GEN-1)</span>
					</div>
					<div class="col-sm-8 text-left text-bold">
						<p><spring:message code="accounts.receipt" text="Receipt" /></p>
					</div>
					<div class="col-sm-4  text-right text-bold">
						<p>
							<spring:message code="advance.management.master.payments" text="Payment" /><br>
						</p>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="container">
						<div id="export-excel">
							<div class="table-responsive">
								<table class="table table-bordered  table-fixed" id="importexcel">
								<div class="excel-title" id="tlExcel" style="display: none">Cash Book Report</div>
									<thead>
										<tr>
											<th data-sorter="false">Sr. No.</th>
											<th>Date</th>
											<th>Rcpt. Vchr. No.</th>
											<th>Code of Account</th>
											<th>Particulars of Receipt</th>
											<th>L/F</th>
											<th>Cash Amount(Rs.)</th>
											<th>Bank Account Amount(Rs.)</th>
											<th>Sr. No.</th>
											<th>Date</th>
											<th>Pymt. Vchr. No.</th>
											<th>Code of Account</th>
											<th>Particulars of Payment</th>
											<th>L/F</th>
											<th>Cash Amount(Rs.)</th>
											<th>Bank Account Amount(Rs.)</th>
										</tr>
									</thead>
									<tfoot class="tfoot">
										<tr>
											<th colspan="16" class="ts-pager form-horizontal" align="center">
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
									<c:set var="totalDrReceipt" value="${0}" />
									<c:set var="totalCrReceipt" value="${0}" />
									<c:set var="totalDrpayment" value="${0}" />
									<c:set var="totalCrpayment" value="${0}" />
									<tbody>
										<c:forEach
											items="${command.accountFinancialReportDTO.listofreceiptinc}"
											var="data" varStatus="index">
											<tr>
												<c:choose>
													<c:when test="${data.receiptvouId eq null}">
														<td></td>
													</c:when>
													<c:otherwise>
														<td>${index.count}</td>
													</c:otherwise>
												</c:choose>
												<td>${data.voucherDate}</td>
												<td>${data.voucherNo}</td>
												<td>${data.accountCode}</td>
												<td>${data.narration}</td>
												<td></td>
												<td class="text-right">${data.drAmount}</td>
												<c:set var="totalDrReceipt"
													value="${totalDrReceipt + data.drAmount}" />
												<td class="text-right">${data.crAmount}</td>
												<c:set var="totalCrReceipt"
													value="${totalCrReceipt + data.crAmount}" />
												<c:choose>
													<c:when test="${data.paymentvouId eq null}">
														<td></td>
													</c:when>
													<c:otherwise>
														<td>${index.count}</td>
													</c:otherwise>
												</c:choose>
												<td>${data.paymentDates}</td>
												<td>${data.paymentNo}</td>
												<td>${data.paymentHeadcode}</td>
												<td>${data.paymentNarration}</td>
												<td></td>
												<td class="text-right">${data.paymentDrAmount}</td>
												<c:set var="totalDrpayment"
													value="${totalDrpayment + data.paymentDrAmount}" />
												<td class="text-right">${data.paymentCrAmount}</td>
												<c:set var="totalCrpayment"
													value="${totalCrpayment + data.paymentCrAmount}" />
											</tr>
										</c:forEach>
										</tbody>
									<tfoot>
										<tr>
											<th colspan="6" class="text-right">Total</th>
											<th class="text-right text-bold"><fmt:formatNumber
													type="number" value="${totalDrReceipt}"
													minFractionDigits="2" /></th>
											<th class="text-right text-bold"><fmt:formatNumber
													type="number" value="${totalCrReceipt}"
													minFractionDigits="2" /></th>
											<th colspan="6" class="text-right">Total</th>
											<th class="text-right text-bold"><fmt:formatNumber
													type="number" value="${totalDrpayment}"
													minFractionDigits="2" /></th>
											<th class="text-right text-bold"><fmt:formatNumber
													type="number" value="${totalCrpayment}"
													minFractionDigits="2" /></th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
					<style>
						@media print{
							.tfoot{
								visibility: hidden;
							}
						}
					  </style>
				</div>
			</form>
			<div class="text-center hidden-print padding-10">
				<button onClick="printdiv('receipt');"
					class="btn btn-primary hidden-print" title='<spring:message code="account.budget.code.print" text="Print" />'>
					<i class="fa fa-print"></i><spring:message code="account.budget.code.print" text="Print" />
				</button>
				<button id="btnExport1" type="button"
					class="btn btn-blue-2 hidden-print" title='<spring:message code="account.common.account.downlaod" text="Download" />'>
					<i class="fa fa-file-excel-o"></i><spring:message code="account.common.account.downlaod" text="Download" /> 
				</button>
				<button type="button" class="btn btn-danger" onClick="back()" title='<spring:message code="solid.waste.back" text="Back" />'>
					<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
					<spring:message code="solid.waste.back" text="Back" />
				</button>
			</div>
		</div>
	</div>
</div>
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
		});
	});
	$(function() {

		$(".table").tablesorter({

			cssInfoBlock : "avoid-sort",

		});

	});
</script>


<ol class="breadcrumb">
	<li><a href="#"><i class="fa fa-home"></i></a></li>
	<li><spring:message code="day.book.report" text="DAY BOOK" /></li>
</ol>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="day.book.report" text="DAY BOOK" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="form-group">
						<div
							class="col-sm-8 col-xs-8 col-xs-offset-1 col-sm-offset-2 text-center">
							<h3 class="text-extra-large  margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}</h3>
							<p>
								<strong class="excel-title"><spring:message code="day.book.report" text="DAY BOOK" /></strong>
							</p>
							<p>
								<spring:message code="day.book.report.fromdate" text="FromDate" />
								: ${reportData.fromDate}
								<spring:message code="day.book.report.todate" text="To Date" />
								: ${reportData.toDate}
							</p>
						</div>

						<div class="col-sm-2 col-xs-3">
							<p>
								Date:
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>Time:
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>

					</div>
					<div class="padding-5 clear">&nbsp;</div>
					<div class="overflow-visible">
						<div id="export-excel">
							<table class="table table-bordered table-fixed clear">
								<thead>
									<tr>
										<th style="width: 12%;"><spring:message code=""
												text="Voucher Date  Voucher No." /></th>
										<th style="width: 14%;"><spring:message code=""
												text="Voucher Type Voucher Sub Type" /></th>
										<th style="width: 9%;"><spring:message code=""
												text="Ref. No.  Ref. Date" /></th>
										<th style="width: 30%; text-align: center;"><spring:message
												code="" text="Account Head " /></th>
										<th style="text-align: center;"><spring:message code=""
												text="Particulars" /></th>
										<th style="width: 11%; text-align: center;"><spring:message
												code="" text="Debit Amount (Rs.)" /></th>
										<th style="width: 11%; text-align: center;"><spring:message
												code="" text="Credit Amount (Rs.)" /></th>


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
												<option selected="selected" value="10" class="form-control">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="all">All Records</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach items="${reportData.listOfDayBook}" var="dayBook">
										<tr>
											<td align="center">${dayBook.voucherDate}<br>
												${dayBook.voucherNo}
											</td>
											<td align="left">${dayBook.voucherType}<br>
												${dayBook.voucherSubType}
											</td>
											<td align="center">${dayBook.receiptNumber}<br>
												${dayBook.receiptDate}
											</td>
											<td align="left">${dayBook.accountHead}</td>
											<td align="left">${dayBook.particular}</td>
											<td align="right">${dayBook.drAmountIndianCurrency}</td>
											<td align="right">${dayBook.crAmountIndianCurrency}</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot class="avoid-sort">
									<tr>
										<th colspan="5" class="text-right">Total</th>
										<th class="text-right">${reportData.drAmountIndianCurrency}</th>
										<th class="text-right">${reportData.crAmountIndianCurrency}</th>

									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${account.deposit.formnumber}');"
						class="btn btn-success hidden-print" type="button" title="Print">
						<i class="fa fa-print"></i> Print
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountDailyReports.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
			</form>
		</div>

	</div>

</div>

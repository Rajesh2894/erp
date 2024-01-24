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
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="summary.daily.collection"
					text="Summary Of Daily Collection" />
			</h2>
		</div>
		<div class="widget-content padding">
		<div id="receipt">
			<form action="" method="get" class="form-horizontal">
					<div class="form-group">
						<div
							class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
								<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if>
							</h3>
							<h3 class="text-extra-large margin-bottom-0 margin-top-0   excel-title"><spring:message code="summary.daily.collection"
					text="Summary Of Daily Collection" /></h3>
							<p>
								<strong><spring:message
                            code="from.date.label" text="From Date" />:</strong> ${reportData.fromDate} &nbsp; <strong>
									<spring:message
                            code="day.book.report.todate" text="To Date" />:</strong> ${reportData.toDate}
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
					<div class="clearfix"></div>
					<!-- <div class="padding-5 clear">&nbsp;</div> -->
						<div id="export-excel">
							<table class="table table-bordered table-condensed" id="importexcel">
								<div class="excel-title" id="tlExcel" style="display:none;"><spring:message code="summary.daily.collection" text="Summary Of Daily Collection" /></div>
								<thead>
									<tr>
										<th data-sorter="false"><spring:message code="summary.daily.collection.srno"
												text="Sr. No." /></th>
										<th><spring:message
												code="summary.daily.collection.nameofdep"
												text="Name Of the Department" /></th>
										<th><spring:message
												code="summary.daily.collection.revenuehead"
												text="Name of the Revenue Head" /></th>
										<th><spring:message
												code="summary.daily.collection.cashamount"
												text="Cash Amount (Rs.)" /></th>
										<th><spring:message
												code="summary.daily.collection.chequeamount"
												text="Chaque/DD Amount (Rs.)" /></th>
										<th><spring:message
												code="summary.daily.collection.depositedwith"
												text="Bank Amount (Rs.)" /></th>
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
												<option value="all"> <spring:message text="All Records" code="account.all.records"></spring:message></option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach items="${reportData.collectionSummaryRecordList}"
										var="collectionReport" varStatus="status">

										<tr>
											<td>${status.count}</td>
											<td>${collectionReport.nameOfDepartment}</td>
											<td>${collectionReport.nameOfTheRevenueHead}</td>
											<td align="right">${collectionReport.cashAmountIndianCurrency}</td>
											<td align="right">${collectionReport.chequeDDAmountIndianCurrency}</td>
											<td align="right">${collectionReport.bankAmountIndianCurrency}</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<th colspan="3" style="text-align: left;"><spring:message
												code="account.Mode.Wise.Total" text="Mode Wise Total" /></th>
										<th class="text-right">${reportData.cashAmountTotalIndianCurrency}</th>
										<th class="text-right">${reportData.chequeDDTotalIndianCurrency}</th>
										<th class="text-right">${reportData.bankTotalIndianCurrency}</th>
									</tr>
									<tr>
										<th style="text-align: left;" colspan="2"><spring:message
												code="summary.daily.collection.grandtotal"
												text="Grand Total" /></th>
										<th style="text-align: left" colspan="5">${reportData.grandTotal}</th>
									</tr>
									<tr>
										<th colspan="2" style="text-align: left;"><spring:message
												code="summary.daily.collection.amountinwords"
												text="Amount in words:Rupees" /></th>
										<th colspan="4" style="text-align: left;">${reportData.totalAmountInWords}</th>

									</tr>
								</tfoot>

							</table>
						</div>
					<div class="row">
						<div class="col-sm-8">
							<p class="margin-top-10">
								<spring:message code="summary.daily.collection.preparedby"
									text="Prepared By**:____________________________" />
							</p>
							<p class="margin-top-15">
								<spring:message code="summary.daily.collection.checkedby"
									text="Checked By**:____________________________" />
							</p>

							<p class="margin-top-10"><spring:message
                            code="account.date" text="Date" />:</p>
							<p></p>
						</div>
						<div class="col-sm-4">
							<p class="margin-top-10">
								<spring:message code="summary.daily.collection.examined"
									text="Examined and Entered" />
							</p>
							<p class="margin-top-15">
								<spring:message code="summary.daily.collection.accountant"
									text="Accountant/Authroized Officer" />
							</p>

						</div>
					</div>
			</form>
			</div>
			<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('receipt');"
						class="btn btn-primary hidden-print" title="Print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
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

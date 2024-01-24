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
<script type="text/javascript" src="js/rnl/report/report.js"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
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

		$(function() {

			$(".table").tablesorter({

				cssInfoBlock : "avoid-sort",

			});

		});
	});
</script>

<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<!-- Start Content here -->
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">
					<spring:message code="rnl.revenue.report" text="Revenue Report" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="form-group">
							<div
								class="col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-1 text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if> </h3>
								<h3
									class="text-extra-large margin-bottom-0 margin-top-0 excel-title">
									<spring:message code="rnl.revenue.register"
										text="REVENUE REGISTER" />
								</h3>
								<p>
									<strong><spring:message code='account.fromDate' text="From Date" /> : </strong> ${command.reportDTO.fromDateString} 
									<strong><spring:message code='account.todate' text="To Date"/> : </strong> ${command.reportDTO.toDateString}
								</p>
							</div>
							<div class="col-sm-2 col-xs-3">
								<p>
									<spring:message code='account.date' text="Date" />:
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									<br><spring:message code='account.time'  text="Time"/>:
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
								</p>
								
								
							</div>
						</div>

						<input type="hidden" value="${validationError}" id="errorId">
						<div class="margin-top-10">
							<div id="export-excel" class="table-responsive">
								<table class="table table-bordered table-condensed">
									<thead>
										<tr>
											<th><spring:message code="receipt.register.srno"
													text="Sr. No." /></th>
											<th><spring:message
													code="receipt.register.receiptnumber" text="Receipt Number" /></th>
											<th><spring:message code="receipt.register.receiptdate"
													text="Receipt Date" /></th>
											<th><spring:message code="receipt.register.mode"
													text="Mode of receipt" /></th>
											<th><spring:message
													code="receipt.register.namedepositor"
													text="Name of  the Depositor" /></th>
											<th><spring:message code="receipt.register.cheque"
													text="Cheque/Draft No." /></th>
											<th><spring:message code="receipt.register.bookfee"
													text="Booking Fee" /></th>
											<th><spring:message code="receipt.register.sdeposit"
													text="Security Deposit" /></th>
											<th><spring:message
													code="receipt.register.receiptamount" text="Receipt Amount" /></th>
											
											<th><spring:message code="accounts.receipt.narration"
													text="Narration*" /></th>
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
										<c:forEach items="${command.summaryReportDetails}"
											var="collectionDetail" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td align="right">${collectionDetail.receiptNumber}</td>
												<td>${collectionDetail.receiptDate}</td>
												<td>${collectionDetail.receiptMode}</td>
												<td width="15%">${collectionDetail.nameOfDepositer}</td>
												<td align="right">${collectionDetail.chequeNumber}</td>
												<td align="right">${collectionDetail.bookinFee}</td>
												<td align="right">${collectionDetail.securityDeposit}</td>
												<td align="right">${collectionDetail.cashAmountIndianCurrency}</td>
												
												<td>${collectionDetail.remarks}</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<th colspan="6"><spring:message
													code="receipt.register.total" text="Total Receipt Amount" /></th>
											<th class="text-right">${command.reportDTO.cashAmountTotalIndianCurrency}</th>
											<th class="text-right"></th>
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
							onclick="window.location.href='EstateRevenueReport.html'"
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
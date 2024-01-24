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
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="opening.balance.reports"
					text="Opening Balance Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">

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
						<h3 class="text-extra-large margin-bottom-0 margin-top-0 excel-title ">
							<spring:message code="opening.balance.reports"
								text="Opening Balance Report" />
						</h3>
						<p class="text-large margin-bottom-0 margin-top-0">
							<spring:message
                            code="account.budgetopenmaster.financialyear" text="Financial Year" />: ${reportData.financialYear}</p>
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
					<div class="clearfix padding-10"></div>
					<div class="margin-top-10">
						<div id="export-excel">
							<table class="table table-bordered table-condensed" id="importexcel">
								<div class="excel-title" id="tlExcel" style="display:none;"><spring:message code="opening.balance.reports" text="Opening Balance Report" /></div>
								<thead>
									<tr>
										<th width="5%" data-sorter="false"><spring:message
												code="opening.balance.report.srno" text="Sr. No." /></th>
										<th><spring:message
												code="opening.balance.report.accouthead"
												text="Account Head Code - Description" /></th>
										<th><spring:message
												code="opening.balance.report.opeingbalance"
												text="Opening Balance" /></th>
										<th><spring:message
												code="opening.balance.report.balancetype"
												text="Balance Type" /></th>
										<th><spring:message
												code="opening.balance.report.balancefinalized"
												text="Balance Finalized" /></th>
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
									<c:forEach items="${reportData.listOfOpeningBalance}"
										var="OpeningBalanceReport" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${OpeningBalanceReport.accountCode}</td>
											<td align="right">${OpeningBalanceReport.openingBalanceIndianCurrency}</td>
											<td class="text-center">${OpeningBalanceReport.balanceType}</td>
											<td class="text-center">${OpeningBalanceReport.finalizedFlag}</td>
										</tr>
									</c:forEach>
								</tbody>
								<tr>
									<th colspan="2"><spring:message
											code="opening.balance.report.totalcredit"
											text="Total Opening Credit" /></th>
									<th class="text-right">${reportData.totalCrAmountIndianCurrency}</th>
									<th colspan="2"></th>

								</tr>

								<tr>
									<th colspan="2"><spring:message
											code="opening.balance.report.totaldebit"
											text="Total Opening Debit" /></th>
									<th class="text-right">${reportData.totalDrAmountIndianCurrency}</th>
									<th colspan="2"></th>

								</tr>
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
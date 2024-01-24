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
</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message code="cash.Cash.Equivalents" text="Cash & Cash Equivalents" /></h2>
		</div>
		<div class="widget-content padding">
		<div id="receipt">
			<form action="" method="get" class="form-horizontal">
				        <h2 class="excel-title" style="display:none"><spring:message code="cash.Cash.Equivalents" text="Cash & Cash Equivalents" /></h2>
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
							<span class="text-bold"> <spring:message code="statement.of.Cash.and.Cash.Equivalents.as.on.Date" text="statement of Cash and Cash Equivalents as on Date" />: ${reportData.transactionDate}</span>
						</div>
						<div class="col-sm-2 col-xs-3">
							<p>
								<spring:message code="account.date" text="Date" />
								:<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="account.time"
									text="Time" />:

								
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
						</div>
					</div>
					<div class="margin-top-10">
						<div id="export-excel">
							<table class="table table-bordered table-condensed clear"
								id="importexcel">
								<h2 class="excel-title" id="tlExcel" style="display: none">
								<spring:message text="Cash & Cash Equivalents" code="cash.Cash.Equivalents"></spring:message>
							 		</h2>
								<thead>
									<tr>
										<th data-sorter="false"> <spring:message code="opening.balance.report.srno" text="SR.No."></spring:message> </th>
										<th> <spring:message code="accounts.registerof.particulars" text="Particulars"></spring:message> </th>
										<th> <spring:message code="contingent.claim.bill.amount" text="Amount (Rs.)"></spring:message></th>
										<th><spring:message code="contingent.claim.bill.amount" text="Amount (Rs.)"></spring:message> </th>
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
												<option selected="selected" value="12" class="form-control">12</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="all">All Records</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>

								<tr>
									<td></td>
									<td><strong> <spring:message code="account.Cash.and.Bank.Balances" text="Cash and Bank Balances"></spring:message></strong></td>
									<td></td>
									<td>&nbsp;</td>
								</tr>

								<tbody>
									<tr>
										<td>1.1</td>
										<td>${reportData.accountHead}</td>
										<td style="text-align: right">${reportData.closingBalance}</td>
										<td>&nbsp;</td>
									</tr>
								</tbody>
								<c:forEach items="${reportData.listCashEquvalent}"
									var="cashequivalentlist" varStatus="count">
									<tr>
										<td>2.${count.count}</td>
										<td>${cashequivalentlist.accountCode}</td>
										<td style="text-align: right">${cashequivalentlist.closingBalance}</td>
										<td style="text-align: right"></td>
									</tr>
								</c:forEach>

								<tfoot>
									<tr>
										<th colspan="2" class="text-right"> <spring:message code="account.total" text="Total"></spring:message></th>
										<th>&nbsp;</th>
										<th style="text-align: right">${reportData.totalbankAmts}</th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</form>
			</div>
			
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${cheque.dishonor.register}');"
						class="btn btn-primary hidden-print" type="button" title="Print">
						<i class="fa fa-print"></i> <spring:message code="account.budget.code.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountFinancialReport.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
		</div>
	</div>
</div>

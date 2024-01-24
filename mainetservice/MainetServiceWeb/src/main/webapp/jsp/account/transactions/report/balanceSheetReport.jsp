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
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">
					<spring:message code="balancesheet" text="Balance Sheet" />
				</h2>
			</div>
			<div class="widget-content padding">
			<div id="receipt">
				<form action="" method="get" class="form-horizontal">
					 <h2 class="excel-title" style="display:none">
					   <spring:message code="balancesheet" text="Balance Sheet" />
				     </h2>
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
								<strong><spring:message code="balancesheetason"
										text="Balance Sheet as on" />:- ${reportList.fromDate}</strong>
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
						</div>

						<div id="export-excel">
							<table class="table table-bordered table-condensed clear" id="importexcel">
								<div class="excel-title" style="display: none" id="tlExcel">
									<spring:message code="balancesheet" text="Balance Sheet" />
								</div>
								<thead>
								<tr>
									<th><spring:message code="balancesheet.account.code"
											text="Code No." /></th>
									<th><spring:message code="balancesheet.account.desc"
											text="Description of items" /></th>
									<th><spring:message code="balancesheet.schedule"
											text="Schedule No." /></th>
									<th><spring:message code="balancesheet.currentyear"
											text="Current Year Amount (Rs.)" /></th>
									<th><spring:message code="balancesheet.previousyear"
											text="Previous Year Amount (Rs.)" /></th>
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
                              <tfoot>
								<tr>
									<th></th>
									<th class="text-center">LIABILITIES</th>
									<th></th>
									<th></th>
									<th></th>
								</tr> 	
							</tfoot>
								<tbody>
								<c:forEach items="${reportList.liabilityList}"
									var="liabilityList">
									<tr>
										<td class="text-center">${liabilityList.accountHead}</td>
										<td>${liabilityList.accountHeadDesc}</td>
										<td></td>
										<td align="right">${liabilityList.closingBalance}</td>
										<td align="right">${liabilityList.closingBalanceAsOn}</td>
									</tr>
								</c:forEach>
								<tr>
									<td></td>
									<td>${reportList.accountCode}</td>
									<td></td>
									<td style="text-align: right">${reportList.previousInTotal}</td>
									<td style="text-align: right">${reportList.previousExpTotal}</td>
								</tr>
								</tbody>
								<tfoot>
								<tr>
									<th></th>
									<th style="text-align: left"><spring:message
											code="balancesheet.account_liability_total"
											text="TOTAL LIABILITIES" /></th>
									<th></th>
									
									<th style="text-align: right">${reportList.sumClosingCR}</th>
									<th style="text-align: right">${reportList.sumClosingDR}</th>
								</tr>
								</tfoot>


							<tfoot>
								<tr>
									<th><spring:message code="balancesheet.account.code"
											text="Code No." /></th>
									<th><spring:message code="balancesheet.account.desc"
											text="Description of items" /></th>
									<th><spring:message code="balancesheet.schedule"
											text="Schedule No." /></th>
									<th><spring:message code="balancesheet.currentyear"
											text="Current Year Amount (Rs.)" /></th>
									<th><spring:message code="balancesheet.previousyear"
											text="Previous Year Amount (Rs.)" /></th>
								</tr>
							
								<tr>
									<th></th>
									<th class="text-center">ASSETS</th>
									<th></th>
									<th></th>
									<th></th>
								</tr>
								</tfoot>
								<tbody><c:forEach items="${reportList.assetList}" var="assetList">
									<tr>
										<td class="text-center">${assetList.accountHead}</td>
										<td>${assetList.accountHeadDesc}</td>
										<td></td>
										<td align="right">${assetList.closingBalance}</td>
										<td align="right">${assetList.closingBalanceAsOn}</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th></th>
									<th style="text-align: left"><spring:message
											code="balancesheet.account_assets_total" text="Total Assets" /></th>
									<th></th>
									<th style="text-align: right">${reportList.sumTransactionCR}</th>
									<th style="text-align: right">${reportList.sumTransactionDR}</th>
								</tr>
							</tfoot>
							
								
							</table>
						</div>
						&nbsp
						<div class="row">
						</div>
				</form>
				</div>
				<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('${cheque.dishonor.register}');"
							class="btn btn-primary hidden-print" title="Print">
							<i class="fa fa-print"></i> <spring:message code="account.budgetestimationpreparation.print" text="Print" />
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
</div>
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
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">
					<spring:message code="general.ledger" text="General Ledger" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
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
								<%-- <p>
									<spring:message code="general.ledger.form.number"
										text="Form No. 13S" /> --%>
									<!-- <br> --> <strong class="excel-title"><spring:message
											code="general.ledger" text="General Ledger" /></strong><%-- <br> <em><spring:message
											code="general.ledger.see.rule" text="(See Rule No.45)" /></em>
								</p> --%>
								<strong><spring:message
                            code="from.date.label" text="From Date" />: ${reportData.fromDate} <spring:message
                            code="to.date.label" text="To Date" />:
									${reportData.toDate}</strong>
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
						
						<c:forEach items="${reportData.generalLedgerList}"
							var="generalLedgerList" varStatus="status">
							<div class="col-sm-12 col-xs-12">
							
							</div>
							<input type="hidden" value="${validationError}" id="errorId">
							<!-- <div class="padding-5 clear">&nbsp;</div> -->
							<div class="">
								<div id="export-excel">
									<table class="table table-bordered table-condensed importexcel">
										<div id="tlExcel" style="display:none;"><spring:message code="general.ledger" text="General Ledger" /></div>
										<thead>
											<tr>
												<th colspan="5">
													<p>
														<strong><spring:message
																code="accounts.Secondaryhead.accountHead"
																text="Account Head : " /></strong> ${generalLedgerList.accountHead}</p>
												</th>
											</tr>
											<tr>
												<th><spring:message code="accounts.Secondaryhead.Date"
														text="Date" /></th>
												<th><spring:message code="general.cash.particulars"
														text="Particulars" /></th>
												<th><spring:message code="account.voucher.number"
														text="Voucher No." /></th>
												<th><spring:message code="general.ledger.report.dr"
														text="Debit Amount (Rs.)" /></th>
												<th><spring:message code="general.ledger.report.cr"
														text="Credit Amount (Rs.)" /></th>
											</tr>
											<tr>
											<th colspan="3" class="text-right"><spring:message
													code="bank.master.opBal" text="Opening Balance" /></th>
											<th class="text-right">${generalLedgerList.openingDrAmount}</th>
											<th class="text-right">${generalLedgerList.openingCrAmount}</th>
										</tr>
										</thead>
										<tfoot class="tfoot">
											<tr>
												<th colspan="5" class="ts-pager form-horizontal">
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
														<option selected="selected" value="12"
															class="form-control">12</option>
														<option value="20">20</option>
														<option value="30">30</option>
														<option value="all">All Records</option>
												</select> <select class="pagenum input-mini form-control"
													title="Select page number"></select>
												</th>
											</tr>
										</tfoot>
										<tbody>
											<c:forEach items="${generalLedgerList.listOfSum}"
												var="listOfSumList" varStatus="status">
												<tr>
													<td>${listOfSumList.voucherDate}</td>
													<td>${listOfSumList.particular}</td>
													<td>${listOfSumList.voucherNo}</td>
													<td align="right">${listOfSumList.drAmount}</td>
													<td align="right">${listOfSumList.crAmount}</td>
												</tr>
											</c:forEach>
										</tbody>
										<tfoot>
										<tr>
											<th><spring:message code="account.voucher.total"
													text="total" /></th>
											<td></td>
											<td></td>
											<th style="text-align: right">${generalLedgerList.sumOfDrAmount}</th>
											<th style="text-align: right">${generalLedgerList.sumOfCrAmount}</th>
										</tr>
										<tr>
											<th colspan="3" class="text-right"><spring:message
													code="bank.master.closeBal" text="Closing Balance" /></th>
											<th class="text-right">${generalLedgerList.closingDrAmount}</th>
											<th class="text-right">${generalLedgerList.closingCrAmount}</th>
										</tr>
										</tfoot>
									</table>
								</div>
							</div>
						</c:forEach>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('receipt');"
							class="btn btn-primary hidden-print" title="Print">
							<i class="fa fa-print"></i>
							<spring:message code="account.budgetestimationpreparation.print"
								text="Print" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='AccountGeneralLedgerReports.html'" title="Back">
							<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
							<spring:message code="account.bankmaster.back" text="Back" />
						</button>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>
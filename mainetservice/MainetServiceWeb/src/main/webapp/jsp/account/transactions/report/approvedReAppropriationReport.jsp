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

<script src="js/account/deductionRegister.js"></script>

<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
	
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="accounts.reappropriation.heading"
					text="Approved Re-Appropriation" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<h2 class="excel-title" style="display:none">
						<spring:message code="accounts.reappropriation.heading"
							text="Approved Re-Appropriation" />
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

							<spring:message code="accounts.reappropriation.statement"
								text="Statement of Approved Re-appropriations in the Municipal Fund Budget Estimate" />
							<%-- <p>
								<spring:message code="accounts.reappropriation.formno"
									text="Form No. 8S" />
								<br>
								<spring:message code="accounts.reappropriation.statement"
									text="Statement of Approved Re-appropriations in the Cantonment Fund Budget Estimate" />
								<br> <em><spring:message
										code="accounts.reappropriation.heading.ruleno"
										text="(See Rule No. 34)" /></em><br>

							</p> --%>
							<strong><spring:message
									code="accounts.reappropriation.heading.budgetperiod"
									text="For budgetary period" /> ${reportData.financialYear}</strong>
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
					<div class="clearfix padding-10"></div>
					
						<!-- Defect #39426 Duplicate code removed-->
						<%-- <div id="export-excel">
							<table class="table table-bordered table-condensed">
								<thead>
									<tr>
										<th><spring:message
												code="accounts.reappropriation.budget.code"
												text="Budget Code" /></th>
										<th style="width: 8%"><spring:message
												code="accounts.reappropriation.budget.head"
												text="Budget Head" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.original.budget"
												text="Amount of Original Budget (Rs.)" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.amount"
												text="Amount of any re-appropriations" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.total.sanction"
												text="Total Budget sanctioned till date" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.total.utilization.Amount"
												text="Utilisation till date" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.total.balance"
												text="Balance available" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.total.date"
												text="Date" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.approved.increase"
												text="Approved Increase" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.approved.decrease"
												text="Approved Decrease" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.balance.adjustment"
												text="Balance after the adjustment" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.approved"
												text="Approved By" /></th>
										<th><spring:message
												code="accounts.reappropriation.budget.remarks"
												text="Remarks" /></th>
									</tr>
								</thead>
								<!---------------------------------------------------------------------->

								<tfoot>
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
									<tr>
										<th colspan="13" style="text-align: left"><spring:message
												code="accounts.reappropriation.budget.decrement"
												text="Payment Decrement" /></th>
									</tr>
									<c:forEach items="${reportData.listDecReAppropriation}"
										var="DecReAppropriation">
										<tr class="text-center">
											<td style="width: 10%">${DecReAppropriation.accountCode}</td>
											<td style="text-align: left">${DecReAppropriation.accountHead}</td>
											<td style="text-align: right">${DecReAppropriation.originalEst}</td>
											<td style="text-align: right">${DecReAppropriation.transferAmt}</td>
											<td style="text-align: right">${DecReAppropriation.budgetSactionAmt}</td>
											<td style="text-align: right">
												${DecReAppropriation.totalUtilamt}</td>
											<td style="text-align: right">${DecReAppropriation.totalBalance}</td>
											<td style="text-align: right">${DecReAppropriation.lmodate}</td>
											<td style="text-align: right"></td>
											<td style="text-align: right">${DecReAppropriation.transferAmt}</td>
											<td style="text-align: right">${DecReAppropriation.budgetSactionAmt}</td>
											<td style="text-align: left">${DecReAppropriation.authorizedBy}</td>
											<td style="text-align: left; width: 10%;">${DecReAppropriation.remarks}</td>
										</tr>
									</c:forEach>
									<tr>
										<th colspan="13" style="text-align: left"><spring:message
												code="accounts.reappropriation.budget.increment"
												text="Payment Increment" /></th>
									</tr>
									<c:forEach items="${reportData.listReAppropriation}"
										var="ReAppropriation">
										<tr class="text-center">
											<td style="width: 10%">${ReAppropriation.accountCode}</td>
											<td style="text-align: left">${ReAppropriation.accountHead}</td>
											<td style="text-align: right">${ReAppropriation.originalEst}</td>
											<td style="text-align: right">${ReAppropriation.transferAmt}</td>
											<td style="text-align: right">${ReAppropriation.budgetSactionAmt}</td>
											<td style="text-align: right">${ReAppropriation.totalUtilamt}</td>
											<td style="text-align: right">${ReAppropriation.totalBalance}</td>
											<td style="text-align: right">${ReAppropriation.lmodate}</td>
											<td style="text-align: right">${ReAppropriation.transferAmt}</td>
											<td style="text-align: right"></td>
											<td style="text-align: right">${ReAppropriation.budgetSactionAmt}</td>
											<td style="text-align: left">${ReAppropriation.authorizedBy}</td>
											<td style="text-align: left; width: 10%;">${ReAppropriation.remarks}</td>
										</tr>
									</c:forEach>

									<!-- ---------------------ReceiptReappropriationDecrement-------------- -->

									<tr>
										<th colspan="13" style="text-align: left">Receipt
											Decrement</th>
									</tr>
									<c:forEach items="${reportData.listofreceiptdec}"
										var="receiptDec">
										<tr class="text-center">
											<td style="width: 10%">${receiptDec.accountCode}</td>
											<td style="text-align: left">${receiptDec.accountHead}</td>
											<td style="text-align: right">${receiptDec.originalEst}</td>
											<td style="text-align: right">${receiptDec.transferAmt}</td>
											<td style="text-align: right">${receiptDec.totalbudgetSacAmt}</td>
											<td style="text-align: right">${receiptDec.totalUtilamt}</td>
											<td style="text-align: right">${receiptDec.totalBalance}</td>
											<td style="text-align: right">${receiptDec.lmodate}</td>
											<td style="text-align: right"></td>
											<td style="text-align: right">${receiptDec.transferAmt}</td>
											<td style="text-align: right">${receiptDec.totalbudgetSacAmt}</td>
											<td style="text-align: left">${receiptDec.authorizedBy}</td>
											<td style="text-align: left; width: 10%;">${receiptDec.remarks}</td>
										</tr>
									</c:forEach> --%>


									<div id="export-excel">
										<table class="table table-bordered table-condensed" id="importexcel">
								<h2 class="excel-title" style="display: none" id="tlExcel">
									<spring:message code="accounts.reappropriation.heading"
										text="Approved Re-Appropriation" />
								</h2>
								<thead>
												<tr>
													<th><spring:message
															code="accounts.reappropriation.budget.code"
															text="Budget Code" /></th>
													<th style="width: 10%"><spring:message
															code="accounts.reappropriation.budget.head"
															text="Budget Head" /></th>
													<th><spring:message
															code="accounts.reappropriation.budget.original.budget"
															text="Amount of Original Budget (Rs.)" /></th>
													<th  style="width: 15%"><spring:message
															code="accounts.reappropriation.budget.amount"
															text="Amount of any re-appropriations" /></th>
													<th style="width: 12%"><spring:message
															code="accounts.reappropriation.budget.total.sanction"
															text="Total Budget sanctioned till date" /></th>
													<th style="width: 12%"><spring:message
															code="accounts.reappropriation.budget.total.utilization.Amount"
															text="Utilisation till date" /></th>
													<th><spring:message
															code="accounts.reappropriation.budget.total.balance"
															text="Balance available" /></th>
													<th><spring:message
															code="accounts.reappropriation.budget.total.date"
															text="Date" /></th>
													<th style="width: 10%"><spring:message
															code="accounts.reappropriation.budget.approved.increase"
															text="Approved Increase" /></th>
													<th style="width: 10%"><spring:message
															code="accounts.reappropriation.budget.approved.decrease"
															text="Approved Decrease" /></th>
													<th><spring:message
															code="accounts.reappropriation.budget.balance.adjustment"
															text="Balance after the adjustment" /></th>
													<th style="width: 10%"><spring:message
															code="accounts.reappropriation.budget.approved"
															text="Approved By" /></th>
													<th><spring:message
															code="accounts.reappropriation.budget.remarks"
															text="Remarks" /></th>
												</tr>
											</thead>
											<!---------------------------------------------------------------------->

											<tfoot class="paginate">
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
											
											<tfoot>
												<tr id="PaymntDecrmntId">
													<th colspan="13" style="text-align: left" ><spring:message
															code="accounts.reappropriation.budget.decrement"
															text="Payment Decrement" />
													</th>
												</tr>
											</tfoot>
											<tbody>
												<c:forEach items="${reportData.listDecReAppropriation}"
													var="DecReAppropriation">
													<tr class="text-center">
														<td style="width: 10%" id="PaymntDecrmnt">${DecReAppropriation.accountCode}</td>
														<td style="text-align: left">${DecReAppropriation.accountHead}</td>
														<td style="text-align: right">${DecReAppropriation.originalEst}</td>
														<td style="text-align: right">${DecReAppropriation.transferAmt}</td>
														<td style="text-align: right">${DecReAppropriation.budgetSactionAmt}</td>
														<td style="text-align: right">
															${DecReAppropriation.totalUtilamt}</td>
														<td style="text-align: right">${DecReAppropriation.totalBalance}</td>
														<td style="text-align: right">${DecReAppropriation.lmodate}</td>
														<td style="text-align: right"></td>
														<td style="text-align: right">${DecReAppropriation.transferAmt}</td>
														<td style="text-align: right">${DecReAppropriation.budgetSactionAmt}</td>
														<td style="text-align: left">${DecReAppropriation.authorizedBy}</td>
														<td style="text-align: left; width: 10%;">${DecReAppropriation.remarks}</td>
													</tr>
												</c:forEach>
											</tbody>
											<tfoot>
												<tr id="PaymntIncrmntId">
													<th colspan="13" style="text-align: left"><spring:message
															code="accounts.reappropriation.budget.increment"
															text="Payment Increment" />
													</th>
												</tr>
											</tfoot>
											<tbody>
												<c:forEach items="${reportData.listReAppropriation}"
													var="ReAppropriation">
													<tr class="text-center">
														<td style="width: 10%" id="PaymntIncrmnt">${ReAppropriation.accountCode}</td>
														<td style="text-align: left">${ReAppropriation.accountHead}</td>
														<td style="text-align: right">${ReAppropriation.originalEst}</td>
														<td style="text-align: right">${ReAppropriation.transferAmt}</td>
														<td style="text-align: right">${ReAppropriation.budgetSactionAmt}</td>
														<td style="text-align: right">${ReAppropriation.totalUtilamt}</td>
														<td style="text-align: right">${ReAppropriation.totalBalance}</td>
														<td style="text-align: right">${ReAppropriation.lmodate}</td>
														<td style="text-align: right">${ReAppropriation.transferAmt}</td>
														<td style="text-align: right"></td>
														<td style="text-align: right">${ReAppropriation.budgetSactionAmt}</td>
														<td style="text-align: left">${ReAppropriation.authorizedBy}</td>
														<td style="text-align: left; width: 10%;">${ReAppropriation.remarks}</td>
													</tr>
												</c:forEach>
											</tbody>
												<!-- ---------------------ReceiptReappropriationDecrement-------------- -->

											<tfoot>
												<tr id="ReceiptDecrmntId">
													<th colspan="13" style="text-align: left">Receipt
														Decrement</th>
												</tr>
											</tfoot>
											<tbody>
												<c:forEach items="${reportData.listofreceiptdec}"
													var="receiptDec">
													<tr class="text-center">
														<td style="width: 10%" id="ReceiptDecrmnt">${receiptDec.accountCode}</td>
														<td style="text-align: left">${receiptDec.accountHead}</td>
														<td style="text-align: right">${receiptDec.originalEst}</td>
														<td style="text-align: right">${receiptDec.transferAmt}</td>
														<td style="text-align: right">${receiptDec.totalbudgetSacAmt}</td>
														<td style="text-align: right">${receiptDec.totalUtilamt}</td>
														<td style="text-align: right">${receiptDec.totalBalance}</td>
														<td style="text-align: right">${receiptDec.lmodate}</td>
														<td style="text-align: right"></td>
														<td style="text-align: right">${receiptDec.transferAmt}</td>
														<td style="text-align: right">${receiptDec.totalbudgetSacAmt}</td>
														<td style="text-align: left">${receiptDec.authorizedBy}</td>
														<td style="text-align: left; width: 10%;">${receiptDec.remarks}</td>
													</tr>
												</c:forEach>
											</tbody>


												<!-- ---------------------ReceiptReappropriationIncrement-------------- -->
											<tfoot>
												<tr id="ReceiptIncrmntId">
													<th colspan="13" style="text-align: left">Receipt
														Increment</th>
												</tr>
											</tfoot>
											<tbody>
												<c:forEach items="${reportData.listofreceiptinc}"
													var="receiptInc">
													<tr class="text-center">
														<td style="width: 10%" id="ReceiptIncrmnt">${receiptInc.accountCode}</td>
														<td style="text-align: left">${receiptInc.accountHead}</td>
														<td style="text-align: right">${receiptInc.originalEst}</td>
														<td style="text-align: right">${receiptInc.transferAmt}</td>
														<td style="text-align: right">${receiptInc.totalbudgetSacAmt}</td>
														<td style="text-align: right">${receiptInc.totalUtilamt}</td>
														<td style="text-align: right">${receiptInc.totalBalance}</td>
														<td style="text-align: right">${receiptInc.lmodate}</td>
														<td style="text-align: right">${receiptInc.transferAmt}</td>
														<td style="text-align: right"></td>
														<td style="text-align: right">${receiptInc.totalbudgetSacAmt}</td>
														<td style="text-align: left">${receiptInc.authorizedBy}</td>
														<td style="text-align: left; width: 10%;">${receiptInc.remarks}</td>
													</tr>
												</c:forEach>
											</tbody>
											<tfoot class="avoid-sort">
												<tr>
													<th colspan="3"><spring:message
															code="accounts.reappropriation.budget.chief.execute"
															text="Chief Executive Officer" /></th>
													<th colspan="4">&nbsp;</th>
													<th colspan="3"><spring:message
															code="accounts.reappropriation.president.cantonment.board"
															text="President , Municipal*" /></th>
													<th colspan="4">&nbsp;</th>
												</tr>
												<tr>
													<th colspan="3"><spring:message
															code="accounts.reappropriation.dated" text="Dated:" /></th>
													<th colspan="4">&nbsp;</th>
													<th colspan="3"><spring:message
															code="accounts.reappropriation.dated" text="Dated:" /></th>
													<th colspan="4">&nbsp;</th>
												</tr>
											</tfoot>
									</table>
									</div>
									<style>
										@media print{
											.paginate{
												visibility: hidden;
											}
											@page {
													margin: 15px;
												} 
										}
									  </style>
									</div>
									<div class="text-center hidden-print padding-10">
										<button onclick="PrintDiv('receipt');"
											class="btn btn-primary hidden-print" title="Print">
											<i class="fa fa-print"></i>
											<spring:message
												code="account.budgetestimationpreparation.print"
												text="Print" />
										</button>
										<button type="button" class="btn btn-danger"
											onclick="window.location.href='AccountBudgetReports.html'"
											title="Back">
											<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
											<spring:message code="account.bankmaster.back" text="Back" />
										</button>
									</div>
									</form>
									</div>
									</div>
									</div>
									
<script>

/* Defect #39426 */
	$(function() {
		var PaymntDecrmnt = $('#PaymntDecrmnt').text();
		var PaymntIncrmnt = $('#PaymntIncrmnt').text();
		var ReceiptDecrmnt = $('#ReceiptDecrmnt').text();
		var ReceiptIncrmnt = $('#ReceiptIncrmnt').text();
		if (!PaymntDecrmnt) {
			$('#PaymntDecrmntId').remove();
		}
		if (!PaymntIncrmnt) {
			$('#PaymntIncrmntId').remove();
		}
		if (!ReceiptDecrmnt) {
			$('#ReceiptDecrmntId').remove();
		}
		if (!ReceiptIncrmnt) {
			$('#ReceiptIncrmntId').remove();
		}
	});

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
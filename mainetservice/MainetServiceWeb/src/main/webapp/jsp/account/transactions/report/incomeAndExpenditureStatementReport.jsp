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
					<spring:message code="income.expenditure.title"
						text="Income and Expenditure" />
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
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}</c:if><br>
										<strong class="excel-title"><spring:message
												code="income.expenditure.acc.yearend"
												text="Income and Expenditure Account for the year ended" />
											${reportData.financialYear}</strong>
										<p>
											<strong><spring:message
                            code="from.date.label" text="From Date" />:</strong> ${reportData.fromDate} <strong>
												<spring:message
                            code="account.todate" text="To Date" />:</strong> ${reportData.toDate}
										</p></div>
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
						<div class="margin-top-10">
							<div id="export-excel">
								<table class="table table-bordered table-condensed" id="importexcel">
									<h2 class="excel-title" id="tlExcel" style="display: none">
										<spring:message code="income.expenditure.title"
											text="Income and Expenditure" />
									</h2>
									<thead>
										<tr>
											<th><spring:message code="classified.abstact.acc.code"
													text="Account Head" /></th>
											<th style="width:10%;"><spring:message
													code="income.expenditure.schedule.number"
													text="Schedule No." /></th>
											<th><spring:message
													code="income.expenditure.current.year.amt"
													text="Current Year Amount (Rs.)" /></th>
											<th><spring:message
													code="income.expenditure.previous.year.amt"
													text="Previous Year Amount (Rs.)" /></th>
										</tr>
									</thead>
                                <tfoot class="tfoot">
									<tr>
										<th colspan="4" class="ts-pager form-horizontal">
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
										<c:forEach items="${reportData.listOfIncome}" var="income">
										
											<tr>
												<td>${income.accountCode }</td>
												<%-- <td>${income.accountHead }</td> --%>
												<td align="center">${income.scheduleNo }</td>
												<td align="right">${income.currentYearAmount }</td>
												<td align="right">${income.previousIncome }</td>
											</tr>
											
										</c:forEach>
								
									<tr>
										<th>A &nbsp;</th>
										<th><spring:message code="income.expenditure.totalincome"
												text="Total Income" /></th>

										<th style="text-align: right" class="cmAmount">${reportData.totalCrAmount}</th>
										<th style="text-align: right">${reportData.previousInTotal}</th>
									</tr>
							
										<c:forEach items="${reportData.listOfExpenditure}"
											var="expenditure">
											
											<tr>
												<td>${expenditure.accountCode}</td>
												<td align="center">${expenditure.scheduleNo }</td>
												<td align="right">${expenditure.currentYearAmount}</td>
												<td align="right">${expenditure.priviousExp}</td>

											</tr>
										</c:forEach>
										<tr><td>272 - Depreciation</td>
												<td align="center"></td>
												<td align="right">${reportData.currAm272}</td>
												<td align="right">${reportData.prevAm272}</td></tr>
								
									
								
									<tr>
										<tr><td>Less Depreciation Transfer to Capital Contribution</td>
												<td align="center"></td>
												<td align="right">${reportData.currAm312}</td>
												<td align="right">${reportData.prevAm312}</td></tr>
								
									
								
									<tr>
										<th>B &nbsp;</th>
										<th><spring:message code="income.expenditure.totalexp"
												text="Total Expenditure" /></th>

										<th style="text-align: right">${reportData.totalDrAmount}</th>
										<th style="text-align: right">${reportData.previousExpTotal}</th>
									</tr>
								
								
									<tr>
											<td  style="text-align: left;"><spring:message
													code="income.expenditure.substract.sum.amount"
													text="<b>A-B </b> Gross surplus/ (deficit) of income over expenditure before prior period items (A-B)" /></td>
											<td></td>
											<td style="text-align: right">${reportData.totalcurrentAmt}</td>
											<td style="text-align: right">${reportData.totaltransferAmts}</td>
										</tr>
										<tr>
										
											<td style="text-align: left;"><spring:message
													code="income.expenditure.substract.sum.ext1"
													text="280 - Add/Less: Prior period Items (Net)" /></td>
														<td style="text-align: center">IE-18</td>
											<td style="text-align: right">${reportData.currAm280}</td>
											<td style="text-align: right">${reportData.prevAm280}</td>
										</tr>
										<tr>
											<td style="text-align: left;"><spring:message
													code="income.expenditure.substract.sum.ext2"
													text="<b>Gross surplus/ (deficit) of Income over expenditure after prior period items (C-D)</b>"/></td>
										<td style="text-align:center"></td>
										<td style="text-align: right">${reportData.currYrCD}</td>
											<td style="text-align: right">${reportData.prevYrCD}</td>
										</tr>
										<tr>
											<td style="text-align: left;"><spring:message
													code="income.expenditure.substract.sum.ext3"
													text="290 - Less: Transfer to Reserved Fund" /></td>
													<td style="text-align: left"></td>
											<td style="text-align: right">${reportData.currAm290}</td>
											<td style="text-align: right">${reportData.prevAm290}</td>
										</tr>
										<tr>
											<td style="text-align: left;"><spring:message
													code="income.expenditure.substract.sum.ext4"
													text="<b>G Net balance being surplus/ (deficit) carried over to Municipal Fund (E-F)</b>" /></td>
												<td style="text-align: left"></td>
											<td style="text-align: right">${reportData.currYrCD-reportData.currAm290}</td>
											<td style="text-align: right">${reportData.prevYrCD-reportData.prevAm290}</td>
										</tr>
								</tbody>
							  </table>
							</div>
						</div>
						<input type="hidden" value="${validationError}" id="errorId">
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
							onclick="window.location.href='AccountFinancialReport.html'"
							title="Back">
							<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
							<spring:message code="account.bankmaster.back" text="Back" />
						</button>
				</div>
			</div>
		</div>
	</div>
</div>
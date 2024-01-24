<!-- Start JSP Necessary Tags -->
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
<script
	src="assets/libs/excel-export/excel-export.js"></script>
	
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>	
	
<script src="js/account/quaterlyBudgetVarianceReport.js"
	type="text/javascript"></script>
<script language="javascript">
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
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message code="quaterly.Budget.Variance.Report" text="Quaterly Budget Variance Report" /></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
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
						<div class="text-bold"><spring:message code="quaterly.Budget.Variance.Report" text="Quaterly Budget Variance Report" /></div>
						<div>
							<spring:message code="For.the.period.from" text="For the period from" /> <span class="text-bold">${command.accountFinancialReportDTO.financialYear}</span>
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
						<span class="text-bold"><spring:message
                                    code="budget.estimate.sheet.format.bud" text="BUD-1" /></span>
					</div>

					<div class="container">
						<div id="export-excel">
							<table class="table table-bordered" id="importexcel">
								<div class="excel-title" id="tlExcel" style="display: none"><spring:message code="quaterly.Budget.Variance.Report" text="Quaterly Budget Variance Report" />
									</div>
								<thead>
									<tr>
										<th rowspan="2" class="text-center">Code No.</th>
										<th rowspan="2" class="text-center">Head Of Account</th>
										<th rowspan="2" class="text-center">Budget Estimate (Rs.)</th>
										<th colspan="4" class="text-center">Progressive Total At
											The End Of Each Quarter Rs.</th>
										<th rowspan="2" class="text-center">Variance Rs.</th>
										<th rowspan="2" class="text-center">Remark</th>
									</tr>
									<tr>
										<th class="text-center">Qtr. 1</th>
										<th class="text-center">Qtr. 2</th>
										<th class="text-center">Qtr. 3</th>
										<th class="text-center">Total</th>
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
												<option selected="selected" value="5" class="form-control">5</option>
												<option value="12">10</option>
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
										<th></th>
										<th class="text-bold" colspan="8" style="text-align: left"><u>REVENUE RECEIPTS<u></th>
									</tr>
									<!-- START REVENUE RECEIPTS  -->
									<c:forEach
										items="${command.accountFinancialReportDTO.listOfBudgetEstimation}"
										var="data" varStatus="index">
										<c:if
											test="${command.accountFinancialReportDTO.listOfBudgetEstimation.size()-1!=index.index}">
											<tr>
												<td class="text-center">${data.accountCode}</td>
												<td>${data.accountHeadDesc}</td>
												<td class="text-right">${data.originalEst}</td>
												<td class="text-right">${data.quarter1Amount}</td>
												<td class="text-right">${data.quarter2Amount}</td>
												<td class="text-right">${data.quarter3Amount}</td>
												<td class="text-right">${data.totalCollected}</td>
												<td class="text-center">${data.percentage}&nbsp;%</td>
												<td></td>
											</tr>
										</c:if>
										<c:if
											test="${command.accountFinancialReportDTO.listOfBudgetEstimation.size()==index.count}">
											<tr>
												<td></td>
												<td>Any Other Revenue Receipts [Specify]</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td class="text-bold">Sub-Total</td>
												<td class="text-right text-bold">${data.totalBudget}</td>
												<td class="text-right text-bold">${data.totalQuater1Amt}</td>
												<td class="text-right text-bold">${data.totalQuater2Amt}</td>
												<td class="text-right text-bold">${data.totalQuater3Amt}</td>
												<td class="text-right text-bold">${data.sumOftotalAmt}</td>
												<td class="text-center text-bold"></td>
												<td></td>
											</tr>
										</c:if>
									</c:forEach>
									<!-- END REVENUE RECEIPTS  -->
									
									
									<!-- START REVENUE EXPENDITURE-->
									<tr>
										<th></th>
										<th class="text-bold" colspan="8" style="text-align: left"><u>REVENUE EXPENDITURE</u></th>
									</tr>
									<c:forEach
										items="${command.accountFinancialReportDTO.listOfExpenditure}"
										var="data" varStatus="index">
										<c:if
											test="${command.accountFinancialReportDTO.listOfExpenditure.size()-1!=index.index}">
											<tr>
												<td class="text-center">${data.accountCode}</td>
												<td>${data.accountHeadDesc}</td>
												<td class="text-right">${data.originalEst}</td>
												<td class="text-right">${data.quarter1Amount}</td>
												<td class="text-right">${data.quarter2Amount}</td>
												<td class="text-right">${data.quarter3Amount}</td>
												<td class="text-right">${data.totalCollected}</td>
												<td class="text-center">0%</td>
												<td></td>
											</tr>
										</c:if>
										<c:if
											test="${command.accountFinancialReportDTO.listOfExpenditure.size()==index.count}">
											<tr>
												<td></td>
												<td class="text-bold">Sub-Total</td>
												<td class="text-right text-bold">${data.totalBudget}</td>
												<td class="text-right text-bold">${data.totalQuater1Amt}</td>
												<td class="text-right text-bold">${data.totalQuater2Amt}</td>
												<td class="text-right text-bold">${data.totalQuater3Amt}</td>
												<td class="text-right text-bold">${data.sumOftotalAmt}</td>
												<td class="text-center text-bold">0%</td>
												<td></td>
											</tr>
										</c:if>
									</c:forEach>
									<!-- END REVENUE EXPENDITURE-->
									
									
									
									
								   <!-- START CAPITAL RECEIPTS -->
									<tr>
										<th></th>
										<th class="text-bold" colspan="8" style="text-align: left"><u>CAPITAL RECEIPTS</u></th>
									</tr>
									<c:forEach
										items="${command.accountFinancialReportDTO.liabilityList}"
										var="data" varStatus="index">
										<c:if
											test="${command.accountFinancialReportDTO.liabilityList.size()-1!=index.index}">
											<tr>
												<td class="text-center">${data.accountCode}</td>
												<td>${data.accountHeadDesc}</td>
												<td class="text-right">${data.originalEst}</td>
												<td class="text-right">${data.quarter1Amount}</td>
												<td class="text-right">${data.quarter2Amount}</td>
												<td class="text-right">${data.quarter3Amount}</td>
												<td class="text-right">${data.totalCollected}</td>
												<td class="text-center">0%</td>
												<td></td>
											</tr>
										</c:if>
										<c:if
											test="${command.accountFinancialReportDTO.liabilityList.size()==index.count}">
											<tr>
												<td></td>
												<td class="text-bold">Sub-Total</td>
												<td class="text-right text-bold">${data.totalBudget}</td>
												<td class="text-right text-bold">${data.totalQuater1Amt}</td>
												<td class="text-right text-bold">${data.totalQuater2Amt}</td>
												<td class="text-right text-bold">${data.totalQuater3Amt}</td>
												<td class="text-right text-bold">${data.sumOftotalAmt}</td>
												<td class="text-center text-bold">0%</td>
												<td></td>
											</tr>
										</c:if>
									</c:forEach>
									<!-- END CAPITAL RECEIPTS-->
									
									
								   <!-- START CAPITAL EXPENDITURES -->
									<tr>
										<th></th>
										<th class="text-bold" colspan="8" style="text-align: left"><u>CAPITAL EXPENDITURES</u></th>
									</tr>
									<c:forEach
										items="${command.accountFinancialReportDTO.assetList}"
										var="data" varStatus="index">
										<c:if
											test="${command.accountFinancialReportDTO.assetList.size()-1!=index.index}">
											<tr>
												<td class="text-center">${data.accountCode}</td>
												<td>${data.accountHeadDesc}</td>
												<td class="text-right">${data.originalEst}</td>
												<td class="text-right">${data.quarter1Amount}</td>
												<td class="text-right">${data.quarter2Amount}</td>
												<td class="text-right">${data.quarter3Amount}</td>
												<td class="text-right">${data.totalCollected}</td>
												<td class="text-center">0%</td>
												<td></td>
											</tr>
										</c:if>
										<c:if
											test="${command.accountFinancialReportDTO.assetList.size()==index.count}">
											<tr>
												<td></td>
												<td class="text-bold">Sub-Total</td>
												<td class="text-right text-bold">${data.totalBudget}</td>
												<td class="text-right text-bold">${data.totalQuater1Amt}</td>
												<td class="text-right text-bold">${data.totalQuater2Amt}</td>
												<td class="text-right text-bold">${data.totalQuater3Amt}</td>
												<td class="text-right text-bold">${data.sumOftotalAmt}</td>
												<td class="text-center text-bold">0%</td>
												<td></td>
											</tr>
										</c:if>
									</c:forEach>
									<!-- END CAPITAL EXPENDITURES-->
								</tbody>
							</table>
						</div>
					</div>
					<div class="text-center margin-top-15 hidden-print">
						<button type="button" class="btn btn-primary" title="Print"
							onClick="printdiv('receipt');">
							<i class="fa fa-print padding-right-5" aria-hidden="true"></i><spring:message code="account.budget.code.print" text="Print" />
						</button>
						<button id="btnExport1" type="button"
							class="btn btn-blue-2 hidden-print" data-toggle="tooltip"
							data-original-title="Download">
							<i class="fa fa-file-excel-o"></i> <spring:message code="acounts.download" text="Download" />
						</button>
						<button type="button" class="btn btn-danger" title="Back"
							onclick="back();">
							<i class="fa fa-chevron-circle-left padding-right-5"
								aria-hidden="true"></i> <spring:message code="account.bankmaster.back" text="Back" />
						</button>
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
		</div>
	</div>
</div>


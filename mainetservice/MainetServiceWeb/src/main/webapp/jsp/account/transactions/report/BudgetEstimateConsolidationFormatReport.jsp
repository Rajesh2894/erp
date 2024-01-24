<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script
	src="assets/libs/excel-export/excel-export.js"></script>
	
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>	
	
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
			<h2 class="excel-title"><spring:message code="Budget.Consolidation.Format" text="Budget Consolidation Format"/></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="form-group">
						<div
							class="ccol-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if>   
								<br> <u><spring:message code="Budget.Estimate.Consolidation.Format" text="Budget Estimate Consolidation Format"/>
								${command.accountFinancialReportDTO.reportType}  </u>
							</h3>
						</div>
						<div class="col-sm-2 col-xs-3 pull-right">
							<p>
								<spring:message code="swm.day.wise.month.report.date"
									text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="swm.day.wise.month.report.time"
									text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
							<span class="text-bold"><spring:message code="budget.consolidation.format.bud2" text="BUD-2"/></span>
						</div>
					</div>
					<div class="col-sm-10 margin-bottom-10">
						<p>
							<span class="text-bold"><spring:message code="budget.consolidation.format.financial.year" text="Financial Year :"/> </span> <u>${command.accountFinancialReportDTO.financialYear}</u>
						</p>
					</div>
					<div class="container">
						<div id="export-excel">
							<table class="table table-bordered" id="importexcel">
								<div class="excel-title" id="tlExcel" style="display: none">
									<spring:message code="" text="Budget Consolidation Format" />
								</div>
								<thead>
									<tr>
										<th class="text-center" width="8%"><spring:message code="budget.consolidation.format.function" text="Function<"/></th>
										<th class="text-center"><spring:message code="budget.consolidation.format.functionary" text="Functionary"/></th>
										<th class="text-center"><spring:message code="budget.consolidation.format.field" text="Field"/></th>
										<th class="text-center"><spring:message code="budget.consolidation.format.hoa" text="Head Of Account - Description Of Item"/></th>
										<th class="text-center"><spring:message code="budget.consolidation.format.previous.year" text="Actual For The Previous Year"/></th>
										<th class="text-center"><spring:message code="budget.consolidation.format.current.year" text="Budget Estimates For The Current Year"/></th>
										<th class="text-center"><spring:message code="budget.consolidation.format.revised.of.current.year" text="Revised Estimates For The Current Year"/></th>
										<th class="text-center"><spring:message code="budget.consolidation.format.budget.of.next.yr" text="Budget Estimates For The Next Year"/></th>
									</tr>
									<tr>
										<th class="text-center">1</th>
										<th class="text-center">2</th>
										<th class="text-center">3</th>
										<th class="text-center">4</th>
										<th class="text-center">5</th>
										<th class="text-center">6</th>
										<th class="text-center">7</th>
										<th class="text-center">8</th>
									</tr>
								</thead>
								
								<tbody>
									<c:forEach items="${command.accountFinancialReportDTO.listOfIncome}" var="data"
										varStatus="index">
										<tr>
										   <td>${data.functionName}</td>
										   <td>${data.columnName}</td>
											<td>${data.fieldId}</td>
											<td>${data.accountHeadDesc}</td>
											<td align="right">${data.prevYramount}</td>
											<td align="right">${data.currentYrAmount}</td>
											<td align="right">${data.revisedEstimation}</td>
											<td align="right">${data.nextYrAmout}</td>
										</tr>
									</c:forEach>
								</tbody>
								<thead>
									<tr>
										<th></th>
										<th></th>
										<th></th>
										<th><spring:message code=""
												text="Total :" /></th>  <!-- Sub-total (Tax Revenues) -->
										    <th align="right">${command.accountFinancialReportDTO.prevYramount}</th>
											<th align="right">${command.accountFinancialReportDTO.currentYrAmount}</th>
											<th align="right">${command.accountFinancialReportDTO.revisedofCurrentYrAmount}</th>
											<th align="right">${command.accountFinancialReportDTO.nextYrAmout}</th>
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
												<option value="12">12</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="all">All Records</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
							</table>
						</div>
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
			<div class="text-center hidden-print padding-10">
				<button onClick="printdiv('receipt');"
					class="btn btn-primary hidden-print" title='<spring:message code="account.budgetestimationpreparation.print" text="Print"/>'>
					<i class="fa fa-print"></i><spring:message code="account.budgetestimationpreparation.print" text="Print"/> 
				</button>
				<button id="btnExport1" type="button"
					class="btn btn-blue-2 hidden-print" title='<spring:message code="acounts.download" text="Download"/>'>
					<i class="fa fa-file-excel-o"></i><spring:message code="acounts.download" text="Download"/> 
				</button>
				<button type="button" class="btn btn-danger" onclick="back();" title='<spring:message code="solid.waste.back" text="Back" />'>
					<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
					<spring:message code="solid.waste.back" text="Back" />
				</button>
			</div>
		</div>
	</div>
</div>
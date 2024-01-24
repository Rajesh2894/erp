<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script
	src="assets/libs/excel-export/excel-export.js"></script>
<script src="js/account/assetsAndLiabilitiesScheduleReport.js"
	type="text/javascript"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<!-- Start Content here -->
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
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>  <spring:message code="liabilities.Assets.Schedule.Report" text="Liabilities And Assets Schedule Report" /></h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" id="incomeAndExpenditureReport"
				class="form-horizontal">
				<div id="receipt">
						<div
							class="ccol-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if> 
								<br> <u>Schedule Report : Code No.${command.accountIEDto.primaryAcHeadDesc} </u>
								<p class="text-extra-large">  <spring:message code="account.budgetopenmaster.financialyear" text="Financial Year" /> : ${command.accountIEDto.financialYr}</p>
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
						</div>
					<div class="clearfix"></div>
					<div class="table-responsive margin-top-10">
						<div id="export-excel">
							<table class="table table-bordered  table-fixed" id="importexcel">
							<div id="tlExcel" style="display: none">Liabilities And Assets Schedule Report</div>
								<thead>
									<tr>
										<th class="text-center">Code No.</th>
										<th class="text-center" width="">Particulars</th>
										<th class="text-center">Opening balance<br> as the beginning of<br> the year amount<br>(Rs.)</th>
										<th class="text-center">Addition during <br>the year <br>(Rs.)</th>
										<th class="text-center">Total <br>(Rs.)</th>
										<th class="text-center">Deductions during<br> the year <br>(Rs.)</th>
										<th class="text-center">Balance at the end<br> of the current year<br>(Rs.)</th>
									</tr>
								</thead>
								<tbody>
								<c:choose>
								  <c:when test="${command.accountIEDto.repotType eq 'Liabilities'}">
								        <c:forEach items="${command.accountIEDto.listOfIE}" var="data"
										varStatus="index">
										<tr>
											<td style="text-align: center;">${data.primaryAcHeadId}</td>
											<td>${data.primaryAcHeadDesc}</td>
											
											<!--opening Balance Dr  -->
											<td class="text-right">${data.openingBalannce}</td>
											<c:set var="sumofopeningBalannce" value="${sumofopeningBalannce + data.openingBalannce}" />
											
											<!--transaction Balance Dr  -->
											<td class="text-right">${data.txcramount}</td>
											<c:set var="sumoftxdramount" value="${sumoftxdramount + data.txcramount}" />
											
											<!--Total opening Balance Dr Add with transaction Balance Dr -->
											<c:set var="totalc" value="${0.00}" />
											<c:set var="totalc" value="${data.openingBalannce + data.txcramount}" />
											<td class="text-right"><fmt:formatNumber type="number" value="${totalc}"   pattern = "0.00" /></td>
										    
										    <!--sum of total amount  -->
										    <c:set var="sumoftotal" value="${sumoftotal +totalc}"/>
											<td class="text-right">${data.txdramount}</td> 
										    <c:set var="sumoftxcramount" value="${sumoftxcramount +data.txdramount}"/>
											
											<!--subtract From total opening balance to deduction balance  -->
											<c:set var="curryrbalance" value="${0}" />
											<c:set var="curryrbalance" value="${totalc - data.txdramount}" />
											<td class="text-right"><fmt:formatNumber type="number" value="${curryrbalance}"  pattern = "0.00" /></td>
											
											<c:set var="sumofcurryrbalance" value="${sumofcurryrbalance +curryrbalance}"/>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
								   <c:if test="${command.accountIEDto.repotType eq 'Assets'}">
								    <c:forEach items="${command.accountIEDto.listOfIE}" var="data"
										varStatus="index">
										<tr>
											<td style="text-align: center;">${data.primaryAcHeadId}</td>
											<td>${data.primaryAcHeadDesc}</td>
											
											<!--opening Balance Dr  -->
											<td class="text-right">${data.openingBalannce}</td>
											<c:set var="sumofopeningBalannce" value="${sumofopeningBalannce + data.openingBalannce}" />
											
											<!--transaction Balance Dr  -->
											<td class="text-right">${data.txdramount}</td>
											<c:set var="sumoftxdramount" value="${sumoftxdramount + data.txdramount}" />
											
											<!--Total opening Balance Dr Add with transaction Balance Dr -->
											<c:set var="totalc" value="${0.00}" />
											<c:set var="totalc" value="${data.openingBalannce + data.txdramount}" />
											<td class="text-right"><fmt:formatNumber type="number" value="${totalc}"   pattern = "0.00" /></td>
										    <c:set var="sumoftotal" value="${sumoftotal +totalc}"/>
										    
										    
										    <!--sum of total amount  -->
										    <c:set var="sumoftotal" value="${sumoftotal +totalc}"/>
											<td class="text-right">${data.txcramount}</td> 
										    <c:set var="sumoftxcramount" value="${sumoftxcramount +data.txcramount}"/>
										    
											
											<!--subtract From total opening balance to transaction Balance Cr -->
											<c:set var="curryrbalance" value="${0}" />
											<c:set var="curryrbalance" value="${totalc - data.txcramount}" />
											<td class="text-right"><fmt:formatNumber type="number" value="${curryrbalance}"  pattern = "0.00" /></td>
											<c:set var="sumofcurryrbalance" value="${sumofcurryrbalance +curryrbalance}"/>
											
											
										</tr>
									</c:forEach>
								   </c:if>
								</c:otherwise>
								</c:choose>
									<tr>
										<td class="text-right text-bold"></td>
										<td class="text-right text-bold"><b>Total</b></td>
										<td class="text-right text-bold"><fmt:formatNumber type="number" value="${sumofopeningBalannce}" minFractionDigits="2" /></td>
										<td class="text-right text-bold"><fmt:formatNumber type="number" value="${sumoftxdramount}" minFractionDigits="2" /></td>
										<td class="text-right text-bold"><fmt:formatNumber type="number" value="${sumoftotal}" minFractionDigits="2" /></td>
										<td class="text-right text-bold"><fmt:formatNumber type="number" value="${sumoftxcramount}" minFractionDigits="2" /></td>
										<td class="text-right text-bold"><fmt:formatNumber type="number" value="${sumofcurryrbalance}" minFractionDigits="2" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</form:form>
			<div class="text-center hidden-print padding-10">
				<button onClick="printdiv('receipt');"
					class="btn btn-primary hidden-print" data-toggle="tooltip" data-original-title="Print">
					<i class="fa fa-print"></i> <spring:message code="account.budgetaryrevision.print" text="Print" />
				</button>
				<button id="btnExport1" type="button"
					class="btn btn-blue-2 hidden-print" data-toggle="tooltip" data-original-title="Download">
					<i class="fa fa-file-excel-o"></i>   <spring:message code="acounts.download" text="Download" />
				</button>
				<button type="button" class="btn btn-danger" onclick="back();" data-toggle="tooltip" data-original-title="Back">
				<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
					<spring:message code="account.back" text="Back" />
				</button>
			</div>
		</div>
	</div>
</div>
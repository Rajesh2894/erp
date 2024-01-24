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
<script src="js/account/incomeAndExpeditureReport.js"
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
			<h2 class="excel-title"> Income And Expenditure Schedule Report</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" id="incomeAndExpenditureReport"
				class="form-horizontal">
				<div id="receipt">
					<div class="form-group">
						<div
							class="ccol-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}
								<br> <u>Schedule ${command.scheduleNo} : ${command.accountIEDto.primaryAcHeadDesc} </u>
								<p class="text-extra-large">Financial Year : ${command.accountIEDto.financialYr}</p>
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
					</div>
					<!-- <div class="clearfix padding-10"></div> -->
					<div class="container">
						<div id="export-excel">
							<table class="table table-bordered  table-fixed" id="importexcel">
							<h2 class="excel-title" id="tlExcel" style="display: none"> Income And Expenditure Schedule Report</h2>
								<thead>
									<tr>
										<td colspan="4" style="display:none">
											<div class="form-group">
												<div
													class="ccol-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
													<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}
														<br> <u>Schedule ${command.scheduleNo} : ${command.accountIEDto.primaryAcHeadDesc} </u>
														<p class="text-extra-large">Financial Year : ${command.accountIEDto.financialYr}</p>
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
											</div>
										</td>
									</tr>
									<tr>
										<th class="text-center">Code No.</th>
										<th class="text-center">Particulars</th>
										<!-- <th class="text-center">Schedule No</th> -->
										<th class="text-center">Current Year Amount (Rs.)</th>
										<th class="text-center">Previous YearAmount (Rs.)</th>
									</tr>
								</thead>
								<c:set var="totalc" value="${0}" />
								<c:set var="totalp" value="${0}" />
								<tbody>
									<c:forEach items="${command.accountIEDto.listOfIE}" var="data"
										varStatus="index">
										<c:if test="${(data.primaryAcHeadId ne '110-90') && (data.primaryAcHeadId ne '130-90') && (data.primaryAcHeadId ne '140-90')  }">
										<tr>
											<td style="text-align: center;">${data.primaryAcHeadId}</td>
											<td>${data.primaryAcHeadDesc}</td>
											<%-- <td align="center">${data.scheduleNo}</td> --%>
											<%-- <td class="text-right">${data.currentYearAmount}</td> --%>
											
													<td class="text-right">${data.currentYearAmount}</td>
											
													<td class="text-right">${data.previousYearAmount}</td>
												
											<c:set var="totalc"
												value="${totalc + data.currentYearAmount}" />
											<c:set var="totalp"
												value="${totalp + data.previousYearAmount}" />
										</tr>
										</c:if>
									</c:forEach>
									<c:forEach items="${command.accountIEDto.listOfIE}" var="data3"
										varStatus="index">
									<c:if test="${(data3.primaryAcHeadId eq '110-90') || (data3.primaryAcHeadId eq '130-90') || (data3.primaryAcHeadId eq '140-90')}">
									<tr>
										<td></td>
										<!-- <td></td> -->
										<td><b>Sub Total  </b></td>
										<td class="text-right text-bold"><fmt:formatNumber
												type="number" value="${totalc}" minFractionDigits="2" /></td>
										<td class="text-right text-bold"><fmt:formatNumber
												type="number" value="${totalp}" minFractionDigits="2" /></td>
									</tr>
									</c:if>
									</c:forEach>
									<c:set var="totalc1" value="${0}" />
								<c:set var="totalp1" value="${0}" />
									<c:forEach items="${command.accountIEDto.listOfIE}" var="data1"
										varStatus="index">
										<c:if test="${(data1.primaryAcHeadId eq '110-90') || (data1.primaryAcHeadId eq '130-90') || (data1.primaryAcHeadId eq '140-90')}">
										<tr>
											<td style="text-align: center;">${data1.primaryAcHeadId}</td>
											<c:if test="${(data1.primaryAcHeadId eq '110-90')}" >
											<td><spring:message code="acc.tax.rem110" text="Less: Tax Remissions & Refund [Schedule IE - 1(a)]"/></td>
											</c:if>
					                        <c:if test="${(data1.primaryAcHeadId eq '130-90')||(data1.primaryAcHeadId eq '140-90')}" >
											<td><spring:message code="acc.tax.rem130" text="Less: Rent remission and refunds"/> </td>
											</c:if>
											<c:choose>
												<c:when test="${data1.currentYearAmount > '0' || data1.currentYearAmount le '0'}">
													<td class="text-right">${-data1.currentYearAmount}</td>
												</c:when>
												<c:otherwise>
													<c:if test="${data1.currentYearAmount ==null  }">
														<td class="text-right">0.00</td>
													</c:if>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${data1.previousYearAmount > '0'||data1.previousYearAmount le '0'}">
													<td class="text-right">${-data1.previousYearAmount}</td>
												</c:when>
												<c:otherwise>
													<c:if test="${data1.previousYearAmount ==null}">
														<td class="text-right">0.00</td>
													</c:if>
												</c:otherwise>
											</c:choose>
											<c:set var="totalc1"
												value="${totalc1 + (-data1.currentYearAmount)}" />
											<c:set var="totalp1"
												value="${totalp1 + (-data1.previousYearAmount)}" />
										</tr>
										</c:if>
									</c:forEach>
									<c:forEach items="${command.accountIEDto.listOfIE}" var="data2"
										varStatus="index">
									<c:if test="${(data2.primaryAcHeadId eq '110-90') || (data2.primaryAcHeadId eq '130-90') || (data2.primaryAcHeadId eq '140-90')}">
									<tr>
										<td></td>
										<!-- <td></td> -->
										<td><b>Sub Total  </b></td>
										<td class="text-right text-bold"><fmt:formatNumber
												type="number" value="${totalc-totalc1}" minFractionDigits="2" /></td>
										<td class="text-right text-bold"><fmt:formatNumber
												type="number" value="${totalp-totalp1}" minFractionDigits="2" /></td>
									</tr>
									</c:if>
									</c:forEach>
									<tr>
										<td></td>
										<!-- <td></td> -->
										<td><b>Total ${command.accountIEDto.primaryAcHeadDesc} </b></td>
										<td class="text-right text-bold"><fmt:formatNumber
												type="number" value="${totalc -totalc1}" minFractionDigits="2" /></td>
										<td class="text-right text-bold"><fmt:formatNumber
												type="number" value="${totalp-totalp1}" minFractionDigits="2" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</form:form>
			<div class="text-center hidden-print padding-10">
				<button onClick="printdiv('receipt');"
					class="btn btn-primary hidden-print" title='<spring:message code="account.budget.code.print" text="Print" />'>
					<i class="fa fa-print"></i> <spring:message code="account.budget.code.print" text="Print" />
				</button>
				<button id="btnExport1" type="button"
					class="btn btn-blue-2 hidden-print" title='<spring:message code="account.common.account.downlaod" text="Download" />'>
					<i class="fa fa-file-excel-o"></i><spring:message code="account.common.account.downlaod" text="Download" /> 
				</button>
				<button type="button" class="btn btn-danger" onclick="back();" title='<spring:message code="solid.waste.back" text="Back" />'>
				<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
					<spring:message code="solid.waste.back" text="Back" />
				</button>
			</div>

		</div>
	</div>
</div>
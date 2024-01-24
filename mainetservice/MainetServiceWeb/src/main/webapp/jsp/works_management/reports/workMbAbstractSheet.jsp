<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/workMbApproval.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
					code="work.measurement.book.report.measurement.sheet"
					text="Measurement Book Sheet" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="post" cssClass="form-horizontal">
				<form:hidden path="saveMode" id="saveMode" />
				<div id="receipt">
					<div
						class="col-xs-8 col-xs-offset-1 col-sm-9 col-sm-offset-1  text-center">
						<h3 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<c:if test="${userSession.languageId eq 1}">
								<spring:message code=""
									text="${userSession.organisation.ONlsOrgname}" />
							</c:if>
							<c:if test="${userSession.languageId ne 1}">
								<spring:message code=""
									text="${userSession.organisation.oNlsOrgnameMar}" />
							</c:if>
							<br />
							<spring:message
								code="work.measurement.book.report.measurement.sheet" />
						</h3>
					</div>
					<div class="col-sm-2 col-xs-3">
						<p>
							<spring:message code="work.estimate.report.date" />
							<fmt:formatDate value="<%=new java.util.Date()%>"
								pattern="dd/MM/yyyy" />
							<br>
							<spring:message code="work.estimate.report.time" />
							<fmt:formatDate value="<%=new java.util.Date()%>"
								pattern="hh:mm a" />
						</p>
					</div>
					<div class="clear"></div>
					<div class="col-sm-12" style="margin: 10px;">
						<p>
							<strong><spring:message
									code="work.measurement.book.report.project.name" /> </strong>
							${command.measureMentBookList[0].projName}
						</p>
						<br>
						<p>
							<strong><spring:message
									code="work.measurement.book.report.work.name" /> </strong>
							${command.measureMentBookList[0].workName}
						</p>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="container">
						<c:set var="d" value="0" scope="page"></c:set>
						<table class="table table-bordered  table-fixed"
							id="calculationMb">
							<thead>
								<tr>
									<th scope="col" width="10%" align="center"><spring:message
											code="sor.iteamno" /></th>
									<th scope="col" width="50%" align="center"><spring:message
											code="work.measurement.book.report.description.item" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.measurement.book.report.actual.quantity"
											text="Actual Quantity" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.management.unit" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.measurement.book.report.actual.rate"
											text="Actual Rate" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.measurement.book.report.actual.amount"
											text="Actual Amount" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.measureMentBookList}"
									var="workMBList" varStatus="status">
									<tr class="totalCalculationMb">
										<td id="sordItemNo${d}">${workMBList.sorDIteamNo}</td>
										<td id="sorDesc${d}">${workMBList.sorDDescription}</td>
										<td align="right" id="workActualQuantity${d}">${workMBList.workEstimQuantity}</td>
										<td id="sorUnit${d}">${workMBList.sorIteamUnitDesc}</td>
										<td align="right" id="actualRate${d}">${workMBList.actualRate}</td>
										<td align="right" id="actualAmount${d}">${workMBList.workActualAmt}</td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>

								<tr>
									<th colspan="5" class="text-right"><spring:message
											code="work.measurement.book.report.amount.total" /></th>
									<th colspan="1" class="text-right" id="totalWorkMb"></th>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onClick="printdiv('receipt');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print padding-right-5"></i>
							<spring:message code="work.estimate.report.print" />
						</button>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" onclick="backToApprvForm();"
							id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
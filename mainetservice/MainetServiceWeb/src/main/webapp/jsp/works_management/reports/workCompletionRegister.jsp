<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/reports/workCompletionRegister.js"></script>

<style>
@media print {
	.print-before {
		page-break-after: always;
	}
}
</style>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.WorkCompletionRegister"
					text="Work Completion Register" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="row margin-top-20 clear">
						<div class="col-sm-10 col-xs-10 text-right">
							<p>
								<spring:message code="" text="Date:" />
							</p>
						</div>
						<div class="col-sm-2 col-xs-2 margin-bottom-10">
							<b><fmt:formatDate value="<%=new java.util.Date()%>"
									pattern="dd/MM/yyyy" /> <fmt:formatDate
									value="<%=new java.util.Date()%>" pattern="hh:mm a" /></b>
						</div>
					</div>

					<div class=" col-sm-12 col-xs-12 text-center">
						<h3 class="margin-bottom-0">
							<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
						</h3>
					</div>
					<div class="col-xs-12 col-sm-12 text-center">
						<p class="text-extra-large text-bold">
							<spring:message code="work.Type" />
						</p>

						<p class="text-extra-large text-bold margin-bottom-10">
							<spring:message code="work.completionReport" />
						</p>
					</div>

					<c:forEach items="${command.workDefDtos}" var="dto">
						<div style="page-break-after: always">
							<div class="form-group">
								<label class="col-sm-2 col-xs-2 control-label "><spring:message
										code="work.name" /></label>
								<div class="col-sm-4 col-xs-4">${dto.workName}</div>
								<label class="col-sm-2 col-xs-2 control-label"><spring:message
										code="contractor.name" /></label>
								<div class="col-sm-4 col-xs-4">${dto.contractorname}</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 col-xs-2 control-label "><spring:message
										code="work.place" /></label>
								<div class="col-sm-4 col-xs-4">${dto.locationDesc}</div>

								<label class="col-sm-2 col-xs-2 control-label "><spring:message
										code="work.consumedCost" /></label>
								<div class="col-sm-4 col-xs-4">${dto.workEstAmt}</div>

							</div>

							<div class="form-group">
								<label class="col-sm-2 col-xs-2 control-label "><spring:message
										code="work.startDate" /></label>
								<div class="col-sm-4 col-xs-4">${dto.agreementFromDate}</div>
								<label class="col-sm-2 col-xs-2 control-label"><spring:message
										code="work.EndDate" /></label>
								<div class="col-sm-4 col-xs-4">${dto.agreementToDate}</div>
							</div>

							<div class="clear"></div>

							<div class="form-group">
								<label class="col-sm-2 col-xs-2 control-label"><spring:message
										code="work.measurementbookNo" /></label>
								<div class="col-sm-4 col-xs-4">${dto.raCode}</div>
							</div>
							<div class="clear"></div>
							<table class="table table-bordered table-condensed margin-top-10">
								<tr>
									<th rowspan="2"><spring:message code="work.particulars" /></th>
									<th colspan="3"><spring:message code="work.AsEstimated" /></th>
									<th colspan="3"><spring:message code="work.AsHappened" /></th>
									<th colspan="3"><spring:message code="work.Difference" /></th>
									<th rowspan="2"><spring:message
											code="work.ExplanationOfDifference" /></th>

								</tr>
								<tr>
									<th><spring:message code="work.Quantity" /></th>
									<th><spring:message code="work.rate" /></th>
									<th><spring:message code="work.price" /></th>
									<th><spring:message code="work.Quantity" /></th>
									<th><spring:message code="work.rate" /></th>
									<th><spring:message code="work.price" /></th>
									<th><spring:message code="work.Quantity" /></th>
									<th><spring:message code="work.rate" /></th>
									<th><spring:message code="work.price" /></th>
								</tr>
								<tr>
									<th>(1)</th>
									<th>(2)</th>
									<th>(3)</th>
									<th>(4)</th>
									<th>(5)</th>
									<th>(6)</th>
									<th>(7)</th>
									<th>(8)</th>
									<th>(9)</th>
									<th>(10)</th>
									<th>(11)</th>

								</tr>
								<tbody>
									<c:forEach items="${dto.regDtos}" var="regDto">
										<tr>
											<td>${regDto.sorDDescription}</td>
											<td>${regDto.workEstimQuantity}</td>
											<td>${regDto.sorBasicRate}</td>
											<td>${regDto.workEstAmt}</td>
											<td>${regDto.workActualQty}</td>
											<td>${regDto.sorBasicRate}</td>
											<td>${regDto.workActualAmt}</td>
											<td>${regDto.workEstimQuantity - regDto.workActualQty}</td>
											<td>${regDto.sorBasicRate}</td>
											<td>${(regDto.workEstimQuantity - regDto.workActualQty)* regDto.sorBasicRate}</td>
											<td>&nbsp;</td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
							<br>
						</div>
					</c:forEach>
					<div class="col-sm-12 col-xs-12 margin-top-10">
						<p>
							<spring:message code="work.note" />
						</p>
					</div>
					<div class="col-sm-12 col-xs-12 text-right margin-top-30">
						<p>.............................</p>
						<p>
							<spring:message code="work.officer" />
						</p>
					</div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="printDiv('receipt');"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print padding-right-5"></i>
						<spring:message code="work.estimate.report.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='workCompletionRegister.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Cancel" />
					</button>
				</div>
			</form>
		</div>

	</div>
</div>



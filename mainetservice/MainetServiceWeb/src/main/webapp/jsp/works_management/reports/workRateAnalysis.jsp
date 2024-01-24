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
	src="js/works_management/reports/workEstimateReports.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.estimate.report.rate.analysis" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="post" cssClass="form-horizontal">
				<form:hidden path="saveMode" id="saveMode" />
				<div id="receipt">
					<div class="col-sm-2">
						<strong><spring:message
								code="work.estimate.report.rate.analysis" text="Rate Analysis" /></strong>
					</div>
					<div class="col-sm-10">
						<strong>: ${command.projectMasterDto.projNameEng}</strong>
					</div>
					<div class="col-sm-12 padding-top-5 padding-bottom-5">
						<strong>${command.definitionDto.workName}</strong>
					</div>
					<div class="col-sm-2">
						<p>Item of Work</p>
					</div>
					<div class="col-sm-4">
						<p>:${command.definitionDto.workName}</p>
					</div>
					<div class="col-sm-4 text-right">
						<p>Rate Analysis for</p>
					</div>
					<div class="col-sm-2">
						<p>:10.000 cu.mt</p>
					</div>
					<div class="col-sm-2">
						<p>Description</p>
					</div>
					<div class="col-sm-10">
						<p>:RCC M20 (1:1 5:3) concrete in Ordinary Works</p>
					</div>


					<div class="clearfix padding-10"></div>
					<div class="container">
						<table class="table table-bordered  table-fixed">
							<thead>
								<tr>
									<th scope="col" width="10%" align="center"><spring:message
											code="ser.no" /></th>
									<th scope="col" width="35%" align="center"><spring:message
											code="work.management.description" /></th>
									<th scope="col" width="5%" align="center"><spring:message
											code="work.estimate.quantity" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.management.unit" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.estimate.rate" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.estimate.report.amount" /></th>
								</tr>

								<tr>
									<th scope="col" colspan="6"><spring:message
											code="work.estimate.report.material" /></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<th colspan="5" class="text-right"><spring:message
											code="work.estimate.report.total.material" /></th>
									<th>&nbsp;</th>
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
							name="button-Cancel" onclick="backReportForm();"
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
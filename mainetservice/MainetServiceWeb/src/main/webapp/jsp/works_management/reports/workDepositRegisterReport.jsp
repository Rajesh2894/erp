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
<script type="text/javascript"
	src="js/works_management/reports/workDepositRegisterReport.js"></script>
<script type="text/javascript">
	
</script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.deposit.refund.report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">

					<div class="row margin-top-20 clear">
						<div class="col-sm-10 col-xs-10 text-right">
							<p>
								<spring:message code="work.estimate.report.date" text="Date:" />
							</p>
						</div>
						<div class="margin-bottom-10">
							<b><fmt:formatDate value="<%=new java.util.Date()%>"
									pattern="dd/MM/yyyy" /> <fmt:formatDate
									value="<%=new java.util.Date()%>" pattern="hh:mm a" /></b>
						</div>
					</div>

					<div class=" col-sm-12 col-xs-12 text-center">
						<h3 class="margin-bottom-0">
							<c:if test="${userSession.languageId eq 1}">
								<spring:message code=""
									text="${userSession.organisation.ONlsOrgname}" />
							</c:if>
							<c:if test="${userSession.languageId ne 1}">
								<spring:message code=""
									text="${userSession.organisation.oNlsOrgnameMar}" />
							</c:if>
						</h3>
						<%-- <p>
							<spring:message code=""
								text="${userSession.organisation.orgAddress}" />

						</p> --%>
					</div>

					<div class="col-xs-12 col-sm-12 text-center">
						<h3 class="margin-bottom-0 margin-top-0 padding-top-30 text-bold">
							<spring:message code="work.deposit.refund.report" text="" />
						</h3>
					</div>

					<div class="row margin-top-20 clear">
						<div class="col-sm-6 col-xs-6 padding-top-30 text-right">
							<p>
								<spring:message code="work.deduction.fromdate" text="" />
								<b> ${command.fromDate} </b>
							</p>

						</div>
						<div class="col-sm-2 col-xs-2 padding-top-30 text-right">
							<p>
								<spring:message code="work.deduction.todate" text="" />
								<b> ${command.toDate} </b>
							</p>

						</div>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="container">
						<table class="table table-bordered  table-fixed" id="calculation">
							<thead>
								<tr>

									<th scope="col" width="8%" align="center"><spring:message
											code="tender.vendorname" text="Vendor Contractor Name" /></th>
									<th scope="col" width="9%" align="center"><spring:message
											code="project.master.projname" text="Project Name" /></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="project.master.startdate" text="Project Start Date" /></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="project.master.enddate" text="Project End Date" /></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="work.def.workCode" text="Work Code" /></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="work.def.workname" text="Work Name" /></th>

									<!-- REMOVE AS PER SUDA UAT -->

									<%-- <th scope="col" width="7%" align="center"><spring:message code="work.def.startDate" text=""/></th>
								<th scope="col" width="7%" align="center"><spring:message code="work.def.endDate" text=""/></th> --%>
									<th scope="col" width="9%" align="center"><spring:message
											code="work.status" text="Work Status" /></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="work.deposit.type" text="Deposit Type" /></th>
									<th scope="col" width="6%" align="center"><spring:message
											code="work.deposit.date" text="Deposit Date" /></th>
									<th scope="col" width="6%" align="center"><spring:message
											code="work.deposit.amount" text="Deposit Amount" /></th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${command.worksDepositRegisterReportDto}"
									var="dto" varStatus="status1">
									<tr>

										<td>${dto.vmVendorname}</td>
										<td>${dto.projNameEng}</td>
										<%-- <td>${dto.projNameReg}</td> --%>
										<td class="text-center">${dto.projStartDateDesc}</td>
										<td class="text-center">${dto.projEndDateDesc}</td>
										<td class="text-center">${dto.workcode}</td>
										<td>${dto.workName}</td>

										<!-- REMOVE AS PER SUDA UAT -->

										<%-- <td>${dto.workStartDateDesc}</td>
									<td>${dto.workEndDateDesc}</td> --%>
										<td class="text-center">${dto.workStatus}</td>
										<td class="text-center">${dto.cpdDesc}</td>
										<%-- <td>${dto.cpdDescMar}</td>  --%>
										<td class="text-center">${dto.depDateDesc}</td>
										<td class="text-right">${dto.depAmount}</td>
									</tr>
								</c:forEach>
								<tr>
									<th colspan="9" class="text-right"><spring:message
											code="work.estimate.report.amount.total" /></th>
									<th colspan="1" class="text-center" id="" >${command.totalValue}</th>
									
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
			</form>
		</div>
	</div>
</div>

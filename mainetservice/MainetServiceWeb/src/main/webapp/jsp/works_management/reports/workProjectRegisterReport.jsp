<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/works_management/reports/workProjectRegisterReport.js"></script>
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
				<spring:message code="work.project.status.report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">

					<div class="row margin-top-20 clear">
						<div class="col-sm-10 text-right">
							<p>
								<spring:message code="work.estimate.report.date" text="Date:" />
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
							<spring:message code="work.project.status.report" text="" />
						</h3>
					</div>
					<div class="clearfix padding-20"></div>
					<div class="">
						<table class="table table-striped table-condensed table-bordered" id="proRegTbl">
						<!-- <table class="table table-bordered  table-fixed"> -->
							<thead>
								<tr>
									<c:if
										test="${command.projectStatusReportDetDto[0].wmSchNameEng ne null}">
										<th width="8%"><spring:message
												code="project.master.schemename" text="Scheme Name" /></th>
									</c:if>
									<th scope="col" width="10%" align="center"><spring:message
											code="project.master.projname" text="Project Name" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.def.workType" text="Type of Work" /></th>
									<th scope="col" width="5%" align="center"><spring:message
											code="work.def.workname" text="Work Name" /></th>
									<th scope="col" width="8%" align="center"><spring:message
											code="tender.estimated.number" text="Estimated Number" /></th>
									<th scope="col" width="8%" align="center"><spring:message
											code="tender.estimated.amount" text="Estimated Amount" /></th>
									<th scope="col" width="8%" align="center"><spring:message
											code="work.sanction.tender.amt"
											text="Sanctioned Tender Amount" /></th>
									<th scope="col" width="6%" align="center"><spring:message
											code="work.estimate.status" text="Status" /></th>
									<th scope="col" width="9%" align="center"><spring:message
											code="work.bill.received.date" text="Bill received till Date" /></th>
									<th scope="col" width="9%" align="center"><spring:message
											code="work.payment.tilldate" text="Payment received till date" /></th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${command.projectStatusReportDetDto}"
									var="dto" varStatus="status1">
									<tr>


										<c:if test="${dto.wmSchNameEng ne null}">
											<td class="text-center">${dto.wmSchNameEng}</td>
										</c:if>

										<%-- <td>${dto.wmSchNameEng}</td> --%>
										<td><c:choose>
											<c:when test="${userSession.languageId eq 1}">
											   ${dto.projNameEng}
									
									       </c:when>
										   <c:otherwise>
									          ${dto.projNameReg}
									   </c:otherwise>

									  </c:choose></td>
										<td class="text-center">${dto.workTypeDesc}</td>
										<td>${dto.workName}</td>
										<td class="text-center">${dto.workeEstimateNo}</td>
										<td align="right">${dto.workEstimAmount}</td>
										<td align="right">${dto.tenderAmount}</td>
										<td>
										<c:choose>
										<c:when test="${dto.status eq 'Tender Generated'}">
										<spring:message code="status.tender" text="Tender Generated" />
										</c:when>
										<c:when test="${dto.status eq 'Technical Sanction Approved'}">
										<spring:message code="status.sanction" text="Technical Sanction Approved" />
										</c:when>
										
										<c:when test="${dto.status eq 'Pending'}">
										<spring:message code="status.pending" text="Pending" />
										</c:when>
										<c:when test="${dto.status eq 'Draft'}">
										<spring:message code="status.draft" text="Draft" />
										</c:when>
										<c:when test="${dto.status eq 'Approved'}">
										<spring:message code="status.approved" text="Approved" />
										</c:when>
										<c:otherwise>
										<spring:message code="status.completed" text="Completed" />
										</c:otherwise>
										</c:choose>
										</td>
										<td class="text-center">${dto.billReceivedDateDesc}</td>
										<td class="text-center">${dto.paymentisDoneTillDateDesc}</td>
									</tr>
								</c:forEach>

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

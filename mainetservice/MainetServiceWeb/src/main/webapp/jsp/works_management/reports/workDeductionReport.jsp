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
<script type="text/javascript"
	src="js/works_management/reports/workDeductionReport.js"></script>
<script type="text/javascript">
$(document).ready(function() {
$("#deductionTbl").dataTable({
	"oLanguage" : {
		"sSearch" : ""
	},
	"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	"iDisplayLength" : 5,
	"bInfo" : true,
	"lengthChange" : true
});
});
</script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.deduction.report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">

					<div class="row margin-top-5 clear">
						<div class="col-sm-12 col-xs-12 text-right">
							<p>
								<spring:message code="work.estimate.report.date" text="Date:" />				
							<b><fmt:formatDate value="<%=new java.util.Date()%>"
									pattern="dd/MM/yyyy" /> <fmt:formatDate
									value="<%=new java.util.Date()%>" pattern="hh:mm a" /></b>
									</p>
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
					<!-- </div> -->

					<!-- <div class="col-xs-12 col-sm-12 text-center"> -->
						<h3 class="margin-bottom-0 margin-top-0 padding-top-10 text-bold">
							<spring:message code="work.deduction.report" text="" />
						</h3>
					<!-- </div> -->

					<!-- <div class="row margin-top-20 clear">

						 <div class="col-sm-6 col-xs-6 padding-top-30 text-right"> -->

							<p class="margin-bottom-0 margin-top-0 padding-top-10">
								<spring:message code="work.deduction.fromdate" text="" />
								<b> ${command.fromDate} </b>
            <!-- 	</p>
						</div>
						<div class="col-sm-2 col-xs-2 padding-top-30 text-right">
							<p> -->
								&nbsp;&nbsp;<spring:message code="work.deduction.todate" text="" />
								<b> ${command.toDate} </b>
							</p>

						<!-- </div> 
					</div> -->
					</div>

					<div class="clearfix padding-5"></div>
					<div class="container">
						<!-- <table class="table table-bordered  table-fixed"> -->
						<div class="">
						<table class="table table-striped table-condensed table-bordered" id="deductionTbl">
								<thead>
									<tr>

										<th scope="col" width="9%" align="center"><spring:message
												code="project.master.projname" text="Project Name" /></th>
										<%-- <th scope="col" width="7%" align="center"><spring:message
											code="project.master.startdate" text="Project Start Date" /></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="project.master.enddate" text="Project End Date" /></th> --%>
										<th scope="col" width="7%" align="center"><spring:message
												code="work.def.workCode" text="Work Code" /></th>
										<th scope="col" width="7%" align="center"><spring:message
												code="work.def.workname" text="Work Name" /></th>
										<th scope="col" width="8%" align="center"><spring:message
												code="tender.vendorname" text="Vendor Contractor Name" /></th>
										<%-- <th scope="col" width="7%" align="center"><spring:message
											code="work.def.startDate" text="" /></th>
									<th scope="col" width="7%" align="center"><spring:message
											code="work.def.endDate" text="" /></th> --%>
										<th scope="col" width="7%" align="center"><spring:message
												code="work.deduction.raBill" text="RA Bill No." /></th>
										<th scope="col" width="7%" align="center"><spring:message
												code="work.deduction.date" text="RA Bill Date" /></th>
										<th scope="col" width="9%" align="center"><spring:message
												code="work.deduction.taxname" text="Deduction Description" /></th>
										<th scope="col" width="6%" align="center"><spring:message
												code="work.deducted.amt" text="Deduction Amount" /></th>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${command.worksDeductionReportDto}" var="dto"
										varStatus="status1">
										<tr>
											<td><c:choose>
											<c:when test="${userSession.languageId eq 1}">
											   ${dto.projNameEng}
									
									       </c:when>
										   <c:otherwise>
									          ${dto.projNameReg}
									   </c:otherwise>

									  </c:choose></td>

											<%-- <td>${dto.projNameReg}</td> --%>
											<%-- <td>${dto.projStartDate}</td>
										<td>${dto.projEndDate}</td> --%>
											<td class="text-center">${dto.workcode}</td>
											<td>${dto.workName}</td>
											<td>${dto.vmVendorname}</td>
											<%-- <td>${dto.contractFromDateDesc}</td>
										<td>${dto.contractToDateDesc}</td> --%>
											<td class="text-center">${dto.raCode}</td>
											<td class="text-center">${dto.bmDateDesc}</td>
											<td>${dto.taxDesc}</td>
											<td align="right">${dto.mbTaxValue}</td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
						</div>
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

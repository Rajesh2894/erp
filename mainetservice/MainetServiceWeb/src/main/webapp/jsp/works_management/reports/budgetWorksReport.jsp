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
<script type="text/javascript"
	src="js/works_management/reports/worksBudgetReport.js"></script>
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
				<spring:message code="work.budget.watch.report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="row margin-top-20 clear">
						<div class="col-sm-10 col-xs-10 text-right padding-right-5">
							<p>
								<spring:message code="work.estimate.report.date" text="Date:" />
							</p>
						</div>
						<div class="col-sm-2 col-xs-2 padding-left-0">
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
                      <!--    Defect #92937 -->
					<!-- <div class="col-xs-12 col-sm-12 text-center"> -->
						<h3 class="margin-bottom-0 margin-top-0 padding-top-20 text-bold">
							<spring:message code="work.budget.watch.report" text="" />
						</h3>
			
				<!-- 	<div class="row margin-top-20 clear"> 
                      <div class="col-sm-6 col-xs-6 padding-top-30 text-right"> -->

						<p class="margin-bottom-0 margin-top-0 padding-top-20">
							<spring:message code="work.deduction.fromdate" text="" />
							<b> ${command.fromDate} </b>

							<!-- </div> 
						<div class="col-sm-2 col-xs-2 padding-top-30 text-right"> -->
							&nbsp;&nbsp;
							<spring:message code="work.deduction.todate" text="" />
							<b> ${command.toDate} </b>
						</p>

						<!-- </div> 
					</div>
					 </div> --> 
        </div>
				<div class="clearfix padding-20"></div>
					<div class="">
						<!-- <table class="table table-bordered" id="calculation"> -->
						<table class="table table-striped table-condensed table-bordered" id="calculation">
							<thead>
								<tr>

									<th scope="col" width="8%" align="center"><spring:message
											code="work.proj.code" text="WMS Project Code" /></th>
									<th scope="col" width="15%" align="center"><spring:message
											code="project.master.projname" text="Project Name" /></th>
									<th scope="col" width="8%" align="center"><spring:message
											code="project.master.startdate" text="Project Start Date" /></th>
									<th scope="col" width="8%" align="center"><spring:message
											code="project.master.enddate" text="Project End Date" /></th>
									<th scope="col" width="8%" align="center"><spring:message
											code="work.def.workCode" text="Work Code" /></th>
									<th scope="col" width="13%" align="center"><spring:message
											code="work.def.workname" text="Work Name" /></th>
									<%-- <th scope="col" width="8%" align="center"><spring:message code="work.def.startDate" text=""/></th>
								<th scope="col" width="8%" align="center"><spring:message code="work.def.endDate" text=""/></th> --%>
									<th scope="col" width="9%" align="center"><spring:message
											code="work.budget.head" text="Budget Heads" /></th>
									<th scope="col" width="5%" align="center"><spring:message
											code="work.approved.amount" text="Approved Amount" /></th>
									<th scope="col" width="9%" align="center"><spring:message
											code="work.expenses.approved"
											text="Actual Expenses (Approved)" /></th>
									<th scope="col" width="5%" align="center"><spring:message
											code="work.expenses.under.approval"
											text="Actual Expenses (Under Approval)" /></th>
									<th scope="col" width="5%" align="center"><spring:message
											code="work.budget.balance" text="Budget Balance(Amt.)" /></th>
									<th scope="col" width="5%" align="center" class="amount"><spring:message
											code="work.budget.balance1" text="Budget Balance(%)" /></th>


								</tr>
							</thead>
							<tbody>
								<c:set var="d" value="0" scope="page" />
								<c:forEach items="${command.worksBudgetReportDto}" var="dto"
									varStatus="status1">
									<tr>
										<td id="projCode${d}">${dto.projCode}</td>
										<td id="projNameEng${d}">${dto.projNameEng}</td>
										<td id="projStartDateDesc${d}">${dto.projStartDateDesc}</td>
										<td id="projEndDateDesc${d}">${dto.projEndDateDesc}</td>
										<td id="workcode${d}">${dto.workcode}</td>
										<td id="workName${d}">${dto.workName}</td>

										<%-- <td id="workStartDateDesc${d}">${dto.workStartDateDesc}</td>
									<td id="workEndDateDesc${d}">${dto.workEndDateDesc}</td> --%>
										<td id="acHeadCode${d}">${dto.acHeadCode}</td>										
										<td id="contAmount${d}" align="right">${dto.contAmount}</td>
										<%-- <td id="contAmount${d}" align="right">${dto.contAmount}</td> --%>
										<td id="mbTotalAmt${d}" align="right">${dto.mbTotalAmt}</td>
										<td id="mbTotalAmtUnderApproval${d}"align="right">${dto.mbTotalAmtUnderApproval}</td>
										<td id="budgetBalanceAmt${d}" align="right">${dto.budgetBalanceAmt}</td>
										<td id="budgetBalancePerc${d}" align="right">${dto.budgetBalancePerc}</td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</tbody>
						</table>
						<!-- </table> -->
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

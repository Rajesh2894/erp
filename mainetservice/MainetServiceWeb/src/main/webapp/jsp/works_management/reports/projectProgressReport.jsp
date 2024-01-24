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
<script src="js/works_management/reports/projectProgressReport.js"
	type="text/javascript"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript">
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">Project Progress Report</h2>
				<%-- <spring:message code="project.progress"
						text="Project Progress Report" /> --%>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<h2 class="excel-title" style="display: none">Project Progress Report</h2>
						<%-- <spring:message code="project.progress"
								text="Project Progress Report" /> --%>

						<div class="row margin-top-20 clear">
							<div class="col-sm-12 col-xs-12 text-right">
								<p>
									<spring:message code="work.estimate.report.date" text="Date:" />
									<b><fmt:formatDate value="<%=new java.util.Date()%>"
											pattern="dd/MM/yyyy" /> <fmt:formatDate
											value="<%=new java.util.Date()%>" pattern="hh:mm a" /></b>
								</p>
							</div>
						</div>
						<div class="form-group padding-bottom-10">
							<div class="col-xs-12 text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgname}" />
									</c:if>
									<c:if test="${userSession.languageId ne 1}">
										<spring:message code=""
											text="${userSession.organisation.oNlsOrgnameMar}" />
									</c:if>
								</h3>
								<strong> <spring:message code="project.progress"
										text="Project Progress Report" />
								</strong>

								<p>
									<strong><spring:message
											code="leadlift.master.fromDate" text="From Date" /></strong> :
									${command.fromDate}

									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<strong><spring:message code="leadlift.master.toDate"
											text="To Date" /></strong> : ${command.toDate}

								</p>


							</div>
							<%-- <div class="form-group col-sm-12">
								<p>
									<strong><spring:message code="" text="From Date" /></strong> :
									${fromDate}

								</p>
								<p>
									<strong><spring:message code="" text="To Date" /></strong> :
									${toDate}

								</p>
							</div> --%>
						</div>
						<div class="padding-5 clear">&nbsp;</div>
						<!-- <div class="overflow-visible"> -->
							<div id="export-excel">
								<table class="table table-bordered table-condensed">

									<thead>
										<tr>

											<th><spring:message code="project.master.projname" /></th>
											<th><spring:message code="project.master.startdate" /></th>
											<th><spring:message code="project.master.enddate" /></th>
											<th><spring:message code="work.def.workCode" /></th>
											<th><spring:message code="work.def.workname" /></th>

											<!-- REMOVE AS PER SUDA UAT -->

											<%-- <th><spring:message code="work.def.startDate" /></th>
											<th><spring:message code="work.def.endDate" /></th> --%>
											<th><spring:message code="mileStone.title" /></th>
											<th><spring:message code="mileStone.weightage" /></th>
											<th><spring:message code="mileStone.percentComplete" /></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th colspan="11" class="ts-pager form-horizontal">
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
													<option selected="selected" value="10" class="form-control">10</option>
													<option value="20">20</option>
													<option value="30">30</option>
													<option value="all">All Records</option>
											</select> <select class="pagenum input-mini form-control"
												title="Select page number"></select>
											</th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach items="${command.projectProgressDto}"
											var="projectProgressList">

											<tr>
												<td rowspan="${projectProgressList.mileStoneDTO.size()+1}">${projectProgressList.projNameEng}</td>
												<td class="text-center" rowspan="${projectProgressList.mileStoneDTO.size()+1}">${projectProgressList.projStartDate}</td>
												<td class="text-center" rowspan="${projectProgressList.mileStoneDTO.size()+1}">${projectProgressList.projEndDate}</td>
												<td class="text-center" rowspan="${projectProgressList.mileStoneDTO.size()+1}">${projectProgressList.workcode}</td>
												<td rowspan="${projectProgressList.mileStoneDTO.size()+1}">${projectProgressList.workName}</td>
												<%-- td rowspan="${projectProgressList.mileStoneDTO.size()+1}">${projectProgressList.workStartDate}</td>
												<td rowspan="${projectProgressList.mileStoneDTO.size()+1}">${projectProgressList.workEndDate}</td> --%>
												<c:forEach items="${projectProgressList.mileStoneDTO}"
													var="mileStoneDTOList">
													<tr>
														<td align="right">${mileStoneDTOList.mileStoneDesc}</td>
														<td align="right">${mileStoneDTOList.mileStoneWeight}</td>
														<td align="right">${mileStoneDTOList.msPercent}</td>
													</tr>
												</c:forEach>
											</tr>

										</c:forEach>

									</tbody>
								</table>
							</div>
						<!-- </div> -->
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('Project Progress Report');"
							class="btn btn-primary hidden-print" type="button">
							<i class="fa fa-print padding-right-5"></i>
							<spring:message code="work.estimate.report.print" text="" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='ProjectProgressReport.html'">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="Back" />
						</button>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>
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
				<spring:message code="work.estimate.report.measurement.sheet" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">

					<table width="100%">
						<tr>
					
 							<td width="25%"><span class="text-center"><spring:message
										code="work.estimate.report.remabs" text="" /></span></td>
							<td width="50%"><h3
									class="text-large text-center margin-bottom-0 margin-top-0 text-bold">
									<c:if test="${userSession.languageId eq 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgname}" />
									</c:if>
									<c:if test="${userSession.languageId ne 1}">
										<spring:message code=""
											text="${userSession.organisation.oNlsOrgnameMar}" />
									</c:if>
									<br />
									<spring:message code="work.estimate.report.measurement.sheet" />
								</h3></td>
							<td width="25%"><span class="text-center"><spring:message
										code="work.estimate.report.date" /> <fmt:formatDate
										value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /> <br>
									<spring:message code="work.estimate.report.time" /> <fmt:formatDate
										value="<%=new java.util.Date()%>" pattern="hh:mm a" /></span></td>
						</tr>
					</table>

					<div class="col-sm-12" style="clear: both">
						<span> <strong><spring:message
									code="work.estimate.report.project.name" /></strong>
							<c:choose>
									<c:when test="${userSession.languageId eq 1}">
										${command.projectMasterDto.projNameEng}
									</c:when>
							        <c:otherwise>
							        	${command.projectMasterDto.projNameReg}
							        </c:otherwise>
						
								</c:choose>
						</span> <br> <span> <strong><spring:message
									code="work.estimate.report.work.name" /></strong>
							${command.definitionDto.workName}
						</span>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="container">
						<table class="table table-bordered  table-fixed">
							<thead>
								<tr>
									<th scope="col" width="6%" align="center"><spring:message
											code="sor.iteamno" text="Item No." /></th>
									<th scope="col" width="29%" align="center"><spring:message
											code="work.management.description" /></th>
									<th scope="col" width="6%" align="center"><spring:message
											code="work.estimate.report.calculate.type" text="Calc Type" /></th>
									<th scope="col" width="5%" align="center"><spring:message
											code="work.estimate.no" text="No." /></th>

									<th scope="col" width="8%" align="center"><spring:message
											code="work.estimate.report.length" text="Length" /></th>
									<th scope="col" width="8%" align="center"><spring:message
											code="work.estimate.report.breadth" text="Breadth" /></th>
									<th scope="col" width="8%" align="center"><spring:message
											code="work.estimate.report.depth.breadth"
											text="Depth/Breadth" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.measurement.sheet.details.formula" text="Formula" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.estimate.quantity" text="Quantity" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="work.estimate.report.total.quantity"
											text="Total Quantity" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.workMasterDtosList}" var="dtoList"
									varStatus="status">
									<c:forEach items="${dtoList.workMeasurementDto}" var="dto"
										varStatus="status1">
										<tr>
											<c:if test="${status1.index eq 0}">
												<td rowspan="${dtoList.workMeasurementDto.size()}" class="text-center">${dtoList.sorDIteamNo}</td>
												<td rowspan="${dtoList.workMeasurementDto.size()}">${dtoList.sorDDescription}</td>
											</c:if>
											<td><c:choose>
													<c:when test="${dto.meMentType eq 'Direct'}">
														<spring:message code="status.direct" text="Direct" />
													</c:when>
												</c:choose></td>
											<td align="right" class="text-center">${dto.meMentNumber}</td>
											<td align="right">${dto.meMentLength}</td>
											<td align="right">${dto.meMentBreadth}</td>
											<td align="right">${dto.meMentHeight}</td>
											<td align="center">${dto.meMentFormula}</td>
											<td align="right">${dto.meValue}</td>
											<td align="right">${dto.meMentToltal}</td>
										</tr>
									</c:forEach>
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

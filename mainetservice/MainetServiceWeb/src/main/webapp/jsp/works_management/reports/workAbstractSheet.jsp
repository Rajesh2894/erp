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
				<spring:message code="work.estimate.report.abstract.sheet" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="post" cssClass="form-horizontal">
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="amount" id="amount" />
				<div id="receipt">

					<table width="100%">
						<tr>
							<td width="25%"><span class="text-center"></span></td>
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
									<spring:message code="work.estimate.report.abstract.sheet" />
								</h3></td>
							<td width="25%"><span class="text-center"><spring:message
										code="work.estimate.report.date" /> <fmt:formatDate
										value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /> <br>
									<spring:message code="work.estimate.report.time" /> <fmt:formatDate
										value="<%=new java.util.Date()%>" pattern="hh:mm a" /></span></td>
						</tr>
					</table>
					        <div class="col-sm-12" style="clear: both">
					      <span><b>Department Name :</b><%-- <c:out value=" ${command.departName}"></c:out> --%>
					      <c:choose>
									<c:when test="${userSession.languageId eq 1}">
										${command.departName}
									</c:when>
							        <c:otherwise>
							        	${command.departNameReg}
							        </c:otherwise>
						
								</c:choose>
					      
					      </span><br>
						  <span> <strong><spring:message
									code="work.estimate.report.project.name" /> </strong>
										
								<c:choose>
									<c:when test="${userSession.languageId eq 1}">
										${command.projectMasterDto.projNameEng}
									</c:when>
							        <c:otherwise>
							        	${command.projectMasterDto.projNameReg}
							        </c:otherwise>
						
								</c:choose>
							
						 </span> <br> 
						<span> <strong><spring:message
									code="work.estimate.report.work.name" /> </strong>
							${command.definitionDto.workName}
						</span><br>
						<span><b>Work Type :</b><c:out value=" ${command.definitionDto.workTypeDesc}"></c:out></span><br>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="container table-responsive">
						<c:set var="d" value="0" scope="page"></c:set>
						<table class="table table-bordered  table-fixed" id="calculation">
							<thead>
								
								<tr>
									<th scope="col" align="center"><spring:message
											code="sor.iteamno" /></th>
									<th scope="col" align="center"><spring:message
											code="work.estimate.report.description.item" /></th>
									<th scope="col" align="center"><spring:message
											code="work.estimate.quantity" /></th>
									<th scope="col" align="center"><spring:message
											code="work.management.unit" /></th>
									<th scope="col" align="center"><spring:message
											code="work.estimate.rate" /></th>
									<th scope="col" align="center"><spring:message
											code="work.estimate.report.amount" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.workMasterDtosList}"
									var="workEstimateList" varStatus="status">
									<tr class="totalCalculation">
										<td id="sordItemNo${d}" class="text-center">${workEstimateList.sorDIteamNo}</td>
										<td id="sorDesc${d}">${workEstimateList.sorDDescription}</td>
										<td id="workQuantity${d}" align="right">${workEstimateList.workEstimQuantity}</td>
										<td id="sorUnit${d}" align="center ">${workEstimateList.sorIteamUnitDesc}</td>
										<td id="rate${d}" align="right">${workEstimateList.rate}</td>
										<td id="amount${d}" align="right">${workEstimateList.workEstimAmount}</td>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>

								<tr>
									<th colspan="5" class="text-right"><spring:message
											code="work.estimate.report.amount.total" /></th>
									<th colspan="1" class="text-right" id="totalEstimate"></th>
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
						<c:choose>
							<c:when test="${command.saveMode eq 'AP'}">
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" onclick="backReportToApprovalForm();"
									id="button-Cancel">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="works.management.back" text="" />
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" onclick="backReportForm();"
									id="button-Cancel">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="works.management.back" text="" />
								</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
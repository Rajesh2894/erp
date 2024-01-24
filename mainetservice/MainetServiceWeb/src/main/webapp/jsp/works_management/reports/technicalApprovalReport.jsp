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
	src="js/works_management/reports/approvalLetterPrint.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget invoice" id="loa">
		<div class="widget-content padding">
			<form:form action="ApprovalLetterPrint.html" class="form-horizontal"
				name="ApprovalLetterPrint" id="ApprovalLetterPrint"
				modelAttribute="command">
				<form:hidden path="saveMode" id="saveMode" />
				<div id="receipt">
					<div class="row">
						<div class="col-xs-12 text-center">
							<h3 class="margin-bottom-0">
								<%-- <spring:message code="techincalapproval.content1" text="" /> --%>
								
								<c:if test="${userSession.getCurrent().getLanguageId() eq 1 }">
								${userSession.getCurrent().organisation.ONlsOrgname}</c:if>
								<c:if test="${userSession.getCurrent().getLanguageId() ne 1 }">
								${userSession.getCurrent().organisation.oNlsOrgnameMar}</c:if>
							</h3>
						</div>
					</div>

					<div class="row margin-top-20 clear">
						<div class=" col-sm-2 col-xs-3">

							<c:set var="number"
								value="${command.definitionDto.sanctionNumber}" />

							<c:if test="${fn:startsWith(number , 'T')}">
								<p>
									<spring:message code="technicalapproval.content2" text=":" />
								</p>
							</c:if>


							<c:if test="${fn:startsWith(number , 'A')}">
								<p>
									<spring:message code="technicalapproval.content17" text=":" />
								</p>
							</c:if>
						</div>

						<div class="col-sm-6 col-xs-5 text-left">
							<b><c:forEach
									items="${command.definitionDto.sanctionDetails}" var="lookUp">
									<p>${lookUp.workSancNo}
								</c:forEach> </b>
						</div>

						<div class="col-sm-1 col-xs-2">
							<p>
								<spring:message code="technicalapproval.content3" text="" />
							</p>
						</div>
						<div class="col-sm-3 col-xs-2 text-left">
							<b><p>${command.definitionDto.worksancDateDesc}</p></b>
						</div>
					</div>


					<div class="row margin-top-30 clear">
						<div class=" col-sm-12">
							<p>
								<c:if test="${userSession.getCurrent().getLanguageId() eq 1 }">
								${userSession.getCurrent().organisation.ONlsOrgname}</c:if>
								<c:if test="${userSession.getCurrent().getLanguageId() ne 1 }">
								${userSession.getCurrent().organisation.oNlsOrgnameMar}</c:if>
								<spring:message code="technicalapproval.content5" />
								${command.definitionDto.workName}
								<spring:message code="technicalapproval.content6" />${command.definitionDto.workEstAmt}
								<spring:message code="technicalapproval.content7" />

							</p>

							<c:if test="${not empty command.scheduleOfRateMstDto.sorName}">
								<p class="margin-top-20">
									<spring:message code="technicalapproval.content8" />
									${command.deptName}
									<spring:message code="technicalapproval.content16" />
									${command.scheduleOfRateMstDto.sorName}
									<spring:message code="technicalapproval.content9" />
									<spring:message code="technicalapproval.content10" />${command.definitionDto.workEstAmt}
									<spring:message code="technicalapproval.content11" />

								</p>
							</c:if>

							<c:if test="${empty command.scheduleOfRateMstDto.sorName}">
								<p class="margin-top-20">
									<spring:message code="technicalapproval.content8" />
									${command.deptName}
									<%--  <spring:message code="technicalapproval.content16" /> --%>
									<%--  ${command.scheduleOfRateMstDto.sorName} --%>
									<spring:message code="technicalapproval.content18" />
									<spring:message code="technicalapproval.content10" />${command.definitionDto.workEstAmt}
									<spring:message code="technicalapproval.content11" />

								</p>
							</c:if>


							<p class="margin-top-20">
								<spring:message code="technicalapproval.content12" />
								${command.projectMasterDto.rsoNumber}
								<spring:message code="technicalapproval.content13" />
								${command.projectMasterDto.orderDateDesc}
								<c:if test="${userSession.getCurrent().getLanguageId() eq 1 }">
								${userSession.getCurrent().organisation.ONlsOrgname}</c:if>
								<c:if test="${userSession.getCurrent().getLanguageId() ne 1 }">
								${userSession.getCurrent().organisation.oNlsOrgnameMar}</c:if>
								<spring:message code="technicalapproval.content5" />
								${command.definitionDto.workName}
								<spring:message code="technicalapproval.content6" />
								${command.definitionDto.workEstAmt} (${command.workEstimAmt})
								<spring:message code="technicalapproval.content15" />
								<strong>:-</strong>

							</p>

						</div>
					</div>

					<div class="row margin-top-30 clear">

						<p>
						<div class="col-sm-6 col-xs-6">

							<p>
								<c:forEach items="${command.approvalTermsConditionDto}"
									var="lookup" varStatus="status">
									<%-- <li>${status.count}</li>  --%>
									<span>${status.count + 0}. ${lookup.termDesc}</span><br>
								</c:forEach>
							</p>
						</div>
						</p>
					</div>
					<div class="text-center clear padding-10">
						<button type="button"
							class="button-input btn btn-danger hidden-print"
							name="button-Cancel" value="Cancel" style=""
							onclick="backReportForm();" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>

						<button onClick="printdiv('receipt');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print padding-right-5"></i>
							<spring:message code="work.estimate.report.print" />
						</button>

					</div>
				</div>
			</form:form>

		</div>
	</div>
</div>


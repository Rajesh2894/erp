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
	src="js/legal/summaryCasePendencyReportDivisionWise.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
					code="legal.summary.Case.Pendency.Report.Division.Wise"
					text=" Summary Case Pendency Report Division Wise" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="legal.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="SummaryCasePendencyReport.html"
				cssClass="form-horizontal" id="SummaryCaseFormReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.summary.Case.Pendency.division" text="Division Wise" /></label>
					<c:set var="baseLookupCode" value="DVN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReport.cseDivWise" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Division Wise" />


					<%-- <label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.summary.Case.Pendency.court " text="Court Type" /></label>
					<c:set var="baseLookupCode" value="CTP" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReport.crtType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Court Type" />

				</div> --%>



				
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="legal.summary.Case.Pendency.court" text="Court Name" /></label>
					<div class="col-sm-4">
						<form:select path="courtMasterDTO.id"
							cssClass="form-control chosen-select-no-results"
							class=" form-control mandColorClass" id="crtId"
							data-rule-required="true">
							<form:option value="0"><spring:message code="applicantinfo.label.select"  text="Select"/></form:option>
							<c:forEach items="${command.courtMasterDTOList}"
								var="courtMasterDTO">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtName}</form:option>
									</c:otherwise>
								</c:choose>
								
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveForm(this)">
						<spring:message code="legal.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm"
					onclick="window.location.href='SummaryCasePendencyReport.html'">
						<spring:message code="legal.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="legal.btn.back"></spring:message>
					</a>
				</div>

			</form:form>
		</div>
	</div>
</div>
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="js/legal/summaryCasePendencyReportDivisionWise.js"></script>
<script src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="legal.report.genderwise.advocate.list"
					text="Gender Wise Advocate List" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="legal.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="SummaryCasePendencyReport.html"
				cssClass="form-horizontal" id="advListGenderWise">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message code="lgl.dg.lSex"
							text="Gender" /></label>
					<c:set var="baseLookupCode" value="GEN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReport.gender" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Gender" />


					<label class="col-sm-2 control-label required-control"> <spring:message
							code="legal.summary.Case.Pendency.court" text="Court Name" /></label>
					<div class="col-sm-4">
						<form:select path="courtMasterDTO.id"
							cssClass="form-control chosen-select-no-results"
							class=" form-control mandColorClass" id="crtId"
							data-rule-required="true">
							<form:option value="0">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.courtMasterDTOList}"
								var="courtMasterDTO">
								<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtName}</form:option>
							</c:forEach>
							<form:option value="-1">
								<spring:message code="legal.case.all" text="All" />
							</form:option>
						</form:select>
					</div>




				</div>

				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveGenderAdvForm(this)">
						<spring:message code="legal.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetReportForm">
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









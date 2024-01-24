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
	src="js/social_security/demographicSummaryReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="social.demographicSummaryReport"
					text="Pensioner payment order register" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="legal.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="demographicsReport.html"
				cssClass="form-horizontal" id="DemographicFormReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="social.demographicReport.pensionSchedle"
							text="Pension Schedule" /></label>
					<c:set var="baseLookupCode" value="BSC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="pensionSchemeDto.schmeMsId" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Pension Schedule" />


					<label class="col-sm-2 control-label required-control"><spring:message
							code="social.demographicReport.pensionSchemName"
							text="Pension Scheme Name" /></label>

					<div class="col-sm-4">
						<form:select path="pensionSchemeDto.serviceId" id="serviceId"
							class="form-control chosen-select-no-results"
							data-rule-required="true" showAll="true">
							<form:option value="0">
								<spring:message code="social.select" text="Select" />
							</form:option>
							<form:option value="-1">
								<spring:message code="social.all" text="All" />
							</form:option>
							<c:forEach items="${command.serviceList}" var="pensionScheme">
								<form:option value="${pensionScheme[0]}"
									code="${pensionScheme[2]}" label="${pensionScheme[1]}"></form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>



				<div class="form-group">

					<label class="col-sm-2 control-label required-control"> <spring:message
							code="social.demographicReport.fromdate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="pensionSchemeDto.createdDate" id="createdDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="createdDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=createdDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="social.demographicReport.todate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="pensionSchemeDto.updatedDate" id="updatedDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="updatedDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=updatedDate></label>
						</div>
					</div>
				</div>



				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveReportForm(this)">
						<spring:message code="social.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm">
						<spring:message code="social.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="social.btn.back"></spring:message>
					</a>
				</div>

			</form:form>
		</div>
	</div>
</div>








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
	src="js/property/outstandingDetailSummaryReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="property.Outstanding.report"
					text="Outstanding Register" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="property.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="DetailOutstandingRegister.html"
				cssClass="form-horizontal" id="OutstandingForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="property.outstanding.report.type" text="Report Type" /></label>
					<div class="radio col-sm-4 margin-top-5">
						<label> <form:radiobutton path="propertydto.reportType"
								value="D" id="Detail" onclick="getReportType(this)" /> <spring:message
								code="property.detail.outstanding.report" /></label> <label><form:radiobutton
								path="propertydto.reportType" value="S" id="Summary"
								onclick="getReportType(this)" /> <spring:message
								code="property.summary.outstanding.report" /></label>
					</div>

				</div>


				<div class="form-group">
					<c:set var="baseLookupCode" value="WZB" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="WZB" hasId="true"
						pathPrefix="propertydto.mnassward"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="property.finyear" /></label>

					<div class="col-sm-4">
						<form:select path="" id="finalcialYear"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="0">
								<spring:message code="property.report.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.listOfinalcialyear}"
								var="finalcialYear">
								<form:option value="${finalcialYear.key}"
									code="${finalcialYear.key}">${finalcialYear.value}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" id="detailType"><spring:message
							code="property.detail.property.wise" text="Property No." /> </label>
					<div class="col-sm-4">
						<form:input path="propSearchdto.proertyNo" id="proertyNo"
							class="form-control mandColorClass" maxlength="50"></form:input>
					</div>
				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveReportForm(this)">
						<spring:message code="property.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm">
						<spring:message code="property.btn.reset" />
					</button>

					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="property.btn.back"></spring:message>
					</a>

					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveXLSXReport(this)">
						<spring:message code="property.report.btn.xlsx" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>
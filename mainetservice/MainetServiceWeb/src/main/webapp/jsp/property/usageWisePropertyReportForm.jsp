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
	src="js/property/usageWisePropertyReportForm.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="property.UsageWisePropertyReport"
					text="Usage Wise Property Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="property.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="UsageWiseReport.html" cssClass="form-horizontal"
				id="UsageWiseReportForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="form-group">
					<c:set var="baseLookupCode" value="WZB" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="WZB" hasId="true"
						pathPrefix="propertyDto.mnassward"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" isMandatory="true" />
				</div>



				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="property.collection.fromDate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="propertyDto.mnFromdt" id="mnFromdt"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="mnFromdt"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=mnFromdt></label>
						</div>
					</div>
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="property.collection.toDate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="propertyDto.mnTodt" id="mnTodt"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="mnTodt"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=mnTodt></label>
						</div>
					</div>
				</div>



				<div class="form-group">
					<c:set var="baseLookupCode" value="USA" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="USA" hasId="true"
						pathPrefix="propertyDto.usageType"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />


					<apptags:input labelCode="Property Number"
						path="propSearchDto.proertyNo" isMandatory="false" maxlegnth="20">
					</apptags:input>
				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveUsageForm(this)">
						<spring:message code="property.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm">
						<spring:message code="property.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="property.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>







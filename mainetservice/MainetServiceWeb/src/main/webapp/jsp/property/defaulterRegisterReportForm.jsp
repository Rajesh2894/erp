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
	src="js/property/defaulterRegisterReportForm.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="property.DefaulterRegisterReport"
					text="Defaulter Register Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="property.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="DefaulterReport.html" cssClass="form-horizontal"
				id="DefaulterRegisterForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


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

					<apptags:input labelCode="property.defaulter.report.from.amount"
						path="propertydto.mnAmount"
						cssClass="decimal text-right hasNumber" isMandatory="true"
						maxlegnth="16"></apptags:input>

					<apptags:input labelCode="property.defaulter.report.to.amount"
						path="propertydto.mntoAmount"
						cssClass="decimal text-right hasNumber" isMandatory="true"
						maxlegnth="16"></apptags:input>
				</div>


				<div class="form-group">
					<apptags:input labelCode="property.defaulter.report"
						path="propertydto.mnDefaulterCount"
						cssClass="decimal text-right hasNumber" isMandatory="true"
						maxlegnth="16"></apptags:input>
				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveForm(this)">
						<spring:message code="property.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm"
						onclick="window.location.href='DefaulterReport.html'">
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







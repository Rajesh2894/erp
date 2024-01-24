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
	src="js/water/connectionWiseDefaulterReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
	.zone-ward .form-group > label[for="codDwzid3"] {
		clear: both;
	}
	.zone-ward .form-group > label[for="codDwzid3"],
	.zone-ward .form-group > label[for="codDwzid3"] + div {
		margin-top: 0.7rem;
	}
		.trf-section .form-group > label[for="trmGroup3"] {
		clear: both;
	}
	.trf-section .form-group > label[for="trmGroup3"],
	.trf-section .form-group > label[for="trmGroup3"] + div {
		margin-top: 0.7rem;
	}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.deafulterReport" text="Defaulter List" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="water.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="defaulterReport.html" cssClass="form-horizontal"
				id="DefaulterForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="zone-ward">
				<div class="form-group">
					<c:set var="baseLookupCode" value="WWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="WWZ" hasId="true"
						pathPrefix="tbCsmrInfoDTO.codDwzid"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />
				</div>
				</div>

				<div class="form-group">
					<apptags:input labelCode="water.defaulter.ConnectionFrom"
						path="tbCsmrInfoDTO.csCcnFrom"
						cssClass="decimal text-right hasAlphaNumeric" isMandatory="false"
						maxlegnth="16"></apptags:input>

					<apptags:input labelCode="water.defaulter.ConnectionTo"
						path="tbCsmrInfoDTO.csCcnTo"
						cssClass="decimal text-right hasAlphaNumeric" isMandatory="false"
						maxlegnth="16"></apptags:input>
				</div>
				<div class="trf-section">
				<div class="form-group">
					<c:set var="baseLookupCode" value="TRF" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="TRF" hasId="true"
						pathPrefix="tbCsmrInfoDTO.trmGroup"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="false" />
				</div>
				</div>
					<div class="form-group">
					<apptags:input labelCode="water.topDefaulterCnt"
						path="tbCsmrInfoDTO.CsdefaulterCount"
						cssClass="decimal text-right hasNumber" isMandatory="false"
						maxlegnth="16"></apptags:input>
				</div>

				<div class="form-group">
					<apptags:input labelCode="water.defaulter.report.fromAmount"
						path="tbCsmrInfoDTO.csfromAmount"
						cssClass="decimal text-right hasNumber" isMandatory="true"
						maxlegnth="16"></apptags:input>

					<apptags:input labelCode="water.defaulter.report.toAmount"
						path="tbCsmrInfoDTO.cstoAmount"
						cssClass="decimal text-right hasNumber" isMandatory="true"
						maxlegnth="16"></apptags:input>
				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveDataForm(this)">
						<spring:message code="water.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm"
						onclick="window.location.href='defaulterReport.html'">
						<spring:message code="water.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="water.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>





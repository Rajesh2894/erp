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
	src="js/water/outstandingWaterDetailSummaryReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.outstandingRegister"
					text="Detail Outstanding Register" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="water.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="waterOutstandingRegister.html"
				cssClass="form-horizontal" id="OutsadignReportForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="form-group" id="form1">
					<div>
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.report.type" text="Report Type:" /></label>
						<div class="radio col-sm-4 margin-top-5">

							<label> <form:radiobutton path="csmrInfoDTO.reportType"
									value="D" id="Detail" onclick="getReportType(this)" /> <spring:message
									code="water.report.detail" /></label> <label> <form:radiobutton
									path="csmrInfoDTO.reportType" value="S" id="Summary"
									onclick="getReportType(this)" /> <spring:message
									code="water.report.summary" /></label> <label> <form:radiobutton
									path="csmrInfoDTO.reportType" value="C" id="connectionNo"
									onclick="getReportType(this)" /> <spring:message
									code="water.report.connectionNoWise" /></label> </label>
						</div>
					</div>


				</div>
				<div class="form-group" id="form2">
					<c:set var="baseLookupCode" value="WWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="WWZ" hasId="true"
						pathPrefix="csmrInfoDTO.codDwzid" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />

				</div>

				<div class="form-group" id="form3">
					<c:set var="baseLookupCode" value="TRF" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="TRF" hasId="true"
						pathPrefix="csmrInfoDTO.trmGroup" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />
				</div>

				<div class="form-group" id="form4">
					<label class="col-sm-2 control-label required-control"
						for="inwardType"><spring:message
							code="MeterReadingDTO.conSize" text="Connection Size" /></label>
					<c:set var="baseLookupCode" value="CSZ" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="csmrInfoDTO.csCcnsize" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Connection Size" />

					<label class="col-sm-2 control-label required-control"
						for="inwardType"><spring:message
							code="water.meter.nonmeter" text="Metered/Non-Metered" /></label>
					<c:set var="baseLookupCode" value="WMN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="csmrInfoDTO.csMeteredccn" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Metered/Non-Metered" />
				</div>


				<div class="form-group" id="Detailtype">
					<apptags:input labelCode="water.ConnectionNo"
						path="csmrInfoDTO.csCcn"
						cssClass="decimal text-left hasAlphaNumeric" isMandatory="true"
						maxlegnth="16" onChange="showReportType()"></apptags:input>


				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveOutstandForm(this)">
						<spring:message code="water.btn.submit" />
					</button>

					<button type="Reset" class="btn btn-warning" id="resetOutform">
						<spring:message code="water.btn.reset" />
					</button>

					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="water.btn.back"></spring:message>
					</a>
				</div>
			</form:form>

		</div>
	</div>
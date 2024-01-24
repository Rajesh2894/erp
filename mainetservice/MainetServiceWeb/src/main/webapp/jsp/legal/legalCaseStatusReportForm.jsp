<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/legal/legalCaseStatusReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="legal.case.status.Report"
					text="Legal Case Status Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="legal.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="LegalCaseReport.html"
				cssClass="form-horizontal" id="LegalCaseForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.case.type" text="Case Type" /></label>
					<c:set var="baseLookupCode" value="TOC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReportDto.cseType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Case Type" />

					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.case.status" text="Case Status" /></label>
					<c:set var="baseLookupCode" value="CSS" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReportDto.cseStatus" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Case Status" />
				</div>


				<div class="form-group">
					<apptags:date labelCode="legal.case.from.date"
						datePath="legalReportDto.csefrmDate" 
						cssClass="form-control" fieldclass="datepicker"
						></apptags:date>	
					<%-- <label class="col-sm-2 control-label required-control"> <spring:message
							code="legal.case.from.date" text="Active From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="legalReportDto.csefrmDate" id="csefrmDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="csefrmDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=csefrmDate></label>
						</div>
					</div> --%>
					<apptags:date labelCode="legal.case.to.date"
						datePath="legalReportDto.csetoDate" 
						cssClass="form-control" fieldclass="datepicker"
						></apptags:date>
					
					<%-- <label class="col-sm-2 control-label required-control"> <spring:message
							code="legal.case.to.date" text="Active To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="legalReportDto.csetoDate" id="csetoDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="csetoDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=csetoDate></label>
						</div>
					</div> --%>
				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveForm(this)">
						<spring:message code="legal.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" onclick="window.location.href='LegalCaseReport.html'">
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


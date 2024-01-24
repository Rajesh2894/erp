<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/marriage_mgmt/marriageDetailSummaryReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="mrm.reports.detail.summary" text="Marriage Detail and Summary report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="mrm.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="marriageDetailSummaryBirtReportList.html"
				cssClass="form-horizontal" id="mrmDetailSummaryReports">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="mrm.report.type" text="Report Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportType"
							class="form-control chosen-select-no-results"
							data-rule-required="true">
<form:option value="0">	<spring:message code="mrm.report.select" text="Select" /></form:option>
<form:option value="A"><spring:message code="mrm.report.detail" text="Marriage Detail Report" /></form:option>
<form:option value="B"><spring:message code="mrm.report.summary" text="Marriage Summary Report" /></form:option>


							
							</form:select>
					</div>
				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveMrmReportForm(this)">
						<spring:message code="mrm.button.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform">
						<spring:message code="mrm.button.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="mrm.button.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>







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
	src="js/works_management/reports/customizeWorksBirtReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.customize.reports" text="Customized Public Works Reports" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="customizeWorksBirtReport.html"
				cssClass="form-horizontal" id="FormReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


      <div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="wms.customize.report.type" text="Report Type" /></label>
                        <div class="col-sm-4">
						<form:select path="reportType"
							id="reportType"
							class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="work.management.select" text="Select" /></form:option>
		<form:option value="A1"><spring:message code="wms.customize.reports.completion.Certificate.works" text="Completion Certificate of works" /></form:option>
		<form:option value="A2"><spring:message code="wms.customize.reports.Hindrence.Register" text="Hindrence Register" /></form:option>
		<form:option value="A3"><spring:message code="wms.customize.reports.Letter.of.acceptance.above" text="Letter of acceptance Above 15cr" /></form:option>
		<form:option value="A3"><spring:message code="wms.customize.reports.Letter.of.acceptance.below " text="Letter of acceptance Below 15cr" /></form:option>
		<form:option value="A5"><spring:message code="wms.customize.reports.Work.Order" text="Work Order" /></form:option>
		<form:option value="A6"><spring:message code="wms.customize.reports.Work.Order.maintainance" text="Work Order maintainance" /></form:option>
		<form:option value="A7"><spring:message code="wms.customize.reports.Work.Order.Quotation" text="Work Order Quotation" /></form:option>
		<form:option value="A8"><spring:message code="wms.customize.reports.Works.Bill.Summary" text="Works and Bill Summary" /></form:option>
		<form:option value="A9"><spring:message code="wms.customize.reports.works.Bill.Report" text="works Bill Report" /></form:option>

</form:select>
					</div>
</div>

				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveCustomBirtForm(this)">
						<spring:message code="wms.reports.print" text="" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform">
						<spring:message code="wms.report.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="wms.report.back"></spring:message>
					</a>
				</div>

			</form:form>
		</div>
	</div>
</div>







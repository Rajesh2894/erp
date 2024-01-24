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
	src="js/property/legacyPropertyReportForm.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="prop.birt.legacy.Property.dcb.report"
					text="DCB Reports" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="property.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="legacyPropertyBirtReport.html" cssClass="form-horizontal"
				id="legacyDCBBirtReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="property.demand.report.type" text="Report Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportType" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		
		<form:option value="B"><spring:message code="prop.birt.WardZone.Wise.Detail.Collection" text="Ward Zone Wise Detail Collection Report" /></form:option>
		<form:option value="C"><spring:message code="prop.birt.WardZone.Wise.summary.Collection" text="Ward Zone Wise Summary Collection Report" /></form:option>
	    <form:option value="D"><spring:message code="prop.birt.WardZone.Tax.Wise.Demand" text="Ward Zone Tax Wise Demand Report" /></form:option>
       <form:option value="E"><spring:message code="prop.birt.Tax.Wise.DCB.Summary.Report" text="Tax Wise DCB Summary Report" /></form:option>
        <form:option value="A"><spring:message code="prop.birt.Usage.Type.wise.DCB.Report" text="Usage Type wise DCB Report" /></form:option>

						</form:select>
					</div>
					
				
					
					
				</div>
				
					



				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveDCBBirtForm(this)">
						<spring:message code="property.btn.submit" />
					</button>
					
				<button type="Reset" class="btn btn-warning" id="resetDcbform">
						<spring:message code="property.btn.reset" text="Reset"></spring:message>
					</button>
					
					
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="property.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>







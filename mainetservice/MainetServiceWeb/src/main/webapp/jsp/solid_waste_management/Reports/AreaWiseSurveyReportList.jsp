<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/solid_waste_management/report/AreaWiseSurveyReport.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Area Wise Survey Report" />
			</h2>
			<apptags:helpDoc url="AreaWiseSurveyReport.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="AreaWiseSurveyReport.html" method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label for="select-1479365507176"
							class="col-sm-2 control-label required-control"><spring:message
								code="" text="Area Wise Collection" /></label>
						<c:set var="baseLookupCode" value="LCT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" path="areaWiseDto.reportType"
							cssClass="form-control"
							isMandatory="true" selectOptionLabelCode="selectdropdown"
							hasId="true" />
					</div>
					<div class="form-group">
						<apptags:date labelCode="From Date" fieldclass="fromDateClass"
							datePath="areaWiseDto.fromDate" cssClass="fromDateClass fromDate" isMandatory="true">
						</apptags:date>
						<apptags:date labelCode="To Date" fieldclass="toDateClass"
							datePath="areaWiseDto.toDate" cssClass="toDateClass toDate" isMandatory="true">
						</apptags:date>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onClick="AreaWiseSurveyReportPrint('AreaWiseSurveyReport.html')"
						data-original-title="Print">
						<spring:message code="solid.waste.print" text="Print"></spring:message>
					</button>
					<button type="Reset" class="btn btn-warning" onclick="resetArea()">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>

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
<script type="text/javascript" src="js/solid_waste_management/report/"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Construction And Demolition Waste" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="ConstructionAndDemolitionReport.html" method="post"
				class="form-horizontal">
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
						<label class="col-sm-2 control-label required-control"
							for="desposalsite"><spring:message code="swm.dsplsite" />
						</label>
						<div class="col-sm-4">
							<form:select path=""
								class="form-control mandColorClass chosen-select-no-results"
								label="Select"
								disabled="${command.saveMode eq 'V' ? true : true }" id="deId">
								<form:option value="0">
									<spring:message code="solid.waste.select" text="select" />
								</form:option>
								<c:forEach items="${command.mrfMasterList}" var="lookUp">
									<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<apptags:date labelCode="From Date" fieldclass="fromDateClass"
							datePath="" cssClass="fromDateClass fromDate"
							isMandatory="true">
						</apptags:date>
						<apptags:date labelCode="To Date" fieldclass="toDateClass"
							datePath="" cssClass="toDateClass toDate"
							isMandatory="true">
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

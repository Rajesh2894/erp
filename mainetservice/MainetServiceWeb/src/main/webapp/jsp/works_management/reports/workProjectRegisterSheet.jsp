<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/reports/workProjectRegisterReport.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<script>
	$(function() {

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		/* maxDate : '0' */
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.project.status.report"
					text="Project Status Report" />
			</h2>
			<div class="additional-btn">
				   <apptags:helpDoc url="ProjectRegisterReport.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="ProjectRegisterReport.html"
				class="form-horizontal" id="ProjectRegisterReportForm"
				name="ProjectRegisterReportForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<%-- <form:hidden path="orgId" id="orgId"/> --%>

				<div class="form-group padding-top-10">


					<label class="col-sm-2 control-label"><spring:message
							code="project.master.schemename" /></label>
					<div class="col-sm-4">
						<form:select path="schemeMasterDto.wmSchNameEng" id="schId"
							class="form-control mandColorClass chosen-select-no-results"
							onchange="getProjectName(this);">
							<%-- <form:option value="">
								<spring:message code='work.management.select' />
							</form:option> --%>
							<form:option value="0">
								<spring:message code='work.management.select' />
							</form:option>
							<form:option value="-1">All</form:option>
							<c:forEach items="${command.schemeMasterList}"
								var="activeSchemeName">
								<form:option value="${activeSchemeName.wmSchId }"
									code="${activeSchemeName.wmSchId }">${activeSchemeName.wmSchNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="workEstimateMasterDto.projId" id="projId"
							class="form-control mandColorClass chosen-select-no-results"
							onchange="">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<form:option value="-1">All</form:option>

							<c:forEach items="${command.projectMasterList}"
								var="activeProjName">
								<form:option value="${activeProjName.projId }"
									code="${activeProjName.projId }">${activeProjName.projNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">

					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workType" text="Type of Work" /> </label>
					<c:set var="WRTlookUp" value="WRT" />
					<apptags:lookupField items="${command.getLevelData(WRTlookUp)}"
						path="workDefinitionDto.workType"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="work.management.select" isMandatory="true" />
				</div>

				<div class="text-center clear padding-10">
					<button type="button" id="save" class="btn btn-success btn-submit"
						onclick="viewProjectRegisterReport(this);">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="mileStone.submit" text="Submit" />
					</button>
					<button class="btn btn-warning" onclick="resetFormProject(this);"
						type="button">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

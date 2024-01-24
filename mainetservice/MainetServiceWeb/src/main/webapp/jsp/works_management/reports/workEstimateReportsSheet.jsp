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
	src="js/works_management/reports/workEstimateReports.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="works.abstract.measurement.rate.analysis"
					text="" />
			</h2>
			<div class="additional-btn">
			    <apptags:helpDoc url="WorkEstimateReport.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="WorkEstimateReport.html" class="form-horizontal"
				id="estimateReportForm" name="estimateReportForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="orgId" id="orgId" />
				<form:hidden path=""  value="${userSession.getCurrent().getLanguageId()}" id="lang" />
				
				<div class="form-group">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.department" text="Department" /> </label>
					<div class="col-sm-4">
					 <form:select path="wmsDto.deptId"
							cssClass="form-control chosen-select-no-results mandColorClass" 
							id="deptId"  data-rule-required="true" onchange="getProjList(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="departments">
								<form:option value="${departments.dpDeptid }"
									code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
							</c:forEach>
						</form:select> 
					</div>
				   <label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workType" text="Type of Work" /> </label>
							 <c:set var="WRTlookUp" value="WRT" /> 
						
					  <div class="col-sm-4">
						<form:select path="wmsDto.workType"
							class="form-control chosen-select-no-results" id="workType">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.getLevelData(WRTlookUp)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>  
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="workEstimateMasterDto.projId" id="projId"
							class="form-control mandColorClass chosen-select-no-results"
							onchange="getWorkName(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.projectMasterList}"
								var="activeProjName">
								<form:option value="${activeProjName.projId }"
									code="${activeProjName.projId }">${activeProjName.projNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<form:select path="workEstimateMasterDto.workId" id="workId"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.estimate.report.type" /></label>
					<div class="col-sm-4">
						<form:select path="" cssClass="form-control" id="reportType">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.reportTypeLookUp}" var="lookUp">
								<form:option value="${lookUp.lookUpId }"
									code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<button type="button" id="save" class="btn btn-success btn-submit"
						onclick="viewWorkReport(this);">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="mileStone.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning"
						onclick="resetReport()">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

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
<script type="text/javascript"
	src="js/works_management/reports/completionCertificate.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="wms.CompletionCertificateSummary"
					text="Completion Certificate Summary" />
			</h2>
			<div class="additional-btn">
				 <apptags:helpDoc url="CompletionCertificate.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="" class="form-horizontal" id="" name="">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="wms.CompletionNo" text="Completion No." /></label>
					<div class="col-sm-4">
						<form:input path="contractCompletionDto.completionNo"
							Class="form-control" id="completionNo" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="contractCompletionDto.projId" id="projId"
							class="form-control chosen-select-no-results"
							onchange="getWorkName(this,'C');">
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
					<label class="col-sm-2 control-label"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<form:select path="contractCompletionDto.workId" id="workId"
							class="form-control chosen-select-no-results" onchange="">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button class="btn btn-blue-2  search" title='<spring:message code="works.management.search" text="Search" />'
						onclick="searchCompletion();" type="button">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning  reset" type="reset" title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="window.location.href='CompletionCertificate.html'">
						<spring:message code="works.management.reset" text="Reset" />
					</button>

					<button class="btn btn-blue-2" onclick="getCompletionForm();" title='<spring:message code="wms.Create" text="Create" />'
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="wms.Create" text="Create" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="wms.CompletionNo" text="Completion No." /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="mb.completionDate" text="Completion Date" /></th>
								<th scope="col" width="18%" align="center"><spring:message
										code="project.master.projname" text="Project Name" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="milestone.works.name" text="Works Name" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.contract.variation.approval.vendor.name"
										text="Contractor Name" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="estate.grid.column.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.completionDtos}" var="mstDto">
								<tr>
									<td class="text-center">${mstDto.completionNo}</td>
									<td class="text-center">${mstDto.completionDate}</td>
									<td>${mstDto.projName}</td>
									<td>${mstDto.workName}</td>
									<td>${mstDto.contractorName}</td>
									<td class="text-center">
										<button type="button" class="btn btn-primary btn-sm"
											title="Print Report"
											onclick="getActionForDefination(${mstDto.workId});">
											<i class="fa fa-print"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>
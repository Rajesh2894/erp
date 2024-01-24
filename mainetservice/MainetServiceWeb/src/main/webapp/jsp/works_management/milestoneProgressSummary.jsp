<!-- Start JSP Necessary Tags -->
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
	src="js/works_management/milestoneProgress.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
table tbody tr :is(:not(td:nth-child(2), td:nth-child(3))) {
	text-align: center;
}
table tbody tr td:nth-child(3) {
	text-align: right;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="wms.ProgressUpdateSummary"
					text="Progress Update Summary" />
			</h2>
			<div class="additional-btn">
			     <apptags:helpDoc url="MilestoneProgress.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="MilestoneProgress.html" class="form-horizontal"
				id="milestoneProgress" name="milestoneProgress">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="mileStoneDTO.projId" id="projId"
							class="form-control chosen-select-no-results"
							onchange="getWorkName(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							 
							     <c:if test="${userSession.getCurrent().getLanguageId() ne '1'}">
							     <c:forEach items="${command.projectMasterList}"
								var="activeProjName">
								<form:option value="${activeProjName.projId }"
									code="${activeProjName.projId }">${activeProjName.projNameReg}</form:option>	
							</c:forEach>
						    </c:if>
					       <c:if test="${userSession.getCurrent().getLanguageId() eq '1'}">
							<c:forEach items="${command.projectMasterList}"
								var="activeProjName">
								<form:option value="${activeProjName.projId }"
									code="${activeProjName.projId }">${activeProjName.projNameEng}</form:option>	
							</c:forEach>
							</c:if>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<form:select path="mileStoneDTO.workId" id="workId"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button class="btn btn-blue-2  search" onclick="searchMilestone();" title='<spring:message code="works.management.search" text="Search" />'
						type="button">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning  reset" type="reset" title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="window.location.href='MilestoneProgress.html'">
						<spring:message code="works.management.reset" text="Reset" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="ser.no" text="Sr. No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="wms.MilestoneDescription" text="Milestone Description" />
								<th scope="col" width="18%" class="text-center"><spring:message
										code="wms.MilestoneWeightage" text="Milestone Weightage" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="wms.MilestoneStartDate" text="Milestone Start Date" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="wms.MilestoneEnddate" text="Milestone End date" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="wms.UpdateProgress" text="Update Progress" /></th>
							</tr>
						</thead>
						<%-- <tfoot id="footer">
							<tr>
								<th colspan="2" style="text-align: right">Total Weightage:</th>
								<th><form:input id="totalWeightage" class=" form-control"
										maxlength="500" path="" readonly="true"/></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot> --%>
						<tbody>
							<c:forEach items="${milestoneList}" var="mstDto">
								<tr>
									<td>${mstDto.mileStoneDesc}</td>
									<td></td>
									<td class="text-right">${mstDto.mileStoneWeight}</td>
									<td class="text-center">${mstDto.msStartDate}</td>
									<td class="text-center">${mstDto.msEndDate}</td>
									<td class="text-center">
										<button type="button" class="btn btn-primary btn-sm"
											title="Update Progress"
											onclick="getActionForDefination(${mstDto.mileId});">
											<i class="fa fa-line-chart"></i>
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
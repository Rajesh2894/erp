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
	src="js/works_management/financialMilestone.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<style>
.fm-summary-table tbody tr :is(td:nth-child(1), td:nth-child(5), td:last-child) {
	text-align: center;
}
.fm-summary-table tbody tr td:nth-child(4) {
	text-align: right;
}
</style>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="wms.FinancialMilestoneSummary"
					text="Financial Milestone Summary" />
			</h2>
			<div class="additional-btn">
			 <apptags:helpDoc url="FinancialMilestone.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="FinancialMilestone.html" class="form-horizontal"
				id="financialMilestone" name="financialMilestone">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="mileStoneFlag" id="mileStoneFlag" />

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="mileStoneDTO.projId" id="projId"
							cssClass="form-control chosen-select-no-results mandColorClass"
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
							cssClass="form-control chosen-select-no-results mandColorClass">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button class="btn btn-blue-2  search"
						title='<spring:message code="works.management.search" text="Search" />'
						onclick="searchFinancialMileStone();" type="button">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						title='<spring:message code="works.management.reset" text="Reset" />'>
						<spring:message code="works.management.reset" text="Reset" />
					</button>

					<button class="btn btn-primary"
						title='<spring:message code="works.management.add" text="Create Financial Milestone" />'
						onclick="openMilestoneAddForm('C');" type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="works.management.add"
							text="Create Financial Milestone" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped fm-summary-table" id="datatables">
					<form:hidden path=""  value="${userSession.getCurrent().getLanguageId()}" id="lang" />
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="project.master.projcode" text="Project Code" /><input
									type="hidden" id="srNo"></th>
								<th scope="col" width="18%" align="center"><spring:message
										code="project.master.projname" text="Project Name" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="milestone.works.name" text="Works Name" /></th>
							<%-- 	<th scope="col" width="10%" align="center"><spring:message
										code="milestone.startdate" text="Start Date" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="milestone.enddate" text="End date" /></th> --%>
								<th scope="col" width="10%" align="center"><spring:message
										code="milestone.amount" text="Amount" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="estate.grid.column.action" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="wms.UpdateProgress"
										text="Update Progress" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${projectDtoList}" var="mstDto">
								<tr>
									<td class="text-center">${mstDto.projCode}</td>
									<td>
									<c:choose>
										<c:when test="${userSession.getCurrent().getLanguageId() eq '1'}">
											${mstDto.projNameEng}
										</c:when>
										<c:otherwise>
											${mstDto.projNameReg}
										</c:otherwise>

									</c:choose>
									</td>

									<td>${mstDto.workName}</td>
								<%-- 	<td>${mstDto.startDate}</td>
									<td>${mstDto.endDate}</td> --%>
									<td class="text-right">${mstDto.projActualCost}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View Milestone"
											onclick="getActionForMileStone(${mstDto.projId},'V',${mstDto.workId});">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit Milestone"
											onclick="getActionForMileStone(${mstDto.projId},'E',${mstDto.workId});">
											<i class="fa fa-pencil-square-o"></i>
										</button>
									<td class="text-center" align="center">
										<button type="button" class="btn btn-primary btn-sm"
											title="Update Progress"
											onclick="getActionForMileStone(${mstDto.projId},'S',${mstDto.workId});">
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
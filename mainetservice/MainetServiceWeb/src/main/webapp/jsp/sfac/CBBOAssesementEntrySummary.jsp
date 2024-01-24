<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/CBBOAssesementEntry.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.cbbo.assessment.entry.summary.title"
					text="CBBO Assessment Entry Summary Form" />
			</h2>
			<apptags:helpDoc url="CBBOAssessmentEntry.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="CBBOAssessmentEntrySummaryForm"
				action="CBBOAssessmentEntry.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
				<div class="form-group">
			<%-- 		<label class="col-sm-2 control-label"><spring:message
							code="sfac.IA.name" text="IA Name" /></label>
					<div class="col-sm-4">
						<form:select path="" id=""
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.assessmentDtoList}" var="dto">
								<form:option value="${dto.iaId}">${dto.iaName}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.CBBO.name" text="" /></label>
					<div class="col-sm-4">
							<form:select path="assementMasterDto.cbboId" id="cbboId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.assessmentDtoList}" var="dto">
								<form:option value="${dto.cbboId}">${dto.cbboName}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.assessment.status" text="Assessment Status" /></label>
					<div class="col-sm-4">
						<form:select path="assementMasterDto.assStatus" id="assStatus"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code='sfac.select' />
							</form:option>
							<form:option value="A">
								<spring:message code='sfac.approved' text="Approved"/>
							</form:option>
							<form:option value="R">
								<spring:message code='sfac.rejected' text="Rejected"/>
							</form:option>
							<form:option value="P">
								<spring:message code='sfac.pending' text="Pending"/>
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="assDate"> <spring:message
							code="sfac.assessment.date" text="Assessment Date" />
					</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="assementMasterDto.assDate" type="text" class="form-control datepicker"
								id="assDate" placeholder="dd/mm/yyyy"  readonly="true"/>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>

				<div class="text-center margin-bottom-10">
				<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA'}">
					<button type="button" class="btn btn-blue-2" title='<spring:message code="sfac.button.search" text="Search" />'
						onclick="searchForm(this)">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search" />
					</button></c:if>
					<button type="button" class="btn btn-warning" title='<spring:message code="sfac.button.reset" text="Reset" />'
						onclick="window.location.href='CBBOAssesementEntry.html'">
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>
					<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA'}">
					<button type="button" class="btn btn-blue-3" title='<spring:message code="sfac.button.add" text="Add" />'
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add" />
					</button></c:if>
					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="sfac.button.back" text="Back"></spring:message>
					</button>
				</div>
				
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="cbboAssessEntSummTable">
						<thead>
							<tr>
								<th>
									<spring:message code="sfac.sr.no" text="Sr. No." />
								</th>
								<th>
									<spring:message code="sfac.assessment.initiation.no" text="Assessment Initiation No" />
								</th>
								<th>
									<spring:message code="sfac.status" text="Status" />
								</th>
								<th>
									<spring:message code="sfac.assessment.action" text="Assessment Action" />
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.assessmentDtoList}" var="dto"
								varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center">${dto.assessmentNo}</td>
									<td class="text-center">${dto.assStatus}</td>
									<td align="center"><button type="button" class="btn btn-blue-2 btn-sm"
											title="View"
											onclick="modifyCase(${dto.assId},'CBBOAssessmentEntry.html','EDIT','V')">
											<i class="fa fa-eye"></i>
										</button>
										<c:if test="${dto.status eq 'A' && (userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA')}">
										<button type="button" class="btn btn-warning btn-sm"
												title="Print"
												onclick="printAssessMentDetails(${dto.assId},'CBBOAssessmentEntry.html','printAssessMentDetails','P')">
												<i class="fa fa-print"></i>
											</button> </c:if>
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
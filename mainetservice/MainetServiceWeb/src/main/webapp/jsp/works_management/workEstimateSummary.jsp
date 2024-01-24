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
	src="js/works_management/workEstimateSummary.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/workEstimateApproval.js"></script>
<!-- End JSP Necessary Tags -->

<!-- Start Breadcrumb -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End Breadcrumb -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.estimate" />
			</h2>
			<div class="additional-btn">
				    <apptags:helpDoc url="WorkEstimate.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">

			<form:form action="WorkEstimate.html" class="form-horizontal"
				name="worksEstimate" id="worksEstimateSummary">
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
				<!-- Defect #80050 -->
				<%-- 	<apptags:input labelCode="work.estimate.number" path=""
						cssClass="workEstimationNo"></apptags:input> --%>
					<label class="col-sm-2 control-label "><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results" id="projectId"
							onchange="getWorkName(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							
						    <c:if test="${userSession.getCurrent().getLanguageId() ne '1'}">
							<c:forEach items="${command.projectMasterList}" var="list">
								<form:option value="${list.projId}" code="${list.projCode}">${list.projNameReg}</form:option>
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
					<label class="col-sm-2 control-label "><spring:message
							code="work.def.workname" /></label>
					<%-- <div class="col-sm-4">
										<form:select path=""
											cssClass="form-control chosen-select-no-results"
											id="workName">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.workDefinitionDto}" var="workDef">
												<form:option code="${workDef.workcode}"
													value="${workDef.workId }">${workDef.workName }</form:option>
											</c:forEach>
										</form:select>
									</div> --%>
					<div class="col-sm-4">
						<form:select path="" id="workName"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<apptags:date fieldclass="datepicker" datePath=""
						labelCode="work.estimate.startdate" cssClass="custDate fromDate"></apptags:date>
					<apptags:date fieldclass="datepicker" datePath=""
						labelCode="work.estimate.enddate" cssClass="custDate todate"></apptags:date>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="work.estimate.new.status" /></label>
					<div class="col-sm-4">
						<form:select path="" cssClass="form-control " id="status">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
								<form:option value="D">
								<spring:message code='mb.draft' />
							</form:option>
							<form:option value="P">
								<spring:message code='mb.pending' />
							</form:option>
							<form:option value="A">
								<spring:message code='work.estimate.Approved' />
							</form:option>
							<form:option value="T">
								<spring:message code='mb.tender' />
							</form:option>
							<form:option value="TS">
								<spring:message code='mb.technical.sanction' />
							</form:option>
							<form:option value="AS">
								<spring:message code='mb.admin.sanction' />
							</form:option>
						</form:select>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button class="btn btn-blue-2  search searchWorkDefination" title='<spring:message code="works.management.search" text="Search" />'
						type="button">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button type="Reset" class="btn btn-warning" title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="resetWorkEstimate();">
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-primary add" title='<spring:message code="works.management.add" text="" />'
						onclick="openAddWorkEstimate('WorkEstimate.html','AddWorkEstimate');"
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="works.management.add" text="" />
					</button>

				</div>
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
					<form:hidden path=""  value="${userSession.getCurrent().getLanguageId()}" id="langId" />
						<thead>
							<tr>
								<th width="12%" scope="col" align="center"><spring:message
										code="project.master.projname" /></th>
								<%-- <th width="11%" scope="col" align="center"><spring:message
										code="work.def.workname" /></th> --%>
								<%-- <th width="12%" scope="col" align="center"><spring:message
										code="work.estimate.location" /></th> --%>
								<th width="12%" scope="col" align="center"><spring:message
										code="work.def.workCode" /></th>
								<th width="14%" scope="col" align="center"><spring:message
										code="work.def.workname" /></th>
								<th width="12%" scope="col" align="center"><spring:message
										code="work.estimate.cost" /></th>
								<th width="11%" scope="col" align="center"><spring:message
										code="work.estimate.new.status" /></th>
								<th width="12%" scope="col" align="center"><spring:message
										code="estate.grid.column.action" /></th>
							</tr>
						</thead>
						<tbody style="text-align:center">
							<c:forEach items="${command.workDefinationList}" var="mstDto">
							
								 <tr>
								 
									<td>${mstDto.projName}</td>
									<%-- <td>${mstDto.workName}</td> --%>
									<%-- <td align="center">${mstDto.locationDesc}</td> --%>
									<td align="center">${mstDto.workcode}</td>
									<td>${mstDto.workName}</td>
									<td align="right">${mstDto.workEstAmt}</td>
									<%-- <td align="center">${mstDto.workStatus}</td> --%>
									<td>
										<c:choose>
										<c:when test="${mstDto.workStatus eq 'Tender Generated'}">
										<spring:message code="status.tender" text="Tender Generated" />
										</c:when>
										<c:when test="${mstDto.workStatus eq 'Technical Sanction Approved'}">
										<spring:message code="status.sanction" text="Technical Sanction Approved" />
										</c:when>
										<c:when test="${mstDto.workStatus eq 'Pending'}">
										<spring:message code="status.pending" text="Pending" />
										</c:when>
										<c:when test="${mstDto.workStatus eq 'Draft'}">
										<spring:message code="status.draft" text="Draft" />
										</c:when>
										<c:when test="${mstDto.workStatus eq 'Admin Sanction Approved'}">
										<spring:message code="status.admin.sanction" text="Admin Sanction Approved" />
										</c:when>
										<c:when test="${mstDto.workStatus eq 'Approved'}">
										<spring:message code="status.approved" text="Approved" />
										</c:when>
										</c:choose>
										</td>
									<td align="center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onclick="getActionForDefination(${mstDto.workId},'V');"
											title="<spring:message code="works.management.view"></spring:message>">
											 <i class="fa fa-eye"></i>
										</button>
										<c:if test="${mstDto.workStatus eq 'Draft'}">
										<button type="button" class="btn btn-warning btn-sm"
											onclick="getActionForDefination(${mstDto.workId},'E');"
											title="<spring:message code="works.management.edit"></spring:message>">
											<i class="fa fa-pencil"></i>
										</button>
										</c:if>
										<c:if test="${mstDto.workStatus eq 'Draft'}">
										<button type="button" class="btn btn-green-3 btn-sm"
											title="<spring:message code="work.management.send.for.approval"></spring:message>"
											onclick="sendForApproval(${mstDto.workId},'S');">
											<i class="fa fa-share-square-o "></i>
										</button></c:if>

										<%-- <button type="button" class="btn btn-primary btn-sm"
											title="<spring:message code="work.estimate.report.print"></spring:message>"
											onclick="getActionForDefination(${mstDto.workId},'P');">
											<i class="fa fa-print "></i>
										</button> --%>
										<c:if test="${mstDto.workStatus ne 'Draft'}">
										<button type="button" class="btn btn-primary btn-sm"
											title="<spring:message code="" text="Action History"></spring:message>"
											onclick="getActionForWorkFlow(${mstDto.workId},'V');">
											<i class="fa fa-history"></i>
										</button></c:if>

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


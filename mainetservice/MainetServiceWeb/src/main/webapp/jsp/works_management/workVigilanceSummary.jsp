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
	src="js/works_management/workVigilance.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<!-- End JSP Necessary Tags -->
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="work.vigilance.summary"
					text="Vigilance Summary" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="WorkVigilance.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="WorkVigilance.html" cssClass="form-horizontal"
				name="WorkVigilance" id="WorkVigilance">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control "><spring:message
							code="project.master.projname" text="Project Name" /></label>
					<div class="col-sm-4">
						<form:select path="vigilanceDto.projId"
							cssClass="form-control chosen-select-no-results" id="projectId"
							disabled="${command.saveMode eq 'V'}"
							onchange="getprojectName();">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.wmsprojectDto}" var="activeProjName">
								<form:option value="${activeProjName.projId }">${activeProjName.projNameEng}</form:option>
							</c:forEach>

						</form:select>
					</div>
					
					<form:hidden path="sorCommonId" value="${vigilanceDto.workId}"
										id="workIdAdd" />
					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<form:select path="vigilanceDto.workId"
							cssClass="form-control chosen-select-no-results" id="workId"
							disabled="${command.saveMode eq 'V'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
					<%-- 		<c:forEach items="${command.wmsprojectDto}"
												var="activeProjName">
												<form:option value="${activeProjName.workId }">${activeProjName.workName}</form:option>
											</c:forEach> --%>
						</form:select>
					</div>
				</div>

				<!-- Start button -->

				<div class="text-center clear padding-10">
					<button class="btn btn-blue-2 search" type="button"
						id="search" onclick="searchDetails();">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning  reset" type="reset">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" />
					</button>
					<button class="btn btn-success add" type="button"
						onclick="openAddWorkVigilence('WorkVigilance.html','OpenWorkVigilanceForm');">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="works.management.add" text="Add" />
					</button>
				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>

								<th width="12%" align="center"><spring:message
										code="project.master.projname" text="Project Name" /></th>
								<th width="15%" align="center"><spring:message
										code="work.def.workname" text=" Project Name." /></th>
								<th width="15%" align="center"><spring:message
										code="work.vigilance.date" text="Work inspection Date" /></th>
								<th width="20%" align="center"><spring:message
										code="works.management.action" text="Action" /></th>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${vigilanceDtosList}" var="workVigilenceDto">

								<tr>

									<td align="center">${workVigilenceDto.projName}</td>
									<td align="center">${workVigilenceDto.workName}</td>

									<td align="center">${workVigilenceDto.inspectDateDesc}</td>

									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="viewWorkVigilance(${workVigilenceDto.vigilanceId},'V')"
											title="View">
											<i class="fa fa-eye"></i>
										</button>

										<button type="button" class="btn btn-success btn-sm"
											onClick="editWorkVigilance(${workVigilenceDto.vigilanceId})"
											title="Edit Work Order">
											<i class="fa fa-pencil"></i>
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

<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript"
	src="js/works_management/wmsprojectmaster.js"></script>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="project.master.project.summary" text="" />
			</h2>
			<div class="additional-btn">
			<apptags:helpDoc url="WmsMaterialMaster.html"></apptags:helpDoc>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start Form -->
			<form:form action="WmsProjectMaster.html" class="form-horizontal"
				name="wmsProjectMaster" id="wmsProjectMaster">
				<!-- Start Validation include tag -->
				<form:hidden path="UADstatusForProject" id="UADstatusForProject" />
				<form:hidden path="" id="isDefaultStatusProj"
					value="${userSession.organisation.defaultStatus}" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<!-- Start Each Section -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="project.master.title" /> </a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="project.master.projcode"
										path="wmsProjectMasterDto.projCode" cssClass="mandColorClass"></apptags:input>
									<apptags:input labelCode="project.master.projname"
										path="wmsProjectMasterDto.projNameEng"
										cssClass="mandColorClass"></apptags:input>
								</div>

								<!-- Remove As Per SUDA UAT -->

								<%-- <div class="form-group">
									<apptags:date fieldclass="datepicker"
										datePath="wmsProjectMasterDto.projStartDate"
										labelCode="project.master.startdate"
										cssClass="mandColorClass custDate"></apptags:date>
									<apptags:date fieldclass="datepicker"
										datePath="wmsProjectMasterDto.projEndDate"
										labelCode="project.master.enddate"
										cssClass="mandColorClass custDate"></apptags:date>
								</div> --%>

								<div class="form-group">

									<label class="col-sm-2 control-label "><spring:message
											code="wms.SourceofFund" text="Source of Fund" /></label>
									<div class="col-sm-4">
										<form:select path=""
											class="form-control chosen-select-no-results" id="sourceCode"
											onchange="getSchemeDetails(this);">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.sourceLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label "><spring:message
											code="scheme.master.schemecode" text="Scheme Code" /></label>
									<div class="col-sm-4 " onclick="return validationCheck(this)">
										<form:select path="" id="sourceName"
											class="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="project.master.dept" /> </label>
									<div class="col-sm-4">
										<form:select path=""
											cssClass="form-control chosen-select-no-results"
											id="dpDeptId">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.departmentsList}"
												var="departments">
												<form:option value="${departments.dpDeptid }"
													code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label for="" class="col-sm-2 control-label "><spring:message
											code="project.master.project.Status" /></label>
									<div class="col-sm-4">
										<form:select path=""
											class="form-control chosen-select-no-results" id="projStatus">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.getLevelData('PRS')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>

									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- End Each Section -->
					<!-- Start button -->
					<div class="text-center clear padding-10">
						<button class="btn btn-blue-2 search" id="searchProjecmaster"
							type="button">
							<i class="fa fa-search padding-right-5"></i>
							<spring:message code="works.management.search" text="" />
						</button>

						<button type="button" class="button-input btn btn-warning"
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='WmsProjectMaster.html'"
							id="button-Cancel">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
						<c:if
							test="${command.UADstatusForProject ne 'YES' || userSession.organisation.defaultStatus eq 'Y'}">
							<button class="btn btn-primary add"
								onclick="openAddProjectMaster('WmsProjectMaster.html','AddProjectMaster');"
								type="button">
								<i class="fa fa-plus-circle padding-right-5"></i>
								<spring:message code="works.management.add" text="" />
							</button>
						</c:if>
					</div>
					<!-- End button -->

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="projectDatatables">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="ser.no" text="Sr.No" /></th>
									<th width="15%" align="center"><spring:message
											code="project.master.projcode" text="" /></th>
									<th width="15%" align="center"><spring:message
											code="project.master.dept" text="" /></th>
									<th width="25%" align="center"><spring:message
											code="project.master.projname" text="" /></th>
									<th width="25%" align="center"><spring:message
											code="project.master.schemename" text="" /></th>

									<!-- Remove As Per SUDA UAT -->

									<%-- <th width="12%" align="center"><spring:message
											code="project.master.startdate" text="" /></th>
									<th width="12%" align="center"><spring:message
											code="project.master.enddate" text="" /></th> --%>
									<th width="15%" align="center"><spring:message
											code="works.management.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${command.UADstatusForProject eq 'YES' && userSession.organisation.defaultStatus ne 'Y'}">
										<c:forEach items="${command.projectMasterList}"
											var="projectMasterDto" varStatus="status">
											<tr>
												<td class="text-center">${status.count}</td>
												<td class="text-center">${projectMasterDto.projCode}</td>
												<td class="text-center">${projectMasterDto.departmentName}</td>
												<td>${projectMasterDto.projNameEng}</td>
												<td>${projectMasterDto.schemeName}</td>
												<!-- Remove As Per SUDA UAT -->
												<%-- <td align="center">${projectMasterDto.startDateDesc}</td>
										<td align="center">${projectMasterDto.endDateDesc}</td> --%>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														onClick="showGridOption(${projectMasterDto.projId} , 'V')"
														title="<spring:message code="works.management.view"></spring:message>">
														<i class="fa fa-eye"></i>
													</button>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${command.projectMasterList}"
											var="projectMasterDto" varStatus="status">
											<tr>
												<td class="text-center">${status.count}</td>
												<td class="text-center">${projectMasterDto.projCode}</td>
												<td class="text-center">${projectMasterDto.departmentName}</td>
												<td>${projectMasterDto.projNameEng}</td>
												<td>${projectMasterDto.schemeName}</td>
												<!-- Remove As Per SUDA UAT -->
												<%-- <td align="center">${projectMasterDto.startDateDesc}</td>
										<td align="center">${projectMasterDto.endDateDesc}</td> --%>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														onClick="showGridOption(${projectMasterDto.projId} , 'V')"
														title="<spring:message code="works.management.view"></spring:message>">
														<i class="fa fa-eye"></i>
													</button>
													<button type="button" class="btn btn-success btn-sm"
														onClick="showGridOption(${projectMasterDto.projId}, 'E')"
														title="<spring:message code="works.management.edit"></spring:message>">
														<i class="fa fa-pencil-square-o"></i>
													</button>
													<button type="button" class="btn btn-danger btn-sm"
														onClick="showGridOption(${projectMasterDto.projId} , 'D')"
														title="<spring:message code="works.management.delete"></spring:message>">
														<i class="fa fa-trash aria-hidden='true'"></i>
													</button>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<!-- End Each Section -->
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->
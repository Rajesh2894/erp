<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"	src="js/workflow/bpmbrmdeployment/bpmbrmdeploymentmaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->



<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="bpm.bpmbrmdeploymentmasterform" text="bpm.bpmbrmdeploymentmasterform" /></strong>
			</h2>

		</div>

		<div class="widget-content padding">



			<form:form action="BpmBrmDeploymentMaster.html"
				class="form-horizontal form" name="command" id="id_BpmBrmDeploymentMaster">


				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<!-- Start button -->

				<div class="text-center clear padding-10">
					<button type="submit" class="button-input btn btn-success"
						onclick="openAddBpmBrmDeploymentMaster('BpmBrmDeploymentMaster.html','AddBpmBrmDeploymentMaster');"
						name="button-Add" style="" id="button-submit">
						<spring:message code="bpm.add" text="Add" />
					</button>
				</div>
				<!-- End button -->

				<!--table responsive start-->
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_bpmBrmDeploymentMaster">
							<thead>
								<tr>
									<th><spring:message code="bpm.bpmRuntime" text="Bpm Runtime" /></th>
									<th><spring:message code="bpm.artifactType" text="Artifact Type" /></th>
									<th><spring:message code="bpm.groupId" text="Group Id" /></th>
									<th><spring:message code="bpm.artifactId" text="Artifact Id" /></th>
									<th><spring:message code="bpm.version" text="Version" /></th>
									<th><spring:message code="bpm.processId" text="Process Id" /></th>
									<th><spring:message code="bpm.status" text="Status" /></th>
									<th><spring:message code="bpm.action" text="Action" /></th>

								</tr>
							</thead>
							<tbody>
								<spring:message code="bpm.view" text="bpm.view" var="viewtitle"></spring:message>
								<spring:message code="bpm.edit" text="bpm.edit" var="edittitle"></spring:message>
								<c:forEach items="${command.bpmBrmDeploymentMasterDtos}" var="bpmbrmdeployment" varStatus="loop">
									<tr>
										<td align="center">${bpmbrmdeployment.bpmRuntime}</td>
										<td align="center">
											<spring:message code="bpm.artifactType.${bpmbrmdeployment.artifactType}" text="bpm.artifactType.${bpmbrmdeployment.artifactType}" /> 
										</td>
										<td align="left">${bpmbrmdeployment.groupId}</td>
										<td align="left">${bpmbrmdeployment.artifactId}</td>
										<td align="left">${bpmbrmdeployment.version}</td>
										<td align="left">${bpmbrmdeployment.processId}</td>
										<td align="center">${bpmbrmdeployment.status}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="${viewtitle}"
												onclick="modifyBpmBrmDeployment(${bpmbrmdeployment.id},'BpmBrmDeploymentMaster.html','ViewBpmBrmDeploymentMaster','V')">
												<i class="fa fa-eye"></i>
											</button>
											<%-- <button type="button" class="btn btn-warning btn-sm"
												title="${edittitle}"
												onclick="modifyBpmBrmDeployment(${bpmbrmdeployment.id},'BpmBrmDeploymentMaster.html','EditBpmBrmDeploymentMaster','E')">
												<i class="fa fa-pencil"></i>
											</button>  --%>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<!-- table responsive end -->
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->


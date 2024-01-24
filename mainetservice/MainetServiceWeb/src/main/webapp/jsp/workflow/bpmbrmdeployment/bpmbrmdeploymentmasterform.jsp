<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"	src="js/workflow/bpmbrmdeployment/bpmbrmdeploymentmaster.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="bpm.bpmbrmdeploymentmasterform"
						text="bpm.bpmbrmdeploymentmasterform" /></strong>
			</h2>
		</div>

		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<!-- Start Form -->
			<form:form action="BpmBrmDeploymentMaster.html" method="POST"
				name="bpmBrmDeploymentMasterForm" class="form-horizontal"
				id="id_BpmBrmDeploymentMasterForm" commandName="command">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="bpm.bpmbrmdeploymentmasterform.info"
										text="bpm.bpmbrmdeploymentmasterform.info" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="disabledRow"
									value="${not empty command.bpmBrmDeploymentMasterDto.id}"
									scope="page"></c:set>

								<c:choose>
									<c:when test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
										
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="bpmRuntime"><spring:message code="bpm.bpmRuntime" /></label>
											<div class="col-sm-4">
												<form:select path="bpmBrmDeploymentMasterDto.bpmRuntime"
													cssClass="form-control mandColorClass" id="bpmRuntime" onchange=""
													data-rule-required="true" disabled="${disabledRow}">
													<form:option value=""><spring:message code="bpm.select" text="bpm.select" /></form:option>
													<form:option value="jbpm">jBPM</form:option>
												</form:select>
											</div>
											
											<label class="col-sm-2 control-label required-control"
												for="artifactType"><spring:message code="bpm.artifactType" /></label>
											<div class="col-sm-4">
												<form:select path="bpmBrmDeploymentMasterDto.artifactType"
													cssClass="form-control mandColorClass" id="artifactType" onchange=""
													data-rule-required="true" disabled="${disabledRow}">
													<form:option value=""><spring:message code="bpm.select" text="bpm.select" /></form:option>
													<form:option value="bpm"><spring:message code="bpm.artifactType.bpm" text="bpm.artifactType.bpm" /></form:option>
													<form:option value="brm"><spring:message code="bpm.artifactType.brm" text="bpm.artifactType.brm" /></form:option>
												</form:select>
											</div>
										</div>
										
										<div class="form-group">
											<apptags:input labelCode="bpm.groupId"
												path="bpmBrmDeploymentMasterDto.groupId" isMandatory="true"
												isDisabled="true"></apptags:input>
											<apptags:input labelCode="bpm.artifactId"
												path="bpmBrmDeploymentMasterDto.artifactId"
												isMandatory="true" isDisabled="${disabledRow}"></apptags:input>
										</div>
										<div class="form-group">
											<apptags:input labelCode="bpm.version"
												path="bpmBrmDeploymentMasterDto.version" isMandatory="true"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
											
											<label class="col-sm-2 control-label required-control"
												for="status"><spring:message code="bpm.status" /></label>
											<div class="col-sm-4">
												<form:select path="bpmBrmDeploymentMasterDto.status"
													cssClass="form-control mandColorClass" id="status" onchange="" readonly="true"
													data-rule-required="true">
													<%-- disabled="${command.saveMode eq 'V' ? true : false }" --%>
													<%-- <form:option value=""><spring:message code="bpm.select" text="bpm.select" /></form:option> --%>
													<form:option value="Y"><spring:message code="bpm.status.yes" text="bpm.status.yes" /></form:option>
													<%-- <form:option value="N"><spring:message code="bpm.status.no" text="bpm.status.no" /></form:option> --%>
												</form:select>
												
											</div>
										</div>

										<div class="form-group">
											<apptags:input labelCode="bpm.processId"
												path="bpmBrmDeploymentMasterDto.processId"
												isMandatory="false" isDisabled="${disabledRow}"></apptags:input>
											<apptags:input labelCode="bpm.ruleFlowGroup"
												path="bpmBrmDeploymentMasterDto.ruleFlowGroup"
												isMandatory="false" isDisabled="${disabledRow}"></apptags:input>	
										</div>
										
										<c:if test="${command.saveMode eq 'E'}">
											<div class="form-group">
												<apptags:checkbox labelCode="bpm.notifyUsers" value="true" path="bpmBrmDeploymentMasterDto.notifyUsers"></apptags:checkbox>
											</div>
											
											<div class="form-group" id="departmentDiv">
			                    				<apptags:select labelCode="bpm.notifyToDepartment" items="${command.departments}"  path="bpmBrmDeploymentMasterDto.notifyToDepartment" 
			                    					selectOptionLabelCode="Select" isMandatory="false" showAll="true" isLookUpItem="false"></apptags:select>
											</div>
										</c:if>
										
									</c:when>
									<c:otherwise>
										
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="bpmRuntime"><spring:message code="bpm.bpmRuntime" /></label>
											<div class="col-sm-4">
												<form:select path="bpmBrmDeploymentMasterDto.bpmRuntime"
													cssClass="form-control mandColorClass" id="bpmRuntime" onchange="" 
													data-rule-required="true" disabled="${disabledRow}">
													<form:option value=""><spring:message code="bpm.select" text="bpm.select" /></form:option>
													<form:option value="jbpm">jBPM</form:option>

												</form:select>
											</div>
											
											<label class="col-sm-2 control-label required-control"
												for="artifactType"><spring:message code="bpm.artifactType" /></label>
											<div class="col-sm-4">
												<form:select path="bpmBrmDeploymentMasterDto.artifactType"
													cssClass="form-control mandColorClass" id="artifactType" onchange="setGroupId(this)"
													data-rule-required="true" disabled="${disabledRow}">
													<form:option value=""><spring:message code="bpm.select" text="bpm.select" /></form:option>
													<form:option value="bpm"><spring:message code="bpm.artifactType.bpm" text="bpm.artifactType.bpm" /></form:option>
													<form:option value="brm"><spring:message code="bpm.artifactType.brm" text="bpm.artifactType.brm" /></form:option>
												</form:select>
											</div>
										</div>
									
										<div class="form-group">
											<apptags:input labelCode="bpm.groupId"
												path="bpmBrmDeploymentMasterDto.groupId" isMandatory="true"
												isReadonly="true"></apptags:input>
											<apptags:input labelCode="bpm.artifactId"
												path="bpmBrmDeploymentMasterDto.artifactId" cssClass="lower-case"
												isDisabled="${disabledRow}" isMandatory="true"></apptags:input>
										</div>

										<div class="form-group">
											<apptags:input labelCode="bpm.version"
												path="bpmBrmDeploymentMasterDto.version" isMandatory="true"
												isDisabled="${disabledRow}"></apptags:input>
											
											<label class="col-sm-2 control-label required-control"
												for="status"><spring:message code="bpm.status" /></label>
											<div class="col-sm-4">
												<form:select path="bpmBrmDeploymentMasterDto.status"
													cssClass="form-control mandColorClass" id="status" onchange="" readonly="true"
													data-rule-required="true">
													<%-- disabled="${command.saveMode eq 'V' ? true : false }" --%>
													<%-- <form:option value=""><spring:message code="bpm.select" text="bpm.select" /></form:option> --%>
													<form:option value="Y"><spring:message code="bpm.status.yes" text="bpm.status.yes" /></form:option>
													<%-- <form:option value="N"><spring:message code="bpm.status.no" text="bpm.status.no" /></form:option> --%>
												</form:select>
												
												
											</div>
										</div>

										<div class="form-group">
											<apptags:input labelCode="bpm.processId"
												path="bpmBrmDeploymentMasterDto.processId"
												isDisabled="${disabledRow}" isMandatory="false"></apptags:input>

											<apptags:input labelCode="bpm.ruleFlowGroup"
												path="bpmBrmDeploymentMasterDto.ruleFlowGroup"
												isDisabled="${disabledRow}" isMandatory="false"></apptags:input>
										</div>
										
									</c:otherwise>
								</c:choose>
							</div>
						</div>

					</div>
				</div>


				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="button-input btn btn-success"
							onclick="Proceed(this)" name="button-submit" style=""
							id="btnSave">
							<spring:message code="bpm.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<apptags:resetButton></apptags:resetButton>
					</c:if>
					<apptags:backButton url="BpmBrmDeploymentMaster.html"></apptags:backButton>
				</div>
				<!-- End button -->
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>

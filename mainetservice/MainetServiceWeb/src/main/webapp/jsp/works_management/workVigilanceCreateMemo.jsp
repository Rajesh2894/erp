<!-- Start JSP Necessary Tags -->
<%@page import="com.abm.mainet.common.utility.UserSession"%>
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


<!-- End JSP Necessary Tags -->
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.vigilance.create.inspection"
					text="Work inspection" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="WorkVigilance.html"></apptags:helpDoc>

			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;"></div>
			<form:form action="WorkVigilance.html" cssClass="form-horizontal"
				name="workVigilanceForm" id="workVigilanceForm">
				<%-- 	<form:hidden path="removeFileById" id="removeFileById" />
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="referenceDetailsById" id="referenceDetailsById" /> --%>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<!-- Start Of Create Work Inspection Section -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="work.vigilance.create.inspection.details"
										text="Work inspection Details" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control "><spring:message
											code="project.master.projname" text="Project Name" /></label>
									<div class="col-sm-4">
										<form:select path="vigilanceDto.projId"
											cssClass="form-control chosen-select-no-results"
											id="projectId" disabled="${command.saveMode eq 'V'}"
											onchange="getprojectName();">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.wmsprojectDto}"
												var="activeProjName">
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
											disabled="${command.saveMode eq 'V'}"
											onchange="getWorkRACcode();">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<%-- <c:forEach items="${command.wmsprojectDto}"
												var="activeProjName">
												<form:option value="${activeProjName.workId }">${activeProjName.workName}</form:option>
											</c:forEach> --%>

										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="work.vigilance.agreement.from.date"
											text="Agreement From Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="contractFromDate"
												cssClass="form-control datepicker mandColorClass"
												id="agreementFromdate" readonly="true"
												disabled="${command.saveMode eq 'V'}" />
											<label class="input-group-addon" for="agreementFromdate"><i
												class="fa fa-calendar"></i><span class="hide"> <spring:message
														code="" text="icon" /></span>
											<!-- <input type="hidden"
												id=agreementFromdate></label> -->
										</div>
									</div>

									<label class="col-sm-2 control-label"><spring:message
											code="work.vigilance.inspection.agreement.to.date"
											text="Agreement To Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="contractToDate"
												cssClass="form-control datepicker mandColorClass"
												id="agreementTodate" readonly="true"
												disabled="${command.saveMode eq 'V'}" />
											<label class="input-group-addon" for="agreementTodate"><i
												class="fa fa-calendar"></i><span class="hide"> <spring:message
														code="" text="icon" /></span>
											<!-- <input type="hidden"
												id=agreementTodate> --></label>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="work.ra.code.select" text="RA Code" /></label>
									<div class="col-sm-4">
										<form:select path="vigilanceDto.referenceNumber"
											cssClass="form-control chosen-select-no-results"
											id="racodeCode" disabled="${command.saveMode eq 'V'}"
											onchange="getWorkMb(this);">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.worksrabilldto}" var="list">
												<form:option value="${list.raCode}" code="${list.raCode}">${list.raCode}</form:option>
											</c:forEach>


										</form:select>
									</div>

								</div>
							</div>
						</div>
					</div>

					<div id="EstimateTagDiv"></div>


					<!-- Start Inspection  Details  -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"><spring:message
										code="work.vigilance.inspection.details"
										text="Inspection Details" /></a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="work.vigilance.inspection.required"
											text="Inspection Required" /></label>
									<div class="col-sm-4">
										<form:select path="vigilanceDto.status"
											cssClass="form-control" id="inspRequired"
											disabled="${command.saveMode eq 'V'}" onchange="getInspectionStatus();">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<form:option value="Y">
												<spring:message
													code="work.vigilance.inspection.required.yes" text="Yes" />
											</form:option>
											<form:option value="N">
												<spring:message code="work.vigilance.inspection.required.no"
													text="No" />
											</form:option>
										</form:select>
									</div>
									<label class="col-sm-2 control-label required-control "><spring:message
											code="work.vigilance.date" text="Work inspection Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="vigilanceDto.inspectionDate"
												cssClass="form-control datepicker mandColorClass"
												id="inspectiondate" readonly="true"
												disabled="${command.saveMode eq 'V'}" />
											<label class="input-group-addon" for="inspectiondate"><i
												class="fa fa-calendar"></i><span class="hide"> <spring:message
														code="" text="icon" /></span><input type="hidden"
												id="inspectiondate"></label>
										</div>
									</div>

								</div>
								<div class="form-group">

									<c:if test="${command.saveMode eq 'V'}">
										<apptags:textArea
											labelCode="work.vigilance.comments.and.remarks"
											path="vigilanceDto.memoDescription" isMandatory="true"
											isReadonly="true"></apptags:textArea>
									</c:if>
									<c:if test="${command.saveMode ne 'V'}">
										<apptags:textArea
											labelCode="work.vigilance.comments.and.remarks"
											path="vigilanceDto.memoDescription" isMandatory="true"></apptags:textArea>
									</c:if>
									<label class="col-sm-2 control-label required-control "><spring:message
											code="work.vigilance.memo.type" text="Inspection Type" /></label>
									<div class="col-sm-4">
										<form:select path="vigilanceDto.memoType" id="memoType"
											cssClass="form-control mandColorClass"
											disabled="${command.saveMode eq 'V'}">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<form:option value="A">
												<spring:message code="work.vigilance.actionable"
													text="Actionable" />
											</form:option>
											<form:option value="I">
												<spring:message code="work.vigilance.informational"
													text="Informational" />
											</form:option>
										</form:select>

									</div>

								</div>
							</div>
						</div>
					</div>
					<!-- End Inspection  Details  -->


					<!-- Start Of File Upload Section -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="work.order.documents" text="Work Order Documents" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:if test="${fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteDoc">
											<tr>
												<th width="" align="center"><spring:message
														code="ser.no" text="" /><input type="hidden" id="srNo"></th>
												<th scope="col" width="64%" align="center"><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th scope="col" width="30%" align="center"><spring:message
														code="scheme.view.document" /></th>

												<c:if test="${command.saveMode ne 'V'}">
													<th scope="col" width="8%"><spring:message
															code="works.management.action" text=""></spring:message></th>
												</c:if>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${e+1}</td>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="WorkVigilance.html?Download" /></td>

													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center"><a href='#' id="deleteFile"
															onclick="return false;" class="btn btn-danger btn-sm"><i
																class="fa fa-trash"></i></a> <form:hidden path=""
																value="${lookUp.attId}" /></td>
													</c:if>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>

								<div id="uploadTagDiv">
									<div class="table-responsive">
										<c:set var="d" value="0" scope="page" />
										<c:if test="${command.saveMode ne 'V'}">
											<table class="table table-bordered table-striped"
												id="attachDoc">
												<tr>
													<th><spring:message
															code="work.estimate.document.description"
															text="Document Description" /></th>
													<th><spring:message code="work.estimate.upload"
															text="Upload Document" /></th>
													<th scope="col" width="8%"><a
														onclick='fileCountUpload(this);'
														class="btn btn-blue-2 btn-sm addButton"><i
															class="fa fa-plus-circle"></i></a></th>
												</tr>

												<tr class="appendableClass">
													<td><form:input path="attachments[${d}].doc_DESC_ENGL"
															class=" form-control" /></td>

													<td class="text-center"><apptags:formField
															fieldType="7"
															fieldPath="attachments[${d}].uploadedDocumentPath"
															currentCount="${d}" showFileNameHTMLId="true"
															folderName="${d}" fileSize="COMMOM_MAX_SIZE"
															isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
														</apptags:formField></td>

													<td class="text-center"><a href='#' id="0_file_${d}"
														onclick="doFileDelete(this)"
														class='btn btn-danger btn-sm delButton'><i
															class="fa fa-trash"></i></a></td>
												</tr>

												<c:set var="d" value="${d + 1}" scope="page" />
											</table>
										</c:if>
									</div>
								</div>
							</div>

						</div>

						<!-- End Of File Upload Section -->
					</div>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveVigilance(this);">
							<spring:message code="" text="Save" />
						</button>

						<%-- <apptags:resetButton></apptags:resetButton> --%>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" id="button-Cancel"
						onclick="backWorkVigilance();">
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
		</div>

	</div>
</div>


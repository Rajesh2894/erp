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
	src="js/works_management/workMbApproval.js"></script>
<script type="text/javascript"
	src="js/works_management/measurementBook.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	jQuery('#tab1').addClass('active');
</script>

<!-- Defect #80503 -->
<ol class="breadcrumb">
	<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
	<li><a href="javascript:void(0);"><spring:message text="Public Works Department" code="public.works.dept" /></a></li>
	<li><a href="javascript:void(0);"><spring:message text="Transactions" code="works.dept.transaction" /></a></li>
	<li class="active"><spring:message text="RA Bill Approval" code="ra.bill.generation" /></li>
</ol>
<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.mb.approval.title"
					text="RA Bill Approval" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="works.fiels.mandatory.message" /></span>
			</div>
			<form:form action="WorkMBApproval.html" cssClass="form-horizontal"
				name="WorkMBApproval" id="WorkMBApproval">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<jsp:include page="/jsp/works_management/workRAApprovalTab.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="mbTaskName" id="mbTaskName" />
				<form:hidden path="removeFileByIds" id="removeFileByIds" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="work.ra.bill.details" text="RA Bill Details" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse ">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="project.master.dept" /></label>
									<div class="col-sm-4">
										<form:input path="department.dpDeptdesc" id="department"
											type="text" class="form-control " disabled="true" />
									</div>
								</div>
								<div class="form-group">
									<apptags:textArea labelCode="project.master.projname"
										path="definitionDto.projName" isReadonly="true"></apptags:textArea>

									<apptags:input labelCode="work.def.workname"
										path="definitionDto.workName" isReadonly="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:date labelCode="work.order.contract.from.date"
										datePath="contractFromDate" fieldclass="" isDisabled="true"></apptags:date>
									<apptags:date labelCode="work.order.contract.to.date"
										datePath="contractToDate" fieldclass="" isDisabled="true"></apptags:date>
								</div>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="rabill.viwe.approval.process" /></label>
						<div class="col-sm-4">
							<a class="text-center" href="#"
								onclick="viewRADetailsForApproval(${command.raBillDto.raId},'AP');"
								style="text-decoration: underline; text-align: center;"><span><c:out
										value="${command.raBillDto.raSerialNo}"></c:out></span></a>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="work.mb.approval.mbdetails" text="Details Of MB" /> </a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse ">
							<div class="panel-body">
								<div class="table-responsive clear">
									<table class="table table-striped table-bordered" id="">
										<thead>
											<tr>
												<th width="25%" align="center"><spring:message
														code="work.approvla.mb.number" text="MB No."></spring:message></th>
												<th width="25%" align="center"><spring:message
														code="work.approvla.mb.date" text="MB Date"></spring:message></th>
												<th width="25%" align="center"><spring:message
														code="work.order.employee.enginner.name"
														text="Engineer Name"></spring:message></th>
												<th width="12%" align="center"><spring:message
														code="work.abstract.sheet.report"
														text="Abstract Sheet Report"></spring:message></th>
												<th width="13%" align="center"><spring:message
														code="works.management.action" text="Action"></spring:message></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${command.measureMentBookMastDtosList}"
												var="mbDetails">
												<tr>
													<td>${mbDetails.workMbNo}</td>
													<td>${mbDetails.mbTakenDate}</td>
													<td>${mbDetails.workAssigneeName}</td>
													<td class="text-center">
														<button type="button" class="btn btn-warning btn-sm"
															onClick="viewMbAbstractSheet(${mbDetails.workMbId});"
															title='<spring:message code="wms.abstract.report"></spring:message>'>
															<i class="fa fa-print"></i>
														</button>
													</td>
													<td class="text-center"><c:choose>
															<c:when
																test="${(command.mbTaskName eq 'Initiator') && (command.flagForSendBack eq 'SEND_BACK')}">
																<button type="button" class="btn btn-success btn-sm"
																	onClick="getEditMB(${mbDetails.workMbId},'${command.estimateMode}');"
																	title="Edit Work Order">
																	<i class="fa fa-pencil"></i>
																</button>
															</c:when>
															<c:otherwise>
																<button type="button" class="btn btn-blue-2 btn-sm"
																	onClick="getEditMB(${mbDetails.workMbId},'${command.estimateMode}');"
																	title='<spring:message code="works.management.view"></spring:message>'>
																	<i class="fa fa-eye"></i>
																</button>
															</c:otherwise>
														</c:choose></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${not empty command.vigilanceDtoList }">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a3" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a3"><spring:message
											code=" " text="Inspection Level Wise Details" /></a>
								</h4>
							</div>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="table-responsive clear">
										<table class="table table-striped table-bordered" id="">
											<thead>
												<tr>
													<th width="5%" align="center"><spring:message
															code="ser.no" text="Sr.No" /></th>
													<th width="25%" align="center"><spring:message code=""
															text="Date of Inspection" /></th>
													<th width="20%" align="center"><spring:message code=""
															text="Role Of Inspector" /></th>
													<th width="20%" align="center"><spring:message code=""
															text="Inspection Type" /></th>
													<th width="30%" align="center"><spring:message code=""
															text="Inspection Comments" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${command.vigilanceDtoList}" var="mastDto"
													varStatus="status">
													<tr>
														<td>${status.count}</td>
														<td>${mastDto.inspectDateDesc}</td>
														<td>${mastDto.dsgName}</td>
														<td>${mastDto.memoTypeDesc}</td>
														<td>${mastDto.memoDescription}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<!-- Start Of Create Memo Section -->
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
										<form:select path="vigilanceDto.inspRequire"
											cssClass="form-control" id="inspRequired"
											onchange="getInspRequired();"  disabled="${command.completedFlag eq 'Y'}">
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
									<label class="col-sm-2 control-label "><spring:message
											code="work.vigilance.inspection.done.date"
											text="Inspection Done Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="vigilanceDto.inspectionDate"
												cssClass="form-control datepicker mandColorClass"
												id="inspectionDate" readonly="true" disabled="true" />
											<label class="input-group-addon" for="inspectionDate"><i
												class="fa fa-calendar"></i><span class="hide"> <spring:message
														code="" text="icon" /></span><input type="hidden"
												id=inspectionDate></label>
										</div>
									</div>

								</div>

								<div class="form-group">
									<apptags:textArea
										labelCode="work.vigilance.comments.and.remarks"
										path="vigilanceDto.memoDescription" isMandatory="true" isDisabled="${command.completedFlag eq 'Y'}">
										</apptags:textArea>

									<label class="col-sm-2 control-label required-control "><spring:message
											code="work.vigilance.memo.type" text="Inspection Type" /></label>
									<div class="col-sm-4">
										<form:select path="vigilanceDto.memoType"
											cssClass="form-control mandColorClass " id="memoType"
											onchange="getMemoType();">
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
										<input type="hidden" id="memoTypeValue"
											name="vigilanceDto.memoType" value="">
									</div>

								</div>
								<!-- Start Of File Upload Section -->

								<c:if test="${fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteDocument">
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
															actionUrl="WorkMBApproval.html?Download" /></td>

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
										<c:if test="${command.saveMode ne 'V' || command.completedFlag ne 'Y'}">
											<table class="table table-bordered table-striped"
												id="attachDoc">
												<tr>
													<th><spring:message
															code="work.estimate.document.description"
															text="Document Description" /></th>
													<th><spring:message code="work.estimate.upload"
															text="Upload Document" /></th>
													<th scope="col" width="8%"><a
														onclick='fileCountInspectionUpload(this);'
														class="btn btn-blue-2 btn-sm addButton" title="Add"><i
															class="fa fa-plus-circle"></i></a></th>
												</tr>

												<tr class="appendableUploadClass">
													<td><form:input
															path="inspectionAttachment[${d}].doc_DESC_ENGL"
															class=" form-control" /></td>

													<td class="text-center"><apptags:formField
															fieldType="7"
															fieldPath="inspectionAttachment[${d}].uploadedDocumentPath"
															currentCount="${d}" showFileNameHTMLId="true"
															folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
															isMandatory="false"
															maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
															validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
															callbackOtherTask="otherTask();">
														</apptags:formField><small class="text-blue-2" style="font-size: unset;"> <spring:message code="wms.img.validatation"
						                          	   text="(Upload Image File upto 5 MB)" /></small></td>

													<td class="text-center"><a href='#' id="0_file_${d}"
														onclick="doFileDelete(this)" title="Delete"
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
						<!-- End Of Create Memo Section -->
					</div>
					<div class="text-center clear padding-10">
					<c:if test="${command.completedFlag ne 'Y' }">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveInspectionDetails(this);">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="" />
						</button>
						</c:if>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>
					</div>
			</form:form>
		</div>
	</div>
</div>


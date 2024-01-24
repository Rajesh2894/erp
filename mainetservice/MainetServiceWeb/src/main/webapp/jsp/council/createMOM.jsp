<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/council/councilMOM.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.mom.entry.title" text="MOM Entry" />
			</h2>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="CouncilMOM.html" cssClass="form-horizontal"
				id="momMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="oMMFlag" id="oMMFlag" />
				<form:hidden path = "couMeetingMasterDto.meetingId" id ="meetingId"/>
				<form:hidden path="removeFileById" id="removeFileById" />
				<div class="form-group">
					<label class="control-label col-sm-2 " for="MeetingType"><spring:message
							code="council.meetingType" text="Meeting Type"></spring:message></label>
					<c:set var="baseLookupCode" value="MPT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="couMeetingMasterDto.meetingTypeId"
						cssClass="form-control required-control" isMandatory="true"
						disabled="${command.disableSelect}"
						selectOptionLabelCode="selectdropdown" hasId="true"
						changeHandler="getMeetingNos()" />

					<label class="col-sm-2 control-label "><spring:message
							code="council.meeting.meetingNo" text="Meeting Number" /></label>
					<div class="col-sm-4">
						<form:select path="couMeetingMasterDto.meetingNo"
							class="form-control chosen-select-no-results" id="meetingNo"
							disabled="${command.disableSelect}"
							onchange="getMeetingDetails(this);">
							<form:option value="">
								<spring:message code='council.management.select' />
							</form:option>
							<c:forEach items="${command.councilMeetingMasterDtoList}"
								var="meetingData">
								<form:option value="${meetingData.meetingNo}">${meetingData.meetingNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<c:if test="${command.oMMFlag ne 'Y'}">
					<div class="form-group">
						<label class="col-sm-2 control-label "><spring:message
								code="council.proposal.no" text="Proposal Number" /></label>
						<div class="col-sm-4">
							<form:select path="meetingMOMDtos[0].proposalId"
								class="form-control" id="proposalId"
								onchange="getProposalEditor(this);" disabled="${command.saveMode eq 'VIEW' }">
								<form:option value="0">
									<spring:message code="council.management.select" />
								</form:option>
								<c:forEach items="${command.meetingMOMDtos}" var="meetingMOMDto">
									<form:option value="${meetingMOMDto.proposalId}">${meetingMOMDto.proposalNo}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<label class="col-sm-2 control-label "><spring:message
												code="council.mom.actionTaken" text="Action Taken" /></label>
						<div class="col-sm-4">
					<form:select path="meetingMOMDtos[0].status"
						cssClass="form-control chosen-select-no-results" id="status0"
						disabled="${command.saveMode eq 'VIEW' }">
						<form:option value="0">
							<spring:message code='council.dropdown.select' />
						</form:option>
						<c:forEach items="${command.getLevelData('PPS')}" var="lookUp">
							<form:option value="${lookUp.lookUpCode}"
								code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
					</div>
						
	
					</div>
				</c:if>
				<!-- Document code set -->
				<div class="form-group">
					<c:set var="count" value="${count + 1}" scope="page" />

					<label for="" class="col-sm-2 control-label"> <spring:message
							code="council.member.documents" text="Documents" /></label>
					<c:set var="count" value="0" scope="page"></c:set>
					<div class="col-sm-4">
						<c:if
							test="${command.saveMode eq 'ADD'  || command.saveMode eq 'EDIT' }">
							<apptags:formField fieldType="7"
								fieldPath="attachments[${count}].uploadedDocumentPath"
								currentCount="${count}" folderName="${count}"
								fileSize="COUNCIL_MOM_MAX_SIZE" showFileNameHTMLId="true"
								isMandatory="true" maxFileCount="CHECKLIST_VRFN_ALL"
								validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
							</apptags:formField>
							<div class="col-sm-12">
								<small class="text-blue-2 "> <spring:message
										code="council.mom.fileUploadNote"
										text="(Upload File upto 120MB and only pdf,doc,docx,jpeg,jpg,png,gif)" />
								</small>
							</div>
						</c:if>

						<%-- <c:if
							test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
							<c:forEach items="${command.attachDocsList}" var="lookUp">
							<input type="hidden" name="deleteFileId"
								value="${lookUp.attId}">
							<input type="hidden" name="downloadLink"
								value="${lookUp}">
							<apptags:filedownload
								filename="${lookUp.attFname}"
								filePath="${lookUp.attPath}"
								actionUrl="CouncilMOM.html?Download"></apptags:filedownload>
							</c:forEach>
						</c:if> --%>

					</div>
				</div>
				<c:if test="${command.oMMFlag ne 'Y'}">
					<div class="text-center clear padding-10">
						<button type="button" class="btn btn-primary"
						
							onclick="addProposal('Y',${command.couMeetingMasterDto.meetingId},${command.couMeetingMasterDto.agendaId});"
							title="Additional Proposal">
							<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.additional.proposal" text="Add Additional Proposal" />
						</button>
					</div>
				</c:if>

				<!-- <h4>Create M.O.M.</h4> -->
				<%-- <div class="form-group">
							<apptags:input labelCode="Meeting Type"
								path="couMeetingMasterDto.meetingTypeName" cssClass="form-control"
								isDisabled = "true"></apptags:input>
								
							<apptags:input labelCode="Meeting Number"
								path="couMeetingMasterDto.meetingNo" cssClass="form-control"
								isDisabled="true"></apptags:input>
						</div>
						<div class="form-group">
							<apptags:input labelCode="Meeting Date"
								path="couMeetingMasterDto.meetingDateDesc" cssClass="form-control"
								isDisabled = "true"></apptags:input>
								
							<apptags:input labelCode="Meeting Place"
								path="couMeetingMasterDto.meetingPlace" cssClass="form-control"
								isDisabled="true"></apptags:input>
						</div>
						<div class="form-group">
							<apptags:input labelCode="Meeting Time"
								path="couMeetingMasterDto.meetingTime" cssClass="form-control"
								isDisabled = "true"></apptags:input>
						</div> --%>


				<!-- Agenda Item details Table by proposal -->
				<%-- <div id="a2" class="panel-collapse collapse in">
					<div class="panel-body">
						<h4>Agenda Items</h4>

						<c:set var="d" value="0" scope="page" />
						<table class="table table-bordered margin-top-10"
							id="momDataTable">
							<thead>
								<tr>
									<th width="3%" align="center"><spring:message
											code="council.srno" text="Sr.No" /></th>
									<th width="15%"><spring:message
											code="council.mom.detailsOfProposal"
											text="Details Of Proposal" /></th>
									<th width="8%"><spring:message code="council.proposal.no"
											text="Proposal Number" /></th>
									<th width="10%"><spring:message
											code="council.mom.resolutionComment"
											text="Resolution Comments" /></th>
									<th width="5%"><spring:message
											code="council.mom.actionTaken" text="Action Taken" /></th>

								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(command.meetingMOMDtos) > 0}">
										<c:forEach var="meetingMOM" items="${command.meetingMOMDtos}"
											varStatus="status">
											<tr>
												<td class="text-center">${status.count}</td>
												<td><form:input
														path="meetingMOMDtos[${d}].proposalDetails"
														cssClass="form-control text-center" readonly="true" /></td>

												<td><form:input path="meetingMOMDtos[${d}].proposalNo"
														cssClass="form-control text-center" readonly="true" /></td>

												<td><form:textarea id="resolutionComments${d}"
														path="meetingMOMDtos[${d}].resolutionComments"
														class="form-control"
														readonly="${command.saveMode eq 'VIEW'}" /></td>

												<td><form:select path="meetingMOMDtos[${d}].status"
														cssClass="form-control chosen-select-no-results"
														id="status${d}" disabled="${command.saveMode eq 'VIEW' }">
														<form:option value="0">
															<spring:message code='council.dropdown.select' />
														</form:option>
														<c:forEach items="${command.getLevelData('PPS')}"
															var="lookUp">
															<form:option value="${lookUp.lookUpCode}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</c:when>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div> --%>

				<!-- Start button -->
				<div class="form-group">
				
				</div>
				<div id="praposal">
				</div>
				<form:hidden path="resolutionComments"
					cssClass="form-control mandColorClass" id="resolutionComments"
					value="${String1}" />
				
				<c:if test="${command.attachDocsList ne null && not empty command.attachDocsList && (command.saveMode eq 'EDIT'  || command.saveMode eq 'VIEW')}">
				
					<div class="form-group">
						<label for="" class="col-sm-2 control-label"> <spring:message
											code="audit.upload.document" text="Attach Documents" /></label>						
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocuments">
									<tr>
										<th><spring:message code="scheme.document.name" text="Document Name" /></th>
										<th><spring:message code="scheme.view.document" text="View Documents" /></th>
										<th><spring:message code="council.upload.date" text="Upload Date" /></th>
										<c:if test="${command.attachDocsList ne null && not empty command.attachDocsList && command.saveMode eq 'EDIT'}">
										<th><spring:message code="scheme.action" text="Action"></spring:message></th>
										</c:if>																		
									</tr>
									<c:forEach items="${command.attachDocsList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}" dmsDocId="${lookUp.dmsDocId}"
													filePath="${lookUp.attPath}"
													actionUrl="CouncilMOM.html?Download" /></td>
													
											<fmt:formatDate pattern="dd-MM-yyyy hh:mm a"
														value="${lookUp.attDate}" var="date" />
													<td><c:out value="${date}"></c:out></td>
											<c:if test="${command.attachDocsList ne null && not empty command.attachDocsList && command.saveMode eq 'EDIT'}">
											<td class="text-center"><a href='#' id="deleteFile"
												onclick="return false;" class="btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a> <form:hidden path=""
													value="${lookUp.attId}" /></td>	
											</c:if>										
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</c:if>

				<div class="text-center">
					<c:if test="${command.saveMode ne 'VIEW'}">
						<button type="button" class="btn btn-success" title="Submit"
							onClick="createMOM(this);">
							<spring:message code="council.button.submit" text="Submit" />
							<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
						</button>

						<button type="button" onclick="resetMOMForm(this);"
							class="btn btn-warning" title="Reset">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="council.button.reset" text="Reset" />
						</button>
					</c:if>



					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backMOMForm();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="council.button.back" text="Back" />
					</button>

				</div>
				<!-- End button -->
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->
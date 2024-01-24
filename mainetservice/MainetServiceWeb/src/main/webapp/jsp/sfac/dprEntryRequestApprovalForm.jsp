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
<script src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/sfac/dprEntryRequestForm.js"></script>

<style>
table.crop-details-table tbody tr td>input[type="checkbox"] {
	margin: 0.5rem 0 0 -0.5rem;
}

.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}

.charCase {
	text-transform: uppercase;
}

#udyogAadharApplicable, #isWomenCentric {
	margin: 0.6rem 0 0 0;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.dpr.title.review"
					text="Detailed Project Report Review Form" />
			</h2>
			<apptags:helpDoc url="DPREntryApprovalForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="dprEntryForm" action="DPREntryApprovalForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#dprDetails">
									<spring:message code="sfac.dpr.entry"
										text="Detailed Project Report Entry" />
								</a>
							</h4>
						</div>
						<div id="dprDetails" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.fpoName" text="FPO Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.fpoName" id="fpoName"
											class="form-control" disabled="true" />
									</div>

									<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.cbbo.name" text="CBBO Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.cbboName" id="cbboName"
											class="form-control" disabled="true" />
									</div>
								</div>
								<div class="form-group">

									<label class="col-sm-2 control-label"><spring:message
											code="sfac.dpr.reviewer.name" text="Reviewer Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.iaName" id="iaName" class="form-control"
											disabled="true" />
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.dpr.submission.date" text="DPR Submission Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.dateOfSubmission" type="text"
												class="form-control datepicker mandColorClass"
												id="dateOfSubmission" placeholder="dd/mm/yyyy"
												readonly="true"
												disabled="${command.viewMode eq 'V' ? true : false }" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>

									</div>

								</div>



								<div class="form-group">


									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.dpr.resubmission.date"
											text="Revised DPR Submission Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.dateOfResubmission" type="text"
												class="form-control datepicker mandColorClass"
												id="dateOfResubmission" placeholder="dd/mm/yyyy"
												readonly="true"
												disabled="${command.viewMode eq 'V' ? true : false }" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>

									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.pm.licenseDocUpload" text="Document Upload" /></label>
									<div class="col-sm-4">


										<c:if test="${command.viewMode ne 'V'}">
											<apptags:formField fieldType="7"
												fieldPath="dto.attachments[0].uploadedDocumentPath"
												currentCount="0" showFileNameHTMLId="true"
												folderName="bpdfg0" fileSize="CARE_COMMON_MAX_SIZE"
												isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="ALL_UPLOAD_VALID_EXTENSION">
											</apptags:formField>
										</c:if>
										<c:if
											test="${command.dto.attachDocsList[0] ne null  && not empty command.dto.attachDocsList[0] }">
											<input type="hidden" name="deleteFileId"
												value="${command.dto.attachDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.dto.attachDocsList[0]}">
											<apptags:filedownload
												filename="${command.dto.attachDocsList[0].attFname}"
												filePath="${command.dto.attachDocsList[0].attPath}"
												actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
											
										</c:if>


									</div>


								</div>

							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#dprSecDetails">
									<spring:message code="sfac.dpr.section.details"
										text="DPR Section Details" />
								</a>
							</h4>
						</div>
						<div id="dprSecDetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="dprSecDetailsTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th width="25%"><spring:message code="sfac.dpr.section"
													text="DPR Section" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseDocUpload"
													text="Document Upload" /></th>

											<th width="15%"><spring:message
													code="sfac.dpr.section.score" text="DPR Section Score" /></th>
											<th><spring:message code="sfac.drp.remark" text="Remark" /></th>


										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.dprEntryDetailsDtos)>0 }">
												<c:forEach var="dto"
													items="${command.dto.dprEntryDetailsDtos}"
													varStatus="status">
													<tr class="appendableDPRSecDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>

														<td><div>
																<c:set var="baseLookupCode" value="DPR" />
																<form:select
																	path="dto.dprEntryDetailsDtos[${d}].dprSection"
																	class="form-control chosen-select-no-results"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	id="dprSection${d}">
																	<form:option value="0">
																		<spring:message code="sfac.select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select>

															</div></td>

														<td><c:if test="${command.viewMode ne 'V'}">
																<apptags:formField fieldType="7"
																	fieldPath="dto.dprEntryDetailsDtos[${d}].attachmentsDet[${d}].uploadedDocumentPath"
																	currentCount="${d}" showFileNameHTMLId="true"
																	folderName="${d}" fileSize="CARE_COMMON_MAX_SIZE"
																	isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="ALL_UPLOAD_VALID_EXTENSION">
																</apptags:formField>
															</c:if> <c:if
																test="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0] ne null  && not empty command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0] }">
																<input type="hidden" name="deleteFileId"
																	value="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0].attId}">
																<input type="hidden" name="downloadLink"
																	value="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0]}">
																<apptags:filedownload
																	filename="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0].attFname}"
																	filePath="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0].attPath}"
																	actionUrl="DPREntryForm.html?Download"></apptags:filedownload>
															</c:if> </td>

														<td><form:input
																path="dto.dprEntryDetailsDtos[${d}].dprScore"
																id="dprScore${d}" onblur="calculateTotal('${d}');"
																onkeypress="return hasAmount(event, this, 3, 3)"
																class="form-control" /></td>
														<td><form:input
																path="dto.dprEntryDetailsDtos[${d}].remark"
																id="remark${d}" class="form-control alphaNumeric" /></td>




													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableDPRSecDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>

													<td><div>
															<c:set var="baseLookupCode" value="DPR" />
															<form:select
																path="dto.dprEntryDetailsDtos[${d}].dprSection"
																class="form-control chosen-select-no-results"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="dprSection${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select>

														</div></td>


													<td><c:if test="${command.viewMode ne 'V'}">
															<apptags:formField fieldType="7"
																fieldPath="dto.dprEntryDetailsDtos[${d}].attachmentsDet[${d}].uploadedDocumentPath"
																currentCount="${d}" showFileNameHTMLId="true"
																folderName="${d}" fileSize="CARE_COMMON_MAX_SIZE"
																isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="ALL_UPLOAD_VALID_EXTENSION">
															</apptags:formField>
														</c:if> <c:if
															test="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0] ne null  && not empty command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0]}">
															<apptags:filedownload
																filename="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0].attFname}"
																filePath="${command.dto.dprEntryDetailsDtos[d].attachDocsListDet[0].attPath}"
																actionUrl="DPREntryForm.html?Download"></apptags:filedownload>
														</c:if> </td>



													<td><form:input
															path="dto.dprEntryDetailsDtos[${d}].dprScore"
															id="dprScore${d}"
															onkeypress="return hasAmount(event, this, 3, 3)"
															onblur="calculateTotal('${d}');" class="form-control" /></td>
													<td><form:input
															path="dto.dprEntryDetailsDtos[${d}].remark"
															id="remark${d}" class="form-control alphaNumeric" /></td>



												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.dpr.sec.overall.scroe" text="Overall Score" /></label>
									<div class="col-sm-4">
										<form:input path="" id="overallScore" readonly="true"
											class="form-control " />


									</div>



								</div>



							</div>
						</div>
					</div>




				</div>

				<div class="">

					<apptags:CheckerAction hideForward="true" hideSendback="true"
						hideUpload="true"></apptags:CheckerAction>
				</div>

				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.submit" text="Submit" />'
						onclick="savDPREntryApprovalData(this);">
						<spring:message code="sfac.submit" text="Submit" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>
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
				<spring:message code="sfac.dpr.title"
					text="Detailed Project Report Entry Form" />
			</h2>
			<apptags:helpDoc url="DPREntryForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="dprEntryForm" action="DPREntryForm.html" method="post"
				class="form-horizontal">
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
										<form:select path="dto.fpoId" id="fpoId"
											disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select'" />
											</form:option>
											<c:forEach items="${command.fpoMasterDtos}" var="dto">
												<form:option value="${dto.fpoId}" code="${dto.fpoName}">${dto.fpoName}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="sfac.dpr.submit.to.ia" text="Submit To IA" /></label>
									<div class="col-sm-4">
										<form:select path="dto.iaId" id="iaId"
											disabled="${command.viewMode eq 'V' ? true : false }"
											cssClass="form-control chosen-select-no-results">
											<form:option value="0">
												<spring:message text="Select" code="sfac.select'" />
											</form:option>
											<c:forEach items="${command.cbboMasterDtos}" var="dto">
												<form:option value="${dto.iaId}" code="${dto.IAName}">${dto.IAName}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>



								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.dpr.date.of.submission"
											text="Date Of Submission/Review" /></label>
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
												actionUrl="DPREntryForm.html?Download"></apptags:filedownload>
											<c:if test="${command.viewMode ne 'V'}">
												<small class="text-blue-2"> <spring:message
														code="sfac.fpo.checklist.validation"
														text="(Upload Image File upto 5 MB)" />
												</small>
											</c:if>
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

											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
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
																	fieldPath="dto.dprEntryDetailsDtos[${d}].attachmentsDet[0].uploadedDocumentPath"
																	currentCount="${d+1}" showFileNameHTMLId="true"
																	folderName="${d+1}" fileSize="CARE_COMMON_MAX_SIZE"
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
															</c:if> <c:if test="${command.viewMode ne 'V'}">
																<small class="text-blue-2"> <spring:message
																		code="sfac.fpo.checklist.validation"
																		text="(Upload Image File upto 5 MB)" />
																</small>
															</c:if></td>


														<c:if test="${command.viewMode ne 'V'}">
															<td class="text-center"><a
																class="btn btn-blue-2 btn-sm addDPRSecButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addDPRSecButton(this);"> <i
																	class="fa fa-plus-circle"></i></a> <a
																class='btn btn-danger btn-sm deleteDPRSecDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteDPRSecDetails(this);"> <i
																	class="fa fa-trash"></i>
															</a></td>
														</c:if>

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
																fieldPath="dto.dprEntryDetailsDtos[${d}].attachmentsDet[0].uploadedDocumentPath"
																currentCount="${d+1}" showFileNameHTMLId="true"
																folderName="${d+1}" fileSize="CARE_COMMON_MAX_SIZE"
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
														</c:if> <c:if test="${command.viewMode ne 'V'}">
															<small class="text-blue-2"> <spring:message
																	code="sfac.fpo.checklist.validation"
																	text="(Upload Image File upto 5 MB)" />
															</small>
														</c:if></td>


													<c:if test="${command.viewMode ne 'V'}">
														<td class="text-center"><a
															class="btn btn-blue-2 btn-sm addDPRSecButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addDPRSecButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteDPRSecDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteDPRSecDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></td>
													</c:if>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>


							</div>
						</div>
					</div>


				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.submit" text="Submit" />'
							onclick="saveDPREntryForm(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.viewMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							title='<spring:message code="sfac.button.reset" text="Reset"/>'
							onclick="ResetForm();">
							<spring:message code="sfac.button.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="DPREntryForm.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>
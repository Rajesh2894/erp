<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/buildingplan/developerRegForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="developerCapacityFormId"
					action="DeveloperRegistrationForm.html" method="post"
					class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
						
					<div>
						<c:if test="${not empty command.checkList}">									
								<h4>
									<spring:message code="" text="Document Attachment" />
									<small class="text-blue-2"><spring:message
													code="" text="Only .pdf and jpeg,jpg files allowed"/></small>
								</h4>

								<div id="checkListDetails">
									<div class="panel-body">

										<div class="overflow margin-top-10 margin-bottom-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
													<c:set var="c" value="0" scope="page" />
														<tr>
															<th><spring:message code="water.serialNo"
																	text="Sr No" /></th>
															<th><spring:message code="water.docName"
																	text="Document Name" /></th>
															<%-- <th><spring:message code="water.status"
																	text="Status" /></th> --%>
															<th width="500"><spring:message
																	code="water.uploadText" text="Upload" /></th>
														</tr>

														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">

															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}
																		<c:if test="${lookUp.checkkMANDATORY eq 'Y'}"><span class="mand">*</span></c:if></td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}<c:if test="${lookUp.checkkMANDATORY eq 'Y'}"><span class="mand">*</span></c:if></td>
																		
																	</c:otherwise>

																</c:choose>															
																<%-- <c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><spring:message code="water.doc.mand" /></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><spring:message code="water.doc.opt" /></td>
																</c:if> --%>
																<td>
																	<div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="CHECK_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
																			currentCount="${c}"
																			folderName="checkListInfo${c}" />
																	</div>
																</td>
															</tr>
															<c:set var="c" value="${c + 1}" scope="page" />
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
						</c:if>
					</div>	
					<div class="form-group">
						<label class="col-sm-8 control-label padding-top-0"><spring:message
								code="devloper.question1"
								text="Whether the Developer has earlier been granted permission to set up a colony under HDRU Act, 1975" /></label>
						<div class="col-sm-4">
							<label for="Yes" class="radio-inline"> <form:radiobutton
									class="licenseHDRUDiv"
									path="developerRegistrationDTO.licenseHDRUFlag" value="Y"
									id="Yes" /> <spring:message code="" text="Yes"></spring:message></label>
							<label for="No" class="radio-inline"> <form:radiobutton
									class="licenseHDRUDiv"
									path="developerRegistrationDTO.licenseHDRUFlag" value="N"
									id="No" /> <spring:message code="" text="No"></spring:message></label>
						</div>
					</div>
					<div id="licenseHDRU">
						<table class="table table-bordered table-striped"
							id="devLicenseTable">
							<c:set var="d" value="0" scope="page" />
							<thead>
								<tr>
									<th width="5%"><spring:message code="" text="Sr No." /></th>
									<th><spring:message code="licence.label" text="License" /><span
										class="mand">*</span></th>
									<th><spring:message code="date.of.grant.of.licence"
											text="Date of grant of a licence" /><span class="mand">*</span></th>
									<th><spring:message code="purpose.of.colony"
											text="Purpose of colony" /><span class="mand">*</span></th>
									<th><spring:message code="validity.of.licence"
											text="Validity of licence" /><span class="mand">*</span></th>
									<th width="10%"><spring:message code="action"
											text="Action" /></th>
								</tr>
							</thead>
							<c:choose>
							
								<c:when
									test="${empty command.developerRegistrationDTO.devLicenseHDRUDTOList}">
									<tbody>										
										<tr class="DevLicenseAppendable">
											<td align="center"><input type="text"
													class="form-control mandColorClass"
													id="devLicenseId${d}" value="${d+1}" readonly="true" /></td>

											<td><form:input
													path="developerRegistrationDTO.devLicenseHDRUDTOList[${d}].licenseNo"
													class="form-control" id="licenseNoId${d}"
													data-rule-required="true" minlength="10" maxlength="30" />
											</td>

											<td>
												<div class="input-group">
													<form:input type="text" class="form-control dateOfGrantLicenseHDRU${d}"
														id="dateOfGrantLicenseId${d}"
														path="developerRegistrationDTO.devLicenseHDRUDTOList[${d}].dateOfGrantLicense"
														placeholder="DD/MM/YYYY" data-rule-required="true" />
													<label class="input-group-addon"><i
														class="fa fa-calendar"></i><span class="hide">Date</span></label>
												</div>
											</td>

											<td><form:select
													path="developerRegistrationDTO.devLicenseHDRUDTOList[${d}].purposeOfColony"
													class="form-control" id="purposeOfColonyId${d}">
													<form:option value="">
														<spring:message code="" text="Select" />
													</form:option>
													<c:forEach items="${command.getLevelData('PUR', 1)}"
														var="lookup">
														<form:option value="${lookup.lookUpId}"
															code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
													</c:forEach>

												</form:select></td>

											<td><div class="input-group">
													<form:input type="text"
														class="form-control licenseValidity${d}"
														id="dateOfValidityLicenseId${d}"
														path="developerRegistrationDTO.devLicenseHDRUDTOList[${d}].dateOfValidityLicense"
														placeholder="DD/MM/YYYY" data-rule-required="true" />
													<label class="input-group-addon"><i
														class="fa fa-calendar"></i><span class="hide">Date</span></label>
												</div></td>

											<td class="text-center"><a
												title='<spring:message code="" text="Add"></spring:message>'
												class="btn btn-success btn-sm" onclick="addDevLicenseRow()"
												id="devLicenseAddButton${d}"><i
													class="fa fa-plus-circle"></i></a> <a
												title="<spring:message code="" text="Delete"></spring:message>"
												class="btn btn-danger btn-sm"
												onclick="deleteDevLicenseRow(this)"
												id="devLicenseDelButton${d}"><i class="fa fa-trash-o"></i></a></td>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</tbody>
								</c:when>
								<c:otherwise>
								
									<tbody>
										<c:forEach var="devLicenseList"
											items="${command.developerRegistrationDTO.devLicenseHDRUDTOList}"
											varStatus="status">
											<tr class="DevLicenseAppendable">	
												 <td align="center"><input type="text"
														class="form-control mandColorClass"
														id="devLicenseId${d}" value="${d+1}" readonly="true"/></td>

												<td><form:input
														path="developerRegistrationDTO.devLicenseHDRUDTOList[${d}].licenseNo"
														class="form-control" id="licenseNoId${d}"
														data-rule-required="true" minlength="10" maxlength="30" />
												</td>

												<td>
													<div class="input-group">
														<form:input type="text" class="form-control dateOfGrantLicenseHDRU"
															id="dateOfGrantLicenseId${d}"
															path="developerRegistrationDTO.devLicenseHDRUDTOList[${d}].dateOfGrantLicense"
															placeholder="DD/MM/YYYY" data-rule-required="true" />
														<label class="input-group-addon"><i
															class="fa fa-calendar"></i><span class="hide">Date</span></label>
													</div>
												</td>

												<td><form:select
														path="developerRegistrationDTO.devLicenseHDRUDTOList[${d}].purposeOfColony"
														class="form-control" id="purposeOfColonyId${d}">
														<form:option value="">
															<spring:message code="" text="Select" />
														</form:option>
														<c:forEach items="${command.getLevelData('PUR', 1)}"
															var="lookup">
															<form:option value="${lookup.lookUpId}"
																code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
														</c:forEach>

													</form:select></td>

												<td>
													<div class="input-group">
														<form:input type="text"
															class="form-control licenseValidity"
															id="dateOfValidityLicenseId${d}"
															path="developerRegistrationDTO.devLicenseHDRUDTOList[${d}].dateOfValidityLicense"
															placeholder="DD/MM/YYYY" data-rule-required="true" />
														<label class="input-group-addon"><i
															class="fa fa-calendar"></i><span class="hide">Date</span></label>
													</div>
												</td>

												<td class="text-center"><a
													title='<spring:message code="" text="Add"></spring:message>'
													class="btn btn-success btn-sm" onclick="addDevLicenseRow()"
													id="devLicenseAddButton${d}"><i
														class="fa fa-plus-circle"></i></a> <a
													title="<spring:message code="" text="Delete"></spring:message>"
													class="btn btn-danger btn-sm"
													onclick="deleteDevLicenseRow(this)"
													id="devLicenseDelButton${d}"><i class="fa fa-trash-o"></i></a></td>
															 
					 						</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</c:otherwise>
							</c:choose>
							
						</table>

					</div>
					<div id="projectsQue">
						<div class="form-group">
							<label class="col-sm-8 control-label padding-top-0"><spring:message
									code="devloper.question2"
									text="Have your company/firm developed projects outside Haryana" /></label>
							<div class="col-sm-4">
								<label for="Yes" class="radio-inline"> <form:radiobutton
										class="licenseProjectDiv"
										path="developerRegistrationDTO.projectsFlag" value="Y"
										id="Yes" /> <spring:message code="" text="Yes"></spring:message></label>
								<label for="No" class="radio-inline"> <form:radiobutton
										class="licenseProjectDiv"
										path="developerRegistrationDTO.projectsFlag" value="N" id="No" />
									<spring:message code="" text="No"></spring:message></label>
							</div>

						</div>
					</div>
					<div id="licenseProject">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="name.of.project" text="Name of Project" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.projectName" id="projectName"
									minlength="2" maxlength="50" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="name.of.authority" text="Name of Authority " /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.athorityName" id="AuthorityName"
									minlength="2" maxlength="50" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="status.of.development" text="Status of Development" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control"
									path="developerRegistrationDTO.devStatus" id="devStatus"
									minlength="2" maxlength="50" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="permission.letter" text="Permission letter" /></label>
							<div class="col-sm-2">
								<i style="font-size: 10px; font-weight: bold;"
									class="text-red-1"><spring:message code="file.upload.msg"
										text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
								<apptags:formField fieldType="7"
									fieldPath="developerRegistrationDTO.attachments[0].uploadedDocumentPath"
									currentCount="401" showFileNameHTMLId="true" folderName="perLetter401"
									fileSize="CHECK_COMMOM_MAX_SIZE" isMandatory="true"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
								</apptags:formField>
								<c:if
									test="${command.developerRegistrationDTO.attachDocsList[0] ne null  && not empty command.developerRegistrationDTO.attachDocsList[0]}">
									<input type="hidden" name="deleteFileId"
										value="${command.developerRegistrationDTO.attachDocsList[0].attId}">
									<input type="hidden" name="downloadLink"
										value="${command.developerRegistrationDTO.attachDocsList[0]}">
									<apptags:filedownload
										filename="${command.developerRegistrationDTO.attachDocsList[0].attFname}"
										filePath="${command.developerRegistrationDTO.attachDocsList[0].attPath}"
										actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
								</c:if>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="area.of.the.project.in.acres"
									text="Area of the project in acres" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control hasDecimal"
									path="developerRegistrationDTO.areaOfProject" id="areaOfProject"
									minlength="1" maxlength="3" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="location" text="Location" /></label>
							<div class="col-sm-2">
								<form:input name="" type="text" class="form-control" id="locationId"
									path="developerRegistrationDTO.location" minlength="1"
									maxlength="20" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="any.other.documents" text="Any other documents" /></label>

							<div class="col-sm-2">
								<i style="font-size: 10px; font-weight: bold;"
									class="text-red-1"><spring:message code="file.upload.msg"
										text="(UploadFile upto 20MB and Only .pdf and jpeg,jpg)" /></i>
								<apptags:formField fieldType="7"
									fieldPath="developerRegistrationDTO.attachments[1].uploadedDocumentPath"
									currentCount="402" showFileNameHTMLId="true" folderName="devCapacity402"
									fileSize="CHECK_COMMOM_MAX_SIZE" isMandatory="true"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
								</apptags:formField>
								<c:if
									test="${command.developerRegistrationDTO.attachDocsList[1] ne null  && not empty command.developerRegistrationDTO.attachDocsList[1]}">
									<input type="hidden" name="deleteFileId"
										value="${command.developerRegistrationDTO.attachDocsList[1].attId}">
									<input type="hidden" name="downloadLink"
										value="${command.developerRegistrationDTO.attachDocsList[1]}">
									<apptags:filedownload
										filename="${command.developerRegistrationDTO.attachDocsList[1].attFname}"
										filePath="${command.developerRegistrationDTO.attachDocsList[1].attPath}"
										actionUrl="DeveloperRegistrationForm.html?Download"></apptags:filedownload>
								</c:if>
							</div>
						</div>
					</div>
					<div class="text-center">
						<button type="button" class="button-input btn btn-success"
							name="button" value="Save" onclick="saveDevCapacityForm(this)"
							id="saveDevCapacity">
							<spring:message code="save.next" text="Save & Next" />
						</button>

						<button type="button" class="button-input btn btn-danger"
							name="button" value="Save" onclick="showTab('#authorizedUser')">
							<spring:message code="back.button" text="Back" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
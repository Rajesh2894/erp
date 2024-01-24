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
<script type="text/javascript" src="js/sfac/fpoProfileManagement.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />

<script src="js/mainet/file-upload.js"></script>
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

<div class="content animated top">
	<div class="widget">
		

		<div class="widget-content padding">
			<form:form id="fpoPMLicenceInfo"
				action="FPOProfileManagementForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="fpmId" id="fpmId" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>




				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">



					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#licenseEntry">
									<spring:message code="sfac.fpo.pm.licenseEntry"
										text="License Detail" />
								</a>
							</h4>
						</div>
						<div id="licenseEntry" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="licenseEntryTable">
									<thead>
										<tr>
											<th width="6%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th width="15%"><spring:message code="sfac.fpo.pm.licenseType"
													text="License Type" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseDesc"
													text="Description" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseStartDate"
													text="License Issue Date" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseEndDate"
													text="License Expiry Date" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseIssueAuth"
													text="License Issue Auth" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseDocUpload"
													text="Document Upload" /></th>
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.licenseInformationDetEntities)>0 }">
												<c:forEach var="dto"
													items="${command.dto.licenseInformationDetEntities}"
													varStatus="status">
													<tr class="appendableLicenseDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
														<td>	<c:set var="baseLookupCode" value="LTS" />
									<form:select path="dto.licenseInformationDetEntities[${d}].LicenseType" class="form-control chosen-select-no-results"
									disabled="${command.viewMode eq 'V' ? true : false }"
										id="LicenseType${d}">
										<form:option value="0">
											<spring:message code="sfac.select" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

														<td><form:input
																path="dto.licenseInformationDetEntities[${d}].licenseDesc" disabled="${command.viewMode eq 'V' ? true : false }"
																id="licenseDesc${d}" class="form-control alphaNumeric" maxlength="200"/></td>
														<td>
															
																<div class="input-group">
																	<form:input
																		path="dto.licenseInformationDetEntities[${d}].licIssueDate"
																		type="text"
																		class="form-control datepicker mandColorClass"
																		id="licIssueDate${d}" placeholder="dd/mm/yyyy"
																		readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																	<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span>
																</div>
														

														</td>

														<td>
															
																<div class="input-group">
																	<form:input
																		path="dto.licenseInformationDetEntities[${d}].licExpDate"
																		type="text"
																		class="form-control datepicker mandColorClass"
																		id="licExpDate${d}" placeholder="dd/mm/yyyy"
																		readonly="true" disabled="${command.viewMode eq 'V' ? true : false }" />
																	<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span>
																</div>
														</td>

														<td><form:input
																path="dto.licenseInformationDetEntities[${d}].licIssueAuth" disabled="${command.viewMode eq 'V' ? true : false }"
																id="licIssueAuth${d}" class="form-control alphaNumeric" maxlength="100"/></td>

														<td><c:if test="${command.viewMode ne 'V'}"><apptags:formField
														fieldType="7"
														fieldPath="dto.licenseInformationDetEntities[${d}].attachmentsLi[0].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="lidfg${d}" fileSize="WORK_COMMON_MAX_SIZE"
														isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="ALL_UPLOAD_VALID_EXTENSION">
													</apptags:formField></c:if>
												<c:if
															test="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0] ne null  && not empty command.dto.licenseInformationDetEntities[d].attachDocsListLi[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0]}">
															<apptags:filedownload
																filename="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0].attFname}"
																filePath="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0].attPath}"
																actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
														</c:if>
													<small class="text-blue-2"> <spring:message
																	code="sfac.fpo.checklist.validation"
																	text="(Upload Image File upto 2 MB)" />
														</small>
													</td>



														<td class="text-center">
															<c:if test="${command.viewMode ne 'V'}">
														<a
															class="btn btn-blue-2 btn-sm addLicenseButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="fileCountUpload(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteLicenseDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteLicenseDetails($(this),${d});"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableLicenseDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>
													<td><c:set var="baseLookupCode" value="LTS" />
									<form:select path="dto.licenseInformationDetEntities[${d}].LicenseType" class="form-control chosen-select-no-results"
									disabled="${command.viewMode eq 'V' ? true : false }"
										id="LicenseType${d}">
										<form:option value="0">
											<spring:message code="sfac.select" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

														<td><form:input
																path="dto.licenseInformationDetEntities[${d}].licenseDesc" disabled="${command.viewMode eq 'V' ? true : false }"
																id="licenseDesc${d}" class="form-control alphaNumeric" maxlength="200"/></td>
														<td>
															
																<div class="input-group">
																	<form:input
																		path="dto.licenseInformationDetEntities[${d}].licIssueDate"
																		type="text"
																		class="form-control datepicker mandColorClass"
																		id="licIssueDate${d}" placeholder="dd/mm/yyyy"
																		readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																	<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span>
																</div>
														

														</td>

														<td>
															
																<div class="input-group">
																	<form:input
																		path="dto.licenseInformationDetEntities[${d}].licExpDate"
																		type="text"
																		class="form-control datepicker mandColorClass"
																		id="licExpDate${d}" placeholder="dd/mm/yyyy"
																		readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																	<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span>
																</div>
														</td>

														<td><form:input
																path="dto.licenseInformationDetEntities[${d}].licIssueAuth" disabled="${command.viewMode eq 'V' ? true : false }"
																id="licIssueAuth${d}" class="form-control alphaNumeric" maxlength="100"/></td>

													<td>
													<c:if test="${command.viewMode ne 'V'}">
													<apptags:formField
														fieldType="7"
														fieldPath="dto.licenseInformationDetEntities[${d}].attachmentsLi[0].uploadedDocumentPath" 
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="lidfg${d}" fileSize="WORK_COMMON_MAX_SIZE"
							isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="ALL_UPLOAD_VALID_EXTENSION">
													</apptags:formField>
													</c:if>
														<c:if
															test="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0] ne null  && not empty command.dto.licenseInformationDetEntities[d].attachDocsListLi[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0]}">
															<apptags:filedownload
																filename="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0].attFname}"
																filePath="${command.dto.licenseInformationDetEntities[d].attachDocsListLi[0].attPath}"
																actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
														</c:if><small class="text-blue-2"> <spring:message
																	code="sfac.fpo.checklist.validation"
																	text="(Upload Image File upto 2 MB)" />
														</small>
													</td>



													<td class="text-center">
														<c:if test="${command.viewMode ne 'V'}">
													<a
														class="btn btn-blue-2 btn-sm addLicenseButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="fileCountUpload(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteLicenseDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteLicenseDetails($(this),${d});"> <i
															class="fa fa-trash"></i>
													</a>
													</c:if>
													</td>

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
						title='<spring:message code="sfac.savencontinue" text="Save & Continue" />'
						onclick="saveLicenseInfoForm(this);">
					<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
					
					<!-- <a data-toggle="tab" href="#finInfo"  > --><button type="button" class="btn btn-danger" onclick="navigateTab('fin-tab','finInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
					<!-- </a> -->
				</div>

			</form:form>
		</div>
	</div>
</div>
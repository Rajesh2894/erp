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


<!-- Start Content here testing commit 2023 -->

<div class="content animated top">
	<div class="widget">
		

		<div class="widget-content padding">
			<form:form id="fpoPMBPInfo"
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
									data-parent="#accordion_single_collapse" href="#bpInfoDiv">
									<spring:message code="sfac.fpo.pm.businessPlan"
										text="Business Plan" />
								</a>
							</h4>
						</div>
						<div id="bpInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<c:set var="d1" value="100" scope="page"></c:set>
							
								<table
									class="table table-bordered table-striped contact-details-table"
									id="bpInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
										

											<th><spring:message code="sfac.fpo.pm.docDesc"
													text="Document Description" /></th>
											<th><spring:message code="sfac.fpo.pm.licenseDocUpload"
													text="Document Upload" /></th>
													
													
											
										<%-- 	<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th> --%>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.businessPlanInfoDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.businessPlanInfoDTOs}"
													varStatus="status">
													<tr class="appendableBPDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.businessPlanInfoDTOs[${d}].documentDescription"
																id="documentDescription${d}" class="form-control " disabled="true" /></td>
														
															<td><c:if test="${command.viewMode ne 'V'}"><apptags:formField
														fieldType="7"
														fieldPath="dto.businessPlanInfoDTOs[${d}].attachmentsBP[0].uploadedDocumentPath"
														currentCount="${d1}" showFileNameHTMLId="true"
														folderName="bpdfg${d}" fileSize="CARE_COMMON_MAX_SIZE" 
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT" 
														validnFunction="ALL_UPLOAD_VALID_EXTENSION">
													</apptags:formField></c:if>
														<c:if
															test="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0] ne null  && not empty command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0]}">
															<apptags:filedownload
																filename="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0].attFname}"
																filePath="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0].attPath}"
																actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
																<small class="text-blue-2"> <spring:message
																	code="sfac.fpo.checklist.validation"
																	text="(Upload Image File upto 2 MB)" />
														</small>
														</c:if>
													</td>
																
																


													<%-- 	<td class="text-center">
														<c:if test="${command.viewMode ne 'V'}">
														<a
															class="btn btn-blue-2 btn-sm addBPButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="fileCountUploadBP(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteBPDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteBPDetails($(this),${d});"> <i
																class="fa fa-trash"></i>
														</a></c:if></td> --%>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
													<c:set var="d1" value="${d1 + 1}" scope="page" />
												
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableBPDetails">
															<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.businessPlanInfoDTOs[${d}].documentDescription"
																id="documentDescription${d}" class="form-control "  disabled="true"/></td>
													
															<td><c:if test="${command.viewMode ne 'V'}"><apptags:formField
														fieldType="7"
														fieldPath="dto.businessPlanInfoDTOs[${d}].attachmentsBP[0].uploadedDocumentPath"
														currentCount="${d1}" showFileNameHTMLId="true"
														folderName="bpdfg${d}" fileSize="CARE_COMMON_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT" 
														validnFunction="ALL_UPLOAD_VALID_EXTENSION">
													</apptags:formField></c:if>
													<c:if
															test="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0] ne null  && not empty command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0]}">
															<apptags:filedownload
																filename="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0].attFname}"
																filePath="${command.dto.businessPlanInfoDTOs[d].attachDocsListBP[0].attPath}"
																actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
														</c:if><small class="text-blue-2"> <spring:message
																	code="sfac.fpo.checklist.validation"
																	text="(Upload Image File upto 2 MB)" />
														</small>
													
													</td>


													<%-- <td class="text-center">
													<c:if test="${command.viewMode ne 'V'}">
													<a
														class="btn btn-blue-2 btn-sm addBPButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="fileCountUploadBP(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteBPDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteBPDetails($(this),${d});"> <i
															class="fa fa-trash"></i>
													</a></c:if></td> --%>

												</tr>
												
												<tr class="appendableBPDetails">
															<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo1"
																value="2" disabled="true" />
														<td><form:input
																path="dto.businessPlanInfoDTOs[1].documentDescription"
																id="documentDescription1" class="form-control "  disabled="true" /></td>
													
															<td><c:if test="${command.viewMode ne 'V'}"><apptags:formField
														fieldType="7"
														fieldPath="dto.businessPlanInfoDTOs[1].attachmentsBP[0].uploadedDocumentPath"
														currentCount="101" showFileNameHTMLId="true"
														folderName="bpdfg1" fileSize="CARE_COMMON_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT" 
														validnFunction="ALL_UPLOAD_VALID_EXTENSION">
													</apptags:formField></c:if>
													<c:if
															test="${command.dto.businessPlanInfoDTOs[1].attachDocsListBP[0] ne null  && not empty command.dto.businessPlanInfoDTOs[1].attachDocsListBP[0] }">
															<input type="hidden" name="deleteFileId"
																value="${command.dto.businessPlanInfoDTOs[1].attachDocsListBP[0].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.dto.businessPlanInfoDTOs[1].attachDocsListBP[0]}">
															<apptags:filedownload
																filename="${command.dto.businessPlanInfoDTOs[1].attachDocsListBP[0].attFname}"
																filePath="${command.dto.businessPlanInfoDTOs[1].attachDocsListBP[0].attPath}"
																actionUrl="FPOProfileManagementForm.html?Download"></apptags:filedownload>
																
														</c:if><small class="text-blue-2"> <spring:message
																	code="sfac.fpo.checklist.validation"
																	text="(Upload Image File upto 2 MB)" />
														</small>
													
													</td>


													<%-- <td class="text-center">
													<c:if test="${command.viewMode ne 'V'}">
													<a
														class="btn btn-blue-2 btn-sm addBPButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="fileCountUploadBP(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteBPDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteBPDetails($(this),${d});"> <i
															class="fa fa-trash"></i>
													</a></c:if></td> --%>

												</tr>
												
												<c:set var="d" value="${d + 1}" scope="page" />
												<c:set var="d1" value="${d1 + 1}" scope="page" />
												
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
						onclick="saveBPInfoForm(this);">
						<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
						
				<button type="button" class="btn btn-danger"
				onclick="navigateTab('ml-tab','mlInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/legal/judgementMaster.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/mainet/file-upload.js"></script>

<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;
}
</style>
<script>
	$(document).ready(function() {
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lgl.case.judgement.details" text="Judgement Details" />
			</h2>
		</div>

		<div class="widget-content padding">
			<form:form action="JudgementMaster.html" class="form-horizontal form"
				name="judgementMasterForm" id="judgementMasterForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="removedIds" id="removedIds" />
				<form:hidden path="length" id="length" />
				<form:hidden path="caseDate" id="caseDate" />

				<div class="table-responsive clear">
					<div class="panel-body">
						<div id="judgementDetailsListDiv">
							<c:set var="d" value="0" scope="page" />
							<table id="judgementDetailsListTB"
								class="table table-striped table-bordered">

								<thead>
									<tr>
										<th width="5%"><spring:message code="lgl.srno" text="Sr. No" /></th>
										<th width="10%"><spring:message code="lgl.dept" text="Department" /></th>
										<th width="15%"><spring:message code="lgl.courtdet" text="Court Details" /></th>
										<th width="10%"><spring:message code="lgl.case.submission.date" text="Case Submission Date" />
										<th width="10%"><spring:message code="lgl.case.benchName.judgement" text="Bench Name who pass judgement" /></th>
										<th width="10%"><spring:message code="lgl.case.judgement.date" text="Date of judgement" /><i class="text-red-1">*</i></th>
										<th width="20%"><spring:message code="lgl.case.judgement.summary" text="Decision" /><i class="text-red-1">*</i></th>
										<th width="10%"><spring:message code="lgl.case.judgement.attachCopy" text="Attach Judgement copy" /><i class="text-red-1">*</i>
										<br><small class="text-blue-2"> <spring:message
															code="legal.doc.msg.case" text="(Only .pdf, .png and .jpg is allowed upto 5 MB)" /></small></th>

										<c:if test="${command.saveMode eq 'EDIT' }">
											<%-- Defect #157025 --%>
											<th scope="col" width="8%"><spring:message code="lgl.action" text="Action" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(command.judgementMasterDtoList)>0 }">
											<c:forEach var="judgementData"
												items="${command.judgementMasterDtoList}">
												<tr class="appendableClass">
													<form:hidden path="judgementMasterDtoList[${d}].judId"
														id="judId${d}" />
														
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence${d}"
															value="${d+1}" disabled="true" /></td>

													<td align="center"><form:input
															path="judgementMasterDtoList[${d}].cseDeptName"
															cssClass="form-control mandColorClass"
															id="cseDeptName${d}" disabled="true" /></td>
													<td align="center"><form:input
															path="judgementMasterDtoList[${d}].cseCourtDesc"
															cssClass="form-control mandColorClass"
															id="cseCourtDesc${d}" disabled="true" /></td>
													<td align="center"><form:input
															path="judgementMasterDtoList[${d}].cseDateDesc"
															cssClass="form-control mandColorClass"
															id="cseDateDesc${d}" disabled="true" /></td>
													<td align="center"><form:input
															path="judgementMasterDtoList[${d}].cseBenchName"
															cssClass="form-control mandColorClass"
															id="cseBenchName${d}" disabled="true" /></td>

													<td align="center"><form:input
															path="judgementMasterDtoList[${d}].judDate"
															cssClass="form-control judgementDate datepicker text-center"
															placeholder="DD/MM/YYYY" maxlength="10" id="judDate${d}"
															disabled="${command.saveMode eq 'VIEW' ? true : false}" readonly="${command.orgFlag eq 'Y'}"/></td>

													<td><form:textarea
															path="judgementMasterDtoList[${d}].judSummaryDetail"
															id="judSummaryDetail${d}" class="form-control"
															maxlength ="1000"
															disabled="${command.saveMode eq 'VIEW' ? true : false}" readonly="${command.orgFlag eq 'Y'}"/></td>
													<!-- document data attribute -->
													<td class="text-center"><c:if
															test="${command.saveMode ne 'VIEW'}">
															<apptags:formField fieldType="7"
																fieldPath="attachments[${d}].uploadedDocumentPath"
																currentCount="${d}" showFileNameHTMLId="true"
																folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
																isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="CARE_VALIDATION_EXTENSION">
															</apptags:formField>
														</c:if> <c:if
															test="${command.attachDocsList[d] ne null  && not empty command.attachDocsList[d] }">
															<input type="hidden" name="deleteFileId"
																value="${command.attachDocsList[d].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.attachDocsList[d]}">
															<apptags:filedownload
																filename="${command.attachDocsList[d].attFname}"
																filePath="${command.attachDocsList[d].attPath}"
																actionUrl="JudgementMaster.html?Download"></apptags:filedownload>
														</c:if></td>

													<c:if
														test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT'}">
														<td class="text-center">
															<%-- Defect #157025 --%> <a
															onclick='fileCountUpload(this);'
															class="btn btn-blue-2 btn-sm addReButton"
															title="<spring:message code="lgl.add" text="Add"></spring:message>">
																<i class="fa fa-plus-circle"></i>
														</a>

														<button title="<spring:message code="work.estimate.delete" text="Delete" />"
														<a href='#' id="0_file_${d}"
														onclick="doFileDelete(this)"
														class='btn btn-danger btn-sm delButton'><i
															class="fa fa-trash"></i></a></button>
														</td>
													</c:if>

													<c:set var="d" value="${d + 1}" />
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="appendableClass">
												<form:hidden path="judgementMasterDtoList[${d}].judId"
													id="judId${d}" />
												<td align="center"><form:input path=""
														cssClass="form-control mandColorClass " id="sequence${d}"
														value="${d+1}" disabled="true" /></td>

												<td align="center"><form:input
														path="judgementMasterDtoList[${d}].cseDeptName"
														cssClass="form-control mandColorClass"
														id="cseDeptName${d}" disabled="true" /></td>
												<td align="center"><form:input
														path="judgementMasterDtoList[${d}].cseCourtDesc"
														cssClass="form-control mandColorClass"
														id="cseCourtDesc${d}" disabled="true" /></td>
												<td align="center"><form:input
														path="judgementMasterDtoList[${d}].cseDateDesc"
														cssClass="form-control mandColorClass"
														id="cseDateDesc${d}" disabled="true" /></td>
												<td align="center"><form:input
														path="judgementMasterDtoList[${d}].cseBenchName"
														cssClass="form-control mandColorClass"
														id="cseBenchName${d}" disabled="true" /></td>

												<td align="center"><form:input
														path="judgementMasterDtoList[${d}].judDate"
														cssClass="form-control judgementDate datepicker text-center"
														placeholder="DD/MM/YYYY" maxlength="10" id="judDate${d}" disabled="${command.orgFlag eq 'Y'}"/></td>

												<td><form:textarea
														path="judgementMasterDtoList[${d}].judSummaryDetail"
														id="judSummaryDetail${d}" class="form-control" disabled="${command.orgFlag eq 'Y'}"/></td>
												<!-- document data attribute -->
												<td class="text-center doc"><apptags:formField
														fieldType="7"
														fieldPath="attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CARE_VALIDATION_EXTENSION">
													</apptags:formField></td>

												<c:if
													test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT'}">
													<td class="text-center">
														<%-- Defect #157025 --%> <a
														onclick='fileCountUpload(this);'
														class="btn btn-blue-2 btn-sm addReButton"
														title="<spring:message code="lgl.add" text="Add"></spring:message>">
															<i class="fa fa-plus-circle"></i></a>
															
													<button title="<spring:message code="work.estimate.delete" text="Delete" />"
													<a href='#' id="0_file_${d}"
														onclick="doFileDelete(this)"
														class='btn btn-danger btn-sm delButton'><i
															class="fa fa-trash"></i></a>
															</button>
													</td>

												</c:if>

												<c:set var="d" value="${d + 1}" />
											</tr>
										</c:otherwise>
									</c:choose>

								</tbody>

							</table>
						</div>
					</div>



				</div>

				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'VIEW'}">
					<c:if test="${command.orgFlag ne 'Y'}">
						<button class="btn btn-success save"
							onclick="saveJudgementForm(this);" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="lgl.save" text="SAVE" />
						</button>
					</c:if>
				</c:if>
					<apptags:backButton url="JudgementMaster.html" />
				</div>

			</form:form>

		</div>

	</div>

</div>
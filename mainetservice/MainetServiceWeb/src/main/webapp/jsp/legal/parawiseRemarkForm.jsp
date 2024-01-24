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
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/legal/parawiseRemark.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<!-- End JSP Necessary Tags -->
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2>
				<spring:message code="lgl.caseEntry.form.heading"
					text="Case Entry Form Heading" />
			</h2>
			<apptags:helpDoc url="ParawiseRemark.html" />
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">

				<span><spring:message code="legal.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="legal.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="ParawiseRemark.html" name="ParawiseRemarkForm"
				id="ParawiseRemarkForm" class="form-horizontal"
				commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<jsp:include page="/jsp/legal/caseEntryViewForm.jsp" />
				<form:input type="hidden" path="removeParawiseIds" id="removeParawiseIds" />

          <!-- for view   -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<c:if
						test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
						<c:if test="${not empty command.parawiseRemarkDTOListView }">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-target="#a3" data-toggle="collapse" class="collapsed"
											data-parent="#accordion_single_collapse" href="#a3"><spring:message
												code="lgl.parawise.entryView" text="Para Wise Remark Entry-View" /></a>
									</h4>
								</div>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="table-responsive clear">
											<table class="table table-striped table-bordered" id="">
												<thead>
													<tr>
														<th width="8%"><spring:message code="lgl.srno"
																text="Sr. No." /></th>
														<th width="15%"><spring:message code="lgl.pgno"
																text="Page Number" /></th>
														<th width="15%"><spring:message code="lgl.secno"
																text="Section Number" /></th>
														<th width ="20%"><spring:message code="lgl.reference.caseno"
																text="Reference Case Number" /></th>
														<th width ="20%"><spring:message code="lgl.comments"
																text="Comments" /></th>
														<th width ="14%"><spring:message code="lgl.comments.UADremark"
																text="UAD Remark" /></th>

														<th width ="8%"><spring:message code="lgl.comment.uploadDoc"
																text="Upload Document" /></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${command.parawiseRemarkDTOListView}"
														var="mastDto" varStatus="status">
														<tr>
															<td>${status.count}</td>
															<td>${mastDto.parPagno}</td>
															<td>${mastDto.parSectionno}</td>
															<td>${mastDto.refCaseNo}</td>
															<td>${mastDto.parComment}</td>
															<td>${mastDto.parUadRemark}</td>
															<td class="text-center"><c:if
																	test="${mastDto.attachDocsList1 ne null  && not empty mastDto.attachDocsList1 }">
																	<c:forEach items="${mastDto.attachDocsList1}"
																		varStatus="status" var="model">
																		<input type="hidden" name="deleteFileId"
																			value="${model.attId}">
																		<apptags:filedownload filename="${model.attFname}"
																			filePath="${model.attPath}"
																			actionUrl="ParawiseRemark.html?Download"></apptags:filedownload>
																	</c:forEach>
																</c:if></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</c:if>
                               
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a1" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a1"><spring:message
											code="lgl.parawise.entry" text="Para Wise Remark Entry" /></a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="table-responsive">
										<table id="parawiseRemarkDetails"
											summary="Parawise Remark Data"
											class="table table-bordered table-striped">
											<c:set var="d" value="0" scope="page"></c:set>
											<thead>
												<tr>
													<th width="12%"><spring:message code="lgl.srno"
															text="Sr. No." /></th>
													<th width="20%"><spring:message code="lgl.pgno"
															text="Page Number" /><span class="mand">*</span></th>
													<th width="20%"><spring:message code="lgl.secno"
															text="Section Number" /><span class="mand">*</span></th>
													<th width="20%"><spring:message code="lgl.reference.caseno"
															text="Reference Case Number" /></th>
													<th width ="20%"><spring:message code="lgl.comments"
															text="Comments" /></th>
												
													<c:if test="${command.saveMode ne 'V'}">
															<th width="8%"><spring:message	code="lgl.action" text="Action" /></th>	
													</c:if>
												</tr>
											</thead>
											<tbody>
												<c:if test="${not empty command.parawiseRemarkDTOList }">
        										 <c:set var="j" value="0" scope="page"></c:set>
													<c:forEach items="${command.parawiseRemarkDTOList}"
														var="data" varStatus="index">
														<tr class="appendableClass">

															<td align="center" width = "12%"><form:input path=""
																	cssClass="form-control mandColorClass"
																	id="sequence${j}" value="${j+1}"
																	disabled="true" /></td>
																<form:hidden
																path="parawiseRemarkDTOList[${j}].parId"
																id="parId${j}" />

															<td width = "20%"><form:input
																	path="parawiseRemarkDTOList[${j}].parPagno"
																	cssClass=" hasNumber form-control mandColorClass required-control "
																	id="parPagno${j}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td width ="20%"><form:input
																	path="parawiseRemarkDTOList[${j}].parSectionno"
																	cssClass="hasMobileNo required-control form-control"
																	id="parSectionno${j}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td width ="20%"><form:input
																	path="parawiseRemarkDTOList[${j}].refCaseNo"
																	cssClass="hasemailclass required-control form-control"
																	id="refCaseNo${j}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td width = "20%"><form:input
																	path="parawiseRemarkDTOList[${j}].parComment"
																	cssClass="form-control mandColorClass preventSpace required-control"
																	id="parComment${j}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<c:if test="${command.saveMode ne 'V'}">
																<td class="text-center" width="8%">
																<a href="#a4" data-toggle="tooltip"
																	data-placement="top" class="btn btn-blue-2  btn-sm"
																	data-original-title="Add" onclick="addData();"><strong
																		class="fa fa-plus addReButton"></strong><span
																		class="hide"></span></a>
																<a href='javascript:void(0);' class="btn btn-danger btn-sm delButton"
															title="<spring:message code="lgl.delete" />">
															<i class="fa fa-minus"></i></a>	
																</td>
															</c:if>
														</tr>
														<c:set var="j" value="${j+1}" scope="page" />
													</c:forEach>
												</c:if>

												<c:if test="${ empty command.parawiseRemarkDTOList }">
													<tr class="appendableClass">

														<td align="center" width ="12%"><form:input path=""
																cssClass="form-control mandColorClass " id="sequence0"
																value="1" disabled="true" /></td>

														<td width ="20%"><form:input
																path="parawiseRemarkDTOList[0].parPagno"
																cssClass="form-control mandColorClass required-control hasNumber"
																id="parPagno0"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td width ="20%"><form:input
																path="parawiseRemarkDTOList[0].parSectionno"
																cssClass="hasMobileNo required-control form-control"
																id="parSectionno0"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td width ="20%"><form:input
																path="parawiseRemarkDTOList[0].refCaseNo"
																cssClass="hasemailclass required-control form-control"
																id="refCaseNo0"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td width ="20%"><form:input
																path="parawiseRemarkDTOList[0].parComment"
																cssClass="form-control mandColorClass required-control preventSpace"
																id="parComment0"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>
														
														 <c:if test="${command.saveMode ne 'V'}">
															<td class="text-center" width="8%">
															<a href="#a4" data-toggle="tooltip"
																data-placement="top" class="btn btn-blue-2  btn-sm"
																data-original-title="Add" onclick="addData();"><strong
																	class="fa fa-plus addReButton"></strong><span
																	class="hide"></span></a>
															<a href="#a4" data-toggle="tooltip"
																data-placement="top" class="btn btn-danger btn-sm delButton"
																data-original-title="Delete"> <strong
																	class="fa fa-trash"></strong> <span class="hide"><spring:message
																			code="lgl.delete" text="Delete" /></span>
															</a></td>
														 </c:if> 
													</tr>

												</c:if>
												<c:set var="d" value="${d + 1}" scope="page" />
											</tbody>
										</table>
									</div>
								</div>

							</div>
						</div>
					</c:if>


					<!-- for UAD Remark -->

					<c:if
						test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a2"><spring:message
											code="lgl.parawise.entryUad" text="Para Wise Remark " /></a>
								</h4>
							</div>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="table-responsive">
										<c:set var="d" value="0" scope="page" />
										<table id="parawiseRemarkDetailsuad"
											summary="Parawise Remark Data"
											class="table table-bordered table-striped">
											<thead>
												<tr>
													<th width="8%"><spring:message code="lgl.srno"
															text="Sr. No." /></th>
													<th width="15%"><spring:message code="lgl.pgno"
															text="Page Number" /><span class="mand">*</span></th>
													<th width="15%"><spring:message code="lgl.secno"
															text="Section Number" /><span class="mand">*</span></th>
													<th width ="20%"><spring:message code="lgl.reference.caseno"
															text="Reference Case Number" /></th>
													<th width ="20%"><spring:message code="lgl.comments.remark"
															text="Council/Corporation Remark" /></th>
													<th width ="14%"><spring:message code="lgl.comments.UADremark"
															text="UAD Remark" /></th>

													<th width ="8%"><spring:message code="lgl.comment.uploadDoc"
															text="Upload Document" /></th>
													<%-- <c:if test="${not empty command.parawiseRemarkDTOList }">
														<th width="50"><a href="#a6" data-toggle="tooltip"
															data-placement="top" class="btn btn-blue-2  btn-sm"
															data-original-title="Add" onclick="addDataPara(this);"  disabled="true"><strong
																class="fa fa-plus addReButton"></strong><span
																class="hide"></span></a></th>
													</c:if>
													<c:if test="${empty command.parawiseRemarkDTOList }">
													<th width="50"><a href="#a6" data-toggle="tooltip"
															data-placement="top" class="btn btn-blue-2  btn-sm"
															data-original-title="Add" onclick="addDataPara(this);"><strong
																class="fa fa-plus addReButton"></strong><span
																class="hide"></span></a></th></c:if> --%>
											

												</tr>
											</thead>
											<tbody>
												<c:if test="${not empty command.parawiseRemarkDTOList }">

													<c:forEach items="${command.parawiseRemarkDTOList}"
														var="data" varStatus="index">
														<tr class="appendableClassPara">

															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass"
																	id="sequence${d}" value="${d + 1 }"
																	disabled="true" /></td>

															<td><form:input
																	path="parawiseRemarkDTOList[${d}].parPagno"
																	cssClass="form-control mandColorClass required-control"
																	id="parUadPagno${d}" disabled="true" /></td>

															<td><form:input
																	path="parawiseRemarkDTOList[${d}].parSectionno"
																	cssClass="hasMobileNo required-control form-control"
																	id="parUadSectionno${d}" disabled="true" /></td>

															<td><form:input
																	path="parawiseRemarkDTOList[${d}].refCaseNo"
																	cssClass="hasemailclass required-control form-control"
																	id="refCaseNo${d}" disabled="true" /></td>

															<td><form:input
																	path="parawiseRemarkDTOList[${d}].parComment"
																	cssClass="form-control mandColorClass required-control"
																	id="parUadComment${d}" disabled="true" /></td>

															<td><form:textarea
																	path="parawiseRemarkDTOList[${d}].parUadRemark"
																	id="uadParComment${d}"
																	cssClass="form-control mandColorClass required-control uadParComment preventSpace"
																	maxlength="100"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td class="text-center">
																<apptags:formField
																	fieldType="7" hasId="true"
																	fieldPath="parawiseRemarkDTOList[${d}].attachments[${d}].uploadedDocumentPath"
																	currentCount="${d}" showFileNameHTMLId="true"
																	folderName="${d}" fileSize="COMMOM_MAX_SIZE"
																	isMandatory="false" isReadonly="${command.saveMode eq 'V' ? true : false }"
																	maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
																</apptags:formField> <c:if
																	test="${data.attachDocsList1 ne null  && not empty data.attachDocsList1 }">
																	<c:forEach items="${data.attachDocsList1}"
																		varStatus="status" var="model">
																		<input type="hidden" name="deleteFileId"
																			value="${model.attId}">
																		<apptags:filedownload filename="${model.attFname}"
																			filePath="${model.attPath}"
																			actionUrl="ParawiseRemark.html?Download"></apptags:filedownload>
																	</c:forEach>
																</c:if>
															</td>
															<!-- <td></td> -->
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>

												</c:if>
									
												<c:if test="${empty command.parawiseRemarkDTOList }">
														<tr class="appendableClassPara">														
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass " id="sequence0"
																value="1" disabled="true" /></td>
														<td><form:input
																	path="parawiseRemarkDTOList[0].parPagno"
																	cssClass="form-control mandColorClass required-control"
																	id="parUadPagno0" disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="parawiseRemarkDTOList[0].parSectionno"
																	cssClass="hasMobileNo required-control form-control"
																	id="parUadSectionno0" disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="parawiseRemarkDTOList[0].refCaseNo"
																	cssClass="hasemailclass required-control form-control"
																	id="refCaseNo0" disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="parawiseRemarkDTOList[0].parComment"
																	cssClass="form-control mandColorClass required-control"
																	id="parUadComment0" disabled="true" /></td>

															<td><form:textarea
																	path="parawiseRemarkDTOList[0].parUadRemark"
																	id="uadParComment0"
																	cssClass="form-control mandColorClass required-control uadParComment preventSpace"
																	maxlength="100" 
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td class="text-center">
																<apptags:formField
																	fieldType="7" hasId="true"
																	fieldPath="parawiseRemarkDTOList[0].attachments[0].uploadedDocumentPath"
																	currentCount="0" showFileNameHTMLId="true"
																	folderName="0" fileSize="COMMOM_MAX_SIZE"
																	isMandatory="false" isReadonly="${command.saveMode eq 'V' ? true : false }"
																	maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
																</apptags:formField> <c:if
																	test="${data.attachDocsList1 ne null  && not empty data.attachDocsList1 }">
																	<c:forEach items="${data.attachDocsList1}"
																		varStatus="status" var="model">
																		<input type="hidden" name="deleteFileId"
																			value="${model.attId}">
																		<apptags:filedownload filename="${model.attFname}"
																			filePath="${model.attPath}"
																			actionUrl="ParawiseRemark.html?Download"></apptags:filedownload>
																	</c:forEach>
																</c:if>
															</td>
															<!-- <td></td> -->
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
				
				<div class="text-center padding-bottom-10">

					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="Proceed(this)">
							<spring:message code="lgl.advocate.proceed" text="Proceed"></spring:message>
						</button>
					</c:if>

					<apptags:backButton url="ParawiseRemark.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
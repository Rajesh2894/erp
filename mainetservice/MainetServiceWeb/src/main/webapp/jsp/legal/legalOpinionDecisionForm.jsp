<!-- Start JSP Necessary Tags -->
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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/legal/legalOpinion.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<!-- Start Content here -->
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lgl.legalOpinion.title" text="Legal Opinion Form" />
			</h2>
			<!-- SET HELP DOC URL WHICH YOU SET IN YOU CONTROLLER INDEX METHOD -->
			<apptags:helpDoc url="LegalOpinion.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="LegalOpinion.html" name="LegalOpinionForm"
				id="LegalOpinionForm" class="form-horizontal">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>


				<div class="panel-group accordion-toggle" id="LegalOpinionDetails">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="lgl.legalOpinion.details" text="Legal Opinion Details" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<label class="control-label col-sm-2"> <spring:message
											code="lgl.dept" text="Department" />
									</label>
									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="legalOpinionDetailDTO.opniondeptId"
											id="legalOpinionDeptid" disabled="true">
											<form:option value="">
												<spring:message code="lgl.select" text="Select" />
											</form:option>
											<c:forEach items="${departments}" var="department">
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() ne '1'}">
														<form:option value="${department.dpDeptid}">${department.dpNameMar}</form:option>
													</c:when>
													<c:otherwise>
														<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</form:select>
									</div>

								</div>
								<div class="form-group">
									<apptags:input labelCode="caseEntryDTO.cseMatdet1"
										path="legalOpinionDetailDTO.matterOfDispute"
										isMandatory="true"
										cssClass="hasSpecialCharAndNumber form-control" maxlegnth="99"
										isDisabled="true" />

									<apptags:input labelCode="caseEntryDTO.cseSectAppl"
										path="legalOpinionDetailDTO.sectionActApplied"
										cssClass="hasSpecialCharAndNumber form-control" maxlegnth="99"
										isDisabled="true" />
								</div>

								<div class="form-group">
									<apptags:textArea labelCode="lgl.opn"
										path="legalOpinionDetailDTO.opinion" isMandatory="true"
										cssClass="hasSpecialCharAndNumber form-control"
										maxlegnth="199"
										isDisabled="${command.saveMode eq 'V' ? true : false }" />
									<apptags:input labelCode="lgl.legalOpinion.DeptRemark"
										path="legalOpinionDetailDTO.deptRemark" isMandatory="true"
										maxlegnth="199" cssClass="hasNameClass form-control"
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>


								</div>


							</div>
						</div>

					</div>
				</div>

				<c:if test="${fn:length(command.cfcAttachment)>0}">
					<div class="table-responsive">
						<table class="table table-bordered table-striped"
							id="cfcattachdoc">

							<tr>
								<th><spring:message code="" text="Attached doc" /></th>
								<th><spring:message code="scheme.view.document"
										text="opinion " /></th>
							</tr>
							<c:forEach items="${command.cfcAttachment}" var="lookUp">
								<tr>
									<td>${lookUp.attFname}</td>
									<td><apptags:filedownload filename="${lookUp.attFname}"
											filePath="${lookUp.attPath}"
											actionUrl="LegalOpinion.html?Download" /></td>
								</tr>
							</c:forEach>
						</table>
					</div>

				</c:if>

				<div class="panel-group accordion-toggle" id="LegalOpinionDetails">
					<div class="panel panel-default">
						<div class="panel-heading">
							<%-- <h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="" text="User Task" />
								</a>
							</h4> --%>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<div class="widget-content padding">
										<apptags:CheckerAction hideForward="true" hideSendback="true"
											hideUpload="true"></apptags:CheckerAction>

										<div class="form-group">
											<label class="col-sm-2 control-label"><spring:message
													code="opinion.document" text="Opinion Document" /></label>
											<c:set var="count" value="0" scope="page"></c:set>		
											<div class="col-sm-4">
												<div id="uploadFiles" class="">
													<apptags:formField fieldType="7" labelCode="" hasId="true"
														fieldPath="documentDetailsList[${count}].uploadedDocumentPath" isMandatory="false"
														showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
														maxFileCount="CHECK_LIST_MAX_COUNT" 	currentCount="${count}" folderName="${count}"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS" />
												</div>
												<h6 class="text-blue-2">
													<spring:message code="uploadfileupto.note" />
												</h6>
											</div>
										</div>
									</div>
									<div class="text-center margin-top-10">

										<input type="button"
											value="<spring:message code="lgl.save" text="Save" />"
											onclick="return saveLegalOpinionDecisionForm(this);"
											class="css_btn btn btn-success">


										<apptags:backButton url="AdminHome.html" />
									</div>


								</div>
							</div>

						</div>
					</div>
					</div>
			</form:form>
		</div>
	</div>
</div>

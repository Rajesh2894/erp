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
				<spring:message code="lgl.opinion.title" text="Legal Opinion Form" />
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
										code="lgl.opinion.det" text="Legal Opinion Details" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="lgl.lglOpn" text="Legal Opinion" /></label>
									<div class="col-sm-4">
										<label class="radio-inline margin-top-5"> <form:radiobutton
												disabled="${command.saveMode eq 'V' ? true : false }"
												path="mode" value="A" id="r1" onclick="getCase()" /> <spring:message
												code='lgl.without.case' text="Without Case" /></label> <label
											class="radio-inline margin-top-5"> <form:radiobutton
												disabled="${command.saveMode eq 'V' ? true : false }"
												path="mode" value="I" id="r2" onclick="getCase()" /> <spring:message
												code='lgl.with.case' text="With Case" />
										</label>
									</div>

								</div>

								<div id="caseNo" style="display: none;">
									<div class="form-group">

										<label class="control-label col-sm-2 required-control">
											<spring:message code="caseEntryDTO.cseSuitNo" text="Case No" />
										</label>
										<div class="col-sm-4">
											<form:select
												class=" mandColorClass form-control chosen-select-no-results"
												path="" id="cseId"
												disabled="${command.saveMode eq 'V' ? true : false }"
												onchange="viewCaseForm(this)">
												<form:option value="">
													<spring:message code="lgl.select" text="Select" />
												</form:option>
												<c:forEach items="${command.caseEntryDTOList}" var="data">
													<form:option value="${data.cseId}">${data.cseSuitNo}</form:option>
												</c:forEach>
											</form:select>
										</div>

									</div>
								</div>
								<div class="form-group withCase">

									<label class="control-label col-sm-2 required-control">
										<spring:message code="lgl.dept" text="Department" />
									</label>
									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="legalOpinionDetailDTO.opniondeptId"
											id="legalOpinionDeptid">
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


								<div id="viewCase"></div>

								<div class="form-group withCase">
									<label class="control-label col-sm-2 required-control">
										<spring:message code="lgl.location" text="Location" />
									</label>
									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="legalOpinionDetailDTO.locId" id="locId2"
											disabled="${command.saveMode eq 'V' ? true : false }">
											<form:option value="">
												<spring:message code="lgl.select" text="Select" />
											</form:option>
											<c:forEach items="${locations}" var="location">
												<form:option value="${location.locId}">${location.locName}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:input labelCode="caseEntryDTO.cseSectAppl"
										path="legalOpinionDetailDTO.sectionActApplied"
										cssClass="hasSpecialCharAndNumber form-control" maxlegnth="99"
										isDisabled="false" />

									<apptags:input labelCode="lgl.opinio.query.dsc"
										path="legalOpinionDetailDTO.matterOfDispute"
										isMandatory="true" cssClass="form-control" maxlegnth="199"
										isDisabled="${command.saveMode eq 'V' ? true : false }" />
								</div>
								<div class="form-group">
									<apptags:input labelCode="lgl.remark"
										path="legalOpinionDetailDTO.remark" isMandatory="true"
										cssClass=" form-control" maxlegnth="1000"
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

									<label class="col-sm-2 control-label"><spring:message
											code="rti.uploadfiles" /> <!-- <span class="mand">*</span> --></label>
									<div class="col-sm-4">
										<div id="uploadFiles" class="">
											<apptags:formField fieldType="7" labelCode="" hasId="true"
												fieldPath="uploadFileList[0]" isMandatory="false"
												showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
												maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
												currentCount="0" />
										</div>

										<h6 class="text-blue-2">
											<spring:message code="rti.uploadfileupto" />
										</h6>
									</div>

								</div>

							</div>
						</div>

					</div>
				</div>

				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button"
							value="<spring:message code="bt.save" text="Save" />"
							onclick="return saveLegalOpinionForm(this);"
							class="css_btn btn btn-success">
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<input type="button" class="btn btn-warning "
							value="<spring:message code="bt.clear" text="Reset"/>"
							onclick="openForm('LegalOpinion.html','ADD')">
					</c:if>
					<apptags:backButton url="LegalOpinion.html" />
				</div>

			</form:form>
		</div>
	</div>
</div>

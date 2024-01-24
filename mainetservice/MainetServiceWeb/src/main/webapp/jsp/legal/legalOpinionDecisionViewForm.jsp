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
				<spring:message code="lgl.legalOpinion.title"
					text="Legal Opinion Form" />
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
										cssClass="hasSpecialCharAndNumber form-control" maxlegnth="99"
										isDisabled="true" />

									<apptags:input labelCode="caseEntryDTO.cseSectAppl"
										path="legalOpinionDetailDTO.sectionActApplied"
										cssClass="hasSpecialCharAndNumber form-control" maxlegnth="99"
										isDisabled="true" />
								</div>

								<div class="form-group">
									<apptags:textArea labelCode="lgl.opn"
										path="legalOpinionDetailDTO.opinion"
										cssClass="hasSpecialCharAndNumber form-control"
										maxlegnth="199" isDisabled="true" />
									<apptags:input labelCode="lgl.legalOpinion.DeptRemark"
										path="legalOpinionDetailDTO.deptRemark" maxlegnth="199"
										cssClass="hasNameClass form-control" isDisabled="true"></apptags:input>


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
								<th><spring:message code="lgl.document.docName" text="Document Name" /></th>
								<th><spring:message code="uploaded.document" text="Uploaded Document " /></th>
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
				<c:if test="${command.viewCommentAndDecision eq 'Y' }">
					<div class="panel-group accordion-toggle" id="LegalOpinionDetails">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#CheckAction"><spring:message
											code="workflow.checkAct.userAct" /></a>
								</h4>
							</div>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<c:set var="radioButtonsRequired">
											<spring:message code="eip.dept.auth.approve" text="Approve" />
										</c:set>
										<c:set var="radioButtonsRequiredVal" value="APPROVED" />
										<c:set var="radioButtonsRequired">${radioButtonsRequired},<spring:message
												code="eip.dept.auth.reject" text="Reject" />
										</c:set>
										<c:set var="radioButtonsRequiredVal"
											value="${radioButtonsRequiredVal},REJECTED" />
										<apptags:radio cssClass="addInfo"
											radioLabel="${radioButtonsRequired}"
											radioValue="${radioButtonsRequiredVal}"
											labelCode="work.estimate.approval.decision"
											path="legalOpinionDetailDTO.decision" disabled="true"
											changeHandler="loadDataBasedOnDecision(this)"></apptags:radio>
									</div>

									<div class="form-group">
										<apptags:textArea labelCode="workflow.checkAct.remark"
											path="legalOpinionDetailDTO.comments" isDisabled="true"
											cssClass="mandColorClass comment" maxlegnth="50" />

									</div>

								</div>

								<c:if test="${fn:length(command.cfcAttachmentList)>0}">
									<h4>
										<spring:message code="opinion.document"
											text="Opinion Document" />
									</h4>
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="cfcattachdoc">
											<tr>
												<th><spring:message code="lgl.document.docName" text="Document Name" /></th>
												<th><spring:message code="uploaded.document" text="Uploaded Document " /></th>
											</tr>
											<c:forEach items="${command.cfcAttachmentList}" var="lookUp">
												<tr>
													<td>${lookUp.attFname}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="LegalOpinion.html?Download" /></td>
												</tr>
											</c:forEach>
										</table>
									</div>

								</c:if>
							</div>
						</div>
					</div>
				</c:if>

				<div class="text-center margin-top-10">
					<apptags:backButton url="LegalOpinion.html" />
				</div>

			</form:form>
		</div>
	</div>
</div>

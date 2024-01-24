<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/treeCuttingtrimingPer.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		<div class="widget-content padding">
			<form:form action="TreeCuttingTrimmingPermission.html" method="post"
				class="form-horizontal" name="nocForOtherGovtDeptChecklist"
				id="nocForOtherGovtDeptChecklist">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="CFC.document.upload" text="Upload Document" />
				</h4>


				<div class="panel panel-default">

					<div id="a5" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-hover table-bordered table-striped">
									<tbody>
										<tr>
											<th><spring:message code="CFC.sr.no" text="Sr.No" /></th>
											<th><spring:message code="CFC.document.group" text="Document Group" /></th>
											<th><spring:message code="CFC.document.status" text="Document Status" /></th>
											<th><spring:message code="CFC.document.upload" text="Upload document" /></th>
										</tr>
										<c:forEach items="${command.checkList}" var="lookUp"
											varStatus="lk">
											<tr>
												<td>${lookUp.documentSerialNo}</td>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
														<td><label>${lookUp.doc_DESC_ENGL}</label></td>
													</c:when>
													<c:otherwise>
														<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
														<td><label>${lookUp.doc_DESC_Mar}</label></td>
													</c:otherwise>
												</c:choose>
												<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
													<td><spring:message code="water.doc.mand" /></td>
												</c:if>
												<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
													<td><spring:message code="water.doc.opt" /></td>
												</c:if>
												<td>
													<div id="docs_${lk}" class="">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="checkList[${lk.index}]" isMandatory="false"
															showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
															checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_BND"
															currentCount="${lk.index}" checkListDesc="${docName}" />
													</div>
													<small class="text-blue-2"> <spring:message
															code="CFC.checklist.validation"
															text="(Upload Image File upto 5 MB)" />
												</small>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button class="btn btn-submit  submit"
						onclick="proceedToSaveDetails(this)" id="Submit" type="button"
						name="button" value="save">
						<i class="button-input"></i>
						<spring:message code="CFC.save" />
					</button>

					<button type="back" class="btn btn-danger"
						onclick="backToTreeForm(this)">
						<spring:message code="NHP.back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>

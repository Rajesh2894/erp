<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script
	src="js/social_security/applicationForm.js"></script>
<div class="pagedivCheckList">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Application Form For Social Security Scheme" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
				</div>
			</div>
			<div class="widget-content padding">
			<form:form id="schemeApplicationFormId"
					action="SchemeApplicationForm.html" method="POST"
					class="form-horizontal" name="schemeApplicationFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="ajaxResponse" style="display: none;"></div>			
				<!---------------------------------------------------------------document upload start------------------------ -->
				<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a5"><spring:message
											text="Document Upload Details" /></a>
								</h4>
							</div>
							<div id="a5" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><spring:message text="Sr.No" /></th>
													<th><spring:message text="Document Group" /></th>
													<th><spring:message text="Document Status" /></th>
													<th><spring:message text="Upload document" /></th>
												</tr>
												<c:forEach items="${command.checkList}" var="lookUp" varStatus="lk">
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
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true" fieldPath="checkList[${lk.index}]"
																	isMandatory="true" showFileNameHTMLId="true"
																	fileSize="BND_COMMOM_MAX_SIZE"
																	checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																	currentCount="${lk.index}" checkListDesc="${docName}" />
															</div>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						</div>
							<div class="text-center">
						<button type="button" class="btn btn-blue-2" title="Submit" onclick="saveCheckListAppForm(this)">
							Submit
						</button>
						<apptags:backButton url="SchemeApplicationForm.html"></apptags:backButton>
					</div>
						<!---------------------------------------------------------------document upload end------------------------ -->
						</form:form>
						</div>
						</div>
						</div>
						</div>
						
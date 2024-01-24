<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/roadcutting/roadCutting.js"></script>
<form:form id="RoadCuttingId" action="RoadCutting.html" method="POST"
					class="form-horizontal" name="RoadCuttingId" >
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#DocumentUpload"><spring:message
								 text="Document Upload" /></a>
					</h4>
				</div>
							<div id="DocumentUpload" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="overflow margin-top-10">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
												<tbody>
													<tr>
														<th><spring:message text="Sr.No" /></th>
														<th><spring:message text="Document Group" /></th>
														<th><spring:message text="Document Status" /></th>
														<th><spring:message text="Upload document" /></th>
													</tr>
													<c:forEach items="${command.checkList}" var="lookUp"
														varStatus="lk">
														<tr>
															<td>${lookUp.documentSerialNo}</td>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<td>${lookUp.doc_DESC_ENGL}</td>
																</c:when>
																<c:otherwise>
																	<td>${lookUp.doc_DESC_Mar}</td>
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
																		hasId="true"
																		fieldPath="command.checkList[${lk.index}]"
																		isMandatory="false" showFileNameHTMLId="true"
																		fileSize="BND_COMMOM_MAX_SIZE"
																		maxFileCount="CHECK_LIST_MAX_COUNT"
																		validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																		currentCount="${lk.index}" />
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
					</div>
					<div class="text-center">
						<button type="button" class="button-input btn btn-success"
							name="button" value="procced" onclick="saveRoadCuttingInfo(this)"
							id="procced">
							<spring:message text="Procced" />
						</button>
						<button type="Reset" class="btn btn-warning" onclick="resetInfo()">
							<spring:message text="Reset" />
						</button>
						<button type="button" class="btn btn-danger" name="button"
							id="Back" value="Back" onclick="">
							<spring:message text="Back" />
						</button>
					</div>
				</form:form>
	


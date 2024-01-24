
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/measurementBook.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	jQuery('#tab6').addClass('active');
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="mb.title" text="Measurement Book" />
			</h2>
			<div class="additional-btn">
				  <apptags:helpDoc url="MeasurementBook.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="MeasurementBook.html" class="form-horizontal"
				name="mbEnclosuresForm" id="mbEnclosuresForm">
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<form:hidden path="requestFormFlag" id="requestFormFlag" />
				<jsp:include page="/jsp/works_management/measurementBookTab.jsp" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:input type="hidden" path="workOrderDto.workId" id="workId" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.order.workOrder.no" text="work Order No." /></label>
					<div class="col-sm-4">
						<form:input path="workOrderDto.workOrderNo"
							cssClass="form-control" id="" readonly="true"
							data-rule-required="true" />
					</div>

				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="work.estimate.enclosures" text="Enclosures" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<form:hidden path="saveMode" id="saveMode" />
							<form:hidden path="removeEnclosureFileById"
								id="removeEnclosureFileById" />
							<div class="panel-body">
								<c:if test="${fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteDoc">
											<tr>
												<th width="" align="center"><spring:message
														code="ser.no" text="" /><input type="hidden" id="srNo"></th>
												<th scope="col" width="64%" align="center"><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th scope="col" width="30%" align="center"><spring:message
														code="scheme.view.document" /></th>
												<c:if test="${command.saveMode eq 'E'}">
													<th scope="col" width="8%"><spring:message
															code="works.management.action" text=""></spring:message></th>
												</c:if>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${e+1}</td>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="MeasurementBook.html?Download" /></td>
													<c:if test="${command.saveMode eq 'E'}">
														<td class="text-center"><a href='#' id="deleteFile"
															onclick="return false;" class="btn btn-danger btn-sm"><i
																class="fa fa-trash"></i></a> <form:hidden path=""
																value="${lookUp.attId}" /></td>
													</c:if>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>

								<div id="uploadTagDiv">
									<div class="table-responsive">
										<c:set var="d" value="0" scope="page" />
										<%-- <c:if test="${command.saveMode ne 'V'}"> --%>
										<table class="table table-bordered table-striped"
											id="attachDoc">
											<tr>
												<th><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th><spring:message code="work.estimate.upload"
														text="Upload Document" /></th>
												<th scope="col" width="8%"><a
													title='<spring:message code="works.management.add" />'
													onclick='fileCountUpload(this);'
													class="btn btn-blue-2 btn-sm addButton"><i
														class="fa fa-plus-circle"></i></a></th>
											</tr>

											<tr class="appendableClass">
												<td><form:input path="attachments[${d}].doc_DESC_ENGL"
														class=" form-control" /></td>
												<td class="text-center"><apptags:formField
														fieldType="7"
														fieldPath="attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
														isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
													</apptags:formField><small class="text-blue-2" style="font-size: unset;"> <spring:message code="wms.doc.validatation"
						                          	 text="(Upload Image File upto 5 MB)" /></small></td>
												<td class="text-center"><a href='#' id="0_file_${d}"
													title='<spring:message code="works.management.delete" />'
													onclick="doFileDelete(this)"
													class='btn btn-danger btn-sm delButton'><i
														class="fa fa-trash"></i></a></td>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</table>
										<%-- </c:if> --%>


									</div>
								</div>

							</div>
							<div class="text-right text-blue-2 margin-top-15">
								<p>
									<strong><spring:message
											code="work.total.estimat.value" /><span id="totalEstatement">${command.totalMbAmount}</span></strong>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							title='<spring:message code="works.management.save" text="Save" />'
							onclick="saveEnclosuresData(this);">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						title='<spring:message code="works.management.back" text="Back" />'
						name="button-Cancel" value="Cancel" onclick="openCheckList();"
						id="backButton">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
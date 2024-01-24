<!-- Start JSP Necessary Tags -->
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/mbMeasurementDetImages.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
#uploadPreview ul li {
	display: inline-block !important;
}

#uploadPreview .img-thumbnail {
	display: block !important
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="" text="Measurement Image Update Summary" />
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
				id="measurementDetImages" name="measurementDetImages">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.estimate.document.description"
							text="Document Description" /></label>
					<div class="col-sm-4">
						<form:input path="docDescription" cssClass="form-control"
							id="docDescription" readonly="" data-rule-required="true" />
					</div>
				</div>
				<div class="table-responsive">
					<c:set var="d" value="0" scope="page" />
					<table class="table table-bordered table-striped" id="attachDoc">
						<tr>
							<th width="20%"><spring:message code="wms.UploadImages"
									text="Upload Images" /></th>
							<th width="32%"><spring:message code="wms.UploadedImages"
									text="Uploaded Images" /></th>
						</tr>
						<tbody>


							<tr class="appendableClass">
								<td class="text-center"><apptags:formField fieldType="7"
										fieldPath="attachDocs[${d}].uploadedDocumentPath"
										currentCount="${d}" showFileNameHTMLId="true"
										folderName="${d}" fileSize="COMMOM_MAX_SIZE"
										isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
										validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
										callbackOtherTask="otherUploadTask();">
									</apptags:formField></td>

								<td><div id="uploadPreview">
										<c:forEach items="${command.fileNames}" var="entry">
											<c:if test="${entry.key eq d}">

												<ul>
													<c:forEach items="${entry.value}" var="val">
														<li id=""><img src="${val}" width="100" height="100"
															class="img-thumbnail"><a href="${val}" download><i
																class="fa fa-download"></i></a></li>
													</c:forEach>
												</ul>

											</c:if>
										</c:forEach>
									</div></td>
							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />

						</tbody>
					</table>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveMbUploadedImage();">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<button type="button"
						class="button-input btn btn-danger backButton"
						name="button-Cancel" value="Cancel" onclick="measurementSheet();">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
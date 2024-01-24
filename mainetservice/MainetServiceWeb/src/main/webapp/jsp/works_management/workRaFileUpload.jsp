<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/workMbApproval.js"></script>

<div class="table-responsive">
	<c:set var="d" value="0" scope="page" />
	<table class="table table-bordered table-striped" id="attachDoc">
		<tr>
			<th><spring:message code="work.estimate.document.description"
					text="Document Description" /></th>
			<th><spring:message code="work.estimate.upload"
					text="Upload Document" /></th>
			<th scope="col" width="8%"><a onclick='fileCountInspectionUpload(this);'
				class="btn btn-blue-2 btn-sm addButton" title="Add"> <i
					class="fa fa-plus-circle"></i></a></th>
		</tr>
		<c:forEach var="count" items="${command.inspectionAttachment}">
			<c:if test="${count.doc_DESC_ENGL ne null}">
				<tr class="appendableClass">
					<td><form:input
							path="command.inspectionAttachment[${d}].doc_DESC_ENGL"
							class=" form-control" /></td>
					<td class="text-center"><apptags:formField fieldType="7"
							fieldPath="command.inspectionAttachment[${d}].uploadedDocumentPath"
							currentCount="${d}" showFileNameHTMLId="true" folderName="${d}"
							fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
							maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
							validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
							callbackOtherTask="otherTask();">
						</apptags:formField></td>
					<td class="text-center"><a href='#' id="0_file_${d}" title="Delete"
						onclick="doFileDelete(this)"
						class='btn btn-danger btn-sm delButton'><i class="fa fa-trash"></i></a></td>
				</tr>
			</c:if>
			<c:set var="d" value="${d + 1}" scope="page" />
		</c:forEach>
	</table>
</div>

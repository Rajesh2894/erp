<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<!-- <script type="text/javascript" src="js/mainet/file-upload.js"></script> -->
<script type="text/javascript" src="js/dms/dmsFileUpload.js"></script>

<div class="table-responsive">
	<c:set var="d" value="0" scope="page" />
	<table class="table table-bordered table-striped" id="attachDoc">
		<tr>
			<th><spring:message code="" text="Document Description" /></th>
			<th><spring:message code="" text="Upload" /></th>
			<th><spring:message code="" text="Action" /></th>
			<th scope="col" width="8%"><a onclick='documentUpload(this);'
				class="btn btn-blue-2 btn-sm addButton"> <i
					class="fa fa-plus-circle"></i></a></th>
		</tr>
		<c:forEach var="count" items="${command.attachments}">
			<tr class="appendableClass" id="attachments">
				<c:if test="${count.doc_DESC_ENGL ne null}">
					<td><form:select id="doc_DESC_ENGL${d}"
							path="command.attachments[${d}].doc_DESC_ENGL"
							cssClass="form-control" hasId="true" disabled="false"
							data-rule-required="false">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.docTypeList}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select> <%-- <form:input path="command.attachments[${d}].doc_DESC_ENGL"
							class=" form-control" /> --%></td>
					<td class="text-center"><apptags:formField fieldType="7"
							fieldPath="command.attachments[${d}].uploadedDocumentPath"
							currentCount="${d}" showFileNameHTMLId="true" folderName="${d}"
							fileSize="CHECK_COMMOM_MAX_SIZE" isMandatory="false"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="ALL_UPLOAD_VALID_EXTENSION">
						</apptags:formField></td>
						<td class="text-center">
										<input type="button" value="<spring:message code="dms.scanDocument"/>"
						                onclick="scanDocument(this)" class="btn btn-blue-3" id="${d}" >
										</td>
					<td class="text-center"><a href='#' id="0_file_${d}"
						onclick="doFileDelete(this)" class='btn btn-danger btn-sm delBtn'><i
							class="fa fa-trash"></i></a></td>
				</c:if>
			</tr>
			<c:set var="d" value="${d + 1}" scope="page" />
		</c:forEach>
	</table>
</div>

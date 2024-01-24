<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/asset/assetDocumentDetails.js"></script>
<style>
input {
	width: 200px;
	padding: 0 20px;
}

input, input::-webkit-input-placeholder {
	font-size: 10px;
	line-height: 2;
}
</style>
<div class="table-responsive">
	<c:set var="index" value="0" scope="page" />
	<table class="table table-bordered table-striped" id="attachDoc">
		<tr>
			<%--  <th><spring:message code="" text="Sr No." /></th>  --%>
			<th><spring:message code="work.estimate.document.description"
					text="Document Description" /></th>
			<th><spring:message code="scheme.master.upload" text="Upload" /></th>
			<th scope="col" width="8%"><a onclick='fileCountUpload(this);'
				class="btn btn-blue-2 btn-sm addButton"> <i
					class="fa fa-plus-circle"></i></a></th>
		</tr>
		<c:forEach var="count" items="${command.astDetailsDTO.attachments}">
			<c:if test="${count.doc_DESC_ENGL ne null}">
				<tr class="appendableClass">
					<%--  <td id="sequnce">${d}</td> --%>
					<td><form:input placeholder="enter document description"
							path="command.astDetailsDTO.attachments[${index}].doc_DESC_ENGL"
							class=" form-control" maxlength="100" /></td>
					<td class="text-center"><apptags:formField fieldType="7"
							fieldPath="command.astDetailsDTO.attachments[${index}].uploadedDocumentPath"
							currentCount="${index}" showFileNameHTMLId="true"
							folderName="${index}" fileSize="WORK_COMMON_MAX_SIZE"
							isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="ALL_UPLOAD_VALID_EXTENSION">
						</apptags:formField>
						<small class="text-blue-2"><spring:message code="work.file.upload.tooltip" text="(Upload File upto 50MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp,xls,xlsx extension(s) file(s) are allowed.)" /></small></td>
					<td class="text-center"><a href='#' id="0_file_${index}"
						onclick="doFileDelete(this)"
						class='btn btn-danger btn-sm delButton'><i class="fa fa-trash"></i></a></td>
				</tr>
			</c:if>

			<c:set var="index" value="${index + 1}" scope="page" />

		</c:forEach>
	</table>
</div>

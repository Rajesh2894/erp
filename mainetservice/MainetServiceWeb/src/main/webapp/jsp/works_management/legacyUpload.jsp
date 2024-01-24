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
	src="js/works_management/legacyMeasurementBook.js"></script>
<style>
#uploadPreview ul li {
	display: inline-block !important;
}

#uploadPreview .img-thumbnail {
	display: block !important
}
</style>
<div class="table-responsive">
	<c:set var="d" value="0" scope="page" />
	<table class="table table-bordered table-striped" id="attachDoc">
		<tr>
			<th width="20%"><spring:message code=""
					text="Old Measurement Book No." /></th>
			<th width="20%"><spring:message
					code="mb.ActualMeasurementTakenDate"
					text="Actual Measurement Taken Date" /></th>
			<th width="20%"><spring:message code=""
					text="Measurement Book Amount" /></th>
			<th width="20%"><spring:message code="wms.UploadImages"
					text="Upload Images" /></th>
			<th scope="col" width="8%"><a onclick='fileCountUpload(this);'
				class="btn btn-blue-2 btn-sm addButton"> <i
					class="fa fa-plus-circle"></i></a></th>
		</tr>
		<c:forEach var="count" items="${command.mbList}">
			<tbody>
				<tr class="appendableClass">
					<td><form:input path="command.mbList[${d}].oldMbNo"
							class="form-control mandColorClass text-center" id="oldMbNo${d}" /></td>
					<td><form:input path="command.mbList[${d}].workMbTakenDate"
							class="form-control datepicker mandColorClass text-center"
							id="workMbTakenDate${d}" /></td>
					<td><form:input path="command.mbList[${d}].mbTotalAmt"
							class=" form-control mandColorClass text-right numbersOnly"
							onkeypress="return hasAmount(event, this,10, 2)"
							onchange="getAmountFormatInDynamic((this),'mbTotalAmt')"
							id="mbTotalAmt${d}" /></td>
					<td class="text-center"><apptags:formField fieldType="7"
							fieldPath="command.attachments[${d}].uploadedDocumentPath"
							currentCount="${d}" showFileNameHTMLId="true" folderName="${d}"
							fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="ALL_UPLOAD_VALID_EXTENSION">
						</apptags:formField></td>

					<td class="text-center"><a href='#' id="0_file_${d}"
						class='btn btn-danger btn-sm delButton'
						onclick="doFileDeletion($(this),${d});"><i class="fa fa-trash"></i></a></td>
				</tr>
			</tbody>
			<c:set var="d" value="${d + 1}" scope="page" />
		</c:forEach>
	</table>
</div>

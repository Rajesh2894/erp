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
	src="js/works_management/milestoneProgress.js"></script>
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
			<th><spring:message code="wms.ProgressUpdateDate" text="Progress Update Date" /></th>
			<th><spring:message code="wms.PhysicalProgressPercent" text="Physical Progress %" /></th>
			<th><spring:message code="wms.UploadImages" text="Upload Images" /></th>
			<th><spring:message code="wms.UploadedImages" text="Uploaded Images" /></th>
			<th scope="col" width="8%"><a onclick='fileCountUpload(this);'
				class="btn btn-blue-2 btn-sm addButton"> <i
					class="fa fa-plus-circle"></i></a></th>
		</tr>
		<c:forEach var="count" items="${command.progressList}">
			<tbody>
				<tr class="appendableClass">
					<td><form:input
							path="command.progressList[${d}].proUpdateDate"
							class="form-control datepicker mandColorClass text-center"
							id="proUpdateDate${d}" /></td>
					<td><form:input path="command.progressList[${d}].phyPercent"
							class=" form-control mandColorClass text-right numbersOnly"
							onkeypress="return hasAmount(event, this, 3, 2)"
							onchange="getAmountFormatInDynamic((this),'phyPercent')"
							id="phyPercent${d}" /></td>
					<td class="text-center"><apptags:formField fieldType="7"
							fieldPath="command.attachments[${d}].uploadedDocumentPath"
							currentCount="${d}" showFileNameHTMLId="true" folderName="${d}"
							fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
							maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
							validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
							callbackOtherTask="otherTask();">
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
					<td class="text-center"><a href='#' id="0_file_${d}"
						class='btn btn-danger btn-sm delButton'
						onclick="doFileDeletion($(this),${d});"><i class="fa fa-trash"></i></a></td>
				</tr>
			</tbody>
			<c:set var="d" value="${d + 1}" scope="page" />
		</c:forEach>
	</table>
</div>

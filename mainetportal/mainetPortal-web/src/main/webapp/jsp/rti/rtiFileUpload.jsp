<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/script-library.js"></script>
<!-- <script src="js/rti/rtiApplicationForm.js"></script> -->

<script>
jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});
</script>

<div class="table-responsive">
	<c:set var="d" value="0" scope="page" />
	<table class="table table-bordered table-striped" id="attachDoc">
		<tr>
			<th><spring:message code="rti.serialNo"></spring:message></th>
			<th><spring:message code="rti.chargemediaType"></spring:message><span
				class="mand">*</span></th>
			<th><spring:message code="rti.quantity"></spring:message><span
				class="mand">*</span></th>
			<th><spring:message code="account.common.desc"></spring:message><span
				class="mand">*</span></th>
			<th><spring:message code="scheme.master.upload" text="Upload" /></th>
			<th scope="col" width="8%"><a onclick='fileCountUpload(this);'
				class="btn btn-blue-2 btn-sm addCF"> <i
					class="fa fa-plus-circle"></i></a></th>
		</tr>
		<c:forEach var="count" items="${command.reqDTO.documentList}">
			<tr class="appendableClass">
				<%-- <c:if test="${count.doc_DESC_ENGL ne null}"> --%>
				<%--  <td ${d+1}></td> --%>
				<td width=8%><form:input path="command.reqDTO.rtiMediaListDTO[${d}].mediaSerialNo" type="text"
						class="form-control text-center unit required-control"
						id="unitNo0" value="${d+1}" /></td>
				<%-- <td><form:input path="command.attachments[${d}].doc_DESC_ENGL" class=" form-control"/></td> --%>
				<td>
					<c:set var="baseLookupCode" value="MDT" />
					<form:select path="command.reqDTO.rtiMediaListDTO[${d}].mediaType" class="form-control mandColorClass required-control"
						id="mediaType" data-rule-required="true">
						<c:forEach
							items="${command.getLevelData(baseLookupCode)}"
							var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}
							</form:option>
						</c:forEach>
					</form:select>				
				</td>
				<td><form:input class="form-control mandColorClass hasNumber" id="quantity"
						path="command.reqDTO.rtiMediaListDTO[${d}].quantity" data-rule-required="true"></form:input></td>
				<td><form:input class="form-control mandColorClass" id="info"
						path="command.reqDTO.rtiMediaListDTO[${d}].description" data-rule-required="true"></form:input></td>
				<td class="text-center"><apptags:formField fieldType="7"
						fieldPath="command.reqDTO.documentList[${d}]"
						currentCount="${d}" showFileNameHTMLId="true" folderName="${d}"
						fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
						maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
					</apptags:formField></td>
				<td class="text-center"><a href='#' id="0_file_${d}"
					onclick="doFileDelete(this); deleteRow(this);"
					class='btn btn-danger btn-sm delButton'><i class="fa fa-minus"></i></a></td>
				<%-- </c:if> --%>
			</tr>
			<c:set var="d" value="${d + 1}" scope="page" />
			<%-- 	 <c:out value="${d}" /> --%>
		</c:forEach>
	</table>
</div>

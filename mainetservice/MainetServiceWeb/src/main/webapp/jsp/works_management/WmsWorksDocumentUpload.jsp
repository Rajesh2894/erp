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
	src="js/works_management/wmsWorkDefination.js"></script>
  <div class="table-responsive">
<c:set var="d" value="0" scope="page" />
	 <table class="table table-bordered table-striped" id="attachDoc">
		<tr>
			<%--  <th><spring:message code="" text="Sr No." /></th> --%>
			<th><spring:message code="work.management.description" text="Document Description" /></th>
			<th><spring:message code="scheme.master.upload" text="Upload" /></th> 
			<th scope="col" width="8%"></th>
			</tr>
		  <c:forEach var="count" items="${command.attachments}">
			<tr class="appendableClass">
			<c:if test="${count.doc_DESC_ENGL ne null}">
		    <!-- <td id="sequnce"></td> -->
			 <td><form:input path="command.attachments[${d}].doc_DESC_ENGL" class=" form-control"/></td>
		    <td class="text-center"><apptags:formField fieldType="7" fieldPath="command.attachments[${d}].uploadedDocumentPath"
			currentCount="${d}" showFileNameHTMLId="true" folderName="${d}" fileSize="MAX_FILE_SIZE"
			isMandatory="false"
			maxFileCount="CHECK_LIST_MAX_COUNT"
			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
			</apptags:formField>
			</td>
			<td align="center">
				<a href="javascript:void(0);" onclick='documentUpload(this);' class="btn btn-blue-2 btn-sm addButton mr-2"><i
				class="fa fa-plus-circle"></i></a> <a href='#' id="0_file_${d}" onclick="doFileDelete(this)" class='btn btn-danger btn-sm delBtn'><i
				class="fa fa-trash"></i></a></td>
			</c:if>
		</tr>
		 <c:set var="d" value="${d + 1}" scope="page"/> 
		 </c:forEach> 	 
	</table> 
</div>  
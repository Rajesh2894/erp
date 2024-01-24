<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript" src="js/rti/rtiPioTable.js"></script>

<script>
	jQuery('.hasNumber').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');

	});
</script>
<div id="uploadTagDiv">
	<div class="table-responsive">
		<c:set var="d" value="0" scope="page" />
		<table class="table table-bordered table-striped" id="attachDoc">
			<tr>
				<th><spring:message code="rti.serialNo"></spring:message></th>
				<th><spring:message code="rti.chargemediaType"></spring:message><span
					class="mand">*</span></th>
				<th><spring:message code="rti.quantity"></spring:message><span
					class="mand">*</span></th>
				<th><spring:message code="rti.common.desc" text=""></spring:message><span
					class="mand">*</span></th>
				<th><spring:message code="scheme.master.upload" text="Upload" /></th>
				<th scope="col" width="8%"><a href="javascript:void(0);"
					onclick='fileCountUpload(this);'
					class=" addCF btn btn-blue-2 btn-sm"> <i
						class="fa fa-plus-circle"></i></a></th>
			</tr>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(command.rtiMediaListDTO) > 0}">
						<c:forEach var="count" items="${command.rtiMediaListDTO}">
							<tr class="appendableClass">
								<%-- <c:if test="${count.doc_DESC_ENGL ne null}"> --%>
								<%--  <td ${d+1}></td> --%>
								<td width=8%><form:input
										path="command.rtiMediaListDTO[${d}].mediaSerialNo" type="text"
										class="form-control text-center unit required-control"
										id="unitNo${d}" value="${d+1}" /></td>
								<%-- <td><form:input path="command.attachments[${d}].doc_DESC_ENGL" class=" form-control"/></td> --%>
								<td><c:set var="baseLookupCode" value="MDT" /> <form:select
										path="command.rtiMediaListDTO[${d}].mediaType"
										class="form-control mandColorClass required-control"
										id="mediaType${d}" data-rule-required="true">
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}
							</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:input
										class="form-control mandColorClass hasNumber"
										id="quantity${d}"
										path="command.rtiMediaListDTO[${d}].quantity"
										data-rule-required="true" maxlength="5"></form:input></td>
								<td><form:input class="form-control mandColorClass"
										id="mediaDesc${d}"
										path="command.rtiMediaListDTO[${d}].mediaDesc"
										data-rule-required="true" maxlength="199"></form:input></td>
								<td class="text-center"><apptags:formField fieldType="7"
										fieldPath="command.pioDoc[${d}]" currentCount="${d}"
										showFileNameHTMLId="true" folderName="${d}"
										fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
										maxFileCount="CHECK_LIST_MAX_COUNT"
										validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
									</apptags:formField></td>
								<td class="text-center"><a href='#' id="file_list_${d}"
									class='btn btn-danger btn-sm delButton'
									onclick="doFileDeletion($(this),${d});"><i
										class="fa fa-trash"></i></a></td>
								<%-- </c:if> --%>
							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
							<%-- 	 <c:out value="${d}" /> --%>
						</c:forEach>

					</c:when>
					<c:otherwise>
						<tr class="appendableClass">

							<td width=8%><form:input
									path="command.rtiMediaListDTO[${d}].mediaSerialNo" type="text"
									class="form-control text-center unit required-control"
									id="unitNo${d}" value="${d+1}" /></td>
							<%-- <td><form:input path="command.attachments[${d}].doc_DESC_ENGL" class=" form-control"/></td> --%>
							<td><c:set var="baseLookupCode" value="MDT" /> <form:select
									path="command.rtiMediaListDTO[${d}].mediaType"
									class="form-control mandColorClass required-control"
									id="mediaType${d}" data-rule-required="true">
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}
							</form:option>
									</c:forEach>
								</form:select></td>
							<td><form:input
									class="form-control mandColorClass hasNumber" id="quantity${d}"
									path="command.rtiMediaListDTO[${d}].quantity"
									data-rule-required="true" maxlength="5"></form:input></td>
							<td><form:input class="form-control mandColorClass"
									id="mediaDesc${d}"
									path="command.rtiMediaListDTO[${d}].mediaDesc"
									data-rule-required="true" maxlength="190"></form:input></td>
							<td class="text-center"><apptags:formField fieldType="7"
									fieldPath="command.pioDoc[${d}]" currentCount="${d}"
									showFileNameHTMLId="true" folderName="${d}"
									fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
								</apptags:formField></td>
							<td class="text-center"><a href='#' id="file_list_${d}"
								class='btn btn-danger btn-sm delButton'
								onclick="doFileDeletion($(this),${d});"><i
									class="fa fa-trash"></i></a></td>
							<%-- </c:if> --%>
						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:otherwise>
				</c:choose>
			</tbody>

		</table>
	</div>
</div>

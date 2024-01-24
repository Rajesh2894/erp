<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<!-- <script type="text/javascript" src="js/mainet/file-upload.js"></script> -->
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript" src="js/dms/metadataDetails.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/dms/dmsFileUpload.js"></script>
<style>
table.table tbody tr td {
	font-size: 14px !important;
}
</style>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Metadata Details" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>

			<!-- Start Form -->
			<form:form action="DmsMetadata.html" method="POST"
				commandName="command" class="form-horizontal form" name=""
				id="frmMetadata">
            <form:hidden path="dmsId" id="dmsId" />  
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "> <spring:message
							code="dms.department" text="Department"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="deptId" id="deptId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.departmentList}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>

					</div>
					<c:choose>
						<c:when test="${command.deptCode eq 'ADH'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="ADZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'BND'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="BZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'CFC'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="CWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'HSM'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="SZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'LTD'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="LZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'PYD'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="PZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'AS'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'WMS'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'RL'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="LZB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'RTI'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="RWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'SWM'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'ML'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="MWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control " showAll="false"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'WT'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control  " showAll="false"
									showData="true" />
							</div>
						</c:when>

						<c:otherwise>
						</c:otherwise>
					</c:choose>
					<%-- <apptags:input labelCode="dms.docRefNo" path="docRefNo"
						cssClass="mandColorClass"></apptags:input> --%>
				</div>



				<c:set var="i" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="frmMetadataTable">
							<thead>
								<tr>
									<th width="10%"><spring:message code="dms.srNo"
											text="Sr. No." /></th>
									<th><spring:message code="dms.metadata"
											text="Metadata Description" /></th>
									<th><spring:message code="dms.value" text="Value" /></th>
								</tr>
							</thead>
							<c:forEach items="${command.metadataList}" var="lookUp"
								varStatus="loop">
								<tbody>
									<td class="text-center">${loop.count}</td>
									<td class="text-center">${lookUp.lookUpDesc}</td>
									<td class="text-center"><form:hidden
											path="lookUpList[${i}].lookUpId" value="${lookUp.lookUpId}" />
										<form:input path="lookUpList[${i}].otherField"
											class="form-control" /></td>
									<c:set var="i" value="${i + 1}" scope="page" />
								</tbody>
							</c:forEach>
						</table>
					</div>
				</div>

				<%-- <div class="form-group" id="reload">
					<label class="col-sm-2 control-label " for=""><spring:message
							code="dms.docType" text="Document Type" /></label>
					<c:set var="baseLookupCode" value="DCT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="documentType" cssClass="form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />

					<apptags:formField fieldType="9"
						cssClass="mandClassColor empname subsize"
						fieldPath="entity.profile_img_path" labelCode="Upload Document"
						showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn"
						randNum="${command.getUUIDNo()}" isMandatory="true" />

					<c:forEach items="${command.attachNameList}" var="Namelist"
						varStatus="sts">
						<c:set var="count" value="${sts.count}" />
						<c:if test="${not empty Namelist}">
							<div id="filid_${count}" class="documentDiv">
								<p>${Namelist}<img src="css/images/close.png" width="17"
										alt='Delete File'
										onclick="deleteUploadFileFromEIP(this,'${Namelist}','${count}','file')">
								</p>
							</div>
						</c:if>
					</c:forEach>
				</div> --%>

				<div id="uploadTagDiv">
					<div class="table-responsive">
						<c:set var="d" value="0" scope="page"></c:set>
						<table class="table table-bordered table-striped" id="attachDoc">
							<tr>
								<th><spring:message code="" text="Document Description" /></th>
								<th><spring:message code="" text="Upload" /></th>
								<th><spring:message code="" text="Action" /></th>
								<th scope="col" width="8%"><a
									onclick='documentUpload(this);'
									class="btn btn-blue-2 btn-sm addButton"> <i
										class="fa fa-plus-circle"></i></a></th>
							</tr>
							<tr class="appendableClass">
								<td><form:select id="doc_DESC_ENGL${d}"
										path="attachments[${d}].doc_DESC_ENGL" cssClass="form-control"
										hasId="true" disabled="false" data-rule-required="false">
										<form:option value="0">
											<spring:message code="Select" text="Select" />
										</form:option>
										<c:forEach items="${command.docTypeList}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select> <%-- <form:input path="attachments[${d}].doc_DESC_ENGL"
										class=" form-control"
										onkeyup="inputPreventSpace(event.keyCode,this);" /> --%></td>
								<td class="text-center"><apptags:formField fieldType="7"
										fieldPath="attachments[${d}].uploadedDocumentPath"
										currentCount="${d}" showFileNameHTMLId="true"
										folderName="${d}" fileSize="CHECK_COMMOM_MAX_SIZE"
										isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
										validnFunction="ALL_UPLOAD_VALID_EXTENSION">
									</apptags:formField></td>
										<td class="text-center">
										<input type="button" id="${d}" value="<spring:message code="dms.scanDocument"/>"
						                onclick="scanDocument(this)" class="btn btn-blue-3">
										</td>
								<td class="text-center"><a href='#' id="0_file_${d}"
									onclick="doFileDelete(this)"
									class='btn btn-danger btn-sm delBtn'> <i
										class="fa fa-trash"></i></a></td>
							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</table>
					</div>
				</div>

				<div class="text-center clear padding-10">

					<input type="button" value="<spring:message code="bt.save"/>"
						onclick="saveForm(this)" class="btn btn-success" id="Save">
						
					<%-- 	<input type="button" value="<spring:message code="dms.scanDocument"/>"
						onclick="scanDocument(this)" class="btn btn-blue-3" id="Save"> --%>

					<button type="button" id="reset"
						onclick="window.location.href='DmsMetadata.html'"
						class="btn btn-warning" title="Reset">
						<spring:message code="bt.clear" text="Reset" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>

				</div>

			</form:form>
		</div>
	</div>
</div>
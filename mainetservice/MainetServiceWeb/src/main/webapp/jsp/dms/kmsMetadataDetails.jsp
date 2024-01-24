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

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/dms/kmsMetadataDetails.js"></script>
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
				<strong><spring:message code="" text="KMS Metadata Details" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>

			<!-- Start Form -->
			<form:form action="KmsMetadata.html" method="POST"
				commandName="command" class="form-horizontal form" name=""
				id="frmMetadata">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<form:hidden path="kms" value="KMS" />
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
				</div>

				<c:if test="${command.metadataList ne null}">
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
				</c:if>

				<div class="form-group" id="reload">
					<%-- <label class="col-sm-2 control-label " for=""><spring:message
							code="dms.docType" text="Document Type" /></label>
					<c:set var="baseLookupCode" value="KDT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="documentType" cssClass="form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" /> --%>

					<%-- <apptags:radio radioLabel="dms.employee,dms.role" radioValue="E,R"
						labelCode="dms.knowledge" isMandatory="true"
						path="knowledgeSharing" /> --%>

					<label class="col-sm-2 control-label "><spring:message
							code="dms.knowledge" text="Knowledge Sharing" /></label>
					<div class="radio col-sm-4 margin-top-5">
						<label> <form:radiobutton path="knowledgeSharing"
								value="E" id="employee" onclick="getEmpOrRoleList()"
								checked="checked" /> <spring:message code="dms.employee" /></label> <label>
							<form:radiobutton path="knowledgeSharing" value="R" id="role"
								onclick="getEmpOrRoleList()" /> <spring:message code="dms.role" />
						</label> <label> <form:radiobutton path="knowledgeSharing"
								value="D" id="assignedDept" onclick="getEmpOrRoleList()" /> <spring:message
								code="dms.deptWise" /></label>
					</div>
				</div>


				<div class="form-group empClass">
					<label class="col-sm-2 control-label "> <spring:message
							code="" text="Employee Name"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="employeeIds" id="emp"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true" multiple="true">
							<form:option value="0">
								<spring:message code="dms.allEmps" text="All Employees" />
							</form:option>
							<c:forEach items="${command.employeeList}" var="emp">
								<form:option value="${emp.empId}">${emp.empname} ${emp.empmname} ${emp.emplname}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group roleClass">
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Role" /></label>
					<div class="col-sm-4">
						<form:select path="roleIds"
							cssClass="form-control chosen-select-no-results" id="gmid"
							multiple="true">
							<form:option value="0">
								<spring:message code="dms.allRoles" text="All Roles" />
							</form:option>
							<c:forEach items="${command.roleList}" var="map">
								<form:option value="${map.key}">${map.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group assignDeptClass">
					<label class="col-sm-2 "><spring:message
							code="dms.assignDept" text="Assigned Department" /></label>
					<div class="col-sm-4">
						<form:select id="assignDeptIds" path="assignDeptIds"
							cssClass="form-control chosen-select-no-results" multiple="true">
							<form:option value="0">
								<spring:message code="dms.allDepts" text="All Departments" />
							</form:option>
							<c:forEach items="${command.assignedDeptList}"
								var="departMstData">
								<form:option value="${departMstData.dpDeptid}">${departMstData.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div id="uploadTagDiv">
					<div class="table-responsive">
						<c:set var="d" value="0" scope="page"></c:set>
						<table class="table table-bordered table-striped" id="attachDoc">
							<tr>
								<th><spring:message code="" text="Document Description" /></th>
								<th><spring:message code="" text="Upload" /></th>
								<th scope="col" width="8%"><a
									onclick='documentUpload(this);'
									class="btn btn-blue-2 btn-sm addButton"> <i
										class="fa fa-plus-circle"></i></a></th>
							</tr>
							<tr class="appendableClass" id="attachments">
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

					<button type="button" id="reset"
						onclick="window.location.href='KmsMetadata.html'"
						class="btn btn-warning" title="Reset">
						<spring:message code="bt.clear" text="Reset" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>

				</div>

			</form:form>
		</div>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/mainet/script-library.js"></script>
<script type="text/javascript" src="js/intranet/intranetUploadDocSummary.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="intranet.uploadDoc"	text="Intranet Upload Documents"></spring:message></strong>
				<apptags:helpDoc url="UploadIntranetDocSummary.html"></apptags:helpDoc>
			</h2>
		</div>

		<div class="widget-content padding">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<form:form action="UploadIntranetDocSummary.html" id="frmUploadIntranetDocSummary" method="POST" commandName="command" class="form-horizontal form">

				<div class="form-group">
					<apptags:input labelCode="intranet.DocName"
						path="intranetDto.docName" isMandatory="true"
						cssClass="hasSpecialChar form-control" maxlegnth="100">
					</apptags:input>
					<apptags:input labelCode="intranet.DocDesc"
						path="intranetDto.docDesc" isMandatory="true"
						cssClass="hasSpecialChar form-control" maxlegnth="100">
					</apptags:input>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="department">
						<spring:message code="intranet.dept" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="intranetDto.deptId" cssClass="form-control"
							id="department" data-rule-required="true" isMandatory="true">
							<form:option value="">
								<spring:message code="intranet.select" text="Select" />
							</form:option>
							<form:option value="0">
								<spring:message text="All" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 required-control" for="Census">
						<spring:message code="intranet.docCat" text="Document Category" />
					</label>
					<c:set var="baseLookupCode" value="IDC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="intranetDto.docCateType" cssClass="form-control"
						isMandatory="true" hasId="true"
						selectOptionLabelCode="selectdropdown" />
				</div>
				
				<div class="form-group">
					<apptags:date fieldclass="lessthancurrdate" labelCode="intranet.DocFromDate"
						datePath="intranetDto.docFromDate" isMandatory="true">
					</apptags:date>
					<apptags:date fieldclass="datepicker" labelCode="intranet.DocToDate"
						datePath="intranetDto.docToDate" isMandatory="true">
					</apptags:date>
				</div>						
				
				<div class="form-group">
					<apptags:input labelCode="intranet.DocCatagoryOrder"
						path="intranetDto.docCatOrder" isMandatory="true" cssClass="hasNumber form-control" maxlegnth="10">
					</apptags:input>
					<%-- <apptags:input labelCode="intranet.DocCatagoryType"
						path="intranetDto.docOrderNo" isMandatory="true" cssClass="hasNumber form-control" maxlegnth="10">
					</apptags:input> --%>
				</div>

				<div class="form-group">
					<apptags:radio
						radioLabel="Active,Inactive" radioValue="A,I" labelCode="Document Status"
						path="intranetDto.docStatus" defaultCheckedValue="A" />
				</div>

				<div class="form-group" id="reload">
					<label class="col-sm-2 control-label" for="ExcelFileUpload"><spring:message
							code="intranet.fileUpld" text="File Upload" /><i class="text-red-1">*</i></label>
					<div class="col-sm-2 text-left">
						<apptags:formField fieldPath="uploadFileList"
							showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION_BND"
							currentCount="0" fieldType="7">
						</apptags:formField>
						<h6 class="text-blue-2">
							<spring:message code="intranet.uploadfileupto" />
						</h6>
					</div>
					
					<form:hidden path="uploadFileList" id="filePath" />
				</div>
				
				<form:input type="hidden" value="${command.saveMode}" id="saveMode" path="intranetDto.saveMode"/>

				<c:if test="${command.saveMode eq 'E'}">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="ExcelFileUpload">
							<spring:message code="intranet.upldFileNm" text="Uploaded File Name" />
						</label>&nbsp
						<apptags:filedownload filename="${command.intranetDto.atdFname}"
							filePath="${command.intranetDto.atdPath}" actionUrl="DeathRegistration.html?Download" >
						</apptags:filedownload>
						
					</div>
				</c:if>
				
				<div class="text-center">
					<input type="button" value="<spring:message code="bt.save"/>"
						onclick="saveIntranetUploadData(this)" class="btn btn-success" id="Submit">
					
					<input type="button" value="<spring:message code="bt.backBtn"/>"
						onclick="window.location.href='UploadIntranetDocSummary.html'" class="btn btn-danger hidden-print">
				</div>

			</form:form>
		</div>
	</div>
</div>

<!-- ashish test -->



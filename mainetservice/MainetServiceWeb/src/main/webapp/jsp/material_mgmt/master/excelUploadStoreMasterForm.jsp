<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/material_mgmt/master/StoreMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<!-- Start Main Page Heading -->
		<div class="widget-header">
			<h2>
				<spring:message code="store.master.heading"
					text="Store Master" />
			</h2>
			<apptags:helpDoc url="StoreMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="StoreMaster.html" name="StoreMaster"
				id="StoreMasterMasterForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
							code="store.master.excel.upload" text="Excel Upload" /></label>
					<div class="col-sm-4">
						<apptags:formField fieldPath="excelFilePath" labelCode="material.management.add"
						showFileNameHTMLId="true"
							fileSize="WORK_COMMON_MAX_SIZE" 
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
							currentCount="0" fieldType="7">
						</apptags:formField>
						<small class="text-blue-2" style="padding-left: 10px;"><spring:message
								code="store.master.excel.upload.max"
								text="(Upload Excel upto 5MB )" /></small>
					</div>
					<label class="col-sm-3 control-label" for="ExportDocument"><spring:message
							code="itemmaster.excel.template" text="Excel Template" /></label>
					<div class="col-sm-3 text-left">
						<button type="button" class="btn btn-success save"
							name="button-Cancel" value="export" onclick="exportExcelData();"
							id="import">
							<spring:message code="material.item.master.downloadtemplete" text="Download Template" />
						</button>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success save"
						name="button-save" value="saveExcel" style=""
						onclick="uploadExcelFile();" id="button-save">
						<spring:message code="store.master.Save.excel"
							text="Save Excel" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backStoreMasterForm();" id="button-Cancel">
						<spring:message code="material.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>




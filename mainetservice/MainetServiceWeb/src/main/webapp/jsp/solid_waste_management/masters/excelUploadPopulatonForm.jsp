<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/solid_waste_management/populationMaster.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
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
				<spring:message code="population.master.heading"
					text="Population Master" />
			</h2>
			<apptags:helpDoc url="PopulationMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="PopulationMaster.html" name="PopulationMaster"
				id="PopulationMasterForm" class="form-horizontal">
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
							code="population.master.excel.upload" text="Excel Upload" /></label>
					<div class="col-sm-4">
						<apptags:formField fieldPath="excelFilePath" showFileNameHTMLId="true"
							fileSize="WORK_COMMON_MAX_SIZE"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
							currentCount="0" fieldType="7">
						</apptags:formField>
						<small class="text-blue-2" style="padding-left: 10px;"><spring:message
								code="population.master.excel.upload.max"
								text="(Upload Excel upto 5MB )" /></small>
					</div>
					<label class="col-sm-2 control-label" for="ExportDocument"><spring:message
							code="population.master.excel.import" text="Excel Import" /></label>
					<div class="col-sm-4">
						<button type="button" class="btn btn-primary" name="button-Cancel"
							value="import" style="" onclick="exportExcelData();" id="import">
							<spring:message code="population.master.import.excel"
								text="Import Excel" />
						</button>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success save"
						name="button-save" value="saveExcel" style=""
						onclick="uploadExcelFile();" id="button-save">
						<spring:message code="population.master.Save.excel"
							text="Save Excel" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backPopulationMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

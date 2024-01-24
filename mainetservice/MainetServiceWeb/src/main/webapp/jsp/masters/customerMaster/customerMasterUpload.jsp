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
	src="js/masters/customerMaster/customerMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header" id="hiddenDiv">
			<h2>
				<spring:message code="customerMaster.heading" text="Customer Master" />
			</h2>
		</div>
		<div class="mand-label clearfix">
			<span><spring:message code="account.common.mandmsg"
					text="Field with" /> <i class="text-red-1">*</i> <spring:message
					code="account.common.mandmsg1" text="is mandatory" /> </span>
		</div>
		<div class="widget-content padding ">

			<form:form class="form-horizontal" commandName="command"
				action="CustomerMaster.html" name="CustomerMaster"
				id="customerMasterFrm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group padding-top-20">
					<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
							code="excel.file.upload" text="Excel Upload" /></label>
					<div class="col-sm-4">
						<apptags:formField fieldPath="uploadFileName"
							showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
							currentCount="0" fieldType="7">
						</apptags:formField>
						<small class="text-blue-2" style="padding-left: 10px;"><spring:message
								code="vendormaster.excel.filesize"
								text="(Upload Excel upto 5MB )" /></small>
					</div>
					<label class="col-sm-2 control-label" for="ExportDocument"><spring:message
							code="excel.template" text="Excel Template" /></label>
					<div class="col-sm-4">
						<button type="button" class="btn btn-primary" name="button-Cancel"
							value="import" style="" onclick="downloadTamplate();" id="import">
							<spring:message code="bank.master.downloadTem"
								text="Download Template" />
						</button>
					</div>
					<form:hidden path="" id="filePath" />
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success save"
						name="button-save" value="saveExcel" style=""
						onclick="uploadExcelFile();" id="button-save">
						<spring:message code="population.master.Save.excel"
							text="Save Excel" />
					</button>
					<input type="button" class="btn btn-warning"
						onclick="openaddcustomerFrom('CustomerMaster.html','uploadCustomerForm')"
						value="<spring:message code="reset.msg" text="Reset"/>"
						id="cancelEdit" />
					<apptags:backButton url="CustomerMaster.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>

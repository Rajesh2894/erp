<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<div xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:s="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:util="urn:jsptagdir:/WEB-INF/tags/util"
	xmlns:input="urn:jsptagdir:/WEB-INF/tags/input" version="2.0">
	<%@page import="java.util.Date"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
	<%
		response.setContentType("text/html; charset=utf-8");
	%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<script src="js/mainet/file-upload.js"></script>
	<script src="js/account/transaction/accountJournalVoucherEntryUpload.js"></script>
	<script src="js/account/transaction/accountJournalVoucherEntry.js"></script>
	<jsp:directive.page contentType="text/html;charset=UTF-8" />
	<jsp:directive.page contentType="text/html;charset=UTF-8" />
	<div class="widget" id="widget">
		<div class="widget-header"
			<h2>
				<spring:message code="account.journal.voucher.transaction" text="Voucher Transaction" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div></div>
		<div class="widget-content padding">
			<form:form action="AccountVoucherEntry.html" method="post"
				class="form-horizontal">
				<input type="hidden" value="${keyTest}" id=keyTest />
				<form:hidden path="" value="${hasEmpty}" id="hasEmpty" />
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>
				<!------------------------- this is for download template and upload excel file start--------------------------->
				<div class="form-group text-center" id="reload">
					<label class="col-sm-3 control-label" for="ExcelFileUpload"><spring:message
							code="excel.file.upload" text="Excel File Upload" /></label>
					<div class="col-sm-3 text-left">
						<apptags:formField fieldPath="uploadFileName"
							showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
							currentCount="0" fieldType="7">
						</apptags:formField>
						<small class="text-blue-2"><spring:message code="bank.master.filesize" text="(Upload Excel upto 5MB )" /></small>
					</div>
					<label class="col-sm-3 control-label" for="ExportDocument"><spring:message
							code="excel.template" text="Excel Template" /></label>
					<div class="col-sm-3 text-left">
						<button type="button" class="btn btn-success save"
							name="button-Cancel" value="export" onclick="downloadTamplate();"
							id="import">
							<spring:message code="bank.master.downloadTem" text="Download Template" />
						</button>
					</div>
					<form:hidden path="uploadFileName" id="filePath" />
				</div>
				<!------------------------- this is for download template and upload excel file end--------------------------->

				<div class="text-center padding-top-20" id="savebutton">
					<button type="button" class="btn btn-success save"
						name="button-save" value="saveExcel"
						onclick="uploadExcelFile(this);" id="button-save">
						<spring:message code="account.deposit.import" text="Import" />
					</button>
					<input type="button" id="Reset"
						class="btn btn-warning exportSecondaryheadMasterClass" onclick="exportTemplate()"
						value="<spring:message code="account.bankmaster.reset" text="Reset" />"></input>
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountVoucherEntry.html'"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
				</div>
		</div>
		<c:if test="${not empty command.errorMap}">
			<h2>
				<spring:message code="account.journal.voucher.errorLog"
					text="Error Log" />
			</h2>
			<TABLE border="2">
				<THEAD >
					<TR>
						<TD align="center">Sr No</TD>
						<TD align="center">Head Description</TD>
					</TR>
				</THEAD>
				<TBODY>
					<c:forEach items="${command.errorMap}" var="entry">
						<TR>
							<TD align="left">${entry.key}</TD>
							<TD align="left">${entry.value}</TD>
						</TR>
					</c:forEach>
				</TBODY>
			</TABLE>

		</c:if>
		</form:form>
		</div>
	</div>
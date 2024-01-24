<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/trade_license/tradeLicenseReportFormat.js"></script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="property.uploadSignCertificate" text="Upload Signed Certificate" />
					<apptags:helpDoc url="TradeLicenseReportFormat.html"></apptags:helpDoc>
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form action="TradeLicenseReportFormat.html"
					id="frmnocbuildpermission" method="POST" commandName="command"
					class="form-horizontal form">
					<form:hidden path="trdLicno" id="trdLicno" />
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<div class="form-group" id="reload">
						<label class="col-sm-2 control-label" for="ExcelFileUpload"><spring:message
								text="Upload Certificate" /><i class="text-red-1">*</i></label>
						<c:set var="count" value="0" scope="page"></c:set>
						<div class="col-sm-2 text-left">
							<apptags:formField fieldType="7"
								fieldPath="checkList[${count}].uploadedDocumentPath"
								currentCount="${count}" folderName="${count}"
								fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
								isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
							</apptags:formField>
						</div>
					</div>
					<div class="text-center padding-top-10">
						<button type="button" class="btn btn-green-3" title="Submit"
							onclick="saveSignCertificate(this)">
							Save<i class="fa padding-left-4" aria-hidden="true"></i>
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
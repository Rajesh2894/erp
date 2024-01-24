<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/common/viewUploadCertificate.js"></script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="View Upload Certificate" />
					<apptags:helpDoc url="ViewUploadCertificate.html"></apptags:helpDoc>
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">

				<form:form action="ViewUploadCertificate.html"
					id="frmnocbuildpermission" method="POST" commandName="command"
					class="form-horizontal form">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<%-- <h4>
						<spring:message text="Applicant Information " />
					</h4> --%>
					<div class="form-group">
						<apptags:input labelCode="ApplicationForm.applicantName" isReadonly="true"
							cssClass="hasCharacter mandColorClass hasNoSpace" path="name"
							isMandatory="true" maxlegnth="100">
						</apptags:input>
					</div>
					<c:if test="${empty command.attachDocsList}">
						<div class="form-group" id="reload">
							<label class="col-sm-2 control-label" for="ExcelFileUpload"><spring:message
									code="intranet.fileUpld" text="File Upload" /><i
								class="text-red-1">*</i></label>
							<c:set var="count" value="0" scope="page"></c:set>
							<div class="col-sm-2 text-left">
								<apptags:formField fieldType="7"
									fieldPath="attachments[${count}].uploadedDocumentPath"
									currentCount="${count}" folderName="${count}"
									fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
									isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
								</apptags:formField>

							</div>

						</div>
					</c:if>
					<c:if test="${not empty command.attachDocsList}">
						<div class="form-group">
							<div class="col-sm-12 text-left">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="attachDocs">
										<tr>
											<th><spring:message code="scheme.document.name" text="" /></th>
											<th><spring:message code="scheme.view.document" text="" /></th>
										</tr>
										<c:forEach items="${command.attachDocsList}" var="lookUp">
											<tr>
												<td align="center">${lookUp.attFname}</td>
												<td align="center"><apptags:filedownload
														filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}"
														actionUrl="ViewUploadCertificate.html?Download">
													</apptags:filedownload></td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>
					</c:if>

					<div class="text-center padding-top-10">
						<c:if test="${empty command.attachDocsList}">
							<button type="button" class="btn btn-green-3" title="Submit"
								onclick="saveData(this)">
								Save<i class="fa padding-left-4" aria-hidden="true"></i>
							</button>
						</c:if>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
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
<script type="text/javascript"
	src="js/birthAndDeath/nacForBirthRegApproval.js"></script>

<script>
	$('[data-toggle="tooltip"]').tooltip({
		trigger : 'hover'
	})
</script>


<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="nac.birth.approval" text="NAC for Birth Registration Approval" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding" id="">
				<form:form id="frmBirthCertificateForm" action="NacForBirthReg.html"
					method="POST" class="form-horizontal"
					name="frmBirthCertificateForm">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<h4>
						<spring:message code="BirthCertificateDTO.childbirthDetails"
							text="Child Information" />
					</h4>

					<div class="form-group">
						<apptags:date fieldclass="datepicker"
							labelCode="BirthCertificateDTO.brDob"
							datePath="nacForBirthRegDTO.brDob" isDisabled="true">
						</apptags:date>


						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="BirthCertificateDTO.brSex" text="Sex" />
						</label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="nacForBirthRegDTO.brSex" cssClass="form-control"
							disabled="true" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.brChildName"
							path="nacForBirthRegDTO.brChildName" isMandatory=""
							isReadonly="true" cssClass="hasNameClass form-control"
							maxlegnth="400">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.brChildNameMar"
							path="nacForBirthRegDTO.brChildNameMar" isMandatory=""
							isReadonly="true" cssClass="hasNameClass form-control"
							maxlegnth="400">
						</apptags:input>
					</div>

					<div class="form-group">

						<apptags:input labelCode="BirthCertificateDTO.brBirthPlace"
							path="nacForBirthRegDTO.brBirthPlace" isMandatory="true"
							isReadonly="true" cssClass="hasNameClass form-control"
							maxlegnth="200">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.brBirthPlaceMar"
							path="nacForBirthRegDTO.brBirthPlaceMar" isMandatory="true"
							isReadonly="true" cssClass="hasNameClass form-control"
							maxlegnth="200">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.brBirthAddr"
							path="nacForBirthRegDTO.brBirthAddr" isMandatory="true"
							isReadonly="true" cssClass="hasNumClass form-control"
							maxlegnth="800">
						</apptags:input>

						<apptags:input labelCode="BirthCertificateDTO.brBirthAddrMar"
							path="nacForBirthRegDTO.brBirthAddrMar" isMandatory="true"
							isReadonly="true" cssClass="hasNumClass form-control"
							maxlegnth="800">
						</apptags:input>
					</div>


					<h4>
						<spring:message code="BirthCertificateDTO.parentDetails"
							text="Parent Information" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.pdFathername"
							path="nacForBirthRegDTO.pdFathername" isReadonly="true"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.pdFathernameMar"
							path="nacForBirthRegDTO.pdFathernameMar" isReadonly="true"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.pdMothername"
							path="nacForBirthRegDTO.pdMothername" isReadonly="true"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.pdMothernameMar"
							path="nacForBirthRegDTO.pdMothernameMar" isReadonly="true"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350">
						</apptags:input>
					</div>


					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.pdParaddress"
							path="nacForBirthRegDTO.pdParaddress" isReadonly="true"
							isMandatory="true" cssClass="hasNumClass form-control"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.pdParaddressMar"
							path="nacForBirthRegDTO.pdParaddressMar" isReadonly="true"
							isMandatory="true" cssClass="hasNumClass form-control"
							maxlegnth="350">
						</apptags:input>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
										code="TbDeathregDTO.attacheddoc" text="Attached Documents" />
								</a>
							</h4>
						</div>

						<c:forEach items="${command.fetchDocumentList}" var="lookUp">
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="birth.document.name" text="Document Name" /></th>
										<th><spring:message code="birth.view.document" text="birth.view.document" /></th>
									</tr>
										<tr>
											<td align="center">${lookUp.attFname}</td>
											<td align="center"><apptags:filedownload
													filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
													actionUrl="NacForBirthRegApproval.html?Download">
												</apptags:filedownload></td>
										</tr>
								</table>
							</div>
						</div>
						</c:forEach>
					</div>

					<div class="form-group">
						<c:choose>
							<c:when test="${CheckFinalApp eq true}">
								<apptags:radio radioLabel="nac.approve,nac.reject"
									radioValue="APPROVED,REJECTED" isMandatory="true"
									labelCode="nac.status" path="nacForBirthRegDTO.birthRegstatus"
									defaultCheckedValue="APPROVED">
								</apptags:radio>
								<br />
							</c:when>
							<c:otherwise>
								<apptags:radio radioLabel="nac.approve" radioValue="APPROVED"
									isMandatory="true" labelCode="nac.status"
									path="nacForBirthRegDTO.birthRegstatus"
									defaultCheckedValue="APPROVED">
								</apptags:radio>
								<br />
							</c:otherwise>
						</c:choose>
						<apptags:textArea labelCode="BirthCertificateDTO.birthRegremark"
							isMandatory="true" path="nacForBirthRegDTO.birthRegremark"
							cssClass="hasNumClass form-control" maxlegnth="100" />
					</div>

					<c:if test="${CheckFinalApp eq true}">
						<div class="form-group" id="reload">
							<label class="col-sm-2 control-label" for="ExcelFileUpload"><spring:message
									code="intranet.fileUpld" text="File Upload" /></label>
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



					<div class="text-center padding-top-10">

						<button type="button" class="btn btn-green-3" title="Submit"
							onclick="saveBirthApprovalData(this)">
							<spring:message code="bt.save" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>

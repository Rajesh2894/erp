<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	src="js/birthAndDeath/applyDeathCertificateApproval.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<script>
	$(document).ready(function() {
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,

		});
	});

	$('[data-toggle="tooltip"]').tooltip({
		trigger : 'hover'
	})
</script>
<style>
input[type=checkbox] {
	position: inherit;
	margin-top: 1px;
	margin-left: 0px;
}

.ckeck-box {
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 0.75em;
	font-weight: 600;
}
</style>


<html>

<div class="pagediv">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Death Certificate Form" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div>
		</div>

		<div class="widget-content padding">
			<form:form id="frmDeathCertificateForm"
				action="ApplyDeathCertificateLevels.html" method="POST"
				class="form-horizontal" name="frmDeathCertificateForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<%-- 					<form:hidden path="tbDeathregDTO.drId" --%>
				<%-- 						cssClass="hasNumber form-control" id="drId" /> --%>

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<!-- <div class="panel-group accordion-toggle"
					id="accordion_single_collapse"> -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 type="h4" class="panel-title table" id="">
							<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
									code="TbDeathregDTO.form.generalDetails" text="General Details" /></a>
						</h4>
					</div>
					<div id="deathRegCor-1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<apptags:date fieldclass="datepicker"
									labelCode="TbDeathregDTO.drDod"
									datePath="deathCertificateDTO.drDod" readonly="true">
								</apptags:date>

								<label class="control-label col-sm-2 required-control"
									for="Census"> <spring:message
										code="TbDeathregDTO.drSex" text="Gender" />
								</label>
								<c:set var="baseLookupCode" value="GEN" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="deathCertificateDTO.drSex" cssClass="form-control"
									isMandatory="true" hasId="true" disabled="true"
									selectOptionLabelCode="selectdropdown" />
							</div>


							<div class="form-group">
								<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
									path="deathCertificateDTO.drDeceasedname" isDisabled="true"
									cssClass="hasNameClass form-control" maxlegnth="100">
								</apptags:input>
								<apptags:input labelCode="TbDeathregDTO.drMarDeceasedname"
									path="deathCertificateDTO.drMarDeceasedname" isDisabled="true"
									isMandatory="true" cssClass="hasNameClass form-control"
									maxlegnth="100">
								</apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="TbDeathregDTO.drRelativeName"
									isDisabled="true" path="deathCertificateDTO.drRelativeName"
									cssClass="hasNameClass form-control" maxlegnth="100">
								</apptags:input>

								<apptags:input labelCode="TbDeathregDTO.drMarRelativeName"
									path="deathCertificateDTO.drMarRelativeName" isDisabled="true"
									cssClass="hasNameClass form-control" maxlegnth="100">
								</apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="TbDeathregDTO.drMotherName"
									isReadonly="true" path="deathCertificateDTO.drMotherName"
									cssClass="hasNameClass form-control" maxlegnth="100">
								</apptags:input>
								<apptags:input labelCode="TbDeathregDTO.drMarMotherName"
									isReadonly="true" path="deathCertificateDTO.drMarMotherName"
									isMandatory="true" cssClass="hasNameClass form-control"
									maxlegnth="100">
								</apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr"
									isDisabled="true" path="deathCertificateDTO.drDeceasedaddr"
									cssClass="hasNumClass form-control" maxlegnth="190">
								</apptags:input>
								<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr"
									isDisabled="true" path="deathCertificateDTO.drMarDeceasedaddr"
									cssClass="hasNumClass form-control" maxlegnth="190">
								</apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeath"
									isDisabled="true" path="deathCertificateDTO.drDcaddrAtdeath"
									cssClass="hasNumClass form-control" maxlegnth="190">
								</apptags:input>
								<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeathMar"
									isDisabled="true" path="deathCertificateDTO.drDcaddrAtdeathMar"
									cssClass="hasNumClass form-control" maxlegnth="190">
								</apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="TbDeathregDTO.drDeathplace"
									isDisabled="true" path="deathCertificateDTO.drDeathplace"
									cssClass="hasNumClass form-control" maxlegnth="100">
								</apptags:input>
								<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
									isDisabled="true" path="deathCertificateDTO.drMarDeathplace"
									cssClass="hasNumClass form-control" maxlegnth="100">
								</apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="BirthRegDto.numberOfCopies" isReadonly="true"
									path="deathCertificateDTO.demandedCopies"
									cssClass="hasNumber form-control" maxlegnth="10">
								</apptags:input>
							</div>



								<c:if test="${not empty command.fetchDocumentList}">

						<div class="panel-group accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a3"><spring:message
										code="mrm.upload.attachement" text="Upload Attachment" /></a>
							</h4>
							<div class="panel-collapse collapse in" id="a3">

								<div class="panel-body">

									<div class="overflow margin-top-10 margin-bottom-10">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
												<tbody>
													<tr>
														<th><label class="tbold"><spring:message
																	code="label.checklist.srno" text="Sr No" /></label></th>
														<th><label class="tbold"><spring:message
																	code="bnd.documentName" text="Document Type" /></label></th>
														<th><label class="tbold"><spring:message
																	code="bnd.documentDesc" text="Document Description" /></label></th>
														<th><spring:message code="birth.view.document"
																text="" /></th>
													</tr>

													<c:forEach items="${command.fetchDocumentList}" var="lookUp"
														varStatus="lk">
														<tr>
															<td><label>${lookUp.clmSrNo}</label></td>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<td><label>${lookUp.clmDescEngl}</label></td>
																</c:when>
																<c:otherwise>
																	<td><label>${lookUp.clmDesc}</label></td>
																</c:otherwise>
															</c:choose>
															<td><label>${lookUp.docDescription}</label></td>
															<td align="center"><apptags:filedownload
																	filename="${lookUp.attFname}"
																	filePath="${lookUp.attPath}"
																	actionUrl="BirthRegCorrectionApprovalLevel.html?Download">
																</apptags:filedownload></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>

					</c:if>

							<div class="form-group">


								<c:choose>
									<c:when test="${CheckFinalApp eq true}">
										<apptags:radio radioLabel="nac.approve,nac.reject"
											radioValue="APPROVED,REJECTED" isMandatory="true"
											labelCode="nac.status" path="deathCertificateDTO.deathRegstatus"
											defaultCheckedValue="APPROVED">
										</apptags:radio>
										<br />
									</c:when>
									<c:otherwise>
										<apptags:radio radioLabel="nac.approve" radioValue="APPROVED"
											isMandatory="true" labelCode="nac.status"
											path="deathCertificateDTO.deathRegstatus"
											defaultCheckedValue="APPROVED">
										</apptags:radio>
										<br />
									</c:otherwise>
								</c:choose>
								
								<apptags:textArea labelCode="TbDeathregDTO.form.remark"
									path="deathCertificateDTO.authRemark"
									cssClass="hasNumClass form-control" maxlegnth="100" />
							</div>
							
					<c:if test="${CheckFinalApp eq true}">
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
						</div>
					</div>
				</div>

				<div class="text-center">
					<button type="button" value="<spring:message code="bt.save"/>"
						class="btn btn-green-3" data-toggle="tooltip"
						data-original-title="Submit"
						onclick="saveDeathCertApprovalData(this)">
						<spring:message code="bt.save" text="Submit" />
					</button>

					<button type="button" onclick="resetMemberMaster(this);"
						class="btn btn-warning" data-toggle="tooltip"
						data-original-title="Reset" id="resetId">
						<spring:message code="TbDeathregDTO.form.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>

				</div>
			</form:form>

		</div>

	</div>

</div>


</html>






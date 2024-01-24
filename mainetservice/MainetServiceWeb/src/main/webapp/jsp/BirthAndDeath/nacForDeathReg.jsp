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
<script type="text/javascript" src="js/birthAndDeath/nacForDeathReg.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<script>
	$(document).ready(function() {
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : new Date(2016, 11, 31),
			yearRange : "-100:-0"
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
				<b><spring:message code="death.drDeathIssuSerEngName" text="NAC for Death Registration" /></b>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div>
		</div>

		<div class="widget-content padding">
			<form:form id="frmDeathCertificateForm" action="NacForDeathReg.html"
				method="POST" commandName="command" class="form-horizontal"
				name="frmDeathCertificateForm">
				<form:hidden path="deathCertificateDTO.applicationNo"
					id="apmApplicationId" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
									code="TbDeathregDTO.form.generalDetails" text="General Details" /></a>
						</h4>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<apptags:date fieldclass="datepicker"
								labelCode="TbDeathregDTO.drDod"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								datePath="deathCertificateDTO.drDod">
							</apptags:date>

							<label class="control-label col-sm-2 required-control"
								for="Census"> <spring:message code="TbDeathregDTO.drSex"
									text="Gender" />
							</label>
							<c:set var="baseLookupCode" value="GEN" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="deathCertificateDTO.drSex" cssClass="form-control"
								isMandatory="true" hasId="true"
								disabled="${command.saveMode eq 'V' ? true : false }"
								selectOptionLabelCode="selectdropdown" />
						</div>


						<div class="form-group">
							<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
								path="deathCertificateDTO.drDeceasedname" isMandatory="true"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="hasNameClass form-control" maxlegnth="100">
							</apptags:input>
							<apptags:input labelCode="TbDeathregDTO.drMarDeceasedname"
								path="deathCertificateDTO.drMarDeceasedname"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								isMandatory="true" cssClass="hasNameClass form-control"
								maxlegnth="100">
							</apptags:input>
						</div>

						<div class="form-group">
							<apptags:input labelCode="TbDeathregDTO.drRelativeName"
								path="deathCertificateDTO.drRelativeName" isMandatory="true"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="hasNameClass form-control" maxlegnth="100">
							</apptags:input>

							<apptags:input labelCode="TbDeathregDTO.drMarRelativeName"
								path="deathCertificateDTO.drMarRelativeName"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								isMandatory="true" cssClass="hasNameClass form-control"
								maxlegnth="100">
							</apptags:input>
						</div>

						<div class="form-group">
							<apptags:input labelCode="TbDeathregDTO.drMotherName"
								path="deathCertificateDTO.drMotherName" isMandatory="true"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="hasNameClass form-control" maxlegnth="100">
							</apptags:input>
							<apptags:input labelCode="TbDeathregDTO.drMarMotherName"
								path="deathCertificateDTO.drMarMotherName" isMandatory="true"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="hasNameClass form-control" maxlegnth="100">
							</apptags:input>
						</div>

						<div class="form-group">
							<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr"
								path="deathCertificateDTO.drDeceasedaddr" isMandatory="true"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="hasNumClass form-control" maxlegnth="190">
							</apptags:input>
							<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr"
								path="deathCertificateDTO.drMarDeceasedaddr"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								isMandatory="true" cssClass="hasNumClass form-control"
								maxlegnth="190">
							</apptags:input>
						</div>

						<div class="form-group">
							<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeath"
								path="deathCertificateDTO.drDcaddrAtdeath" isMandatory="true"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="hasNumClass form-control" maxlegnth="190">
							</apptags:input>
							<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeathMar"
								path="deathCertificateDTO.drDcaddrAtdeathMar"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								isMandatory="true" cssClass="hasNumClass form-control"
								maxlegnth="190">
							</apptags:input>
						</div>

						<div class="form-group">
							<apptags:input labelCode="TbDeathregDTO.drDeathplace"
								path="deathCertificateDTO.drDeathplace" isMandatory="true"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="hasNumClass form-control" maxlegnth="100">
							</apptags:input>
							<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
								path="deathCertificateDTO.drMarDeathplace" isMandatory="true"
								isDisabled="${command.saveMode eq 'V' ? true : false }"
								cssClass="hasNumClass form-control" maxlegnth="100">
							</apptags:input>
						</div>

					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
									code="BirthCertificateDTO.certificate.print"
									text="Certificate print details" />
							</a>
						</h4>
					</div>
					<div id="deathRegCor-1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<apptags:input labelCode="TbDeathregDTO.demandedCopies"
									isDisabled="${command.saveMode eq 'V' ? true : false }"
									path="deathCertificateDTO.demandedCopies" maxlegnth="2"
									onBlur="getAmount(this)" cssClass="hasNumber form-control"
									isMandatory="true">
								</apptags:input>


								<c:if
									test="${command.saveMode ne 'V' && command.deathCertificateDTO.chargeStatus eq 'CA' }">
									<label for="amount"
										class="col-sm-2 control-label required-control" id="amount"><spring:message
											code="BirthCertificateDTO.amount" text="amount" /></label>
									<div class="col-sm-4">
										<form:input type='text' path="deathCertificateDTO.amount"
											maxlength="350" id="amount" isReadonly="true" disabled="true"
											class="hasNumClass form-control amount" />
									</div>

									<form:hidden path="chargesAmount" value="" id="chargesAmount"/>
								</c:if>


								<%-- <c:if test="${command.saveMode ne 'V'}">
											<apptags:input labelCode="BirthCertificateDTO.amount"
												path="deathCertificateDTO.amount"
												cssClass="hasNumClass form-control" isReadonly="true">
											</apptags:input>
										</c:if> --%>
							</div>
						</div>
					</div>
				</div>






				<c:if test="${command.saveMode ne 'V'}">
					<c:if test="${not empty command.checkList}">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
											code="BirthCertificateDTO.document" text="Upload Documents" /></a>
								</h4>
							</div>
							<div class="panel-body">
								<fieldset class="fieldRound">
									<div class="overflow">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped"
												id="DeathTable">
												<tbody>
													<tr>
														<th><label class="tbold"><spring:message
																	code="label.checklist.srno" text="Sr No" /></label></th>
														<th><label class="tbold"><spring:message
																	code="label.checklist.docname" text="Document Required" /></label></th>
														<th><label class="tbold"><spring:message
																	code="label.checklist.status" text="Status" /></label></th>
														<th><label class="tbold"><spring:message
																	code="label.checklist.upload" text="Upload" /></label></th>
													</tr>

													<c:forEach items="${command.checkList}" var="lookUp"
														varStatus="lk">

														<tr>
															<td>${lookUp.documentSerialNo}</td>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																	<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																</c:when>
																<c:otherwise>
																	<c:set var="docName" value="${lookUp.doc_DESC_Mar }" />
																	<td><label>${lookUp.doc_DESC_Mar}</label></td>
																</c:otherwise>
															</c:choose>
															<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																<td>${lookUp.descriptionType}<spring:message
																		code="" text="Mandatory" /></td>
															</c:if>


															<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																<td>${lookUp.descriptionType}<spring:message
																		code="" text="Optional" /></td>
															</c:if>
															<td>



																<div id="docs_${lk}">
																	<apptags:formField fieldType="7" labelCode=""
																		hasId="true" fieldPath="checkList[${lk.index}]"
																		isMandatory="false" showFileNameHTMLId="true"
																		fileSize="CARE_COMMON_MAX_SIZE"
																		maxFileCount="CHECK_LIST_MAX_COUNT"
																		validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC"
																		checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																		checkListDesc="${docName}" currentCount="${lk.index}" />
																</div>

															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
					</c:if>
				</c:if>
				<div class="form-group">

					<c:if test="${command.saveMode eq 'V'}">

						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#deathRegCor-2"> <spring:message
										code="TbDeathregDTO.attacheddoc" text="Attached Documents" />
								</a>
							</h4>
						</div>


						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="birth.document.name" text="" /></th>
										<th><spring:message code="birth.view.document" text="" /></th>
									</tr>
									<c:forEach items="${command.checkList}" var="lookUp">
										<tr>
											<td align="center">${lookUp.documentName}</td>
											<td align="center"><apptags:filedownload
													filename="${lookUp.documentName}"
													filePath="${lookUp.uploadedDocumentPath}"
													actionUrl="applyForDeathCertificate.html?Download">
												</apptags:filedownload></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</c:if>

				</div>
				<form:hidden path="deathCertificateDTO.chargeStatus"
					id="chargeStatus" />
				<c:if test="${command.deathCertificateDTO.chargeStatus eq 'CA'}">
					<div class="panel panel-default" id="payId">
						<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
					</div>
				</c:if>
				<div class="text-center">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" value="<spring:message code="bt.save"/>"
							class="btn btn-green-3" data-toggle="tooltip"
							data-original-title="Submit"
							onclick="saveDeathCertificateData(this)">
							<spring:message code="bt.save" text="Submit" />
						</button>

						<button type="button" onclick="resetMemberMaster(this);"
							class="btn btn-warning" data-toggle="tooltip"
							data-original-title="Reset" id="resetId">
							<spring:message code="bt.clear" text="Reset" />
						</button>

					</c:if>
					
					<button type="button" class="btn btn-danger " id="backId"
						data-toggle="tooltip" data-original-title="Back"
						onclick="previousPage()">
						<spring:message code="TbDeathregDTO.form.backbutton" text="Back" />
					</button>

				</div>
			</form:form>

		</div>

	</div>

</div>


</html>






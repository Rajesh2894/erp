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
<script type="text/javascript" src="js/rts/birthCertificate.js"></script>
<script type="text/javascript" src="js/cfc/challan/offlinePay.js"></script>


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
					<spring:message code="BirthCertificateDTO.form.name"
						text="Birth Certificate Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding" id="">
				<form:form id="frmBirthCertificateForm" commandName="command"
					action="ApplyforBirthCertificate.html" method="POST"
					class="form-horizontal" name="birthRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
                   <form:hidden path="birthCertificateDto.apmApplicationId"
						id="apmApplicationId" />
						
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#birthReg-1"> <spring:message
											code="BirthCertificateDTO.childbirthDetails"
											text="Child Information" /></a>
								</h4>
							</div>
							<div id="birthReg-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">

										<apptags:date fieldclass="datepicker" 
											labelCode="BirthCertificateDTO.brDob" isMandatory="true"
											datePath="birthCertificateDto.brDob" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="form-control mandColorClass">
										</apptags:date>

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthCertificateDTO.brSex" text="Sex" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthCertificateDto.brSex" cssClass="form-control"
											isMandatory="true" hasId="true"  disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthCertificateDTO.brChildName"
											path="birthCertificateDto.brChildName" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control" maxlegnth="400">
										</apptags:input>
										<apptags:input labelCode="BirthCertificateDTO.brChildNameMar"
											path="birthCertificateDto.brChildNameMar" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control" maxlegnth="400">
										</apptags:input>
									</div>

									<div class="form-group">

										<apptags:input labelCode="BirthCertificateDTO.brBirthPlace"
											path="birthCertificateDto.brBirthPlace" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control" maxlegnth="200">
										</apptags:input>
										<apptags:input labelCode="BirthCertificateDTO.brBirthPlaceMar"
											path="birthCertificateDto.brBirthPlaceMar" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control" maxlegnth="200">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthCertificateDTO.brBirthAddr" isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthCertificateDto.brBirthAddr" isMandatory="true"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>

										<apptags:input labelCode="BirthCertificateDTO.brBirthAddrMar" isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthCertificateDto.brBirthAddrMar" isMandatory="true"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>
									</div>

								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#birthReg-2"> <spring:message
											code="BirthCertificateDTO.parentDetails"
											text="Parent Information" />
									</a>
								</h4>
							</div>
							<div id="birthReg-2" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthCertificateDTO.pdFathername" isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthCertificateDto.pdFathername" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="BirthCertificateDTO.pdFathernameMar" isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthCertificateDto.pdFathernameMar" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="350">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthCertificateDTO.pdMothername" isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthCertificateDto.pdMothername" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="BirthCertificateDTO.pdMothernameMar"
											path="birthCertificateDto.pdMothernameMar" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control" maxlegnth="350">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthCertificateDTO.pdParaddress"
											path="birthCertificateDto.pdParaddress" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNumClass form-control" maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="BirthCertificateDTO.pdParaddressMar"
											path="birthCertificateDto.pdParaddressMar" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNumClass form-control" maxlegnth="350">
										</apptags:input>
									</div>
								</div>
							</div>


						</div>



						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#birthReg-3"> <spring:message
											code="BirthCertificateDTO.certificate.print"
											text="Certificate print details" />
									</a>
								</h4>
							</div>

							<div id="birthReg-3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthCertificateDTO.noOfCopies"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthCertificateDto.noOfCopies" isMandatory="true"
											onBlur="getAmountOnNoOfCopes()"
											cssClass="hasNumber form-control" maxlegnth="350">
										</apptags:input>

										<c:if test="${command.saveMode ne 'V' && command.birthCertificateDto.chargesStatus eq 'CA'}">
											<label for="amount"
												class="col-sm-2 control-label required-control" id="amount"><spring:message
													code="BirthCertificateDTO.amount" text="amount" /></label>
											<div class="col-sm-4">
												<form:input type='text' path="birthCertificateDto.amount"
													maxlength="350" id="amount" isReadonly="true" disabled="true"
													class="hasNumClass form-control amount" />
											</div>
										</c:if>

									</div>

								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
					<c:if test="${command.saveMode eq 'V'}">

						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#birthReg-5"> <spring:message
										code="TbDeathregDTO.attacheddoc" text="Attached Documents" />
								</a>
							</h4>
						</div>


						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="scheme.document.name" text="" /></th>
										<th><spring:message code="scheme.view.document" text="" /></th>
									</tr>
									<c:forEach items="${command.checkList}" var="lookUp">
										<tr>
											<td align="center">${lookUp.documentName}</td>
											<td align="center"><apptags:filedownload
													filename="${lookUp.documentName}" filePath="${lookUp.uploadedDocumentPath}"
													actionUrl="ApplyforBirthCertificate.html?Download">
												</apptags:filedownload></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</c:if>
					</div>
					<c:if test="${command.saveMode ne 'V'}">
						<c:if test="${not empty command.checkList}">

							<h4 type="h4" class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#birthReg-6"> <spring:message
										code="BirthCertificateDTO.documentsweety"
										text="Upload Documents" />
								</a>
							</h4>

							<%-- <h4>
							<spring:message text="Upload Documents" />
						</h4> --%>
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
																<c:set var="docName" value="${lookUp.doc_DESC_Mar}" />
																<td><label>${lookUp.doc_DESC_Mar}</label></td>
															</c:otherwise>
														</c:choose>

														<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
															<td>${lookUp.descriptionType}<spring:message code=""
																	text="Mandatory" /></td>
														</c:if>

														<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
															<td>${lookUp.descriptionType}<spring:message code=""
																	text="Optional" /></td>
														</c:if>
														<td>
															<div id="docs_${lk}">
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true" fieldPath="checkList[${lk.index}]"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="BND_COMMOM_MAX_SIZE"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
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
						</c:if>
					</c:if>
					<form:hidden path="birthCertificateDto.chargesStatus"
						id="chargeStatus" />
					<c:if test="${command.birthCertificateDto.chargesStatus eq 'CA'}">
						<div class="panel panel-default" id="payId">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</div>
					</c:if>
					<div class="text-center padding-top-10">

						<c:if test="${command.saveMode ne 'V'}">
							<button type="button" class="btn btn-green-3"
								data-toggle="tooltip" data-original-title="Save"
								onclick="saveBirthData(this)">
								<spring:message code="rts.save" />
								<i class="fa padding-left-4" aria-hidden="true"></i>
							</button>

							<button type="button" onclick="resetMemberMaster(this);"
								class="btn btn-warning" data-toggle="tooltip"
								data-original-title="Reset">
								<spring:message code="rts.reset" />
								<i class="fa padding-left-4" aria-hidden="true"></i>
							</button>

						</c:if>
						<button type="button" class="btn btn-danger " id="backId"
							data-toggle="tooltip" data-original-title="Back"
							onclick="previousPage()">
							<i class="fa padding-left-4" aria-hidden="true"></i>
							<spring:message code="TbDeathregDTO.form.backbutton" text="Back" />
						</button>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>

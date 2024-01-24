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
<script type="text/javascript" src="js/birthAndDeath/birthCertificate.js"></script>
<script src="js/challan/offlinePay.js"></script>


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
				<apptags:helpDoc url="bndApplyforBirthCertificate.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding" >
				<form:form id="frmBirthCertificateForm" commandName="command"
					action="bndApplyforBirthCertificate.html" method="POST"
					class="form-horizontal" name="birthRegFormId">
					<form:hidden path="requestDTO.applicationId" id="applicationId" />
					<form:hidden path="applicationChargeFlag"
						id="applicationChargeFlag" />
					<div class="compalint-error-div">
								<jsp:include page="/jsp/tiles/validationerror.jsp" />
								<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display:none;"></div>
						</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel-collapse collapse in">
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
									
									<label class="col-sm-2 control-label required-control datePicker"><spring:message
                                                code="BirthCertificateDTO.brDob" text="Date of Birth" /></label>
                                        <div class="col-sm-4">
                                            <div class="input-group">
                                            <c:if test="${command.saveMode ne 'V'}">
                                                <form:input class="form-control datepicker" path="birthCertificateDto.brDob" 
                                                    isMandatory="true" id="brDob" disabled="${command.saveMode eq 'V' ? true : false }"
                                                     /></c:if>
												<c:if test="${command.saveMode eq 'V'}">
													<form:input class="form-control datepicker"
														path="birthCertificateDto.dateOfBirth" isMandatory="true"
														id="brDob"
														disabled="${command.saveMode eq 'V' ? true : false }" />
												</c:if>
												<div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>

										<%-- <apptags:date fieldclass="datepicker"
											labelCode="BirthCertificateDTO.brDob" isMandatory="true"
											datePath="birthCertificateDto.brDob" cssClass="form-control mandColorClass">
										</apptags:date> --%>

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthCertificateDTO.brSex" text="Sex" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthCertificateDto.brSex" cssClass="form-control"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
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
										<apptags:input labelCode="BirthCertificateDTO.brBirthAddr"
											path="birthCertificateDto.brBirthAddr" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>

										<apptags:input labelCode="BirthCertificateDTO.brBirthAddrMar"
											path="birthCertificateDto.brBirthAddrMar" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>
									</div>

								</div>
							</div>
						</div>
						<div id="birthReg-3" class="panel-collapse collapse in">
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
										<apptags:input labelCode="BirthCertificateDTO.pdFathername"
											path="birthCertificateDto.pdFathername" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control" maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="BirthCertificateDTO.pdFathernameMar"
											path="birthCertificateDto.pdFathernameMar" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control" maxlegnth="350">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthCertificateDTO.pdMothername"
											path="birthCertificateDto.pdMothername" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
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

						<div class="panel-collapse collapse in">
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

										<label class="col-sm-2 control-label"> <spring:message
												code="BirthCertificateDTO.noOfCopies" text="No of Copies" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="birthCertificateDto.noOfCopies" id="noOfCopies" disabled="${command.saveMode eq 'V' ? true : false }"
												cssClass="hasNumber form-control" maxlegnth="2"
												onBlur="getAmountOnNoOfCopes()" data-rule-required="true"></form:input>
										</div>
										
										<c:if test="${command.saveMode ne 'V' && command.applicationChargeFlag eq 'Y'}">
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
					<c:if test="${command.saveMode eq 'V' && not empty command.checkList}">
					
					<div class="form-group">
						<div class="panel-heading">
							<h4 class="panel-collapse collapse in" id="">
								<a data-toggle="collapse" class="" href="#birthReg-5"> <spring:message
										 text="Attached Documents" />
								</a>
							</h4>
						</div>
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="birth.document.name" text="Document Name" /></th>
										<th><spring:message code="birth.view.document" text="View Documents" /></th>
									</tr>
									<c:forEach items="${command.checkList}" var="lookUp">
										<tr>
											<td align="center">${lookUp.documentName}</td>
											<td align="center"><apptags:filedownload
													filename="${lookUp.documentName}" filePath="${lookUp.uploadedDocumentPath}"
													actionUrl="">
												</apptags:filedownload></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
						</div>
					</c:if>
					
					<c:if test="${command.saveMode ne 'V'}">
					<c:if test="${not empty command.checkList}">

						<h4 type="h4" class="panel-title table" id="">
							<a data-toggle="collapse" class="" href="#birthReg-6"> <spring:message
									text="Upload Documents" />
							</a>
						</h4>

						<fieldset class="fieldRound ">
							<div class="overflow margin-top-10">
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
																fileSize="CARE_COMMON_MAX_SIZE"
																maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="PDF_UPLOAD_EXTENSION"
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

						<c:if test="${command.applicationChargeFlag eq 'Y'}">
							<div id="payId">
								<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
							</div>
						</c:if>
					</c:if>
					
					<div class="text-center padding-top-10">
					
                        <c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-blue-2" data-toggle="tooltip"
							data-original-title="Save" onclick="saveBirthData(this)">
							<spring:message code="rts.submit" text="Submit" /><i class="fa fa-arrow-right padding-left-5"
								aria-hidden="true"></i>
						</button>
						<button type="Reset" class="btn btn-warning" id="resetform"
							onclick="resetSchemeApplicationForm(this)">
							<spring:message code="rts.reset" text="Reset" />
						</button>
						</c:if>
						<button type="button" class="btn btn-danger" id="bck"
								onclick="openForm('bndService.html','applicantForm')">
								<spring:message code="trade.back"></spring:message>
							</button>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>

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
<script src="js/challan/offlinePay.js"></script>
<script type="text/javascript" src="js/rts/applyDeathCertificate.js"></script>

<script>
$('[data-toggle="tooltip"]').tooltip({
    trigger : 'hover'
})

</script>



<div class="pagediv">
<!-- Start Content here -->
	<apptags:breadcrumb ></apptags:breadcrumb>
	<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rts.deathcertapplform" text="Death Certificate Application Form" />
			</h2>
			<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
				</div>
		</div>

		<div class="widget-content padding">
			<form:form id="frmDeathCertificateForm"
				action="applyForDeathCertificate.html" method="POST"
				class="form-horizontal" name="frmDeathCertificateForm">
				
				<form:hidden path="applicationChargeFlag"
						id="applicationChargeFlag" />

				<div class="compalint-error-div">
								<jsp:include page="/jsp/tiles/validationerror.jsp" />
								<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display:none;"></div>
						</div>
          <div class="panel-group accordion-toggle" id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4  class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
										code="TbDeathregDTO.form.generalDetails"
										text="General Details" /></a>
							</h4>
						</div>
						<div id="deathRegCor-1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
								<label class="col-sm-2 control-label required-control datePicker"><spring:message
                                                code="TbDeathregDTO.drDod" text="Date of Birth" /></label>
                                        <div class="col-sm-4">
                                            <div class="input-group">
                                                <form:input class="form-control datepicker" path="deathCertificateDTO.drDod" 
                                                    isMandatory="true" id="drDod" disabled="${command.saveMode eq 'V' ? true : false }"
                                                     />
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
								
									<%-- <apptags:date fieldclass="datepicker"
										labelCode="TbDeathregDTO.drDod"
										datePath="deathCertificateDTO.drDod">
									</apptags:date>
									 --%>

									 <label class="control-label col-sm-2 required-control"
										for="Census"> <spring:message code="TbDeathregDTO.drSex"
											 text="Gender" />
									</label>
									<c:set var="baseLookupCode" value="GEN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="deathCertificateDTO.drSex" cssClass="form-control"
										isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
										selectOptionLabelCode="selectdropdown" />
								</div>


								 <div class="form-group">
									<apptags:input labelCode="TbDeathregDTO.drDeceasedname" 
										path="deathCertificateDTO.drDeceasedname" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
										cssClass="hasNameClass form-control" maxlegnth="100">
									</apptags:input>
									<apptags:input labelCode="TbDeathregDTO.drMarDeceasedname"
										path="deathCertificateDTO.drMarDeceasedname" isDisabled="${command.saveMode eq 'V' ? true : false }"
										isMandatory="true" cssClass="hasNameClass form-control"
										maxlegnth="100">
									</apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="TbDeathregDTO.drRelativeName"
										path="deathCertificateDTO.drRelativeName" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
										cssClass="hasNameClass form-control" maxlegnth="100">
									</apptags:input>

									<apptags:input labelCode="TbDeathregDTO.drMarRelativeName"
										path="deathCertificateDTO.drMarRelativeName" isDisabled="${command.saveMode eq 'V' ? true : false }"
										isMandatory="true" cssClass="hasNameClass form-control"
										maxlegnth="100">
									</apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="TbDeathregDTO.drMotherName"
										path="deathCertificateDTO.drMotherName" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
										cssClass="hasNameClass form-control" maxlegnth="100">
									</apptags:input>
									<apptags:input labelCode="TbDeathregDTO.drMarMotherName"
										path="deathCertificateDTO.drMarMotherName" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
										cssClass="hasNameClass form-control" maxlegnth="100">
									</apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr"
										path="deathCertificateDTO.drDeceasedaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
										cssClass="hasNumClass form-control" maxlegnth="190">
									</apptags:input>
									<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr"
										path="deathCertificateDTO.drMarDeceasedaddr" isDisabled="${command.saveMode eq 'V' ? true : false }"
										isMandatory="true" cssClass="hasNumClass form-control"
										maxlegnth="190">
									</apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeath"
										path="deathCertificateDTO.drDcaddrAtdeath" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
										cssClass="hasNumClass form-control" maxlegnth="190">
									</apptags:input>
									<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeathMar"
										path="deathCertificateDTO.drDcaddrAtdeathMar" isDisabled="${command.saveMode eq 'V' ? true : false }"
										isMandatory="true" cssClass="hasNumClass form-control"
										maxlegnth="190">
									</apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="TbDeathregDTO.drDeathplace"
										path="deathCertificateDTO.drDeathplace" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
										cssClass="hasNumClass form-control" maxlegnth="100">
									</apptags:input>
									<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
										path="deathCertificateDTO.drMarDeathplace" isMandatory="true" isDisabled="${command.saveMode eq 'V' ? true : false }"
										cssClass="hasNumClass form-control" maxlegnth="100">
									</apptags:input>
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
						 <label class="col-sm-2 control-label required-control"><spring:message
								code="TbDeathregDTO.demandedCopies" text="Demanded copies"/></label>
							<div class="col-sm-4">
								<form:input class="form-control hasNumber"
									path="deathCertificateDTO.demandedCopies" id="demandedCopies" disabled="${command.saveMode eq 'V' ? true : false }"
									maxlength="5" onblur="getAmount(this)"></form:input>
							</div> 
							<c:if test="${command.saveMode ne 'V' && command.applicationChargeFlag eq 'Y'}">
											<label for="amount"
												class="col-sm-2 control-label required-control" id="amount"><spring:message
													code="BirthCertificateDTO.amount" text="amount" /></label>
											<div class="col-sm-4">
												<form:input type='text' path="deathCertificateDTO.amount"
													maxlength="350" id="amount" isReadonly="true" disabled="true"
													class="hasNumClass form-control amount" />
											</div>
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
					</div>
					</div>
					<c:if test="${command.saveMode eq 'V'}">
					<div class="form-group">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#birthReg-5"> <spring:message
										 text="Attached Documents" />
								</a>
							</h4>
						</div>

                  <c:if test="${command.saveMode eq 'V'}">
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="rts.document.name" text="" /></th>
										<th><spring:message code="rts.view.document" text="" /></th>
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
						</c:if>
					</div>
					</c:if>
					 <c:if test="${command.saveMode ne 'V'}">
					 <c:if test="${not empty command.checkList}">
                  <div class="panel panel-default">
						<div class="panel-heading">
							<h4  class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
										code="TbDeathregDTO.form.uploadDocuments"
										text="Upload Documents" /></a>
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
							</div>
							</div>
						</c:if>
						<c:if test="${command.applicationChargeFlag eq 'Y'}">
						<div id="payId">
						<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
						</div></c:if>
						</c:if>
						<div class="text-center">
						 <c:if test="${command.saveMode ne 'V'}">
							<button type="button" class="btn btn-blue-2"
								data-toggle="tooltip" data-original-title="Save"
								onclick="saveDeathCertificateData(this)">
								<spring:message code="rts.submit" text="Submit" /><i class="fa fa-arrow-right padding-left-5"
									aria-hidden="true"></i>
							</button>

							<button type="button" onclick="resetMemberMaster(this);"
								class="btn btn-warning" data-toggle="tooltip"
								data-original-title="Reset" id="resetId">
								<i class="fa padding-left-4" aria-hidden="true"></i>
								<spring:message code="rts.reset" text="Reset" />
							</button>
							</c:if>
							<button type="button" class="btn btn-danger" id="bck"
								onclick="openForm('rtsService.html','applicantForm')">
								<spring:message code="trade.back"></spring:message>
							</button>
							
						</div>
					</div>
 						
 			</form:form> 

		</div>

	</div>

</div>
</div>



 
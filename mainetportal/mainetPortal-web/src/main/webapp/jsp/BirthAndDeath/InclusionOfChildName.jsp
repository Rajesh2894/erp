<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/birthAndDeath/InclusionOfChildName.js"></script>
<script src="js/challan/offlinePay.js"></script>



<div class="pageDiv">
<apptags:breadcrumb></apptags:breadcrumb>
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Inclusion Of Child Name and Birth Certificate" code="BirthRegDto.inclAndBrCert"/>
			</h2>
		<apptags:helpDoc url="InclusionOfChildName.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="frmInclusionOfChildName"
				action="InclusionOfChildName.html" method="POST" commandName="command"
				class="form-horizontal" name="InclusionOfChildNameId">
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" /> --%>
				<form:hidden path="birthRegDto.brId"
						cssClass="hasNumber form-control" id="brId" />
				<div class="compalint-error-div">
								<jsp:include page="/jsp/tiles/validationerror.jsp" />
								<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display:none;"></div>
						</div>

				<div id="RegisDetail">
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#inclChildName-1" tabindex="-1">
										<spring:message text="Registration Detail" code="Issuaance.birth.certificate.detail"/></a>
								</h4>
							</div>
							<div id="inclChildName-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.brChildName" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brChildName" isMandatory="true" maxlegnth="400"
											cssClass="hasNameClass form-control" >
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.brChildNameMar" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brChildNameMar" isMandatory="true" maxlegnth="400"
											cssClass="hasNameClass form-control" >
										</apptags:input>
									</div>
									
									
				
									<div class="form-group">
									
									<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="BirthRegistrationDTO.brSex" text="Sex" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.brSex" cssClass="form-control" 
											isMandatory="true" hasId="true" disabled="true"
											selectOptionLabelCode="selectdropdown" />
										<%-- <apptags:input labelCode="BirthRegDto.brSex"
											path="birthRegDto.brSex" cssClass="hasNameClass form-control" 
											maxlegnth="12" isReadonly="true">
										</apptags:input> --%>
										<%-- <apptags:date fieldclass="datepicker" labelCode="BirthRegDto.brDob"
											datePath="birthRegDto.brDob" readonly="true">
										</apptags:date> --%>
										
										<label class="col-sm-2 control-label"> <spring:message
												code="BirthRegistrationDTO.brDob" text="Date of Birth" />
										</label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="birthRegDto.brDateOfBirth"
													cssClass="form-control mandColorClass datepicker" id="brDob"
													disabled="true"/>
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="BirthRegDto.parentDetailDTO.pdFathername"
											path="birthRegDto.parentDetailDTO.pdFathername"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegDto.parentDetailDTO.pdFathernameMar"
											path="birthRegDto.parentDetailDTO.pdFathernameMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="BirthRegDto.parentDetailDTO.pdMothername"
											path="birthRegDto.parentDetailDTO.pdMothername"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegDto.parentDetailDTO.pdMothernameMar"
											path="birthRegDto.parentDetailDTO.pdMothernameMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.brBirthPlace"
											path="birthRegDto.brBirthPlace"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.brBirthPlaceMar"
											path="birthRegDto.brBirthPlaceMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.brBirthAddr"
											path="birthRegDto.brBirthAddr"
											cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.brBirthAddrMar"
											path="birthRegDto.brBirthAddrMar"
											cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="BirthRegDto.brRegNo"
											path="birthRegDto.brRegNo"
											cssClass="alphaNumeric" isReadonly="true">
										</apptags:input>
										
										<c:set var="baseLookupCode" value="BDZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="BDZ"
											hasId="true" pathPrefix="birthRegDto.bndDw"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false"
											isMandatory="true" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#inclChildName-2" tabindex="-1">
										<spring:message text="Certificate Print Details" code="Issuaance.birth.certificate.print"/></a>
								</h4>
							</div>
							<div id="inclChildName-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
									<c:if test="${command.saveMode ne 'V'}">
										<apptags:input labelCode="BirthRegDto.alreayIssuedCopy" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.alreayIssuedCopy"                             
											cssClass="hasNumClass form-control" maxlegnth="12"
											isMandatory="" isReadonly="true">
										</apptags:input> 
										</c:if>	
										<label class="col-sm-2 control-label required-control"><spring:message
											code="BirthRegDto.numberOfCopies" text="Number of Copies" /></label>
									<div class="col-sm-4">
										<form:input class="form-control hasNumber" disabled="${command.saveMode eq 'V'}"
											path="birthRegDto.noOfCopies" id="noOfCopies" onblur="getAmountOnNoOfCopes()"
											maxlength="2" ></form:input>
									</div>
										
										
										<%-- <apptags:input labelCode="BirthRegDto.numberOfCopies" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.noOfCopies"
											cssClass="hasNumber form-control" maxlegnth="10"
											isMandatory="true" >
										</apptags:input> --%>
									</div>
								 <c:if test="${command.birthRegDto.chargesStatus eq 'CC' || command.birthRegDto.chargesStatus eq 'CA'}"> 
									<div class="form-group" id="chargeid">
										<apptags:input labelCode="bnd.serviceCharge"
											path="birthRegDto.serviceCharge" cssClass="hasNumClass form-control"
											maxlegnth="12"  isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="bnd.certificateFee" 
											path="birthRegDto.certificateFee" cssClass="hasNumClass form-control"
											maxlegnth="12"  isReadonly="true">
										</apptags:input>
									</div>
				                     <div class="form-group" id="amountid">
										<apptags:input labelCode="bnd.totalAmount"
											path="birthRegDto.amount" cssClass="hasNumClass form-control"
											maxlegnth="12"  isReadonly="true">
										</apptags:input>
									</div>
								 </c:if> 
									
								</div>
							</div>
						</div>
						
						
					</div>
					
					<c:if
						test="${command.saveMode eq 'V' && not empty command.viewCheckList}">
						<div class="form-group">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#inclChildName-3" tabindex="-1"> <spring:message
											text="Attached Documents" />
									</a>
								</h4>
							</div>
							<div class="col-sm-12 text-left">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="attachDocs">
										<tr>
											<th><label class="tbold"><spring:message
														code="label.checklist.srno" text="Sr No" /></label></th>
											<th><label class="tbold"><spring:message
														code="bnd.documentName" text="Document Type" /></label></th>
											<th><label class="tbold"><spring:message
														code="bnd.documentDesc" text="Document Description" /></label></th>
											<th><spring:message code="birth.view.document" text="View Documents" /></th>
										</tr>
										<c:forEach items="${command.viewCheckList}" var="lookUp" varStatus="index">
											<tr>
											    <td class="text-center"><label>${index.count}</label></td>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<td class="text-center"><label>${lookUp.doc_DESC_ENGL}</label></td>
													</c:when>
													<c:otherwise>
														<td class="text-center"><label>${lookUp.doc_DESC_Mar}</label></td>
													</c:otherwise>
												</c:choose>
												<td class="text-center"><label>${lookUp.docDescription}</label></td>
												<td align="center"><apptags:filedownload
														filename="${lookUp.documentName}"
														filePath="${lookUp.uploadedDocumentPath}" actionUrl="BirthCorrectionForm.html?Download">
													</apptags:filedownload></td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>
					</c:if>
				<c:if test="${command.birthRegDto.statusCheck eq 'A'}">
					
					<div class="padding-top-10 text-center" id="chekListChargeId">
							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" class="btn btn-success"
									data-toggle="tooltip" data-original-title="Proceed"
									id="proceedId" onclick="getChecklistAndCharges(this)">
									<spring:message code="BirthRegDto.proceed" text="Proceed" />
								</button>
								<button type="button" onclick="resetMemberMaster(this);"
									class="btn btn-warning" data-toggle="tooltip"
									data-original-title="" id="resetId">
									<i class="fa padding-left-4" aria-hidden="true"></i>
									<spring:message code="BirthRegDto.reset" />
								</button>
							</c:if>

							<%-- <c:if test="${command.saveMode ne 'E'}"> --%>
							<button type="button" class="btn btn-danger "
								id="backId" data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href ='InclusionOfChildName.html'">
								<i class="fa padding-left-4" aria-hidden="true"></i>
								<spring:message code="BirthRegDto.back" text="Back" />
							</button>
						<%-- </c:if> --%>
					</div>

					<c:if test="${not empty command.checkList}">
						<h4>
							<spring:message code="TbDeathregDTO.form.uploadDocuments" text="Upload Documents" />
						</h4>
						<fieldset class="fieldRound">
							<div class="overflow">
								<div class="table-responsive">
									<table class="table table-hover table-bordered table-striped" id="BirthIncTable">
										<tbody>
											<tr>
													<th><label class="tbold"><spring:message
																code="label.checklist.srno" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="bnd.documentName" text="Document Type" /></label></th>
													<th><label class="tbold"><spring:message code="bnd.documentDesc"
													text="Document Description" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.status" text="Status" /></label></th>
													<th><label class="tbold"><spring:message
																code="bnd.upload.document" text="Upload" /></label></th>
												</tr>


											<c:forEach items="${command.checkList}" var="lookUp" varStatus="lk">
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
													<%-- <td><form:input
														path="checkList[${lk.index}].docDescription" type="text"
														class="form-control alphaNumeric " maxLength="50"
														id="docDescription[${lk.index}]" data-rule-required="true" />
													</td> --%>
													<c:choose>
													<c:when test="${lookUp.docDes ne null}">
														<td><form:select
																path="checkList[${lk.index}].docDescription"
																class="form-control" id="docTypeSelect_${lk.index}">
																<form:option value="">
																	<spring:message code="mrm.select" />
																</form:option>
																<c:set var="baseLookupCode" value="${lookUp.docDes}" />
																<c:forEach items="${command.getLevelData(baseLookupCode)}"
																	var="docLookup">
																	<form:option value="${docLookup.lookUpDesc}">${docLookup.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
															</c:when>
													<c:otherwise>
													<td></td>
													</c:otherwise>
												</c:choose>

														<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
														<td>${lookUp.descriptionType}<spring:message code="bnd.acknowledgement.doc.mand"
																text="Mandatory" /></td>
													</c:if>
										
													<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
														<td>${lookUp.descriptionType}<spring:message code="bnd.acknowledgement.doc.opt"
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
						</c:if>
						<form:hidden path="birthRegDto.chargesStatus" id="chargeStatus"/>
						<c:if test="${command.birthRegDto.chargesStatus eq 'CA'}">
						<div class="panel panel-default" id="payId">
							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
						</div>
					   </c:if>
					  <c:if test="${command.birthRegDto.statusCheck eq 'NA' || not empty command.checkList || command.birthRegDto.chargesStatus eq 'CA'}">
						<div class="text-center padding-top-10">
							<c:if test="${command.saveMode ne 'V'}">
							<button type="button" value="<spring:message code="bt.save"/>"
								class="btn btn-success" data-toggle="tooltip" data-original-title="Submit"
								onclick="saveInclusionOfChildName(this)">
								<i class="fa padding-left-4" aria-hidden="true"></i>
								<spring:message code="TbDeathregDTO.form.savebutton" text="Save" />
							</button>
							
							<%-- <apptags:backButton url="AdminHome.html"></apptags:backButton> --%>
							<button type="button" onclick="resetMemberMaster(this);"
										class="btn btn-warning" data-toggle="tooltip" data-original-title="" id="reset">
										<i class="fa padding-left-4" aria-hidden="true"></i>
										<spring:message code="BirthRegDto.reset"/>
									</button>
									</c:if>
									
						<button type="button" class="btn btn-danger "
							id="back" data-toggle="tooltip" data-original-title=""
							onclick="window.location.href ='InclusionOfChildName.html'">
							<i class="fa padding-left-4" aria-hidden="true"></i>
							<spring:message code="BirthRegDto.back"/>
						</button>
						</div>
					</c:if>
					<c:if test="${command.viewMode eq 'V'}">
					<div class="text-center padding-top-10">
					<apptags:backButton url="CitizenHome.html"></apptags:backButton>
					</div>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
</div>







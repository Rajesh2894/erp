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



<div class="pageDiv">
<apptags:breadcrumb></apptags:breadcrumb>
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Inclusion Of Child Name and Birth Certificate" code="BirthRegDto.inclAndBrCert"/>
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div> -->
			<apptags:helpDoc url="InclusionOfChildName.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="frmInclusionOfChildName"
				action="InclusionOfChildName.html" method="POST" commandName="command"
				class="form-horizontal" name="InclusionOfChildNameId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="birthRegDto.brId"
						cssClass="hasNumber form-control" id="brId" />
				<form:hidden path="" id="kdmcEnv" value="${command.kdmcEnv}" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div id="RegisDetail">
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<c:if test="${command.saveMode eq 'E'}">
						<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a4"> <spring:message
											code="applicant.details" text="Applicant Details" /></a>
								</h4>
								<div id="a4" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">

											<label class="col-sm-2 control-label required-control"
												for="applicantTitle">
												<spring:message
											code="applicant.title" text="Title" />
												</label>
											<c:set var="baseLookupCode" value="TTL" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="birthRegDto.requestDTO.titleId"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />

											<apptags:input labelCode="rti.firstName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="birthRegDto.requestDTO.fName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">


											<apptags:input labelCode="rti.middleName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="birthRegDto.requestDTO.mName" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

											<apptags:input labelCode="rti.lastName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="birthRegDto.requestDTO.lName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>
										<div class="form-group">

											<label class="col-sm-2 control-label "><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField items="${command.getLevelData('GEN')}"
												path="birthRegDto.requestDTO.gender"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												disabled="${command.saveMode eq 'V' ? true : false }" />
										</div>
										
									</div>
								</div>
							</div>						
						<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="applicant.ApplicantAddress" text="Applicant Address" /></a>
							</h4>
						<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">
										<apptags:input labelCode="rti.buildingName"
											cssClass="hasNumClass mandColorClass " path="birthRegDto.requestDTO.bldgName"
											maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										<apptags:input labelCode="rti.taluka"
											cssClass=" mandColorClass " path="birthRegDto.requestDTO.blockName"
											 maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="applicant.roadName"
											cssClass="hasNumClass mandColorClass"
											path="birthRegDto.requestDTO.roadName"  maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }">
										</apptags:input>
										<c:if test="${command.kdmcEnv eq 'N'}">
											<label class="control-label col-sm-2 " for="text-1"><spring:message
													code="tbDeathregDTO.applicant.wardName" text="Ward Name" /></label>
											<c:set var="baseLookupCode" value="BWD" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="birthRegDto.requestDTO.wardNo" cssClass="form-control"
												hasId="true" selectOptionLabelCode="selectdropdown" />
										</c:if>
										<c:if test="${command.kdmcEnv eq 'Y'}">
											<label class="control-label col-sm-2 required-control"
												for="text-1"><spring:message
													code="ParentDetailDTO.pdRegUnitId" text="Registration Unit" /></label>
											<c:set var="baseLookupCode" value="REU" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="birthRegDto.parentDetailDTO.pdRegUnitId"
												cssClass="form-control" hasId="true"
												selectOptionLabelCode="selectdropdown" />
										</c:if>

									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.Try5"
											cssClass="hasNameClass mandColorClass "
											path="birthRegDto.requestDTO.cityName"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											isMandatory="true" maxlegnth="100"></apptags:input>
										<apptags:input labelCode="rti.pinCode"
											cssClass="hasPincode mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.requestDTO.pincodeNo" 
											maxlegnth="100"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.mobile1"
											cssClass="hasMobileNo mandColorClass "
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.requestDTO.mobileNo" isMandatory="true" maxlegnth="100"></apptags:input>
										<apptags:input labelCode="chn.lEmail"
											cssClass="hasemailclass  mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="birthRegDto.requestDTO.email" maxlegnth="100"></apptags:input>
									</div>
								</div>



							</div>
							</c:if>
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#inclChildName-1">
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
										<apptags:date fieldclass="datepicker" labelCode="BirthRegDto.brDob"
											datePath="birthRegDto.brDob" isDisabled="true" readonly="true">
										</apptags:date>
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
											labelCode="ParentDetailDTO.permanent.parent.addr"
											path="birthRegDto.brBirthAddr"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input
											labelCode="ParentDetailDTO.permanent.parent.addrMar"
											path="birthRegDto.brBirthAddrMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.BrParAddr.childBirthEng"
											path="birthRegDto.parentDetailDTO.pdParaddress"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="BirthRegDto.BrParAddr.childBirthReg"
											path="birthRegDto.parentDetailDTO.pdParaddressMar"
											cssClass="hasNameClass form-control" isReadonly="true">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="BirthRegDto.brRegNo"
											path="birthRegDto.brRegNo"
											cssClass="alphaNumeric" isReadonly="true">
										</apptags:input>
									</div>
								</div>
							</div>
						
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#inclChildName-2">
										<spring:message text="Certificate Print Details" code="Issuaance.birth.certificate.print"/></a>
								</h4>
							</div>
							<div id="inclChildName-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.alreayIssuedCopy" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.alreayIssuedCopy"                             
											cssClass="hasNumClass form-control" maxlegnth="12"
											isMandatory="" isReadonly="true">
										</apptags:input> 
										<apptags:input labelCode="BirthRegDto.numberOfCopies" isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.noOfCopies"
											cssClass="hasNumber form-control" maxlegnth="2"
											isMandatory="true" onBlur="getAmountOnNoOfCopes()">
										</apptags:input>
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
				<c:if test="${command.birthRegDto.statusCheck eq 'A'}">
					<div class="text-center">
					<div class="padding-top-10 text-center" id="chekListChargeId">
							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" class="btn btn-submit"
									data-toggle="tooltip" data-original-title="Proceed"
									id="proceedId" onclick="getChecklistAndCharges(this)">
									<spring:message code="BirthRegDto.proceed" text="Proceed" />
								</button>
								<button type="button" onclick="resetMemberMaster(this);"
									class="btn btn-warning" data-toggle="tooltip"
									data-original-title="" id="resetId">
									<spring:message code="BirthRegDto.reset" />
								</button>
							</c:if>

							<%-- <c:if test="${command.saveMode ne 'E'}"> --%>
							<button type="button" class="btn btn-danger "
								id="backId" data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href ='InclusionOfChildName.html'">
								<spring:message code="BirthRegDto.back" text="Back" />
							</button>
						<%-- </c:if> --%>
					</div>
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
													<th><label class="tbold"><spring:message
																code="bnd.documentDesc" text="Document Description" /></label></th>
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
													<%-- <td><form:input	path="checkList[${lk.index}].docDescription"
														type="text"	class="form-control alphaNumeric "
														maxLength="50" id="docDescription[${lk.index}]" data-rule-required="true" />
													</td> --%>
												<c:choose>
													<c:when test="${lookUp.docDescription ne null}">
													<td><form:select 
													path="checkList[${lk.index}].docDescription"
													 class="form-control" id="docTypeSelect_${lk.index}">
													<form:option value="">
														<spring:message code="mrm.select" />
													</form:option>
													<c:set var="baseLookupCode" value="${lookUp.docDescription}" />
													<c:forEach items="${command.getLevelData(baseLookupCode)}" var="docLookup">	
														<form:option value="${docLookup.lookUpDesc}" >${docLookup.lookUpDesc}</form:option>
													</c:forEach>
												    </form:select></td>
												    </c:when>
													<c:otherwise>
													<td></td>
													</c:otherwise>
												</c:choose>
													
													<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
														<td>${lookUp.descriptionType}<spring:message code=""
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
						</c:if>
						</c:if>
						<form:hidden path="birthRegDto.chargesStatus" id="chargeStatus"/>
						<c:if test="${command.birthRegDto.chargesStatus eq 'CA'}">
						<div class="panel panel-default" id="payId">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</div>
					   </c:if>
					  <c:if test="${command.birthRegDto.statusCheck eq 'NA' || not empty command.checkList || command.birthRegDto.chargesStatus eq 'CA'}">
						<div class="text-center">
						<div class="text-center padding-top-10" align="center">
							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" align="center" class="btn btn-green-3" data-toggle="tooltip" data-original-title="Submit"
									onclick="saveInclusionOfChildName(this)">
									<spring:message code="BirthRegDto.submit"/>
								</button>
							
							<%-- <apptags:backButton url="AdminHome.html"></apptags:backButton> --%>
							<button type="button" align="center" onclick="resetMemberMaster(this);"
										class="btn btn-warning" data-toggle="tooltip" data-original-title="" id="reset">
										<spring:message code="BirthRegDto.reset"/>
									</button>
									</c:if>
									
						<button type="button" align="center" class="btn btn-danger "
							id="back" data-toggle="tooltip" data-original-title=""
							onclick="window.location.href ='InclusionOfChildName.html'">
							<spring:message code="BirthRegDto.back"/>
						</button>
						</div>
						</div>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
</div>







<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/witness.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<div class="widget-content padding" id="witnessTabForm">
	<form:form action="MarriageRegistration.html" id="witnessFormId"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<ul>
						<li><label id="errorId"></label></li>
					</ul>
				</div>
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="marriageDTO.marId" id="marId" />
		<form:hidden path="payableFlag" id="payableFlagAppl" />
		<form:hidden path="marriageDTO.noOfWitness" id="noOfWitness" />
		<form:hidden path="" id="langId" value="${userSession.languageId}"/>

		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div id="witnessDetDiv">
				<div class="panel-heading">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#WitnessDetails" ><spring:message
								code="mrm.tab.witnessDetails" /></a>
					</h4>
				</div>

				
				<div id="WitnessDetails" class="panel-collapse collapse in">
					<div class="panel-body">

						<div id="witnessDataTable">
							<table class="table table-bordered margin-bottom-10"
								id="witnessTable">
								<thead>
									<tr>
										<th class="text-center"><spring:message
												code="mrm.witness.firstNameE" text="First Name" /></th>
										<th class="text-center"><spring:message
												code="mrm.witness.middleNameE" text="Middle Name" /></th>
										<th class="text-center"><spring:message
												code="mrm.witness.lastNameE" text="Last Name" /></th>
										<th class="text-center"><spring:message
												code="mrm.witness.address" text="Address" /></th>
										<th class="text-center"><spring:message
												code="mrm.witness.occupation" text="Occupation" /></th>
										<th class="text-center"><spring:message
												code="mrm.witness.officeAddr" text="Office Address" /></th>
										<th class="text-center"><spring:message
												code="mrm.witness.relationWithCouple"
												text="Relation With Married Couple" /></th>
										<th class="text-center"><spring:message
												code="mrm.witness.uid" text="UID" /></th>
										<th width="10%" class="text-center"><spring:message code="mrm.action"
												text="Action" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.marriageDTO.witnessDetailsDTO}"
										var="witnessDTO" varStatus="count">
										<tr class="witnessList">
											<c:choose>
												<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
													<td align="center">${witnessDTO.firstNameEng}</td>
													<td align="center">${witnessDTO.middleNameEng}</td>
													<td align="center">${witnessDTO.lastNameEng}</td>
													<td align="center">${witnessDTO.fullAddrEng}</td>
												</c:when>
												<c:otherwise>
													<td align="center">${witnessDTO.firstNameReg}</td>
													<td align="center">${witnessDTO.middleNameReg}</td>
													<td align="center">${witnessDTO.lastNameReg}</td>
													<td align="center">${witnessDTO.fullAddrReg}</td>
												</c:otherwise>
											</c:choose>
											<td align="center">${witnessDTO.occupationDesc}</td>
											<td align="center">${witnessDTO.offcAddr}</td>
											<td align="center">${witnessDTO.relationDesc}</td>
											<td align="center"><span id="witUID_${count.count}">${witnessDTO.uidNo}</span></td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													onClick="openActionWitness(${witnessDTO.srNo},'V')"
													title="<spring:message code="mrm.witness.view" text="View witness"></spring:message>">
													<i class="fa fa-eye"></i>
												</button>


												<button type="button" class="btn btn-blue-2 btn-sm btn-sm"
													onClick="openActionWitness(${witnessDTO.srNo},'E')"
													title="<spring:message code="mrm.witness.edit"/>"
													<c:if test="${ command.modeType eq 'V'|| command.status eq 'PROCESSING'}">disabled='disabled'</c:if>>
													<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
												</button>


											</td>
										</tr>

									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
				</div>
				</div>


				<div id="witnessInputId">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse1" href="#WitnessDet"tabindex="-1"><spring:message
									code="mrm.witness.enterDetails" /></a>
						</h4>
					</div>

					<div id="WitnessDet" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group">
								<apptags:input labelCode="mrm.witness.firstNameE"
									path="witnessDTO.firstNameEng"
									isReadonly="${command.witnessModeType eq 'V'}"
									isMandatory="true" cssClass="hasCharacter form-control"
									maxlegnth="100"></apptags:input>
								<apptags:input labelCode="mrm.witness.firstNameR"
									path="witnessDTO.firstNameReg"
									isReadonly="${command.witnessModeType eq 'V'}"
									isMandatory="true" cssClass="  form-control hasNameClass"
									maxlegnth="100"></apptags:input>
							</div>
							<div class="form-group">
								<apptags:input labelCode="mrm.witness.middleNameE"
									path="witnessDTO.middleNameEng"
									isReadonly="${command.witnessModeType eq 'V'}"
									isMandatory="false" cssClass="hasCharacter form-control"
									maxlegnth="100"></apptags:input>
								<apptags:input labelCode="mrm.witness.middleNameR"
									path="witnessDTO.middleNameReg"
									isReadonly="${command.witnessModeType eq 'V'}"
									isMandatory="false" cssClass="  form-control hasNameClass"
									maxlegnth="100"></apptags:input>
							</div>
							<div class="form-group">
								<apptags:input labelCode="mrm.witness.lastNameE"
									path="witnessDTO.lastNameEng"
									isReadonly="${command.witnessModeType eq 'V'}"
									isMandatory="true" cssClass="hasCharacter form-control"
									maxlegnth="100"></apptags:input>
								<apptags:input labelCode="mrm.witness.lastNameR"
									path="witnessDTO.lastNameReg"
									isReadonly="${command.witnessModeType eq 'V'}"
									isMandatory="true" cssClass="  form-control hasNameClass"
									maxlegnth="100"></apptags:input>
							</div>
							<div class="form-group">
								<apptags:textArea labelCode="mrm.witness.fullAddress"
									path="witnessDTO.fullAddrEng"
									isDisabled="${command.witnessModeType eq 'V'}"
									isMandatory="true"  maxlegnth="499"></apptags:textArea>
								<apptags:textArea labelCode="mrm.witness.fullAddressR"
									path="witnessDTO.fullAddrReg"
									isDisabled="${command.witnessModeType eq 'V'}"
									isMandatory="true"  maxlegnth="499"></apptags:textArea>
							</div>
							<div class="form-group">
								<c:set var="baseLookupCodeOCU" value="OCU" />
								<label class="col-sm-2 control-label required-control "
									for="assetgroup"> <spring:message
										code="mrm.witness.occupation" />
								</label>
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCodeOCU)}"
									path="witnessDTO.occupation"
									disabled="${command.witnessModeType eq 'V'}"
									cssClass="form-control chosen-select-no-results"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="Select" isMandatory="true" />

								<apptags:textArea labelCode="mrm.witness.officeAddr"
									path="witnessDTO.offcAddr"
									isDisabled="${command.witnessModeType eq 'V'}"
									 maxlegnth="499"></apptags:textArea>
							</div>
							<div class="form-group">
								<%-- <apptags:input labelCode="mrm.witness.relationWithCouple"
									path="witnessDTO.relation"
									isDisabled="${command.witnessModeType eq 'V'}"
									isMandatory="true" cssClass="form-control"
									maxlegnth="100"></apptags:input> --%>
								<c:set var="baseLookupCodeOCU" value="OCU" />
								<label class="col-sm-2 control-label required-control "
									for="assetgroup"> <spring:message
										code="mrm.witness.relationWithCouple" />
								</label>
								<apptags:lookupField
									items="${command.getLevelData('RLS')}"
									path="witnessDTO.relation" changeHandler="checkRelation(this)"
									disabled="${command.witnessModeType eq 'V'}"
									cssClass="form-control chosen-select-no-results"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="Select" isMandatory="true" />
									
								
								<apptags:input labelCode="mrm.witness.uid"
									path="witnessDTO.uidNo"
									isReadonly="${command.witnessModeType eq 'V'}"
									isMandatory="true" cssClass="form-control "
									maxlegnth="14"></apptags:input>
							</div>
							
							<div class="form-group specifyRel">
								<apptags:input labelCode="mrm.specifyRel"
									path="witnessDTO.otherRel"
									isDisabled="${command.witnessModeType eq 'V'}"
									isMandatory="true" cssClass="form-control hasNameClass"
									maxlegnth="60"></apptags:input>
							</div>



						</div>
					</div>

					<div class="text-center addWitBT">
						<button type="button" class="button-input btn btn-primary"
							name="button" value="Save" onclick="addWitnessBT(this)"
							id="addWitness">
							<spring:message code="mrm.witness.addWitness" />
						</button>

					</div>
				</div>

			</div>
		</div>
		<br>

		<c:if test="${not empty command.checkList}">

			<div class="panel-group accordion-toggle">
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#a3"><spring:message code="mrm.upload.attachement"
							text="Upload Attachment" /></a>
				</h4>
                <!-- Defect #127364 -->
                <h5>
					<strong><i><spring:message code="mrm.document.note"
								text="Kindlly Enter Document Description" /></i></strong>
				</h5>
				<div class="panel-collapse collapse in" id="a3">

					<div class="panel-body">

						<div class="overflow margin-top-10 margin-bottom-10">
							<div class="table-responsive">
								<table class="table table-hover table-bordered table-striped"
									id="documentList">
									<tbody>
										<tr>
											<th><spring:message code="mrm.srno" text="Sr No" /></th>
											<th><spring:message code="mrm.documentName"
													text="Document Name" /></th>
											<th><spring:message code="mrm.documentDesc"
													text="Document Description" /></th>
											<th><spring:message code="mrm.status" text="Status" /></th>
											<th width="500"><spring:message code="mrm.upload.doc"
													text="Upload" /></th>
										</tr>

										<c:forEach items="${command.checkList}" var="lookUp"
											varStatus="lk">

											<tr>
												<td>${lookUp.documentSerialNo}</td>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<td>${lookUp.doc_DESC_ENGL}</td>
													</c:when>
													<c:otherwise>
														<td>${lookUp.doc_DESC_Mar}</td>
													</c:otherwise>
												</c:choose>

												<%-- <td><form:input
														path="checkList[${lk.index}].docDescription" type="text"
														class="form-control alphaNumeric " maxLength="50"
														id="docDescription[${lk.index}]" data-rule-required="true" /></td> --%>
														
												<c:choose>
													<c:when test="${lookUp.docDes ne null}">
													<td><form:select 
													path="checkList[${lk.index}].docDescription"
													 class="form-control" id="docTypeSelect_${lk.index}">
													<form:option value="">
														<spring:message code="mrm.select" />
													</form:option>
													<c:set var="baseLookupCode" value="${lookUp.docDes}" />
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
													<td><spring:message code="mrm.doc.mandatory" /></td>
												</c:if>
												<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
													<td><spring:message code="mrm.doc.optional" /></td>
												</c:if>
												<td>
													<div id="docs_${lk}" class="">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="checkList[${lk.index}]" isMandatory="false"
															showFileNameHTMLId="true" fileSize="CARE_COMMON_MAX_SIZE"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
															currentCount="${lk.index}" />
														<div id="tooltip_${lk.index}">
														<c:set var="sizeInMB" value="${lookUp.docSize/1024/1024 }"/>
															<%-- <small class="text-red  hideTooltip" > <spring:message
																	code="mrm.file.upload.checklist.size"
																	text="Maximum Size should be ${sizeInMB} MB" />${sizeInMB}
																	<spring:message code="mrm.file.size" text=" MB" />
																	<spring:message code="mrm.file.extension.end" text=" extension" />
															</small> --%>
															<small class="text-blue-2"> <spring:message
																code="mrm.file.extension.end"
																text="Only jpg,jpeg,png,gif,bmp,pdf file allowed to upload." />
															    <spring:message code="mrm.file.size.stmt" text="MB" />
															</small>
														
														
														</div>
													</div>
												</td>
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

		<c:if test="${command.payableFlag eq 'Y' }">
			<div class="form-group margin-top-10">
				<label class="col-sm-2 control-label"><spring:message
						code="mrm.field.name.amounttopay" /></label>
				<div class="col-sm-4">
					<input type="text" class="form-control text-right"
						value="${command.offlineDTO.amountToShow}" maxlength="12"
						disabled="true"></input> <a
						class="fancybox fancybox.ajax text-small text-info"
						href="MarriageRegistration.html?showChargeDetails"><spring:message
							code="mrm.lable.name.chargedetail" /> <i
						class="fa fa-question-circle "></i></a>
				</div>
			</div>
			<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
		</c:if>

		<c:if test="${command.approvalProcess ne 'Y' }">
			<div class="text-center">
				<c:choose>
					<c:when
						test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D' }">
						<c:set var="backButtonAction" value="showTab('#wife')" />
					</c:when>
					<c:otherwise>
						<c:set var="backButtonAction" value="backToHomePage()" />
					</c:otherwise>
				</c:choose>


				<c:if
					test="${(command.applicationChargeApplFlag eq 'Y' || command.checkListApplFlag eq 'Y' )  && command.modeType ne 'V'}">
					<button type="button" class="btn btn-success" id="confirmToProceed"
						onclick="getChecklistAndCharges(this)">
						<spring:message code="mrm.confirm.to.proceed"
							text="Confirm To Proceed"></spring:message>
					</button>

				</c:if>


				<c:if
					test="${command.applicationChargeApplFlag eq 'N' && command.checkListApplFlag eq 'N'}">

					<c:choose>
						<c:when test="${command.status eq 'APPROVED'}">
							<button type="button" class="btn btn-success" id="save"
								onclick="saveMarriageCertificateAfterEdit(this)">
								<spring:message code="mrm.proceed" text="Save"></spring:message>
							</button>
						</c:when>

						<c:otherwise>
							<button type="button" class="btn btn-success" id="save"
								onclick="saveWitness(this)">
								<spring:message code="mrm.proceed" text="Save"></spring:message>
							</button>
						</c:otherwise>
					</c:choose>




				</c:if>

				<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button type="Reset" class="btn btn-warning"
						onclick="resetWitnessDet()">
						<spring:message code="mrm.button.reset" text="Reset" />
					</button>
				</c:if>
				<button type="button" class="btn btn-danger" name="button" id="Back"
					value="Back" onclick="${backButtonAction}">
					<spring:message code="mrm.button.back" />
				</button>
			</div>
		</c:if>
	</form:form>
</div>
<script>
 if('${command.witnessModeType}' == "N"){
	 $('#witnessInputId').hide();
 }
//D#127354
 var witnessDet='${command.marriageDTO.witnessDetailsDTO[0].firstNameEng}';
 if(witnessDet.length <=0)
	 $('#witnessDetDiv').hide();
 
//D#127357
 var rowCount = $('#witnessTable >tbody >tr').length;
 var noOfWitn = Number($('#noOfWitness').val());
 if(noOfWitn == rowCount){
 	$('#witnessInputId').hide();
 }
 
 </script>
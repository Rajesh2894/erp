<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<script src="js/social_security/pensionSchemeMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="pension.sch.eligibility.schemeeligcriteria" />
				</h2>

				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="pensionSchemeId" action="PensionSchemeMaster.html"
					method="POST" class="form-horizontal" name="pensionSchemeId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<form:hidden path="modeType" id="modeType" />
					<form:hidden path="applValidFlag" id="applValidFlag" />
					<form:hidden path="envFlag" id="envFlag" />
					<!----------------------------- Scheme Details start ---------------------------->
					<h4>
						<spring:message code="pension.sch.eligibility.schemedetails" />
					</h4>
					
					<div class="form-group">	
						<label for="" class="col-sm-2 control-label required-control"><spring:message
								code="pension.sch.eligibility.selectschemename" /> </label>
						<div class="col-sm-4">
							<form:select name="serviceId" path="pensionSchmDto.serviceId"
								id="serviceId" class="form-control"
								disabled="${command.modeType eq 'E' || command.modeType eq 'V' || command.modeType eq 'R'}"
								data-rule-required="true">
								<option value="0"><spring:message text="Select" /></option>
								<c:forEach items="${command.serviceList}" var="objArray">
									<c:choose>
										<c:when
											test="${userSession.getCurrent().getLanguageId() eq 1}">
											<form:option value="${objArray[0]}" code="${objArray[2]}"
												label="${objArray[1]}"></form:option>
										</c:when>
										<c:otherwise>
											<form:option value="${objArray[0]}" code="${objArray[2]}"
												label="${objArray[3]}"></form:option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select>
						</div>
                      
						<apptags:textArea path="pensionSchmDto.objOfschme"
							labelCode="pension.sch.eligibility.objectiveofscheme"
							isMandatory="true" maxlegnth="500" cssClass="form-control"
							isDisabled="${command.modeType eq 'E'|| command.modeType eq 'V' || command.modeType eq 'R'}" />
			
					</div>

					<div class="form-group">

						<apptags:input path="pensionSchmDto.resolutionNo"
							labelCode="soc.resolutionNo" isMandatory="true" maxlegnth="200"
							cssClass="form-control" isDisabled="${command.modeType eq 'E'|| command.modeType eq 'V' || command.modeType eq 'R'}" />


						<apptags:date fieldclass="datepicker" labelCode="soc.resolutionDate"
							datePath="pensionSchmDto.resolutionDate" cssClass="form-control"
							isDisabled="${command.modeType eq 'E'|| command.modeType eq 'V' || command.modeType eq 'R'}">
						</apptags:date>

					</div>
					
					<c:if test="${command.envFlag eq 'Y'}">
					<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="social.sec.typeOfScheme" text="Type Of Scheme" /></label>
							<c:set var="baseLookupCode" value="TSC" />
							<apptags:lookupField items="${command.getLevelData('TSC')}"
								path="configurtionMasterDto.typeOfScheme"
								disabled="${command.modeType eq 'E' || command.modeType eq 'V' || command.modeType eq 'R'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
								
							<apptags:input labelCode="config.mst.beneficiary.count.number"
							path="configurtionMasterDto.beneficiaryCount"
							isDisabled="${command.modeType eq 'E' || command.modeType eq 'V' || command.modeType eq 'R'}"
							cssClass="hasAadharNo" isMandatory="true" maxlegnth="12"></apptags:input>
					</div>
					<div class="form-group">
						<label for="text-1"
							class="control-label col-sm-2 required-control fromDate"><spring:message
								code="social.demographicReport.fromdate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker " id="fromDate"
									path="configurtionMasterDto.fromDate"
									onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
									placeholder="Enter From Date" maxlength="10"
									disabled="${command.modeType eq 'E' || command.modeType eq 'V' || command.modeType eq 'R'}" />
								<label class="input-group-addon" for="trasaction-date-icon30"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>
						<label for="text-1"
							class="control-label col-sm-2 required-control"><spring:message
								code="social.demographicReport.todate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker todate" id="toDate"
									path="configurtionMasterDto.toDate"
									onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
									placeholder="Enter To Date" maxlength="10"
									onchange="dateEnable()"
									disabled="${command.modeType eq 'E' || command.modeType eq 'V' || command.modeType eq 'R'}" />
								<label class="input-group-addon" for="trasaction-date-icon30"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>
					</div>
                    </c:if>
		



					<c:if test="${command.modeType ne 'C'}">
						<c:choose>
							<c:when test="${command.pensionSchmDto.isSchmeActive eq 'Y'}">

								<div class="form-group">
									<label for="" class="col-sm-2 control-label "><spring:message
											code="soc.inactiveScheme" text="Inactive Scheme" />  </label>
									<div class="col-sm-4">
										<form:checkbox path="pensionSchmDto.isSchmeActive"
											onchange="checkBoxDisableAll(this)" id="checkBoxIds"
											value="true" class="margin-left-50" disabled = "${ command.modeType eq 'V'}"/>
									</div>
									
								</div>
							</c:when>
							<c:otherwise>

								<div class="form-group">
									<label for="" class="col-sm-2 control-label "><spring:message
											code="soc.inactiveScheme" text="Inactive Scheme" />  </label>
									<div class="col-sm-4">
										<form:checkbox path="pensionSchmDto.isSchmeActive"
											onchange="checkBoxDisableAll(this)" id="checkBoxIds"
											value="true" class="margin-left-50" checked="checked" disabled = "${ command.modeType eq 'V'}"/>
								 </div>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
						
					

					<!-- checkbox for inactive scheme end -->

					<!----------------------------- Scheme Details end ---------------------------->

					<!----------------------------- Source Of Fund start ---------------------------->
					<h4>
						<spring:message code="pension.sch.eligibility.sourceoffund" />
					</h4>
					<table class="table text-left table-striped table-bordered"
						id="sourcefund">
						<thead>
							<tr>
								<th class="text-center required-control"><spring:message
										code="pension.sch.eligibility.sponsoredby" /></th>
								<th class="text-center required-control"><spring:message
										code="pension.sch.eligibility.sharingamount" /></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="fundstatus"
								items="${command.pensionSchmDto.pensionSourceFundList}"
								varStatus="varcount">
								<tr class="appendableClass">
									<td class="vertical-align-middle" width="40%;"><form:select
											path="pensionSchmDto.pensionSourceFundList[${varcount.index}].sponcerId"
											onchange="findDuplicate(this,${varcount.index})"
											class="form-control" data-rule-required="true"
											disabled="${command.modeType eq 'E'|| command.modeType eq 'V' || command.modeType eq 'R'}">
											<form:option value="">
												<spring:message text="Select" />
											</form:option>
											<c:forEach items="${command.sponcerByList}" var="slookUp">
												<form:option value="${slookUp.lookUpId}"
													code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td class="text-center vertical-align-middle"><form:input
											path="pensionSchmDto.pensionSourceFundList[${varcount.index}].sharingAmt"
											cssClass="text-right form-control hasDecimal"
											onkeypress="return hasAmount(event,this,9,2)"
											disabled="${command.modeType eq 'E' || command.modeType eq 'V' || command.modeType eq 'R'}" /></td>
									
												<c:if test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'R'}">
													<td class="text-center"><a href="javascript:void(0);"
														data-toggle="" data-placement="top"
														class="addCF btn btn-success btn-sm"
														id="addCF" title="add"><i
															class="fa fa-plus-circle"></i></a> <a
														href="javascript:void(0);" data-placement="top"
														class="remCF btn btn-danger btn-sm"><i
															class="fa fa-trash"></i></a></td>
												</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!----------------------------- Source Of Fund end ---------------------------->
					
					<!----------------------------- eligibility criteria start ---------------------------->
					<c:if test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'R'}">
						<h4>
							<spring:message code="pension.sch.eligibility.eligibilitycrit" />
						</h4>
						<div class="nextPageClass">
							<table class="table table-bordered margin-bottom-10"
								id="criteriatableId">
								<thead>
									<tr>
										<th colspan="2" class="text-center"><spring:message
												code="pension.sch.eligibility.factorapplicable" /></th>
										<th class="text-center" width="20%;"><spring:message
												code="pension.sch.eligibility.selectcriteria" /></th>
										<th class="text-center"><spring:message
												code="pension.sch.eligibility.rangefrom" /></th>
										<th class="text-center"><spring:message
												code="pension.sch.eligibility.rangeto" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.sourceLookUps}" var="lookUp"
										varStatus="varcount">
										<tr class="appendableClass1" id="appendableClassId">
											
											<form:hidden
												path="pensionSchmDto.pensioneligibilityList[${varcount.index}].factorApplicableId"
												value="${lookUp.lookUpId}" />
											<form:hidden
												path="pensionSchmDto.pensioneligibilityList[${varcount.index}].factorApplicableDesc"
												value="${lookUp.lookUpDesc}" />
											<td align="right" width="3%"><form:checkbox
													path="pensionSchmDto.pensioneligibilityList[${varcount.index}].checkBox"
													onclick="removeDisable(${varcount.index},'${lookUp.lookUpDesc}');"
													cssClass="checkboxClass" value="true" /></td>
											<td align="left">${lookUp.lookUpDesc}</td>
											<td><form:select
													path="pensionSchmDto.pensioneligibilityList[${varcount.index}].criteriaId"
													class="form-control chosen-select-no-results"
													disabled="true"
													>
													<form:option value="">
														<spring:message code='master.selectDropDwn' />
													</form:option>
													<c:forEach items="${command.secondLevellookUps}"
														var="slookUp">
														<c:if test="${lookUp.lookUpId eq slookUp.lookUpParentId}">
															<form:option value="${slookUp.lookUpId}"
																code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
														</c:if>
													</c:forEach>
												</form:select></td>
											<td><form:input
													path="pensionSchmDto.pensioneligibilityList[${varcount.index}].rangeFrom"
													cssClass="text-right form-control hasNumber"
													disabled="true" maxlength="5" /></td>
											<td><form:input
													path="pensionSchmDto.pensioneligibilityList[${varcount.index}].rangeTo"
													cssClass="text-right form-control hasNumber"
													disabled="true" maxlength="5" /></td>
										</tr>
									</c:forEach>

									<th colspan="2"
										class="text-center vertical-align-middle required-control"><spring:message
											code="pension.sch.eligibility.paymentschedule" /></th>
									<td><form:select
											path="pensionSchmDto.paySchedule"
											class="form-control chosen-select-no-results"
											disabled="${command.modeType eq 'V'}">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.paymentList}" var="slookUp">
												<form:option value="${slookUp.lookUpId}"
													code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
											</c:forEach>

										</form:select></td>
									<th class="text-center vertical-align-middle required-control"><spring:message
											code="pension.sch.eligibility.amount" /></th>
									<td><form:input
											path="pensionSchmDto.amt"
											cssClass="text-right form-control hasDecimal"
											disabled="${command.modeType eq 'V'}" maxlength="9" /></td>
									</td>

								</tbody>
							</table>
					</c:if>

					<!-------------------------- eligibility criteria end ------------------------------->
					<c:if test="${command.modeType ne 'V'}">
						<div class="text-center margin-top-15 margin-bottom-15">
							<button type="button" class="btn btn-success" title="Save"
								id="Savebutton" onclick="savePensionCriteria(this)">
								<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i><spring:message code="soc.save" text="Save" />
							</button>
							<button type="button" class="btn btn-warning" title="Reset"
								id="Resetbutton" onclick="resetPensionCriteria(this,${command.pensionSchmDto.schmeMsId},${command.pensionSchmDto.orgId})">
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message code="social.btn.reset" text="Reset" />
							</button>
						</div>
					</c:if>

					<!---------------- view the entered data start--------------------->
                 <c:if test="${command.modeType ne 'R'}">
					<h4>
						<spring:message code="pension.sch.eligibility.selecteddetails" />
					</h4>
					<c:forEach items="${command.saveDataList}" var="viewList"
						varStatus="varcount">
						<table class="table table-bordered margin-bottom-15"
							id="summitAfterSaveId">
							<tbody>
								<tr>
									<th class="text-center"><spring:message
											code="pension.sch.eligibility.factorapps" /></th>
									<th class="text-center"><spring:message
											code="pension.sch.eligibility.selectcriterias" /></th>
									<th class="text-center"><spring:message
											code="pension.sch.eligibility.rangefroms" /></th>
									<th class="text-center"><spring:message
											code="pension.sch.eligibility.rangetos" /></th>
									<th class="text-center"><spring:message code="pension.sch.eligibility.paymentschedule"
											text="Payment Schedule" /></th>
									<th class="text-center"><spring:message
											code="pension.sch.eligibility.amounts" /></th>
									<%-- <c:if test="${command.modeType ne 'V'}">
										<th class="text-center"><spring:message
												code="pension.sch.eligibility.edits" /></th>
									</c:if> --%>
									<%-- <th class="text-center"><spring:message code="pension.sch.eligibility.edits"/></th> --%>
								</tr>

								<c:forEach items="${viewList}" var="viewLists"
									varStatus="varcount">
									<tr>
										<td><c:forEach items="${command.sourceLookUps}"
												var="lookUp">
												<c:if
													test="${lookUp.lookUpId eq viewLists.factorApplicableId}">
													 ${lookUp.lookUpDesc}
												</c:if>
											</c:forEach></td>
										<td><c:forEach items="${command.secondLevellookUps}"
												var="slookUp">
												<c:if test="${slookUp.lookUpId eq viewLists.criteriaId}">
													 ${slookUp.lookUpDesc}
												</c:if>
											</c:forEach></td>
										<td align="right">${viewLists.rangeFrom}</td>
										<td align="right">${viewLists.rangeTo}</td>
										<%-- <td align="right" rowspan="${fn:length(viewList)}">${viewLists.payScheduleDesc}</td> --%>


										<c:if test="${varcount.index==0}">
										<td   rowspan="${fn:length(viewList)}">${viewLists.payScheduleDesc}</td>
											<td rowspan="${fn:length(viewList)}" align="right">${viewLists.amt}</td>
											<c:if test="${command.modeType ne 'V'}">
												<%-- <td class="text-center vertical-align-middle"
													rowspan="${fn:length(viewList)}">
													<button type="button" class="deleteDetails btn btn-danger"
														name="button-123" id="Deletebutton" title="Delete"
														onclick="deleteDetails(${viewLists.batchId},this)">
														<i class="fa fa-trash" aria-hidden="true"></i>
													</button>
												</td> --%>
											</c:if>

										</c:if>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</c:forEach>
					</c:if>
	              </div>
					<!---------------- view the entered data end--------------------->

			<div class="form-group margin-top-15">
				<label for="" class="col-sm-2 control-label "><spring:message
						code="soc.family.req" text="Is family details required" /> </label>
				<c:if test="${command.modeType ne 'C'}">
					<c:choose>
						<c:when test="${command.pensionSchmDto.familyDetReq eq 'Y'}">
							<div class="col-sm-4">
								<form:checkbox path="pensionSchmDto.familyDetReq"
									id="familyDetReq" value="true" class="margin-left-50"
									disabled="${ command.modeType eq 'V'}" checked="checked" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="col-sm-4">
								<form:checkbox path="pensionSchmDto.familyDetReq"
									id="familyDetReq" value="true" class="margin-left-50"
									disabled="${ command.modeType eq 'V'}" />
							</div>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${command.modeType eq 'C'}">
					<div class="col-sm-4">
						<form:checkbox path="pensionSchmDto.familyDetReq"
							id="familyDetReq" value="true" class="margin-left-50" />
					</div>

				</c:if>
				<c:if test="${command.envFlag eq 'Y'}">
					<label for="" class="col-sm-2 control-label "><spring:message
							code="soc.life.certificate.req"
							text="Is Last Date of Life Certificate required" /> </label>
					<c:if test="${command.modeType ne 'C'}">
						<c:choose>
							<c:when test="${command.pensionSchmDto.lifeCertificateReq eq 'Y'}">
								<div class="col-sm-4">
									<form:checkbox path="pensionSchmDto.lifeCertificateReq"
										id="lifeCertificateReq" value="true" class="margin-left-50"
										disabled="${ command.modeType eq 'V'}" checked="checked" />
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-sm-4">
									<form:checkbox path="pensionSchmDto.lifeCertificateReq"
										id="lifeCertificateReq" value="true" class="margin-left-50"
										disabled="${ command.modeType eq 'V'}" />
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${command.modeType eq 'C'}">
						<div class="col-sm-4">
							<form:checkbox path="pensionSchmDto.lifeCertificateReq"
								id="lifeCertificateReq" value="true" class="margin-left-50" />
						</div>
					</c:if>
				</c:if>
			</div>

			<c:if test="${command.modeType eq 'C'}">
			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code="swm.fileupload" /> </label>
				<div class="col-sm-4">

					<apptags:formField fieldType="7" labelCode="" hasId="true"
						fieldPath="" isMandatory="false" showFileNameHTMLId="true"
						fileSize="CARE_COMMON_MAX_SIZE"
						maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CARE_VALIDATION_EXTENSION"
						currentCount="500" />
					<small class="text-blue-2"> <spring:message
							code="social.checklist.tooltip" text="(Upload Image File upto 1 MB)" />
				</div>
			</div>
			</c:if>
			<div class="text-center margin-top-15">
				<c:choose>
					<c:when test="${command.modeType eq 'E' || command.modeType eq 'R'}">
						<button type="button" class="btn btn-blue-2"
							onclick="updatePensionDetails(this)" title="Submit">
							<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i><spring:message code="social.btn.submit" text="Submit" />
						</button>
						<apptags:backButton url="PensionSchemeMaster.html"></apptags:backButton>
					</c:when>
					<c:otherwise>
						<c:if test="${command.modeType eq 'C'}">
							<button type="button" class="btn btn-blue-2" title="Submit"
								onclick="savePensionDetails(this)">
								<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i><spring:message code="social.btn.submit" text="Submit" /> 
							</button>
							<apptags:backButton url="PensionSchemeMaster.html"></apptags:backButton>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="margin-bottom-10">
				<c:if test="${command.modeType eq 'V'}">
					<apptags:backButton url="PensionSchemeMaster.html"></apptags:backButton>
				</c:if>
			</div>
			</form:form>
		</div>
	</div>
</div>
</div>
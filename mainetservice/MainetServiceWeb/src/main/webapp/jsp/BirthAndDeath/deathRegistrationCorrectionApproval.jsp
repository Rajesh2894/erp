<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/birthAndDeath/deathRegistrationCorrectionApproval.js"></script>
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
	.highlight-field {
	-moz-box-shadow: 0rem 0.2rem #ed4040;
	-webkit-box-shadow: 0rem 0.2rem #ed4040;
	box-shadow: 0rem 0.2rem #ed4040;
}
</style>


<html>

<div class="pagediv">

	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="TbDeathregDTO.form.deathRegCorrAproForm"
						text="Death Registration Correction Approval" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>

			<div class="widget-content padding">
				<form:form id="frmDeathRegCorrForm"
					action="DeathRegistrationCorrectionApproval.html" method="POST"
					class="form-horizontal" name="deathRegFormId">
					<form:hidden path="tbDeathregcorrDTO.corrCategory"
						cssClass="hasNumber form-control" id="corrCategory" />

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv">
					</div>
					
					<div class="form-group">
						<h3>
							<span style="float: left;" class="col-sm-4"><spring:message code="bnd.Reg.details" text="Registration Details" /></span>
							<span style="float: right;" class="col-sm-4"><spring:message code="bnd.Corr.details" text="Correction Details" /></span>
							
						</h3>
					</div>	
					
					<h4>
						<spring:message code="TbDeathregDTO.form.generalDetails"
							text="General Details" />
					</h4>
					
					<div class="form-group">
						<apptags:date fieldclass="datepicker" isDisabled="true"
							labelCode="TbDeathregDTO.drDod" cssClass="form-control mandColorClass DRDOD" datePath="tbDeathregDTO.drDod">
						</apptags:date>
						<apptags:date fieldclass="datepicker" isDisabled="true"
							labelCode="TbDeathregDTO.drDod"  cssClass="form-control mandColorClass DRDOD" datePath="tbDeathregcorrDTO.drDod">
						</apptags:date>
					</div>

				<div class="form-group">	
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:input labelCode="TbDeathregDTO.drSex" isDisabled="true"
							path="tbDeathregDTO.drSex" cssClass="hasNumClass form-control DRSEX" maxlegnth="190">
						</apptags:input>
							
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:input labelCode="TbDeathregDTO.drSex" isDisabled="true"
							path="tbDeathregcorrDTO.drSex" cssClass="hasNumClass form-control DRSEX" maxlegnth="190">
						</apptags:input>
					</div>
					
					<div class="form-group">
										<label for="text-1" class="col-sm-2 control-label "> <spring:message
												code="TbDeathregDTO.drDeceasedage" text="Deceased Age" /><span
											class="mand">*</span>
										</label>
										<div class="col-sm-2">
											<form:input path="tbDeathregDTO.drDeceasedage" id="age" disabled="true"
												cssClass="hasNumber form-control DRDECESEAGE" data-rule-required="true"
												maxlength="3" />
										</div>
				
										<div class="col-sm-2">
											<form:select path="tbDeathregDTO.cpdAgeperiodId" id="ageperiod" disabled="true"
												cssClass="form-control DRDECESEAGE" hasId="true" data-rule-required="true">
												<form:option value="">
													<spring:message code="TbDeathregDTO.form.select" text="Select" />
												</form:option>
												<c:set var="baseLookupCode" value="APG" />
				
												<c:forEach items="${command.getLevelData(baseLookupCode)}" 
													var="lookUp">
													<form:option value="${lookUp.lookUpId}" 
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
										
										<label for="text-1" class="col-sm-2 control-label "> <spring:message
												code="TbDeathregDTO.drDeceasedage" text="Deceased Age" /><span
											class="mand">*</span>
										</label>
										<div class="col-sm-2">
											<form:input path="tbDeathregcorrDTO.drDeceasedage" id="age" disabled="true"
												cssClass="hasNumber form-control DRDECESEAGE" data-rule-required="true"
												maxlength="3" />
										</div>
				
										<div class="col-sm-2">
											<form:select path="tbDeathregcorrDTO.cpdAgeperiodId" id="ageperiod" disabled="true"
												cssClass="form-control DRDECESEAGE" hasId="true" data-rule-required="true">
												<form:option value="">
													<spring:message code="TbDeathregDTO.form.select" text="Select" />
												</form:option>
												<c:set var="baseLookupCode" value="APG" />
				
												<c:forEach items="${command.getLevelData(baseLookupCode)}"  
													var="lookUp">
													<form:option value="${lookUp.lookUpId}" 
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
				
				</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDeceasedname" isDisabled="true"
							path="tbDeathregDTO.drDeceasedname" isMandatory="true"
							cssClass="hasNameClass form-control DRDECEASEDNAME" maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drDeceasedname" isDisabled="true"
							path="tbDeathregcorrDTO.drDeceasedname" isMandatory="true"
							cssClass="hasNameClass form-control DRDECEASEDNAME" maxlegnth="100">
						</apptags:input>
					</div>
					
					<div class="form-group">	
						<apptags:input labelCode="TbDeathregDTO.drMarDeceasedname" isDisabled="true"
							path="tbDeathregDTO.drMarDeceasedname" isMandatory="true"
							cssClass="hasNameClass form-control DRDECEASEDNAME" maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarDeceasedname"
							path="tbDeathregcorrDTO.drMarDeceasedname" isMandatory="true" isDisabled="true"
							cssClass="hasNameClass form-control DRDECEASEDNAME" maxlegnth="100">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drRelativeName" isDisabled="true"
							path="tbDeathregDTO.drRelativeName" isMandatory="true"
							cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drRelativeName" isDisabled="true"
							path="tbDeathregcorrDTO.drRelativeName" isMandatory="true"
							cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
						</apptags:input>
					</div>
		
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drMarRelativeName" isDisabled="true"
							path="tbDeathregDTO.drMarRelativeName" isMandatory="true"
							cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarRelativeName" isDisabled="true"
							path="tbDeathregcorrDTO.drMarRelativeName" isMandatory="true"
							cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
						</apptags:input>
					</div>
					
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drMotherName" isDisabled="true"
							path="tbDeathregDTO.drMotherName" isMandatory="true"
							cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMotherName" isDisabled="true"
							path="tbDeathregcorrDTO.drMotherName" isMandatory="true"
							cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
						</apptags:input>
					</div>
					
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drMarMotherName" isDisabled="true"
							path="tbDeathregDTO.drMarMotherName" isMandatory="true"
							cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarMotherName" isDisabled="true"
							path="tbDeathregcorrDTO.drMarMotherName" isMandatory="true"
							cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
						</apptags:input>
					</div>	
					
				
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr" isDisabled="true"
							path="tbDeathregDTO.drDeceasedaddr" isMandatory="true"
							cssClass="hasNumClass form-control ADDRESS" maxlegnth="190">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr" isDisabled="true"
							path="tbDeathregcorrDTO.drDeceasedaddr" isMandatory="true"
							cssClass="hasNumClass form-control ADDRESS" maxlegnth="190">
						</apptags:input>
					</div>
					
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr" isDisabled="true"
							path="tbDeathregDTO.drMarDeceasedaddr" isMandatory="true"
							cssClass="hasNumClass form-control ADDRESS" maxlegnth="190">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr" isDisabled="true"
							path="tbDeathregcorrDTO.drMarDeceasedaddr" isMandatory="true"
							cssClass="hasNumClass form-control ADDRESS" maxlegnth="190">
						</apptags:input>
					</div>
	
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDeathplace" isDisabled="true"
							path="tbDeathregDTO.drDeathplace" isMandatory="true"
							cssClass="hasNumClass form-control DRDEATHPLACE" maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drDeathplace" isDisabled="true"
							path="tbDeathregcorrDTO.drDeathplace" isMandatory="true"
							cssClass="hasNumClass form-control DRDEATHPLACE" maxlegnth="100">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
							isDisabled="true" path="tbDeathregDTO.drMarDeathplace"
							isMandatory="true" cssClass="hasNumClass form-control DRDEATHPLACE"
							maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
							isDisabled="true" path="tbDeathregcorrDTO.drMarDeathplace"
							isMandatory="true" cssClass="hasNumClass form-control DRDEATHPLACE"
							maxlegnth="100">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDeathaddr" isDisabled="true"
							path="tbDeathregDTO.drDeathaddr" isMandatory="true"
							cssClass="hasNumClass form-control DRDEATHPLACE" maxlegnth="190">
						</apptags:input>

						<apptags:input labelCode="TbDeathregDTO.drDeathaddr" isDisabled="true"
							path="tbDeathregcorrDTO.drDeathaddr" isMandatory="true"
							cssClass="hasNumClass form-control DRDEATHPLACE" maxlegnth="190">
						</apptags:input>

					</div>

					<h4>
						<spring:message code="TbDeathregDTO.form.informantDetails"
							text="Informant Details" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drInformantName" isDisabled="true"
							path="tbDeathregDTO.drInformantName" isMandatory="true"
							cssClass="hasNameClass form-control DRINFORMANTNAME" maxlegnth="100">
						</apptags:input>

						<apptags:input labelCode="TbDeathregDTO.drInformantName" isDisabled="true"
							path="tbDeathregcorrDTO.drInformantName" isMandatory="true"
							cssClass="hasNameClass form-control DRINFORMANTNAME" maxlegnth="100">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drInformantAddr" isDisabled="true"
							path="tbDeathregDTO.drInformantAddr" isMandatory="true"
							cssClass="hasNumClass form-control DRINFORMANTADDR" maxlegnth="190">
						</apptags:input>

						<apptags:input labelCode="TbDeathregDTO.drInformantAddr" isDisabled="true"
							path="tbDeathregcorrDTO.drInformantAddr" isMandatory="true"
							cssClass="hasNumClass form-control DRINFORMANTADDR" maxlegnth="190">
						</apptags:input>

					</div>

					<h4>
						<spring:message text="Others Details"
							code="TbDeathregDTO.form.othersdetails" />
					</h4>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdReligionId" text="Religion" />
						</label>
						<c:set var="baseLookupCode" value="RLG" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregDTO.cpdReligionId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdReligionId" text="Religion" />
						</label>
						<c:set var="baseLookupCode" value="RLG" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregcorrDTO.cpdReligionId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					<div class="form-group">

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdMaritalStatId" text="Marrital Status" />
						</label>
						<c:set var="baseLookupCode" value="MAR" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregDTO.cpdMaritalStatId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />


						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdMaritalStatId" text="Marrital Status" />
						</label>
						<c:set var="baseLookupCode" value="MAR" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregcorrDTO.cpdMaritalStatId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>



					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdOccupationId" text="Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregDTO.cpdOccupationId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />


						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdOccupationId" text="Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregcorrDTO.cpdOccupationId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>


					<h4>
						<spring:message text="Medical Certificate Details"
							code="TbDeathregDTO.form.medicalCertDetails" />
					</h4>


					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdDeathcauseId" text="Death Cause" />
						</label>
						<c:set var="baseLookupCode" value="DCA" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdDeathcauseId" cssClass="form-control" disabled="true"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdDeathcauseId" text="Death Cause" />
						</label>
						<c:set var="baseLookupCode" value="DCA" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregcorrDTO.cpdDeathcauseId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					<div class="form-group">
						<apptags:input labelCode="MedicalMasterDTO.mcOthercond" isDisabled="true"
							path="tbDeathregDTO.medicalMasterDto.mcOthercond"
							cssClass="hasNameClass form-control" maxlegnth="190">
						</apptags:input>

						<apptags:input labelCode="MedicalMasterDTO.mcOthercond" isDisabled="true"
							path="tbDeathregcorrDTO.mcOthercond"
							cssClass="hasNameClass form-control" maxlegnth="190">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="MedicalMasterDTO.mcMdSuprName" isDisabled="true"
							path="tbDeathregDTO.medicalMasterDto.mcMdSuprName"
							cssClass="hasNameClass form-control" maxlegnth="45">
						</apptags:input>

						<apptags:input labelCode="MedicalMasterDTO.mcMdSuprName" isDisabled="true"
							path="tbDeathregcorrDTO.mcMdSuprName"
							cssClass="hasNameClass form-control" maxlegnth="45">
						</apptags:input>

					</div>
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="applicant.wardName" text="WARD" />
						</label>
						<c:set var="baseLookupCode" value="BWD" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregDTO.wardid" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="applicant.wardName" text="WARD" />
						</label>
						<c:set var="baseLookupCode" value="BWD" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregcorrDTO.wardid" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

					</div>
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.form.remark" path="tbDeathregDTO.drRemarks" isDisabled="true"
							isMandatory="true" cssClass="hasNumClass form-control"
							maxlegnth="20">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.form.remark" path="tbDeathregcorrDTO.drRemarks" isDisabled="true"
							isMandatory="true" cssClass="hasNumClass form-control"
							maxlegnth="20">
						</apptags:input>
					</div>
					<div class="form-group">
					<apptags:date fieldclass="datepicker" isMandatory="true"  isDisabled="true"
							readonly="true" labelCode="TbDeathregDTO.drRegdate"
							datePath="tbDeathregDTO.drRegdate">
						</apptags:date>
					<apptags:input labelCode="TbDeathregDTO.drRegno"
							path="tbDeathregDTO.drRegno" isReadonly="true"
							cssClass="hasNumClass form-control" maxlegnth="20">
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
																	actionUrl="DeathRegistrationCorrectionApproval.html?Download">
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
					         <%--  <apptags:radio radioLabel="nac.approve,nac.reject" radioValue="APPROVED,REJECTED" isMandatory="true"
							  	labelCode="TbDeathregDTO.form.status" path="tbDeathregDTO.deathRegstatus" defaultCheckedValue="APPROVED" 
							  	changeHandler="ApplyChargeDisable()">
							  </apptags:radio> --%>
					          <br/>
					       
							   <apptags:textArea
								labelCode="TbDeathregDTO.form.remark" isMandatory="true"
								path="tbDeathregcorrDTO.corrAuthRemark" cssClass="hasNumClass form-control"
								maxlegnth="100" isDisabled="true"/>  <!-- tbDeathregcorrDTO -->
					</div>
					
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<apptags:CheckerAction hideForward="true" hideUpload="true"></apptags:CheckerAction>
					</div>
					
					
					<%-- <div class="form-group">
							  <apptags:textArea
								labelCode="bnd.final.remark" isMandatory="true"
								path="tbDeathregDTO.deathRegremark" cssClass="hasNumClass form-control"
								maxlegnth="100" />  <!-- tbDeathregcorrDTO -->
					</div> --%>
					
					<div class="text-center">
								<button type="button" class="btn btn-success" id="applCharge"
									onclick="displayCorrCharge(this);">
									<spring:message code="TbDeathregDTO.form.proceed"
										text="Proceed"></spring:message>
								</button>
								<button type="button" value="<spring:message code="bt.save"/>"
									style="display:none"
									class="btn btn-green-3" title="Submit" id="saveDeath"
									onclick="saveDeathRegCorrApprLOI(this)">
									<spring:message code="BirthRegDto.submit"
										text="Submit" />
								</button>

						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>

				</form:form>

			</div>

		</div>

	</div>
</div>


</html>






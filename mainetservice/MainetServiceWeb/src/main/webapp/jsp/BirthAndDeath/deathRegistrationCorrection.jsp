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
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript"
	src="js/birthAndDeath/deathRegistrationCorrection.js"></script>

<script>
	$(document).ready(function() {
		var list = document.getElementById('corrCategory');
		var listArray = new Array();
		if(list!=null){
		for (i = 0; i < list.options.length; i++) {
			listArray[i] = list.options[i].value;
		}
		
		$.each(listArray, function(index) {
			
			$("." + listArray[index]).attr("disabled", true);
		});
		}
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
<apptags:breadcrumb></apptags:breadcrumb>
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="TbDeathregDTO.deathRegCorrFrm"
						text="Death Registration Correction Form" />
				</h2>
				<!-- <div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div> -->
				<apptags:helpDoc url="DeathRegistrationCorrection.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">
				<form:form id="frmDeathRegCorrForm"
					action="DeathRegistrationCorrection.html" method="POST"
					class="form-horizontal" name="deathRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="tbDeathregDTO.drId"
						cssClass="hasNumber form-control" id="drId" />
					<form:hidden path="" id="kdmcEnv" value="${command.kdmcEnv}" />
				
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					
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
												path="tbDeathregDTO.requestDTO.titleId"
												cssClass="form-control chosen-select-no-results"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true"
												disabled="${command.saveMode eq 'V' ? true : false }" />

											<apptags:input labelCode="rti.firstName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="tbDeathregDTO.requestDTO.fName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">


											<apptags:input labelCode="rti.middleName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="tbDeathregDTO.requestDTO.mName" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

											<apptags:input labelCode="rti.lastName"
												cssClass="hasNameClass mandColorClass hasNoSpace"
												path="tbDeathregDTO.requestDTO.lName" isMandatory="true" maxlegnth="100"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>
										<div class="form-group">

											<label class="col-sm-2 control-label "><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField items="${command.getLevelData('GEN')}"
												path="tbDeathregDTO.requestDTO.gender"
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
											cssClass="hasNumClass mandColorClass " path="tbDeathregDTO.requestDTO.bldgName"
											maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										<apptags:input labelCode="rti.taluka"
											cssClass=" mandColorClass " path="tbDeathregDTO.requestDTO.blockName"
											 maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="applicant.roadName"
											cssClass="hasNumClass mandColorClass"
											path="tbDeathregDTO.requestDTO.roadName"  maxlegnth="100"
											isDisabled="${command.saveMode eq 'V' ? true : false }">
										</apptags:input>
									<c:if test="${command.kdmcEnv eq 'N'}">
										<label class="control-label col-sm-2 " for="text-1"><spring:message
												code="tbDeathregDTO.applicant.wardName" text="Ward Name" /></label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.requestDTO.wardNo"
											cssClass="form-control" hasId="true"
											selectOptionLabelCode="selectdropdown" />
									</c:if>
									<c:if test="${command.kdmcEnv eq 'Y'}">
										<label class="control-label col-sm-2 required-control"
											for="text-1"><spring:message code="ParentDetailDTO.pdRegUnitId"
												text="Registration Unit" /></label>
										<c:set var="baseLookupCode" value="REU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdRegUnit"
											cssClass="form-control" hasId="true"
											selectOptionLabelCode="selectdropdown" />
									</c:if>
								</div>

									<div class="form-group">
										<apptags:input labelCode="rti.Try5"
											cssClass="hasNameClass mandColorClass "
											path="tbDeathregDTO.requestDTO.cityName"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											isMandatory="true" maxlegnth="100"></apptags:input>
										<apptags:input labelCode="rti.pinCode"
											cssClass="hasPincode mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="tbDeathregDTO.requestDTO.pincodeNo" 
											maxlegnth="100"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="rti.mobile1"
											cssClass="hasMobileNo mandColorClass "
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="tbDeathregDTO.requestDTO.mobileNo" isMandatory="true" maxlegnth="10"></apptags:input>
										<apptags:input labelCode="chn.lEmail"
											cssClass="hasemailclass  mandColorClass hasNoSpace"
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											path="tbDeathregDTO.requestDTO.email" maxlegnth="100"></apptags:input>
									</div>
								</div>



							</div>
							
							
							<div class="panel panel-default" id="correct">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="bnd.correction.category" text="Correction Catagory" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="corrCategory"><spring:message
										code="bnd.correction.category" text="Correction Catagory" /></label>
										<div class="col-sm-4">
											<form:select path="tbDeathregDTO.corrCategory" multiple="true" onchange="correctionsCategory(this)" id="corrCategory"
												class="form-control chosen-select-no-results " label="Select">
												<c:set var="baseLookupCode" value="DCC" />
												<%-- <form:option value="">
													<spring:message code="solid.waste.select" text="select" />
												</form:option> --%>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpCode}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
								</div>

							</div>
						</div>
							</c:if>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#deathRegCor-1">
										<spring:message code="TbDeathregDTO.form.generalDetails" text="General Details" /></a>
								</h4>
							</div>
							<div id="deathRegCor-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:date fieldclass="datepicker"
											labelCode="TbDeathregDTO.drDod" datePath="tbDeathregDTO.drDod"
											cssClass="form-control mandColorClass DRDOD" isDisabled="true">
										</apptags:date>
				
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="TbDeathregDTO.drSex"
												text="Gender" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.drSex" cssClass="form-control DRSEX"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>
									
									<div class="form-group">
										<label for="text-1" class="col-sm-2 control-label "> <spring:message
												code="TbDeathregDTO.drDeceasedage" text="Deceased Age" /><span
											class="mand">*</span>
										</label>
										<div class="col-sm-2">
											<form:input path="tbDeathregDTO.drDeceasedage" id="age"
												cssClass="hasNumber form-control DRDECESEAGE" data-rule-required="true"
												maxlength="3" />
										</div>
				
										<div class="col-sm-2">
											<form:select path="tbDeathregDTO.cpdAgeperiodId" id="ageperiod"
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
										<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
											path="tbDeathregDTO.drDeceasedname" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control DRDECEASEDNAME" maxlegnth="100">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeceasedname"
											path="tbDeathregDTO.drMarDeceasedname" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control DRDECEASEDNAME" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drRelativeName"
											path="tbDeathregDTO.drRelativeName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
										</apptags:input>
				
										<apptags:input labelCode="TbDeathregDTO.drMarRelativeName"
											path="tbDeathregDTO.drMarRelativeName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drMotherName"
											path="tbDeathregDTO.drMotherName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarMotherName"
											path="tbDeathregDTO.drMarMotherName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control PARENTNAME" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr"
											path="tbDeathregDTO.drDeceasedaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control ADDRESS" maxlegnth="190">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr"
											path="tbDeathregDTO.drMarDeceasedaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control ADDRESS" maxlegnth="190">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeath"
											path="tbDeathregDTO.drDcaddrAtdeath" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control ADDRESS" maxlegnth="190">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeathMar"
											path="tbDeathregDTO.drDcaddrAtdeathMar" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control ADDRESS" maxlegnth="190">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeathplace"
											path="tbDeathregDTO.drDeathplace" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control DRDEATHPLACE" maxlegnth="100">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
											path="tbDeathregDTO.drMarDeathplace" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control DRDEATHPLACE" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
									<apptags:input labelCode="TbDeathregDTO.drDeathaddr"
											path="tbDeathregDTO.drDeathaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control DRDEATHADDR" maxlegnth="190" >
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeathaddr"
											path="tbDeathregDTO.drMarDeathaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control DRDEATHADDR" maxlegnth="190" >
										</apptags:input>
										
								   </div>
								</div>
							</div>
						</div>
						
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#deathReg-2">
										<spring:message code="TbDeathregDTO.form.informantDetails" text="Informant Details" /></a>
								</h4>
							</div>
							<div id="deathReg-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drInformantName"
											path="tbDeathregDTO.drInformantName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control DRINFORMANTNAME" maxlegnth="100">
										</apptags:input>
						
										<apptags:input labelCode="TbDeathregDTO.drInformantAddr"
											path="tbDeathregDTO.drInformantAddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control DRINFORMANTADDR" maxlegnth="190">
										</apptags:input>
										
									</div>
									
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#deathReg-3">
										<spring:message code="TbDeathregDTO.form.othersdetails" text="Others Details" /></a>
								</h4>
							</div>
							<div id="deathReg-3" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdReligionId" text="Religion" />
										</label>
										<c:set var="baseLookupCode" value="RLG" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdReligionId" cssClass="form-control CPDRELIGIONID"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									
										<label class="control-label col-sm-2 "
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdMaritalStatId" text="Marrital Status" />
										</label>
										<c:set var="baseLookupCode" value="MAR" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdMaritalStatId" cssClass="form-control CPDMARITALSTATID"
											isMandatory="" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
				</div>
				
				
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdOccupationId" text="Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdOccupationId" cssClass="form-control CPDOCCUPATIONID"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>
								</div>
							</div>
						</div>
						
					
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#deathReg-4">
										<spring:message code="TbDeathregDTO.form.medicalCertDetails" text="Medical Certificate Details" /></a>
								</h4>
							</div>
							<div id="deathReg-4" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdDeathcauseId" text="Death Cause" />
										</label>
										<c:set var="baseLookupCode" value="DCA" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdDeathcauseId" cssClass="form-control CPDDEATHCAUSEID"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
				
										<apptags:input labelCode="MedicalMasterDTO.mcOthercond"
											path="tbDeathregDTO.medicalMasterDto.mcOthercond" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control MCOTHERCOND" maxlegnth="190">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="MedicalMasterDTO.mcMdSuprName"
											path="tbDeathregDTO.medicalMasterDto.mcMdSuprName"
											 cssClass="hasNumClass form-control MCMDSUPRNAME" isDisabled="${command.saveMode eq 'V'}"
											maxlegnth="100">
										</apptags:input>
										
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="applicant.wardName" text="WARD" />
										</label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.wardid"
											cssClass="form-control WARDID"  hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
										
									</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker" isMandatory="true" isDisabled="true"
										readonly="true" labelCode="TbDeathregDTO.drRegdate" 
										datePath="tbDeathregDTO.drRegdate">
									</apptags:date>
									<apptags:input labelCode="TbDeathregDTO.drRegno"
										path="tbDeathregDTO.drRegno" isReadonly="true" isDisabled="${command.saveMode eq 'V'}"
										cssClass="hasNumClass form-control DRREGNO" maxlegnth="20">
									</apptags:input>
									
								</div>
								<!--  Defect #133946 -->
								<%-- <div class="form-group">
									<apptags:input labelCode="TbDeathregDTO.form.remark"
										path="tbDeathregDTO.drRemarks" isMandatory="" isDisabled="${command.saveMode eq 'V'}"
										cssClass="hasNumClass form-control DRREMARKS" maxlegnth="20">
									</apptags:input>
								</div> --%>
							</div>
							</div>
						</div>
				
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#deathRegCor-2">
										<spring:message text="Certificate print details"  code="Issuaance.death.certificate.print"/></a>
								</h4>
							</div>
							<div id="deathRegCor-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.alreayIssuedCopy"
											path="tbDeathregDTO.alreayIssuedCopy"
											cssClass="hasNumClass form-control"
											isReadonly="true">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.numberOfCopies" isDisabled="${command.saveMode eq 'V'}"
											path="tbDeathregDTO.numberOfCopies" maxlegnth="2"
											cssClass="hasNumber form-control" isMandatory="true">
										</apptags:input>
									</div>
								
								</div>
							</div>
						</div>
					</div>
					<c:if test="${command.tbDeathregDTO.checkStatus  eq 'A'}">
					<div class="text-center">
					<div class="padding-top-10 text-center" id="chekListChargeId">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-submit" data-toggle="tooltip" data-original-title="Proceed" id="proceedId"
							onclick="getChecklistAndCharges(this)">
							<spring:message code="TbDeathregDTO.form.proceed" text="Proceed"/>
						</button>
					 </c:if>
					 <c:if test="${command.saveMode ne 'V'}">
						<button type="button" onclick="resetMemberMaster(this);"
										class="btn btn-warning" data-toggle="tooltip" data-original-title="Reset" id="resetId">
										<spring:message code="TbDeathregDTO.form.reset" text="Reset" />
									</button>
						</c:if>
									
						<button type="button" class="btn btn-danger " id="backId"
							 data-toggle="tooltip" data-original-title="Back"
							onclick="window.location.href ='DeathRegistrationCorrection.html'">
							<spring:message code="TbDeathregDTO.form.backbutton" text="Back" />
						</button>
					</div>
					</div>

					<c:if test="${not empty command.checkList}">

						<h4>
							<spring:message code="TbDeathregDTO.form.uploadDocuments" text="Upload Documents" />
						</h4>
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
															code="bnd.documentName" text="Document Type" /></label></th>
												<th><label class="tbold"><spring:message
															code="bnd.documentDesc" text="Document Description" /></label></th>
												<th><label class="tbold"><spring:message
															code="label.checklist.status" text="Status" /></label></th>
												<th><label class="tbold"><spring:message
															code="bnd.upload.document" text="Upload" /></label></th>
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
													<%-- <td><form:input	path="checkList[${lk.index}].docDescription"
														type="text"	class="form-control alphaNumeric "
														maxLength="50" id="docDescription[${lk.index}]" data-rule-required="true" />
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
																validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC"
																checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																checkListDesc="${docName}" currentCount="${lk.index}" />
														</div> <small class="text-blue-2"> <spring:message
																code="bnd.checklist.tooltip"
																text="(Upload Image File upto 1 MB)" />
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
						
					<form:hidden path="tbDeathregDTO.chargeStatus" id="chargeStatus"/>
						 <c:if test="${command.tbDeathregDTO.checkStatus eq 'NA' || not empty command.checkList || command.tbDeathregDTO.chargeStatus eq 'CA'}">
						<div class="text-center">
						<c:if test="${command.saveMode ne 'V'}">
							<button type="button" align="center" value="<spring:message code="bt.save"/>"
								class="btn btn-green-3" data-toggle="tooltip" data-original-title="Submit"
								onclick="saveDeathCorrData(this)">
								<spring:message code="TbDeathregDTO.form.savebutton" text="Save" />
							</button>
							</c:if>
							<c:if test="${command.saveMode ne 'V'}">
						   <button type="button" align="center" onclick="resetMemberMaster(this);"
								class="btn btn-warning" data-toggle="tooltip" data-original-title="Reset" id="resetId">
								<spring:message code="TbDeathregDTO.form.reset" text="Reset" />
					       </button>
						   </c:if>
							<button type="button" align="center" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href ='DeathRegistrationCorrection.html'">
								<spring:message code="TbDeathregDTO.form.backbutton" text="Back" />
							</button>
						</div>
						</c:if>
				</form:form>

			</div>

		</div>

	</div>
</div>


</html>
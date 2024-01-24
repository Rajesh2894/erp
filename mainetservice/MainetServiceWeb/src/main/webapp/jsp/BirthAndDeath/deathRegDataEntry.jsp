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
<script type="text/javascript" src="js/cfc/challan/offlinePay.js"></script>
<script type="text/javascript" src="js/birthAndDeath/dataEntryForDeathReg.js"></script>
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


$('[data-toggle="tooltip"]').tooltip({
    trigger : 'hover'
})
</script>

<style>
input[type=radio], input[type=checkbox] {
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

<div class="pagediv">

	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="dataentry.deathRegForm" text="Death Registration Data Entry Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>

			<div class="widget-content padding">

				<form:form id="frmDeathRegistrationForm"
					action="dataEntryForDeathReg.html" method="POST"
					class="form-horizontal" name="deathRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="tbDeathregDTO.drId"
						cssClass="hasNumber form-control" id="drId" />
					<form:hidden path="tbDeathregDTO.statusFlag"
						cssClass="hasNumber form-control" id="status" />

					<form:hidden path="tbDeathregDTO.drDraftId"
						cssClass="hasNumber form-control" id="drDraftId" />
						
						<form:hidden path="tbDeathregDTO.apmApplicationId"
						cssClass="hasNumber form-control" id="apmApplicationId" />
					
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					
					
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#deathReg-1">
										<spring:message code="TbDeathregDTO.form.generalDetails" text="General Details" /></a>
								</h4>
							</div>
							<div id="deathReg-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
                                        <apptags:date fieldclass="datepicker" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											labelCode="TbDeathregDTO.drDod" datePath="tbDeathregDTO.drDod">
										</apptags:date>
				
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="TbDeathregDTO.drSex"
												text="Gender" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.drSex" cssClass="form-control"
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
												cssClass="hasNumber form-control" data-rule-required="true" disabled="${command.saveMode eq 'V' ? true : false }"
												maxlength="3" />
										</div>
				
										<div class="col-sm-2">
											<form:select path="tbDeathregDTO.cpdAgeperiodId" id="ageperiod" disabled="${command.saveMode eq 'V' ? true : false }"
												cssClass="form-control" hasId="true" data-rule-required="true">
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
				
				
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdNationalityId" text="Nationality" />
										</label>
										<c:set var="baseLookupCode" value="NAT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdNationalityId" cssClass="form-control"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
											path="tbDeathregDTO.drDeceasedname" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeceasedname"
											path="tbDeathregDTO.drMarDeceasedname" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drRelativeName"
											path="tbDeathregDTO.drRelativeName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
				
										<apptags:input labelCode="TbDeathregDTO.drMarRelativeName"
											path="tbDeathregDTO.drMarRelativeName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drMotherName"
											path="tbDeathregDTO.drMotherName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarMotherName"
											path="tbDeathregDTO.drMarMotherName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr"
											path="tbDeathregDTO.drDeceasedaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr"
											path="tbDeathregDTO.drMarDeceasedaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeath"
											path="tbDeathregDTO.drDcaddrAtdeath" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeathMar"
											path="tbDeathregDTO.drDcaddrAtdeathMar" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdDeathplaceType" text="Death Place Type" />
										</label>
										<c:set var="baseLookupCode" value="DPT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											changeHandler="selecthosp(this)"
											path="tbDeathregDTO.cpdDeathplaceType" cssClass="form-control"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
				
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="TbDeathregDTO.hiId"
												text="Hospital Name" />
										</label>

										<div class="col-sm-4">
											<form:select path="tbDeathregDTO.hiId"
												cssClass="form-control" id="hospitalList">
												<form:option value="">
													<spring:message code="TbDeathregDTO.form.select"
														text="Select" />
												</form:option>
												<c:forEach items="${command.hospitalMasterDTOList}"
													var="hospList">
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">
															<form:option value="${hospList.hiId}">${hospList.hiName}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${hospList.hiId}">${hospList.hiNameMar}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</div>

									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeathplace"
											path="tbDeathregDTO.drDeathplace" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="100">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
											path="tbDeathregDTO.drMarDeathplace" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="100">
										</apptags:input>
									</div>
				
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drDeathaddr"
											path="tbDeathregDTO.drDeathaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarDeathaddr"
											path="tbDeathregDTO.drMarDeathaddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
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
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
				
										<apptags:input labelCode="TbDeathregDTO.drMarInformantName"
											path="tbDeathregDTO.drMarInformantName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drInformantAddr"
											path="tbDeathregDTO.drInformantAddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.drMarInformantAddr"
											path="tbDeathregDTO.drMarInformantAddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
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
											path="tbDeathregDTO.cpdReligionId" cssClass="form-control"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
				
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdEducationId" text="Education" />
										</label>
										<c:set var="baseLookupCode" value="EDU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdEducationId" cssClass="form-control"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>
				
				
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdMaritalStatId" text="Marrital Status" />
										</label>
										<c:set var="baseLookupCode" value="MAR" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdMaritalStatId" cssClass="form-control"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
				
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdOccupationId" text="Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdOccupationId" cssClass="form-control"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>
				
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdRegUnit" text="Registration Unit" />
										</label>
										<c:set var="baseLookupCode" value="REU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdRegUnit" cssClass="form-control"
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
				
				
										<label class="control-label col-sm-2 required-control"
											for="Census"><spring:message
												code="TbDeathregDTO.cpdAttntypeId" text="Attention Type" /> </label>
										<c:set var="baseLookupCode" value="ATD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdAttntypeId" cssClass="form-control"
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
										<spring:message code="TbDeathregDTO.form.cemeteryDetails" text="Cemetery Details" /></a>
								</h4>
							</div>
							
							<div id="deathReg-4" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-sm-2">
											<label class="col-sm-6 padding-left-0 padding-right-0" for="ceFlag"> <form:radiobutton
													path="tbDeathregDTO.ceFlag" value="W" id="within1" disabled="${command.saveMode eq 'V' ? true : false }"
													onclick="disFields(this)" /> <spring:message
													code="TbDeathregDTO.form.withinulb" text="Within ULB" />
											</label> <label class="col-sm-6 padding-left-0 padding-right-0" for="ceFlag"> <form:radiobutton
													path="tbDeathregDTO.ceFlag" value="O" id="within2" disabled="${command.saveMode eq 'V' ? true : false }"
													onclick="enaFields(this)" /> <spring:message
													code="TbDeathregDTO.form.outsideulb" text="Outside ULB" />
											</label>
				
										</div>
				
				
										<label
											class="control-label col-sm-offset-4 col-sm-2 required-control"
											for="Census" onchange="getCemetery(this)"><spring:message
												code="TbDeathregDTO.form.selectCemetery" text="Select cemetery" />
										</label>
										<div class="col-sm-4">
											<form:select path="tbDeathregDTO.ceId" cssClass="form-control"
												id="ceId" onchange="getCemetery(this)" disabled="${command.saveMode eq 'V' ? true : false }">
												<form:option value="">
													<spring:message code="TbDeathregDTO.form.select" text="Select" />
												</form:option>
												<c:forEach items="${command.cemeteryMasterDTOList}"
													var="cemeList">
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">
															<form:option value="${cemeList.ceId}">${cemeList.ceName}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${cemeList.ceId}">${cemeList.ceNameMar}</form:option>
														</c:otherwise>
													</c:choose>
													
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.ceName"
											path="tbDeathregDTO.ceName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.ceNameMar"
											path="tbDeathregDTO.ceNameMar" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="100">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.ceAddr"
											path="tbDeathregDTO.ceAddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
										</apptags:input>
										<apptags:input labelCode="TbDeathregDTO.ceAddrMar"
											path="tbDeathregDTO.ceAddrMar" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="190">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#deathReg-5">
										<spring:message code="TbDeathregDTO.form.medicalCertDetails" text="Medical Certificate Details" /></a>
								</h4>
							</div>
							<div id="deathReg-5" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="TbDeathregDTO.cpdDeathcauseId" text="Death Cause" />
										</label>
										<c:set var="baseLookupCode" value="DCA" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.cpdDeathcauseId" cssClass="form-control"
											isMandatory="true" hasId="true"  disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
				
										<apptags:input labelCode="MedicalMasterDTO.mcOthercond"
											path="tbDeathregDTO.medicalMasterDto.mcOthercond" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="190">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="MedicalMasterDTO.mcDeathManner" text="Death Manner" />
										</label>
										<c:set var="baseLookupCode" value="BDM" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.medicalMasterDto.mcDeathManner" disabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
				
										<apptags:input labelCode="MedicalMasterDTO.mcMdattndName"
											path="tbDeathregDTO.medicalMasterDto.mcMdattndName" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="45">
										</apptags:input>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="MedicalMasterDTO.mcMdSuprName"
											path="tbDeathregDTO.medicalMasterDto.mcMdSuprName" isDisabled="${command.saveMode eq 'V'}"
											 cssClass="hasNameClass form-control"
											maxlegnth="45">
										</apptags:input>
				
										<apptags:date fieldclass="datepicker"
											labelCode="MedicalMasterDTO.mcVerifnDate" readonly="true" isDisabled="${command.saveMode eq 'V'}"
											datePath="tbDeathregDTO.medicalMasterDto.mcVerifnDate">
										</apptags:date>
									</div>
				
									<div class="form-group">
										<apptags:input labelCode="MedicalMasterDTO.mcInteronset"
											path="tbDeathregDTO.medicalMasterDto.mcInteronset" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumber form-control" maxlegnth="10">
										</apptags:input>
										
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="applicant.wardName" text="WARD" />
										</label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tbDeathregDTO.wardid" disabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
										
									</div>
									<div class="form-group">
										<apptags:input labelCode="TbDeathregDTO.drRegno" isDisabled="${command.saveMode eq 'V'}"
											path="tbDeathregDTO.drRegno" isMandatory="true"
											cssClass="hasNumClass form-control" maxlegnth="20">
										</apptags:input>
										<apptags:date fieldclass="datepicker" isMandatory="true"  isDisabled="${command.saveMode eq 'V'}"
											labelCode="TbDeathregDTO.drRegdate" datePath="tbDeathregDTO.drRegdate">
										</apptags:date>
									</div>
				
									<div class="form-group">
				
										<div class="col-sm-2 ckeck-box">
											<form:checkbox id="medCert" disabled="${command.saveMode eq 'V' ? true : false }"
												path="tbDeathregDTO.medicalMasterDto.medCert" value="Y"
												onclick="toggleMedicalCert(this)" />
											<spring:message code="MedicalMasterDTO.medCert"
												text="Medically certified" />
										</div>
										<div class="col-sm-4">
											<div class="col-sm-6 padding-left-0 ckeck-box">
												<form:checkbox id="mcPregnAssoc" disabled="${command.saveMode eq 'V' ? true : false }"
													path="tbDeathregDTO.medicalMasterDto.mcPregnAssoc" value="Y"
													onclick="togglePregnAssoc(this)" />
												<spring:message code="MedicalMasterDTO.mcPregnAssoc"
													text="pregnacy Related" />
											</div>
											<div class="col-sm-6 ckeck-box">
												<form:checkbox id="decChewtb" disabled="${command.saveMode eq 'V' ? true : false }"
													path="tbDeathregDTO.deceasedMasterDTO.decChewtb" value="Y"
													onclick="toggleChewtb(this)" />
												<spring:message code="DeceasedMasterDTO.decChewtb"
													text="Used to Chew tobacco" />
											</div>
										</div>
										<div class="col-sm-2 ckeck-box">
											<form:checkbox id="decAlcoholic" disabled="${command.saveMode eq 'V' ? true : false }"
												path="tbDeathregDTO.deceasedMasterDTO.decAlcoholic" value="Y"
												onclick="toggleAlcoholic(this)" />
											<spring:message code="DeceasedMasterDTO.decAlcoholic"
												text="Was an alcoholic" />
										</div>
										<div class="col-sm-4">
											<div class="col-sm-6 padding-left-0 ckeck-box">
												<form:checkbox id="decSmoker" disabled="${command.saveMode eq 'V' ? true : false }"
													path="tbDeathregDTO.deceasedMasterDTO.decSmoker" value="Y"
													onclick="toggleSmoker(this)" />
												<spring:message code="DeceasedMasterDTO.decSmoker"
													text="Used to smoke" />
											</div>
											<div class="col-sm-6 ckeck-box">
												<form:checkbox id="decChewarac" disabled="${command.saveMode eq 'V' ? true : false }"
													path="tbDeathregDTO.deceasedMasterDTO.decChewarac" value="Y"
													onclick="toggleChewarac(this)" />
												<spring:message code="DeceasedMasterDTO.decChewarac"
													text="Used to Chew Betel nut" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<form:hidden path="tbDeathregDTO.chargeStatus" id="chargeStatus"/>
						<div class="text-center padding-top-10">
							<c:if test="${command.saveMode ne 'V'}">
							<button type="button" align="center" value="<spring:message code="bt.save"/>"
								class="btn btn-green-3" data-toggle="tooltip" data-original-title="Save"
								onclick="saveDeathData(this)">
								<spring:message code="TbDeathregDTO.form.savebutton" text="Save" />
							</button>
							<button type="button" align="center" onclick="resetMemberMaster(this);"
								class="btn btn-warning" data-toggle="tooltip" data-original-title="Reset">
								<spring:message code="TbDeathregDTO.form.reset" text="Reset" />
							</button>
							</c:if>
							<button type="button" align="center" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href ='dataEntryForDeathReg.html'">
								<spring:message code="TbDeathregDTO.form.backbutton" text="Back" />
							</button>
						</div>

				</form:form>

			</div>

		</div>

	</div>
</div>

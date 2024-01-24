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
<script type="text/javascript"
	src="js/birthAndDeath/dataEntryForDeathRegApproval.js"></script>
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
</style>


<div class="pagediv">

	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="dataentry.deathRegForm"
						text="Death Registration Data Entry Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>

			<div class="widget-content padding">

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<form:form id="frmDeathRegistrationForm"
					action="DataEntryDeathRegApproval.html" method="POST"
					class="form-horizontal" name="deathRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />


					<h4>
						<spring:message code="TbDeathregDTO.form.generalDetails"
							text="General Details" />
					</h4>

					<div class="form-group">
					<apptags:date fieldclass="datepicker" isMandatory="true" readonly="true" isDisabled="true"
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
											isMandatory="true" hasId="true" disabled="true"
											selectOptionLabelCode="selectdropdown" />
					</div>

					<div class="form-group">
						<label for="text-1" class="col-sm-2 control-label "> <spring:message
								code="TbDeathregDTO.drDeceasedage" text="Deceased Age" /><span
							class="mand">*</span>
						</label>
						<div class="col-sm-2">
							<form:input path="tbDeathregDTO.drDeceasedage"
								cssClass="hasNumber form-control" data-rule-required="true"
								maxlength="6" disabled="true" />
						</div>

						<div class="col-sm-2">
							<form:select path="tbDeathregDTO.cpdAgeperiodId"
								cssClass="form-control" hasId="true" data-rule-required="true"
								disabled="true">
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
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
							path="tbDeathregDTO.drDeceasedname" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarDeceasedname"
							path="tbDeathregDTO.drMarDeceasedname" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drRelativeName"
							path="tbDeathregDTO.drRelativeName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>

						<apptags:input labelCode="TbDeathregDTO.drMarRelativeName"
							path="tbDeathregDTO.drMarRelativeName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drMotherName"
							path="tbDeathregDTO.drMotherName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarMotherName"
							path="tbDeathregDTO.drMarMotherName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDeceasedaddr"
							path="tbDeathregDTO.drDeceasedaddr" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarDeceasedaddr"
							path="tbDeathregDTO.drMarDeceasedaddr" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeath"
							path="tbDeathregDTO.drDcaddrAtdeath" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drDcaddrAtdeathMar"
							path="tbDeathregDTO.drDcaddrAtdeathMar" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
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
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="TbDeathregDTO.hiId"
								text="Hospital Name" />
						</label>

						<div class="col-sm-4">
							<form:select path="tbDeathregDTO.hiId" cssClass="form-control"
								id="hospitalList" disabled="true">
								<form:option value="">
									<spring:message code="Select" text="Select" />
								</form:option>
								<c:forEach items="${hospitalList}" var="hospList">
									<form:option value="${hospList.hiId}">${hospList.hiName}</form:option>
								</c:forEach>
							</form:select>
						</div>

					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDeathplace"
							path="tbDeathregDTO.drDeathplace" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarDeathplace"
							path="tbDeathregDTO.drMarDeathplace" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>
					</div>


					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drDeathaddr"
							path="tbDeathregDTO.drDeathaddr" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarDeathaddr"
							path="tbDeathregDTO.drMarDeathaddr" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
						</apptags:input>
					</div>

					<h4>
						<spring:message code="TbDeathregDTO.form.informantDetails"
							text="Informant Details" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drInformantName"
							path="tbDeathregDTO.drInformantName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>

						<apptags:input labelCode="TbDeathregDTO.drMarInformantName"
							path="tbDeathregDTO.drMarInformantName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drInformantAddr"
							path="tbDeathregDTO.drInformantAddr" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="TbDeathregDTO.drMarInformantAddr"
							path="tbDeathregDTO.drMarInformantAddr" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
						</apptags:input>
					</div>

					<h4>
						<spring:message code="TbDeathregDTO.form.othersdetails"
							text="Others Details" />
					</h4>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdReligionId" text="Religion" />
						</label>
						<c:set var="baseLookupCode" value="RLG" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdReligionId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdEducationId" text="Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdEducationId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />
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
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdOccupationId" text="Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdOccupationId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdRegUnit" text="Reg Unit" />
						</label>
						<c:set var="baseLookupCode" value="REU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdRegUnit" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />


						<label class="control-label col-sm-2 required-control"
							for="Census"><spring:message
								code="TbDeathregDTO.cpdAttntypeId" text="Attention Type" /> </label>
						<c:set var="baseLookupCode" value="ATD" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdAttntypeId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />
					</div>


					<h4>
						<spring:message code="TbDeathregDTO.form.cemeteryDetails"
							text="Cemetery Details" />
					</h4>

					<div class="form-group">
						<div class="col-sm-2">
							<label class="col-sm-6" for="ceFlag"> <form:radiobutton
									path="tbDeathregDTO.ceFlag" value="W" id="within1"
									onclick="disFields(this)" disabled="true" /> <spring:message
									code="TbDeathregDTO.form.withinulb" text="Within ULB" />
							</label> <label class="col-sm-6" for="ceFlag"> <form:radiobutton
									path="tbDeathregDTO.ceFlag" value="O" id="within2"
									onclick="enaFields(this)" disabled="true" /> <spring:message
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
								id="cemeteryList" data-rule-required="true" disabled="true">
								<form:option value="">
									<spring:message code="TbDeathregDTO.form.select" text="Select" />
								</form:option>
								<c:forEach items="${cemeteryList}" var="cemeList">
									<form:option value="${cemeList.ceId}">${cemeList.ceName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.ceName"
							path="tbDeathregDTO.ceName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="100"
							isReadonly="true">
						</apptags:input>

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.ceNameMar" />
						</label>
						<div class="col-sm-4">
							<input class="form-control" value="${command.cemeteryNameMar}"
								readonly="readonly"></input>
						</div>
					</div>

					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.ceAddr"
							path="tbDeathregDTO.ceAddr" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="190"
							isReadonly="true">
						</apptags:input>

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.ceAddrMar" />
						</label>
						<div class="col-sm-4">
							<input class="form-control" value="${command.cemeteryAddressMar}"
								readonly="readonly"></input>
						</div>
					</div>

					<h4>
						<spring:message code="TbDeathregDTO.form.medicalCertDetails"
							text="Medical Certificate Details" />
					</h4>
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdDeathcauseId" text="Death Cause" />
						</label>
						<c:set var="baseLookupCode" value="DCA" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdDeathcauseId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />

						<apptags:input labelCode="MedicalMasterDTO.mcOthercond"
							path="tbDeathregDTO.medicalMasterDto.mcOthercond"
							cssClass="hasNameClass form-control" maxlegnth="190"
							isReadonly="true">
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
							path="tbDeathregDTO.medicalMasterDto.mcDeathManner"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true" />

						<apptags:input labelCode="MedicalMasterDTO.mcMdattndName"
							path="tbDeathregDTO.medicalMasterDto.mcMdattndName"
							cssClass="hasNameClass form-control" maxlegnth="45"
							isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="MedicalMasterDTO.mcMdSuprName"
							path="tbDeathregDTO.medicalMasterDto.mcMdSuprName"
							cssClass="hasNameClass form-control" maxlegnth="45"
							isReadonly="true">
						</apptags:input>

						<apptags:date fieldclass="datepicker"
							labelCode="MedicalMasterDTO.mcVerifnDate" isDisabled="true"
							datePath="tbDeathregDTO.medicalMasterDto.mcVerifnDate"
							readonly="true">
						</apptags:date>
					</div>

					<div class="form-group">
						<apptags:input labelCode="MedicalMasterDTO.mcInteronset"
							path="tbDeathregDTO.medicalMasterDto.mcInteronset"
							cssClass="hasNumber form-control" maxlegnth="10"
							isReadonly="true">
						</apptags:input>

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="applicant.wardName" text="WARD" />
						</label>
						<c:set var="baseLookupCode" value="BWD" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbDeathregDTO.wardid" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

					</div>
					<div class="form-group">
						<apptags:input labelCode="TbDeathregDTO.drRegno"
							path="tbDeathregDTO.drRegno" isReadonly="true"
							cssClass="hasNumClass form-control" maxlegnth="20">
						</apptags:input>
						<apptags:date fieldclass="datepicker" isMandatory="false"
							labelCode="TbDeathregDTO.drRegdate"
							datePath="tbDeathregDTO.drRegdate" isDisabled="true">
						</apptags:date>
					</div>

					<%-- <div class="form-group">
						<apptags:input labelCode="Remark" isDisabled="true"
							path="tbDeathregDTO.drRemarks" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="20">
						</apptags:input>
					</div> --%>
					<div class="form-group">

						<div class="col-sm-2 ckeck-box">
							<form:checkbox path="tbDeathregDTO.medicalMasterDto.medCert"
								value="Y" disabled="true" />
							<spring:message code="MedicalMasterDTO.medCert"
								text="Medically certified" />
						</div>
						<div class="col-sm-4">
							<div class="col-sm-6 padding-left-0 ckeck-box">
								<form:checkbox
									path="tbDeathregDTO.medicalMasterDto.mcPregnAssoc" value="Y"
									disabled="true" />
								<spring:message code="MedicalMasterDTO.mcPregnAssoc"
									text="pregnacy Related" />
							</div>
							<div class="col-sm-6 ckeck-box">
								<form:checkbox path="tbDeathregDTO.deceasedMasterDTO.decChewtb"
									value="Y" disabled="true" />
								<spring:message code="DeceasedMasterDTO.decChewtb"
									text="Used to Chew tobacco" />
							</div>
						</div>
						<div class="col-sm-2 ckeck-box">
							<form:checkbox
								path="tbDeathregDTO.deceasedMasterDTO.decAlcoholic" value="Y"
								disabled="true" />
							<spring:message code="DeceasedMasterDTO.decAlcoholic"
								text="Was an alcoholic" />
						</div>
						<div class="col-sm-4">
							<div class="col-sm-6 padding-left-0 ckeck-box">
								<form:checkbox path="tbDeathregDTO.deceasedMasterDTO.decSmoker"
									value="Y" disabled="true" />
								<spring:message code="DeceasedMasterDTO.decSmoker"
									text="Used to smoke" />
							</div>
							<div class="col-sm-6 ckeck-box">
								<form:checkbox
									path="tbDeathregDTO.deceasedMasterDTO.decChewarac" value="Y"
									disabled="true" />
								<spring:message code="DeceasedMasterDTO.decChewarac"
									text="Used to Chew Betel nut" />
							</div>
						</div>
					</div>
					<br>
					<br>
					<br>

					<div class="form-group">
						<apptags:radio radioLabel="nac.approve,nac.reject"
							radioValue="APPROVED,REJECTED" isMandatory="true"
							labelCode="nac.status" path="tbDeathregDTO.deathRegstatus"
							defaultCheckedValue="APPROVED">
						</apptags:radio>
						<br />
						<%-- <apptags:textArea labelCode="TbDeathregDTO.form.remark"
							isMandatory="true" path="tbDeathregDTO.deathRegremark"
							cssClass="hasNumClass form-control" maxlegnth="100" /> --%>
					</div>


					<div class="text-center">
						<button type="button" value="<spring:message code="bt.save"/>"
							class="btn btn-green-3" title="Submit"
							onclick="saveDeathApprovalData(this)">
							<spring:message code="TbDeathregDTO.form.savebutton" text="Save" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>

				</form:form>

			</div>

		</div>

	</div>
</div>

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

<script type="text/javascript" src="js/birthAndDeath/birthCorrectionApproval.js"></script>

<style>
.highlight-field {
	-moz-box-shadow: 0rem 0.2rem #ed4040;
	-webkit-box-shadow: 0rem 0.2rem #ed4040;
	box-shadow: 0rem 0.2rem #ed4040;
}
</style>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="BirthRegDto.BrcFrm" text="Birth Correction Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding" id="ashish">
				<form:form id="frmBirthRegistrationCorrectionApproval"
					action="BirthRegistrationCorrectionApproval.html" method="POST"
					class="form-horizontal" name="BirthRegistrationCorrectionApprovalId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="tbBirthregcorrDTO.corrCategory"
						cssClass="hasNumber form-control" id="corrCategory" />
					

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv">
					</div>
					
					<div class="form-group">
						<h3>
							<span style="float: left;" class="col-sm-4"><spring:message code="bnd.Reg.details" text="Registration Details" /></span>
							<span style="float: right;" class="col-sm-4"><spring:message code="bnd.Corr.details" text="Correction Details" /></span>
							
						</h3><br>
					</div>	

					<h4>
						<spring:message code="TbDeathregDTO.form.generalDetails"
						text="General Details" />
					</h4>

					<%-- <div class="form-group">
						<apptags:input labelCode="Certificate No."
							path="birthRegDto.brCertNo" isMandatory="false"
							cssClass="hasNumber form-control" maxlegnth="12">
						</apptags:input>

					</div>

					<div class="form-group" align="center">OR</div>
					<div class="form-group"> 
						<apptags:input labelCode="Application Id."
							path="birthRegDto.applicationId" isMandatory="false"
							cssClass="hasNumber form-control" maxlegnth="12">
						</apptags:input>
					</div>

					<div class="form-group" align="center">OR</div>
					<div class="form-group"> 
						<apptags:input labelCode="Registration No."
							path="birthRegDto.brRegNo" isMandatory="fales"
							cssClass="hasNumber form-control" maxlegnth="10">
						</apptags:input>
					</div>

					<div class="form-group" align="center">OR</div>
					<div class="form-group">
						<apptags:date fieldclass="datepicker"
							labelCode="Registration Date" datePath="birthRegDto.brRegDate"
							isMandatory="false" cssClass="custDate mandColorClass date">
						</apptags:date>
					</div> --%>

					<!-- <div class="text-center"> 
						<button type="button" class="btn btn-blue-3" title="Search"
							onclick="searchBirthData(this)">
							Search
						</button>
						<button type="button" class="btn btn-warning btn-yellow-2"
							title="Submit"
							onclick="window.location.href ='BirthCorrectionForm.html'">
							Reset
						</button>
					</div> -->

					<!-- fetch data after search start -->
					<%-- <h4>
						<spring:message code="BirthRegistrationDTO.birthRegistration" text="Birth Registration" />
					</h4> --%>

					<%-- <div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Birth Type"
								text="Birth Type" />
						</label>
						<c:set var="baseLookupCode" value="REF" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdRefTypeId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Birth Type"
								text="Birth Type" />
						</label>
						<c:set var="baseLookupCode" value="REF" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="tbBirthregcorrDTO.cpdRefTypeId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />	
					</div>	 --%>	
					
					<div class="form-group">		
						<apptags:date fieldclass="datepicker" labelCode="BirthRegistrationDTO.brDob"
							datePath="birthRegDto.brDob" cssClass="form-control mandColorClass BRDOB" isDisabled="true" >
						</apptags:date>
						<apptags:date fieldclass="datepicker" labelCode="BirthRegistrationDTO.brDob" isDisabled="true"
							datePath="tbBirthregcorrDTO.brDob" isMandatory="true"
							cssClass="custDate mandColorClass date BRDOB">
						</apptags:date>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brSex" isDisabled="true"
							path="birthRegDto.brSex" isMandatory=""
							cssClass="hasNameClass form-control BRSEX" maxlegnth="400">
						</apptags:input>	
						<apptags:input labelCode="BirthRegistrationDTO.brSex" isDisabled="true"
							path="tbBirthregcorrDTO.brSex" isMandatory=""
							cssClass="hasNameClass form-control BRSEX" maxlegnth="400">
						</apptags:input>
							
					</div>
					<%-- <div class="form-group">		
						<apptags:input labelCode="Birth Weight(in kg)" isDisabled="true"
							path="birthRegDto.BrBirthWt" isMandatory="true"
							cssClass="hasDecimal form-control" maxlegnth="10">
						</apptags:input>
						<apptags:input labelCode="Birth Weight(in kg)" isDisabled="true"
							path="tbBirthregcorrDTO.BrBirthWt" isMandatory="true"
							cssClass="hasDecimal form-control" maxlegnth="10">
						</apptags:input>
					</div> --%>

					<h4>
						<spring:message code="BirthRegistrationDTO.childbirthDetails" text="Child Birth Details" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brChildName" isDisabled="true"
							path="birthRegDto.brChildName" isMandatory=""
							cssClass="hasNameClass form-control BRCHILDNAME" maxlegnth="400">
						</apptags:input> 
						<apptags:input labelCode="BirthRegistrationDTO.brChildName" isDisabled="true"
							path="tbBirthregcorrDTO.brChildName" isMandatory=""
							cssClass="hasNameClass form-control BRCHILDNAME" maxlegnth="400">
						</apptags:input>
					</div>	
					<div class="form-group">	
						<apptags:input labelCode="BirthRegistrationDTO.brChildNameMar" isDisabled="true"
							path="birthRegDto.brChildNameMar" isMandatory=""
							cssClass="hasNameClass form-control BRCHILDNAME" maxlegnth="400">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brChildNameMar" isDisabled="true"
							path="tbBirthregcorrDTO.brChildNameMar" isMandatory=""
							cssClass="hasNameClass form-control BRCHILDNAME" maxlegnth="400">
						</apptags:input>
					</div>

					<%-- <div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.brBirthPlaceType"
								text="Birth Place Type" />
						</label>
						<c:set var="baseLookupCode" value="DPT" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.brBirthPlaceType" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
							
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.brBirthPlaceType"
								text="Birth Place Type" />
						</label>
						<c:set var="baseLookupCode" value="DPT" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.brBirthPlaceType" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />	
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.hiId"
								text="Hospital Name" />
						</label>
						<div class="col-sm-4">
							<form:select path="birthRegDto.hiId" cssClass="form-control" disabled="true"
								id="hospitalList" data-rule-required="true">
								<form:option value="">
									<spring:message code="Select" text="Select" />
								</form:option>
								<c:forEach items="${hospitalList}" var="hospList">
									<form:option value="${hospList.hiId}">${hospList.hiName}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.hiId"
								text="Hospital Name" />
						</label>
						<div class="col-sm-4">
							<form:select path="birthRegDto.hiId" cssClass="form-control" disabled="true"
								id="hospitalList" data-rule-required="true">
								<form:option value="">
									<spring:message code="Select" text="Select" />
								</form:option>
								<c:forEach items="${hospitalList}" var="hospList">
									<form:option value="${hospList.hiId}">${hospList.hiName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div> --%>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brBirthPlace" isDisabled="true"
							path="birthRegDto.brBirthPlace" isMandatory="true"
							cssClass="hasNameClass form-control BRBIRTHPLACE" maxlegnth="200">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brBirthPlace" isDisabled="true"
							path="tbBirthregcorrDTO.brBirthPlace" isMandatory="true"
							cssClass="hasNameClass form-control BRBIRTHPLACE" maxlegnth="200">
						</apptags:input>
					</div>	
					<div class="form-group">	
						<apptags:input labelCode="BirthRegistrationDTO.brBirthPlaceMar" isDisabled="true"
							path="birthRegDto.brBirthPlaceMar" isMandatory="true"
							cssClass="hasNameClass form-control BRBIRTHPLACE" maxlegnth="200">
						</apptags:input>
						
						<apptags:input labelCode="BirthRegistrationDTO.brBirthPlaceMar" isDisabled="true"
							path="tbBirthregcorrDTO.brBirthPlaceMar" isMandatory="true"
							cssClass="hasNameClass form-control BRBIRTHPLACE" maxlegnth="200">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brBirthAddr" isDisabled="true"
							path="birthRegDto.brBirthAddr" isMandatory="true"
							cssClass="alphaNumeric BRBIRTHADDR" maxlegnth="800">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brBirthAddr" isDisabled="true"
							path="tbBirthregcorrDTO.brBirthAddr" isMandatory="true"
							cssClass="alphaNumeric BRBIRTHADDR" maxlegnth="800">
						</apptags:input>
					</div>	
					<div class="form-group">	
						<apptags:input labelCode="BirthRegistrationDTO.brBirthAddrMar" isDisabled="true"
							path="birthRegDto.brBirthAddrMar" isMandatory="true"
							cssClass="alphaNumeric BRBIRTHADDR" maxlegnth="800">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brBirthAddrMar" isDisabled="true"
							path="tbBirthregcorrDTO.brBirthAddrMar" isMandatory="true"
							cssClass="alphaNumeric BRBIRTHADDR" maxlegnth="800">
						</apptags:input>
					</div>
					<h4>
						<spring:message code="TbDeathregDTO.form.informantDetails"
							text="Informant Details" />
					</h4>
					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brInformantName" isDisabled="true"
							path="birthRegDto.brInformantName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="800">
						</apptags:input>
						
						<apptags:input labelCode="BirthRegistrationDTO.brInformantName" isDisabled="true"
							path="tbBirthregcorrDTO.brInformantName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="800">
						</apptags:input>
						</div>
						<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brInformantAddr" isDisabled="true"
							path="birthRegDto.brInformantAddr" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brInformantAddr" isDisabled="true"
							path="tbBirthregcorrDTO.brInformantAddr" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800">
						</apptags:input>
					</div>	


						<%-- <div class="form-group">
						<apptags:input labelCode="Informant Name(in English)" isDisabled="true"
							path="birthRegDto.brInformantName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="800">
						</apptags:input>
						<apptags:input labelCode="Informant Name(in English)" isDisabled="true"
							path="tbBirthregcorrDTO.brInformantName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="800">
						</apptags:input>
					</div>	
					<div class="form-group">	
						<apptags:input labelCode="Informant Name(in Marathi)" isDisabled="true"
							path="birthRegDto.brInformantNameMar" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="800">
						</apptags:input>
						<apptags:input labelCode="Informant Name(in Marathi)" isDisabled="true"
							path="tbBirthregcorrDTO.brInformantNameMar" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="800">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="Informant Address(in English)" isDisabled="true"
							path="birthRegDto.brInformantAddr" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800">
						</apptags:input>
						<apptags:input labelCode="Informant Address(in English)" isDisabled="true"
							path="tbBirthregcorrDTO.brInformantAddr" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800">
						</apptags:input>
					</div>	
					<div class="form-group">	
						<apptags:input labelCode="Informant Address(in Marathi)" isDisabled="true"
							path="birthRegDto.brInformantAddrMar" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800">
						</apptags:input>
						<apptags:input labelCode="Informant Address(in Marathi)" isDisabled="true"
							path="tbBirthregcorrDTO.brInformantAddrMar" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800">
						</apptags:input>
					</div> --%>

					<%-- <div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"><spring:message code="Attention Type"
								text="Attention Type" /> </label>
						<c:set var="baseLookupCode" value="ATT" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdAttntypeId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div> --%>
			<%-- 		<div class="form-group">
					<label class="control-label col-sm-2 required-control"
							for="Census"><spring:message code="Attention Type"
								text="Attention Type" /> </label>
						<c:set var="baseLookupCode" value="ATT" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdAttntypeId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
						
					<label class="control-label col-sm-2 required-control"
							for="Census"><spring:message code="Attention Type"
								text="Attention Type" /> </label>
						<c:set var="baseLookupCode" value="ATT" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdAttntypeId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />				
					</div>		
							
					<div class="form-group">		
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Delivery Method"
								text="Delivery Method" />
						</label>
						<c:set var="baseLookupCode" value="DEM" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdDelMethId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Delivery Method"
								text="Delivery Method" />
						</label>
						<c:set var="baseLookupCode" value="DEM" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdDelMethId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />		
					</div> --%>

					<%-- <div class="form-group">
						<apptags:input labelCode="Pregnancy Duration(in Weeks)" isDisabled="true"
							path="birthRegDto.brPregDuratn" isMandatory="true"
							cssClass="hasNumber form-control" maxlegnth="10">
						</apptags:input>
						<apptags:input labelCode="Pregnancy Duration(in Weeks)" isDisabled="true"
							path="tbBirthregcorrDTO.brPregDuratn" isMandatory="true"
							cssClass="hasNumber form-control" maxlegnth="10">
						</apptags:input>
					</div>	
					<div class="form-group">	
						<apptags:input labelCode="Birth Mark"
							path="birthRegDto.brBirthMark" isMandatory="true" isDisabled="true"
							cssClass="hasNameClass form-control" maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="Birth Mark"
							path="tbBirthregcorrDTO.brBirthMark" isMandatory="true" isDisabled="true"
							cssClass="hasNameClass form-control" maxlegnth="350">
						</apptags:input>
					</div> --%>

					 <h4>
						<spring:message code="BirthRegistrationDTO.parentDetails" text="Parent Details" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.pdFathername" isDisabled="true"
							path="birthRegDto.parentDetailDTO.pdFathername"
							isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.pdFathername" isDisabled="true"
							path="tbBirthregcorrDTO.pdFathername"
							isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
							maxlegnth="350">
						</apptags:input>
					</div>	
					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.pdFathernameMar" isDisabled="true"
							path="birthRegDto.parentDetailDTO.pdFathernameMar"
							isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.pdFathernameMar" isDisabled="true"
							path="tbBirthregcorrDTO.pdFathernameMar"
							isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
							maxlegnth="350">
						</apptags:input>
					</div>
					<div class="form-group">

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegDto.BrFatherEdu"
								text="Select Father Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdFEducnId" disabled="true"
							cssClass="form-control CPDFEDUCNID" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegDto.BrFatherEdu"
								text="Select Father Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbBirthregcorrDTO.cpdFEducnId" cssClass="form-control CPDFEDUCNID"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>
					<div class="form-group">

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegDto.BrFatherOcc"
								text="Select Father Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdFOccuId" disabled="true"
							cssClass="form-control CPDFOCCUID" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegDto.BrFatherOcc"
								text="Select Father Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbBirthregcorrDTO.cpdFOccuId" cssClass="form-control CPDFOCCUID"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					<%-- <div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Education"
								text="Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="parentDtlDto.cpdFEducnId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>		
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Occupation"
								text="Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="parentDtlDto.cpdFOccuId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>
 --%>
					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.pdMothername" isDisabled="true"
							path="birthRegDto.parentDetailDTO.pdMothername"
							isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.pdMothername" isDisabled="true"
							path="tbBirthregcorrDTO.pdMothername"
							isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
							maxlegnth="350">
						</apptags:input>	
					</div>
					<div class="form-group">	
						<apptags:input labelCode="ParentDetailDTO.pdMothernameMar" isDisabled="true"
							path="birthRegDto.parentDetailDTO.pdMothernameMar"
							isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.pdMothernameMar" isDisabled="true"
							path="tbBirthregcorrDTO.pdMothernameMar"
							isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
							maxlegnth="350">
						</apptags:input>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegDto.BrMotherEdu"
								text="Mother Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdMEducnId" disabled="true"
							cssClass="form-control CPDMEDUCNID" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegDto.BrMotherEdu"
								text="Mother Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbBirthregcorrDTO.cpdMEducnId"
							cssClass="form-control CPDMEDUCNID" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

					</div>
					<div class="form-group">

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegDto.BrMotherOcc"
								text=" Mother Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="birthRegDto.parentDetailDTO.cpdMOccuId"
							cssClass="form-control CPDMOCCUID" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegDto.BrMotherOcc"
								text=" Mother Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbBirthregcorrDTO.cpdMOccuId"
							cssClass="form-control CPDMOCCUID" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					<div class="form-group">

						<apptags:input labelCode="ParentDetailDTO.pdAgeAtBirth" isDisabled="true"
							path="birthRegDto.parentDetailDTO.pdAgeAtBirth"
							isMandatory="true" cssClass="hasNumber form-control PDAGEATBIRTH"
							maxlegnth="2">
						</apptags:input>

						<apptags:input labelCode="ParentDetailDTO.pdAgeAtBirth" isDisabled="true"
							path="tbBirthregcorrDTO.pdAgeAtBirth"
							isMandatory="true" cssClass="hasNumber form-control PDAGEATBIRTH"
							maxlegnth="2">
						</apptags:input>
					</div>

					<div class="form-group">

						<apptags:input labelCode="ParentDetailDTO.pdLiveChildn" isDisabled="true"
							path="birthRegDto.parentDetailDTO.pdLiveChildn"
							isMandatory="true" cssClass="hasNumber form-control PDLIVECHILDN"
							maxlegnth="2">
						</apptags:input>

						<apptags:input labelCode="ParentDetailDTO.pdLiveChildn" isDisabled="true"
							path="tbBirthregcorrDTO.pdLiveChildn"
							isMandatory="true" cssClass="hasNumber form-control PDLIVECHILDN"
							maxlegnth="2">
						</apptags:input>
					</div>

					<%-- 	<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Education"
								text="Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="parentDtlDto.cpdMEducnId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>		
					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Occupation"
								text="Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="parentDtlDto.cpdMOccuId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					<div class="form-group">
						<apptags:input labelCode="Mother's Age At The Time Of Marraige" isDisabled="true"
							path="parentDtlDto.pdAgeAtMarry"
							isMandatory="true" cssClass="hasNumber form-control"
							maxlegnth="11">
						</apptags:input>
						<apptags:input labelCode="Mother's Age At The Time Of Marraige" isDisabled="true"
							path=""
							isMandatory="true" cssClass="hasNumber form-control"
							maxlegnth="11">
						</apptags:input>
					</div>
					<div class="form-group">	
						<apptags:input isDisabled="true"
							labelCode="Mother's Age At The Time Of Child's Birth"
							path="parentDtlDto.pdAgeAtBirth"
							isMandatory="true" cssClass="hasNumber form-control"
							maxlegnth="11">
						</apptags:input>
						<apptags:input isDisabled="true"
							labelCode="Mother's Age At The Time Of Child's Birth"
							path=""
							isMandatory="true" cssClass="hasNumber form-control"
							maxlegnth="11">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="Number Of Live Childrens" isDisabled="true"
							path="parentDtlDto.pdLiveChildn"
							isMandatory="true" cssClass="hasNumber form-control"
							maxlegnth="11">
						</apptags:input>
						<apptags:input labelCode="Number Of Live Childrens" isDisabled="true"
							path=""
							isMandatory="true" cssClass="hasNumber form-control"
							maxlegnth="11">
						</apptags:input>
						
					</div> --%> 

					 <h4>
						<spring:message code="ParentDetailDTO.parentAddressDetails" text="Parent's Address Details" />
					</h4>

					<%-- <div class="form-group">
						<apptags:input labelCode="Mother's Address(in English)" isDisabled="true"
							path="parentDtlDto.motheraddress"
							isMandatory="true" cssClass="alphaNumeric" maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="Mother's Address(in English)" isDisabled="true"
							path=""
							isMandatory="true" cssClass="alphaNumeric" maxlegnth="350">
						</apptags:input>
					</div>
					<div class="form-group">	
						<apptags:input labelCode="Mother's Address(in Marathi)" isDisabled="true"
							path="parentDtlDto.motheraddress"
							isMandatory="true" cssClass="alphaNumeric" maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="Mother's Address(in Marathi)" isDisabled="true"
							path=""
							isMandatory="true" cssClass="alphaNumeric" maxlegnth="350">
						</apptags:input>
					</div> --%>

					<div class="form-group">
						<apptags:input isDisabled="true"
							labelCode="ParentDetailDTO.pdParaddress"
							path="birthRegDto.parentDetailDTO.pdParaddress"
							isMandatory="false" cssClass="alphaNumeric ADDRESS" maxlegnth="350">
						</apptags:input>
						<apptags:input isDisabled="true"
							labelCode="ParentDetailDTO.pdParaddress"
							path="tbBirthregcorrDTO.pdParaddress" isMandatory="true"
							cssClass="alphaNumeric ADDRESS" maxlegnth="350">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input isDisabled="true"
							labelCode="ParentDetailDTO.pdParaddressMar"
							path="birthRegDto.parentDetailDTO.pdParaddressMar"
							isMandatory="false" cssClass="alphaNumeric ADDRESS" maxlegnth="350">
						</apptags:input>
						<apptags:input isDisabled="true"
							labelCode="ParentDetailDTO.pdParaddressMar"
							path="tbBirthregcorrDTO.pdParaddressMar" isMandatory="true"
							cssClass="alphaNumeric ADDRESS" maxlegnth="350">
						</apptags:input>
					</div>


					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr" isDisabled="true"
							path="birthRegDto.parentDetailDTO.pdAddress" isMandatory="true"
							cssClass="hasNumClass form-control ADDRESS" maxlegnth="350">
						</apptags:input>

						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr" isDisabled="true"
							path="tbBirthregcorrDTO.parentDetailDTO.pdAddress"
							isMandatory="true" cssClass="hasNumClass form-control ADDRESS"
							maxlegnth="350">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addrMar" isDisabled="true"
							path="birthRegDto.parentDetailDTO.pdAddressMar"
							isMandatory="true" cssClass="hasNumClass form-control ADDRESS"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addrMar" isDisabled="true"
							path="tbBirthregcorrDTO.parentDetailDTO.pdAddressMar"
							isMandatory="true" cssClass="hasNumClass form-control ADDRESS"
							maxlegnth="350">
						</apptags:input>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="ParentDetailDTO.cpdReligionId"
								text="Religion" />
						</label>
						<c:set var="baseLookupCode" value="RLG" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}" 
							path="birthRegDto.parentDetailDTO.cpdReligionId"
							cssClass="form-control CPDRELIGIONID" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="ParentDetailDTO.cpdReligionId"
								text="Religion" />
						</label>
						<c:set var="baseLookupCode" value="RLG" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="tbBirthregcorrDTO.cpdReligionId"
							cssClass="form-control CPDRELIGIONID" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					<%-- <div class="form-group">
						<apptags:lookupFieldSet cssClass="form-control required-control margin-bottom-10" disabled="true"
							baseLookupCode="TRY" hasId="true"
							pathPrefix="parentDtlDto.cpdId"
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true" showAll="false" />
					</div> --%>

					<%-- <div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Religion"
								text="Religion" />
						</label>
						<c:set var="baseLookupCode" value="REG" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdReligionId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div> --%>
					
<%-- 					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Religion"
								text="Religion" />
						</label>
						<c:set var="baseLookupCode" value="REG" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdReligionId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
			
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Religion"
								text="Religion" />
						</label>
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="tbBirthregcorrDTO.parentDetailDTO.cpdReligionId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div> --%>
					
<%-- 					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Reg Unit"
								text="Reg Unit" />
						</label>
						<c:set var="baseLookupCode" value="REU" />
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.pdRegUnitId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
							
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="Reg Unit"
								text="Reg Unit" />
						</label>
						<apptags:lookupField disabled="true"
							items="${command.getLevelData(baseLookupCode)}"
							path="tbBirthregcorrDTO.parentDetailDTO.pdRegUnitId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />	
					</div> --%>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="applicant.wardName" text="WARD" />
						</label>
						<c:set var="baseLookupCode" value="BWD" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="birthRegDto.wardid" cssClass="form-control WARDID"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="applicant.wardName" text="WARD" />
						</label>
						<c:set var="baseLookupCode" value="BWD" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}" disabled="true"
							path="tbBirthregcorrDTO.wardid" cssClass="form-control WARDID"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					<div class="form-group">
					<apptags:input
							labelCode="BirthRegDto.brRegNo"
							path="birthRegDto.brRegNo" isReadonly="true"
							isMandatory="false" cssClass="alphaNumeric" maxlegnth="20">
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
																	actionUrl="BirthRegistrationCorrectionApproval.html?Download">
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
							  	labelCode="nac.status" path="tbBirthregcorrDTO.birthRegstatus" defaultCheckedValue="APPROVED" 
							  	changeHandler="ApplyChargeDisable()">
							  </apptags:radio> --%>
					          <br/>
					      		<apptags:textArea
								labelCode="TbDeathregDTO.form.remark" isMandatory="true" isDisabled="true"
								path="tbBirthregcorrDTO.birthRegremark" cssClass="hasNumClass form-control"
								maxlegnth="100" /> 
					</div>
					
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<apptags:CheckerAction hideForward="true" hideUpload="true" ></apptags:CheckerAction>
					</div>
					
				<%-- 	<div class="form-group">
					      		<apptags:textArea
								labelCode="bnd.final.remark" isMandatory="true"
								path="birthRegDto.authRemark" cssClass="hasNumClass form-control"
								maxlegnth="100" /> 
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
									onclick="saveBirthRegCorrApprLOI(this)">
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



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

<script type="text/javascript" src="js/birthAndDeath/dataEntryForbirthRegApproval.js"></script>


<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="dataentry.birthRegForm" text="Birth Registration Data Entry Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="frmBirthRegApproval"
					action="DataEntryBirthRegApproval.html" method="POST"
					class="form-horizontal" name="birthRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<h4>
						<spring:message code="dataentry.birthReg" text="Birth Registration" />
					</h4>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.cpdRefTypeId"
								text="Birth Type" />
						</label>
						<c:set var="baseLookupCode" value="REF" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdRefTypeId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>
							
						<apptags:date fieldclass="datepicker"
							labelCode="BirthRegistrationDTO.brDob" readonly="true" isDisabled="true"
						    datePath="birthRegDto.brDob" isMandatory="true">
						</apptags:date>							
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.brSex" text="Sex" />
						</label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.brSex" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>
						<apptags:input labelCode="Birth Weight(in kg)"
							path="birthRegDto.BrBirthWt" isMandatory="true"
							cssClass="hasDecimal form-control" maxlegnth="3" isReadonly="true">
						</apptags:input>
					</div>

					<h4>
						<spring:message code="BirthRegistrationDTO.childbirthDetails" text="Child Birth Details" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brChildName"
							path="birthRegDto.brChildName" isMandatory=""
							cssClass="hasNameClass form-control" maxlegnth="400" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brChildNameMar"
							path="birthRegDto.brChildNameMar" isMandatory=""
							cssClass="hasNameClass form-control" maxlegnth="400" isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.brBirthPlaceType"
								text="Birth Place Type" />
						</label>
						<c:set var="baseLookupCode" value="DPT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.brBirthPlaceType" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.hiId"
								text="Hospital Name" />
						</label>

						<div class="col-sm-4">
							<form:select path="birthRegDto.hiId" cssClass="form-control"
								id="hospitalList" data-rule-required="true" disabled="true">
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
						<apptags:input labelCode="BirthRegistrationDTO.brBirthPlace"
							path="birthRegDto.brBirthPlace" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="200" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brBirthPlaceMar"
							path="birthRegDto.brBirthPlaceMar" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="200" isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brBirthAddr"
							path="birthRegDto.brBirthAddr" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brBirthAddrMar"
							path="birthRegDto.brBirthAddrMar" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800" isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brInformantName"
							path="birthRegDto.brInformantName" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="800" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brInformantNameMar"
							path="birthRegDto.brInformantNameMar" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="800" isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brInformantAddr"
							path="birthRegDto.brInformantAddr" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brInformantAddrMar"
							path="birthRegDto.brInformantAddrMar" isMandatory="true"
							cssClass="alphaNumeric" maxlegnth="800" isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"><spring:message code="BirthRegistrationDTO.cpdAttntypeId"
								text="Attention Type" /> </label>
						<c:set var="baseLookupCode" value="ATT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdAttntypeId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="BirthRegistrationDTO.cpdDelMethId"
								text="Delivery Method" />
						</label>
						<c:set var="baseLookupCode" value="DEM" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.cpdDelMethId" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brPregDuratn"
							path="birthRegDto.brPregDuratn" isMandatory="true"
							cssClass="hasNumber form-control" maxlegnth="2" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegistrationDTO.brBirthMark"
							path="birthRegDto.brBirthMark" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="350" isReadonly="true">
						</apptags:input>
					</div>

					<h4>
						<spring:message code="BirthRegistrationDTO.parentDetails" text="Parent Details" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.pdFathername"
							path="birthRegDto.parentDetailDTO.pdFathername"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.pdFathernameMar"
							path="birthRegDto.parentDetailDTO.pdFathernameMar"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350" isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="ParentDetailDTO.cpdFEducnId"
								text="Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdFEducnId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="ParentDetailDTO.cpdFOccuId"
								text="Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdFOccuId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>

					</div>

					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.pdMothername"
							path="birthRegDto.parentDetailDTO.pdMothername"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.pdMothernameMar"
							path="birthRegDto.parentDetailDTO.pdMothernameMar"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350" isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="ParentDetailDTO.cpdMEducnId"
								text="Education" />
						</label>
						<c:set var="baseLookupCode" value="EDU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdMEducnId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="ParentDetailDTO.cpdMOccuId"
								text="Occupation" />
						</label>
						<c:set var="baseLookupCode" value="OCU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdMOccuId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>

					</div>
					
					<div class="form-group">
					<apptags:input labelCode="ParentDetailDTO.pdAgeAtMarry"
					path="birthRegDto.parentDetailDTO.pdAgeAtMarry"
					isMandatory="true" cssClass="hasNumber form-control" isReadonly="true"
					maxlegnth="11">
					</apptags:input>
					<apptags:input
					labelCode="ParentDetailDTO.pdAgeAtBirth"
					path="birthRegDto.parentDetailDTO.pdAgeAtBirth"
					isMandatory="true" cssClass="hasNumber form-control" isReadonly="true"
					maxlegnth="2">
				   </apptags:input>
					</div>


					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.pdLiveChildn"
							path="birthRegDto.parentDetailDTO.pdLiveChildn"
							isMandatory="true" cssClass="hasNumber form-control"
							maxlegnth="2" isReadonly="true">
						</apptags:input>
					</div>

					<h4>
						<spring:message code="ParentDetailDTO.parentAddressDetails" text="Parent's Address Details" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.motheraddress"
							path="birthRegDto.parentDetailDTO.motheraddress"
							isMandatory="true" cssClass="alphaNumeric" maxlegnth="350" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.motheraddressMar"
							path="birthRegDto.parentDetailDTO.motheraddressMar"
							isMandatory="true" cssClass="alphaNumeric" maxlegnth="350"  isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.pdParaddress"
							path="birthRegDto.parentDetailDTO.pdParaddress"
							isMandatory="true" cssClass="alphaNumeric" maxlegnth="350"
							isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.pdParaddressMar"
							path="birthRegDto.parentDetailDTO.pdParaddressMar"
							isMandatory="true" cssClass="alphaNumeric" maxlegnth="350"
							isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr"
							path="birthRegDto.parentDetailDTO.pdAddress" isMandatory="true"
							cssClass="hasNumClass form-control" maxlegnth="350" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addrMar"
							path="birthRegDto.parentDetailDTO.pdAddressMar"
							isMandatory="true" cssClass="hasNumClass form-control"
							maxlegnth="350" isReadonly="true">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:lookupFieldSet
							cssClass="form-control required-control margin-bottom-10"
							baseLookupCode="TRY" hasId="true"
							pathPrefix="birthRegDto.parentDetailDTO.cpdId"
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true" showAll="false"
							disabled="true" />

						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="applicant.wardName" text="WARD" />
						</label>
						<c:set var="baseLookupCode" value="BWD" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.wardid" cssClass="form-control"
							isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="ParentDetailDTO.cpdReligionId"
								text="Religion" />
						</label>
						<c:set var="baseLookupCode" value="RLG" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.cpdReligionId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>
							
							<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message code="ParentDetailDTO.pdRegUnitId"
								text="Registration Unit" />
						</label>
						<c:set var="baseLookupCode" value="REU" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthRegDto.parentDetailDTO.pdRegUnitId"
							cssClass="form-control" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" disabled="true"/>
							
					</div>


					<div class="form-group">
						
							<apptags:date fieldclass="datepicker" labelCode="BirthRegistrationDTO.brRegDate"
							datePath="birthRegDto.brRegDate"  isDisabled="true" readonly="true" >
						</apptags:date>
						
							<apptags:input
							labelCode="BirthRegDto.brRegNo"
							path="birthRegDto.brRegNo"
							isMandatory="false" cssClass="alphaNumeric" maxlegnth="20" isReadonly="true">
						</apptags:input>
					</div>
              
              
              <div class="form-group">

          
       <apptags:radio radioLabel="nac.approve,nac.reject" radioValue="APPROVED,REJECTED" isMandatory="true"
				labelCode="nac.status" path="birthRegDto.birthRegstatus" defaultCheckedValue="APPROVED" >
			    </apptags:radio>
        <br />
			   <%-- <apptags:textArea
				labelCode="TbDeathregDTO.form.remark" isMandatory="true"
				path="birthRegDto.birthRegremark" cssClass="hasNumClass form-control"
				maxlegnth="100" /> --%>
			</div>
             
					<div class="text-center">
						<button type="button" class="btn btn-green-3" title="Submit"
							onclick="saveBirthApprovalData(this)">
							<i class="fa padding-left-4" aria-hidden="true"></i>
							<spring:message code="BirthRegDto.save"/>
						</button>
						
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
							
				</form:form>
			</div>
		</div>
	</div>
</div>



